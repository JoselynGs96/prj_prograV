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
public class Recurso {
    int id;
    String nombre;
    String descripcion;
    int cantidad;
    int capacidad;
    TipoRecurso tipoRecurso;
    int Id_Registra;
    Date FechaRegistra;
    int Id_Edita;
    Date FechaEdita;
    String estado;
    EnumTipoInfraestructura TipoInfraestructura;
    Programa programa = null;

    public Recurso() {
    }

    public Recurso(int id, String nombre, String descripcion, int cantidad, int capacidad, TipoRecurso tipoRecurso, int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita, String estado) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setCantidad(cantidad);
        this.setCapacidad(capacidad);
        this.setTipoRecurso(tipoRecurso);
        this.setEstado(estado);
        this.setId_Registra(Id_Registra);
        this.setFechaRegistra(FechaRegistra);
        this.setId_Edita(Id_Edita);
        this.setFechaEdita(FechaEdita);
        this.setEstado(estado);
    }
    
    

    public Recurso(int id, String nombre, String descripcion, int cantidad, int capacidad, TipoRecurso tipoRecurso, String estado) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setCantidad(cantidad);
        this.setCapacidad(capacidad);
        this.setTipoRecurso(tipoRecurso);
        this.setEstado(estado);
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
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

    public EnumTipoInfraestructura getTipoInfraestructura() {
        return TipoInfraestructura;
    }

    public void setTipoInfraestructura(EnumTipoInfraestructura TipoInfraestructura) {
        this.TipoInfraestructura = TipoInfraestructura;
    }


    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
    
    
}
