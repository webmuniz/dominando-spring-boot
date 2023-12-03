package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@Log4j2
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name){

        log.info("Request received to list all animes, param name: {}", name);

        List<Anime> animes = Anime.getAnimes();

        List<AnimeGetResponse> animeGetResponseList = MAPPER.toAnimeGetResponseList(animes);

        if (name == null) {
            return ResponseEntity.ok(animeGetResponseList);
        }

        List<AnimeGetResponse> list = animeGetResponseList.stream()
                .filter(animeGetResponse -> animeGetResponse.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(list);

    }

    @GetMapping(path = "{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id){
        log.info("Request received to find anime by id: {}", id);

        Anime animeFound = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        AnimeGetResponse animeGetResponse = MAPPER.toAnimeGetResponse(animeFound);

        return ResponseEntity.ok(animeGetResponse);
    }

    /**
    POST is NOT Idempotent: Which does NOT have the property of being able to be applied more than once whithout the result changing.
    Every time you call the POST method, a new resource is created.
    PUT is Idempotent: Which has the property of being able to be applied more than once whithout the result changing.
    **/
    @PostMapping()
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest){

        log.info("Request received to save anime: {}", animePostRequest);

        Anime anime = MAPPER.toAnime(animePostRequest);
        AnimePostResponse animePostResponse = MAPPER.toAnimePostResponse(anime);
        Anime.getAnimes().add(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(animePostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Request received to delete anime with id: {}", id);

        Anime anime = Anime.getAnimes()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found to be deleted"));

        Anime.getAnimes().remove(anime);
        return ResponseEntity.noContent().build();
    }
}
