package film_service.business;

import java.util.Objects;

public class Film {
    private String title;
    private String genre;
    private int numRatings;
    private int totalRatings;

    public Film(String title, String genre) {
        this.title = title;
        this.genre = genre;
        this.totalRatings = 0;
        this.numRatings = 0;
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

    //When a user rates a film out of 10 their rating is added to the total and the number of ratings is increased
    public void addRating(int rating) {
        //Make sure that ratings can only be out of 10
        if (rating >= 0 && rating <= 10) {
            totalRatings += rating;
            numRatings++;
        }
    }

    //Calculate the total
    public double calculateRating() {
        if (numRatings == 0) {
            return 0; //Don't divide by 0 or the computer will explode
        }
        return (double) totalRatings / numRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", totalRatings=" + totalRatings +
                ", numRatings=" + numRatings +
                '}';
    }

    public String encode(String delimiter){
        return this.title + delimiter + this.genre + delimiter + this.totalRatings + delimiter + this.numRatings;
    }

    public static Film decode(String encoded, String delimiter){
        String [] components  = encoded.split(delimiter);
        if(components.length != 2){
            return null;
        }
        return new Film(components[0], components[1]);
    }
}