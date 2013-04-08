<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/buscarElementos" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function exportar()
	{
		if(document.getElementById('nombreObjetoAmbito').value=='')
			alert('<bean:message key="errors.seleccion.fondos.exportar.excel"/>');
		else
			document.forms['<c:out value="${actionMapping.name}" />'].submit();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.fondosDocumentales.exportar.cuadro"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:exportar();">
					<html:img page="/pages/images/displaytag/ico_file_excel.png"
					        altKey="archigest.archivo.exportar"
					        titleKey="archigest.archivo.exportar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.exportar"/></a>
				</td>
				<td width="10">&nbsp;</td>
			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:cleanForm(document.forms['<c:out value="${actionMappingName}" />'])">
					<html:img page="/pages/images/clear0.gif"
					        altKey="archigest.archivo.limpiar"
					        titleKey="archigest.archivo.limpiar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/buscarElementos">
				<input type="hidden" name="method" value="exportarAExcel"/>

				<script language="javascript">
				function getNivelAmbitoPermtido(){
					return 0;
				}

				function getMsgAmbitoInvalido(){
					var msgAmbitoInvalido = "<bean:message key="archigest.archivo.cf.busqueda.ambito.invalido.nivel.inferior"/>";
					return msgAmbitoInvalido;
				}
				</script>
				<table class="formulario">

					<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
					<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
					<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
					<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
					<bean:define id="sizeCampo" value="200" toScope="request"/>
					<tiles:insert page="../fondos/busquedas/campo_busqueda_condiciones_ambito.jsp" flush="true"/>
				</table>
			</html:form>
		</div>
	</tiles:put>
</tiles:insert>