package es.ujaen.dae.gabri_raul.hoteles.beans;

import es.ujaen.dae.gabri_raul.hoteles.dao.HotelDAO;
import es.ujaen.dae.gabri_raul.hoteles.dao.OperadorDAO;
import es.ujaen.dae.gabri_raul.hoteles.dao.ReservaDAO;
import es.ujaen.dae.gabri_raul.hoteles.dao.UsuarioDAO;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorBloquear;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorCambiarUsuario;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaNoEncontrada;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaNoPosible;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorDefault;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.*;
import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Modelo de negocio para el Operador
 *
 * @author Raúl &  Gabri
 */
@Component(value = "beanOperador")
public class BeanOperador {

    @Resource
    UsuarioDAO usuarioDAO;

    @Resource
    OperadorDAO operadorDAO;

    @Resource
    HotelDAO hotelDAO;

    @Resource
    ReservaDAO reservaDAO;

    /**
     * Dar de alta un usuario.
     *
     * @param nombre
     * @param direccion
     * @param dni
     * @throws UsuarioErrorDatos
     * @throws UsuarioErrorPersistir
     */
    public void altaUsuario(String nombre, String direccion, String dni)
            throws UsuarioErrorDatos, UsuarioErrorPersistir {
        Usuario usuario = new Usuario(nombre, direccion, dni);
        if (!usuario.hasEmptyFields()) {
            usuarioDAO.insertar(usuario);
        } else {
            throw new UsuarioErrorDatos();
        }
    }

    /**
     * Devuelve el usuario con el dni indicado.
     *
     * @param dni
     * @return Devuelve el usuario, null si no es encontrado.
     */
    public Usuario obtenerUsuario(String dni) {
        return usuarioDAO.buscar(dni);
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param dni
     * @throws UsuarioErrorEliminar
     * @throws UsuarioNoEncontrado
     * @throws ReservaErrorCambiarUsuario
     */
    public void bajaUsuario(String dni) throws UsuarioErrorEliminar, UsuarioNoEncontrado, ReservaErrorCambiarUsuario {
        Usuario u = usuarioDAO.buscar(dni);
        if (u == null) {
            throw new UsuarioNoEncontrado();
        }

        Usuario usuario = usuarioDAO.buscar("00000000A");
        if ( usuario != null) {
            reservaDAO.cambiarUsuario(usuario,u);
            usuarioDAO.eliminar(u);
        } else {
            usuario = new Usuario("UsuarioDefault", "Direccion", "00000000A");
            try {
                usuarioDAO.insertar(usuario);
                reservaDAO.cambiarUsuario(usuario,u);
                usuarioDAO.eliminar(u);
            } catch (UsuarioErrorPersistir e) {
                throw new UsuarioErrorDefault();
            }
        }

    }
    
    /**
     * Modifica un usuario del sistema.
     *
     * @param nombre
     * @param dni
     * @param direccion
     * @throws es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorActualizar
     */
    public void modificarUsuario(String nombre, String dni, String direccion) throws UsuarioErrorActualizar {
        Usuario usuario = new Usuario(nombre, direccion, dni);
        usuarioDAO.actualizar(usuario);
    }
    
    /**
     * Modifica una reserva del sistema.
     *
     * @param id
     * @param fechaEntrada
     * @param fechaSalida
     * @param simples
     * @param dobles
     * @param triples
     * @param hotel
     * @param usuario
     * @throws es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorActualizar
     */
    public void modificarReserva(int id, Date fechaEntrada, Date fechaSalida, Integer simples, Integer dobles, Integer triples, String hotel, String usuario) throws ReservaErrorActualizar {
        Reserva reserva = reservaDAO.buscar(id);
        reserva.setSimples(simples);
        reserva.setDobles(dobles);
        reserva.setTriples(triples);
        reserva.setFechaEntrada(fechaEntrada);
        reserva.setFechaSalida(fechaSalida);
        reservaDAO.actualizar(reserva);
    }

    /**
     * Devuelve un operador si coincide con los datos para el login.
     *
     * @param cif
     * @param pass
     * @return Devuelve un operador
     */
    public Operador login(String cif, String pass) {
        return operadorDAO.buscar(cif);
    }

    /**
     * Devuelve un mapa de hoteles que contengan el nombre indicado.
     *
     * @param nombre
     * @return Devuelve un mapa de hoteles, null si no es encontrado ninguno.
     */
    public Map<String, Hotel> consultaNombreHotel(String nombre) {
        return hotelDAO.consultarNombre(nombre);
    }

    /**
     * Devuelve un mapa de hoteles que contengan el nombre de la ciudad
     * indicada.
     *
     * @param ciudad
     * @return Devuelve un mapa de hoteles, null si no encuentra ningun hotel
     * con esa ciudad.
     */
    public Map<String, Hotel> consultaCiudad(String ciudad) {
        return hotelDAO.consultarCiudad(ciudad);
    }

    /**
     * Devuelve un mapa de hoteles que coincida.
     *
     * @param ciudad
     * @param fechaEntrada
     * @param fechaSalida
     * @return Devuelve un mapa de hoteles, null si no encuentra ningun hotel.
     */
    public Map<String, Hotel> consultaFecha(String ciudad, Date fechaEntrada, Date fechaSalida) {
        return hotelDAO.consultarFecha(ciudad, fechaEntrada, fechaSalida);
    }

    /**
     * True si el usuario se ha creado y guardado correctamente, false en caso
     * contrario
     *
     * @param fechaEntrada
     * @param fechaSalida
     * @param simples
     * @param dobles
     * @param triples
     * @param dni
     * @param nombreHotel
     * @throws UsuarioNoEncontrado
     * @throws HotelErrorBloquear
     * @throws HotelNoEncontrado
     * @throws ReservaErrorDatos
     * @throws HotelErrorActualizar
     * @throws ReservaNoPosible
     */
    public void crearReserva(Date fechaEntrada, Date fechaSalida, Integer simples, Integer dobles, Integer triples, String dni, String nombreHotel)
            throws UsuarioNoEncontrado, HotelErrorBloquear, HotelNoEncontrado, ReservaErrorDatos, ReservaNoPosible, HotelErrorActualizar {

        Usuario usuario = usuarioDAO.buscar(dni);
        if (usuario == null) {
            throw new UsuarioNoEncontrado();
        }

        Hotel hotel = hotelDAO.buscar(nombreHotel);
        if (hotel == null) {
            throw new HotelNoEncontrado();
        }
        hotelDAO.bloquear(hotel);

        Reserva reserva = new Reserva(fechaEntrada, fechaSalida, simples, dobles, triples, usuario, hotel);
        if (!reserva.hasEmptyFields()) {
            hotel.crearReserva(reserva);
        } else {
            throw new ReservaErrorDatos();
        }
        hotelDAO.actualizar(hotel);
    }

    /**
     * Devuelve la reserva con el id indicado.
     *
     * @param id
     * @return Devuelve la reserva, null si no es encontrada.
     */
    public Reserva obtenerReserva(int id) {
        return reservaDAO.buscar(id);
    }

    /**
     * Elimina una reserva del sistema.
     *
     * @param id
     * @throws HotelErrorActualizar
     * @throws ReservaNoEncontrada
     */
    public void eliminarReserva(int id) throws HotelErrorActualizar, ReservaNoEncontrada {
        Reserva r = reservaDAO.buscar(id);
        if (r == null) {
            throw new ReservaNoEncontrada();
        }
        Hotel hotel = r.getHotel();
        hotel.eliminarReserva(id);
        hotelDAO.actualizar(hotel);
    }

    /**
     * Devuelve un mapa con la lista de usuarios.
     *
     * @return Devuelve un mapa con la lista de usuarios
     */
    public Map<String, Usuario> listaUsuarios() {
        return usuarioDAO.listar();
    }

    public Map<Integer, Reserva> listadoReservas() {
        return reservaDAO.listar();
    }
}
