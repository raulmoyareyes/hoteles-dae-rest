/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorBloquear;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaNoEncontrada;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.ReservaNoPosible;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.UsuarioNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Reserva;
import java.net.URI;
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
@Path("/reservas")
@Component(value = "recursoReservas")
public class RecursoReservas {

    @Autowired
    BeanOperador operador;

    @GET
    @Path("/{id}")
    @Produces("application/json; charset=utf-8")
    public Response obtenerReserva(@PathParam("id") int id) {
        Reserva reserva = operador.obtenerReserva(id);
        if (reserva == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Reserva no encontrada.").build()
            );
        }
        return Response.ok(reserva).build();
    }

    @GET
    @Produces("application/json; charset=utf-8")
    public List<Reserva> listadoReservas() {
        return new ArrayList(operador.listadoReservas().values());
    }

    @PUT
    @Consumes("application/json")
    public Response crearReserva(Reserva reserva) {

        try {
            operador.crearReserva(reserva.getFechaEntrada(), reserva.getFechaSalida(), reserva.getSimples(), reserva.getDobles(), reserva.getTriples(), reserva.getUsuario().getDni(), reserva.getHotel().getNombre());
        } catch (ReservaErrorDatos | UsuarioNoEncontrado | HotelErrorBloquear | HotelNoEncontrado | ReservaNoPosible | HotelErrorActualizar e) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_ACCEPTABLE).entity("Error al dar de alta la reserva.").build()
            );
        }
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{id}")
    //@Consumes("application/json")
    public Response eliminarReserva(@PathParam("id") int id) {
        Reserva reserva = operador.obtenerReserva(id);
        if (reserva == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Reserva no encontrada.").build()
            );
        } else {
            try {
                operador.eliminarReserva(id);
            } catch (HotelErrorActualizar | ReservaNoEncontrada e) {
                throw new WebApplicationException(
                        Response.status(Status.NOT_ACCEPTABLE).entity("Error al eliminar la reserva.").build()
                );
            }
            return Response.status(Status.ACCEPTED).build();
        }
    }

    @POST
    @Path("/{id}")
    @Consumes("application/json")
    public Response modificarReserva(@PathParam("id") int id, Reserva reserva) {
        if (reserva == null) {
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST).entity("Falta el objeto reserva.").build()
            );
        }

        if (operador.obtenerReserva(id) == null) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_FOUND).entity("Reserva no encontrada.").build()
            );
        }
        try {
            operador.modificarReserva(id, reserva.getFechaEntrada(), reserva.getFechaSalida(), reserva.getSimples(), reserva.getDobles(), reserva.getTriples(), reserva.getUsuario().getDni(), reserva.getHotel().getNombre());
        } catch (ReservaErrorActualizar e) {
            throw new WebApplicationException(
                    Response.status(Status.NOT_ACCEPTABLE).entity("Error al actualizar la reserva.").build()
            );
        }
        return Response.status(Status.ACCEPTED).build();
    }
}
