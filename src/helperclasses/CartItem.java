package helperclasses;

import java.util.Date;

public class CartItem {
        public int item_id;
        public String ISBN;
        public int quantity;
        Date date_of_purchase;
        public CartItem(int id, String isbn, int q){
                item_id = id;
                ISBN = isbn;
                quantity = q;
        }
}
