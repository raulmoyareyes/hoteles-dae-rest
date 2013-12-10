<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/administrador/cabecera.jspf"/>

<!-- container -->

<jsp:include page="/WEB-INF/administrador/menu.jsp?ad=hoteles"/>
<section class="panel panel-default">
    <div class="panel-heading">
        <h1>Modificar hotel</h1>
    </div>
    <div class="panel-body">
        <form method="post" class="form-horizontal">
            <div class="form-group">
                <label class="col-lg-2 control-label">Nombre</label>
                <div class="col-lg-6">
                    <input type="text" name="nombre" disabled="true" class="form-control" value="${hotel.nombre}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Ciudad</label>
                <div class="col-lg-6">
                    <input type="text" name="ciudad" class="form-control" value="${hotel.ciudad}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Dirección</label>
                <div class="col-lg-6">
                    <input type="text" name="direccion" class="form-control" value="${hotel.direccion}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Habitaciones simples</label>
                <div class="col-lg-6">
                    <input type="text" name="simples" class="form-control" value="${hotel.numSimples}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Habitaciones dobles</label>
                <div class="col-lg-6">
                    <input type="text" name="dobles" class="form-control" value="${hotel.numDobles}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Habitaciones triples</label>
                <div class="col-lg-6">
                    <input type="text" name="triples" class="form-control" value="${hotel.numTriples}"/> 
                </div>
            </div>
                <div class="form-group">
                <label class="col-lg-2 control-label">Precio habitaciones simples</label>
                <div class="col-lg-6">
                    <input type="text" name="psimples" class="form-control" value="${hotel.precioSimples}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Precio habitaciones dobles</label>
                <div class="col-lg-6">
                    <input type="text" name="pdobles" class="form-control" value="${hotel.precioDobles}"/> 
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label">Precio habitaciones triples</label>
                <div class="col-lg-6">
                    <input type="text" name="ptriples" class="form-control" value="${hotel.precioTriples}"/> 
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

