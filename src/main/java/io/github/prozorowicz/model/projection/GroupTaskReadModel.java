package io.github.prozorowicz.model.projection;

import io.github.prozorowicz.model.Task;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class GroupTaskReadModel {
    private int id;
    private LocalDateTime deadline;
    private boolean done;
    @NotBlank(message = "Task description must not be empty")
    private String description;

    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.getDone();
        id = source.getId();
        deadline=source.getDeadline();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
}
