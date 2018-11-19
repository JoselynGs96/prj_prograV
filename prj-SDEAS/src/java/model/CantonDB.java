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
import javax.inject.Provider;

/**
 *
 * @author Fabi
 */
public class CantonDB {

    int Id_Canton;
    String Dsc_Canton;
    Provincia Id_Provincia;

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Canton> listaD = new LinkedList<Canton>();

    public CantonDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public CantonDB() {
        super();
    }
     public LinkedList<Canton> SeleccionarTodos(int Id_Provincia) throws SNMPExceptions,
            SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        ResultSet rsPA = null;
        LinkedList<Canton> listaCantones = new LinkedList<Canton>();

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Canton,Dsc_Canton, Id_Provincia from Canton where Id_Provincia="+Id_Provincia;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

                int Id_Canton = rsPA.getInt("Id_Canton");
                String Dsc_Canton = rsPA.getString("Dsc_Canton");
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                Canton can = new Canton(Id_Canton, Dsc_Canton, provincia);
                listaCantones.add(can);
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

        return listaCantones;
    }
      public Canton SeleccionarPorId(int idCanton,int id_Provincia) throws SNMPExceptions,
            SQLException {
        String select = "";
          ProvinciaDB proDB = new ProvinciaDB();
        ResultSet rsPA = null;
        Canton can = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Canton,Dsc_Canton, Id_Provincia from Canton WHERE Id_Canton = " + idCanton+"and Id_Provincia="+id_Provincia;

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {

               int Id_Canton = rsPA.getInt("Id_Canton");
                String Dsc_Canton = rsPA.getString("Dsc_Canton");
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
              can = new Canton(Id_Canton, Dsc_Canton, provincia);          
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

        return can;
    }
}
