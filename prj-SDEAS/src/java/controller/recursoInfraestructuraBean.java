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
import model.Recurso;
import model.RecursoDB;
import model.TipoRecurso;
import model.TipoRecursoDB;

/**
 *
 * @author ujose
 */
@Named(value = "recursoInfraestructuraBean")
@SessionScoped
public class recursoInfraestructuraBean implements Serializable {
    int id;
    int cantidad;
    int capacidad;
    String nombre;
    String descripcion;
    String estado;
    int tipoRecurso;
    LinkedList<Recurso> listaTablaRecurso = new LinkedList<Recurso>();
    LinkedList<TipoRecurso> listaTipoRecurso = new LinkedList<TipoRecurso>();
    String buscarFiltro;
    String mensajeFiltro;
    String mensajeNombre;
    String mensajeDescripcion;
    String mensajeEstado;
    String mensajeGuardar;
    String mensajeTipoRecurso;
    
    

   
    /**
     * Creates a new instance of recursoInfraestructuraBean
     */
    public recursoInfraestructuraBean() {
    }
    
     /*Selecciona todos los programas*/
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        RecursoDB rec = new RecursoDB();
        if(!rec.SeleccionarTodos().isEmpty()){
            listaTablaRecurso.clear();
            listaTablaRecurso = rec.SeleccionarTodos();
        }
    }

    
    
    /*Botón guardar; inserta uno nuevo o modifica*/
     public void insertarRecurso() throws SNMPExceptions, SQLException{
        if(Validaciones() == true){
            Recurso rec = new Recurso();
            TipoRecursoDB tdb = new TipoRecursoDB();
            rec.setId(id);
            rec.setNombre(nombre);
            rec.setDescripcion(descripcion);
            rec.setCantidad(cantidad);
            rec.setCapacidad(capacidad);
            rec.setTipoRecurso(tdb.SeleccionarPorId(tipoRecurso));
            rec.setEstado(estado);
            RecursoDB recd = new RecursoDB();
            if(getId() != 0){
                 recd.actulizar(rec);
                 setMensajeGuardar("¡Recurso actualizado con éxito!");
                  FacesContext context = FacesContext.getCurrentInstance();
                  context.addMessage(null, new FacesMessage("Exitoso",  mensajeGuardar) );
                 
            }else{
                 recd.registrar(rec);
                 setMensajeGuardar("¡Recurso registrado con éxito!");
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Exitoso",  mensajeGuardar) );
                 
            }
            seleccionarTodos();
        }
    }

    
     
 
     /*Botón editar*/
     public void editar(int i) throws SNMPExceptions, SQLException{
        RecursoDB recd = new RecursoDB();
        Recurso recurso = recd.SeleccionarPorId(i);
         setId(recurso.getId());
         setNombre(recurso.getNombre());
         setDescripcion(recurso.getDescripcion());
         setCantidad(recurso.getCantidad());
         setCapacidad(recurso.getCapacidad());
         setTipoRecurso(recurso.getTipoRecurso().getId());
         setEstado(recurso.getEstado());
      }
    
     /*Botón de buscar*/
     public void buscar() throws SNMPExceptions, SQLException{
        RecursoDB recd = new RecursoDB();
        if(!getBuscarFiltro().equals("")){
            if(!recd.FiltrarRecurso(buscarFiltro).isEmpty()){
                listaTablaRecurso = recd.FiltrarRecurso(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaTablaRecurso = recd.SeleccionarTodos();
                setMensajeFiltro("No se encontraron registros con el dato proporcionado");
                FacesContext context = FacesContext.getCurrentInstance();
                  context.addMessage(null, new FacesMessage("Lo sentimos,",  mensajeFiltro) );
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
         setTipoRecurso(0);
         setCantidad(0);
         setCapacidad(0);
     }
     
     /*Botón de ayuda*/
     
     
     /*Validaciones*/
     public boolean Validaciones(){
         boolean indicador = true;
         if(getNombre().equals("")){
             setMensajeNombre("Debe ingresar el nombre del Recurso*");
             return indicador = false;
         }else{
             setMensajeNombre("");
             indicador = true;
         }
         
         if(tipoRecurso == 1){
             if(getCapacidad() == 0){
             setMensajeTipoRecurso("Debe seleccionar la capacidad del Recurso*");
             return indicador = false;
                }else{
                    setMensajeTipoRecurso("");
                    indicador = true;
                }
         }else{
             if(getCantidad() == 0){
             setMensajeTipoRecurso("Debe seleccionar la cantidad del Recurso*");
             return indicador = false;
                }else{
                    setMensajeTipoRecurso("");
                    indicador = true;
                }
         }
         
         
         if(getDescripcion().equals("")){
             setMensajeDescripcion("Debe ingresar una descripción del Recurso*");
             return indicador = false;
         }else{
             setMensajeDescripcion("");
             indicador = true;
         }
         
         if(getEstado().equals("")){
             setMensajeEstado("Debe seleccionar el estado del Recurso*");
             return indicador = false;
         }else{
             setMensajeEstado("");
             indicador = true;
         }
         return indicador;
     }
     
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
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

    public LinkedList<Recurso> getListaTablaRecurso() {
        return listaTablaRecurso;
    }

    public void setListaTablaRecurso(LinkedList<Recurso> listaTablaRecurso) {
        this.listaTablaRecurso = listaTablaRecurso;
    }

    public String getBuscarFiltro() {
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
    
    public int getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(int tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }
    
    public String getMensajeTipoRecurso() {
        return mensajeTipoRecurso;
    }

    public void setMensajeTipoRecurso(String mensajeTipoRecurso) {
        this.mensajeTipoRecurso = mensajeTipoRecurso;
    }
    
    public LinkedList<TipoRecurso> getListaTipoRecurso() throws SNMPExceptions, SQLException {
        TipoRecursoDB tdb = new TipoRecursoDB();
        return tdb.SeleccionarTodos();
    }

    public void setListaTipoRecurso(LinkedList<TipoRecurso> listaTipoRecurso) {
        this.listaTipoRecurso = listaTipoRecurso;
    }
}