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
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Tipo_solicitud, estado);

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

    public LinkedList<EncabezadoSolicitud> SeleccionarTodosporCoordinador(int id) throws SNMPExceptions,
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
                    = "SELECT [Id_EncSolicitud],[FechaSolicitud] ,[FechaRespuesta],[Id_Funcionario],[Id_Coordinador],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo]FROM [dbo].[EncSolicitud]where Id_Coordinador=" + id+" and Log_Activo=1";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Encabezado = rsPA.getInt("Id_EncSolicitud");
                Date FechaInicio = rsPA.getDate("FechaSolicitud");
                Date FechaFinal = rsPA.getDate("FechaSolicitud");
                Usuario Funcionario = usuDB.SeleccionarPorId(rsPA.getInt("Id_Funcionario"));
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                EncabezadoSolicitud enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Tipo_solicitud, estado);

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

    public int SeleccionarUltimo() throws SNMPExceptions,
            SQLException {
        int max = 0;
        String select = "";
        ResultSet rsPA = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT  MAX(Id_EncSolicitud ) as Maximo\n"
                    + "FROM EncSolicitud";
            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                max = rsPA.getInt("Maximo");
                if (max == 0) {
                    max = 1000;
                }
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

        return max;
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
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                EncabezadoSolicitud enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Tipo_solicitud, estado);

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

    public LinkedList<EncabezadoSolicitud> FiltrarEncabezado(String fil) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        String filtro = fil;
        String valorEstado = "-1";
        String valorTipoSo = "-1";
        TipoIdentificacionDB tipoIdentificacion = new TipoIdentificacionDB();
        EstadoSolicitudDB estadoDB = new EstadoSolicitudDB();
        UsuarioDB usuDB = new UsuarioDB();
        TipoSolicitudDB tipoSolDB = new TipoSolicitudDB();

        if (filtro.equalsIgnoreCase("Pendiente") || filtro.equalsIgnoreCase("Pen") || filtro.equalsIgnoreCase("Pend") || filtro.equalsIgnoreCase("Pendi") || filtro.equalsIgnoreCase("Pendie") || filtro.equalsIgnoreCase("Pendien") || filtro.equalsIgnoreCase("Pendient")) {
            valorEstado = "4";
        } else {
            if (filtro.equalsIgnoreCase("Aceptada") || filtro.equalsIgnoreCase("Acep") || filtro.equalsIgnoreCase("Acept") || filtro.equalsIgnoreCase("Acepta") || filtro.equalsIgnoreCase("Aceptad")) {
                valorEstado = "2";
            } else {
                if (filtro.equalsIgnoreCase("Denegada") || filtro.equalsIgnoreCase("Dene") || filtro.equalsIgnoreCase("Deneg") || filtro.equalsIgnoreCase("Denega") || filtro.equalsIgnoreCase("Denegad")) {
                    valorEstado = "3";
                }
            }
            if (filtro.equalsIgnoreCase("Evento") || filtro.equalsIgnoreCase("Eve") || filtro.equalsIgnoreCase("Even") || filtro.equalsIgnoreCase("Event")) {
                valorTipoSo = "4";
            } else {
                if (filtro.equalsIgnoreCase("Jornada Académica") || filtro.equalsIgnoreCase("Jorn") || filtro.equalsIgnoreCase("Jornad") || filtro.equalsIgnoreCase("Jornada") || filtro.equalsIgnoreCase("Académica") || filtro.equalsIgnoreCase("Aca") || filtro.equalsIgnoreCase("Acade") || filtro.equalsIgnoreCase("Académi") || filtro.equalsIgnoreCase("Académic")) {
                    valorTipoSo = "2";
                }
            }
        }

        LinkedList<EncabezadoSolicitud> listaEncabezadoSolicituds = new LinkedList<EncabezadoSolicitud>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT [Id_EncSolicitud],[FechaSolicitud] ,[FechaRespuesta],[Id_Funcionario],[Id_Coordinador],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo] from EncSolicitud\n"
                    + " WHERE ( Cast(Id_EncSolicitud as nvarchar(20)) LIKE '%' + '" + filtro + "' + '%') AND Id_EstadoSolicitud != 3\n"
                    + "OR ( FechaRespuesta LIKE '%' + '" + filtro + "' + '%') AND [Id_EstadoSolicitud] != 3\n"
                    + "OR ( FechaSolicitud LIKE '%' + '" + filtro + "' + '%') AND [Id_EstadoSolicitud] != 3\n"
                    + "OR ( Id_TipoSolicitud LIKE '%' + '" + valorTipoSo + "' + '%') AND [Id_EstadoSolicitud] !=3\n"
                    + "OR ( [Id_EstadoSolicitud]  LIKE '%' + '" + valorEstado + "' + '%')AND [Id_EstadoSolicitud] !=3\n";
            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Encabezado = rsPA.getInt("Id_EncSolicitud");
                Date FechaInicio = rsPA.getDate("FechaSolicitud");
                Date FechaFinal = rsPA.getDate("FechaSolicitud");
                Usuario Funcionario = usuDB.SeleccionarPorId(rsPA.getInt("Id_Funcionario"));
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                EncabezadoSolicitud enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Tipo_solicitud, estado);

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

    public LinkedList<EncabezadoSolicitud> SeleccionarTodosPorId(int id) throws SNMPExceptions,
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
                    = "SELECT [Id_EncSolicitud],[FechaSolicitud] ,[FechaRespuesta],[Id_Funcionario],[Id_Coordinador],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo]FROM [dbo].[EncSolicitud] WHERE Id_Funcionario = " + id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Encabezado = rsPA.getInt("Id_EncSolicitud");
                Date FechaInicio = rsPA.getDate("FechaSolicitud");
                Date FechaFinal = rsPA.getDate("FechaSolicitud");
                Usuario Funcionario = usuDB.SeleccionarPorId(rsPA.getInt("Id_Funcionario"));
                TipoSolicitud Tipo_solicitud = tipoSolDB.SeleccionarPorId(rsPA.getInt("Id_TipoSolicitud"));
                EstadoSolicitud estado = estadoDB.SeleccionarPorId(rsPA.getInt("Id_EstadoSolicitud"));
                EncabezadoSolicitud enca = new EncabezadoSolicitud(id_Encabezado, FechaInicio, FechaFinal, Funcionario, Tipo_solicitud, estado);

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

            strSQL = "INSERT INTO [dbo].[EncSolicitud]([FechaSolicitud],[Id_Funcionario],[Id_Coordinador],[Id_TipoSolicitud],[Id_EstadoSolicitud],[Log_Activo])VALUES(  GETDATE()  " + "," + encabezadoSolicitud.getFuncionario().Id + "," + encabezadoSolicitud.getCoordinador().Id + "," + 1001 + "," + 4 + "," + 1 + ")";
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

    public void ActualizarEstadoSolicitud(EncabezadoSolicitud encabe) throws SNMPExceptions, SQLException {

        String strSQL = "";

        try {
            EncabezadoSolicitud encabezadoSolicitud = encabe;

            strSQL = "UPDATE [dbo].[EncSolicitud]\n"
                    + "   SET [FechaRespuesta] = GETDATE() "
                    + "      ,[Id_EstadoSolicitud] = "+ encabe.estado.id
                    +",       [Log_Activo]="+ encabe.log
                    + " WHERE Id_EncSolicitud="+encabe.id_Encabezado;
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
