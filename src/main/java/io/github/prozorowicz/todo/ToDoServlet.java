package io.github.prozorowicz.todo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
class ToDoServlet {
    private final Logger logger = LoggerFactory.getLogger(ToDoServlet.class);
    private ToDoRepository repository;

    ToDoServlet(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    ResponseEntity<List<ToDo>> findAllToDos() {
        logger.info("Got request to findAllToDos");
        return ResponseEntity.ok(repository.findAll());
    }
}
