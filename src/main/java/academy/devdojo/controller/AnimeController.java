package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    @GetMapping
    public List<Anime> list(@RequestParam(required = false) String name){

        log.info("Request received to list all animes, param name: {}", name);
        List<Anime> animes = Anime.getAnimes();
        if (name != null && !name.isEmpty()) {
            return animes.stream().filter(anime -> anime.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }
        return animes;
    }

    @GetMapping(path = "{id}")
    public Anime findById(@PathVariable Long id){
        log.info("Request received to find anime by id: {}", id);

        return Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
