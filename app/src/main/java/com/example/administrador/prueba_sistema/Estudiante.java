package com.example.administrador.prueba_sistema;

class Estudiante {
    private  String id_persona;
    private  Integer id_universidad;
    private  Integer id_rol;


    public Estudiante() {
    }

    public Estudiante(String id_persona, Integer id_universidad, Integer id_rol) {
        this.id_persona = id_persona;
        this.id_universidad = id_universidad;
        this.id_rol = id_rol;
    }

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public Integer getId_universidad() {
        return id_universidad;
    }

    public void setId_universidad(Integer id_universidad) {
        this.id_universidad = id_universidad;
    }

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }
}
