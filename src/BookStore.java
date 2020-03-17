import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class BookStore implements StoreInterface {
    String book_store_name;
    HashMap<String,Book> books;
    HashMap<String,Publisher> publishers;
    HashMap<String,Author> authors;
    HashMap<Integer,String> search_types;
    Scanner usrinput = new Scanner(System.in);
    boolean dbempty;

    //No Data iun DB
    public BookStore(String bsn, boolean db){
        book_store_name = bsn;
        books = null;
        publishers = null;
        authors = null;
        dbempty = true;
        addSearchTypes();
    }

    //Data in DB
    public BookStore(String bsn){
        book_store_name = bsn;
        books = addBooks();
        publishers = addPubslishers();
        authors = addAuthors();
        dbempty = false;
        addSearchTypes();
    }

    public void addSearchTypes(){
        this.search_types.put(1,"ISBN");
        this.search_types.put(2,"book_name");
        this.search_types.put(3,"authors");
        this.search_types.put(4,"genre");
    }

    public HashMap<String, Book> addBooks(){
        HashMap<String, Book> bookstore = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe","shubhamsharan09","");
                Statement statement = connection.createStatement();
        ) {
            //Checks if Publisher exist already
            ResultSet resultSet = statement.executeQuery("select * from bookstore");
            while(resultSet.next()){
                Book book = new Book();
                book.ISBN = resultSet.getString("isbn");
                book.quantity = resultSet.getInt("quantity");
                book.book_name = resultSet.getString("book_name");
                //book.authors = resultSet.getString("author_id");
                //book.genre = resultSet.getString("genre");
                book.publisher_id = resultSet.getString("publisher_id");
                book.number_of_pages = resultSet.getInt("number_of_pages");
                book.unit_price = resultSet.getDouble("unit_price");
                book.percentage_to_publisher = resultSet.getDouble("percent_to_publisher");
                book.date_of_publish = resultSet.getDate("date_of_publish");

                bookstore.put(book.ISBN, book);
            }

            //Checks if all authors exist in the database
            //Adds Book to database



        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
        return null;
    }

    @Override
    public void addNewBook(Book book) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe","shubhamsharan09","");
                Statement statement = connection.createStatement();
        ) {
            //Checks if Publisher exist already
            ResultSet pubSet = statement.executeQuery("select publisher_id from publisher where publisher_id = '"+book.publisher_id+"'");

            //Checks if all authors exist in the database
            ResultSet authSet = statement.executeQuery("");
            //Adds Book to database
            ResultSet InsertSet = statement.executeQuery("insert into bookstore values ("+book.ISBN+","+book.quantity+","+book.book_name+","+book.publisher_id+","+book.number_of_pages+","+book.unit_price+","+book.percentage_to_publisher+")");



        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }

    }

    @Override
    public HashMap<String, Author> addAuthors() {
        HashMap<String, Author> authStore = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe");
                Statement statement = connection.createStatement();
        ) {
            ResultSet aSet = statement.executeQuery("select * from author");
            while(aSet.next()){
                Author author = new Author();
                author.author_id = aSet.getString("author_id");
                //Custom name type
                //author.first_name = aSet.getString();
                //author.second_name = aSet.getString();
            }


        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
        return authStore;
    }

    @Override
    public HashMap<String, Publisher> addPubslishers() {
        HashMap<String, Publisher> pubSet = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe");
                Statement statement = connection.createStatement();
        ) {
            ResultSet aSet = statement.executeQuery("select * from author");
            while(aSet.next()){
                Publisher publisher = new Publisher();
                publisher.user_id = aSet.getString("author_id");
                //Custom name type
                //author.first_name = aSet.getString();
                //author.second_name = aSet.getString();
            }


        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }

        return pubSet;
    }

    //Adding Books to a BookStore via SQL

    @Override
    public void removeBook(String isbn) {

    }

    //Book Printing
    public void printBookDetails(Book book){
        System.out.println("Book name       : "+ book.book_name);
        System.out.println("ISBN            : "+ book.ISBN);
        System.out.println("Authors         : "+ book.printAuthors());
        System.out.println("Genres          : "+ book.printGenres());
        System.out.println("Price           : "+ book.unit_price);
        System.out.println("Number of Pages : "+ book.number_of_pages);
        System.out.println("Date of publish : "+ book.date_of_publish);
    }
    //
    public void addToCart(){
        System.out.println("Enter 10 digit ISBN number to add to Cart: ");
        String isbn = usrinput.nextLine();
        if(isbn.length()!=10){
            System.out.println("Invalid ISBN number");
        }else{

        }

    }

    public void search(int option){

    }

    //SearchTool
    public void searchTool(){
        Book selectedBook = null;
        System.out.println("\u001b[34m------------- Search a Book By -------------");
        System.out.println("\uD83D\uDCD6 ISBN - press 1");
        System.out.println("\uD83D\uDCD6 Book Name - press 2");
        System.out.println("\uD83D\uDCD6 Author name - press 3");
        System.out.println("\uD83D\uDCD6 Genre - press 4");
        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            if(search_types.get(option)!=null){
                search(option);
            }else{System.out.println("You have selected an invalid option!");}

        }
    }




}
