package io.github.prozorowicz.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_groups")
public class TaskGroup extends BaseTask {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<Task> tasks;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project project;
    @Embedded
    private Audit audit = new Audit();

    @SuppressWarnings("unused")
    public TaskGroup() {
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }
}
