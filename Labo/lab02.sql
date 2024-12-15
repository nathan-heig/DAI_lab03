-- Ajouter les autres tables sans références croisées
CREATE TABLE IF NOT EXISTS Fabricant (
    nom varchar(255) PRIMARY KEY
);
 
CREATE TABLE IF NOT EXISTS Genre (
    nom varchar(255) PRIMARY KEY
);
 
CREATE TABLE IF NOT EXISTS Editeur (
    id SERIAL PRIMARY KEY,
    nom varchar(255) UNIQUE,
    siegeSocial varchar(255)
);
 
-- Créer d'abord les tables de base
CREATE TABLE IF NOT EXISTS Client (
    pseudo varchar(80) PRIMARY KEY,
    dateNaissance date,
    adresseFacturation varchar(255),
    email varchar(255) UNIQUE
);
 
CREATE TABLE IF NOT EXISTS Article (
    id Serial PRIMARY KEY,
    nom varchar(255),
    description text,
    dateSortie date,
    prix decimal(10, 2),
    note decimal(3, 2),
    CONSTRAINT check_dateSortie_prix_null CHECK (
        (dateSortie IS NULL AND prix IS NULL) OR (dateSortie IS NOT NULL AND prix IS NOT NULL)
    ),
    CONSTRAINT check_note_after_dateSortie CHECK (
        note IS NULL or dateSortie < CURRENT_DATE
    )
);
 
CREATE TABLE IF NOT EXISTS Console (
    nom varchar(255) PRIMARY KEY,
    anneeParution int,
    nomFabricant varchar(255),                              -- Clé étrangère pour lier à un fabricant
    FOREIGN KEY (nomFabricant)
        REFERENCES Fabricant(nom)    -- Définition de la relation
        ON DELETE CASCADE
);
 
-- Tables héritées et associatives avec des clés étrangères
CREATE TABLE IF NOT EXISTS JeuVideo (
    idArticle int PRIMARY KEY REFERENCES Article(id),
    ageMinimum int,
    idEditeur int,                              -- Clé étrangère pour lier à un editeur
    FOREIGN KEY (idEditeur) REFERENCES Editeur(id)
);
 
CREATE TABLE IF NOT EXISTS DLC (
    idArticle int PRIMARY KEY REFERENCES Article(id),
    necessiteJeuDeBase boolean,
    idJeuVideo int,
    FOREIGN KEY (idJeuVideo) REFERENCES JeuVideo(idArticle)
);
 
-- Tables associatives pour les relations
CREATE TABLE IF NOT EXISTS Article_Console (
    idArticle int REFERENCES Article(id),
    nomConsole varchar(255) REFERENCES Console(nom),
    PRIMARY KEY (idArticle, nomConsole)
);
 
CREATE TABLE IF NOT EXISTS Client_Article (
    pseudoClient varchar(80) REFERENCES Client(pseudo),
    idArticle int REFERENCES Article(id),
    dateAchat date,
    PRIMARY KEY (pseudoClient, idArticle)
);
 
CREATE TABLE IF NOT EXISTS JeuVideo_Genre (
    idJeuVideo int REFERENCES JeuVideo(idArticle),
    nomGenre varchar(255) REFERENCES Genre(nom),
    PRIMARY KEY (idJeuVideo, nomGenre)
);