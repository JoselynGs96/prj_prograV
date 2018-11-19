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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    String estado;
    LinkedList<Programa> listaTablaPrograma = new LinkedList<Programa>();
    String buscarFiltro;
    String mensajeFiltro;
    String mensajeNombre;
    String mensajeDescripcion;
    String mensajeEstado;
    String mensajeGuardar;

   
   
    
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
        if(Validaciones() == true){
            Programa pro = new Programa();
            pro.setId(id);
            pro.setNombre(nombre);
            pro.setDescripcion(descripcion);
            pro.setEstado(estado);
            ProgramaDB prog = new ProgramaDB();
            if(getId() != 0){
                 prog.actulizar(pro);
                 setMensajeGuardar("¡Programa actualizado con éxito!");
                  FacesContext context = FacesContext.getCurrentInstance();
                  context.addMessage(null, new FacesMessage("Exitoso",  mensajeGuardar) );
                 
            }else{
                 prog.registrar(pro);
                 setMensajeGuardar("¡Programa registrado con éxito!");
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Exitoso",  mensajeGuardar) );
                 
            }
            seleccionarTodos();
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
        ProgramaDB pro = new ProgramaDB();
        if(!getBuscarFiltro().equals("")){
            if(!pro.FiltrarPrograma(buscarFiltro).isEmpty()){
                listaTablaPrograma = pro.FiltrarPrograma(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaTablaPrograma = pro.SeleccionarTodos();
                setMensajeFiltro("No se encontraron registros con el dato proporcionado");
               
            }
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
     
     
     
     
     /*Validaciones*/
     public boolean Validaciones(){
         boolean indicador = true;
         if(getNombre().equals("")){
             setMensajeNombre("Debe ingresar el nombre del Programa*");
             return indicador = false;
         }else{
             setMensajeNombre("");
             indicador = true;
         }
         
         if(getDescripcion().equals("")){
             setMensajeDescripcion("Debe ingresar una descripción del Programa*");
             return indicador = false;
         }else{
             setMensajeDescripcion("");
             indicador = true;
         }
         
         if(getEstado().equals("")){
             setMensajeEstado("Debe seleccionar el estado del Programa*");
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

    public LinkedList<Programa> getListaTablaPrograma() {
        return listaTablaPrograma;
    }

    public void setListaTablaPrograma(LinkedList<Programa> listaTablaPrograma) {
        this.listaTablaPrograma = listaTablaPrograma;
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
    
    
    
}
