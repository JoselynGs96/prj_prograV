/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SNMPExceptions;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import model.AgendaDB;
import model.DetalleDB;
import model.Direccion;
import model.EncabezadoSolicitudDB;
import model.Provincia;
import model.Recurso;
import model.RecursoDB;
import model.UsuarioDB;

/**
 *
 * @author Fabi
 */
@Named(value = "preestamoBean")
@SessionScoped
public class PreestamoBean implements Serializable {
    
    AgendaDB agendaDB = new AgendaDB();
    EncabezadoSolicitudDB encabezadoDB = new EncabezadoSolicitudDB();
    RecursoDB recursoDB = new RecursoDB();
    DetalleDB detalleDB = new DetalleDB();
    UsuarioDB usuarioDB = new UsuarioDB();
    
    boolean Lunes;
    boolean Martes;
    boolean Miercoles;
    boolean Jueves;
    boolean Viernes;
    boolean Sabado;
    boolean Domingo;
    Date FechaInicio;
    Date FechaFinal;
    Time HoraInicio;
    Time HoraFinal;
    private int recurso = 1000;
    String Observaciones;
    String mensajeError;
    
    LinkedList<Recurso> listaRecurso = new LinkedList<Recurso>();
    LinkedList<Recurso> listaRecursoAgregardos = new LinkedList<Recurso>();
    
    public PreestamoBean() throws SNMPExceptions, SQLException {
        if (!recursoDB.seleccionarTodos().isEmpty()) {
            listaRecurso = recursoDB.seleccionarTodos();
        } else {
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Aún NO existen recurso</div>");
        }
    }
    
    public void agregarRecursos() throws SNMPExceptions, SQLException {
        Recurso recurso = recursoDB.SeleccionarPorId(this.getRecurso());
        listaRecursoAgregardos.add(recurso);
        LinkedList<Recurso> listaRecurso2 = listaRecurso;
        
        for (Recurso re : listaRecurso2) {
            for (Recurso recursoAgregados : listaRecursoAgregardos) {
                if (re.getId() == recurso.getId()) {
                    listaRecurso.remove(re);
                }
            }
            
        }
    }

    public void validaciones() {
        if (this.getFechaInicio() == null) {
            this.setMensajeError("*Debe colocar la fecha de inicio");
        } else {
            if (this.getHoraInicio() == null) {
                this.setMensajeError("*Debe colocar la hora de inicio");
            } else {
                if (this.getFechaFinal() == null) {
                    this.setMensajeError("*Debe colocar la fecha de final");
                } else {
                    if (this.getHoraFinal() == null) {
                        this.setMensajeError("*Debe colocar la hora de final");
                    } else {
                        if (this.getHoraFinal() == null) {
                            this.setMensajeError("*Debe colocar la hora de final");
                        } else {
                            if (validarDiasSeleccionados()) {
                                this.setMensajeError("Debe seleccionar al menos un dia");
                            }else{
                                if(this.getListaRecursoAgregardos().isEmpty()){
                                     this.setMensajeError("Debe agregar al menos un recurso a la lista");
                                }else{
                                    if(this.getObservaciones().equals("")){
                                         this.setMensajeError("Debe colocar alguna observación");
                                    }
                                }
                            }
                        }
                        
                    }
                }
            }
        }
    }

    public boolean validarDiasSeleccionados() {
        boolean respuesta = false;
        return respuesta;
    }
    
    public void eliminarRecursos(int id) throws SNMPExceptions, SQLException {
        LinkedList<Recurso> listaRecurso2 = recursoDB.seleccionarTodos();
        LinkedList<Recurso> listaAgregados = listaRecursoAgregardos;
        Recurso recurso = recursoDB.SeleccionarPorId(id);
        
        for (Recurso re : listaRecurso2) {
            if (re.getId() == id) {
                listaRecurso.add(re);
            }
        }
        for (Recurso re : listaAgregados) {
            if (re.getId() == id) {
                listaRecursoAgregardos.remove(re);
            }
        }
    }
    
    public AgendaDB getAgendaDB() {
        return agendaDB;
    }
    
    public void setAgendaDB(AgendaDB agendaDB) {
        this.agendaDB = agendaDB;
    }
    
    public EncabezadoSolicitudDB getEncabezadoDB() {
        return encabezadoDB;
    }
    
    public void setEncabezadoDB(EncabezadoSolicitudDB encabezadoDB) {
        this.encabezadoDB = encabezadoDB;
    }
    
    public RecursoDB getRecursoDB() {
        return recursoDB;
    }
    
    public void setRecursoDB(RecursoDB recursoDB) {
        this.recursoDB = recursoDB;
    }
    
    public DetalleDB getDetalleDB() {
        return detalleDB;
    }
    
    public void setDetalleDB(DetalleDB detalleDB) {
        this.detalleDB = detalleDB;
    }
    
    public UsuarioDB getUsuarioDB() {
        return usuarioDB;
    }
    
    public void setUsuarioDB(UsuarioDB usuarioDB) {
        this.usuarioDB = usuarioDB;
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
    
    public Time getHoraInicio() {
        return HoraInicio;
    }
    
    public void setHoraInicio(Time HoraInicio) {
        this.HoraInicio = HoraInicio;
    }
    
    public Time getHoraFinal() {
        return HoraFinal;
    }
    
    public void setHoraFinal(Time HoraFinal) {
        this.HoraFinal = HoraFinal;
    }
    
    public int getRecurso() {
        return recurso;
    }
    
    public void setRecurso(int recurso) {
        this.recurso = recurso;
    }
    
    public String getObservaciones() {
        return Observaciones;
    }
    
    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }
    
    public LinkedList<Recurso> getListaRecurso() {
        return listaRecurso;
    }
    
    public void setListaRecurso(LinkedList<Recurso> listaRecurso) {
        this.listaRecurso = listaRecurso;
    }
    
    public String getMensajeError() {
        return mensajeError;
    }
    
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
    public LinkedList<Recurso> getListaRecursoAgregardos() {
        return listaRecursoAgregardos;
    }
    
    public void setListaRecursoAgregardos(LinkedList<Recurso> listaRecursoAgregardos) {
        this.listaRecursoAgregardos = listaRecursoAgregardos;
    }
    
}
