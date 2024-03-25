package film_service.business;
import java.util.ArrayList;
public class UserManager {
    private final ArrayList<User> userList = new ArrayList<User>();

    public UserManager() {
        bootstrapUserList();

    }

    public boolean addUser(String username, String password, boolean isAdmin) {

        User name = new User(username, password, isAdmin);

        if (userList.contains(name)) {
            return false;
        } else {
            userList.add(name);
            return true;
        }
    }

    public ArrayList<User> searchByUsername(String username) {
        ArrayList<User> check = new ArrayList<User>();

        for (User u : userList) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                check.add(u);
            }
        }
        return check;
    }

    private void bootstrapUserList() {

        User u1 = new User("BillyBob321", "BillyBobGGnoree", false);
        if (u1 != null) userList.add(u1);
        User u2 = new User("E10", "GGs237", false);
        if (u2 != null) userList.add(u2);
        User u3 = new User("FVGhost", "Function21", true);
        if (u3 != null) userList.add(u3);
        User u4 = new User("JaketheKuza", "Yakuza23", true);
        if (u4 != null) userList.add(u4);
    }

    private User createUser(String username, String password, boolean isAdmin) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.err.println("Invalid username or password for user creation: " + username);
            return null;
        }
        return new User(username, password, isAdmin);
    }
}


