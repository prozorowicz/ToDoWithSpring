package io.github.prozorowicz.logic;

import io.github.prozorowicz.TaskConfigurationProperties;
import io.github.prozorowicz.model.ProjectRepository;
import io.github.prozorowicz.model.TaskGroupRepository;
import io.github.prozorowicz.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskGroupService groupService,
            final TaskConfigurationProperties taskConfigurationProperties
    ) {
        return new ProjectService(projectRepository, taskGroupRepository, groupService, taskConfigurationProperties);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository
    ) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}
