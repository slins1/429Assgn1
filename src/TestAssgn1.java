import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;

import java.io.Console;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Spencer on 2/7/2017.
 */
public class TestAssgn1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//      Book tests------------------------------------------------
        System.out.println("Enter information for a new book");
        System.out.println("Title: ");
        String title = scanner.nextLine();
        System.out.println("Author: ");
        String author = scanner.nextLine();
        System.out.println("Publication Year: ");
        String pubYear = scanner.nextLine();

        Properties bookProps = new Properties();
        bookProps.setProperty("title", title);
        bookProps.setProperty("author", author);
        bookProps.setProperty("pubYear", pubYear);
        bookProps.setProperty("status", "Active");

        Book book = new Book(bookProps);
        book.update();

//      Patron test--------------------------------------------------
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

//      BookCollection tests--------------------------------------------
        BookCollection bookCollection = new BookCollection();
        System.out.println("Search for books by fragment of title: ");
        String titleFrag = scanner.nextLine();
        bookCollection.findBooksWithTitleLike(titleFrag);
        System.out.println(bookCollection.getBooks().toString());
        System.out.println("");
        System.out.println("Search for books published before a given publication year: ");
        String pubYearSearch = scanner.nextLine();
        bookCollection.findBooksOlderThanDate(pubYearSearch);
        System.out.println(bookCollection.getBooks().toString());
        System.out.println("");

//      PatronCollection tests------------------------------------------
        PatronCollection patronCollection = new PatronCollection();
        System.out.println("Search for patrons younger than a given date: ");
        String patronBdaySearch = scanner.nextLine();
        patronCollection.findPatronYoungerThan(patronBdaySearch);
        System.out.println(patronCollection.getPatrons().toString());
        System.out.println("");
        System.out.println("Search for patrons by zip code: ");
        String zipSearch = scanner.nextLine();
        patronCollection.findPatronAtZip(zipSearch);
        System.out.println(patronCollection.getPatrons().toString());
    }
}
