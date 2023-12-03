package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Anime {
    private Long id;
    //@JsonProperty("nomeAnime") -> to change the name of the property in the JSON
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    public static List<Anime> getAnimes() {
        animes.addAll(List.of(new Anime(1L, "Boku no Hero"),
                        new Anime(2L, "Berserk"),
                        new Anime(3L, "Death Note"),
                        new Anime(4L, "Naruto")));
        return animes;
    }
}
