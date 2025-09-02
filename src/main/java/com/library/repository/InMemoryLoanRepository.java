package com.library.repository;

import com.library.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, Loan> byId = new ConcurrentHashMap<>();

    public void save(Loan loan) {
        byId.put(loan.getLoanId(), loan);
    }

    public Optional<Loan> findById(String loanId) {
        return Optional.ofNullable(byId.get(loanId));
    }

    public Optional<Loan> findActiveByMemberAndIsbn(String membershipId, String isbn) {
        return byId.values().stream()
                .filter(I -> I.getMembershipId().equals(membershipId)
                        && I.getIsbn().equals(isbn)
                        && I.getStatus() == LoanStatus.ACTIVE).findFirst();
    }

    public List<Loan> findActiveByMember(String membershipId) {
        return byId.values().stream()
                .filter(I -> I.getMembershipId().equals(membershipId)
                        && I.getStatus() == LoanStatus.ACTIVE).collect(Collectors.toList());
    }

    public List<Loan> findAllActive() {
        return byId.values().stream()
                .filter(I -> I.getStatus() == LoanStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
