import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;

public class Homepage {
    public static void main(String [] args){
        BookStore bookcafe = new BookStore("bookcafe", true); //right now the db is empty
        System.out.println("\u001b[35mWELCOME TO THE BOOK CAFE \uD83D\uDCDA \uD83E\uDD13");
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDC69\u200D Book Cafe Administrator press 1 ");
        System.out.println("\uD83E\uDDB8\u200D️ Existing User (Sign in) press 2");
        System.out.println("\uD83D\uDD75️\u200D️ New User (Sign up) press 3");
        System.out.println("\uD83D\uDC81\u200D️ Publisher press 4");

        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);

            switch(option){
                case 1: bookcafe.AdministratorView();flag = false; break;
                case 2: bookcafe.ExUserView();flag = false; break;
                case 3: bookcafe.NewUserView();flag = false; break;
                case 4: bookcafe.PublisherView(); flag = false; break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }



    }

}
