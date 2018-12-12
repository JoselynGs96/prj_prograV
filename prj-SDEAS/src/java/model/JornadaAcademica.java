/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author ujose
 */
public class JornadaAcademica {
    int Id_JornadaAcademica;
    String Nombre;
    String Dsc_JornadaAcademica;
    Date FechaInicio;
    Date FechaFinal;
    Date HoraInicio;
    Date HoraFinal;
    Curso Curso;
    String Log_Activo;

    

    public JornadaAcademica() {
    }

    public JornadaAcademica(String Nombre, String Dsc_JornadaAcademica, Date FechaInicio, Date FechaFinal, Date HoraInicio, Date HoraFinal, Curso Curso, String Log_Activo) {
        this.setNombre(Nombre);
        this.setDsc_JornadaAcademica(Dsc_JornadaAcademica);
        this.setFechaInicio(FechaInicio);
        this.setFechaFinal(FechaFinal);
        this.setHoraInicio(HoraInicio);
        this.setHoraFinal(HoraFinal);
        this.setCurso(Curso);
        this.setLog_Activo(Log_Activo);
    }

    public int getId_JornadaAcademica() {
        return Id_JornadaAcademica;
    }

    public void setId_JornadaAcademica(int Id_JornadaAcademica) {
        this.Id_JornadaAcademica = Id_JornadaAcademica;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDsc_JornadaAcademica() {
        return Dsc_JornadaAcademica;
    }

    public void setDsc_JornadaAcademica(String Dsc_JornadaAcademica) {
        this.Dsc_JornadaAcademica = Dsc_JornadaAcademica;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Date getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(Date FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    public Date getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(Date HoraInicio) {
        this.HoraInicio = HoraInicio;
    }

    public Date getHoraFinal() {
        return HoraFinal;
    }

    public void setHoraFinal(Date HoraFinal) {
        this.HoraFinal = HoraFinal;
    }


    public String getLog_Activo() {
        return Log_Activo;
    }

    public void setLog_Activo(String Log_Activo) {
        this.Log_Activo = Log_Activo;
    }
    
    public Curso getCurso() {
        return Curso;
    }

    public void setCurso(Curso Curso) {
        this.Curso = Curso;
    }
}
