/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.AccesoDatos;
import dao.SNMPExceptions;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Fabi
 */
public class DetalleDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public DetalleDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public DetalleDB() {
        super();
    }

    public void registrar(Detalle deta) throws SNMPExceptions, SQLException {

        String strSQL = "";

        try {
            Detalle detalle = deta;

            strSQL = "INSERT INTO [dbo].[DetSolicitud]\n"
                    + "           ([Id_EncSolicitud]\n"
                    + "           ,[Id_Recurso]\n"
                    + "           ,[Log_Activo])\n"
                    + "     VALUES("+detalle.getEncabezado().id_Encabezado+","+detalle.getRecurso().id+","
                   + 1 + ")";
            accesoDatos.ejecutaSQL(strSQL/*, sqlBitacora*/);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage());
        } finally {

        }

    }

}
