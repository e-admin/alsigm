<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.gestionCampoDato"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>			  	
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>	
	</tiles:put>	

	<c:url var="paginationURL" value="/action/gestionReemplazarValores" />
	<jsp:useBean id="paginationURL" type="java.lang.String" />
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<c:set var="listaSeries" value="${requestScope[appConstants.descripcion.DESCRIPCION_LISTA_SERIES_AFECTADAS]}" />
			
			<display:table name="pageScope.listaSeries"
					id="nodo"
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI='<%=paginationURL%>'
					pagesize="15">
				<display:column titleKey="archigest.archivo.codigo" sortable="true" headerClass="sortable" >
					<c:url var="verURL" value="/action/buscarElementos">
						<c:param name="method" value="verEnCuadro" />
						<c:param name="id" value="${nodo.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
						<c:out value="${nodo.codigo}" />
					</a>
				</display:column>
		  		<display:column titleKey="archigest.archivo.titulo" property="titulo" sortable="true" headerClass="sortable" />
		  		<display:column titleKey="archigest.archivo.codigoReferencia" property="codReferencia" sortable="true" headerClass="sortable" />	
		  		<display:column titleKey="archigest.archivo.cf.codReferenciaFondo" property="codRefFondo" sortable="true" headerClass="sortable" />	
			</display:table>	
		</div>
	</tiles:put>
</tiles:insert>