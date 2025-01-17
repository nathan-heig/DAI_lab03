package ch.bdr.ImdbLike.Media;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Media {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected Date date;
    protected String titre;
    protected String genre;



    public Media() {}


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

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

}

