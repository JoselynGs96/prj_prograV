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
public class CursoDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Curso> listaD = new LinkedList<Curso>();
    
    public CursoDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public CursoDB() {
        super();
    }
    
    public void registrar(Curso cur) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Curso curso = new Curso(); 
            curso = cur;             
            
            if(curso.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "INSERT INTO Curso (Nombre, Dsc_Curso, Id_Programa, Log_Activo) "
                    + "VALUES ('" 	+	curso.getNombre()	+"', '" 
            + curso.getDescripcion() + "', '"  + curso.getPrograma().id+ "', '"+ estado +"')"; 
            
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
    
    
    public void actulizar(Curso cur) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Curso curso = new Curso(); 
            curso = cur;             
            
            if(curso.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "UPDATE Curso SET "
                     +"Nombre='" +curso.getNombre() 
                     +"', Dsc_Curso= '" + curso.getDescripcion()
                     +"', Id_Programa='" + curso.getPrograma().id
                     +"', Log_Activo='" + estado
                     +"' WHERE Id_Curso='" + curso.getId()+"';";
                    
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
    
    
    public  Curso SeleccionarPorId(int idCurso) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      Curso cur = null;
      ProgramaDB proDB = new ProgramaDB();
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Curso, Nombre, Dsc_Curso, Id_Programa, Log_Activo from Curso WHERE Id_Curso = " +idCurso;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Curso = rsPA.getInt("Id_Curso");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Curso = rsPA.getString("Dsc_Curso");
                        Programa Programa = proDB.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        cur = new Curso(Id_Curso, Nombre, Dsc_Curso, Programa, Log_Activo==0? "Inactivo":"Activo");
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
         
          return cur;
      }
    
    
    public  LinkedList<Curso> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      ProgramaDB proDB = new ProgramaDB();
      LinkedList<Curso> listaCurso = new LinkedList<Curso>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Curso, Nombre, Dsc_Curso, Id_Programa, Log_Activo from Curso";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Curso = rsPA.getInt("Id_Curso");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Curso = rsPA.getString("Dsc_Curso");
                        Programa Programa = proDB.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        Curso cur = new Curso(Id_Curso, Nombre, Dsc_Curso, Programa, Log_Activo==0? "Inactivo":"Activo");
                        listaCurso.add(cur);
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
         
          return listaCurso;
      }
    
    
    public  LinkedList<Curso> FiltrarCurso(String fil) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      String filtro = fil;
      String valor = "-1";
      ProgramaDB proDB = new ProgramaDB();
      if(filtro.equalsIgnoreCase("Activo")||filtro.equalsIgnoreCase("A")||filtro.equalsIgnoreCase("Ac")||filtro.equalsIgnoreCase("Act")||filtro.equalsIgnoreCase("Acti")||filtro.equalsIgnoreCase("Activ")){
          valor = "1";
      }else{
          if(filtro.equalsIgnoreCase("Inactivo")||filtro.equalsIgnoreCase("I")||filtro.equalsIgnoreCase("In")||filtro.equalsIgnoreCase("Ina")||filtro.equalsIgnoreCase("Inac")||filtro.equalsIgnoreCase("Inact")||filtro.equalsIgnoreCase("Inacti")||filtro.equalsIgnoreCase("Inactiv")){
              valor = "0";
          }
      }
      LinkedList<Curso> listaCurso = new LinkedList<Curso>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Curso, Nombre, Dsc_Curso, Id_Programa, Log_Activo from Curso WHERE"
                           + "   ( Cast(Id_Curso as nvarchar(5)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Nombre LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Dsc_Curso LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Log_Activo as nvarchar(5)) LIKE '%' + '" + valor + "' + '%')";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_Curso = rsPA.getInt("Id_Curso");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_Curso = rsPA.getString("Dsc_Curso");
                        Programa Programa = proDB.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        Curso cur = new Curso(Id_Curso, Nombre, Dsc_Curso, Programa, Log_Activo==0? "Inactivo":"Activo");
                        listaCurso.add(cur);
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
         
          return listaCurso;
      }
}
