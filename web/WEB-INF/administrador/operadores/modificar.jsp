<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/administrador/cabecera.jspf"/>

<!-- container -->

<jsp:include page="/WEB-INF/administrador/menu.jsp?ad=operadores"/>
<section class="panel panel-default">
    <div class="panel-heading">
        <h1>Modificar operador</h1>
    </div>
    <div class="panel-body">
        <form method="post" class="form-horizontal">
            <div class="form-group">
                <label class="col-lg-2 control-label">Nombre</label>
                <div class="col-lg-6">
                    <input type="text" name="nombre" class="form-control" value="${operador.nombre}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">CIF</label>
                <div class="col-lg-6">
                    <input type="text" name="cif" disabled="true" class="form-control" value="${operador.cif}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Dirección social</label>
                <div class="col-lg-6">
                    <input type="text" name="direccionSocial" class="form-control" value="${operador.direccionSocial}"/> 
                </div>
            </div>
            <div class="col-lg-offset-2 col-lg-10">
                <button type="submit" class="btn btn-success" name="modificar">Modificar</button>
                <button type="reset" class="btn btn-primary">Limpiar</button>
                <button type="submit" class="btn btn-danger" name="cancelar">Cancelar</button>
            </div>
        </form>
    </div>
</section>
<!-- container -->

<jsp:include page="/WEB-INF/administrador/pie.jspf"/>

