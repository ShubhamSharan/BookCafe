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

public class User {
    String user_id;
    String first_name;
    String middle_name;
    String second_name;
    Date date_of_birth;
    HashSet<Integer> uids = new HashSet<>();
    ArrayList<ShoppingCart> shoppingcarts;

    public String iDGen(){
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
    public void addIDs(){

    }

    private String email;
    private String password;
    private Address address;
    private String[] phonenumber;
    private BankingAccount account;

    public User(String id){
        user_id = id;
        first_name = "";
        middle_name = "";
        second_name = "";
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        date_of_birth = new Date();
        account = new BankingAccount();
        shoppingcarts = new ArrayList<>();
    }
    public User(){
        user_id = "";
        first_name = "";
        middle_name = "";
        second_name = "";
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        date_of_birth = new Date();
        account = new BankingAccount();
        shoppingcarts = new ArrayList<>();

    }

    public User(String userId, String fname, String sname, String eml, String pass, Address adrs, String[] pnumber, Date dob, BankingAccount acc, ArrayList<ShoppingCart> sc){
        user_id = userId;
        first_name = fname;
        second_name = sname;
        email = eml;
        password = pass;
        address = adrs;
        phonenumber = pnumber;
        date_of_birth = dob;
        account = acc;
        shoppingcarts = sc;
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


    public static User NewUsr(String id) throws IOException, ParseException {
        System.out.println("\u001b[34m------------- Welcome to the BookCafe -------------");
        User newUser = new User(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        newUser.first_name = getInput(br, "First Name: ");
        System.out.print("Middle Name (Optional): ");
        newUser.middle_name = br.readLine();
        newUser.second_name = getInput(br, "Last Name : ");
        newUser.email = getInput(br,"Email : " );
        newUser.password = getInput(br, "Password : ");
        newUser.address = makeAddress();
        newUser.phonenumber = enterPhoneNumber(br);
        newUser.date_of_birth = new SimpleDateFormat("dd/MM/yyyy").parse(getInput(br, "Date of Birth : "));
        newUser.setAccount(makeAccount());
        return newUser;
    }

}
