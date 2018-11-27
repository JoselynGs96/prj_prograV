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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Curso;
import model.CursoDB;
import model.Programa;
import model.ProgramaDB;

/**
 *
 * @author ujose
 */
@Named(value = "cursoPorProgramaBean")
@SessionScoped
public class CursoPorProgramaBean implements Serializable {
    int id;
    String nombre;
    String descripcion;
    String estado = "Activo";
    int programa;
    LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
    LinkedList<Curso> listaTablaCurso = new LinkedList<Curso>();
    String buscarFiltro;
    String mensajeFiltro;
    String mensajeGuardar;
    String mensajeError;

   
    /**
     * Creates a new instance of ProgramaBean
     */
    public CursoPorProgramaBean() throws SNMPExceptions, SQLException {
        ProgramaDB proDb = new ProgramaDB();
        if(!proDb.SeleccionarTodos().isEmpty()){
            listaPrograma = proDb.SeleccionarTodos();
        }else{
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Aún NO existen programas</div>");         
        }
        seleccionarTodos();
    }

    
    
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        CursoDB cur = new CursoDB();
        if(!cur.SeleccionarTodos().isEmpty()){
            listaTablaCurso.clear();
            listaTablaCurso = cur.SeleccionarTodos();
            setMensajeError("");
        }else{
           
        }
    }
    
    /*Botón guardar; inserta uno nuevo o modifica*/
     public void insertarCurso() throws SNMPExceptions, SQLException{
        try{
         if(Validaciones() == true){
            Curso cur = new Curso();
            cur.setId(id);
            cur.setNombre(nombre);
            cur.setDescripcion(descripcion);
            cur.setEstado(estado);
            ProgramaDB prog = new ProgramaDB();
            cur.setPrograma(prog.SeleccionarPorId(programa));
            CursoDB curs = new CursoDB();
            if(getId() != 0){
                 curs.actulizar(cur);
                 setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Curso actualizado con éxito!</div> ");
            }else{
                 curs.registrar(cur);
                 setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Curso registrado con éxito!</div> ");
            }
            seleccionarTodos();
        }
        }catch(Exception e){
            setMensajeError( "<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al guardar el curso...¡Intentelo de nuevo!</div>");
        }
    }
     
     /*Botón editar*/
     public void editar(int i) throws SNMPExceptions, SQLException{
        CursoDB cur = new CursoDB();
        Curso Curso = cur.SeleccionarPorId(i);
         setId(Curso.getId());
         setNombre(Curso.getNombre());
         setDescripcion(Curso.getDescripcion());
         setEstado(Curso.getEstado());
      }
    
     /*Botón de buscar*/
     public void buscar() throws SNMPExceptions, SQLException{
        CursoDB cur = new CursoDB();
        try{
        if(!getBuscarFiltro().equals("")){
            if(!cur.FiltrarCurso(buscarFiltro).isEmpty()){
                listaTablaCurso = cur.FiltrarCurso(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaTablaCurso = cur.SeleccionarTodos();
                setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
            }
        }else{
            seleccionarTodos();
            setMensajeFiltro("");
        }
        }catch(Exception e){
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el curso...¡Intentelo de nuevo!</div>");
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
     public boolean Validaciones() throws SNMPExceptions, SQLException{
         boolean indicador = true;
         if(getNombre().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe ingresar el nombre del Curso</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getDescripcion().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe ingresar la descripción del Curso</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
          ProgramaDB proDb = new ProgramaDB();
            if(proDb.SeleccionarTodos().isEmpty()){
                setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Aún NO existen programas</div>");         
                return indicador = false;
            }else{
                if(getPrograma() == 0){
                    setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el programa al que pertenece el Curso</div>");
                    return indicador = false;
                }else{
                    setMensajeError("");
                    indicador = true;
                }
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

    public LinkedList<Curso> getListaTablaCurso() {
        return listaTablaCurso;
    }

    public void setListaTablaCurso(LinkedList<Curso> listaTablaCurso) {
        this.listaTablaCurso = listaTablaCurso;
    }
    
     public String getMensajeFiltro() {
        return mensajeFiltro;
    }

    public void setMensajeFiltro(String mensajeFiltro) {
        this.mensajeFiltro = mensajeFiltro;
    }
    
    public String getBuscarFiltro() {
        return buscarFiltro;
    }

    public void setBuscarFiltro(String buscar) {
        this.buscarFiltro = buscar;
    }
    
    
    public String getMensajeGuardar() {
        return mensajeGuardar;
    }

    public void setMensajeGuardar(String mensajeGuardar) {
        this.mensajeGuardar = mensajeGuardar;
    }
    
    
    public int getPrograma() {
        return programa;
    }

    public void setPrograma(int programa) {
        this.programa = programa;
    }

    public LinkedList<Programa> getListaPrograma() {
        return listaPrograma;
    }

    public void setListaPrograma(LinkedList<Programa> listaPrograma) {
        this.listaPrograma = listaPrograma;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
   
    
    
}
