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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import model.Agenda;
import model.AgendaDB;
import model.Detalle;
import model.DetalleDB;
import model.Direccion;
import model.EncabezadoSolicitud;
import model.EncabezadoSolicitudDB;
import model.Provincia;
import model.Recurso;
import model.RecursoDB;
import model.Usuario;
import model.UsuarioDB;
import model.UsuarioMante;
import model.UsuarioManteDB;

/**
 *
 * @author Fabi
 */
@Named(value = "preestamoBean")
@SessionScoped
public class PreestamoBean implements Serializable {

    AgendaDB agendaDB = new AgendaDB();
    EncabezadoSolicitudDB encabezadoDB = new EncabezadoSolicitudDB();
    RecursoDB recursoDB = new RecursoDB();
    DetalleDB detalleDB = new DetalleDB();
    UsuarioDB usuarioDB = new UsuarioDB();

    boolean Lunes;
    boolean Martes;
    boolean Miercoles;
    boolean Jueves;
    boolean Viernes;
    boolean Sabado;
    boolean Domingo;
    Date FechaInicio;
    Date FechaFinal;
    Date HoraInicio;
    Date HoraFinal;
    private int recurso = 1000;
    String Observaciones;
    String mensajeError;
    int Contador = 0;
    Usuario usuario = null;
    String MensajeBueno;
    ObtenerDatosSesion datos = null;
    LinkedList<Recurso> listaRecurso = new LinkedList<Recurso>();
    LinkedList<Recurso> listaRecursoAgregardos = new LinkedList<Recurso>();
    String nombre  = "";
            
            
            
        public PreestamoBean() throws SNMPExceptions, SQLException {
        datos = new ObtenerDatosSesion();
       
        datos.consultarSesion();
        if(!datos.getId_Usuario().equals("")){
            usuario = usuarioDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
            setNombre(usuario.getNombreCompleto());
        }
        if (!recursoDB.seleccionarTodos().isEmpty()) {
            listaRecurso = recursoDB.seleccionarTodos();          
        } else {
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Aún NO existen recurso</div>");
        }
    }

    public void agregarRecursos() throws SNMPExceptions, SQLException {
        Recurso recurso = recursoDB.SeleccionarPorId(this.getRecurso());
        listaRecursoAgregardos.add(recurso);
        LinkedList<Recurso> listaRecurso2 = listaRecurso;    
       

        for (Recurso re : listaRecurso2) {
            for (Recurso recursoAgregados : listaRecursoAgregardos) {
                if (re.getId() == recurso.getId()) {
                    listaRecurso.remove(re);
                }
            }

        }
    }

    public boolean validaciones() {
        boolean respuesta;
        if (this.getFechaInicio() == null) {
            this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>*Debe colocar la fecha de inicio</div>");
            respuesta = false;
        } else {
            if (this.getHoraInicio() == null) {
                this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>*Debe colocar la hora de inicio</div>");
                respuesta = false;
            } else {
                if (this.getFechaFinal() == null) {
                    this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>*Debe colocar la fecha de final</div>");
                    respuesta = false;
                } else {
                    if (this.getHoraFinal() == null) {
                        this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe colocar la hora de final</div>*");
                        respuesta = false;
                    } else {
                        if (this.getHoraFinal() == null) {
                            this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>*Debe colocar la hora de final</div>");
                            respuesta = false;
                        } else {
                            if (!validarDiasSeleccionados()) {
                                this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe seleccionar al menos un dia</div>");
                                respuesta = false;
                            } else {
                                if (this.getListaRecursoAgregardos().isEmpty()) {
                                    this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe agregar al menos un recurso a la lista</div>");
                                    respuesta = false;
                                } else {
                                    if (this.getObservaciones().equals("")) {
                                        this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Debe colocar alguna observación</div>");
                                        respuesta = false;
                                    } else {
                                        respuesta = true;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return respuesta;
    }

    public boolean validarDiasSeleccionados() {
        boolean respuesta = false;
        if (Lunes) {
            respuesta = true;
        } else {
            if (Martes) {
                respuesta = true;
            } else {
                if (Miercoles) {
                    respuesta = true;
                } else {
                    if (Jueves) {
                        respuesta = true;
                    } else {
                        if (Viernes) {
                            respuesta = true;
                        } else {
                            if (Sabado) {
                                respuesta = true;
                            } else {
                                if (Domingo) {
                                    respuesta = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return respuesta;
    }

    /*Este metodo valida que los dias seleccionados esten estre el rango*/
    public boolean prueba() throws ParseException {
        /*Tengocontadores x dia para no contar dos dias iguales y que el contador me quede igual a los activos*/
        contadorActivos();
        int contadorMartes = 0;
        int contadorMiercoles = 0;
        int contadorJueves = 0;
        int contadorViernes = 0;
        int contadorLunes = 0;
        int contadorSabado = 0;
        int contadorDomingo = 0;
        int contador2 = 0;
        /*Formato Fecha*/
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        /*Pone formao a la fecha de inicio*/
        String fecha = formato.format(this.getFechaInicio());
        String fechaFinal = formato.format(this.getFechaFinal());
        /*Combierte el string en fecha para convertirlo en calendar*/
        Date fechaInicio1 = formato.parse(fecha);
        Date fechaFinal1 = formato.parse(fechaFinal);
        /*Calendario para sacar el dia de la semana*/
        Calendar can = Calendar.getInstance();
        can.setTime(fechaInicio1);
        /*Calendario final*/
        Calendar canFinal = Calendar.getInstance();
        canFinal.setTime(fechaFinal1);
        /*Dia de la semana de la fecha inicio*/
        int diaSemana = can.get(Calendar.DAY_OF_WEEK);
        /*Dias que hay entre las dos fechas*/
        int diasEntreFechas = (int) ((fechaFinal1.getTime() - fechaInicio1.getTime()) / 86400000) + 1;

        for (int i = 1; i <= diasEntreFechas; i++) {
            if (diaSemana == 2) {
                if (this.isLunes()) {
                    contadorLunes++;
                    /*Este contador lunes esta hecho para no contar dos veces el lunes y que el contador2 sea igual al contador de activos*/
                    if (contadorLunes == 1) {
                        contador2++;
                    }
                }
            } else {
                if (diaSemana == 3) {
                    contadorMartes++;
                    if (this.isMartes()) {
                        if (contadorMartes == 1) {
                            contador2++;
                        }
                    }
                } else {
                    if (diaSemana == 4) {
                        contadorMiercoles++;
                        if (this.isMiercoles()) {
                            if (contadorMiercoles == 1) {
                                contador2++;
                            }
                        }
                    } else {
                        if (diaSemana == 5) {
                            contadorJueves++;
                            if (this.isJueves()) {
                                if (contadorJueves == 1) {
                                    contador2++;
                                }
                            }
                        }
                        if (diaSemana == 6) {
                            contadorViernes++;
                            if (this.isViernes()) {
                                if (contadorViernes == 1) {
                                    contador2++;
                                }
                            }
                        } else {
                            if (diaSemana == 7) {
                                contadorSabado++;
                                if (this.isSabado()) {
                                    if (contadorSabado == 1) {
                                        contador2++;
                                    }
                                }
                            } else {
                                if (diaSemana == 1) {
                                    if (this.isDomingo()) {
                                        contadorDomingo++;
                                        if (contadorDomingo == 1) {
                                            contador2++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*Cambia los dias de la semana*/
            if (diaSemana == 7) {
                diaSemana = 1;
            } else {
                diaSemana++;
            }
        }
        /*Valida que los dias de la semana que se contaron sean iguales que los activos*/
        if (contador2 == this.getContador()) {
            return true;
        } else {
            return false;
        }

    }

    /*Este metodo cuenta cuantos dias selecciono el usuario*/
    public void contadorActivos() {
        if (this.isLunes()) {
            this.setContador(this.getContador() + 1);
        }
        if (this.isMartes()) {
            this.setContador(this.getContador() + 1);
        }
        if (this.isMiercoles()) {
            this.setContador(this.getContador() + 1);
        }
        if (this.isJueves()) {
            this.setContador(this.getContador() + 1);
        }
        if (this.isViernes()) {
            this.setContador(this.getContador() + 1);
        }
        if (this.isSabado()) {
            this.setContador(this.getContador() + 1);
        }
        if (this.isDomingo()) {
            this.setContador(this.getContador() + 1);
        }

    }

    /*Este metodo valida que las fechas ingresadas sean mayores a la fecha actual*/
    public boolean validaFechaActual() throws ParseException {
        int contador = 0;
        /*Formato Fecha*/
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        /*Pone formao a la fecha de inicio*/
        String fecha = formato.format(this.getFechaInicio());
        String fechaFinal = formato.format(this.getFechaFinal());
        /*Combierte el string en fecha para convertirlo en calendar*/
        Date fechaInicio1 = formato.parse(fecha);
        Date fechaFinal1 = formato.parse(fechaFinal);
        Date ahora = new Date();
        /*Valida  fecha Inicio y fecha actual*/
        int diasEntreFechas = (int) ((this.getFechaInicio().getTime() - ahora.getTime()) / 86400000);
        /*Valida  fecha final y fecha actual*/
        int diasEntreFechas1 = (int) ((fechaFinal1.getTime() - ahora.getTime()) / 86400000);

        /*si el numero es negativo quire decir que la fecha es menor si es mayor quiere decir que esta bien*/
        if (diasEntreFechas > 1) {
            contador++;
        } else {
            if (diasEntreFechas1 > 0) {
                contador++;
            }
        }
        if (contador == 0) {
            return false;
        } else {
            return true;
        }

    }

    public void eliminarRecursos(int id) throws SNMPExceptions, SQLException {
        LinkedList<Recurso> listaRecurso2 = recursoDB.seleccionarTodos();
        LinkedList<Recurso> listaAgregados = listaRecursoAgregardos;
        Recurso recurso = recursoDB.SeleccionarPorId(id);

        for (Recurso re : listaRecurso2) {
            if (re.getId() == id) {
                listaRecurso.add(re);
            }
        }
        for (Recurso re : listaAgregados) {
            if (re.getId() == id) {
                listaRecursoAgregardos.remove(re);
            }
        }
    }

    public boolean validaExistente() throws SNMPExceptions, SQLException {
        boolean respuesta = false;
        int contadorfor = 0;
        int contadorRespuestas = 0;
        for (Recurso re : listaRecursoAgregardos) {
            contadorfor++;
            Agenda agendaNueva = new Agenda(this.isLunes(), this.isMartes(), this.isMiercoles(), this.isJueves(), this.isViernes(), this.isSabado(), this.isDomingo(), this.getFechaInicio(), this.getFechaFinal(), this.getHoraInicio(), this.getHoraFinal(), re, 1, this.getObservaciones());
            int AgendaBD = agendaDB.SeleccionarExistente(agendaNueva);
            if (AgendaBD == 0) {
                contadorRespuestas++;

            }
        }
        if (contadorRespuestas == contadorfor) {
           
            respuesta = true;
        } else {
            this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>No puede agregar la solicitud, Ya existe una solicitud con esos datos</div>");
            respuesta = false;
        }

        return respuesta;
    }

    public void ingresarAgendas() throws ParseException, SNMPExceptions, SQLException {
try{
        EncabezadoSolicitudDB encabezadoDB = new EncabezadoSolicitudDB();
        DetalleDB detalleDB = new DetalleDB();
        AgendaDB agendaDB = new AgendaDB();
        if (validaciones()) {
             if (validaFechaActual()) {
            if (prueba()) {            
                if (validarDiasSeleccionados()) {
                    if (validaExistente()) {
                        /*INGRESO EL ENCABEZADO*/
                        EncabezadoSolicitud encabezado = new EncabezadoSolicitud();
                        encabezado.setFuncionario(usuarioDB.SeleccionarPorId(207750517));
                        encabezadoDB.registrar(encabezado);
                        /*Ingreso el detalle*/
                        LinkedList<Recurso> listaAgregados = listaRecursoAgregardos;
                        for (Recurso re : listaAgregados) {
                            Detalle detalle = new Detalle();
                            int enca= encabezadoDB.SeleccionarUltimo();
                            detalle.setEncabezado(encabezadoDB.SeleccionarporId(enca));
                            detalle.setRecurso(re);
                            detalleDB.registrar(detalle);
                        }
                        /*Ingeso la agenda*/
                        for (Recurso re : listaRecursoAgregardos) {
                            Agenda agendaNueva = new Agenda(this.isLunes(), this.isMartes(), this.isMiercoles(), this.isJueves(), this.isViernes(), this.isSabado(), this.isDomingo(), this.getFechaInicio(), this.getFechaFinal(), this.getHoraInicio(), this.getHoraFinal(), re, 1, this.getObservaciones());
                            agendaNueva.setId_Registra(207750517);
                            agendaDB.registrar(agendaNueva);
                        }
                        limpiar();
                        setMensajeError("");
               
                        setMensajeBueno("<div class='alert alert-success alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Exitoso!&nbsp;</strong>¡Su solicitud ha sido enviada correctamente!</div>");
                    }

                }
            } else {
                this.setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Los días seleccionados no concuerdan con el rango de fechas...¡Intentelo de nuevo!</div>");
            }
        }
        }
         
         }catch(Exception e){
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Hubo un error al buscar el curso...¡Intentelo de nuevo!</div>");
        }

    }
    public void limpiar()throws SNMPExceptions,SQLException{
        this.setFechaInicio(null);
        this.setFechaFinal(null);
        this.setHoraInicio(null);
        this.setHoraFinal(null);
        
          if (!recursoDB.seleccionarTodos().isEmpty()) {
            listaRecurso = recursoDB.seleccionarTodos();          
        } else {
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a> <strong>Error!&nbsp;</strong>Aún NO existen recurso</div>");
        }
          this.listaRecursoAgregardos.remove();
          this.setObservaciones("");
          this.setLunes(false);
          this.setMartes(false);
          this.setMiercoles(false);
          this.setJueves(false);
          this.setViernes(false);
          this.setJueves(false);
          this.setDomingo(false);         
        
    }

    public int getContador() {
        return Contador;
    }

    public void setContador(int Contador) {
        this.Contador = Contador;
    }

    public AgendaDB getAgendaDB() {
        return agendaDB;
    }

    public void setAgendaDB(AgendaDB agendaDB) {
        this.agendaDB = agendaDB;
    }

    public EncabezadoSolicitudDB getEncabezadoDB() {
        return encabezadoDB;
    }

    public void setEncabezadoDB(EncabezadoSolicitudDB encabezadoDB) {
        this.encabezadoDB = encabezadoDB;
    }
    
    

    public RecursoDB getRecursoDB() {
        return recursoDB;
    }

    public void setRecursoDB(RecursoDB recursoDB) {
        this.recursoDB = recursoDB;
    }

    public DetalleDB getDetalleDB() {
        return detalleDB;
    }

    public void setDetalleDB(DetalleDB detalleDB) {
        this.detalleDB = detalleDB;
    }

    public UsuarioDB getUsuarioDB() {
        return usuarioDB;
    }

    public void setUsuarioDB(UsuarioDB usuarioDB) {
        this.usuarioDB = usuarioDB;
    }

    public boolean isLunes() {
        return Lunes;
    }

    public void setLunes(boolean Lunes) {
        this.Lunes = Lunes;
    }

    public boolean isMartes() {
        return Martes;
    }

    public void setMartes(boolean Martes) {
        this.Martes = Martes;
    }

    public boolean isMiercoles() {
        return Miercoles;
    }

    public void setMiercoles(boolean Miercoles) {
        this.Miercoles = Miercoles;
    }

    public boolean isJueves() {
        return Jueves;
    }

    public void setJueves(boolean Jueves) {
        this.Jueves = Jueves;
    }

    public boolean isViernes() {
        return Viernes;
    }

    public void setViernes(boolean Viernes) {
        this.Viernes = Viernes;
    }

    public boolean isSabado() {
        return Sabado;
    }

    public void setSabado(boolean Sabado) {
        this.Sabado = Sabado;
    }

    public boolean isDomingo() {
        return Domingo;
    }

    public void setDomingo(boolean Domingo) {
        this.Domingo = Domingo;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Date getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(Date FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    public Date getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(Date HoraInicio) {
        this.HoraInicio = HoraInicio;
    }

    public Date getHoraFinal() {
        return HoraFinal;
    }

    public void setHoraFinal(Date HoraFinal) {
        this.HoraFinal = HoraFinal;
    }

    public int getRecurso() {
        return recurso;
    }

    public void setRecurso(int recurso) {
        this.recurso = recurso;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public LinkedList<Recurso> getListaRecurso() {
        return listaRecurso;
    }

    public void setListaRecurso(LinkedList<Recurso> listaRecurso) {
        this.listaRecurso = listaRecurso;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public LinkedList<Recurso> getListaRecursoAgregardos() {
        return listaRecursoAgregardos;
    }

    public void setListaRecursoAgregardos(LinkedList<Recurso> listaRecursoAgregardos) {
        this.listaRecursoAgregardos = listaRecursoAgregardos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensajeBueno() {
        return MensajeBueno;
    }

    public void setMensajeBueno(String MensajeBueno) {
        this.MensajeBueno = MensajeBueno;
    }

    public ObtenerDatosSesion getDatos() {
        return datos;
    }

    public void setDatos(ObtenerDatosSesion datos) {
        this.datos = datos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
