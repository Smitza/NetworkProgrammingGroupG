package business;

import film_service.User;
import java.util.ArrayList;
public class UserManager {
private final ArrayList<User> userList = new ArrayList<User>();
public boolean addUser(String username, String password, boolean isAdmin) {

    User name = new User(username, password, isAdmin);

    if(userList.contains(name)) {
        return false;
    }
    else {
        userList.add(name);
        return true;
    }
}

public ArrayList<User> searchByUsername(String username)
{
    ArrayList<User>check = new ArrayList<User>();

    for(User u : userList)
    {
        if(u.getUsername().equalsIgnoreCase(username))
        {
            check.add(u);
        }
    }
    return check;
}
}