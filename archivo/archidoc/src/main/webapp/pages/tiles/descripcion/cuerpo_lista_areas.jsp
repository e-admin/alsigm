<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<bean:struts id="actionMapping" mapping="/gestionArea" />
<html:form action="/gestionArea">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.cacceso.gestionArea"/>
</tiles:put>

<tiles:put name="buttonBar" direct="true">
	<table>
		<tr>
				<td width="10px"></td>
				<td nowrap>
					<c:url var="createURL" value="/action/gestionArea">
						<c:param name="method" value="new" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.crear"/></a>
				</td>


				<td width="10px"></td>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:removeArea(document.forms['areasForm'],'<bean:message key='archigest.archivo.cacceso.msgConfirmAreasEliminar'/>','<bean:message key='archigest.archivo.cacceso.msgNoAreasEliminar'/>')" >

					<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.eliminar"/></a>
				</td>

	  		<td width="10px"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="true"/>
			</td>
		</tr>
	</table>
</tiles:put>

<c:url var="paginationURL" value="/action/gestionArea" />
<jsp:useBean id="paginationURL" type="java.lang.String" />
<tiles:put name="boxContent" direct="true">
	<div id="barra_errores"><archivo:errors /></div>
	<div class="bloque">
		<c:set var="listaAreas" value="${requestScope[appConstants.controlAcceso.LISTA_AREAS]}" />
		<display:table name="pageScope.listaAreas"
				id="area"
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				requestURI='<%=paginationURL%>'
				pagesize="15"
				export="true"
				>
			<display:column style="width:10px" title="" media="html">
				<html-el:multibox property="areasABorrar" value="${area.id}"></html-el:multibox>
			</display:column>

			<display:column titleKey="archigest.archivo.menu.admin.gestionArea.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
	  			<c:url var="verURL" value="/action/gestionArea">
					<c:param name="method" value="retrieveFromList" />
					<c:param name="idArea" value="${area.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
					<c:out value="${area.nombre}" />
				</a>
	  		</display:column>

	  		<!-- Exportar -->
	  		<display:column titleKey="archigest.archivo.menu.admin.gestionArea.nombre" property="nombre" media="csv excel xml pdf"/>


			<display:column titleKey="archigest.archivo.identificador" property="id" sortable="true" headerClass="sortable" />
		</display:table>
	</div>
</tiles:put>
</tiles:insert>
</html:form>