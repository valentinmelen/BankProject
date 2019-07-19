package menu;

import Utils.ApplicationConst;
import Utils.TxtFileWriter;
import model.Account;
import model.User;
import service.AccountService;

import java.util.logging.Logger;

public class AccountsMenu extends AbstractMenu {
    private static final Logger logger = Logger.getLogger(Logger.class.getName());
    private User user;

    public AccountsMenu(User user) {
        this.user = user;
    }

    protected void displayOption() {
        System.out.println("ACCOUNT MENU");
        System.out.println("1 - Read accounts");
        System.out.println("2 - Create account");
        System.out.println("3 - Make payment");
        System.out.println("4 - Display info");
        System.out.println("0 - Logout");

    }

    protected void executeOption(Integer option) {
        AccountService accountService = new AccountService(user);
        switch (option) {
            case 1:
                accountService.buildAccounts();
                break;
            case 2:
                Account account = accountService.createdAccount();
                TxtFileWriter txtFileWriter2 = new TxtFileWriter(ApplicationConst.ACCOUNTS_FILE_PATH);
                txtFileWriter2.writer(buildString(account));
                break;
            case 3:
                accountService.makePayments();
                accountService.displayCurrentInfo();
                break;
            case 4:
                accountService.displayCurrentInfo();
                break;
            case 0:
                logger.info("User " + user.getUserId() + " is successfully log out");
                break;
            default:
                logger.warning("Invalid option!");
                break;

        }
    }

    private String buildString(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append(account.getAccountId() + " " +
                account.getOwner() + " " +
                account.getBalance() + " " +
                account.getCurrency() + "\n");
        return sb.toString();
    }
}
