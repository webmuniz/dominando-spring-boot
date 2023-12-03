package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Anime {
    private Long id;
    //@JsonProperty("nomeAnime") -> to change the name of the property in the JSON
    private String name;
    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        var anime1 = Anime.builder().id(1L).name("Boku no Hero").build();
        var anime2 = Anime.builder().id(2L).name("Shingeki no Kyojin").build();
        var anime3 = Anime.builder().id(3L).name("Naruto").build();

        animes.addAll(List.of(anime1, anime2, anime3));
    }
}
