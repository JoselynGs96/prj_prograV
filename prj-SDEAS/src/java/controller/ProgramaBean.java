/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SNMPExceptions;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Programa;
import model.ProgramaDB;

/**
 *
 * @author ujose
 */
@Named(value = "programaBean")
@SessionScoped
public class ProgramaBean implements Serializable {
    int id;
    String nombre;
    String descripcion;
    int estado;
    LinkedList<Programa> listaTablaPrograma = new LinkedList<Programa>();
    /**
     * Creates a new instance of ProgramaBean
     */
    public ProgramaBean() throws SNMPExceptions, SQLException {
        seleccionarTodos();
        
    }

    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        ProgramaDB pro = new ProgramaDB();
        listaTablaPrograma = pro.SeleccionarTodos();
    }
    
     public void insertarPrograma() throws SNMPExceptions, SQLException{
        Programa pro = new Programa();
        pro.setNombre(nombre);
        pro.setDescripcion(descripcion);
        pro.setEstado(estado);
        ProgramaDB prog = new ProgramaDB();
        prog.registrar(pro);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LinkedList<Programa> getListaTablaPrograma() {
        return listaTablaPrograma;
    }

    public void setListaTablaPrograma(LinkedList<Programa> listaTablaPrograma) {
        this.listaTablaPrograma = listaTablaPrograma;
    }
}
