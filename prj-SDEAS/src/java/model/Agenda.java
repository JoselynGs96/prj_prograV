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
 * @author Fabi
 */
public class Agenda {

    int id_Agenda;
    boolean Lunes;
    boolean Martes;
    boolean Miercoles;
    boolean Jueves;
    boolean Viernes;
    boolean Sabado;
    boolean Domingo;
    Date FechaInicio;
    Date FechaFinal;
    Date HoraInicio;
    Date HoraFinal;
    Recurso recurso;
    int activo;
    int Id_Registra;
    Date FechaRegistra;
    int Id_Edita;
    Date FechaEdita;
    String Obseraciones;
    JornadaAcademica Jornada;

    public Agenda(int id_Agenda, boolean Lunes, boolean Martes, boolean Miercoles, boolean Jueves, boolean Viernes, boolean Sabado, boolean Domingo, Date FechaInicio, Date FechaFinal, Date HoraInicio, Date HoraFinal, Recurso recurso, int activo, int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita, String Obseraciones) {
        this.id_Agenda = id_Agenda;
        this.Lunes = Lunes;
        this.Martes = Martes;
        this.Miercoles = Miercoles;
        this.Jueves = Jueves;
        this.Viernes = Viernes;
        this.Sabado = Sabado;
        this.Domingo = Domingo;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.HoraInicio = HoraInicio;
        this.HoraFinal = HoraFinal;
        this.recurso = recurso;
        this.activo = activo;
        this.Id_Registra = Id_Registra;
        this.FechaRegistra = FechaRegistra;
        this.Id_Edita = Id_Edita;
        this.FechaEdita = FechaEdita;
        this.Obseraciones = Obseraciones;
    }

    public Agenda( boolean Lunes, boolean Martes, boolean Miercoles, boolean Jueves, boolean Viernes, boolean Sabado, boolean Domingo, Date FechaInicio, Date FechaFinal, Date HoraInicio, Date HoraFinal, Recurso recurso, int activo, String Obseraciones) {
        this.Lunes = Lunes;
        this.Martes = Martes;
        this.Miercoles = Miercoles;
        this.Jueves = Jueves;
        this.Viernes = Viernes;
        this.Sabado = Sabado;
        this.Domingo = Domingo;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.HoraInicio = HoraInicio;
        this.HoraFinal = HoraFinal;
        this.recurso = recurso;
        this.activo = activo;
        this.Obseraciones = Obseraciones;
    }
      public Agenda( boolean Lunes, boolean Martes, boolean Miercoles, boolean Jueves, boolean Viernes, boolean Sabado, boolean Domingo, Date FechaInicio, Date FechaFinal, Date HoraInicio, Date HoraFinal, JornadaAcademica jornada, int activo, String Obseraciones) {
        this.Lunes = Lunes;
        this.Martes = Martes;
        this.Miercoles = Miercoles;
        this.Jueves = Jueves;
        this.Viernes = Viernes;
        this.Sabado = Sabado;
        this.Domingo = Domingo;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.HoraInicio = HoraInicio;
        this.HoraFinal = HoraFinal;
        this.Jornada = jornada;
        this.activo = activo;
        this.Obseraciones = Obseraciones;
    }
    
  

    public Agenda() {
    }

    public int getId_Agenda() {
        return id_Agenda;
    }

    public void setId_Agenda(int id_Agenda) {
        this.id_Agenda = id_Agenda;
    }

    public boolean isLunes() {
        return Lunes;
    }

    public void setLunes(boolean Lunes) {
        this.Lunes = Lunes;
    }

    public boolean isMartes() {
        return Martes;
    }

    public void setMartes(boolean Martes) {
        this.Martes = Martes;
    }

    public boolean isMiercoles() {
        return Miercoles;
    }

    public void setMiercoles(boolean Miercoles) {
        this.Miercoles = Miercoles;
    }

    public boolean isJueves() {
        return Jueves;
    }

    public void setJueves(boolean Jueves) {
        this.Jueves = Jueves;
    }

    public boolean isViernes() {
        return Viernes;
    }

    public void setViernes(boolean Viernes) {
        this.Viernes = Viernes;
    }

    public boolean isSabado() {
        return Sabado;
    }

    public void setSabado(boolean Sabado) {
        this.Sabado = Sabado;
    }

    public boolean isDomingo() {
        return Domingo;
    }

    public void setDomingo(boolean Domingo) {
        this.Domingo = Domingo;
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

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getId_Registra() {
        return Id_Registra;
    }

    public void setId_Registra(int Id_Registra) {
        this.Id_Registra = Id_Registra;
    }

    public Date getFechaRegistra() {
        return FechaRegistra;
    }

    public void setFechaRegistra(Date FechaRegistra) {
        this.FechaRegistra = FechaRegistra;
    }

    public int getId_Edita() {
        return Id_Edita;
    }

    public void setId_Edita(int Id_Edita) {
        this.Id_Edita = Id_Edita;
    }

    public Date getFechaEdita() {
        return FechaEdita;
    }

    public void setFechaEdita(Date FechaEdita) {
        this.FechaEdita = FechaEdita;
    }

    public String getObseraciones() {
        return Obseraciones;
    }

    public void setObseraciones(String Obseraciones) {
        this.Obseraciones = Obseraciones;
    }

 
}
