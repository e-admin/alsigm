<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- ¿Skin CSS? -->

<link rel="stylesheet" href="./css/terceros/reset.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="./css/terceros/table.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="./css/terceros/estilos.css" type="text/css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="css/global.css" />
<link rel="stylesheet" type="text/css" href="css/font.css" />
<link rel="stylesheet" type="text/css" href="css/estilos.css">
<link rel="stylesheet" type="text/css" href="css/displaytag.css">
<link rel="stylesheet" type="text/css" href="css/intercambioRegistral.css">

<link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui-1.8.16.custom.css">

<script type="text/javascript" src="./scripts/genmsg.js"></script>
<script type="text/javascript" src="./scripts/bandejasIntercambioRegistral.js"></script>
<script type="text/javascript" src="./scripts/global.js"></script>
<script type="text/javascript" src="./scripts/qryfmt.js"></script>
<script type="text/javascript" src="./scripts/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="./scripts/jquery-ui-1.8.16.custom.min.js"></script>


<!--[if lte IE 5]>
	<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
<![endif]-->

<!--[if IE 6]>
	<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css">
<![endif]-->

<!--[if gte IE 7]>
	<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css">
<![endif]-->

<!--[if lte IE 6]>
	<script type="text/javascript" src="./scripts/iepngfix_tilebg.js"></script>
<![endif]-->
<script type="text/javascript">
var estado='<c:out value="${estado}" />';
var tipoBandeja='<c:out value="${tipoBandeja}" />';
var libroSeleccionado='<c:out value="${libroSeleccionado}" />';
window.onload=init;
function init()
{
	selectState();
	setRowsEvents();
	selectBook();

}

//metodo para setear los eventos de las filas de las tablas y asociar el evento de abrir registro
function setRowsEvents()
{
	if(document.getElementById('bandejaSalida')!=null)
	{
		var rows = document.getElementById('bandejaSalida').rows;

		for(i=1;i<rows.length;i++)
		{
			rows[i].style.cursor='pointer';
			rows[i].ondblclick=abrirRegistro;
		}
	}

}
function selectState()
{
 var select = document.getElementById('estadosSalida');
 for ( var i = 0; i < select.options.length; i++ ) {
        if ( select.options[i].value == estado ) {
            select.options[i].selected = true;
            return;
        }
    }
 var selectTipoBandeja = document.getElementById('tipoBandeja');
 for ( var i = 0; i < selectTipoBandeja.options.length; i++ ) {
        if ( selectTipoBandeja.options[i].value == tipoBandeja ) {
            selectTipoBandeja.options[i].selected = true;
            return;
        }
    }
}
function selectBook()
{
var select = document.getElementById('libroSeleccionado');
 for ( var i = 0; i < select.options.length; i++ ) {
        if ( select.options[i].value == libroSeleccionado ) {
            select.options[i].selected = true;
            return;
        }
    }
}


function mostrarCapaNuevoDestino(){
	var buscarNuevoDestino = document.getElementById('buscarNuevoDestino');
	buscarNuevoDestino.style.display='block';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='block';
}


function buscarNuevoDestino()
{
	top.g_WndVld = top.Main.Workspace;
		top.g_FormVld = document.getElementById("formBandejaSalida");
		top.g_Field = document.getElementById("nuevoDestino");
		top.g_VldPath = "top.Main.Workspace" ;

	 				var URL = top.g_URL + "/mainvld.htm?SessionPId=" + top.g_SessionPId
				+ "&ArchivePId=" + top.g_ArchivePId
				+ "&FldId=7"
				+ "&tblvalidated=11"
				+ "&TblFldId=7"
				+ "&Idioma=" + top.Idioma.toString()
				+ "&caseSensitive=CS"
				+ "&Enabled=0&IsDtrList=0";

		OnHelpWindow = true;
		OpenVldHlpWnd(URL, 'name', '97%', '97%', 'no');
}

// funcion para abrir el registro situado en la la fila que estamos situados de la tabla
function abrirRegistro(event)
{
	if ( !event )
    {
        event = window.event;
    }
    var target = event.currentTarget ? event.currentTarget : event.srcElement;
	var idRegistro = target.id;

	if(idRegistro=='')
	{
		idRegistro = target.parentNode.id;
	}

	var idLibro = document.getElementById('idLibroParaRegistro_'+idRegistro).value;

	var URL = top.g_URL + "/AbrirLibroVerRegistroIntercambioRegistral.do?AppId=" + top.g_AppId.toString()
			+ "&SessionPId=" + top.g_SessionPId + "&FolderView=1&ArchiveId=" + idLibro
            + "&ArchiveName=" + top.g_ArchiveName.toString()
            + "&ArchivePId=" + top.g_ArchivePId.toString()
            + "&FolderPId=" + top.g_FolderPId.toString()
            + "&FolderId=" + idRegistro
            + "&VldSave=1" + "&Idioma=" + top.Idioma.toString()
            + "&numIdioma=" + top.numIdioma.toString()
            + "&FdrQryPId=" + top.g_FdrQryPId.toString()
	        + "&OpenType=0";

	top.OpenNewWindow(URL, "", "10000", "10000", "auto","yes");
}


</script>

</head>
<body class="cabecera">
<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
<c:if test="${ message == null }">
    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
</c:if>

	<form action="BandejaSalidaIntercambioRegistral.do" method="post" id="formBandejaSalida" name="formBandejaSalida">
	<div class="barraBandeja">
	<!-- TODO ESTE COMBO VIENE EN VARIABLE -->
		<select id="tipoBandeja" style="display:inline;"  onchange="javascript:changeSelectEstados()" name="tipoBandeja">
			<option value="0"><fmt:message key="intercambioRegistral.bandeja.salida" /></option>
			<option value="1"><fmt:message key="intercambioRegistral.bandeja.entrada" /></option>
		</select>

		<select id="estadosSalida" style="display:inline;"    name="estadosSalida">
			<option value="1"><fmt:message key="intercambioRegistral.estados.enviados" /></option>
			<option value="5"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
			<option value="4"><fmt:message key="intercambioRegistral.estados.devueltos" /></option>

		</select>

		<select id="estadosEntrada" style="display:none;"  name="estadosEntrada">
			<option value="0"><fmt:message key="intercambioRegistral.estados.pendientes" /></option>
			<option value="1"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
			<option value="2"><fmt:message key="intercambioRegistral.estados.rechazados" /></option>
		</select>
		<select id="libroSeleccionado"  name="libroSeleccionado" style="display:inline;">
			<c:forEach items="${librosIntercambio}" var="libro">
				<option value="<c:out value='${libro.id}' />"><c:out value='${libro.name}' /></option>
			</c:forEach>
		</select>
		<a href="#" onclick="javascript:submitRefrescarBandejaEntradaOSalida();" class="Options linkBarraBandeja"><fmt:message key="intercambioRegistral.boton.refrescar" /></a>
		<a class="linkBarraBandeja Options" href="frquery.htm"><fmt:message key="intercambioRegistral.boton.volver" /></a>

	</div>
	<br />


	<c:if test="${requestScope.msg!=null}">
	<div style="color:blue;">
		<c:out  value="${requestScope.msg}" ></c:out>
	</div>
	</c:if>
	<c:if test="${requestScope.error!=null}">
	<div style="color:red;">
		<c:out value="${requestScope.error}" ></c:out>
	</div>
	</c:if>

<div id="capaBloqueo" style="display:none;z-index:9998;"></div>


			<display:table  htmlId="bandejaSalida" name="sessionScope.bandejaSalida" id="row" requestURI="/BandejaSalidaIntercambioRegistral.do" pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaSalidaTableDecorator">
				<display:column><input id="idLibroParaRegistro_<c:out value='${row.idRegistro}'/>" type="hidden" value="<c:out value='${row.idLibro}'/>"/></display:column>
				<display:column sortable="true" property="numeroRegistro" titleKey="intercambioRegistral.tabla.numeroRegistro"></display:column>
				<display:column sortable="true" property="fechaRegistro" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaRegistro"></display:column>

				<display:column sortable="true" property="nameEntity" titleKey="intercambioRegistral.tabla.destino.entidadRegistral"></display:column>
				<display:column sortable="true" property="nameTramunit" titleKey="intercambioRegistral.tabla.destino.unidadTramitacion"></display:column>

				<display:column sortable="true" property="fechaEstado" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaIntercambio"></display:column>
				<display:column sortable="true" property="idIntercambioRegistral" titleKey="intercambioRegistral.tabla.idIntercambioRegistral"></display:column>

				<display:setProperty name="paging.banner.item_name">
					<fmt:message key="intercambioRegistral.tabla.item_name" />
				</display:setProperty>
				<display:setProperty name="paging.banner.items_name">
					<fmt:message key="intercambioRegistral.tabla.items_name" />
				</display:setProperty>
			</display:table>

	</form>
	<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no"
					style="height:345px;left:5px;position:absolute;top:5px;width:450px;display:none">
	</iframe>
</body>
</html>