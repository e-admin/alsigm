<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/cotejoysignaturizacionAction" />

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="relacionMultidoc" value="${vRelacion.formato.multidoc}" />
<c:set var="cajas" value="${sessionScope[appConstants.transferencias.CAJAS_KEY]}" />
<c:set var="udocsElectronicas" value="${sessionScope[appConstants.transferencias.LISTA_UDOCS_ELECTRONICAS_KEY]}"/>

<c:set var="VER_BOTON_SIGNATURAR" value="${requestScope[appConstants.transferencias.VER_BOTON_SIGNATURAR_KEY]}" />
<c:set var="VER_BOTON_ACEPTAR_COTEJO" value="${requestScope[appConstants.transferencias.VER_BOTON_ACEPTAR_COTEJO_KEY]}" />
<c:set var="VER_BOTON_FINALIZAR_COTEJO" value="${requestScope[appConstants.transferencias.VER_BOTON_FINALIZAR_COTEJO_KEY]}" />
<c:set var="VER_BOTON_GENERAR_CARTELAS" value="${requestScope[appConstants.transferencias.VER_BOTON_GENERAR_CARTELAS_KEY]}" />
<c:set var="PUEDE_SER_SIGNATURADA" value="${requestScope[appConstants.transferencias.PUEDE_SER_SIGNATURADA_KEY]}" />

<script>
var sinGuardar = false;

function activateFlagSinGuardar()
{
	sinGuardar = true;
}

function avisoSinGuardar(urlRedirect)
{
	if (sinGuardar)
	{
		if (confirm("<bean:message key="archigest.warning.formularioModificado"/>"))
			window.location = urlRedirect;
	}
	else{
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		window.location = urlRedirect;
	}
}


function finalizarCotejo(corregir)
{
	if(corregir == null || corregir == "undefined") corregir = "N";

	<c:url var="finalizarCotejoURL" value="/action/cotejoysignaturizacionAction">
		<c:param name="method" value="finalizarCotejo" />
		<c:param name="codigoseleccionada" value="${vRelacion.id}"/>
	</c:url>
	var direccion = '<c:out value="${finalizarCotejoURL}" escapeXml="false"/>';

	if(corregir == 'S'){
		direccion += "&correccionenarchivo=S";
	}

	avisoSinGuardar(direccion);
}

function marcarRevisada()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "marcarCajas";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}

function marcarUDocElectronicaRevisada()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "marcarUDocElectronicaRevisada";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}



function save()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarInfoCajaEntreArchivos";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}

function marcarConErroresCotejo()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "marcarCajasConErroresCotejo";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

function marcarUDocElectronicaConErroresCotejo()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "marcarUDocsElectronicaConErroresCotejo";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

function verCaja(idCaja, orden)
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "verCaja";
	form.idCaja.value = idCaja;
	form.ordenCaja.value = orden;

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

function verUDocElectronicas(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "verUDocsElectronicas";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}


function checkChange(idCaja, estado)
{
	activateFlagSinGuardar();
	if (estado == 1)
	{
		setVisibilityObservaciones(idCaja, false);
	}
	else if (estado == 2)
	{
		setVisibilityObservaciones(idCaja, false);
	}
	else if (estado == 3)
	{
		setVisibilityObservaciones(idCaja, true);
	}

}

function setVisibilityObservaciones(idCaja, mostrar)
{
	var textarea = document.getElementById("observacioneserror"+idCaja);
	var iddevolver = document.getElementById("iddevolver"+idCaja);

	if(mostrar){
		textarea.style.visibility='visible';
		textarea.rows=2;
		iddevolver.style.visibility='visible';
	}
	else {
		textarea.style.visibility='hidden';
		textarea.rows=1;
		iddevolver.style.visibility='hidden';
	}
}
</script>

<div id="contenedor_ficha">

<html:form action="/cotejoysignaturizacionAction" >
<html:hidden property="method" value="guardarestadocajas"/>
<html:hidden property="codigoseleccionada"/>
<html:hidden property="idCaja" />
<html:hidden property="estadoCotejo" />
<html:hidden property="ordenCaja" styleId="ordenCaja"/>


<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">

	<c:choose>
		<c:when test="${vRelacion.puedeSerSignaturada}">
			<bean:message key="archigest.archivo.transferencias.cotejoSignat"/>
		</c:when>
		<c:otherwise>
			<bean:message key="archigest.archivo.transferencias.cotejar"/>
		</c:otherwise>
	</c:choose>
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td>
				<c:url var="URL" value="/action/informeCotejo">
					<c:param name="idRelacion" value="${vRelacion.id}" />
		   		</c:url>
				<a class="etiquetaAzul12Bold"
				   href="<c:out value="${URL}" escapeXml="false"/>"
				><html:img page="/pages/images/documentos/doc_pdf.gif"
				        border="0"
				        altKey="archigest.archivo.informe"
				        titleKey="archigest.archivo.informe"
				        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.cotejo"/></a>
			</td>
			<TD width="10">&nbsp;</TD>

			<c:if test="${VER_BOTON_GENERAR_CARTELAS}">
			<TD>
				<c:url var="urlImprimir" value="/action/gestionRelaciones">
					<c:param name="method" value="selCartelas"/>
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
					<html:img page="/pages/images/cartela.gif"
						titleKey="archigest.archivo.transferencias.generarCartelas"
						altKey="archigest.archivo.transferencias.generarCartelas"
						styleClass="imgTextMiddle" />
					 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>

			</c:if>


			<TD>
				<a class="etiquetaAzul12Bold"
					href="javascript:finalizarCotejo();" >
					<c:if test="${VER_BOTON_FINALIZAR_COTEJO}">
					 <html:img page="/pages/images/caja_cotejoError.gif"
					 	titleKey="archigest.archivo.transferencias.finalizarCotejo"
					 	altKey="archigest.archivo.transferencias.finalizarCotejo"
					 	styleClass="imgTextMiddle"/>
					 <bean:message key="archigest.archivo.transferencias.finalizarCotejo"/>
					</c:if>
					<c:if test="${VER_BOTON_SIGNATURAR}">
					 <html:img page="/pages/images/finCotejo.gif"
					 	titleKey="archigest.archivo.transferencias.signaturar"
					 	altKey="archigest.archivo.transferencias.signaturar"
					 	styleClass="imgTextMiddle"/>
					 <c:choose>
					 	<c:when test="${PUEDE_SER_SIGNATURADA}">
						 	<bean:message key="archigest.archivo.transferencias.signaturar"/>
					 	</c:when>
					 	<c:otherwise>
						 	<bean:message key="archigest.archivo.transferencias.finalizarCotejo"/>
					 	</c:otherwise>
					 </c:choose>
					</c:if>
					<c:if test="${VER_BOTON_ACEPTAR_COTEJO}">
					 	<html:img page="/pages/images/caja_ok.gif"
					 		titleKey="archigest.archivo.transferencias.aceptarCotejo"
					 		altKey="archigest.archivo.transferencias.aceptarCotejo"
					 		styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.transferencias.aceptarCotejo"/>
					</c:if>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>

			<c:if test="${VER_BOTON_FINALIZAR_COTEJO}">
			<security:permissions action="${appConstants.transferenciaActions.CORRECCION_ERRORES_EN_ARCHIVO}">
			<TD>
				<a class="etiquetaAzul12Bold"
					href="javascript:finalizarCotejo('S');" >
					 <html:img page="/pages/images/caja_cotejoError.gif"
					 	titleKey="archigest.archivo.transferencias.finalizarCotejo.corregir.tooltip"
					 	altKey="archigest.archivo.transferencias.finalizarCotejo.y.corregir.tooltip"
					 	styleClass="imgTextMiddle"/>
					 <bean:message key="archigest.archivo.transferencias.finalizarCotejo.y.corregir"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			</security:permissions>
			</c:if>
			<TD>
				<c:url var="cancelURI" value="/action/cotejoysignaturizacionAction">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class="etiquetaAzul12Bold"
					href="javascript:avisoSinGuardar('<c:out value="${cancelURI}" escapeXml="false"/>');" >
					<html:img page="/pages/images/close.gif" border="0"
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar"
						styleClass="imgTextTop" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
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

<DIV class="cabecero_bloque_sin_height">
	<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
</DIV>

<div class="separador5">&nbsp;</div>

	<c:set var="estadoPendiente"><c:out value="${appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}"/></c:set>
	<c:set var="estadoRevisada"><c:out value="${appConstants.transferencias.estadoCotejo.REVISADA.identificador}"/></c:set>
	<c:set var="estadoErrores"><c:out value="${appConstants.transferencias.estadoCotejo.ERRORES.identificador}"/></c:set>

   	<c:if test="${not empty udocsElectronicas}">
	<div class="cabecero_bloque">
		<table class="w98m1" cellpadding="0" cellspacing="0" height="100%">
			<tr>
				<td class="etiquetaAzul12Bold">
				<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
				<bean:message key="archigest.archivo.transferencias.unidadesElectronicas" />				</td>
		    	<td align="right">
					<table cellpadding="0" cellspacing="0">
						<tr>
							   	<td><a class="etiquetaAzul12Bold"
									   href="javascript:marcarUDocElectronicaRevisada()"
									><html:img page="/pages/images/doc_electronico_ok.gif"
										altKey="archigest.archivo.transferencias.marcarRevisada"
										titleKey="archigest.archivo.transferencias.marcarRevisada"
										styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.transferencias.marcarRevisada"/></a>
									&nbsp;
									<a class="etiquetaAzul12Bold"
									   href="javascript:marcarUDocElectronicaConErroresCotejo()"
									><html:img page="/pages/images/doc_electronico_no_ok.gif"
										altKey="archigest.archivo.transferencias.marcarConErroresCotejo"
										titleKey="archigest.archivo.transferencias.marcarConErroresCotejo"
										styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.transferencias.marcarConErroresCotejo"/></a>
								</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<div class="bloque">
		<div class="separador5">&nbsp;</div>
		<table class="formulario">
			<tr>
				<td align="left">
				&nbsp;<a class="tdlink"
		   		href="javascript:verUDocElectronicas()">
				   <bean:message key="archigest.archivo.transferencias.cotejoUnidadesElectronicas"/>
				</a>
				</td>
				<td align="right">
					<a class="etiquetaAzul12Bold"
						href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].udocElectronicaRevisada);"
		 			><html:img page="/pages/images/checked.gif"
						    altKey="archigest.archivo.selTodas"
						    titleKey="archigest.archivo.selTodas"
						    styleClass="imgTextBottom" />
				    &nbsp;<bean:message key="archigest.archivo.selTodas"/></a>
					&nbsp;
					<a class="etiquetaAzul12Bold"
						href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].udocElectronicaRevisada);"
		 			><html:img page="/pages/images/check.gif"
						    altKey="archigest.archivo.quitarSel"
						    titleKey="archigest.archivo.quitarSel"
						    styleClass="imgTextBottom" />
				    &nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
					&nbsp;&nbsp;
			   </td>
			</tr>
		</table>
		<div class="separador1">&nbsp;</div>

		<display:table name="pageScope.udocsElectronicas"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="udocElectronica"
			export="false"
			>
			<display:column style="width:23px">
					<input type="checkbox" name="udocElectronicaRevisada"
						onChange="javascript:activateFlagSinGuardar()"
						value="<c:out value="${udocElectronica.id}"/>"/>
			</display:column>
			<display:column>
					<c:choose>
					<c:when test="${vRelacion.entreArchivos}">
						<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verEnFondos" />
							<c:param name="unidadDocumental" value="${udocElectronica.id}" />
						</c:url>

					</c:when>
					<c:otherwise>
						<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
							<c:param name="method" value="infoUnidadDocumental"/>
							<c:param name="udocID" value="${udocElectronica.id}"/>
						</c:url>
					</c:otherwise>
					</c:choose>
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink" target="_self">
							<c:out value="${udocElectronica.expediente}" />
						</a>
			</display:column>
			<display:column property="asunto"></display:column>
			<display:column titleKey="archigest.archivo.transferencias.estado"
				style="width:50px;text-align:center;">
				<c:choose>

					<c:when test="${udocElectronica.estadoCotejo==estadoRevisada}">
						<c:set var="imageEstado">/pages/images/cajaRevisada.gif</c:set>
					</c:when>
					<c:when test="${udocElectronica.estadoCotejo==estadoErrores}">
						<c:set var="imageEstado">/pages/images/cajaError.gif</c:set>
					</c:when>
					<c:otherwise>
						<c:set var="imageEstado">/pages/images/cajaPendiente.gif</c:set>
					</c:otherwise>
				</c:choose>
				<img src="<c:url value="${imageEstado}"/>"
					class="imgTextMiddle" />
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.observaciones"
				property="notasCotejo" />
		</display:table>
		<span class="separador5">&nbsp;</span>
	</div>
			<span class="separador5">&nbsp;</span>
		<span class="separador5">&nbsp;</span>
		<span class="separador5">&nbsp;</span>
		<span class="separador5">&nbsp;</span>

</c:if>

	<c:if test="${not empty cajas}">
	<div class="cabecero_bloque">
		<table class="w98m1" cellpadding="0" cellspacing="0" height="100%">
			<tr>
				<td class="etiquetaAzul12Bold">
					<html:img page="/pages/images/caja_closed.gif" altKey="archigest.archivo.transferencias.UndInstal" titleKey="archigest.archivo.transferencias.UndInstal" styleClass="imgTextBottom" />
					<bean:message key="archigest.archivo.transferencias.UndInstal" />
				</td>
		    	<td align="right">
					<table cellpadding="0" cellspacing="0">
						<tr>
						   	<c:if test="${!vRelacion.entreArchivos}">
							   	<td><a class="etiquetaAzul12Bold"
									   href="javascript:marcarRevisada()"
									><html:img page="/pages/images/caja_ok.gif"
										altKey="archigest.archivo.transferencias.marcarRevisada"
										titleKey="archigest.archivo.transferencias.marcarRevisada"
										styleClass="imgTextTop"/>
									&nbsp;<bean:message key="archigest.archivo.transferencias.marcarRevisada"/></a>
									&nbsp;
									<a class="etiquetaAzul12Bold"
									   href="javascript:marcarConErroresCotejo()"
									><html:img page="/pages/images/caja_cotejoError.gif"
										altKey="archigest.archivo.transferencias.marcarConErroresCotejo"
										titleKey="archigest.archivo.transferencias.marcarConErroresCotejo"
										styleClass="imgTextTop"/>
									&nbsp;<bean:message key="archigest.archivo.transferencias.marcarConErroresCotejo"/></a>
								</td>
							</c:if>
							<c:if test="${vRelacion.entreArchivos}">
								<td>
									<a class="etiquetaAzul12Bold"
									   href="javascript:save()"
									><html:img page="/pages/images/save.gif"
									        altKey="archigest.archivo.guardar"
									        titleKey="archigest.archivo.guardar"
									        styleClass="imgTextMiddle"/>
							        &nbsp;<bean:message key="archigest.archivo.guardar"/></a>
								</td>
							</c:if>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>


	<div class="bloque">
		<div class="separador5">&nbsp;</div>
		<c:if test="${!vRelacion.entreArchivos}">
		<table class="formulario">
			<tr>
				<td align="right">
					<a class="etiquetaAzul12Bold"
						href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].revisada);"
		 			><html:img page="/pages/images/checked.gif"
						    altKey="archigest.archivo.selTodas"
						    titleKey="archigest.archivo.selTodas"
						    styleClass="imgTextBottom" />
				    &nbsp;<bean:message key="archigest.archivo.selTodas"/></a>
					&nbsp;
					<a class="etiquetaAzul12Bold"
						href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].revisada);"
		 			><html:img page="/pages/images/check.gif"
						    altKey="archigest.archivo.quitarSel"
						    titleKey="archigest.archivo.quitarSel"
						    styleClass="imgTextBottom" />
				    &nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
					&nbsp;&nbsp;
			   </td>
			</tr>
		</table>
		</c:if>
		<div class="separador1">&nbsp;</div>

		<c:if test="${!vRelacion.entreArchivos}">
		<display:table name="pageScope.cajas"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="caja"
			export="false">
			<display:column style="width:23px" headerClass="marcarRevisadaIcon">
					<input type="checkbox" name="revisada"
						onChange="javascript:activateFlagSinGuardar()"
						value="<c:out value="${caja.id}"/>"/>
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.unidadInst"
				style="width:100px">
				<a class="tdlink"
					href="javascript:verCaja('<c:out value="${caja.id}"/>',<c:out value="${caja_rowNum}"/>)">
					<c:choose>
						<c:when test="${relacionMultidoc}">
							<bean:message key="archigest.archivo.transferencias.caja"/>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.transferencias.unidadInst"/>
						</c:otherwise>
					</c:choose>
					<c:out value="${caja_rowNum}"/>
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.signatura"
				property="signaturaUI" style="width:100px"/>
			<display:column titleKey="archigest.archivo.transferencias.estado"
				style="width:50px;text-align:center;">
				<c:choose>
					<c:when test="${caja.pendiente}">
						<c:set var="imageEstado">/pages/images/cajaPendiente.gif</c:set>
					</c:when>
					<c:when test="${caja.revisada}">
						<c:set var="imageEstado">/pages/images/cajaRevisada.gif</c:set>
					</c:when>
					<c:when test="${caja.conErrores}">
						<c:set var="imageEstado">/pages/images/cajaError.gif</c:set>
					</c:when>
				</c:choose>
				<img src="<c:url value="${imageEstado}"/>"
					class="imgTextMiddle" />
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.observaciones"
				property="notasCotejo" />
			<display:column titleKey="archigest.archivo.transferencias.devolver"
				style="width:100px;text-align:center;">
				<c:if test="${caja.devolver}">
					<html:img page="/pages/images/cajaDevuelta.gif" />
				</c:if>
			</display:column>
		</display:table>
		</c:if>
		<c:if test="${vRelacion.entreArchivos}">
			<display:table name="pageScope.cajas"
				style="width:99%;margin-left:auto;margin-right:auto" id="caja" export="false">

				<display:column titleKey="archigest.archivo.transferencias.unidadInst" style="width:100px">
					<c:choose>
						<c:when test="${vRelacion.entreArchivos}">
							<c:choose>
								<c:when test="${relacionMultidoc}">
									<bean:message key="archigest.archivo.transferencias.caja"/>
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.transferencias.unidadInst"/>
								</c:otherwise>
							</c:choose>
							<c:out value="${caja.orden}"/>
						</c:when>
						<c:otherwise>
							<a class="tdlink" href="javascript:verCaja('<c:out value="${caja.id}"/>')">
							<c:choose>
								<c:when test="${relacionMultidoc}">
									<bean:message key="archigest.archivo.transferencias.caja"/>
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.transferencias.unidadInst"/>
								</c:otherwise>
							</c:choose>
							<c:out value="${caja.orden}"/>
							</a>
						</c:otherwise>

					</c:choose>



				</display:column>
				<display:column titleKey="archigest.archivo.signatura" property="signaturaUI" style="width:100px"/>
				<display:column title='<img src="../images/cajaPendiente.gif" class="imgTextBottom"/>' style="width:20px">
					<input id='pendiente<c:out value="${caja_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${caja.id})"/>'
						value='<c:out value="${estadoPendiente}"/>'
						<c:if test="${caja.estadoCotejo == estadoPendiente}">
							<c:out value="checked"/>
						</c:if>
						onClick="javascript:checkChange('<c:out value="${caja.id}"/>',1);"/>
				</display:column>
				<display:column title='<img src="../images/cajaRevisada.gif" class="imgTextBottom"/>' style="width:20px">
					<input id='revisada<c:out value="${caja_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${caja.id})"/>'
						value='<c:out value="${estadoRevisada}"/>'
						<c:if test="${caja.estadoCotejo == estadoRevisada}">
							<c:out value="checked"/>
						</c:if>
						onClick="javascript:checkChange('<c:out value="${caja.id}"/>',2);"/>
				</display:column>
				<display:column title='<img src="../images/cajaError.gif" class="imgTextBottom"/>' style="width:20px">
					<input id='error<c:out value="${caja_rowNum}"/>'
						type="radio" name='<c:out value="estadocotejounidaddocumental(${caja.id})"/>'
						value='<c:out value="${estadoErrores}"/>'
						<c:if test="${caja.estadoCotejo == estadoErrores}">
							<c:out value="checked"/>
						</c:if>
						onClick="javascript:checkChange('<c:out value="${caja.id}"/>',3);" />
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.observaciones">
					<textarea id='observacioneserror<c:out value="${caja.id}"/>'
						name='<c:out value="observacioneserror(${caja.id})"/>'
						rows="1" class="textarea99" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" ><c:out value="${caja.notasCotejo}" /></textarea>
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.devolver" style="width:100px;text-align:center;">
					<input type="checkbox" id='iddevolver<c:out value="${caja.id}"/>'
						name='<c:out value="devolver${caja.id}"/>'
						<c:if test="${caja.devolucion == 'S'}">
							<c:out value="checked"/>
						</c:if>
					/>
					<c:choose>
						<c:when test="${caja.estadoCotejo == estadoErrores}">
							<SCRIPT>setVisibilityObservaciones('<c:out value="${caja.id}"/>',true)</SCRIPT>
						</c:when>
						<c:otherwise>
							<SCRIPT>setVisibilityObservaciones('<c:out value="${caja.id}"/>',false)</SCRIPT>
						</c:otherwise>
					</c:choose>
				</display:column>
			</display:table>
		</c:if>
		<span class="separador5">&nbsp;</span>
	</div>
	</c:if>
</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>
<h2><span>&nbsp;</span></h2>
</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>