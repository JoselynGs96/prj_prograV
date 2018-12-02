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
        int estado = 0;
        try {  
            Programa programa = new Programa(); 
            programa = pro;             
            
            if(programa.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "INSERT INTO Programa (Nombre, Dsc_Programa, Id_Registra, FechaRegistra, Id_Edita, FechaEdita, Log_Activo) "
                    + "VALUES ('" 	
                     +	programa.getNombre()	
                     +"', '"  + programa.getDescripcion() 
                     + "', '" +  programa.getId_Registra()  
                     + "', '" + new java.sql.Date(programa.getFechaRegistra().getTime()) 
                     + "', '" + programa.getId_Edita() 
                     + "', '" + new java.sql.Date(programa.getFechaEdita().getTime())
                     +  "', '" +  estado +"')"; 
            
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
    
    
    public void actulizar(Programa pro) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Programa programa = new Programa(); 
            programa = pro;             
            
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
        }catch (Exception e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage()); 
        } finally { 
         
        } 
    } 
    
    
    public  Programa SeleccionarPorId(int idPrograma) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      Programa pro = null;
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Programa, Nombre, Dsc_Programa, Log_Activo from Programa WHERE Id_Programa = " +idPrograma;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Programa = rsPA.getInt("Id_Programa");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Programa = rsPA.getString("Dsc_Programa");
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        pro = new Programa(Id_Programa, Nombre, Dsc_Programa, Log_Activo==0? "Inactivo":"Activo");
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
         
          return pro;
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
                        Programa pro = new Programa(Id_Programa, Nombre, Dsc_Programa, Log_Activo==0? "Inactivo":"Activo");
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
    
    
    public  LinkedList<Programa> FiltrarPrograma(String fil) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      String filtro = fil;
      String valor = "-1";
      if(filtro.equalsIgnoreCase("Activo")||filtro.equalsIgnoreCase("A")||filtro.equalsIgnoreCase("Ac")||filtro.equalsIgnoreCase("Act")||filtro.equalsIgnoreCase("Acti")||filtro.equalsIgnoreCase("Activ")){
          valor = "1";
      }else{
          if(filtro.equalsIgnoreCase("Inactivo")||filtro.equalsIgnoreCase("I")||filtro.equalsIgnoreCase("In")||filtro.equalsIgnoreCase("Ina")||filtro.equalsIgnoreCase("Inac")||filtro.equalsIgnoreCase("Inact")||filtro.equalsIgnoreCase("Inacti")||filtro.equalsIgnoreCase("Inactiv")){
              valor = "0";
          }
      }
      LinkedList<Programa> listaPrograma= new LinkedList<Programa>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Programa, Nombre, Dsc_Programa, Log_Activo from Programa WHERE"
                           + "   ( Cast(Id_Programa as nvarchar(5)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Nombre LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Dsc_Programa LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Log_Activo as nvarchar(5)) LIKE '%' + '" + valor + "' + '%')";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Programa = rsPA.getInt("Id_Programa");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Programa = rsPA.getString("Dsc_Programa");
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        Programa pro = new Programa(Id_Programa, Nombre, Dsc_Programa, Log_Activo==0? "Inactivo":"Activo");
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
