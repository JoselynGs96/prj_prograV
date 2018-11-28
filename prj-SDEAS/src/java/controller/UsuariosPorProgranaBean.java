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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import model.Direccion;
import model.DireccionDB;
import model.EstadoAcceso;
import model.EstadoAccesoDB;
import model.Programa;
import model.ProgramaDB;
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.RolUsuario;
import model.RolUsuarioDB;
import model.Telefono;
import model.TelefonoDB;
import model.Usuario;
import model.UsuarioDB;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ujose
 */
@Named(value = "usuariosPorProgranaBean")
@SessionScoped
public class UsuariosPorProgranaBean implements Serializable {

    Usuario usuario = null;
    ProgramaUsuario programaUsu = null;
    String cedula = "";
    String nombre = "";
    String estadoPrograma = "";
    int estadoAcceso;
    String estadoUsuario = "";
    String buscarFiltro = "";
    String nombrePrograma = "";
    String mensajeGuardar;
    String mensajeFiltro;
    String mensajeError;
    String telefonos;
    String direcciones;
    String edad;
    String programas;
    LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
    LinkedList<ProgramaUsuario> listaProusu = new LinkedList<ProgramaUsuario>();
    LinkedList<EstadoAcceso> listaEstadoAcceso = new LinkedList<EstadoAcceso>();
    
    
    
    public UsuariosPorProgranaBean() throws SNMPExceptions, SQLException{
        seleccionarTodos();
    }
    

    /*Botón ver más*/
     public void verMas(int i) throws SNMPExceptions, SQLException{
        usuario = new UsuarioDB().SeleccionarPorId(i+"");
        
        DireccionDB DD = new DireccionDB();
        LinkedList<Direccion> d = DD.SeleccionarPorUsuario(i+"");
         for (int j = 0; j < d.size(); j++) {
             Direccion dir = d.get(j);
             direcciones +=  dir.getOtras_sennas() +" ("+dir.getId_Provincia()+", "+dir.getId_Canton()+", "+dir.getId_Distrito()+","+dir.getId_Barrio()+") \n";
             
         }
        
        TelefonoDB tt = new TelefonoDB();
        
        ProgramaUsuarioDB pp = new ProgramaUsuarioDB();
        LinkedList<ProgramaUsuario> p = pp.SeleccionarPorId(i);
         for (int j = 0; j < p.size(); j++) {
             ProgramaUsuario ps = p.get(j);
             programas +=  ps.getPrograma().getNombre() +", "+ps.getRolUsuario().getDsc_RolUsuario() +", "+ps.getEstado()+"\n";
         }
        
         edad = calculaEdad();
        
        PrimeFaces.current().executeScript("abrirModal();");
     }
    
    public String calculaEdad(){
        String f = usuario.getFechaNacimiento().toString();
       DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNac = LocalDate.parse(f, fmt);
            LocalDate ahora = LocalDate.now();

            Period periodo = Period.between(fechaNac, ahora);
            return  periodo.getYears() + " años , " + periodo.getMonths() + " meses con " + periodo.getDays() +" días";
    }
     
     
    /*Botón de buscar*/
     public void buscar() throws SNMPExceptions, SQLException{
        UsuarioDB recd = new UsuarioDB();
        try{
        if(!getBuscarFiltro().equals("")){
            if(!recd.FiltrarUsuario(buscarFiltro).isEmpty()){
                listaUsuario = recd.FiltrarUsuario(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaUsuario = recd.SeleccionarTodos2();
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
        programaUsu = null;
        setMensajeError("");
        setMensajeFiltro("");
        setMensajeGuardar("");
        listaProusu.clear();
        setEstadoAcceso(0);
        setEstadoUsuario("");
    }
    
    public void guardarBoton(){
        UsuarioDB us = new UsuarioDB();
        ProgramaUsuarioDB proU = new ProgramaUsuarioDB();
        
        try{
            if(Validaciones()){
                usuario.setEst(this.estadoUsuario);
                usuario.setEstAcc(new EstadoAccesoDB().SeleccionarPorId(estadoAcceso));
               us.actulizar(usuario, listaProusu);
                setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Usuario actualizado con éxito!</div> ");
            }
            seleccionarTodos();
        }catch(Exception e){
            setMensajeError( "<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al guardar el usuario...¡Intentelo de nuevo!</div>");
        }
    }
    
    public void editarBoton(String id) throws SNMPExceptions, SQLException{
        UsuarioDB udb = new UsuarioDB();
        usuario = udb.SeleccionarPorId(id);
        setEstadoAcceso(usuario.getEstAcc().getId());
        setEstadoUsuario(usuario.getEst());
        ProgramaUsuarioDB pdb = new ProgramaUsuarioDB();
        listaProusu = pdb.SeleccionarPorId(Integer.parseInt(id));
    }
    
    public boolean Validaciones(){
        boolean indicador = true;
         
         if(getNombre().equals("")|| getCedula().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         return indicador;
    }
    
    
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
       UsuarioDB u = new UsuarioDB();
        if(!u.SeleccionarTodos2().isEmpty()){
            listaUsuario.clear();
            listaUsuario = u.SeleccionarTodos2();
        }
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ProgramaUsuario getProgramaUsu() {
        return programaUsu;
    }

    public void setProgramaUsu(ProgramaUsuario programaUsu) {
        this.programaUsu = programaUsu;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstadoPrograma() {
        return estadoPrograma;
    }

    public void setEstadoPrograma(String estadoPrograma) {
        this.estadoPrograma = estadoPrograma;
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

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    

    public LinkedList<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(LinkedList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getProgramas() {
        return programas;
    }

    public void setProgramas(String programas) {
        this.programas = programas;
    }
 
    
  
}
