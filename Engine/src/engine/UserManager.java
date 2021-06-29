package engine;

import java.util.*;

public class UserManager {

    private Map<String, User> Users;

    public UserManager() {
        Users = new HashMap<>();
    }

    public synchronized void addUser(String username)
    {
        User newUser = new User(username);
        Users.put(username, newUser);
    }

    public synchronized void removeUser(String username) {
        Users.remove(username);
    }

    public synchronized Map<String, User> getUsers() {
        return Collections.unmodifiableMap(Users);
    }

    public boolean isUserExists(String username) {
        return Users.containsKey(username);
    }

}
