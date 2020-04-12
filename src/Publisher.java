import helperclasses.Address;
import helperclasses.BankingAccount;
import helperclasses.Name;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


import static helperclasses.Address.makeAddress;
import static helperclasses.BankingAccount.makeAccount;
import static helperclasses.inputFunctions.*;

public class Publisher {
    String publisher_id;
    String first_name;
    String middle_name;
    String second_name;


    private String email;
    private String password;
    private Address address;
    private String[] phonenumber;
    private BankingAccount account;

    public Publisher(String id){
        publisher_id = id;
        first_name = "";
        middle_name = "";
        second_name = "";
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
    }

    public Publisher(String id, String fn, String sn){
        publisher_id = id;
        first_name = fn;
        middle_name = "";
        second_name = sn;
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
    }

    public static void viewAll() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select * from PubDets('')";
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("No publishers exist");
                return;
            }
            System.out.println("================================================== P U B L I S H E R S ===================================================");
            do{
                System.out.println("============================================================================================================");
                System.out.println("\uD83D\uDCF8Publisher ID      : "+usr.getString("publisher_id"));
                System.out.println("\uD83D\uDCF8First Name        : "+usr.getString("first_name"));
                System.out.println("\uD83D\uDCF8Middle Name       : "+usr.getString("middle_name"));
                System.out.println("\uD83D\uDCF8Last Name         : "+usr.getString("last_name"));
                System.out.println("\uD83D\uDCF8Email             : "+usr.getString("email"));
                System.out.println("\uD83D\uDCF8Address           : "+usr.getString("address"));
                System.out.println("============================================================================================================\n\n");
            }while(usr.next());
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public BankingAccount getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public static Publisher NewUsr(String id)  {
        System.out.println("\u001b[34m------------- Welcome New Publisher -------------");
        Publisher newPublisher = new Publisher(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        newPublisher.first_name = getInput(br, "First Name: ");
        System.out.print("Middle Name (Optional): ");
        try {
            newPublisher.middle_name = br.readLine();
            if(newPublisher.middle_name.replaceAll("\\s+","").length() == 0){
                System.out.println("No middle name added");
            }
        } catch (IOException e) {
            System.out.println("No middle name added");
            newPublisher.middle_name = "";
        }
        newPublisher.second_name = getInput(br, "Last Name : ");
        newPublisher.email = getInput(br,"Email : " );
        newPublisher.password = getInput(br, "Password : ");
        newPublisher.address = makeAddress();
        newPublisher.phonenumber = enterPhoneNumber(br);
        newPublisher.account = makeAccount();
        return newPublisher;
    }

    public static Publisher checkPub(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String email = getInput(br,"Email : ");
        String password = getInput(br,"Password : ");
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select publisher_id,(name).first_name as first_name,(name).last_name as last_name from public.publisher where publisher.email = '"+email+"' and publisher.password = '"+password+"'";
//            System.out.println(query);
            ResultSet pub = statement.executeQuery(query);
            if(!pub.next()){
                System.out.println("This publisher doesn't exist");
            }
            else{
                String pub_id = pub.getString("publisher_id");
                String fname = pub.getString("first_name");
                String lname = pub.getString("last_name");
                return new Publisher(pub_id,fname,lname);
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
        return null;
    }

    public void checkSales(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String refquery = "REFRESH MATERIALIZED VIEW SalesVsExpAllMonth";
            PreparedStatement qry = connection.prepareStatement(refquery);
            qry.execute();
            qry.close();
            String query ="select * from SalesVsExpAllMonth where publisher_id = '"+this.publisher_id+"'";
            ResultSet crt = statement.executeQuery(query);
            if(!crt.next()){
                System.out.println("No Reports Available");
                return;
            }
            System.out.println("============================================= Y E A R L Y   S A L E S =============================================");
            float amt = 0;
            do{
                System.out.println("Date                      : "+crt.getString("date"));
                System.out.println("ISBN                      : "+crt.getString("isbn"));
                System.out.println("Percent to Publisher      : "+crt.getString("percent_to_publisher"));
                System.out.println("Book name                 : "+crt.getString("book_name"));
                System.out.println("Copies Sold               : "+crt.getString("copies_sold"));
                System.out.println("Sales                     : "+crt.getString("sales"));
                System.out.println("Publishers Cut            : "+crt.getString("publisherscut"));
                System.out.println("Profit                     : "+crt.getString("profit"));
                System.out.println("-------------------------------------------------------------------------------------------------------------\n");
                amt = amt + crt.getFloat("publisherscut");
            }while(crt.next());
            System.out.println("This Month's Transfer = $: "+amt);
            crt.close();
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public void checkProfile(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select * from PubDets('"+this.publisher_id+"')";
            ResultSet pb = statement.executeQuery(query);
            if(!pb.next()){
                System.out.println("Unknown error");
                return;
            }
            System.out.println("========================================= Y O U R == P R O F I L E =========================================");
            do{
                System.out.println("============================================================================================================");
                System.out.println("User ID           : "+pb.getString("publisher_id"));
                System.out.println("First Name        : "+pb.getString("first_name"));
                System.out.println("Middle Name       : "+pb.getString("middle_name"));
                System.out.println("Last Name         : "+pb.getString("last_name"));
                System.out.println("Email             : "+pb.getString("email"));
                System.out.println("Address           : "+pb.getString("address"));
                System.out.println("============================================================================================================\n");
            }while(pb.next());
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public void checkRequests() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select * from public.book where public.book.publisher_id ='"+this.publisher_id+"' and public.book.request_approved = FALSE";
            ResultSet req = statement.executeQuery(query);
            if(!req.next()){
                System.out.println("The Publisher has no refill requests.");
            }else{
            do{
                BookStore.search(1,req.getString("isbn"));
                System.out.println("ORDER REQUESTS :" + req.getString("requested_quantity"));
                int validate = Integer.parseInt(getInput(br,"1 <- Approve | Any other number <- NotApprove : "));
                if(validate==1){
                    updateBook(req.getString("isbn"),true);
                }else{
                    updateBook(req.getString("isbn"),false);
                }
            }while (req.next());
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    private void updateBook(String isbn, Boolean check) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","")
        ) {
            String query;
            if(check){
                query = "update public.book set request_approved = TRUE, quantity = (quantity + requested_quantity), last_request_date = Now() where public.book.isbn = '"+isbn+"' ";
            }
            else{
                query = "update public.book set last_request_date = Now() where isbn = '"+isbn+"' ";
            }
            PreparedStatement purchase = connection.prepareStatement(query);
            purchase.execute();
            purchase.close();
        } catch (Exception sqle) {
            System.out.println(" Exception updatebook: " + sqle);
        }
    }
}
