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
import java.util.LinkedList;

/**
 *
 * @author ujose
 */
public class TipoSolicitudDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public TipoSolicitudDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public TipoSolicitudDB() {
        super();
    }
    
    public LinkedList<TipoSolicitud> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        LinkedList<TipoSolicitud> listaTipoSolicitud = new LinkedList<TipoSolicitud>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_TipoSolicitud,Nombre,Log_Activo from TipoSolicitud";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id  = rsPA.getInt("Id_TipoSolicitud");
                String nombre = rsPA.getString("Nombre");
                int estado = rsPA.getInt("Log_Activo");
                TipoSolicitud ts = new TipoSolicitud(id, nombre, estado==0? "Inactivo":"Activo");
                listaTipoSolicitud.add(ts);
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

        return listaTipoSolicitud;
    }
    
    public  TipoSolicitud SeleccionarPorId(int idTipoSolicitud) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      TipoSolicitud tip = null;
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_TipoSolicitud, Nombre, Log_Activo from TipoSolicitud WHERE Id_TipoSolicitud = " +idTipoSolicitud;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int id  = rsPA.getInt("Id_TipoRecurso");
                        String nombre = rsPA.getString("Nombre");
                        int estado = rsPA.getInt("Log_Activo");
                        tip = new TipoSolicitud(id, nombre, estado==0? "Inactivo":"Activo");
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
         
          return tip;
      }
   
}
