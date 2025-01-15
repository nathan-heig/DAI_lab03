package ch.bdr.ImdbLike.Film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;

@Controller
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;


    @GetMapping("/films")
    public String listFilms(Model model) {
        model.addAttribute("medias", filmRepository.getMediaIn("film"));
        String test = "film";
        model.addAttribute("name",test);
        return "medias";
    }

    @GetMapping("/film/{id}")
    public String getFilm(Model model, @PathVariable int id) {
        Film film = filmRepository.findFilmById(id).orElse(null);
        if (film == null) {
            model.addAttribute("errorMessage", "Film not found");
            return "error";
        }
        model.addAttribute("film", film);
        return "film";
    }

    public String encodeToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}
