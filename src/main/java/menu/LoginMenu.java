package menu;

import model.User;
import service.LoginService;

import java.util.Scanner;
import java.util.logging.Logger;

public class LoginMenu extends AbstractMenu {
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    protected void displayOption() {
        System.out.println("LOGIN MENU ");
        System.out.println("1 - Login");
        System.out.println("0 - Exit");
    }

    protected void executeOption(Integer option) {
        switch (option) {
            case 1:
                System.out.println("User: ");
                Scanner scanner = new Scanner(System.in);
                String userId = scanner.nextLine();

                System.out.println("Password: ");
                String password = scanner.nextLine();

                System.out.println(userId + "\n" + password);
                LoginService loginService = new LoginService();
                User user = loginService.login(userId, password);
                if (user != null) {
                    logger.info("Welcome, " + userId);
                    AccountsMenu accountsMenu = new AccountsMenu(user);
                    while (option != 0) {//
                        accountsMenu.displayOption();
                        System.out.println("Your option: ");//
                        option = scanner.nextInt();//
                        accountsMenu.executeOption(option);//
                    }
                } else {
                    logger.warning("Invalid username/passsword!");
                }
                break;
            case 0:
                logger.info("Exiting ...");
                break;
            default:
                logger.warning("Invalid option!");
                break;
        }
    }
}
