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
public class BarrioDB {
     int Id_Distrito;
    String Dsc_Distrito;
    Provincia Id_Provincia;
    Canton Id_Canton;

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Barrio> listaB = new LinkedList<Barrio>();

    public BarrioDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public BarrioDB() {
        super();
    }
    
    public LinkedList<Barrio> SeleccionarTodos(int Id_Provincia ,int Id_Canton,int id_Distrito) throws SNMPExceptions,
            SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
         DistritoDB disDB = new DistritoDB();
        ResultSet rsPA = null;
        LinkedList<Barrio> listaBarrio = new LinkedList<Barrio>();
        Barrio barr = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Barrio,Dsc_Barrio, Id_Provincia,Id_Canton,Id_Distrito from Barrio where Id_Provincia="+Id_Provincia+" and Id_Canton="+Id_Canton+"and id_Distrito="+id_Distrito;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Barrio  = rsPA.getInt("Id_Barrio");
                String Dsc_Barrio = rsPA.getString("Dsc_Barrio");
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"),rsPA.getInt("Id_Provincia"));
                Distrito dis= disDB.SeleccionarPorId(rsPA.getInt("Id_Distrito"),rsPA.getInt("Id_Canton"),rsPA.getInt("Id_Provincia"));
                barr = new Barrio(Id_Barrio,Dsc_Barrio, provincia,can,dis);
                listaBarrio.add(barr);
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

        return listaBarrio;
    }
   
    public Barrio SeleccionarPorId(int Id_Provincia ,int Id_Canton,int id_Distrito, int Id_barrio) throws SNMPExceptions,
            SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
        DistritoDB disDB = new DistritoDB();
        ResultSet rsPA = null;
        Barrio bar = null;

        try {                                                                                         
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Barrio,Dsc_Barrio, Id_Provincia,Id_Canton,Id_Distrito from Barrio WHERE Id_Canton = CONVERT(int," + Id_Canton+") and Id_Provincia= CONVERT(int,"+Id_Provincia+" )and Id_Distrito= CONVERT(int,"+id_Distrito+") and Id_Barrio=CONVERT(int,"+Id_barrio+")";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

               int Id_Barrio = rsPA.getInt("Id_Barrio");
                String Dsc_Barrio = rsPA.getString("Dsc_Barrio");
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"),rsPA.getInt("Id_Provincia"));
                Distrito dis = disDB.SeleccionarPorId(rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                 bar = new Barrio(Id_Barrio,Dsc_Barrio,provincia,can,dis);          
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

        return bar;
    }
}

