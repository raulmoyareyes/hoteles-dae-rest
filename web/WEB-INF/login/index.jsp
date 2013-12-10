<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/login/cabecera.jspf"/>

<!-- container -->
<c:if test="${user == null}">
    <div id="error" class="alert alert-error modal">
        <button id="closeAlert" type="button" class="close">&times;</button>
        <strong>¡Error al iniciar sesión!</strong> Compruebe sus datos.
    </div>
</c:if>

<div class="container">

    <form class="form-signin" method="post">
        <h2 class="form-signin-heading">Iniciar sesión</h2>
        <input id="user" type="text" class="form-control" placeholder="Nombre de usuario" name="id" value="${user.nombre}"/>
        <input id="pass" type="password" class="form-control" placeholder="Contraseña" name="pass"/>
        <label id="remember" class="checkbox">
            <input type="checkbox" value="remember-me"> Recordarme
        </label> 
        <button name="login" class="btn btn-large btn-primary" type="submit">Entrar</button>
    </form>

</div>
<!-- container -->

<jsp:include page="/WEB-INF/login/pie.jspf"/>