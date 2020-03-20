import helperclasses.Genre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        addIDs(uids, "public.bookstore", "isbn");
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
        addIDs(uids, "public.bookstore", "isbn");
    }

    public String iDGen(){
        int selected;
        while(true){
            Random rand = new Random();
            selected = 1000000000+ rand.nextInt(100000000);
            if(!uids.contains(String.valueOf(selected))){
                uids.add(String.valueOf(selected));
                return String.valueOf(selected);
            }
        }
    }

    public String printAuthors(){
        return null;

    }
    //Book Printing
    public void printBookDetails(){
        System.out.println("Book name       : "+ this.book_name);
        System.out.println("ISBN            : "+ this.ISBN);
        System.out.println("Authors         : "+ this.printAuthors());
        System.out.println("Genres          : "+ this.printGenres());
        System.out.println("Price           : "+ this.unit_price);
        System.out.println("Number of Pages : "+ this.number_of_pages);
        System.out.println("Date of publish : "+ this.date_of_publish);
    }
    public String printGenres(){
        return null;
    }

    public boolean foundItem(String id, String columnname, String tablename){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            ResultSet set = statement.executeQuery("select "+columnname+" from public."+tablename+" where "+columnname+" = '"+id+"'");
            if (set.next() == false) {
                System.out.println("No such"+id+"exisits in "+tablename);
                statement.close();connection.close();
                return false;
            }else{
                statement.close();connection.close();
                return true;
            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
            return false;
        }
    }

    public Book createBook() throws IOException, ParseException {
        Book book = new Book();
        boolean authCheck = true;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        book.ISBN = getIDinput(br,"Enter ISBN: "); //make sure 10 charectors
        book.publisher_id = getInput(br,"Enter Publisger ID: ");
        if(!foundItem(book.publisher_id,"publisher_id","publisher")){System.out.println("Publisher does't exist");book.publisher_id=null; authCheck=false;}
        int howManyAuthors = Integer.parseInt(getInput(br,"Number of authors: "));
        int howManyGenres =  Integer.parseInt(getInput(br,"Number of Genres: "));

        for(int i=0; i<howManyAuthors;i++){
            String val = "Author ID : "+i+" : ";
            String input = getIDinput(br,val);
            if(!foundItem(input,"author_id","author")){System.out.println("Author does't exist"); authCheck=false;}
            book.authors.add(input);
        }
        for(int i=0; i<howManyGenres;i++){
            String val = "Genre : "+i+" : ";
            book.authors.add(getIDinput(br,val));
        }
        book.book_name = getInput(br,"Enter book name: ");
        book.percentage_to_publisher = Double.parseDouble(getInput(br,"Enter percent to publisher"));
        book.unit_price = Double.parseDouble(getInput(br,"Enter unit price: "));
        book.date_of_publish = new SimpleDateFormat("dd/MM/yyyy").parse(getInput(br, "Date of Publish : "));
        book.number_of_pages = Integer.parseInt(getInput(br,"Number of Pages: "));
        book.quantity = Integer.parseInt(getInput(br,"Quantity: "));

        if(authCheck){
            return book;
        }else{
            return null;
        }
    }


}
