<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<c:set var="posicion" value="${sessionScope[appConstants.transferencias.POSICION_UDOC]}"/>
<c:set var="unidades" value="${sessionScope[appConstants.transferencias.CONTADOR_UNIDADES_DOCUMENTALES]}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="unidadDocumental" value="${sessionScope[appConstants.transferencias.UNIDAD_DOCUMENTAL]}"/>
<c:set var="prevUnidadDocumental" value="${unidadDocumental.prevUnidadDocumental}"/>
<c:set var="nextUnidadDocumental" value="${unidadDocumental.nextUnidadDocumental}"/>
			
<TABLE class="w98m1" cellpadding=0 cellspacing=2>
	<TR>
		<TD width="50%" class="etiquetaAzul11Bold">
				
			<c:choose>
				<c:when test="${vRelacion.isIngresoDirecto}">
					<bean:message key="archigest.archivo.contador.altaunidadesdoc" />:&nbsp;
				</c:when>
				<c:otherwise>
					<bean:message key="archigest.archivo.contador.unidadesdocre" />:&nbsp;
				</c:otherwise>
			</c:choose>		
			
			<span class="etiquetaNegra11Normal">
				<c:out value="${unidades}"/>
			</span>
		</TD>		
		<TD width="40%" class="etiquetaAzul11Bold">
			<bean:message key="archigest.archivo.posicion.udoc"/>:&nbsp;
			<span class="etiquetaNegra11Normal">
				<c:out value="${posicion}"/>
			</span>
		</TD>
		<c:if test="${showPrevNext}">
			<TD class="etiquetaAzul11Bold" align="right" style="float:right;">
					<c:url var="anteriorUdocUrl" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="infoUnidadDocumental" />
						<c:param name="udocID" value="${prevUnidadDocumental.id}" />
						<c:param name="numOrden" value="${prevUnidadDocumental.orden}"  />
						<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
					</c:url>
					<c:url var="siguienteUdocUrl" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="infoUnidadDocumental" />
						<c:param name="udocID" value="${nextUnidadDocumental.id}" />
						<c:param name="numOrden" value="${nextUnidadDocumental.orden}"  />
						<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
					</c:url>
					<c:if test="${(prevUnidadDocumental!=null)||(nextUnidadDocumental!=null)}">
						<c:if test="${prevUnidadDocumental!=null}">
							<a class="etiquetaAzul12Bold" href="<c:out value="${anteriorUdocUrl}" escapeXml="false" />"><html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.prev.unidad.documental" titleKey="archigest.archivo.prev.unidad.documental" styleClass="imgTextMiddle" /></a>
						</c:if>
						<c:out value="${posicion}"/>/<c:out value="${unidades}"/>
						<c:if test="${nextUnidadDocumental!=null}">
							<a class="etiquetaAzul12Bold" href="<c:out value="${siguienteUdocUrl}" escapeXml="false" />"><html:img page="/pages/images/Next.gif" altKey="archigest.archivo.next.unidad.documental" titleKey="archigest.archivo.next.unidad.documental" styleClass="imgTextMiddle" /></a>
						</c:if>
					</c:if>
			</TD>
		</c:if>
	</TR>
</TABLE>	