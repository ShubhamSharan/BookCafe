import helperclasses.CartItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static helperclasses.inputFunctions.getInput;

public class ShoppingCart {
    String user_id;
    String order_id;
    ArrayList<CartItem> cartItems;
    boolean state_of_purchase;


    public ShoppingCart(String usrID, String ordid){
        user_id = usrID;
        order_id = ordid;
        cartItems = new ArrayList<>();
        state_of_purchase = false;
    }

    public void addToCart() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CartItem item = new CartItem();
        int item_id = cartItems.size() + 1;
        String isbn = getInput(br, "Enter 10 digit ISBN number to add to Cart: ");
        int quantity = Integer.parseInt(getInput(br,"Enter Quantity : "));

        while(true){
            if((isbn.length()!=10)&&(quantity<=0)){
                System.out.println("Invalid ISBN number or quantity less than 1");
                isbn = getInput(br, "Enter 10 digit ISBN number to add to Cart: ");
                quantity = Integer.parseInt(getInput(br,"Enter Quantity : "));
            }else{
                cartItems.add(new CartItem(item_id,isbn,quantity));
                break;
            }
        }
    }

}
