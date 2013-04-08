<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.edicionUbicacion"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<bean:struts id="mapping" mapping="/gestionTipoNoAsignableAction" />
		<script>
			function guardarTipoNoAsignable() {
				var form = document.forms['<c:out value="${mapping.name}" />'];
				form.submit();
			}
		</script>
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:guardarTipoNoAsignable()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.datosElemNoAsignable"/>:&nbsp;
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<html:form action="/gestionTipoNoAsignableAction">
					<html:hidden property="id" />
					<html:hidden property="pathPadre"/>
					<html:hidden property="idPadre"/>
					<html:hidden property="idUbicacion"/>
					<input type="hidden" name="method" value="guardarNoAsignable">
					<table class="formulario">
						<tr>
							<td width="150px" class="tdTitulo">
								<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<c:out value="${NoAsignableForm.pathPadre}"/>
								<html:text property='nombre' styleClass="input60" maxlength="64" />
							</td>
						</tr>
					</table>
				</html:form>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>