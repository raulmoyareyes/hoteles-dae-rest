package es.ujaen.dae.gabri_raul.hoteles.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase utilizada para crear operadores
 *
 * @author Raúl & Gabri
 */
@Entity
@Table(name = "operadores")
public class Operador implements Serializable {

    @Id
    private String cif;
    private String nombre;
    private String direccionSocial;

    /**
     * Constructor por defecto. Construye un objeto de tipo operador sin
     * inicializar los atributos.
     */
    public Operador() {

    }

    /**
     * Construye un objeto de tipo operador pasandole todos los parámetros
     * necesarios para inicializar todos los atributos.
     *
     * @param nombre
     * @param cif
     * @param direccionSocial
     */
    public Operador(String nombre, String cif, String direccionSocial) {
        this.nombre = nombre;
        this.cif = cif;
        this.direccionSocial = direccionSocial;
    }

    /**
     * Obtiene el valor del atributo nombre de tipo String.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Introduce el valor del parámetro en el atributo nombre. Requiere un tipo
     * String.
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor del atributo cif de tipo String.
     *
     * @return cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * Introduce el valor del parámetro en el atributo cif. Requiere un tipo
     * String.
     *
     * @param cif
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Obtiene el valor del atributo direccionSocial de tipo String.
     *
     * @return direccionSocial
     */
    public String getDireccionSocial() {
        return direccionSocial;
    }

    /**
     * Introduce el valor del parámetro en el atributo direccionSocial. Requiere
     * un tipo String.
     *
     * @param direccionSocial
     */
    public void setDireccionSocial(String direccionSocial) {
        this.direccionSocial = direccionSocial;
    }

    /**
     * Devuelve un boolean. True si alguno de los atributos no ha sido
     * inicializado. False si todos los atributos han sido inicializados.
     *
     * @return boolean
     */
    public Boolean hasEmptyFields() {
        if (nombre.isEmpty()) {
            return true;
        } else if (cif.isEmpty()) {
            return true;
        } else {
            return direccionSocial.isEmpty();
        }
    }
    
    public void validar(){
        
    }

}
