import helperclasses.Genre;
import helperclasses.Name;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static helperclasses.inputFunctions.*;

public class Book {
    String ISBN;
    int quantity;
    String book_name;
    ArrayList<String> authors;
    ArrayList<String> genre;
    String publisher_id;
    int number_of_pages;
    double unit_price;
    Date date_of_publish;
    double percentage_to_publisher;
    HashSet<String> uids=new HashSet<>();

    public Book(){
        ISBN = "";
        quantity = 0;
        book_name = "";
        authors = new ArrayList<>();
        genre = new ArrayList<>();
        publisher_id = "";
        number_of_pages = 0;
        unit_price = 0.0;
        percentage_to_publisher = 0.0;
        date_of_publish = new Date();
        addIDs(uids, "public.book", "isbn");
    }


    public Book(String isbn, int quant, String bname, ArrayList<String> auths, ArrayList<String> gns, String pid, int pages, double price, double perpub, Date dateop){
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
        addIDs(uids, "public.book", "isbn");
    }

    public void printAuthors(){

    }
    //Book Printing
    public void printBookDetails(){
        System.out.println("Book name       : "+ this.book_name);
        System.out.println("ISBN            : "+ this.ISBN);
        System.out.println("Authors         : ");this.printAuthors();
        System.out.println("Genres          : "+ this.printGenres());
        System.out.println("Price           : "+ this.unit_price);
        System.out.println("Number of Pages : "+ this.number_of_pages);
        System.out.println("Date of publish : "+ this.date_of_publish);
    }
    public String printGenres(){
        return null;
    }


    public Book createBook() {
        Book book = new Book();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        book.ISBN = getIDinput(br,"Enter ISBN: "); //make sure 10 charectors
        book.book_name = getInput(br,"Enter book name: ");
        //ADD authors
        int howManyAuthors = Integer.parseInt(getInput(br,"Number of authors: "));
        for(int i=0; i<howManyAuthors;i++){
            System.out.println("Author "+i+" : ");
            String x = getInput(br,"Enter author full name: ");
            book.authors.add(x);
        }
        //Add Genres
        int howManyGenres =  Integer.parseInt(getInput(br,"Number of Genres: "));
        for(int i=0; i<howManyGenres;i++){
            String val = "Genre : "+i+" : ";
            book.genre.add(getInput(br,val));
        }
        //Publisher must exist
        book.publisher_id = getInput(br,"Enter Publisher ID: ");
        if(!foundItem(book.publisher_id,"publisher_id","publisher")){
            System.out.println("Publisher does't exist :  Come back after adding publisher into system!");
            book.publisher_id=null;
            return null;
        }
        book.percentage_to_publisher = Double.parseDouble(getInput(br,"Enter percent to publisher"));
        book.unit_price = Double.parseDouble(getInput(br,"Enter unit price: "));
        try {
            book.date_of_publish = new SimpleDateFormat("d-M-yyyy").parse(getInput(br, "Date of Publish (d-M-yyyy) : "));
        } catch (ParseException e) {
            System.out.println("Date format was incorrect! Retry!!");
            return null;
        }
        book.number_of_pages = Integer.parseInt(getInput(br,"Number of Pages: "));
        book.quantity = Integer.parseInt(getInput(br,"Quantity: "));
        return book;
    }

    public static void removeBook(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String isbn = getInput(br,"Enter ISBN :");
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            ResultSet bookDeleted = statement.executeQuery("select isbn from public.book where public.book.isbn = '"+isbn+"'");
            if(!bookDeleted.next()){
                System.out.println(isbn+" doesn't exist in the database");
            }else{
                bookDeleted.close();
                PreparedStatement rem = connection.prepareStatement("delete from public.book where public.book.isbn = '"+isbn+"'");
                rem.executeUpdate();
                System.out.println("Your book "+isbn+" is deleted");
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public static void addNewBook(){
        Book book = new Book();
        book = book.createBook();
        if(book == null){
            System.out.println("Make sure the book is created correctly!");
            return;
        }
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")
        ) {
            java.sql.Date pubDate = new java.sql.Date(book.date_of_publish.getTime());
            java.sql.Date req = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            String query = "insert into public.book (isbn,quantity,book_name,number_of_pages,unit_price,date_of_publish,publisher_id,percent_to_publisher,requested_quantity,request_approved,last_request_date )"+
                    " values ('"+book.ISBN+"',"+book.quantity+",'"+book.book_name+"',"+book.number_of_pages+","+book.unit_price+",'"+pubDate+"','"+book.publisher_id+"',"+book.percentage_to_publisher+","+100+",TRUE,'"+req+"');";
            PreparedStatement usr = connection.prepareStatement(query);
            usr.execute();
            String authorQuery;
            for(String author : book.authors)
            {
                authorQuery = "insert into public.author (author_name,isbn)" + "values ('"+author+"','"+book.ISBN+"');";
                PreparedStatement auths = connection.prepareStatement(authorQuery);
                auths.execute();
                auths.close();
            }
            String genQuery;
            for(String genre : book.genre)
            {
                genQuery = "insert into public.genre (genre,isbn)" + "values ('"+genre+"','"+book.ISBN+"');";
                PreparedStatement gens = connection.prepareStatement(genQuery);
                gens.execute();
                gens.close();
            }
            System.out.println("\uD83D\uDCD6 Your "+book.ISBN+" has been added");
        } catch (Exception sqle) {
            System.out.println("Exception addNewBook: " + sqle);
        }
    }


}
