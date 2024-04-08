package entities;

public class Ticket {

    private int id;
    private String movieName;
    private double price;
    private String time;

    public Ticket(String movieName, int id, String time, double price) {
        this.movieName = movieName;
        this.id = id;
        this.time = time;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public double getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }
}
