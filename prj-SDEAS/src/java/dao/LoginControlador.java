/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.Usuario;

import javax.faces.context.FacesContext;
import model.UsuarioDB;

/**
 *
 * @author Alberto
 */
@Named(value = "loginControlador")
@SessionScoped
public class LoginControlador implements Serializable {
       
    int Id_Usuario;
    String Contrasenna;
    Usuario Usuario1;
    String TpoUsuario;

    public Usuario getUsuario1() {
        return Usuario1;
    }

    public void setUsuario1(Usuario Usuario1) {
        this.Usuario1 = Usuario1;
    }

    public String getTpoUsuario() {
        return TpoUsuario;
    }

    public void setTpoUsuario(String TpoUsuario) {
        this.TpoUsuario = TpoUsuario;
    }

     /**
     * Creates a new instance of LoginControlador
     */
    public LoginControlador() {
    }
    
    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int Usuario) {
        this.Id_Usuario = Usuario;
    }

    public String getContrasenna() {
        return Contrasenna;
    }

    public void setContrasenna(String Contrasenna) {
        this.Contrasenna = Contrasenna;
    }
    
   public void autenticar(){
       try{
           Usuario1=UsuarioDB.InicioSeccion(this.getId_Usuario(), this.getContrasenna());
           
           if (Usuario1 != null){
               FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario",Id_Usuario);               
               FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
              
              
           }
       }catch (Exception e){
       
       }
       
   }
    
}
