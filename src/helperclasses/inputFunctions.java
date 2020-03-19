package helperclasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;


public class inputFunctions {
    public static String getInput(BufferedReader r, String x) throws IOException {
        System.out.print("* "+x);
        String temp = r.readLine();
        while(true){
            if((temp != null)&&(temp.replaceAll("\\s+","").length()!=0)){
                System.out.println("value:{"+temp+"}"+temp.length());
                break;
            }else{
                System.out.println("This field shouldn't be left blank");
                System.out.print(x);
                temp = r.readLine();
            }

        }
        return temp;
    }



    public static String[] enterPhoneNumber(BufferedReader r) throws IOException {
        String [] ptits ={"Primary Phone","Secondary Phone Number","Other Phone Number"};
        String [] pnum = new String[3];
        System.out.println("Enter "+ptits[0]+" : ");
        while(true){
            pnum[0] = r.readLine();
            if(pnum[0] != null){
                break;
            }else{System.out.println("This field shouldn't be left blank");}
        }
        System.out.println("Enter "+ptits[1]+" : ");
        pnum[1] = r.readLine();
        System.out.println("Enter "+ptits[2]+" : ");
        pnum[2] = r.readLine();
        return pnum;

    }

}
