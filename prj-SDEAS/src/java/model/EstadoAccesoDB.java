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
public class EstadoAccesoDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public EstadoAccesoDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public EstadoAccesoDB() {
        super();
    }
    
    public LinkedList<EstadoAcceso> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        LinkedList<EstadoAcceso> listaEstadoAcceso = new LinkedList<EstadoAcceso>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_EstadoAcceso,Dsc_EstadoAcceso,Log_Activo from EstadoAcceso";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id  = rsPA.getInt("Id_EstadoAcceso");
                String nombre = rsPA.getString("Dsc_EstadoAcceso");
                int estado = rsPA.getInt("Log_Activo");
                EstadoAcceso ea = new EstadoAcceso(id, nombre, estado==0? "Inactivo":"Activo");
                listaEstadoAcceso.add(ea);
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

        return listaEstadoAcceso;
    }
    
    public  EstadoAcceso SeleccionarPorId(int idEstadoAcceso) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      EstadoAcceso ea = null;
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_EstadoAcceso, Dsc_EstadoAcceso, Log_Activo from EstadoAcceso WHERE Id_EstadoAcceso = " +idEstadoAcceso;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int id  = rsPA.getInt("Id_EstadoAcceso");
                        String nombre = rsPA.getString("Dsc_EstadoAcceso");
                        int estado = rsPA.getInt("Log_Activo");
                        ea = new EstadoAcceso(id, nombre, estado==0? "Inactivo":"Activo");
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
