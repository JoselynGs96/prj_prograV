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
public class TipoIdentificacionDB {
      private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Programa> listaD = new LinkedList<Programa>();
    
    public TipoIdentificacionDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public TipoIdentificacionDB() {
        super();
    }
      public  LinkedList<TipoIdentificacion> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      UsuarioDB usu = new UsuarioDB();
      
      LinkedList<TipoIdentificacion> listaTipo= new LinkedList<TipoIdentificacion>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "select Id_TipoIdentificacion,Dsc_TipoIdentificacion from TipoIdentificacion ";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_TipoIdentificacion = rsPA.getInt("Id_TipoIdentificacion");                
                        String Dsc_TipoIdentificacion= rsPA.getString("Dsc_TipoIdentificacion");
                       
                        TipoIdentificacion tip = new TipoIdentificacion(Id_TipoIdentificacion, Dsc_TipoIdentificacion);
                        listaTipo.add(tip);
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
         
          return listaTipo;
      }
      
       public  TipoIdentificacion SeleccionarPorId(int tipoIdentificacion) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      TipoIdentificacion tip = null;
   
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_TipoIdentificacion, Dsc_TipoIdentificacion from TipoIdentificacion WHERE Id_TipoIdentificacion = " +tipoIdentificacion;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int id = rsPA.getInt("Id_TipoIdentificacion");
                        String Nombre = rsPA.getString("Dsc_TipoIdentificacion");                      
                        tip = new TipoIdentificacion(id,Nombre);
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
