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
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author ujose
 */
public class JornadaAcademicaDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Programa> listaD = new LinkedList<Programa>();
    
    public JornadaAcademicaDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public JornadaAcademicaDB() {
        super();
    }
    
    public void registrar(JornadaAcademica jor) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            JornadaAcademica jornada = new JornadaAcademica(); 
            jornada = jor;             
            
            if(jornada.Log_Activo.equals("Activo")){
                estado = 1;
            }
           
            
             strSQL = "INSERT INTO JornadaAcademica (Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio,"
                     + "HoraFinal, Id_Curso, Log_Activo) "
                    + "VALUES ('" 	+	jornada.getNombre()	+"', '" 
            + jornada.getDsc_JornadaAcademica() + "', '" +  jornada.FechaInicio + "', '" + jornada.FechaFinal + "', '" + jornada.HoraInicio
            + "', '" + jornada.HoraFinal + "', '" + jornada.getCurso().id + "', '" + estado +"')"; 
            
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
    
    
    public void actulizar(JornadaAcademica jor) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            JornadaAcademica jornada = new JornadaAcademica(); 
            jornada = jor;             
            
            if(jornada.Log_Activo.equals("Activo")){
                estado = 1;
            }
             strSQL = "UPDATE JornadaAcademica SET "
                     +"Nombre='" + jornada.getNombre() 
                     +"', Dsc_JornadaAcademica= '" + jornada.getDsc_JornadaAcademica()
                     +"', FechaInicio='" + jornada.getFechaInicio()
                     +"', FechaFinal='" + jornada.getFechaFinal()
                     +"', HoraInicio='" + jornada.getHoraInicio()
                     +"', HoraFinal='" + jornada.getHoraFinal()
                     +"', Curso='" + jornada.getCurso().id
                     +"', Log_Activo='" + estado
                     +"' WHERE Id_JornadaAcademica='" + jornada.getId_JornadaAcademica()+"';";
                    
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
    
    
    public  JornadaAcademica SeleccionarPorId(int idJornada) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      JornadaAcademica jor = null;
      CursoDB cr = new CursoDB();
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_JornadaAcademica, Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio, HoraFinal, Id_Curso, Log_Activo from JornadaAcademica WHERE Id_JornadaAcademica = " +idJornada;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_JornadaAcademica = rsPA.getInt("Id_JornadaAcademica");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_JornadaAcademica = rsPA.getString("Dsc_JornadaAcademica");
                        Date FechaInicio = rsPA.getDate("FechaInicio");
                        Date FechaFinal = rsPA.getDate("FechaFinal");
                        Time HoraInicio = rsPA.getTime("HoraInicio");
                        Time HoraFinal = rsPA.getTime("HoraFinal");
                        Curso Id_Curso = cr.SeleccionarPorId(rsPA.getInt("Id_Curso"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        jor = new JornadaAcademica(Id_JornadaAcademica, Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio, HoraFinal, Id_Curso, Log_Activo==0? "Inactivo":"Activo");
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
         
          return jor;
      }
    
    
    public  LinkedList<JornadaAcademica> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      CursoDB cr = new CursoDB();
      LinkedList<JornadaAcademica> listaJornadaAcademica= new LinkedList<JornadaAcademica>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_JornadaAcademica, Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio, HoraFinal, Id_Curso, Log_Activo from JornadaAcademica";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_JornadaAcademica = rsPA.getInt("Id_JornadaAcademica");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_JornadaAcademica = rsPA.getString("Dsc_JornadaAcademica");
                        Date FechaInicio = rsPA.getDate("FechaInicio");
                        Date FechaFinal = rsPA.getDate("FechaFinal");
                        Time HoraInicio = rsPA.getTime("HoraInicio");
                        Time HoraFinal = rsPA.getTime("HoraFinal");
                        Curso Id_Curso = cr.SeleccionarPorId(rsPA.getInt("Id_Curso"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        JornadaAcademica jor = new JornadaAcademica(Id_JornadaAcademica, Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio, HoraFinal, Id_Curso, Log_Activo==0? "Inactivo":"Activo");
                        listaJornadaAcademica.add(jor);
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
         
          return listaJornadaAcademica;
      }
    
    
    public  LinkedList<JornadaAcademica> FiltrarJornadaAcademica(String fil) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      String filtro = fil;
      String valor = "-1";
      CursoDB cr = new CursoDB();
      if(filtro.equalsIgnoreCase("Activo")||filtro.equalsIgnoreCase("A")||filtro.equalsIgnoreCase("Ac")||filtro.equalsIgnoreCase("Act")||filtro.equalsIgnoreCase("Acti")||filtro.equalsIgnoreCase("Activ")){
          valor = "1";
      }else{
          if(filtro.equalsIgnoreCase("Inactivo")||filtro.equalsIgnoreCase("I")||filtro.equalsIgnoreCase("In")||filtro.equalsIgnoreCase("Ina")||filtro.equalsIgnoreCase("Inac")||filtro.equalsIgnoreCase("Inact")||filtro.equalsIgnoreCase("Inacti")||filtro.equalsIgnoreCase("Inactiv")){
              valor = "0";
          }
      }
      LinkedList<JornadaAcademica> listaJornadaAcademica= new LinkedList<JornadaAcademica>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_JornadaAcademica, Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio, HoraFinal, Id_Curso, Log_Activo from JornadaAcademica WHERE"
                           + "   ( Cast(Id_JornadaAcademica as nvarchar(5)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Nombre LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Dsc_JornadaAcademica LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(FechaInicio as nvarchar(10)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(FechaFinal as nvarchar(10)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(HoraInicio as nvarchar(10)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(HoraFinal as nvarchar(10)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Log_Activo as nvarchar(5)) LIKE '%' + '" + valor + "' + '%')";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id_JornadaAcademica = rsPA.getInt("Id_JornadaAcademica");
                        String Nombre = rsPA.getString("Nombre");
                        String Dsc_JornadaAcademica = rsPA.getString("Dsc_JornadaAcademica");
                        Date FechaInicio = rsPA.getDate("FechaInicio");
                        Date FechaFinal = rsPA.getDate("FechaFinal");
                        Time HoraInicio = rsPA.getTime("HoraInicio");
                        Time HoraFinal = rsPA.getTime("HoraFinal");
                        Curso Id_Curso = cr.SeleccionarPorId(rsPA.getInt("Id_Curso"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        JornadaAcademica jor = new JornadaAcademica(Id_JornadaAcademica, Nombre, Dsc_JornadaAcademica, FechaInicio, FechaFinal, HoraInicio, HoraFinal, Id_Curso, Log_Activo==0? "Inactivo":"Activo");
                        listaJornadaAcademica.add(jor);
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
         
          return listaJornadaAcademica;
      }
}
