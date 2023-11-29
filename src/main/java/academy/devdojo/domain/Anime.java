package academy.devdojo.domain;

import lombok.Data;

import java.util.List;

@Data
public class Anime {
    private Long id;
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> getAnimes() {
        return List.of(new Anime(1L, "Boku no Hero"),
                        new Anime(2L, "Berserk"),
                        new Anime(3L, "Death Note"),
                        new Anime(4L, "Naruto"));
    }
}
