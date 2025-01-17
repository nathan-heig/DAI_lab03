Pour les Films

Créer un nouveau film
curl -X POST http://localhost:8080/api/films -H "Content-Type: application/json" -d '{
  "titre": "Inception",
  "date": "2010-07-16",
  "genre": "Science Fiction",
  "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology.",
  "duree": 148
}'


Obtenir tous les films
curl -X GET http://localhost:8080/api/films

Obtenir un film par ID
curl -X GET http://localhost:8080/api/films/1

Mettre a jour un film
curl -X PUT http://localhost:8080/api/films/1 -H "Content-Type: application/json" -d '{
  "titre": "Inception Updated",
  "date": "2010-07-16",
  "genre": "Science Fiction",
  "synopsis": "A thief who steals corporate secrets through the use of dream-sharing technology.",
  "duree": 150
}'

Supprimer un film
curl -X DELETE http://localhost:8080/api/films/1

Pour les series
curl -X POST http://localhost:8080/api/series -H "Content-Type: application/json" -d '{
  "titre": "Breaking Bad",
  "date": "2008-01-20",
  "genre": "Crime, Drama"
}'
curl -X GET http://localhost:8080/api/series
curl -X GET http://localhost:8080/api/series/1
curl -X PUT http://localhost:8080/api/series/1 -H "Content-Type: application/json" -d '{
  "titre": "Breaking Bad Updated",
  "date": "2008-01-20",
  "genre": "Crime, Drama"
}'
curl -X DELETE http://localhost:8080/api/series/1