<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script src="include/js/validations.js" type="text/javascript"></script>

<script language="javascript">
var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
var id = '<c:out value="${requestScope.id}"/>';
var tipo = '<c:out value="${requestScope.tipo}"/>';
var rowIndex;
var nameColum = 0;

function properties(){
    var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		if (tipo == 1 ) // repositorios
			parent.edicion.location.href = appBase + '/volume/repositoryProperties.do?id='+id;
		else
			parent.edicion.location.href = appBase + '/volume/listProperties.do?id='+id;
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');
	}
}
function edit(){
    var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		if (tipo == 1 ) // repositorios
			parent.edicion.location.href = appBase + '/volume/repositoryEdit.do?id='+id;
		else
			parent.edicion.location.href = appBase + '/volume/listEdit.do?id='+id;
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');
	}
}

function volNew()
{
	parent.edicion.location.href = appBase + '/volume/volumeNew.do?idRep='+id;
}


function volProperties(id)
{
	parent.edicion.location.href = appBase + '/volume/volumeProperties.do?id='+id;

}
function volEdit (id)
{
	parent.edicion.location.href = appBase + '/volume/volumeEdit.do?id='+id;
}

	var volumes = new Array();
	<logic:iterate name="volumeListForm" property="volumenes" id="volume" >
		volumes['<bean:write name="volume" property="name" />']=<bean:write name="volume" property="id" />;
	</logic:iterate>


	function getId(name){
		return volumes[name];
	}
	function selectRow(index){
		var table = document.getElementById('lista');
		if (rowIndex){
			var rowSelected = table.rows[rowIndex];
			// rowSelected.style.backgroundColor = "#ffffff";
			rowSelected.className='notSelectedRow';
		}
		rowIndex = index;
		var rowSelected = table.rows[rowIndex];
		// rowSelected.style.backgroundColor = "#ff0000";
		rowSelected.className = 'selectedRow';

	}
	function changeRow(table,id1, id2){
		var row1 = table.rows[id1];
		numCells = row1.cells.length;
		var array1 = new Array();
		for (i = 0; i < numCells; i++ ){
			array1[i] = row1.cells[i].innerHTML;
		}
		var row2 = table.rows[id2];
		for (i = 0; i < numCells; i++ ){
			row1.cells[i].innerHTML = row2.cells[i].innerHTML;
			row2.cells[i].innerHTML = array1[i];
		}
	}
	function up()
	{
		var lista = document.getElementById('lista');

		if (rowIndex && rowIndex >1)
		{
			changeRow(lista, rowIndex,rowIndex - 1);
			selectRow(rowIndex - 1);
		}
	}
	function down()
	{
		var table = document.getElementById('lista');
		var tam = table.rows.length;
		if (rowIndex && rowIndex != tam -1)
		{
			changeRow(lista, rowIndex,rowIndex + 1);
			selectRow(rowIndex + 1);
		}
	}

	function changeOrder()
	{
		var table = document.getElementById('lista');
		var n = table.rows.length;
		var i = 1;
		var arrayId = "";

		for ( i = 1; i < n; i ++ )
		{
			var name = table.rows[i].cells[nameColum].innerHTML;
			var id = getId(name);
			arrayId += id + "/";
		}
		var form = document.getElementById('changeOrderForm');
		document.getElementById('order').value = arrayId;
		form.submit();

	}

</script>
</head>

<body>
<c:set scope="session" var="id" >
	<c:out value="${requestScope.id}"/>
</c:set>
<c:set scope="session" var="tipo" >
	<c:out value="${requestScope.tipo}"/>
</c:set>
    <div id="contenedora">
		<div class="cuerpo" style="width:590px; height:130px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
	    			<h1>
						<c:choose>
							<c:when test="${requestScope.tipo == 1}">
								<bean:message key="message.repositorio.etiqueta.listavolumennes"/>
							</c:when>
							<c:otherwise>
								<bean:message key="message.lista.etiqueta.listavolumennes"/>
							</c:otherwise>
						</c:choose>
						<c:out value="${requestScope.name}"/>
	    			</h1>
	    			<br/>
	    			<div class="cuadro" style="height: 130px;">
						<a class="menu0" href="javascript:properties();"><bean:message key="message.comun.etiqueta.propiedades"/></a>
						<a class="menu0" href="javascript:edit();"><bean:message key="message.comun.etiqueta.editar"/></a>

						<div id="lista" style="position:absolute; left:10px; top:75px; width:570px; height:90px; z-index:1; border: 1px none #000000;">
						  <iframe name="lista" src="<html:rewrite page="/volume/volumeTable.jsp"/>" frameborder="no" width="100%" height="90px;" scrolling="yes" >
						  </iframe>
						</div>

	       			</div>
	       		</div>
     		</div>
	   	</div>


	</div>

	<div class="cuerpobt" style="width:590px">
    	<div class="cuerporightbt">
      		<div class="cuerpomidbt"></div>
    	</div>
	</div>


</body>

<!--
<body>
	<div class="encabezado1">
	<c:choose>
		<c:when test="${requestScope.tipo == 1}">
			Volúmenes del repositorio:
		</c:when>
		<c:otherwise>
			Volúmenes de la lista:
		</c:otherwise>
	</c:choose>
	<c:out value="${requestScope.name}"/>
	<br>
	</div>
	<a href="javascript:properties();">Propiedades</a>
	<a href="javascript:edit();">Editar</a>
<table border class="tableBase" id="lista">
	<tr>
		<th>Nombre</th>
		<th>Capacidad(MB)</th>
		<th>Ocupacion(MB)</th>
		<th>Ficheros</th>
		<th>Estado</th>
		<th colspan="2">&nbsp;</th>
	</tr>
	<logic:iterate name="volumeListForm" property="volumenes" id="volume" >

	<c:choose>
		<c:when test="${requestScope.tipo == 2}"> <%-- Solo quiero que las filas sean seleccionables, si se muestran volumenes de listas --%>
			<tr onclick="selectRow(this.rowIndex)">
			<td class="tdBase"><bean:write name="volume" property="name" /></td>
			<td class="tdBase"><bean:write name="volume" property="maxSize" /></td>
			<td class="tdBase"><bean:write name="volume" property="actSize" /></td>
			<td class="tdBase"><bean:write name="volume" property="numFiles" /></td>
			<td class="tdBase"><bean:write name="volume" property="states" /></td>
			<td class="tdBase"><a href="javascript:volProperties('<bean:write name='volume' property='id'/>');">Propiedades</a></td>
			<td class="tdBase"><a href="javascript:volEdit('<bean:write name='volume' property='id'/>');">Editar</a></td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td class="tdBase"><bean:write name="volume" property="name" /></td>
				<td class="tdBase"><bean:write name="volume" property="maxSize" /></td>
				<td class="tdBase"><bean:write name="volume" property="actSize" /></td>
				<td class="tdBase"><bean:write name="volume" property="numFiles" /></td>
				<td class="tdBase"><bean:write name="volume" property="states" /></td>
				<td class="tdBase"><a href="javascript:volProperties('<bean:write name='volume' property='id'/>');">Propiedades</a></td>
				<td class="tdBase"><a href="javascript:volEdit('<bean:write name='volume' property='id'/>');">Editar</a></td>
			</tr>
		</c:otherwise>
	</c:choose>

	</logic:iterate>
	<c:if test="${requestScope.tipo != 2}"> <%-- Si Ocultar la opcion, nuevo volumen, si se muestra los volumenes asociados a listas --%>
	<tr>
		<td colspan="7"><a href="javascript:volNew();">Nuevo Volumen</a></td>
	</tr>
	</c:if>
	</table>

	<c:if test="${requestScope.tipo == 2}">
	<html:form action="/volume/volumeList" styleId="changeOrderForm">
		<table align="center" width="30%">
			<tr>
				<td><input type="button" value="Subir" onclick="up();"></td>
				<td><input type="button" value="Bajar" onclick="down();"></td>
				<td><input type="button" value="Cambiar orden" onclick="changeOrder();"></td>
			</tr>
		</table>
		<input type="hidden" name="order" id="order"/>
	</html:form>
	</c:if>


</body>
 -->


</html>