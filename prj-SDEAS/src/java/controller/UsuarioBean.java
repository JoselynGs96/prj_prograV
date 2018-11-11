/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Fabi
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    String cedula;
    String TipoIden;
    Date fechaNacimiento;
    String correo;
    String contrasenna;
    String codAcceso;
    String estado;
    String mensaje;
    String tipoPerfil;
    String nombre;
    String apellido1;
    String apellido2;
    String tipoTelefono;
    String NumeroTelefono;
    String Programa;
    String Provincia;
    String Canton;
    String Distrito;
    String Barrio;
    String OtrasSenas;
    String TipoFuncionario;
    int edad;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipoFuncionario() {
        return TipoFuncionario;
    }

    public void setTipoFuncionario(String TipoFuncionario) {
        this.TipoFuncionario = TipoFuncionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String NumeroTelefono) {
        this.NumeroTelefono = NumeroTelefono;
    }

    public String getPrograma() {
        return Programa;
    }

    public void setPrograma(String Programa) {
        this.Programa = Programa;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String Provincia) {
        this.Provincia = Provincia;
    }

    public String getCanton() {
        return Canton;
    }

    public void setCanton(String Canton) {
        this.Canton = Canton;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String Distrito) {
        this.Distrito = Distrito;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String Barrio) {
        this.Barrio = Barrio;
    }

    public String getOtrasSenas() {
        return OtrasSenas;
    }

    public void setOtrasSenas(String OtrasSenas) {
        this.OtrasSenas = OtrasSenas;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoIden() {
        return TipoIden;
    }

    public void setTipoIden(String TipoIden) {
        this.TipoIden = TipoIden;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getCodAcceso() {
        return codAcceso;
    }

    public void setCodAcceso(String codAcceso) {
        this.codAcceso = codAcceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public void validaIngresar() {
        if (this.getCedula().equals("")) {
            this.setMensaje("*Debe colocar el usuario.");
        } else {
            if (this.getContrasenna().equals("")) {
                this.setMensaje("*Debe colocar la contraseña");
            } else {
                if (this.getCedula().equals("207750517") && this.getContrasenna().equals("123")) {
                    this.setMensaje("Bienvenida Fabiola");
                }
            }
        }
    }

    public void validaAutoRegistro() {
        if (this.getTipoIden().equals("-Seleccionar-")) {
            this.setMensaje("*Debe colocar el tipo de identificación.");
        } else {
            if (this.getCedula().equals("")) {
                this.setMensaje("*Debe colocar el usuario.");
            } else {
                if (this.getNombre().equals("")) {
                    this.setMensaje("*Debe colocar el Nombre");
                } else {
                    if (this.getApellido1().equals("")) {
                        this.setMensaje("*Debe colocar el Primer Apellido");
                    } else {
                        if (this.getApellido2().equals("")) {
                            this.setMensaje("*Debe colocar el Segundo Apellido");
                        } else {
                            if (this.getFechaNacimiento().equals("")) {
                                this.setMensaje("*Debe colocar la fecha de nacimiento");
                            } else {
                                if (this.getTipoTelefono().equals("-Seleccionar-")) {
                                    this.setMensaje("*Debe colocar el tipo de telefono");
                                } else {
                                    if (this.getCorreo().equals("")) {
                                        this.setMensaje("*Debe colocar un correo electrónico");
                                    } else {
                                        if (this.getPrograma().equals("-Seleccionar-")) {
                                            this.setMensaje("*Debe colocar el programa al que pertenece");
                                        } else {
                                            if (this.getTipoFuncionario().equals("-Seleccionar-")) {
                                                this.setMensaje("*Debe colocar el tipo de funcionario");
                                            } else {
                                                if (this.getProvincia().equals("-Seleccionar-")) {
                                                    this.setMensaje("*Debe colocar la provincia");
                                                } else {
                                                    if (this.getCanton().equals("-Seleccionar-")) {
                                                        this.setMensaje("*Debe colocar el Cantón");
                                                    } else {
                                                        if (this.getDistrito().equals("-Seleccionar-")) {
                                                            this.setMensaje("*Debe colocar el Distrito");
                                                        } else {
                                                            if (this.getBarrio().equals("-Seleccionar-")) {
                                                                this.setMensaje("*Debe colocar el Barrio");
                                                            } else {
                                                                if (this.getOtrasSenas().equals("")) {
                                                                    this.setMensaje("*Debe colocar Otras señas de su direccion ");
                                                                } else {
                                                                    this.setMensaje("Datos completos se le enviará la solicitud a un coordinador y posterior mente le estará llegando un correo con el código de acceso");

                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

}
