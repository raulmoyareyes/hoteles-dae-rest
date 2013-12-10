package es.ujaen.dae.gabri_raul.hoteles.dao;

import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorPersistir;
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
public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    public UsuarioDAO() {

    }

    /**
     * @param dni Clave para buscar el usuario
     * @return Devuelve un objeto de tipo Usuario o null si no lo encuentra.
     */
    @Cacheable(value = "usuarios", key = "#dni")
    public Usuario buscar(String dni) {
        return em.find(Usuario.class, dni);
    }

    /**
     * Persiste un usuario en la base de datos.
     *
     * @param usuario
     * @throws UsuarioErrorPersistir
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorPersistir.class)
    public void insertar(Usuario usuario) throws UsuarioErrorPersistir {
        try {
            em.persist(usuario);
            em.flush();
        } catch (Exception e) {
            throw new UsuarioErrorPersistir();
        }
    }

    /**
     * Actualiza en la BBDD el usuario
     *
     * @param usuario
     * @throws UsuarioErrorActualizar
     */
    @CacheEvict(value = "usuarios", key = "#usuario.getDni()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorActualizar.class)
    public void actualizar(Usuario usuario) throws UsuarioErrorActualizar {
        try {
            em.merge(usuario);
            em.flush();
        } catch (Exception e) {
            throw new UsuarioErrorActualizar();
        }
    }

    /**
     * Elimina en la BBDD el usuario
     *
     * @param usuario
     * @throws UsuarioErrorEliminar
     */
    @CacheEvict(value = "usuarios", key = "#usuario.getDni()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorEliminar.class)
    public void eliminar(Usuario usuario) throws UsuarioErrorEliminar {
        try {
            em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
            em.flush();
        } catch (Exception e) {
            throw new UsuarioErrorEliminar();
        }
    }

    /**
     * Listado de todos los usuarios.
     *
     * @return Devuelve un map con todos los usuarios.
     */
    public Map<String, Usuario> listar() {
        Map<String, Usuario> usuarios = new HashMap();
        List<Usuario> lista = em.createQuery("Select u from Usuario u").getResultList();

        for (Usuario usuario : lista) {
            if (!"00000000A".equals(usuario.getDni())) {
                usuarios.put(usuario.getDni(), usuario);
            }
        }
        return usuarios;
    }
}
