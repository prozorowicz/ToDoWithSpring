package io.github.prozorowicz.todo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TODOS")
class ToDo {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer ID;
    private String TEXT;
    private boolean DONE;

    /**
     * used by Hibernate.
     */
    @SuppressWarnings("unused")
    public ToDo() {
    }

    public boolean getDONE() {
        return DONE;
    }

    public void setDONE(boolean DONE) {
        this.DONE = DONE;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }
}
