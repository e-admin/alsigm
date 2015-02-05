<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.consultas.gestion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<security:permissions permission="${appConstants.permissions.ESTANDAR_SOLICITUD_CONSULTAS}">
		<%--CONSULTAS EN ELABORACION --%>
		<c:set var="consultasEnElaboracion" value="${requestScope[appConstants.consultas.LISTA_CONSULTAS_EN_ELABORACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.consultas.enElaboracion"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td nowrap>
						<c:url var="urlCrearConsulta" value="/action/gestionConsultas">
							<c:param name="method" value="nuevo"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href='<c:out value="${urlCrearConsulta}" escapeXml="false"/>'>
							<html:img titleKey="archigest.archivo.consultas.crear" altKey="archigest.archivo.consultas.crear" page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.consultas.crear"/>
						</a>
						</td>
					</tr>
				</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.consultasEnElaboracion"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="consulta" 
				export="false">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.consultas.noPrev"/>
			</display:setProperty>
			
			<display:column titleKey="archigest.archivo.consultas.consulta">
				<c:url var="verConsultaURL" value="/action/gestionConsultas">
					<c:param name="method" value="verconsulta" />
					<c:param name="id" value="${consulta.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verConsultaURL}" escapeXml="false"/>" >
					<c:out value="${consulta.codigoTransferencia}"/>
				</a>
			</display:column> 
				
			<display:column titleKey="archigest.archivo.consultas.norgconsultor" property="norgconsultor" />
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" property="nusrconsultor" />
			<display:column titleKey="archigest.archivo.consultas.estado">
				<fmt:message key="archigest.archivo.solicitudes.consulta.estado.${consulta.estado}" />
			</display:column>
			<display:column titleKey="archigest.archivo.consultas.festado">
				<fmt:formatDate value="${consulta.festado}" pattern="${FORMATO_FECHA}" />
			</display:column>
				
			</display:table>  
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty consultasEnElaboracion}">
		<div class="pie_bloque_right">
			<c:url var="consultasEnElaboracionURL" value="/action/gestionConsultas">
				<c:param name="method" value="listadoconsultaver" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${consultasEnElaboracionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>

		<div class="separador20"></div>
		</security:permissions>

		<security:permissions permission="${appConstants.permissions.GESTION_SOLICITUDES_CONSULTAS}">
		<%--CONSULTAS A GESTIONAR --%>
		<c:set var="consultasAGestionar" value="${requestScope[appConstants.consultas.LISTA_CONSULTAS_A_GESTIONAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.consultas.aGestionar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.consultasAGestionar"
				id="consulta" 
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.consultas.noPrev"/>
			</display:setProperty>
			
			<display:column titleKey="archigest.archivo.consultas.consulta">
				<c:url var="verConsultaURL" value="/action/gestionConsultas">
					<c:param name="method" value="verconsulta" />
					<c:param name="id" value="${consulta.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verConsultaURL}" escapeXml="false"/>" >
					<c:out value="${consulta.codigoTransferencia}"/>
				</a>
			</display:column> 
				
			<display:column titleKey="archigest.archivo.consultas.norgconsultor" property="norgconsultor" />
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" property="nusrconsultor" />
			<display:column titleKey="archigest.archivo.consultas.estado">
				<fmt:message key="archigest.archivo.solicitudes.consulta.estado.${consulta.estado}" />
			</display:column>
			<display:column titleKey="archigest.archivo.consultas.festado">
				<fmt:formatDate value="${consulta.festado}" pattern="${FORMATO_FECHA}" />
			</display:column>

			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty consultasAGestionar}">
		<div class="pie_bloque_right">
			<c:url var="consultasAGestionarURL" value="/action/gestionConsultas">
				<c:param name="method" value="listadoconsulta" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${consultasAGestionarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>

		<div class="separador20"></div>
		</security:permissions>

		<security:permissions permission="${appConstants.permissions.ENTREGA_UNIDADES_DOCUMENTALES}">
		<%--CONSULTAS A ENTREGAR --%>
		<c:set var="consultasAEntregar" value="${requestScope[appConstants.consultas.LISTA_CONSULTAS_A_ENTREGAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.consultas.aEntregar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.consultasAEntregar"
				id="consulta" 
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.consultas.noPrev"/>
			</display:setProperty>
			
			<display:column titleKey="archigest.archivo.consultas.consulta">
				<c:url var="verConsultaURL" value="/action/gestionConsultas">
					<c:param name="method" value="verconsulta" />
					<c:param name="id" value="${consulta.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verConsultaURL}" escapeXml="false"/>" >
					<c:out value="${consulta.codigoTransferencia}"/>
				</a>
			</display:column> 
				
			<display:column titleKey="archigest.archivo.consultas.norgconsultor" property="norgconsultor" />
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" property="nusrconsultor" />
			<display:column titleKey="archigest.archivo.consultas.estado">
				<fmt:message key="archigest.archivo.solicitudes.consulta.estado.${consulta.estado}" />
			</display:column>
			<display:column titleKey="archigest.archivo.consultas.festado">
				<fmt:formatDate value="${consulta.festado}" pattern="${FORMATO_FECHA}" />
			</display:column>

			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
			<c:if test="${!empty consultasAEntregar}">
		<div class="pie_bloque_right">
			<c:url var="consultasAEntregarURL" value="/action/gestionConsultas">
				<c:param name="method" value="listadoconsultaentregar" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${consultasAEntregarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		</security:permissions>

	</tiles:put>
</tiles:insert>