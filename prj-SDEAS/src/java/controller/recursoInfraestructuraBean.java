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
import java.util.Date;
import java.util.LinkedList;
import model.EnumTipoInfraestructura;
import model.Programa;
import model.ProgramaDB;
import model.ProgramaUsuarioDB;
import model.Recurso;
import model.RecursoDB;
import model.TipoRecurso;
import model.TipoRecursoDB;
import model.UsuarioMante;
import model.UsuarioManteDB;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ujose
 */
@Named(value = "recursoInfraestructuraBean")
@SessionScoped
public class recursoInfraestructuraBean implements Serializable {
    int id;
    int cantidad = 0;
    int capacidad = 0;
    int tipoRecurso;
    int programa;
    String nombre;
    String descripcion;
    String estado = "Activo";
    String dscTipo;
    String dscNum;
    Recurso recurso;
    EnumTipoInfraestructura tipoInfra;
    String buscarFiltro;
    String mensajeFiltro;
    String mensajeGuardar;
    String mensajeError; 
    String nombreTipoInfra;
    String dscTipoInfra;
    UsuarioMante usuario = null;
    LinkedList<Programa> listaPrograma = new LinkedList<Programa>();
    LinkedList<Recurso> listaTablaRecurso = new LinkedList<Recurso>();
    LinkedList<TipoRecurso> listaTipoRecurso = new LinkedList<TipoRecurso>();
     public EnumTipoInfraestructura[] EnumTipoInfra() {
        return EnumTipoInfraestructura.values();
    }
    Date fecha = new Date();
    int IdRegistra = 0;
    int IdEdita = 0;
    /**
     * Creates a new instance of recursoInfraestructuraBean
     */
    
    public recursoInfraestructuraBean() throws SNMPExceptions, SQLException {
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
        
        llenarComboTipoRecurso();
        llenaListaProgramas();
        seleccionarTodos();
    }
    
    public void llenarComboTipoRecurso() throws SNMPExceptions, SQLException{
        TipoRecursoDB tipDb = new TipoRecursoDB();
        if(!tipDb.SeleccionarTodos().isEmpty()){
            listaTipoRecurso = tipDb.SeleccionarTodos();
        }
    }
    
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        RecursoDB rec = new RecursoDB();
        listaTablaRecurso.clear();
        
        if(!listaPrograma.isEmpty()){
            for (Programa programa1 : listaPrograma) {
                if(!rec.seleccionarTodosPorId(programa1.getId()).isEmpty()){
                    for (Recurso recurso : rec.seleccionarTodosPorId(programa1.getId())) {
                        listaTablaRecurso.add(recurso);
                    }
                }
            }
            
        }else{
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No hay registros porque no hay programas existentes</div>");
        }
    }

    public void llenaListaProgramas() throws SNMPExceptions, SQLException{
        ProgramaUsuarioDB p = new ProgramaUsuarioDB();
        if(!p.SeleccionarTodosPorId(usuario.getId()).isEmpty()){
            listaPrograma = p.SeleccionarTodosPorId(usuario.getId());
        }else{
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Aún NO existen programas</div>");         
        }
    }
    
    /*Botón guardar; inserta uno nuevo o modifica*/
     public void insertarRecurso() throws SNMPExceptions, SQLException{
       try{
         if(Validaciones() == true){
            Recurso rec = new Recurso();
            TipoRecursoDB tdb = new TipoRecursoDB();
            rec.setId(id);
            rec.setNombre(nombre);
            rec.setDescripcion(descripcion);
            rec.setCantidad(cantidad);
            rec.setCapacidad(capacidad);
            rec.setTipoRecurso(tdb.SeleccionarPorId(tipoRecurso));
            rec.setId_Edita(IdEdita);
            rec.setFechaEdita(fecha);
            rec.setEstado(estado);
            ProgramaDB p = new ProgramaDB();
            rec.setPrograma(p.SeleccionarPorId(programa));
            rec.setTipoInfraestructura(tipoInfra);
            RecursoDB recd = new RecursoDB();
            if(getId() != 0){
                 recd.actulizar(rec);
                 setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Recurso actualizado con éxito!</div> ");
                 
            }else{
                 rec.setId_Registra(IdRegistra);
                 rec.setFechaRegistra(fecha);
                 recd.registrar(rec);
                 setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Recurso registrado con éxito!</div> ");
            }
           seleccionarTodos();
        }
       }catch(Exception e){
           setMensajeError( "<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al guardar el recurso...¡Intentelo de nuevo!</div>");
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
         setPrograma(recurso.getPrograma().getId());
         if(recurso.getTipoRecurso().getId()==2){
              setTipoInfra(recurso.getTipoInfraestructura());
         }
        
      }
     
     /*Botón ver más*/
     public void verMas(int i) throws SNMPExceptions, SQLException{
        RecursoDB recd = new RecursoDB();
        this.recurso = recd.SeleccionarPorId(i);
         if(recurso.getTipoRecurso().getId()==2){
             setDscTipo("Capacidad: ");
             setDscNum(recurso.getCapacidad()+"");
             setNombreTipoInfra("Tipo Infraestructura: ");
             setDscTipoInfra(recurso.getTipoInfraestructura().toString());
         }else{
             setDscTipo("Cantidad: ");
             setDscNum(recurso.getCantidad()+"");
             setNombreTipoInfra("");
             setDscTipoInfra("");
         }
        PrimeFaces.current().executeScript("abrirModal();");
     }
    
     /*Botón de buscar*/
     public void buscar() throws SNMPExceptions, SQLException{
        RecursoDB recd = new RecursoDB();
        ProgramaUsuarioDB p = new ProgramaUsuarioDB();
        LinkedList<Recurso> lr = new LinkedList<Recurso>();
        try{
            if(!listaPrograma.isEmpty()) {
              if(!listaTablaRecurso.isEmpty()){
                    if(!getBuscarFiltro().equals("")){
                        if(!recd.FiltrarRecurso(buscarFiltro).isEmpty()){
                            listaTablaRecurso = recd.FiltrarRecurso(buscarFiltro);
                            setMensajeFiltro("");
                            
                            for (Recurso recurso : recd.FiltrarRecurso(buscarFiltro)) {
                                for (Recurso recurso1 : listaTablaRecurso) {
                                    if(recurso.getId() == recurso1.getId()){
                                        lr.add(recurso1);
                                    }
                                }
                            }

                            listaTablaRecurso = lr;
                            setMensajeFiltro("");
                            
                        }else{
                            seleccionarTodos();
                            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
                        }
                    }else{
                        seleccionarTodos();
                        setMensajeFiltro("");
                    }
              }
          }
        }catch(Exception e){
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el recurso...¡Intentelo de nuevo!</div>");
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
         setTipoRecurso(1);
         setCantidad(0);
         setCapacidad(0);
         setNombre("");
         setBuscarFiltro("");
     }
     
     
     
     /*Validaciones*/
     public boolean Validaciones(){
         boolean indicador = true;
         
         if(getNombre().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe ingresar el nombre del Recurso</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getPrograma() == 0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el programa al que pertenece el Recurso</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
            
         if(tipoRecurso == 1){
                setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar el Tipo de Recurso</div>");
                return indicador = false;
         }else{
                if(tipoRecurso == 2){
                           if(getCapacidad() == 0){
                            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar la capacidad del recurso</div>");
                            return indicador = false;
                           }else{
                               setMensajeError("");
                               indicador = true;
                           }
                           
                          
                }else{
                        if(getCantidad() == 0){
                                setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar la cantidad del recurso</div>");
                                return indicador = false;
                           }else{
                               setMensajeError("");
                               indicador = true;
                           }
                }
         }
         
         

         if(getDescripcion().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe ingresar la descripción del Curso</div>");
             return indicador = false;
         }else{
             setMensajeError("");
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
   
    public LinkedList<TipoRecurso> getListaTipoRecurso() throws SNMPExceptions, SQLException {
        TipoRecursoDB tdb = new TipoRecursoDB();
        return tdb.SeleccionarTodos();
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public String getDscNum() {
        return dscNum;
    }

    public void setDscNum(String dscNum) {
        this.dscNum = dscNum;
    }

    public String getDscTipo() {
        return dscTipo;
    }

    public void setDscTipo(String dscTipo) {
        this.dscTipo = dscTipo;
    }

    public EnumTipoInfraestructura getTipoInfra() {
        return tipoInfra;
    }

    public void setTipoInfra(EnumTipoInfraestructura tipoInfra) {
        this.tipoInfra = tipoInfra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getNombreTipoInfra() {
        return nombreTipoInfra;
    }

    public void setNombreTipoInfra(String nombreTipoInfra) {
        this.nombreTipoInfra = nombreTipoInfra;
    }

    public String getDscTipoInfra() {
        return dscTipoInfra;
    }

    public void setDscTipoInfra(String dscTipoInfra) {
        this.dscTipoInfra = dscTipoInfra;
    }
    
    
    
}
