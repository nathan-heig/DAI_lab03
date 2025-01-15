package ch.bdr.ImdbLike.User;

public class Utilisateur {
    String pseudo;
    String mdp;
    String mail;

    public Utilisateur() {
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setMdp(String password) {
        this.mdp = password;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMail(String email) {
        this.mail = email;
    }
    public String getMail() {
        return mail;
    }
}
