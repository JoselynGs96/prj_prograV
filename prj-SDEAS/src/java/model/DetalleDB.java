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
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Fabi
 */
public class DetalleDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public DetalleDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public DetalleDB() {
        super();
    }

    public void registrar(Detalle deta) throws SNMPExceptions, SQLException {

        String strSQL = "";

        try {
            Detalle detalle = deta;

            strSQL = "INSERT INTO [dbo].[DetSolicitud]\n"
                    + "           ([Id_EncSolicitud]\n"
                    + "           ,[Id_AgendaRecurso]\n"
                    + "           ,[Log_Activo])\n"
                    + "     VALUES("+detalle.getEncabezado().id_Encabezado+","+detalle.getAgenda().id_Agenda+","
                   + 1 + ")";
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

    public LinkedList<Detalle> SeleccionarTodos() throws SNMPExceptions{
        String select = "";
      ResultSet rsPA = null;
      RecursoDB rDB = new RecursoDB();
      JornadaAcademicaDB jDB = new JornadaAcademicaDB();
      EncabezadoSolicitudDB eDB = new EncabezadoSolicitudDB();
      LinkedList<Detalle> listaDetalle= new LinkedList<Detalle>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_DetSolicitud, Id_EncSolicitud, Id_Recurso, Id_JornadaAcademica from DetSolicitud ";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {
                        int Id_det = rsPA.getInt("Id_DetSolicitud");
                        EncabezadoSolicitud encabezado = eDB.SeleccionarporId(rsPA.getInt("Id_EncSolicitud"));
                        Recurso recurso = rDB.SeleccionarPorId(rsPA.getInt("Id_Recurso"));
                        JornadaAcademica jornada = !rsPA.getString("Id_JornadaAcademica").equals("")? jDB.SeleccionarPorId(rsPA.getInt("Id_JornadaAcademica")) : null;
                        Detalle det = new Detalle();
                        listaDetalle.add(det);
                       
                      }
              
            rsPA.close();
              
          } catch (SQLException e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage(), e.getErrorCode());
          }catch (Exception e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage());
          } finally {
              
          }
         
          return listaDetalle;
    }
    
    public LinkedList<Recurso> buscarRecursos(int idEnc) throws SNMPExceptions{
      String select = "";
      ResultSet rsPA = null;
      RecursoDB rDB = new RecursoDB();
      LinkedList<Recurso> listaRecurso= new LinkedList<Recurso>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Recurso from DetSolicitud WHERE Id_EncSolicitud = "+ idEnc;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {
                        Recurso recurso = rDB.SeleccionarPorId(rsPA.getInt("Id_Recurso"));
                        listaRecurso.add(recurso);
                       
                      }
              
            rsPA.close();
              
          } catch (SQLException e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage(), e.getErrorCode());
          }catch (Exception e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage());
          } finally {
              
          }
         
          return listaRecurso;
    }
    
    
}
