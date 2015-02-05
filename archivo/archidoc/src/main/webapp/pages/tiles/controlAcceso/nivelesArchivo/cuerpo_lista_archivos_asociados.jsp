<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<c:set var="listaArchivosAsociados" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS_ASOCIADOS]}" />
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.nivelesArchivo.listaArchivosAsociados"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
	      	  <TD nowrap>
				<tiles:insert definition="button.closeButton" />
			</TD>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.listaArchivosAsociados"
							   id="archivoAsociado"
							   htmlId="listaArchivosAsociados"
							   style="width:99%;margin-left:auto;margin-right:auto">
					<display:column titleKey="archigest.archivo.codigo">
						<c:url var="URL" value="/action/gestionArchivos">
							<c:param name="method" value="editarArchivo"/>
							<c:param name="idArchivo" value="${archivoAsociado.id}" />
						</c:url>

						<a href="<c:out value="${URL}" escapeXml="false" />" class="tdlink">
							<c:out value="${archivoAsociado.codigo}" />
						</a>
					</display:column>

					<display:column titleKey="archigest.archivo.nombre" property="nombre"/>
				</display:table>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>

</tiles:insert>