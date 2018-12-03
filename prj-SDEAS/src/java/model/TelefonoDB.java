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

            strSQL = "INSERT INTO Telefono (Numero, Id_Usuario,Id_TipoTelefono,Id_Registra,FechaRegistra,Id_Edita,FechaEdita Log_Activo) "
                    + "VALUES (" + telefono.getNumero() + ", "
                    + telefono.getId_Usuario().Id + "," + telefono.getId_TipoTelefono().id_Telefono + ", "
                    + telefono.getId_Registra() + "," + new java.sql.Date(telefono.FechaRegistra.getTime()) + "," + telefono.Id_Edita + "," + new java.sql.Date(telefono.FechaEdita.getTime()) + 1 + ")";

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

    public void Actualizar(Telefono tel) throws SNMPExceptions, SQLException {
        String strSQL = "";
        int estado = 0;
        try {
            Telefono telefono = new Telefono();
            telefono = tel;

            strSQL = "UPDATE [dbo].[Telefono]\n"
                    + "   SET [Numero] ="+ telefono.getNumero()
                    + "      ,[Id_Usuario] = "+telefono.getId_Usuario().Id
                    + "      ,[Id_TipoTelefono] ="+telefono.getId_TipoTelefono().id_Telefono              
                    + "      ,[Id_Edita] = "+telefono.Id_Edita
                    + "      ,[FechaEdita] ="+new java.sql.Date(telefono.FechaEdita.getTime())
                    + "      ,[Log_Activo] = "+1
                    +"where Id_Telefono= "+telefono.getId_Telefono();

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

    public LinkedList<Telefono> SeleccionarTodos(int id_Usuario) throws SNMPExceptions,
            SQLException {
        String select = "";
        TipoTelefonoDB telDB = new TipoTelefonoDB();
        ResultSet rsPA = null;
        LinkedList<Telefono> listaTel = new LinkedList<Telefono>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "select Id_Telefono, Numero, Id_TipoTelefono from Telefono where Id_Usuario =" + id_Usuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Telefono = rsPA.getInt("Id_Telefono");
                int numero = rsPA.getInt("Numero");
                TipoTelefono tipoTel = telDB.SeleccionarPorId(rsPA.getInt("Id_TipoTelefono"));
                Telefono tel = new Telefono();
                tel.setId_TipoTelefono(tipoTel);
                tel.setNumero(numero + "");
                tel.id_Telefono = Id_Telefono + "";
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

    public Telefono SeleccionarPorId(int id) throws SNMPExceptions,
            SQLException {
        String select = "";
        TipoTelefonoDB telDB = new TipoTelefonoDB();
        Telefono tel = null;
        ResultSet rsPA = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "select Id_Telefono, Numero,Id_TipoTelefono from Telefono where Id_Telefono=" + id;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Telefono = rsPA.getInt("Id_Telefono");
                int numero = rsPA.getInt("Numero");
                TipoTelefono tipoTel = telDB.SeleccionarPorId(rsPA.getInt("Id_TipoTelefono"));
                tel = new Telefono();
                tel.setId_TipoTelefono(tipoTel);
                tel.setNumero(numero + "");
                tel.id_Telefono = Id_Telefono + "";
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

    public void actulizar(Telefono tel) throws SNMPExceptions, SQLException {
        String strSQL = "";
        int estado = 0;
        try {
            Telefono telefono = new Telefono();
            telefono = tel;

            strSQL = "UPDATE Telefono SET "
                    + "Numero='" + telefono.getNumero()
                    + "', Id_TipoTelefono= '" + telefono.getId_TipoTelefono().id_Telefono
                    + "', Id_Edita= '" + telefono.getId_Edita()
                    + "', FechaEdita= '" + new java.sql.Date(telefono.getFechaEdita().getTime())
                    + "', Log_Activo='" + estado
                    + "' WHERE Id_Telefono='" + telefono.getId_Telefono() + "';";

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

    /*Elimina telefonos de las listas*/
    public void eliminaTelefono(int id_telefono) throws SNMPExceptions, SQLException {
        String strSQL = "";
        try {
            strSQL = "delete Telefono from Telefono where Id_Telefono =" + id_telefono;

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
