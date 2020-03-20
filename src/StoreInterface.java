import java.util.HashMap;

public interface StoreInterface {

    public void addNewBook(Book book);
    public void removeBook(String isbn);
    public void searchBook();
    public void NewPublisherView();
    public void NewUserView();
}
