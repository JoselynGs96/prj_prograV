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
    int Id;
    TipoIdentificacion TipoIdentificacion;
    String Nombre = "";
    String Apellido1 = "";
    String Apellido2 = "";
    Date FechaNacimiento;
    String Correo;
    EstadoAcceso EstadoAcceso;
    String Log_Activo;
    int TipoId;
    String contrasenna;
    String codAcceso;
    RolUsuario rolUsuario;
    Programa Programa;  
    String nombreCompleto = "";
    EnumFuncionario Funcionario = null;
    int Id_Registra;
    Date FechaRegistra;
    int Id_Edita;
    Date FechaEdita;
    int PrimeraVez;
    
    
    public Usuario( int cedula,TipoIdentificacion TipoIden,String nombre,String apellido1, String apellido2,Date fechaNacimiento,String correo,EstadoAcceso estAcc,String Log_Act){
        this.setId(cedula);
        this.setTipoIdentificacion(TipoIden);
        this.setNombre(nombre);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
        this.setFechaNacimiento(fechaNacimiento);
        this.setEstadoAcceso(estAcc);
        this.setCorreo(correo);
        this.setLog_Activo(Log_Activo);
        
    
    }
    public Usuario(){
        
    }
    
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return TipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion TipoIdentificacion) {
        this.TipoIdentificacion = TipoIdentificacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String Apellido1) {
        this.Apellido1 = Apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String Apellido2) {
        this.Apellido2 = Apellido2;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public EstadoAcceso getEstadoAcceso() {
        return EstadoAcceso;
    }

    public void setEstadoAcceso(EstadoAcceso EstadoAcceso) {
        this.EstadoAcceso = EstadoAcceso;
    }

    public String getLog_Activo() {
        return Log_Activo;
    }

    public void setLog_Activo(String Log_Activo) {
        this.Log_Activo = Log_Activo;
    }

    public int getTipoId() {
        return TipoId;
    }

    public void setTipoId(int TipoId) {
        this.TipoId = TipoId;
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

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public Programa getPrograma() {
        return Programa;
    }

    public void setPrograma(Programa Programa) {
        this.Programa = Programa;
    }

    public String getNombreCompleto() {
        return getNombre() +" "+ getApellido1() +" "+ getApellido2();
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public EnumFuncionario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(EnumFuncionario Funcionario) {
        this.Funcionario = Funcionario;
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

    public int getPrimeraVez() {
        return PrimeraVez;
    }

    public void setPrimeraVez(int PrimeraVez) {
        this.PrimeraVez = PrimeraVez;
    }
    
    


    

}
