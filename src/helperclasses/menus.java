package helperclasses;

public class menus {
    public static void mainmenu(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDC69\u200D Book Cafe Administrator press 1 ");
        System.out.println("\uD83E\uDDB8\u200D️ Existing User (Sign in) press 2");
        System.out.println("\uD83D\uDD75️\u200D️ New User (Sign up) press 3");
        System.out.println("\uD83D\uDC81\u200D️ Existing Publisher press 4");
        System.out.println("\uD83E\uDD37\u200D️ New Publisher press 5");
        System.out.println("\uD83D\uDC4B EXIT BOOKCAFE press 6 \n");
    }
    public static void ExUserView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book              - Press 1 ");
        System.out.println("\uD83D\uDCD6 Add to cart/ Check your orders - Press 2");
        System.out.println("\uD83D\uDCD6 Check profile details          - Press 3");
        System.out.println("\uD83D\uDCD6 Exit                           - Press 4");
    }

    public static void ShipmentMenu(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Add New Cart - Press 1 ");
        System.out.println("\uD83D\uDCD6 Fill Previous Cart - Press 2");
        System.out.println("\uD83D\uDCD6 View Shipments - Press 3");
        System.out.println("\uD83D\uDCD6 Cancel Shipment - Press 4");
        System.out.println("\uD83D\uDCD6 Exit - Press 5");
    }

    public static void AdministratorView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Search for a book press 1");
        System.out.println("\uD83D\uDCD6 Add a book press 2");
        System.out.println("\uD83D\uDCD6 Delete a book press 3");
        System.out.println("\uD83D\uDCD9 View all users press 4");
        System.out.println("\uD83D\uDC4D Track shipments press 5");
        System.out.println("\uD83D\uDC4D To show reports press 6");
        System.out.println("\uD83D\uDC4D View all publishers 7");
        System.out.println("\uD83D\uDC4D Exit mode press 8");
    }
    public static void ExPublisherView(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Show my books press 1 ");
        System.out.println("\uD83D\uDCD6 Check Sales Account Status press 2");
        System.out.println("\uD83D\uDCD6 Check profile details 3");
        System.out.println("\uD83D\uDCD6 Check Book Reorder Requests press 4 ");
        System.out.println("\uD83D\uDCD6 Exit Press 5");
    }

    public static void Sales(){
        System.out.println("\u001b[34m------------- Menu -------------");
        System.out.println("\uD83D\uDCD6 Copies sold by genre 1 ");
        System.out.println("\uD83D\uDCD6 Copies sold by author 2");
        System.out.println("\uD83D\uDCD6 Sales by Current Month 3");
        System.out.println("\uD83D\uDCD6 Sales by Since First Sale 4");
        System.out.println("\uD83D\uDCD6 Exit Any other number to exit");
    }

    public static void SearchMenu(){
        System.out.println("\u001b[34m------------- Search Book By -------------");
        System.out.println("\uD83D\uDCD6 ISBN - press 1 ");
        System.out.println("\uD83D\uDCD6 Book name - press 2");
        System.out.println("\uD83D\uDCD6 Author Name - press 3");
        System.out.println("\uD83D\uDCD6 Genre - press 4");
        System.out.println("\uD83D\uDCD6 Exit - press 5");
    }
}
