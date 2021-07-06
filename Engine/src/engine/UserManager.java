package engine;

import java.util.*;

public class UserManager {


    private Map<String, User> Users;
    private List<String> Admins;

    public UserManager() {
        Users = new HashMap<>();
        Admins = new ArrayList<>();
    }

    public synchronized void addAdmin(String AdminName)
    {
        Admins.add(AdminName);
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
    public synchronized List<String> getAdmins() {
        return Collections.unmodifiableList(Admins);
    }

    private boolean isUserExists(String username) {
        return Users.containsKey(username);
    }
    private boolean isAdminExists(String adminname) {
        return Admins.contains(adminname);
    }
    public boolean isExists(String name)
{
    return isAdminExists(name)|| isUserExists(name);
}
}
