/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.AccesoDatos;
import java.sql.Connection;
import java.util.LinkedList;

/**
 *
 * @author Fabi
 */
public class DireccionDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<Programa> listaD = new LinkedList<Programa>();
    
    public DireccionDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public DireccionDB() {
        super();
    }
}
