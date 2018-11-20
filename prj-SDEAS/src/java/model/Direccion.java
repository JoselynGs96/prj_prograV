/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Fabi
 */
public class Direccion {
    Provincia Id_Provincia;
    Canton Id_Canton;
    Distrito Id_Distrito;
    Barrio Id_Barrio;
    Usuario usuario;
    String Otras_sennas;
    
    
    public Direccion(){
        
    }
    public Direccion(Provincia Id_Provincia,Canton Id_Canton,Distrito Id_Distrito,Barrio Id_Barrio,Usuario usuario,String Otras_sennas){
        this.setId_Barrio(Id_Barrio);
        this.setId_Canton(Id_Canton);
        this.setId_Distrito(Id_Distrito);
        this.setId_Provincia(Id_Provincia);
        this.setOtras_sennas(Otras_sennas);
        this.setUsuario(usuario);
    
    }

  

    public Provincia getId_Provincia() {
        return Id_Provincia;
    }

    public void setId_Provincia(Provincia Id_Provincia) {
        this.Id_Provincia = Id_Provincia;
    }

    public Canton getId_Canton() {
        return Id_Canton;
    }

    public void setId_Canton(Canton Id_Canton) {
        this.Id_Canton = Id_Canton;
    }

    public Distrito getId_Distrito() {
        return Id_Distrito;
    }

    public void setId_Distrito(Distrito Id_Distrito) {
        this.Id_Distrito = Id_Distrito;
    }

    public Barrio getId_Barrio() {
        return Id_Barrio;
    }

    public void setId_Barrio(Barrio Id_Barrio) {
        this.Id_Barrio = Id_Barrio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getOtras_sennas() {
        return Otras_sennas;
    }

    public void setOtras_sennas(String Otras_sennas) {
        this.Otras_sennas = Otras_sennas;
    }
    
}
