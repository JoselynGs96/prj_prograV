/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ObtenerDatosSesion;
import dao.SNMPExceptions;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import javax.faces.component.behavior.AjaxBehavior;
import model.Barrio;
import model.BarrioDB;
import model.Canton;
import model.CantonDB;
import model.Direccion;
import model.DireccionDB;
import model.Distrito;
import model.DistritoDB;
import model.EnumFuncionario;
import model.EstadoAccesoDB;
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
import model.UsuarioManteDB;

/**
 *
 * @author Fabi
 */
@Named(value = "actualizarUsuarioBean")
@SessionScoped
public class ActualizarUsuarioBean implements Serializable {

    String cedula;
    TipoIdentificacion TipoIden;
    Date fechaNacimiento;
    String correo;
    String mensaje1;
    String mensaje2;
    String msjDireccion;
    String msjTelefono;
    String nombre;
    String apellido1;
    String apellido2;
    TipoTelefono tipoTelefono;
    String NumeroTelefono;
    int Programa;
    String OtrasSenas;
    int edad;
    int Id_Direccion = 0;
    int Id_Provincia = 0;
    int Id_Canton = 0;
    int Id_Distrito = 0;
    int id_Barrio = 0;
    int id_TipoTelefono;
    int id_TipoCedula;
    String Id_Telefono = "";
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
    Date fecha = new Date();
    public EnumFuncionario[] EnumFuncionario() {
        return EnumFuncionario.values();
    }

    
    /**
     * Creates a new instance of ActualizarUsuarioBean
     */
    public ActualizarUsuarioBean() throws SNMPExceptions, SQLException {
        llenarCombos();
        LlenarDatos();
    }

    public void LlenarDatos() throws SNMPExceptions, SQLException {
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
        
        UsuarioDB usuDB = new UsuarioDB();
        datos.consultarSesion();
        UsuarioMantenimiento = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
       
        DireccionDB direc = new DireccionDB();
        TelefonoDB teldb = new TelefonoDB();
       
        if (this.UsuarioMantenimiento.getId()!= 0) {
            this.setBotonNombre("Actualizar Información");
            
            /*Pantalla personal*/
            this.setId_TipoCedula(UsuarioMantenimiento.getTipoIdentificacion().getId_TipoIdentificacion());
            this.setNombre(UsuarioMantenimiento.getNombre());
            this.setApellido1(UsuarioMantenimiento.getApellido1());
            this.setApellido2(UsuarioMantenimiento.getApellido2());
            this.setApellido2(UsuarioMantenimiento.getApellido2());
            this.setCedula(UsuarioMantenimiento.getId()+"");
            
            /*Buscar como hacer q este funcione*/
            this.setFechaNacimiento(UsuarioMantenimiento.getFechaNacimiento());
            
            /*Pantalla Direccion*/
            if (!direc.SeleccionarPorUsuario(UsuarioMantenimiento.getId()).isEmpty()) {
                listaDirec = direc.SeleccionarPorUsuario(UsuarioMantenimiento.getId());
            }
            /*Pantalla de telefono*/
            if (!teldb.SeleccionarTodos(UsuarioMantenimiento.getId()).isEmpty()) {
                listaTel = teldb.SeleccionarTodos(UsuarioMantenimiento.getId());
            }
            this.setCorreo(UsuarioMantenimiento.getCorreo());
            
            /*Pantalla Progrema Deas*/
            this.setFuncionario(UsuarioMantenimiento.getFuncionario());
            this.setFuncionario(UsuarioMantenimiento.getFuncionario());
        } else {
            this.setBotonNombre("Registrarse");
        }

    }
    
    /*Ajax canton*/
    public LinkedList cargarCantonProv(AjaxBehavior evento) throws SNMPExceptions, SQLException {
        CantonDB can = new CantonDB();       
        return can.SeleccionarTodos(this.getId_Provincia());
    }

    /*Ajax distrito*/
    public LinkedList cargarDisCan(AjaxBehavior evento) throws SNMPExceptions, SQLException {
        DistritoDB dis = new DistritoDB();
        return dis.SeleccionarTodos(this.getId_Provincia(), this.getId_Canton());
    }

    /*Ajax barrio*/
    public LinkedList cargarBarDis(AjaxBehavior evento) throws SNMPExceptions, SQLException {
        BarrioDB barr = new BarrioDB();
        return barr.SeleccionarTodos(this.getId_Provincia(), this.getId_Canton(), this.getId_Distrito());
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
            Id_Provincia = listaPro.element().getId_Provincia();
        }
        if (!can.SeleccionarTodos(Id_Provincia).isEmpty()) {
          
            listaCan =  cargarCantonProv(null);
            Id_Canton = listaCan.element().getId_Canton();
        }
        if (!dis.SeleccionarTodos(Id_Provincia, Id_Canton).isEmpty()) {         
            listaDis = cargarDisCan(null);
            Id_Distrito = listaDis.element().getId_Distrito();
        }
        if (!barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito).isEmpty()) {
            listaBarrio =cargarBarDis(null);
            id_Barrio = listaBarrio.element().getId_Barrio();
        }
        if (!tel.SeleccionarTodos().isEmpty()) {
            listaTipoTelefono = tel.SeleccionarTodos();
            id_TipoTelefono = listaTipoTelefono.element().getId_Telefono();
        }
        if (!progra.SeleccionarTodos().isEmpty()) {
            listaPrograma = progra.SeleccionarTodos();
            Programa = listaPrograma.element().getId();
        }
        if (!tipoIden.SeleccionarTodos().isEmpty()) {
            listaIden = tipoIden.SeleccionarTodos();
            id_TipoCedula = listaIden.element().getId_TipoIdentificacion();
        }
    }

    public void actualizar() throws SNMPExceptions, SQLException {
        
        try{
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
            usu.setId(Integer.parseInt(this.getCedula()));
            usu.setNombre(this.getNombre());
            usu.setApellido1(this.getApellido1());
            usu.setApellido2(this.getApellido2());
            usu.setFechaNacimiento(this.getFechaNacimiento());
            Programa progra = new Programa();
            progra = prograDB.SeleccionarPorId(this.getPrograma());
            usu.setCorreo(this.getCorreo());
            usu.setId_Edita(usu.getId());
            usu.setFechaEdita(fecha);
            usuDB.ActualizarUsuario(usu);
            RolUsuario rol1 = rolDB.SeleccionarPorId(2);
            ProgramaUsuario prousu = new ProgramaUsuario(progra, rol1, "1");

            programaUsuarioDB.actulizar(prousu);

            setMensaje2("<div class='alert alert-success alert-dismissible fade in' > <strong>Exitoso&nbsp;</strong>¡Tus datos han sido actualizados con éxito! </div>" );

        }
        }catch(Exception e){
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, hubo un error al actualizar el usuario. Por favor, intentelo de nuevo</div>" );
        }
    }

    public boolean validaAutoRegistro() {
     boolean respuesta = true;
        
        
        if (this.getId_TipoCedula() == 1) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Debe seleccionar el Tipo de Identificación</div>");
            return false;
        } else {
            setMensaje1("");
        }
        
        if (this.getCedula().equals("")) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Número de Identificación requerida</div>");
            return false;
        } else {
            setMensaje1("");
        }
        
        try{
            Integer.parseInt(getCedula());
            respuesta = true;
        }catch(Exception e){
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>´El valor en el campo Cédula no es un dato válido</div>");
            return false;
        }
        
        if(getId_TipoCedula() == 2){
            if(getCedula().toString().length()<9 ||getCedula().toString().length()>9){
                setMensaje1("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>El número de cédula ingresado debe estar en el formato correcto, debe contar con los 9 caracteres </div>");
                return false;
            }else{
                setMensaje1("");
            }
        }
        
        
        if (this.getNombre().equals("")) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Nombre de usuario requerido</div>");
            return false;
        } else {  
            setMensaje1("");
        }
        
    
        if (this.getApellido1().equals("")) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Primer apellido requerido</div>");
            return false;
        } else {
            setMensaje1("");
        }
                    
                    
        if (this.getApellido2().equals("")) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Segundo apellido requerido</div>");
            return false;
        } else {
            setMensaje1("");                
        }
           
        
        if (this.getFechaNacimiento() == null) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Fecha de nacimiento requerida</div>");
            return false;
        } else {
            setMensaje1("");
        }
        
        
        if (listaDirec.isEmpty()) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Debe agregar al menos una dirección</div>");
            return false;
        } else {
            setMensaje1("");
        }
        
        
        if (this.getCorreo().equals("")) {
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Debe ingresar un Correo Electrónico</div>");
            return false;
        } else {
            setMensaje1("");
        }
        
        if (listaTel.isEmpty()) {  
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Debe ingresar al menos un Teléfono</div>");
            return false;
        } else {
            setMensaje1("");
        }
        
        
        if (this.getPrograma() == 0) {
           setMensaje1("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Ups!&nbsp;</strong>Debe seleccionar el Programa SDEAS a ingresar</div>");
           return false;
        } else {
           setMensaje1("");                       
        }
        
        
        return respuesta;
    }
   
    /*Valida campos de numeros*/
    public boolean validarNumero() {
        boolean respuesta = true;
        if (this.getId_TipoTelefono() == 0) {
            setMsjTelefono("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Debe seleccionar el Tipo de Teléfono</div>");
            return false;
        } else {
            setMsjTelefono("");
        }
        
        if (this.getNumeroTelefono().equals("")) {
            setMsjTelefono("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Ups!&nbsp;</strong>Debe ingresar el número de teléfono</div>");
            return false;
        } else {
            setMsjTelefono("");
        }
        
        return respuesta;
    }

    /*Valida campos de direcciones*/
    public boolean validarDirecciones() {
        boolean respuesta = true;
        if (this.getId_Provincia() == 0) {
            setMsjDireccion("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Debe seleccionar la Provincia</div>");
            return false;
        } else {
            setMsjDireccion("");
        }
        if (this.getId_Canton() == 0) {
            setMsjDireccion("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Debe seleccionar el Cantón</div>");
            return false;
        } else {
            setMsjDireccion("");
        }
        if (this.getId_Distrito() == 0) {
           setMsjDireccion("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Debe seleccionar el Distrito</div>");
            return false;
        } else {
            setMsjDireccion("");
        }
        if (this.getId_Barrio() == 0) {
            setMsjDireccion("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Debe seleccionar el Barrio</div>");
            return false;
        } else {
            setMsjDireccion("");
        }
        
        if (this.getOtrasSenas().equals("")) {
            setMsjDireccion("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Campo otras señas requerido</div>");
            return false;
        } else {
            setMsjDireccion("");
        }
            
        return respuesta;
    }
    
    /*Agrega direcciones a la lista*/
    public void agregarDireccones() throws SNMPExceptions, SQLException {
        DireccionDB dirDB = new DireccionDB();
        ProvinciaDB pro = new ProvinciaDB();
        CantonDB can = new CantonDB();
        DistritoDB dis = new DistritoDB();
        BarrioDB barr = new BarrioDB();
        
        boolean ind = false;
        if(validarDirecciones()){
            for (Direccion direccion : listaDirec) {
                if(direccion.getId_direccion().equals(Id_Direccion)){
                    direccion.setId_Edita(UsuarioMantenimiento.getId());
                    direccion.setFechaEdita(fecha);
                    direccion.setProvincia(pro.SeleccionarPorId(Id_Provincia));
                    direccion.setCanton(can.SeleccionarPorId(Id_Canton, Id_Provincia));
                    direccion.setDistrito(dis.SeleccionarPorId(Id_Distrito, Id_Canton, Id_Provincia));
                    direccion.setBarrio(barr.SeleccionarPorId(Id_Provincia, Id_Canton, Id_Distrito, id_Barrio));
                    direccion.setOtras_sennas(getOtrasSenas());
                    ind = true;
                }
            }  

            if(ind == false){
                Direccion dir = new Direccion();
                dir.setProvincia(pro.SeleccionarPorId(Id_Provincia));
                dir.setCanton(can.SeleccionarPorId(Id_Canton, Id_Provincia));
                dir.setDistrito(dis.SeleccionarPorId(Id_Distrito, Id_Canton, Id_Provincia));
                dir.setBarrio(barr.SeleccionarPorId(Id_Provincia, Id_Canton, Id_Distrito, id_Barrio));
                dir.setUsuario(UsuarioMantenimiento);
                dir.setOtras_sennas(OtrasSenas);
                dir.setId_Registra(UsuarioMantenimiento.getId());
                dir.setFechaRegistra(fecha);
                dir.setId_Edita(UsuarioMantenimiento.getId());
                dir.setFechaEdita(fecha);
                listaDirec.add(dir);
            }
            limpiarDireccion();
       }
    }

    /*Elimina Direcciones*/
    public void editarDirecciones(String id_Direc) throws SNMPExceptions, SQLException {
        for (Direccion d : listaDirec) {
            if(d.getId_direccion().equals(id_Direc)){
                    setId_Direccion(Integer.parseInt(d.getId_direccion()));
                    setId_Provincia(d.getProvincia().getId_Provincia());
                    setId_Canton(d.getCanton().getId_Canton());
                    setId_Distrito(d.getDistrito().getId_Distrito());
                    setId_Barrio(d.getBarrio().getId_Barrio());
                    setOtrasSenas(d.getOtras_sennas());
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
        TipoTelefonoDB tel = new TipoTelefonoDB();
        UsuarioManteDB usuarioDB = new UsuarioManteDB();
        TelefonoDB telDB = new TelefonoDB();
        boolean ind = false;
        if(validarNumero()){
            for (Telefono telefono : listaTel) {
                if(telefono.getId_Telefono().equals(getId_Telefono())){
                    telefono.setId_Edita(UsuarioMantenimiento.getId());
                    telefono.setFechaEdita(fecha);
                    telefono.setId_TipoTelefono(tel.SeleccionarPorId(getId_TipoTelefono()));
                    telefono.setNumero(getNumeroTelefono());
                    ind = true;
                }
            }
            
            if(ind == false){
                Telefono telefono = new Telefono();
                telefono.setNumero(getNumeroTelefono());
                telefono.setId_Usuario(UsuarioMantenimiento);
                telefono.setId_TipoTelefono(new TipoTelefonoDB().SeleccionarPorId(id_TipoTelefono));
                telefono.setId_Registra(UsuarioMantenimiento.getId());
                telefono.setFechaRegistra(fecha);
                telefono.setId_Edita(UsuarioMantenimiento.getId());
                telefono.setFechaEdita(fecha);
                listaTel.add(telefono);
            }
            
            limpiarTelefono();
        }
    }

    /*Limpia campos en telefono*/
    public void limpiarTelefono() {
        this.setNumeroTelefono("");
        this.setId_TipoTelefono(0);
    }

    /*Elimina telefono*/
    public void editarTelefonos(int id_Tel) throws SNMPExceptions, SQLException {
       for(Telefono telefono : listaTel) {
                if(telefono.getId_Telefono().equals(id_Tel)){
                    setId_Telefono(telefono.getId_Telefono());
                    setId_TipoTelefono(telefono.getId_TipoTelefono().getId_Telefono());
                    setNumeroTelefono(telefono.getNumero());
                }
         }
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

    public int getId_TipoTelefono() {
        return id_TipoTelefono;
    }

    public void setId_TipoTelefono(int id_TipoTelefono) {
        this.id_TipoTelefono = id_TipoTelefono;
    }

    public int getId_TipoCedula() {
        return id_TipoCedula;
    }

    public void setId_TipoCedula(int id_TipoCedula) {
        this.id_TipoCedula = id_TipoCedula;
    }

    

    public String getBotonNombre() {
        return botonNombre;
    }

    public void setBotonNombre(String botonNombre) {
        this.botonNombre = botonNombre;
    }

        public LinkedList<Provincia> getListaPro() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        return pro.SeleccionarTodos();
    }

    public void setListaPro(LinkedList<Provincia> listaPro) {
        this.listaPro = listaPro;
    }

    public LinkedList<Canton> getListaCan() throws SNMPExceptions, SQLException {
        return cargarCantonProv(null);
    }

    public void setListaCan(LinkedList<Canton> listaCan) {
        this.listaCan = listaCan;
    }

    public LinkedList<Distrito> getListaDis() throws SNMPExceptions, SQLException {
        return cargarDisCan(null);
    }

    public void setListaDis(LinkedList<Distrito> listaDis) {
        this.listaDis = listaDis;
    }

    public LinkedList<Barrio> getListaBarrio() throws SNMPExceptions, SQLException {
        return cargarBarDis(null);
    }

    public void setListaBarrio(LinkedList<Barrio> listaBarrio) {
        this.listaBarrio = listaBarrio;
    }
    
    public LinkedList<TipoTelefono> getListaTipoTelefono() {
        return listaTipoTelefono;
    }

    public void setListaTipoTelefono(LinkedList<TipoTelefono> listaTipoTelefono) {
        this.listaTipoTelefono = listaTipoTelefono;
    }

    public LinkedList<Programa> getListaPrograma() {
        return listaPrograma;
    }

    public void setListaPrograma(LinkedList<Programa> listaPrograma) {
        this.listaPrograma = listaPrograma;
    }

    public LinkedList<TipoIdentificacion> getListaIden() {
        return listaIden;
    }

    public void setListaIden(LinkedList<TipoIdentificacion> listaIden) {
        this.listaIden = listaIden;
    }

    public LinkedList<Direccion> getListaDirec() throws SNMPExceptions, SQLException {
        DireccionDB dire = new DireccionDB();
        return dire.SeleccionarPorUsuario(Integer.parseInt(this.getCedula()));
    }

    public void setListaDirec(LinkedList<Direccion> listaDirec) {
        this.listaDirec = listaDirec;
    }

    public LinkedList<Telefono> getListaTel() throws SNMPExceptions, SQLException {
        TelefonoDB telDB = new TelefonoDB();
        return telDB.SeleccionarTodos(Integer.parseInt(this.getCedula()));
    }

    public void setListaTel(LinkedList<Telefono> listaTel) {
        this.listaTel = listaTel;
    }

    public EnumFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(EnumFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public Usuario getUsuarioMantenimiento() {
        return UsuarioMantenimiento;
    }

    public void setUsuarioMantenimiento(Usuario UsuarioMantenimiento) {
        this.UsuarioMantenimiento = UsuarioMantenimiento;
    }

    public String getMsjDireccion() {
        return msjDireccion;
    }

    public void setMsjDireccion(String msjDireccion) {
        this.msjDireccion = msjDireccion;
    }

    public String getMsjTelefono() {
        return msjTelefono;
    }

    public void setMsjTelefono(String msjTelefono) {
        this.msjTelefono = msjTelefono;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_Direccion() {
        return Id_Direccion;
    }

    public void setId_Direccion(int Id_Direccion) {
        this.Id_Direccion = Id_Direccion;
    }

    public String getId_Telefono() {
        return Id_Telefono;
    }

    public void setId_Telefono(String Id_Telefono) {
        this.Id_Telefono = Id_Telefono;
    }
    
    
    
    
}
