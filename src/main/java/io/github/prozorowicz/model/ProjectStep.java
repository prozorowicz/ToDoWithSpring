package io.github.prozorowicz.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "task description must not be empty")
    private String description;
    @NotBlank(message = "days to deadline must not be empty")
    private int daysToDeadline;                                      // in ProjectService negative value of daysToDeadline is assumed
    @NotBlank(message = "project step must be assigned to project")
    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project project;

    @SuppressWarnings("unused")
    ProjectStep() {
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    void setDaysToDeadline(final int days_to_deadline) {
        this.daysToDeadline = days_to_deadline;
    }

    Project getProject() {
        return project;
    }

    void setProject(final Project project) {
        this.project = project;
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

    void setDescription(final String description) {
        this.description = description;
    }
}
