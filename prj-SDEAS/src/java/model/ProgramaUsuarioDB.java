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
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author ujose
 */
public class ProgramaUsuarioDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public ProgramaUsuarioDB(Connection conn) {
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

            if (pu.estado.equals("Activo")) {
                estado = 1;
            }
            strSQL = "INSERT INTO Programa_Usuario (Id_Programa, Id_Usuario, Id_RolUsuario, TipoFuncionario, Id_Registra, FechaRegistra, Id_Edita, FechaEdita, Log_Activo) "
                    + "VALUES ('"
                    + pu.getPrograma().getId()
                    + "', '" + pu.getUsuario().getId()
                    + "', '" + pu.getRolUsuario().getId_RolUsuario()
                    + "', '" + pu.Funcionario.toString()
                    + "', '" + pu.getId_Registra()
                    + "', '" + new java.sql.Date(pu.getFechaRegistra().getTime())
                    + "', '" + pu.getId_Edita()
                    + "', '" + new java.sql.Date(pu.getFechaEdita().getTime())
                    + "', '" + estado + "')";

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

    public void actulizar(ProgramaUsuario prousu) throws SNMPExceptions, SQLException {
        String strSQL = "";
        int estado = 0;
        try {
            ProgramaUsuario pu = new ProgramaUsuario();
            pu = prousu;

            if (pu.estado.equals("Activo")) {
                estado = 1;
            }

            strSQL = "UPDATE Programa_Usuario SET "
                    + "TipoFuncionario='" + pu.getFuncionario().toString()
                    + "', Id_Edita='" + pu.getId_Edita()
                    + "', FechaEdita='" + new java.sql.Date(pu.getFechaEdita().getTime())
                    + "', Log_Activo='" + estado
                    + "' WHERE Id_Programa='" + pu.getPrograma().id + "'AND Id_RolUsuario='" + pu.getRolUsuario().Id_RolUsuario + "'AND Id_Usuario='" + pu.usuario.Id + "';";

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

    public LinkedList<ProgramaUsuario> SeleccionarPorId(int id) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        ProgramaDB pro = new ProgramaDB();
        UsuarioDB usu = new UsuarioDB();
        RolUsuarioDB rol = new RolUsuarioDB();

        LinkedList<ProgramaUsuario> listaProgramaUsuario = new LinkedList<ProgramaUsuario>();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Programa, Id_Usuario, Id_RolUsuario, TipoFuncionario, Id_Edita, FechaEdita, Log_Activo from Programa_Usuario WHERE Id_Usuario = " + id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                Programa Id_Programa = pro.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                Usuario usuario = usu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
                RolUsuario Id_RolUsuario = rol.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                EnumFuncionario TipoFunci = EnumFuncionario.valueOf(rsPA.getString("TipoFuncionario"));
                int idEdita = rsPA.getInt("Id_Edita");
                Date fechaEdita = rsPA.getDate("FechaEdita");
                int Log_Activo = rsPA.getInt("Log_Activo");
                ProgramaUsuario p = new ProgramaUsuario(Id_Programa, Id_RolUsuario, TipoFunci, Log_Activo == 0 ? "Inactivo" : "Activo");
                p.setUsuario(usuario);
                p.setId_Edita(idEdita);
                p.setFechaEdita(fechaEdita);
                listaProgramaUsuario.add(p);
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

        return listaProgramaUsuario;
    }

    public ProgramaUsuario VerificarRol(int idUsu, int idRol) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        ProgramaDB pro = new ProgramaDB();
        UsuarioDB usu = new UsuarioDB();
        RolUsuarioDB rol = new RolUsuarioDB();
        ProgramaUsuario p = null;
        EnumFuncionario TipoFunci = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Programa, Id_Usuario, Id_RolUsuario, TipoFuncionario, Id_Edita, FechaEdita, Log_Activo from Programa_Usuario WHERE Id_Usuario = " + idUsu + " AND Id_RolUsuario = " + idRol;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                Programa Id_Programa = pro.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                Usuario usuario = usu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
                RolUsuario Id_RolUsuario = rol.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                if ((Id_RolUsuario).Id_RolUsuario == 3) {
                    TipoFunci = EnumFuncionario.valueOf(rsPA.getString("TipoFuncionario"));
                }
                int idEdita = rsPA.getInt("Id_Edita");
                Date fechaEdita = rsPA.getDate("FechaEdita");
                int Log_Activo = rsPA.getInt("Log_Activo");
                p = new ProgramaUsuario();
                p.setPrograma(Id_Programa);
                p.setRolUsuario(Id_RolUsuario);
                if (p.rolUsuario.Id_RolUsuario == 3) {
                    p.setFuncionario(TipoFunci);
                }
                if (Log_Activo == 0) {
                    p.setEstado("Inactivo");
                } else {
                    p.setEstado("Activo");
                }
                p.setUsuario(usuario);
                p.setId_Edita(idEdita);
                p.setFechaEdita(fechaEdita);
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

        return p;
    }

    public LinkedList<Programa> SeleccionarTodosPorId(int id) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        ProgramaDB pro = new ProgramaDB();
        UsuarioDB usu = new UsuarioDB();
        RolUsuarioDB rol = new RolUsuarioDB();

        LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Programa from Programa_Usuario WHERE Id_Usuario = " + id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                Programa Id_Programa = pro.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                listaPrograma.add(Id_Programa);
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

        return listaPrograma;
    }

    public LinkedList<ProgramaUsuario> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        ProgramaUsuario p = null;
        ProgramaDB pro = new ProgramaDB();
        UsuarioDB usu = new UsuarioDB();
        RolUsuarioDB rol = new RolUsuarioDB();

        LinkedList<ProgramaUsuario> listaProgramaUsuario = new LinkedList<ProgramaUsuario>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Programa, Id_Usuario, Id_RolUsuario, TipoFuncionario, Id_Edita, FechaEdita, Log_Activo from Programa_Usuario";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                Programa Id_Programa = pro.SeleccionarPorId(rsPA.getInt("Id_Programa"));
                Usuario Id_Usuario = usu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
                RolUsuario Id_RolUsuario = rol.SeleccionarPorId(rsPA.getInt("Id_RolUsuario"));
                EnumFuncionario TipoFunci = EnumFuncionario.valueOf(rsPA.getString("TipoFuncionario"));
                int idEdita = rsPA.getInt("Id_Edita");
                Date fechaEdita = rsPA.getDate("FechaEdita");
                int Log_Activo = rsPA.getInt("Log_Activo");
                ProgramaUsuario pu = new ProgramaUsuario(Id_Usuario, Id_Programa, Id_RolUsuario, TipoFunci, Log_Activo == 0 ? "Inactivo" : "Activo");
                pu.setId_Edita(idEdita);
                pu.setFechaEdita(fechaEdita);
                listaProgramaUsuario.add(pu);
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

        return listaProgramaUsuario;
    }

    public LinkedList<UsuarioMante> SeleccionarTodosUsuarioPorPrograma(LinkedList<Programa> lsP, int idU) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        ProgramaDB pro = new ProgramaDB();
        UsuarioManteDB usu = new UsuarioManteDB();
        RolUsuarioDB rol = new RolUsuarioDB();

        LinkedList<UsuarioMante> listaUsuario = new LinkedList<UsuarioMante>();
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            for (Programa um : lsP) {
                int id = um.getId();
                select
                        = "SELECT Id_Usuario from Programa_Usuario WHERE Id_Programa = " + id + "and Id_Usuario !=" + idU;

                rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

                while (rsPA.next()) {

                    UsuarioMante usua = usu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));
                    listaUsuario.add(usua);
                }
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

    /*Este metodo me trae el coordinador del progrma del usuario*/
    public UsuarioMante SeleccionarTodosCoordinadorPorPrograma(int id) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        ProgramaDB pro = new ProgramaDB();
        UsuarioManteDB usu = new UsuarioManteDB();
        RolUsuarioDB rol = new RolUsuarioDB();
        UsuarioMante usua=null;
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Usuario from Programa_Usuario WHERE Id_Programa = " + id + " and Id_RolUsuario=2";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                usua = usu.SeleccionarPorId(rsPA.getInt("Id_Usuario"));

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

        return usua;
    }

}
