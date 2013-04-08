<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<bean:struts id="actionMapping" mapping="/gestionUInstRelacion" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="numMaxRegistrosScroll" value="20"/>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<script type="text/javascript">
	function aceptar(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		document.getElementById("formulario").submit();
	}
</script>

<c:set var="listaUInst" value="${sessionScope[appConstants.transferencias.LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS]}"/>
<c:set var="cajasRelacion" value="${sessionScope[appConstants.transferencias.LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS]}"/>
<c:set var="listaUDocsElectronicas" value="${sessionScope[appConstants.transferencias.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY]}"/>

<html:form action="/gestionUInstRelacion" styleId="formulario">
<input type="hidden" name="method" id="method" value="aceptar"/>
<html:hidden property="idRelacionEntrega" styleId="idRelacionEntrega"/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${form.electronicas}">
			<bean:message key="NavigationTitle_TRANSFERENCIAS_ADD_UDOCS_ELECTRONICAS" />
			</c:when>
			<c:otherwise>
				<bean:message key="NavigationTitle_TRANSFERENCIAS_ADD_UINST_REEA" />
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0 >
		 <TR>
		   <TD width="10">&nbsp;</TD>

			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
					&nbsp;<bean:message key="archigest.archivo.aceptar" />
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
	       <TD>
				<c:url var="volverURL" value="/action/gestionRelaciones">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextTop" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		   </TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.relaciones.relacionEntrega"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
					<td>&nbsp;</td>
				 </TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="visibleContent" direct="true">
					<TABLE class="w98m1" cellpadding=0 cellspacing=2>
						<TR>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.codigoTransferencia}"/>
								</span>
							</TD>
							<TD width="25%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.nombreestado}"/>
								</span>
							</TD>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
								</span>
							</TD>
							<TD width="35%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
								<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
								<span class="etiquetaNegra11Normal">
									<c:out value="${gestorEnOrganoRemitente.nombreCompleto}"/>
								</span>
							</TD>
						</TR>
					</TABLE>
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>


		<c:if test="${empty cajasRelacion and empty listaUDocsElectronicas}">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockContent" direct="true">
					<c:choose>
						<c:when test="${form.electronicas}">
						<bean:message key="archigest.archivo.no.encontradas.unidades.electronicas.entre.archivos" />
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.no.encontradas.unidades.instalacion.entre.archivos"/>
						</c:otherwise>
					</c:choose>



				</tiles:put>
			</tiles:insert>


		</c:if>

	<c:if test="${not empty listaUDocsElectronicas}">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<div class="separador8">&nbsp;</div>
		<tiles:put name="blockTitle" direct="true">
				<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
				<bean:message key="archigest.archivo.transferencias.unidadesElectronicas" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
					<td align="right">
						<a class="etiquetaAzul12Bold"
							href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosElectronicosSel);"
			 			><html:img page="/pages/images/checked.gif"
							    altKey="archigest.archivo.selTodos"
							    titleKey="archigest.archivo.selTodos"
							    styleClass="imgTextBottom" />
					    &nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
						&nbsp;
						<a class="etiquetaAzul12Bold"
							href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosElectronicosSel);"
			 			><html:img page="/pages/images/check.gif"
							    altKey="archigest.archivo.quitarSel"
							    titleKey="archigest.archivo.quitarSel"
							    styleClass="imgTextBottom" />
					    &nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
						&nbsp;&nbsp;
				   </td>
				</tr>
			</table>

			<div id="divListaUdocsElectronicas">
					<display:table name="pageScope.listaUDocsElectronicas"
						style="width:99%;"
						id="listaRegistros"
						export="false">

						<display:column>
							<c:set var="numUdocsElectronicas" value="${listaRegistros_rowNum}"/>
							<input type="checkbox" name="elementosElectronicosSel" value='<c:out value="${listaRegistros_rowNum-1}"/>' >
						</display:column>
						<display:column titleKey="archigest.archivo.transferencias.expediente" property="codigo"/>
						<display:column titleKey="archigest.archivo.transferencias.asunto" property="titulo"/>
						<display:column titleKey="archigest.archivo.transferencias.fIni">
							<c:out value="${listaRegistros.fechaInicial}"></c:out>
						</display:column>
						<display:column titleKey="archigest.archivo.transferencias.fFin" property="fechaFinal"/>

					</display:table>
			</div>

			<c:if test="${numUdocsElectronicas>numMaxRegistrosScroll}">
				<SCRIPT>
					var capaConScroll=document.getElementById("divListaUdocsElectronicas");
					capaConScroll.className="bloqueConScroll250";
				</SCRIPT>
			</c:if>

			<span class="separador5">&nbsp;</span>
			</tiles:put>
		</tiles:insert>
	</c:if>

	<c:if test="${not empty cajasRelacion}">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<div class="separador8">&nbsp;</div>
		<tiles:put name="blockTitle" direct="true">
				<html:img page="/pages/images/caja_closed.gif" altKey="archigest.archivo.transferencias.UndInstal" titleKey="archigest.archivo.transferencias.UndInstal" styleClass="imgTextBottom" />
				<bean:message key="archigest.archivo.transferencias.UndInstal" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
					<td align="right">
						<a class="etiquetaAzul12Bold"
							href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosSeleccionados);"
			 			><html:img page="/pages/images/checked.gif"
							    altKey="archigest.archivo.selTodas"
							    titleKey="archigest.archivo.selTodas"
							    styleClass="imgTextBottom" />
					    &nbsp;<bean:message key="archigest.archivo.selTodas"/></a>
						&nbsp;
						<a class="etiquetaAzul12Bold"
							href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosSeleccionados);"
			 			><html:img page="/pages/images/check.gif"
							    altKey="archigest.archivo.quitarSel"
							    titleKey="archigest.archivo.quitarSel"
							    styleClass="imgTextBottom" />
					    &nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
						&nbsp;&nbsp;
				   </td>
				</tr>
			</table>
			<div id="divListaCajas">
				<table class="w98" cellpadding="0" cellspacing="0">



				<c:forEach items="${cajasRelacion}" var="unidadInstalacion" varStatus="nCajas" >
					<c:set var="numCajasRelacion" value="${nCajas.count}" />

					<c:set var="cajaPanelName" value="Caja${nCajas.count}" />
					<jsp:useBean id="cajaPanelName" type="java.lang.String" />

					<tr class="titulo_gris_bloque">
						<c:set var="panelControlImg" value="img${cajaPanelName}" />
						<jsp:useBean id="panelControlImg" type="java.lang.String" />
						<c:set var="panelVisibilityCommand" value="switchDivVisibility('${cajaPanelName}')" />
						<jsp:useBean id="panelVisibilityCommand" type="java.lang.String" />

							<td width="2%" nowrap="nowrap" class="etiquetaAzul11Normal">
								<c:choose>
									<c:when test="${unidadInstalacion.unidadesvalidas == unidadInstalacion.unidadestotales}">
										<input type="checkbox" name="elementosSeleccionados" value='<c:out value="${nCajas.count-1}"/>' >
									</c:when>
									<c:otherwise>
										&nbsp;<html:img page="/pages/images/wrong.gif" border="0"  styleClass="imgTextTop" /><html:img page="/pages/images/pixel.gif" border="0"  styleClass="imgTextMiddle" />
									</c:otherwise>
								</c:choose>
								&nbsp;<html:img page="/pages/images/down.gif" styleId="<%=panelControlImg%>" onclick="<%=panelVisibilityCommand%>" styleClass="imgTextMiddle"/>

							</td>
							<td width="35%" class="etiquetaAzul11Normal" nowrap="nowrap">
								<b><bean:message key="archigest.archivo.transferencias.signaturaUI" />:</b>
								&nbsp;<c:out value="${unidadInstalacion.signaturaui}" />
							</td>
							<td width="25%" class="etiquetaAzul11Normal" nowrap="nowrap">
								<b><bean:message key="archigest.archivo.transferencias.formato" />:</b>
								&nbsp;<c:out value="${unidadInstalacion.nombreFormato}" />
							</td>

							<td width="15%" class="etiquetaAzul11Normal">
								<b><bean:message key="archigest.archivo.transferencias.unidades.totales" />:</b>
								&nbsp;<c:out value="${unidadInstalacion.unidadestotales}" />
							</td>
							<td width="15%" class="etiquetaAzul11Normal">
								<b><bean:message key="archigest.archivo.transferencias.unidades.validas" />:</b>
								&nbsp;<c:out value="${unidadInstalacion.unidadesvalidas}" />
							</td>

							<td>
								<c:choose>
									<c:when test="${unidadInstalacion.unidadesvalidas != unidadInstalacion.unidadestotales}">

									<c:url var="moverUdocsURL" value="/action/reubicacionUdocsAction">
										<c:param name="method" value="initReubicarUDocsEA" />
										<c:param name="idUinstalacionOrigen" value="${unidadInstalacion.id}"/>
										<c:param name="idHuecoOrigen" value="${unidadInstalacion.huecoID}"/>
									</c:url>
									<a class="etiquetaAzul12Bold" href="<c:out value="${moverUdocsURL}" escapeXml="false"/>" >
										<html:img page="/pages/images/compactar.gif" altKey="archigest.archivo.cf.mover"
										titleKey="archigest.archivo.cf.mover" styleClass="imgTextBottom" />
										<bean:message key="archigest.archivo.deposito.compactar"/>&nbsp;
									</a>


									</c:when>
									<c:otherwise>
										<html:img page="/pages/images/pixel.gif" border="0" styleClass="imgTextTop" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>

					<tr>

					<td isOpen="false" id="div<%=cajaPanelName%>" style="display:none;" colspan="6">
						<c:set var="udocs" value="${unidadInstalacion.listaUDocs}"/>
						<display:table name="pageScope.udocs" id="unidadDocumental"
							style="width:99%;margin-left:auto;margin-right:auto">

							<display:setProperty name="basic.show.header" value="false" />

							<display:setProperty name="basic.msg.empty_list">
								<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
									&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
								</div>
							</display:setProperty>

							<display:column title="" style="width:25px;text-align:right;" >
								<c:choose>
									<c:when test="${unidadDocumental.enRelacion== 'S'}">
										<html:img page="/pages/images/right.gif" border="0" styleClass="imgTextTop" />
									</c:when>
									<c:otherwise>
										<html:img page="/pages/images/wrong.gif" border="0" styleClass="imgTextTop" />
									</c:otherwise>
								</c:choose>
							</display:column>

							<c:if test="${vRelacion.formato.multidoc}">
								<display:column title="" style="width:30px;text-align:right;">
									<c:out value="${unidadDocumental_rowNum}" />:
								</display:column>
							</c:if>



							<display:column titleKey="archigest.archivo.transferencias.asunto">
							<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
								<c:param name="method" value="verEnFondos" />
								<c:param name="unidadDocumental" value="${unidadDocumental.idunidaddoc}" />
							</c:url>
								<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
									<c:out value="${unidadDocumental.titulo}" />
								</a>
							</display:column>


						</display:table>
					</td>
					</tr>
				</c:forEach>
				</table>

			</div>

			<c:if test="${numCajasRelacion > numMaxRegistrosScroll}">
				<SCRIPT>
					var capaConScroll=document.getElementById("divListaCajas");
					capaConScroll.className="bloqueConScroll250";
				</SCRIPT>
			</c:if>

			<span class="separador5">&nbsp;</span>
			</tiles:put>
		</tiles:insert>
	</c:if>



	</tiles:put>
</tiles:insert>
</html:form>








