package es.ujaen.dae.gabri_raul.hoteles.modelos;

import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaNoPosible;
import java.io.Serializable;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase utilizada para crear hoteles
 *
 * @author Raúl &  Gabri
 */
@Entity
@Table(name = "hoteles")
public class Hotel implements Serializable {

    @Id
    private String nombre;
    private String direccion;
    private String ciudad;
    private int numSimples;
    private int numDobles;
    private int numTriples;
    private float precioSimples;
    private float precioDobles;
    private float precioTriples;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey
    @XmlTransient
    private Map<Integer, Reserva> reservas;

    /**
     * Constructor por defecto.
     */
    public Hotel() {
        reservas = new HashMap();
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param nombre
     * @param direccion
     * @param ciudad
     * @param numSimples
     * @param numDobles
     * @param numTriples
     * @param precioSimples
     * @param precioDobles
     * @param precioTriples
     */
    public Hotel(String nombre, String direccion, String ciudad, int numSimples, int numDobles, int numTriples, float precioSimples, float precioDobles, float precioTriples) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.numSimples = numSimples;
        this.numDobles = numDobles;
        this.numTriples = numTriples;
        this.precioSimples = precioSimples;
        this.precioDobles = precioDobles;
        this.precioTriples = precioTriples;
        reservas = new HashMap();
    }

    /**
     * Constructor de copia.
     *
     * @param hotel
     */
    public Hotel(final Hotel hotel) {
        this.nombre = hotel.getNombre();
        this.direccion = hotel.getDireccion();
        this.ciudad = hotel.getCiudad();
        this.numSimples = hotel.getNumSimples();
        this.numDobles = hotel.getNumDobles();
        this.numTriples = hotel.getNumTriples();
        this.precioSimples = hotel.getPrecioSimples();
        this.precioDobles = hotel.getPrecioDobles();
        this.precioTriples = hotel.getPrecioTriples();
    }

    /**
     * @return El nombre del hotel
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the numSimples
     */
    public int getNumSimples() {
        return numSimples;
    }

    /**
     * @param numSimples the numSimples to set
     */
    public void setNumSimples(int numSimples) {
        this.numSimples = numSimples;
    }

    /**
     * @return the numDobles
     */
    public int getNumDobles() {
        return numDobles;
    }

    /**
     * @param numDobles the numDobles to set
     */
    public void setNumDobles(int numDobles) {
        this.numDobles = numDobles;
    }

    /**
     * @return the numTriples
     */
    public int getNumTriples() {
        return numTriples;
    }

    /**
     * @param numTriples the numTriples to set
     */
    public void setNumTriples(int numTriples) {
        this.numTriples = numTriples;
    }

    /**
     * @return the precioSimples
     */
    public float getPrecioSimples() {
        return precioSimples;
    }

    /**
     * @param precioSimples the precioSimples to set
     */
    public void setPrecioSimples(float precioSimples) {
        this.precioSimples = precioSimples;
    }

    /**
     * @return the precioDobles
     */
    public float getPrecioDobles() {
        return precioDobles;
    }

    /**
     * @param precioDobles the precioDobles to set
     */
    public void setPrecioDobles(float precioDobles) {
        this.precioDobles = precioDobles;
    }

    /**
     * @return the precioTriples
     */
    public float getPrecioTriples() {
        return precioTriples;
    }

    /**
     * @param precioTriples the precioTriples to set
     */
    public void setPrecioTriples(float precioTriples) {
        this.precioTriples = precioTriples;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return Devuelve un mapa con las reservas de este hotel.
     */
    @XmlTransient
    public Map<Integer, Reserva> getReservas() {
        return reservas;
    }

    /**
     * Inserta reservas como el listado de reservas del hotel.
     *
     * @param reservas Mapa de reservas.
     */
    public void setReservas(Map<Integer, Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * Añade una reserva al Map de reservas del hotel.
     *
     * @param reserva Reserva que se quiere añadir a este hotel.
     * @throws ReservaNoPosible
     */
    public void crearReserva(Reserva reserva) throws ReservaNoPosible {
        if (puedeReservar(reserva)) {
            reservas.put(reserva.getId(), reserva);
        } else {
            throw new ReservaNoPosible();
        }
    }

    /**
     * Devuelve el objeto de tipo reserva que coincide con el id indicado.
     *
     * @param id
     * @return Devuelve un objeto de tipo reserva.
     */
    public Reserva obtenerReserva(int id) {
        return reservas.get(id);
    }

    /**
     * Elimina una reserva de un hotel.
     *
     * @param id
     */
    public void eliminarReserva(int id) {
        reservas.remove(id);
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
        } else if (ciudad.isEmpty()) {
            return true;
        } else if (numSimples == 0 && numDobles == 0 && numTriples == 0) {
            return true;
        } else if (numSimples > 0 && precioSimples <= 0) {
            return true;
        } else if (numDobles > 0 && precioDobles <= 0) {
            return true;
        } else {
            return numTriples > 0 && precioTriples <= 0;
        }
    }

    /**
     * Devuelve el número de habitaciones simples libres que hay en el hotel.
     *
     * @param fechaEntrada
     * @param fechaSalida
     * @return Devuelve un entero.
     */
    public int getSimplesLibres(Date fechaEntrada, Date fechaSalida) {
        int resSimples = 0;
        for (Reserva res : reservas.values()) {
            if (res.coincideCon(fechaEntrada, fechaSalida)) {
                resSimples += res.getSimples();
            }
        }
        return numSimples - resSimples;
    }

    /**
     * Devuelve el número de habitaciones dobles libres que hay en el hotel.
     *
     * @param fechaEntrada
     * @param fechaSalida
     * @return Devuelve un entero.
     */
    public int getDoblesLibres(Date fechaEntrada, Date fechaSalida) {
        int resDobles = 0;
        for (Reserva res : reservas.values()) {
            if (res.coincideCon(fechaEntrada, fechaSalida)) {
                resDobles += res.getDobles();
            }
        }
        return numDobles - resDobles;
    }

    /**
     * Devuelve el número de habitaciones triples libres que hay en el hotel.
     *
     * @param fechaEntrada
     * @param fechaSalida
     * @return Devuelve un entero.
     */
    public int getTriplesLibres(Date fechaEntrada, Date fechaSalida) {
        int resTriples = 0;
        for (Reserva res : reservas.values()) {
            if (res.coincideCon(fechaEntrada, fechaSalida)) {
                resTriples += res.getTriples();
            }
        }
        return numTriples - resTriples;
    }

    /**
     * Comprueba si se puede realizar una reserva para la fecha indicada.
     *
     * @param reserva
     * @return Devuelve true si se puede reservar, false en caso contrario.
     */
    private Boolean puedeReservar(Reserva reserva) {
        
        if(reserva.getFechaEntrada().after(reserva.getFechaSalida())){
            return false;
        }
        
        int sLibres = getSimplesLibres(reserva.getFechaEntrada(), reserva.getFechaSalida());
        int dLibres = getDoblesLibres(reserva.getFechaEntrada(), reserva.getFechaSalida());
        int tLibres = getTriplesLibres(reserva.getFechaEntrada(), reserva.getFechaSalida());

        if (sLibres < reserva.getSimples()) {
            return false;
        } else if (dLibres < reserva.getDobles()) {
            return false;
        } else {
            return tLibres >= reserva.getTriples();
        }
    }

    public void validar() {

    }
}
