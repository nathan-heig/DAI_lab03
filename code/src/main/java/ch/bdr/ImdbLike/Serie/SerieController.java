package ch.bdr.ImdbLike.Serie;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Serie createSerie(@RequestBody Serie serie) {
        return serieRepository.save(serie);
    }

    // Get all series
    @GetMapping
    public List<Serie> getAllSeries() {
        return serieRepository.findAll();
    }

    // Get a single serie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSerieById(@PathVariable int id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            return ResponseEntity.ok(serie.get());
        } else {
            return ResponseEntity.notFound().build();
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
            serieRepository.save(updatedSerie);
            return ResponseEntity.ok(updatedSerie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a serie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable int id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            serieRepository.delete(serie.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
