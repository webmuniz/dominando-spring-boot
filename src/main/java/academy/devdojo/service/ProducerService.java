package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class ProducerService {

    private final ProducerHardCodedRepository repository;

    public ProducerService() {
        this.repository = new ProducerHardCodedRepository();
    }

    public List<Producer> findAll(String name) {
        return repository.findByName(name);
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public Optional<Producer> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        Producer producer = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be deleted"));
        repository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        Producer producer = findById(producerToUpdate.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be updated"));
        producer.setCreatedAt(producerToUpdate.getCreatedAt());
        repository.update(producerToUpdate);
    }

}
