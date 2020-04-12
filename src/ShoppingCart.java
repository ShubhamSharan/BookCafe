import helperclasses.Address;
import helperclasses.CartItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;

import static helperclasses.inputFunctions.*;

public class ShoppingCart {
    String order_id;
    String user_id;
    HashMap<String,CartItem> cartItems;
    String shipment_status;
    Address shipment_address;
    Date shipment_placement_date;
    int cartSize;

    public ShoppingCart(String usrID, String ordid){
        user_id = usrID;
        order_id = ordid;
        cartItems = new HashMap<>();
        shipment_address = null;
        shipment_status = "Inititiated";
        shipment_placement_date = new Date();
    }

    public void addToCart() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String isbn = getIDinput(br, "Enter 10 digit ISBN number to add to Cart: ");
        boolean flag = foundItem(isbn,"public.book.isbn","book");
        int quantity;
        if(flag){
            while(true){
                quantity = Integer.parseInt(getInput(br,"Enter Quantity : "));
                if(quantity<0){
                    System.out.println("Quantity less than 1");
                }else{
                    try (
                            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","");
                            Statement statement = connection.createStatement()
                    ) {
                        ResultSet aSet = statement.executeQuery("select * from public.book where public.book.isbn = '"+isbn+"' and public.book.quantity >= "+ quantity);
                        if(aSet == null){
                            System.out.println("Quantity is too much");
                        }
                        else{
                            int item_id = cartSize + 1;
                            cartItems.put(isbn,new CartItem(item_id,isbn,quantity));
                            cartSize++;
                            return;
                        }
                    } catch (Exception sqle) {
                        System.out.println(" Exception addtoCarts: " + sqle);
                    }
                }
            }
        }

    }

    public void buyCart(){
        for(CartItem item : cartItems.values()){
            try (
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","")
            ) {
                String query = "insert into public.shipment_confirmed (order_id,isbn,item_quantity) values ('"+this.order_id+"','"+item.ISBN+"',"+item.quantity+");";
                PreparedStatement purchase = connection.prepareStatement(query);
                purchase.execute();
                purchase.close();
            } catch (Exception sqle) {
                System.out.println(order_id+" was not able to be shipped at this point in time.");
                System.out.println(" Exception buyCarts: " + sqle);
            }
        }
    }
    public void displayItems(){
        System.out.println("ORDER ID = "+order_id);
        System.out.println("All items ===============================================================");
        float sum = 0;
        for(CartItem item : cartItems.values()){
            BookStore.search(1,item.ISBN);
            try (
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","yvan2002");
                    Statement statement = connection.createStatement()
            ) {
                String query ="select public.book.unit_price from public.book where public.book.isbn = '"+item.ISBN+"'";
                ResultSet crt = statement.executeQuery(query);
                while(crt.next()){
                    sum  = sum + Float.parseFloat(crt.getString("unit_price"))*item.quantity;
                }
                crt.close();
            } catch (Exception sqle) {
                System.out.println("Exception 1: " + sqle);
            }
            System.out.println("____________________________________________________________________");
        }
        System.out.println("TOTAL PRICE :  $"+sum);
    }

    public void editItems(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            int in = Integer.parseInt(getInput(br, "1 <- remove | 2 <- edit quantity | 3 <- Exit : "));
            switch (in) {
                case 1:
                    removeItem();
                case 2:
                    editQuantity();
                case 3:
                    System.out.println("Changes Saved___________________________");
                    return;
                default:
                    System.out.println("Changes Saved -- Force Exited_____________________________");
                    return;
            }
        } while (true);
    }

    private void editQuantity() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String isbn = getInput(br,"Book you modify quantity for : ");
        int q = Integer.parseInt(getInput(br,"new quantity : "));
        if(cartItems.get(isbn)!=null){
            cartItems.get(isbn).quantity = q;
        }else{
            System.out.println("Incorrect isbn entered!!");
        }
    }

    private void removeItem(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in = getInput(br,"Book you wanna remove");
        if(cartItems.get(in)!=null){
            cartItems.remove(in);
        }else{
            System.out.println("Incorrect isbn entered!!");
        }
    }





}
