package ch.bdr.ImdbLike.User;

public class User {
    String pseudo;
    String password;
    String email;

    public User() {
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
