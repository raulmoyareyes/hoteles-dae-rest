<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/administrador/cabecera.jspf"/>

<!-- container -->

<jsp:include page="/WEB-INF/administrador/menu.jsp?ad=hoteles"/>
<section class="panel panel-default">
    <div class="panel-heading">
        <h1>Listado de hoteles</h1>
        <a class="pull-right btn btn-success" href="/Hoteles-DAE-WS/administrador/nuevohotel">Nuevo hotel</a>
        <div class="clearfix"></div>
    </div>
    <div class="panel-body">
        <c:if test="${noEliminado == 'true'}">
            <div class="alert alert-error">
                El hotel no ha sido eliminado.
            </div>
        </c:if>
        <table class="table table-striped table-condensed table-hover">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Ciudad</th>
                    <th>Dirección</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="h" items="${hoteles}" varStatus="estado">
                    <tr if="fila${h.value.nombre}">
                        <td>${h.value.nombre}</td>
                        <td>${h.value.ciudad}</td>
                        <td>${h.value.direccion}</td>
                        <td>
                            <!--<a href="#modalElimina" data-toggle="modal" class="elimina"><i class="icon-remove"></i></a>-->
                            <a href="/Hoteles-DAE-WS/administrador/eliminarhotel?nombre=${h.value.nombre}"><i class="glyphicon glyphicon-remove"></i></a>
                            <a href="/Hoteles-DAE-WS/administrador/modificarhotel?nombre=${h.value.nombre}"><i class="glyphicon glyphicon-pencil"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<!-- container -->

<jsp:include page="/WEB-INF/administrador/pie.jspf"/>
