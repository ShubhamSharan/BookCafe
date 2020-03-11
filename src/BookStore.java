import java.util.HashMap;
import java.util.Scanner;

public class BookStore {
    String book_store_name;
    HashMap<String,Book> books;
    HashMap<Integer,String> search_types;
    Scanner usrinput = new Scanner(System.in);

    public BookStore(String bsn){
        book_store_name = bsn;
        books = new HashMap();
        search_types.put(1,"ISBN");
        search_types.put(2,"book_name");
        search_types.put(3,"authors");
        search_types.put(4,"genre");
    }
    //Adding Books to a BookStore via SQL
    public void addBookToStore(Book book){


    }
    //Book Printing
    public void printBookDetails(Book book){
        System.out.println("Book name       : "+ book.book_name);
        System.out.println("ISBN            : "+ book.ISBN);
        System.out.println("Authors         : "+ book.printAuthors());
        System.out.println("Genres          : "+ book.printGenres());
        System.out.println("Price           : "+ book.unit_price);
        System.out.println("Number of Pages : "+ book.number_of_pages);
        System.out.println("Date of publish : "+ book.date_of_publish);
    }
    //
    public void addToCart(){
        System.out.println("Enter 10 digit ISBN number to add to Cart: ");
        String isbn = usrinput.nextLine();
        if(isbn.length()!=10){
            System.out.println("Invalid ISBN number");
        }else{

        }

    }
    public void search(int option){

    }

    //SearchTool
    public void searchTool(){
        Book selectedBook = null;
        System.out.println("\u001b[34m------------- Search a Book By -------------");
        System.out.println("\uD83D\uDCD6 ISBN - press 1");
        System.out.println("\uD83D\uDCD6 Book Name - press 2");
        System.out.println("\uD83D\uDCD6 Author name - press 3");
        System.out.println("\uD83D\uDCD6 Genre - press 4");
        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            if(search_types.get(option)!=null){
                search(option);
            }else{System.out.println("You have selected an invalid option!");}

        }
    }




}
