import controllers.CinemaSystem;
import repositories.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private CinemaSystem movie = new CinemaSystem();

    void displayMenu() {
        System.out.println("Cinema Ticket System Menu: ");
        System.out.println("Press 1 to add a new movie.");
        System.out.println("Press 2 to show all available movies.");
        System.out.println("Press 3 to add a new user.");
        System.out.println("Press 4 to show all users.");
        System.out.println("Press 5 to delete a user.");
        System.out.println("Press 6 to buy a ticket.");
        System.out.println("Press 7 to exit.");
    }

    public int getUserInput() {
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }

    void startProgram() {
        while (true) {
            displayMenu();
            int userChoice = getUserInput();

            switch (userChoice) {
                case 1:
                    movie.addNewMovie();
                    break;
//                case 2:
//                    movie.displayAllMovies();
//                    break;
                case 2:
                    movie.displayAllMoviesFromDatabase();
                    break;
                case 3:
                    movie.addNewUser();
                    break;
                case 4:
                    movie.displayAllUsersFromDatabase();
                    break;
                case 5:
                    movie.deleteUser();
                    break;
                case 6:
                    movie.buyTicket();
                    break;
                case 7:
                    scanner.close();
                    return;
                default:
                    System.out.println("Please enter a valid number.");
            }
        }
    }

    private void createMoviesTable() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS movies (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "genre VARCHAR(255) NOT NULL," +
                    "age_restriction INT NOT NULL)";
            statement.executeUpdate(sql);
            System.out.println("Movies table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUsersTable() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY," +
                    "username VARCHAR(255) NOT NULL," +
                    "age INT NOT NULL," +
                    "balance DOUBLE PRECISION NOT NULL)";
            statement.executeUpdate(sql);
            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Main main = new Main();

        main.createMoviesTable();
        main.createUsersTable();

        try {
            Connection connection = DatabaseConnection.getConnection();

            main.startProgram();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}