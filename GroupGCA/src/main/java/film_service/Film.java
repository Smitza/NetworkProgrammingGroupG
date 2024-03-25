package film_service;

import java.util.Objects;

public class Film {
    private String title;
    private String genre;
    private int totalRatings;
    private int numRatings;

    public Film(String title, String genre, int totalRatings, int numRatings) {
        this.title = title;
        this.genre = genre;
        this.totalRatings = totalRatings;
        this.numRatings = numRatings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, totalRatings, numRatings);
    }
}
