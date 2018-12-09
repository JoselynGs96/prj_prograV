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
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Barrio;
import model.BarrioDB;
import model.Canton;
import model.CantonDB;
import model.Direccion;
import model.DireccionDB;
import model.Distrito;
import model.DistritoDB;
import model.EstadoAcceso;
import model.EstadoAccesoDB;
import model.ProgramaDB;
import model.ProgramaUsuario;
import model.ProgramaUsuarioDB;
import model.Provincia;
import model.ProvinciaDB;
import model.Telefono;
import model.TelefonoDB;
import model.TipoTelefono;
import model.TipoTelefonoDB;
import model.UsuarioDB;
import model.UsuarioMante;
import model.UsuarioManteDB;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ujose
 */
@Named(value = "usuariosPorProgranaBean")
@SessionScoped
public class UsuariosPorProgranaBean implements Serializable {
    ProgramaDB proDB = new ProgramaDB();
    ProgramaUsuarioDB prousuDB = new ProgramaUsuarioDB();
    TelefonoDB telDB = new TelefonoDB();
    DireccionDB dirDB = new DireccionDB();
    EstadoAccesoDB estAccDB = new EstadoAccesoDB();
    ProvinciaDB pro = new ProvinciaDB();
    CantonDB can = new CantonDB();
    DistritoDB dis = new DistritoDB();
    BarrioDB barr = new BarrioDB();
    TipoTelefonoDB tel = new TipoTelefonoDB();
    UsuarioManteDB usuarioDB = new UsuarioManteDB();
    
    Date fecha = new Date();
    String buscarFiltro = "";
    UsuarioMante usuario = null;
    ProgramaUsuario programaUsuario = null;
    LinkedList<Direccion> listaDirecciones = new LinkedList<Direccion>();
    LinkedList<Telefono> listaTelefonos = new LinkedList<Telefono>();
    LinkedList<ProgramaUsuario> listaProusu = new LinkedList<ProgramaUsuario>();
    LinkedList<UsuarioMante> listaUsuario = new LinkedList<UsuarioMante>();
    LinkedList<EstadoAcceso> listaEstadoAcceso = new LinkedList<EstadoAcceso>();
    LinkedList<Provincia> listaPro = new LinkedList<Provincia>();
    LinkedList<Canton> listaCan = new LinkedList<Canton>();
    LinkedList<Distrito> listaDis = new LinkedList<Distrito>();
    LinkedList<Barrio> listaBarrio = new LinkedList<Barrio>();
   
    /*Para el modal*/
    UsuarioMante usuarioModal = null;
    LinkedList<ProgramaUsuario> listaProusuModal = new LinkedList<ProgramaUsuario>();
    String telefonos = "";
    String direcciones = "";
    String programas = "";
 
    
    /*Para validaciones*/
    String mensajeGuardar = "";
    String mensajeFiltro = "";
    String mensajeError = "";
    
    
    String Id = "";
    String TipoIdentificacion = "";
    String Nombre = "";
    String Apellido1 = "";
    String Apellido2 = "";
    Date FechaNacimiento = null;
    String Correo = "";
    int estadoAcceso = 0;
    String estadoUsuario = "";
    
    int idDireccion=0;
    int Id_Provincia=0;
    int Id_Canton=0;
    int Id_Distrito=0;
    int id_Barrio=0;
    String otrasSennas="";
    
    String msjRechazo = "";
    UsuarioMante denegado = null;
    
    
    UsuarioMante usuario4 = null;
    int IdRegistra = 0;
    int IdEdita = 0;

    LinkedList<TipoTelefono> listaTipoTelefonos = new LinkedList<>();
    String idTelefono = "";
    int idTipoTelefono = 1;
    String numero = "";
    
    public UsuariosPorProgranaBean() throws SNMPExceptions, SQLException{
        ObtenerDatosSesion datos = new ObtenerDatosSesion();
       
        UsuarioManteDB usuDB = new UsuarioManteDB();
        datos.consultarSesion();
        if(!datos.getId_Usuario().equals("")){
            usuario4 = usuDB.SeleccionarPorId(Integer.parseInt(datos.getId_Usuario()));
            IdRegistra = usuario4.getId();
            IdEdita = usuario4.getId();
        }else{
            usuario4 = usuDB.SeleccionarPorId(116390998);
            IdRegistra = usuario4.getId();
            IdEdita = usuario4.getId();
        }
        
        
        seleccionarTodos();
    }
    
    public void nuevoBoton(){
        usuario = null;
        setId("");
        setTipoIdentificacion("");
        setNombre("");
        setApellido1("");
        setApellido2("");
        FechaNacimiento = null;
        idDireccion = 0;
        Id_Provincia = 0;
        Id_Canton = 0;
        Id_Distrito = 0;
        id_Barrio = 0;
        otrasSennas = "";
        idTelefono = "";
        idTipoTelefono = 1;
        numero = "";
        Correo = "";
        
        if(!listaTipoTelefonos.isEmpty()){
            listaTipoTelefonos.clear();
        }
        if(!listaDirecciones.isEmpty()){
            listaDirecciones.clear();
        }
        if(!listaTelefonos.isEmpty()){
           listaTelefonos.clear();
        }
        if(!listaTipoTelefonos.isEmpty()){
            listaDirecciones.clear();
        }
        if(!listaProusuModal.isEmpty()){
            listaProusuModal.clear();
        }
        if(!listaProusu.isEmpty()){
            listaProusu.clear();
        }
        
        setMensajeError("");
        setMensajeFiltro("");
        setMensajeGuardar("");
        setEstadoAcceso(0);
        setEstadoUsuario("");
        setBuscarFiltro("");
        setTelefonos("");
        setDirecciones("");
        setProgramas("");
        usuarioModal = null;
    }
    
    public void recibeDenegado(int id) throws SNMPExceptions, SQLException{
        denegado = new UsuarioManteDB().SeleccionarPorId(id);
        PrimeFaces.current().executeScript("abrirModalDenegar();");
    }
   
    public void botonDenegar() throws SNMPExceptions, SQLException{
        setMensajeFiltro("");
        String remitente = "joselyn.grana2@gmail.com";  //Para la dirección nomcuenta@gmail.com
        String destinatario =  denegado.getCorreo(); //A quien le quieres escribir.
        String asunto = "Plataforma SDEAS";
        String cuerpo = "Estimado(a): "+ denegado.getNombreCompleto() +", Se le notifica que su solicitud de acceso a SDEAS fue Denegada:"
                + "\nDenegada por el siguiente motivo:\n" + getMsjRechazo();
        
        
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
            UsuarioManteDB ddd = new UsuarioManteDB();
            denegado.getEstadoAcceso().setId(2);
            ddd.actulizar(denegado);
            setMensajeFiltro("<div class='alert alert-success alert-dismissible fade in' >  <strong>Exito!&nbsp;</strong>Correo enviado satisfactoriamente</div>");
        }
        catch (MessagingException me) {
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Error!&nbsp;</strong>El correo no se pudo enviar</div>");  //Si se produce un error
        }
        
        seleccionarTodos();
    }
    
    public void botonAceptar( int id) throws SNMPExceptions, SQLException{
         setMensajeFiltro("");
        String contra = generarContrasenna();
        int Codigo = generarCodigo();
        UsuarioMante usu = new UsuarioManteDB().SeleccionarPorId(id);
        String remitente = "joselyn.grana2@gmail.com";  //Para la dirección nomcuenta@gmail.com
        String destinatario =  usu.getCorreo(); //A quien le quieres escribir.
        String asunto = "Aceptación de SDEAS";
        String cuerpo = "Estimado(a): "+ usu.getNombreCompleto() +",Se le notifica que su solicitud de acceso a SDEAS fue Aceptada:"
                + "\nCódigo de usuario: " + usu.getId()
                + "\nContraseña: " + contra
                + "\nCódigo de seguridad: " + Codigo
                + "\n\n Dirijase a la página de Inicio de Sesión e ingrese los datos proporcionados anteriormente para finalizar el procesos de autoregistro";
        
        
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
            UsuarioManteDB ddd = new UsuarioManteDB();
            usu.setContrasenna(contra);
            usu.setCodigo(Codigo);
            usu.getEstadoAcceso().setId(1);
            usu.setLog_Activo("Activo");
            ddd.actulizarContraCodi(usu);
            setMensajeFiltro("<div class='alert alert-success alert-dismissible fade in' >  <strong>Exito!&nbsp;</strong>Correo enviado satisfactoriamente</div>");
        }
        catch (MessagingException me) {
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Error!&nbsp;</strong>El correo no se pudo enviar</div>");  //Si se produce un error  //Si se produce un error
        }
        seleccionarTodos();
    }
    
    public String generarContrasenna(){
        int largo = (int)Math.floor(Math.random()*(12-8+1)+8); 
        String contra = "";
        char [] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int charsLength = chars.length;
        Random random = new Random();
        for (int i = 0; i < largo; i++) {
            contra += chars[random.nextInt(charsLength)];
        }
        return contra;
    }
    
    public int generarCodigo(){
        return (int)Math.floor(Math.random()*(999999-100000+1)+100000);
    }
    
    public void editarBoton(int id) throws SNMPExceptions, SQLException{
        setMensajeError("");
        listaTipoTelefonos = tel.SeleccionarTodos();
        llenarCombosDireccion();
        try{
            usuario = usuarioDB.SeleccionarPorId(id);
            setTipoIdentificacion(usuario.getTipoIdentificacion().getDsc_TipoTelefono());
            setId(usuario.getId()+"");
            setNombre(usuario.getNombre());
            setApellido1(usuario.getApellido1());
            setApellido2(usuario.getApellido2());
            setFechaNacimiento(usuario.getFechaNacimiento());
            setEstadoUsuario(usuario.getLog_Activo());
            
            setCorreo(usuario.getCorreo());
            
            listaTelefonos = telDB.SeleccionarTodos(usuario.getId());
            
            listaDirecciones = dirDB.SeleccionarPorUsuario(usuario.getId());
            
            listaProusu = prousuDB.SeleccionarPorId(id);
            
            
       }catch(Exception e){
           setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' >  <strong>Error!&nbsp;</strong>Hubo un error al buscar el usuario...¡Intentelo de nuevo!</div>");
       }
        
    }
    
    public void editarDireccion(String id) throws SNMPExceptions, SQLException{
        for (Direccion d : listaDirecciones) {
            if(d.getId_direccion().endsWith(id)){
                setIdDireccion(Integer.parseInt(d.getId_direccion()));
                    setId_Provincia(d.getProvincia().getId_Provincia());
                    setId_Canton(d.getCanton().getId_Canton());
                    setId_Distrito(d.getDistrito().getId_Distrito());
                    setId_Barrio(d.getBarrio().getId_Barrio());
                    setOtrasSennas(d.getOtras_sennas());
            }
        }
        
    }
    
    public void limpiarCamposDireccion(){
        setIdDireccion(0);
        setId_Provincia(0);
        setId_Canton(0);
        setId_Distrito(0);
        setId_Barrio(0);
        setOtrasSennas("");
    }
    
    public void guardarDireccion() throws SNMPExceptions, SQLException{
       boolean ind = false;
        if(ValidaDireccion()){
            for (Direccion direccion : listaDirecciones) {
                if(direccion.getId_direccion().equals(Integer.toString(idDireccion))){
                    direccion.setId_Edita(116390998);
                    direccion.setFechaEdita(fecha);
                    direccion.setProvincia(pro.SeleccionarPorId(Id_Provincia));
                    direccion.setCanton(can.SeleccionarPorId(Id_Canton, Id_Provincia));
                    direccion.setDistrito(dis.SeleccionarPorId(Id_Distrito, Id_Canton, Id_Provincia));
                    direccion.setBarrio(barr.SeleccionarPorId(Id_Provincia, Id_Canton, Id_Distrito, id_Barrio));
                    direccion.setOtras_sennas(getOtrasSennas());
                    ind = true;
                }
            }  

            if(ind == false){
                Direccion dir = new Direccion();
                dir.setProvincia(pro.SeleccionarPorId(Id_Provincia));
                dir.setCanton(can.SeleccionarPorId(Id_Canton, Id_Provincia));
                dir.setDistrito(dis.SeleccionarPorId(Id_Distrito, Id_Canton, Id_Provincia));
                dir.setBarrio(barr.SeleccionarPorId(Id_Provincia, Id_Canton, Id_Distrito, id_Barrio));
                dir.setUsuario(new UsuarioDB().SeleccionarPorId(Integer.parseInt(Id)));
                dir.setOtras_sennas(otrasSennas);
                dir.setId_Registra(116390998);
                dir.setFechaRegistra(fecha);
                dir.setId_Edita(116390998);
                dir.setFechaEdita(fecha);
                listaDirecciones.add(dir);
            }
            limpiarCamposDireccion();
       }
       
    }
    
    public boolean ValidaDireccion(){
        boolean indicador = true;
        
        if(getId().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
        
         if(getId_Provincia()==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe seleccionar la Provincia</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getId_Canton() ==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe seleccionar el Cantón</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getId_Distrito()==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe seleccionar el Distrito</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getId_Barrio() ==0){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Error!&nbsp;</strong>Debe seleccionar el Barrio</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(getOtrasSennas().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Error!&nbsp;</strong>Debe llenar el campo Otras señas</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         return indicador;
    }
    
    public void editarTelefono(String id) throws SNMPExceptions, SQLException{
         for(Telefono telefono : listaTelefonos) {
                if(telefono.getId_Telefono().equals(id)){
                    setIdTelefono(telefono.getId_Telefono());
                    setIdTipoTelefono(telefono.getId_TipoTelefono().getId_Telefono());
                    setNumero(telefono.getNumero());
                }
         }
        
    }
    
    public boolean ValidarTelefono(){
       boolean indicador;
       
       if(getId().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
       
        if(getIdTipoTelefono() == 1){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe seleccionar el Tipo de Teléfono</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
        
        if(getNumero().equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe llenar el campo Numero</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
        
        try{
            Integer.parseInt(getNumero());
            indicador = true;
        }catch(Exception e){
            setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>En el campo Numero debe ingresar datos numéricos</div>");
            return false;
        }
        return indicador;
    }
    
    public void limpiarCamposTelefono(){
        setIdTelefono("");
        setIdTipoTelefono(1);
        setNumero("");
    }
    
    public void guardarTelefono() throws SNMPExceptions, SQLException{
        boolean ind = false;
        if(ValidarTelefono()){
            for (Telefono telefono : listaTelefonos) {
                if(telefono.getId_Telefono().equals(getIdTelefono())){
                    telefono.setId_Edita(IdEdita);
                    telefono.setFechaEdita(fecha);
                    telefono.setId_TipoTelefono(tel.SeleccionarPorId(getIdTipoTelefono()));
                    telefono.setNumero(getNumero());
                    ind = true;
                }
            }
            
            if(ind == false){
                Telefono telefono = new Telefono();
                telefono.setNumero(getNumero());
                telefono.setId_Usuario(new UsuarioDB().SeleccionarPorId(Integer.parseInt(getId())));
                telefono.setId_TipoTelefono(new TipoTelefonoDB().SeleccionarPorId(idTipoTelefono));
                telefono.setId_Registra(IdRegistra);
                telefono.setFechaRegistra(fecha);
                telefono.setId_Edita(IdEdita);
                telefono.setFechaEdita(fecha);
                listaTelefonos.add(telefono);
            }
            
            limpiarCamposTelefono();
        }
        
        
    }
   
    public void verMas(int i) throws SNMPExceptions, SQLException{
        direcciones = "";
        telefonos = "";
        programas = "";
        listaDirecciones.clear();
        listaTelefonos.clear();
        listaProusuModal.clear();
        
        usuarioModal = usuarioDB.SeleccionarPorId(i);
        
        listaDirecciones = dirDB.SeleccionarPorUsuario(i);
         for (int j = 0; j < listaDirecciones.size(); j++) {
             Direccion dir = listaDirecciones.get(j);
             direcciones += "Dirección #"+(j+1)+": "+ dir.getOtras_sennas() +" ("+dir.getProvincia().getDsc_Provincia()+", "+dir.getCanton().getDsc_Canton()+", "+dir.getDistrito().getDsc_Distrito()+","+dir.getBarrio().getDsc_Barrio()+")\n";
         }
        
        listaTelefonos = telDB.SeleccionarTodos(i);
        for (int j = 0; j < listaTelefonos.size(); j++) {
             Telefono tel = listaTelefonos.get(j);
             telefonos += tel.getId_TipoTelefono().getDsc_TipoTelefono()+": "+ tel.getNumero()+"\n";
         }
        
        listaProusuModal = prousuDB.SeleccionarPorId(i);
         for (int j = 0; j < listaProusuModal.size(); j++) {
             ProgramaUsuario ps = listaProusuModal.get(j);
             programas +=  ps.getPrograma().getNombre() +", "+(ps.getRolUsuario().getId_RolUsuario()==3?ps.getRolUsuario().getDsc_RolUsuario()+" : "+ps.getFuncionario().toString(): ps.getRolUsuario().getDsc_RolUsuario()) +", "+ps.getEstado()+"\n";
         }
        
        
        PrimeFaces.current().executeScript("abrirModal();");
     }
   
    public void buscar() throws SNMPExceptions, SQLException{
        try{
        if(!getBuscarFiltro().equals("")){
            if(!usuarioDB.FiltrarUsuario(buscarFiltro).isEmpty()){
                listaUsuario = usuarioDB.FiltrarUsuario(buscarFiltro);
                 setMensajeFiltro("");
            }else{
                listaUsuario = usuarioDB.SeleccionarTodos();
                setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' ><strong>Error!&nbsp;</strong>No se encontraron registros con el dato proporcionado</div>");
            }
        }else{
            seleccionarTodos();
            setMensajeFiltro("");
        }
        }catch(Exception e){
            setMensajeFiltro("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Hubo un error al buscar el usuario...¡Intentelo de nuevo!</div>");
        }
     }
    
    public void cambiarEstado(int idPro, int idT) throws SNMPExceptions, SQLException{
        for (int i = 0; i < listaProusu.size(); i++) {
            
            ProgramaUsuario pro = listaProusu.get(i);
            
            if(pro.getPrograma().getId() == idPro && pro.getRolUsuario().getId_RolUsuario() == idT){
                if(pro.getEstado().equals("Activo")){
                    pro.setEstado("Inactivo");
                }else{
                    pro.setEstado("Activo");
                }
                pro.setId_Edita(IdEdita);
                pro.setFechaEdita(fecha);
            }
        }
    }
   
    public void guardarBoton() throws SNMPExceptions, SQLException{
        
            if(Validaciones()){
                usuario.setNombre(Nombre);
                usuario.setApellido1(Apellido1);
                usuario.setApellido2(Apellido2);
                usuario.setFechaNacimiento(FechaNacimiento);
                usuario.setCorreo(Correo);
                usuario.setLog_Activo(this.estadoUsuario);
                usuario.setId_Edita(IdEdita);
                usuario.setFechaEdita(fecha);
                usuarioDB.actulizar(usuario);
                
                for (Direccion listaDireccione : listaDirecciones) {
                    if(!listaDireccione.getId_direccion().equals("")){
                        dirDB.actulizar(listaDireccione);
                    }else{
                        dirDB.registrar(listaDireccione);
                    }
                }
                
                for (Telefono lisTelefon : listaTelefonos) {
                    if(!lisTelefon.getId_Telefono().equals("")){
                        telDB.actulizar(lisTelefon);
                    }else{
                        telDB.registrar(lisTelefon);
                    }
                }
                
                for (ProgramaUsuario listaProus : listaProusu) {
                     prousuDB.actulizar(listaProus);
                }
                
                
                setMensajeGuardar("<div class='alert alert-success alert-dismissible fade in' > <strong>Exitoso!&nbsp;</strong>¡Usuario actualizado con éxito!</div> ");
            }
            seleccionarTodos();
        
    }
    
    public boolean Validaciones(){
        boolean indicador = true;
         
         if(Id.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Debe seleccionar el registro a actualizar</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Nombre.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Nombre del usuario requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Apellido1.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Primer apellido requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Apellido2.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Segundo apellido requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(FechaNacimiento == null){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Fecha de Nacimiento requerida</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         if(Correo.equals("")){
             setMensajeError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>Correo requerido</div>");
             return indicador = false;
         }else{
             setMensajeError("");
             indicador = true;
         }
         
         return indicador;
    }
    
    public void seleccionarTodos() throws SNMPExceptions, SQLException{
        if(!usuarioDB.SeleccionarTodos().isEmpty()){
            listaUsuario.clear();
            listaUsuario = usuarioDB.SeleccionarTodos();
        }
    }
    
    public void llenarCombosDireccion() throws SNMPExceptions, SQLException {

        if (!pro.SeleccionarTodos().isEmpty()) {
            listaPro = pro.SeleccionarTodos();
            Id_Provincia = pro.SeleccionarTodos().element().getId_Provincia();
        }
        if (!can.SeleccionarTodos(Id_Provincia).isEmpty()) {
            listaCan = can.SeleccionarTodos(Id_Provincia);
            Id_Canton = can.SeleccionarTodos(Id_Provincia).element().getId_Canton();
        }
        if (!dis.SeleccionarTodos(Id_Provincia, Id_Canton).isEmpty()) {
            listaDis = dis.SeleccionarTodos(Id_Provincia, Id_Canton);
            Id_Distrito = dis.SeleccionarTodos(Id_Provincia, Id_Canton).element().getId_Distrito();
        }
        if (!barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito).isEmpty()) {
            listaBarrio = barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito);
            id_Barrio = barr.SeleccionarTodos(Id_Provincia, Id_Canton, Id_Distrito).element().getId_Barrio();
        }
       
    }
    
    public UsuarioMante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMante usuario) {
        this.usuario = usuario;
    }

    public ProgramaUsuario getProgramaUsuario() {
        return programaUsuario;
    }

    public void setProgramaUsuario(ProgramaUsuario programaUsuario) {
        this.programaUsuario = programaUsuario;
    }

    public LinkedList<Direccion> getListaDirecciones() {
        return listaDirecciones;
    }

    public void setListaDirecciones(LinkedList<Direccion> listaDirecciones) {
        this.listaDirecciones = listaDirecciones;
    }

    public LinkedList<Telefono> getListaTelefonos() {
        return listaTelefonos;
    }

    public void setListaTelefonos(LinkedList<Telefono> listaTelefonos) {
        this.listaTelefonos = listaTelefonos;
    }

    public LinkedList<UsuarioMante> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(LinkedList<UsuarioMante> listaUsuario) {
        this.listaUsuario = listaUsuario;
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
        return estAccDB.SeleccionarTodos();
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

    public String getProgramas() {
        return programas;
    }

    public void setProgramas(String programas) {
        this.programas = programas;
    }

    public UsuarioMante getUsuarioModal() {
        return usuarioModal;
    }

    public void setUsuarioModal(UsuarioMante usuarioModal) {
        this.usuarioModal = usuarioModal;
    }

    public LinkedList<ProgramaUsuario> getListaProusuModal() {
        return listaProusuModal;
    }

    public void setListaProusuModal(LinkedList<ProgramaUsuario> listaProusuModal) {
        this.listaProusuModal = listaProusuModal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTipoIdentificacion() {
        return TipoIdentificacion;
    }

    public void setTipoIdentificacion(String TipoIdentificacion) {
        this.TipoIdentificacion = TipoIdentificacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String Apellido1) {
        this.Apellido1 = Apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String Apellido2) {
        this.Apellido2 = Apellido2;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public ProgramaDB getProDB() {
        return proDB;
    }

    public void setProDB(ProgramaDB proDB) {
        this.proDB = proDB;
    }

    public ProgramaUsuarioDB getProusuDB() {
        return prousuDB;
    }

    public void setProusuDB(ProgramaUsuarioDB prousuDB) {
        this.prousuDB = prousuDB;
    }

    public TelefonoDB getTelDB() {
        return telDB;
    }

    public void setTelDB(TelefonoDB telDB) {
        this.telDB = telDB;
    }

    public DireccionDB getDirDB() {
        return dirDB;
    }

    public void setDirDB(DireccionDB dirDB) {
        this.dirDB = dirDB;
    }

    public EstadoAccesoDB getEstAccDB() {
        return estAccDB;
    }

    public void setEstAccDB(EstadoAccesoDB estAccDB) {
        this.estAccDB = estAccDB;
    }

    public ProvinciaDB getPro() {
        return pro;
    }

    public void setPro(ProvinciaDB pro) {
        this.pro = pro;
    }

    public CantonDB getCan() {
        return can;
    }

    public void setCan(CantonDB can) {
        this.can = can;
    }

    public DistritoDB getDis() {
        return dis;
    }

    public void setDis(DistritoDB dis) {
        this.dis = dis;
    }

    public BarrioDB getBarr() {
        return barr;
    }

    public void setBarr(BarrioDB barr) {
        this.barr = barr;
    }

    public TipoTelefonoDB getTel() {
        return tel;
    }

    public void setTel(TipoTelefonoDB tel) {
        this.tel = tel;
    }

    public UsuarioManteDB getUsuarioDB() {
        return usuarioDB;
    }

    public void setUsuarioDB(UsuarioManteDB usuarioDB) {
        this.usuarioDB = usuarioDB;
    }

     public LinkedList<Provincia> getListaPro() throws SNMPExceptions, SQLException {
        return pro.SeleccionarTodos();
    }

    public void setListaPro(LinkedList<Provincia> listaPro) {
        this.listaPro = listaPro;
    }

    public LinkedList<Canton> getListaCan() throws SNMPExceptions, SQLException {
        return can.SeleccionarTodos(this.getId_Provincia());
    }

    public void setListaCan(LinkedList<Canton> listaCan) {
        this.listaCan = listaCan;
    }

    public LinkedList<Distrito> getListaDis() throws SNMPExceptions, SQLException {
        return dis.SeleccionarTodos(this.getId_Provincia(), this.getId_Canton());
    }

    public void setListaDis(LinkedList<Distrito> listaDis) {
        this.listaDis = listaDis;
    }

    public LinkedList<Barrio> getListaBarrio() throws SNMPExceptions, SQLException {
        return barr.SeleccionarTodos(this.getId_Provincia(), this.getId_Canton(), this.getId_Distrito());
    }

    public void setListaBarrio(LinkedList<Barrio> listaBarrio) {
        this.listaBarrio = listaBarrio;
    }
    

    public int getId_Provincia() {
        return Id_Provincia;
    }

    public void setId_Provincia(int Id_Provincia) {
        this.Id_Provincia = Id_Provincia;
    }

    public int getId_Canton() {
        return Id_Canton;
    }

    public void setId_Canton(int Id_Canton) {
        this.Id_Canton = Id_Canton;
    }

    public int getId_Distrito() {
        return Id_Distrito;
    }

    public void setId_Distrito(int Id_Distrito) {
        this.Id_Distrito = Id_Distrito;
    }

    public int getId_Barrio() {
        return id_Barrio;
    }

    public void setId_Barrio(int id_Barrio) {
        this.id_Barrio = id_Barrio;
    }

    public String getOtrasSennas() {
        return otrasSennas;
    }

    public void setOtrasSennas(String otrasSennas) {
        this.otrasSennas = otrasSennas;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
   
    public LinkedList<TipoTelefono> getListaTipoTelefonos() {
        return listaTipoTelefonos;
    }

    public void setListaTipoTelefonos(LinkedList<TipoTelefono> listaTipoTelefonos) {
        this.listaTipoTelefonos = listaTipoTelefonos;
    }

    public String getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(String idTelefono) {
        this.idTelefono = idTelefono;
    }

    public int getIdTipoTelefono() {
        return idTipoTelefono;
    }

    public void setIdTipoTelefono(int idTipoTelefono) {
        this.idTipoTelefono = idTipoTelefono;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMsjRechazo() {
        return msjRechazo;
    }

    public void setMsjRechazo(String msjRechazo) {
        this.msjRechazo = msjRechazo;
    }

    public UsuarioMante getDenegado() {
        return denegado;
    }

    public void setDenegado(UsuarioMante denegado) {
        this.denegado = denegado;
    }

    public UsuarioMante getUsuario4() {
        return usuario4;
    }

    public void setUsuario4(UsuarioMante usuario4) {
        this.usuario4 = usuario4;
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
