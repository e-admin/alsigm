<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<c:set var="eliminacion" value="${sessionScope[appConstants.valoracion.ELIMINACION_KEY]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<script>
	function eliminarSelectedItems(selectionFormName) {
		var selectionForm = document.forms[selectionFormName];
		if (selectionForm && selectionForm.selectedUdoc) {
			var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedUdoc");
			if(nSelected >= 1) {
				selectionForm.submit();
			} else 
				alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
		} else {
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
		}
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.eliminacion.datosSelecion"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionEliminacion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false" />" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.informacion"/>
			</tiles:put>
	
			<tiles:put name="blockContent" value="valoracion.datosEliminacion" type="definition" />
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		
		<html:form action="/gestionEliminacion" style="padding:0; margin:0;">
			<input type="hidden" name="method" value="delUdocs">
	
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.eliminacion.udocs.conservar"/></tiles:put>
	
					<tiles:put name="buttonBar" direct="true">
						<table cellpadding=0 cellspacing=0>
							<td nowrap>
								<c:url var="addUdocURL" value="/action/gestionEliminacion?method=addUdocs" />
								<a class="etiquetaAzul12Normal" href="<c:out value="${addUdocURL}" escapeXml="false"/>">
										<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.anadir"/>
									</a>		
								</td>
							   <td width="10">&nbsp;</td>
		
							   <td nowrap>
									<bean:struts id="mappingGestionEliminacion" mapping="/gestionEliminacion" />
									<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingGestionEliminacion.name}" />')">
										<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.eliminar"/>
									</a>
							   </td>
						</table>
					</tiles:put>
	
				<tiles:put name="blockContent" direct="true">
				
					<c:set var="unidadesConservar" value="${sessionScope[appConstants.valoracion.ELIMINACION_UDOCS_CONSERVAR_KEY]}" />
					<c:if test="${(unidadesConservar != null) && (not empty unidadesConservar)}">
						<div class="separador8">&nbsp;</div>
						<table cellpadding="0" cellspacing="0" class="w100">
						  <tr>
						   <td align="right">
								<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].selectedUdoc);" >
									<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
									<bean:message key="archigest.archivo.selTodos"/>&nbsp;
								</a>&nbsp;
								<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].selectedUdoc);" >
									<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
									&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
								</a>&nbsp;&nbsp;
						   </td>
						 </tr>
						</table>
					</c:if>
					<div class="separador8">&nbsp;</div>
					<display:table name="pageScope.unidadesConservar" id="unidadDocumental" 
						style="width:99%;margin-left:auto;margin-right:auto"
						sort="list"
						pagesize="0"
						export="true"
						requestURI='<%=request.getContextPath()+"/action/gestionEliminacion?method=edicionEliminacionAvanzado"%>'
						>
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.valoraciones.noUdocs.seleccionadas.conservacion"/>
						</display:setProperty>
						<display:column style="width:20px" headerClass="minusDocIcon" media="html">
							<html-el:multibox property="selectedUdoc" value="${unidadDocumental.idUdoc}" ></html-el:multibox>
						</display:column>
						
						<display:column titleKey="archigest.archivo.transferencias.num" sortable="true" >
							<c:out value="${unidadDocumental_rowNum}"/>
						</display:column>
					
						<display:column titleKey="archigest.archivo.transferencias.asunto" media="html">
							<c:url var="infoUdocURL" value="/action/buscarElementos">
								<c:param name="method" value="verEnCuadro" />
								<c:param name="id" value="${unidadDocumental.idUdoc}" />
							</c:url>
			
							<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
								<c:out value="${unidadDocumental.tituloUdoc}" />
							</a>
						</display:column>
						
						<display:column titleKey="archigest.archivo.transferencias.asunto" media="xml pdf csv" >
							<c:out value="${unidadDocumental.tituloUdoc}" />
						</display:column>
						
						<display:column titleKey="archigest.archivo.fechaInicial">
							<fmt:formatDate value="${unidadDocumental.fechaIniUdoc}" pattern="${FORMATO_FECHA}" />
						</display:column>
						<display:column titleKey="archigest.archivo.fechaFinal">
							<fmt:formatDate value="${unidadDocumental.fechaFinUdoc}" pattern="${FORMATO_FECHA}" />
						</display:column>			
						<display:column titleKey="archigest.archivo.codigo" property="signaturaUdoc"/>
					</display:table>
				</tiles:put>
			</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>