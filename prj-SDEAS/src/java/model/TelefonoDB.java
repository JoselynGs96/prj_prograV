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
public class TelefonoDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Telefono> listat = new LinkedList<Telefono>();

    public TelefonoDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public TelefonoDB() {
        super();
    }

    public void registrar(Telefono tel) throws SNMPExceptions, SQLException {
        String strSQL = "";
        int estado = 0;
        try {
            Telefono telefono = new Telefono();
            telefono = tel;

            strSQL = "INSERT INTO Telefono (Numero, Id_Usuario,Id_TipoTelefono, Log_Activo) "
                    + "VALUES (" + telefono.getNumero() + ", "
                    + telefono.getId_Usuario().cedula +","+ telefono.getId_TipoTelefono().id_Telefono + ", " + 1 + ")";

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
    
    public LinkedList<Telefono> SeleccionarTodos(String id_Usuario) throws SNMPExceptions,
            SQLException {
        String select = "";
        TipoTelefonoDB telDB = new TipoTelefonoDB();
        ResultSet rsPA = null;
        LinkedList<Telefono> listaTel = new LinkedList<Telefono>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "select Numero,Id_TipoTelefono from Telefono where Id_Usuario="+id_Usuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int numero = rsPA.getInt("Numero");               
                TipoTelefono tipoTel = telDB.SeleccionarPorId(rsPA.getInt("Id_TipoTelefono"));
                Telefono tel = new Telefono();
                tel.setId_TipoTelefono(tipoTel);
                tel.setNumero(numero+"");
                listaTel.add(tel);
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

        return listaTel;
    }
    /*Elimina telefonos de las listas*/
    public void eliminaTelefono(String id_telefono)throws SNMPExceptions, SQLException {
    String strSQL = "";
        try {
           strSQL = "delete Telefono from Telefono where Id_Telefono ="+id_telefono;

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
