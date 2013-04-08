<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="serie" value="${requestScope[appConstants.fondos.SERIE_KEY]}"/>
<c:if test="${empty serie}" >
<c:set var="serie" value="${sessionScope[appConstants.fondos.SERIE_KEY]}"/>
</c:if>
<c:if test="${empty serie}" >
<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>
</c:if>
<c:if test="${empty serie}" >
<c:set var="serie" value="${requestScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>
</c:if>

		<table class="formulario">
			<tr>
				<td width="150px" class="tdTitulo" style="vertical-align:top;">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> - <c:out value="${serie.titulo}" />
				</td>
			</tr>
		</table>
		<div class="separador5">&nbsp;</div>

		<table class="formulario">
			<tr>
				<td width="150px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</td>
			</tr>

			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.gestor"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${serie.gestor.nombreCompleto}" />
				</td>
			</tr>
		
		</table>
