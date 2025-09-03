package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.LoanStatus;
import com.library.model.Member;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.MemberRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoanService {

    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final LoanRepository loanRepo;

    private static final long DUE_SECONDS = 30L;
    private static final int MAX_ACTIVE_LOANS = 5;

    public LoanService(BookRepository bookRepo, MemberRepository memberRepo, LoanRepository loanRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.loanRepo = loanRepo;
    }

    private LocalDateTime calcDueDateTime(LocalDateTime borrowAt) {
        return borrowAt.plusSeconds(DUE_SECONDS);
    }

    public String lendBook(String membershipId, String isbn) {
        Optional<Member> optionalMember = memberRepo.findById(membershipId);
        if (optionalMember.isEmpty()) {
            System.out.println("❌ Member with ID " + membershipId + " was not found in the system.");
            return null;
        }
        Member member = optionalMember.get();

        Optional<Book> optionalBook = bookRepo.findByISBN(isbn);
        if (optionalBook.isEmpty()) {
            System.out.println("❌ Book with ISBN " + isbn + " was not found in the system.");
            return null;
        }
        Book book = optionalBook.get();

        List<Loan> actives = loanRepo.findActiveByMember(membershipId);
        if (actives.size() >= MAX_ACTIVE_LOANS)
            throw new IllegalStateException("MaxLoansExceeded");

        if (book.getAvailableCopies() <= 0)
            throw new IllegalStateException("NoCopiesAvailable");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime due = calcDueDateTime(now);

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
        Optional<Loan> optionalLoan = loanRepo.findById(loanId);
        if (optionalLoan.isEmpty()) {
            System.out.println("❌ Loan with ID " + loanId + " was not found in the system.");
            return -1; // Use -1 to indicate not found
        }
        Loan loan = optionalLoan.get();

        Book book = bookRepo.findByISBN(loan.getIsbn())
                .orElseThrow(() -> new IllegalStateException("BookMissingForLoan: " + loan.getIsbn()));

        int fineEuros;
        if (loan.isOverdue(LocalDateTime.now())) {
            fineEuros = loan.calculateOverdueFine(LocalDateTime.now());
            System.out.println("Book is overdue. Fine: " + fineEuros + "€");
        } else {
            fineEuros = 0;
            System.out.println("Book returned on time. No fine.");
        }

        loan.markReturned();
        loanRepo.save(loan);

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        return fineEuros;
    }


}
