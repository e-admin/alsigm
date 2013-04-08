<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relaciones"/></tiles:put>
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

		<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

		<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
		<%--RELACIONES EN ELABORACION --%>
		<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_EN_ELABORACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionesElaboracion"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaRelaciones"
				id="relacion" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noRel"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.relacion"  style="white-space: nowrap;">
					<c:url var="verRelacionURL" value="/action/gestionRelaciones">
						<c:param name="method" value="verrelacion" />
						<c:param name="idrelacionseleccionada" value="${relacion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
						<c:out value="${relacion.codigoTransferencia}"/>
					</a>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${relacion.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.estado">
					<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${relacion.estado}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.fEstado">
					<fmt:formatDate value="${relacion.fechaestado}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.procedimiento" style="width:25%">
					<c:out value="${relacion.idprocedimiento}"/>&nbsp;<c:out value="${relacion.procedimiento.nombre}"/>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.serie" style="width:25%">
					<c:out value="${relacion.serie.codigo}"/>
					<c:out value="${relacion.serie.titulo}"/>
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaRelaciones[0]}">
		<div class="pie_bloque_right">
			<c:url var="relacionesEnElaboracionURL" value="/action/gestionRelaciones">
				<c:param name="method" value="listadorelacionesoficina" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${relacionesEnElaboracionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>

		<div class="separador20"></div>
		</security:permissions>

		<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ARCHIVO_RECEPTOR}">
		<%--RELACIONES A GESTIONAR --%>
		<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_A_GESTIONAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionesGestionar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaRelaciones"
				id="relacion" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noRel"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.relacion"  style="white-space: nowrap;">
					<c:url var="verRelacionURL" value="/action/gestionRelaciones">
						<c:param name="method" value="verrelacion" />
						<c:param name="idrelacionseleccionada" value="${relacion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
						<c:out value="${relacion.codigoTransferencia}"/>
					</a>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${relacion.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.estado">
					<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${relacion.estado}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.fEstado">
					<fmt:formatDate value="${relacion.fechaestado}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.procedimiento" style="width:25%">
					<c:out value="${relacion.idprocedimiento}"/>&nbsp;<c:out value="${relacion.procedimiento.nombre}"/>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.serie" style="width:25%">
					<c:out value="${relacion.serie.codigo}"/>
					<c:out value="${relacion.serie.titulo}"/>
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaRelaciones[0]}">
		<div class="pie_bloque_right">
			<c:url var="relacionesAGestionarURL" value="/action/gestionRelaciones">
				<c:param name="method" value="listadorelacionesarchivo" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${relacionesAGestionarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>

		<div class="separador20"></div>
		</security:permissions>

		<security:permissions action="${appConstants.transferenciaActions.GESTION_SOLICITUD_RESERVA}">
		<%--RESERVAS DE ESPACIO A GESTIONAR --%>
		<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RESERVAS_ESPACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.reservasEspacio"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaRelaciones"
				id="relacion" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noReservas"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.relacion"  style="white-space: nowrap;">
					<c:url var="verRelacionURL" value="/action/gestionReservaRelaciones">
						<c:param name="method" value="vernavegador" />
						<c:param name="idrelacionseleccionada" value="${relacion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
					<c:out value="${relacion.codigoTransferencia}"/>
					</a>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${relacion.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.estado">
					<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${relacion.estado}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.fEstado">
					<fmt:formatDate value="${relacion.fechaestado}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.procedimiento" style="width:25%">
					<c:out value="${relacion.idprocedimiento}"/>&nbsp;<c:out value="${relacion.procedimiento.nombre}"/>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.serie" style="width:25%">
					<c:out value="${relacion.serie.codigo}"/>
					<c:out value="${relacion.serie.titulo}"/>
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaRelaciones[0]}">
		<div class="pie_bloque_right">
			<c:url var="reservasPendientesURL" value="/action/gestionReservaRelaciones">
				<c:param name="method" value="listadorelaciones" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${reservasPendientesURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		</security:permissions>
	</tiles:put>
</tiles:insert>