<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="listaPadres" value="${requestScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}" />

<tiles:definition id="infoFondo" extends="fondos.fondo.infoFondo">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.cf.elementoMover" />
	</tiles:put>
</tiles:definition>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.moverFondo" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<c:if test="${!empty listaPadres}">
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['seleccionNuevoClasificador'].submit();" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			</td>		
			<td width="10px">&nbsp;</td>
		</c:if>
		<td nowrap>
			<c:url var="cancelURL" value="/action/gestionFondoAction">
				<c:param name="method" value="goBack" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			</a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert beanName="infoFondo">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.fondoAMover"/></tiles:put>
		</tiles:insert>

		<div class="separador1">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cf.msgselNuevaUbicacionFondo" />
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<div class="separador1">&nbsp;</div>

				<c:url var="moverFondoURL" value="/action/gestionFondoAction" />
				<form name="seleccionNuevoClasificador" action="<c:url value="/action/gestionFondoAction" />">
				<c:set var="vFondo" value="${requestScope[appConstants.fondos.FONDO_KEY]}"/>

				<input type="hidden" name="method" value="moverFondo">
				<input type="hidden" name="idFondo" value="<c:out value="${vFondo.id}" />">
				<display:table name="pageScope.listaPadres" id="clasificadorFondos" 
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:column style="width:15px">
						<input type="radio" name="clasificadorSeleccionado" value="<c:out value="${clasificadorFondos.id}" />" >
					</display:column>
					<display:column titleKey="archigest.archivo.cf.codigo" property="codReferencia" />
					<display:column titleKey="archigest.archivo.cf.denominacion" property="titulo" />
				</display:table>

				</form>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>