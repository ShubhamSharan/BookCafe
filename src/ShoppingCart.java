import helperclasses.Address;
import helperclasses.CartItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import static helperclasses.inputFunctions.getInput;

public class ShoppingCart {
    String order_id;
    String user_id;
    ArrayList<CartItem> cartItems;
    String shipment_status;
    Address shipement_address;
    Date shipment_placement_date;
    Date shipment_recieved_date;
    int cartSize;

    public ShoppingCart(String usrID, String ordid){
        user_id = usrID;
        order_id = ordid;
        cartItems = new ArrayList<>();
        shipment_status = "Inititiated";
        shipment_placement_date = new Date();
        shipment_recieved_date = null;
        cartSize = cartItems.size();
    }

    public void addToCart() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int item_id = cartSize + 1;
        String isbn = getInput(br, "Enter 10 digit ISBN number to add to Cart: ");
        int quantity = Integer.parseInt(getInput(br,"Enter Quantity : "));

        while(true){
            if((isbn.length()!=10)&&(quantity<=0)){
                System.out.println("Invalid ISBN number or quantity less than 1");
                isbn = getInput(br, "Enter 10 digit ISBN number to add to Cart: ");
                quantity = Integer.parseInt(getInput(br,"Enter Quantity : "));
            }else{
                cartItems.add(new CartItem(item_id,isbn,quantity));
                cartSize++;
                break;
            }
        }
    }


    public static void printShipments(){

    }

    public static void checkShipments(){


    }



}
