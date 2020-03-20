import java.util.Scanner;

public class Homepage {
    public static void menu(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDC69\u200D Book Cafe Administrator press 1 ");
        System.out.println("\uD83E\uDDB8\u200D️ Existing User (Sign in) press 2");
        System.out.println("\uD83D\uDD75️\u200D️ New User (Sign up) press 3");
        System.out.println("\uD83D\uDC81\u200D️ Existing Publisher press 4");
        System.out.println("\uD83E\uDD37\u200D️ New Publisher press 5");
        System.out.println("\uD83D\uDC4B EXIT BOOKCAFE press 6 \n");
    }
    public static void main(String [] args){
        System.out.println("\u001b[35mWELCOME TO THE BOOK CAFE \uD83D\uDCDA \uD83E\uDD13");
        BookStore bookcafe = new BookStore("bookcafe"); //right now the db is empty
        Scanner usrin = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            menu();
            System.out.print("\u001b[33mInsert a Single Number and Press enter/ return :");
            int option = usrin.nextInt();
            System.out.println("You have entered " + option);
            switch(option){
                case 1: bookcafe.AdministratorView();System.out.println("\uD83D\uDC4B Goodbye Admin");break;
                case 2: bookcafe.ExUserView();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 3: bookcafe.NewUserView();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 4: bookcafe.ExPublisherView();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 5: bookcafe.NewPublisherView();System.out.println("\uD83D\uDCDA Back to Menu");break;
                case 6: flag = false; System.out.println("\uD83D\uDC4B Thank you for visiting"); break;
                default: System.out.println("\u001b[31mPlease make sure you type a number fromt the MENU followed by clicking on the enter key");
            }
        }



    }

}
