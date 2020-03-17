import helperclasses.Address;
import helperclasses.BankingAccount;

import java.util.Date;

public class Publisher {
    String user_id;
    String first_name;
    String second_name;
    String email;

    private String password;
    private Address address;
    private String[] phonenumber;
    private Date date_of_birth;
    private BankingAccount account;

    public Publisher(String userId, String fname, String sname, String eml, String pass, Address adrs, String[] pnumber, Date dob, BankingAccount acc){
        String user_id = userId;
        String first_name = fname;
        String second_name = sname;
        String email = eml;
        String password = pass;
        Address address = adrs;
        String[] phonenumber = pnumber;
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
        String[] phonenumber = null;
        Date date_of_birth = null;
        BankingAccount account = new BankingAccount();
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
}
