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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Detalle;
import model.DetalleDB;
import model.Reporte;
import model.ReporteDB;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
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
    LinkedList<Reporte> listaSolicitudes = new LinkedList<>();
    ScheduleModel eventModel;
    ReporteDB reporteDB = null;
    DefaultScheduleEvent evento;
    String msj = "";
    SimpleDateFormat  formato = new SimpleDateFormat("dd-MM-yyyy");
    int idCoord = 0;
    
    public reporteOutlookBean() throws SNMPExceptions {
        inicializar();
    }
    
    
    @PostConstruct
    public void inicializar(){
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
        datos.consultarSesion();
        idCoord = Integer.parseInt(datos.getId_Usuario());
        reporteDB = new ReporteDB();
        eventModel = new DefaultScheduleModel();
        evento = new DefaultScheduleEvent();
        try{
            listaSolicitudes = reporteDB.Reporte(idCoord);
             for (Reporte listaSolicitude : listaSolicitudes) {
                DefaultScheduleEvent evento = new DefaultScheduleEvent();
                evento.setStartDate(listaSolicitude.getFechaInicio());
                evento.setEndDate(listaSolicitude.getFechaFinal());
                evento.setId(listaSolicitude.getEncabezado().getId_Encabezado()+"");
                evento.setTitle("#"+listaSolicitude.getEncabezado().getId_Encabezado()+", "+listaSolicitude.getEncabezado().getTipo_solicitud().getNombre());
                evento.setDescription("Id Solicitud:"+listaSolicitude.getEncabezado().getId_Encabezado()+"\nTipo Solicitud:"+listaSolicitude.getEncabezado().getTipo_solicitud().getNombre() +"\nFuncionario: "+listaSolicitude.getUsuario().getNombreCompleto()+"\n Fecha y Hora de Inicio: "+listaSolicitude.getFechaInicio()+" "+listaSolicitude.getHoraInicio() +"\nFecha y hora Final:"+listaSolicitude.getFechaFinal() +" "+listaSolicitude.getHoraFinal() +"\n Estado: "+listaSolicitude.getEncabezado().getEstado().getNombre());
                evento.setData(listaSolicitude.getEncabezado().getId_Encabezado());
                evento.setEditable(false);
                
                eventModel.addEvent(evento);
             }
             
            
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","Error en ejecutar"));
            setMsj("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Aún no hay eventos</div>");
        }
                
    }
    
    public void seleccionado(SelectEvent sel) throws ParseException{
        ScheduleEvent event = (ScheduleEvent)sel.getObject();
        for (Reporte listaSolicitude : listaSolicitudes) {
            if(listaSolicitude.getEncabezado().getId_Encabezado() == (Integer)event.getData()){
                evento.setId(listaSolicitude.getEncabezado().getId_Encabezado()+"");
                evento.setTitle("#"+listaSolicitude.getEncabezado().getId_Encabezado()+", "+listaSolicitude.getEncabezado().getTipo_solicitud().getNombre());
                evento.setDescription("Id Solicitud:"+listaSolicitude.getEncabezado().getId_Encabezado()+"\nTipo Solicitud:"+listaSolicitude.getEncabezado().getTipo_solicitud().getNombre() +"\nFuncionario: "+listaSolicitude.getUsuario().getNombreCompleto()+"\n Fecha y Hora de Inicio: "+listaSolicitude.getFechaInicio()+" "+listaSolicitude.getHoraInicio() +"\nFecha y hora Final:"+listaSolicitude.getFechaFinal() +" "+listaSolicitude.getHoraFinal() +"\n Estado: "+listaSolicitude.getEncabezado().getEstado().getNombre());
                evento.setData(listaSolicitude.getEncabezado().getId_Encabezado());
                evento.setEditable(false);
                break;
            }
        }
    }

    public LinkedList<Reporte> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setListaSolicitudes(LinkedList<Reporte> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public SimpleDateFormat getFormato() {
        return formato;
    }

    public void setFormato(SimpleDateFormat formato) {
        this.formato = formato;
    }

    
  
    public ReporteDB getReporteDB() {
        return reporteDB;
    }

    public void setReporteDB(ReporteDB reporteDB) {
        this.reporteDB = reporteDB;
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

    public DefaultScheduleEvent getEvento() {
        return evento;
    }

    public void setEvento(DefaultScheduleEvent evento) {
        this.evento = evento;
    }

    public int getIdCoord() {
        return idCoord;
    }

    public void setIdCoord(int idCoord) {
        this.idCoord = idCoord;
    }


 
    
    
    
}
