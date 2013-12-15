/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.gabri_raul.hoteles.recursos;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanOperador;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Operador;
import java.security.Principal;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Recurso REST para el Operador
 *
 * @author Raúl & Gabri
 */
@Path("/login")
@Component(value = "recursoLogin")
public class RecursoLogin {

    @Autowired
    BeanOperador operador;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @GET
    @Path("")
    @Consumes("application/json")
    @Produces("application/json; charset=utf-8")
    public Response login(@QueryParam("usuario") String usuario, @QueryParam("pass") String pass) {

        try {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usuario, pass);
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
            // Error de autenticación
        }

        if (usuario == null) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            Operador op = operador.login(usuario, null);
            if (op == null) {
                return Response.status(Status.NOT_FOUND).build();
            } else {
                return Response.ok(op).build();
            }
        }
    }
    
    @GET
    @Path("/out")
    @Consumes("application/json")
    @Produces("application/json; charset=utf-8")
    public Response logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return Response.ok("Exit...").build();
    }

}
