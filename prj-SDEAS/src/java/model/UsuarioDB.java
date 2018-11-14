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

/**
 *
 * @author Fabi
 */
public class UsuarioDB {
      private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  
    
     public UsuarioDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
       public UsuarioDB() {
        super();
    }
       public void registrar(Usuario usu) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Usuario usuario = new Usuario(); 
            usuario = usu;             
            
            if(usuario.estado.equals("Activo")){
                estado = 1;
            }
             strSQL = "INSERT INTO Usuario  ([Id_Usuario],[TipoIdentificacion],[Nombre],[Apellido1],[Apellido2],[FechaNacimiento],[Correo],[Id_EstadoAcceso],[Log_Activo]) values("+usuario.getCedula()+",'"+usuario.getTipoIden()+"','"+usuario.getApellido1()+"','"+usuario.getApellido2()+"',"+usuario.getFechaNacimiento()+",'"+usuario.getCorreo()+"',"+0+")"; 
            
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
