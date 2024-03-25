import film_service.business.UserManager; // Import the correct UserManager class
import film_service.business.User; // Import the User class if needed
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
public class UserTest {

    private UserManager userManagement;

    @BeforeEach
    public void setUp() {
        userManagement = new UserManager();
    }

    @Test
    public void testAddUser() {
        assertTrue(userManagement.addUser("testUser", "testPassword", false));

        assertFalse(userManagement.addUser("testUser", "testPassword", false));
    }

    @Test
    public void testSearchByUsername() {
        userManagement.addUser("user1", "password1", false);
        userManagement.addUser("user2", "password2", true);

        ArrayList<User> result = userManagement.searchByUsername("user1");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("user1", result.get(0).getUsername());

        ArrayList<User> result2 = userManagement.searchByUsername("nonExistingUser");
        Assertions.assertTrue(result2.isEmpty());
    }
}
