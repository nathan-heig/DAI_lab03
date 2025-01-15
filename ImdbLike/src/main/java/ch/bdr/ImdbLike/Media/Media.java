package ch.bdr.ImdbLike.Media;

import jakarta.persistence.*;
import java.util.Date;
import ch.bdr.ImdbLike.Utils.Genre;;

@Entity
public class Media {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected Date date;
    protected String titre;
    protected Genre genre;

    protected String affichePath;
    protected int idSaga;


    public Media() {}

    public Media(String title, Date date, String affichePath) {
        this.titre = title;
        this.date = date;
        this.affichePath = affichePath;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitle(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAffichePath() {
        return affichePath;
    }

    public void setAffichePath(String affichePath) {
        this.affichePath = affichePath;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

}

