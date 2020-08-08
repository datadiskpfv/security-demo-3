package uk.co.datadisk.securitydemo3.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "Index Mapping";
    }

    @GetMapping("/filter1")
    public String filter1(Authentication auth) {
        System.out.println("Authentication: " + auth.getName());
        return "Filter1 Mapping";
    }

    @GetMapping("/filter2")
    public String filter2() {
        return "Filter2 Mapping";
    }

    @GetMapping("/filter3")
    public String filter3() {
        return "Filter3 Mapping";
    }
}
