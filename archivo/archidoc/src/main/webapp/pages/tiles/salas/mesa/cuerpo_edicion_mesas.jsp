<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionMesasAction" />

<c:set var="sala" value="${sessionScope[appConstants.salas.SALA_KEY]}" />

<script language="javascript">
function save() {
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.submit();
}
</script>

<html:form action="/gestionMesasAction">
	<input type="hidden" name="method" value="guardar" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.crear.mesas"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:save()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td noWrap>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</td>
		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.ver.sala"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<tiles:insert name="salas.datos.sala" flush="true"/>
			</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.selectNumMesas"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td width="150px" class="tdTitulo">
							<bean:message key="archigest.archivo.salas.numero.mesas"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property="numeroMesas" size="5" maxlength="5" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>