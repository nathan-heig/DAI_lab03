package ch.bdr.ImdbLike.Media;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

import java.util.Base64;

@Controller
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/medias")
    public String listMedias(Model model) {
        model.addAttribute("medias", mediaService.getAllMedias());
        model.addAttribute("name","media");
        return "medias";
    }

    @GetMapping("/media/{id}")
    public String getFilm(Model model, @PathVariable int id) {
        Media media = mediaService.getMedia(id);
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
