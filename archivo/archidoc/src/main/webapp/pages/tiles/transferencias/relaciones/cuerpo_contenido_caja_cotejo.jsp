<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/cotejoysignaturizacionAction" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="estadoPendiente" value="${appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}" />
<c:set var="estadoRevisada" value="${appConstants.transferencias.estadoCotejo.REVISADA.identificador}" />
<c:set var="estadoErrores" value="${appConstants.transferencias.estadoCotejo.ERRORES.identificador}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="caja" value="${sessionScope[appConstants.transferencias.CAJA_KEY]}" />
<jsp:useBean id="caja" type="transferencias.actions.UnidadInstalacionPO2" />

<SCRIPT>
var sinGuardar = false;

function activateFlagSinGuardar()
{
	sinGuardar = true;
}

function avisoSinGuardar(urlRedirect)
{
	if (sinGuardar)
	{
		if (confirm("<bean:message key="archigest.warning.formularioModificado"/>")) {
			var form = document.forms['<c:out value="${actionMapping.name}" />'];
			form.method.value = "closeCaja";
			form.submit();
		}
	}
	else {
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		form.method.value = "closeCaja";
		form.submit();
	}
}
function checkChange(idDocumento, estado)
{
	activateFlagSinGuardar();
	if (estado == 1)
	{
		setVisibilityObservaciones(idDocumento, false);
	}
	else if (estado == 2)
	{
		setVisibilityObservaciones(idDocumento, false);
	}
	else if (estado == 3)
	{
		setVisibilityObservaciones(idDocumento, true);
	}
	checkImagenEstado();
}
function setVisibilityObservaciones(idDocumento, mostrar)
{
	var divTextarea = document.getElementById("divObservacioneserror"+idDocumento);
	var tArea = document.getElementById("observacioneserror"+idDocumento);
	var divLabel = document.getElementById("observacioneserrorSL"+idDocumento);
	if(mostrar){
		divTextarea.style.display='block';
		divLabel.style.display='none';
	}
	else {
		divTextarea.style.display='none';
		divLabel.style.display='block';
		tArea.value="";
	}
}
function checkImagenEstado()
{
	var imageEstadoPendiente='<c:url value="/pages/images/cajaPendiente.gif" />';
	var imageEstadoRevisado='<c:url value="/pages/images/cajaRevisada.gif" />';
	var imageEstadoError='<c:url value="/pages/images/cajaError.gif" />';

	//establecer nueva imagen
	var newImg="";
	if (hayError())
		newImg=imageEstadoError;
	else if (hayPendientes())
		newImg=imageEstadoPendiente;
	else
		newImg=imageEstadoRevisado;

	configurarDevolver();
	document.getElementById("imagenEstadoCaja").src=newImg;
}

function hayPendientes()
{
	var hayExpedientesPendientes = false;
	var numCajas = Number("<%=caja != null ? caja.getContenido().size() : 0%>");
	for (var i = 1; !hayExpedientesPendientes && i <= numCajas; i++)
	{
		var radio = document.getElementById("pendiente"+i);
		if (radio && radio.checked)
			hayExpedientesPendientes = true;
	}
	return hayExpedientesPendientes;
}

function hayError()
{
	var hayExpedientesError = false;
	var numCajas = Number("<%=caja != null ? caja.getContenido().size() : 0%>");
	for (var i = 1; !hayExpedientesError && i <= numCajas; i++)
	{
		var radio = document.getElementById("error"+i);
		if (radio && radio.checked)
			hayExpedientesError = true;
	}
	return hayExpedientesError;
}

function configurarDevolver()
{
	if (!hayPendientes() && hayError())
		document.getElementById("iddevolver").disabled=false;
	else
	{
		document.getElementById("iddevolver").checked=false;
		document.getElementById("iddevolver").disabled=true;
	}
}

function saveVerUnidadDocumental(idUdoc)
{
	sinGuardar = false;
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarVerUnidadDocumental";
	document.getElementById("idUdoc").value = idUdoc;
	form.submit();

}

function verUnidadDocumental(idUdoc)
{
	sinGuardar = false;
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "verUnidadDocumental";
	document.getElementById("idUdoc").value = idUdoc;
	form.submit();

}

function save()
{
	sinGuardar = false;
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarInfoCaja";
	form.submit();
}

</SCRIPT>

<div id="contenedor_ficha">

<html:form action="/cotejoysignaturizacionAction" >
<html:hidden property="method" styleId="method"  value="guardarInfoCaja"/>
<html:hidden property="codigoseleccionada" styleId="codigoseleccionada"/>
<html:hidden property="idCaja" styleId="idCaja"/>
<html:hidden property="idUdoc" styleId="idUdoc"/>
<html:hidden property="ordenCaja" styleId="ordenCaja"/>

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.transferencias.cotejoCaja"/>
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<c:choose>
				<c:when test="${!vRelacion.entreArchivos}">
				   	<td>
						<a class="etiquetaAzul12Bold"
						   href="javascript:save()"
						><html:img page="/pages/images/save.gif"
						        altKey="archigest.archivo.guardar"
						        titleKey="archigest.archivo.guardar"
						        styleClass="imgTextMiddle"/>
				        &nbsp;<bean:message key="archigest.archivo.guardar"/></a>
					</td>
					<td width="10">&nbsp;</td>
				   	<td>
						<c:url var="cancelURI" value="/action/cotejoysignaturizacionAction">
							<c:param name="method" value="goBack" />
						</c:url>
					 	<a class="etiquetaAzul12Bold"
							href="javascript:avisoSinGuardar()" >
							<html:img page="/pages/images/close.gif" border="0"
								altKey="archigest.archivo.cerrar"
								titleKey="archigest.archivo.cerrar"
								styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>
						</a>
					</td>
				</c:when>
				<c:otherwise>
				   	<td>
						<c:url var="cancelURI" value="/action/cotejoysignaturizacionAction">
							<c:param name="method" value="goBack" />
						</c:url>
					 	<a class="etiquetaAzul12Bold"
							href="<c:out value="${cancelURI}"/>" >
							<html:img page="/pages/images/close.gif" border="0"
								altKey="archigest.archivo.cerrar"
								titleKey="archigest.archivo.cerrar"
								styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>
						</a>
					</td>
				</c:otherwise>
			</c:choose>
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

	<DIV class="cabecero_bloque_sin_height">
		<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
	</DIV>

	<div class="separador5">&nbsp;</div>

	<c:if test="${!vRelacion.entreArchivos}">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">infoCaja</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.cotejoCaja.caption"/>
			</tiles:put>
			<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<div class="separador5">&nbsp;</div>
				<table class="w98" cellpadding="0" cellspacing="0">
					<tr valign="top">
					    <td class="etiquetaAzul12Bold" width="100px" rowspan="3">
					    	<nobr>
							<bean:message key="archigest.archivo.transferencias.caja" />
							&nbsp;<c:out value="${form.ordenCaja}"/>:
							</nobr>
						</td>
						<td class="etiquetaAzul12Normal" width="250px">
					    	<nobr>
							<bean:message key="archigest.archivo.transferencias.signatura" />:&nbsp;
							<c:out value="${caja.signaturaUI}"/>
					    	</nobr>
						</td>
						<td class="etiquetaAzul12Normal" width="150px">
							<c:choose>
								<c:when test="${caja.pendiente}">
									<c:set var="imageEstadoUInstalacion">/pages/images/cajaPendiente.gif</c:set>
								</c:when>
								<c:when test="${caja.revisada}">
									<c:set var="imageEstadoUInstalacion">/pages/images/cajaRevisada.gif</c:set>
								</c:when>
								<c:when test="${caja.conErrores}">
									<c:set var="imageEstadoUInstalacion">/pages/images/cajaError.gif</c:set>
								</c:when>
							</c:choose>
							<nobr>
							<bean:message key="archigest.archivo.transferencias.estado" />:&nbsp;
							<img id="imagenEstadoCaja"
								src='<c:url value="${imageEstadoUInstalacion}"/>'
								class="imgTextMiddle" />
							</nobr>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="etiquetaAzul12Normal" colspan="3">
							<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
							<html:textarea property="notasCotejo"
								styleClass="textarea80_20" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"/>
						</td>
					</tr>
					<tr>
						<td class="etiquetaAzul12Normal" colspan="3">
							<bean:message key="archigest.archivo.transferencias.devolver" />:&nbsp;
							<c:set var="checkedDevolver" value=""/>
							<c:if test="${caja.devolver}">
								<c:set var="checkedDevolver" value="checked"/>
							</c:if>
							<input type="checkbox" id="iddevolver"
							name='<c:out value="devolver"/>' <c:out value="${checkedDevolver}"/>
							onClick="javascript:activateFlagSinGuardar()" />
						</TD>
					</tr>
				</table>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</c:if>

	<div class="separador5">&nbsp;</div>

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">contenidoCaja</tiles:put>
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.transferencias.cotejoCaja.contenido"/>
		</tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
			<div class="separador5">&nbsp;</div>
			<display:table name="pageScope.caja.contenido"
						style="width:99%;margin-left:auto;margin-right:auto"
						id="parteUdocumental"
						export="false"
						decorator="common.view.VisitedRowDecorator">

				<c:set var="parteUdocID" value="${parteUdocumental.idUnidadDoc}:${parteUdocumental.numParteUdoc}" />

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noUDCaja" />
				</display:setProperty>

				<display:column title="" style="width:20px">
					<c:out value="${parteUdocumental_rowNum}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.expediente" sortable="false" headerClass="sortable" style="width:150px">
					<c:out value="${parteUdocumental.expediente}"/>
					<c:if test="${parteUdocumental.totalPartes > 1}">
						(<c:out value="${parteUdocumental.numParteUdoc}"/>/<c:out value="${parteUdocumental.totalPartes}"/>)
					</c:if>
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.asunto">
					<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
						<c:param name="method">
						<c:choose>
						<c:when test="${relacionEnArchivo}">infoUnidadDocumentalAGestionar</c:when>
						<c:otherwise>infoUnidadDocumental</c:otherwise></c:choose>
						</c:param>
						<c:param name="udocID" value="${parteUdocumental.idUnidadDoc}" />
					</c:url>
					<c:choose>
						<c:when test="${!vRelacion.entreArchivos}">
							<a href="javascript:saveVerUnidadDocumental('<c:out value="${parteUdocumental.idUnidadDoc}" />')" class="tdlink">
								<c:out value="${parteUdocumental.asunto}" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:verUnidadDocumental('<c:out value="${parteUdocumental.idUnidadDoc}" />')" class="tdlink">
								<c:out value="${parteUdocumental.asunto}" />
							</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${!empty parteUdocumental.descContenido}">
							<br><span class="etiquetaNegra11Normal"><br><b><i><bean:message key="archigest.archivo.transferencias.descContenido"/>:</i></b> <c:out value="${parteUdocumental.descContenido}" /></span>
						</c:if>


				</display:column>
				<c:if test="${!vRelacion.entreArchivos}">
					<display:column title='<img src="../images/cajaPendiente.gif"
											class="imgTextBottom"/>'
						style="width:20px">
						<input id='pendiente<c:out value="${parteUdocumental_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${parteUdocID})"/>'
						value='<c:out value="${estadoPendiente}"/>'
							<c:if test="${parteUdocumental.estadoCotejo == estadoPendiente}">
								<c:out value="checked"/>
							</c:if>
						onClick="javascript:checkChange('<c:out value="${parteUdocID}"/>',1);"/>
					</display:column>
					<display:column title='<img src="../images/cajaRevisada.gif"
											class="imgTextBottom"/>'
						style="width:20px">
						<input id='revisada<c:out value="${parteUdocumental_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${parteUdocID})"/>'
						value='<c:out value="${estadoRevisada}"/>'
							<c:if test="${parteUdocumental.estadoCotejo == estadoRevisada}">
								<c:out value="checked"/>
							</c:if>
							onClick="javascript:checkChange('<c:out value="${parteUdocID}"/>',2);"/>
					</display:column>
					<display:column title='<img src="../images/cajaError.gif"
											class="imgTextBottom"/>'
						style="width:20px">
						<input id='error<c:out value="${parteUdocumental_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${parteUdocID})"/>'
							value='<c:out value="${estadoErrores}"/>'
							<c:if test="${parteUdocumental.estadoCotejo == estadoErrores}">
								<c:out value="checked"/>
							</c:if>
							onClick="javascript:checkChange('<c:out value="${parteUdocID}"/>',3);" />
					</display:column>
				</c:if>
				<display:column titleKey="archigest.archivo.transferencias.observaciones" style="width:200px">
					<div id='observacioneserrorSL<c:out value="${parteUdocID}"/>'>
						<c:out value="${parteUdocumental.notasCotejo}" />
					</div>
					<div id='divObservacioneserror<c:out value="${parteUdocID}"/>'>
					<textarea id='observacioneserror<c:out value="${parteUdocID}"/>'
							name='<c:out value="observacioneserror(${parteUdocID})"/>'
							rows="1" class="textarea99" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" ><c:out value="${parteUdocumental.notasCotejo}" /></textarea>
					</div>
					<c:choose>
						<c:when test="${parteUdocumental.estadoCotejo == estadoErrores}">
							<SCRIPT>setVisibilityObservaciones('<c:out value="${parteUdocID}"/>',true)</SCRIPT>
						</c:when>
						<c:otherwise>
							<SCRIPT>setVisibilityObservaciones('<c:out value="${parteUdocID}"/>',false)</SCRIPT>
						</c:otherwise>
					</c:choose>
				</display:column>
			</display:table>
			<span class="separador5">&nbsp;</span>
		</tiles:put>
	</tiles:insert>
</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>

<script>configurarDevolver()</script>
