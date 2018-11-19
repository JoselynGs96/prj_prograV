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
import model.Distrito;
import model.DistritoDB;
import model.Provincia;
import model.ProvinciaDB;
import model.TipoFuncionario;
import model.TipoIden;
import model.TipoTelefono;
import model.TipoTelefonoDB;
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
    String Programa;
    String OtrasSenas;
    TipoFuncionario TipoFuncionario;
    int edad;
    int Id_Provincia;
    int Id_Canton;
    int Id_Distrito;
    int id_Barrio;
    LinkedList<Provincia> listaPro = new LinkedList<Provincia>();
    LinkedList<Canton> listaCan = new LinkedList<Canton>();
    LinkedList<Distrito> listaDis = new LinkedList<Distrito>();
    LinkedList<Barrio> listaBarrio = new LinkedList<Barrio>();
    int id_TipoTelefono;
     LinkedList<TipoTelefono> listaTipoTelefono = new LinkedList<TipoTelefono>();

   
   

    /**
     * Creates a new instance of UsuariosBean
     */
    public UsuariosBean() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        CantonDB can = new CantonDB();
        DistritoDB dis = new DistritoDB();
        BarrioDB barr = new BarrioDB();
        TipoTelefonoDB  tel = new TipoTelefonoDB();

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

    public void validaAutoRegistro() {
        if (this.getTipoIden().equals("-Seleccionar-")) {
            this.setMensaje("*Debe colocar el tipo de identificación.");
        } else {
            if (this.getCedula().equals("")) {
                this.setMensaje("*Debe colocar el usuario.");
            } else {
                if (this.getNombre().equals("")) {
                    this.setMensaje("*Debe colocar el Nombre");
                } else {
                    if (this.getApellido1().equals("")) {
                        this.setMensaje("*Debe colocar el Primer Apellido");
                    } else {
                        if (this.getApellido2().equals("")) {
                            this.setMensaje("*Debe colocar el Segundo Apellido");
                        } else {
                            if (this.getFechaNacimiento() == null) {
                                this.setMensaje("*Debe colocar la fecha de nacimiento");
                            } else {
                                if (this.getTipoTelefono().equals("-Seleccionar-")) {
                                    this.setMensaje("*Debe colocar el tipo de telefono");
                                } else {
                                    if (this.getCorreo().equals("")) {
                                        this.setMensaje("*Debe colocar un correo electrónico");
                                    } else {
                                        if (this.getPrograma().equals("-Seleccionar-")) {
                                            this.setMensaje("*Debe colocar el programa al que pertenece");
                                        } else {
                                            if (this.getTipoFuncionario().equals("-Seleccionar-")) {
                                                this.setMensaje("*Debe colocar el tipo de funcionario");
                                            } else {
                                                if (this.getId_Provincia() == 0) {
                                                    this.setMensaje("*Debe colocar la provincia");
                                                } else {
                                                    if (this.getId_Canton() == 0) {
                                                        this.setMensaje("*Debe colocar el Cantón");
                                                    } else {
                                                        if (this.getId_Distrito() == 0) {
                                                            this.setMensaje("*Debe colocar el Distrito");
                                                        } else {
                                                            if (this.getId_Barrio() == 0) {
                                                                this.setMensaje("*Debe colocar el Barrio");
                                                            } else {
                                                                if (this.getOtrasSenas().equals("")) {
                                                                    this.setMensaje("*Debe colocar Otras señas de su direccion ");
                                                                } else {
                                                                    this.setMensaje("Datos completos se le enviará la solicitud a un coordinador y posterior mente le estará llegando un correo con el código de acceso");

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

    public String getPrograma() {
        return Programa;
    }

    public void setPrograma(String Programa) {
        this.Programa = Programa;
    }

    public String getOtrasSenas() {
        return OtrasSenas;
    }

    public void setOtrasSenas(String OtrasSenas) {
        this.OtrasSenas = OtrasSenas;
    }

    public TipoFuncionario getTipoFuncionario() {
        return TipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario TipoFuncionario) {
        this.TipoFuncionario = TipoFuncionario;
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

}
