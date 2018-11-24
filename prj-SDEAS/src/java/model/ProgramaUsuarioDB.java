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
public class ProgramaUsuarioDB {
    
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    
    
    public ProgramaUsuarioDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public ProgramaUsuarioDB() {
        super();
    }
    
    public void registrar(ProgramaUsuario prousu) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            ProgramaUsuario pu = new ProgramaUsuario(); 
            pu = prousu;             
            
            if(pu.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "INSERT INTO Programa_Usuario (Id_Programa, Id_Usuario, Id_RolUsuario, Log_Activo) "
                    + "VALUES ('" 	+	pu.getUsuario().id	+"', '" 
            + pu.getPrograma().id + "', '" +  pu.rolUsuario.Id_RolUsuario + "', '"+ estado +"')"; 
            
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
    
    
    public void actulizar(ProgramaUsuario prousu) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            ProgramaUsuario pu = new ProgramaUsuario(); 
            pu = prousu;           
            
            if(pu.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "UPDATE Programa_Usuario SET "
                     +"Id_Programa='" + pu.getPrograma().id
                     +"', Id_Usuario= '" + pu.getUsuario().id
                     +"', Id_RolUsuario= '" + pu.getRolUsuario().Id_RolUsuario
                     +"', Log_Activo='" + estado
                     +"' WHERE Id_Programa='" + pu.getPrograma().id +"'AND Id_Usuario='"+ pu.getUsuario().id +"'AND Id_RolUsuario='"+pu.getRolUsuario().Id_RolUsuario+"';";
                    
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
    
    public  ProgramaUsuario SeleccionarPorId(int id) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      ProgramaUsuario p = null;
      ProgramaDB pro = new ProgramaDB();
      UsuarioDB usu = new UsuarioDB();
      RolUsuarioDB rol = new RolUsuarioDB();
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Programa, Id_RolUsuario, Log_Activo from Programa_Usuario WHERE Id_Usuario = " +id;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        Programa Id_Programa = pro.SeleccionarPorId(rsPA.getInt("Id_Programa")) ;
                        RolUsuario Id_RolUsuario = rol.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        p = new ProgramaUsuario(Id_Programa, Id_RolUsuario, Log_Activo==0? "Inactivo":"Activo");
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
         
          return p;
      }
    
    public  LinkedList<ProgramaUsuario> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      ProgramaUsuario p = null;
      ProgramaDB pro = new ProgramaDB();
      UsuarioDB usu = new UsuarioDB();
      RolUsuarioDB rol = new RolUsuarioDB();
      
      LinkedList<ProgramaUsuario> listaProgramaUsuario= new LinkedList<ProgramaUsuario>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                  select = 
                      "SELECT Id_Programa, Id_Usuario, Id_RolUsuario, Log_Activo from Programa_Usuario";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        Programa Id_Programa = pro.SeleccionarPorId(rsPA.getInt("Id_Programa")) ;
                        Usuario Id_Usuario = usu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
                        RolUsuario Id_RolUsuario = rol.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                        int Log_Activo = rsPA.getInt("Log_Activo");
                        ProgramaUsuario pu = new ProgramaUsuario( Id_Usuario, Id_Programa, Id_RolUsuario, Log_Activo==0? "Inactivo":"Activo");
                        listaProgramaUsuario.add(pu);
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
         
          return listaProgramaUsuario;
      }
}
