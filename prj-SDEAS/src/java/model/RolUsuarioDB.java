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
 * @author Fabi
 */
public class RolUsuarioDB {    
    int Id_RolUsuario;
    String Dsc_RolUsuario;
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<RolUsuario> listaD = new LinkedList<RolUsuario>();

    public RolUsuarioDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public RolUsuarioDB() {
        super();
    }
    
     public LinkedList<RolUsuario> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";      
        ResultSet rsPA = null;
        LinkedList<RolUsuario> listaRol = new LinkedList<RolUsuario>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "  SELECT  Id_RolUsuario, Dsc_RolUsuario, Log_Activo from RolUsuario ";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_RolUsuario = rsPA.getInt("Id_RolUsuario");
                String Dsc_RolUsuario = rsPA.getString("Dsc_RolUsuario");
                int Log_Activo = rsPA.getInt("Log_Activo");
               RolUsuario rol = new RolUsuario(Id_RolUsuario, Dsc_RolUsuario, Log_Activo==0? "Inactivo":"Activo");
                listaRol.add(rol);
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

        return listaRol;
    }
     
     public  RolUsuario SeleccionarPorId(int idRolUsuario) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      RolUsuario rol = null;
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_RolUsuario, Dsc_RolUsuario,  Log_Activo from RolUsuario WHERE Id_RolUsuario = " +idRolUsuario;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_RolUsuario = rsPA.getInt("Id_RolUsuario");
                        String Dsc_RolUsuario = rsPA.getString("Dsc_RolUsuario");
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        rol = new RolUsuario(Id_RolUsuario, Dsc_RolUsuario, Log_Activo==0? "Inactivo":"Activo");
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
         
          return rol;
      }
}
