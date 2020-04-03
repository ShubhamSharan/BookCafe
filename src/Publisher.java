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

}
