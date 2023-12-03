package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerPostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
public class ProducerController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                headers = "X-API-VERSION=V1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest producerPostRequest){

        Producer producer = Producer.builder()
                .name(producerPostRequest.getName())
                .id(ThreadLocalRandom.current().nextLong(4, 100000))
                .createdAt(LocalDateTime.now())
                .build();

        Producer.getProducers().add(producer);

        ProducerPostResponse producerPostResponse = ProducerPostResponse.builder()
                .id(producer.getId())
                .name(producer.getName())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);

        /*return ResponseEntity.ok(producer);
           return ResponseEntity.noContent().build();*/
    }
}
