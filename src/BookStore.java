import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class BookStore implements StoreInterface {
    String book_store_name;
    // Trackers
    HashSet<Integer> uids = new HashSet<>();
    HashMap<String,Book> books = new HashMap<>();
    HashMap<String,Publisher> publishers= new HashMap<>();
    HashMap<String,Author> authors= new HashMap<>();
    HashMap<String, Shipment> shipments= new HashMap<>();

    HashMap<Integer,String> search_types = new HashMap<>();
    boolean dbempty;

    //No Data iun DB
    public BookStore(String bsn, boolean db){
        book_store_name = bsn;
        books = new HashMap<>();
        publishers = new HashMap<>();
        authors = new HashMap<>();
        shipments = new HashMap<>();
        dbempty = true;
        this.addSearchTypes();
    }

    //Data in DB
    public BookStore(String bsn){
        book_store_name = bsn;
        addBooks();
        addPubslishers();
        addAuthors();
        dbempty = false;
        addSearchTypes();
    }

    public void addSearchTypes(){
        search_types.put(1,"ISBN");
        search_types.put(2,"book_name");
        search_types.put(3,"authors");
        search_types.put(4,"genre");
    }

    public String iDGen(){
        int selected;
        while(true){
            Random rand = new Random();
            selected = 1000000000+ rand.nextInt(100000000);
            if(!uids.contains(selected)){
                uids.add(selected);
                return String.valueOf(selected);
            }
        }
    }

    public void addBooks(){
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

                //In this Instance
                this.books.put(book.ISBN, book);
            }

            //Checks if all authors exist in the database
            //Adds Book to database



        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
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
    public void addAuthors() {
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
    }

    @Override
    public void addShipments() {

    }

    @Override
    public void addUsers() {

    }

    @Override
    public void addPubslishers() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe");
                Statement statement = connection.createStatement();
        ) {
            ResultSet aSet = statement.executeQuery("select * from author");
            while(aSet.next()){
                Publisher publisher = new Publisher();
                publisher.publisher_id = aSet.getString("author_id");
                //TODO: Custom name type
                publisher.first_name = aSet.getString("(name).first_name");
                publisher.middle_name = aSet.getString("(name).middle_name");
                publisher.second_name = aSet.getString("(name).second_name");

                publisher.setEmail(aSet.getString("email"));
                publisher.setPassword(aSet.getString("password"));
                publisher.setPhonenumber((String[]) aSet.getArray("phone_number").getArray());
            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }

    }

    //Adding Books to a BookStore via SQL

    @Override
    public void removeBook(String isbn) {

    }
    //Non static methods
    public void ExUserView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book press 1 ");
        System.out.println("\uD83D\uDCD6 Check your orders 2");
        System.out.println("\uD83D\uDCD6 Check profile details 3");
    }

    public void NewUserView() {
        try{
            User.NewUsr(this.iDGen());
        }catch(IOException | ParseException ex){
            System.out.println("ERROR Creating New User");
        }
    }

    public void AdministratorView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book press 1");
        System.out.println("\uD83D\uDCD6 Add a book press 2");
        System.out.println("\uD83D\uDCD6 Delete a book press 3");
        System.out.println("\uD83D\uDCD6 Search for a book press 4");
    }
    public void ExPublisherView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search your books press 1 ");
        System.out.println("\uD83D\uDCD6 Check Sales Account Status press 2");
        System.out.println("\uD83D\uDCD6 Check profile details 3");
    }

    public void NewPublisherView(){
        try{
            Publisher.NewUsr(this.iDGen());
        }catch(IOException | ParseException ex){
            System.out.println("ERROR Creating New Publisher");
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
        int option;
        while (flag){
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            option = usrin.nextInt();
            System.out.println("You have entered " + option);
            if(search_types.get(option)!=null){
                search(option);
            }else{System.out.println("You have selected an invalid option!");}
        }


        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe");
                Statement statement = connection.createStatement();
        ) {
            ResultSet aSet = statement.executeQuery("select * from author where ");
            while(aSet.next()){

            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }




}
