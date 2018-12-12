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
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Fabi
 */
public class AgendaDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Canton> listaD = new LinkedList<Canton>();

    public AgendaDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public AgendaDB() {
        super();
    }

    public void registrar(Agenda agen) throws SNMPExceptions, SQLException {
        String strSQL = "";
        int Lunes = 0;
        int Martes = 0;
        int Miercoles = 0;
        int Jueves = 0;
        int Viernes = 0;
        int Sabado = 0;
        int Domingo = 0;
        try {
            Agenda agenda = new Agenda();
            agenda = agen;

            if (agenda.isLunes()) {
                Lunes = 1;
            }
            if (agenda.isMartes()) {
                Martes = 1;
            }
            if (agenda.isMiercoles()) {
                Miercoles = 1;
            }
            if (agenda.isJueves()) {
                Jueves = 1;
            }
            if (agenda.isViernes()) {
                Viernes = 1;
            }
            if (agenda.isSabado()) {
                Sabado = 1;
            }
            if (agenda.isDomingo()) {
                Domingo = 1;
            }

            strSQL = "INSERT INTO [dbo].[AgendaRecurso]\n"
                    + "           ([Lunes]\n"
                    + "           ,[Martes]\n"
                    + "           ,[Miercoles]\n"
                    + "           ,[Jueves]\n"
                    + "           ,[Viernes]\n"
                    + "           ,[Sabado]\n"
                    + "           ,[Domingo]\n"
                    + "           ,[FechaInicio]\n"
                    + "           ,[FechaFinal]\n"
                    + "           ,[HoraInicio]\n"
                    + "           ,[HoraFinal]\n"
                    + "           ,[Id_Recurso]\n"
                    + "           ,[Id_Registra]\n"
                    + "           ,[FechaRegistra]\n"
                    + "            ,[Observaciones]\n"
                    + "           ,[Log_Activo])\n"
                    + "     VALUES\n"
                    + " (" + Lunes + "," + Martes + "," + Miercoles + "," + Jueves + "," + Viernes + "," + Sabado + "," + Domingo + ",'" + new java.sql.Date(agenda.getFechaInicio().getTime()) + "','" + new java.sql.Date(agenda.getFechaFinal().getTime()) + "','" + new java.sql.Time(agenda.getHoraInicio().getTime()) + "','" + new java.sql.Time(agenda.getHoraFinal().getTime()) + "'," + agenda.recurso.id
                    + "," + agenda.Id_Registra + ", GETDATE() ,'" + agenda.Obseraciones + "'," + 1 + ")";

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

    public LinkedList<Agenda> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        RecursoDB recursoDB = new RecursoDB();
        ResultSet rsPA = null;
        LinkedList<Agenda> listaAgenda = new LinkedList<Agenda>();
        Agenda agen = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT [Id_AgendaRecurso]\n"
                    + "      ,[Lunes]\n"
                    + "      ,[Martes]\n"
                    + "      ,[Miercoles]\n"
                    + "      ,[Jueves]\n"
                    + "      ,[Viernes]\n"
                    + "      ,[Sabado]\n"
                    + "      ,[Domingo]\n"
                    + "      ,[FechaInicio]\n"
                    + "      ,[FechaFinal]\n"
                    + "      ,[HoraInicio]\n"
                    + "      ,[HoraFinal]\n"
                    + "      ,[Id_Recurso]  \n"
                    +"       ,[Observaciones]\n"
                    + "      ,[Log_Activo]\n"
                    + "  FROM [dbo].[AgendaRecurso]";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Agenda = rsPA.getInt("Id_AgendaRecurso");
                boolean Lunes = (rsPA.getInt("Lunes") == 0 ? true : false);
                boolean Martes = (rsPA.getInt("Martes") == 0 ? true : false);
                boolean Miercoles = (rsPA.getInt("Miercoles") == 0 ? true : false);
                boolean Jueves = (rsPA.getInt("Jueves") == 0 ? true : false);
                boolean Viernes = (rsPA.getInt("Viernes") == 0 ? true : false);
                boolean Sabado = (rsPA.getInt("Sabado") == 0 ? true : false);
                boolean Domingo = (rsPA.getInt("Domingo") == 0 ? true : false);
                Date FechaInicio = rsPA.getDate("FechaInicio");
                Date FechaFinal = rsPA.getDate("FechaFinal");
                Time HoraInicio = rsPA.getTime("HoraInicio");
                Time HoraFinal = rsPA.getTime("HoraFinal");
                Recurso recurso = recursoDB.SeleccionarPorId(rsPA.getInt("Id_Recurso"));
                       String Observaciones = rsPA.getString("Observaciones");
                int activo = rsPA.getInt("Log_Activo");
                 
                 agen = new Agenda(id_Agenda, Lunes, Martes, Miercoles, Jueves, Viernes, Sabado, Domingo, FechaInicio, FechaFinal, HoraInicio, HoraFinal, recurso, activo, id_Agenda, FechaFinal, id_Agenda, FechaFinal, Observaciones);
                listaAgenda.add(agen);
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

        return listaAgenda;
    }

    public LinkedList<Agenda> SeleccionarTodosPorEncabezado(int id) throws SNMPExceptions,
            SQLException {
        String select = "";
        RecursoDB recursoDB = new RecursoDB();
        ResultSet rsPA = null;
        LinkedList<Agenda> listaAgenda = new LinkedList<Agenda>();
        Agenda agen = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT AgendaRecurso.Id_AgendaRecurso\n"
                    + ",AgendaRecurso.Lunes\n"
                    + ",AgendaRecurso.Martes\n"
                    + ",AgendaRecurso.Miercoles\n"
                    + ",AgendaRecurso.Jueves\n"
                    + ",AgendaRecurso.Viernes\n"
                    + ",AgendaRecurso.Sabado\n"
                    + ",AgendaRecurso.Domingo\n"
                    + ",AgendaRecurso.FechaInicio\n"
                    + ",AgendaRecurso.FechaFinal\n"
                    + ",AgendaRecurso.HoraInicio\n"
                    + ",AgendaRecurso.HoraFinal\n"
                    + ",AgendaRecurso.Id_Recurso\n"
                    +",AgendaRecurso.Observaciones\n"
                    + ",AgendaRecurso.Log_Activo\n"
                    + "from DetSolicitud INNER JOIN EncSolicitud ON EncSolicitud.Id_EncSolicitud = DetSolicitud.Id_EncSolicitud\n"
                    + "INNER JOIN AgendaRecurso  ON AgendaRecurso.Id_AgendaRecurso  = DetSolicitud.Id_AgendaRecurso \n"
                    + "where EncSolicitud.Id_EncSolicitud="+id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Agenda = rsPA.getInt("Id_AgendaRecurso");
                boolean Lunes = (rsPA.getInt("Lunes") == 0 ? true : false);
                boolean Martes = (rsPA.getInt("Martes") == 0 ? true : false);
                boolean Miercoles = (rsPA.getInt("Miercoles") == 0 ? true : false);
                boolean Jueves = (rsPA.getInt("Jueves") == 0 ? true : false);
                boolean Viernes = (rsPA.getInt("Viernes") == 0 ? true : false);
                boolean Sabado = (rsPA.getInt("Sabado") == 0 ? true : false);
                boolean Domingo = (rsPA.getInt("Domingo") == 0 ? true : false);
                Date FechaInicio = rsPA.getDate("FechaInicio");
                Date FechaFinal = rsPA.getDate("FechaFinal");
                Time HoraInicio = rsPA.getTime("HoraInicio");
                Time HoraFinal = rsPA.getTime("HoraFinal");
                Recurso recurso = recursoDB.SeleccionarPorId(rsPA.getInt("Id_Recurso"));
                String Observaciones = rsPA.getString("Observaciones");
                int activo = rsPA.getInt("Log_Activo");
                 
                 agen = new Agenda(id_Agenda, Lunes, Martes, Miercoles, Jueves, Viernes, Sabado, Domingo, FechaInicio, FechaFinal, HoraInicio, HoraFinal, recurso, activo, id_Agenda, FechaFinal, id_Agenda, FechaFinal, Observaciones);

                listaAgenda.add(agen);
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

        return listaAgenda;
    }

    public Agenda SeleccionarId(int id) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        Agenda agen = null;
        RecursoDB recursoDB = new RecursoDB();
        AgendaDB agendaDB = new AgendaDB();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT [Id_AgendaRecurso]\n"
                    + "      ,[Lunes]\n"
                    + "      ,[Martes]\n"
                    + "      ,[Miercoles]\n"
                    + "      ,[Jueves]\n"
                    + "      ,[Viernes]\n"
                    + "      ,[Sabado]\n"
                    + "      ,[Domingo]\n"
                    + "      ,[FechaInicio]\n"
                    + "      ,[FechaFinal]\n"
                    + "      ,[HoraInicio]\n"
                    + "      ,[HoraFinal]\n"
                    + "      ,[Id_Recurso]\n"
                    + "      ,[Log_Activo]\n"
                    + "      ,[Obseraciones]\n"
                    + "  FROM [dbo].[AgendaRecurso] where Id_AgendaRecurso=" + id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                int id_Agenda = rsPA.getInt("Id_AgendaRecurso");
                boolean Lunes = (rsPA.getInt("Lunes")) == 1 ? true : false;
                boolean Martes = (rsPA.getInt("Martes")) == 1 ? true : false;
                boolean Miercoles = (rsPA.getInt("Miercoles")) == 1 ? true : false;
                boolean Jueves = (rsPA.getInt("Jueves")) == 1 ? true : false;
                boolean Viernes = (rsPA.getInt("Viernes")) == 1 ? true : false;
                boolean Sabado = (rsPA.getInt("Sabado")) == 1 ? true : false;
                boolean Domingo = (rsPA.getInt("Domingo")) == 1 ? true : false;
                Date FechaInicio = rsPA.getDate("FechaInicio");
                Date FechaFinal = rsPA.getDate("FechaFinal");;
                Date HoraInicio = rsPA.getDate("HoraInicio");
                Date HoraFinal = rsPA.getDate("HoraFinal");
                Recurso recurso = recursoDB.SeleccionarPorId(rsPA.getInt("Id_Recurso"));
                int activo = rsPA.getInt("Log_Activo");
                String Obseraciones = rsPA.getString("Obseraciones");
                agen = new Agenda(id_Agenda, Lunes, Martes, Miercoles, Jueves, Viernes, Sabado, Domingo, FechaInicio, FechaFinal, HoraInicio, HoraFinal, recurso, activo, id_Agenda, FechaFinal, id_Agenda, FechaFinal, Obseraciones);

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

        return agen;
    }

    public int SeleccionarExistente(Agenda agenda) throws SNMPExceptions,
            SQLException {
        int Lunes = agenda.isLunes() ? 1 : 2;
        int Martes = agenda.isMartes() ? 1 : 2;
        int Miercoles = agenda.isMiercoles() ? 1 : 2;
        int Jueves = agenda.isJueves() ? 1 : 2;
        int Viernes = agenda.isViernes() ? 1 : 2;
        int Sabado = agenda.isSabado() ? 1 : 2;
        int Domingo = agenda.isDomingo() ? 1 : 2;

        String select = "";
        ResultSet rsPA = null;
        int agen = 0;
        AgendaDB agendaDB = new AgendaDB();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select = "Select Id_DetSolicitud\n"
                    + "From Recurso inner join AgendaRecurso\n"
                    + "On Recurso.Id_Recurso = AgendaRecurso.Id_Recurso inner join DetSolicitud \n"
                    + "On AgendaRecurso.Id_AgendaRecurso =DetSolicitud.Id_AgendaRecurso\n"
                    + "Where Recurso.Id_Recurso =" + agenda.getRecurso().id + " and (('" + new java.sql.Date(agenda.getFechaInicio().getTime()) + "' between AgendaRecurso.FechaInicio and AgendaRecurso.FechaFinal) OR ('" + new java.sql.Date(agenda.getFechaFinal().getTime()) + "' between AgendaRecurso.FechaInicio and AgendaRecurso.FechaFinal)\n"
                    + "Or (AgendaRecurso.FechaInicio between '" + new java.sql.Date(agenda.getFechaInicio().getTime()) + "' and '" + new java.sql.Date(agenda.getFechaFinal().getTime()) + "' or AgendaRecurso.FechaFinal between '" + new java.sql.Date(agenda.getFechaInicio().getTime()) + "' and '" + new java.sql.Date(agenda.getFechaFinal().getTime()) + "'))\n"
                    + "and (('" + new java.sql.Time(agenda.getHoraInicio().getTime()) + "' between AgendaRecurso.HoraInicio and AgendaRecurso.HoraFinal Or '" + new java.sql.Time(agenda.getHoraFinal().getTime()) + "' between AgendaRecurso.HoraInicio and AgendaRecurso.HoraFinal)\n"
                    + "or (AgendaRecurso.HoraInicio between '" + new java.sql.Time(agenda.getHoraInicio().getTime()) + "' and '" + new java.sql.Time(agenda.getHoraFinal().getTime()) + "' or AgendaRecurso.HoraFinal between '" + new java.sql.Time(agenda.getHoraInicio().getTime()) + "' and '" + new java.sql.Time(agenda.getHoraFinal().getTime()) + "'))\n"
                    + "And (Lunes =" + Lunes + " or Martes = " + Martes + "or Miercoles = " + Miercoles + " or Jueves = " + Jueves + " or Viernes = " + Viernes + " or Sabado = " + Sabado + " or Domingo = " + Domingo + " )\n"
                    + "and DetSolicitud.Log_Activo = 1\n";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                agen = rsPA.getInt("Id_DetSolicitud");
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

        return agen;
    }

    public int SeleccionarUltimo() throws SNMPExceptions,
            SQLException {
        int max = 0;
        String select = "";
        ResultSet rsPA = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT  MAX(Id_AgendaRecurso) as Maximo\n"
                    + "FROM AgendaRecurso";
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

     public void ActualizarEstadoSolicitud(int id) throws SNMPExceptions{
        String strSQL = "";   
        int estado = 0;
        try {  
             
             strSQL = "UPDATE AgendaRecurso SET "                 
                     +"Log_Activo=0"
                     +"where Id_AgendaRecurso="+id;
                   
                    
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
    
    
    public void CancelarSolicitud(int id) throws SNMPExceptions{
        String strSQL = "";   
        int estado = 0;
        try {  
            Programa programa = new Programa(); 
            
            if(programa.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "UPDATE Programa SET "
                     +"Nombre='" +programa.getNombre() 
                     +"', Dsc_Programa= '" + programa.getDescripcion()
                     +"', Id_Edita= '" + programa.getId_Edita()
                     +"', FechaEdita= '" + new java.sql.Date(programa.getFechaEdita().getTime())
                     +"', Log_Activo='" + estado
                     +"' WHERE Id_Programa='" + programa.getId()+"';";
                    
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
