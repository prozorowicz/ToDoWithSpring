package io.github.prozorowicz.controller;

import io.github.prozorowicz.model.Task;
import io.github.prozorowicz.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "/tasks")
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
    @GetMapping(path = "/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        logger.info("finding task by id");
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping( path ="/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        logger.info("Got request to updateTask");
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).
                ifPresent(t -> {
                    t.updateFrom(toUpdate);
                    repository.save(t);
                });

        return ResponseEntity.noContent().build();
    }
    @PatchMapping( path ="/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        logger.info("Got request to toggleTask");
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).
                ifPresent(t -> {
                    t.setDone(!t.getDone());
                    repository.save(t);
                });

        return ResponseEntity.noContent().build();
    }
    @PostMapping
    ResponseEntity<Task> addTask(@RequestBody @Valid Task task) {
        logger.info("Got request to addTask");
        Task result = repository.save(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);

    }


}
