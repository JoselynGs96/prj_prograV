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
public class Telefono {
    String Numero;
    Usuario Id_Usuario;
    TipoTelefono Id_TipoTelefono;
    String id_Telefono = "";
    int Id_Registra;
    Date FechaRegistra;
    int Id_Edita;
    Date FechaEdita; 
    
   
    
    public Telefono(){
        
    }
     public Telefono( String Numero,TipoTelefono Id_TipoTelefono){
        
        this.setId_TipoTelefono(Id_TipoTelefono); 
        this.setNumero(Numero);
    }

    public Telefono(String Numero, Usuario Id_Usuario, TipoTelefono Id_TipoTelefono,  int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita ) {
        this.setId_TipoTelefono(Id_TipoTelefono); 
        this.setNumero(Numero);
        this.setId_Registra(Id_Registra);
        this.setFechaRegistra(FechaRegistra);
        this.setId_Edita(Id_Edita);
        this.setFechaEdita(FechaEdita);
    }
    
     

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public Usuario getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(Usuario Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    public TipoTelefono getId_TipoTelefono() {
        return Id_TipoTelefono;
    }

    public void setId_TipoTelefono(TipoTelefono Id_TipoTelefono) {
        this.Id_TipoTelefono = Id_TipoTelefono;
    }
     public String getId_Telefono() {
        return id_Telefono;
    }

    public void setId_Telefono(String id_Telefono) {
        this.id_Telefono = id_Telefono;
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
