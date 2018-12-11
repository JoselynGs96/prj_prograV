/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ObtenerDatosSesion;
import dao.SNMPExceptions;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import model.UsuarioMante;
import model.UsuarioManteDB;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author ujose
 */
@Named(value = "mantenimientoBean")
@SessionScoped
public class MantenimientoBean implements Serializable {

    /**
     * Creates a new instance of MantenimientoBean
     */
    UsuarioMante usuario = null;
    Date fecha = new Date();
    int IdRegistra = 0;
    int IdEdita = 0;
    private ScheduleModel eventModel;
     
    public MantenimientoBean() throws SNMPExceptions, SQLException {
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
        UsuarioManteDB usuDB = new UsuarioManteDB();
        datos.consultarSesion();
        
        if(!datos.getId_Usuario().equals("")){
            usuario = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
            IdRegistra = usuario.getId();
            IdEdita = usuario.getId();
        }else{
            usuario = usuDB.SeleccionarPorId(116390998);
            IdRegistra = usuario.getId();
            IdEdita = usuario.getId();
        }
        
        llenarCalendario();
    
    }

    public void llenarCalendario(){
        eventModel = new DefaultScheduleModel();
        eventModel.addEvent(new DefaultScheduleEvent("Evento: despedida", fecha,fecha));
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
    
    
    
    
    
    
    
    
    
    
    public UsuarioMante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMante usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdRegistra() {
        return IdRegistra;
    }

    public void setIdRegistra(int IdRegistra) {
        this.IdRegistra = IdRegistra;
    }

    public int getIdEdita() {
        return IdEdita;
    }

    public void setIdEdita(int IdEdita) {
        this.IdEdita = IdEdita;
    }
    
    
    
    
}
