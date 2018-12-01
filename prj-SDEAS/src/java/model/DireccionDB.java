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
public class DireccionDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Programa> listaD = new LinkedList<Programa>();

    public DireccionDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public DireccionDB() {
        super();
    }

    public void registrar(Direccion direc) throws SNMPExceptions, SQLException {
        String strSQL = "";
        try {
            Direccion direccion = new Direccion();
            direccion = direc;

            strSQL = "INSERT INTO Direccion (Id_Usuario, Id_Provincia,Id_Canton,Id_Distrito,Id_Barrio,Otras_Sennas,Id_Registra, FechaRegistra, Id_Edita, FechaEdita, Log_Activo) VALUES (" + direccion.getUsuario().cedula + "," + direccion.getId_Provincia().Id_Provincia + "," + direccion.getId_Canton().Id_Canton 
                    + ", " + direccion.getId_Distrito().Id_Distrito + "," + direccion.getId_Barrio().Id_Barrio + ",'" 
                    + direccion.Otras_sennas 
                    + "'," + direccion.Id_Registra
                    + "'," + new java.sql.Date(direccion.getFechaRegistra().getTime())
                    + "'," + direccion.Id_Edita
                    + "'," + new java.sql.Date(direccion.getFechaEdita().getTime())
                    + 1 + ")";

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

    public LinkedList<Direccion> SeleccionarPorUsuario(String id_Usuario) throws SNMPExceptions, SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
        DistritoDB disDB = new DistritoDB();
        BarrioDB barDB = new BarrioDB();
        ResultSet rsPA = null;
        LinkedList<Direccion> listaDireccion = new LinkedList<Direccion>();
        Direccion dir = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "select [Id_Direccion],[Id_Provincia],[Id_Distrito],[Id_Canton], [Id_Barrio],[Otras_Sennas] from Direccion where Id_Usuario=" + id_Usuario;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                Distrito dis = disDB.SeleccionarPorId(rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                Barrio bar = barDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Barrio"));
                String id_direccion = rsPA.getString("Id_Direccion");
                dir = new Direccion();
                dir.setId_direccion(id_direccion);
                dir.setId_Provincia(provincia);
                dir.setId_Canton(can);
                dir.setId_Distrito(dis);
                dir.setId_Barrio(bar);
                listaDireccion.add(dir);
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

        return listaDireccion;
    }

    public void eliminaDireccion(String id_direccion)throws SNMPExceptions, SQLException {
    String strSQL = "";
        try {
           strSQL = "delete Direccion from Direccion where Id_Direccion="+id_direccion;

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
