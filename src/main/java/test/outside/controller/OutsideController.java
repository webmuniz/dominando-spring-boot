package test.outside.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greetings")
public class OutsideController {

    @GetMapping("outside")
    public String outside() {
        return "Você acabou de chamar um Bean fora do seu pacote!";
    }
}
