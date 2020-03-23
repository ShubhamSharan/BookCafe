package helperclasses;

import java.util.Date;

public class CartItem {
        int item_id;
        String ISBN;
        int quantity;
        Date date_of_purchase;
        public CartItem(){
                item_id = 0;
                ISBN = "";
                quantity = 0;
        }
        public CartItem(int id, String isbn, int q){
                item_id = id;
                ISBN = isbn;
                quantity = q;
        }
}
