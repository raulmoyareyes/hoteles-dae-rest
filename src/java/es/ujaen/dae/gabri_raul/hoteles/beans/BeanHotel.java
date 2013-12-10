package es.ujaen.dae.gabri_raul.hoteles.beans;

import es.ujaen.dae.gabri_raul.hoteles.dao.HotelDAO;
import es.ujaen.dae.gabri_raul.hoteles.dao.UsuarioDAO;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorBloquear;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorReserva;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaNoPosible;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorDefault;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Reserva;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Usuario;
import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Modelo de negocio para el Hotel
 *
 * @author Raúl &  Gabri
 */
@Component(value = "beanHotel")
public class BeanHotel {

    @Resource
    HotelDAO hotelDAO;

    @Resource
    UsuarioDAO usuarioDAO;

    /**
     * @param nombreHotel Nombre del hotel para el que se consulta.
     * @return Devuelve un Map con las reservas de este hotel
     */
    public Map<Integer, Reserva> consultarReservas(String nombreHotel) {
        Hotel h = hotelDAO.buscar(nombreHotel);
        if (h != null) {
            return h.getReservas();
        } else {
            return new HashMap();
        }
    }

    /**
     * Crea una reserva con un usuario por defecto
     *
     * @param fechaEntrada
     * @param fechaSalida
     * @param simples
     * @param dobles
     * @param nombreHotel Hotel para el que se realiza la reserva.
     * @param triples
     * @throws ReservaNoPosible
     * @throws HotelNoEncontrado
     * @throws HotelErrorReserva
     * @throws HotelErrorBloquear
     * @throws HotelErrorActualizar
     * @throws UsuarioErrorDefault
     */
    public void crearReserva(Date fechaEntrada, Date fechaSalida, Integer simples, Integer dobles, Integer triples, String nombreHotel)
            throws ReservaNoPosible, HotelNoEncontrado, HotelErrorReserva, HotelErrorBloquear, HotelErrorActualizar {

        Usuario usuario;
        if (usuarioDAO.buscar("00000000A") != null) {
            usuario = usuarioDAO.buscar("00000000A");
        } else {
            usuario = new Usuario("UsuarioDefault", "Direccion", "00000000A");
            try {
                usuarioDAO.insertar(usuario);
            } catch (UsuarioErrorPersistir e) {
                throw new UsuarioErrorDefault();
            }
        }

        Hotel h = hotelDAO.buscar(nombreHotel);
        if (h == null) {
            throw new HotelNoEncontrado();
        }

        Reserva reserva = new Reserva(fechaEntrada, fechaSalida, simples, dobles, triples, usuario, h);
        hotelDAO.bloquear(h);
        h.crearReserva(reserva);
        hotelDAO.actualizar(h);
    }
}
