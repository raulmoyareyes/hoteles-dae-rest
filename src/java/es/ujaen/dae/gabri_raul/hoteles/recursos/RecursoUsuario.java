package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorCambiarUsuario;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Recurso REST para el Operador
 *
 * @author Raúl & Gabri
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
        if (usuario == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Usuario no encontrado.").build()
            );
        }
        return Response.ok(usuario).build();
    }

    @GET
    @Produces("application/json; charset=utf-8")
    public List<Usuario> listaUsuarios() {
        return new ArrayList(operador.listaUsuarios().values());
    }

    @PUT
    @Path("/{dni}")
    @Consumes("application/json")
    public Response altaUsuario(@PathParam("dni") String dni, Usuario usuario) {
        if (usuario == null) {
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST).entity("Falta el objeto usuario.").build()
            );
        }

        if (operador.obtenerUsuario(dni) != null) {
            throw new WebApplicationException(
                    Response.status(Status.CONFLICT).entity("Usuario existente.").build()
            );
        }
        try {
            operador.altaUsuario(usuario.getNombre(), usuario.getDireccion(), usuario.getDni());
        } catch (UsuarioErrorDatos | UsuarioErrorPersistir e) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_ACCEPTABLE).entity("Error al dar de alta el usuario.").build()
            );
        }
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{dni}")
    //@Consumes("application/json")
    public Response bajaUsuario(@PathParam("dni") String dni) {
        Usuario usuario = operador.obtenerUsuario(dni);
        if (usuario == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Usuario no encontrado.").build()
            );
        } else {
            try {
                operador.bajaUsuario(dni);
            } catch (UsuarioErrorEliminar | UsuarioNoEncontrado | ReservaErrorCambiarUsuario e) {
                throw new WebApplicationException(
                        Response.status(Status.NOT_ACCEPTABLE).entity("Error al dar de baja el usuario.").build()
                );
            }
            return Response.status(Status.ACCEPTED).build();
        }
    }

    @POST
    @Path("/{dni}")
    @Consumes("application/json")
    public Response modificarUsuario(@PathParam("dni") String dni, Usuario usuario) {
        if (usuario == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        if (operador.obtenerUsuario(dni) == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Usuario no encontrado.").build()
            );
        }
        try {
            operador.modificarUsuario(usuario.getNombre(), usuario.getDni(), usuario.getDireccion());
        } catch (UsuarioErrorActualizar e) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_ACCEPTABLE).entity("Error al actualizar el usuario.").build()
            );
        }
        return Response.status(Status.ACCEPTED).build();
    }
}
