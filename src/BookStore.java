import helperclasses.Genre;

import java.util.ArrayList;
import java.util.Date;

public class BookStore {
    String ISBN;
    int quantity;
    String book_name;
    ArrayList<Author> authors;
    ArrayList<Genre> genre;
    String publisher_id;
    int number_of_pages;
    double unit_price;
    private double percentage_to_publisher;
    Date date_of_publish;

    public BookStore(String isbn, int quant, String bname, ArrayList<Author> auths, ArrayList<Genre> gns, String pid, int pages, double price, double perpub, Date dateop){
        ISBN = isbn;
        quantity = quant;
        book_name = bname;
        authors = auths;
        genre = gns;
        publisher_id = pid;
        number_of_pages = pages;
        unit_price = price;
        percentage_to_publisher = perpub;
        date_of_publish = dateop;
    }
}
