package ch.bdr.ImdbLike.Film;

import jakarta.persistence.*;
import ch.bdr.ImdbLike.Media.Media;
import java.util.Date;
import ch.bdr.ImdbLike.Utils.Genre;

@Entity
public class Film extends Media {

    protected String synopsis;
    protected Integer duree;

    @Enumerated(EnumType.STRING)
    protected Genre genre;

    public Film() {}

    public Film(String titre, Date date, byte[] affiche, String synopsis, Integer duree, Genre genre) {
        super(titre, date, affiche);
        this.synopsis = synopsis;
        this.duree = duree;
        this.genre = genre;
    }

    // Getters et setters 
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
