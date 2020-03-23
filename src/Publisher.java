import helperclasses.Address;
import helperclasses.BankingAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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

}
