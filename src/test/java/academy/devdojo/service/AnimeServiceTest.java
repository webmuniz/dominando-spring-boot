package academy.devdojo.service;


import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {


    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeHardCodedRepository animeHardCodedRepository;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        animes = new ArrayList<>(List.of(
                Anime.builder().id(1L).name("Death Note").build(),
                Anime.builder().id(2L).name("Pokemon").build(),
                Anime.builder().id(3L).name("Berserk").build()
        ));
    }

    @Test
    @DisplayName("findAll returns list of animes when successful")
    @Order(1)
    void findAll() {
        BDDMockito.when(animeHardCodedRepository.findByName(null)).thenReturn(this.animes);
        List<Anime> all = animeService.findAll(null);
        Assertions.assertThat(all).isNotEmpty().hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("findAll() returns a list With found animes when name is not null")
    @Order(2)
    void findAll_WhenNameIsNotNull() {
        String name = "Death Note";
        var animeFound = animes.stream().filter(anime -> anime.getName().equals(name)).toList();
        BDDMockito.when(animeHardCodedRepository.findByName(name)).thenReturn(animeFound);

        List<Anime> all = animeService.findAll(name);
        Assertions.assertThat(all).hasSize(1).isEqualTo(animeFound);
    }

    @Test
    @DisplayName("findById returns an empty list of animes when anime is not found")
    @Order(3)
    void findById_ReturnsAnEmptyListOfAnimes_WhenAnimeIsNotFound() {
        BDDMockito.when(animeHardCodedRepository.findByName("x")).thenReturn(Collections.emptyList());

        List<Anime> all = animeService.findAll("x");
        Assertions.assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("findById returns an optional anime when successful")
    @Order(4)
    void findById_ReturnsAnOptionalAnime_WhenSuccessful() {
        BDDMockito.when(animeHardCodedRepository.findById(1L)).thenReturn(Optional.of(animes.get(0)));

        Anime animeFound = animeService.findById(1L);
        Assertions.assertThat(animeFound).isNotNull().isEqualTo(animes.get(0));
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeHardCodedRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> animeService.findById(1L));
    }

    @Test
    @DisplayName("save returns anime when successful")
    @Order(6)
    void save_ReturnsAnime_WhenSuccessful() {
        var animeToBeSaved = Anime.builder().name("Studio Pierrot").build();
        var animeSaved = Anime.builder().id(1L).name("Studio Pierrot").build();
        BDDMockito.when(animeHardCodedRepository.save(animeToBeSaved)).thenReturn(animeSaved);

        var anime = animeService.save(animeToBeSaved);
        org.assertj.core.api.Assertions.assertThat(anime).isNotNull().isEqualTo(animeSaved);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    @Order(7)
    void delete_RemovesAnime_WhenSuccessful() {
        var animeToBeDeleted = Anime.builder().id(1L).name("Studio Pierrot").build();
        BDDMockito.when(animeHardCodedRepository.findById(1L)).thenReturn(Optional.of(animeToBeDeleted));

        animeService.delete(1L);
        BDDMockito.verify(animeHardCodedRepository, BDDMockito.times(1)).delete(animeToBeDeleted);
        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() -> animeService.delete(1L));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeHardCodedRepository.findById(1L)).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> animeService.delete(1L));
    }

    @Test
    @DisplayName("update save updated anime when successful")
    @Order(9)
    void update_SaveUpdatedAnime_WhenSuccessful() {
        var id = 1L;
        var animeToBeUpdated = this.animes.get(0);

        BDDMockito.when(animeHardCodedRepository.findById(id)).thenReturn(Optional.of(animeToBeUpdated));
        BDDMockito.doNothing().when(animeHardCodedRepository).update(animeToBeUpdated);

        animeToBeUpdated.setName("KyoAni");
        animeService.update(animeToBeUpdated);

        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() -> animeService.update(animeToBeUpdated));
        org.assertj.core.api.Assertions.assertThat(animeToBeUpdated.getName()).isEqualTo("KyoAni");

    }

    @Test
    @DisplayName("update throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToBeUpdated = this.animes.get(0);

        BDDMockito.when(animeHardCodedRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> animeService.update(animeToBeUpdated));
    }



}