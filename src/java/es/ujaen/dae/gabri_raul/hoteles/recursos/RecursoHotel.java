package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanAdministrador;
import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Path("/hoteles")
@Component(value = "recursoHotel")
public class RecursoHotel {

    @Autowired
    BeanOperador operador;

    @Autowired
    BeanAdministrador administrador;

    @GET
    @Path("/{nombre}")
    @Produces("application/json; charset=utf-8")
    public Response obtenerHotel(@PathParam("nombre") String nombre) {
        Hotel hotel = operador.obtenerHotel(nombre);
        if (hotel == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Hotel no encontrado.").build()
            );
        }
        return Response.ok(hotel).build();

    }

    @GET
    @Produces("application/json; charset=utf-8")
    public Response listaHoteles(@QueryParam("nombre") String nombre) {
        if (nombre == null) {
            return Response.ok(administrador.listaHoteles()).build();
        } else {
            Map<String, Hotel> hoteles = operador.consultaNombreHotel(nombre);
            if (hoteles == null) {
                throw new WebApplicationException(
                        Response.status(Status.NOT_FOUND).entity("Usuario no encontrado.").build()
                );
            }
            return Response.ok(hoteles).build();
        }
    }

    @PUT
    @Path("/{nombre}")
    @Consumes("application/json")
    public Response altaHotel(@PathParam("nombre") String nombre, Hotel hotel) {
        if (hotel == null) {
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST).entity("Falta el objeto hotel.").build()
            );
        }

        if (administrador.obtenerHotel(nombre) != null) {
            throw new WebApplicationException(
                    Response.status(Status.CONFLICT).entity("Hotel existente.").build()
            );
        }
        try {
            administrador.altaHotel(hotel);
        } catch (HotelErrorDatos | HotelErrorPersistir e) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_ACCEPTABLE).entity("Error al dar de alta el hotel.").build()
            );
        }
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{nombre}")
    //@Consumes("application/json")
    public Response bajaHotel(@PathParam("nombre") String nombre) {
        Hotel hotel = administrador.obtenerHotel(nombre);
        if (hotel == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Hotel no encontrado.").build()
            );
        } else {
            try {
                administrador.bajaHotel(nombre);
            } catch (HotelErrorEliminar | HotelNoEncontrado e) {
                throw new WebApplicationException(
                        Response.status(Status.NOT_ACCEPTABLE).entity("Error al dar de baja el hotel.").build()
                );
            }
            return Response.status(Status.ACCEPTED).build();
        }
    }

    @POST
    @Path("/{nombre}")
    @Consumes("application/json")
    public Response modificarHotel(@PathParam("nombre") String nombre, Hotel hotel) {
        if (hotel == null) {
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST).entity("Falta el objeto hotel.").build()
            );
        }

        if (administrador.obtenerHotel(nombre) == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Hotel no encontrado.").build()
            );
        }
        try {
            administrador.modificarHotel(hotel);
        } catch (HotelErrorActualizar e) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_ACCEPTABLE).entity("Error al actualizar el hotel.").build()
            );
        }
        return Response.status(Status.ACCEPTED).build();
    }

    @GET
    @Path("/busqueda")
    @Produces("application/json; charset=utf-8")
    public List<Hotel> consultaNombreHotel(@QueryParam("nombre") String nombre) {
        return new ArrayList(operador.consultaNombreHotel(nombre).values());
    }

}
