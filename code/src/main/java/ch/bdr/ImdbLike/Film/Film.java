package ch.bdr.ImdbLike.Film;

import jakarta.persistence.*;
import ch.bdr.ImdbLike.Media.Media;

@Entity
public class Film extends Media {

    protected String synopsis;
    protected Integer duree;

    public Film() {}

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

}
