package helperclasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;


public class inputFunctions {
    public static String getInput(BufferedReader r, String x) {
        System.out.print("* "+x);
        String temp = null;
        try {
            temp = r.readLine();
        } catch (IOException e) {
            System.out.println("Can't be left blank!!");
        }
        while(true){
            if((temp != null)&&(temp.replaceAll("\\s+","").length()!=0)){
                System.out.println("value:{"+temp+"}"+temp.length());
                break;
            }else{
                System.out.println("This field shouldn't be left blank");
                System.out.print(x);
                try {
                    temp = r.readLine();
                } catch (IOException e) {
                    System.out.println("Can't be left blank!!");
                }
            }

        }
        return temp;
    }

    public static String getIDinput(BufferedReader r, String x) {
        System.out.print("* "+x);
        String temp = null;
        try {
            temp = r.readLine();
        } catch (IOException e) {
            System.out.println("Can't be left blank!!");
        }
        while(true){
            if((temp != null)&&(temp.replaceAll("\\s+","").length()==10)){
                System.out.println("value:{"+temp+"}"+temp.length());
                break;
            }else{
                System.out.println("Your "+x+"Shouldn't be less than 10 charectors!");
                System.out.print(x);
                try {
                    temp = r.readLine();
                } catch (IOException e) {
                    System.out.println("Can't be left blank!!");
                }
            }
        }
        return temp;
    }

    public static HashSet<String> addIDs(HashSet<String> uids, String tablename, String columnname){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","");
                Statement statement = connection.createStatement()
        ) {
            ResultSet aSet = statement.executeQuery("select "+columnname+" from "+tablename);
            while(aSet.next()){
                uids.add(aSet.getString(columnname));
            }
        } catch (Exception sqle) {
            System.out.print("Tablename: "+tablename+" columnname: "+columnname);
            System.out.println(" Exception addIDs: " + sqle);
        }
        return uids;
    }

    public static Name getName(){
        Name name = new Name();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        name.first_name = getInput(br,"Enter First Name: ");
        try {
            System.out.println("Middle name : ");
            name.middle_name = br.readLine();
            if(name.middle_name.length() == 0){
                System.out.println("No middle name added");
            }
        } catch (IOException e) {
            name.middle_name = "";
        }
        name.last_name = getInput(br,"Enter Last Name:");
        return name;
    }

    public static String iDGen(HashSet<String> uids){
        int selected;
        while(true){
            Random rand = new Random();
            selected = 1000000000+ rand.nextInt(100000000);
            if(!uids.contains(String.valueOf(selected))){
                uids.add(String.valueOf(selected));
                return String.valueOf(selected);
            }
        }
    }



    public static String[] enterPhoneNumber(BufferedReader r){
        String [] ptits ={"Primary Phone","Secondary Phone Number","Other Phone Number"};
        String [] pnum = new String[3];
        System.out.println("* Enter "+ptits[0]+" : ");
        while(true){
            try {
                pnum[0] = r.readLine();
                if(pnum[0].replaceAll("\\s+","").length() == 10){
                    break;
                }else{
                    System.out.println("Should be 10 charectors long!");
                    System.out.println("* Enter "+ptits[0]+" : ");
                }
            } catch (IOException e) {
                System.out.println("Invalid Phone Number");
            }
        }
        System.out.println("Enter "+ptits[1]+" : ");
        try {
            pnum[1] = r.readLine();
            if(pnum[1].replaceAll("\\s+","").length() == 0){
                pnum[1] = "";
                System.out.println("No "+ptits[2]+ " added");
            }
        } catch (IOException e) {
            System.out.println("No "+ptits[1]+ " added");
        }

        System.out.println("Enter "+ptits[2]+" : ");
        try {
            pnum[2] = r.readLine();
            if(pnum[2].replaceAll("\\s+","").length() == 0){
                pnum[2] = "";
                System.out.println("No "+ptits[2]+ " added");
            }
        } catch (IOException e) {
            System.out.println("No "+ptits[2]+ " added");
        }
        System.out.println("Phone Numbers : "+Arrays.toString(pnum));
        return pnum;

    }

    public static boolean foundItem(String id, String columnname, String tablename){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            ResultSet set = statement.executeQuery("select "+columnname+" from public."+tablename+" where "+columnname+" = '"+id+"'");
            if (!set.next()) {
                System.out.println("No such"+id+"exisits in "+tablename);
                statement.close();connection.close();
                return false;
            }else{
                statement.close();connection.close();
                return true;
            }
        } catch (Exception sqle) {
            System.out.println("Exception foundItem: " + sqle);
            return false;
        }
    }

}
