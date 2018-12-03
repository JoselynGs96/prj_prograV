/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.AccesoDatos;
import dao.SNMPExceptions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Fabi
 */
public class EncabezadoSolicitudDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public EncabezadoSolicitudDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public EncabezadoSolicitudDB() {
        super();
    }

    public EncabezadoSolicitud SeleccionarporId(int id) throws SNMPExceptions,
            SQLException {
        UsuarioDB usuDB = new UsuarioDB();
        EstadoSolicitudDB estadoDB = new EstadoSolicitudDB();
        TipoSolicitudDB tipoSolDB = new TipoSolicitudDB();
        String select = "";
        ResultSet rsPA = null;
        EncabezadoSolicitud enca = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT [Id_EncSolicitud],[FechaSolicitud] ,[FechaRespuesta],[Id_Funcionario],[Id_Coordinador],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo]FROM [dbo].[EncSolicitud] where Id_EncSolicitud=" + id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Encabezado = rsPA.getInt("Id_EncSolicitud");
                Date FechaInicio = rsPA.getDate("FechaSolicitud");
                Date FechaFinal = rsPA.getDate("FechaSolicitud");
                Usuario Funcionario = usuDB.SeleccionarPorId(rsPA.getInt("Id_Funcionario"));
                Usuario Coordinador = usuDB.SeleccionarPorId(rsPA.getInt("Id_Coordinador"));
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Coordinador, Tipo_solicitud, estado);

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

        return enca;
    }

    public LinkedList<EncabezadoSolicitud> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        UsuarioDB usuDB = new UsuarioDB();
        EstadoSolicitudDB estadoDB = new EstadoSolicitudDB();
        TipoSolicitudDB tipoSolDB = new TipoSolicitudDB();
        String select = "";
        ResultSet rsPA = null;

        LinkedList<EncabezadoSolicitud> listaEncabezadoSolicituds = new LinkedList<EncabezadoSolicitud>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT [Id_EncSolicitud],[FechaSolicitud] ,[FechaRespuesta],[Id_Funcionario],[Id_Coordinador],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo]FROM [dbo].[EncSolicitud]";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Encabezado = rsPA.getInt("Id_EncSolicitud");
                Date FechaInicio = rsPA.getDate("FechaSolicitud");
                Date FechaFinal = rsPA.getDate("FechaSolicitud");
                Usuario Funcionario = usuDB.SeleccionarPorId(rsPA.getInt("Id_Funcionario"));
                Usuario Coordinador = usuDB.SeleccionarPorId(rsPA.getInt("Id_Coordinador"));
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                EncabezadoSolicitud enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Coordinador, Tipo_solicitud, estado);

                listaEncabezadoSolicituds.add(enca);
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

        return listaEncabezadoSolicituds;
    }

    public void registrar(EncabezadoSolicitud encabe) throws SNMPExceptions, SQLException {

        String strSQL = "";

        try {
            EncabezadoSolicitud encabezadoSolicitud = encabe;

            strSQL = "INSERT INTO [dbo].[EncSolicitud]([FechaSolicitud],[Id_Funcionario],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo])VALUES('"  
                +new java.sql.Date(encabezadoSolicitud.getFechaInicio().getTime())+"',"+encabezadoSolicitud.getFuncionario().Id+","+1001+","+4+","+1+")";
            accesoDatos.ejecutaSQL(strSQL/*, sqlBitacora*/);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage());
        } finally {

        }

    }
}
