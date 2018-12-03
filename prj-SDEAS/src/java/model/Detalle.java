/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Fabi
 */
public class Detalle {

    int Id_Detalle;
    EncabezadoSolicitud encabezado;
    JornadaAcademica jornadaAcademica;
    int activo;
    Recurso recurso; 

    
    
    public Detalle(int Id_Detalle, EncabezadoSolicitud encabezado, JornadaAcademica jornadaAcademica, int activo,Recurso recurso) {
        this.Id_Detalle = Id_Detalle;
        this.encabezado = encabezado;
        this.jornadaAcademica = jornadaAcademica;
        this.activo = activo;
        this.recurso = recurso;
    }

    public Detalle() {
    }

    public int getId_Detalle() {
        return Id_Detalle;
    }

    public void setId_Detalle(int Id_Detalle) {
        this.Id_Detalle = Id_Detalle;
    }

    public EncabezadoSolicitud getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(EncabezadoSolicitud encabezado) {
        this.encabezado = encabezado;
    }

    public JornadaAcademica getJornadaAcademica() {
        return jornadaAcademica;
    }

    public void setJornadaAcademica(JornadaAcademica jornadaAcademica) {
        this.jornadaAcademica = jornadaAcademica;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
}
