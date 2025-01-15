package ch.bdr.ImdbLike.Personne;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Personne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected String nom;
    protected String prenom;
    protected Date dateNaissance;
    
    public Personne() {}

    public Personne(String nom, String prenom, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
