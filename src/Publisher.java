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
    Date date_of_birth;


    private String email;
    private String password;
    private Address address;
    private String[] phonenumber;
    private BankingAccount account;

    public Publisher(String id){
        String publisher_id = id;
        String first_name = "";
        String middle_name = "";
        String second_name = "";
        String email = "";
        String password = "";
        Address address = null;
        String[] phonenumber = null;
        Date date_of_birth = new Date();
        BankingAccount account = new BankingAccount();
    }

    public Publisher(){
        String publisher_id = "";
        String first_name = "";
        String middle_name = "";
        String second_name = "";
        String email = "";
        String password = "";
        Address address = null;
        String[] phonenumber = null;
        Date date_of_birth = new Date();
        BankingAccount account = new BankingAccount();
    }
    public Publisher( String pid,String fname, String sname, String eml, String pass, Address adrs, String[] pnumber, Date dob, BankingAccount acc){
        String publisher_id = pid;
        String first_name = fname;
        String second_name = sname;
        String email = eml;
        String password = pass;
        Address address = adrs;
        String[] phonenumber = pnumber;
        Date date_of_birth = dob;
        BankingAccount account = acc;
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

    public static Publisher NewUsr(String id) throws IOException, ParseException {
        System.out.println("\u001b[34m------------- Welcome to the BookCafe -------------");
        Publisher newPublisher = new Publisher(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        newPublisher.first_name = getInput(br, "First Name: ");
        System.out.print("Middle Name (Optional): ");
        newPublisher.middle_name = br.readLine();
        newPublisher.second_name = getInput(br, "Last Name : ");
        //Login Details
        newPublisher.email = getInput(br,"Email : " );
        newPublisher.password = getInput(br, "Password : ");
        //Personal identifiers
        newPublisher.address = makeAddress();
        newPublisher.phonenumber = enterPhoneNumber(br);
        newPublisher.date_of_birth = new SimpleDateFormat("dd/MM/yyyy").parse(getInput(br, "Date of Birth : "));
        newPublisher.account = makeAccount();

        return newPublisher;
    }

}
