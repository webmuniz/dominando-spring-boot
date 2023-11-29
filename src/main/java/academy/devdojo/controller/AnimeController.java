package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    @GetMapping
    public List<Anime> list(@RequestParam(required = false) String name){
        List<Anime> animes = Anime.getAnimes();
        if (name != null && !name.isEmpty()) {
            return animes.stream().filter(anime -> anime.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }
        return animes;
    }

    @GetMapping(path = "{id}")
    public Anime getById(@PathVariable Long id){
        return Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
