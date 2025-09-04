package com.library;

import com.library.model.*;
import com.library.repository.*;
import com.library.service.*;

import java.util.Scanner;

public class LibraryApp {
    private final Scanner scanner = new Scanner(System.in);

    private final InMemoryAdminRepository adminRepo = new InMemoryAdminRepository();
    private final InMemoryBookRepository bookRepo = new InMemoryBookRepository();
    private final InMemoryMemberRepository memberRepo = new InMemoryMemberRepository();
    private final InMemoryLoanRepository loanRepo = new InMemoryLoanRepository();

    private final AdminService adminService = new AdminService(adminRepo);
    private final BookService bookService = new BookService(bookRepo);
    private final MemberService memberService = new MemberService(memberRepo);
    private final LoanService loanService = new LoanService(bookRepo, memberRepo, loanRepo);

    public static void main(String[] args) {
        new LibraryApp().run();
    }

    private void run() {
        mainAdmin(); // create default admin
        while (true) {
            showStartMenu();
        }
    }

    private void mainAdmin() {
        Admin defaultAdmin = new Admin("Victoria", "victoria123", "V123");
        adminRepo.save(defaultAdmin);
    }

    private void showStartMenu() {
        System.out.println("\n===== 📚 WELCOME TO LIBRARY SYSTEM =====");
        System.out.println("1. 🔐 Sign In");
        System.out.println("2. 🆕 Sign Up (Member)");
        System.out.println("0. 🚪 Exit");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> showLoginTypeMenu();
            case "2" -> signUpMember();
            case "0" -> exitProgram();
            default -> System.out.println("⚠️ Invalid choice!");
        }
    }

    private void showLoginTypeMenu() {
        System.out.println("\n🔐 Sign In as:");
        System.out.println("1. 👩‍💼 Admin");
        System.out.println("2. 👤 Member");
        System.out.println("0. 🔙 Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> loginAdmin();
            case "2" -> loginMember();
            case "0" -> {}
            default -> System.out.println("⚠️ Invalid choice!");
        }
    }

    private void loginAdmin() {
        System.out.print("👩‍💼 Username: ");
        String user = scanner.nextLine();
        System.out.print("🔐 Password: ");
        String pass = scanner.nextLine();
        adminService.login(user, pass);

        if (adminService.isLoggedIn()) {
            showAdminMenu();
        } else {
            System.out.println("❌ Login failed!");
        }
    }

    private void loginMember() {
        System.out.print("👤 Enter member ID: ");
        String id = scanner.nextLine();
        Member member = memberService.findById(id);
        if (member != null) {
            System.out.println("✅ Logged in as Member: " + member.getName());
            showMemberMenu(member);
        } else {
            System.out.println("❌ Member not found!");
        }
    }

    private void signUpMember() {
        System.out.println("🆕 Member Registration:");
        addMemberUI();
    }

    private void showAdminMenu() {
        while (adminService.isLoggedIn()) {
            System.out.println("\n===== 👩‍💼 ADMIN MENU =====");
            System.out.println("1. 📚 Book Management");
            System.out.println("2. 👥 Member Management");
            System.out.println("3. 🔁 Loan Management");
            System.out.println("4. ➕ Add Admin");
            System.out.println("5. 🔒 Logout");
            System.out.print("👉 Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> manageBooks();
                case "2" -> manageMembers();
                case "3" -> manageLoans();
                case "4" -> registerAdminUI();
                case "5" -> adminService.logOut();
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    private void showMemberMenu(Member member) {
        while (true) {
            System.out.println("\n===== 👤 MEMBER MENU =====");
            System.out.println("1. 📖 Borrow Book");
            System.out.println("2. 📦 Return Book");
            System.out.println("3. 🔍 Search by Title");
            System.out.println("4. 🔍 Search by ISBN");
            System.out.println("5. 🔍 Search by Author");
            System.out.println("6. 🔍 Search by Year");
            System.out.println("7. 📚 View All Books");
            System.out.println("0. 🔙 Logout");
            System.out.print("👉 Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> lendBookAsMember(member);
                case "2" -> returnBookUI(member);
                case "3" -> {
                    System.out.print("Title: ");
                    bookService.searchBooksByTitle(scanner.nextLine()).forEach(System.out::println);
                }
                case "4" -> {
                    System.out.print("ISBN: ");
                    System.out.println(bookService.searchBookByISBN(scanner.nextLine()));
                }
                case "5" -> {
                    System.out.print("Author: ");
                    bookService.searchBooksByAuthor(scanner.nextLine()).forEach(System.out::println);
                }
                case "6" -> {
                    int year;
                    while (true) {
                        System.out.print("Year: ");
                        String input = scanner.nextLine();
                        try {
                            year = Integer.parseInt(input);
                            break; // valid input, break the loop
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid numeric year.");
                        }
                    }

                    bookService.searchByYear(year)
                            .forEach(System.out::println);
                }
                case "7" -> bookService.listAll().forEach(System.out::println);
                case "0" -> {
                    System.out.println("👋 Logged out.");
                    return;
                }
                default -> System.out.println("⚠️ Invalid choice!");

            }
        }
    }

    // Admin-Only: Add other Admins
    private void registerAdminUI() {
        if (adminService.isLoggedIn()) {
            System.out.print("Name of new Admin: ");
            String user = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            System.out.print("Employee ID: ");
            String empId = scanner.nextLine();

            Admin newAdmin = new Admin(user, pass, empId);
            adminRepo.save(newAdmin);
            System.out.println("✅ New admin added: " + user);
        } else {
            System.out.println("⚠️ Only main admin can add new admins!");
        }
    }

    // Book Management
    private void manageBooks() {
        System.out.println("\n📚 Book Management:");
        System.out.println("1. ➕ Add Book");
        System.out.println("2. ✏️ Edit Book");
        System.out.println("3. ❌ Delete Book");
        System.out.println("4. 🔍 Search by Title");
        System.out.println("5. 🔍 Search by ISBN");
        System.out.println("6. 🔍 Search by Author");
        System.out.println("7. 🔍 Search by Year");
        System.out.println("8. 📋 List All Books");
        System.out.println("0. 🔙 Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> addBookUI();
            case "2" -> editBookUI();
            case "3" -> deleteBookUI();
            case "4" -> {
                System.out.print("Title: ");
                bookService.searchBooksByTitle(scanner.nextLine()).forEach(System.out::println);
            }
            case "5" -> {
                System.out.print("ISBN: ");
                System.out.println(bookService.searchBookByISBN(scanner.nextLine()));
            }
            case "6" -> {
                System.out.print("Author: ");
                bookService.searchBooksByAuthor(scanner.nextLine()).forEach(System.out::println);
            }
            case "7" -> {
                int year;
                while (true) {
                    System.out.print("Year: ");
                    String input = scanner.nextLine();
                    try {
                        year = Integer.parseInt(input);
                        break; // valid input, break the loop
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid numeric year.");
                    }
                }

                bookService.searchByYear(year)
                        .forEach(System.out::println);
            }
            case "8" -> bookService.listAll().forEach(System.out::println);
            case "0" -> {}
            default -> System.out.println("⚠️ Invalid choice!");
        }
    }

    private void addBookUI() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Total Copies: ");
        int copies = Integer.parseInt(scanner.nextLine());
        bookService.addBook(new Book(title, author, year, isbn, copies));
        System.out.println("✅ Book added!");
    }

    private void editBookUI() {
        System.out.print("Enter ISBN to edit: ");
        String isbn = scanner.nextLine();
        Book book = bookService.searchBookByISBN(isbn);
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }
        System.out.print("New Title (" + book.getTitle() + "): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isBlank()) book.setTitle(newTitle);
        System.out.print("New Author (" + book.getAuthor() + "): ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.isBlank()) book.setAuthor(newAuthor);
        bookService.editBook(book);
        System.out.println("✅ Book updated!");
    }

    private void deleteBookUI() {
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine();
        Book book = bookService.searchBookByISBN(isbn);
        if (book != null) {
            bookService.removeBook(book);
            System.out.println("✅ Book removed!");
        } else {
            System.out.println("❌ Book not found.");
        }
    }

    // Member Management
    private void manageMembers() {
        System.out.println("\n👥 Member Management:");
        System.out.println("1. ➕ Add Member");
        System.out.println("2. ✏️ Edit Member");
        System.out.println("3. ❌ Delete Member");
        System.out.println("4. 📋 List Members");
        System.out.println("0. 🔙 Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> addMemberUI();
            case "2" -> editMemberUI();
            case "3" -> deleteMemberUI();
            case "4" -> memberService.listAll().forEach(System.out::println);
            case "0" -> {}
            default -> System.out.println("⚠️ Invalid choice!");
        }
    }

    private void addMemberUI() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Email: ");
        String contact = scanner.nextLine();
        memberService.register(new Member(id, name, age, contact));
    }

    private void editMemberUI() {
        System.out.print("Enter ID to edit: ");
        String id = scanner.nextLine();
        Member m = memberService.findById(id);
        if (m == null) {
            System.out.println("❌ Member not found.");
            return;
        }
        System.out.print("New Name (" + m.getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) m.setName(newName);
        memberService.edit(m);
        System.out.println("✅ Member updated.");
    }

    private void deleteMemberUI() {
        System.out.print("Enter ID to delete: ");
        String id = scanner.nextLine();
        memberService.delete(id);
        System.out.println("✅ Member deleted.");
    }

    // Loans
    private void manageLoans() {
        System.out.println("\n📖 Loan Management:");
        System.out.println("1. ➕ Borrow Book");
        System.out.println("2. 📦 Return Book");
        System.out.println("0. 🔙 Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> lendBookUI();
            case "2" -> System.out.println("⚠️ Only members can return books from their own account.");
            case "0" -> {}
            default -> System.out.println("⚠️ Invalid choice!");
        }
    }

    private void lendBookUI() {
        System.out.print("Member ID: ");
        String mid = scanner.nextLine();
        Member member = memberService.findById(mid);
        if (member == null) {
            System.out.println("❌ Member with ID " + mid + " was not found.");
            return;
        }
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        String loanId = loanService.lendBook(mid, isbn);
        if (loanId != null) {
            System.out.println("✅ Book lent. Loan ID: " + loanId);
        }
    }

    private void lendBookAsMember(Member member) {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        String loanId = loanService.lendBook(member.getMembershipId(), isbn);
        if (loanId != null) {
            System.out.println("✅ Book lent. Loan ID: " + loanId);
        }
    }

    private void returnBookUI(Member member) {
        var activeLoans = loanRepo.findActiveByMember(member.getMembershipId());
        if (activeLoans == null || activeLoans.isEmpty()) {
            System.out.println("⚠️ You have not borrowed any books yet.");
            System.out.println("📭 Your loan list is empty.");
            return;
        }
        System.out.print("Loan ID: ");
        String loanId = scanner.nextLine();
        int fine = loanService.returnBook(loanId);
        if (fine == -1) {
            System.out.println("❗ write the Loan ID correctly.");
            return;
        }
        if (fine > 0) {
            System.out.println("⚠️ Late return. Fine: " + fine + "€");
        } else {
            System.out.println("✅ Book returned.");
        }
    }

    // Exit
    private void exitProgram() {
        System.out.println("👋 Goodbye!");
    }
}
