package io.github.prozorowicz.controller;

import io.github.prozorowicz.model.TaskGroup;
import io.github.prozorowicz.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "/taskGroups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupRepository repository;

    TaskGroupController(final TaskGroupRepository repository) {
        this.repository = repository;
    }
    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<TaskGroup>> readAllTaskGroups() {
        logger.warn("Exposing all the taskGroups");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<TaskGroup> readTaskGroup(@PathVariable int id) {
        logger.info("finding taskGroup by id");
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<TaskGroup> addTaskGroup(@RequestBody @Valid TaskGroup taskGroup) {
        logger.info("Got request to addTaskGroup");
        TaskGroup result = repository.save(taskGroup);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);

    }


}
