/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.gabri_raul.hoteles.cliente.controller;

import es.ujaen.dae.gabri_raul.hoteles.beans.BeanAdministrador;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.HotelNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorActualizar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorDatos;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorEliminar;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorErrorPersistir;
import es.ujaen.dae.gabri_raul.hoteles.excepciones.OperadorNoEncontrado;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Hotel;
import es.ujaen.dae.gabri_raul.hoteles.modelos.Operador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author raul
 */
@WebServlet(name = "Administrador", urlPatterns = {"/administrador/*"})
public class Administrador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        BeanAdministrador beanAdministrador = (BeanAdministrador) context.getBean("beanAdministrador");

        String action = (request.getPathInfo() != null ? request.getPathInfo() : "");

        if (action.equals("/listadooperadores")) {

            request.setAttribute("operadores", beanAdministrador.listaOperadores());

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/administrador/operadores/listado.jsp");
                rd.forward(request, response);
        } else if (action.equals("/nuevooperador")) {

            if (request.getParameter("crear") != null) {

                String nombre = request.getParameter("nombre");
                String direccionSocial = request.getParameter("direccionSocial");
                String cif = request.getParameter("cif");
                try {
                    beanAdministrador.altaOperador(nombre, direccionSocial, cif);
                } catch (OperadorErrorDatos ex) {
                    System.out.println("No se ha podido crear el operador");
                } catch (OperadorErrorPersistir ex) {

                }
                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadooperadores");

            } else if (request.getParameter("cancelar") != null) {

                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadooperadores");

            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/administrador/operadores/nuevo.jsp");
                rd.forward(request, response);
            }

        } else if (action.equals("/modificaroperador")) {

            if (request.getParameter("modificar") != null) {
                String nombre = request.getParameter("nombre");
                String direccionSocial = request.getParameter("direccionSocial");
                String cif = request.getParameter("cif");

                Operador operador = beanAdministrador.obtenerOperador(cif);
                operador.setDireccionSocial(direccionSocial);
                operador.setNombre(nombre);
                try{
                    beanAdministrador.modificarOperador(operador);
                }catch(OperadorErrorActualizar ex){
                    System.out.println("No se ha podido modificar el operador");
                }

                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadooperadores");

            } else if (request.getParameter("cancelar") != null) {
                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadooperadores");
            } else {
                Operador operador = beanAdministrador.obtenerOperador((String) request.getParameter("cif"));
                request.setAttribute("operador", operador);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/administrador/operadores/modificar.jsp");
                rd.forward(request, response);
            }
        } else if (action.equals("/eliminaroperador")) {
            try {
                beanAdministrador.bajaOperador(request.getParameter("cif"));
            } catch (OperadorErrorEliminar | OperadorNoEncontrado ex) {
                System.out.println("No se ha podido eliminar el operador");
            }

            response.sendRedirect("/Hoteles-DAE-WS/administrador/listadooperadores");

        }else if (action.equals("/listadohoteles")){
            
            request.setAttribute("hoteles", beanAdministrador.listaHoteles());

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/administrador/hoteles/listado.jsp");
                rd.forward(request, response);
        }else if (action.equals("/nuevohotel")) {

            if (request.getParameter("crear") != null) {

                String nombre = request.getParameter("nombre");
                String direccion = request.getParameter("direccion");
                String ciudad = request.getParameter("ciudad");
                Integer simples = Integer.parseInt(request.getParameter("simples"));
                Integer dobles = Integer.parseInt(request.getParameter("dobles"));
                Integer triples = Integer.parseInt(request.getParameter("triples"));
                Float psimples = Float.parseFloat(request.getParameter("psimples"));
                Float pdobles = Float.parseFloat(request.getParameter("pdobles"));
                Float ptriples = Float.parseFloat(request.getParameter("ptriples"));
                Hotel hotel = new Hotel(nombre, direccion, ciudad, simples, dobles, triples, psimples, pdobles, ptriples);
                try {
                    beanAdministrador.altaHotel(hotel);
                } catch (HotelErrorDatos ex) {
                    System.out.println("No se ha podido crear el operador");
                } catch (HotelErrorPersistir ex) {

                }
                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadohoteles");

            } else if (request.getParameter("cancelar") != null) {

                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadohoteles");

            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/administrador/hoteles/nuevo.jsp");
                rd.forward(request, response);
            }

        }else if (action.equals("/modificarhotel")) {

            if (request.getParameter("modificar") != null) {
                String nombre = request.getParameter("nombre");
                String direccion = request.getParameter("direccion");
                String ciudad = request.getParameter("ciudad");
                Integer simples = Integer.parseInt(request.getParameter("simples"));
                Integer dobles = Integer.parseInt(request.getParameter("dobles"));
                Integer triples = Integer.parseInt(request.getParameter("triples"));
                Float psimples = Float.parseFloat(request.getParameter("psimples"));
                Float pdobles = Float.parseFloat(request.getParameter("pdobles"));
                Float ptriples = Float.parseFloat(request.getParameter("ptriples"));

                Hotel hotel = beanAdministrador.obtenerHotel(nombre);
                hotel.setDireccion(direccion);
                hotel.setNombre(nombre);
                hotel.setCiudad(ciudad);
                hotel.setNumSimples(simples);
                hotel.setNumDobles(dobles);
                hotel.setNumTriples(triples);
                hotel.setPrecioSimples(psimples);
                hotel.setPrecioDobles(pdobles);
                hotel.setPrecioTriples(ptriples);
                try{
                    beanAdministrador.modificarHotel(hotel);
                }catch(HotelErrorActualizar ex){
                    System.out.println("No se ha podido modificar el hotel");
                }

                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadohoteles");

            } else if (request.getParameter("cancelar") != null) {
                response.sendRedirect("/Hoteles-DAE-WS/administrador/listadohoteles");
            } else {
                Hotel hotel = beanAdministrador.obtenerHotel((String) request.getParameter("nombre"));
                request.setAttribute("hotel", hotel);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/administrador/hoteles/modificar.jsp");
                rd.forward(request, response);
            }
        }else if (action.equals("/eliminarhotel")) {
            try {
                beanAdministrador.bajaHotel(request.getParameter("nombre"));
            } catch (HotelErrorEliminar | HotelNoEncontrado ex) {
                System.out.println("No se ha podido eliminar el hotel");
            }

            response.sendRedirect("/Hoteles-DAE-WS/administrador/listadohoteles");

        }else {
            response.sendRedirect("/Hoteles-DAE-WS/administrador/listadooperadores");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
