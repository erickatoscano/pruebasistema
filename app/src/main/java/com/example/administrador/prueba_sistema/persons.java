package com.example.administrador.prueba_sistema;

public class persons {

    private String mail;
    private String pass;
    private String name;
    private String last_name;
    private Integer rol_id;

    public persons() {
    }

    public persons(String mail, String pass, String name, String last_name, Integer rol_id) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
        this.last_name = last_name;
        this.rol_id = rol_id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getRol_id() {
        return rol_id;
    }

    public void setRol_id(Integer rol_id) {
        this.rol_id = rol_id;
    }
}
