<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.series.valoracion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
				<c:url var="buscarValoracionURL" value="/action/buscarValoracion">
					<c:param name="method" value="formBusqueda" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${buscarValoracionURL}" escapeXml="false"/>">
					<html:img page="/pages/images/buscar_go.gif" 
						altKey="archigest.archivo.valoracion.buscar" 
						titleKey="archigest.archivo.valoracion.buscar" 
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.buscar"/>
				</a>
				</td>
				<td width="10">&nbsp;</td>

				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<%-- VALORACIONES EN ELABORACION --%>
		<security:permissions permission="${appConstants.permissions.GESTOR_SERIE}">
		<c:set var="listaValoraciones" value="${requestScope[appConstants.valoracion.LISTA_VALORACIONES_EN_ELABORACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.enElaboracion"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<security:permissions action="${appConstants.fondosActions.EDITAR_VALORACIONES_ACTION}">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td nowrap>
						<c:url var="iniciarValoracionURL" value="/action/gestionValoracion">
							<c:param name="method" value="nuevaValoracion" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${iniciarValoracionURL}" escapeXml="false"/>">
							<html:img page="/pages/images/new.gif" altKey="archigest.archivo.valoracion.iniValoracion" titleKey="archigest.archivo.valoracion.iniValoracion" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.valoracion.iniValoracion"/>
						</a>
						</td>
					</tr>
				</table>
				</security:permissions>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaValoraciones"
				id="valoracion" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<div class="separador8">&nbsp;</div>
					<bean:message key="archigest.archivo.valoraciones.ningunaValoracionEnElaboracion"/>
					<div class="separador8">&nbsp;</div>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.codigo" sortable="true" headerClass="sortable">
					<c:url var="infoValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="verValoracion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>"><c:out value="${valoracion.titulo}" /></a>
				</display:column>
				<display:column titleKey="archigest.archivo.serie">
					<c:out value="${valoracion.tituloSerie}" />
				</display:column>

				<display:column titleKey="archigest.archivo.estado">
					<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaValoraciones[0]}">
		<div class="pie_bloque_right">
			<c:url var="valoracionesEnElaboracionURL" value="/action/gestionValoracion">
				<c:param name="method" value="valoracionesEnElaboracion" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${valoracionesEnElaboracionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>
		</security:permissions>

		<%-- LISTA DE VALORACIONES A GESTIONAR --%>
		<security:permissions permission="${appConstants.permissions.GESTION_VALORACIONES}">
		<c:set var="listaValoraciones" value="${requestScope[appConstants.valoracion.LISTA_VALORACIONES_A_GESTIONAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.aGestionar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaValoraciones"
				id="valoracion" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<div class="separador8">&nbsp;</div>
					<bean:message key="archigest.archivo.valoraciones.ningunaValoracionAGestionar"/>
					<div class="separador8">&nbsp;</div>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.codigo" sortable="true" headerClass="sortable">
					<c:url var="infoValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="verValoracion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>"><c:out value="${valoracion.titulo}" /></a>
				</display:column>
				<display:column titleKey="archigest.archivo.serie">
					<c:out value="${valoracion.tituloSerie}" />
				</display:column>

				<display:column titleKey="archigest.archivo.estado">
					<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			<script>removeCookie("tabSeleccionDatos");</script>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaValoraciones[0]}">
		<div class="pie_bloque_right">
			<c:url var="valoracionesAGestionarURL" value="/action/gestionValoracion">
				<c:param name="method" value="valoracionesAGestionar" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${valoracionesAGestionarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		</security:permissions>

	</tiles:put>
</tiles:insert>