/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SNMPExceptions;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Barrio;
import model.BarrioDB;
import model.Canton;
import model.CantonDB;
import model.Direccion;
import model.Distrito;
import model.DistritoDB;
import model.Programa;
import model.ProgramaDB;
import model.Provincia;
import model.ProvinciaDB;
import model.RolUsuario;
import model.RolUsuarioDB;
import model.TipoIden;
import model.TipoIdentificacion;
import model.TipoIdentificacionDB;
import model.TipoTelefono;
import model.TipoTelefonoDB;
import model.Usuario;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Fabi
 */
@Named(value = "usuariosBean")
@SessionScoped
public class UsuariosBean implements Serializable {

    String cedula;
    TipoIden TipoIden;
    Date fechaNacimiento;
    String correo;
    String contrasenna;
    String codAcceso;
    String estado;
    String mensaje;
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
    int id_Rol;
    int id_TipoCedula;
    LinkedList<Provincia> listaPro = new LinkedList<Provincia>();
    LinkedList<Canton> listaCan = new LinkedList<Canton>();
    LinkedList<Distrito> listaDis = new LinkedList<Distrito>();
    LinkedList<Barrio> listaBarrio = new LinkedList<Barrio>();
    LinkedList<TipoTelefono> listaTipoTelefono = new LinkedList<TipoTelefono>();
    LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
    LinkedList<RolUsuario> listarol = new LinkedList<RolUsuario>();
    LinkedList<TipoIdentificacion> listaIden = new LinkedList<TipoIdentificacion>();
    LinkedList<Direccion> listaDirec = new LinkedList<Direccion>();

   

    /**
     * Creates a new instance of UsuariosBean
     */
    public UsuariosBean() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        CantonDB can = new CantonDB();
        DistritoDB dis = new DistritoDB();
        BarrioDB barr = new BarrioDB();
        TipoTelefonoDB tel = new TipoTelefonoDB();
        ProgramaDB progra = new ProgramaDB();
        RolUsuarioDB rol = new RolUsuarioDB();
        TipoIdentificacionDB tipoIden = new TipoIdentificacionDB();

        if (!pro.SeleccionarTodos().isEmpty()) {
            listaPro = pro.SeleccionarTodos();
            Id_Provincia = pro.SeleccionarTodos().element().getId_Provincia();
        }
        if (!can.SeleccionarTodos(Id_Provincia).isEmpty()) {
            listaCan = can.SeleccionarTodos(Id_Provincia);
            Id_Canton = can.SeleccionarTodos(Id_Provincia).element().getId_Canton();
        }
        if (!dis.SeleccionarTodos(Id_Provincia, Id_Canton).isEmpty()) {
            listaDis = dis.SeleccionarTodos(Id_Provincia, Id_Canton);
            Id_Distrito = dis.SeleccionarTodos(Id_Provincia, Id_Canton).element().getId_Distrito();
        }
        if (!barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito).isEmpty()) {
            listaBarrio = barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito);
            id_Barrio = barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito).element().getId_Barrio();
        }
        if (!tel.SeleccionarTodos().isEmpty()) {
            listaTipoTelefono = tel.SeleccionarTodos();
            id_TipoTelefono = tel.SeleccionarTodos().element().getId_Telefono();
        }
        if (!progra.SeleccionarTodos().isEmpty()) {
            listaPrograma = progra.SeleccionarTodos();
            Programa = progra.SeleccionarTodos().element().getId();
        }
        if (!rol.SeleccionarTodos().isEmpty()) {
            listarol = rol.SeleccionarTodos();
            id_Rol = rol.SeleccionarTodos().element().getId_RolUsuario();
        }
        if (!tipoIden.SeleccionarTodos().isEmpty()) {
            listaIden = tipoIden.SeleccionarTodos();
            id_TipoCedula = tipoIden.SeleccionarTodos().element().getId_TipoIdentificacion();
        }

    }

    public void validaIngresar() {
        if (this.getCedula().equals("")) {
            this.setMensaje("*Debe colocar el usuario.");
        } else {
            if (this.getContrasenna().equals("")) {
                this.setMensaje("*Debe colocar la contraseña");
            } else {
                if (this.getCedula().equals("207750517") && this.getContrasenna().equals("123")) {
                    this.setMensaje("Bienvenida Fabiola");
                }
            }
        }
    }

    public boolean validaAutoRegistro() {
        boolean respuesta;
        if (this.getId_TipoCedula() == 0) {
            this.setMensaje("*Debe colocar el tipo de identificación.");
            respuesta = false;
        } else {
            if (this.getCedula().equals("")) {
                this.setMensaje("*Debe colocar la cédula de identificación.");
                respuesta = false;
            } else {
                if (this.getNombre().equals("")) {
                    this.setMensaje("*Debe colocar el Nombre");
                    respuesta = false;
                } else {
                    if (this.getApellido1().equals("")) {
                        this.setMensaje("*Debe colocar el Primer Apellido");
                        respuesta = false;
                    } else {
                        if (this.getApellido2().equals("")) {
                            this.setMensaje("*Debe colocar el Segundo Apellido");
                            respuesta = false;
                        } else {
                            if (this.getFechaNacimiento() == null) {
                                this.setMensaje("*Debe colocar la fecha de nacimiento");
                                respuesta = false;
                            } else {
                                if (this.getId_Provincia() == 0) {
                                    this.setMensaje("*Debe colocar la Provincia.");
                                    respuesta = false;
                                } else {
                                    if (this.getId_Canton() == 0) {
                                        this.setMensaje("*Debe colocar la Cantón.");
                                        respuesta = false;
                                    } else {
                                        if (this.getId_Distrito() == 0) {
                                            this.setMensaje("*Debe colocar la Distrito.");
                                            respuesta = false;
                                        } else {
                                            if (this.getId_Barrio() == 0) {
                                                this.setMensaje("*Debe colocar la Barrio.");
                                                respuesta = false;
                                            } else {
                                                if (this.getOtrasSenas().equals("")) {
                                                    this.setMensaje("*Debe colocar las otras señas.");
                                                    respuesta = false;
                                                } else {
                                                    if (this.getId_TipoTelefono() == 0) {
                                                        this.setMensaje("*Debe colocar el tipo de Teléfono.");
                                                        respuesta = false;
                                                    } else {
                                                        if (this.getNumeroTelefono().equals("")) {
                                                            this.setMensaje("*Debe colocar el número de teléfono.");
                                                            respuesta = false;
                                                        } else {
                                                            if (this.getCorreo().equals("")) {
                                                                this.setMensaje("*Debe colocar el correo electrónico.");
                                                                respuesta = false;
                                                            } else {
                                                                if (this.getPrograma() == 0) {
                                                                    this.setMensaje("*Debe colocar el porgrama al que pertenece");
                                                                    respuesta = false;
                                                                } else {
                                                                    if (this.getId_Rol() == 0) {
                                                                        this.setMensaje("*Debe colocar el tipo de Funcionario.");
                                                                        respuesta = false;
                                                                    } else {
                                                                        this.setMensaje("");
                                                                        respuesta = true;
                                                                    }

                                                                }
                                                            }
                                                        }

                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return respuesta;
    }

    public void IngresarUsuario() {
        if (validaAutoRegistro()) {
            Usuario usu = new Usuario();
            usu.setCedula(this.getCedula());

        }
    }

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

    public TipoIden getTipoIden() {
        return TipoIden;
    }

    public void setTipoIden(TipoIden TipoIden) {
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

    public int getId_Rol() {
        return id_Rol;
    }

    public void setId_Rol(int id_Rol) {
        this.id_Rol = id_Rol;
    }

    public LinkedList<RolUsuario> getListarol() {
        return listarol;
    }

    public void setListarol(LinkedList<RolUsuario> listarol) {
        this.listarol = listarol;
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

}
