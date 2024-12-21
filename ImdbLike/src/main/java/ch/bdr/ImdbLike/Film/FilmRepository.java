package ch.bdr.ImdbLike.Film;

import ch.bdr.ImdbLike.Media.MediaRepository;
import ch.bdr.ImdbLike.Utils.Genre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class FilmRepository extends MediaRepository{


    public Optional<List<Film>> findFilmByGenre(Genre genre) {
        List<Film> films = new ArrayList<>();
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

    public Optional<Film> findFilmById(int id) {
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

    public List<Film> findAllForListFilm() {
        List<Film> films = new ArrayList<>();
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

    public Film addFilm(Film film) {
        String sqlFilm = "INSERT INTO film (idMedia, synopsis, duree) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false); // Commencer une transaction
            
            try {
                int mediaId = super.addMedia(film);
                
                try (PreparedStatement stmtFilm = conn.prepareStatement(sqlFilm)) {
                    stmtFilm.setInt(1, mediaId);
                    stmtFilm.setString(2, film.getSynopsis());
                    stmtFilm.setInt(3, film.getDuree());
                    int affectedRowsFilm = stmtFilm.executeUpdate();
                    
                    if (affectedRowsFilm == 0) {
                        throw new SQLException("Creating film failed, no rows affected.");
                    }
                    
                    conn.commit(); // Valider la transaction
                    film.setId(mediaId); // Définir l'ID du film
                    return film;
                }
            } catch (SQLException e) {
                conn.rollback(); // Annuler la transaction en cas d'erreur
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Film mapResultSetToFilm(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setTitle(rs.getString("titre"));
        film.setSynopsis(rs.getString("synopsis"));
        film.setGenre(Genre.valueOf(rs.getString("genre")));
        film.setDate(rs.getDate("date"));
        film.setAffiche(rs.getBytes("affiche"));
        film.setDuree(rs.getInt("duree"));
        return film;
    }

}