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
public class Distrito {
    int Id_Distrito;
    String Dsc_Distrito;
    Provincia Provincia;
    Canton Canton;
       public Distrito() {
    }
     public Distrito( int Id_Distrito,String Dsc_Distrito,Provincia Id_Provincia, Canton Id_Canton) {
         this.setId_Distrito(Id_Distrito);
         this.setDsc_Distrito(Dsc_Distrito);
         this.setProvincia(Id_Provincia);
         this.setCanton(Id_Canton);
    }
     

    public int getId_Distrito() {
        return Id_Distrito;
    }

    public void setId_Distrito(int Id_Distrito) {
        this.Id_Distrito = Id_Distrito;
    }

    public String getDsc_Distrito() {
        return Dsc_Distrito;
    }

    public void setDsc_Distrito(String Dsc_Distrito) {
        this.Dsc_Distrito = Dsc_Distrito;
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

    
    
    
}
