package es.ujaen.dae.gabri_raul.hoteles.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase utilizada para crear administradores
 *
 * @author Raúl & Gabri
 */
@Entity
@Table(name = "administradores")
public class Administrador implements Serializable {

    @Id
    private String id;

    /**
     * Constructor por defecto. Construye un objeto de tipo administrador sin
     * inicializar los atributos.
     */
    public Administrador() {

    }

    /**
     * Construye un objeto de tipo operador pasandole todos los parámetros
     * necesarios para inicializar todos los atributos.
     *
     * @param id
     */
    public Administrador(String id) {
        this.id = id;
    }

    /**
     * Obtiene el valor del atributo id de tipo String.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Introduce el valor del parámetro en el atributo id. Requiere un tipo
     * String.
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Devuelve un boolean. True si alguno de los atributos no ha sido
     * inicializado. False si todos los atributos han sido inicializados.
     *
     * @return boolean
     */
    public Boolean hasEmptyFields() {
        return id.isEmpty();
    }
    
    public void validar(){
        
    }

}
