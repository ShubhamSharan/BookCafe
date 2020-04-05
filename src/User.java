import helperclasses.Address;
import helperclasses.BankingAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static helperclasses.Address.makeAddress;
import static helperclasses.BankingAccount.makeAccount;
import static helperclasses.inputFunctions.*;

public class User {
    String user_id;
    String first_name;
    String middle_name;
    String second_name;

    private String email;
    private String password;
    private Address address;
    private String[] phonenumber;
    private BankingAccount account;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public String[] getPhonenumber() {
        return phonenumber;
    }

    public BankingAccount getAccount() {
        return account;
    }

    public User(String id){
        user_id = id;
        first_name = "";
        middle_name = "";
        second_name = "";
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
    }

    public User(String userId, String fname, String sname){
        user_id = userId;
        first_name = fname;
        second_name = sname;
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
    }

    public static User NewUsr(String id) {
        System.out.println("\u001b[34m------------- Welcome New User -------------");
        User newUser = new User(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        newUser.first_name = getInput(br, "First Name: ");
        System.out.print("Middle Name (Optional): ");
        try {
            newUser.middle_name = br.readLine();
            if(newUser.middle_name.replaceAll("\\s+","").length() == 0){
                System.out.println("No middle name added");
            }
        } catch (IOException e) {
            newUser.middle_name = "";
        }
        newUser.second_name = getInput(br, "Last Name : ");
        newUser.email = getInput(br,"Email : " );
        newUser.password = getInput(br, "Password : ");
        newUser.address = makeAddress();
        newUser.phonenumber = enterPhoneNumber(br);
        newUser.account = makeAccount();
        return newUser;
    }

    public static void printUsers(){

    }

    public static User checkUser(String type){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String email = getInput(br,"Email : ");
        String password = getInput(br,"Password : ");
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select user_id,user_type,(name).first_name as first_name,(name).last_name as last_name from public.user where public.user.email = '"+email+"' and public.user.password = '"+password+"'";
//            System.out.println(query);
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("This user doesn't exist");
            }
            else if(type == "Admin"){
                String id = usr.getString("user_id");
                String fname = usr.getString("first_name");
                String lname = usr.getString("last_name");
                Boolean iden = Boolean.getBoolean(usr.getString("user_type"));
//                System.out.println(iden);
                if(!iden){ return new User(id,fname,lname); }
                else{ return null;}
            }
            else if(type == "User"){
                String id = usr.getString("user_id");
                String fname = usr.getString("first_name");
                String lname = usr.getString("last_name");
                return new User(id,fname,lname);
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
        return null;
    }

    public static void checkProfile(){

    }


}
