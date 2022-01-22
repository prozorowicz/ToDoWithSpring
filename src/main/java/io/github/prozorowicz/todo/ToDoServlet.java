package io.github.prozorowicz.todo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
class ToDoServlet {
    private final Logger logger = LoggerFactory.getLogger(ToDoServlet.class);
    private ToDoRepository repository;
    private ToDoService service;

    public ToDoServlet(ToDoRepository repository, ToDoService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<ToDo>> findAllToDos() {
        logger.info("Got request to findAllToDos");
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping("/{id}")
    ResponseEntity<ToDo> ToggleToDo(@PathVariable Integer id) {
        logger.info("Got request to ToggleToDo " + id);
        return ResponseEntity.ok(service.ToggleToDo(id));
    }

}
