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
import model.UsuarioDB;

/**
 *
 * @author Fabi
 */
@Named(value = "usuariosBean")
@SessionScoped
public class UsuariosBean implements Serializable {

    String codAcceso;
    String conta1;
    String conta2;
    String contaActual;
    String mensajeCod;
    String mensajeContra;
    

    public boolean validaCodigoAcceso() {
        boolean bandera = true;
        if (codAcceso.equals("")) {
            this.setMensajeCod("Debe colocar el codigo de acceso");
        } else {
            bandera = false;
        }

        return bandera;
    }

    public void enviaCodigo() throws SNMPExceptions,SQLException, IOException{
        Usuario usuarioLogin = new Usuario();
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
        UsuarioDB usuDB = new UsuarioDB();
        datos.consultarSesion();
        usuarioLogin = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
        if(validaCodigoAcceso()){
            if(usuarioLogin.getCodAcceso().equals(this.getCodAcceso())){
                usuDB.ActualizarUsuario(usuarioLogin);
                  FacesContext.getCurrentInstance().getExternalContext().redirect("Contraseña.xhtml");
            }else{
                this.setMensajeCod("El codigo es incorrecto");
            }
        }
        
        
        
    }

    /**
     * Creates a new instance of UsuariosBean
     */
    public UsuariosBean() {
    }

    public String getMensajeCod() {
        return mensajeCod;
    }

    public void setMensajeCod(String mensajeCod) {
        this.mensajeCod = mensajeCod;
    }

    public String getMensajeContra() {
        return mensajeContra;
    }

    public void setMensajeContra(String mensajeContra) {
        this.mensajeContra = mensajeContra;
    }

    public String getCodAcceso() {
        return codAcceso;
    }

    public void setCodAcceso(String codAcceso) {
        this.codAcceso = codAcceso;
    }

    public String getConta1() {
        return conta1;
    }

    public void setConta1(String conta1) {
        this.conta1 = conta1;
    }

    public String getConta2() {
        return conta2;
    }

    public void setConta2(String conta2) {
        this.conta2 = conta2;
    }

    public String getContaActual() {
        return contaActual;
    }

    public void setContaActual(String contaActual) {
        this.contaActual = contaActual;
    }

}
