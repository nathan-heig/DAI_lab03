package ch.bdr.ImdbLike.Film;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.bdr.ImdbLike.Utils.Genre;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.Base64;

@Controller
public class FilmController {

    @Autowired
    private FilmService filmService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/films")
    public String listFilms(Model model) {
        model.addAttribute("medias", filmService.getAllFilms());
        String test = "film";
        model.addAttribute("name",test);
        return "medias";
    }

    @GetMapping("/film/add")
    public String showAddFilmForm(Model model) {
        Set<Genre> genres = EnumSet.allOf(Genre.class);
        model.addAttribute("genres", genres);
        return "addFilm";
    }

    @PostMapping("/film")
    public String addFilm(@RequestParam("title") String title,
                          @RequestParam("releaseDate") Date releaseDate,
                          @RequestParam("synopsis") String synopsis,
                          @RequestParam("duree") Double duree,
                          @RequestParam("genre") Genre genre,
                          @RequestParam("poster") MultipartFile posterFile) throws IOException {
        byte[] poster = posterFile.getBytes();
        Film film = new Film(title, releaseDate, poster, synopsis, duree, genre);
        filmService.addFilm(film);
        return "redirect:/films";
    }

    @GetMapping("/film/{id}")
    public String getFilm(Model model, @PathVariable int id) {
        Film film = filmService.getFilm(id);
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
