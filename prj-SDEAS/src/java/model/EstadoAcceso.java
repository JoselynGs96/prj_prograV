/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ujose
 */
public class EstadoAcceso {
    int id;
   String  nombre;
   String estado;

    public EstadoAcceso() {
    }

    public EstadoAcceso(int id, String nombre, String estado) {
        this.setId(id);
        this.setNombre(nombre);
        this.setEstado(estado);
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
