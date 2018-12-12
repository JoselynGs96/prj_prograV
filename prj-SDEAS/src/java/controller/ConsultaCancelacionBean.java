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
import java.util.LinkedList;
import model.Agenda;
import model.AgendaDB;
import model.DetalleDB;
import model.EncabezadoSolicitud;
import model.EncabezadoSolicitudDB;
import model.EstadoSolicitudDB;
import model.Recurso;
import model.UsuarioMante;
import model.UsuarioManteDB;

/**
 *
 * @author ujose
 */
@Named(value = "consultaCancelacionBean")
@SessionScoped
public class ConsultaCancelacionBean implements Serializable {

    int IdUsuario = 0;
    String msj = "";
    ObtenerDatosSesion datos = null;
    EncabezadoSolicitudDB encDB = new EncabezadoSolicitudDB();
    DetalleDB detDB = new DetalleDB();
    AgendaDB agenDB = new AgendaDB();
    LinkedList<EncabezadoSolicitud> listaSolicitud = new LinkedList<EncabezadoSolicitud>();
    LinkedList<Recurso> listaRecurso = new LinkedList<Recurso>();

    /**
     * Creates a new instance of ConsultaCancelacionBean
     */
    public ConsultaCancelacionBean() throws SNMPExceptions, SQLException {
        datos = new ObtenerDatosSesion();

        datos.consultarSesion();
        if (!datos.getId_Usuario().equals("")) {
            IdUsuario = Integer.parseInt(datos.getId_Usuario());
        }

        llenarTabla();
    }

    public void llenarTabla() throws SNMPExceptions, SQLException {
        if (!encDB.SeleccionarTodosPorId(IdUsuario).isEmpty()) {
            listaSolicitud = encDB.SeleccionarTodosPorId(IdUsuario);
        } else {
            setMsj("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Usted no ha realizado ninguna solicitud</div>");
        }

    }

    public void cancelar(int id) throws SNMPExceptions, SQLException {
        listaRecurso = detDB.buscarRecursos(id);
        EncabezadoSolicitudDB ddd = new EncabezadoSolicitudDB();
         AgendaDB ageDB = new AgendaDB();
        EncabezadoSolicitud enca = ddd.SeleccionarporId(id);
        UsuarioMante usu = new UsuarioManteDB().SeleccionarPorId(enca.getFuncionario().getId());
        LinkedList<Agenda> listaAgenda = new LinkedList<Agenda>();
         listaAgenda = agenDB.SeleccionarTodosPorEncabezado(id);
        try {
            EstadoSolicitudDB estadodb = new EstadoSolicitudDB();
            enca.setLog(0);            
            ddd.ActualizarEstadoSolicitud(enca);
            
              for (Agenda re : listaAgenda) {
                 ageDB.ActualizarEstadoSolicitud(re.getId_Agenda());
              }

        } catch (Exception e) {

        }
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public EncabezadoSolicitudDB getEncDB() {
        return encDB;
    }

    public void setEncDB(EncabezadoSolicitudDB encDB) {
        this.encDB = encDB;
    }

    public LinkedList<EncabezadoSolicitud> getListaSolicitud() {
        return listaSolicitud;
    }

    public void setListaSolicitud(LinkedList<EncabezadoSolicitud> listaSolicitud) {
        this.listaSolicitud = listaSolicitud;
    }

}
