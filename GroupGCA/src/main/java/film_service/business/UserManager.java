package film_service.business;
import java.util.ArrayList;
public class UserManager {
private final ArrayList<User> userList = new ArrayList<User>();

private UserManager() {
    bootstrapUserList();
}
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
    public void bootstrapUserList() {

    User u1 = new User("BillyBob321","BillyBobGGnoree", false);
    User u2= new User("E10","GGs237", false);
    User u3 = new User("FVGhost","Function21", true);
    User u4 = new User("JaketheKuza","Yakuza23", true);
    }
}

