/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ObtenerDatosSesion;
import dao.SNMPExceptions;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.context.FacesContext;
import model.Usuario;
import model.UsuarioMante;
import model.UsuarioManteDB;

/**
 *
 * @author ujose
 */
@Named(value = "indexBean")
@SessionScoped
public class indexBean implements Serializable {
    UsuarioMante usuario =  null;
    String nombre = null;
    int valor;
    ObtenerDatosSesion datos = null;
    UsuarioManteDB usuDB = new UsuarioManteDB();
    /**
     * Creates a new instance of indexBean
     */
    public indexBean() throws SNMPExceptions, SQLException {
        datos = new ObtenerDatosSesion();
       
        datos.consultarSesion();
        if(!datos.getId_Usuario().equals("")){
            usuario = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
            setNombre(usuario.getNombreCompleto());
        }
    }
    
    
    public void cerrarSesion() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
         FacesContext fc=FacesContext.getCurrentInstance();
           fc.getExternalContext().redirect("faces/InicioSesion.xhtml");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UsuarioMante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMante usuario) {
        this.usuario = usuario;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    
    
    
}
