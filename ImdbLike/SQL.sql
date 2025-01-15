CREATE TYPE GENRE AS ENUM ('science-fiction', 'action', 'drame', 'comedie', 'romance', 'thriller', 'fantastique');
 

-- Table User 
CREATE TABLE Utilisateur ( 
    pseudo VARCHAR(50) PRIMARY KEY, 
    mail VARCHAR(100) NOT NULL UNIQUE, 
    mdp VARCHAR(255) NOT NULL 
); 

-- Table Personne 
CREATE TABLE Personne ( 
    id SERIAL PRIMARY KEY, 
    nom VARCHAR(100) NOT NULL, 
    prenom VARCHAR(100) NOT NULL, 
    date_de_naissance DATE NOT NULL 
); 

-- Table Saga 
CREATE TABLE Saga ( 
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL, 
    affiche TEXT 
); 
  
-- Table Media 
CREATE TABLE Media ( 
    id SERIAL PRIMARY KEY, 
    date DATE NOT NULL, 
    titre VARCHAR(255) NOT NULL, 
    genre GENRE NOT NULL, 
    affiche TEXT,
    idSaga INT, 
    FOREIGN KEY (idSaga) REFERENCES Saga(id) ON DELETE SET NULL 
); 
  
-- Table Film 
CREATE TABLE Film ( 
    idMedia INT PRIMARY KEY, 
    synopsis TEXT, 
    duree INT, -- en minutes  
    FOREIGN KEY (idMedia) REFERENCES Media(id) 
); 
-- Vue Film
CREATE VIEW film_view AS
SELECT m.id, m.date, m.titre, m.genre, m.affiche, f.synopsis, f.duree
FROM Film f
JOIN Media m ON f.idMedia = m.id;

-- Table Serie 
CREATE TABLE Serie ( 
    idMedia INT PRIMARY KEY, 
    FOREIGN KEY (idMedia) REFERENCES Media(id) 
); 
-- Vue Serie
CREATE VIEW serie_view AS
SELECT m.id, m.date, m.titre, m.genre, m.affiche
FROM Serie s
JOIN Media m ON s.idMedia = m.id;
  
-- Table Saison 
CREATE TABLE Saison ( 
    idSerie INT NOT NULL, 
    numero INT NOT NULL, 
    affiche TEXT, 
    date DATE, 
    PRIMARY KEY (idSerie, numero), 
    FOREIGN KEY (idSerie) REFERENCES Serie(idMedia)
    ON DELETE CASCADE
    ON UPDATE CASCADE
); 

-- Table Episode 
CREATE TABLE Episode ( 
    idSerie INT NOT NULL, 
    numeroSaison INT NOT NULL,
    numero INT NOT NULL, 
    synopsis TEXT, 
    duree INT, -- en minutes
    PRIMARY KEY (idSerie, numeroSaison, numero), 
    FOREIGN KEY (idSerie, numeroSaison) REFERENCES Saison(idSerie, numero)
    ON DELETE CASCADE
    ON UPDATE CASCADE
); 

-- Table Avis 
CREATE TABLE Avis ( 
    id SERIAL PRIMARY KEY, 
    commentaire TEXT, 
    note FLOAT NOT NULL, 
    pseudoUtilisateur VARCHAR(50) NOT NULL, 
    idMedia INT NOT NULL, 
    FOREIGN KEY (pseudoUtilisateur) REFERENCES Utilisateur(pseudo)
    ON DELETE SET NULL -- Si l'utilisateur est supprimé, on ne supprime pas son avis
    ON UPDATE CASCADE, 
    FOREIGN KEY (idMedia) REFERENCES Media(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
); 

-- Table Liste 
CREATE TABLE Liste ( 
    id SERIAL PRIMARY KEY, 
    nom VARCHAR(100) NOT NULL, 
    pseudoUtilisateur VARCHAR(50) NOT NULL, 
    FOREIGN KEY (pseudoUtilisateur) REFERENCES Utilisateur(pseudo) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
); 

-- Table Liste_Media 
CREATE TABLE Liste_Media ( 
    idListe INT NOT NULL, 
    idMedia INT NOT NULL, 
    PRIMARY KEY (idListe, idMedia), 
    FOREIGN KEY (idListe) REFERENCES Liste(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (idMedia) REFERENCES Media(id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
); 

-- Table JoueDans qui lie une/des personne(s) à un média
CREATE TABLE JoueDans ( 
    idPersonne INT NOT NULL, 
    idMedia INT NOT NULL, 
    PRIMARY KEY (idPersonne, idMedia), 
    FOREIGN KEY (idPersonne) REFERENCES Personne(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE, 
    FOREIGN KEY (idMedia) REFERENCES Media(id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
); 

-- Table ARealise qui lie une/des personne(s) à un média
CREATE TABLE ARealise ( 
    idPersonne INT NOT NULL, 
    idMedia INT NOT NULL, 
    PRIMARY KEY (idPersonne, idMedia), 
    FOREIGN KEY (idPersonne) REFERENCES Personne(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE, 
    FOREIGN KEY (idMedia) REFERENCES Media(id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
);