import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import static helperclasses.inputFunctions.addIDs;

public class BookStore implements StoreInterface {
    String book_store_name;
    // Trackers
    HashSet<String> uids;
    HashMap<Integer,String> search_types = new HashMap<>();

    //No Data iun DB
    public BookStore(String bsn){
        book_store_name = bsn;
        uids = new HashSet<>();
        this.addSearchTypes();
        uids = addIDs(uids,"user","user_id");
        uids = addIDs(uids,"publisher","publisher_id");


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
            if(!uids.contains(String.valueOf(selected))){
                uids.add(String.valueOf(selected));
                return String.valueOf(selected);
            }
        }
    }


    @Override
    public void addNewBook(Book book) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe","shubhamsharan09","yvan2002");
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
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe");
                Statement statement = connection.createStatement();
        ) {
            ResultSet aSet = statement.executeQuery("select * from author");
            while(aSet.next()){

            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }

    //SearchTool
    public void searchBook(){
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
    }
}
