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
import java.util.Date;
import java.util.LinkedList;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.font;
import model.Detalle;
import model.DetalleDB;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author ujose
 */
@Named(value = "reporteOutlookBean")
@SessionScoped
public class reporteOutlookBean implements Serializable {

    /**
     * Creates a new instance of reporteOutlookBean
     */
    LinkedList<Detalle> listaSolicitudes = new LinkedList<>();
    ScheduleModel eventModel = new DefaultScheduleModel();
    Date fecha = new Date();
    DetalleDB ddb = new DetalleDB();
    String msj = "";
    
    public reporteOutlookBean() throws SNMPExceptions {
        llenarLista();
    }
    
    public void llenarLista() throws SNMPExceptions{
        if(ddb.SeleccionarTodos().isEmpty()){
            setMsj("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Aún no hay eventos</div>");
        }else{
            for (Detalle listaSolicitude : listaSolicitudes) {
                eventModel.addEvent(new DefaultScheduleEvent("Champions League Match",fecha,fecha ));
            }
        }
    }

    public LinkedList<Detalle> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setListaSolicitudes(LinkedList<Detalle> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public DetalleDB getDdb() {
        return ddb;
    }

    public void setDdb(DetalleDB ddb) {
        this.ddb = ddb;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
