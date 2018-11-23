package com.example.administrador.prueba_sistema;

class Paciente {

    private  String id_persona;
    private  Integer id_rol;
    private  String sector;

    public Paciente() {
    }
    public Paciente(String id_persona, int id_rol, String sector) {
        this.id_persona = id_persona;
        this.id_rol = id_rol;
        this.sector = sector;
    }

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
