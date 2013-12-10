package es.ujaen.dae.gabri_raul.hoteles.dao;

import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorCambiarUsuario;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Reserva;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
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
public class ReservaDAO {

    @PersistenceContext
    EntityManager em;

    public ReservaDAO() {

    }

    /**
     * @param id
     * @return Devuelve un objeto de tipo Reserva o null si no lo encuentra.
     */
    @Cacheable(value = "reservas", key = "#id")
    public Reserva buscar(int id) {
        return em.find(Reserva.class, id);
    }

    /**
     * Persiste en la BBDD el usuario
     *
     * @param reserva
     * @throws ReservaErrorPersistir
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorPersistir.class)
    public void insertar(Reserva reserva) throws ReservaErrorPersistir {
        try {
            em.persist(reserva);
            em.flush();
        } catch (Exception e) {
            throw new ReservaErrorPersistir();
        }
    }

    /**
     * Actualiza en la BBDD el usuario
     *
     * @param reserva
     * @throws ReservaErrorActualizar
     */
    @CacheEvict(value = "reservas", key = "#reserva.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorActualizar.class)
    public void actualizar(Reserva reserva) throws ReservaErrorActualizar {
        try {
            em.merge(reserva);
            em.flush();
        } catch (Exception e) {
            throw new ReservaErrorActualizar();
        }
    }

    /**
     * Elmina en la BBDD el usuario
     *
     * @param reserva
     * @throws ReservaErrorEliminar
     */
    @CacheEvict(value = "reservas", key = "#reserva.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorEliminar.class)
    public void eliminar(Reserva reserva) throws ReservaErrorEliminar {
        try {
            em.remove(em.contains(reserva) ? reserva : em.merge(reserva));
            em.flush();
        } catch (Exception e) {
            throw new ReservaErrorEliminar();
        }
    }

    /**
     * Listado de todas las reservas.
     *
     * @return Devuelve un map con todas las reservas.
     */
    public Map<Integer, Reserva> listar() {
        Map<Integer, Reserva> reservas = new HashMap();
        List<Reserva> lista = em.createQuery("Select r from Reserva r").getResultList();

        for (Reserva reserva : lista) {
            reservas.put(reserva.getId(), reserva);
        }
        return reservas;
    }

    /**
     * Cambia el usuario a un usuario por defecto
     *
     * @param ud
     * @param u
     * @throws ReservaErrorCambiarUsuario
     */
    @CacheEvict(value = "reservas")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorActualizar.class)
    public void cambiarUsuario(Usuario ud, Usuario u) throws ReservaErrorCambiarUsuario {
//        try {
//            em.createQuery("Update Reserva r SET r.usuario = ?1 Where r.usuario = ?2").setParameter(1, ud).setParameter(2, u).executeUpdate();
//        } catch (Exception ex) {
//            throw new ReservaErrorCambiarUsuario();
//        }

        List<Reserva> lista = em.createQuery("Select r from Reserva r Where r.usuario = ?1").setParameter(1, u).getResultList();
        for (Reserva reserva : lista) {
            reserva.setUsuario(ud);
            em.merge(reserva);
            em.flush();
        }
    }
}
