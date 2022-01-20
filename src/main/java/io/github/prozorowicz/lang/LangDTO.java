package io.github.prozorowicz.lang;

class LangDTO {
    private Integer ID;
    private String CODE;
    private String FLAG;

    LangDTO(Lang lang) {
        this.ID = lang.getId();
        this.CODE = lang.getCODE();
        this.FLAG = lang.getFLAG();
    }
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }
}
