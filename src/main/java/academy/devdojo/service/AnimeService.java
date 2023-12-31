package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import academy.devdojo.repository.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodedRepository repository;

    public List<Anime> findAll(String name) {
        return repository.findByName(name);
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public Anime findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found to be deleted"));
    }

    public void delete(Long id) {
        Anime anime = findById(id);
        repository.delete(anime);
    }

    public void update(Anime animeToUpdate) {
        Anime anime = findById(animeToUpdate.getId());
        anime.setName(animeToUpdate.getName());
        repository.update(anime);
    }

}
