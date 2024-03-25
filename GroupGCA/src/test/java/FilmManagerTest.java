import film_service.business.Film;
import film_service.business.FilmManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilmManagerTest {

    private FilmManager filmManager;

    @BeforeEach
    public void setUp() {
        filmManager = new FilmManager();
    }

    @Test
    public void testAddDuplicateFilm() {
        filmManager = new FilmManager();
        Film film1 = new Film("Inception", "Sci-Fi");
        Film film2 = new Film("Inception", "Thriller");

        assertTrue(filmManager.addFilm(film1));
        assertFalse(filmManager.addFilm(film2)); // Adding the same film again should return false
    }

    @Test
    public void testSearchTitle() {
        Film film = filmManager.searchByTitle("Kung Fu Panda 4");
        assertNotNull(film);
        assertEquals("Kung Fu Panda 4", film.getTitle());
    }

    @Test
    public void testGetFilmRating() {
        assertEquals(9.0, filmManager.getFilmRating("Finding Nemo"), 0.01);
        assertEquals(8.0, filmManager.getFilmRating("The Shining"), 0.01);
        assertEquals(0.0, filmManager.getFilmRating("Film that doesn't exist"), 0.01); // Non-existing film should return 0
    }

    @Test
    public void testRemoveFilm() {
        assertTrue(filmManager.removeFilm("Drive"));
        assertNull(filmManager.searchByTitle("Drive")); // Shouldn't exist anymore
        assertFalse(filmManager.removeFilm("Non-existing Film")); // Removing non-existing film should return false
    }
}
