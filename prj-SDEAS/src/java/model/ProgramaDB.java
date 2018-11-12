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
public class ProgramaDB {
   private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Programa> listaD = new LinkedList<Programa>();
    
    public ProgramaDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public ProgramaDB() {
        super();
    }
    
    public void registrar(Programa pro) throws SNMPExceptions, SQLException { 
        String strSQL = "";         
        try {  
            Programa programa = new Programa(); 
            programa = pro;             
            
             strSQL = "INSERT INTO Programa (Nombre, Dsc_Programa, Log_Activo) "
                    + "VALUES ('" 	+	programa.getNombre()	+"', '" 
            + programa.getDescripcion() + "', '" +  programa.getEstado() +"')"; 
            
            accesoDatos.ejecutaSQL(strSQL/*, sqlBitacora*/);  
        } catch (SQLException e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage(), e.getErrorCode());         
        }catch (Exception e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage()); 
        } finally { 
         
        } 
    } 
    
    
    public  LinkedList<Programa> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      
      LinkedList<Programa> listaPrograma= new LinkedList<Programa>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Programa, Nombre, Dsc_Programa, Log_Activo from Programa";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Programa = rsPA.getInt("Id_Programa");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Programa = rsPA.getString("Dsc_Programa");
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        Programa pro = new Programa(Id_Programa, Nombre, Dsc_Programa, Log_Activo);
                        listaPrograma.add(pro);
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
         
          return listaPrograma;
      }
}
