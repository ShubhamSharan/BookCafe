import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static helperclasses.inputFunctions.*;
import static helperclasses.menus.*;

//This is the ADMIN
public class BookStore {
    String book_store_name;
    // Trackers
    HashSet<String> uids;
    HashMap<Integer,String> search_types = new HashMap<>();

    //No Data iun DB
    public BookStore(String bsn){
        book_store_name = bsn;
        uids = new HashSet<>();
        this.addSearchTypes();
        addIDs(uids, "public.user", "user_id");
        addIDs(uids,"public.publisher","publisher_id");
    }

    public void addSearchTypes(){
        search_types.put(1,"isbn");
        search_types.put(2,"book_name");
        search_types.put(3,"authors");
        search_types.put(4,"genre");
    }

    public void addNewBook(){
        Book book = new Book();
        book = book.createBook();
        if(book == null){
            System.out.println("Make sure the book is created correctly!");
            return;
        }
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")
        ) {
            java.sql.Date sqlDate = new java.sql.Date(book.date_of_publish.getTime());
            String query = "insert into public.book" + "(isbn,quantity,book_name,number_of_pages,unit_price,date_of_publish)"+
                    " values ("+book.ISBN+","+book.quantity+","+book.book_name+","+book.number_of_pages+","+book.unit_price+","+sqlDate+");";

            query = query + "insert into public.request (publisher_id,isbn,percent_to_publish,request_quantity,request_approved)"+
                "values ("+book.publisher_id+","+book.ISBN+","+book.percentage_to_publisher+","+100+",TRUE)";
            PreparedStatement usr = connection.prepareStatement(query);
            usr.execute();
            String authorQuery;
            for(String author : book.authors)
            {
                authorQuery = "insert into public.wrote (author_id,isbn)" + "values ("+author+","+book.ISBN+");";
                PreparedStatement auths = connection.prepareStatement(authorQuery);
                auths.execute();
                auths.close();
            }
        } catch (Exception sqle) {
            System.out.println("Exception addNewBook: " + sqle);
        }
    }

    //Adding Books to a BookStore via SQL
    public void removeBook(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String isbn = getInput(br,"Enter ISBN :");
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            ResultSet bookDeleted = statement.executeQuery("select isbn from public.book where isbn ="+isbn);
            if(!bookDeleted.next()){
                System.out.println(isbn+" doesn't exist in the database");
            }else{
                bookDeleted.close();
                PreparedStatement rem = connection.prepareStatement("delete from public.book where isbn ="+isbn);
                rem.executeUpdate();
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    //Non Static views
    public void Admin(){
        String pass = "30055003";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String usr = getInput(br,"Enter ADMIN CODE:");
        if(!usr.equals(pass)){
            System.out.println("You are not admin!!");
            return;
        }
        System.out.println("\u001b[35mWELCOME ADMIN \uD83D\uDCDA \uD83E\uDD13");
        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            AdministratorView();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: searchBook();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 2: addNewBook(); System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 3: removeBook(); System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 4: printUsers();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 5: printShipments();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 6: showReports();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 7: flag = false; System.out.println("\uD83D\uDC4B Goodbye Admin"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }
    }



    public void ExUser(){
        ExUserView();
    }

    public void ExPublisher(){
        ExPublisherView();

    }
//2/2/2022
    public void NewUser() {
        User newuser = User.NewUsr(iDGen(this.uids));
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
        ) {
            java.sql.Date sqlDate = new java.sql.Date(newuser.getAccount().expirydetail.getTime());
            System.out.println(sqlDate);

            String query = "insert into public.user " + "(user_id,name,email,password,bank_account)"
                    +
                    " values " +
                    "( '"+ newuser.user_id+"',ROW('"+newuser.first_name+"','"+newuser.middle_name+"','"+newuser.second_name+"'),'"+newuser.getEmail()+"','"+newuser.getPassword()+"',ROW ('"+newuser.getAccount().account_name+"',"+newuser.getAccount().account_number+",'"+sqlDate+"'))";
            System.out.println(query);
            PreparedStatement usr = connection.prepareStatement(query);
            usr.execute();
        } catch (Exception sqle) {
            System.out.println("Exception SQL: " + sqle);
        }
    }

    public void NewPublisher(){
        Publisher newpub = Publisher.NewUsr(iDGen(this.uids));
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
        ) {
            java.sql.Date sqlDate = new java.sql.Date(newpub.getAccount().expirydetail.getTime());
            String query = "insert into public.publisher " + "(publisher_id,name,email,password,address,bank_account)"
                    +
                    " values " +
                    "( '"+ newpub.publisher_id+"',ROW('"+newpub.first_name+"','"+newpub.middle_name+"','"+newpub.second_name+"'),'"+newpub.getEmail()+"','"+newpub.getPassword()+"', ROW('"+newpub.getAddress().address_name+"','"+newpub.getAddress().city+"','"+newpub.getAddress().state+"','"+newpub.getAddress().zip+ "') ,ROW ('"+newpub.getAccount().account_name+"',"+newpub.getAccount().account_number+",'"+sqlDate+"'))";
            System.out.println(query);
            PreparedStatement pubsr = connection.prepareStatement(query);
            pubsr.execute();
        } catch (Exception sqle) {
            System.out.println("Exception Pub SQL: " + sqle);
        }
    }
    //Functions
    public void printUsers(){

    }

    public void printShipments(){

    }

    public void showReports(){

    }

    public void search(int option){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
        ) {

        } catch (Exception sqle) {
            System.out.println("Exception search: " + sqle);
        }
    }

    //SearchTool
    public void searchBook(){
        System.out.println("\u001b[34m------------- Search a Book By -------------");
        System.out.println("\uD83D\uDCD6 ISBN - press 1");
        System.out.println("\uD83D\uDCD6 Book Name - press 2");
        System.out.println("\uD83D\uDCD6 Author name - press 3"); //different table
        System.out.println("\uD83D\uDCD6 Genre - press 4"); //different table
        System.out.println("\uD83D\uDCD6 Exit - press 5");

        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        int option;
        while (flag){
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            option = usrin.nextInt();
            System.out.println("You have entered " + option);
            if(option==5){System.out.println("Hope you found what you were looking for!");break;}
            if(search_types.get(option)!=null){
                search(option);
            }else{System.out.println("You have selected an invalid option!");}
        }
    }
}
