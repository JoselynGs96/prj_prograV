/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author ujose
 */
public class ProgramaUsuario {
    Usuario usuario = null;
    Programa programa = null;
    RolUsuario rolUsuario = null;
    String estado = "";
    int Id_Registra = 0;
    Date FechaRegistra = null;
    int Id_Edita = 0;
    Date FechaEdita = null;
    EnumFuncionario Funcionario = null;
     
    public ProgramaUsuario() {
    }
    
    public ProgramaUsuario(Programa programa, RolUsuario rolUsuario, EnumFuncionario Funcionario, String estado) {
        this.setPrograma(programa);
        this.setRolUsuario(rolUsuario);
        this.setFuncionario(Funcionario);
        this.setEstado(estado);
    }
    
     public ProgramaUsuario(Usuario usu, Programa programa, RolUsuario rolUsuario, EnumFuncionario Funcionario, String estado) {
        this.setUsuario(usu);
        this.setPrograma(programa);
        this.setRolUsuario(rolUsuario);
        this.setFuncionario(Funcionario);
        this.setEstado(estado);
    }


    public ProgramaUsuario(Programa programa, RolUsuario rolUsuario, EnumFuncionario Funcionario ,String estado, int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita) {
        this.setPrograma(programa);
        this.setRolUsuario(rolUsuario);
        this.setEstado(estado);
        this.setId_Registra(Id_Registra);
        this.setFechaRegistra(FechaRegistra);
        this.setId_Edita(Id_Edita);
        this.setFechaEdita(FechaEdita);
    }

    public ProgramaUsuario(Programa programa, RolUsuario rolUsuario, String estado, int Id_Registra, Date FechaRegistra, int Id_Edita, Date FechaEdita, EnumFuncionario Funcionario) {
        this.setPrograma(programa);
        this.setRolUsuario(rolUsuario);
        this.setEstado(estado);
        this.setId_Registra(Id_Registra);
        this.setFechaRegistra(FechaRegistra);
        this.setId_Edita(Id_Edita);
        this.setFechaEdita(FechaEdita);
        this.setFuncionario(Funcionario);
    }
    
    
    public ProgramaUsuario(Programa programa, RolUsuario rolUsuario, String estado) {
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

    public EnumFuncionario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(EnumFuncionario Funcionario) {
        this.Funcionario = Funcionario;
    }
    
    
    
}
