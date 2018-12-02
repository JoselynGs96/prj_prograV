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
public class Canton {

    int Id_Canton;
    String Dsc_Canton;
    Provincia Provincia;
     public Canton() {
    }
     public Canton( int Id_Canton,String Dsc_Canton,Provincia Id_Provincia) {
         this.setId_Canton(Id_Canton);
         this.setDsc_Canton(Dsc_Canton);
         this.setProvincia(Id_Provincia);
    }
     

    public int getId_Canton() {
        return Id_Canton;
    }

    public void setId_Canton(int Id_Canton) {
        this.Id_Canton = Id_Canton;
    }

    public String getDsc_Canton() {
        return Dsc_Canton;
    }

    public void setDsc_Canton(String Dsc_Canton) {
        this.Dsc_Canton = Dsc_Canton;
    }

    public Provincia getProvincia() {
        return Provincia;
    }

    public void setProvincia(Provincia Provincia) {
        this.Provincia = Provincia;
    }

    
   
}
