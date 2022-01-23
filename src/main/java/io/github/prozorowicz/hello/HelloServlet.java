package io.github.prozorowicz.hello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class HelloServlet {
    private final Logger logger = LoggerFactory.getLogger(HelloServlet.class);
    private HelloService service;

    HelloServlet(HelloService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<String> prepareGreeting(@RequestParam String name, @RequestParam String lang) {
        logger.info("Got request to prepareGreeting");
        Integer langId = null;
        try {
            langId = Integer.valueOf(lang);

        } catch (NumberFormatException e) {
            logger.warn("Non-numeric language id used: " + lang);
        }
        String nameToPass = null;
        if (!name.isEmpty()) {
            nameToPass = name;
        }
        return ResponseEntity.ok(service.prepareGreeting(nameToPass, langId));
    }
}
