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

/**
 *
 * @author Fabi
 */
public class UsuarioDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public UsuarioDB(Connection conn) {
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

            if (usuario.estado.equals("Activo")) {
                estado = 1;
            }
            strSQL = "INSERT INTO Usuario  ([Id_Usuario],[TipoIdentificacion],[Nombre],[Apellido1],[Apellido2],[FechaNacimiento],[Correo],[Id_EstadoAcceso],[Log_Activo]) values(" + usuario.getCedula() + ",'" + usuario.getTipoIden() + "','" + usuario.getApellido1() + "','" + usuario.getApellido2() + "'," + usuario.getFechaNacimiento() + ",'" + usuario.getCorreo() + "'," + 0 + ")";

            accesoDatos.ejecutaSQL(strSQL/*, sqlBitacora*/);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage());
        } finally {

        }

    }

      
    
    public static Usuario InicioSeccion(int Id_Usuario, String contrasena) throws SNMPExceptions, SQLException {
        String select = "";
        ResultSet rsPA = null;
        Usuario usu = new Usuario();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select= "select * from Usuario where Id_Usuario = " + Id_Usuario + " and PWDCOMPARE('" + contrasena + "',Contrasenna)=1";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                int id = rsPA.getInt("Id_Usuario");
                String Nombre = rsPA.getString("Nombre");
                String Apellido1 = rsPA.getString("Apellido1");
                String Apellido2 = rsPA.getString("Apellido2");
                String Correo= rsPA.getString("Correo"); 
                usu = new Usuario();
                usu.cedula = id;
                usu.nombre = Nombre;
                usu.apellido1 = Apellido1;
                usu.apellido2=Apellido2;
                usu.correo=Correo;

            }

            rsPA.close();

            if (usu != null) {
                return usu;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage());
        } finally {

        }

    }
     public Usuario SeleccionarPorId(int idUsuario) throws SNMPExceptions,
            SQLException {
        String select = "";        
        ResultSet rsPA = null;
        Usuario usu = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "select Id_Usuario, TipoIdentificacion,Nombre,Apellido1,Apellido2,Correo from Usuario where Id_Usuario="+idUsuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

               int Id_Usuario = rsPA.getInt("Id_Usuario");
               int TipoIdentificacion = rsPA.getInt("TipoIdentificacion");
                String Nombre = rsPA.getString("Nombre");
                String Apellido1 = rsPA.getString("Apellido1");
                String Apellido2 = rsPA.getString("Apellido2");
                 String Correo = rsPA.getString("Correo");              
                 usu = new Usuario(Id_Usuario,TipoIdentificacion,Nombre,Apellido1,Apellido2,Correo);          
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

        return usu;
    }

}
