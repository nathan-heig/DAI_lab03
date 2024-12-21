package ch.bdr.ImdbLike.Media;

import jakarta.persistence.*;
import java.util.Date;
import ch.bdr.ImdbLike.Utils.Genre;;

@Entity
public class Media {
    
    protected int id;

    protected Date date;
    protected String titre;
    protected Genre genre;

    protected byte[] affiche;


    public Media() {}

    public Media(String title, Date date, byte[] affiche) {
        this.titre = title;
        this.date = date;
        this.affiche = affiche;
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

    public byte[] getAffiche() {
        return affiche;
    }

    public void setAffiche(byte[] affiche) {
        this.affiche = affiche;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

}

