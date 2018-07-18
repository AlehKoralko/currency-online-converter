package com.korolko.converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping(value = "")
    public String sayHello(Model model) {
        model.addAttribute("msg", "Hello");

        return "index";
    }
}
