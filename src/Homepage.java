import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Homepage {
    public static void AdministratorView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book press 1");
        System.out.println("\uD83D\uDCD6 Add a book press 2");
        System.out.println("\uD83D\uDCD6 Delete a book press 3");
        System.out.println("\uD83D\uDCD6 Search for a book press 3");
    }

    public static void ExUserView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book press 1 ");
        System.out.println("\uD83D\uDCD6 Check your orders 2");
        System.out.println("\uD83D\uDCD6 Check profile details 3");
    }
    public static void NewUserView(){


    }

    public static void PublisherView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book press 1 ");
        System.out.println("\uD83D\uDCD6 Check Sales Account Status press 2");
        System.out.println("\uD83D\uDCD6 Check profile details 3");
    }
    public static void main(String [] args){
        System.out.println("\u001b[35mWELCOME TO THE BOOK CAFE \uD83D\uDCDA \uD83E\uDD13");
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDC69\u200D\uD83D\uDCBC♂️ Book Cafe Administrator press 1 ");
        System.out.println("\uD83E\uDDB8\u200D♂️ Existing User (Sign in) press 2");
        System.out.println("\uD83D\uDD75️\u200D♂️ New User (Sign up) press 3");
        System.out.println("\uD83D\uDC81\u200D♂️ Publisher press 3");

        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);

            switch(option){
                case 1: AdministratorView();flag = false; break;
                case 2: ExUserView();flag = false; break;
                case 3: NewUserView();flag = false; break;
                case 4: PublisherView(); flag = false; break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }



    }

}
