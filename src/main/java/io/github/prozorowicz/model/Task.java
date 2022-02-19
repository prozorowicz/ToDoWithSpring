package io.github.prozorowicz.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseTask{

    private LocalDateTime deadline;
    @ManyToOne(targetEntity = TaskGroup.class)
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;
    @Embedded
    private Audit audit = new Audit();

    @SuppressWarnings("unused")
    Task() {
    }

    public Task(String description, LocalDateTime deadline){
        this.deadline=deadline;
        this.setDescription(description);
    }

    public void updateFrom(Task source){
        super.updateFrom(source);
        deadline=source.deadline;
        group=source.group;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(final TaskGroup group) {
        this.group = group;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
