<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<c:set var="detallePrevision" value="${__INFO_DETALLE}" />

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="estadoAbierta" value="${appConstants.transferencias.estadoPrevision.ABIERTA.identificador}" />
<c:set var="estadoRechazada" value="${appConstants.transferencias.estadoPrevision.RECHAZADA.identificador}" />

<c:choose>
<c:when test="${param.method == 'nuevoDetalle'}">
	<c:set var="__ACTION_TO_PERFORM" scope="session" value="crearDetalle" />
</c:when>
<c:when test="${param.method == 'editarDetalle'}">
	<c:set var="__ACTION_TO_PERFORM" scope="session" value="guardarDetalle" />
</c:when>
</c:choose>

<script>
	function mostrarListaProcedimientos(formName) {
		var form = document.forms[formName];
		form.elements['method'].value="listaProcedimientos";
		form.submit();
	}
	function mostrarListaSeries(formName) {
		var form = document.forms[formName];
		form.elements['method'].value="listarSeries";
		form.submit();
	}

	function mostrarBuscadorSeries(formName) {
		var buscadorSeries = xGetElementById('buscadorSerie');
		if (!buscadorSeries || buscadorSeries.style.display == 'none') {
			var form = document.forms[formName];
			form.elements['method'].value="verBuscadorSeries";
			form.submit();
		} else
			xDisplay(buscadorSeries, 'none');
	}

	function mostrarBuscadorProcedimiento() {
		var buscadorProcedimiento = xGetElementById('seleccionProcedimiento');
		if ((buscadorProcedimiento.style.display == 'none')||(buscadorProcedimiento.className == 'bloque_busquedas_oculto')) {
			xDisplay(buscadorProcedimiento, 'block');
		} else {
			xDisplay('listaProcedimientos', 'none');
			xDisplay(buscadorProcedimiento, 'none');
		}
	}

</script>
<bean:struts id="mappingGestionDetallesPrevision" mapping="/gestionDetallesPrevision" />
<c:set var="detallePrevisionFormName" value="${mappingGestionDetallesPrevision.name}" />
<c:set var="detallePrevisionForm" value="${requestScope[detallePrevisionFormName]}" />

<div id="contenedor_ficha">

<html:form action="/gestionDetallesPrevision">
<input type="hidden" name="method" value="<c:out value="${__ACTION_TO_PERFORM}" />">

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.transferencias.edicion"/><bean:message key="archigest.archivo.transferencias.ele"/>
			<bean:message key="archigest.archivo.transferencias.contenido"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<TD>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${detallePrevisionFormName}" />'].submit()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="cancelURI" value="/action/gestionDetallesPrevision">
						<c:param name="method" value="goBack"  />
					</c:url>
					<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.cancelar"/>
					</a>
				</TD>
			</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	</div>
</span></h1>



<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
			<jsp:include page="cabeceracte_prevision.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
</div>

<div class="bloque"> <%--primer bloque de datos --%>

	<table class="formulario">
		<c:if test="${bPrevision.ordinaria}">
			<tr>
				<td class="tdTitulo" width="200px" >
					<bean:message key="archigest.archivo.transferencias.sistProd"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<input name="sistemaProductor" type="text" class="inputRO99" readOnly=1
						value='<c:out value="${detallePrevisionForm.nombreSistemaProductor}" />'>
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="tdTitulo" width="120px" >
				<bean:message key="archigest.archivo.transferencias.procedimiento"/>:&nbsp;
				<html:hidden property="procedimiento" />
				<html:hidden property="serieDestino" />
			</td>
			<td class="tdDatos">
			<div>
				<input type="hidden" name="nombreProcedimiento" id="nombreProcedimiento"
					value="<c:out value="${detallePrevisionForm.nombreProcedimiento}" />">
				<input name="etiquetaProcedimiento" id="etiquetaProcedimiento" type="text" class="input95"
					value='<c:out value="${detallePrevisionForm.procedimiento}" />&nbsp;<c:out value="${detallePrevisionForm.nombreProcedimiento}" />' />
				<c:if test="${bPrevision.estado == estadoAbierta || bPrevision.estado == estadoRechazada}">
					<c:choose>
						<c:when test="${bPrevision.permitidaSeleccionProcedimiento}">
							<c:set var="enlaceSeleccionProcedimiento" value="javascript:mostrarBuscadorProcedimiento()" />
							<c:set var="permitidaBusquedaProcedimiento" value="${true}" />
						</c:when>
						<c:otherwise>
							<c:set var="enlaceSeleccionProcedimiento" value="javascript:mostrarListaProcedimientos('${mappingGestionDetallesPrevision.name}')" />
						</c:otherwise>
					</c:choose>
					<a href="<c:out value="${enlaceSeleccionProcedimiento}" escapeXml="false"/>">
						<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.transferencias.procedimientos" altKey="archigest.archivo.transferencias.procedimientos" styleClass="imgTextMiddleHand" />
					</a>
					<c:set var="listaProcedimientos" value="${requestScope[appConstants.transferencias.LISTA_PROCEDIMIENTOS_KEY]}" />

<div id="seleccionProcedimiento" class="bloque_busquedas<c:if test="${listaProcedimientos == null}">_oculto</c:if>">
	<script>
		function buscarProcedimientos(formName) {
			var form = document.forms[formName];
			form.elements['method'].value="buscarProcedimiento";
			form.submit();
		}
	</script>
	<div id="buscadorProcedimiento" <c:if test="${!permitidaBusquedaProcedimiento}">style="display:none"</c:if>>
		<div class="cabecero_bloque_tab">
			<TABLE width="100%" cellpadding=0 cellspacing=0>
			  <TR>
				<TD width="100%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
						<TD>
						<a class="etiquetaAzul12Normal" href="javascript:buscarProcedimientos('<c:out value="${mappingGestionDetallesPrevision.name}" />')">
							<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.buscar"/>
						</a>
						</TD><td width="10px">&nbsp;</td>
				 </TR>
				</TABLE>
				</TD>
			</TR></TABLE>
		</div>

		<c:url var="gestionDetallePrevision" value="/action/gestionDetallesPrevision" />
		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" width="175px"><bean:message key="archigest.archivo.cf.tipoProcedimiento"/>:&nbsp;</td>
				<td class="tdDatos">
					<table cellpadding="0" cellspacing="0"><tr>
						<td class="etiquetaNegra11Normal"><bean:message key="archigest.archivo.cf.procedimientoAutomatizado"/></td>
						<td><input type="radio" class="radio" name="tipoProcedimiento" value="2" <c:if test="${empty param.tipoProcedimiento || param.tipoProcedimiento == '2'}">checked</c:if>></td>
						<td class="etiquetaNegra11Normal">&nbsp;&nbsp;<bean:message key="archigest.archivo.cf.procedimientoNoAutomatizado"/></td>
						<td><input class="radio" type="radio" name="tipoProcedimiento" value="3" <c:if test="${param.tipoProcedimiento == '3'}">checked</c:if>></td>
					</tr></table>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.transferencias.titulo"/>:&nbsp;</td>
				<td class="tdDatos"><input type="text" size="40" name="tituloProcedimiento" value="<c:out value="${param.tituloProcedimiento}" />"></td>
			</tr>
		</table>
	</div>

	<c:if test="${listaProcedimientos != null}">
		<script>
			function selectProcedimiento(item) {
				var formName = '<c:out value="${mappingGestionDetallesPrevision.name}" />';
				var form = document.forms[formName];
				form.procedimiento.value = item.id;
				form.serieDestino.value = item.idSerieDestino;
				form.etiquetaProcedimiento.value = item.id+' '+item.getAttribute('procedimiento');
				form.nombreProcedimiento.value = item.getAttribute('procedimiento');
				form.nombreSerieDestino.value = item.getAttribute('serieDestino');
				form.funcion.value = item.getAttribute('funcion');
				if (form.sistemaProductor) {
					form.sistemaProductor.value = item.getAttribute('sistemaProductor');
				}
				hideListaProcedimientos();
			}
		</script>
			<div id="listaProcedimientos" style="width:99.9%;height:100px;overflow:auto;background-color:#efefef;">
			<c:choose>
			<c:when test="${!empty listaProcedimientos}">
			<c:forEach var="procedimiento" items="${listaProcedimientos}">
				<c:set var="serieProcedimiento" value="${procedimiento.serie}" />
				<div class="etiquetaGris12Normal" id='<c:out value="${procedimiento.codigo}" />'
						procedimiento='<c:out value="${procedimiento.nombre}" />'
						idSerie='<c:out value="${serieProcedimiento.id}" />'
						serieDestino='<c:out value="${serieProcedimiento.codigo} ${serieProcedimiento.denominacion}" />'
						funcion='<c:out value="${procedimiento.funcion.codReferencia}" />&nbsp;<c:out value="${procedimiento.funcion.titulo}" />'
						codigoSistemaProductor='<c:out value="${procedimiento.codSistProductor}" />'
						sistemaProductor='<c:out value="${procedimiento.nombreSistProductor}" />'
						onmouseOver="this.style.backgroundColor='#dedede'"
						onmouseOut="this.style.backgroundColor='#efefef'"
						onclick="selectProcedimiento(this)" style='padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
					<c:out value="${procedimiento.codigo}" />&nbsp;&nbsp;
					<c:out value="${procedimiento.nombre}" /></div>
			</c:forEach>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.transferencias.prev.noProcedimientos"/>
			</c:otherwise>
			</c:choose>
			</div>
			<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6"  style="border-top:1px solid #000000">
			<a class=etiquetaAzul12Normal href="javascript:hideListaProcedimientos()">
				<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;
			</a>
			</td></tr></table>
			<script>
				function hideListaProcedimientos() {
					xDisplay('seleccionProcedimiento', 'none');
					xDisplay('listaProcedimientos', 'none');
				}
			</script>
	</c:if>
</div>

				</c:if>
			</div>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.serie"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:set var="canSelectSerie" value="${!bPrevision.ordinaria && (bPrevision.estado == estadoAbierta || bPrevision.estado == estadoRechazada)}" />
				<c:set var="inputClass" value="input95" />
				<c:if test="${!canSelectSerie}"><c:set var="inputClass" value="inputRO99" /></c:if>
				<input name="nombreSerieDestino" id="nombreSerieDestino" type="text" class="<c:out value="${inputClass}" />"
				value="<c:out value="${detallePrevisionForm.nombreSerieDestino}" />" <c:if test="${!canSelectSerie}">readOnly=1</c:if>>
				<c:if test="${canSelectSerie}">
					<a href="javascript:mostrarBuscadorSeries('<c:out value="${mappingGestionDetallesPrevision.name}" />')">
					<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.series" altKey="archigest.archivo.series" styleClass="imgTextMiddleHand" />
					</a>
					<jsp:include page="buscador_serie.jsp" flush="true" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.funcion"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<input name="funcion" id="funcion" type="text" class="inputRO99" readOnly="readonly"
					value='<c:out value="${detallePrevisionForm.funcion}" />'/>
			</td>
		</tr>
		<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
		<TR>
			<TD class="tdTitulo" colspan="2">
				<bean:message key="archigest.archivo.transferencias.rangoFechas"/>:&nbsp;
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">&nbsp;</TD>
			<TD class="tdDatos">
				<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="100px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fechaInicioExps"/>:&nbsp;
						</TD>
						<TD width="50px">
							<html:text property="fechaInicio" size="5" maxlength="4"/>
						</TD>
						<TD width="20px">
						</TD>
						<TD width="100px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fechaFinExps"/>:&nbsp;&nbsp;
						</TD>
						<TD width="50px">
							<html:text property="fechaFin" size="5" maxlength="4"/>
						</TD>
					</TR>
				</TABLE>
			</TD>
		</tr>
		<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<html:text property="numUInstalacion" size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:set var="formatos" value="${sessionScope[appConstants.transferencias.LISTA_FORMATOS_KEY]}" />
				<html:select property="formato">
					<html:options collection="formatos" labelProperty="nombre" property="id" />
				</html:select>
			</td>
		</tr>
		<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
		<tr>
			<td class="tdTitulo" style="vertical-align:top">
				<bean:message key="archigest.archivo.transferencias.observaciones"/>:
			</td>
			<td class="tdDatos">
				<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
			</td>
		</tr>
	</table>

</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>

