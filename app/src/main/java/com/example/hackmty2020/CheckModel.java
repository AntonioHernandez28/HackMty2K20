package com.example.hackmty2020;

import com.google.firebase.Timestamp;

public class CheckModel {

    private String PacienteID;
    private String Doctor_Encargado;
    private String Departamento;
    private Timestamp timeStmp;

    private CheckModel(){}

    private CheckModel(String PacienteID, String DoctorEncargado, String Dpto, Timestamp Date){
        this.PacienteID = PacienteID;
        this.Doctor_Encargado = DoctorEncargado;
        this.Departamento = Dpto;
        this.timeStmp = Date;
    }

    public String getPacienteID() {
        return PacienteID;
    }

    public void setPacienteID(String pacienteID) {
        PacienteID = pacienteID;
    }

    public String getDoctor_Encargado() {
        return Doctor_Encargado;
    }

    public void setDoctor_Encargado(String doctorEncargado) {
        Doctor_Encargado = doctorEncargado;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String dpto) {
        Departamento = dpto;
    }

    public Timestamp getTimeStmp() {
        return timeStmp;
    }

    public void setTimeStmp(Timestamp date) {
        timeStmp = date;
    }
}
