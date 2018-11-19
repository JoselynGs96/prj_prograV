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
public class EstadoSolicitudDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public EstadoSolicitudDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public EstadoSolicitudDB() {
        super();
    }
    
    public LinkedList<EstadoSolicitud> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        LinkedList<EstadoSolicitud> listaEstadoSolicitud = new LinkedList<EstadoSolicitud>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_EstadoSolicitud,Nombre,Log_Activo from EstadoSolicitud";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id  = rsPA.getInt("Id_TipoSolicitud");
                String nombre = rsPA.getString("Nombre");
                int estado = rsPA.getInt("Log_Activo");
                EstadoSolicitud ea = new EstadoSolicitud(id, nombre, estado==0? "Inactivo":"Activo");
                listaEstadoSolicitud.add(ea);
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

        return listaEstadoSolicitud;
    }
    
    public  EstadoSolicitud SeleccionarPorId(int idEstadoSolicitud) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      EstadoSolicitud ea = null;
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_EstadoSolicitud, Nombre, Log_Activo from EstadoSolicitud WHERE Id_EstadoSolicitud = " +idEstadoSolicitud;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int id  = rsPA.getInt("Id_TipoRecurso");
                        String nombre = rsPA.getString("Nombre");
                        int estado = rsPA.getInt("Log_Activo");
                        ea = new EstadoSolicitud(id, nombre, estado==0? "Inactivo":"Activo");
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
         
          return ea;
      }
}
