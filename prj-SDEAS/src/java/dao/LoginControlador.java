/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import model.Usuario;

import javax.faces.context.FacesContext;
import model.Programa;
import model.RolUsuario;
import model.RolUsuarioDB;
import model.UsuarioDB;

/**
 *
 * @author Alberto
 */
@Named(value = "loginControlador")
@SessionScoped
public class LoginControlador implements Serializable {
       
    String Id_Usuario;
    String Contrasenna;
    Usuario Usuario1;
    String TpoUsuario;
    String Mensaje;
     LinkedList<RolUsuario> listaRolUsuario = new LinkedList<RolUsuario>();
     int id_rol; 

  

     /**
     * Creates a new instance of LoginControlador
     */
    public LoginControlador() throws SNMPExceptions,SQLException{
        RolUsuarioDB rol = new RolUsuarioDB();
         if (!rol.SeleccionarTodos().isEmpty()) {
            listaRolUsuario = rol.SeleccionarTodos();
            id_rol = rol.SeleccionarTodos().element().getId_RolUsuario();
        }
    }
    
     /*valida el login*/
    public void validaIngresar() {
        if (this.getId_Usuario().equals("")) {
            this.setMensaje("*Debe colocar el usuario.");
        } else {
            if (this.getContrasenna().equals("")) {
                this.setMensaje("*Debe colocar la contraseña");
            }
        }
    }
    
     public void autenticar(){
       try{          
           Usuario1=UsuarioDB.InicioSeccion(this.getId_Usuario(), this.getContrasenna(), this.TpoUsuario);
           
           if (Usuario1 != null){
               FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario",Id_Usuario); 
               if(Usuario1.getRolUsuario().getId_RolUsuario()==1){
                FacesContext.getCurrentInstance().getExternalContext().redirect("Mantenimiento.xhtml");
               }else{
               FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
               }
              
           }else{
               this.setMensaje("Usuario o Contraseña o Tipo Funcionario incorrectos");
               
           }
       }catch (Exception e){
       
       }
       
   }
       public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

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
    
    public String getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(String Usuario) {
        this.Id_Usuario = Usuario;
    }

    public String getContrasenna() {
        return Contrasenna;
    }

    public void setContrasenna(String Contrasenna) {
        this.Contrasenna = Contrasenna;
    }
    public LinkedList<RolUsuario> getListaRolUsuario() {
        return listaRolUsuario;
    }

    public void setListaRolUsuario(LinkedList<RolUsuario> listaRolUsuario) {
        this.listaRolUsuario = listaRolUsuario;
    }
     public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}