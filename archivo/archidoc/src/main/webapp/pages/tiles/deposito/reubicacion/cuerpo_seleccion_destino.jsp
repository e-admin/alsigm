<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}" />

<bean:struts id="mappingReubicacionAction" mapping="/reubicacionAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.reubicar" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<script>
					var isWorking = false;
					function reubicarUnidadesInstalacion() {


						if(!isWorking){
							var form = document.forms['<c:out value="${mappingReubicacionAction.name}" />'];
							var elementoSeleccionado = window.frames['navegador'].getValorSeleccionado();

							if(elementoSeleccionado != null && elementoSeleccionado != ""){
								form.elementoDestino.value = elementoSeleccionado;

								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
									isWorking= true;
								}


								form.submit();
							}
							else{
								alert("<bean:message key="archigest.archivo.deposito.situacionActualNoSeleccionada"/>");
							}
						}
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:reubicarUnidadesInstalacion()" >
					<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.siguiente"/>
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
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.datosBalda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td width="100px" class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${elementoAsignable.pathName}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.longitud}" /> <bean:message key="archigest.archivo.cm"/>.
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.formato.nombre}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.huecos"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.numhuecos}" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<html:form action="/reubicacionAction">
	<input type="hidden" name="method" value="moverContenidoHuecos">
	<html:hidden property="elementoDestino" />
	<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			<TR>
			<TD class="etiquetaAzul12Bold" width="40%" height="100%">
				<bean:message key="archigest.archivo.deposito.elementoAMover"/>
			</TD>
			</TR>
		</TABLE>
	</div> <%-- cabecero bloque --%>

	<div class="bloque">
		<TABLE class="w100">
		<TR>
			<td>
				<input type="hidden" name="asignabledestino" value="">
				<script>
					function resizeNavegador() {
						var frameNavegador = document.getElementById("navegador");
						if (frameNavegador.contentDocument && frameNavegador.contentDocument.body.offsetHeight) //ns6 syntax
							frameNavegador.height = frameNavegador.contentDocument.body.offsetHeight;
						else if (frameNavegador.Document && frameNavegador.Document.body.scrollHeight) //ie5+ syntax
							frameNavegador.height = frameNavegador.Document.body.scrollHeight;
					}
					if (window.top.showWorkingDiv) {
						var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
						var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
						window.top.showWorkingDiv(title, message);
					}
				</script>
				<c:set var="elementoPadre" value="${requestScope[appConstants.deposito.ELEMENTO_PADRE]}" />

				<c:url var="urlNavegador" value="/action/navegadorDepositoTableAction">
					<c:param name="method" value="initial"/>
					<c:param name="seleccionadoinicial" value="${elementoAsignable.id}:${elementoAsignable.idTipoElemento}"/>
					<c:param name="filterByIdformato" value="${elementoAsignable.idFormato}"/>
					<c:param name="numHuecosNecesarios" value="${sessionScope[appConstants.deposito.NUM_HUECOS_A_BUSCAR]}"/>
					<c:param name="checkHasHuecos" value="true"/>
				</c:url>

				<iframe id="navegador" name="navegador" src='<c:out value="${urlNavegador}" escapeXml="false"/>' frameborder="0" height="100%" width="100%"></iframe>

				<script>
					var frameNavegador = document.getElementById("navegador");
					if (frameNavegador.addEventListener)
						frameNavegador.addEventListener("load", resizeNavegador, false);
					else if (frameNavegador.attachEvent) {
						frameNavegador.detachEvent("onload", resizeNavegador); // Bug fix line
						frameNavegador.attachEvent("onload", resizeNavegador);
					}
				</script>
			</td>
		</TR>
		</TABLE>
	</div>

	</html:form>

</tiles:put>
</tiles:insert>