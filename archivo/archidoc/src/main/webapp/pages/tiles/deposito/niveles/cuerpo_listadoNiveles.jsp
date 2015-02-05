<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionNivelesDeposito" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="listaNiveles" value="${sessionScope[appConstants.deposito.LISTA_NIVELES_KEY]}" />

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

		<tiles:put name="boxTitle" direct="true">
			<bean:message key="NavigationTitle_DEPOSITO_NIVELES"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">

			<table cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
			</table>
		</tiles:put>

		<c:url var="paginationURL" value="/action/gestionNivelesDeposito" >
			<c:param name="method" value="init"/>
		</c:url>
		<jsp:useBean id="paginationURL" type="java.lang.String" />
		<tiles:put name="boxContent" direct="true">
			<div class="bloque">
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaNiveles"
						id="nivel"
						htmlId="listaNiveles"
						sort="list"
						style="width:98%;margin-left:auto;margin-right:auto"
						requestURI='<%=paginationURL%>'
						excludedParams="*"
						pagesize="15">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.depositos.niveles.listaVacia"/>
					</display:setProperty>
					<display:column style="width:30px;text-align:center">
						<html-el:img page="/pages/images/arboles/${nivel.imagen}" styleClass="imgTextMiddle"
								altKey="${nivel.nombre}" titleKey="${nivel.nombre}"/>
					</display:column>

					<display:column style="width:60px;text-align:center" titleKey="archigest.archivo.depositos.niveles.asignable">
						<c:choose>
			          		<c:when test="${nivel.tipoAsignable}">
								<bean:message key="archigest.archivo.si"/>
			          		</c:when>
			          		<c:otherwise>
			          			<bean:message key="archigest.archivo.no"/>
			          		</c:otherwise>
			          	</c:choose>

					</display:column>
					<display:column titleKey="archigest.archivo.depositos.niveles.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
						<c:url var="datosNivelURL" value="/action/gestionNivelesDeposito">
							<c:param name="method" value="verNivel"/>
							<c:param name="idNivel" value="${nivel.id}" />
						</c:url>

						<a href='<c:out value="${datosNivelURL}" escapeXml="false" />' class="tdlink">
							<c:out value="${nivel.nombre}"/>
						</a>
					</display:column>
				</display:table>
				<div class="separador10">&nbsp;</div>
			</div>
		</tiles:put>
	</tiles:insert>