package helperclasses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static helperclasses.inputFunctions.getInput;

public class BankingAccount {
    public String account_name;
    public long account_number;
    public Date expirydetail;

    public BankingAccount(){
        account_name = "";
        account_number = 0;
        expirydetail = null;
    }

    public String getAccount_name() {
        return account_name;
    }

    public long getAccount_number() {
        return account_number;
    }

    public Date getExpirydetail() {
        return expirydetail;
    }

    public boolean accverifier(long account_number){
        if(String.valueOf(account_number).length() == 16){
            return true;
        }
        return false;
    }

    public boolean dateverifier(Date exp){
        Date today = new Date();
        if(exp.after(today)){
            return true;
        }
        return false;
    }

    public static BankingAccount makeAccount() {
        BankingAccount acc = new BankingAccount();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        acc.account_name = getInput(br,"Account Name: ");
        long accnum = Long.parseLong(getInput(br,"Account Number: "));
        Date exp = null;
        try {
            exp = new SimpleDateFormat("d-M-yyyy").parse(getInput(br,"Expiry Date: "));
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        while(true){
            if(acc.accverifier(accnum)){acc.account_number = accnum; break;}
            else{
                System.out.println("Incorrect pin");
                accnum = Long.parseLong(getInput(br,"Account Number: "));
            }
        }
        while(true){
            if(acc.dateverifier(exp)){acc.expirydetail = exp; break;}
            else{
                System.out.println("Incorrect date!!");
                try {
                    exp = new SimpleDateFormat("dd-MM-yyyy").parse(getInput(br,"Expiry Date: "));
                } catch (ParseException e) {
                    System.out.println("Invalid date format");
                }
            }
        }
        return acc;
    }
}

