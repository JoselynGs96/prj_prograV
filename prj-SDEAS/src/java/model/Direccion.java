/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Fabi
 */
public class Direccion {
    Provincia Provincia = null;
    Canton Canton = null;
    Distrito Distrito = null;
    Barrio Barrio = null;
    Usuario usuario = null;
    String Otras_sennas = "";
    String id_direccion = "";
    int Id_Registra = 0;
    Date FechaRegistra = null;
    int Id_Edita = 0; 
    Date FechaEdita = null;

    public String getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(String id_direccion) {
        this.id_direccion = id_direccion;
    }
    
    
    public Direccion(){
        
    }
    public Direccion(Provincia Id_Provincia,Canton Id_Canton,Distrito Id_Distrito,Barrio Id_Barrio,Usuario usuario,String Otras_sennas){
        this.setBarrio(Id_Barrio);
        this.setCanton(Id_Canton);
        this.setDistrito(Id_Distrito);
        this.setProvincia(Id_Provincia);
        this.setOtras_sennas(Otras_sennas);
        this.setUsuario(usuario);
    
    }

    public Direccion(Provincia Id_Provincia, Canton Id_Canton, Distrito Id_Distrito, Barrio Id_Barrio, Usuario usuario, String Otras_sennas, int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita) {
        this.setBarrio(Id_Barrio);
        this.setCanton(Id_Canton);
        this.setDistrito(Id_Distrito);
        this.setProvincia(Id_Provincia);
        this.setOtras_sennas(Otras_sennas);
        this.setUsuario(usuario);
        this.setId_Registra(Id_Registra);
        this.setFechaRegistra(FechaRegistra);
        this.setId_Edita(Id_Edita);
        this.setFechaEdita(FechaEdita);
    }

    public Provincia getProvincia() {
        return Provincia;
    }

    public void setProvincia(Provincia Provincia) {
        this.Provincia = Provincia;
    }

    public Canton getCanton() {
        return Canton;
    }

    public void setCanton(Canton Canton) {
        this.Canton = Canton;
    }

    public Distrito getDistrito() {
        return Distrito;
    }

    public void setDistrito(Distrito Distrito) {
        this.Distrito = Distrito;
    }

    public Barrio getBarrio() {
        return Barrio;
    }

    public void setBarrio(Barrio Barrio) {
        this.Barrio = Barrio;
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

    public int getId_Registra() {
        return Id_Registra;
    }

    public void setId_Registra(int Id_Registra) {
        this.Id_Registra = Id_Registra;
    }

    public Date getFechaRegistra() {
        return FechaRegistra;
    }

    public void setFechaRegistra(Date FechaRegistra) {
        this.FechaRegistra = FechaRegistra;
    }

    public int getId_Edita() {
        return Id_Edita;
    }

    public void setId_Edita(int Id_Edita) {
        this.Id_Edita = Id_Edita;
    }

    public Date getFechaEdita() {
        return FechaEdita;
    }

    public void setFechaEdita(Date FechaEdita) {
        this.FechaEdita = FechaEdita;
    }

    
    
}
