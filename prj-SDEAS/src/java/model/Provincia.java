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
public class Provincia {
    int Id_Provincia;
    String Dsc_Corta_Provincia;
    String Dsc_Provincia;
    
    public Provincia() {
    }
     public Provincia( int Id_Provincia,String Dsc_Corta_Provincia,String Dsc_Provincia) {
         this.setId_Provincia(Id_Provincia);
         this.setDsc_Corta_Provincia(Dsc_Corta_Provincia);
         this.setDsc_Provincia(Dsc_Provincia);
    }
    
    public int getId_Provincia() {
        return Id_Provincia;
    }

    public void setId_Provincia(int Id_Provincia) {
        this.Id_Provincia = Id_Provincia;
    }

    public String getDsc_Corta_Provincia() {
        return Dsc_Corta_Provincia;
    }

    public void setDsc_Corta_Provincia(String Dsc_Corta_Provincia) {
        this.Dsc_Corta_Provincia = Dsc_Corta_Provincia;
    }

    public String getDsc_Provincia() {
        return Dsc_Provincia;
    }

    public void setDsc_Provincia(String Dsc_Provincia) {
        this.Dsc_Provincia = Dsc_Provincia;
    }
   
}
