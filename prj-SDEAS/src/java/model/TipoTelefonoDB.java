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
 * @author Fabi
 */
public class TipoTelefonoDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<TipoTelefono> listaT = new LinkedList<TipoTelefono>();

    public LinkedList<TipoTelefono> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;

        LinkedList<TipoTelefono> listaTipo = new LinkedList<TipoTelefono>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select = "select Id_TipoTelefono, Dsc_TipoTelefono from TipoTelefono";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int id_Telefono = rsPA.getInt("Id_TipoTelefono");
                String Dsc_TipoTelefono = rsPA.getString("Dsc_TipoTelefono");
                TipoTelefono tip = new TipoTelefono(id_Telefono, Dsc_TipoTelefono);
                listaTipo.add(tip);
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

        return listaTipo;
    }

    public TipoTelefono SeleccionarPorId(int idtel) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        TipoTelefono tel = null;
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_TipoTelefono, Dsc_TipoTelefono from TipoTelefono WHERE Id_TipoTelefono = " + idtel;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_TipoTelefono = rsPA.getInt("Id_TipoTelefono");
                String Dsc_TipoTelefono = rsPA.getString("Dsc_TipoTelefono");

                tel = new TipoTelefono(Id_TipoTelefono, Dsc_TipoTelefono);
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

        return tel;
    }

    public TipoTelefonoDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public TipoTelefonoDB() {
        super();
    }
}
