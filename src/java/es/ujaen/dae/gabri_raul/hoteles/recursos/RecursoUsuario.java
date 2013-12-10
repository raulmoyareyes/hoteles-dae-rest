package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Usuario;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Recurso REST para el Operador
 *
 * @author Raúl &  Gabri
 */
@Path("/usuarios")
@Component(value = "recursoUsuario")
public class RecursoUsuario {
    
    @Autowired
    BeanOperador operador;
    
    @GET
    @Path("/{dni}")
    @Produces("application/json; charset=utf-8")
    public Usuario obtenerUsuario(@PathParam("dni") String dni) {
        return operador.obtenerUsuario(dni);
    }
    
    @GET
    @Path("")
    @Produces("application/json; charset=utf-8")
    public Map<String, Usuario> listaUsuarios() {
        return operador.listaUsuarios();
    }
    
}
