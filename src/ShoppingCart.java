import helperclasses.Address;
import helperclasses.CartItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static helperclasses.inputFunctions.*;

public class ShoppingCart {
    String order_id;
    String user_id;
    ArrayList<CartItem> cartItems;
    String shipment_status;
    Address shipement_address;
    Date shipment_placement_date;
    int cartSize;



    public ShoppingCart(String usrID, String ordid){
        user_id = usrID;
        order_id = ordid;
        cartItems = new ArrayList<>();
        shipment_status = "Inititiated";
        shipment_placement_date = new Date();
    }

    public void addToCart() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String isbn = getIDinput(br, "Enter 10 digit ISBN number to add to Cart: ");
        boolean flag = foundItem(isbn,"public.book.isbn","public.book");
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
                        ResultSet aSet = statement.executeQuery("select public.book where public.book.isbn = '"+isbn+"' and public.book.quantity >= "+ quantity);
                        if(aSet == null){
                            System.out.println("Quantity is too much");
                        }
                        else{
                            int item_id = cartSize + 1;
                            cartItems.add(new CartItem(item_id,isbn,quantity));
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
        for(CartItem item : cartItems){
            try (
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe?currentSchema=public","shubhamsharan09","")
            ) {
                String query = "insert into public.shipment_confirmed (order_id,isbn,item_quantity) values ('"+this.order_id+"','"+item.ISBN+"',"+item.quantity+");";
                query = query + " UPDATE public.shopping_cart SET shipment_placement_date = Now() WHERE public.shopping_cart.order_id = '"+this.order_id+"';";
                PreparedStatement purchase = connection.prepareStatement(query);
                purchase.execute();
                purchase.close();
            } catch (Exception sqle) {
                System.out.println(" Exception buyCarts: " + sqle);
            }
        }
    }

    public void editItems(){

    }


    public static void printShipments(){

    }



}
