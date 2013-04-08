<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="elementoDeposito" value="${requestScope[appConstants.deposito.ELEMENTO_DEPOSITO_KEY]}" />
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.informe"/> <bean:message key="archigest.archivo.deposito.ocupacion"/>
	</tiles:put>
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

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
					<td width="100px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<c:out value="${elementoDeposito.pathName}" />
					</td>
				</tr>
			</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador8"></div>

	<div class="bloque">
	<table class="formulario">
		<tr>
			<td width="150px" class="tdTitulo">
				<bean:message key="archigest.archivo.deposito.total"/>
				<bean:message key="archigest.archivo.deposito.huecos"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${__INFORME_OCUPACION.totalHuecos}" />
			</td>
		</tr>
		<c:if test="${!empty __INFO_ELEMENTO}">
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.deposito.huecos"/>
					<bean:message key="archigest.archivo.deposito.ocupados"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${__INFORME_OCUPACION.totalHuecosOcupados}" />&nbsp;
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.deposito.huecos"/>
					<bean:message key="archigest.archivo.deposito.reservados"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${__INFORME_OCUPACION.totalHuecosReservados}" />&nbsp;
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.deposito.huecos"/>
				<bean:message key="archigest.archivo.deposito.libres"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${__INFORME_OCUPACION.totalHuecosLibres}" />
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.deposito.huecos"/>
				<bean:message key="archigest.archivo.deposito.inutilizados"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${__INFORME_OCUPACION.totalHuecosInutilizados}" />
			</td>
		</tr>
		<c:if test="${__INFORME_OCUPACION.totalHuecos gt 0}">
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.deposito.porcentaje"/>
					<bean:message key="archigest.archivo.deposito.ocupacion"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${__INFORME_OCUPACION.porcentajeOcupacion}" />&nbsp;<bean:message key="archigest.archivo.simbolo.porcentaje"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.deposito.longitud"/>
				<bean:message key="archigest.archivo.deposito.total"/>:&nbsp;
			</td>
			<td class="tdDatos">
					<c:set var="metros" value="0"/>
					<c:choose>
						<c:when test="${__INFORME_OCUPACION.totalLongitud gt 0}">
							<c:set var="metros" value="${__INFORME_OCUPACION.longitudEnMetros}"/>
						</c:when>
					</c:choose>
					<c:out value="${metros}" />&nbsp;
					<bean:message key="archigest.archivo.m" />
			</td>
		</tr>
	</table>
	<c:set var="treeView" value="${sessionScope[appConstants.deposito.DEPOSITO_VIEW_NAME]}" />
	<c:set var="selectedNode" value="${treeView.selectedNode}" />

	<c:if test="${empty __INFO_ELEMENTO}">

			<c:set var="entradasInformeOcupacion" value="${__INFORME_OCUPACION.entradasInforme}" />
			<display:table id="entradaInforme" 
				style="width:99%; margin-bottom:10px;margin-top:10px;margin-left:auto;margin-right:auto"
				name="pageScope.entradasInformeOcupacion">
	
			<display:setProperty name="basic.msg.empty_list"><bean:message key="archigest.archivo.cf.noDescendientes"/></display:setProperty>
				<c:url var="urlVerElemento" value="/action/manageVistaDeposito">
					<c:param name="actionToPerform" value="goHome"/>
					<c:param name="itemID" value="${entradaInforme.idElemento}"/>
					<c:param name="itemTipo" value="${entradaInforme.idTipoElemento}"/>
				</c:url>										
				<display:column titleKey="archigest.archivo.descripcion">
					<a target="_self" class="tdlink12" href="javascript:window.document.location='<c:out value='${urlVerElemento}' escapeXml='false'/>'">
						<c:out value="${entradaInforme.descripcion}"/>
					</a>
				</display:column>
				
				<display:column titleKey="archigest.archivo.deposito.huecos" property="numeroHuecos" />
				<display:column titleKey="archigest.archivo.deposito.huecosLibres" property="huecosLibres" />
				<display:column titleKey="archigest.archivo.deposito.huecosInutilizados" property="huecosInutilizados" />
				<display:column titleKey="archigest.archivo.deposito.porcentajeOcupacion">
					<c:choose>
						<c:when test="${entradaInforme.numeroHuecos gt 0}">
						<c:out value="${entradaInforme.porcentajeOcupacion}" />&nbsp;<bean:message key="archigest.archivo.simbolo.porcentaje"/>
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.deposito.longitud">
					<c:set var="metros" value="0"/>				
					<c:choose>
						<c:when test="${entradaInforme.longitud gt 0}">
							<c:set var="metros" value="${entradaInforme.longitudEnMetros}"/>
						</c:when>
					</c:choose>
					<c:out value="${metros}" />&nbsp;
					<bean:message key="archigest.archivo.m" />
				</display:column>
				
			</display:table>

	</c:if>
	</div>
	</tiles:put>
</tiles:insert>