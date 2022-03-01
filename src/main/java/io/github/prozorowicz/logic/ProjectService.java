package io.github.prozorowicz.logic;

import io.github.prozorowicz.TaskConfigurationProperties;
import io.github.prozorowicz.model.*;
import io.github.prozorowicz.model.projection.GroupReadModel;
import io.github.prozorowicz.model.projection.GroupTaskWriteModel;
import io.github.prozorowicz.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService groupService;
    private TaskConfigurationProperties config;

    ProjectService(final ProjectRepository repository, final TaskGroupRepository taskGroupRepository, final TaskGroupService groupService, final TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.groupService = groupService;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project createProject(final Project source) {
        return repository.save(source);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {

        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed in config");
        }

        return repository.findById(projectId).map(
                project -> {
                    var group = new GroupWriteModel();
                    group.setDescription(project.getDescription());
                    group.setTasks(project.getProjectSteps().stream()
                            .map(step -> {
                                var task = new GroupTaskWriteModel();
                                task.setDescription(step.getDescription());
                                task.setDeadline(deadline.plusDays(step.getDaysToDeadline()));
                                return task;
                            })
                            .collect(Collectors.toSet())
                    );
                    return groupService.createGroup(group);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }
}

