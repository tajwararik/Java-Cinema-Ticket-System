package entities;

public class Movie {

    private String name;
    private String genre;
    private int ageRestriction;

    public Movie(String name, String genre, int ageRestriction) {
        this.name = name;
        this.genre = genre;
        this.ageRestriction = ageRestriction;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }
}