<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">Valoración dictaminada de la serie</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionValoracion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert definition="valoracion.datosValoracion">
		</tiles:insert>
	</tiles:put>
</tiles:insert>