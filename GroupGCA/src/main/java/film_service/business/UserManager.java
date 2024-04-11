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

    public User authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }


    private void bootstrapUserList() {

        User u1 = new User("BillyBob321", "BillyBobGGnoree", false);
        userList.add(u1);
        User u2 = new User("E10", "GGs237", false);
        userList.add(u2);
        User u3 = new User("FVGhost", "Function21", true);
        userList.add(u3);
        User u4 = new User("JaketheKuza", "Yakuza23", true);
        userList.add(u4);
        User u5 = new User("Admin", "admin", true);
        userList.add(u5);
    }


}


