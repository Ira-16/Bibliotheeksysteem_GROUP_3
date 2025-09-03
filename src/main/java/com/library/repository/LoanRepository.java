package com.library.repository;

import com.library.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);

    Optional<Loan> findById(String loanId);

    Optional<Loan> findActiveByMemberAndIsbn(String membershipId, String isbn);

    List<Loan> findActiveByMember(String membershipId);

    List<Loan> findAllActive();
}
