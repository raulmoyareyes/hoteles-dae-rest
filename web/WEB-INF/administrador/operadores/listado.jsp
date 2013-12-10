<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/administrador/cabecera.jspf"/>

<!-- container -->

<jsp:include page="/WEB-INF/administrador/menu.jsp?ad=operadores"/>
<section class="panel panel-default">
    <div class="panel-heading">
        <h1>Listado de operadores</h1>
        <a class="pull-right btn btn-success" href="/Hoteles-DAE-WS/administrador/nuevooperador">Nuevo operador</a>
        <div class="clearfix"></div>
    </div>
    <div class="panel-body">
        <c:if test="${noEliminado == 'true'}">
            <div class="alert alert-error">
                El operador no ha sido eliminado.
            </div>
        </c:if>
        <table class="table table-striped table-condensed table-hover">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>CIF</th>
                    <th>Dirección social</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${operadores}" varStatus="estado">
                    <tr if="fila${o.value.cif}">
                        <td>${o.value.nombre}</td>
                        <td>${o.value.cif}</td>
                        <td>${o.value.direccionSocial}</td>
                        <td>
                            <!--<a href="#modalElimina" data-toggle="modal" class="elimina"><i class="icon-remove"></i></a>-->
                            <a href="/Hoteles-DAE-WS/administrador/eliminaroperador?cif=${o.value.cif}"><i class="glyphicon glyphicon-remove"></i></a>
                            <a href="/Hoteles-DAE-WS/administrador/modificaroperador?cif=${o.value.cif}"><i class="glyphicon glyphicon-pencil"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<!-- container -->

<jsp:include page="/WEB-INF/administrador/pie.jspf"/>
