<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>


<bean:struts id="mappingGestionTipoNoAsignable" mapping="/gestionTipoNoAsignableAction" />

<html:form action="/gestionTipoNoAsignableAction" >
	<input type="hidden" name="method" value="crearDescendientes" />
	<html:hidden property="idPadre" />
	<html:hidden property="idUbicacion" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.crear"/> <bean:message key="archigest.archivo.deposito.descendientes"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<script>
					function crearNoAsignable() {
						var form = document.forms['<c:out value="${mappingGestionTipoNoAsignable.name}" />'];
						form.submit();
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:crearNoAsignable()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
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
				<bean:message key="archigest.archivo.deposito.creacionDescendientesElemento"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<c:set var="elementoPadre" value="${sessionScope[appConstants.deposito.ELEMENTO_DEPOSITO_KEY]}" />
			<table class="formulario">
				<tr>
					<td width="210px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<c:out value="${elementoPadre.pathName}" />
					</td>
				</tr>
			</table>
			</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.elemCrear"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
					<td width="220px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.tipoElemCrear"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:set var="tiposElemento" value="${sessionScope[appConstants.deposito.LISTA_TIPO_ELEMENTO_KEY]}" />
						<html:select property="tipoElemento" size="1">
							<html:optionsCollection name="tiposElemento" label="nombre" value="id"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.numElemCrear"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text property="numACrear" size="5" maxlength="4"/>
					</td>
				</tr>
				</TR>
			</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>