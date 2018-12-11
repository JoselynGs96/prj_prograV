/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SNMPExceptions;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.faces.context.FacesContext;
import model.EnumFuncionario;
import model.ProgramaUsuarioDB;
import model.RolUsuario;
import model.RolUsuarioDB;
import model.UsuarioDB;

/**
 *
 * @author ujose
 */
@Named(value = "inicioSesionBean")
@SessionScoped
public class InicioSesionBean implements Serializable {
    
    UsuarioDB uDB = new UsuarioDB();
    ProgramaUsuarioDB puDB = new ProgramaUsuarioDB();
    
    String cedula = "";
    String contrasenna = "";
    RolUsuario rolUsuario = null;
    int id_rol = 0;
    String codigoSeguridad = "";
    String nuevaContrasenna = "";
    String verificarContrasenna = "";
    String msjError = "";
    String msjError2 = "";
    LinkedList<RolUsuario> listaRolUsuario = new LinkedList<RolUsuario>();
    /**
     * Creates a new instance of InicioSesionBean
     */
    public InicioSesionBean() throws SNMPExceptions, SQLException {
     RolUsuarioDB rol = new RolUsuarioDB();
        if (!rol.SeleccionarTodos().isEmpty()) {
            listaRolUsuario = rol.SeleccionarTodos();
            id_rol = rol.SeleccionarTodos().element().getId_RolUsuario();
        }
    }
    
    
    public boolean validarCampos(){
        boolean respuesta = true;
        if (this.getCedula().equals("")) {
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Código de usuario requerido</div>");
            return false;
        } else {
            setMsjError("");
        }
        
        try{
            Integer.parseInt(getCedula());
            respuesta = true;
        }catch(Exception e){
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>El valor en el campo Código de usuario no es un dato válido</div>");
            return false;
        }
        
        if(contrasenna.equals("")){
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Debe ingresar la contraseña</div>");
            return false;
        }else{
            setMsjError("");
        }
        
        if(id_rol == 1){
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Debe seleccionar el rol con que quiere ingresar</div>");
            return false;
        }else{
            setMsjError("");
        }
        
        return respuesta;
    }
    
    
    public boolean validaUsuario() throws SNMPExceptions, SQLException, IOException{
        boolean respuesta = false;
        rolUsuario = new RolUsuarioDB().SeleccionarPorId(id_rol);
        if(validarCampos()){
        if(uDB.UsuarioExistente(Integer.parseInt(cedula))==null){
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>El código de usuario no pertenece a ningún usuario registrado</div>");
            return false;
        }else{
            if(uDB.UsuarioExistente(Integer.parseInt(cedula)).getEstadoAcceso().getId()==2){
                setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, pero su solicitud de acceso al Sistema fue rechazada. El motivo de rechazo fue enviado a su correo</div>");
                return false;
            }else{
                if(uDB.UsuarioExistente(Integer.parseInt(cedula)).getEstadoAcceso().getId()==3){
                     setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, pero su solicitud de acceso al Sistema aún se encuentra en Espera</div>");
                     return false;
                }else{
                   if(uDB.InicioSesion(Integer.parseInt(cedula), getContrasenna())!=0){
                        if(puDB.VerificarRol(Integer.parseInt(cedula), rolUsuario.getId_RolUsuario()) != null){
                            
                            if(uDB.UsuarioExistente(Integer.parseInt(cedula)).getPrimeraVez() == 1){
                                 FacesContext.getCurrentInstance().getExternalContext().redirect("faces/CodigoVerificacion.xhtml");
                            }else{
                                if(uDB.UsuarioExistente(Integer.parseInt(cedula)).getLog_Activo().equals("Activo")){
                                    if(rolUsuario.getId_RolUsuario()== 2){
                                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario", cedula);
                                        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/Mantenimiento.xhtml");
                                    } else{
                                        if(rolUsuario.getId_RolUsuario() == 3){
                                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario", cedula);
                                            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
                                        }
                                    }
                                }else{
                                    setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, pero usted se encuentra Inactivo en el sistema. Contacte al Coordinador a cargo</div>");
                                    return false;
                                }
                            }
                        }else{
                             setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, pero no eres parte de DEAS con el rol indicado </div>");
                             return false;
                        }
                   }else{
                       setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Contraseña incorrecta</div>");
                       return false;
                   }   
                }
                setMsjError("");
            }
            setMsjError("");
        }
        }
        return respuesta;
    }

    
    /*Página Codigo Verificación*/
    
    public boolean validaCamposCodigoVer(){
        boolean respuesta = true;
        if (this.getCodigoSeguridad().equals("")) {
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Código de seguridad requerido</div>");
            return false;
        } else {
            setMsjError2("");
        }
        
        try{
            Integer.parseInt(getCodigoSeguridad());
            respuesta = true;
        }catch(Exception e){
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' > <strong>Error!&nbsp;</strong>El valor en el campo Código de seguridad no es un dato válido</div>");
            return false;
        }
        
        if (this.getNuevaContrasenna().equals("")) {
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Nueva contraseña requerida</div>");
            return false;
        } else {
            setMsjError2("");
        }
        
        if (this.getVerificarContrasenna().equals("")) {
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Verificación de Nueva contraseña requerida</div>");
            return false;
        } else {
            setMsjError2("");
        }
        
        return respuesta;
    }
   
    public boolean validaCodigoSeguridad() throws SNMPExceptions, SQLException{
        String cod = uDB.UsuarioExistente(Integer.parseInt(getCedula())).getCodAcceso();
        if(!cod.equals(getCodigoSeguridad())){
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Código de seguridad incorrecto</div>");
            return false;
        }
        return true;
    }
    
    public boolean validarContrasennaNueva(){
        int cantNum = 0;
        int cantLetMay = 0;
        int cantLetMin = 0;
        int cantEspec = 0;
        
        boolean indi = true;
        if(getNuevaContrasenna().length()<8||getNuevaContrasenna().length()>12){
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Contraseña no válida. Debe tener un largo mínimo de 8 caracteres y un máximo de 12</div>");
            return false;
        }else{
            setMsjError2("");
        }
        
        for (int i = 0; i < getNuevaContrasenna().length(); i++) {
            char caracter = getNuevaContrasenna().charAt(i);
            if(String.valueOf(caracter).matches("[0-9]")){
                cantNum++;
            }else{
                if(!String.valueOf(caracter).matches("^[A-Za-z\\u00C0-\\u017F]*$")){
                    cantEspec++;
                }else{
                    if(String.valueOf(caracter).matches("[a-z]")){
                        cantLetMin++;
                    }else{
                        if(String.valueOf(caracter).matches("[A-Z]")){
                            cantLetMay++;
                        }
                    }
                }
            }
        }
        
        if(cantEspec == 0 && cantNum != 0 && cantLetMay != 0 && cantLetMin != 0){
             setMsjError2("");
        }else{
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Contraseña no válida. </div>");
            return false;
        }
        
        return indi;
    }
     
    
    public boolean validaAmbasContrasennas(){
        if(getNuevaContrasenna().equals(getVerificarContrasenna())){
            setMsjError2("");
        }else{
            setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Las contraseñas no coinciden</div>");
            return false;
        }
        
        return true;
    }
    
    
    public void ingresar() throws SNMPExceptions, SQLException{
        
        if(validaCamposCodigoVer() && validaCodigoSeguridad() && validarContrasennaNueva() && validaAmbasContrasennas()){
            try{
                uDB.IngresarContrasenna(Integer.parseInt(cedula), getNuevaContrasenna());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario", cedula);
                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
            }catch(Exception e){
                  setMsjError2("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, Hubo un error al ingresar... Vuelva a Intentarlo</div>");
           
            }
        }
    }
    
    
    public UsuarioDB getuDB() {
        return uDB;
    }

    public void setuDB(UsuarioDB uDB) {
        this.uDB = uDB;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getMsjError() {
        return msjError;
    }

    public void setMsjError(String msjError) {
        this.msjError = msjError;
    }

    public ProgramaUsuarioDB getPuDB() {
        return puDB;
    }

    public void setPuDB(ProgramaUsuarioDB puDB) {
        this.puDB = puDB;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public LinkedList<RolUsuario> getListaRolUsuario() {
        return listaRolUsuario;
    }

    public void setListaRolUsuario(LinkedList<RolUsuario> listaRolUsuario) {
        this.listaRolUsuario = listaRolUsuario;
    }

    public String getMsjError2() {
        return msjError2;
    }

    public void setMsjError2(String msjError2) {
        this.msjError2 = msjError2;
    }

    public String getNuevaContrasenna() {
        return nuevaContrasenna;
    }

    public void setNuevaContrasenna(String nuevaContrasenna) {
        this.nuevaContrasenna = nuevaContrasenna;
    }

    public String getVerificarContrasenna() {
        return verificarContrasenna;
    }

    public void setVerificarContrasenna(String verificarContrasenna) {
        this.verificarContrasenna = verificarContrasenna;
    }
    
    
    
    
    
    
    
}
