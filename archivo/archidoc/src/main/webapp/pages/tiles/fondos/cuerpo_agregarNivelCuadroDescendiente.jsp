<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionNivelesCuadro" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="listaPosiblesHijos" value="${requestScope[appConstants.fondos.LISTA_POSIBLES_NIVELES_HIJO]}" />
<c:set var="nivel" value="${requestScope[appConstants.fondos.NIVEL_CF_KEY]}"/>

<html:form action="/gestionNivelesCuadro" styleId="formulario">
	<input type="hidden" name="method" value="addNivelDescendiente">
	<input type="hidden" name="idNivel" value="<c:out value="${nivel.id}" />">
	<input type="hidden" name="tipoNivel" value="<c:out value="${nivel.tipo}" />">
	
	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

		<tiles:put name="boxTitle" direct="true">
			<bean:message key="NavigationTitle_CUADRO_CLASIFICACION_NIVELES_AGREGAR_NIVEL"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
		
			<table cellpadding=0 cellspacing=0>
			<tr>
				<logic:notEmpty name="listaPosiblesHijos">
				<td>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${actionMapping.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
	            </td>
	            </logic:notEmpty>
				<td width="20">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
			</table>
		</tiles:put>
		
		<c:url var="paginationURL" value="/action/gestionNivelesCuadro" >
			<c:param name="method" value="agregarNivelHijo"/>
			<c:param name="idNivel" value="${nivel.id}"/>
		</c:url>
		<jsp:useBean id="paginationURL" type="java.lang.String" />
		<tiles:put name="boxContent" direct="true">
			<div class="bloque">	
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaPosiblesHijos" 
						id="nivel" 
						htmlId="listaPosiblesHijos"
						sort="list" 
						style="width:98%;margin-left:auto;margin-right:auto"
						requestURI='<%=paginationURL%>'
						excludedParams="*"
						pagesize="15">
						
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.fondos.cf.niveles.listaPosiblesHijosVacia"/>
					</display:setProperty>
					
					<display:column style="width:10px" title="">
						<input type="checkbox" name="descendientes" value="<c:out value="${nivel.id}" />" >
					</display:column>												
																		
					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">						
						<bean:write name="nivel" property="nombre"/>																																
					</display:column>
				</display:table>
				<div class="separador10">&nbsp;</div>
			</div>
		</tiles:put>
	</tiles:insert>
</html:form>