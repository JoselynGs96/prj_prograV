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
    String codigoSeguridad = "";
    RolUsuario rolUsuario = null;
    int id_rol = 0;
    String msjError = "";
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
        
        if(id_rol == 0){
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Debe seleccionar el rol con que quiere ingresar</div>");
            return false;
        }else{
            setMsjError("");
        }
        
        return respuesta;
    }
    
    
    public boolean validaUsuario() throws SNMPExceptions, SQLException, IOException{
         boolean respuesta = false;
        if(validarCampos()){
        if(uDB.UsuarioExistente(Integer.parseInt(cedula))==null){
            setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Código de usuario no pertenece a ningún usuario registrado</div>");
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
                   if(UsuarioDB.InicioSeccion(Integer.parseInt(cedula), getContrasenna())!=null){
                        if(puDB.VerificarRol(Integer.parseInt(cedula), rolUsuario.getId_RolUsuario()) != null){
                            
                            if(uDB.UsuarioExistente(Integer.parseInt(cedula)).getPrimeraVez() == 1){
                                 FacesContext.getCurrentInstance().getExternalContext().redirect("CodigoVerificacion.xhtml");
                            }else{
                                if(uDB.UsuarioExistente(Integer.parseInt(cedula)).getLog_Activo().equals("Activo")){
                                    if(rolUsuario.getId_RolUsuario()==1){
                                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario", cedula);
                                        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/Mantenimiento.xhtml");
                                    } else{
                                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Id_Usuario", cedula);
                                        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
                                    }
                                }else{
                                    setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, pero esta Inactivo en el sistema. Contacta al Coordinador a cargo</div>");
                                    return false;
                                }
                            }
                        }else{
                             setMsjError("<div class='alert alert-danger alert-dismissible fade in' ><strong>Ups!&nbsp;</strong>Lo sentimos, pero no eres parte de DEAS como el rol indicado </div>");
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
    
    
    
    
    
}
