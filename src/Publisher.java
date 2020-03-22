import helperclasses.Address;
import helperclasses.BankingAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public BankingAccount getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    public String[] getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public Publisher(){
        first_name = "";
        middle_name = "";
        second_name = "";
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccount(BankingAccount account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhonenumber(String[] phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Publisher NewUsr(String id)  {
        System.out.println("\u001b[34m------------- Welcome to the BookCafe -------------");
        Publisher newPublisher = new Publisher(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        newPublisher.first_name = getInput(br, "First Name: ");
        System.out.print("Middle Name (Optional): ");
        try {
            newPublisher.middle_name = br.readLine();
        } catch (IOException e) {
            System.out.println("No middle name added");
            newPublisher.middle_name = "";
        }
        newPublisher.second_name = getInput(br, "Last Name : ");
        //Login Details
        newPublisher.email = getInput(br,"Email : " );
        newPublisher.password = getInput(br, "Password : ");
        //Personal identifiers
        newPublisher.address = makeAddress();
        newPublisher.phonenumber = enterPhoneNumber(br);
        newPublisher.account = makeAccount();

        return newPublisher;
    }

}
