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
import java.util.Iterator;
import java.util.LinkedList;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.mail.Part;
import model.Canton;
import model.CantonDB;
import model.Distrito;
import model.DistritoDB;
import model.Provincia;
import model.ProvinciaDB;

/**
 *
 * @author Fabi
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    String cedula;
    String TipoIden;
    Date fechaNacimiento;
    String correo;
    String contrasenna;
    String codAcceso;
    String estado;
    String mensaje;
    String tipoPerfil;
    String nombre;
    String apellido1;
    String apellido2;
    String tipoTelefono;
    String NumeroTelefono;
    String Programa;
    String OtrasSenas;
    String TipoFuncionario;
    int edad;
    Part file;
    LinkedList listaPro = new LinkedList();
    LinkedList listaCan = new LinkedList();
    LinkedList listaDis = new LinkedList();
    static int IdProvincia;
    static int IdCanton;
   static int IdDistrito;
    static int idBarrio;

    public int getIdDistrito() {
        return IdDistrito;
    }

    public void setIdDistrito(int IdDistrito) {
        this.IdDistrito = IdDistrito;
    }

    public int getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(int idBarrio) {
        this.idBarrio = idBarrio;
    }

    public LinkedList getListaDis() {
        return listaDis;
    }

    public void setListaDis(LinkedList listaDis) {
        this.listaDis = listaDis;
    }

    public LinkedList<SelectItem> getListaCan() throws SNMPExceptions, SQLException {
         String dscCanton="";
        float codigoCanton=0;
        CantonDB canDB = new CantonDB();
        
        
         LinkedList<Canton> lista = new LinkedList<Canton>();
         lista= canDB.SeleccionarTodos(this.getIdProvincia());
         
          LinkedList listares = new LinkedList();
         for(Iterator iter = lista.iterator(); iter.hasNext();){
             Canton can=(Canton) iter.next();
             dscCanton = can.getDsc_Canton();
             codigoCanton= can.getId_Canton();
             listares.add(new SelectItem(codigoCanton,dscCanton));
             
         }
        
        return listares;
        
    }

    public void setListaCan(LinkedList listaCan) {
        this.listaCan = listaCan;
    }

    public int getIdCanton() {
        return IdCanton;
    }

    public void setIdCanton(int IdCanton) {
        this.IdCanton = IdCanton;
    }

    public int getIdProvincia() {
        return IdProvincia;
    }

    public void setIdProvincia(int IdProvincia) {
        this.IdProvincia = IdProvincia;
    }

    public LinkedList<SelectItem> getListaPro() throws SNMPExceptions, SQLException {
        String dscProvincia="";
        float codigoProvincia=0;
        ProvinciaDB proDB = new ProvinciaDB();
        
        
         LinkedList<Provincia> lista = new LinkedList<Provincia>();
         lista= proDB.SeleccionarTodos();
         
          LinkedList listares = new LinkedList();
         for(Iterator iter = lista.iterator(); iter.hasNext();){
             Provincia pro=(Provincia) iter.next();
             dscProvincia = pro.getDsc_Provincia();
             codigoProvincia= pro.getId_Provincia();
             listares.add(new SelectItem(codigoProvincia,dscProvincia));
             
         }
        
        return listares;
    }

    public void setListaPro(LinkedList listaPro) {
        this.listaPro = listaPro;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipoFuncionario() {
        return TipoFuncionario;
    }

    public void setTipoFuncionario(String TipoFuncionario) {
        this.TipoFuncionario = TipoFuncionario;
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

    public String getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoIden() {
        return TipoIden;
    }

    public void setTipoIden(String TipoIden) {
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

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
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
        if (this.getFile() == null) {
            this.setMensaje("*Debe subir una foto de perfil");
        } else {
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
                                                    if (this.getIdProvincia() == 0) {
                                                        this.setMensaje("*Debe colocar la provincia");
                                                    } else {
                                                        if (this.getIdCanton() == 0) {
                                                            this.setMensaje("*Debe colocar el Cantón");
                                                        } else {
                                                            if (this.getIdDistrito() == 0) {
                                                                this.setMensaje("*Debe colocar el Distrito");
                                                            } else {
                                                                if (this.getIdBarrio() == 0) {
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
    }

    public UsuarioBean() throws SNMPExceptions, SQLException {
        
    }

    
}
