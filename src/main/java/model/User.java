package model;

import java.util.Iterator;
import java.util.Map;

public class User {

    private String userId;
    private String password;
    private Map<String, Account> accounts;

    public User(String userId, String password, Map<String, Account> accounts) {
        this.userId = userId;
        this.password = password;
        this.accounts = accounts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("User: " + userId + "\nAccounts: ");
        if (accounts == null || accounts.isEmpty()) {
            stringBuilder.append("\tThere is not account for user: " + userId);
        }else {
           /* for(Map.Entry<String,Account> entry : accounts.entrySet()){
                stringBuilder.append(entry.getValue());
            }*/
            Iterator<Map.Entry<String, Account>> itr = accounts.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Account> entry = itr.next();
                stringBuilder.append(entry.getValue());
            }
        }

        return stringBuilder.toString();
    }
}
