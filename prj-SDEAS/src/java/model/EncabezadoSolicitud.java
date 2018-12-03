/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Fabi
 */
public class EncabezadoSolicitud {
    int id_Encabezado;
    Date FechaInicio;
    Date FechaFinal;
    Usuario Funcionario;
    Usuario Coordinador;
    TipoSolicitud Tipo_solicitud;
    EstadoSolicitud estado;

    public EncabezadoSolicitud(int id_Encabezado, Date FechaInicio, Date FechaFinal, Usuario Funcionario, Usuario Coordinador, TipoSolicitud Tipo_solicitud, EstadoSolicitud estado) {
        this.id_Encabezado = id_Encabezado;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.Funcionario = Funcionario;
        this.Coordinador = Coordinador;
        this.Tipo_solicitud = Tipo_solicitud;
        this.estado = estado;
    }
    
    public  EncabezadoSolicitud(){
        
    }

    public int getId_Encabezado() {
        return id_Encabezado;
    }

    public void setId_Encabezado(int id_Encabezado) {
        this.id_Encabezado = id_Encabezado;
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

    public Usuario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(Usuario Funcionario) {
        this.Funcionario = Funcionario;
    }

    public Usuario getCoordinador() {
        return Coordinador;
    }

    public void setCoordinador(Usuario Coordinador) {
        this.Coordinador = Coordinador;
    }

    public TipoSolicitud getTipo_solicitud() {
        return Tipo_solicitud;
    }

    public void setTipo_solicitud(TipoSolicitud Tipo_solicitud) {
        this.Tipo_solicitud = Tipo_solicitud;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
    
    
    
}
