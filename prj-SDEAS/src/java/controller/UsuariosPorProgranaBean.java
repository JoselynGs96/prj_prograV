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
import model.EstadoAcceso;
import model.EstadoAccesoDB;
import model.ProgramaDB;
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.Provincia;
import model.ProvinciaDB;
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
import org.primefaces.PrimeFaces;

/**
 *
 * @author ujose
 */
@Named(value = "usuariosPorProgranaBean")
@SessionScoped
public class UsuariosPorProgranaBean implements Serializable {
    Date fecha = new Date();
    String buscarFiltro = "";
    UsuarioMante usuario = null;
    ProgramaUsuario programaUsuario = null;
    LinkedList<Direccion> listaDirecciones = new LinkedList<Direccion>();
    LinkedList<Telefono> listaTelefonos = new LinkedList<Telefono>();
    LinkedList<ProgramaUsuario> listaProusu = new LinkedList<ProgramaUsuario>();
    LinkedList<UsuarioMante> listaUsuario = new LinkedList<UsuarioMante>();
    LinkedList<EstadoAcceso> listaEstadoAcceso = new LinkedList<EstadoAcceso>();
    LinkedList<Provincia> listaPro = new LinkedList<Provincia>();
    LinkedList<Canton> listaCan = new LinkedList<Canton>();
    LinkedList<Distrito> listaDis = new LinkedList<Distrito>();
    LinkedList<Barrio> listaBarrio = new LinkedList<Barrio>();
   
    /*Para el modal*/
    UsuarioMante usuarioModal = null;
    LinkedList<ProgramaUsuario> listaProusuModal = new LinkedList<ProgramaUsuario>();
    String telefonos = "";
    String direcciones = "";
    String programas = "";
 
    
    /*Para validaciones*/
    String mensajeGuardar = "";
    String mensajeFiltro = "";
    String mensajeError = "";
    
    
    String Id = "";
    String TipoIdentificacion = "";
    String Nombre = "";
    String Apellido1 = "";
    String Apellido2 = "";
    Date FechaNacimiento = null;
    String Correo = "";
    int estadoAcceso = 0;
    String estadoUsuario = "";
    
    int idDireccion;
    int Id_Provincia;
    int Id_Canton;
    int Id_Distrito;
    int id_Barrio;
    String otrasSennas;
    
    LinkedList<TipoTelefono> listaTipoTelefonos = new LinkedList<>();
    String idTelefono;
    int idTipoTelefono;
    int numero;
    
    public UsuariosPorProgranaBean() throws SNMPExceptions, SQLException{
        seleccionarTodos();
    }
    
    public void nuevoBoton(){
        usuario = null;
        setId("");
        setTipoIdentificacion("");
        setNombre("");
        setApellido1("");
        setApellido2("");
        FechaNacimiento = null;
        idDireccion = 0;
        Id_Provincia = 0;
        Id_Canton = 0;
        Id_Distrito = 0;
        id_Barrio = 0;
        otrasSennas = "";
        idTelefono = "";
        idTipoTelefono = 0;
        numero = 0;
        Correo = "";
        
        if(!listaTipoTelefonos.isEmpty()){
            listaTipoTelefonos.clear();
        }
        if(!listaDirecciones.isEmpty()){
            listaDirecciones.clear();
        }
        if(!listaTelefonos.isEmpty()){
           listaTelefonos.clear();
        }
        if(!listaTipoTelefonos.isEmpty()){
            listaDirecciones.clear();
        }
        if(!listaProusuModal.isEmpty()){
            listaProusuModal.clear();
        }
        if(!listaProusu.isEmpty()){
            listaProusu.clear();
        }
        
        setMensajeError("");
        setMensajeFiltro("");
        setMensajeGuardar("");
        setEstadoAcceso(0);
        setEstadoUsuario("");
        setBuscarFiltro("");
        setTelefonos("");
        setDirecciones("");
        setProgramas("");
        usuarioModal = null;
    }
    
    public void editarBoton(int id) throws SNMPExceptions, SQLException{
        listaTipoTelefonos = new TipoTelefonoDB().SeleccionarTodos();
        llenarCombosDireccion();
        try{
            UsuarioManteDB udb = new UsuarioManteDB();
            usuario = udb.SeleccionarPorId(id);
            setTipoIdentificacion(usuario.getTipoIdentificacion().getDsc_TipoTelefono());
            setId(usuario.getId()+"");
            setNombre(usuario.getNombre());
            setApellido1(usuario.getApellido1());
            setApellido2(usuario.getApellido2());
            setFechaNacimiento(usuario.getFechaNacimiento());
            setEstadoAcceso(usuario.getEstadoAcceso().getId());
            setEstadoUsuario(usuario.getLog_Activo());
            
            setCorreo(usuario.getCorreo());
            
            TelefonoDB tt = new TelefonoDB();
            listaTelefonos = tt.SeleccionarTodos(usuario.getId());
            
            DireccionDB dd = new DireccionDB();
            listaDirecciones = dd.SeleccionarPorUsuario(usuario.getId());
            
            ProgramaUsuarioDB pdb = new ProgramaUsuarioDB();
            listaProusu = pdb.SeleccionarPorId(id);
            
            
       }catch(Exception e){
           setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el usuario...¡Intentelo de nuevo!</div>");
       }
        
    }
    
    public void editarDireccion(String id) throws SNMPExceptions, SQLException{
        DireccionDB ddb = new DireccionDB();
        Direccion dir = new Direccion();
        dir = ddb.SeleccionarPorId(Integer.getInteger(id));
        setIdDireccion(Integer.getInteger(dir.getId_direccion()));
        setId_Provincia(dir.getProvincia().getId_Provincia());
        setId_Canton(dir.getCanton().getId_Canton());
        setId_Distrito(dir.getDistrito().getId_Distrito());
        setId_Barrio(dir.getBarrio().getId_Barrio());
        setOtrasSennas(dir.getOtras_sennas());
    }
    
    public void limpiarCamposDireccion(){
        setIdDireccion(0);
        setId_Provincia(0);
        setOtrasSennas("");
    }
    
    public void guardarDireccion() throws SNMPExceptions, SQLException{
       if(ValidaDireccion()){
        for (Direccion direccion : listaDirecciones) {
            if(direccion.getId_direccion().equals(idDireccion)){
                direccion.setId_Edita(116390998);
                direccion.setFechaEdita(fecha);
                direccion.setProvincia(new ProvinciaDB().SeleccionarPorId(Id_Provincia));
                direccion.setCanton(new CantonDB().SeleccionarPorId(Id_Canton, Id_Provincia));
                direccion.setDistrito(new DistritoDB().SeleccionarPorId(Id_Distrito, Id_Canton, Id_Provincia));
                direccion.setBarrio(new BarrioDB().SeleccionarPorId(Id_Provincia, Id_Canton, Id_Distrito, id_Barrio));
                direccion.setOtras_sennas(otrasSennas);
            }else{
                listaDirecciones.add(new Direccion(new ProvinciaDB().SeleccionarPorId(Id_Provincia), new CantonDB().SeleccionarPorId(Id_Canton, Id_Provincia),new DistritoDB().SeleccionarPorId(Id_Distrito, Id_Canton, Id_Provincia), new BarrioDB().SeleccionarPorId(Id_Provincia, Id_Canton, Id_Distrito, id_Barrio), new UsuarioDB().SeleccionarPorId(Integer.getInteger(Id)) , otrasSennas, 116390998, fecha, 116390998, fecha));
            }
        }  
       }
        limpiarCamposDireccion();
    }
    
    public boolean ValidaDireccion(){
        boolean indicador = true;
        
        if(Id.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
        
         if(Id_Provincia==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar la Provincia</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Id_Canton==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el Cantón</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Id_Distrito==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el Distrito</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(id_Barrio==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el Barrio</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(otrasSennas.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe llenar el campo Otras señas</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         return indicador;
    }
    
    public void editarTelefono(String id) throws SNMPExceptions, SQLException{
        Telefono tel = new TelefonoDB().SeleccionarPorId(Integer.getInteger(id));
        setIdTelefono(tel.getId_Telefono());
        setIdTipoTelefono(tel.getId_TipoTelefono().getId_Telefono());
        setNumero(Integer.getInteger(tel.getNumero()));
    }
    
    public boolean ValidarTelefono(){
       boolean indicador;
       
       if(Id.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
       
        if(idTipoTelefono == 1){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el Tipo de Teléfono</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
        if(numero == 0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe llenar el campo Numero</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
        return indicador;
    }
    
    public void limpiarCamposTelefono(){
        setIdTelefono("");
        setIdTipoTelefono(0);
        setNumero(0);
    }
    
    public void guardarTelefono() throws SNMPExceptions, SQLException{
        if(ValidarTelefono()){
        for (Telefono telefono : listaTelefonos) {
            if(telefono.getId_Telefono().equals(idTelefono)){
                telefono.setId_Edita(116390998);
                telefono.setFechaEdita(fecha);
                telefono.setId_TipoTelefono(new TipoTelefonoDB().SeleccionarPorId(idTipoTelefono));
                telefono.setNumero(numero+"");
            }else{
                listaTelefonos.add(new Telefono(numero+"",new UsuarioDB().SeleccionarPorId(Integer.getInteger(Id)) , new TipoTelefonoDB().SeleccionarPorId(idTipoTelefono), 116390998, fecha, 116390998, fecha));
            }
        }  
        }
        limpiarCamposTelefono();
    }
   
    public void verMas(int i) throws SNMPExceptions, SQLException{
        usuarioModal = new UsuarioManteDB().SeleccionarPorId(i);
        
        DireccionDB dd = new DireccionDB();
        listaDirecciones = dd.SeleccionarPorUsuario(i);
         for (int j = 0; j < listaDirecciones.size(); j++) {
             Direccion dir = listaDirecciones.get(j);
             direcciones += "Dirección #"+j+1+": "+ dir.getOtras_sennas() +" ("+dir.getProvincia()+", "+dir.getCanton()+", "+dir.getDistrito()+","+dir.getBarrio()+") \n";
         }
        
        TelefonoDB tt = new TelefonoDB();
        listaTelefonos = tt.SeleccionarTodos(i);
        for (int j = 0; j < listaTelefonos.size(); j++) {
             Telefono tel = listaTelefonos.get(j);
             telefonos += tel.getId_TipoTelefono().getDsc_TipoTelefono()+": "+ tel.getNumero()+" \n";
         }
        
        ProgramaUsuarioDB pp = new ProgramaUsuarioDB();
        listaProusuModal = pp.SeleccionarPorId(i);
         for (int j = 0; j < listaProusuModal.size(); j++) {
             ProgramaUsuario ps = listaProusuModal.get(j);
             programas +=  ps.getPrograma().getNombre() +", "+ps.getRolUsuario().getDsc_RolUsuario() +", "+ps.getEstado()+"\n";
         }
        
        
        PrimeFaces.current().executeScript("abrirModal();");
     }
   
    public void buscar() throws SNMPExceptions, SQLException{
        UsuarioManteDB udb = new UsuarioManteDB();
        try{
        if(!getBuscarFiltro().equals("")){
            if(!udb.FiltrarUsuario(buscarFiltro).isEmpty()){
                listaUsuario = udb.FiltrarUsuario(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaUsuario = udb.SeleccionarTodos();
                setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
            }
        }else{
            seleccionarTodos();
            setMensajeFiltro("");
        }
        }catch(Exception e){
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el usuario...¡Intentelo de nuevo!</div>");
        }
     }
    
    public void cambiarEstado(int idPro, int idT) throws SNMPExceptions, SQLException{
        ProgramaUsuarioDB pdb = new ProgramaUsuarioDB();
        for (int i = 0; i < listaProusu.size(); i++) {
            
            ProgramaUsuario pro = listaProusu.get(i);
            
            if(pro.getPrograma().getId() == idPro && pro.getRolUsuario().getId_RolUsuario() == idT){
                if(pro.getEstado().equals("Activo")){
                    pro.setEstado("Inactivo");
                }else{
                    pro.setEstado("Activo");
                }
                pro.setId_Edita(116390998);
                pro.setFechaEdita(fecha);
            }
        }
    }
    
    public void cambiarTipoFuncionario(int idPro, int idT) throws SNMPExceptions, SQLException{
        ProgramaUsuarioDB pdb = new ProgramaUsuarioDB();
        for (int i = 0; i < listaProusu.size(); i++) {
            
            ProgramaUsuario pro = listaProusu.get(i);
            if(pro.getPrograma().getId() == idPro && pro.getRolUsuario().getId_RolUsuario() == idT){
                //cambiar
            }
        }
    }
    
    public void guardarBoton(){
        UsuarioManteDB us = new UsuarioManteDB();
        ProgramaUsuarioDB proU = new ProgramaUsuarioDB();
        DireccionDB dirDB = new DireccionDB();
        TelefonoDB telDB = new TelefonoDB();
        try{
            if(Validaciones()){
                usuario.setNombre(Nombre);
                usuario.setApellido1(Apellido1);
                usuario.setApellido2(Apellido2);
                usuario.setFechaNacimiento(FechaNacimiento);
                usuario.setCorreo(Correo);
                usuario.setLog_Activo(this.estadoUsuario);
                usuario.setEstadoAcceso(new EstadoAccesoDB().SeleccionarPorId(this.estadoAcceso));
                usuario.setId_Edita(116390998);
                usuario.setFechaEdita(fecha);
                us.actulizar(usuario);
                
                for (int i = 0; i < listaDirecciones.size(); i++) {
                    if(listaDirecciones.get(i).getId_direccion().equals("")){
                        dirDB.registrar(listaDirecciones.get(i));
                    }else{
                        dirDB.actulizar(listaDirecciones.get(i));
                    }
                    
                }
                
                for (int i = 0; i < listaTelefonos.size(); i++) {
                    if(listaTelefonos.get(i).getId_Telefono().equals("")){
                        telDB.registrar(listaTelefonos.get(i));
                    }else{
                        telDB.actulizar(listaTelefonos.get(i));
                    }
                    
                }
                
                for (int i = 0; i < listaProusu.size(); i++) {
                    proU.actulizar(listaProusu.get(i));
                }
                setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Usuario actualizado con éxito!</div> ");
            }
            seleccionarTodos();
        }catch(Exception e){
            setMensajeError( "<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al guardar el usuario...¡Intentelo de nuevo!</div>");
        }
    }
    
    public boolean Validaciones(){
        boolean indicador = true;
         
         if(Id.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Nombre.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Nombre del usuario requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Apellido1.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Primer apellido requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Apellido2.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Segundo apellido requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(FechaNacimiento == null){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Fecha de Nacimiento requerida</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Correo.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Correo requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         return indicador;
    }
    
    
    
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
       UsuarioManteDB u = new UsuarioManteDB();
        if(!u.SeleccionarTodos().isEmpty()){
            listaUsuario.clear();
            listaUsuario = u.SeleccionarTodos();
        }
    }
    
    public void llenarCombosDireccion() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        CantonDB can = new CantonDB();
        DistritoDB dis = new DistritoDB();
        BarrioDB barr = new BarrioDB();

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
       
    }

    
    public UsuarioMante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMante usuario) {
        this.usuario = usuario;
    }

    public ProgramaUsuario getProgramaUsuario() {
        return programaUsuario;
    }

    public void setProgramaUsuario(ProgramaUsuario programaUsuario) {
        this.programaUsuario = programaUsuario;
    }

    public LinkedList<Direccion> getListaDirecciones() {
        return listaDirecciones;
    }

    public void setListaDirecciones(LinkedList<Direccion> listaDirecciones) {
        this.listaDirecciones = listaDirecciones;
    }

    public LinkedList<Telefono> getListaTelefonos() {
        return listaTelefonos;
    }

    public void setListaTelefonos(LinkedList<Telefono> listaTelefonos) {
        this.listaTelefonos = listaTelefonos;
    }

    public LinkedList<UsuarioMante> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(LinkedList<UsuarioMante> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    
    public int getEstadoAcceso() {
        return estadoAcceso;
    }

    public void setEstadoAcceso(int estadoAcceso) {
        this.estadoAcceso = estadoAcceso;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public LinkedList<ProgramaUsuario> getListaProusu() {
        return listaProusu;
    }

    public void setListaProusu(LinkedList<ProgramaUsuario> listaProusu) {
        this.listaProusu = listaProusu;
    }

    public String getMensajeGuardar() {
        return mensajeGuardar;
    }

    public void setMensajeGuardar(String mensajeGuardar) {
        this.mensajeGuardar = mensajeGuardar;
    }

    public String getMensajeFiltro() {
        return mensajeFiltro;
    }

    public void setMensajeFiltro(String mensajeFiltro) {
        this.mensajeFiltro = mensajeFiltro;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getBuscarFiltro() {
        return buscarFiltro;
    }

    public void setBuscarFiltro(String buscarFiltro) {
        this.buscarFiltro = buscarFiltro;
    }

    public LinkedList<EstadoAcceso> getListaEstadoAcceso() throws SNMPExceptions, SQLException {
        EstadoAccesoDB db = new EstadoAccesoDB();
        return db.SeleccionarTodos();
    }

    public void setListaEstadoAcceso(LinkedList<EstadoAcceso> listaEstadoAcceso) {
        this.listaEstadoAcceso = listaEstadoAcceso;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(String direcciones) {
        this.direcciones = direcciones;
    }


    public String getProgramas() {
        return programas;
    }

    public void setProgramas(String programas) {
        this.programas = programas;
    }

    public UsuarioMante getUsuarioModal() {
        return usuarioModal;
    }

    public void setUsuarioModal(UsuarioMante usuarioModal) {
        this.usuarioModal = usuarioModal;
    }

    public LinkedList<ProgramaUsuario> getListaProusuModal() {
        return listaProusuModal;
    }

    public void setListaProusuModal(LinkedList<ProgramaUsuario> listaProusuModal) {
        this.listaProusuModal = listaProusuModal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTipoIdentificacion() {
        return TipoIdentificacion;
    }

    public void setTipoIdentificacion(String TipoIdentificacion) {
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

    public LinkedList<Provincia> getListaPro() throws SNMPExceptions, SQLException {
        ProvinciaDB pro = new ProvinciaDB();
        getListaCan();
        return pro.SeleccionarTodos();
    }

    public void setListaPro(LinkedList<Provincia> listaPro) {
        this.listaPro = listaPro;
    }

    public LinkedList<Canton> getListaCan() throws SNMPExceptions, SQLException {
        CantonDB can = new CantonDB();
        getListaDis();
        return can.SeleccionarTodos(this.getId_Provincia());
    }

    public void setListaCan(LinkedList<Canton> listaCan) {
        this.listaCan = listaCan;
    }

    public LinkedList<Distrito> getListaDis() throws SNMPExceptions, SQLException {
        DistritoDB dis = new DistritoDB();
        getListaBarrio();
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

    public String getOtrasSennas() {
        return otrasSennas;
    }

    public void setOtrasSennas(String otrasSennas) {
        this.otrasSennas = otrasSennas;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
   
    public LinkedList<TipoTelefono> getListaTipoTelefonos() {
        return listaTipoTelefonos;
    }

    public void setListaTipoTelefonos(LinkedList<TipoTelefono> listaTipoTelefonos) {
        this.listaTipoTelefonos = listaTipoTelefonos;
    }

    public String getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(String idTelefono) {
        this.idTelefono = idTelefono;
    }

    public int getIdTipoTelefono() {
        return idTipoTelefono;
    }

    public void setIdTipoTelefono(int idTipoTelefono) {
        this.idTipoTelefono = idTipoTelefono;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    
    
    

}
