import helperclasses.Genre;

import java.util.ArrayList;
import java.util.Date;

public class Book {
    String ISBN;
    int quantity;
    String book_name;
    ArrayList<Author> authors;
    ArrayList<Genre> genre;
    String publisher_id;
    int number_of_pages;
    double unit_price;
    Date date_of_publish;
    double percentage_to_publisher;

    public Book(){
        ISBN = "";
        quantity = 0;
        book_name = "";
        authors = new ArrayList<Author>();
        genre = new ArrayList<Genre>();
        publisher_id = "";
        number_of_pages = 0;
        unit_price = 0.0;
        percentage_to_publisher = 0.0;
        date_of_publish = new Date();
    }


    public Book(String isbn, int quant, String bname, ArrayList<Author> auths, ArrayList<Genre> gns, String pid, int pages, double price, double perpub, Date dateop){
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
    public String printAuthors(){
        return null;

    }
    public String printGenres(){
        return null;
    }
}
