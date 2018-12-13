/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ObtenerDatosSesion;
import dao.SNMPExceptions;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Programa;
import model.ProgramaDB;
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.RolUsuario;
import model.RolUsuarioDB;
import model.UsuarioDB;
import model.UsuarioMante;
import model.UsuarioManteDB;

/**
 *
 * @author ujose
 */
@Named(value = "programaBean")
@SessionScoped
public class ProgramaBean implements Serializable {
    int id;
    String nombre;
    String descripcion;
    String estado = "Activo";
    LinkedList<Programa> listaTablaPrograma = new LinkedList<Programa>();
    String buscarFiltro = "";
    String mensajeGuardar = "";
    String mensajeError = "";
    String mensajeFiltro = "";
    Date fecha = new Date();
    UsuarioMante usuario = null;
    int IdRegistra = 0;
    int IdEdita = 0;
    
    /**
     * Creates a new instance of ProgramaBean
     */
    public ProgramaBean() throws SNMPExceptions, SQLException {
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
       
        UsuarioManteDB usuDB = new UsuarioManteDB();
        datos.consultarSesion();
        if(!datos.getId_Usuario().equals("")){
            usuario = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
            IdRegistra = usuario.getId();
            IdEdita = usuario.getId();
        }else{
            usuario = usuDB.SeleccionarPorId(116390998);
            IdRegistra = usuario.getId();
            IdEdita = usuario.getId();
        }
        
        seleccionarTodos();
    }
    
    
    /*Selecciona todos los programas*/
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        ProgramaUsuarioDB p = new ProgramaUsuarioDB();
        if(!p.SeleccionarTodosPorId(usuario.getId()).isEmpty()){
            listaTablaPrograma.clear();
            listaTablaPrograma = p.SeleccionarTodosPorId(usuario.getId());
        }
    }
    
    /*Botón guardar; inserta uno nuevo o modifica*/
     public void insertarPrograma() throws SNMPExceptions, SQLException{
        try{
            if(Validaciones() == true){
                Programa pro = new Programa();
                pro.setId(id);
                pro.setNombre(nombre);
                pro.setDescripcion(descripcion);
                pro.setEstado(estado);
                pro.setId_Edita(IdEdita);
                pro.setFechaEdita(fecha);
                
                ProgramaDB prog = new ProgramaDB();
                if(getId() != 0){
                     prog.actulizar(pro);
                     setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Programa actualizado con éxito!</div> ");

                }else{
                     pro.setId_Registra(IdRegistra);
                     pro.setFechaRegistra(fecha);
                     prog.registrar(pro);
                     
                     pro.setId(prog.ultimoRegistro());
                     ProgramaUsuarioDB prodb = new ProgramaUsuarioDB();
                     ProgramaUsuario pr = new ProgramaUsuario();
                     pr.setPrograma(pro);
                     pr.setUsuario(new UsuarioDB().SeleccionarPorId(usuario.getId()));
                     RolUsuarioDB r = new RolUsuarioDB();
                     pr.setRolUsuario(r.SeleccionarPorId(2));
                     pr.setId_Registra(IdRegistra);
                     pr.setFechaRegistra(fecha);
                     pr.setId_Edita(IdEdita);
                     pr.setFechaEdita(fecha);
                     pr.setFuncionario(null);
                     pr.setEstado("Activo");
                     prodb.registrar2(pr);
                     setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Programa registrado con éxito!</div>" );
                }
                seleccionarTodos();
            }
        }catch(Exception e){
            setMensajeError( "<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al guardar el programa...¡Intentelo de nuevo!</div>");
        }
         
    }
 
     /*Botón editar*/
     public void editar(int i) throws SNMPExceptions, SQLException{
        ProgramaDB pro = new ProgramaDB();
        Programa programa = pro.SeleccionarPorId(i);
         setId(programa.getId());
         setNombre(programa.getNombre());
         setDescripcion(programa.getDescripcion());
         setEstado(programa.getEstado());
      }
    
     /*Botón de buscar*/
     public void buscar() throws SNMPExceptions, SQLException{
       try{
        ProgramaDB pro = new ProgramaDB();
        ProgramaUsuarioDB p = new ProgramaUsuarioDB();
        LinkedList<Programa> lp = new LinkedList<Programa>();
        
        if(!listaTablaPrograma.isEmpty()){
            
            if(!buscarFiltro.equals("")){
                if(!pro.FiltrarPrograma(buscarFiltro).isEmpty()){
                    
                    for (Programa programa : pro.FiltrarPrograma(buscarFiltro)) {
                        for (Programa programa1 : listaTablaPrograma) {
                            if(programa.getId() == programa1.getId()){
                                lp.add(programa);
                            }
                        }
                    }

                    listaTablaPrograma = lp;
                    setMensajeFiltro("");
                }else{
                    seleccionarTodos();
                    setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
                
                }
            }
        }
        
       }catch(Exception e){
           setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el programa...¡Intentelo de nuevo!</div>");
       }
     }
     
     /*Botón de nuevo*/
     public void nuevo(){
         setId(0);
         setNombre("");
         setDescripcion("");
         setEstado("Activo");
         setMensajeGuardar("");
         setMensajeError("");
         setMensajeFiltro("");
         setBuscarFiltro("");
     }
     
     /*Validaciones*/
     public boolean Validaciones(){
         boolean indicador = true;
         if(getNombre().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe ingresar el nombre del Programa</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getDescripcion().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe ingresar la descripción del programa</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         return indicador;
     }
     
     
    /*Todos los set y get*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LinkedList<Programa> getListaTablaPrograma() {
        return listaTablaPrograma;
    }

    public void setListaTablaPrograma(LinkedList<Programa> listaTablaPrograma) {
        this.listaTablaPrograma = listaTablaPrograma;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    

    public  String getBuscarFiltro() {
        return buscarFiltro;
    }

    public void setBuscarFiltro(String buscarFiltro) {
        this.buscarFiltro = buscarFiltro;
    }

    public String getMensajeFiltro() {
        return mensajeFiltro;
    }

    public void setMensajeFiltro(String mensajeFiltro) {
        this.mensajeFiltro = mensajeFiltro;
    }

    public String getMensajeGuardar() {
        return mensajeGuardar;
    }

    public void setMensajeGuardar(String mensajeGuardar) {
        this.mensajeGuardar = mensajeGuardar;
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

    public int getIdRegistra() {
        return IdRegistra;
    }

    public void setIdRegistra(int IdRegistra) {
        this.IdRegistra = IdRegistra;
    }

    public int getIdEdita() {
        return IdEdita;
    }

    public void setIdEdita(int IdEdita) {
        this.IdEdita = IdEdita;
    }

    
    
}
