package com.example.administrador.prueba_sistema;

class Cita {

    private String fecha;
    private String id_patient;
    private String id_student;

    public Cita() {
    }

    public Cita(String fecha, String id_patient, String id_student) {
        this.fecha = fecha;
        this.id_patient = id_patient;
        this.id_student = id_student;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_patient() {
        return id_patient;
    }

    public void setId_patient(String id_patient) {
        this.id_patient = id_patient;
    }

    public String getId_student() {
        return id_student;
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }
}
