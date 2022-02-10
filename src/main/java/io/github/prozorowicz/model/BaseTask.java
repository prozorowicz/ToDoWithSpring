package io.github.prozorowicz.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
abstract class BaseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task description must not be empty")
    private String description;
    private boolean done;

    public void updateFrom(BaseTask source){
        description=source.description;
        done=source.done;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
