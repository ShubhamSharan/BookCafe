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

    public void searchMyBooks(){

    }
    public void checkSales(){


    }
    public void viewMyBooks(){

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
            while (req.next()){
                BookStore.search(1,req.getString("isbn"));
                int validate = Integer.parseInt(getInput(br,"1 <- Approve | Any other number <- NotApprove"));
                if(validate==1){
                    updateBook(req.getString("isbn"),true);
                }else{
                    updateBook(req.getString("isbn"),false);
                }
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
                query = "update public.book set public.book.request_approved = TRUE, public.book.last_request_date = Now()";
            }
            else{
                query = "update public.book set public.book.last_request_date = Now()";
            }
            PreparedStatement purchase = connection.prepareStatement(query);
            purchase.execute();
            purchase.close();
        } catch (Exception sqle) {
            System.out.println(" Exception updatebook: " + sqle);
        }

    }
}
