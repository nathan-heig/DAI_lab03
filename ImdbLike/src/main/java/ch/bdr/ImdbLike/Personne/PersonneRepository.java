package ch.bdr.ImdbLike.Personne;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonneRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public Optional<Personne> findById(int id) {
        String sql = "SELECT * FROM personne WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToPersonne(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Personne> findAll() {
        List<Personne> personnes = new ArrayList<>();
        String sql = "SELECT personne.nom, personne.prenom, personne.id FROM personne";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Personne personne = new Personne();
                personne.setId(rs.getInt("id"));
                personne.setNom(rs.getString("nom"));
                personne.setPrenom(rs.getString("prenom"));
                personnes.add(personne);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnes;
    }

    public Personne addPersonne(Personne personne) {
        String sql = "INSERT INTO personne (nom, prenom, dateNaissance) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, personne.getNom());
            stmt.setString(2, personne.getPrenom());
            stmt.setDate(3, new java.sql.Date(personne.getDateNaissance().getTime()));
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating personne failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    personne.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating personne failed, no ID obtained.");
                }
            }
            return personne;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletePersonne(int id) {
        String sql = "DELETE FROM personne WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Personne mapResultSetToPersonne(ResultSet rs) throws SQLException {
        Personne personne = new Personne();
        personne.setId(rs.getInt("id"));
        personne.setNom(rs.getString("nom"));
        personne.setPrenom(rs.getString("prenom"));
        personne.setDateNaissance(rs.getDate("dateNaissance"));
        return personne;
    }
}
