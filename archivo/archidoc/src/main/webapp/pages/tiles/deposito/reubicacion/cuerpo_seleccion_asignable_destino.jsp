<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_ORIGEN_COMPACTAR_KEY]}"/>

<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_DESTINO_KEY]}" />
<c:if test="${empty elementoAsignable}">
	<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}" />
</c:if>

<bean:struts id="mappingReubicacionAction" mapping="/reubicacionUdocsAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.reubicar" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
				</tiles:insert>
		   	</td>
			<td width="10">&nbsp;</td>

			<TD>
				<script>
					function goOn(){
						var form = document.forms['<c:out value="${mappingReubicacionAction.name}" />'];
						var frameSeleccion = window.frames['navegador'];

						var seleccionado = frameSeleccion.document.forms[0].sel;
						var elementoSeleccionado = null;


						if(seleccionado){
							if( seleccionado.length == null || seleccionado.length == "undefined"){
								var elementoSeleccionado = frameSeleccion.document.forms[0].sel.value;
							}
							else{
						    	var i;
	    						for (i=0;i<frameSeleccion.document.forms[0].sel.length;i++){
	       							if (frameSeleccion.document.forms[0].sel[i].checked)
	          						break;
	    						}

	    						var elemento = frameSeleccion.document.forms[0].sel[i];

	    						if(elemento){
	    							elementoSeleccionado = elemento.value;
	    						}
							}
						}



						if(elementoSeleccionado != null && elementoSeleccionado!= ""){
							form.elementoDestino.value = elementoSeleccionado;


							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							form.submit()
						}
						else{
							alert("<bean:message key="archigest.archivo.documentos.digitalizacion.selElemento"/>");
						}
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
					<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</TD>
			<td width="10">&nbsp;</td>
			<TD>
				<c:url var="cancelURL" value="/action/reubicacionUdocsAction">
					<c:param name="method" value="goReturnPoint" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
				<html:img page="/pages/images/close.gif"
					altKey="archigest.archivo.cerrar"
					titleKey="archigest.archivo.cerrar"
					styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
		   	</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
			<fmt:message key="archigest.archivo.deposito.unidadInstalacionOrigen"/>:&nbsp;
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<TABLE class="formulario" cellpadding=0 cellspacing=0>
					<TR>
						<TD class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.ruta"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.path}"/>
						</TD>
					</TR>

					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.signatura"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.unidInstalacion.signaturaui}"/>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.nombreformato}"/>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<html:form action="/reubicacionUdocsAction">
	<input type="hidden" name="method" value="seleccionarHuecoDestino"/>

	<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			<TR>
		<html:hidden property="elementoDestino" />
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
				<c:url var="urlNavegador" value="/action/navegadorDepositoTableAction">
					<c:param name="method" value="initial"/>
					<c:param name="seleccionadoinicial" value="${elementoAsignable.id}:${elementoAsignable.idTipoElemento}"/>
					<c:param name="filterByIdformato" value="${elementoAsignable.idFormato}"/>
					<c:param name="allowAllFormats" value="true"/>
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
