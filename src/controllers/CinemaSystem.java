package controllers;

import entities.Movie;
import entities.Ticket;
import entities.User;
import repositories.DatabaseConnection;
import repositories.MovieRepository;
import repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class CinemaSystem {

    Scanner scanner = new Scanner(System.in);

    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Ticket> soldTickets;
    private MovieRepository movieRepository;
    private UserRepository userRepository;

    public CinemaSystem() {
        this.users = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.soldTickets = new ArrayList<>();
        this.movieRepository = new MovieRepository();
        this.userRepository = new UserRepository();
    }

    public void addNewMovie() {

        System.out.print("Enter movie name: ");
        String name = scanner.nextLine();

        System.out.print("Enter movie genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter Movie age ratting: ");
        int ageRestriction = scanner.nextInt();
        scanner.nextLine();

        Movie newMovie = new Movie(name, genre, ageRestriction);

        movies.add(newMovie);

        movieRepository.addMovieToDatabase(newMovie);

        System.out.println("New movie added successfully.");
        System.out.println();
    }

//    public void displayAllMovies() {
//        if (movies.isEmpty()) {
//            System.out.println("There are no available movies.");
//            System.out.println();
//        } else {
//            for (Movie movie : movies) {
//                System.out.println("Movie name: " + movie.getName());
//                System.out.println("Movie genre: " + movie.getGenre());
//                System.out.println("Movie age ratting: " + movie.getAgeRestriction());
//                System.out.println();
//            }
//        }
//    }

    public void addNewUser() {
        System.out.print("Enter user name: ");
        String userName = scanner.next();
        scanner.nextLine();

        System.out.print("Enter user age: ");
        int userAge = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter user balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        User user = new User(userName, userAge, balance);

        users.add(user);

        userRepository.addUserToDatabase(user);

        displayUserDetails();
    }

    public void displayUserDetails() {
        for(User user : users) {
            System.out.println("User name: " + user.getUserName());
            System.out.println("User age: " + user.getUserAge());
            System.out.println("User balance: " + user.getBalance());
            System.out.println();
        }
    }

    public void buyTicket() {

//        displayAllMovies();
        displayAllMoviesFromDatabase();

//        if (movies.isEmpty()) {
//            System.out.println("No movies available to buy tickets for.");
//            return;
//        }

        System.out.print("Enter user name: ");
        String userName = scanner.next();
        scanner.nextLine();

        User user = findUserName(userName);

        if (user == null) {
            System.out.println("User not found. Please enter a valid user name.");
            return;
        }

        System.out.print("Enter movie name: ");
        String movieName = scanner.nextLine();

        boolean movieFound = false;

        for (Movie movie : movies) {
            if (movieName.equals(movie.getName())) {

                movieFound = true;

                System.out.print("Enter ticket id: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter time: ");
                String time = scanner.nextLine();

                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();

                Ticket ticket = new Ticket(movieName, id, time, price);

                soldTickets.add(ticket);
                System.out.println("Ticket purchased successfully.");

                user.addToOrderHistory(ticket);
                displayOrderHistory(user);

                System.out.println();
                break;
            }
        }

        if (!movieFound) {
            System.out.println("Movie not found. Please enter a valid movie name.");
        }
    }

//    private User findUserName(String userName) {
//        for(User user : users) {
//            if(userName.equals(user.getUserName())) {
//                return user;
//            }
//        }
//        return null;
//    }

    private User findUserName(String userName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int userAge = resultSet.getInt("age");
                    double balance = resultSet.getDouble("balance");
                    return new User(userName, userAge, balance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void displayOrderHistory(User user) {
        System.out.println("Order history:");
        ArrayList<Ticket> orderHistory = user.getOrderHistory();
        if (orderHistory.isEmpty()) {
            System.out.println("No orders in history.");
        } else {
            for (Ticket ticket : orderHistory) {
                System.out.println("Ticket ID: " + ticket.getId());
                System.out.println("Movie name: " + ticket.getMovieName());
                System.out.println("Time: " + ticket.getTime());
                System.out.println("Price: " + ticket.getPrice());
                System.out.println();
            }
        }
    }

    public void displayAllMoviesFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM movies";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String genre = resultSet.getString("genre");
                    int ageRestriction = resultSet.getInt("age_restriction");
                    System.out.println("Movie name: " + name);
                    System.out.println("Movie genre: " + genre);
                    System.out.println("Movie age rating: " + ageRestriction);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAllUsersFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String userName = resultSet.getString("username");
                    int userAge = resultSet.getInt("age");
                    double balance = resultSet.getDouble("balance");
                    System.out.println("User name: " + userName);
                    System.out.println("User age: " + userAge);
                    System.out.println("User balance: " + balance);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        System.out.print("Enter user name to delete: ");
        String userName = scanner.next().trim();
        scanner.nextLine();

        System.out.println("Searching for user: " + userName);

        User user = findUserName(userName);

        if (user == null) {
            System.out.println("User not found in users list.");
            return;
        }

        users.remove(user);

        System.out.println("Deleting user from database: " + user.getUserName());

        userRepository.deleteUser(user.getUserName());

        System.out.println("User deleted successfully.");
        System.out.println();
    }
}
