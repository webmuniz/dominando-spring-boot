package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
public class ProducerController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=V1")
    public Producer save(@RequestBody Producer producer){
        producer.setId(ThreadLocalRandom.current().nextLong(4, 100000));
        Producer.getProducers().add(producer);
        return producer;
    }



}
