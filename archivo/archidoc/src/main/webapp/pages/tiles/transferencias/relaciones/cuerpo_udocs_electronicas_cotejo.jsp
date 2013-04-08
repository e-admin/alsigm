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
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="udocsElectronicas" value="${sessionScope[appConstants.transferencias.LISTA_UDOCS_ELECTRONICAS_COTEJO_KEY]}" />



<c:set var="estadoPendiente"><c:out value="${appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}"/></c:set>
<c:set var="estadoRevisada"><c:out value="${appConstants.transferencias.estadoCotejo.REVISADA.identificador}"/></c:set>
<c:set var="estadoErrores"><c:out value="${appConstants.transferencias.estadoCotejo.ERRORES.identificador}"/></c:set>

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
	var textarea = document.getElementById("observacioneserror"+idDocumento);
	if(mostrar){
		textarea.style.visibility='visible';
		textarea.rows=2;
	}
	else {
		textarea.style.visibility='hidden';
		textarea.rows=1;
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

	document.getElementById("imagenEstadoCaja").src=newImg;
}

function hayPendientes()
{
	
	var hayExpedientesPendientes = false;
	var numCajas = getNumDocumentosElectronicos();


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
	var numCajas = getNumDocumentosElectronicos();

	for (var i = 1; !hayExpedientesError && i <= numCajas; i++)
	{
		var radio = document.getElementById("error"+i);
		if (radio && radio.checked)
			hayExpedientesError = true;
	}
	return hayExpedientesError;
}


function saveVerUnidadDocumental(idUdoc)
{
	sinGuardar = false;
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarVerUnidadDocumentalElectronica";
	document.getElementById("idUdoc").value = idUdoc;
	form.submit();

}

function saveVerUnidadDocumentalEA(idUdoc)
{
	sinGuardar = false;
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarVerUnidadDocumentalElectronicaEA";
	document.getElementById("idUdoc").value = idUdoc;
	form.target="_self";
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
	form.method.value = "guardarCotejoUDocsElectronicas";
	form.submit();
}

function getNumDocumentosElectronicos(){
	var tabla = document.getElementById("tblUdocsElectronicas");
	var tbody = tabla.getElementsByTagName("tbody").item(0);
	return tbody.rows.length;
}
</SCRIPT>

<div id="contenedor_ficha">

<html:form action="/cotejoysignaturizacionAction" >
<html:hidden property="method" styleId="method"  value="guardarInfoCaja"/>
<html:hidden property="codigoseleccionada" styleId="codigoseleccionada"/>
<html:hidden property="idUdoc" styleId="idUdoc"/>


<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.transferencias.cotejoUnidadesElectronicas"/>							
		<c:set var="imageEstadoUInstalacion">/pages/images/pixel.gif</c:set>
	
		<nobr>
			&nbsp;<img id="imagenEstadoCaja" 
				src='<c:url value="${imageEstadoUInstalacion}"/>' 
				class="imgTextMiddle" />
		</nobr>
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
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

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">contenidoCaja</tiles:put>
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.transferencias.unidadesElectronicas"/>
		</tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
			<div class="separador5">&nbsp;</div>
			
			
			<display:table name="pageScope.udocsElectronicas"
						style="width:99%;margin-left:auto;margin-right:auto"
						id="udocElectronica" 
						export="false"
						htmlId="tblUdocsElectronicas"
						>

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noUDCaja" />
				</display:setProperty>
	
				<display:column title="" style="width:20px">
					<c:out value="${udocElectronica_rowNum}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.expediente" sortable="false" headerClass="sortable" style="width:150px">
					<c:choose>
					<c:when test="${vRelacion.entreArchivos}">
						<a href="javascript:saveVerUnidadDocumentalEA('<c:out value="${udocElectronica.id}" />')" class="tdlink">
							<c:out value="${udocElectronica.expediente}" />
						</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:saveVerUnidadDocumental('<c:out value="${udocElectronica.id}" />')" class="tdlink">
							<c:out value="${udocElectronica.expediente}" />
						</a>
					</c:otherwise>					
					</c:choose>					
				</display:column>					
				<display:column titleKey="archigest.archivo.transferencias.asunto">
						<c:out value="${udocElectronica.asunto}" />
				</display:column>
					<c:set var="parteUdocID" value="${udocElectronica.id}"/>
					
					<display:column title='<img src="../images/cajaPendiente.gif" 
											class="imgTextBottom"/>'
						style="width:20px">
						<input id='pendiente<c:out value="${udocElectronica_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${parteUdocID})"/>' 
						value='<c:out value="${estadoPendiente}"/>'
							<c:if test="${udocElectronica.estadoCotejo == estadoPendiente}">
								<c:out value="checked"/>
							</c:if>
						onClick="javascript:checkChange('<c:out value="${parteUdocID}"/>',1);"/>
					</display:column>
					<display:column title='<img src="../images/cajaRevisada.gif" 
											class="imgTextBottom"/>' 
						style="width:20px">
						<input id='revisada<c:out value="${udocElectronica_rowNum}"/>' 
						type="radio" name='<c:out value="estadocotejounidaddocumental(${parteUdocID})"/>' 
						value='<c:out value="${estadoRevisada}"/>'
							<c:if test="${udocElectronica.estadoCotejo == estadoRevisada}">
								<c:out value="checked"/>
							</c:if>
							onClick="javascript:checkChange('<c:out value="${parteUdocID}"/>',2);"/>
					</display:column>
					<display:column title='<img src="../images/cajaError.gif" 
											class="imgTextBottom"/>' 
						style="width:20px">
						<input id='error<c:out value="${udocElectronica_rowNum}"/>'						
						type="radio" name='<c:out value="estadocotejounidaddocumental(${parteUdocID})"/>' 
							value='<c:out value="${estadoErrores}"/>'
							<c:if test="${udocElectronica.estadoCotejo == estadoErrores}">
								<c:out value="checked"/>
							</c:if>
							onClick="javascript:checkChange('<c:out value="${parteUdocID}"/>',3);" />
					</display:column>
				<display:column titleKey="archigest.archivo.transferencias.observaciones">
					<textarea id='observacioneserror<c:out value="${parteUdocID}"/>' 
						name='<c:out value="observacioneserror(${parteUdocID})"/>' 
						rows="1" class="textarea99" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" ><c:out value="${udocElectronica.notasCotejo}" /></textarea>
					<c:choose>
						<c:when test="${udocElectronica.estadoCotejo == estadoErrores}">
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
<script type="text/javascript">checkImagenEstado();</script>
</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>
