<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="listaSeries" value="${requestScope[appConstants.fondos.SERIE_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.cederControlSeries"/></tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<c:url var="goBackURL" value="/action/gestionAsignacionSerie">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${goBackURL}" escapeXml="false"/>">
				<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.atras"/>
				</a>
			</td>
			<c:if test="${!empty listaSeries}">
			<td width="10">&nbsp;</td>
			<td>
				<script>
					function goOn() {
						var seleccionGestorForm = document.forms['seleccionGestor'];
						
						if (confirm("<bean:message key='archigest.archivo.cf.confirm.cederControlSeries'/>"))
							seleccionGestorForm.submit();
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			</c:if>
			<td width="10">&nbsp;</td>
			<td>
				<c:url var="closeURL" value="/action/gestionAsignacionSerie">
					<c:param name="method" value="goReturnPoint" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${closeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
				 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.seriesCuyoGestorDeseaModificar"/>:</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>

			<c:url var="asginacionGestorSerieURL" value="/action/gestionAsignacionSerie" />
			<form name="seleccionGestor" action="<c:out value="${asginacionGestorSerieURL}" escapeXml="false"/>">
		
			<display:table 
				name="pageScope.listaSeries" 
				id="serie" 
				style="width:99%;margin-left:auto;margin-right:auto">
			
				<display:column titleKey="archigest.archivo.cf.codigo">
					<c:out value="${serie.codReferencia}" />
					
				</display:column>
				<display:column titleKey="archigest.archivo.cf.denominacion" property="titulo" />
				<display:column titleKey="archigest.archivo.cf.estado">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</display:column>
				<display:column titleKey="archigest.archivo.cf.fondo">
					<c:out value="${serie.fondo.codigo}" />
					<c:out value="${serie.fondo.titulo}" />
				</display:column>
				<display:column titleKey="archigest.archivo.cf.gestor">
					<c:out value="${serie.gestor.nombreCompleto}" />
				</display:column>
			</display:table>

			<span class="separador5"></span>

			<input type="hidden" name="method" value="asignarGestor">
			<c:set var="gestoresDeSeries" value="${requestScope[appConstants.fondos.LISTA_GESTORES_SERIE_KEY]}" />
			<table class="w98">
			<tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.cf.seleccioneElNuevoGestorDeLaSerie"/>:&nbsp;&nbsp;
					<select name="idGestor">
						<option value="">&nbsp;</option>
						<c:forEach var="gestorSeries" items="${gestoresDeSeries}">
							<option value="<c:out value="${gestorSeries.id}" />"> <c:out value="${gestorSeries.nombreCompleto}" />
						</c:forEach>
					</select>
				</td>
			</form>
			</tr></table>
			<span class="separador5"></span>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>