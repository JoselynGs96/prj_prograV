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
import model.Usuario;
import model.UsuarioDB;

/**
 *
 * @author Fabi
 */
@Named(value = "contrasennaBean")
@SessionScoped
public class ContrasennaBean implements Serializable {

    String contrasennaActual;
    String contrasennaNueva;
    String contrasennaRepita;
    String mensaje;

    public boolean validarContrasenna() {
        boolean mayuscula = false;
        boolean miniscula = false;
        if (getContrasennaNueva().matches("^[\\w]{8,12}$")) {
            return false;
        } else {
            mayuscula = false;
            miniscula = false;
            for (int i = 0; i < this.getContrasennaNueva().length(); i++) {
                char letra = this.getContrasennaNueva().charAt(i);
                /*Verifica que haya una letra mayuscula*/
                if (Character.isUpperCase(letra) && !mayuscula) {
                    mayuscula = true;
                } else {
                    if (Character.isLowerCase(letra) && !miniscula) {
                        miniscula = true;
                    }
                }
            }
        }
        return !(!miniscula || !mayuscula);

    }

    /*Valida que las contraseñas sean iguales*/
    public boolean validarRepita() {
        if (this.getContrasennaNueva().equals(this.getContrasennaRepita())) {
            return true;
        } else {
            this.setMensaje("*Las contraseñas no coinciden");
            return false;
        }
    }
    
    public void ingresarContrasenna(Usuario usu)throws SNMPExceptions, SQLException{
        UsuarioDB usuDB = new UsuarioDB();
        if(validarContrasenna()){
            if(validarRepita()){
                usuDB.IngresarContrasenna(usu);
            }
        }else{
            this.setMensaje("La contraseña tiene que tener letras,numeros, mayusculas y minisculas");
        }
    }

    public String getContrasennaActual() {
        return contrasennaActual;
    }

    public void setContrasennaActual(String contrasennaActual) {
        this.contrasennaActual = contrasennaActual;
    }

    public String getContrasennaNueva() {
        return contrasennaNueva;
    }

    public void setContrasennaNueva(String contrasennaNueva) {
        this.contrasennaNueva = contrasennaNueva;
    }

    public String getContrasennaRepita() {
        return contrasennaRepita;
    }

    public void setContrasennaRepita(String contrasennaRepita) {
        this.contrasennaRepita = contrasennaRepita;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Creates a new instance of ContrasennaBean
     */
    public ContrasennaBean() {

    }

}
