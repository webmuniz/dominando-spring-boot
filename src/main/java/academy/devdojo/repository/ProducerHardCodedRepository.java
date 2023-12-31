package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import test.outside.controller.Connection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProducerHardCodedRepository {

    private static final List<Producer> PRODUCERS = new ArrayList<>();

    @Qualifier(value = "mysql")
    private final Connection connection;

    static {
        var producer1 = Producer.builder().id(1L).name("MAPPA").createdAt(LocalDateTime.now()).build();
        var producer2 = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var producer3 = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();

        PRODUCERS.addAll(List.of(producer1, producer2, producer3));
    }

    public List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst();
    }

    public List<Producer> findByName(String name) {

        log.info("Connection: {}", connection);

        return name == null ? PRODUCERS : PRODUCERS.stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .toList();
    }

    public Producer save(Producer producer) {
        PRODUCERS.add(producer);
        return producer;
    }

    public void delete(Producer producer) {
        PRODUCERS.remove(producer);
    }

    public void update(Producer producer) {
        delete(producer);
        save(producer);
    }

}
