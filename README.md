# ImdbLike API
## Auteurs

- Kocher Benjamin
- Wulliamoz Nathan

L'API ImdbLike permet de gérer les films et les séries disponibles dans la base de données ImdbLike. Elle utilise le protocole HTTP sur le port `8080`.

Pour plus de détails sur l'API, consultez la [documentation complète](./apidDoc.md).

## Installation

1. Clonez le dépôt :
    ```sh
    git clone git@github.com:nathan-heig/DAI_lab03.git
    cd DAI_lab03
    ```

2. Construisez le projet avec Maven :
    ```sh
    cd code
    ./mvnw clean package
    ```

3. Exécutez l'application :
    ```sh
    java -jar target/ImdbLike-0.0.1-SNAPSHOT.jar
    ```

4. (Optionnel) Construisez et exécutez l'application avec Docker :
    ```sh
    cd ..
    docker build -t imdb-like .
    docker run -p 8080:8080 imdb-like
    ```


# Démo
## Pour les Films

Créer un nouveau film:
```sh
curl -X POST https://webapp.agooddailab.duckdns.org/api/films -H "Content-Type: application/json" -d '{
  "titre": "Inception",
  "date": "2010-07-16",
  "genre": "Science Fiction",
  "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology.",
  "duree": 148
}'
```

Obtenir tous les films:
```sh
curl -X GET https://webapp.agooddailab.duckdns.org/api/films
```
result:
```json
[
  {
    "id": 1,
    "date": "2010-07-16T00:00:00.000+00:00",
    "titre": "Inception",
    "genre": "Science Fiction",
    "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology.",
    "duree": 148
  }
]
```

Obtenir un film par ID:
```sh
curl -X GET https://webapp.agooddailab.duckdns.org/api/films/1
```
result:
```json
{
  "id":1,
  "date":"2010-07-16T00:00:00.000+00:00",
  "titre":"Inception","genre":"Science Fiction",
  "synopsis":"A thief who steals corporate secrets through the use of dream-sharing technology.",
  "duree":148
}
```

Mettre à jour un film:
```sh
curl -X PUT https://webapp.agooddailab.duckdns.org/api/films/1 -H "Content-Type: application/json" -d '{
  "titre": "Inception Updated",
  "date": "2010-07-16",
  "genre": "Science Fiction",
  "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology.",
  "duree": 150
}'
```

Supprimer un film:
```sh
curl -X DELETE https://webapp.agooddailab.duckdns.org/api/films/1
```

## Pour les Séries

Créer une nouvelle série:
```sh
curl -X POST https://webapp.agooddailab.duckdns.org/api/series -H "Content-Type: application/json" -d '{
  "titre": "Breaking Bad",
  "date": "2008-01-20",
  "genre": "Crime, Drama"
}'
```

Obtenir toutes les séries:
```sh
curl -X GET https://webapp.agooddailab.duckdns.org/api/series
```

Obtenir une série par ID:
```sh
curl -X GET https://webapp.agooddailab.duckdns.org/api/series/1
```

Mettre à jour une série:
```sh
curl -X PUT https://webapp.agooddailab.duckdns.org/api/series/1 -H "Content-Type: application/json" -d '{
  "titre": "Breaking Bad Updated",
  "date": "2008-01-20",
  "genre": "Crime, Drama"
}'
```

Supprimer une série:
```sh
curl -X DELETE https://webapp.agooddailab.duckdns.org/api/series/1
```

## Accès à l'API et au Dashboard Traefik

Pour accéder à l'API, utilisez le sous-domaine `webapp.agooddailab.duckdns.org`. Pour accéder au dashboard de Traefik, utilisez `traefik.agooddailab.duckdns.org`.


## Pour deployer le container
```sh
sudo docker compose up -d
```
cette commande pull la dernieres version de la webapp java lance la web app et traefik.
