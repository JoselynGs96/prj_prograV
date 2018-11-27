/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SNMPExceptions;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Programa;
import model.ProgramaDB;

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
   
    
    /**
     * Creates a new instance of ProgramaBean
     */
    public ProgramaBean() throws SNMPExceptions, SQLException {
        seleccionarTodos();
       
    }
    
    
    /*Selecciona todos los programas*/
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        ProgramaDB pro = new ProgramaDB();
        if(!pro.SeleccionarTodos().isEmpty()){
            listaTablaPrograma.clear();
            listaTablaPrograma = pro.SeleccionarTodos();
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
                ProgramaDB prog = new ProgramaDB();
                if(getId() != 0){
                     prog.actulizar(pro);
                     setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Programa actualizado con éxito!</div> ");

                }else{
                     prog.registrar(pro);
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
        if(!buscarFiltro.equals("")){
            if(!pro.FiltrarPrograma(buscarFiltro).isEmpty()){
                listaTablaPrograma = pro.FiltrarPrograma(buscarFiltro);
                setMensajeFiltro("");
            }else{
                listaTablaPrograma = pro.SeleccionarTodos();
                setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
            }
        }else{
            seleccionarTodos();
            setMensajeFiltro("");
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

    
}
