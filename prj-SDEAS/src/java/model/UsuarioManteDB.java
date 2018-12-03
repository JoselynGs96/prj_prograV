/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.AccesoDatos;
import dao.SNMPExceptions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author ujose
 */
public class UsuarioManteDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;  

    private LinkedList<UsuarioMante> listaD = new LinkedList<UsuarioMante>();
    
    public UsuarioManteDB (Connection conn) {
        accesoDatos = new AccesoDatos();    
        accesoDatos.setDbConn(conn);
    }
    
    public UsuarioManteDB() {
        super();
    }
    
    public void actulizar(UsuarioMante usu) throws SNMPExceptions, SQLException { 
        String strSQL = "";   
        int estado = 0;
        try {  
            UsuarioMante usuario = new UsuarioMante(); 
            usuario = usu;             
            
            if(usuario.Log_Activo.equals("Activo")){
                estado = 1;
            }
            
            strSQL = "UPDATE Usuario SET "
                    + "[Nombre] ='" +  usuario.Nombre 
                    + "', [Apellido1]='" +usuario.Apellido1
                    + "', [Apellido2] ='" +usuario.Apellido2
                    + "', [FechaNacimiento] ='" + new java.sql.Date(usuario.getFechaNacimiento().getTime())
                    + "', [Correo] ='"+usuario.Correo
                    + "', [Id_EstadoAcceso] ='"+usuario.EstadoAcceso.getId()
                    + "', [Log_Activo] ='"+estado
                    + "', Id_Edita = '"+usuario.Id_Edita
                    + "', FechaEdita = '"+new java.sql.Date(usuario.getFechaEdita().getTime())
                    + "' where [Id_Usuario]= '"+usuario.Id+"';";
                    
            accesoDatos.ejecutaSQL(strSQL/*, sqlBitacora*/);  
        } catch (SQLException e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage(), e.getErrorCode());         
        }catch (Exception e) { 
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,  
                                    e.getMessage()); 
        } finally { 
         
        } 
    } 
    
    public  UsuarioMante SeleccionarPorId(int id) throws SNMPExceptions, SQLException {
      String select = "";
      ResultSet rsPA = null;
      UsuarioMante usuario = null;
      TipoIdentificacionDB tipoIdentificacion = new TipoIdentificacionDB();
      EstadoAccesoDB estadoAcceso = new EstadoAccesoDB();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario WHERE Id_Usuario = " +id;
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id = rsPA.getInt("Id_Usuario");
                        TipoIdentificacion TipoIdentificacion = tipoIdentificacion.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                        String Nombre = rsPA.getString("Nombre");
                        String Apellido1 = rsPA.getString("Apellido1");
                        String Apellido2 = rsPA.getString("Apellido2");
                        Date FechaNacimiento = rsPA.getDate("FechaNacimiento");
                        String Correo = rsPA.getString("Correo");
                        EstadoAcceso EstadoAcceso = estadoAcceso.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                        String Log_Activo = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                        usuario = new UsuarioMante(Id, TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, EstadoAcceso, Log_Activo);
                        usuario.setEdad(calculaEdad(FechaNacimiento));
                      }
              
            rsPA.close();
              
          } catch (SQLException e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage(), e.getErrorCode());
          }catch (Exception e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage());
          } finally {
              
          }
         
          return usuario;
      }
    
    public  LinkedList<UsuarioMante> SeleccionarTodos() throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      TipoIdentificacionDB tipoIdentificacion = new TipoIdentificacionDB();
      EstadoAccesoDB estadoAcceso = new EstadoAccesoDB();
      LinkedList<UsuarioMante> listaUsuarioMante= new LinkedList<UsuarioMante>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id = rsPA.getInt("Id_Usuario");
                        TipoIdentificacion TipoIdentificacion = tipoIdentificacion.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                        String Nombre = rsPA.getString("Nombre");
                        String Apellido1 = rsPA.getString("Apellido1");
                        String Apellido2 = rsPA.getString("Apellido2");
                        Date FechaNacimiento = rsPA.getDate("FechaNacimiento");
                        String Correo = rsPA.getString("Correo");
                        EstadoAcceso EstadoAcceso = estadoAcceso.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                        String Log_Activo = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                        UsuarioMante usu = new UsuarioMante(Id, TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, EstadoAcceso, Log_Activo);
                        usu.setEdad(calculaEdad(FechaNacimiento));
                        listaUsuarioMante.add(usu);
                      }
              
            rsPA.close();
              
          } catch (SQLException e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage(), e.getErrorCode());
          }catch (Exception e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage());
          } finally {
              
          }
         
          return listaUsuarioMante;
      }
    
    public  LinkedList<UsuarioMante> FiltrarUsuario(String fil) throws SNMPExceptions, 
            SQLException {
      String select = "";
      ResultSet rsPA = null;
      String filtro = fil;
      String valor = "-1";
      TipoIdentificacionDB tipoIdentificacion = new TipoIdentificacionDB();
      EstadoAccesoDB estadoAcceso = new EstadoAccesoDB();
      
      if(filtro.equalsIgnoreCase("Activo")||filtro.equalsIgnoreCase("A")||filtro.equalsIgnoreCase("Ac")||filtro.equalsIgnoreCase("Act")||filtro.equalsIgnoreCase("Acti")||filtro.equalsIgnoreCase("Activ")){
          valor = "1";
      }else{
          if(filtro.equalsIgnoreCase("Inactivo")||filtro.equalsIgnoreCase("I")||filtro.equalsIgnoreCase("In")||filtro.equalsIgnoreCase("Ina")||filtro.equalsIgnoreCase("Inac")||filtro.equalsIgnoreCase("Inact")||filtro.equalsIgnoreCase("Inacti")||filtro.equalsIgnoreCase("Inactiv")){
              valor = "0";
          }
      }
      LinkedList<UsuarioMante> listaUsuarioMante = new LinkedList<UsuarioMante>();
          
          try {
              AccesoDatos accesoDatos = new AccesoDatos();  
              
                   select = 
                      "SELECT Id_Usuario, Id_TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, Id_EstadoAcceso, Log_Activo from Usuario WHERE"
                           + " ( Cast(Id_Usuario as nvarchar(20)) LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Nombre LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Apellido1 LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Apellido2 LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Correo LIKE '%' + '" + filtro + "' + '%')"
                           + "OR ( Cast(Log_Activo as nvarchar(5)) LIKE '%' + '" + valor + "' + '%')";
              
                      rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
             
                      while (rsPA.next()) {

                        int Id = rsPA.getInt("Id_Usuario");
                        TipoIdentificacion TipoIdentificacion = tipoIdentificacion.SeleccionarPorId(rsPA.getInt("Id_TipoIdentificacion"));
                        String Nombre = rsPA.getString("Nombre");
                        String Apellido1 = rsPA.getString("Apellido1");
                        String Apellido2 = rsPA.getString("Apellido2");
                        Date FechaNacimiento = rsPA.getDate("FechaNacimiento");
                        String Correo = rsPA.getString("Correo");
                        EstadoAcceso EstadoAcceso = estadoAcceso.SeleccionarPorId(rsPA.getInt("Id_EstadoAcceso"));
                        String Log_Activo = rsPA.getInt("Log_Activo") == 1 ? "Activo" : "Inactivo";
                        UsuarioMante usu = new UsuarioMante(Id, TipoIdentificacion, Nombre, Apellido1, Apellido2, FechaNacimiento, Correo, EstadoAcceso, Log_Activo);
                        listaUsuarioMante.add(usu);
                      }
              
            rsPA.close();
              
          } catch (SQLException e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage(), e.getErrorCode());
          }catch (Exception e) {
              throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, 
                                      e.getMessage());
          } finally {
              
          }
         
          return listaUsuarioMante;
      }

    public String calculaEdad(Date fecha){
       String f = fecha.toString();
       DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNac = LocalDate.parse(f, fmt);
            LocalDate ahora = LocalDate.now();

            Period periodo = Period.between(fechaNac, ahora);
            return  periodo.getYears() + " años , " + periodo.getMonths() + " meses con " + periodo.getDays() +" días";
    }
}
