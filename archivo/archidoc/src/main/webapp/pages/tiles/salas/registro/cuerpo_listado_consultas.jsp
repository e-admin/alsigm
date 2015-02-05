<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<c:set var="listadoConsultas" value="${sessionScope[appConstants.salas.LISTA_CONSULTAS_USUARIO_KEY]}" />
<c:set var="idUsrCSala" value="${requestScope[appConstants.salas.PARAM_ID_USUARIO]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.listado.consultas.usuario"/>
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
		<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<div class="separador1">&nbsp;</div>
				<div class="bloque">
					<c:url var="paginationURL" value="/action/gestionConsultas" >
						<c:param name="method" value="listadoConsultasSala"/>
						<c:param name="idUsuario" value="${idUsrCSala}"/>
					</c:url>
					<jsp:useBean id="paginationURL" type="java.lang.String" />
					<display:table name="pageScope.listadoConsultas"
						style="width:99%;margin-left:auto;margin-right:auto"
						id="consulta"
						pagesize="15"
						sort="list"
						requestURI='<%=paginationURL%>'
						excludedParams="*"
						export="true">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.consultas.noPrev"/>
						</display:setProperty>

						<display:column titleKey="archigest.archivo.consultas.consulta" sortable="true" headerClass="sortable" sortProperty="codigoTransferencia" media="html">
							<c:url var="URL" value="/action/gestionConsultas">
								<c:param name="method" value="verconsulta" />
								<c:param name="id" value="${consulta.id}" />
							</c:url>
							<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
								<c:out value="${consulta.codigoTransferencia}"/>
							</a>
						</display:column>
						<display:column titleKey="archigest.archivo.consultas.consulta" media="csv excel xml pdf">
							<c:out value="${consulta.codigoTransferencia}"/>
						</display:column>

						<display:column titleKey="archigest.archivo.salas.consulta.usuario" sortable="true" headerClass="sortable" property="nombreUsuarioSala" media="html csv excel xml pdf"/>
						<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" sortable="true" headerClass="sortable" property="nusrconsultor" media="html csv excel xml pdf"/>
						<display:column titleKey="archigest.archivo.consultas.estado" sortable="true" headerClass="sortable" media="html csv excel xml pdf">
							<fmt:message key="archigest.archivo.solicitudes.consulta.estado.${consulta.estado}"/>
						</display:column>
						<display:column titleKey="archigest.archivo.consultas.festado" sortable="true" headerClass="sortable" media="html csv excel xml pdf">
							<fmt:formatDate value="${consulta.festado}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</display:column>
					</display:table>
				</div>
				<div class="separador1">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>