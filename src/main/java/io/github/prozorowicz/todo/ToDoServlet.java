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
    private ToDoService service;

    public ToDoServlet(ToDoService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<ToDo>> findAllToDos() {
        logger.info("Got request to findAllToDos");
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    ResponseEntity<ToDo> toggleToDo(@PathVariable Integer id) {
        logger.info("Got request to ToggleToDo " + id);
        var toDo = service.toggleToDo(id);
        return toDo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<ToDo> addTodo(@RequestBody ToDo toDo) {
        logger.info("Got request to addTodo");
        return ResponseEntity.ok(service.addTodo(toDo));

    }

}
