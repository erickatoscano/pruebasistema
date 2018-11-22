package com.example.administrador.prueba_sistema;

class Persons {

    private String mail;
    private String pass;
    private String name;
    private Integer rol_id;

    public Persons() {
    }

    public Persons(String name, String mail, String pass, Integer rol_id) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
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

    public Integer getRol_id() {
        return rol_id;
    }

    public void setRol_id(Integer rol_id) {
        this.rol_id = rol_id;
    }
}
