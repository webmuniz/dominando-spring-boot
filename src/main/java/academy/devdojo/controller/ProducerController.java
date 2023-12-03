package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import academy.devdojo.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerMapper mapper;

    private final ProducerService producerService;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {

        log.info("Request received to list all producers, param name: {}", name);
        List<Producer> producers = producerService.findAll(name);
        List<ProducerGetResponse> producerGetResponseList = mapper.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponseList);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "X-API-VERSION=V1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest producerPostRequest) {

        Producer producer = mapper.toProducer(producerPostRequest);
        producerService.save(producer);
        ProducerPostResponse producerPostResponse = mapper.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Request received to delete producer with id: {}", id);

        producerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest producerPutRequest) {

        log.info("Request received to update producer with id: {}", producerPutRequest.getId());

        Producer producer = mapper.toProducer(producerPutRequest);

        producerService.update(producer);

        return ResponseEntity.noContent().build();

    }
}
