package io.github.prozorowicz.controller;

import io.github.prozorowicz.model.Task;
import io.github.prozorowicz.model.TaskRepository;
import io.github.prozorowicz.model.projection.GroupTaskWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Controller
@RequestMapping(path = "/tasks")
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @GetMapping(path = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readTodayTasks() {
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.MAX;
        LocalDateTime deadline = LocalDateTime.of(today, time);
        return ResponseEntity.ok(repository.findAllByDoneIsFalseAndDeadlineIsNullOrDoneIsFalseAndDeadlineIsLessThanEqual(deadline));
    }

    @ResponseBody
    @GetMapping(params = {"!sort", "!page", "!size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @ResponseBody
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Task> readTask(@PathVariable int id) {
        logger.info("finding task by id");
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseBody
    @GetMapping(path = "/search/done", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(
                repository.findByDone(state)
        );
    }

    @ResponseBody
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @ResponseBody
    @Transactional
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        logger.info("Got request to toggleTask");
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).
                ifPresent(t -> {
                    t.setDone(!t.getDone());
                    //repository.save(t);      @Transactional does it, makes method all or nothing,
                    //                         if we get error after setting done it rolls it back.
                });

        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Task> addTask(@RequestBody @Valid GroupTaskWriteModel task) {
        logger.info("Got request to addTask");
        Task result = repository.save(task.toTask(null));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }
}
