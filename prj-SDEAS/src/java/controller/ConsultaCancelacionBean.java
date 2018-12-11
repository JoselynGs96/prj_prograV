/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author ujose
 */
@Named(value = "consultaCancelacionBean")
@SessionScoped
public class ConsultaCancelacionBean implements Serializable {

    /**
     * Creates a new instance of ConsultaCancelacionBean
     */
    public ConsultaCancelacionBean() {
    }
    
}
