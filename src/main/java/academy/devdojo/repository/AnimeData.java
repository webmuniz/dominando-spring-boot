package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class AnimeData {


    private final List<Anime> animes = new ArrayList<>();

    {
        var anime1 = Anime.builder().id(1L).name("Boku no Hero").build();
        var anime2 = Anime.builder().id(2L).name("Shingeki no Kyojin").build();
        var anime3 = Anime.builder().id(3L).name("Naruto").build();

        animes.addAll(List.of(anime1, anime2, anime3));
    }

}
