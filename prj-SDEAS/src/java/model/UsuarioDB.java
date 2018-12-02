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
import java.util.LinkedList;

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

    /*Selecciona todos los usuarios*/
    public LinkedList<Usuario> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        RolUsuarioDB rolDb = new RolUsuarioDB();
        String select = "";
        ResultSet rsPA = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select = "Select Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo, CodigoAcceso,Id_RolUsuario from Usuario";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int cedula = rsPA.getInt("Id_Usuario");
                TipoIdentificacion TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                Date fechaNacimiento = rsPA.getDate("FechaNacimiento");
                String correo = rsPA.getString("Correo");
                EstadoAcceso estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                String Log_Act = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                RolUsuario rol = rolDb.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                Usuario usu = new Usuario(cedula, TipoIden, nombre, apellido1, apellido2, fechaNacimiento, correo, estAcc, Log_Act, rol);

                listaUsuario.add(usu);
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

        return listaUsuario;
    }

    /*Busca usuario por id*/
    public Usuario SeleccionarPorId(int idUsuario) throws SNMPExceptions,
            SQLException {
        RolUsuarioDB rolDb = new RolUsuarioDB();
        String select = "";
        ResultSet rsPA = null;
        Usuario usu = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        usu = new Usuario();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "Select Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo, CodigoAcceso,Id_RolUsuario from Usuario WHERE Id_Usuario=" + idUsuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int cedula = rsPA.getInt("Id_Usuario");
                TipoIdentificacion TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                Date fechaNacimiento = rsPA.getDate("FechaNacimiento");
                String correo = rsPA.getString("Correo");
                EstadoAcceso estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                String Log_Act = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                RolUsuario rol = rolDb.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                usu = new Usuario(cedula, TipoIden, nombre, apellido1, apellido2, fechaNacimiento, correo, estAcc, Log_Act, rol);

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

    /*REgistra un usuario*/
    public void registrar(Usuario usuario) throws SNMPExceptions, SQLException {

        String strSQL = "";

        try {
            Usuario usu = usuario;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object

            strSQL = "INSERT INTO Usuario ([Id_Usuario],[Id_TipoIdentificacion],[Nombre],[Apellido1],[Apellido2],[FechaNacimiento],[Correo],[Id_EstadoAcceso],Id_Registra,FechaRegistra,Id_Edita,FechaEdita,[Log_Activo]) values('" 
                    + usuario.Id + "','" + usuario.TipoIdentificacion.getId_TipoIdentificacion() 
                    + "','" + usuario.Nombre + "','" + usuario.Apellido1 + "','" 
                    + usuario.Apellido2 + "','" +  new java.sql.Date(usuario.getFechaNacimiento().getTime()) + "','" + usuario.getCorreo() 
                    + "','" + 2 
                    + "', '" +  usuario.getId_Registra()  + "', '"+  new java.sql.Date(usuario.getFechaRegistra().getTime()) + "', '" + usuario.getId_Edita() + "', '" + new java.sql.Date(usuario.getFechaEdita().getTime()) + "', '" + 1 +"')";
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

    public void ActualizarUsuario(Usuario usu) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            Usuario usuario = usu;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object     

            strSQL = "UPDATE [dbo].[Usuario] SET [Nombre] ='" +  usuario.Nombre + 
                    "',[Apellido1]="+usuario.Apellido1
                    + "', [Apellido2] ='" +usuario.Apellido2
                    + "', [FechaNacimiento] ='" +fecha
                    + "', [Correo] ='"+usuario.Correo
                    + "', Id_Edita = '"+usuario.Id_Edita
                    + "', FechaEdita = '"+new java.sql.Date(usuario.getFechaEdita().getTime())
                    +"' where [Id_Usuario]= '"+usuario.Id+"';";

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

    /*Busca el usuario logeado*/
    public static Usuario InicioSeccion(String Id_Usuario, String contrasena, String tipoUsuario) throws SNMPExceptions, SQLException {
        String select = "";
        ResultSet rsPA = null;
        Usuario usu = new Usuario();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select = "select * from Usuario where Id_Usuario = " + Id_Usuario + " and PWDCOMPARE('" + contrasena + "',Contrasenna)=1 and Id_RolUsuario=" + tipoUsuario + " and Id_EstadoAcceso=1";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int cedula = rsPA.getInt("Id_Usuario");
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                String correo = rsPA.getString("Correo");
                usu = new Usuario();
                usu.setId(cedula);
                usu.setNombre(nombre);
                usu.setApellido1(apellido1);
                usu.setApellido2(apellido2);

            }

            rsPA.close();

            if (usu.getId() != 0) {
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

    /*Ingresa contraseña por primera vez*/
    public void IngresarContrasenna(Usuario usu) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            Usuario usuario = new Usuario();
            usuario = usu;

            strSQL = "UPDATE Usuario SET Contrasenna = PWDENCRYPT('" + usu.getContrasenna() + "') WHERE Id_Usuario = " + usu.getId();
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
