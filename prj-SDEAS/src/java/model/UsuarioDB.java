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
        String select = "";
        ResultSet rsPA = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select = "Select Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int ced = rsPA.getInt("Id_Usuario");
                String cedula = rsPA.getInt("Id_Usuario") + "";
                TipoIdentificacion TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                Date fechaNacimiento = rsPA.getDate("FechaNacimiento");
                String correo = rsPA.getString("Correo");
                int estado = rsPA.getInt("Log_Activo");
                EstadoAcceso estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                String esta = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                Usuario usu = new Usuario(ced, cedula, TipoIden, fechaNacimiento, correo, estado, nombre, apellido1, apellido2, esta, estAcc);
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
    public Usuario SeleccionarPorId(String idUsuario) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        Usuario usu = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        usu = new Usuario();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "Select Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario WHERE Id_Usuario=" + idUsuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int ced = rsPA.getInt("Id_Usuario");
                String cedula = rsPA.getInt("Id_Usuario") + "";
                TipoIdentificacion TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                Date fechaNacimiento = rsPA.getDate("FechaNacimiento");
                String correo = rsPA.getString("Correo");
                int estado = rsPA.getInt("Log_Activo");
                EstadoAcceso estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                String esta = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                usu = new Usuario(ced, cedula, TipoIden, fechaNacimiento, correo, estado, nombre, apellido1, apellido2, esta, estAcc);

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
    public void registrar(Usuario usu) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            Usuario usuario = new Usuario();
            usuario = usu;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object

            strSQL = "INSERT INTO Usuario ([Id_Usuario],[Id_TipoIdentificacion],[Nombre],[Apellido1],[Apellido2],[FechaNacimiento],[Correo],[Id_RolUsuario],[Id_EstadoAcceso],[TipoFuncionario],[Log_Activo],Id_Registra,FechaRegistra,Id_Edita,FechaEdita) values(" 
                    + usuario.cedula + "," + usuario.TipoIden.getId_TipoIdentificacion() 
                    + ",'" + usuario.nombre + "','" + usuario.apellido1 + "','" 
                    + usuario.apellido2 + "','" + fecha + "','" + usuario.getCorreo() 
                    + "'," + 2 + "," + 3 + ",'" + usuario.Funcionario.toString() + "'," + 1 
                    + "', '" +  usuario.getId_Registra()  + "', '"+  new java.sql.Date(usuario.getFechaRegistra().getTime()) + "', '" + usuario.getId_Edita() + "', '" + new java.sql.Date(usuario.getFechaEdita().getTime()) + ")";
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
    
   public void ActualizarUsuario(Usuario usu) throws SNMPExceptions, SQLException{
         String strSQL = "";

        try {
            Usuario usuario = new Usuario();
            usuario = usu;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object

            strSQL = "UPDATE [dbo].[Usuario] SET [Nombre] ='" +  usuario.nombre + 
                    "',[Apellido1]="+usuario.apellido1+"', "
                    + "[Apellido2] ='" +usuario.apellido2+"', "
                    + "[FechaNacimiento] ='" +fecha+"', "
                    + "[Correo] ='"+usuario.correo+"',"
                    + "[TipoFuncionario] ="+usuario.Funcionario.toString()
                    + "Id_Edita = "+usuario.Id_Edita
                    + "FechaEdita = "+new java.sql.Date(usuario.getFechaEdita().getTime())
                    +"'where [Id_Usuario]="+usuario.cedula;

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

            select = "select * from Usuario where Id_Usuario = " + Id_Usuario + " and PWDCOMPARE('" + contrasena + "',Contrasenna)=1 and Id_RolUsuario=" + tipoUsuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                String id = rsPA.getInt("Id_Usuario") + "";

                String Nombre = rsPA.getString("Nombre");
                String Apellido1 = rsPA.getString("Apellido1");
                String Apellido2 = rsPA.getString("Apellido2");
                String Correo = rsPA.getString("Correo");
                int rol = rsPA.getInt("Id_RolUsuario");
                usu = new Usuario();
                usu.cedula = id;
                usu.nombre = Nombre;
                usu.apellido1 = Apellido1;
                usu.apellido2 = Apellido2;
                usu.correo = Correo;
                usu.rolUsuario = new RolUsuario();
                usu.rolUsuario.setId_RolUsuario(rol);
                usu.rolUsuario.setDsc_RolUsuario(rol == 1 ? "Coordinador" : "Funcionario");

            }

            rsPA.close();

            if (!usu.cedula.equals("0")) {
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

            strSQL = "UPDATE Usuario SET Contrasenna = PWDENCRYPT('" + usu.getContrasenna() + "') WHERE Id_Usuario = " + usu.getCedula();
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

    public LinkedList<Usuario> SeleccionarTodos2() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select = "Select Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int ced = rsPA.getInt("Id_Usuario");
                String cedula = rsPA.getInt("Id_Usuario") + "";
                TipoIdentificacion TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                Date fechaNacimiento = rsPA.getDate("FechaNacimiento");
                String correo = rsPA.getString("Correo");
                int estado = rsPA.getInt("Log_Activo");
                EstadoAcceso estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                String esta = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                Usuario usu = new Usuario(ced, cedula, TipoIden, fechaNacimiento, correo, estado, nombre, apellido1, apellido2, esta, estAcc);
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

    public LinkedList<Usuario> FiltrarUsuario(String fil) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        String filtro = fil;
        String valor = "-1";
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        if (filtro.equalsIgnoreCase("Activo") || filtro.equalsIgnoreCase("A") || filtro.equalsIgnoreCase("Ac") || filtro.equalsIgnoreCase("Act") || filtro.equalsIgnoreCase("Acti") || filtro.equalsIgnoreCase("Activ")) {
            valor = "1";
        } else {
            if (filtro.equalsIgnoreCase("Inactivo") || filtro.equalsIgnoreCase("I") || filtro.equalsIgnoreCase("In") || filtro.equalsIgnoreCase("Ina") || filtro.equalsIgnoreCase("Inac") || filtro.equalsIgnoreCase("Inact") || filtro.equalsIgnoreCase("Inacti") || filtro.equalsIgnoreCase("Inactiv")) {
                valor = "0";
            }
        }
        LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "Select Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario WHERE"
                    + " ( Cast(Id_Usuario as nvarchar(20)) LIKE '%' + '" + filtro + "' + '%')"
                    + "OR ( Nombre LIKE '%' + '" + filtro + "' + '%')"
                    + "OR ( Apellido1 LIKE '%' + '" + filtro + "' + '%')"
                    + "OR ( Apellido2 LIKE '%' + '" + filtro + "' + '%')"
                    + "OR ( Correo LIKE '%' + '" + filtro + "' + '%')"
                    + "OR ( Cast(Log_Activo as nvarchar(5)) LIKE '%' + '" + valor + "' + '%')";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int ced = rsPA.getInt("Id_Usuario");
                String cedula = rsPA.getInt("Id_Usuario") + "";
                TipoIdentificacion TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                String nombre = rsPA.getString("Nombre");
                String apellido1 = rsPA.getString("Apellido1");
                String apellido2 = rsPA.getString("Apellido2");
                Date fechaNacimiento = rsPA.getDate("FechaNacimiento");
                String correo = rsPA.getString("Correo");
                int estado = rsPA.getInt("Log_Activo");
                EstadoAcceso estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                String esta = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                Usuario usu = new Usuario(ced, cedula, TipoIden, fechaNacimiento, correo, estado, nombre, apellido1, apellido2, esta, estAcc);
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

    public void actulizar(Usuario usuario, LinkedList<ProgramaUsuario> listaProusu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
