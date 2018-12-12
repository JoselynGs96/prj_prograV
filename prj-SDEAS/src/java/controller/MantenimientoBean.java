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
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Agenda;
import model.AgendaDB;
import model.Detalle;
import model.Direccion;
import model.EncabezadoSolicitud;
import model.EncabezadoSolicitudDB;
import model.EstadoSolicitudDB;
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.Recurso;
import model.Telefono;
import model.TipoTelefono;
import model.UsuarioMante;
import model.UsuarioManteDB;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author ujose
 */
@Named(value = "mantenimientoBean")
@SessionScoped
public class MantenimientoBean implements Serializable {

    /**
     * Creates a new instance of MantenimientoBean
     */
    UsuarioMante usuario = null;
    Date fecha = new Date();
    int IdRegistra = 0;
    int IdEdita = 0;
    private ScheduleModel eventModel;
    EncabezadoSolicitud enca;
    Agenda agenda;
    Detalle detalle;
    LinkedList<EncabezadoSolicitud> listaEnca = new LinkedList<EncabezadoSolicitud>();
    String mensajeFiltro;

    /*Para el modal*/
    String id;
    String nombrefuncionario;
    String Tipo;
    String Estado;
    String log;
    String buscarFiltro = "";
    LinkedList<EncabezadoSolicitud> listaEncabe = new LinkedList<EncabezadoSolicitud>();
    String MsjRechazo = "";

    /*Ajendas*/
    LinkedList<Agenda> listaAgenda = new LinkedList<Agenda>();
    String Agendas = "";

    public MantenimientoBean() throws SNMPExceptions, SQLException {
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
        UsuarioManteDB usuDB = new UsuarioManteDB();
        datos.consultarSesion();

        if (!datos.getId_Usuario().equals("")) {
            usuario = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
            IdRegistra = usuario.getId();
            IdEdita = usuario.getId();
        } else {
            usuario = usuDB.SeleccionarPorId(116390998);
            IdRegistra = usuario.getId();
            IdEdita = usuario.getId();
        }

        llenarCalendario();
        seleccionarTodos();

    }

    public void verMas2(int i) throws SNMPExceptions, SQLException {
        id = "";
        nombrefuncionario = "";
        Tipo = "";
        listaAgenda.clear();
        Estado = "";
        log = "";
        Agendas = "";
        EncabezadoSolicitudDB encDB = new EncabezadoSolicitudDB();
        AgendaDB agenDB = new AgendaDB();

        EncabezadoSolicitud encaSeleccionado = encDB.SeleccionarporId(i);

        id = encaSeleccionado.getId_Encabezado() + "";
        nombrefuncionario = encaSeleccionado.getFuncionario().getNombreCompleto();
        Tipo = encaSeleccionado.getTipo_solicitud().getNombre();
        Estado = encaSeleccionado.getEstado().getNombre();

        listaAgenda = agenDB.SeleccionarTodosPorEncabezado(i);
        int contador = 1;
        for (Agenda re : listaAgenda) {
            Agendas += "Agenda #" + (contador++) + ": \n"
                    + "Identificador: " + re.getId_Agenda() + "\n"
                    + "Dias selecconados: ";
            if (re.isLunes()) {
                Agendas += "Lunes, ";
            }
            if (re.isMartes()) {
                Agendas += "Martes ";
            }
            if (re.isMiercoles()) {
                Agendas += "Miércoles ";
            }
            if (re.isJueves()) {
                Agendas += "Jueves ";
            }
            if (re.isViernes()) {
                Agendas += "Viernes ";
            }
            if (re.isSabado()) {
                Agendas += "Sabado ";
            }
            if (re.isDomingo()) {
                Agendas += "Domingo. ";
            }
            Agendas += "\n Fecha Inicio: " + re.getFechaInicio() + " Hora Inicio: " + re.getHoraInicio() + "\n"
                    + "Fecha Final: " + re.getFechaFinal() + " Hora Final: " + re.getHoraFinal() + "\n Recurso: " + re.getRecurso().getNombre()
                    + "\n Observaciones: " + re.getObseraciones() + "\n\n\n";

        }

        PrimeFaces.current().executeScript("abrirModal();");
    }

    public void verMas(int i) throws SNMPExceptions, SQLException {
        id = "";
        nombrefuncionario = "";
        Tipo = "";
        listaAgenda.clear();
        Estado = "";
        log = "";
        Agendas = "";
        EncabezadoSolicitudDB encDB = new EncabezadoSolicitudDB();
        AgendaDB agenDB = new AgendaDB();

        EncabezadoSolicitud encaSeleccionado = encDB.SeleccionarporId(i);

        id = encaSeleccionado.getId_Encabezado() + "";
        nombrefuncionario = encaSeleccionado.getFuncionario().getNombreCompleto();
        Tipo = encaSeleccionado.getTipo_solicitud().getNombre();
        Estado = encaSeleccionado.getEstado().getNombre();

        listaAgenda = agenDB.SeleccionarTodosPorEncabezado(i);
        int contador = 1;
        for (Agenda re : listaAgenda) {
            Agendas += "Agenda #" + (contador++) + ": \n"
                    + "Identificador: " + re.getId_Agenda() + "\n"
                    + "Dias selecconados: ";
            if (re.isLunes()) {
                Agendas += "Lunes, ";
            }
            if (re.isMartes()) {
                Agendas += "Martes ";
            }
            if (re.isMiercoles()) {
                Agendas += "Miércoles ";
            }
            if (re.isJueves()) {
                Agendas += "Jueves ";
            }
            if (re.isViernes()) {
                Agendas += "Viernes ";
            }
            if (re.isSabado()) {
                Agendas += "Sabado ";
            }
            if (re.isDomingo()) {
                Agendas += "Domingo. ";
            }
            Agendas += "\n Fecha Inicio: " + re.getFechaInicio() + " Hora Inicio: " + re.getHoraInicio() + "\n"
                    + "Fecha Final: " + re.getFechaFinal() + " Hora Final: " + re.getHoraFinal() + "\n Recurso: " + re.getRecurso().getNombre()
                    + "\n Observaciones: " + re.getObseraciones() + "\n\n\n";

        }

    }

    public void botonAceptar(int id) throws SNMPExceptions, SQLException {
        verMas2(id);
        EncabezadoSolicitudDB ddd = new EncabezadoSolicitudDB();
        EncabezadoSolicitud enca = ddd.SeleccionarporId(id);
        setMensajeFiltro("");
        UsuarioMante usu = new UsuarioManteDB().SeleccionarPorId(enca.getFuncionario().getId());
        String remitente = "joselyn.grana2@gmail.com";  //Para la dirección nomcuenta@gmail.com
        String destinatario = usu.getCorreo(); //A quien le quieres escribir.
        String asunto = "Aceptación de SDEAS";
        String cuerpo = "Estimado(a): " + usu.getNombreCompleto() + ",Se le notifica que su solicitud de Evento a SDEAS fue Aceptada. \n Agendas Aceptadas: \n" + this.getAgendas();

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "Abril1210jgs");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            /*Aqui Actualizo el mae*/

            EstadoSolicitudDB estadodb = new EstadoSolicitudDB();
            enca.setEstado(estadodb.SeleccionarPorId(2));
            ddd.ActualizarEstadoSolicitud(enca);

            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "Abril1210jgs");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            setMensajeFiltro("<div class='alert alert-success alert-dismissible fade in' >  <strong>Exito!&nbsp;</strong>Solicitud Aceptada correctamente, correo enviado!</div>");
        } catch (MessagingException me) {
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Error!&nbsp;</strong>El correo no se pudo enviar</div>");  //Si se produce un error  //Si se produce un error
        }
        seleccionarTodos();
    }

    public void botonDenegar(int id) throws SNMPExceptions, SQLException {
        verMas2(id);
        setMensajeFiltro("");
        EncabezadoSolicitudDB ddd = new EncabezadoSolicitudDB();
        EncabezadoSolicitud enca = ddd.SeleccionarporId(id);
        UsuarioMante usu = new UsuarioManteDB().SeleccionarPorId(enca.getFuncionario().getId());
        String remitente = "joselyn.grana2@gmail.com";  //Para la dirección nomcuenta@gmail.com
        String destinatario = usu.getCorreo(); //A quien le quieres escribir.
        String asunto = "Plataforma SDEAS";
        String cuerpo = "Estimado(a): " + usu.getNombreCompleto() + ", Se le notifica que su solicitud de evento en SDEAS fue Denegada, de las siguientes agendas: \n" + Agendas
                + "\nDenegadas por el siguiente motivo:\n" + getMsjRechazo();

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "Abril1210jgs");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "Abril1210jgs");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            EstadoSolicitudDB estadodb = new EstadoSolicitudDB();
            enca.setEstado(estadodb.SeleccionarPorId(3));
            ddd.ActualizarEstadoSolicitud(enca);

            setMensajeFiltro("<div class='alert alert-success alert-dismissible fade in' >  <strong>Exito!&nbsp;</strong>Correo enviado satisfactoriamente</div>");
        } catch (MessagingException me) {
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Error!&nbsp;</strong>El correo no se pudo enviar</div>");  //Si se produce un error
        }

        seleccionarTodos();
    }

    public void buscar() throws SNMPExceptions, SQLException {
        LinkedList<EncabezadoSolicitud> uu = new LinkedList<>();

        EncabezadoSolicitudDB encaDB = new EncabezadoSolicitudDB();
        try {
            if (!getBuscarFiltro().equals("")) {
                if (!encaDB.FiltrarEncabezado(buscarFiltro).isEmpty()) {
                    listaEnca = encaDB.SeleccionarTodosporCoordinador(usuario.getId());
                    uu = encaDB.FiltrarEncabezado(buscarFiltro);
                    LinkedList<EncabezadoSolicitud> listafiltro = new LinkedList<>();
                    listafiltro = uu;

                    for (EncabezadoSolicitud lu : listafiltro) {
                        boolean ind = false;
                        for (EncabezadoSolicitud encabe : listaEnca) {
                            if (encabe.getId_Encabezado() == lu.getId_Encabezado()) {
                                ind = true;
                            }
                        }

                        if (ind == false) {
                            uu.remove(lu);
                        }
                    }

                    if (!uu.isEmpty()) {
                        listaEnca = uu;
                    } else {
                        seleccionarTodos();
                        setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
                    }

                    setMensajeFiltro("");
                } else {
                    seleccionarTodos();
                    setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
                }
            } else {
                seleccionarTodos();
                setMensajeFiltro("");
            }
        } catch (Exception e) {
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <strong>Ups!&nbsp;</strong>Hubo un error al buscar la Solicitud...¡Intentelo de nuevo!</div>");
        }
    }

    public void llenarCalendario() {
        eventModel = new DefaultScheduleModel();
        eventModel.addEvent(new DefaultScheduleEvent("Evento: despedida", fecha, fecha));
    }

    public void seleccionarTodos() throws SNMPExceptions, SQLException {
        EncabezadoSolicitudDB enca = new EncabezadoSolicitudDB();
        if (!enca.SeleccionarTodosporCoordinador(usuario.getId()).isEmpty()) {
            listaEnca = enca.SeleccionarTodosporCoordinador(usuario.getId());

        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public UsuarioMante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMante usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public EncabezadoSolicitud getEnca() {
        return enca;
    }

    public void setEnca(EncabezadoSolicitud enca) {
        this.enca = enca;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrefuncionario() {
        return nombrefuncionario;
    }

    public void setNombrefuncionario(String nombrefuncionario) {
        this.nombrefuncionario = nombrefuncionario;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LinkedList<Agenda> getListaAgenda() {
        return listaAgenda;
    }

    public void setListaAgenda(LinkedList<Agenda> listaAgenda) {
        this.listaAgenda = listaAgenda;
    }

    public String getAgendas() {
        return Agendas;
    }

    public void setAgendas(String Agendas) {
        this.Agendas = Agendas;
    }

    public LinkedList<EncabezadoSolicitud> getListaEnca() {
        return listaEnca;
    }

    public void setListaEnca(LinkedList<EncabezadoSolicitud> listaEnca) {
        this.listaEnca = listaEnca;
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

    public void setBuscarFiltro(String buscarFiltro) {
        this.buscarFiltro = buscarFiltro;
    }

    public LinkedList<EncabezadoSolicitud> getListaEncabe() {
        return listaEncabe;
    }

    public void setListaEncabe(LinkedList<EncabezadoSolicitud> listaEncabe) {
        this.listaEncabe = listaEncabe;
    }

    public String getMsjRechazo() {
        return MsjRechazo;
    }

    public void setMsjRechazo(String MsjRechazo) {
        this.MsjRechazo = MsjRechazo;
    }
}
