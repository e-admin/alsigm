<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingBuscarEliminacion" mapping="/buscarEliminacion" />

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function clean()
	{
		document.forms['<c:out value="${mappingBuscarEliminacion.name}" />'].fondo.value = "";
		document.forms['<c:out value="${mappingBuscarEliminacion.name}" />'].codigo.value = "";
		document.forms['<c:out value="${mappingBuscarEliminacion.name}" />'].titulo.value = "";
		document.forms['<c:out value="${mappingBuscarEliminacion.name}" />'].tituloSerie.value = "";
		document.forms['<c:out value="${mappingBuscarEliminacion.name}" />'].tituloValoracion.value = "";
		
		deseleccionarCheckboxSet(document.forms[0].estados);
	}

	function buscar(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgSelecciones"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		document.forms['<c:out value="${mappingBuscarEliminacion.name}" />'].submit();
	}
	
</script>

<html:form action="/buscarEliminacion?method=buscar" >
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.eliminacion.buscar"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">		
		<table>
		<tr>	
			<td nowrap>
				<a class="etiquetaAzul12Bold"  href="javascript:buscar();">
					<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar"  styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.buscar"/>
				</a>
			</td>
			<td width="5px">&nbsp;</td>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:clean()">
					<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar" titleKey="archigest.archivo.limpiar" styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.limpiar"/>
				</a>
			</td>
			<td width="5px">&nbsp;</td>									
			<td nowrap="nowrap">
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
			</td>
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
		<table class="formulario">					
			<tr>
				<td class="tdTitulo" width="150px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
				<td class="tdDatos">
				<c:set var="listaFondos" value="${sessionScope[appConstants.valoracion.LISTA_FONDOS_KEY]}" />
				<select name="fondo">
					<option value=""> -- <bean:message key="archigest.archivo.cf.fondosDocumentales"/>-- </option>
					<c:forEach var="fondo" items="${listaFondos}">
					<option value="<c:out value="${fondo.id}" />" <c:if test="${fondo.id == param.fondo}">selected</c:if>> <c:out value="${fondo.label}" /> </option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.eliminacion.buscar.codigo"/>:&nbsp;</td>
				<td class="tdDatos"><input type="text" size="30" name="codigo" value="<c:out value="${param.codigo}" />"></td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.eliminacion.buscar.tituloSerie"/>:&nbsp;</td>
				<td class="tdDatos"><input type="text" class="input80" name="tituloSerie" value="<c:out value="${param.tituloSerie}" />"></td>
			</tr>		
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.valoracion.buscar.tituloValoracion"/>:&nbsp;</td>
				<td class="tdDatos"><input type="text" size="24" name="tituloValoracion" value="<c:out value="${param.tituloValoracion}" />"></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.eliminacion.buscar.tituloEliminacion"/>:</td>
				<td class="tdDatos"><input type="text" size="24" name="titulo" value="<c:out value="${param.titulo}" />"></td>
			</tr>
			<%-- Filtro por estado--%>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.eliminacion.buscar.estado"/>:</td>
						<td class="tdDatosFicha">
							<table class="formulario">
									<tr>
										<td align="right">
											<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].estados);" 
								 			><html:img page="/pages/images/checked.gif" 
												    border="0" 
												    altKey="archigest.archivo.selTodos" 
												    titleKey="archigest.archivo.selTodos" 
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
											&nbsp;
											<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].estados);" 
								 			><html:img page="/pages/images/check.gif" 
												    border="0" 
												    altKey="archigest.archivo.quitarSel" 
												    titleKey="archigest.archivo.quitarSel"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
											&nbsp;&nbsp;
									   </td>
									</tr>
							</table>
							<table cellpadding="0" cellspacing="0"><tr><td>
							<logic:iterate id="estado" name="estados" indexId="rowNum">
								<% if ((rowNum.intValue() % 3) == 0) out.println("<tr>"); %>
									<td>
										<html:multibox style="border:0" property="estados">
											<bean:write name="estado" property="value"/>
										</html:multibox>
									</td>
									<td class="tdDatos">
										<bean:write name="estado" property="label"/>
									</td>
									<td width="10">&nbsp;</td>
								<% if ((rowNum.intValue() % 3) == 2) out.println("</tr>"); %>
								</logic:iterate>
							</td></tr></table>
						</td>
					</tr>	
		</table>
		</div>

	</tiles:put>
</tiles:insert>
</html:form>