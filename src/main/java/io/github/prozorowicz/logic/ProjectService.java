package io.github.prozorowicz.logic;

import io.github.prozorowicz.TaskConfigurationProperties;
import io.github.prozorowicz.model.*;
import io.github.prozorowicz.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    ProjectService(final ProjectRepository repository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
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

        TaskGroup result = repository.findById(projectId).map(
                project -> {
                    TaskGroup group = new TaskGroup();
                    group.setDescription(project.getDescription());
                    group.setDone(false);
                    group.setProject(project);
                    group.setTasks(project.getProjectSteps().stream()
                            .map(step -> new Task(
                                    step.getDescription(),
                                    deadline.plusDays(step.getDaysToDeadline())))
                            .collect(Collectors.toSet())
                    );
                    return taskGroupRepository.save(group);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }
}

