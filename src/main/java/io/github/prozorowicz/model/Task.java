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

    public void updateFrom(Task source){
        super.updateFrom(source);
        deadline=source.deadline;
        group=source.group;
    }

    public TaskGroup getGroup() {
        return group;
    }

    public void setGroup(final TaskGroup group) {
        this.group = group;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
