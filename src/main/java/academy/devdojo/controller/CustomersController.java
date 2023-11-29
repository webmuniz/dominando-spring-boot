package academy.devdojo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"v1/customers", "v1/customers/"})
public class CustomersController {

    public static final List<String> NAMES = List.of("John Doe", "Fulano", "Ciclano", "Beltrano");

    @GetMapping
    public List<String> list() {
        return NAMES;
    }

    /*@RequestParam*/
    @GetMapping(path = "filter")
    public List<String> filter(@RequestParam String name) {
        return NAMES.stream().filter(n -> n.toLowerCase().contains(name.toLowerCase())).toList();
    }

    @GetMapping(path = {"filterOptional", "filterOptional/"})
    public List<String> filterOptional(@RequestParam Optional<String> name) {
        return NAMES.stream().filter(n -> n.toLowerCase().contains(name.orElse("").toLowerCase())).toList();
    }

    @GetMapping(path = {"filterList", "filterList/"})
    public List<String> filterList(@RequestParam List<String> names) {
        return NAMES.stream().filter(names::contains).toList();
    }

    /*@PathVariable*/
    @GetMapping(path = "find/{name}")
    public String findByName(@PathVariable String name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name)).findFirst().orElseGet(() -> "Name not found");
    }

    @GetMapping(path = "{id}/{name}")
    public String findByIdAndName(@PathVariable int id, @PathVariable String name) {
        return String.format("ID: %d, Name: %s", id, name);
    }

}
