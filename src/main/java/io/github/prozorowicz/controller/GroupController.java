package io.github.prozorowicz.controller;

import io.github.prozorowicz.logic.GroupService;
import io.github.prozorowicz.model.projection.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Controller
@RequestMapping(path = "/groups")
class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService service;

    GroupController(final GroupService service) {
        this.service = service;
    }
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.warn("Exposing all the TaskGroups");
        return ResponseEntity.ok(service.readAll());
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    ResponseEntity<List<GroupTaskReadModel>> readAllTasksInGroup(@PathVariable int id) {
        logger.warn("Reading all Tasks from a group with given id");
        return ResponseEntity.ok(service.readAllTasksFromGroup(id));
    }
    @ResponseBody
    @PatchMapping(path = "/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable @Valid int id) {
        logger.info("toggling taskGroup by id");
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> addGroup(@RequestBody @Valid GroupWriteModel group) {
        logger.info("Got request to add TaskGroup");
        GroupReadModel result = service.createGroup(group);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("groupWrite", new GroupWriteModel());
        return "groups";
    }


    @PostMapping(params = "addTask",
                produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("groupWrite") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("groupWrite") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        service.createGroup(current);
        model.addAttribute("groupWrite", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Group Added");
        return "groups";
    }
    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return service.readAll();
    }



}
