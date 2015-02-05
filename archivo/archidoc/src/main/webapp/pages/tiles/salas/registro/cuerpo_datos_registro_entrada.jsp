<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="registro" value="${sessionScope[appConstants.salas.REGISTRO_CONSULTA_KEY]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}" />

<c:choose>
	<c:when test="${registro!=null}">

<c:set var="archivo" value="${registro.archivo}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.datos.registro.entrada"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<script>
			function registrarSalida(url) {
				if (confirm('<bean:message key="archigest.archivo.salas.registrarSalidaMsg"/>')){
					window.location = url;
				}
			}
		</script>
		<table cellpadding=0 cellspacing=0>
		<tr>
			<c:if test="${!registro.finalizado}">
			<td>
				<c:url var="verConsultasURL" value="/action/gestionConsultas">
					<c:param name="method" value="listadoConsultasSala" />
					<c:param name="idUsuario" value="${registro.idUsrCSala}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${verConsultasURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/consulta.gif" altKey="archigest.archivo.salas.ver.consultas" titleKey="archigest.archivo.salas.ver.consultas" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.salas.ver.consultas"/>
				</a>
			</td>
			<td width="10"></td>
			<td>
				<c:url var="registrarSalidaURL" value="/action/gestionRegistroConsultaAction">
					<c:param name="method" value="registrarSalida" />
					<c:param name="registrosSeleccionados" value="${registro.id}" />
					<c:param name="datosRegistro" value="true" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="javascript:registrarSalida('<c:out value="${registrarSalidaURL}" escapeXml="false"/>')" >
					<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.salas.registrar.salida" titleKey="archigest.archivo.salas.registrar.salida" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.salas.registrar.salida"/>
				</a>
			</td>
			</c:if>
			<td width="10"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="true"/>
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
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.registro.fechaRegistro"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:formatDate value="${registro.fechaEntrada}" pattern="${FORMATO_FECHA}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.registro.horaEntrada"/>:&nbsp;
						</td>
						<td class="tdDatos">
							 <c:out value="${registro.horaEntrada}"/>
						</td>
					</tr>
					<c:if test="${registro.finalizado}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.registro.horaSalida"/>:&nbsp;
						</td>
						<td class="tdDatos">
							 <c:out value="${registro.horaSalida}"/>
						</td>
					</tr>
					</c:if>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>	</c:when>
	<c:otherwise>
		<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
			<tiles:put name="boxTitle" direct="true">
				<bean:message key="archigest.archivo.salas.datos.registro.entrada"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table cellpadding=0 cellspacing=0>
				<tr>
					<td width="10"></td>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true"/>
					</td>
				</tr>
				</table>
			</tiles:put>
			<tiles:put name="boxContent" direct="true">
				<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockContent" direct="true">
						<bean:message key="archigest.archivo.registro.consulta.cerrado"/>
					</tiles:put>
				</tiles:insert>
			</tiles:put>
		</tiles:insert>
	</c:otherwise>
</c:choose>