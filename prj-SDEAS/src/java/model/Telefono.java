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
public class Telefono {
    int Numero;
    Usuario Id_Usuario;
    TipoTelefono Id_TipoTelefono;
    
    public Telefono(){
        
    }
     public Telefono( int Numero,Usuario Id_Usuario,TipoTelefono Id_TipoTelefono){
        this.setId_TipoTelefono(Id_TipoTelefono);
        this.setId_Usuario(Id_Usuario);
        this.setNumero(Numero);
    }
    

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int Numero) {
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
    
}
