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
public class RolUsuario {

    int Id_RolUsuario;
    String Dsc_RolUsuario;

    public RolUsuario() {

    }

    public RolUsuario(int Id_RolUsuario, String Dsc_RolUsuario) {
        this.setDsc_RolUsuario(Dsc_RolUsuario);
        this.setId_RolUsuario(Id_RolUsuario);
    }

    public int getId_RolUsuario() {
        return Id_RolUsuario;
    }

    public void setId_RolUsuario(int Id_RolUsuario) {
        this.Id_RolUsuario = Id_RolUsuario;
    }

    public String getDsc_RolUsuario() {
        return Dsc_RolUsuario;
    }

    public void setDsc_RolUsuario(String Dsc_RolUsuario) {
        this.Dsc_RolUsuario = Dsc_RolUsuario;
    }

}
