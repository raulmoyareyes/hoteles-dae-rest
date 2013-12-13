package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanAdministrador;
import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import java.net.URI;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Recurso REST para el Operador
 *
 * @author Raúl &  Gabri
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
        if(hotel != null){
            return Response.ok(hotel).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("")
    @Produces("application/json; charset=utf-8")
    public Response listaHoteles(@QueryParam("nombre") String nombre) {
        if(nombre==null)
        {
            return Response.ok(administrador.listaHoteles()).build();
        }else{
            Map<String, Hotel> hoteles = operador.consultaNombreHotel(nombre);
            if(hoteles != null){
                return Response.ok(hoteles).build();
            }else{
                return Response.status(Status.NOT_FOUND).build();
            }
        }
    }
    
    @PUT
    @Path("/{nombre}")
    @Consumes("application/json")
    public Response altaHotel (@PathParam("nombre") String nombre, Hotel hotel){
        if (hotel == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        if(administrador.obtenerHotel(nombre) != null){
            return Response.status(Status.CONFLICT).build();
        }
        try{
        administrador.altaHotel(hotel);
        }catch(HotelErrorDatos | HotelErrorPersistir e){
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        return Response.created(URI.create("")).build();
    }
    
    @DELETE
    @Path("/{nombre}")
    //@Consumes("application/json")
    public Response bajaHotel (@PathParam("nombre") String nombre){
        Hotel hotel = administrador.obtenerHotel(nombre);
        if(hotel == null){
            return Response.status(Status.NOT_FOUND).build();
        }else{
            try{
                administrador.bajaHotel(nombre);
            }catch (HotelErrorEliminar | HotelNoEncontrado e){
                return Response.status(Status.NOT_ACCEPTABLE).build();
            }
            return Response.status(Status.ACCEPTED).build();
        }
    }
    
    @POST
    @Path("/{nombre}")
    @Consumes("application/json")
    public Response modificarHotel (@PathParam("nombre") String nombre, Hotel hotel){
        if (hotel == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        if(administrador.obtenerHotel(nombre) == null){
            return Response.status(Status.NOT_FOUND).build();
        }
        try{
        administrador.modificarHotel(hotel);
        }catch(HotelErrorActualizar e){
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        return Response.status(Status.ACCEPTED).build();
    }
    
//    @GET
//    @Path("/busqueda")
//    @Produces("application/json; charset=utf-8")
//    public Response consultaNombreHotel(@QueryParam("nombre") String nombre) {
//        
//        Map<String, Hotel> hoteles = operador.consultaNombreHotel(nombre);
//        if(hoteles != null){
//            return Response.ok(hoteles).build();
//        }else{
//            return Response.status(Status.NOT_FOUND).build();
//        }
//    }
    
    
}