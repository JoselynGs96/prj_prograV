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
import java.util.LinkedList;
import model.Direccion;
import model.DireccionDB;
import model.EstadoAcceso;
import model.EstadoAccesoDB;
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.Telefono;
import model.TelefonoDB;
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

    UsuarioMante usuario = null;
    ProgramaUsuario programaUsuario = null;
    LinkedList<Direccion> listaDirecciones = new LinkedList<Direccion>();
    LinkedList<Telefono> listaTelefonos = new LinkedList<Telefono>();
    LinkedList<ProgramaUsuario> listaProusu = new LinkedList<ProgramaUsuario>();
    LinkedList<UsuarioMante> listaUsuario = new LinkedList<UsuarioMante>();
    LinkedList<EstadoAcceso> listaEstadoAcceso = new LinkedList<EstadoAcceso>();
    
    UsuarioMante usuarioModal = null;
    LinkedList<ProgramaUsuario> listaProusuModal = new LinkedList<ProgramaUsuario>();
    
    int estadoAcceso;
    String estadoUsuario = "";
    String buscarFiltro = "";
    String mensajeGuardar = "";
    String mensajeFiltro = "";
    String mensajeError = "";
    String telefonos = "";
    String direcciones = "";
    String programas = "";
    
    
    
    
    public UsuariosPorProgranaBean() throws SNMPExceptions, SQLException{
        seleccionarTodos();
    }
    
   
    public void verMas(int i) throws SNMPExceptions, SQLException{
        usuarioModal = new UsuarioManteDB().SeleccionarPorId(i);
        
        DireccionDB dd = new DireccionDB();
        listaDirecciones = dd.SeleccionarPorUsuario(i+"");
         for (int j = 0; j < listaDirecciones.size(); j++) {
             Direccion dir = listaDirecciones.get(j);
             direcciones += "Dirección #"+j+1+": "+ dir.getOtras_sennas() +" ("+dir.getId_Provincia()+", "+dir.getId_Canton()+", "+dir.getId_Distrito()+","+dir.getId_Barrio()+") \n";
         }
        
        TelefonoDB tt = new TelefonoDB();
        listaTelefonos = tt.SeleccionarTodos(i +"");
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
     
    
    public void nuevoBoton(){
        usuario = null;
        listaDirecciones.clear();
        listaProusu.clear();
        listaTelefonos.clear();
        setMensajeError("");
        setMensajeFiltro("");
        setMensajeGuardar("");
        listaProusu.clear();
        setEstadoAcceso(0);
        setEstadoUsuario("");
        setBuscarFiltro("");
        setTelefonos("");
        setDirecciones("");
        setProgramas("");
        usuarioModal = null;
        listaProusuModal.clear();
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
            }
        }
    }
    
    public void guardarBoton(){
        UsuarioManteDB us = new UsuarioManteDB();
        ProgramaUsuarioDB proU = new ProgramaUsuarioDB();
        
        try{
            if(Validaciones()){
                usuario.setLog_Activo(this.estadoUsuario);
                usuario.setEstadoAcceso(new EstadoAccesoDB().SeleccionarPorId(this.estadoAcceso));
                us.actulizar(usuario);
                
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
    
    
    public void editarBoton(int id) throws SNMPExceptions, SQLException{
       try{
            UsuarioManteDB udb = new UsuarioManteDB();
            usuario = udb.SeleccionarPorId(id);
            setEstadoAcceso(usuario.getEstadoAcceso().getId());
            setEstadoUsuario(usuario.getLog_Activo());

            ProgramaUsuarioDB pdb = new ProgramaUsuarioDB();
            listaProusu = pdb.SeleccionarPorId(id);
       }catch(Exception e){
           setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el usuario...¡Intentelo de nuevo!</div>");
       }
        
    }
    
    
    public boolean Validaciones(){
        boolean indicador = true;
         
         if(usuario == null){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
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

}
