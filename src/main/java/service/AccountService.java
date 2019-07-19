package service;

import Utils.AccountUtil;
import Utils.ApplicationConst;
import Utils.TxtFileReader;
import Utils.TxtFileWriter;
import menu.AccountsMenu;
import model.Account;
import model.CurrencyType;
import model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountService {
    private static final Logger logger = Logger.getLogger(Logger.class.getName());
    private User user;

    public AccountService(User user) {
        this.user = user;
    }

    public void buildAccounts() {
        TxtFileReader txtFileReader = new TxtFileReader(ApplicationConst.ACCOUNTS_FILE_PATH);
        ArrayList<String> lines = txtFileReader.read();

        for (String line : lines) {
            String[] tokens = line.split(" ");
            if (tokens.length != 4) {
                continue;
            }
            String accountId = tokens[0];
            if (!AccountUtil.isValidId(accountId)) {
                continue;
            }
            String ownerName = tokens[1];
            if (!user.getUserId().equals(ownerName)) {
                continue;
            }
            String accountBalanceStr = tokens[2];
            int accountBalance = Integer.parseInt(accountBalanceStr);
            if (accountBalance < 0) {
                continue;
            }
            String currencyTypeStr = tokens[3];
            if (!AccountUtil.isValidCurrencyType(currencyTypeStr)) {
                continue;
            }
            CurrencyType currencyType = AccountUtil.getCurrencyType(currencyTypeStr);
            Account account = new Account(ownerName, accountId, new BigDecimal(accountBalance), currencyType);
            user.addAccount(account);
        }
    }

    public void displayCurrentInfo() {
        logger.info(user.toString());
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress any key...");
        scanner.nextLine();
    }

    public Account createdAccount() {
        Scanner scanner = new Scanner(System.in);
        boolean isValidAccount = false;
        boolean isValidCurrency = false;
        boolean isValidBalance = false;

        String accountId = "";
        while (!isValidAccount) {
            System.out.print("Account number (account should start with 'RO' in should have 10 characters)");
            accountId = scanner.nextLine();
            isValidAccount = AccountUtil.isValidId(accountId);
        }
        String balanceStr = "";
        while (!isValidBalance) {
            System.out.println("Amount of money is: ");
            balanceStr = scanner.nextLine();
            isValidBalance = AccountUtil.isValidAmount(balanceStr);
        }
        String currency = "";
        while (!isValidCurrency) {
            System.out.println("Currency of your newly created account is: ");
            currency = scanner.nextLine();
            isValidCurrency = AccountUtil.isValidCurrencyType(currency);
        }
        CurrencyType currencyType = AccountUtil.getCurrencyType(currency);
        BigDecimal balance = new BigDecimal(Integer.parseInt(balanceStr));
        Account account = new Account(user.getUserId(), accountId, balance, currencyType);
        user.addAccount(account);

        return account;
    }

    public void displayAvailableAccounts() {
        logger.info("" + user);
    }

    public void makePayments() {
        System.out.println("--------Transfer-----");
        boolean isValidAccount = false;
        boolean isValidBalance = false;
        displayAvailableAccounts();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Source account: ");
        String sourceAccountStr = scanner.nextLine();
        Account sourceAccount = user.getAccounts().get(sourceAccountStr);

        if (sourceAccount == null) {
            while (!isValidAccount) {
                System.out.print("Introduceti un cont valid care exista! ");
                System.out.println("Source account: ");
                sourceAccountStr = scanner.nextLine();
                isValidAccount = AccountUtil.isValidId(sourceAccountStr);

            }
        }
        String amountOfMoney = "";
        while (!isValidBalance) {
            System.out.println("Introduceti suma de transfer: ");
            amountOfMoney = scanner.nextLine();
            BigDecimal sourceBalance = sourceAccount.getBalance();
            if (sourceBalance.subtract(new BigDecimal(amountOfMoney)).compareTo(BigDecimal.ZERO) == -1) {
                System.out.println("Bani insuficienti!" +
                        "\nTransferati o alta suma mai mica!");
            } else {
                isValidBalance = AccountUtil.isValidAmount(amountOfMoney);
            }
        }

        System.out.println("Destination account: ");
        String destAccountStr = scanner.nextLine();
        Account destAccount = user.getAccounts().get(destAccountStr);
        if (destAccount == null) {
            while (!isValidAccount) {
                System.out.print("Introduceti un cont valid care exista! ");
                System.out.println("Destination account: ");
                destAccountStr = scanner.nextLine();
                isValidAccount = AccountUtil.isValidId(destAccountStr);

            }
        }
        transferMoney(sourceAccount, destAccount, amountOfMoney);
    }

    private void transferMoney(Account sourceAccount, Account destAccount, String amountOfMoney) {

        BigDecimal amountOfMoneyToSend = new BigDecimal(amountOfMoney);

        BigDecimal sourceNewBalance = sourceAccount.getBalance().subtract(amountOfMoneyToSend);
        sourceAccount.setBalance(sourceNewBalance);
        user.addAccount(sourceAccount);

        BigDecimal destNewBalance = destAccount.getBalance().add(amountOfMoneyToSend);
        destAccount.setBalance(destNewBalance);
        user.addAccount(destAccount);
    }
}
