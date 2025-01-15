package ch.bdr.ImdbLike.Media;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@Controller
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;


    @GetMapping("/")
    public String defaultRoute() {
        return "dashboard";
    }

    @GetMapping("/medias")
    public String listMedias(Model model, HttpSession session) {
        model.addAttribute("films", mediaRepository.getMediaIn("film"));
        model.addAttribute("series", mediaRepository.getMediaIn("serie"));
        //a implementer model.addAttribute("topRanked", model.addAttribute("films", mediaRepository.getTopRankedMedia()));
        String username = (String) session.getAttribute("username");
        if (username != null) {
            //model.addAttribute("forYou", mediaRepository.getForYouMedia(username));
        }
        return "medias";
    }
}
