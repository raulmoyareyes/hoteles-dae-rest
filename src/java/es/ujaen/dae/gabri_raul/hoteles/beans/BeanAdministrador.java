package es.ujaen.dae.gabri_raul.hoteles.beans;

import es.ujaen.dae.gabri_raul.hoteles.dao.AdministradorDAO;
import es.ujaen.dae.gabri_raul.hoteles.dao.HotelDAO;
import es.ujaen.dae.gabri_raul.hoteles.dao.OperadorDAO;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Administrador;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Operador;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Modelo de negocio para el Administrador
 *
 * @author Raúl &  Gabri
 */
@Component(value = "beanAdministrador")
public class BeanAdministrador {

    @Resource
    HotelDAO hotelDAO;

    @Resource
    OperadorDAO operadorDAO;
    
    @Resource
    AdministradorDAO administradorDAO;

    /**
     * Dar de alta un hotel.
     *
     * @param hotel
     * @throws HotelErrorPersistir
     * @throws HotelErrorDatos
     */
    public void altaHotel(Hotel hotel) throws HotelErrorPersistir, HotelErrorDatos {
        if (!hotel.hasEmptyFields()) {
            hotelDAO.insertar(hotel);
        } else {
            throw new HotelErrorDatos();
        }
    }

    /**
     * Devuelve el hotel con el nombre indicado.
     *
     * @param nombreHotel
     * @return Devuelve el hotel, null si no es encontrado.
     */
    public Hotel obtenerHotel(String nombreHotel) {
        return hotelDAO.buscar(nombreHotel);
    }

    /**
     * Elimina un hotel del sistema.
     *
     * @param nombreHotel
     * @throws HotelErrorEliminar
     * @throws HotelNoEncontrado
     */
    public void bajaHotel(String nombreHotel) throws HotelErrorEliminar, HotelNoEncontrado {
        Hotel h = hotelDAO.buscar(nombreHotel);
        if (h == null) {
            throw new HotelNoEncontrado();
        }
        hotelDAO.eliminar(h);
    }

    /**
     * Dar de alta un operador.
     *
     * @param operador
     * @throws OperadorErrorPersistir
     * @throws OperadorErrorDatos
     */
    public void altaOperador(Operador operador) throws OperadorErrorPersistir, OperadorErrorDatos {
        if (!operador.hasEmptyFields()) {
            operadorDAO.insertar(operador);
        } else {
            throw new OperadorErrorDatos();
        }
    }
    
    /**
     * Dar de alta un operador.
     *
     * @param nombre
     * @param direccionSocial
     * @param cif
     * @throws OperadorErrorPersistir
     * @throws OperadorErrorDatos
     */
    public void altaOperador(String nombre, String direccionSocial, String cif) throws OperadorErrorPersistir, OperadorErrorDatos {
        Operador operador = new Operador();
        operador.setNombre(nombre);
        operador.setDireccionSocial(direccionSocial);
        operador.setCif(cif);
        if (!operador.hasEmptyFields()) {
            operadorDAO.insertar(operador);
        } else {
            throw new OperadorErrorDatos();
        }
    }

    /**
     * Devuelve el operador con el cif indicado.
     *
     * @param cif
     * @return Devuelve el operador, null si no es encontrado.
     */
    public Operador obtenerOperador(String cif) {
        return operadorDAO.buscar(cif);
    }

    /**
     * Elimina un operador del sistema.
     *
     * @param cif
     * @throws OperadorNoEncontrado
     * @throws OperadorErrorEliminar
     */
    public void bajaOperador(String cif) throws OperadorNoEncontrado, OperadorErrorEliminar {
        Operador o = operadorDAO.buscar(cif);
        if (o == null) {
            throw new OperadorNoEncontrado();
        }
        operadorDAO.eliminar(o);
    }
    
    /**
     * Modifica un operador del sistema.
     *
     * @param operador
     * @throws es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorActualizar
     */
    public void modificarOperador(Operador operador) throws OperadorErrorActualizar {
        operadorDAO.actualizar(operador);
    }
    
    /**
     * Modifica un hotel del sistema.
     *
     * @param hotel
     * @throws es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar
     */
    public void modificarHotel(Hotel hotel) throws HotelErrorActualizar {
        hotelDAO.actualizar(hotel);
    }
    
    /**
     * Devuelve un administrador si coincide con los datos para el login.
     *
     * @param id
     * @param pass
     * @return Devuelve un administrador
     */
    public Administrador login(String id, String pass) {
        return administradorDAO.buscar(id);
    }
    
    /**
     * Devuelve un mapa con la lista de operadores.
     *
     * @return Devuelve un mapa con la lista de operadores
     */
    public Map<String, Operador> listaOperadores() {
        return operadorDAO.listar();
    }
    
    /**
     * Devuelve un mapa con la lista de hoteles.
     *
     * @return Devuelve un mapa con la lista de hoteles
     */
    public Map<String, Hotel> listaHoteles() {
        return hotelDAO.listar();
    }

}
