<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="detallePrevision" value="${requestScope[appConstants.transferencias.DETALLEPREVISION_KEY]}" />

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	  <TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.transferencias.datos"/><bean:message key="archigest.archivo.transferencias.ele"/>
			<bean:message key="archigest.archivo.transferencias.contenido"/>
		</TD>
	    <TD align="right">
			<script>
				function eliminarDetallePrevision() {
					if (confirm("<bean:message key='archigest.archivo.transferencias.previsiones.delLineaDetalle'/>")) {
						<c:url var="URLEliminacion" value="/action/gestionDetallesPrevision">
							<c:param name="method" value="eliminarDetalle" />
							<c:param name="idDetallePrevision" value="${detallePrevision.id}" />
						</c:url>
						window.location = '<c:out value="${URLEliminacion}" escapeXml="false" />';
					}
				}

			</script>

			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
				<c:if test="${detallePrevision.puedeSerEliminado}">
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:eliminarDetallePrevision()" >
						<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   </c:if>
				<c:if test="${detallePrevision.puedeSerEditado}">
				<TD>
					<c:url var="edicionURL" value="/action/gestionDetallesPrevision">
						<c:param name="method" value="editarDetalle" />
						<c:param name="idDetallePrevision" value="${detallePrevision.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="javascript:window.location='<c:out value="${edicionURL}" escapeXml="false"/>'" >
						<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.editar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</c:if>
		       	<TD>
					<%--boton Cerrar --%>
					<c:url var="cerrarURL" value="/action/gestionPrevisiones">
						<c:param name="method" value="goBack" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cerrarURL}" escapeXml="false"/>'">
						<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
			   		</a>
			   </TD>
			 </TR>
			</TABLE>
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

	<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<c:if test="${bPrevision.ordinaria}">
			<TR>
				<TD class="tdTitulo" width="220px">
					<bean:message key="archigest.archivo.transferencias.sistProd"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detallePrevision.nombreSistProductor}" />
				</TD>
			</TR>
			</c:if>
			<c:if test="${bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.nombrearchivoremitente}"/>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.archivoReceptor"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.nombrearchivoreceptor}"/>
					</TD>
				</TR>
			</c:if>
			<c:if test="${!bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.procedimiento"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="procedimiento" value="${detallePrevision.procedimiento}" />
						<c:out value="${procedimiento.id}"> -- </c:out>&nbsp;<c:out value="${procedimiento.nombre}" />
					</TD>
				</TR>
			</c:if>
			<c:choose>
				<c:when test="${!bPrevision.entreArchivos}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.serie"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.serieDestino.codigo}" />
							<c:out value="${detallePrevision.serieDestino.denominacion}" />
						</TD>
					</TR>
				</c:when>
				<c:otherwise>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.serieOrigen"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.serieOrigen.codReferencia}" />
							<c:out value="${detallePrevision.serieOrigen.denominacion}" />
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.serieDestino"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.serieDestino.codReferencia}" />
							<c:out value="${detallePrevision.serieDestino.denominacion}" />
						</TD>
					</TR>
				</c:otherwise>
			</c:choose>
			<c:if test="${!bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.transferencias.funcion"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${detallePrevision.funcion.codReferencia}" />
						<c:out value="${detallePrevision.funcion.titulo}" />
					</TD>
				</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo" colspan="2">
					<bean:message key="archigest.archivo.transferencias.rangoFechas"/>:&nbsp;
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="100px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fechaInicioExps"/>:&nbsp;
						</TD>
						<TD width="50px" class="td2Datos">
							<c:out value="${detallePrevision.anoIniUdoc}" />
						</TD>
						<TD width="20px">
						</TD>
						<TD width="100px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fechaFinExps"/>:&nbsp;
						</TD>
						<TD width="50px" class="td2Datos">
							<c:out value="${detallePrevision.anoFinUdoc}" />
						</TD>
					</TR>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detallePrevision.numUInstalacion}" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detallePrevision.formato.nombre}" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" style="vertical-align:top">
					<bean:message key="archigest.archivo.transferencias.observaciones"/>:
				</TD>
				<TD class="tdDatos">
					<c:out value="${detallePrevision.observaciones}"> -- </c:out>
				</TD>
			</TR>
	</TABLE>

</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
