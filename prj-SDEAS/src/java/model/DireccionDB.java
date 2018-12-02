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

            strSQL = "INSERT INTO Direccion (Id_Usuario, Id_Provincia,Id_Canton,Id_Distrito,Id_Barrio,Otras_Sennas,Id_Registra, FechaRegistra, Id_Edita, FechaEdita, Log_Activo) VALUES (" + direccion.getUsuario().Id + "," + direccion.getProvincia().Id_Provincia + "," + direccion.getCanton().Id_Canton 
                    + ", " + direccion.getDistrito().Id_Distrito + "," + direccion.getBarrio().Id_Barrio + ",'" 
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

    public LinkedList<Direccion> SeleccionarPorUsuario(int id_Usuario) throws SNMPExceptions, SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
        DistritoDB disDB = new DistritoDB();
        BarrioDB barDB = new BarrioDB();
        ResultSet rsPA = null;
        LinkedList<Direccion> listaDireccion = new LinkedList<Direccion>();
        

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Direccion, Id_Barrio, Id_Canton, Id_Distrito, Id_Provincia, Otras_Sennas from Direccion WHERE Id_Usuario= CONVERT(int," + id_Usuario+")";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                Barrio bar = barDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Barrio"));
                Distrito dis = disDB.SeleccionarPorId(rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                String id_direccion = rsPA.getString("Id_Direccion");
                String otrasSenas = rsPA.getString("Otras_Sennas");
                Direccion dir = new Direccion();
                dir.setId_direccion(id_direccion);
                dir.setProvincia(provincia);
                dir.setCanton(can);
                dir.setDistrito(dis);
                dir.setBarrio(bar);
                dir.setOtras_sennas(otrasSenas);
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
    
    public Direccion SeleccionarPorId(int id) throws SNMPExceptions, SQLException {
        String select = "";
        ProvinciaDB proDB = new ProvinciaDB();
        CantonDB canDB = new CantonDB();
        DistritoDB disDB = new DistritoDB();
        BarrioDB barDB = new BarrioDB();
        ResultSet rsPA = null;
        Direccion dir = null;

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            select
                    = "SELECT Id_Direccion, Id_Barrio, Id_Canton, Id_Distrito, Id_Provincia, Otras_Sennas from Direccion WHERE Id_Usuario= CONVERT(int," + id +")";

            rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                Barrio bar = barDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Barrio"));
                Distrito dis = disDB.SeleccionarPorId(rsPA.getInt("Id_Distrito"), rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                Canton can = canDB.SeleccionarPorId(rsPA.getInt("Id_Canton"), rsPA.getInt("Id_Provincia"));
                Provincia provincia = proDB.SeleccionarPorId(rsPA.getInt("Id_Provincia"));
                String id_direccion = rsPA.getString("Id_Direccion");
                String otrasSenas = rsPA.getString("Otras_Sennas");
                dir = new Direccion();
                dir.setId_direccion(id_direccion);
                dir.setProvincia(provincia);
                dir.setCanton(can);
                dir.setDistrito(dis);
                dir.setBarrio(bar);
                dir.setOtras_sennas(otrasSenas);
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

        return dir;
    }
    
    
    


    public void eliminaDireccion(int id_direccion)throws SNMPExceptions, SQLException {
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
    
    public void actulizar(Direccion dir) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            Direccion direccion = new Direccion(); 
            direccion = dir;             
            
             strSQL = "UPDATE Direccion SET "
                     +"Id_Barrio='" +direccion.Barrio.Id_Barrio
                     +"', Id_Distrito= '" + direccion.Distrito.Id_Distrito
                     +"', Id_Canton= '" + direccion.Canton.Id_Canton
                     +"', Id_Provincia= '" + direccion.Provincia.Id_Provincia
                     +"', Otras_Sennas='" + direccion.Otras_sennas
                     +"', Id_Edita='" + direccion.Id_Edita
                     +"', FechaEdita='" + new java.sql.Date(direccion.getFechaEdita().getTime())
                     +"' WHERE Id_Direccion='" + direccion.getId_direccion()+"';";
                    
            accesoDatos.ejecutaSQL(strSQL/*, sqlBitacora*/);  
        } catch (SQLException e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage(), e.getErrorCode());         
        }catch (Exception e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage()); 
        } finally { 
         
        } 
    } 
    
}
