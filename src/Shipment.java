import helperclasses.Status;
import java.util.Date;

public class Shipment {
    String shipment_id;
    String ISBN;
    Status status;
    int requested_quantity;
    int provided_quantity;
    Date shipment_placement_date;
    Date shipment_recieved_date;

    public Shipment(String sid, String isbn, int reqqnty){
        shipment_id = sid;
        ISBN = isbn;
        status = Status.ORDER_PLACED;
        requested_quantity = reqqnty;
        provided_quantity = 0;
        shipment_placement_date = new Date();
        shipment_recieved_date = null;
    }

}
