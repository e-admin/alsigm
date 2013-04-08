<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="mappingGestionTipoAsignable" mapping="/gestionTipoAsignableAction" />

<c:set var="formName" value="${mappingGestionTipoAsignable.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="elementoAsignable" value="${requestScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}" />

<SCRIPT>

function seleccionarFormatoRegular() {
	var form = document.forms['<c:out value="${mappingGestionTipoAsignable.name}" />'];
	xGetElementById('formatosIrregulares').disabled = true;
	form.numeroHuecos.disabled = true;
	form.numeroHuecos.className = 'inputRO';
	xGetElementById('formatosRegulares').disabled = false;
}

function seleccionarFormatoIrregular() {
	var form = document.forms['<c:out value="${mappingGestionTipoAsignable.name}" />'];
	xGetElementById('formatosRegulares').disabled = true;
	xGetElementById('formatosIrregulares').disabled = false;
	form.numeroHuecos.className = 'input';
	form.numeroHuecos.disabled = false;
}

</SCRIPT>

<html:form action="/gestionTipoAsignableAction">
		<input type="hidden" name="method" value="guardarAsignables" />
		<html:hidden property="id" />
		<html:hidden property="idPadre" />
		<html:hidden property="tipoElemento" />
		<html:hidden property="numACrear" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${!empty AsignableForm.id}">
				<bean:message key="archigest.archivo.editar"/>
				<bean:message key="archigest.archivo.deposito.elementoDeposito"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.crear"/>
				<bean:message key="archigest.archivo.deposito.descendientes"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<SCRIPT>
					function save() {
						var form = document.forms['<c:out value="${mappingGestionTipoAsignable.name}" />'];
						form.submit();
					}
				</SCRIPT>
				<a class="etiquetaAzul12Bold" href="javascript:save()" >
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

		<c:if test="${empty AsignableForm.id}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.creacionElemAsignable"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<c:set var="elementoPadre" value="${sessionScope[appConstants.deposito.ELEMENTO_DEPOSITO_KEY]}" />
			<table class="formulario">
				<tr>
					<td width="220px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.elementoPadre"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${elementoPadre.pathName}" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.tipoElemCrear"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${AsignableForm.nombreTipoElemento}" />
					</td>
				</tr>
			</table>
			</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		</c:if>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.datosElemAsignable"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<c:if test="${!empty AsignableForm.id}">
				<tr>
					<td width="100px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<c:out value="${AsignableForm.pathName}"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="100px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text property='longitud' size="5" maxlength="5"/>
						<bean:message key="archigest.archivo.cm"/>.
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" colspan="2">
						<bean:message key="archigest.archivo.deposito.formato"/>:&nbsp;
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						&nbsp;
					</td>
					<td class="tdDatos">
						<TABLE cellpadding="0" cellspacing="0">
							<c:set var="FormBean" value="${requestScope[mappingGestionTipoAsignable.name]}" />
							<c:set var="formatoRegularSeleccionado" value="${FormBean.formatoRegular}" />
							<TR>
								<TD width="150px" class="td2Datos">
									<input type="radio" class="radio" name="formatoRegular" value="true" onclick="seleccionarFormatoRegular()" <c:if test="${formatoRegularSeleccionado}">checked</c:if>>
									<bean:message key="archigest.archivo.deposito.formato"/>
									<bean:message key="archigest.archivo.deposito.regular"/>
								</TD>
								<TD class="td2Datos">
									<c:set var="listaFormatosRegulares" value="${sessionScope[appConstants.deposito.LISTA_FORMATOS_REGULARES_KEY]}" />
									<html:select property="idFormato" size="1" styleClass="input" styleId="formatosRegulares">
										<html:optionsCollection name="listaFormatosRegulares" label="nombre" value="id"/>
									</html:select>
								</TD>
							</TR>
							<TR>
								<TD class="td2Datos">
									<input type="radio" class="radio" name="formatoRegular" value="false" onclick="seleccionarFormatoIrregular()" <c:if test="${! formatoRegularSeleccionado}">checked</c:if>>
									<bean:message key="archigest.archivo.deposito.formato"/>
									<bean:message key="archigest.archivo.deposito.irregular"/>
								</TD>
								<TD class="td2Datos">
									<c:set var="listaFormatosIRegulares" value="${sessionScope[appConstants.deposito.LISTA_FORMATOS_IRREGULARES_KEY]}" />
									<html:select property="idFormato" size="1" styleClass="input" styleId="formatosIrregulares">
										<html:optionsCollection name="listaFormatosIRegulares" label="nombre" value="id"/>
									</html:select>
								</TD>
								<td>&nbsp;</td>
								<TD class="td2Datos">
									<bean:message key="archigest.archivo.num"/>
									<bean:message key="archigest.archivo.deposito.huecos"/>&nbsp;
									<input type="text" name="numeroHuecos" value="<c:out value="${form.numeroHuecos}" />" <c:choose><c:when test="${formatoRegularSeleccionado}">	disabled="true" class="inputRO"</c:when><c:otherwise>class="input"</c:otherwise></c:choose> size="5" maxlength="5" />
								</TD>
							</TR>
						</TABLE>
						<script>
							<c:choose>
								<c:when test="${formatoRegularSeleccionado}">
									seleccionarFormatoRegular();
								</c:when>
								<c:otherwise>
									seleccionarFormatoIrregular();
								</c:otherwise>
							</c:choose>
						</script>
					</td>
				</tr>
			</table>
			</tiles:put>
		</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>