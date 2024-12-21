package ch.bdr.ImdbLike.Media;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Base64;

@Controller
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;


    @GetMapping("/")
    public String defaultRoute() {
        return "dashboard";
    }

    @GetMapping("/medias")
    public String listMedias(Model model) {
        model.addAttribute("medias", mediaRepository.getAllMediaForList());
        model.addAttribute("name","media");
        return "medias";
    }

    @GetMapping("/media/{id}")
    public String getFilm(Model model, @PathVariable int id) {
        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) {
            model.addAttribute("errorMessage", "Film not found");
            return "error";
        }
        model.addAttribute("media", media);
        return "media";
    }

    public String encodeToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}
