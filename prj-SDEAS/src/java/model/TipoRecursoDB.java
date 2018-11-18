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
public class TipoRecursoDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public TipoRecursoDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public TipoRecursoDB() {
        super();
    }
    
    public LinkedList<TipoRecurso> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        LinkedList<TipoRecurso> listaTipoRecurso = new LinkedList<TipoRecurso>();
        Barrio barr = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_TipoRecurso,Nombre,Log_Activo from TipoRecurso";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id  = rsPA.getInt("Id_TipoRecurso");
                String nombre = rsPA.getString("Nombre");
                int estado = rsPA.getInt("Log_Activo");
                TipoRecurso tr = new TipoRecurso(id, nombre, estado==0? "Inactivo":"Activo");
                listaTipoRecurso.add(tr);
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

        return listaTipoRecurso;
    }
    
    public  TipoRecurso SeleccionarPorId(int idTipoRecurso) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      TipoRecurso tip = null;
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_TipoRecurso, Nombre, Log_Activo from TipoRecurso WHERE Id_TipoRecurso = " +idTipoRecurso;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int id  = rsPA.getInt("Id_TipoRecurso");
                        String nombre = rsPA.getString("Nombre");
                        int estado = rsPA.getInt("Log_Activo");
                        tip = new TipoRecurso(id, nombre, estado==0? "Inactivo":"Activo");
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
