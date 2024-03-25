import film_service.business.Film;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FilmTest {
    @Test
    public void testCalculateRating() {
        Film film = new Film("Test Film","Test Genre");
        film.addRating(8);
        film.addRating(6);
        film.addRating(9);
        double finalRating = film.calculateRating();
        assertEquals(7.67, finalRating, 0.1 );
    }
}
