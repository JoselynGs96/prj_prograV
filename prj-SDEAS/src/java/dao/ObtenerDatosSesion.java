/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import model.Usuario;
import java.util.Map;
import javax.faces.context.ExternalContext;
/**
 *
 * @author Alberto
 */
@Named(value = "obtenerDatosSesion")
@SessionScoped
public class ObtenerDatosSesion implements Serializable {
    
    Usuario UsuarioLogin;
    String datos;
    int Id_Usuario;

    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    public Usuario getUsuarioLogin() {
        return UsuarioLogin;
    }

    public void setUsuarioLogin(Usuario UsuarioLogin) {
        this.UsuarioLogin = UsuarioLogin;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }
    /**
     * Creates a new instance of ObtenerDatosSesion
     */
    public ObtenerDatosSesion() {
    }
    
    public void consultarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Usuario");
        
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	final Map<String, Object> session = context.getSessionMap();
	final Object user = session.get("Id_Usuario");

	if (user != null) {
		try {
			int userId = (Integer)user;
                        this.setId_Usuario(userId);
                   
			
		} catch (ClassCastException e) {
			
			
		}
	}else{
		context.invalidateSession();
		
	}
	
	
    }
            
}
