package film_service.business;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private final ArrayList<Film> films = new ArrayList<Film>();

    public FilmManager(){
        bootstrapFilmList();
    }

    public boolean addFilm(Film film) {
        // Check if a film with the same title already exists
        for (Film existingFilm : films) {
            if (existingFilm.getTitle().equals(film.getTitle())) {
                return false;
            }
        }
        // Add the film to the list
        films.add(film);
        return true;
    }

    public Film searchByTitle(String title) {
        for (Film film : films) {
            if (film.getTitle().equals(title)) {
                return film; // Return the film if found
            }
        }
        return null; // Return null if the film is not found
    }

    public List<Film> searchByGenre(String genre) {
        List<Film> genreFilms = new ArrayList<>();
        for (Film film : films) {
            if (film.getGenre().equalsIgnoreCase(genre)) {
                genreFilms.add(film);
            }
        }
        return genreFilms; // Return a list of films with the specified genre
    }

    public boolean rateFilm(String title, int rating) {
        Film film = searchByTitle(title);
        boolean rated;
        if (film != null) {
            film.addRating(rating); // Add rating to the film if found
            rated = true;
        } else {
            rated = false;
        }
        return  rated;
    }

    public double getFilmRating(String title) {
        Film film = searchByTitle(title);
        if (film != null) {
            return film.calculateRating();
        }
        return 0;
    }

    public boolean removeFilm(String title) {
        for (int i = 0; i < films.size(); i++) {
            if (films.get(i).getTitle().equals(title)) {
                films.remove(i); // Remove the film if found
                return true;
            }
        }
        return false;
    }

    private void bootstrapFilmList(){
        Film f1 = new Film("Kung Fu Panda 4", "Animated");
        f1.addRating(6);
        films.add(f1);
        Film f2 = new Film("Spider-Man: Beyond the Spider-Verse", "Animated");
        f2.addRating(10);
        films.add(f2);
        Film f3 = new Film("Drive", "Action");
        f3.addRating(7);
        films.add(f3);
        Film f4 = new Film("Finding Nemo", "Adventure");
        f4.addRating(9);
        films.add(f4);
        Film f5 = new Film("The Shining", "Horror");
        f5.addRating(8);
        films.add(f5);

    }


}
