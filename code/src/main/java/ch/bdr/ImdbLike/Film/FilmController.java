package ch.bdr.ImdbLike.Film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    // Create a new film
    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        try {
            Film savedFilm = filmRepository.save(film);
            return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all films
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        try {
            List<Film> films = filmRepository.findAll();
            if (films.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(films, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a single film by ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            return new ResponseEntity<>(film.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a film
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            Film updatedFilm = film.get();
            updatedFilm.setTitre(filmDetails.getTitre());
            updatedFilm.setDate(filmDetails.getDate());
            updatedFilm.setGenre(filmDetails.getGenre());
            updatedFilm.setSynopsis(filmDetails.getSynopsis());
            updatedFilm.setDuree(filmDetails.getDuree());
            return new ResponseEntity<>(filmRepository.save(updatedFilm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a film
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable Long id) {
        try {
            Optional<Film> film = filmRepository.findById(id);
            if (film.isPresent()) {
                filmRepository.delete(film.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}