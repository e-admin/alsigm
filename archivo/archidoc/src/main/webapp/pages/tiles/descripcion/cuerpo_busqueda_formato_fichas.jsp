<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingFormatoFichas" mapping="/gestionFormatoFicha" />
<c:set var="listaFormatosFichas" value="${requestScope[appConstants.controlAcceso.LISTA_FORMATO_FICHAS]}" />
<c:set var="listaFichas" value="${sessionScope[appConstants.controlAcceso.LISTA_FICHAS]}" />

<html:form action="/gestionFormatoFicha">
<input type="hidden" name="method" value="buscar">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.busquedaFicha"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td nowrap>
			<script>
				function buscar() {
					var form = document.forms['<c:out value="${mappingFormatoFichas.name}" />'];
					form.submit();
				}
			</script>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" width="180"><bean:message key="archigest.archivo.descripcion.fichas.form.nombre"/>:&nbsp;</td>
				<td class="tdDatos"><html:text property="nombre" size="64" maxlength="64"/>&nbsp;</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.cf.ficha"/></td>
				<td class="tdDatos">
					<html:select property="idFicha" styleClass="input">
						<html:option value=""></html:option>
						<html:optionsCollection name="listaFichas" label="nombre" value="id"/>
					</html:select>
				</td>
			</tr>
		</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${listaFormatosFichas != null}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

			<c:url var="paginationURL" value="/action/gestionFormatoFicha" />
			<jsp:useBean id="paginationURL" type="java.lang.String" />

		<display:table name="pageScope.listaFormatosFichas"
				id="formato"
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				requestURI='<%=paginationURL%>'
				pagesize="15">
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
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>
