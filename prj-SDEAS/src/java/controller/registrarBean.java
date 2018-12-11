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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import model.UsuarioMante;
import model.UsuarioManteDB;

/**
 *
 * @author Fabi
 */
@Named(value = "registrarBean")
@SessionScoped
public class registrarBean implements Serializable {

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
    String edad;
    private int Id_Provincia = 0;
    private int Id_Canton = 0;
    private int Id_Distrito = 0;
    private int id_Barrio = 0;
    private int id_TipoTelefono;
    private int id_TipoCedula;
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
    UsuarioMante usuario = null; 
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

    public boolean validaAutoRegistro() throws SNMPExceptions, SQLException {
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
            direc.setId_Registra(116390998);
            direc.setFechaRegistra(fecha);
            direc.setId_Edita(116390098);
            direc.setFechaEdita(fecha);
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
        setMsjDireccion("");
    }

    /*Agrega telefonos a la lista */
    public void agregarTelefonos() throws SNMPExceptions, SQLException {

        TipoTelefonoDB telefo = new TipoTelefonoDB();
        if (validarNumero()) {
            Telefono tel = new Telefono();
            tel.setId_TipoTelefono(telefo.SeleccionarPorId(this.getId_TipoTelefono()));
            tel.setNumero(this.getNumeroTelefono());
            tel.setId_Registra(116390998);
            tel.setFechaRegistra(fecha);
            tel.setId_Edita(116390098);
            tel.setFechaEdita(fecha);
            listaTel.add(tel);
            limpiarTelefono();
        }
    }

    /*Limpia campos en telefono*/
    public void limpiarTelefono() {
        this.setNumeroTelefono("");
        this.setId_TipoTelefono(0);
        setMsjTelefono("");
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
        try{
        TipoIdentificacionDB tipoidenDB = new TipoIdentificacionDB();
        ProgramaDB prograDB = new ProgramaDB();
        RolUsuarioDB rolDB = new RolUsuarioDB();
        UsuarioDB usuDB = new UsuarioDB();
        DireccionDB direcDB = new DireccionDB();
        TelefonoDB telDB = new TelefonoDB();
        ProgramaUsuarioDB programaUsuarioDB = new ProgramaUsuarioDB();
        Usuario u = null;
        u = usuDB.UsuarioExistente(Integer.parseInt(getCedula()));
        if (validaAutoRegistro()) {
            if(u!=null){
                setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Ya existe un usuario registrado con el número de cédula proporcionado</div>");
                return;
            }else{
            setMensaje1("");
            Usuario usu = new Usuario();
            usu.setTipoIdentificacion(tipoidenDB.SeleccionarPorId(this.getId_TipoCedula()));
            usu.setId(Integer.parseInt(this.getCedula()));
            usu.setNombre(this.getNombre());
            usu.setApellido1(this.getApellido1());
            usu.setApellido2(this.getApellido2());
            usu.setFechaNacimiento(this.getFechaNacimiento());
            usu.setId_Registra(Integer.parseInt(getCedula()));
            usu.setFechaRegistra(fecha);
            usu.setId_Edita(Integer.parseInt(getCedula()));
            usu.setFechaEdita(fecha);
            usu.setCorreo(this.getCorreo());
            usu.setPrimeraVez(1);
            usuDB.registrar(usu);

            /*agregar telefono*/
            for (Telefono tel : listaTel) {
                tel.setId_Usuario(usu);
                tel.setId_Registra(Integer.parseInt(getCedula()));
                tel.setFechaRegistra(fecha);
                tel.setId_Edita(Integer.parseInt(getCedula()));
                tel.setFechaEdita(fecha);
                telDB.registrar(tel);
            }
            /*agregar direcciones*/
            for (Direccion dir : listaDirec) {
                dir.setId_Registra(Integer.parseInt(getCedula()));
                dir.setFechaRegistra(fecha);
                dir.setId_Edita(Integer.parseInt(getCedula()));
                dir.setFechaEdita(fecha);
                dir.setUsuario(usu);
                direcDB.registrar(dir);
            }

            Programa progra = new Programa();
            progra = prograDB.SeleccionarPorId(this.getPrograma());
            RolUsuario rol1 = rolDB.SeleccionarPorId(3);

            /*Agrega programa_usuario*/
            ProgramaUsuario prousu = new ProgramaUsuario();
            prousu.setPrograma(progra);
            prousu.setUsuario(usu);
            prousu.setRolUsuario(rol1);
            prousu.setFuncionario(getFuncionario());
            prousu.setId_Registra(Integer.parseInt(getCedula()));
            prousu.setFechaEdita(fecha);
            prousu.setId_Edita(Integer.parseInt(getCedula()));
            prousu.setFechaRegistra(fecha);
            prousu.setEstado("Activo");
            programaUsuarioDB.registrar(prousu);
            setMensaje2("<div class='alert alert-success alert-dismissible fade in' > <strong>Exitoso&nbsp;</strong>¡Estas un paso más cerca de ser parte de SDEAS!\n  Su AutoRegistro ha sido enviado al Coordinador del Programa seleccionado. \n  Se le enviará el estado de su solicitud al correo proporcionado. </div>" );
        }
        }
    }catch(Exception e){
            setMensaje1("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, hubo un error al registrar el usuario. Por favor, intentelo de nuevo</div>" );
        }
    }
    
    

    public void limpiar(){
        setTipoIden(null);
        setCedula("");
        setNombre("");
        setApellido1("");
        setApellido2("");
        setFechaNacimiento(null);
        setId_Provincia(0);
        setId_Canton(0);
        setId_Distrito(0);
        setId_Barrio(0);
        setOtrasSenas("");
        listaDirec.clear();
        setCorreo("");
        setId_TipoTelefono(1);
        setNumeroTelefono("");
        listaTel.clear();
        setPrograma(0);
        setFuncionario(EnumFuncionario.Docente);
        setMensaje1("");
        setMensaje2("");
        setMsjDireccion("");
        setMsjTelefono("");
        setId_TipoCedula(1);
        
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

    public TipoIdentificacion getTipoIden() {
        return TipoIden;
    }

    public void setTipoIden(TipoIdentificacion TipoIden) {
        this.TipoIden = TipoIden;
    }

    public Date getFechaNacimiento() {
        getEdad();
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
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

  

    public UsuarioMante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMante usuario) {
        this.usuario = usuario;
    }
    
    
}
