package io.github.prozorowicz.lang;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LANGUAGES")
public class Lang {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer ID;
    private String WELCOME_MSG;
    private String CODE;
    private String FLAG;


    @SuppressWarnings("unused")
    public Lang() {
    }

    public Lang(Integer ID, String WELCOME_MSG, String CODE, String FLAG) {
        this.ID = ID;
        this.WELCOME_MSG = WELCOME_MSG;
        this.CODE = CODE;
        this.FLAG = FLAG;


    }

    public Integer getId() {
        return ID;
    }

    public String getWelcomeMsg() {
        return WELCOME_MSG;
    }

    public String getCODE() {
        return CODE;
    }

    public String getFLAG() {
        return FLAG;
    }
}
