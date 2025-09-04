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

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    /**
     * Checks if the loan is overdue and updates the status if needed.
     * Prints a warning if overdue.
     */
    public void checkAndUpdateOverdue(LocalDateTime now) {
        if (status == LoanStatus.ACTIVE && now.isAfter(dueDateTime)) {
            status = LoanStatus.OVERDUE;
            System.out.println("Overdue! A fine of €1/day will be applied.");
        }
    }

    /**
     * Calculates the overdue fine: €1 per day overdue (minimum 1 if overdue).
     * Returns 0 if not overdue or already returned.
     */
    public int calculateOverdueFine(LocalDateTime now) {
        if (status != LoanStatus.OVERDUE && status != LoanStatus.RETURNED) {
            checkAndUpdateOverdue(now);
        }
        if (status == LoanStatus.OVERDUE) {
            LocalDateTime effectiveReturn = (returnDateTime != null) ? returnDateTime : now;
            long daysOverdue = java.time.Duration.between(dueDateTime, effectiveReturn).toDays();
            return (int) Math.max(1, daysOverdue);
        }
        return 0;
    }

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
