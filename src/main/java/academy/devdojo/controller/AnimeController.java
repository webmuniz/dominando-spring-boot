package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    private final AnimeMapper mapper;

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name){

        log.info("Request received to list all animes, param name: {}", name);

        List<Anime> animes = animeService.findAll(name);

        List<AnimeGetResponse> animeGetResponseList = mapper.toAnimeGetResponseList(animes);

        return ResponseEntity.ok(animeGetResponseList);

    }

    @GetMapping(path = "{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id){
        log.info("Request received to find anime by id: {}", id);

        Anime animeFound = animeService.findById(id);

        AnimeGetResponse animeGetResponse = mapper.toAnimeGetResponse(animeFound);

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

        Anime anime = mapper.toAnime(animePostRequest);

        animeService.save(anime);

        AnimePostResponse animePostResponse = mapper.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(animePostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Request received to delete anime with id: {}", id);

        animeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update (@RequestBody AnimePutRequest animePutRequest){

        log.info("Request received to update anime with id: {}", animePutRequest.getId());

        Anime animeUpdated = mapper.toAnime(animePutRequest);

       animeService.update(animeUpdated);

        return ResponseEntity.noContent().build();
    }
}
