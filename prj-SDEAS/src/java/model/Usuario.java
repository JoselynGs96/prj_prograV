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
public class Usuario {
    int id;
    int ced;
    int TipoId;
    String cedula;
    TipoIdentificacion TipoIden;
    Date fechaNacimiento;
    String correo;
    String contrasenna;
    String codAcceso;
    int estado;  
    RolUsuario rolUsuario;
    String nombre;
    String apellido1;
    String apellido2;
    Programa Programa;  
    String est;
    EstadoAcceso estAcc;
    String nombreCompleto;
    ProgramaUsuario pro;
    EnumFuncionario Funcionario;

  
    
    public Usuario(){
        
    }

    public Usuario(int ced, String cedula, TipoIdentificacion TipoIden, Date fechaNacimiento, String correo,  int estado, String nombre, String apellido1, String apellido2, ProgramaUsuario Programa, String est, EstadoAcceso estAcc) {
        this.setCed(ced);
        this.setCedula(cedula);
        this.setTipoIden(TipoIden);
        this.setFechaNacimiento(fechaNacimiento);
        this.setCorreo(correo);
        this.setEstado(estado);
        this.setNombre(nombre);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
        this.setPro(Programa);
        this.setEst(est);
        this.setEstAcc(estAcc);
    }
    
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public TipoIdentificacion getTipoIden() {
        return TipoIden;
    }

    public void setTipoIden(TipoIdentificacion TipoIden) {
        this.TipoIden = TipoIden;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getCodAcceso() {
        return codAcceso;
    }

    public void setCodAcceso(String codAcceso) {
        this.codAcceso = codAcceso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Programa getPrograma() {
        return Programa;
    }

    public void setPrograma(Programa Programa) {
        this.Programa = Programa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCed() {
        return ced;
    }

    public void setCed(int ced) {
        this.ced = ced;
    }

    public int getTipoId() {
        return TipoId;
    }

    public void setTipoId(int TipoId) {
        this.TipoId = TipoId;
    }

    public String getEst() {
        return est;
    }

    public void setEst(String est) {
        this.est = est;
    }

    public EstadoAcceso getEstAcc() {
        return estAcc;
    }

    public void setEstAcc(EstadoAcceso estAcc) {
        this.estAcc = estAcc;
    }

    public String getNombreCompleto() {
        return this.nombre +" "+this.apellido1+" "+this.apellido2;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public ProgramaUsuario getPro() {
        return pro;
    }

    public void setPro(ProgramaUsuario pro) {
        this.pro = pro;
    }

      public EnumFuncionario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(EnumFuncionario Funcionario) {
        this.Funcionario = Funcionario;
    }
    

}
