<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>


<SCRIPT>

var identificadorPrevision = ' <c:out value="${bPrevision.codigoTransferencia}"/>';

function eliminarPrevision() {
	if (confirm("<bean:message key='archigest.archivo.transferencias.previsiones.delPrevCod1'/>"+identificadorPrevision+"<bean:message key='archigest.archivo.transferencias.previsiones.delPrevCod2'/>")) {
		<c:url var="URLEliminacion" value="/action/gestionPrevisiones">
			<c:param name="method" value="eliminarprevision" />
			<c:param name="idprevision" value="${bPrevision.id}" />
		</c:url>
		window.location = '<c:out value="${URLEliminacion}" escapeXml="false" />';
	}
}

function enviarPrevision() {
	if (confirm("<bean:message key='archigest.archivo.transferencias.previsiones.enviada1'/>"+identificadorPrevision+"<bean:message key='archigest.archivo.transferencias.previsiones.enviada2'/>")) {
		<c:url var="URLEnvio" value="/action/gestionPrevisiones">
			<c:param name="method" value="enviar" />
			<c:param name="idprevision" value="${bPrevision.id}" />
		</c:url>
		window.location = '<c:out value="${URLEnvio}" escapeXml="false" />';
	}
}
</SCRIPT>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.transferencias.datosPrevision"/>
	</TD>
	<TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
				<c:if test="${!bPrevision.detallada && bPrevision.numrentrega gt 0}">
					<TD>
						<c:url var="verRelacionesURL" value="/action/gestionRelaciones">
							<c:param name="method" value="verRelacionesPrevision" />
							<c:param name="idPrevision" value="${bPrevision.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${verRelacionesURL}" escapeXml="false"/>">
							 <html:img titleKey="archigest.archivo.transferencias.relacionesAsociadas" altKey="archigest.archivo.transferencias.relacionesAsociadas"
								page="/pages/images/related.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.transferencias.relacionesAsociadas"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>

                <security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">
				<%--boton eliminar --%>
				<c:if test="${bPrevision.puedeSerEliminada}">
			       	<TD>
						<c:set var="llamadaEliminar">javascript:eliminarPrevision()</c:set>
						<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaEliminar}" escapeXml="false"/>' >
							<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop" />
				   		 	&nbsp;<bean:message key="archigest.archivo.eliminar"/>
						</a>
				   	</TD>
			       	<TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton enviar --%>
				<c:if test="${bPrevision.puedeSerEnviada}">
				    <TD>
				   		<a class=etiquetaAzul12Bold href="javascript:enviarPrevision()">
							<html:img page="/pages/images/enviar.gif" altKey="archigest.archivo.enviar" titleKey="Enviar" styleClass="imgTextTop" />
				   		 	&nbsp;<bean:message key="archigest.archivo.enviar"/>
				   		</a>
				   </TD>
			       <TD width="10">&nbsp;</TD>
				</c:if>
				</security:permissions>

			<%--botones aceptar y rechazar --%>
			<c:if test="${bPrevision.puedeSerAceptada}">
	                <security:permissions action="${appConstants.transferenciaActions.ACEPTAR_PREVISION}">
					<TD>
						<c:url var="aceptarURL" value="/action/recepcionPrevisiones">
							<c:param name="method" value="aceptarprevision" />
							<c:param name="idPrevision" value="${bPrevision.id}" />
						</c:url>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${aceptarURL}" escapeXml="false"/>">
							<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
				   		 	&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				   		</a>
					</TD>
			        <TD width="10">&nbsp;</TD>
					<TD>
						<c:url var="rechazarURL" value="/action/recepcionPrevisiones">
							<c:param name="method" value="rechazarprevision" />
							<c:param name="idPrevision" value="${bPrevision.id}" />
						</c:url>
				   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${rechazarURL}" escapeXml="false"/>'">
							<html:img page="/pages/images/rechazar.gif" altKey="archigest.archivo.rechazar" titleKey="archigest.archivo.rechazar" styleClass="imgTextTop" />
				   		 	&nbsp;<bean:message key="archigest.archivo.rechazar"/>
				   		</a>
					</TD>
			        <TD width="10">&nbsp;</TD>
					</security:permissions>
			</c:if>
			<%--boton crear relacion --%>
			<c:if test="${bPrevision.aceptaRelaciones && !bPrevision.detallada}">
				<security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">
				<TD>
					<c:url var="urlCrearRelacion" value="/action/gestionRelaciones">
						<c:param name="method" value="nueva"/>
						<c:param name="idprevisionseleccionada" value="${bPrevision.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${urlCrearRelacion}" escapeXml="false"/>'>
						<html:img titleKey="archigest.archivo.transferencias.relacion.crear" altKey="archigest.archivo.transferencias.relacion.crear" page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.transferencias.crearRel"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>
	       <TD>
				<%--boton Cerrar --%>
				<c:url var="volverURL" value="/action/gestionPrevisiones">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>">
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
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

<div class="separador1">&nbsp;</div>

<DIV class="cabecero_bloque"> <%--cabecero primer bloque de datos --%>

<TABLE class="w98m1" cellpadding=0 cellspacing=0>
  <TBODY>
  <TR>
    <TD class="etiquetaAzul12Bold" width="20%">
		<bean:message key="archigest.archivo.transferencias.identificacion"/>
	</TD>
    <TD width="80%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<c:if test="${bPrevision.puedeSerEditada}">
				<security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">
				<c:url var="editarURL" value="/action/gestionPrevisiones">
					<c:param name="method" value="edicion" />
					<c:param name="idPrevision" value="${bPrevision.id}" />
				</c:url>
				<TD>
					<a class="etiquetaAzul12Normal" href="<c:out value="${editarURL}" escapeXml="false"/>">
						 <html:img titleKey="archigest.archivo.editar" altKey="archigest.archivo.editar" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle" />
						 &nbsp;<bean:message key="archigest.archivo.editar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
				</security:permissions>
		</c:if>
		<%--boton modificar --%>
		<c:if test="${bPrevision.puedeSerModificada}">
				<security:permissions action="${appConstants.transferenciaActions.MODIFICAR_PREVISION}">
				<c:url var="modificarURL" value="/action/gestionPrevisiones">
					<c:param name="method" value="modificar" />
				</c:url>
				<TD>
					<a class=etiquetaAzul12Normal href="javascript:window.location='<c:out value="${modificarURL}" escapeXml="false"/>'">
						<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.transferencias.modificarFechas" titleKey="archigest.archivo.transferencias.modificarFechas" styleClass="imgTextTop" />
						&nbsp;<bean:message key="archigest.archivo.transferencias.modificarFechas"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
				</security:permissions>
		</c:if>
       <TD>
   		<a href="javascript:showHideDiv('Prev');">
			<html:img styleId="imgPrev" page="/pages/images/down.gif" titleKey="archigest.archivo.desplegar" altKey="archigest.archivo.desplegar" styleClass="imgTextBottom" />
   		</a>
	   </TD>
     </TR>
	</TABLE>
	</TD>
  </TR></TBODY></TABLE>
</div> <%--cabecero bloque --%>


<DIV class="bloque"> <%--primer bloque de datos (resumen siempre visible) --%>
			<jsp:include page="cabeceracte_prevision.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="bloque" id="divPrev" style="display:none;"> <%--primer bloque de datos --%>

		<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="250px">
					<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${bPrevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />
				</TD>
			</TR>
			<c:if test="${!bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.organoRemitente.nombreLargo}"/>
					</TD>
				</TR>
			</c:if>
			<c:if test="${bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
					</TD>
					<TD class="tdDatos" id="nombreArchivoRemitente">
						<c:out value="${bPrevision.nombrearchivoremitente}"/>
					</TD>
				</TR>
			</c:if>
			<c:if test="${!bPrevision.ordinaria}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.nombrearchivoreceptor}"/>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<c:choose>
							<c:when test="${bPrevision.entreArchivos}">
								<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
							</c:otherwise>
						</c:choose>
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.fondo.codReferencia}"/>
						<c:out value="${bPrevision.fondo.titulo}"/>
					</TD>
				</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.numuinstalacion}"/>
				</TD>
			</TR>

		<c:if test="${!empty bPrevision.fechainitrans}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="50px" class="td2Datos">
							<fmt:formatDate value="${bPrevision.fechainitrans}" pattern="${FORMATO_FECHA}"/>
						</TD>
						<TD width="20px">
						</TD>
						<TD width="250px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
						</TD>
						<TD width="60px" class="td2Datos">
							<fmt:formatDate value="${bPrevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
						</TD>
					</TR>
					</TABLE>
				</TD>
			</TR>
		</c:if>

		<c:if test="${not empty bPrevision.motivorechazo}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.notas"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.motivorechazo}"/>
				</TD>
			</TR>
		</c:if>

			<TR>
				<TD class="tdTitulo" style="vertical-align:top">
					<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.observaciones}">--</c:out>
				</TD>
			</TR>
		</TABLE>

</div> <%--primer bloque de datos --%>

<c:if test="${!bPrevision.detallada}">
<script>
	showHideDiv('Prev');
</script>
</c:if>

<c:if test="${bPrevision.detallada}">

<%--8 pixels de separacion --%>
<div class="separador8">&nbsp;</div>

<div class="cabecero_bloque"> <%--cabecero segundo bloque de datos --%>

	<script>
		function eliminarSelectedItems(selectionFormName) {
			var selectionForm = document.forms[selectionFormName];
			if (selectionForm && selectionForm.seleccionDetalle) {
				var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"seleccionDetalle");
				if(nSelected >= 1) {
						if (confirm("<bean:message key='archigest.archivo.transferencias.eliminarWarningMsg'/>"))
							selectionForm.submit();
				} else
					alert("<bean:message key='archigest.archivo.transferencias.seleccionarDetalle'/>");
			} else
				alert("<bean:message key='archigest.archivo.transferencias.ningunDetalle'/>");
		}
	</script>

<TABLE class="w98m1" cellpadding=0 cellspacing=0>
  <TBODY>
  <TR>
    <TD class="etiquetaAzul12Bold" width="20%">
		<bean:message key="archigest.archivo.transferencias.contenido"/>
	</TD>
    <TD width="80%" align="right">
	<bean:struts id="mappingGestionUdocs" mapping="/gestionDetallesPrevision" />
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<c:if test="${bPrevision.numrentrega gt 0}">
			<TD>
			<c:url var="verRelacionesURL" value="/action/gestionRelaciones">
				<c:param name="method" value="verRelacionesPrevision" />
				<c:param name="idPrevision" value="${bPrevision.id}" />
			</c:url>
			<script>
				var detalleSeleccionado = null;
				function verRelacionesPrevision() {
					//if (!document.forms['<c:out value="${mappingGestionUdocs.name}" />'].detalleSeleccionado) return;
					if (detalleSeleccionado == null)
						alert("<bean:message key='archigest.archivo.transferencias.seleccionarDetallePrevision'/>")
					else {
						var urlRelacionesPrevision = '<c:out value="${verRelacionesURL}" escapeXml="false"/>';
						urlRelacionesPrevision += '&idDetalle='+detalleSeleccionado;
						window.location = urlRelacionesPrevision;
					}
				}
			</script>
			<A class="etiquetaAzul12Normal" href="javascript:verRelacionesPrevision()">
			 <html:img titleKey="archigest.archivo.transferencias.relacionesAsociadas" altKey="archigest.archivo.transferencias.relacionesAsociadas"
				page="/pages/images/related.gif" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.transferencias.relacionesAsociadas"/>
			</A>
			</TD>
			<TD width="10">&nbsp;</TD>
		</c:if>
        <security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">
		<c:if test="${bPrevision.puedeSerEditada}">
		    <TD>
				<c:url var="addDetalleURL" value="/action/gestionDetallesPrevision?method=nuevoDetalle" />
				<a href="<c:out value="${addDetalleURL}" escapeXml="false"/>" class="etiquetaAzul12Normal">
					<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.anadir"/>
				</a>
	    	</TD>
		    <TD width="10">&nbsp;</TD>
			<c:if test="${bPrevision.numDetallesPrevision gt 0}">
		    <TD>
				<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingGestionUdocs.name}" />')">
					<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				</a>
		    </TD>
		    <TD width="10">&nbsp;</TD>
			</c:if>
		</c:if>
		<c:if test="${bPrevision.aceptaRelaciones}">
			    <TD>
					<c:url var="urlCrearRelacion" value="/action/gestionRelaciones">
						<c:param name="method" value="nueva"/>
						<c:param name="idprevisionseleccionada" value="${bPrevision.id}"/>
					</c:url>
					<a class="etiquetaAzul12Normal" href='<c:out value="${urlCrearRelacion}" escapeXml="false"/>'>
						<html:img titleKey="archigest.archivo.transferencias.relacion.crear" altKey="archigest.archivo.transferencias.relacion.crear" page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
			  			&nbsp;<bean:message key="archigest.archivo.transferencias.relacion.crear"/>
					</a>
			    </TD>
		</c:if>
		</security:permissions>
	  </TR>
	</TABLE>
	</TD>
  </TR></TBODY></TABLE>
</div> <%--cabecero bloque --%>


<DIV class="bloque"> <%--segundo bloque de datos --%>
	<c:url var="vistaPrevisionPaginationURI" value="/action/gestionPrevisiones">
		<c:param name="method" value="${param.method}" />
		<c:param name="idprevision" value="${bPrevision.id}" />
	</c:url>
	<jsp:useBean id="vistaPrevisionPaginationURI" type="java.lang.String" />

	<html:form action="/gestionDetallesPrevision">

	<input type="hidden" name="method" value="eliminarDetalles" />

	<c:set var="detallesPrevision" value="${bPrevision.detallesPrevision}" />
	<display:table name="pageScope.detallesPrevision"
		id="detallePrevision"
		style="width:98%;margin-left:auto;margin-right:auto"
		sort="list"
		pagesize="10"
		requestURI='<%=vistaPrevisionPaginationURI %>'>

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.transferencias.noDetallesPrevision"/>
		</display:setProperty>
		<c:if test="${bPrevision.puedeSerEditada}">
			<display:column style="width:20px" headerClass="minusDocIcon">
					<input type="checkbox" name="seleccionDetalle" value="<c:out value="${detallePrevision.id}" />" >
			</display:column>
		</c:if>

		<display:column style="width:20px" headerClass="relsRelatedIcon">
			<c:if test="${detallePrevision.numrentrega gt 0}" >
				<input type="radio" name="detalleSeleccionado" onclick="window.detalleSeleccionado = '<c:out value="${detallePrevision.id}" />'">
			</c:if>
		</display:column>

		<display:column titleKey="archigest.archivo.num" sortable="true" headerClass="sortable">
			<c:url var="infoDetalleURL" value="/action/gestionDetallesPrevision">
				<c:param name="method" value="infoDetalle" />
				<c:param name="idDetallePrevision" value="${detallePrevision.id}" />
			</c:url>
			<a href="<c:out value="${infoDetalleURL}" escapeXml="false"/>" class="tdlink">
				<fmt:formatNumber value="${detallePrevision.numeroOrden}" pattern="000"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.procedimiento" sortable="true" headerClass="sortable">
			<c:out value="${detallePrevision.idProcedimiento}"> -- </c:out>
			<c:out value="${detallePrevision.procedimiento.nombre}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.serieDestino" sortable="true" headerClass="sortable">
			<c:out value="${detallePrevision.serieDestino.codigo}" />
			<c:out value="${detallePrevision.serieDestino.titulo}" />
		</display:column>
		<display:column titleKey="archigest.archivo.unds" sortable="true" headerClass="sortable">
			<c:out value="${detallePrevision.numUInstalacion}"/>
			<c:if test="${detallePrevision.sinDocumentosFisicos}">
				&nbsp;<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
			</c:if>

		</display:column>
	</display:table>
</html:form>
</div> <%--bloque --%>
</c:if>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>