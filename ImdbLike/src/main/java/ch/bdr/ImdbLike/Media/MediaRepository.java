package ch.bdr.ImdbLike.Media;

import ch.bdr.ImdbLike.Film.Film;
import ch.bdr.ImdbLike.Utils.Genre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class MediaRepository {
    @Value("${spring.datasource.url}")
    protected String url;
    
    @Value("${spring.datasource.username}")
    protected String username;
    
    @Value("${spring.datasource.password}")
    protected String password;

    public Optional<List<Media>> findByGenre(Genre genre) {
        List<Media> films = new ArrayList<>();
        String sql = "SELECT * FROM film_view WHERE film_view.genre = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, genre.toString());
            System.out.println(stmt);
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

    public Optional<Media> findById(int id) {
        String sql = "SELECT * FROM film_view WHERE film_view.id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            System.out.println(stmt);
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

    public List<Media> getAllMediaForList() {
        List<Media> films = new ArrayList<>();
        String sql = "SELECT * FROM film_view";
        System.out.println(sql);
        
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

    public int addMedia(Film film) throws SQLException {
        String sqlMedia = "INSERT INTO media (titre, date, affiche) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmtMedia = conn.prepareStatement(sqlMedia, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtMedia.setString(1, film.getTitre());
            stmtMedia.setDate(2, new java.sql.Date(film.getDate().getTime()));
            stmtMedia.setBytes(3, film.getAffiche());
            int affectedRowsMedia = stmtMedia.executeUpdate();
            
            if (affectedRowsMedia == 0) {
                throw new SQLException("Creating media failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmtMedia.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating media failed, no ID obtained.");
                }
            }
        }
    
    }

    private Media mapResultSetToFilm(ResultSet rs) throws SQLException {
        Media media = new Film();
        media.setId(rs.getInt("id"));
        media.setTitle(rs.getString("titre"));
        media.setGenre(Genre.valueOf(rs.getString("genre")));
        media.setDate(rs.getDate("date"));
        media.setAffiche(rs.getBytes("affiche"));
        return media;
    }

}