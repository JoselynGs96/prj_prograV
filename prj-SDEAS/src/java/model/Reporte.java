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
public class Reporte {
    EncabezadoSolicitud encabezado = null;
    TipoSolicitud tipoSoli = null;
    Usuario usuario = null;
    EstadoSolicitud estado = null;
    Date fechaInicio = null;
    Date fechaFinal = null;
    Date horaInicio = null;
    Date horaFinal = null;

    public Reporte() {
    }


    public EncabezadoSolicitud getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(EncabezadoSolicitud encabezado) {
        this.encabezado = encabezado;
    }

    public TipoSolicitud getTipoSoli() {
        return tipoSoli;
    }

    public void setTipoSoli(TipoSolicitud tipoSoli) {
        this.tipoSoli = tipoSoli;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    
    
}
