import helperclasses.Address;
import helperclasses.BankingAccount;
import helperclasses.CartItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static helperclasses.Address.makeAddress;
import static helperclasses.BankingAccount.makeAccount;
import static helperclasses.inputFunctions.*;
import static helperclasses.menus.ShipmentMenu;

public class User {
    //Trackers
    HashSet<String> ordersuids = new HashSet<>();
    HashMap<String,ShoppingCart> currentCarts;

    String user_id;
    String first_name;
    String middle_name;
    String second_name;

    private String email;
    private String password;
    private Address address;
    private String[] phonenumber;
    private BankingAccount account;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public String[] getPhonenumber() {
        return phonenumber;
    }

    public BankingAccount getAccount() {
        return account;
    }

    public User(String id){
        user_id = id;
        first_name = "";
        middle_name = "";
        second_name = "";
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
        addIDs(ordersuids, "public.shopping_cart", "order_id");
        currentCarts = new HashMap<>();
    }

    public User(String userId, String fname, String sname){
        user_id = userId;
        first_name = fname;
        second_name = sname;
        email = "";
        password = "";
        address = null;
        phonenumber = null;
        account = new BankingAccount();
        addIDs(ordersuids, "public.shopping_cart", "order_id");
        currentCarts = new HashMap<>();
    }

    public static User NewUsr(String id) {
        System.out.println("\u001b[34m------------- Welcome New User -------------");
        User newUser = new User(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        newUser.first_name = getInput(br, "First Name: ");
        System.out.print("Middle Name (Optional): ");
        try {
            newUser.middle_name = br.readLine();
            if(newUser.middle_name.replaceAll("\\s+","").length() == 0){
                System.out.println("No middle name added");
            }
        } catch (IOException e) {
            newUser.middle_name = "";
        }
        newUser.second_name = getInput(br, "Last Name : ");
        newUser.email = getInput(br,"Email : " );
        newUser.password = getInput(br, "Password : ");
        newUser.address = makeAddress();
        newUser.phonenumber = enterPhoneNumber(br);
        newUser.account = makeAccount();
        return newUser;
    }

    public static void printUsers(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select * from UserDets('')";
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("No users exist");
                return;
            }
            System.out.println("================================================== U S E R S ===================================================");
            do{
                System.out.println("============================================================================================================");
                System.out.println("\uD83D\uDCF8User ID           : "+usr.getString("user_id"));
                System.out.println("\uD83D\uDCF8First Name        : "+usr.getString("first_name"));
                System.out.println("\uD83D\uDCF8Middle Name       : "+usr.getString("middle_name"));
                System.out.println("\uD83D\uDCF8Last Name         : "+usr.getString("last_name"));
                System.out.println("\uD83D\uDCF8Email             : "+usr.getString("email"));
                System.out.println("\uD83D\uDCF8Address           : "+usr.getString("address"));
                System.out.println("============================================================================================================\n\n");
            }while(usr.next());
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public static User checkUser(String type){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String email = getInput(br,"Email : ");
        String password = getInput(br,"Password : ");
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select user_id,user_type,(name).first_name as first_name,(name).last_name as last_name from public.user where public.user.email = '"+email+"' and public.user.password = '"+password+"'";
//            System.out.println(query);
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("This user doesn't exist");
            }
            //-- update public.user set user_type = false where user_id = '1000000004'
            else if(type.equals("Admin")){
                String id = usr.getString("user_id");
                String fname = usr.getString("first_name");
                String lname = usr.getString("last_name");
                boolean iden = Boolean.getBoolean(usr.getString("user_type"));
//                System.out.println(iden);
                if(!iden){ return new User(id,fname,lname); }
                else{ return null;}
            }
            else if(type.equals("User")){
                String id = usr.getString("user_id");
                String fname = usr.getString("first_name");
                String lname = usr.getString("last_name");
                return new User(id,fname,lname);
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
        return null;
    }

    public void checkProfile(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select * from UserDets('"+this.user_id+"')";
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("Unknown error");
                return;
            }
            System.out.println("========================================= Y O U R == P R O F I L E =========================================");
            do{
                System.out.println("============================================================================================================");
                System.out.println("User ID           : "+usr.getString("user_id"));
                System.out.println("First Name        : "+usr.getString("first_name"));
                System.out.println("Middle Name       : "+usr.getString("middle_name"));
                System.out.println("Last Name         : "+usr.getString("last_name"));
                System.out.println("Email             : "+usr.getString("email"));
                System.out.println("Address           : "+usr.getString("address"));
                System.out.println("============================================================================================================\n");
            }while(usr.next());
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }
    private Address fetchAddress(){
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query = String.format("select (public.user.address).address_name as an,(public.user.address).city as ct,(public.user.address).state as st, (public.user.address).zip as zip from public.user where public.user.user_id = '%s'", this.user_id);
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("This user doesn't exist");
            }
            else{
                return new Address(usr.getString("an"),usr.getString("ct"),usr.getString("st"),usr.getString("zip"));
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
        return null;
    }

    public ShoppingCart addNewCart(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("New Cart--------------------------------");
        int address = Integer.parseInt(getInput(br,"Press 1 to use registered address info and 0 for new address info: "));
        ShoppingCart newCart = new ShoppingCart(this.user_id,iDGen(ordersuids));
        if(address==1){
            newCart.shipment_address = this.fetchAddress();
            assert newCart.shipment_address != null;
            System.out.println("This order will be shipped too "+newCart.shipment_address.address_name+", "+newCart.shipment_address.city+", "+newCart.shipment_address.state+", "+newCart.shipment_address.zip);

        }else{
            newCart.shipment_address = makeAddress();
            System.out.println("This order will be shipped too "+newCart.shipment_address.address_name+", "+newCart.shipment_address.city+", "+newCart.shipment_address.state+", "+newCart.shipment_address.zip);
        }
        try (
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")
        ) {
            String query = "insert into public.shopping_cart " + "(order_id,user_id,shipment_address)"
                    +
                    " values " +
                    "( '"+ newCart.order_id+"','"+newCart.user_id+"', ROW('"+newCart.shipment_address.address_name+"','"+newCart.shipment_address.city+"','"+newCart.shipment_address.state+"','"+newCart.shipment_address.zip+"') )";
//            System.out.println(query);
            PreparedStatement newcart = connection.prepareStatement(query);
            newcart.execute();
            newcart.close();
            int addElemenets = Integer.parseInt(getInput(br,"Would you like to add elements to this new cart? (Press 1 if yes, 0 otherwise) : "));
            if(addElemenets==1){
                addTo(newCart);
                currentCarts.put(newCart.order_id,newCart);
            }
            return  newCart;
        } catch (Exception sqle) {
            System.out.println("Exception addNewBook: " + sqle);
        }
        return null;
    }
    public void addTo(ShoppingCart cart){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            BookStore.searchBook();
            cart.addToCart();
            int input = Integer.parseInt(getInput(br, "Do you want to continue adding more books to cart (press 1 to say yes/ 0 otherwise): "));
            if(input!=1){
                break;
            }
        }
        int inputTwo = Integer.parseInt(getInput(br, "Do buyAll press 1 / Do edit cart press 2 / Any other number to exit!"));
        if(inputTwo==1){
            int option = Integer.parseInt(getInput(br, "1 <- Use Current Registered Billing Info | 2 <- Enter New Billing Info : "));
            if(option==1){
                try (
                        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                        Statement statement = connection.createStatement()
                ) {
                    String query ="select (public.user.bank_account).account_name as ac,(public.user.bank_account).account_number as an, (public.user.bank_account).expirydate as ex from public.user where public.user.user_id = '"+user_id+"'";
                    ResultSet crt = statement.executeQuery(query);
                    while(crt.next()){
                        this.account.account_number = crt.getLong("an");
                        this.account.account_name = crt.getString("ac");
                        this.account.expirydetail = crt.getDate("ex");
                    }
                } catch (Exception sqle) {
                    System.out.println("Exception 1: " + sqle);
                }
                System.out.println("Using Banking Details of :"+this.getAccount().account_name);
                System.out.println("Using Banking Details of :"+String.valueOf(this.getAccount().account_number).substring(0,6)+"***** ******");
            }else{
                BankingAccount account = makeAccount();
                System.out.println("Using Banking Details of :"+account.account_name);
                System.out.println("Using Banking Details of :"+String.valueOf(account.account_number).substring(0,6)+"***** ******");
            }
            cart.buyCart();
            cart.displayItems();
        }else if(inputTwo == 2){
            cart.displayItems();
            cart.editItems();
        }
    }

    public void addToExCart(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DisplayExistingCarts();
        String order_id = getIDinput(br,"Enter order id for which you want to add elements too :");
        if(order_id.contains(order_id)){
            try (
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                    Statement statement = connection.createStatement()
            ) {
                String query ="select * from ShoppingCartsList('"+this.user_id+"') where order_id = '"+order_id+"'";
                ResultSet crt = statement.executeQuery(query);
                while(crt.next()){
                    ShoppingCart cart = new ShoppingCart(this.user_id,crt.getString("order_id"));
                    cart.shipment_address = new Address(crt.getString("address_name"),crt.getString("city"),crt.getString("state"),crt.getString("zip"));
                    addTo(cart);
                    currentCarts.put(cart.order_id,cart);
                }
            } catch (Exception sqle) {
                System.out.println("Exception 1: " + sqle);
            }
        }else{System.out.println("Order ID doesn't exist!!!");
        }
    }

    private void DisplayExistingCarts() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query ="select * from ShoppingCartsList('"+this.user_id+"')";
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("No existing carts available");
            }else{
            System.out.println("========================================= Y O U R == C A R T S =========================================");
            do{
                System.out.println("============================================================================================================");
                System.out.println("\uD83D\uDCF2Order ID       : "+usr.getString("order_id"));
                System.out.println("\uD83D\uDCF2Address Name   : "+usr.getString("address_name"));
                System.out.println("\uD83D\uDCF2City           : "+usr.getString("city"));
                System.out.println("\uD83D\uDCF2State          : "+usr.getString("state"));
                System.out.println("\uD83D\uDCF2Zip            : "+usr.getString("zip"));
                if(currentCarts.get(usr.getString("order_id")) != null){
                    ShoppingCart cart  = currentCarts.get(usr.getString("order_id"));
                    for(CartItem item : cart.cartItems.values()){
                        System.out.println("    \uD83D\uDCF2Index                : "+item.item_id);
                        System.out.println("    \uD83D\uDCF2ISBN                 : "+item.ISBN);
                        System.out.println("    \uD83D\uDCF2Quantity             : "+item.quantity);
                    }
                }
                System.out.println("============================================================================================================\n\n");
            }while(usr.next());}
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public void viewShipments(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query;
            String extraInfo = "";
            int view = Integer.parseInt(getInput(br,"1 <- Check all shipments | Any number <- Specific shipment : "));
            if(view ==1){
                query ="select * from Shipment('"+this.user_id+"')";
            }else{
                String orderid = getIDinput(br,"Enter Order ID : ");
                query = "select * from Shipment('"+this.user_id+"') where orderId = '"+orderid+"'";
                extraInfo = " and in particular this shipment with order number : "+orderid+" doesn't exist.";
            }
            ResultSet usr = statement.executeQuery(query);
                if(!usr.next()){
                    System.out.println("No shipments exist for :"+user_id+extraInfo);
                }
                else{
                    System.out.println("========================================= Y O U R == S H I P M E N T S =========================================");
                    do{
                        System.out.println("============================================================================================================\n");
                        System.out.println("\uD83C\uDF89Order ID                 : "+usr.getString("orderId"));
                        System.out.println("    \uD83C\uDF89Address Name             : "+usr.getString("addressName"));
                        System.out.println("    \uD83C\uDF89City                     : "+usr.getString("city"));
                        System.out.println("    \uD83C\uDF89State                    : "+usr.getString("state"));
                        System.out.println("    \uD83C\uDF89Zip                      : "+usr.getString("zip"));
                        Calendar ncal = Calendar.getInstance();
                        ncal.setTime(usr.getDate("shipmentPlacementDate"));
                        System.out.println("    \uD83C\uDF89Shipment placement date  : "+ncal.getTime().toString());
                        Calendar cal = Calendar.getInstance();
                        Date date = usr.getDate("shipmentPlacementDate");
                        cal.setTime(date);
                        cal.add(Calendar.DATE,2);
                        cal.set(Calendar.HOUR, 10);
                        cal.set(Calendar.MINUTE, 30);
                        System.out.println("    \uD83C\uDF89Shipment Arrival date    : "+cal.getTime().toString());
                        String ans;
                        Calendar today = Calendar.getInstance();
                        if(today.get(Calendar.DAY_OF_YEAR) == ncal.get(Calendar.DAY_OF_YEAR)){
                            ans = "Order still in Store";
                        }else if(today.get(Calendar.DAY_OF_YEAR) > cal.get(Calendar.DAY_OF_YEAR)){
                            ans = "Delievered";
                        }else{ans = "On the road";}
                        System.out.println("    \uD83C\uDF89Order currently in       : "+ans);
                        System.out.println("============================================================================================================\n\n");
                    }while(usr.next());
                }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    private void cancelOrders() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String order_id = getIDinput(br,"Enter Order ID : ");
        try (
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002")
        ) {
            String query = "delete from public.shopping_cart where public.shopping_cart.order_id = '"+order_id+"';";
            PreparedStatement can = connection.prepareStatement(query);
            can.execute();
            can.close();
        } catch (Exception sqle) {
            System.out.println("Exception addNewBook: " + sqle);
        }
    }

    public void checkShipments(){
        boolean flag = true;
        Scanner usrin = new Scanner(System.in);
        while(flag){
            ShipmentMenu();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: addNewCart();System.out.println("\uD83D\uDC4B Back to menu");break;
                case 2: addToExCart();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 3: viewShipments();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 4: cancelOrders();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 5: exitProtocol(); flag = false; System.out.println("\u001B[33m \uD83D\uDC4B Goodbye User"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }
    }

    public static void showAllShipments(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                Statement statement = connection.createStatement()
        ) {
            String query;
            int view = Integer.parseInt(getInput(br,"1 <- Check all shipments | Any number <- Specific shipment : "));
            if(view ==1){
                query ="select * from Shipment('')";
            }else{
                String orderid = getIDinput(br,"Enter Order ID : ");
                query = "select * from Shipment('') where orderId = '"+orderid+"'";
            }
            ResultSet usr = statement.executeQuery(query);
            if(!usr.next()){
                System.out.println("No shipments exists");
            }
            else{
                System.out.println("========================================= A L L == S H I P M E N T S =========================================");
                do{
                    System.out.println("============================================================================================================\n");
                    System.out.println("\uD83C\uDF89Order ID                 : "+usr.getString("orderId"));
                    System.out.println("\uD83C\uDF89Address Name             : "+usr.getString("addressName"));
                    System.out.println("\uD83C\uDF89City                     : "+usr.getString("city"));
                    System.out.println("\uD83C\uDF89State                    : "+usr.getString("state"));
                    System.out.println("\uD83C\uDF89Zip                      : "+usr.getString("zip"));
                    System.out.println("\uD83C\uDF89Shipment placement date  : "+usr.getDate("shipmentPlacementDate"));
                    System.out.println("============================================================================================================\n\n");
                }while(usr.next());
            }
        } catch (Exception sqle) {
            System.out.println("Exception 1: " + sqle);
        }
    }

    public void exitProtocol(){
        System.out.println("CARTS INSIDE : "+currentCarts.size());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        HashMap<String, ShoppingCart> temp = currentCarts;
        for(ShoppingCart item : currentCarts.values()){
            try (
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                    Statement statement = connection.createStatement()
            ) {
                String query ="select * from Shipment('"+this.user_id+"') where orderId = '"+item.order_id+"'" ;
                ResultSet usr = statement.executeQuery(query);
                if(usr.next()){
                    currentCarts.remove(item.order_id);
                }else{
                    System.out.println("\u001B[31m You still have items in "+item.order_id+" that you have not purchased!");
                    int option = Integer.parseInt(getInput(br,"Press 1 - Buy | Any Other Number to Ignore "));
                    if(option==1){
                        int opt = Integer.parseInt(getInput(br, "1 <- Use Current Registered Billing Info | Any Number <- Enter New Billing Info : "));
                        if(opt==1){
                            System.out.println("Using Banking Details of :"+this.getAccount().account_name);
                            System.out.println("Account Number :"+String.valueOf(this.getAccount().account_number).substring(0,6)+"***** ******");
                        }else{
                            BankingAccount account = makeAccount();
                            System.out.println("Using Banking Details of :"+account.account_name);
                            System.out.println("Account Number :"+String.valueOf(account.account_number).substring(0,6)+"***** ******");
                        }
                        currentCarts.get(item.order_id).buyCart();
                    }else{
                        currentCarts.remove(item.order_id);
                    }
                }
                usr.close();
                //Clean Up Statement
                PreparedStatement rem = connection.prepareStatement("delete from public.shopping_cart where public.shopping_cart.shipment_placement_date = null");
                rem.executeUpdate();
            } catch (Exception sqle) {
                System.out.println("Exception 1: " + sqle);
            }
        }
    }




}
