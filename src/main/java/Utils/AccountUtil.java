package Utils;

import exception.NotIntegerException;
import model.CurrencyType;

import java.util.Calendar;

public class AccountUtil {
    public static boolean isValidId(String accountId) {
        if (accountId.length() != ApplicationConst.ACCOUNT_NAME_LENGTH) {
            return false;
        }
        if (!accountId.substring(0, 2).equals(ApplicationConst.ACCOUNT_NAME_PREFIX)) {
            return false;
        }
        return true;
    }

    public static boolean isValidAmount(String amount) {
        try {
            if (Integer.valueOf(amount) > 0) {
                return true;
            }
        } catch (Exception e) {
            Character letterOrNumber = amount.charAt(0);
            if (Character.isLetter(letterOrNumber)) {
                System.out.println("Is letter! The string is not integer!");
                return false;
            }/* else if(Integer.valueOf(amount) <= 0){
                System.out.println("The number is not positive!");
                return false;
            }*/
        }
        return false;
    }

    public static boolean isValidCurrencyType(String currencyType) {
        if (currencyType.equalsIgnoreCase("RON") || (currencyType.equalsIgnoreCase("EUR"))) {
            return true;
        }
        return false;
    }

    public static CurrencyType getCurrencyType(String currencyStr) {
        if (currencyStr.equalsIgnoreCase("RON")) {
            return CurrencyType.RON;
        }
        if (currencyStr.equalsIgnoreCase("EUR")) {
            return CurrencyType.EUR;
        }
        return CurrencyType.NO_CURRENCY;
    }
}
