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
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.Provincia;
import model.ProvinciaDB;
import model.RolUsuario;
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
@Named(value = "registrarBean")
@SessionScoped
public class registrarBean implements Serializable {
int cedula;
    TipoIdentificacion TipoIden;
    Date fechaNacimiento;
    String correo;
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
     * Creates a new instance of RegistroBean
     */
    public registrarBean() throws SNMPExceptions, SQLException {
        llenarCombos();
    }

    public void llenarCombos() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        CantonDB can = new CantonDB();
        DistritoDB dis = new DistritoDB();
        BarrioDB barr = new BarrioDB();
        TipoTelefonoDB tel = new TipoTelefonoDB();
        ProgramaDB progra = new ProgramaDB();
        UsuarioDB usuarioDB = new UsuarioDB();
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
        if (!tipoIden.SeleccionarTodos().isEmpty()) {
            listaIden = tipoIden.SeleccionarTodos();
            id_TipoCedula = tipoIden.SeleccionarTodos().element().getId_TipoIdentificacion();
        }
    }

    public boolean validaAutoRegistro() {
        boolean respuesta;
        if (this.getId_TipoCedula() == 0) {
            this.setMensaje("*Debe colocar el tipo de identificación.");
            respuesta = false;
        } else {
            if (this.getCedula()==0) {
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
                                if (listaDirec.isEmpty()) {
                                    this.setMensaje("*Debe agregar al menos una dirección");
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
                                            if (listaTel.isEmpty()) {
                                                this.setMensaje("*Debe agregar al menos un telefono.");
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

        return respuesta;
    }

    /*Valida campos de numeros*/
    public boolean validarNumero() {
        boolean respuesta;
        if (this.getId_TipoTelefono() == 0) {
            this.setMensaje2("*Debe colocar el tipo de Teléfono.");
            respuesta = false;
        } else {
            if (this.getNumeroTelefono().equals("")) {
                this.setMensaje2("*Debe colocar el número de teléfono.");
                respuesta = false;
            } else {
                respuesta = true;
            }
        }
        return respuesta;
    }

    /*Valida campos de direcciones*/
    public boolean validarDirecciones() {
        boolean respuesta;
        if (this.getId_Provincia() == 0) {
            this.setMensaje1("*Debe colocar la Provincia.");
            respuesta = false;
        } else {
            if (this.getId_Canton() == 0) {
                this.setMensaje1("*Debe colocar la Cantón.");
                respuesta = false;
            } else {
                if (this.getId_Distrito() == 0) {
                    this.setMensaje1("*Debe colocar la Distrito.");
                    respuesta = false;
                } else {
                    if (this.getId_Barrio() == 0) {
                        this.setMensaje1("*Debe colocar la Barrio.");
                        respuesta = false;
                    } else {
                        if (this.getOtrasSenas().equals("")) {
                            this.setMensaje1("*Debe colocar las otras señas.");
                            respuesta = false;
                        } else {
                            respuesta = true;
                        }
                    }
                }
            }
        }
        return respuesta;
    }

    /*Agrega direcciones a la lista*/
    public void agregarDireccones() throws SNMPExceptions, SQLException {
        Direccion direc = new Direccion();
        ProvinciaDB pro = new ProvinciaDB();
        CantonDB can = new CantonDB();
        DistritoDB dis = new DistritoDB();
        BarrioDB barr = new BarrioDB();

        if (validarDirecciones()) {
            direc.setProvincia(pro.SeleccionarPorId(this.getId_Provincia()));
            direc.setCanton(can.SeleccionarPorId(this.getId_Canton(), this.getId_Provincia()));
            direc.setDistrito(dis.SeleccionarPorId(this.getId_Distrito(), this.getId_Canton(), this.getId_Provincia()));
            direc.setBarrio(barr.SeleccionarPorId(this.getId_Provincia(), this.getId_Canton(), this.getId_Distrito(), this.getId_Barrio()));
            direc.setOtras_sennas(this.getOtrasSenas());       
            listaDirec.add(direc);
            limpiarDireccion();
        }

    }

    /*Elimina Direcciones*/
    public void eliminarDirecciones(String otras) {
        for (Direccion dir : listaDirec) {

            if (dir.getOtras_sennas().equals(otras)) {

                listaDirec.remove(dir);
            }

        }
    }

    /*Limpia los campos de Direccion*/
    public void limpiarDireccion() {
        this.setId_Barrio(0);
        this.setId_Provincia(0);
        this.setId_Canton(0);
        this.setId_Distrito(0);
        this.setOtrasSenas("");
    }

    /*Agrega telefonos a la lista */
    public void agregarTelefonos() throws SNMPExceptions, SQLException {

        TipoTelefonoDB telefo = new TipoTelefonoDB();
        if (validarNumero()) {
            Telefono tel = new Telefono();
            tel.setId_TipoTelefono(telefo.SeleccionarPorId(this.getId_TipoTelefono()));
            tel.setNumero(this.getNumeroTelefono());
            listaTel.add(tel);
            limpiarTelefono();
        }
    }

    /*Limpia campos en telefono*/
    public void limpiarTelefono() {
        this.setNumeroTelefono("");
        this.setId_TipoTelefono(0);
    }

    /*Elimina telefono*/
    public void eliminarTelefono(String numero) {
        for (Telefono tel : listaTel) {

            if (tel.getNumero().equals(numero)) {

                listaTel.remove(tel);
            }

        }
    }

    /*Registra el usuario*/
    public void ingresarUsuario() throws SNMPExceptions, SQLException {
         TipoIdentificacionDB tipoidenDB = new TipoIdentificacionDB();
        ProgramaDB prograDB = new ProgramaDB();
        RolUsuarioDB rolDB = new RolUsuarioDB();
        UsuarioDB usuDB = new UsuarioDB();
        DireccionDB direcDB = new DireccionDB();
        TelefonoDB telDB = new TelefonoDB();
        ProgramaUsuarioDB programaUsuarioDB = new ProgramaUsuarioDB();

        if (validaAutoRegistro()) {
            Usuario usu = new Usuario();
            usu.setTipoIdentificacion(tipoidenDB.SeleccionarPorId(this.getId_TipoCedula()));
            usu.setId(this.getCedula());
            usu.setNombre(this.getNombre());
            usu.setApellido1(this.getApellido1());
            usu.setApellido2(this.getApellido2());
            usu.setFechaNacimiento(this.getFechaNacimiento());
            Programa progra = new Programa();
            progra = prograDB.SeleccionarPorId(this.getPrograma());
            usu.setPrograma(progra);
            usu.setCorreo(this.getCorreo());
            usu.setFuncionario(this.getFuncionario());
            usuDB.registrar(usu);
            RolUsuario rol1 = rolDB.SeleccionarPorId(2);
            /*agregar telefono*/
            
            for (Telefono tel : listaTel) {
                tel.setId_Usuario(usu);
                telDB.registrar(tel);
            }
            /*agregar direcciones*/
            for (Direccion dir : listaDirec) {
                dir.setUsuario(usu);
                direcDB.registrar(dir);
            }
            /*Agrega programa_usuario*/
           ProgramaUsuario prousu = new ProgramaUsuario(usu, progra, rol1, this.getFuncionario(), "1");
            
             programaUsuarioDB.registrar(prousu);

            setMensaje("Su solicitud de registro ha sido enviada. Se le enviara un correo con el codigo de acceso y su comtraseña al correo");
        }
    }

    public LinkedList<TipoTelefono> getListaTipoTelefono() {

        return listaTipoTelefono;
    }

    public void setListaTipoTelefono(LinkedList<TipoTelefono> listaTipoTelefono) {
        this.listaTipoTelefono = listaTipoTelefono;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
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
