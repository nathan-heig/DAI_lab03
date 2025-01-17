package ch.bdr.ImdbLike.Serie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/series")
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    // Create a new serie
    @PostMapping
    public ResponseEntity<Serie> createSerie(@RequestBody Serie serie) {
        try {
            Serie savedSerie = serieRepository.save(serie);
            return new ResponseEntity<>(savedSerie, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all series
    @GetMapping
    public ResponseEntity<List<Serie>> getAllSeries() {
        try {
            List<Serie> series = serieRepository.findAll();
            if (series.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(series, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a single serie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSerieById(@PathVariable int id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            return new ResponseEntity<>(serie.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a serie
    @PutMapping("/{id}")
    public ResponseEntity<Serie> updateSerie(@PathVariable int id, @RequestBody Serie serieDetails) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie updatedSerie = serie.get();
            updatedSerie.setTitre(serieDetails.getTitre());
            updatedSerie.setDate(serieDetails.getDate());
            updatedSerie.setGenre(serieDetails.getGenre());
            return new ResponseEntity<>(serieRepository.save(updatedSerie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a serie
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSerie(@PathVariable int id) {
        try {
            Optional<Serie> serie = serieRepository.findById(id);
            if (serie.isPresent()) {
                serieRepository.delete(serie.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}