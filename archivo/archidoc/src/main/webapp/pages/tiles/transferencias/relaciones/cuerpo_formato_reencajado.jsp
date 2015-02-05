<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/gestionRelaciones" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.seleccion.formato.reencajado"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
			   <td nowrap>
					<script>
						function save(){
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							if(form.idformatoseleccionado.value == ""){
								alert('<bean:message key="archigest.archivo.transferencias.noSelFormato"/>');
							}else{
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
								}
								form.submit();
							}
						}
					</script>
					<a class="etiquetaAzul12Bold"
						href="javascript:save()">
						<html:img page="/pages/images/Ok_Si.gif"
						titleKey="archigest.archivo.aceptar"
						altKey="archigest.archivo.aceptar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
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
			<html:form action="/gestionRelaciones">
				<input type="hidden" name="method" value="seleccionFormatoReencajado"/>
				<html:hidden property="idrelacionseleccionada"/>

				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:set var="listaFormatos" value="${sessionScope[appConstants.transferencias.LISTA_FORMATOS_KEY]}"/>
							<html:select property='idformatoseleccionado' size="1">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="listaFormatos" labelProperty="nombre" property="id"/>
							</html:select>
						</td>
					</tr>
				</table>
			</html:form>
		</div>
	</tiles:put>
</tiles:insert>