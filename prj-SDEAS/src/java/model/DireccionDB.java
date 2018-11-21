/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.AccesoDatos;
import dao.SNMPExceptions;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Fabi
 */
public class DireccionDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Programa> listaD = new LinkedList<Programa>();
    
    public DireccionDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public DireccionDB() {
        super();
    }
    
      public void registrar(Direccion direc) throws SNMPExceptions, SQLException { 
        String strSQL = "";       
        try {  
            Direccion direccion = new Direccion(); 
             direccion=direc;             
            
            strSQL = "INSERT INTO Direccion (Id_Usuario, Id_Provincia,Id_Canton,Id_Distrito,Id_Barrio,Otras_Sennas ,Log_Activo) VALUES ("+direccion.getUsuario().cedula+","+ direccion.getId_Provincia().Id_Provincia+ "," +  direccion.getId_Canton().Id_Canton+ ", " +direccion.getId_Distrito().Id_Distrito + "," +direccion.getId_Barrio().Id_Barrio + ",'" +direccion.Otras_sennas+"',"+1+")"; 
            
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
}
