<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="estadoConErrores"><c:out value="${appConstants.transferencias.estadoREntrega.CON_ERRORES_COTEJO.identificador}"/></c:set>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="codigoRelacion"><c:out value="${vRelacion.codigoTransferencia}" /></c:set>
<script>

	var isWorking = false;
	function workingDiv(){
		if(isWorking == false){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				isWorking = true;
				window.top.showWorkingDiv(title, message);
			}
		}
	}

	function enviarRelacion() {
		if(isWorking == false){
			var codigoRelacion = '<c:out value="${codigoRelacion}" />';
			if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.enviada1'/>"+codigoRelacion+"<bean:message key='archigest.archivo.transferencias.previsiones.enviada2'/>")) {
				<c:url var="envioRelacionURL" value="/action/recepcionRelaciones">
					<c:param name="method" value ="enviarrelacion" />
					<c:param name="codigo" value ="${vRelacion.id}" />
				</c:url>
				workingDiv();
				window.location = '<c:out value="${envioRelacionURL}" escapeXml="false" />';
			}
		}
	}

	function enviarIngresoSeleccionUbicacion() {
		<c:url var="envioIngresoURL" value="/action/gestionRelaciones">
			<c:param name="method" value ="enviarIngresoSeleccionUbicacion" />
			<c:param name="codigo" value ="${vRelacion.id}" />
		</c:url>
		if(isWorking == false){
			workingDiv();
			window.location = '<c:out value="${envioIngresoURL}" escapeXml="false" />';
		}
	}

	function eliminarRelacion() {
		var codigoRelacion = '<c:out value="${codigoRelacion}" />';
		if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.eliminarRelacionCodigo'/> "+codigoRelacion+"<bean:message key='archigest.archivo.deseaContinuar'/>")) {
			<c:url var="eliminacionRelacionURL" value="/action/gestionRelaciones">
				<c:param name="method" value ="eliminarrelacion" />
				<c:param name="idrelacionseleccionada" value ="${vRelacion.id}" />
			</c:url>
			window.location = '<c:out value="${eliminacionRelacionURL}" escapeXml="false" />';
		}
	}

	function eliminarRegistro(codigo, direccion) {
		if (confirm("<bean:message key='archigest.archivo.confirmacion.borrado.expediente'/> "+codigo+"<bean:message key='archigest.archivo.deseaContinuar'/>")) {
			window.location=direccion;
		}
	}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${!vRelacion.isIngresoDirecto}">
				<bean:message key="archigest.archivo.transferencias.datosRelacion"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.fondos.datos.ingreso.directo"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<%--Insertamos la tile de la barra de botones que se ha extraido fuera --%>
		<bean:define id="vRelacion" name="vRelacion" toScope="request"/>
		<tiles:insert page="/pages/tiles/transferencias/relaciones/cuerpo_view_relacion_bar.jsp" flush="true"/>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.ingresoDirecto"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding="0" cellspacing="0">
				  <TR>
					<%--boton editar --%>
					<c:if test="${vRelacion.puedeSerModificada}">
						<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
						<c:url var="editarURL" value="/action/gestionRelaciones">
							<c:param name="method" value="edicion" />
						</c:url>
						<TD>
							<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarURL}" escapeXml="false"/>'">
								<html:img titleKey="archigest.archivo.editar" altKey="archigest.archivo.editar" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.editar"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
						</security:permissions>
					</c:if>
				 </TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>

	<c:set var="expedientesFueraPlazoConservación" value="${sessionScope[appConstants.transferencias.LISTA_EXPEDIENTES_A_ELIMINAR]}" />
	<c:if test="${!empty expedientesFueraPlazoConservación}">
		<div class="separador8">&nbsp;</div>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.expedientes.con.plazo.expirado"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<display:table name="pageScope.expedientesFueraPlazoConservación"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="expediente">
				<display:column titleKey="archigest.archivo.transferencias.nExp" sortable="true" headerClass="sortable" property="numExp" />

				<display:column titleKey="archigest.archivo.transferencias.asunto" sortable="true" headerClass="sortable" property="datosIdentificativos" />
			</display:table>

		</tiles:put>
		</tiles:insert>

	</c:if>
	<div class="separador8">&nbsp;</div>


<c:set var="infoAsignacion" value="${sessionScope[appConstants.transferencias.ASIGNACION_UDOC2UI]}" />

		<script>
			var tabPanel = new TabPanel("tabsUdocsRelacion");
			tabPanel.addTab(new Tab("tabCajasRelacion", "tabCajasRelacion"));
			tabPanel.addTab(new Tab("tabUdocsRelacion", "tabUdocsRelacion"));
		</script>

		<div class="cabecero_tabs">
			<table cellspacing="0" cellpadding=0>
				<tr>
			    	<td class="tabActual" id="ptabUdocsRelacion" onclick="javascript:tabPanel.showTab('tabUdocsRelacion');">
						<a href="javascript:tabPanel.showTab('tabUdocsRelacion');" id="ttabUdocsRelacion" class="textoPestana">
							<bean:message key="archigest.archivo.transferencias.contenido"/>
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
			    	<td class="tabSel" id="ptabCajasRelacion" onclick="javascript:tabPanel.showTab('tabCajasRelacion');">
						<a href="javascript:tabPanel.showTab('tabCajasRelacion');" id="ttabCajasRelacion" class="textoPestanaSel">
							<c:choose>
								<c:when test="${vRelacion.sinDocsFisicos}">
									<bean:message key="archigest.archivo.detalle"/>
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.transferencias.unidInstalacion"/>
								</c:otherwise>
							</c:choose>
						</a>
				    </td>
				</tr>
			</table>
		</div>

		<%--Tab de Unidades de Instalacion (Cajas) --%>
		<div id="tabCajasRelacion" class="bloque_tab" style="display:none">
			<bean:define id="vRelacion" name="vRelacion" toScope="request"/>
			<bean:define id="infoAsignacion" name="infoAsignacion" toScope="request"/>
			<tiles:insert page="/pages/tiles/transferencias/relaciones/cuerpo_view_relacion_cajas.jsp" flush="true"/>
		</div> <%--bloque tabCajasRelacion --%>

	<%--Tab de Unidades Documentales (Expedientes) --%>
	<div id="tabUdocsRelacion" class="bloque_tab" style="display:block">
		<script>
			var SEPARATOR = "@@";
			function marcarBloqueo(selectionFormName, elemento, marcaBloqueo){
				var selectionForm = document.forms[selectionFormName];
				if(selectionForm) {
					if(elemento.checked){
						if(marcaBloqueo > 0){
							if(selectionForm.udocBloqueadas.value == ''){
								selectionForm.udocBloqueadas.value = elemento.value;
							}else{
								selectionForm.udocBloqueadas.value = selectionForm.udocBloqueadas.value + SEPARATOR + elemento.value;
							}
						}
					}else{
						if(marcaBloqueo > 0){
							var bloqueadas = selectionForm.udocBloqueadas.value.split(SEPARATOR);
							if(bloqueadas.length > 0){
								for(var i=0; i<bloqueadas.length;i++){
									if(bloqueadas[i] == elemento.value){
										bloqueadas[i] = '';
									}
								}
								selectionForm.udocBloqueadas.value = '';
								for(var i=0; i<bloqueadas.length;i++){
									if(bloqueadas[i] != '')
										selectionForm.udocBloqueadas.value = selectionForm.udocBloqueadas.value + bloqueadas[i] + SEPARATOR;
								}
							}
						}
					}
				}
			}

			function selectedAllElements(selectionFormName, operacion){
				var selectionForm = document.forms[selectionFormName];
				if (selectionForm) {
					if(operacion == 'selectAll'){
						selectionForm.udocBloqueadas.value = selectionForm.allUdocsBloqueadas.value;
					}else if(operacion == 'unselectAll'){
						selectionForm.udocBloqueadas.value = '';
					}
				}
			}

			function lockSelectedItem(selectionFormName) {
				var selectionForm = document.forms[selectionFormName];
				if (selectionForm && selectionForm.selectedUdoc) {
					var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedUdoc");
					if(nSelected >= 1){
						if(selectionForm.udocBloqueadas.value != ''){
							var bloqueadas = selectionForm.udocBloqueadas.value.split(SEPARATOR);
							if(bloqueadas.length > 0)
								alert("<bean:message key='errors.relaciones.noPermitirBloquearUdocBloqueadas'/>");
						}else{
							selectionForm.method.value='lockUdocs';
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}
							selectionForm.submit();
						}
					} else {
						alert("<bean:message key='archigest.archivo.transferencias.selUDocLock'/>");
					}
				}else
					alert("<bean:message key='archigest.archivo.transferencias.selUDocLock'/>");
			}

			function unlockSelectedItem(selectionFormName) {
				var selectionForm = document.forms[selectionFormName];
				if (selectionForm && selectionForm.selectedUdoc) {
					var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedUdoc");
					if(nSelected >= 1){
						if((selectionForm.udocBloqueadas.value == '') ||
								(selectionForm.udocBloqueadas.value != '' && selectionForm.udocBloqueadas.value.split(SEPARATOR).length != nSelected)){
							alert("<bean:message key='errors.relaciones.noPermitirDesbloquearUdocsDesbloqueadas'/>");
						}else{
							selectionForm.method.value='unlockUdocs';
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}
							selectionForm.submit();
						}
					} else {
						alert("<bean:message key='archigest.archivo.transferencias.selUDocUnlock'/>");
					}
				}else
					alert("<bean:message key='archigest.archivo.transferencias.selUDocUnlock'/>");
			}

			function addUdocSelectedItem(selectionFormName) {
				var selectionForm = document.forms[selectionFormName];
				if (selectionForm && selectionForm.selectedUdoc) {
					var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedUdoc");
					if(nSelected > 1)
						alert("<bean:message key='archigest.archivo.transferencias.selUDocAnadir'/>");
					else {
						selectionForm.method.value='addUdocs';
						if (window.top.showWorkingDiv) {
							var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
							var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
							window.top.showWorkingDiv(title, message);
						}
						selectionForm.submit();
					}
				} else {
					selectionForm.method.value='addUdocs';
					if (window.top.showWorkingDiv) {
						var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
						var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
						window.top.showWorkingDiv(title, message);
					}
					selectionForm.submit();
				}
			}

			function eliminarSelectedItems(selectionFormName) {
				var selectionForm = document.forms[selectionFormName];
				if (selectionForm && selectionForm.selectedUdoc) {
					var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedUdoc");
					if(nSelected >= 1) {
						if (confirm("<bean:message key='archigest.archivo.transferencias.delUDocSel'/>")) {
							if (selectionForm){
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
								}
								selectionForm.submit();
							}
						}
					} else
						alert("<bean:message key='archigest.archivo.transferencias.selUDocEliminar'/>");
				}else
					alert("<bean:message key='archigest.archivo.transferencias.selUDocEliminar'/>");
			}
		</script>

		<%--Insertamos la tile de formulario que se ha extraido fuera --%>
		<tiles:insert page="/pages/tiles/transferencias/relaciones/cuerpo_view_relacion_formulario.jsp" flush="true"/>
</div> <%--bloque --%>
</tiles:put>
</tiles:insert>
<script>removeCookie('tabsInfoUdoc');</script>