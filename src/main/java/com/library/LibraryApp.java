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
        mainAdmin();
        while (true) {
            if (!adminService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void mainAdmin() {
        Admin defaultAdmin = new Admin("Victoria", "victoria123", "V123");
        adminRepo.save(defaultAdmin);
    }

    private void showLoginMenu() {
        System.out.println("\n===== 📚 LIBRARY SYSTEM =====");
        System.out.println("1. Log in Admin");
        System.out.println("2. Add admin");
        System.out.println("0. Log Out");
        System.out.print("👉 Choose: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> loginAdmin();
            case "2" -> registerAdminUI();
            case "0" -> exitProgram();
            default -> System.out.println("⚠️ Wrong choice!");
        }
    }
    private void registerAdminUI(){
        if (adminService.isLoggedIn()) {
            System.out.print("Enter the name of new Admin: ");
            String user = scanner.nextLine();
            System.out.print("Enter password: ");
            String pass = scanner.nextLine();
            System.out.print("Enter EmployeeID: ");
            String empId = scanner.nextLine();

            Admin newAdmin = new Admin(user, pass, empId);
            adminRepo.save(newAdmin);
            System.out.println("✅ Registration was successful: " + user);
        } else {
            System.out.println("⚠️ Only main admin can add new admin!");
        }
    }

    private void loginAdmin() {
        System.out.print("Enter user name: ");
        String user = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();
        adminService.login(user, pass);
    }

    private void showMainMenu() {
        System.out.println("\n===== 🛠️ MAIN MENU =====");
        System.out.println("1. Book management");
        System.out.println("2. Member management");
        System.out.println("3. Loan management");
        System.out.println("4. Log out of account");
        System.out.println("0. Exit the program");
        System.out.print("👉 Choose: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> manageBooks();
            case "2" -> manageMembers();
            case "3" -> manageLoans();
            case "4" -> adminService.logOut();
            case "0" -> exitProgram();
            default -> System.out.println("⚠️ Wrong choice!");
        }
    }

    // BOOKS
    private void manageBooks() {
        System.out.println("\n📚 Book management:");
        System.out.println("1. Add Book");
        System.out.println("2. Edit Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Search Book By Title");
        System.out.println("5. List of all books");
        System.out.println("0. Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1" -> addBookUI();
                case "2" -> editBookUI();
                case "3" -> deleteBookUI();
                case "4" -> searchBookByTitleUI();
                case "5" -> bookService.searchBooksByTitle("").forEach(System.out::println);
                case "0" -> {}
                default -> System.out.println("⚠️ Wrong choice!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
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
        String year = scanner.nextLine();
        System.out.print("Total: ");
        int copies = Integer.parseInt(scanner.nextLine());
        bookService.addBook(new Book(title, author, year, isbn, copies));
        System.out.println("✅ Book added!");
    }

    private void editBookUI() {
        System.out.print("Enter ISBN to update: ");
        String isbn = scanner.nextLine();
        Book book = bookService.searchBookByISBN(isbn);
        System.out.print("New Title (" + book.getTitle() + "): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isBlank()) book.setTitle(newTitle);
        System.out.print("New Author (" + book.getAuthor() + "): ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.isBlank()) book.setAuthor(newAuthor);
        bookService.editBook(book);
        System.out.println("✅ Book Added!");
    }

    private void deleteBookUI() {
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine();
        Book book = bookService.searchBookByISBN(isbn);
        bookService.removeBook(book);
        System.out.println("✅ Book is removed!");
    }

    private void searchBookByTitleUI() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        bookService.searchBooksByTitle(title).forEach(System.out::println);
    }

    // MEMBERS
    private void manageMembers() {
        System.out.println("\n👤 Member menagment:");
        System.out.println("1. Add member");
        System.out.println("2. Edit member");
        System.out.println("3. Delete member");
        System.out.println("4. List of members");
        System.out.println("0. Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1" -> addMemberUI();
                case "2" -> editMemberUI();
                case "3" -> deleteMemberUI();
                case "4" -> memberService.listAll().forEach(System.out::println);
                case "0" -> {}
                default -> System.out.println("⚠️ Wrong choice!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void addMemberUI() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Contact info: ");
        String contact = scanner.nextLine();
        memberService.register(new Member(id, name, age, contact));
    }

    private void editMemberUI() {
        System.out.print("Enter ID to edit: ");
        String id = scanner.nextLine();
        Member m = memberService.findById(id);
        System.out.print("New name (" + m.getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) m.setName(newName);
        memberService.edit(m);
    }

    private void deleteMemberUI() {
        System.out.print("Enter ID to delete: ");
        String id = scanner.nextLine();
        memberService.delete(id);
    }

    // LOANS
    private void manageLoans() {
        System.out.println("\n📖 Loans management:");
        System.out.println("1. Lend book");
        System.out.println("2. Return book");
        System.out.println("0. Back");
        System.out.print("👉 Choose: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1" -> lendBookUI();
                case "2" -> returnBookUI();
                case "0" -> {}
                default -> System.out.println("⚠️ Wrong choice!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void lendBookUI() {
        System.out.print("ID member: ");
        String mid = scanner.nextLine();
        System.out.print("ISBN book: ");
        String isbn = scanner.nextLine();
        String loanId = loanService.lendBook(mid, isbn);
        System.out.println("✅ Book lent, ID: " + loanId);
    }

    private void returnBookUI() {
        System.out.print("ID book: ");
        String loanId = scanner.nextLine();
        int fine = loanService.returnBook(loanId);
        if (fine > 0) {
            System.out.println("⚠️ Time for your book is expired! Fine: " + fine + "€");
        } else {
            System.out.println("✅ The book has been successfully returned!");
        }
    }

    // EXIT
    private void exitProgram() {
        System.out.println("👋 Goodbye!");
        System.exit(0);
    }
}
