package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // This annotation is used to run the test with Mockito
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // This annotation is used to set the order of the tests
class ProducerHardCodedRepositoryTest {

    @InjectMocks // This annotation is used to inject the mock dependencies into the class that we want to test
    private ProducerHardCodedRepository producerHardCodedRepository;

    @Mock // This annotation is used to create a mock dependency
    private ProducerData producerData;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        producers = new ArrayList<>(List.of(
                Producer.builder().id(1L).name("Ufotable").build(),
                Producer.builder().id(2L).name("Wit Studio").build(),
                Producer.builder().id(3L).name("Studio Gibli").build()
        ));

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll returns list of producers when successful")
    @Order(1) // This annotation is used to set the order of the tests
    void findAll_ReturnsAllProducers_WhenSuccessful() {

        List<Producer> producers = producerHardCodedRepository.findAll();
        Assertions.assertThat(producers).isNotEmpty().hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findById returns an optional producer when successful")
    @Order(2)
    void findById_ReturnsAnOptionalProducer_WhenSuccessful() {

        Optional<Producer> producerFound = producerHardCodedRepository.findById(1L);
        Assertions.assertThat(producerFound).isPresent().isEqualTo(Optional.of(producers.get(0)));
    }

    @Test
    @DisplayName("findById returns an empty optional producer when producer is not found")
    @Order(3)
    void findById_ReturnsAnEmptyOptionalProducer_WhenProducerIsNotFound() {

        Optional<Producer> producerFound = producerHardCodedRepository.findById(4L);
        Assertions.assertThat(producerFound).isEmpty();
    }

    @Test
    @DisplayName("findByName returns a list of producers when successful")
    @Order(4)
    void findByName_ReturnsAListOfProducers_WhenSuccessful() {

        List<Producer> producersFound = producerHardCodedRepository.findByName("Ufotable");
        Assertions.assertThat(producersFound).isNotEmpty().hasSameElementsAs(List.of(producers.get(0)));
    }

    @Test
    @DisplayName("findByName returns an empty list of producers when producers is not found")
    @Order(5)
    void findByName_ReturnsAnEmptyListOfProducers_WhenProducerIsNotFound() {

        List<Producer> producersFound = producerHardCodedRepository.findByName("Studio Pierrot");
        Assertions.assertThat(producersFound).isEmpty();
    }

    @Test
    @DisplayName("findByName returns all producers when name is null")
    @Order(6)
    void findByName_ReturnsAllProducers_WhenNameIsNull() {

        List<Producer> producersFound = producerHardCodedRepository.findByName(null);
        Assertions.assertThat(producersFound).isNotEmpty().hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("save create a producer when successful")
    @Order(7)
    void save_CreateProducer_WhenSuccessful() {

        Producer producerToBeSaved = Producer.builder().id(4L).name("Studio Pierrot").createdAt(LocalDateTime.now()).build();

        Producer producerSaved = producerHardCodedRepository.save(producerToBeSaved);
        Assertions.assertThat(producerSaved).isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();

        List<Producer> producers = producerHardCodedRepository.findAll();
        Assertions.assertThat(producers).contains(producerToBeSaved);
    }


    @Test
    @DisplayName("delete removes the producer when successful")
    @Order(8)
    void delete_RemovesTheProducer_WhenSuccessful() {

        Producer producerToBeDeleted = producers.get(0);
        producerHardCodedRepository.delete(producerToBeDeleted);
        Assertions.assertThat(producers).doesNotContain(producerToBeDeleted);
    }

    @Test
    @DisplayName("update save updated producer when successful")
    @Order(9)
    void update_SaveUpdatedProducer_WhenSuccessful() {

        Producer producerToBeUpdated = producers.get(0);
        producerToBeUpdated.setName("A-1 Pictures");
        producerHardCodedRepository.update(producerToBeUpdated);
        Assertions.assertThat(producers).contains(producerToBeUpdated);

        this.producers.stream()
                .filter(producer -> producer.getId().equals(producerToBeUpdated.getId()))
                .findFirst()
                .ifPresent(
                        producer -> Assertions.assertThat(producer.getName()).isEqualTo(producerToBeUpdated.getName())
                );
    }

}