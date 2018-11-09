/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Fabi
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
 String cedula;
    String TipoIden;
    Date fechaNacimiento;
    String correo;
    String contrasenna;
    String codAcceso;
    String estado;
    String mensaje;
    String  tipoPerfil;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoIden() {
        return TipoIden;
    }

    public void setTipoIden(String TipoIden) {
        this.TipoIden = TipoIden;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getCodAcceso() {
        return codAcceso;
    }

    public void setCodAcceso(String codAcceso) {
        this.codAcceso = codAcceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    
    
    public void validaIngresar(){
        if(this.getCedula().equals("")){
            this.setMensaje("*Debe colocar el usuario.");
        }else{
            if(this.getContrasenna().equals("")){
                this.setMensaje("*Debe colocar la contraseña");
            }else{
              if(this.getCedula().equals("207750517")&&this.getContrasenna().equals("123")){
                    this.setMensaje("Bienvenida Fabiola");
              }
            }
        }
    }
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
}

