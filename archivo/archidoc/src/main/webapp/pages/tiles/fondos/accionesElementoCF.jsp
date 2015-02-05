<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="elementoCF" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
	<c:set var="childTypes" value="${requestScope[appConstants.fondos.CHILD_TYPES_ELEMENTO_CF_KEY]}" />
	<c:forEach var="tipoNivelHijo" items="${childTypes}">
		<c:choose>
		<c:when test="${tipoNivelHijo == appConstants.fondos.tiposNivel.CLASIFICADOR_SERIE}">
		<security:permissions action="${appConstants.fondosActions.ALTA_ELEMENTO_ACTION}">
			<TD>
				<c:url var="altaClasificadorSeriesURL" value="/action/gestionClasificadorSeriesAction">
					<c:param name="method" value="altaClasificadorSeries" />
					<c:param name="nivelPadre" value="${elementoCF.idNivel}" />
					<c:param name="idPadre" value="${elementoCF.id}" />
				</c:url>
				<c:set var="niveles" value="${sessionScope[appConstants.fondos.LISTA_SUBNIVELES_KEY]}" />
				<c:set var="nombreNivelACrear"><bean:message key="archigest.archivo.cf.clasificador" /></c:set>
				<c:if test="${empty niveles[1]}">
					<c:set var="nombreNivelACrear" value="${niveles[0].nombre}" />
				</c:if>
				<a class="etiquetaAzul12Bold" href="<c:out value="${altaClasificadorSeriesURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.cf.crearClasificador" titleKey="archigest.archivo.cf.crearClasificador" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.crear"/>
					<c:out value="${nombreNivelACrear}" />
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
		</security:permissions>	
		</c:when>
		<c:when test="${tipoNivelHijo == appConstants.fondos.tiposNivel.CLASIFICADOR_FONDOS}">
		<security:permissions action="${appConstants.fondosActions.ALTA_ELEMENTO_ACTION}">
			<TD nowrap="nowrap">
				<c:url var="altaClasificadorFondoURL" value="/action/gestionClasificadorFondosAction">
					<c:param name="method" value="altaClasificadorFondos" />
					<c:param name="nivelPadre" value="${elementoCF.idNivel}" />
					<c:param name="idPadre" value="${elementoCF.id}" />
				</c:url>
				<c:set var="niveles" value="${sessionScope[appConstants.fondos.LISTA_SUBNIVELES_KEY]}" />
				<c:set var="nombreNivelACrear"><bean:message key="archigest.archivo.cf.clasificador" /></c:set>
				<c:if test="${empty niveles[1]}">
					<c:set var="nombreNivelACrear" value="${niveles[0].nombre}" />
				</c:if>
				<a class="etiquetaAzul12Bold" href="<c:out value="${altaClasificadorFondoURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.cf.crearClasificador" titleKey="archigest.archivo.cf.crearClasificador" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.crear"/>
					<c:out value="${nombreNivelACrear}" />
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
		</security:permissions>	
		</c:when>
		<c:when test="${tipoNivelHijo == appConstants.fondos.tiposNivel.FONDO}">
		<security:permissions action="${appConstants.fondosActions.ALTA_ELEMENTO_ACTION}">
			<TD>
				<c:url var="altaFondoURL" value="/action/gestionFondoAction">
					<c:param name="method" value="pantallanuevofondo" />
					<c:param name="idnivelpadre" value="${elementoCF.idNivel}" />
					<c:param name="idclpadre" value="${elementoCF.id}" />
					<c:param name="codigoclasificadorpadre" value="${elementoCF.codigo}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${altaFondoURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.cf.crearFondo" titleKey="archigest.archivo.cf.crearFondo" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.crear"/>
					<bean:message key="archigest.archivo.cf.fondo"/>&nbsp;
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
		</security:permissions>	
		</c:when>
		<c:when test="${tipoNivelHijo == appConstants.fondos.tiposNivel.SERIE}">
		<security:permissions action="${appConstants.fondosActions.SOLICITUD_ALTA_SERIE_ACTION}">
			<TD>
				<c:url var="solicitudAltaSerieURL" value="/action/gestionSeries">
					<c:param name="method" value="nuevaSolicitudAlta" />
					<c:param name="idPadre" value="${elementoCF.id}" />
					<c:param name="idNivelPadre" value="${elementoCF.idNivel}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${solicitudAltaSerieURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.permiso.501" titleKey="archigest.archivo.permiso.501" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cf.solicitarAltaSerie"/>&nbsp;
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
		</security:permissions>
		</c:when>
		</c:choose>
	</c:forEach>
