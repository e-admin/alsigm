<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<%@ page import="common.OrganizationMessages" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<c:set var="unidadDocumental" value="${sessionScope[appConstants.transferencias.UNIDAD_DOCUMENTAL]}"/>
<c:set var="modificacionesEnTabsDeExpediente" value="${sessionScope[appConstants.transferencias.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE]}"/>
<bean:struts id="actionMapping" mapping="/gestionUdocsRelacion" />
<script>
	function cerrar(){
		<c:choose>
			<c:when test="${modificacionesEnTabsDeExpediente}">
				if (confirm("<bean:message key='archigest.warning.formularioModificado'/>")){
					document.forms["<c:out value="${actionMapping.name}" />"].action+="?method=goBack";
					document.forms["<c:out value="${actionMapping.name}" />"].submit();
				}
			</c:when>
			<c:otherwise>
				document.forms["<c:out value="${actionMapping.name}" />"].action+="?method=goBack";
				document.forms["<c:out value="${actionMapping.name}" />"].submit();
			</c:otherwise>
		</c:choose>
	}

	function guardarCambiosYNueva(url){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.operacion.guardandoInformacion"/>';
			var message = '<bean:message key="archigest.archivo.operacion.msgGuardandoInformacion"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		window.location=url;
	}

	function guardarCambios(url){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.operacion.guardandoInformacion"/>';
			var message = '<bean:message key="archigest.archivo.operacion.msgGuardandoInformacion"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		window.location=url;
	}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.datos"/>
		<bean:message key="archigest.archivo.transferencias.la"/>
		<c:choose>
			<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
				<bean:message key="archigest.archivo.transferencias.fraccionSerie"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.transferencias.unidadDoc"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<c:if test="${vRelacion.validada && !empty unidadDocumental.udocEnCuadroClasificacion && !empty vRelacion.nivelDocumental && unidadDocumental.udocEnCuadroClasificacion.subtipo == vRelacion.nivelDocumental.subtipo}">
			<security:permissions action="${appConstants.fondosActions.CONSULTA_ACTION}">
			<TD>
				<c:url var="showCFURL" value="/action/gestionUdocsCF">
					<c:param name="method" value="verEnFondos" />
					<c:param name="unidadDocumental" value="${unidadDocumental.udocEnCuadroClasificacion.id}" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${showCFURL}" escapeXml="false" />" target="_self">
					<html:img page="/pages/images/tree.gif" altKey="archigest.archivo.cf.verEnFondos" titleKey="archigest.archivo.cf.verEnFondos" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cf.verEnFondos"/>
				</a>
			</TD>
			</security:permissions>
			</c:if>
			<c:if test="${(vRelacion.abierta && empty vRelacion.iddeposito && !vRelacion.ordinaria)
							|| (vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador && vRelacion.conErroresCotejo && !vRelacion.ordinaria)}">
				<TD style="vertical-align:top">
					<c:url var="guardarCambiosYDuplicarURL" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="guardarCambiosYNueva" />
						<c:param name="mantenerInformacion" value="1" />
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${guardarCambiosYDuplicarURL}" escapeXml="false" />">
						<html:img page="/pages/images/duplicarDoc.gif" altKey="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar"
								  titleKey="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar"/>
					</a>
				</TD>
				<TD width="20px">&nbsp;</TD>
				<TD>
					<c:url var="guardarCambiosYNuevaURL" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="guardarCambiosYNueva" />
					</c:url>

					<a class="etiquetaAzul12Bold" href="javascript:guardarCambiosYNueva('<c:out value="${guardarCambiosYNuevaURL}" escapeXml="false"/>');">
						<html:img titleKey="archigest.archivo.guardarYNueva" altKey="archigest.archivo.guardarYNueva" page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.guardarYNueva"/>
					</a>
				</TD>
			</c:if>
			<TD width="20">&nbsp;</TD>
			<TD>
				<%--boton Cerrar --%>
				<c:url var="cerrarURL" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="goBack" />
				</c:url>
					<a class=etiquetaAzul12Bold href="javascript:cerrar()">
					<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>
		   </TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.ingresoDirecto"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp; </div>

		<div class="cabecero_bloque"> <%--segundo bloque de datos (Contador de unidades documentales siempre visible) --%>
			<bean:define id="showPrevNext" value="true" toScope="request"/>
			<jsp:include page="contador_unidaddocre.jsp" flush="true" />
		</div> <%--segundo bloque de datos (Contador de unidades documentales siempre visible) --%>

		<div class="separador8">&nbsp; </div>

	<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TBODY>
			  <TR>
				<TD class="etiquetaAzul12Bold" width="20%">
				<c:choose>
					<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
						<bean:message key="archigest.archivo.transferencias.fraccionSerie"/>
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.transferencias.unidadDoc"/>
					</c:otherwise>
				</c:choose>
				</TD>
					<c:if test="${unidadDocumental.permitidoRealizarCambios}">
					<TD width="80%" align="right">
						<TABLE cellpadding=0 cellspacing=0>
						  <TR>
							<c:url var="edicionUDocURL" value="/action/gestionUdocsRelacion">
								<c:param name="method" value="editarUnidadDocumental" />
							</c:url>
							<TD>
								<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${edicionUDocURL}" escapeXml="false"/>';">
									<html:img titleKey="archigest.archivo.editar" altKey="archigest.archivo.editar" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.editar"/>
								</a>
							</TD>
						  </TR>
						</TABLE>
					</TD>
				</c:if>
		</TR></TBODY></TABLE>
	</div>

	<div class="bloque">

		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<TR>
				<TD class="tdTitulo" width="220px">
				<c:choose>
					<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
						<bean:message key="archigest.archivo.transferencias.rangoExpedientes"/>:&nbsp;
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.transferencias.numExp"/>:&nbsp;
					</c:otherwise>
				</c:choose>
				</TD>
				<TD class="tdDatos">
				<c:choose>
					<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
						<table class="tablaFicha">
							<thead>
								<tr>
									<th style="">Desde</th>
									<th style="">Hasta</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="rangosUdoc" items="${unidadDocumental.rangos}">
									<tr class="odd">
										<td style=""><c:out value="${rangosUdoc.desde}" /></td>
										<td style=""><c:out value="${rangosUdoc.hasta}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<c:out value="${unidadDocumental.numeroExpediente}" />
					</c:otherwise>
				</c:choose>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.asunto"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${unidadDocumental.asunto}" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.fIni"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="100px" class="td2Datos">
							<fmt:formatDate value="${unidadDocumental.fechaInicio}" pattern="${FORMATO_FECHA}" />
						</TD>
						<TD width="10px">
						</TD>
						<TD width="100px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFin"/>:&nbsp;
						</TD>
						<TD width="120px" class="td2Datos">
							<fmt:formatDate value="${unidadDocumental.fechaFin}" pattern="${FORMATO_FECHA}" />
						</TD>
					</TR>
					</TABLE>
				</TD>
			</TR>
			<c:set var="udoc" value="${unidadDocumental.partesUdoc[0]}"/>
			<c:if test="${!empty udoc.signaturaUdoc && (!vRelacion.formato.multidoc || vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador)}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.signatura"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${udoc.signaturaUdoc}" />
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.orgProd"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:set var="productor" value="${unidadDocumental.productor}"/>
					<c:out value="${productor.nombre}" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.nDocs"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${unidadDocumental.numeroDocumentos}" />
				</TD>
			</TR>
			<c:if test="${unidadDocumental.estadoCotejo > 0}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.cotejoUDoc"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${unidadDocumental.estadoCotejo}" />
					</TD>
				</TR>
			</c:if>
		</TABLE>

	</div> <%--expediente --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
</div>

	<script>
		var tabPanel = new TabPanel("tabsInfoUdoc");
		tabPanel.addTab(new Tab("emplazamientos", "emplazamientos"));
		tabPanel.addTab(new Tab("interesados", "interesados"));
		tabPanel.addTab(new Tab("documentos", "documentos"));
		<c:if test="${!vRelacion.ordinaria}">
			tabPanel.addTab(new Tab("soportes", "soportes"));
		</c:if>
	</script>

	<div class="cabecero_tabs">

			<table cellspacing="0" cellpadding=0 width="99%" style="table-layout:fixed">
				<tr>
			    	<td width="100px" class="tabActual" id="pinteresados" onclick="javascript:tabPanel.showTab('interesados');">
						<a href="javascript:tabPanel.showTab('interesados');" id="tinteresados" class="textoPestana">
							<bean:message key="archigest.archivo.transferencias.interesados"/>
						</a>
				    </td>
					<td width="4px">&nbsp;</td>
			    	<td width="130px" class="tabSel" id="pemplazamientos" onclick="javascript:tabPanel.showTab('emplazamientos');">
						<a href="javascript:tabPanel.showTab('emplazamientos');" id="templazamientos" class="textoPestana">
							<bean:message key="archigest.archivo.transferencias.emplazamientos"/>
						</a>
				    </td>
					<td width="4px">&nbsp;</td>
			    	<td width="110px" class="tabSel" id="pdocumentos" onclick="javascript:tabPanel.showTab('documentos');">
						<a href="javascript:tabPanel.showTab('documentos');" id="tdocumentos" class="textoPestana">
							<bean:message key="archigest.archivo.transferencias.documentos"/>
						</a>
				    </td>
					<c:if test="${!vRelacion.ordinaria}">
					<td width="4px">&nbsp;</td>
			    	<td width="115px" class="tabSel" id="psoportes" onclick="javascript:tabPanel.showTab('soportes');">
						<a href="javascript:tabPanel.showTab('soportes');" id="tsoportes" class="textoPestana">
							<bean:message key="archigest.archivo.transferencias.soportes"/>
						</a>
				    </td>
					</c:if>
					<td width="5px">&nbsp;</td>
					<TD align="right"><TABLE><TR>
						<c:if test="${unidadDocumental.permitidoRealizarCambios}">
							<TD style="padding-bottom:5px">
								<c:url var="guardarCambiosURL" value="/action/gestionUdocsRelacion">
									<c:param name="method" value="guardarCambios" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="javascript:guardarCambios('<c:out value="${guardarCambiosURL}" escapeXml="false"/>');">
									<html:img titleKey="archigest.archivo.guardar" altKey="archigest.archivo.guardar" page="/pages/images/save.gif" styleClass="imgTextMiddle"/>
									<bean:message key="archigest.archivo.guardar"/>
								</a>
							</TD>
						</c:if>
					</TR></TABLE></TD>
				</tr>
			</table>
	</div>

	<div id="interesados" class="bloque_tab" style="display:block">
		<c:url var="urlNuevoInteresado" value="/action/gestionInteresados">
			<c:param name="method" value="nuevoInteresado" />
		</c:url>
		<c:url var="urlCreacionInteresadoNoVal" value="/action/gestionInteresados">
			<c:param name="method" value="nuevoInteresadoNoVal" />
		</c:url>

		<script>
			function eliminarInteresados(selectionFormName) {
				if (confirm("<bean:message key='archigest.archivo.transferencias.eliminarInteresadosWarning'/>")) {
					var selectionForm = document.forms[selectionFormName];
					if (selectionForm)
						selectionForm.submit();
				}
			}
		</script>

		<div class="cabecero_bloque_tab">
			<c:if test="${unidadDocumental.permitidoRealizarCambios}">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TBODY>
				  <TR>
				    <TD width="100%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
					  <TR>
							<TD>
							<a class="etiquetaAzul12Normal" href="<c:out value="${urlNuevoInteresado}" escapeXml="false"/>">
								<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.anadir"/>
							</a>
							</TD>
					        <TD width="20">&nbsp;</TD>
							<bean:struts id="mappingGestionInteresados" mapping="/gestionInteresados" />
							<TD>
							<a class="etiquetaAzul12Normal" href="javascript:eliminarInteresados('<c:out value="${mappingGestionInteresados.name}" />')">
								<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.eliminar"/>
							</a>
							</TD>
				     </TR>
					</TABLE>
					</TD>
				</TR></TBODY></TABLE>
			</c:if>
		</div>

		<c:set var="interesados" value="${unidadDocumental.interesados}" />
		<c:set var="interesadoPrincipal" value="${unidadDocumental.interesadoPrincipal}" />

		<html:form action="/gestionInteresados" >
		<input type="hidden" name="method" value="eliminarInteresados">

		<display:table name="pageScope.interesados" id="interesado"
			style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto;margin-bottom:5px;">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.transferencias.noInteresados" />
			</display:setProperty>

			<c:if test="${unidadDocumental.permitidoRealizarCambios}">
				<display:column style="width:20px">
					<input type="checkbox" name="seleccionInteresado" value="<c:out value="${interesado_rowNum}" />" >
				</display:column>
			</c:if>

			<display:column titleKey="archigest.archivo.transferencias.principal" style="width:60px; text-align:center">
				<c:choose>
				<c:when test="${unidadDocumental.permitidoRealizarCambios}">
					<c:url var="seleccionInteresadoPrincipalURL" value="/action/gestionInteresados">
						<c:param name="method" value="seleccionarInteresadoPrincipal" />
						<c:param name="itemIndex" value="${interesado_rowNum}" />
					</c:url>
					<c:set var="linkID" value="linkSeleccionInteresadoPrincipal${interesado_rowNum}" />

						<c:choose>
							<c:when test="${interesado == interesadoPrincipal}">
								<a href="<c:out value="${seleccionInteresadoPrincipalURL}" escapeXml="false" />" class="tdlink" style="width:60px;" id='<c:out value="${linkID}" />'>
									<html:img titleKey="archigest.archivo.transferencias.interesadoPrincipal" altKey="archigest.archivo.transferencias.interesadoPrincipal" page="/pages/images/man.gif" styleClass="imgTextMiddle"/>
								</a>
							</c:when>
							<c:otherwise>
								<input type="radio" name="interesadoPrincipal" onclick="javascript:window.location='<c:out value="${seleccionInteresadoPrincipalURL}" escapeXml="false" />'">
							</c:otherwise>
						</c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="${interesado.principal}">
						<html:img titleKey="archigest.archivo.transferencias.interesadoPrincipal" altKey="archigest.archivo.transferencias.interesadoPrincipal" page="/pages/images/man.gif" styleClass="imgTextMiddle"/>
					</c:if>
				</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.validado" style="width:60px;text-align:center">
				<c:choose>
					<c:when test="${interesado.validado}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
					<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.nombre">
				<c:choose>
				<c:when test="${unidadDocumental.permitidoRealizarCambios}">
					<c:url var="edicionInteresadoURL" value="/action/gestionInteresados">
						<c:param name="method" value="editarInfoInteresado" />
						<c:param name="itemIndex" value="${interesado_rowNum-1}" />
					</c:url>
					<a href="<c:out value="${edicionInteresadoURL}" escapeXml="false"/>" class="tdlink">
						<c:out value="${interesado.nombre}" />
					</a>
				</c:when>
				<c:otherwise>
					<c:out value="${interesado.nombre}" />
				</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.idFiscal" property="numeroIdentificacion" />
			<display:column titleKey="archigest.archivo.rol" property="rol" />
		</display:table>
		</html:form>

	</div> <%--interesados --%>


	<div id="emplazamientos" class="bloque_tab" style="display:none">

		<div class="cabecero_bloque_tab">

			<c:url var="urlNuevonEmplazamiento" value="/action/composicionEmplazamiento">
				<c:param name="method" value="nuevoEmplazamiento" />
			</c:url>

			<script>
				function eliminarEmplazamientos(selectionFormName) {
					if (confirm("<bean:message key='archigest.archivo.transferencias.eliminarEmplazamientosWarning' />")) {
						var selectionForm = document.forms[selectionFormName];
						if (selectionForm)
							selectionForm.submit();
					}
				}
			</script>

			<c:if test="${unidadDocumental.permitidoRealizarCambios}">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TR>
				    <TD width="100%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
					  <TR>
							<TD>
							<a class="etiquetaAzul12Normal" href="<c:out value="${urlNuevonEmplazamiento}" escapeXml="false"/>">
								<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.anadir"/>
							</a>
							</TD>
					        <TD width="20">&nbsp;</TD>
							<bean:struts id="targetMapping" mapping="/composicionEmplazamiento" />
							<TD>
							<a class="etiquetaAzul12Normal" href="javascript:eliminarEmplazamientos('<c:out value="${targetMapping.name}" />')">
								<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.eliminar"/>
							</a>
							</TD>
				     </TR>
					</TABLE>
					</TD>
				</TR></TABLE>
			</c:if>
		</div>

		<c:set var="emplazamientos" value="${unidadDocumental.emplazamientos}" />

		<html:form action="/composicionEmplazamiento">
		<input type="hidden" name="method" value="eliminarEmplazamientos">

		<display:table name="pageScope.emplazamientos" id="emplazamiento"
			style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto;margin-bottom:5px;">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.transferencias.noEmplazamiento" />
			</display:setProperty>

			<c:if test="${unidadDocumental.permitidoRealizarCambios}">
				<display:column style="width:20px">
					<input type="checkbox" name="seleccionEmplazamiento" value="<c:out value="${emplazamiento_rowNum}" />" >
				</display:column>
			</c:if>

			<display:column style="width:20px">
				<c:choose>
					<c:when test="${emplazamiento.validado == 'S'}">
						<html:img  page="/pages/images/checkbox-yes.gif" titleKey="archigest.archivo.transferencias.emplzamientoValidado" styleClass="imgTextMiddle"/>
					</c:when>
					<c:otherwise>
						<html:img  page="/pages/images/checkbox-no.gif" titleKey="archigest.archivo.transferencias.emplzamientoNoValidado" styleClass="imgTextMiddle"/>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.pais" property="pais"/>
			<display:column titleKey="archigest.archivo.transferencias.comunidad" property="comunidad"/>
			<display:column titleKey="archigest.archivo.transferencias.municipio">
				<c:choose>
				<c:when test="${cambiosPermitidos && !relacionEnArchivo}">
					<c:url var="urlEdicionEmplazamiento" value="/action/composicionEmplazamiento">
						<c:param name="method" value="editarEmplazamiento" />
						<c:param name="numEmplazamiento" value="${emplazamiento_rowNum}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${urlEdicionEmplazamiento}" escapeXml="false"/>" >
						<c:out value="${emplazamiento.concejo}" />
					</a>
				</c:when>
				<c:otherwise>
					<c:out value="${emplazamiento.concejo}" />
				</c:otherwise>
				</c:choose>
			</display:column>

			<display:column titleKey="archigest.archivo.transferencias.poblacion" property="poblacion" />
			<display:column titleKey="archigest.archivo.transferencias.localizacion" property="localizacion" />


		</display:table>
		</html:form>
	</div> <%--emplazamientos --%>


	<div id="documentos" class="bloque_tab" style="display:none">
		<c:url var="urlCreacionDocumento" value="/action/gestionUdocsRelacion">
			<c:param name="method" value="nuevoDocumento" />
		</c:url>

		<div class="cabecero_bloque_tab">
			<script>
			function eliminarDocumentos(selectionFormName) {
				if (confirm("<bean:message key='archigest.archivo.transferencias.documentosEliminarWarning'/>")) {
					var selectionForm = document.forms[selectionFormName];
					if (selectionForm)
						selectionForm.submit();
				}
			}
			</script>

			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TR>
				<TD width="100%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
					<bean:struts id="mappingGestionDocumentos" mapping="/gestionUdocsRelacion" />
					<c:if test="${unidadDocumental.permitidoRealizarCambios}">
						<TD>
						<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${urlCreacionDocumento}" escapeXml="false"/>'">
							<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.anadir"/>
						</a>
						</TD>
						<TD width="20">&nbsp;</TD>
						<TD>
						<a class="etiquetaAzul12Normal" href="javascript:eliminarDocumentos('<c:out value="${mappingGestionDocumentos.name}" />')">
							<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.eliminar"/>
						</a>
						</TD>
					</c:if>
				 </TR>
				</TABLE>
				</TD>
			</TR></TABLE>
		</div>


		<c:set var="documentos" value="${unidadDocumental.documentosFisicos}" />

		<c:if test="${!empty documentos}">
			<div class="separador8">&nbsp;</div>
			<div class="w98" style="text-align:left;">
				<bean:message key="archigest.archivo.transferencias.documentos.fisicos.title"/>:
			</div>
		</c:if>
		<html:form action="/gestionUdocsRelacion">

		<div class="separador1">
			<input type="hidden" name="method" value="eliminarDocumentos">
			<input type="hidden" name="tipoDocumento" value="<c:out value="${appConstants.transferencias.tiposDocumento.DOCUMENTO_FISICO}" />" >
		</div>
		<%--Documentos --%>
		<display:table name="pageScope.documentos" id="documento"
			style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto;margin-bottom:5px;">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.transferencias.noDocumentos" />
			</display:setProperty>

			<c:if test="${unidadDocumental.permitidoRealizarCambios}">
				<display:column style="width:20px">
					<input type="checkbox" name="seleccionDocumento" value="<c:out value="${documento_rowNum}" />" >
				</display:column>
			</c:if>

			<display:column titleKey="archigest.archivo.transferencias.tipoDoc" property="nombre" />

			<display:column titleKey="archigest.archivo.transferencias.asunto" style="width:60%">
				<c:choose>
				<c:when test="${cambiosPermitidos && !relacionEnArchivo}">
					<c:url var="edicionURL" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="editarDocumento" />
						<c:param name="itemIndex" value="${documento_rowNum-1}" />
					</c:url>
					<a href="<c:out value="${edicionURL}" escapeXml="false"/>" class="tdlink">
						<c:out value="${documento.descripcion}" />
					</a>
				</c:when>
				<c:otherwise>
					<c:out value="${documento.descripcion}" />
				</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
	</html:form>

	<c:set var="documentosElectronicos" value="${unidadDocumental.documentosElectronicos}" />
		<c:if test="${!empty documentosElectronicos}">
			<div class="separador5">&nbsp;</div>
			<div class="w98" style="text-align:left;">
				<bean:message key="archigest.archivo.transferencias.documentos.electronicos.title"/>:
			</div>
			<display:table
				name="pageScope.documentosElectronicos"
				id="documentoElectronico"
				style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto;margin-bottom:5px;">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noDocumentos" />
				</display:setProperty>

				<display:column titleKey="archigest.archivo.transferencias.asunto" property="nombre" style="width:40%" />
				<display:column titleKey="archigest.archivo.transferencias.tipoDoc" property="descripcion"/>
				<display:column titleKey="archigest.archivo.transferencias.repositorio" property="repositorio" />
			</display:table>
			<div class="separador8">&nbsp;</div>
		</c:if>

	</div> <%--documentos --%>

	<c:if test="${!vRelacion.ordinaria}">

	<div id="soportes" class="bloque_tab" style="display:none">
		<c:url var="urlAltaSoporte" value="/action/gestionSoportes">
			<c:param name="method" value="altaSoporte" />
		</c:url>

		<div class="cabecero_bloque_tab">
			<script>
			function eliminarSoportes(selectionFormName) {
				if (confirm("<bean:message key='archigest.archivo.transferencias.eliminarSoportesWarning'/>")) {
					var selectionForm = document.forms[selectionFormName];
					if (selectionForm)
						selectionForm.submit();
				}
			}
			</script>

			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TR>
				<TD width="100%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
					<bean:struts id="mappingGestionSoportes" mapping="/gestionSoportes" />
					<c:if test="${unidadDocumental.permitidoRealizarCambios}">
						<TD>
						<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${urlAltaSoporte}" escapeXml="false"/>'">
							<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.anadir"/>
						</a>
						</TD>
						<TD width="20">&nbsp;</TD>
						<TD>
						<a class="etiquetaAzul12Normal" href="javascript:eliminarSoportes('<c:out value="${mappingGestionSoportes.name}" />')">
							<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.eliminar"/>
						</a>
						</TD>
					</c:if>
				 </TR>
				</TABLE>
				</TD>
			</TR></TABLE>
		</div>


		<c:set var="soportes" value="${unidadDocumental.soportes}" />

		<html:form action="/gestionSoportes">
		<input type="hidden" name="method" value="eliminarSoportes">

		<display:table name="pageScope.soportes" id="infoVolumen"
			style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto;margin-bottom:5px;">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.transferencias.noSoportes" />
			</display:setProperty>

			<c:if test="${unidadDocumental.permitidoRealizarCambios}">
				<display:column style="width:20px">
					<input type="checkbox" name="posSoporteSeleccionado" value="<c:out value="${infoVolumen_rowNum}" />" >
				</display:column>
			</c:if>

			<display:column titleKey="archigest.archivo.transferencias.volumen" property="volumen" style="width:150px" />
			<display:column titleKey="archigest.archivo.transferencias.formato" property="formato" />

			<display:column titleKey="archigest.archivo.transferencias.soporte">
				<c:choose>
				<c:when test="${cambiosPermitidos && !relacionEnArchivo}">
					<c:url var="edicionURL" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="editarSoporte" />
						<c:param name="itemIndex" value="${infoVolumen_rowNum-1}" />
					</c:url>
					<a href="<c:out value="${edicionURL}" escapeXml="false"/>" class="tdlink">
						<c:out value="${infoVolumen.soporte}" />
					</a>
				</c:when>
				<c:otherwise>
					<c:out value="${infoVolumen.soporte}" />
				</c:otherwise>
				</c:choose>
			</display:column>

		</display:table>
		</html:form>
	</div> <%--soportes --%>
	</c:if>
	<script>
		if (typeof tabPanel != 'undefined') {
			tabPanel.showTab(getCookie("tabsInfoUdoc"));
		}
	</script>

	</tiles:put>
</tiles:insert>