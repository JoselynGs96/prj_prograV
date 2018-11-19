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
public class TipoIdentificacion {
    int Id_TipoIdentificacion; 
    String Dsc_TipoTelefono;
    
    public TipoIdentificacion(){
        
    }
    public  TipoIdentificacion(  int Id_TipoIdentificacion,String Dsc_TipoTelefono){
        this.setDsc_TipoTelefono(Dsc_TipoTelefono);
        this.setId_TipoIdentificacion(Id_TipoIdentificacion);
      
    } 
    
    
    
    
    
    
    
    
    
    
    
    

    public int getId_TipoIdentificacion() {
        return Id_TipoIdentificacion;
    }

    public void setId_TipoIdentificacion(int Id_TipoIdentificacion) {
        this.Id_TipoIdentificacion = Id_TipoIdentificacion;
    }

 
    public String getDsc_TipoTelefono() {
        return Dsc_TipoTelefono;
    }

    public void setDsc_TipoTelefono(String Dsc_TipoTelefono) {
        this.Dsc_TipoTelefono = Dsc_TipoTelefono;
    }
}
