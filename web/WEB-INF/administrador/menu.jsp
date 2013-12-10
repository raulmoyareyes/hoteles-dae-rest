<!-- menu -->
<div class="navbar navbar-inverse">
    <div class="navbar-header">
        <a class="navbar-brand" href="/Hoteles-DAE-WS/administrador/listadooperadores">Administrador</a>
    </div>

    <div class="ollapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li class="${(param.ad == 'operadores')?'active':''}"><a href="/Hoteles-DAE-WS/administrador/listadooperadores">Operadores</a></li>
            <li class="${(param.ad == 'hoteles')?'active':''}"><a href="/Hoteles-DAE-WS/administrador/listadohoteles">Hoteles</a></li>

        </ul>                                    
    </div>
</div>
<!-- menu -->