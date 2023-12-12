package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository animeHardCodedRepository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        animes = new ArrayList<>(List.of(
                Anime.builder().id(1L).name("Death Note").build(),
                Anime.builder().id(2L).name("Pokemon").build(),
                Anime.builder().id(3L).name("Berserk").build()
        ));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }


    @Test
    @DisplayName("findAll returns list of animes when successful")
    @Order(1)
    void findAll_ReturnsListOfAnimes_WhenSuccessful() {
        List<Anime> animes = animeHardCodedRepository.findAll();
        Assertions.assertThat(animes).isNotEmpty().hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("findById returns an optional anime when successful")
    @Order(2)
    void findById_ReturnsAnOptionalAnime_WhenSuccessful() {
        Anime animeFound = animeHardCodedRepository.findById(1L).get();
        Assertions.assertThat(animeFound).isNotNull().isEqualTo(animes.get(0));
    }

    @Test
    @DisplayName("findById returns an empty optional anime when anime is not found")
    @Order(3)
    void findById_ReturnsAnEmptyOptionalAnime_WhenAnimeIsNotFound() {
        Anime animeFound = animeHardCodedRepository.findById(4L).orElse(null);
        Assertions.assertThat(animeFound).isNull();
    }

    @Test
    @DisplayName("findByName returns a list of animes when successful")
    @Order(4)
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        List<Anime> animesFound = animeHardCodedRepository.findByName("Pokemon");
        Assertions.assertThat(animesFound).isNotEmpty().hasSameElementsAs(List.of(animes.get(1)));
    }

    @Test
    @DisplayName("findByName returns an empty list of animes when anime is not found")
    @Order(5)
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        List<Anime> animesFound = animeHardCodedRepository.findByName("Naruto");
        Assertions.assertThat(animesFound).isEmpty();
    }

    @Test
    @DisplayName("save creates an anime when successful")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() {

        Anime animeToBeSaved = Anime.builder().id(4L).name("Tenshu").build();

        Anime animeSaved = animeHardCodedRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();

        List<Anime> animes = animeHardCodedRepository.findAll();
        Assertions.assertThat(animes).contains(animeToBeSaved);
    }

    @Test
    @DisplayName("delete removes the anime when successful")
    @Order(7)
    void delete_RemovesAnime_WhenSuccessful() {
        Anime animeToBeDeleted = Anime.builder().id(1L).name("Death Note").build();
        animeHardCodedRepository.delete(animeToBeDeleted);
        Assertions.assertThat(animeHardCodedRepository.findAll()).doesNotContain(animeToBeDeleted);
    }

    @Test
    @DisplayName("update save updated anime when successful")
    @Order(8)
    void update_UpdateAnime_WhenSuccessfull(){
        Anime animeToBeUpdated = animes.get(0);
        animeToBeUpdated.setName("Death Note 2");
        animeHardCodedRepository.update(animeToBeUpdated);
        Assertions.assertThat(animes).contains(animeToBeUpdated);

        this.animes.stream()
                .filter(anime -> anime.getId().equals(animeToBeUpdated.getId()))
                .findFirst()
                .ifPresent(anime -> Assertions.assertThat(anime.getName()).isEqualTo("Death Note 2"));

    }

}