<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>
<c:set var="vPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="vDetallePrevision" value="${sessionScope[appConstants.transferencias.DETALLEPREVISION_KEY]}"/>
<c:set var="vDetallePrevisionSeleccionado" value="${sessionScope[appConstants.transferencias.DETALLEPREVISION_SELECCIONADO_KEY]}"/>
<c:set var="vListaSeries" value="${sessionScope[appConstants.transferencias.LISTA_SERIES_KEY]}"/>

<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />
<c:set var="gestionRelacionesFormName" value="${mappingGestionRelaciones.name}" />
<c:set var="gestionRelacionesForm" value="${requestScope[gestionRelacionesFormName]}" />
<c:set var="infoNoDesplegadoName" value="infoNoDesplegado" />
<c:set var="infoDesplegadoName" value="infoDesplegado" />

<script>
	function plegarInfo(posicion){
		divDesplegado = 'infoDesplegado' + posicion;
		divNoDesplegado = 'infoNoDesplegado' + posicion;
				
		document.getElementById(divDesplegado).style.display="none";
		document.getElementById(divNoDesplegado).style.display="block";
	}
	
	function desplegarInfo(posicion){
		divDesplegado = 'infoDesplegado' + posicion;
		divNoDesplegado = 'infoNoDesplegado' + posicion;
		document.getElementById(divDesplegado).style.display="block";
		document.getElementById(divNoDesplegado).style.display="none";
	}

	function detalleSeleccionado(idDetalle, formato, serie) {
		
		var form = document.forms['<c:out value="${gestionRelacionesFormName}" />'];
		/*var selectFormato = form.idformatoseleccionado;
		var nOptions = selectFormato.options.length;
		for (var i=0;i<nOptions; i++) {
			if (selectFormato.options[i].value == formato) {
				selectFormato.selectedIndex = i;
				break;
			}
		}*/
		
		form.iddetalleseleccionado.value = idDetalle;
		
		form.method.value="cambioDetalle";
		form.submit()
		
	}

</script>		
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.crearRel" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0 >
		 <TR>
			<TD>
				<c:url var="atrasURL" value="/action/gestionRelaciones">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${atrasURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Previous.gif" altKey="wizard.atras" titleKey="wizard.atras" styleClass="imgTextTop" />
					&nbsp;<bean:message key="wizard.atras"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>

			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionRelaciones.name}" />'].submit();" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
					&nbsp;<bean:message key="archigest.archivo.aceptar" />
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
	       <TD>
				<c:url var="urlCancelar" value="/action/gestionRelaciones">
					<c:param name="method" value="goReturnPoint"/>
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${urlCancelar}" escapeXml="false"/>'" >
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextTop" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		   </TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoPrevision</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.previsionDeTransferencia"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
				<TABLE class="w98m1" cellpadding=0 cellspacing=2>
					<TR>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.prevision"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${bPrevision.codigoTransferencia}"/>
							</span>
						</TD>
						<TD width="25%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${bPrevision.nombreestado}"/>
							</span>
						</TD>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<fmt:formatDate value="${bPrevision.fechaestado}" pattern="${FORMATO_FECHA}" />
							</span>
						</TD>
						<TD width="35%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${bPrevision.gestor.nombreCompleto}"/>
							</span>
						</TD>
					</TR>
				</TABLE>
				</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/datos_prevision.jsp" />
			</tiles:put>
		</tiles:insert>	

		<html:form action="/gestionRelaciones">
			<html:hidden property="idseriemostrarproductores" styleId="idseriemostrarproductores" />
			<html:hidden property="idfondoorigen" styleId="idfondoorigen" />
	
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<c:choose>
			<c:when test="${vPrevision.detallada}">
				<bean:message key="archigest.archivo.transferencias.relacion.selLineaDetalle"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.transferencias.relacion.selSerie"/>
			</c:otherwise>
			</c:choose>
		</tiles:put>
		<c:if test="${!vPrevision.detallada}">
		<tiles:put name="buttonBar" direct="true">
			<table><tr>
				<td align="right">
					<script>
						function buscarSeries() {
							var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
							form.method.value = "buscarSerie";
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Normal" href="javascript:buscarSeries()">
						<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.buscar"/>
					</a>		
				</td>
			</tr></table>
		</tiles:put>
		</c:if>
		<tiles:put name="blockContent" direct="true">
		<div class="separador5"></div>
				<c:url var="urlPaginacion" value="/action/gestionRelaciones">
					<c:param name="method" value="nueva"/>
					<c:param name="idprevisionseleccionada" value="${vPrevision.id}"/>
				</c:url>

				<c:set var="detallesPrevision" value="${sessionScope[appConstants.transferencias.DETALLEPREVISION_KEY]}" />
				
				<display:table name="pageScope.detallesPrevision" 
					style="width:99%;margin-top:2px;"
					id="listaRegistros" 
					export="false">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.msgNoDetCrearRel" />:
					</display:setProperty>

					<display:column title="&nbsp;" style="width:10px;vertical-align:top">
						<input type="radio" name="iddetalleseleccionado" value="<c:out value="${listaRegistros.id}" />" onclick="detalleSeleccionado('<c:out value="${listaRegistros.id}" />', '<c:out value="${listaRegistros.idFormatoUI}" />', '<c:out value="${listaRegistros.serieDestino.id}" />')" <c:if test="${RelacionForm.iddetalleseleccionado == listaRegistros.id}">CHECKED</c:if> >
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.archivoOrigen" property="nombreArchivoOrigen" style="vertical-align:top;" /> 
					<display:column titleKey="archigest.archivo.transferencias.serieOrigen" style="vertical-align:top;">
						<c:out value="${listaRegistros.serieOrigen.codigo}" />
						<c:out value="${listaRegistros.serieOrigen.denominacion}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.archivoDestino" property="nombreArchivoDestino" style="vertical-align:top;" />
					<display:column titleKey="archigest.archivo.transferencias.serieDestino" style="vertical-align:top;" >
						<c:out value="${listaRegistros.serieDestino.codigo}" />
						<c:out value="${listaRegistros.serieDestino.denominacion}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.formato" style="vertical-align:top;width:200px">
							<c:if test="${listaRegistros.udocsElectronicas != null}">
								&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${listaRegistros.udocsElectronicas}"/>&nbsp;
								<bean:message key="archigest.archivo.transferencias.unidadesElectronicas"/>
								<br>
							</c:if>
							<c:set var="position" value="${listaRegistros_rowNum - 1}" />
							<c:set var="infoNoDesplegado" value="${infoNoDesplegadoName}${position}" />
							<c:set var="infoDesplegado" value="${infoDesplegadoName}${position}" />						
							<div id="<c:out value="${infoNoDesplegado}"/>" style="display:block;width:100%">
								<a href="javascript:desplegarInfo(<c:out value="${position}"/>)">
									<html:img page="/pages/images/plus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${listaRegistros.totalVolumenFormato}" />&nbsp;
									<bean:message key="archigest.archivo.deposito.unidInstalacion"/>
							</div>	
							<div id="<c:out value="${infoDesplegado}"/>" style="display:none;width:100%">
								<a href="javascript:plegarInfo(<c:out value="${position}"/>)">
									<html:img page="/pages/images/minus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${listaRegistros.totalVolumenFormato}" />&nbsp;
									<bean:message key="archigest.archivo.deposito.unidInstalacion"/>
									<c:forEach items="${listaRegistros.volumenFormato}" var="elemento">
												<br><html:img page="/pages/images/pixel.gif" width="25" styleClass="imgTextMiddle" />
												<c:out value="${elemento.numUI}"/>&nbsp;
												<c:out value="${elemento.nombreFmt}"/>
									</c:forEach>
							</div>				
					</display:column>
				</display:table>  
				<div class="separador5">&nbsp;</div>

		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">Datos Relación</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
				<input type="hidden" name="method" value="crearrelacion">
				<html:hidden property="idprevisionseleccionada" />
				<TR>
					<TD class="tdTitulo" width="180px">
						<bean:message key="archigest.archivo.transferencias.tipoTransfEntreArchivos"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipooperacion<c:out value="${vPrevision.tipooperacion}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.archivoOrigen"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vDetallePrevisionSeleccionado.nombreArchivoOrigen}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.serieOrigen"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vDetallePrevisionSeleccionado.serieOrigen.codReferencia}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.archivoDestino"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vDetallePrevisionSeleccionado.nombreArchivoDestino}" />
						
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.serieDestino"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vDetallePrevisionSeleccionado.serieDestino.codReferencia}" />
					</TD>
				</TR>
				<c:if test="${!vPrevision.detallada}">
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vPrevision.numuinstalacion}"/>
					</TD>
				</TR>
				</c:if>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="listaFormatos" value="${sessionScope[appConstants.transferencias.LISTA_FORMATOS_KEY]}"/>
						<html:select property='idformatoseleccionado' size="1">
							<html:options collection="listaFormatos" labelProperty="nombre" property="id"/>
						</html:select>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.soporteDocumental"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="listaFormasDocumentales" value="${sessionScope[appConstants.transferencias.LISTA_FORMAS_DOCUMENTALES_KEY]}"/>
						<html:select property='formaDocumental' size="1">
							<html:options collection="listaFormasDocumentales" labelProperty="valor" property="valor"/>
						</html:select>
					</TD>
				</TR>
				<c:set var="nivelesDocumentales" value="${sessionScope[appConstants.transferencias.LISTA_NIVELES_DOCUMENTALES_KEY]}" />
				<%-- Se podrá elegir el nivel documental siempre que haya más de un nivel documental definido --%>
				<c:if test="${!empty nivelesDocumentales}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.nivelDocumental"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:select property="idNivelDocumental">
								<html:option value=""></html:option>
								<html:options collection="nivelesDocumentales" labelProperty="nombre" property="id" />
							</html:select>
						</td>
					</tr>
				</c:if>
				<TR>
					<TD class="tdTitulo" style="vertical-align:top">
						<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:textarea property="observaciones" rows="2" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>
					</TD>
				</TR>
			</TABLE>
			</tiles:put>
		</tiles:insert>		
		</html:form>

	</tiles:put>
</tiles:insert>