import model.Book;
import model.Patron;

import java.io.Console;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Spencer on 2/7/2017.
 */
public class TestAssgn1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        Book test---------------------------------------------
//        System.out.println("Enter information for a new book");
//        System.out.println("Title: ");
//        String title = scanner.nextLine();
//        System.out.println("Author: ");
//        String author = scanner.nextLine();
//        System.out.println("Publication Year: ");
//        String pubYear = scanner.nextLine();
//
//        Properties bookProps = new Properties();
//        bookProps.setProperty("title", title);
//        bookProps.setProperty("author", author);
//        bookProps.setProperty("pubYear", pubYear);
//        bookProps.setProperty("status", "Active");
//
//        Book book = new Book(bookProps);
//        book.update();

//        Patron test---------------------------------------------
        System.out.println("Enter information for a new Patron");
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("City: ");
        String city = scanner.nextLine();
        System.out.println("State (two-letter abbreviation): ");
        String state = scanner.nextLine();
        System.out.println("ZIP: ");
        String zip = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        Properties patronProps = new Properties();
        patronProps.setProperty("name", name);
        patronProps.setProperty("address", address);
        patronProps.setProperty("city", city);
        patronProps.setProperty("stateCode", state);
        patronProps.setProperty("zip", zip);
        patronProps.setProperty("email", email);
        patronProps.setProperty("dateOfBirth", dob);
        patronProps.setProperty("status", "Active");

        Patron patron = new Patron(patronProps);
        patron.update();
    }
}
