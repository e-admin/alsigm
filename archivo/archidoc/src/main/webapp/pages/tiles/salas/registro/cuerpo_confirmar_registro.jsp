<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="archivo" value="${sessionScope[appConstants.salas.ARCHIVO_KEY]}" />
<c:set var="registro" value="${sessionScope[appConstants.salas.REGISTRO_CONSULTA_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.registro.confirmar"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
		<tr>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
				</tiles:insert>
			</td>
			<td width="10"></td>
			<td>
				<c:url var="guardarRegistroURL" value="/action/gestionRegistroConsultaAction">
					<c:param name="method" value="guardar" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${guardarRegistroURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.finalizar" titleKey="archigest.archivo.finalizar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.finalizar"/>
				</a>
			</td>
			<td width="10"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
				</tiles:insert>
			</td>
		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.datos.usuario.consulta"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table>
					<tr>
						<td>
							<a href="javascript:showHideDiv('User');">
								<html:img styleId="imgUser" page="/pages/images/down.gif" titleKey="archigest.archivo.desplegar" altKey="archigest.archivo.desplegar" styleClass="imgTextBottom" />
			   				</a>
			   			</td>
			   		</tr>
			   	</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<tiles:insert name="salas.datos.usuario.consulta" flush="true"/>
			</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.datos.registro" />
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td width="180px" class="tdTitulo">
							<bean:message key="archigest.archivo.archivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${archivo.nombre}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.mesa.asignada"/>:&nbsp;
						</td>
						<td class="tdDatos">
							 <c:out value="${registro.mesaAsignada}"/>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>