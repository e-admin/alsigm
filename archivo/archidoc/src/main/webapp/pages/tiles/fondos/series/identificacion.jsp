<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string" prefix="str" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />
<c:set var="productoresVigentes" value="${sessionScope[appConstants.fondos.PRODUCTORES_VIGENTES_KEY]}" />
<c:set var="productoresHistoricos" value="${sessionScope[appConstants.fondos.PRODUCTORES_HISTORICOS_KEY]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}" />

<script>
/*
	function modificarFechaInicial(pos){

		var divTextFechaInicial="divTextFechaInicial"+pos;
		var textBoxFechaInicial="textBoxFechaInicial"+pos;

		for(i=1;;i++)
		{
			divAceptarCancelarFechaInicial="divAceptarCancelarFechaInicial"+i;
			divTextFechaInicial="divTextFechaInicial"+i
			elemento=document.getElementById(divAceptarCancelarFechaInicial)

			if(elemento!=null){
				elemento.className="hidden";
				document.getElementById(divTextFechaInicial).className="visible";
			}
			else{
				break;
			}
		}

		divAceptarCancelarFechaInicial="divAceptarCancelarFechaInicial"+pos;
		divTextFechaInicial="divTextFechaInicial"+pos;

		document.getElementById(divAceptarCancelarFechaInicial).className="visible";
		document.getElementById(divTextFechaInicial).className="hidden";

		document.getElementById("valueFechaInicial").value=textBoxFechaInicial.value;

	}

	function cancelarModificacionFechaInicial(pos){
		var divAceptarCancelarFechaInicial="divAceptarCancelarFechaInicial"+pos;
		var divTextFechaInicial="divTextFechaInicial"+pos;
		document.getElementById(divAceptarCancelarFechaInicial).className="hidden";
		document.getElementById(divTextFechaInicial).className="visible";
	}
	*/
	function aceptarModificacionFechaInicial(valor,posicion){
		document.getElementById("position").value=posicion;
		document.getElementById("valueFechaInicial").value=valor;
		document.getElementById("method").value="actualizarFechaInicial";
		document.forms[0].submit();
	}

	function aceptarFechaFinalVigenciaHistorico(valor,posicion, tipo){
		document.getElementById("position").value=posicion;
		document.getElementById("valueFechaInicial").value=valor;
		document.getElementById("tipo").value=tipo;
		document.getElementById("method").value="actualizarFechaFinalVigenciaHistorico";
		document.forms[0].submit();
	}
</script>
<input type="hidden" id="valueFechaInicial" name="valueFechaInicial" />
<input type="hidden" name="method" id="method" value="actualizarFechaInicial" />
<input type="hidden" id="position" name="position" />
<input type="hidden" id="tipo" name="tipo" />
<c:choose>
<c:when test="${identificacionSerie.withoutValues}">
	<div class="separador5">&nbsp;</div>
	<bean:message key="archigest.archivo.cf.serieNoIdentificada"/>
	<div class="separador5">&nbsp;</div>
</c:when>
<c:otherwise>
	<div class="separador5">&nbsp;</div>

	<c:if test="${!empty identificacionSerie.procedimiento}" >
			<DIV class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
					<TABLE class="w100m1" cellpadding=0 cellspacing=0>
					  <TR>
						<TD class="etiquetaAzul12Bold" width="40%" style="vertical-align:top;">
							&nbsp;<bean:message key="archigest.archivo.cf.procedimientosDeLaSerie"/>
						</TD>
					  </TR>
					</TABLE>
			</DIV>

		<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--primer bloque de datos --%>
			<c:set var="vProcedimiento" value="${identificacionSerie.procedimiento}" />
			<div class="separador5">&nbsp;</div>
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="170px">Código: </td>
					<td class="tdDatos"><c:out value="${vProcedimiento.codigo}" /></td>
				</tr>
				<tr>
					<td class="tdTitulo">Título: </td>
					<td class="tdDatos"><c:out value="${vProcedimiento.nombre}" /></td>
				</tr>
			</table>
			<div class="separador5">&nbsp;</div>
		</div> <%--bloque --%>
	</c:if>

	<div class="separador8">&nbsp;</div>

	<DIV class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--cabecero primer bloque de datos --%>
		<TABLE class="w100m1" cellpadding="0" cellspacing="0" height="100%">
		  <TR>
			<TD class="etiquetaAzul12Bold">
				&nbsp;<bean:message key="archigest.archivo.contenido"/>
			</TD>
		   </TR>
		</TABLE>
	</DIV>
	<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
	<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
		<TR>
			<TD class="tdTitulo" width="170px">
				<bean:message key="archigest.archivo.identificacion.definicion"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${identificacionSerie.definicion}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.identificacion.tramites"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<str:replace replace="NL" with="<br>NL" newlineToken="NL"><c:out value="${identificacionSerie.tramites}"/></str:replace>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.identificacion.normativa"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<str:replace replace="NL" with="<br>NL" newlineToken="NL"><c:out value="${identificacionSerie.normativa}"/></str:replace>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.identificacion.documentosbasicos"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<str:replace replace="NL" with="<br>NL" newlineToken="NL"><c:out value="${identificacionSerie.docsBasicosUnidadDocumental}"/></str:replace>
			</TD>
		</TR>

		<c:if test="${appConstants.configConstants.mostrarInformacionIdentificacionExtendia}">
		<TR>
			<TD class="tdTitulo" nowrap="nowrap">
				<bean:message key="archigest.archivo.series.tipo.documentacion"/>:
			</TD>
			<TD class="tdDatos">
				<c:out value="${identificacionSerie.tipoDocumentacion}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" nowrap="nowrap" width="200px">
				<bean:message key="archigest.archivo.series.prevision.anual.volumen"/>:
			</TD>
			<TD class="tdDatos">
				<c:out value="${identificacionSerie.volumenPrevisionAnual}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" nowrap="nowrap">
				<bean:message key="archigest.archivo.series.prevision.anual.soporte"/>:
			</TD>
			<TD class="tdDatos">
				<c:out value="${identificacionSerie.soportePrevisionAnual}"/>
			</TD>
		</TR>
		</c:if>



	</TABLE>

	<c:if test="${serie.contieneUnidadesDocumentales}">
	<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
		<TR>
			<TD class="tdTitulo" colspan="2">
				<bean:message key="archigest.archivo.identificacion.descripcionfisica"/>:&nbsp;
			</TD>
		</TR>
		<TR>
			<TD colspan="2">
				<TABLE class="w100" cellpadding="0" cellspacing="0">
				<c:set var="volumenSerie" value="${serie.volumenSerie}" />
				<c:if test="${!empty volumenSerie}">
				<TR>
					<TD width="35px">&nbsp;</TD>
					<TD class="tdDatos" colspan="2">
						<display:table name="pageScope.volumenSerie" id="infoVolumen" style="width:98%; margin-bottom:2px;;margin-left:auto;margin-right:auto">
							<display:setProperty name="basic.msg.empty_list">
								&nbsp;&nbsp;<bean:message key="archigest.archivo.cf.noSoportesEnSerie"/>
							</display:setProperty>
							<display:column titleKey="archigest.archivo.transferencias.volumen" property="cantidad" />
							<display:column titleKey="archigest.archivo.transferencias.soporte" property="tipoDocumental" />
						</display:table>
					</TD>
				</TR>
				</c:if>
				<TR>
					<TD width="35px">&nbsp;</TD>
					<TD class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.serie.numUdocs"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${serie.numunidaddoc}"/>
					</TD>
				</TR>
				<TR>
					<TD width="35px">&nbsp;</TD>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.serie.numUInstalacion"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${serie.numuinstalacion}"/>
					</TD>
				</TR>
				<TR>
					<TD width="35px">&nbsp;</TD>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.serie.volumen"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${serie.volumen/100}"/>
					</TD>
				</TR>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" colspan="2">
				<bean:message key="archigest.archivo.identificacion.fechasExtremas"/>:&nbsp;
			</TD>
		</TR>
		<TR>
			<TD colspan="2">
				<TABLE class="w100" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="35px">&nbsp;</TD>
					<TD class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.identificacion.inicial"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:if test="${serie.fextremainicial != null}">
							<fmt:formatDate value="${serie.fextremainicial}" pattern="${FORMATO_FECHA}"/>
						</c:if>
						<c:if test="${serie.fextremainicial == null}">&nbsp;</c:if>
					</TD>
				</TR>
				<TR>
					<TD width="35px">&nbsp;</TD>
					<TD class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.identificacion.final"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:if test="${serie.fextremafinal != null}">
							<fmt:formatDate value="${serie.fextremafinal}" pattern="${FORMATO_FECHA}"/>
						</c:if>
						<c:if test="${serie.fextremafinal == null}">&nbsp;</c:if>
					</TD>
				</TR>
				</TABLE>
			</TD>
		</TR>
	</TABLE>
	</c:if>
	</div>

	<div class="separador5">&nbsp;</div>
	<DIV class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--cabecero primer bloque de datos --%>
		<TABLE class="w100m1" cellpadding=0 cellspacing=0>
		  <TR>
			<TD class="etiquetaAzul12Bold" width="40%" style="vertical-align:top;">
				&nbsp;<bean:message key="archigest.archivo.cf.productoresVigentes"/>
			</TD>

			</TR>
		</TABLE>
	</DIV>

	<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--primer bloque de datos --%>
		<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.productoresVigentes" id="productorVigente" style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						&nbsp;<bean:message key="archigest.archivo.cf.noProductoresVigentes"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.nombre" property="nombre" />
					<display:column titleKey="archigest.archivo.fechaInicial" style="width:200px">
						<tiles:insert definition="campo.fecha.editable" flush="true">
							<c:if test="${fechaEditable}">
									<c:if test="${serie.estadoserie == appConstants.estadosSerie.NO_VIGENTE ||
												  serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO}">
										<tiles:put name="editable" value="true" />
									</c:if>
							</c:if>
							<tiles:put name="nombreFuncionAceptar">aceptarModificacionFechaInicial</tiles:put>
							<tiles:put name="suffix"><c:out value="${productorVigente_rowNum}"/></tiles:put>
							<tiles:put name="nombreCampo" value="campoFechaInicial" />
							<tiles:put name="valorCampo"><fmt:formatDate value="${productorVigente.fechaInicial}" pattern="${appConstants.common.FORMATO_FECHA}" /></tiles:put>
							<tiles:put name="tipo" value=""/>
						</tiles:insert>
					</display:column>
					<c:if test="${!empty productorVigente.idLCAPref}">
					<display:column titleKey="archigest.archivo.cf.controlAcceso" style="width:140px;text-align:center">
						<c:url var="verListaControlAccesoURL" value="/action/gestionListasAcceso">
							<c:param name="method" value="verListaAcceso" />
							<c:param name="idListaAcceso" value="${productorVigente.idLCAPref}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${verListaControlAccesoURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/candado.gif" altKey="archigest.archivo.cf.controlAcceso" titleKey="archigest.archivo.cf.controlAcceso" styleClass="imgTextMiddle" />
						</a>
					</display:column>
					</c:if>
					<c:if test="${serie.estadoserie == appConstants.estadosSerie.HISTORICA}">
						<display:column titleKey="archigest.archivo.fechaFinal" style="width:100px">
							<tiles:insert definition="campo.fecha.editable" flush="true">
								<tiles:put name="editable" value="true" />
								<tiles:put name="nombreFuncionAceptar">aceptarFechaFinalVigenciaHistorico</tiles:put>
								<tiles:put name="suffix"><c:out value="${productorVigente_rowNum}"/></tiles:put>
								<tiles:put name="nombreCampo" value="campoFechaFinal" />
								<tiles:put name="valorCampo"><fmt:formatDate value="${productorVigente.fechaFinal}" pattern="${appConstants.common.FORMATO_FECHA}" /></tiles:put>
								<tiles:put name="tipo" value="VIGENTE"/>
							</tiles:insert>
						</display:column>
					</c:if>
				</display:table>

		<div class="separador5">&nbsp;</div>
	</div>

	<div class="separador5">&nbsp;</div>

	<DIV class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--cabecero primer bloque de datos --%>
		<TABLE class="w100m1" cellpadding=0 cellspacing=0>
		  <TR>
			<TD class="etiquetaAzul12Bold" width="40%" style="vertical-align:top;">
				&nbsp;<bean:message key="archigest.archivo.cf.productoresHistoricos"/>
			</TD>
			</TR>
		</TABLE>
	</DIV>

	<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--primer bloque de datos --%>
		<div class="separador5">&nbsp;</div>
			<c:set var="vProductores" value="${identificacionSerie.productoresHistoricos}" />
			<display:table name="pageScope.productoresHistoricos" id="productorHistorico" style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					&nbsp;<bean:message key="archigest.archivo.cf.noProductoresHistoricos"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.nombre" property="nombre" />
				<display:column titleKey="archigest.archivo.fechaInicial" style="width:100px">
					<fmt:formatDate value="${productorHistorico.fechaInicial}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.fechaFinal" style="width:100px">
						<tiles:insert definition="campo.fecha.editable" flush="true">
							<c:if test="${fechaEditable}">
									<c:if test="${serie.estadoserie == appConstants.estadosSerie.NO_VIGENTE ||
												  serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO ||
												  serie.estadoserie == appConstants.estadosSerie.HISTORICA}">
										<tiles:put name="editable" value="true" />
									</c:if>
							</c:if>
							<tiles:put name="nombreFuncionAceptar">aceptarFechaFinalVigenciaHistorico</tiles:put>
							<tiles:put name="suffix"><c:out value="${productorHistorico_rowNum}"/></tiles:put>
							<tiles:put name="nombreCampo" value="campoFechaFinalHistorico" />
							<tiles:put name="valorCampo"><fmt:formatDate value="${productorHistorico.fechaFinal}" pattern="${appConstants.common.FORMATO_FECHA}" /></tiles:put>
							<tiles:put name="tipo" value="HISTORICO"/>
						</tiles:insert>
				</display:column>
				<c:if test="${!empty productorHistorico.idLCAPref}">
					<display:column titleKey="archigest.archivo.cf.controlAcceso" style="width:140px;text-align:center">
						<c:url var="verListaControlAccesoHistoricoURL" value="/action/gestionListasAcceso">
							<c:param name="method" value="verListaAcceso" />
							<c:param name="idListaAcceso" value="${productorHistorico.idLCAPref}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${verListaControlAccesoHistoricoURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/candado.gif" altKey="archigest.archivo.cf.controlAcceso" titleKey="archigest.archivo.cf.controlAcceso" styleClass="imgTextMiddle" />
						</a>
					</display:column>
					</c:if>
			</display:table>

		<div class="separador5">&nbsp;</div>
	</div>

</c:otherwise>
</c:choose>
