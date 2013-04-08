<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" type="text/JavaScript">

	function mostrarBuscadorProcedimiento(form) {
		var buscadorProcedimiento = xGetElementById('seleccionProcedimiento');
		if (buscadorProcedimiento.style.display == 'none')
			xDisplay(buscadorProcedimiento, 'block');
		else {
			xDisplay('listaProcedimientos', 'none');
			xDisplay(buscadorProcedimiento, 'none');
		}
	}
</script>

<tr>

	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>" ><bean:message key="archigest.archivo.transferencias.procedimiento"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<div>
			<html:text property="procedimiento" styleId="procedimiento" styleClass="inputRO90" readonly="true"/>

			<c:set var="enlaceSeleccionProcedimiento" value="javascript:mostrarBuscadorProcedimiento('${actionMappingName}')" />
			<a href="<c:out value="${enlaceSeleccionProcedimiento}" escapeXml="false"/>"
				><html:img styleId="imgExpand"
					page="/pages/images/expand.gif"
					styleClass="imgTextMiddle" /></a>

			<a href="javascript:cleanProcedimiento(document.forms['<c:out value="${actionMappingName}" />']);"
				><html:img styleId="imgClear"
						   page="/pages/images/clear0.gif"
						   styleClass="imgTextMiddle"/></a>

			<%-- Div para mostrar los procedimientos --%>
			<c:set var="listaProcedimientos" value="${requestScope[appConstants.fondos.LISTA_PROCEDIMIENTOS_KEY]}" />

			<div id="seleccionProcedimiento" class="bloque_busquedas_rapidas" <c:if test="${listaProcedimientos == null}">style="display:none"</c:if>>
				<script type="text/javascript">
					function buscarProcedimientos() {
						var form = document.forms['<c:out value="${actionMappingName}" />'];
						form.method.value = '<c:out value="${methodBuscarProcedimiento}" />';
						form.submit();
					}
				</script>

				<div id="buscadorProcedimiento">
					<div class="cabecero_bloque_tab">
						<TABLE width="100%" cellpadding=0 cellspacing=0>
						  <TR>
							<TD width="100%" align="right">
							<TABLE cellpadding=0 cellspacing=0>
							  <TR>
									<TD>
									<a class="etiquetaAzul12Normal" href="javascript:buscarProcedimientos()">
										<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
										<bean:message key="archigest.archivo.buscar"/>
									</a>
									</TD>
									</TD><td width="10px">&nbsp;</td>
							 </TR>
							</TABLE>
							</TD>
						</TR></TABLE>
					</div>

					<table class="formulario" width="99%">
						<tr>
							<td class="tdTitulo" width="170px"><bean:message key="archigest.archivo.cf.tipoProcedimiento"/>:&nbsp;</td>
							<td class="tdDatos">
								<table cellpadding="0" cellspacing="0"><tr>
									<td><input class="radio" type="radio" name="tipoProcedimiento" value="1" <c:if test="${empty param.tipoProcedimiento || param.tipoProcedimiento == '1'}">checked</c:if>></td>
									<td class="etiquetaNegra11Normal">&nbsp;&nbsp;<bean:message key="archigest.archivo.todos"/></td>
									<td><input type="radio" class="radio" name="tipoProcedimiento" value="2" <c:if test="${param.tipoProcedimiento == '2'}">checked</c:if>></td>
									<td class="etiquetaNegra11Normal"><bean:message key="archigest.archivo.cf.procedimientoAutomatizado"/></td>
									<td><input class="radio" type="radio" name="tipoProcedimiento" value="3" <c:if test="${param.tipoProcedimiento == '3'}">checked</c:if>></td>
									<td class="etiquetaNegra11Normal">&nbsp;&nbsp;<bean:message key="archigest.archivo.cf.procedimientoNoAutomatizado"/></td>
								</tr></table>
							</td>
						</tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.transferencias.titulo"/>:&nbsp;</td>
							<td class="tdDatos"><input type="text" size="40" name="tituloProcedimiento" value="<c:out value="${param.tituloProcedimiento}" />"></td>
						</tr>
					</table>
				</div>

				<c:if test="${listaProcedimientos != null}">
					<script>
						function selectProcedimiento(item) {
							var form = document.forms['<c:out value="${actionMappingName}" />'];
							form.procedimiento.value = item.id;
							hideListaProcedimientos();
						}
					</script>

					<div id="listaProcedimientos" style="width:99.9%;height:100px;overflow:auto;background-color:#efefef;">
						<c:choose>
							<c:when test="${!empty listaProcedimientos}">
								<c:forEach var="procedimiento" items="${listaProcedimientos}">
									<div class="etiquetaGris12Normal" id='<c:out value="${procedimiento.codigo}" />'
											procedimiento='<c:out value="${procedimiento.nombre}" />'
											codigoSistemaProductor='<c:out value="${procedimiento.codSistProductor}" />'
											sistemaProductor='<c:out value="${procedimiento.nombreSistProductor}" />'
											onmouseOver="this.style.backgroundColor='#dedede'"
											onmouseOut="this.style.backgroundColor='#efefef'"
											onclick="selectProcedimiento(this)" style='padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
										<c:out value="${procedimiento.codigo}" />&nbsp;&nbsp;
										<c:out value="${procedimiento.nombre}" /></div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.prev.noProcedimientos"/>
							</c:otherwise>
						</c:choose>
					</div>
					<table cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right" bgcolor="#B0B0C6"  style="border-top:1px solid #000000">
								<a class=etiquetaAzul12Normal href="javascript:hideListaProcedimientos();">
									<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;
								</a>
							</td>
						</tr>
					</table>

					<script>
						function hideListaProcedimientos() {
							xDisplay('seleccionProcedimiento', 'none')
							xDisplay('listaProcedimientos', 'none');
						}
					</script>
				</c:if>
			</div>
		</div>
	</td>
</tr>