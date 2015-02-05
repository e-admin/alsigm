<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<bean:struts id="actionMapping" mapping="/elementos" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function buscarElementos(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		document.forms['<c:out value="${actionMappingName}" />'].submit();
	}
</script>

<div id="contenedor_ficha">

	<div class="ficha">
		<html:form action="/buscarElementos">
		<input type="hidden" name="method" value="buscar"/>
		<html:hidden property="ref" styleId="ref"/>
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.busqueda.form.caption"/></td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:buscarElementos();"
										><input type="image"
												src="<%=request.getContextPath()%>/pages/images/buscar.gif"
										        border="0"
										        alt="<bean:message key="archigest.archivo.buscar"/>"
										        title="<bean:message key="archigest.archivo.buscar"/>"
										        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:cleanForm(document.forms['<c:out value="${actionMappingName}" />'])"
										><html:img page="/pages/images/clear0.gif"
										        border="0"
										        altKey="archigest.archivo.limpiar"
										        titleKey="archigest.archivo.limpiar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:window.close()"
										><html:img page="/pages/images/close.gif"
												border="0"
												altKey="archigest.archivo.cerrar"
												titleKey="archigest.archivo.cerrar"
												styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors /></div>

			<div class="bloque">
				<table class="formulario">
					<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
					<bean:define id="classTdTituloCampoSinBorde" value="tdTitulo" toScope="request"/>
					<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
					<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
					<bean:define id="sizeCampo" value="150" toScope="request"/>
					<bean:define id="busquedaForm" name="busquedaElementosForm" toScope="request"/>
					<bean:define id="methodBuscarProcedimiento" value="buscarProcedimientoElementos" toScope="request"/>
					<bean:define id="methodLoadFicha" value="formBusquedaElem" toScope="request"/>
					<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>
					<c:if test="${listaFondos != null}">
						<bean:define id="listaFondos" name="listaFondos" toScope="request"/>
					</c:if>

					<c:forEach var="elementos" items="${beanBusqueda.listaCamposEntrada}">
						<bean:define id="elemento" name="elementos" toScope="request"/>
						<tiles:insert name="campos.busqueda" flush="true"/>
					</c:forEach>
				</table>
			</div>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;<br/></span></h2>
		</html:form>
	</div> <%--ficha --%>

</div> <%--contenedor_ficha --%>
