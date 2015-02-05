<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>
<c:set var="listaUdocs" value="${sessionScope[appConstants.prestamos.LISTADO_BUSQUEDA_UDOCS]}" />
<bean:struts id="mappingGestionDetallePrestamo" mapping="/gestionDetallesPrestamos" />

		<c:if test="${!empty listaUdocs}">
			<table class="formulario">
			<TR><TD align="right">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
						<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
							<html:img
								page="/pages/images/checked.gif" border="0"
								altKey="archigest.archivo.selTodos"
								titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.selTodos"/>&nbsp;
						</a>
					</TD>
					<TD width="20">&nbsp;</TD>
					<TD>
						<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
							<html:img
								page="/pages/images/check.gif" border="0"
								altKey="archigest.archivo.quitarSel"
								titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
			</c:if>

			<c:url var="paginationURI" value="/action/gestionDetallesPrestamos">
				<c:param name="method" value="buscarUDocs" />
			</c:url>
			<jsp:useBean id="paginationURI" type="java.lang.String" />
			<display:table name="pageScope.listaUdocs"
				style="width:98%;margin-left:auto;margin-right:auto"
				id="udocs"
				decorator="common.view.VisitedRowDecorator"
				pagesize="0"
				sort="external"
				defaultsort="1"
				requestURI='<%=paginationURI%>'
				export="false" excludedParams="method">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.solicitudes.noPrev"/>
				</display:setProperty>

				<display:column style="width:23px;">
					<c:choose>
						<c:when test="${udocs.subtipoCaja}">
							<input type="checkbox" name="detallesseleccionados"
								value='<c:out value="${udocs.identificacion}"/>|<c:out value="${udocs.id}"/>|null|<c:out value="${udocs.titulo}"/>|<c:out value="${udocs.signaturaudoc}"/>|<c:out value="${udocs.idFondo}"/>|<c:out value="${udocs.codsistproductor}">null</c:out>' >
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="detallesseleccionados"
								value='<c:out value="${udocs.identificacion}"/>|<c:out value="${udocs.id}"/>|<c:out value="${udocs.numexp}">null</c:out>|<c:out value="${udocs.titulo}"/>|<c:out value="${udocs.signaturaudoc}"/>|<c:out value="${udocs.idFondo}"/>|<c:out value="${udocs.codsistproductor}">null</c:out>' >
						</c:otherwise>
					</c:choose>
				</display:column>

				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA] != null}">
					<c:set var="campoCodReferencia" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA]}"/>
					<c:set var="codigoReferencia" value="${udocs.codReferencia}"/>
					<display:column titleKey="archigest.archivo.cf.codReferencia" sortable="true" sortProperty="codReferencia" headerClass="sortable"  media="html" >
						<c:if test="${campoCodReferencia.abreviado == 'S'}">
							<c:if test="${udocs.codReferencia != udocs.codReferenciaAbreviado}">
								<span title='<c:out value="${udocs.codReferencia}"/>'><html:img page="/pages/images/abreviado.gif" styleClass="imgTextMiddle"/></span>
							</c:if>
							<c:set var="codigoReferencia" value="${udocs.codReferenciaAbreviado}"/>
						</c:if>
						<c:out value="${codigoReferencia}"/>
					</display:column>
				</c:if>

				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE] != null}">
					<display:column titleKey="archigest.archivo.prestamos.expedienteudoc"  sortable="true" headerClass="sortable" property="numexp"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE] != null}">
					<display:column titleKey="archigest.archivo.prestamos.expedienteudoc" style="width:18%;">
						<c:choose>
							<c:when test="${udocs.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
								<c:out value="${udocs.nombreRangos}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${udocs.numexp}"/>
							</c:otherwise>
						</c:choose>
					</display:column>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO] != null}">
					<display:column titleKey="archigest.archivo.solicitudes.fondo"  sortable="true" headerClass="sortable" property="nombreFondo"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA] != null}">
					<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc"  sortable="true" headerClass="sortable" property="signaturaudoc"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO] != null}">
					<display:column titleKey="archigest.archivo.codigo"  sortable="true" headerClass="sortable" property="codigo"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO] != null}">
					<display:column titleKey="archigest.archivo.titulo" sortable="true" headerClass="sortable" property="titulo"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL] != null}">
					<display:column titleKey="archigest.archivo.fechaInicial" sortable="true" property="valorFInicial" headerClass="sortable">
						<logic:equal name="udocs" property="formatoFInicial" value="S"><fmt:message key="archigest.archivo.abreviatura.siglo"/></logic:equal>
						<bean:write name="udocs" property="valorFInicial"/>
						<logic:present name="udocs" property="calificadorFInicial">
							(<bean:write name="udocs" property="calificadorFInicial"/>)
						</logic:present>
					</display:column>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL] != null}">
					<display:column titleKey="archigest.archivo.fechaFinal" sortable="true" property="valorFFinal" headerClass="sortable">
						<logic:equal name="udocs" property="formatoFFinal" value="S"><fmt:message key="archigest.archivo.abreviatura.siglo"/></logic:equal>
						<bean:write name="udocs" property="valorFFinal"/>
						<logic:present name="udocs" property="calificadorFFinal">
							(<bean:write name="udocs" property="calificadorFFinal"/>)
						</logic:present>
					</display:column>
				</c:if>
				<display:column titleKey="archigest.archivo.solicitudes.observaciones">
					<c:choose>
						<c:when test="${udocs.subtipoCaja}">
							<input type="text" size="40" name="observaciones|<c:out value="${udocs.identificacion}"/>|<c:out value="${udocs.id}"/>|null|<c:out value="${udocs.titulo}"/>|<c:out value="${udocs.signaturaudoc}"/>|<c:out value="${udocs.idFondo}"/>|<c:out value="${udocs.codsistproductor}">null</c:out>" class="input">
						</c:when>
						<c:otherwise>
							<input type="text" size="40" name="observaciones|<c:out value="${udocs.identificacion}"/>|<c:out value="${udocs.id}"/>|<c:out value="${udocs.numexp}">null</c:out>|<c:out value="${udocs.titulo}"/>|<c:out value="${udocs.signaturaudoc}"/>|<c:out value="${udocs.idFondo}"/>|<c:out value="${udocs.codsistproductor}">null</c:out>" class="input">
						</c:otherwise>
					</c:choose>
				</display:column>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_BLOQUEO] != null}">
					<display:column style="text-align: center;"  media="html">
						<c:choose>
							<c:when test="${udocs.marcasBloqueoUnidad>0}" >
								<html:img
									page="/pages/images/udocBloqueada.gif"
									titleKey="archigest.archivo.bloqueada"
									altKey="archigest.archivo.bloqueada"
									styleClass="imgTextBottom" />
							</c:when>
							<c:otherwise>
								<html:img
									page="/pages/images/udocDesbloqueada.gif"
									titleKey="archigest.archivo.desbloqueada"
									altKey="archigest.archivo.desbloqueada"
									styleClass="imgTextBottom" />
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
						<c:choose>
							<c:when test="${udocs.marcasBloqueoUnidad>0}" >
								<bean:message key="archigest.archivo.si" />
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.no" />
							</c:otherwise>
						</c:choose>
					</display:column>
				</c:if>
			</display:table>
