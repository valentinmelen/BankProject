package service;

import Utils.ApplicationConst;
import Utils.TxtFileReader;
import model.Account;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginService {

    public User login(String userId, String password) {
        User user = null;

        TxtFileReader txtFileReader = new TxtFileReader(ApplicationConst.USERS_FILE_PATH);
        ArrayList<String> lines = txtFileReader.read();

        for (String line : lines) {
            String[] tokens = line.split(" ");
            if (tokens.length != 2) {
                continue;
            }
            String fileUserId = tokens[0];
            String filePassword = tokens[1];

            if (userId.equals(fileUserId) && password.equals(filePassword)) {
                Map<String, Account> accountsMap = new HashMap<String, Account>();
                user = new User(userId, password, accountsMap);
                break;
            }
        }
        return user;
    }
}
