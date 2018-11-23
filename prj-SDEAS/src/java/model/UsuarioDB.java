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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.ParseConversionEvent;
import model.Usuario;

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

        try {
            Usuario usuario = new Usuario();
            usuario = usu;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object

            strSQL = "INSERT INTO Usuario ([Id_Usuario],[Id_TipoIdentificacion],[Nombre],[Apellido1],[Apellido2],[FechaNacimiento],[Correo],[Id_RolUsuario],[Id_EstadoAcceso],[Log_Activo]) values(" + usuario.cedula + "," + usuario.TipoIden.getId_TipoIdentificacion() + ",'" + usuario.nombre + "','" + usuario.apellido1 + "','" + usuario.apellido2 + "','" + fecha+ "','" + usuario.getCorreo() + "'," + usuario.rolUsuario.Id_RolUsuario + "," + 3 + "," + 1 + ")";
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

            select = "select * from Usuario where Id_Usuario = " + Id_Usuario + " and PWDCOMPARE('" + contrasena + "',Contrasenna)=1";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                String id = rsPA.getInt("Id_Usuario") + "";

                String Nombre = rsPA.getString("Nombre");
                String Apellido1 = rsPA.getString("Apellido1");
                String Apellido2 = rsPA.getString("Apellido2");
                String Correo = rsPA.getString("Correo");
                usu = new Usuario();
                usu.cedula = id;
                usu.nombre = Nombre;
                usu.apellido1 = Apellido1;
                usu.apellido2 = Apellido2;
                usu.correo = Correo;

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
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "select Id_Usuario, TipoIdentificacion,Nombre,Apellido1,Apellido2,Correo from Usuario where Id_Usuario=" + idUsuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                String Id_Usuario = rsPA.getInt("Id_Usuario") + "";
                TipoIdentificacion tipoide = tipoIdenDB.SeleccionarPorId(rsPA.getInt("TipoIdentificacion"));
                String Nombre = rsPA.getString("Nombre");
                String Apellido1 = rsPA.getString("Apellido1");
                String Apellido2 = rsPA.getString("Apellido2");
                String Correo = rsPA.getString("Correo");
                usu = new Usuario();
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

    public void IngresarContrasenna(Usuario usu)throws SNMPExceptions, SQLException{
         String strSQL = "";

        try {
            Usuario usuario = new Usuario();
            usuario = usu;
            
            strSQL = "UPDATE Usuario SET Contrasenna = PWDENCRYPT('"+usu.getContrasenna()+"') WHERE Id_Usuario = "+usu.getCedula();
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
}
