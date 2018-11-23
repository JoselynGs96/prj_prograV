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
    String estado;
    int programa;
    LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
    LinkedList<Curso> listaTablaCurso = new LinkedList<Curso>();
    String buscarFiltro;
    String mensajeFiltro;
    String mensajeNombre;
    String mensajeDescripcion;
    String mensajeEstado;
    String mensajeGuardar;
    String mensajePrograma;

   
    /**
     * Creates a new instance of ProgramaBean
     */
    public CursoPorProgramaBean() throws SNMPExceptions, SQLException {
        ProgramaDB proDb = new ProgramaDB();
        if(!proDb.SeleccionarTodos().isEmpty()){
            listaPrograma = proDb.SeleccionarTodos();
        }
        seleccionarTodos();
        
    }

    
    
    /*Selecciona todos los programas*/
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        CursoDB cur = new CursoDB();
        if(!cur.SeleccionarTodos().isEmpty()){
            listaTablaCurso.clear();
            listaTablaCurso = cur.SeleccionarTodos();
            setMensajePrograma("");
        }else{
            setMensajePrograma("No existen programas*");
        }
    }
    
    /*Botón guardar; inserta uno nuevo o modifica*/
     public void insertarCurso() throws SNMPExceptions, SQLException{
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
                 setMensajeGuardar("¡Curso actualizado con éxito!");
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Exitoso",  mensajeGuardar) );
            }else{
                 curs.registrar(cur);
                 setMensajeGuardar("¡Curso registrado con éxito!");
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Exitoso",  mensajeGuardar) );
            }
            seleccionarTodos();
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
        if(!getBuscarFiltro().equals("")){
            if(!cur.FiltrarCurso(buscarFiltro).isEmpty()){
                listaTablaCurso = cur.FiltrarCurso(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaTablaCurso = cur.SeleccionarTodos();
                setMensajeFiltro("No se encontraron registros con el dato proporcionado");
            }
        }else{
            seleccionarTodos();
        }
     }
     
     /*Botón de nuevo*/
     public void nuevo(){
         setId(0);
         setNombre("");
         setDescripcion("");
         setEstado("");
         setMensajeGuardar("");
         setMensajeEstado("");
         setMensajeDescripcion("");
         setMensajeNombre("");
     }
     
     /*Botón de ayuda*/
     
     
     /*Validaciones*/
     public boolean Validaciones(){
         boolean indicador = true;
         if(getNombre().equals("")){
             setMensajeNombre("Debe ingresar el nombre del Curso*");
             return indicador = false;
         }else{
             setMensajeNombre("");
             indicador = true;
         }
         
         if(getDescripcion().equals("")){
             setMensajeDescripcion("Debe ingresar una descripción del Curso*");
             return indicador = false;
         }else{
             setMensajeDescripcion("");
             indicador = true;
         }
         
         if(getEstado().equals("")){
             setMensajeEstado("Debe seleccionar el estado del Curso*");
             return indicador = false;
         }else{
             setMensajeEstado("");
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
    
    public String getMensajeNombre() {
        return mensajeNombre;
    }

    public void setMensajeNombre(String mensajeNombre) {
        this.mensajeNombre = mensajeNombre;
    }

    public String getMensajeDescripcion() {
        return mensajeDescripcion;
    }

    public void setMensajeDescripcion(String mensajeDescripcion) {
        this.mensajeDescripcion = mensajeDescripcion;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
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
    
    public String getMensajePrograma() {
        return mensajePrograma;
    }

    public void setMensajePrograma(String mensajePrograma) {
        this.mensajePrograma = mensajePrograma;
    }
    
    
}
