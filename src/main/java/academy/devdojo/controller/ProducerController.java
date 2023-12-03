package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {

        log.info("Request received to list all producers, param name: {}", name);
        List<Producer> producers = Producer.getProducers();
        List<ProducerGetResponse> producerGetResponseList = MAPPER.toProducerGetResponseList(producers);

        if (name == null) {
            return ResponseEntity.ok(producerGetResponseList);
        }

        List<ProducerGetResponse> list = producerGetResponseList.stream()
                .filter(producerGetResponse -> producerGetResponse.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(list);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "X-API-VERSION=V1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest producerPostRequest) {

        Producer producer = MAPPER.toProducer(producerPostRequest);
        Producer.getProducers().add(producer);
        ProducerPostResponse producerPostResponse = MAPPER.toProducerPostResponse(producer);

        log.info("Producer saved: name: {}, id: {}, created at: {}", producerPostResponse.getName(), producerPostResponse.getId(), producer.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Request received to delete producer with id: {}", id);

        Producer producer = Producer.getProducers()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be deleted"));

        Producer.getProducers().remove(producer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest producerPutRequest){

            log.info("Request received to update producer with id: {}", producerPutRequest.getId());

            Producer producer = Producer.getProducers()
                    .stream()
                    .filter(p -> p.getId().equals(producerPutRequest.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be updated"));

            Producer.getProducers().remove(producer);
            Producer producerUpdated = MAPPER.toProducer(producerPutRequest, producer.getCreatedAt());
            Producer.getProducers().add(producerUpdated);

            return ResponseEntity.noContent().build();

    }
}
