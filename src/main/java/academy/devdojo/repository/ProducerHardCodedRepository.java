package academy.devdojo.repository;

import academy.devdojo.domain.Producer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCodedRepository {

    private static final List<Producer> PRODUCERS = new ArrayList<>();

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