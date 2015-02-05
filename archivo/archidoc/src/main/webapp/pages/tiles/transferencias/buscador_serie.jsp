<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}" />
<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>

<c:if test="${param.method == 'verBuscadorSeries' || param.method == 'buscarSerie'}">
<script>
	function buscarSeries(formName) {
		var form = document.forms[formName];
		form.elements['method'].value="buscarSerie";

		if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
</script>
<div id="buscadorSerie" class="bloque_busquedas">
<div class="cabecero_bloque_tab">
	<TABLE width="100%" cellpadding=0 cellspacing=0>
	  <TR>
		<TD width="100%" align="right">
		<TABLE cellpadding=0 cellspacing=0>
		  <TR>
				<TD>
				<bean:struts id="mappingGestionDetallesPrevision" mapping="/gestionDetallesPrevision" />
				<a class="etiquetaAzul12Normal" href="javascript:buscarSeries('<c:out value="${mappingGestionDetallesPrevision.name}" />')">
					<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.buscar"/>
				</a>		
				</TD><td width="10px">&nbsp;</td>
		 </TR>
		</TABLE>
		</TD>
	</TR></TABLE>
</div>

<c:url var="gestionDetallePrevision" value="/action/gestionDetallesPrevision" />
<div class="separador5"></div>

<table class="formulario" width="99%">
	<tr>
		<td class="tdTitulo" width="175px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
		<td class="tdDatos"><c:out value="${bPrevision.fondo.codReferencia}" />&nbsp;<c:out value="${bPrevision.fondo.titulo}" /></td>
	</tr>
	<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;
		</td>
		<td class="tdDatos"><input type="text" size="30" name="codigo" value="<c:out value="${param.codigo}" />"></td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;
		</td>
		<td class="tdDatos"><input type="text" class="input80" name="titulo" value="<c:out value="${param.titulo}" />"></td>
	</tr>
</table>




	<c:set var="listaSeries" value="${requestScope[appConstants.fondos.SERIE_KEY]}" />
	<c:if test="${!empty listaSeries}">
		<script>
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message);
			}

			function selectSerie(item) {
				var formName = '<c:out value="${mappingGestionDetallesPrevision.name}" />';
				var form = document.forms[formName];
				form.procedimiento.value = '';
				form.nombreProcedimiento.value = '';
				form.etiquetaProcedimiento.value = '';
				//form.serie.value = item.id;
				form.serieDestino.value = item.id;
				//form.nombreSerie.value = item.getAttribute('nombreSerie');
				form.nombreSerieDestino.value = item.getAttribute('nombreSerie');
				form.funcion.value = item.getAttribute('funcion');
				//form.idOrganoProductorDefecto.value = '';
				//form.nombreOrganoProductorDefecto.value = '';				
				hideBusquedaSeries();
			}
		</script>
		<div id="listaSeries" style="width:100%;height:100px;overflow:auto;background-color:#efefef" >
		<c:forEach var="serie" items="${listaSeries}">
			<div class="etiquetaGris12Normal" id='<c:out value="${serie.id}" />' 
					nombreSerie='<c:out value="${serie.codigo} ${serie.titulo}" />' 
					funcion='<c:out value="${serie.funcion.codReferencia} ${serie.funcion.titulo}" />'
					onmouseOver="this.style.backgroundColor='#dedede'"
					onmouseOut="this.style.backgroundColor='#efefef'"
					onclick="selectSerie(this)" style='padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
				<c:out value="${serie.codigo}" />&nbsp;&nbsp;
				<c:out value="${serie.denominacion}" /></div>
		</c:forEach>
		</div>
		<script>
			function hideBusquedaSeries() {
				xGetElementById('buscadorSerie').style.display='none';
			}
		</script>
		<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #000000">
		<a class=etiquetaAzul12Normal href="javascript:hideBusquedaSeries()">
			<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		</a>&nbsp;
		</td></tr></table>
	</c:if>
</c:if>
</div>