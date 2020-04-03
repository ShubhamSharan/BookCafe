import helperclasses.Address;
import helperclasses.BankingAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public User(String userId, String fname, String sname, String eml, String pass, Address adrs, String[] pnumber, BankingAccount acc){
        user_id = userId;
        first_name = fname;
        second_name = sname;
        email = eml;
        password = pass;
        address = adrs;
        phonenumber = pnumber;
        account = acc;
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

    public static void checkProfile(){

    }


}
