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
    Provincia Id_Provincia;
    Canton Id_Canton;
       public Distrito() {
    }
     public Distrito( int Id_Distrito,String Dsc_Distrito,Provincia Id_Provincia, Canton Id_Canton) {
         this.setId_Distrito(Id_Distrito);
         this.setDsc_Distrito(Dsc_Distrito);
         this.setId_Provincia(Id_Provincia);
         this.setId_Canton(Id_Canton);
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
    
    
}
