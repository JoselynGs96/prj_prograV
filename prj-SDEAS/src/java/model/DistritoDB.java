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
public class DistritoDB {

    int Id_Distrito;
    String Dsc_Distrito;
    Provincia Id_Provincia;
    Canton Id_Canton;

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Canton> listaD = new LinkedList<Canton>();

    public DistritoDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public DistritoDB() {
        super();
    }

    public LinkedList<Distrito> SeleccionarTodos() throws SNMPExceptions,
            SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
        ResultSet rsPA = null;
        LinkedList<Distrito> listaDistritos = new LinkedList<Distrito>();
        Distrito dis = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Distrito,Dsc_Distrito, Id_Provincia,Id_Canton from Distrito";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Distrito  = rsPA.getInt("Id_Distrito");
                String Dsc_Distrito = rsPA.getString("Dsc_Distrito");
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"));
                dis = new Distrito(Id_Distrito,Dsc_Distrito, provincia,can);
                listaDistritos.add(dis);
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

        return listaDistritos;
    }
    public Distrito SeleccionarPorId(int idDis) throws SNMPExceptions,
            SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
        ResultSet rsPA = null;    
        Distrito dis = null;
        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Distrito,Dsc_Distrito, Id_Provincia,Id_Canton from Distrito = " + idDis;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

               int Id_Distrito  = rsPA.getInt("Id_Distrito");
                String Dsc_Distrito = rsPA.getString("Dsc_Distrito");
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"));
                dis = new Distrito(Id_Distrito,Dsc_Distrito, provincia,can);              
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

        return dis;
    }
}
