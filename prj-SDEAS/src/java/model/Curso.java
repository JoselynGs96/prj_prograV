/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author ujose
 */
public class Curso {
    int id;
    String nombre;
    String descripcion;
    Programa programa;
    String estado;
    int Id_Registra;
    Date FechaRegistra;
    int Id_Edita;
    Date FechaEdita;
    
    public Curso() {
    }

    public Curso(int id, String nombre, String descripcion, Programa programa, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.programa = programa;
        this.estado = estado;
    }
    
    
    public Curso(int id, String nombre, String descripcion, Programa programa, String estado,  int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setPrograma(programa);
        this.setEstado(estado);
        this.setId_Registra(Id_Registra);
        this.setFechaRegistra(FechaRegistra);
        this.setId_Edita(Id_Edita);
        this.setFechaEdita(FechaEdita);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    
}
