package repositories;

import entities.Movie;

import java.sql.*;

public class MovieRepository {

    public void addMovieToDatabase(Movie movie) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO movies (name, genre, age_restriction) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, movie.getName());
                statement.setString(2, movie.getGenre());
                statement.setInt(3, movie.getAgeRestriction());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

















