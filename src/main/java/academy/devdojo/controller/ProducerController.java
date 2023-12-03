package academy.devdojo.controller;

import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.domain.Producer;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerPostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
public class ProducerController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                headers = "X-API-VERSION=V1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest producerPostRequest){

        ProducerMapper producerMapper = ProducerMapper.INSTANCE;
        Producer producer = producerMapper.toProducer(producerPostRequest);
        Producer.getProducers().add(producer);
        ProducerPostResponse producerPostResponse = producerMapper.toProducerPostResponse(producer);

        log.info("Producer saved: name: {}, id: {}, created at: {}", producerPostResponse.getName(), producerPostResponse.getId(), producer.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);

    }
}
