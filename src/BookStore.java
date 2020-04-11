import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static helperclasses.inputFunctions.*;
import static helperclasses.menus.*;

//This is the ADMIN
public class BookStore {
    String book_store_name;
    // Trackers
    HashSet<String> uids; //For publisher and users
    public static HashMap<Integer,String> search_titles = new HashMap<>();
    public static HashMap<Integer,String> search_types = new HashMap<>();


    //No Data iun DB
    public BookStore(String bsn){
        book_store_name = bsn;
        uids = new HashSet<>();
        this.addSearchTypes();
        this.addSearchTitles();
        addIDs(uids, "public.user", "user_id");
        addIDs(uids,"public.publisher","publisher_id");
        prepareAdmin();
    }

    public void addSearchTypes(){
        search_types.put(1,"book.isbn");
        search_types.put(2,"book.book_name");
        search_types.put(3,"author.author_name");
        search_types.put(4,"genre.genre");
        search_types.put(5,"publisher.publisher_id");
    }
    public void addSearchTitles(){
        search_titles.put(1," Book ISBN ");
        search_titles.put(2," Book name ");
        search_titles.put(3," Author Name ");
        search_titles.put(4," Genre ");
        search_titles.put(5," Publisher ID ");
    }


    //Non Static views
    public void Admin(){
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        User exusr = null;
        while (exusr==null){
            exusr = User.checkUser("Admin");
            if(exusr==null){
                int exit = Integer.parseInt(getInput(br,"Click 1 if you wanna exit and 0 if you wanna retry :"));
                if(exit==1){return;}}
        }
        System.out.println("\u001b[35mWELCOME ADMIN \uD83D\uDCDA \uD83E\uDD13");
        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            AdministratorView();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: searchBook();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 2: Book.addNewBook(); System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 3: Book.removeBook(); System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 4: User.printUsers();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 5: ShoppingCart.printShipments();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 6: showReports();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 7: flag = false; System.out.println("\uD83D\uDC4B Goodbye Admin"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }
    }



    public void ExUser(){
        Scanner usrin = new Scanner(System.in);
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        User exusr = null;
        while (exusr==null){
            exusr = User.checkUser("User");
            if(exusr==null){
                int exit = Integer.parseInt(getInput(br,"Click 1 if you wanna exit and 0 if you wanna retry :"));
                if(exit==1){return;}}
        }
        boolean flag = true;
        while (flag){
            ExUserView();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: searchBook();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 2: exusr.checkShipments();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 3: exusr.checkProfile();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 4: flag = false; System.out.println("\uD83D\uDC4B Goodbye Admin"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }
    }

    public void ExPublisher(){
        Scanner usrin = new Scanner(System.in);
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        Publisher expub = null;
        while (expub==null){
            expub = Publisher.checkPub();
            if(expub==null){
                int exit = Integer.parseInt(getInput(br,"Click 1 if you wanna exit and 0 if you wanna retry :"));
                if(exit==1){return;}}
        }
        System.out.println("\n\n\uD83C\uDF4E Publisher_id :"+ expub.publisher_id );
        System.out.println("\uD83C\uDF4E Welcome back:"+ expub.first_name + " " + expub.second_name);

        boolean flag = true;
        while (flag){
            ExPublisherView();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: search(5,expub.publisher_id);System.out.println("\uD83D\uDC4B Back to menu");break;
                case 2: expub.checkSales();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 3: expub.checkProfile();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 4: expub.checkRequests();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 5: flag = false; System.out.println("\uD83D\uDC4B Goodbye Publisher - Logged Out"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }

    }

    public void NewUser() {
        String id = iDGen(this.uids);
        User newuser = User.NewUsr(id);
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")
        ) {
            java.sql.Date sqlDate = new java.sql.Date(newuser.getAccount().expirydetail.getTime());
            System.out.println(sqlDate);

            String query = "insert into public.user " + "(user_id,user_type,name,email,password,bank_account,address)"
                    +
                    " values " +
                    "( '"+ newuser.user_id+"',TRUE,ROW('"+newuser.first_name+"','"+newuser.middle_name+"','"+newuser.second_name+"'),'"+newuser.getEmail()+"','"+newuser.getPassword()+"',ROW ('"+newuser.getAccount().account_name+"',"+newuser.getAccount().account_number+",'"+sqlDate+"'),ROW('"+newuser.getAddress().address_name+"','"+newuser.getAddress().city+"','"+newuser.getAddress().state+"','"+newuser.getAddress().zip+"') )";
            System.out.println(query);
            PreparedStatement usr = connection.prepareStatement(query);
            usr.execute();
            uids.add(id);
        } catch (Exception sqle) {
            System.out.println("Exception SQL: " + sqle);
        }
    }

    public void NewPublisher(){
        String id = iDGen(this.uids);
        Publisher newpub = Publisher.NewUsr(id);
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")

        ) {
            java.sql.Date sqlDate = new java.sql.Date(newpub.getAccount().expirydetail.getTime());
            String query = "insert into public.publisher " + "(publisher_id,name,email,password,address,bank_account)"
                    +
                    " values " +
                    "( '"+ newpub.publisher_id+"',ROW('"+newpub.first_name+"','"+newpub.middle_name+"','"+newpub.second_name+"'),'"+newpub.getEmail()+"','"+newpub.getPassword()+"', ROW('"+newpub.getAddress().address_name+"','"+newpub.getAddress().city+"','"+newpub.getAddress().state+"','"+newpub.getAddress().zip+ "') ,ROW ('"+newpub.getAccount().account_name+"',"+newpub.getAccount().account_number+",'"+sqlDate+"'))";
            System.out.println(query);
            PreparedStatement pubsr = connection.prepareStatement(query);
            pubsr.execute();
            uids.add(id);
        } catch (Exception sqle) {
            System.out.println("Exception Pub SQL: " + sqle);
        }
    }
    //Functions
    public void showReports(){

    }

    public static void search(int option, String input){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query = "select * from SearchByAtomic('"+input+"','"+search_types.get(option)+"');";
            ResultSet result = statement.executeQuery(query);
            System.out.println("=============================================== Your Books Are ================================================\n");
            while(result.next()){
                System.out.println("=============================================================================================================");
                System.out.println("\uD83E\uDD3E\uD83C\uDFFC\u200DISBN             : "+result.getString("isbn"));
                System.out.println("    \uD83D\uDD0BQuantity         : "+result.getString("quantity"));
                System.out.println("    \uD83D\uDCD3Book Name        : "+result.getString("book_name"));
                System.out.println("    \uD83C\uDFA7Number Of Pages  : "+result.getString("number_of_pages"));
                System.out.println("    \uD83D\uDC41Unit Price       : $"+result.getString("unit_price"));
                System.out.println("    \uD83D\uDCC6Date of Publish  : "+result.getString("date_of_publish"));
                System.out.println("    \uD83E\uDD39\u200D️Publisher Name   : "+result.getString("publisher_name"));
                System.out.println("    \uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDFA8Authors          : "+result.getString("authors"));
                System.out.println("    \uD83E\uDDE0Genres           : "+result.getString("genres"));
                System.out.println("=============================================================================================================\n");
            }

        } catch (Exception sqle) {
            System.out.println("Exception search: " + sqle);
        }
    }

    //SearchTool
    public static void searchBook(){
        Scanner usrin = new Scanner(System.in);
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        int option;
        while (true){
            SearchMenu();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            option = usrin.nextInt();
            System.out.println("You have entered " + option);
            if(option==5){System.out.println("Hope you found what you were looking for!"); break;}
            if(search_types.get(option)!=null){
                String statement = "Enter your" + search_titles.get(option) + " : ";
                String input = getInput(br,statement);
                search(option,input);
            }else{System.out.println("You have selected an invalid option!");}
        }
    }

    private void prepareAdmin(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")
        ) {
            Statement statement = connection.createStatement();
            String query = "select * from public.user where public.user.user_type = false";
            ResultSet result = statement.executeQuery(query);
            if(!result.next()){
                String qry = "update public.user set user_type = false where user_id = '1000000004'";
                PreparedStatement pubsr = connection.prepareStatement(qry);
                pubsr.execute();
            }

        } catch (Exception sqle) {
            System.out.println("Exception Pub SQL: " + sqle);
        }
    }
}
