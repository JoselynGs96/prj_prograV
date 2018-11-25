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

    public void registrar(Usuario usu) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            Usuario usuario = new Usuario();
            usuario = usu;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object

            strSQL = "INSERT INTO Usuario ([Id_Usuario],[Id_TipoIdentificacion],[Nombre],[Apellido1],[Apellido2],[FechaNacimiento],[Correo],[Id_RolUsuario],[Id_EstadoAcceso],[TipoFuncionario],[Log_Activo]) values(" + usuario.cedula + "," + usuario.TipoIden.getId_TipoIdentificacion() + ",'" + usuario.nombre + "','" + usuario.apellido1 + "','" + usuario.apellido2 + "','" + fecha + "','" + usuario.getCorreo() + "'," + 2 + "," + 3 + ",'" + usuario.Funcionario.toString() + "'," + 1 + ")";
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
        EstadoAccesoDB est = new EstadoAccesoDB();
        usu = new Usuario();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "Select Id_Usuario, TipoIdentificacion,Nombre,Apellido1,Apellido2,Correo,Id_EstadoAcceso, Log_Activo from Usuario where Id_Usuario=" + idUsuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                usu.ced = rsPA.getInt("Id_Usuario");
                usu.cedula = rsPA.getInt("Id_Usuario") + "";
                usu.TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("TipoIdentificacion"));
                usu.nombre = rsPA.getString("Nombre");
                usu.apellido1 = rsPA.getString("Apellido1");
                usu.apellido2 = rsPA.getString("Apellido2");
                usu.correo = rsPA.getString("Correo");
                usu.estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                usu.est = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";

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

    public LinkedList<Usuario> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
        ProgramaUsuarioDB pu = new ProgramaUsuarioDB();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "Select Id_Usuario, TipoIdentificacion,Nombre,Apellido1,Apellido2,Correo,Id_EstadoAcceso, Log_Activo from Usuario";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                Usuario usu = new Usuario();
                usu.ced = rsPA.getInt("Id_Usuario");
                usu.cedula = rsPA.getInt("Id_Usuario") + "";
                usu.TipoIden = tipoIdenDB.SeleccionarPorId(rsPA.getInt("TipoIdentificacion"));
                usu.nombre = rsPA.getString("Nombre");
                usu.apellido1 = rsPA.getString("Apellido1");
                usu.apellido2 = rsPA.getString("Apellido2");
                usu.correo = rsPA.getString("Correo");
                usu.estAcc = est.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                usu.est = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                usu.pro = pu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
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

    public LinkedList<Usuario> SeleccionarTodos2() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        TipoIdentificacionDB tipoIdenDB = new TipoIdentificacionDB();
        EstadoAccesoDB est = new EstadoAccesoDB();
        LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
        ProgramaUsuarioDB pu = new ProgramaUsuarioDB();
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
                ProgramaUsuario pro = pu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
                Usuario usu = new Usuario(ced, cedula, TipoIden, fechaNacimiento, correo, estado, nombre, apellido1, apellido2, pro, esta, estAcc);
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
    
    public void ActualizarContrasenna(Usuario usu)throws SNMPExceptions, SQLException{
        String strSQL = "";

        try {
            Usuario usuario = new Usuario();
            usuario = usu;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String fecha = formato.format(usuario.getFechaNacimiento());
//mydate is your date object

            strSQL = "update Usuario set [Id_TipoIdentificacion]=" + usuario.TipoIden.getId_TipoIdentificacion() +",[Nombre]='" + usuario.nombre + "'" +",[Apellido1]='" + usuario.apellido1 +"'" +",[Apellido2]='" + usuario.apellido2 + "'" +",[FechaNacimiento]='" + fecha + "'" + ",[Correo]='" + usuario.getCorreo() + "'" +",[TipoFuncionario]='" + usuario.Funcionario.toString() + "'where Id_Usuario="+usuario.id;
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
