package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.MemberRepository;
import com.library.service.LoanService;

import java.util.Optional;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        // Repos in-memory
        BookRepository bookRepo = new BookRepository();
        MemberRepository memberRepo = new MemberRepository();
        LoanRepository loanRepo = new LoanRepository();
        LoanService loanService = new LoanService(bookRepo, memberRepo, loanRepo);

        // Test Data
        seedDemoData(bookRepo, memberRepo);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Library CLI ===");
            System.out.println("1) Borrow a book");
            System.out.println("2) Return a book");
            System.out.println("3) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> borrowFlow(sc, loanService);
                    case "2" -> returnFlow(sc, loanService);
                    case "3" -> { System.out.println("Bye."); return; }
                    default -> System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void borrowFlow(Scanner sc, LoanService loanService) {
        System.out.print("Membership ID: ");
        String mid = sc.nextLine().trim();
        System.out.print("Book ISBN: ");
        String isbn = sc.nextLine().trim();
        String loanId = loanService.lendBook(mid, isbn);
        System.out.println("Loan created. ID = " + loanId);
    }

    private static void returnFlow(Scanner sc, LoanService loanService) {
        System.out.print("Loan ID: ");
        String loanId = sc.nextLine().trim();
        int fine = loanService.returnBook(loanId);
        if (fine < 0) {
            System.out.println("Returned with OVERDUE. Penalty = " + fine + " EUR.");
        }
        else {
            System.out.println("Returned successfully (on time).");
        }
    }

    private static void seedDemoData(BookRepository bookRepo, MemberRepository memberRepo) {
        // Test book
        Book b = new Book("Effective Java", "Joshua Bloch", "2018", "978-0134685991", 2);
        bookRepo.save(b);

        // Test User
        Member m = new Member("Haydar", 28, "M123", "haydartarek@gmail.com", "STANDARD");
        memberRepo.save(m);

        System.out.println("Seeded: Book ISBN=978-0134685991 (2 copies), Member ID=M123");
    }
}