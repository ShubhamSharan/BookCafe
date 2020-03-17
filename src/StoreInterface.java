import java.util.HashMap;

public interface StoreInterface {
    public HashMap<String,Book> addBooks();
    public void addNewBook(Book book);
    public void removeBook(String isbn);
    public HashMap<String,Publisher> addPubslishers();
    public HashMap<String, Author> addAuthors();
}
