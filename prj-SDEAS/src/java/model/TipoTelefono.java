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
public class TipoTelefono {
    int id_Telefono;
    String Dsc_TipoTelefono;
    
    public TipoTelefono(){
        
    }
    public TipoTelefono(int id_Telefono, String Dsc_TipoTelefono ){
        this.setDsc_TipoTelefono(Dsc_TipoTelefono);
        this.setId_Telefono(id_Telefono);
    }
    
    
    
    
    

    public int getId_Telefono() {
        return id_Telefono;
    }

    public void setId_Telefono(int id_Telefono) {
        this.id_Telefono = id_Telefono;
    }

    public String getDsc_TipoTelefono() {
        return Dsc_TipoTelefono;
    }

    public void setDsc_TipoTelefono(String Dsc_TipoTelefono) {
        this.Dsc_TipoTelefono = Dsc_TipoTelefono;
    }
}
