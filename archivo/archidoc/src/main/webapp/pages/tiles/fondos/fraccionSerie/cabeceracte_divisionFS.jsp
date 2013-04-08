<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

	<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
	<c:set var="divisionFraccionSerie" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}" />
	<c:set var="udocEnDivisionFS" value="${sessionScope[appConstants.fondos.UNIDAD_DOCUMENTAL_EN_FS]}"/>
	<c:set var="prevUnidadDocumental" value="${udocEnDivisionFS.prevUdocEnDivisionFs}"/>
	<c:set var="nextUnidadDocumental" value="${udocEnDivisionFS.nextUdocEnDivisionFs}"/>

	<TABLE class="w98m1" cellpadding=0 cellspacing=2>
		<TR>
			<TD width="50%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.cf.fraccionSerie"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<c:out value="${divisionFraccionSerie.asunto}" />
				</span>
			</TD>	
			<TD width="20%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.divisionfs.estado"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<fmt:message key="archigest.archivo.fondos.estadoDivisionFS.${divisionFraccionSerie.estado}" />
				</span>
			</TD>
			<TD width="20%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.fondos.gestorDivisionFS"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<c:out value="${divisionFraccionSerie.gestor.nombreCompleto}"/>
				</span>
			</TD>	

			<c:if test="${showPrevNext}">
				<TD class="etiquetaAzul11Bold" align="right" style="float:right;">
						<c:url var="anteriorUdocUrl" value="/action/gestionUDocsEnFS">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${prevUnidadDocumental.idUDoc}" />
							<c:param name="numOrden" value="${prevUnidadDocumental.orden}"  />
							<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
						</c:url>
						<c:url var="siguienteUdocUrl" value="/action/gestionUDocsEnFS">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${nextUnidadDocumental.idUDoc}" />
							<c:param name="numOrden" value="${nextUnidadDocumental.orden}"  />
							<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
						</c:url>
						<c:if test="${(prevUnidadDocumental!=null)||(nextUnidadDocumental!=null)}">
							<c:if test="${prevUnidadDocumental!=null}">
								<a class="etiquetaAzul12Bold" href="<c:out value="${anteriorUdocUrl}" escapeXml="false" />"><html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.prev.unidad.documental" titleKey="archigest.archivo.prev.unidad.documental" styleClass="imgTextMiddle" /></a>
							</c:if>
							|
							<c:if test="${nextUnidadDocumental!=null}">
								<a class="etiquetaAzul12Bold" href="<c:out value="${siguienteUdocUrl}" escapeXml="false" />"><html:img page="/pages/images/Next.gif" altKey="archigest.archivo.next.unidad.documental" titleKey="archigest.archivo.next.unidad.documental" styleClass="imgTextMiddle" /></a>
							</c:if>
						</c:if>
				</TD>
			</c:if>
		</TR>
	</TABLE>