package ch.bdr.ImdbLike.Media;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    //selectionne les media qui sont dans une certaine table
    public List<Media> getMediaIn(String tableName){
        List<Media> films = new ArrayList<>();
        String sql = "SELECT media.titre, media.affiche FROM " + tableName + " INNER JOIN media ON media.id = " + tableName + ".idMedia";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Media media = new Media();
                media.setTitle(rs.getString("titre"));
                media.setAffichePath(rs.getString("affiche"));
                films.add(media);
            }
            return films;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addMedia(Media film) throws SQLException {
        String sqlMedia = "INSERT INTO media (titre, date, affiche) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmtMedia = conn.prepareStatement(sqlMedia, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtMedia.setString(1, film.getTitre());
            stmtMedia.setDate(2, new java.sql.Date(film.getDate().getTime()));
            stmtMedia.setString(3, film.getAffichePath());
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

}