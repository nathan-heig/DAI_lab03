package ch.bdr.ImdbLike.Film;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Film createFilm(@RequestBody Film film) {
        return filmRepository.save(film);
    }

    // Get all films
    @GetMapping
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // Get a single film by ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            return ResponseEntity.ok(film.get());
        } else {
            return ResponseEntity.notFound().build();
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
            filmRepository.save(updatedFilm);
            return ResponseEntity.ok(updatedFilm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a film
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            filmRepository.delete(film.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}