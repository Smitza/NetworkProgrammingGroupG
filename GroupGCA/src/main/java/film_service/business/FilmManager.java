package film_service.business;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private final ArrayList<Film> films = new ArrayList<Film>();


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

    public void rateFilm(String title, int rating) {
        Film film = searchByTitle(title);
        if (film != null) {
            film.addRating(rating); // Add rating to the film if found
        }
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


}
