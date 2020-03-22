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
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            ResultSet InsertSet = statement.executeQuery("insert into public.bookstore values ("+book.ISBN+","+book.quantity+","+book.book_name+","+book.publisher_id+","+book.number_of_pages+","+book.unit_price+","+book.percentage_to_publisher+")");
            statement.close();connection.close();
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
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
            ResultSet bookDeleted = statement.executeQuery("select isbn from public.bookstore where isbn ="+isbn);
            if(!bookDeleted.next()){
                System.out.println(isbn+" doesn't exist in the database");
                return;
            }else{
                bookDeleted.close();
                PreparedStatement rem = connection.prepareStatement("delete from public.bookstore where isbn ="+isbn);
                rem.executeUpdate();
            }
            statement.close();connection.close();
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
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
        User newuser = new User();
        newuser = newuser.NewUsr(iDGen(this.uids));
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            java.sql.Date sqlDate = new java.sql.Date(newuser.getAccount().expirydetail.getTime());
            System.out.println(sqlDate);

            System.out.println("Welcome "+newuser.first_name+" "+newuser.second_name+newuser.getPhonenumber()[0]);
            String query = "insert into public.user " + "(user_id,name,email,password,address,phone_number,bank_account)"
                    +
                    " values " +
                    "( '"+ newuser.user_id+"',ROW('"+newuser.first_name+"','"+newuser.middle_name+"','"+newuser.second_name+"'),'"+newuser.getEmail()+"','"+newuser.getPassword()+"', ROW('"+newuser.getAddress().address_name+"','"+newuser.getAddress().city+"','"+newuser.getAddress().state+"','"+newuser.getAddress().zip+ "'), array[' "+newuser.getPhonenumber()[0]+"','"+newuser.getPhonenumber()[1]+"','"+newuser.getPhonenumber()[2]+"'] ,ROW ('"+newuser.getAccount().account_name+"',"+newuser.getAccount().account_number+",'"+sqlDate+"'))";
            System.out.println(query);
            PreparedStatement usr = connection.prepareStatement(query);
            usr.execute();

        } catch (Exception sqle) {
            System.out.println("Exception SQL: " + sqle);
        }


    }


    public void NewPublisher(){
        Publisher newpub = new Publisher();
        newpub.NewUsr(iDGen(this.uids));
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            PreparedStatement usr = connection.prepareStatement("insert into public.user (isbn,(name).first_name,(name).middle_name,(name).last_name,email,password,(address).address_name,(address).city,(address).state,(address).zip,phone_number,(bank_account).account_name,(bank_account).account_number,(bank_account).expirydate)" +
                    " values ("+newpub.publisher_id+"','"+newpub.first_name+"','"+newpub.middle_name+"','"+newpub.second_name+"','"+newpub.getEmail()+"','"+newpub.getPassword()+"','"+newpub.getAddress().address_name+"','"+newpub.getAddress().city+"','"+newpub.getAddress().state+"','"+newpub.getAddress().zip+"','"+newpub.getAccount().account_name+"','"+newpub.getAccount().account_number+"','"+newpub.getAccount().expirydetail+"')");
            usr.execute();

        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
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
                Statement statement = connection.createStatement()
        ) {
            ResultSet aSet = statement.executeQuery("select * from author");
            while(aSet.next()){

            }
            statement.close();connection.close();
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
