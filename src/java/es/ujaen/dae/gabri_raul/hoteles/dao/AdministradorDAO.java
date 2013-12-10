package es.ujaen.dae.gabri_raul.hoteles.dao;

import es.ujaen.dae.gabri_raul.hoteles.excepciones.AdministradorErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.AdministradorErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.AdministradorErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Administrador;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase utilizada para trabajar con la BBDD
 *
 * @author Raúl & Gabri
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdministradorDAO {

    @PersistenceContext
    EntityManager em;

    public AdministradorDAO() {

    }

    /**
     * @param id Clave para buscar el administrador
     * @return Devuelve un objeto de tipo administrador o null si no lo encuentra.
     */
    public Administrador buscar(String id) {
        return em.find(Administrador.class, id);
    }

    /**
     * Persiste en la BBDD un administrador.
     *
     * @param administrador
     * @throws AdministradorErrorPersistir
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.AdministradorErrorPersistir.class)
    public void insertar(Administrador administrador) throws AdministradorErrorPersistir {
        try {
            em.persist(administrador);
            em.flush();
        } catch (Exception e) {
            throw new AdministradorErrorPersistir();
        }
    }

    /**
     * Actualizar en la BBDD un administrador
     *
     * @param administrador
     * @throws AdministradorErrorActualizar
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.AdministradorErrorActualizar.class)
    public void actualizar(Administrador administrador) throws AdministradorErrorActualizar {
        try {
            em.merge(administrador);
            em.flush();
        } catch (Exception e) {
            throw new AdministradorErrorActualizar();
        }
    }

    /**
     * Elimina en la BBDD un administrador
     *
     * @param administrador
     * @throws AdministradorErrorEliminar
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.AdministradorErrorEliminar.class)
    public void eliminar(Administrador administrador) throws AdministradorErrorEliminar {
        try {
            em.remove(em.contains(administrador) ? administrador : em.merge(administrador));
            em.flush();
        } catch (Exception e) {
            throw new AdministradorErrorEliminar();
        }
    }
    
    /**
     * Listado de todos los administradores.
     *
     * @return Devuelve un map con todos los operadores.
     */
    public Map<String, Administrador> listar() {
        Map<String, Administrador> administradores = new HashMap();
        List<Administrador> lista = em.createQuery("Select a from Administrador a").getResultList();

        for (Administrador administrador : lista) {
            administradores.put(administrador.getId(), administrador);
        }
        return administradores;
    }
}
