package priv.markingxs.mpic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/error")
@RestController
public class ErrorController {

    @RequestMapping(value = "/404")
    public String error_404() {
        System.out.println("66666666666666666666");
        return "这是404";
    }
}
