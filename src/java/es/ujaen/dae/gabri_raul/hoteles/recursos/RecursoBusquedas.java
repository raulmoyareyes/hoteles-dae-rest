/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import java.util.Date;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Recurso REST para el Operador
 *
 * @author Raúl &  Gabri
 */
@Path("/{ciudad}")
@Component(value = "recursoBusquedas")
public class RecursoBusquedas {
    @Autowired
    BeanOperador operador;
    
    @GET
    @Path("/hoteles")
    @Produces("application/json; charset=utf-8")
    public Map<String, Hotel> consultaCiudad(@PathParam("ciudad") String ciudad, @QueryParam("entrada") Date entrada, @QueryParam("salida") Date salida) {
        if(entrada==null || salida==null)
        {
            return operador.consultaCiudad(ciudad);
        }else{
            return operador.consultaFecha(ciudad, entrada, salida);
        }
    }
    
}