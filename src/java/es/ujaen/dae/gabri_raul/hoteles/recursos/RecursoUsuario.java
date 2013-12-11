package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Usuario;
import java.net.URI;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
    public Response obtenerUsuario(@PathParam("dni") String dni) {
        Usuario usuario = operador.obtenerUsuario(dni);
        if(usuario != null){
            return Response.ok(usuario).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("")
    @Produces("application/json; charset=utf-8")
    public Map<String, Usuario> listaUsuarios() {
        return operador.listaUsuarios();
    }
    
    @PUT
    @Path("/nuevo/{dni}")
    @Consumes("application/json")
    public Response crearUsuario (@PathParam("dni") String dni, Usuario usuario){
        if (usuario == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        if(operador.obtenerUsuario(dni) != null){
            return Response.status(Status.CONFLICT).build();
        }
        try{
        operador.altaUsuario(usuario.getNombre(), usuario.getDireccion(), usuario.getDni());
        }catch(UsuarioErrorDatos | UsuarioErrorPersistir e){
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        return Response.created(URI.create("")).build();
    }
    
}
