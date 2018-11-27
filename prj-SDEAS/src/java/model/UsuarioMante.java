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
public class UsuarioMante {
    int Id;
    TipoIdentificacion TipoIdentificacion;
    String Nombre;
    String Apellido1;
    String Apellido2;
    Date FechaNacimiento;
    String Correo;
    EstadoAcceso EstadoAcceso;
    String Log_Activo;
    String NombreCompleto;
    String Edad;

    public UsuarioMante() {
    }

    public UsuarioMante(int Id, TipoIdentificacion TipoIdentificacion, String Nombre, String Apellido1, String Apellido2, Date FechaNacimiento, String Correo, EstadoAcceso EstadoAcceso, String Log_Activo) {
        this.setId(Id);
        this.setTipoIdentificacion(TipoIdentificacion);
        this.setNombre(Nombre);
        this.setApellido1(Apellido1);
        this.setApellido2(Apellido2);
        this.setFechaNacimiento(FechaNacimiento);
        this.setCorreo(Correo);
        this.setEstadoAcceso(EstadoAcceso);
        this.setLog_Activo(Log_Activo);
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

    public String getNombreCompleto() {
        return getNombre() +" "+ getApellido1() +" "+ getApellido2();
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String Edad) {
        this.Edad = Edad;
    }
    
    
    
}
