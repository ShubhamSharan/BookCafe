import helperclasses.Address;
import helperclasses.BankingAccount;
import helperclasses.PhoneNumber;

import java.util.Date;

public class Publisher {
    String user_id;
    String first_name;
    String second_name;

    private String email;
    private String password;
    private Address address;
    private PhoneNumber phonenumber;
    private Date date_of_birth;
    private BankingAccount account;

    public Publisher(String userId, String fname, String sname, String eml, String pass, Address adrs, PhoneNumber pnumber, Date dob, BankingAccount acc){
        String user_id = userId;
        String first_name = fname;
        String second_name = sname;
        String email = eml;
        String password = pass;
        Address address = adrs;
        PhoneNumber phonenumber = pnumber;
        Date date_of_birth = dob;
        BankingAccount account = acc;
    }
    public Publisher(){
        String user_id = "";
        String first_name = "";
        String second_name = "";
        String email = "";
        String password = "";
        Address address = new Address();
        PhoneNumber phonenumber = new PhoneNumber();
        Date date_of_birth = null;
        BankingAccount account = new BankingAccount();
    }
}
