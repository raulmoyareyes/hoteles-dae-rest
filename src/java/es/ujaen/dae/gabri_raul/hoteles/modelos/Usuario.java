package es.ujaen.dae.gabri_raul.hoteles.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase utilizada para crear usuarios
 *
 * @author Raúl & Gabri
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    private String dni;
    private String nombre;
    private String direccion;

    /**
     * Construye un objeto de tipo usuario pasandole todos los parámetros
     * necesarios para inicializar todos los atributos.
     *
     * @param _nombre
     * @param _direccion
     * @param _dni
     */
    public Usuario(String _nombre, String _direccion, String _dni) {
        nombre = _nombre;
        direccion = _direccion;
        dni = _dni;
    }

    /**
     * Constructor por defecto. Construye un objeto de tipo usuario sin
     * inicializar los atributos.
     */
    public Usuario() {

    }

    /**
     * Obtiene el valor del atributo nombre de tipo String.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Introduce el valor del parámetro en el atributo nombre. Requiere un tipo
     * String.
     *
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor del atributo direccion de tipo String.
     *
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Introduce el valor del parámetro en el atributo direccion. Requiere un
     * tipo String.
     *
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el valor del atributo dni de tipo String.
     *
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * Introduce el valor del parámetro en el atributo dni. Requiere un tipo
     * String.
     *
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
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
        } else if (direccion.isEmpty()) {
            return true;
        } else {
            return dni.isEmpty();
        }
    }
    
    public void validar(){
        
    }
}
