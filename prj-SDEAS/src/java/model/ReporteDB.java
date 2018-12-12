/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.AccesoDatos;
import dao.SNMPExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author ujose
 */
public class ReporteDB {
    
    
     public LinkedList<Reporte> Reporte() throws SNMPExceptions,
            SQLException {
        String select = "";
        EncabezadoSolicitudDB enDB = new EncabezadoSolicitudDB();
        TipoSolicitudDB tDb = new TipoSolicitudDB();
        UsuarioDB uDB = new UsuarioDB();
        EstadoSolicitudDB eDB = new EstadoSolicitudDB();
        ResultSet rsPA = null;
        LinkedList<Reporte> lista = new LinkedList<Reporte>();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm a");
        Agenda agen = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "Select DetSolicitud.Id_EncSolicitud, Id_TipoSolicitud, Id_Funcionario, Id_EstadoSolicitud, FechaInicio, FechaFinal, HoraInicio, HoraFinal from DetSolicitud INNER JOIN  EncSolicitud ON EncSolicitud.Id_EncSolicitud = DetSolicitud.Id_EncSolicitud INNER JOIN AgendaRecurso ON AgendaRecurso.Id_AgendaRecurso  = DetSolicitud.Id_AgendaRecurso";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                
                EncabezadoSolicitud enc = enDB.SeleccionarporId(rsPA.getInt("Id_EncSolicitud"));
                TipoSolicitud tip = tDb.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                Usuario usu = uDB.SeleccionarPorId(rsPA.getInt("Id_Funcionario"));
                EstadoSolicitud est = eDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                Date fechaInicio = rsPA.getDate("FechaInicio");
                Date fechaFinal = rsPA.getDate("FechaFinal");
                Date horaInicio = rsPA.getTime("HoraInicio");
                Date horaFinal = rsPA.getTime("HoraFinal");
                Reporte reporte = new Reporte();
                reporte.setEncabezado(enc);
                reporte.setTipoSoli(tip);
                reporte.setUsuario(usu);
                reporte.setEstado(est);
                reporte.setFechaInicio(fechaInicio);
                reporte.setFechaFinal(fechaFinal);
                reporte.setHoraInicio(horaInicio);
                reporte.setHoraFinal(horaFinal);
                
                lista.add(reporte);
            }

            rsPA.close();

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage());
        } finally {

        }

        return lista;
    }
}
