package monitoreo.data.dashboards.users;

import java.util.HashMap;
import java.util.Set;

public class DashboardUserRegisterServer {
    HashMap<String, User> users = new HashMap<String, User>();


    public boolean existsUser(String entryUserName) {
        return users.containsKey(entryUserName);
    }

    public String getUserPassword(String entryUserName) {
        User user = users.get(entryUserName);
        return user.getPassword();
    }

    public Boolean addUser(String entryUserName, String entryPassword) {
        if (existsUser(entryUserName)) {
            return false;
        }

        User newUser = new User(entryUserName, entryPassword);
        users.put(entryUserName, newUser);
        return true;
    }

    public User getUser(String userName) {
        return users.get(userName);
    }

    public Set<String> getAllUsers() {
        return users.keySet();
    }
}
