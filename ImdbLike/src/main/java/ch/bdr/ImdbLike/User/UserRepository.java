package ch.bdr.ImdbLike.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public Optional<Utilisateur> findByPseudo(String pseudo) {
        String sql = "SELECT * FROM utilisateur WHERE pseudo = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pseudo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Utilisateur addUser(Utilisateur user) {
        String sql = "INSERT INTO Utilisateur (pseudo, mdp, mail) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getPseudo());
            stmt.setString(2, user.getMdp());
            stmt.setString(3, user.getMail());
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return user;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Utilisateur mapResultSetToUser(ResultSet rs) throws SQLException {
        Utilisateur user = new Utilisateur();
        user.setPseudo(rs.getString("pseudo"));
        user.setMdp(rs.getString("password"));
        user.setMail(rs.getString("mail"));
        return user;
    }
}