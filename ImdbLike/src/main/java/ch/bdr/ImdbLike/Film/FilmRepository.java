package ch.bdr.ImdbLike.Film;

import ch.bdr.ImdbLike.Utils.Genre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class FilmRepository {
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;

    public Optional<List<Film>> findByGenre(Genre genre) {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT f.id, m.titre, f.synopsis, f.genre, m.date, m.affiche " +
                     "FROM film f " +
                     "JOIN media m ON f.id = m.id " +
                     "WHERE f.genre = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, genre.toString());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                films.add(mapResultSetToFilm(rs));
            }
            return Optional.of(films);
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Film> findById(int id) {
        String sql = "SELECT f.id, f.titre, m.synopsis, m.genre, m.date, m.affiche " +
                     "FROM film f " +
                     "JOIN media m ON f.id = m.id " +
                     "WHERE f.id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToFilm(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT f.id, m.titre, m.synopsis, f.genre, m.date, m.affiche " +
                     "FROM film f " +
                     "JOIN media m ON f.id = m.id";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                films.add(mapResultSetToFilm(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    private Film mapResultSetToFilm(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setTitle(rs.getString("title"));
        film.setSynopsis(rs.getString("synopsis"));
        film.setGenre(Genre.valueOf(rs.getString("genre")));
        film.setDate(rs.getDate("date"));
        film.setAffiche(rs.getBytes("affiche"));
        return film;
    }
}