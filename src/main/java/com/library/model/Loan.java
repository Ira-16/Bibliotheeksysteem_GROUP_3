package com.library.model;

import java.util.Date;

public class Loan {
    private Book book;
    private Member member;
    private Date borrowDate;
    private Date dueDate;
    private boolean returned;
}
