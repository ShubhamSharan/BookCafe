import helperclasses.Address;
import helperclasses.BankingAccount;
import helperclasses.PhoneNumber;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class User {
    String user_id;
    String first_name;
    String second_name;
    Date date_of_birth;
    HashSet<Integer> uids = new HashSet<>();


    private String password;
    private Address address;
    private PhoneNumber phonenumber;
    private BankingAccount account;

    public User(){
        String user_id = "0";
        String first_name = "";
        String second_name = "";
        String password = "";
        Address address = new Address();
        PhoneNumber phonenumber = new PhoneNumber();
        Date date_of_birth = new Date();
        BankingAccount account = new BankingAccount();
    }

    public User(String userId, String fname, String sname, String pass, Address adrs, PhoneNumber pnumber, Date dob, BankingAccount acc){
        String user_id = userId;
        String first_name = fname;
        String second_name = sname;
        String password = pass;
        Address address = adrs;
        PhoneNumber phonenumber = pnumber;
        Date date_of_birth = dob;
        BankingAccount account = acc;
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

    public void setPhonenumber(PhoneNumber phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String UserIDGen(){
        int selected;
        while(true){
            Random rand = new Random();
            selected = 1000000000+ rand.nextInt(100000000);
            if(!uids.contains(selected)){
                uids.add(selected);
                return String.valueOf(selected);
            }
        }
    }

    public User addUser(){
        User newuser = null;


        return newuser;
    }

}
