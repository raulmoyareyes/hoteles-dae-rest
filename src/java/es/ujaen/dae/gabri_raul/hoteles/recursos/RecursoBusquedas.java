/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import es.ujaen.dae.localidadescercanas.Ciudad;
import es.ujaen.dae.localidadescercanas.CiudadInexistente_Exception;
import es.ujaen.dae.localidadescercanas.LocalidadesCercanas;
import es.ujaen.dae.localidadescercanas.LocalidadesCercanasService;
import es.ujaen.dae.localidadescercanas.RadioIncorrecto_Exception;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

/**
 * Recurso REST para el Operador
 *
 * @author Raúl & Gabri
 */
@Path("/{ciudad}")
@Component(value = "recursoBusquedas")
public class RecursoBusquedas {

    @Autowired
    BeanOperador operador;

    @GET
    @Path("/hoteles")
    @Produces("application/json; charset=utf-8")
    @Secured("ROLE_OPERADOR")
    public List<Hotel> consultaCiudad(@PathParam("ciudad") String ciudad, @QueryParam("entrada") String entrada, @QueryParam("salida") String salida) {
        if (entrada == null || salida == null) {
            LocalidadesCercanasService localidadesWS = new LocalidadesCercanasService();
            LocalidadesCercanas localidades = localidadesWS.getLocalidadesCercanasPort();

            List<Ciudad> ciudades = new ArrayList();
            try {
                ciudades = localidades.ciudadesCercanas(ciudad, 20);
            } catch (CiudadInexistente_Exception | RadioIncorrecto_Exception ex) {
                //
            }

            List<Hotel> hoteles = new ArrayList();
            List<Hotel> cercanos = new ArrayList();
            for (Ciudad c : ciudades) {
                System.out.println(c.getNombre());

                if (c.getNombre().equals(ciudad)) {
                    hoteles.addAll(operador.consultaCiudad(ciudad).values());
                } else {
                    cercanos.addAll(operador.consultaCiudad(c.getNombre()).values());
                }
            }
            hoteles.add(new Hotel());
            hoteles.addAll(cercanos);

            return hoteles;
        } else {
            Date fEntrada = new Date();
            Date fSalida = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fEntrada = format.parse(entrada);
                fSalida = format.parse(salida);
            } catch (ParseException ex) {
                //
            }
            return new ArrayList(operador.consultaFecha(ciudad, fEntrada, fSalida).values());
        }
    }

}
