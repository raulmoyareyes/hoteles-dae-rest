package es.ujaen.dae.gabri_raul.hoteles.dao;

import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorBloquear;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import es.ujaen.dae.gabri_raul.hoteles.anotacionaop.CatchExceptionAOP;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

/**
 * Clase utilizada para trabajar con la BBDD
 *
 * @author Raúl & Gabri
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HotelDAO {

    @PersistenceContext
    private EntityManager em;

    public HotelDAO() {

    }

    /**
     * @param nombre Clave para buscar el hotel (nombre del hotel)
     * @return Devuelve un objeto de tipo hotel o null si no lo encuentra.
     */
    @Cacheable(value = "hoteles", key = "#nombre")
    public Hotel buscar(String nombre) {
        return em.find(Hotel.class, nombre);
    }

    /**
     * Persiste un hotel en la base de datos.
     *
     * @param hotel
     * @throws HotelErrorPersistir
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorPersistir.class)
    @CatchExceptionAOP(exception = HotelErrorPersistir.class)
    public void insertar(Hotel hotel) throws HotelErrorPersistir {
        em.persist(hotel);
        em.flush();
    }

    /**
     * Actualiza en la BBDD el hotel
     *
     * @param hotel
     * @throws HotelErrorActualizar
     */
    @CacheEvict(value = "hoteles", key = "#hotel.getNombre()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar.class)
    @CatchExceptionAOP(exception = HotelErrorActualizar.class)
    public void actualizar(Hotel hotel) throws HotelErrorActualizar {
        em.merge(hotel);
        em.flush();
    }

    /**
     * Elimina de la BBDD el hotel
     *
     * @param hotel
     * @throws HotelErrorEliminar
     */
    @CacheEvict(value = "hoteles", key = "#hotel.getNombre()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorEliminar.class)
    @CatchExceptionAOP(exception = HotelErrorEliminar.class)
    public void eliminar(Hotel hotel) throws HotelErrorEliminar {
        em.remove(em.contains(hotel) ? hotel : em.merge(hotel));
        em.flush();
    }

    /**
     * Devuelve un map con hoteles que coinciden con el nombre.
     *
     * @param nombre
     * @return Devuelve un map con todos los hoteles con el nombre indicado.
     */
    public Map<String, Hotel> consultarNombre(String nombre) {
        Map<String, Hotel> hoteles = new HashMap();
        List<Hotel> lista = em.createQuery("Select h from Hotel h WHERE h.nombre like ?1", Hotel.class).setParameter(1, "%" + nombre + "%").getResultList();

        for (Hotel hotel : lista) {
            hoteles.put(hotel.getNombre(), hotel);
        }
        return hoteles;
    }

    /**
     * Devuelve un map con hoteles que coinciden con la ciudad.
     *
     * @param ciudad
     * @return Devuelve un map con todos los hoteles con la ciudad indicada.
     */
    public Map<String, Hotel> consultarCiudad(String ciudad) {
        Map<String, Hotel> hoteles = new HashMap();
        List<Hotel> lista = em.createQuery("Select h from Hotel h WHERE h.ciudad like ?1", Hotel.class).setParameter(1, "%" + ciudad + "%").getResultList();

        for (Hotel hotel : lista) {
            hoteles.put(hotel.getNombre(), hotel);
        }
        return hoteles;
    }

    /**
     * Devuelve un mapa con los hoteles que están en la ciudad indicada y tienen
     * habitaciones libres para la fecha indicada.
     *
     * @param ciudad
     * @param fechaEntrada
     * @param fechaSalida
     * @return Devuelve un mapa con hoteles.
     */
    public Map<String, Hotel> consultarFecha(String ciudad, Date fechaEntrada, Date fechaSalida) {
        Map<String, Hotel> hoteles = new HashMap();
        List<Hotel> lista = em.createQuery("Select h from Hotel h WHERE h.ciudad like ?1", Hotel.class).setParameter(1, "%" + ciudad + "%").getResultList();

        for (Hotel hotel : lista) {
            if (hotel.getSimplesLibres(fechaEntrada, fechaSalida) != 0
                    || hotel.getDoblesLibres(fechaEntrada, fechaSalida) != 0
                    || hotel.getTriplesLibres(fechaEntrada, fechaSalida) != 0) {
                hoteles.put(hotel.getNombre(), hotel);
            }
        }
        return hoteles;
    }

    /**
     * Comprueba y aumenta la versión del bloqueo optimista
     *
     * @param hotel
     * @throws HotelErrorBloquear
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorBloquear.class)
    @CatchExceptionAOP(exception = HotelErrorBloquear.class)
    public void bloquear(Hotel hotel) throws HotelErrorBloquear {
        em.merge(hotel);
        em.lock(em.contains(hotel) ? hotel : em.merge(hotel), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.flush();
    }
    
    /**
     * Listado de todos los hoteles.
     *
     * @return Devuelve un map con todos los hoteles.
     */
    public Map<String, Hotel> listar() {
        Map<String, Hotel> hoteles = new HashMap();
        List<Hotel> lista = em.createQuery("Select h from Hotel h").getResultList();

        for (Hotel hotel : lista) {
            hoteles.put(hotel.getNombre(), hotel);
        }
        return hoteles;
    }
}
