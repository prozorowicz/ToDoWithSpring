package io.github.prozorowicz.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "project description must not be empty")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> taskGroups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> projectSteps;

    @SuppressWarnings("unused")
    public Project() {
    }

    public Set<ProjectStep> getProjectSteps() {
        return projectSteps;
    }

    public void setProjectSteps(final Set<ProjectStep> project_steps) {
        this.projectSteps = project_steps;
    }

    Set<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    void setTaskGroups(final Set<TaskGroup> taskGroups) {
        this.taskGroups = taskGroups;
    }

    public int getId() {
        return id;
    }


    void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
