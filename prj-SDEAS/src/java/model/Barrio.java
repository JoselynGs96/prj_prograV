
package model;

/**
 *
 * @author Fabi
 */
public class Barrio {
    int Id_Barrio;
    String Dsc_Barrio;
    Provincia Provincia;
    Canton Canton;
    Distrito Distrito;
    
     public Barrio() {
    }

    public Barrio(int Id_Barrio, String Dsc_Barrio, Provincia Provincia, Canton Canton, Distrito Distrito) {
        this.Id_Barrio = Id_Barrio;
        this.Dsc_Barrio = Dsc_Barrio;
        this.Provincia = Provincia;
        this.Canton = Canton;
        this.Distrito = Distrito;
    }
     
     
    public int getId_Barrio() {
        return Id_Barrio;
    }

    public void setId_Barrio(int Id_Barrio) {
        this.Id_Barrio = Id_Barrio;
    }

    public String getDsc_Barrio() {
        return Dsc_Barrio;
    }

    public void setDsc_Barrio(String Dsc_Barrio) {
        this.Dsc_Barrio = Dsc_Barrio;
    }

    public Provincia getProvincia() {
        return Provincia;
    }

    public void setProvincia(Provincia Provincia) {
        this.Provincia = Provincia;
    }

    public Canton getCanton() {
        return Canton;
    }

    public void setCanton(Canton Canton) {
        this.Canton = Canton;
    }

    public Distrito getDistrito() {
        return Distrito;
    }

    public void setDistrito(Distrito Distrito) {
        this.Distrito = Distrito;
    }

    
}
