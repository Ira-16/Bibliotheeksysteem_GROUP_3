package com.library.service;

import com.library.model.*;
import com.library.repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoanService {

    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final LoanRepository loanRepo;

    public LoanService(BookRepository bookRepo, MemberRepository memberRepo, LoanRepository loanRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.loanRepo = loanRepo;
    }

    private static final boolean TEST_MODE = true;

    private static final long TEST_DUE_SECONDS = 20L;

    private static final long TEST_SECONDS_PER_DAY = 10L;

    private static final int REAL_DUE_DAYS = 14;

    private static final int MAX_ACTIVE_LOANS = 5;

    private LocalDateTime calcDueDateTime(LocalDateTime borrowAt, Member m) {
        if (TEST_MODE) return borrowAt.plusSeconds(TEST_DUE_SECONDS);
        return borrowAt.plusDays(REAL_DUE_DAYS);
    }

    public String lendBook(String membershipId, String isbn) {
        Member member = memberRepo.findByMembershipId(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("MemberNotFound: " + membershipId));

        Book book = bookRepo.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("BookNotFound: " + isbn));

        List<Loan> actives = loanRepo.findActiveByMember(membershipId);
        if (actives.size() >= MAX_ACTIVE_LOANS)
            throw new IllegalStateException("MaxLoansExceeded");

        if (book.getAvailableCopies() <= 0)
            throw new IllegalStateException("NoCopiesAvailable");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime due = calcDueDateTime(now, member);

        Loan loan = new Loan(
                UUID.randomUUID().toString(),
                book.getISBN(),
                membershipId,
                now,
                due,
                LoanStatus.ACTIVE
        );
        loanRepo.save(loan);
        return loan.getLoanId();
    }

    public int returnBook(String loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("LoanNotFound: " + loanId));

        Book book = bookRepo.findByIsbn(loan.getIsbn())
                .orElseThrow(() -> new IllegalStateException("BookMissingForLoan: " + loan.getIsbn()));

        LocalDateTime now = LocalDateTime.now();

        int fineEuros = 0;
        if (loan.isOverdue(now)) {
            long secondsLate = Duration.between(loan.getDueDateTime(), now).getSeconds();
            if (secondsLate > 0) {
                long unit = TEST_MODE ? TEST_SECONDS_PER_DAY : 86400L;
                int daysLate = (int) Math.ceil(secondsLate / (double) unit);
                fineEuros = Math.max(0, daysLate) * 1; // 1€ لكل يوم
            }
        }

        loan.markReturned();
        loanRepo.save(loan);

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        return fineEuros;
    }

    public Optional<Loan> getLoan(String loanId) { return loanRepo.findById(loanId); }
}
