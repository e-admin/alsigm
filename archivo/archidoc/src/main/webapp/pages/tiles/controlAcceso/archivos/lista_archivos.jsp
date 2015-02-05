<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionNivelesArchivo" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="listaArchivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="delimiter" value="${appConstants.common.DELIMITER_IDS}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="NavigationTitle_ARCHIVOS"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
		<tr>
			<td>
				<c:url var="altaArchivoURL" value="/action/gestionArchivos">
					<c:param name="method" value="nuevoArchivo"/>
				</c:url>

				<a class="etiquetaAzul12Bold" href="<c:out value="${altaArchivoURL}" escapeXml="false" />" >
					<html:img page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"
						altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear"/>
					<bean:message key="archigest.archivo.crear"/>
				</a>
			</td>
			 <td width="20">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true"/>
			</td>
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

					<html:form action="/gestionArchivos" styleId="formulario">
						<input type="hidden" name="method" id="method"/>

				<div class="bloque">
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaArchivos" id="elemento" htmlId="listaNiveles"
				style="width:98%;margin-left:auto;margin-right:auto">
					<display:column titleKey="archigest.archivo.codigo" >
												<c:url var="infoArchivoURL" value="/action/gestionArchivos">
								<c:param name="method" value="editarArchivo"/>
								<c:param name="idArchivo" value="${elemento.id}" />
							</c:url>

						<a href='<c:out value="${infoArchivoURL}" escapeXml="false" />' class="tdlink">
						<c:out value="${elemento.codigo}"  />
						</a>
					</display:column>

					<display:column titleKey="archigest.archivo.nivel" property="nombreNivel"/>
					<display:column titleKey="archigest.archivo.nombre.corto" property="nombrecorto"/>
					<display:column titleKey="archigest.archivo.nombre" >
						<c:out value="${elemento.nombre}"  />
					</display:column>

					<display:column titleKey="archigest.archivo.admin.archivos.archivo.defecto" property="nombreReceptor"/>
					<c:if test="${appConstants.configConstants.permitirSeleccionSignaturacion}">
						<display:column titleKey="archigest.archivo.tipo.signaturacion">
							<fmt:message key="${elemento.nombreSignaturacion}"/>
						</display:column>
					</c:if>
				</display:table>
				<div class="separador10">&nbsp;</div>
				</div>
				</html:form>

	</tiles:put>
</tiles:insert>