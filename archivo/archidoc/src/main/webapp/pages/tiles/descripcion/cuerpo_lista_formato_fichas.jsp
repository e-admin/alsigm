<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="actionMapping" mapping="/gestionFormatoFicha" />
	<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/gestion_ficha.js" type="text/JavaScript"></script>
<html:form action="/gestionFormatoFicha">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.cacceso.gestionFormatoFicha"/>
</tiles:put>

	
<tiles:put name="buttonBar" direct="true">
	<table>
		<tr>
				<td width="10px"></td>
				<td nowrap>
					<c:url var="busquedaURL" value="/action/gestionFormatoFicha">
						<c:param name="method" value="verBuscador" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/></a>
				</td>

				<td width="10px"></td>
				<td nowrap>
					<c:url var="createURL" value="/action/gestionFormatoFicha">
						<c:param name="method" value="new" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.crear"/></a>
				</td>
				<td width="10px"></td>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:removeFormato(document.forms['formatoFichasForm'],'<bean:message key='archigest.archivo.cacceso.msgConfirmFormatosFichasEliminar'/>','<bean:message key='archigest.archivo.cacceso.msgNoFormatosFichasEliminar'/>')" >
					
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

<c:url var="paginationURL" value="/action/gestionFormatoFicha" />
<jsp:useBean id="paginationURL" type="java.lang.String" />

<tiles:put name="boxContent" direct="true">
	<div id="barra_errores"><archivo:errors /></div>
	<div class="bloque">
		<c:set var="listaFormatosFichas" value="${requestScope[appConstants.controlAcceso.LISTA_FORMATO_FICHAS]}" />
		<display:table name="pageScope.listaFormatosFichas"
				id="formato"
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				requestURI='<%=paginationURL%>'
				pagesize="15">
			<display:column style="width:10px" title="">
				<input type="checkbox" name="formatosABorrar" value="<c:out value="${formato.id}" />" >
			</display:column>
			<display:column titleKey="archigest.archivo.menu.admin.gestionFicha.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
	  			<c:url var="verURL" value="/action/gestionFormatoFicha">
					<c:param name="method" value="retrieveFromList" />
					<c:param name="idFormato" value="${formato.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
					<c:out value="${formato.nombre}" />
				</a>
	  		</display:column>
	  		<display:column titleKey="archigest.archivo.descripcion.descriptor.form.nivelAcceso" sortProperty="nivelAcceso" sortable="true" headerClass="sortable"> 
	  			<c:choose>
	  				<c:when test="${formato.nivelAcceso == 1}"><bean:message key="archivo.nivel_acceso.publico"/></c:when>
	  				<c:when test="${formato.nivelAcceso == 2}"><bean:message key="archivo.nivel_acceso.archivo"/></c:when>
	  				<c:when test="${formato.nivelAcceso == 3}"><bean:message key="archivo.nivel_acceso.restringido"/></c:when>
	  			</c:choose>
	  		</display:column>
	  		<display:column titleKey="archigest.archivo.descripcion.fmtfichas.form.tipo" sortProperty="tipo" sortable="true" headerClass="sortable"> 
	  			<c:choose>
	  				<c:when test="${formato.tipo == 1}"><bean:message key="archigest.archivo.consultas.consulta"/></c:when>
	  				<c:when test="${formato.tipo == 2}"><bean:message key="archigest.archivo.formato.tipo.edicion"/></c:when>
	  			</c:choose>
	  		</display:column>	  		
		</display:table>
	</div>
</tiles:put>
</tiles:insert>
</html:form>