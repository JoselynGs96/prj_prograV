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
import model.UsuarioDB;

/**
 *
 * @author ujose
 */
@Named(value = "usuariosPorProgranaBean")
@SessionScoped
public class UsuariosPorProgranaBean implements Serializable {

    Usuario usuario = null;
    ProgramaUsuario programaUsu = null;
    String cedula = "";
    String nombre = "";
    String estadoPrograma = "";
    String estadoAcceso = "";
    String estadoUsuario = "";
    String nombrePrograma = "";
    LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
    LinkedList<ProgramaUsuario> listaProusu = new LinkedList<ProgramaUsuario>();

    
    
    
    public UsuariosPorProgranaBean() throws SNMPExceptions, SQLException{
       
    }

    
    
    public void llenarTabla() throws SNMPExceptions, SQLException{
       UsuarioDB u = new UsuarioDB();
       if(!u.SeleccionarTodos2().isEmpty()){
            listaUsuario = u.SeleccionarTodos2();
       }
      
    }
    
   
    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ProgramaUsuario getProgramaUsu() {
        return programaUsu;
    }

    public void setProgramaUsu(ProgramaUsuario programaUsu) {
        this.programaUsu = programaUsu;
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

    public String getEstadoPrograma() {
        return estadoPrograma;
    }

    public void setEstadoPrograma(String estadoPrograma) {
        this.estadoPrograma = estadoPrograma;
    }

    public String getEstadoAcceso() {
        return estadoAcceso;
    }

    public void setEstadoAcceso(String estadoAcceso) {
        this.estadoAcceso = estadoAcceso;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
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
 
    
  
}
