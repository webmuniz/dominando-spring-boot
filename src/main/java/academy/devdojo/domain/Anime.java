package academy.devdojo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Anime {
    private Long id;
    //@JsonProperty("nomeAnime") -> to change the name of the property in the JSON
    private String name;
}
