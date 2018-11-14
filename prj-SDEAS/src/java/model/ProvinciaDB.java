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
public class ProvinciaDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Provincia> listaD = new LinkedList<Provincia>();

    public ProvinciaDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public ProvinciaDB() {
        super();
    }

    public LinkedList<Provincia> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        LinkedList<Provincia> listaProvincias = new LinkedList<Provincia>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Provincia,Dsc_Corta_Provincia, Dsc_Provincia from Provincia";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Provincia = rsPA.getInt("Id_Curso");
                String Dsc_Corta_Provincia = rsPA.getString("Nombre");
                String Dsc_Provincia = rsPA.getString("Dsc_Curso");
                Provincia pro = new Provincia(Id_Provincia, Dsc_Corta_Provincia, Dsc_Provincia);
                listaProvincias.add(pro);
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

        return listaProvincias;
    }

    public Provincia SeleccionarPorId(int idProvincia) throws SNMPExceptions,
            SQLException {
        String select = "";
        ResultSet rsPA = null;
        Provincia pro = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Provincia,Dsc_Corta_Provincia, Dsc_Provincia from Provincia WHERE Id_Provincia = " + idProvincia;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Provincia = rsPA.getInt("Id_Curso");
                String Dsc_Corta_Provincia = rsPA.getString("Nombre");
                String Dsc_Provincia = rsPA.getString("Dsc_Curso");
                pro = new Provincia(Id_Provincia, Dsc_Corta_Provincia, Dsc_Provincia);
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

        return pro;
    }

}
