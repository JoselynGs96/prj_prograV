/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ObtenerDatosSesion;
import dao.SNMPExceptions;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import model.Barrio;
import model.BarrioDB;
import model.Canton;
import model.CantonDB;
import model.Direccion;
import model.DireccionDB;
import model.Distrito;
import model.DistritoDB;
import model.EnumFuncionario;
import model.Programa;
import model.ProgramaDB;
import model.Provincia;
import model.ProvinciaDB;
import model.RolUsuarioDB;
import model.Telefono;
import model.TelefonoDB;
import model.TipoIdentificacion;
import model.TipoIdentificacionDB;
import model.TipoTelefono;
import model.TipoTelefonoDB;
import model.Usuario;
import model.UsuarioDB;

/**
 *
 * @author Fabi
 */
@Named(value = "usuarioBen")
@Dependent
public class UsuarioBean {

    String cedula;
    TipoIdentificacion TipoIden;
    Date fechaNacimiento;
    String correo;
    String contrasenna;
    String codAcceso;
    String estado;
    String mensaje;
    String mensaje1;
    String mensaje2;
    String nombre;
    String apellido1;
    String apellido2;
    TipoTelefono tipoTelefono;
    String NumeroTelefono;
    int Programa;
    String OtrasSenas;
    int edad;
    int Id_Provincia;
    int Id_Canton;
    int Id_Distrito;
    int id_Barrio;
    int id_TipoTelefono;
    int id_TipoCedula;
    String botonNombre;
    LinkedList<Provincia> listaPro = new LinkedList<Provincia>();
    LinkedList<Canton> listaCan = new LinkedList<Canton>();
    LinkedList<Distrito> listaDis = new LinkedList<Distrito>();
    LinkedList<Barrio> listaBarrio = new LinkedList<Barrio>();
    LinkedList<TipoTelefono> listaTipoTelefono = new LinkedList<TipoTelefono>();
    LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
    LinkedList<TipoIdentificacion> listaIden = new LinkedList<TipoIdentificacion>();
    LinkedList<Direccion> listaDirec = new LinkedList<Direccion>();
    LinkedList<Telefono> listaTel = new LinkedList<Telefono>();
    EnumFuncionario funcionario;
    Usuario UsuarioMantenimiento = new Usuario();

    public EnumFuncionario[] EnumFuncionario() {
        return EnumFuncionario.values();
    }

    /**
     * Creates a new instance of UsuarioBen
     * @throws dao.SNMPExceptions
     * @throws java.sql.SQLException
     */
    public UsuarioBean() throws SNMPExceptions, SQLException{
        /*Metodo para cargas combos*/
           
    } 

    /*Carga todo lo de la pagina de Actualizar Usuario*/
    public void PaginaRegistro() throws SNMPExceptions, SQLException {
        DireccionDB direc = new DireccionDB();
        TelefonoDB teldb = new TelefonoDB();
        if (this.UsuarioMantenimiento.getCed() != 0) {
            this.setBotonNombre("Actualizar Información");
            /*Pantalla personal*/
            this.setNombre(UsuarioMantenimiento.getNombre());
            this.setApellido1(UsuarioMantenimiento.getApellido1());
            this.setApellido2(UsuarioMantenimiento.getApellido2());
            this.setApellido2(UsuarioMantenimiento.getApellido2());
            this.setCedula(UsuarioMantenimiento.getCedula());
            /*Buscar como hacer q este funcione*/
            this.setFechaNacimiento(UsuarioMantenimiento.getFechaNacimiento());
            /*Pantalla Direccion*/

            if (!direc.SeleccionarPorUsuario(UsuarioMantenimiento.getCedula()).isEmpty()) {
                listaDirec = direc.SeleccionarPorUsuario(UsuarioMantenimiento.getCedula());
            }
            /*Pantalla de telefono*/
            if (!teldb.SeleccionarTodos(UsuarioMantenimiento.getCedula()).isEmpty()) {
                listaTel = teldb.SeleccionarTodos(UsuarioMantenimiento.getCedula());
            }
            this.setCorreo(UsuarioMantenimiento.getCorreo());
            /*Pantalla Progrema Deas*/
            this.setFuncionario(UsuarioMantenimiento.getFuncionario());
        } else {
            this.setBotonNombre("Registrarse");
        }
    }


   

    /*valida pagina de registro*/
   

   

  

   

  

    public LinkedList<TipoTelefono> getListaTipoTelefono() {

        return listaTipoTelefono;
    }

    public void setListaTipoTelefono(LinkedList<TipoTelefono> listaTipoTelefono) {
        this.listaTipoTelefono = listaTipoTelefono;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getId_TipoTelefono() {
        return id_TipoTelefono;
    }

    public void setId_TipoTelefono(int id_TipoTelefono) {
        this.id_TipoTelefono = id_TipoTelefono;
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

    public TipoTelefono getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(TipoTelefono tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String NumeroTelefono) {
        this.NumeroTelefono = NumeroTelefono;
    }

    public int getPrograma() {
        return Programa;
    }

    public void setPrograma(int Programa) {
        this.Programa = Programa;
    }

    public String getOtrasSenas() {
        return OtrasSenas;
    }

    public void setOtrasSenas(String OtrasSenas) {
        this.OtrasSenas = OtrasSenas;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LinkedList<Provincia> getListaPro() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        return pro.SeleccionarTodos();
    }

    public void setListaPro(LinkedList<Provincia> listaPro) {
        this.listaPro = listaPro;
    }

    public LinkedList<Canton> getListaCan() throws SNMPExceptions, SQLException {
        CantonDB can = new CantonDB();
        return can.SeleccionarTodos(this.getId_Provincia());
    }

    public void setListaCan(LinkedList<Canton> listaCan) {
        this.listaCan = listaCan;
    }

    public LinkedList<Distrito> getListaDis() throws SNMPExceptions, SQLException {
        DistritoDB dis = new DistritoDB();
        return dis.SeleccionarTodos(this.getId_Provincia(), this.getId_Canton());
    }

    public void setListaDis(LinkedList<Distrito> listaDis) {
        this.listaDis = listaDis;
    }

    public LinkedList<Barrio> getListaBarrio() throws SNMPExceptions, SQLException {
        BarrioDB barr = new BarrioDB();
        return barr.SeleccionarTodos(this.getId_Provincia(), this.getId_Canton(), this.getId_Distrito());
    }

    public void setListaBarrio(LinkedList<Barrio> listaBarrio) {
        this.listaBarrio = listaBarrio;
    }

    public int getId_Provincia() {
        return Id_Provincia;
    }

    public void setId_Provincia(int Id_Provincia) {
        this.Id_Provincia = Id_Provincia;
    }

    public int getId_Canton() {
        return Id_Canton;
    }

    public void setId_Canton(int Id_Canton) {
        this.Id_Canton = Id_Canton;
    }

    public int getId_Distrito() {
        return Id_Distrito;
    }

    public void setId_Distrito(int Id_Distrito) {
        this.Id_Distrito = Id_Distrito;
    }

    public int getId_Barrio() {
        return id_Barrio;
    }

    public void setId_Barrio(int id_Barrio) {
        this.id_Barrio = id_Barrio;
    }

    public LinkedList<Programa> getListaPrograma() {
        return listaPrograma;
    }

    public void setListaPrograma(LinkedList<Programa> listaPrograma) {
        this.listaPrograma = listaPrograma;
    }

    public int getId_TipoCedula() {
        return id_TipoCedula;
    }

    public void setId_TipoCedula(int id_TipoCedula) {
        this.id_TipoCedula = id_TipoCedula;
    }

    public LinkedList<TipoIdentificacion> getListaIden() {
        return listaIden;
    }

    public void setListaIden(LinkedList<TipoIdentificacion> listaIden) {
        this.listaIden = listaIden;
    }

    public LinkedList<Direccion> getListaDirec() {
        return listaDirec;
    }

    public void setListaDirec(LinkedList<Direccion> listaDirec) {
        this.listaDirec = listaDirec;
    }

    public LinkedList<Telefono> getListaTel() {
        return listaTel;
    }

    public void setListaTel(LinkedList<Telefono> listaTel) {
        this.listaTel = listaTel;
    }

    public String getMensaje1() {
        return mensaje1;
    }

    public void setMensaje1(String mensaje1) {
        this.mensaje1 = mensaje1;
    }

    public String getMensaje2() {
        return mensaje2;
    }

    public void setMensaje2(String mensaje2) {
        this.mensaje2 = mensaje2;
    }

    public EnumFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(EnumFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getBotonNombre() {
        return botonNombre;
    }

    public void setBotonNombre(String botonNombre) {
        this.botonNombre = botonNombre;
    }

    public Usuario getUsuarioMantenimiento() {
        return UsuarioMantenimiento;
    }

    public void setUsuarioMantenimiento(Usuario UsuarioMantenimiento) {
        this.UsuarioMantenimiento = UsuarioMantenimiento;
    }
}
