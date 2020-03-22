import java.util.Scanner;

import static helperclasses.menus.*;

public class Homepage {
    public static void main(String [] args){
        System.out.println("\u001b[35mWELCOME TO THE BOOK CAFE \uD83D\uDCDA \uD83E\uDD13");
        BookStore bookcafe = new BookStore("bookcafe"); //right now the db is empty
        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            mainmenu();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: bookcafe.Admin();break;
                case 2: bookcafe.ExUser();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 3: bookcafe.NewUser();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 4: bookcafe.ExPublisher();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 5: bookcafe.NewPublisher();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 6: flag = false; System.out.println("\uD83D\uDC4B Thank you for visiting"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }



    }

}
