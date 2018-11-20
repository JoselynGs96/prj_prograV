/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SNMPExceptions;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import model.Programa;
import model.ProgramaDB;
import model.ProgramaUsuario;
import model.RolUsuario;
import model.RolUsuarioDB;
import model.Usuario;

/**
 *
 * @author ujose
 */
@Named(value = "usuariosPorProgranaBean")
@SessionScoped
public class UsuariosPorProgranaBean implements Serializable {

    String cedula;
    String nombre;
    int idPrograma;
    int idTipoUsuario;
    String estado;
    LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
    LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
    LinkedList<RolUsuario> listaRolUsuario = new LinkedList<RolUsuario>();
    LinkedList<ProgramaUsuario> listaProusu = new LinkedList<ProgramaUsuario>();
    LinkedList<ProgramaUsuario> temporal = new LinkedList<ProgramaUsuario>();

    
    
    /**
     * Creates a new instance of UsuariosPorProgranaBean
     */
    public UsuariosPorProgranaBean() {
    }
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Programa> getListaPrograma() throws SNMPExceptions, SQLException {
        ProgramaDB pro = new ProgramaDB();
        return pro.SeleccionarTodos();
    }

    public void setListaPrograma(LinkedList<Programa> listaPrograma) {
        this.listaPrograma = listaPrograma;
    }

    public LinkedList<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(LinkedList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public LinkedList<ProgramaUsuario> getListaProusu() {
        return listaProusu;
    }

    public void setListaProusu(LinkedList<ProgramaUsuario> listaProusu) {
        this.listaProusu = listaProusu;
    }

    public LinkedList<ProgramaUsuario> getTemporal() {
        return temporal;
    }

    public void setTemporal(LinkedList<ProgramaUsuario> temporal) {
        this.temporal = temporal;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LinkedList<RolUsuario> getListaRolUsuario() throws SNMPExceptions, SQLException {
        RolUsuarioDB d = new RolUsuarioDB();
        return d.SeleccionarTodos();
    }

    public void setListaRolUsuario(LinkedList<RolUsuario> listaRolUsuario) {
        this.listaRolUsuario = listaRolUsuario;
    }
    
  
}
