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
public class RecursoDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Programa> listaD = new LinkedList<Programa>();
    
    public RecursoDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public RecursoDB() {
        super();
    }
    
    public void registrar(Recurso rec) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Recurso recurso = new Recurso(); 
            recurso = rec;             
            
            if(recurso.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "INSERT INTO Recurso (Nombre, Dsc_Recurso, Cantidad, Capacidad, Id_TipoRecurso, Id_Registra, FechaRegistra, Id_Edita, FechaEdita, Log_Activo) "
                    + "VALUES ('" 	+	recurso.getNombre()	+"', '" 
            + recurso.getDescripcion() + "', '" + recurso.cantidad + "', '" + recurso.capacidad + "', '" + recurso.getTipoRecurso().id + "', '" +  recurso.getId_Registra()  + "', '" + new java.sql.Date(recurso.getFechaRegistra().getTime()) + "', '" + recurso.getId_Edita() + "', '" + new java.sql.Date(recurso.getFechaEdita().getTime()) + "', '" + estado +"')"; 
            
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
    
    public void actulizar(Recurso rec) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Recurso recurso = new Recurso(); 
            recurso = rec;             
            
            if(recurso.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "UPDATE Recurso SET "
                     +"Nombre='" +recurso.getNombre() 
                     +"', Dsc_Recurso= '" + recurso.getDescripcion()
                     +"', Cantidad= '" + recurso.getCantidad()
                     +"', Capacidad= '" + recurso.getCapacidad()
                     +"', Id_TipoRecurso= '" + recurso.getTipoRecurso().id
                     +"', Id_Edita= '" + recurso.getId_Edita()
                     +"', FechaEdita= '" + new java.sql.Date(recurso.getFechaEdita().getTime())
                     +"', Log_Activo='" + estado
                     +"' WHERE Id_Recurso='" + recurso.getId()+"';";
                    
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
    
    public  Recurso SeleccionarPorId(int idRecurso) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      Recurso rec = null;
      TipoRecursoDB tdb = new TipoRecursoDB();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Recurso, Nombre, Dsc_Recurso, Cantidad, Capacidad, Id_TipoRecurso, Log_Activo from Recurso WHERE Id_Recurso = " +idRecurso;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Recurso = rsPA.getInt("Id_Recurso");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Recurso = rsPA.getString("Dsc_Recurso");
                        int Cantidad = rsPA.getInt("Cantidad");
                        int Capacidad = rsPA.getInt("Capacidad");
                        TipoRecurso tipoRecurso = tdb.SeleccionarPorId(rsPA.getInt("Id_TipoRecurso"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        rec = new Recurso(Id_Recurso, Nombre, Dsc_Recurso, Cantidad, Capacidad, tipoRecurso, Log_Activo==0? "Inactivo":"Activo");
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
         
          return rec;
      }
    
    public  LinkedList<Recurso> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      TipoRecursoDB tdb = new TipoRecursoDB();
      LinkedList<Recurso> listaRecurso= new LinkedList<Recurso>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Recurso, Nombre, Dsc_Recurso, Cantidad, Capacidad, Id_TipoRecurso, Log_Activo from Recurso";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Recurso = rsPA.getInt("Id_Recurso");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Recurso = rsPA.getString("Dsc_Recurso");
                        int Cantidad = rsPA.getInt("Cantidad");
                        int Capacidad = rsPA.getInt("Capacidad");
                        TipoRecurso tipoRecurso = tdb.SeleccionarPorId(rsPA.getInt("Id_TipoRecurso"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        Recurso rec = new Recurso(Id_Recurso, Nombre, Dsc_Recurso, Cantidad, Capacidad, tipoRecurso, Log_Activo==0? "Inactivo":"Activo");
                        listaRecurso.add(rec);
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
    
    public  LinkedList<Recurso> FiltrarRecurso(String fil) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      String filtro = fil;
      String valor = "-1";
      TipoRecursoDB tdb = new TipoRecursoDB();
      if(filtro.equalsIgnoreCase("Activo")||filtro.equalsIgnoreCase("A")||filtro.equalsIgnoreCase("Ac")||filtro.equalsIgnoreCase("Act")||filtro.equalsIgnoreCase("Acti")||filtro.equalsIgnoreCase("Activ")){
          valor = "1";
      }else{
          if(filtro.equalsIgnoreCase("Inactivo")||filtro.equalsIgnoreCase("I")||filtro.equalsIgnoreCase("In")||filtro.equalsIgnoreCase("Ina")||filtro.equalsIgnoreCase("Inac")||filtro.equalsIgnoreCase("Inact")||filtro.equalsIgnoreCase("Inacti")||filtro.equalsIgnoreCase("Inactiv")){
              valor = "0";
          }
      }
      LinkedList<Recurso> listaRecurso= new LinkedList<Recurso>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Recurso, Nombre, Dsc_Recurso, Cantidad, Capacidad, Id_TipoRecurso, Log_Activo from Recurso WHERE"
                           + " ( Cast(Id_Recurso as nvarchar(5)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Nombre LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Dsc_Recurso LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Cantidad as nvarchar(255)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Capacidad as nvarchar(255)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Log_Activo as nvarchar(5)) LIKE '%' + '" + valor + "' + '%')";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Recurso = rsPA.getInt("Id_Recurso");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Recurso = rsPA.getString("Dsc_Recurso");
                        int Cantidad = rsPA.getInt("Cantidad");
                        int Capacidad = rsPA.getInt("Capacidad");
                        TipoRecurso tipoRecurso = tdb.SeleccionarPorId(rsPA.getInt("Id_TipoRecurso"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        Recurso rec = new Recurso(Id_Recurso, Nombre, Dsc_Recurso, Cantidad, Capacidad, tipoRecurso, Log_Activo==0? "Inactivo":"Activo");
                        listaRecurso.add(rec);
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
