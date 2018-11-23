/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ujose
 */
public class ProgramaUsuario {
    Usuario usuario;
    Programa programa;
    RolUsuario rolUsuario;
    String estado;

    public ProgramaUsuario() {
    }

    public ProgramaUsuario(Usuario usuario, Programa programa, RolUsuario rolUsuario, String estado) {
        this.setUsuario(usuario);
        this.setPrograma(programa);
        this.setRolUsuario(rolUsuario);
        this.setEstado(estado);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
