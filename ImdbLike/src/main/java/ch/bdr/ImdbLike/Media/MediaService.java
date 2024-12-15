package ch.bdr.ImdbLike.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public List<Media> getAllMedias() {
        return mediaRepository.findAll();
    }

    public Media getMedia(int id) {
        return mediaRepository.findById(id).orElse(null);
    }

    public void addMedia(Media media) {
        mediaRepository.save(media);
    }
}