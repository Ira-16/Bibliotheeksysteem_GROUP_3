package com.library.model;

import java.time.LocalDateTime;

public class Loan {
    private String loanId;
    private String isbn;
    private String membershipId;
    private LocalDateTime borrowDateTime;
    private LocalDateTime dueDateTime;
    private LocalDateTime returnDateTime; //also we need returnDate
    private LoanStatus status;

    public Loan() { }

    public Loan(String loanId, String isbn, String membershipId,
                LocalDateTime borrowDateTime, LocalDateTime dueDateTime, LoanStatus status) {
        this.loanId = loanId;
        this.isbn = isbn;
        this.membershipId = membershipId;
        this.borrowDateTime = borrowDateTime;
        this.dueDateTime = dueDateTime;
        this.status = status;
    }

    public String getLoanId() { return loanId; }
    public String getIsbn() { return isbn; }
    public String getMembershipId() { return membershipId; }
    public LocalDateTime getBorrowDateTime() { return borrowDateTime; }
    public LocalDateTime getDueDateTime() { return dueDateTime; }
    public LocalDateTime getReturnDateTime(){return returnDateTime;}
    public LoanStatus getStatus() { return status; }

    public boolean isOverdue(LocalDateTime now) {
        return status == LoanStatus.ACTIVE && now.isAfter(dueDateTime);
    }

    public void markReturned() {
        if (status != LoanStatus.RETURNED) this.status = LoanStatus.RETURNED;
    }

    @Override
    public String toString() { //ook
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", membershipId='" + membershipId + '\'' +
                ", borrowDateTime=" + borrowDateTime +
                ", dueDateTime=" + dueDateTime +
                ", returnDateTime=" + returnDateTime +
                ", status=" + status +
                '}';
    }
}
