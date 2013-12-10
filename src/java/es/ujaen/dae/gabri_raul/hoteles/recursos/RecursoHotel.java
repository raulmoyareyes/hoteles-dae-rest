package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    
    @GET
    @Path("/{dni}")
    @Produces("application/json; charset=utf-8")
    public Map<String, Hotel> consultaNombreHotel(String nombre) {
        return operador.consultaNombreHotel(nombre);
    }
}