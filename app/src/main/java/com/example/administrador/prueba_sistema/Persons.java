package com.example.administrador.prueba_sistema;

class Persons {

    private String mail;
    private String pass;
    private String name;
    private Integer rol_id;
    private  String disponibilidad;
    private  String disponible;
    private  String enfermedad_a_tratar;

    public Persons() {
    }

    public Persons(String name, String mail, String pass, Integer rol_id) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
        this.rol_id = rol_id;
    }
    public Persons(String name, String mail, String pass,  String disponibilidad, String disponible, String enfermedad_a_tratar) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
        this.disponibilidad = disponibilidad;
        this.disponible = disponible;
        this.enfermedad_a_tratar = enfermedad_a_tratar;

    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getEnfermedad_a_tratar() {
        return enfermedad_a_tratar;
    }

    public void setEnfermedad_a_tratar(String enfermedad_a_tratar) {
        this.enfermedad_a_tratar = enfermedad_a_tratar;
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
