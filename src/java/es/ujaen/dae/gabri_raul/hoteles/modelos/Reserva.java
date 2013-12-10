package es.ujaen.dae.gabri_raul.hoteles.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase utilizada para crear reservas
 *
 * @author Raúl & Gabri
 */
@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date fechaEntrada;
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    private int simples;
    private int dobles;
    private int triples;

    @ManyToOne
    @XmlElement
    private Usuario usuario;

    @ManyToOne
    @XmlElement
    private Hotel hotel;

    /**
     * Constructor por defecto. Construye un objeto de tipo reserva sin
     * inicializar los atributos.
     */
    public Reserva() {

    }
    
    /**
     * Constructor con todos los parametros para inicializar todos los atributos.
     * @param fechaEntrada
     * @param fechaSalida
     * @param simples
     * @param dobles
     * @param usuario
     * @param triples
     * @param hotel
     */
    public Reserva(Date fechaEntrada, Date fechaSalida, int simples, int dobles, int triples, Usuario usuario, Hotel hotel) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.simples = simples;
        this.dobles = dobles;
        this.triples = triples;
        this.usuario = usuario;
        this.hotel = hotel;
    }

    /**
     * Obtiene el valor del atributo fechaEntrada de tipo Date.
     *
     * @return the fechaEntrada
     */
    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    /**
     * Introduce el valor del parámetro en el atributo fechaEntrada. Requiere un
     * tipo Date.
     *
     * @param fechaEntrada
     */
    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    /**
     * Obtiene el valor del atributo fechaSalida de tipo Date.
     *
     * @return the fechaSalida
     */
    public Date getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Introduce el valor del parámetro en el atributo fechaSalida. Requiere un
     * tipo Date.
     *
     * @param fechaSalida the fechaSalida to set
     */
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * Obtiene el valor del atributo habitaciones simples de tipo int.
     *
     * @return the simples
     */
    public int getSimples() {
        return simples;
    }

    /**
     * Introduce el valor del parámetro en el atributo simples. Requiere un tipo
     * int.
     *
     * @param simples the simples to set
     */
    public void setSimples(int simples) {
        this.simples = simples;
    }

    /**
     * Obtiene el valor del atributo habitaciones dobles de tipo int.
     *
     * @return the dobles
     */
    public int getDobles() {
        return dobles;
    }

    /**
     * Introduce el valor del parámetro en el atributo dobles. Requiere un tipo
     * int.
     *
     * @param dobles the dobles to set
     */
    public void setDobles(int dobles) {
        this.dobles = dobles;
    }

    /**
     * Obtiene el valor del atributo habitaciones triples de tipo int.
     *
     * @return the triples
     */
    public int getTriples() {
        return triples;
    }

    /**
     * Introduce el valor del parámetro en el atributo triples. Requiere un tipo
     * int.
     *
     * @param triples the triples to set
     */
    public void setTriples(int triples) {
        this.triples = triples;
    }

    /**
     * Obtiene el valor del atributo usuario de tipo Usuario.
     *
     * @return the usuario
     */
    @XmlTransient
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Introduce el valor del parámetro en el atributo usuario. Requiere un tipo
     * Usuario.
     *
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el valor del atributo hotel de tipo Hotel.
     *
     * @return the hotel
     */
    @XmlTransient
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Introduce el valor del parámetro en el atributo hotel. Requiere un tipo
     * Hotel.
     *
     * @param hotel the hotel to set
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Obtiene el valor del atributo id de tipo int.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Introduce el valor del parámetro en el atributo id. Requiere un tipo int.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Comprueba si las fechas coinciden o están dentro del rango.
     *
     * @param fEntrada
     * @param fSalida
     * @return True si coinciden las fechas o esta dentro del espacio de tiempo,
     * false en caso contrario.
     */
    public Boolean coincideCon(Date fEntrada, Date fSalida) {

        //Si fSalida es anterior a fechaEntrada, no coinciden
        if (fSalida.before(fechaEntrada)) {
            return false;
        } //Si fEntrada es posterior a fechaSalida, no coinciden
        else if (fEntrada.after(fechaSalida)) {
            return false;
        }
        //En otro caso sí coinciden
        return true;
    }

    /**
     * Devuelve un boolean. True si alguno de los atributos no ha sido
     * inicializado. False si todos los atributos han sido inicializados.
     *
     * @return boolean
     */
    public Boolean hasEmptyFields() {
        if (fechaSalida.toString().isEmpty()) {
            return true;
        } else if (fechaEntrada.toString().isEmpty()) {
            return true;
        } else if (simples < 0) {
            return true;
        } else if (dobles < 0) {
            return true;
        } else if (triples < 0) {
            return true;
        } else if (usuario.hasEmptyFields()) {
            return true;
        } else {
            return hotel.hasEmptyFields();
        }
    }

    /**
     * Devuelve un boolean. True si alguno de los atributos principales no ha
     * sido inicializado. False si todos los atributos han sido inicializados.
     *
     * @return boolean
     */
    public Boolean hasEmptyMainFields() {
        if (fechaEntrada.toString().isEmpty()) {
            return true;
        } else if (usuario.hasEmptyFields()) {
            return true;
        } else {
            return hotel.hasEmptyFields();
        }
    }

    public void validar() {

    }

}
