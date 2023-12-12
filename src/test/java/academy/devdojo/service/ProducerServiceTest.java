package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService producerService;

    @Mock
    private ProducerHardCodedRepository producerHardCodedRepository;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        producers = new ArrayList<>(List.of(
                Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build(),
                Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build(),
                Producer.builder().id(3L).name("Studio Gibli").createdAt(LocalDateTime.now()).build()
        ));
    }

    @Test
    @DisplayName("findAll returns list of producers when successful")
    @Order(1)
    void findAll() {
        BDDMockito.when(producerHardCodedRepository.findByName(null)).thenReturn(this.producers);
        List<Producer> all = producerService.findAll(null);
        Assertions.assertThat(all).isNotEmpty().hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findAll() returns a list With found producers when name is not null")
    @Order(2)
    void findAll_WhenNameIsNotNull() {
        String name = "Ufotable";
        var producerFound = producers.stream().filter(producer -> producer.getName().equals(name)).toList();
        BDDMockito.when(producerHardCodedRepository.findByName(name)).thenReturn(producerFound);

        List<Producer> all = producerService.findAll(name);
        Assertions.assertThat(all).hasSize(1).isEqualTo(producerFound);
    }

    @Test
    @DisplayName("findById returns an empty list of producers when producer is not found")
    @Order(3)
    void findById_ReturnsAnEmptyListOfProducers_WhenProducerIsNotFound() {
        BDDMockito.when(producerHardCodedRepository.findByName("x")).thenReturn(Collections.emptyList());

        List<Producer> all = producerService.findAll("x");
        Assertions.assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("findById returns an optional producer when successful")
    @Order(4)
    void findById_ReturnsAnOptionalProducer_WhenSuccessful() {
        BDDMockito.when(producerHardCodedRepository.findById(1L)).thenReturn(Optional.of(producers.get(0)));

        var producerFound = producerService.findById(1L);
        Assertions.assertThat(producerFound).isPresent().isEqualTo(Optional.of(producers.get(0)));
    }

    @Test
    @DisplayName("findById returns an empty optional producer when producer is not found")
    @Order(5)
    void findById_ReturnsAnEmptyOptionalProducer_WhenProducerIsNotFound() {
        BDDMockito.when(producerHardCodedRepository.findById(4L)).thenReturn(Optional.empty());

        var producerFound = producerService.findById(4L);
        Assertions.assertThat(producerFound).isEmpty();
    }

    @Test
    @DisplayName("save returns producer when successful")
    @Order(6)
    void save_ReturnsProducer_WhenSuccessful() {
        var producerToBeSaved = Producer.builder().name("Studio Pierrot").build();
        var producerSaved = Producer.builder().id(1L).name("Studio Pierrot").createdAt(LocalDateTime.now()).build();
        BDDMockito.when(producerHardCodedRepository.save(producerToBeSaved)).thenReturn(producerSaved);

        var producer = producerService.save(producerToBeSaved);
        Assertions.assertThat(producer).isNotNull().isEqualTo(producerSaved);
    }

    @Test
    @DisplayName("delete removes producer when successful")
    @Order(7)
    void delete_RemovesProducer_WhenSuccessful() {
        var producerToBeDeleted = Producer.builder().id(1L).name("Studio Pierrot").createdAt(LocalDateTime.now()).build();
        BDDMockito.when(producerHardCodedRepository.findById(1L)).thenReturn(Optional.of(producerToBeDeleted));

        producerService.delete(1L);
        BDDMockito.verify(producerHardCodedRepository, BDDMockito.times(1)).delete(producerToBeDeleted);
        Assertions.assertThatNoException().isThrownBy(() -> producerService.delete(1L));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        BDDMockito.when(producerHardCodedRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> producerService.delete(1L));
    }

    @Test
    @DisplayName("update save updated producer when successful")
    @Order(9)
    void update_SaveUpdatedProducer_WhenSuccessful() {
        var id = 1L;
        var producerToBeUpdated = this.producers.get(0);

        BDDMockito.when(producerHardCodedRepository.findById(id)).thenReturn(Optional.of(producerToBeUpdated));
        BDDMockito.doNothing().when(producerHardCodedRepository).update(producerToBeUpdated);

        producerToBeUpdated.setName("KyoAni");
        producerService.update(producerToBeUpdated);

        Assertions.assertThatNoException().isThrownBy(() -> producerService.update(producerToBeUpdated));
        Assertions.assertThat(producerToBeUpdated.getName()).isEqualTo("KyoAni");

    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToBeUpdated = this.producers.get(0);

        BDDMockito.when(producerHardCodedRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> producerService.update(producerToBeUpdated));
    }



}