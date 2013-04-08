<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionReemplazarValores" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>
<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>


			<div style="height:10px">&nbsp;</div>
			<display:table name="pageScope.elementos"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="elemento"
				pagesize="0"
				sort="external"
				defaultsort="0"
				requestURI="../../action/gestionReemplazarValores?method=verElementosAfectados"
				export="true"
				excludedParams="method"
				decorator="common.view.VisitedRowDecorator">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
				</display:setProperty>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO] != null}">
					<c:set var="campoCodigo" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO]}"/>
					<c:choose>
						<c:when test="${campoCodigo.mostrarLink == 'S'}">
							<display:column titleKey="archigest.archivo.cf.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
								<c:url var="URL" value="/action/buscarElementos">
									<c:param name="method" value="verEnCuadro" />
									<c:param name="id" value="${elemento.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="codigo"/></a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.cf.codigo" property="codigo" media="html"/>
						</c:otherwise>
					</c:choose>
					<display:column titleKey="archigest.archivo.cf.codigo" property="codigo" media="csv excel xml pdf"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA] != null}">
					<c:set var="campoCodReferencia" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA]}"/>
					<c:choose>
						<c:when test="${campoCodReferencia.mostrarLink == 'S'}">
							<display:column titleKey="archigest.archivo.cf.codReferencia" sortProperty="codReferencia" sortable="true" headerClass="sortable" media="html">
								<c:url var="URL" value="/action/buscarElementos">
									<c:param name="method" value="verEnCuadro" />
									<c:param name="id" value="${elemento.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="codReferencia"/></a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" sortable="true" media="html"/>
						</c:otherwise>
					</c:choose>
					<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" media="csv excel xml pdf"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE] != null}">
					<c:set var="campoNumExp" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE]}"/>
					<c:choose>
						<c:when test="${campoNumExp.mostrarLink == 'S'}">
							<display:column titleKey="archigest.archivo.cf.numExpediente" sortProperty="numexp" sortable="true" headerClass="sortable" media="html">
								<c:url var="URL" value="/action/buscarElementos">
									<c:param name="method" value="verEnCuadro" />
									<c:param name="id" value="${elemento.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="numexp"/></a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.cf.numExpediente" property="numexp" sortable="true" media="html"/>
						</c:otherwise>
					</c:choose>
					<display:column titleKey="archigest.archivo.cf.numExpediente" property="numexp" media="csv excel xml pdf"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO] != null}">
					<c:set var="campoTitulo" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO]}"/>
					<c:choose>
						<c:when test="${campoTitulo.mostrarLink == 'S'}">
							<display:column titleKey="archigest.archivo.cf.titulo" sortProperty="titulo" sortable="true" headerClass="sortable" media="html">
								<c:url var="URL" value="/action/buscarElementos">
									<c:param name="method" value="verEnCuadro" />
									<c:param name="id" value="${elemento.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="titulo"/></a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" sortable="true" media="html"/>
						</c:otherwise>
					</c:choose>
					<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" media="csv excel xml pdf"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO] != null}">
					<display:column titleKey="archigest.archivo.cf.fondo" property="nombreFondo" headerClass="sortable" style="width:10%;"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS] != null}">
					<display:column titleKey="archigest.archivo.cf.rango" property="nombreRangos" headerClass="sortable" style="width:10%;"/>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE] != null}">
					<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;"  media="html">
						<c:choose>
							<c:when test="${elemento.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
								<c:out value="${elemento.nombreRangos}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${elemento.numexp}"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="csv excel pdf xml">
						<c:choose>
							<c:when test="${elemento.subtipoCaja}">
								<bean:message key="archigest.archivo.simbolo.rango"/> <c:out value="${elemento.nombreRangos}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${elemento.numexp}"/>
							</c:otherwise>
						</c:choose>
					</display:column>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL] != null}">
					<display:column titleKey="archigest.archivo.cf.nivel" property="nombre" sortable="true" headerClass="sortable" />
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR] != null}">
					<display:column titleKey="archigest.archivo.cf.productor" property="nombreProductor" headerClass="sortable" />
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_ESTADO] != null}">
					<display:column titleKey="archigest.archivo.estado" sortProperty="estado" sortable="true" headerClass="sortable">
						<c:set var="keyTituloEstado">
							archigest.archivo.cf.estadoElementoCF.<c:out value="${elemento.estado}"/>
						</c:set>
						<fmt:message key="${keyTituloEstado}" />
					</display:column>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL] != null}">
					<display:column titleKey="archigest.archivo.fechaInicial">
						<logic:equal name="elemento" property="formatoFInicial" value="S">S.</logic:equal>
						<bean:write name="elemento" property="valorFInicial"/>
						<logic:present name="elemento" property="calificadorFInicial">
							(<bean:write name="elemento" property="calificadorFInicial"/>)
						</logic:present>
					</display:column>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL] != null}">
					<display:column titleKey="archigest.archivo.fechaFinal">
						<logic:equal name="elemento" property="formatoFFinal" value="S">S.</logic:equal>
						<bean:write name="elemento" property="valorFFinal"/>
						<logic:present name="elemento" property="calificadorFFinal">
							(<bean:write name="elemento" property="calificadorFFinal"/>)
						</logic:present>
					</display:column>
				</c:if>
				<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_BLOQUEO] != null}">
					<display:column style="text-align: center;"  media="html">
						<c:choose>
							<c:when test="${elemento.marcasBloqueoUnidad>0}" >
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
							<c:when test="${elemento.marcasBloqueoUnidad>0}" >
								<bean:message key="archigest.archivo.si" />
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.no" />
							</c:otherwise>
						</c:choose>
					</display:column>
				</c:if>
			</display:table>
			<div style="height:10px">&nbsp</div>