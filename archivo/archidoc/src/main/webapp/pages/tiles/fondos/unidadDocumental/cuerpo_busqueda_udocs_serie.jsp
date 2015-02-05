<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="productores" value="${sessionScope[appConstants.fondos.LISTA_PRODUCTORES_KEY]}"/>
<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_SERIE_KEY]}"/>

<security:permissions action="${appConstants.documentosActions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
	<c:set var="mostrarLinkDocumentos" value="true"/>
	<tiles:insert page="/pages/tiles/documentos/script_funciones_acceso_documentos.jsp"></tiles:insert>
</security:permissions>

<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>
<c:set var="accionesPermitidas" value="${sessionScope[appConstants.common.LISTA_ACCIONES_BUSQUEDA_KEY]}"/>


<c:url var="gestionarConservadasURL" value="/action/gestionUdocsCF">
	<c:param name="method" value="gestionarUdocsConservadas" />
	<c:param name="idSerie" value="${serie.id}"/>
</c:url>

<script language="javascript" type="text/javascript">

function generateInformeUdocsSerie(){
	var frm = document.getElementById("formularioBusqueda");
	document.getElementById("methodBusqueda").value="generateInformeUdocsSerie";
	frm.submit();
}

function desplegarProductor(posicion) {
	switchVisibility("productorNoDesplegado"+posicion);
	switchVisibility("productorDesplegado"+posicion);
}

function limpiar(form){
	cleanFormularioBusquedaUdocsSerie(form);
	document.getElementById("productores").value="";
}

function ejecutarBusqueda(){

	var frm = document.getElementById("formularioBusqueda");
	frm.method.value = 'buscar';

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
		var message = '<bean:message key="archigest.archivo.buscando.msgUDocs"/>';
		var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
		window.top.showWorkingDiv(title, message, message2);
	}
	frm.submit();
}

function ejecutar(){
	if(document.getElementById("accion").value == ""){
		alert('<fmt:message key="error.busquedas.accion.obligatoria"/>');
	}
	else {
		var frm = document.getElementById("formularioResultado");
		if (frm && frm.ids) {
			var nSelected = FormsToolKit.getNumSelectedChecked(frm,"ids");
			if(nSelected >= 1) {
				frm.submit();
			} else{
				alert("<bean:message key='errors.gcontrol.listasacceso.necesarioSeleccionarAlMenosUnElemento'/>");
			}

		} else {
			alert("<bean:message key='errors.gcontrol.listasacceso.necesarioSeleccionarAlMenosUnElemento'/>");
		}
	}
}

function cleanFormularioBusquedaUdocsSerie(form){
	cleanTitulo(form);
	cleanNumeroExpediente(form);
	cleanCodigo(form);
	cleanFechaInicial(form);
	cleanFechaFinal(form);

}

</script>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.busqueda.unidadesDocumentales.serie"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<tiles:insert definition="button.closeButton" />
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.serie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="180px">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> <c:out value="${serie.titulo}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>


		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.cf.uDocsSerie"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table>
					<tr>
					 <c:if test="${not empty elementos}">

						<td nowrap="nowrap">
							<a class="etiquetaAzul12Bold"
							href="javascript:generateInformeUdocsSerie()">
							<html:img page="/pages/images/documentos/doc_pdf.gif"
							titleKey="archigest.archivo.informe.udocs.serie"
							altKey="archigest.archivo.informe.udocs.serie"
							styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.informe.udocs.serie"/>
						</a>
						</td>
						<td nowrap="nowrap" width="10">&nbsp;</td>
					</c:if>
					</tr>
				</table>




			</tiles:put>


			<tiles:put name="blockContent" direct="true">
			<html:form action="/buscarElementos" styleId="formularioBusqueda">
			<input type="hidden" name="method" id="methodBusqueda" value="buscar"/>
				<div class="separador5">&nbsp;</div>
				<div class="w98">
					<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp" flush="false">
						<tiles:put name="blockName" direct="true">filtro</tiles:put>
						<c:choose>
							<c:when test="${empty elementos}">
								<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
							</c:when>
							<c:otherwise>
								<tiles:put name="dockableContentVisible" direct="true">false</tiles:put>
							</c:otherwise>
						</c:choose>



						<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.filtro.busqueda"/></tiles:put>
						<tiles:put name="buttonBar" direct="true">


							<table cellpadding="0" cellspacing="0">
							<tr>
								<td nowrap="nowrap">
									<a href="javascript:ejecutarBusqueda()" class="etiquetaAzul12Normal">
										<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar"
					        			titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle"/>&nbsp;
					        			<bean:message key="archigest.archivo.buscar"/>
							        </a>
								</td>
								<td width="10">&nbsp;</td>
								<td nowrap="nowrap">
									<a href="javascript:limpiar(this.form)" class="etiquetaAzul12Normal">
										<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar"
							        		titleKey="archigest.archivo.limpiar" styleClass="imgTextMiddle"/>&nbsp;
							        	<bean:message key="archigest.archivo.limpiar"/>
							        </a>
								</td>
							</tr>
						</table>

						</tiles:put>

						<tiles:put name="dockableContent" direct="true">
							<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
							<bean:define id="classTdTituloCampoSinBorde" value="tdTitulo" toScope="request"/>
							<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
							<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
							<bean:define id="sizeCampo" value="150" toScope="request"/>
							<table class="formulario">

							<c:forEach var="campos" items="${beanBusqueda.listaCamposEntrada}">
								<bean:define id="elemento" name="campos" toScope="request"/>
								<tiles:insert name="campos.busqueda.udocs.serie" flush="true"/>
							</c:forEach>

							</table>
						</tiles:put>
					</tiles:insert>
				</div>
				<div class="separador5">&nbsp;</div>
</html:form>

			<div class="w98">
<html:form action="/resultadoBusquedaFondosAction" styleId="formularioResultado">
			<input type="hidden" name="method" id="methodResultado" value="ejecutarAccion"/>



				<display:table name="pageScope.elementos"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="elemento"
					pagesize="0"
					sort="external"
					defaultsort="0"
					requestURI="../../action/buscarElementos?method=buscar"
					export="true"
					decorator="common.view.VisitedRowDecorator">
				<c:if test ="${not empty elementos and  not empty accionesPermitidas}">
				<display:footer>
					<div class="separador8">&nbsp;</div>
					<div style="text-align:right">
						<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms['ResultadoBusquedaFondosForm'].ids);" >
							<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos"
									  titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
									  <bean:message key="archigest.archivo.selTodos"/>&nbsp;
						</a>
						&nbsp;&nbsp;
						<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms['ResultadoBusquedaFondosForm'].ids);" >
							<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel"
									  titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
									&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
						</a>
						&nbsp;&nbsp;
						<span class="etiquetaAzul12Bold">
							<bean:message key="archigest.archivo.acciones"/>:&nbsp;
						</span>
						<html:select property="accion" styleId="accion">
								<html:option value="">&nbsp;</html:option>
								<c:forEach items="${accionesPermitidas}" var="accion">
									<html-el:option value="${accion.value}">
										<fmt:message key="${accion.label}"/>
									</html-el:option>
								</c:forEach>
							</html:select>
						  <a href="javascript:ejecutar()" class="etiquetaAzul12Bold">
						  <html:img page="/pages/images/go.gif"
	                              altKey="archigest.archivo.busquedas.operacion.accion.ejecutar"
	                              titleKey="archigest.archivo.busquedas.operacion.accion.ejecutar"
	                              styleClass="imgTextMiddle" />
						</a>&nbsp;&nbsp;
						</div>
						<div class="separador8">&nbsp;</div>
				</display:footer>
				</c:if>
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
					</display:setProperty>
					<c:if test ="${not empty accionesPermitidas}">
					<display:column media="html" style="width:25px">
						<html-el:multibox style="border:0" property="ids" value="${elemento.id}"/>
					</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO] != null}">
						<c:set var="campoCodigo" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO]}"/>
						<c:choose>
							<c:when test="${campoCodigo.mostrarLink == 'S'}">
								<display:column titleKey="archigest.archivo.cf.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
									<c:url var="URL" value="/action/buscarElementos">
										<c:param name="method" value="verEnCuadro" />
										<c:param name="id" value="${elemento.id}" />
									</c:url>
									<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="codigo"/></a>
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column titleKey="archigest.archivo.cf.codigo" property="codigo" media="html"/>
							</c:otherwise>
						</c:choose>
						<display:column titleKey="archigest.archivo.cf.codigo" property="codigo" media="csv excel xml pdf"/>
					</c:if>

					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE] != null}">
						<c:choose>
							<c:when test="${campoTitulo.mostrarLink == 'S'}">
								<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" sortProperty="numexp" sortable="true" headerClass="sortable" media="html">
									<c:choose>
										<c:when test="${elemento.subtipoCaja}">
											<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
											<c:out value="${elemento.nombreRangos}"/>
										</c:when>
										<c:otherwise>
											<c:out value="${elemento.numexp}"/>
										</c:otherwise>
									</c:choose>
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="html" headerClass="sortable" sortable="true">
									<c:choose>
										<c:when test="${elemento.subtipoCaja}">
											<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
											<c:out value="${elemento.nombreRangos}"/>
										</c:when>
										<c:otherwise>
											<c:out value="${elemento.numexp}"/>
										</c:otherwise>
									</c:choose>
								</display:column>
							</c:otherwise>
						</c:choose>
						<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="csv xml excel pdf">
							<c:choose>
								<c:when test="${elemento.subtipoCaja}">
									<bean:message key="archigest.archivo.simbolo.rango"/> <c:out value="${elemento.nombreRangos}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${elemento.numexp}"/>
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>

					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO] != null}">
						<c:set var="campoTitulo" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO]}"/>
						<c:choose>
							<c:when test="${campoTitulo.mostrarLink == 'S'}">
								<display:column titleKey="archigest.archivo.cf.titulo" sortProperty="titulo" sortable="true" headerClass="sortable" media="html">
									<c:url var="URL" value="/action/buscarElementos">
										<c:param name="method" value="verEnCuadro" />
										<c:param name="id" value="${elemento.id}" />
									</c:url>
									<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="titulo"/></a>
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" sortable="true" media="html"/>
							</c:otherwise>
						</c:choose>
						<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" media="csv excel xml pdf"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA] != null}">
						<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc"  sortable="true" headerClass="sortable" property="signaturaudoc" sortProperty="signaturaudoc"/>
					</c:if>

					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL] != null}">
						<display:column titleKey="archigest.archivo.cf.nivel" property="nombre" sortable="true" headerClass="sortable" />
					</c:if>

					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_ESTADO] != null}">
						<display:column titleKey="archigest.archivo.estado" sortProperty="estado" sortable="true" headerClass="sortable">
							<c:set var="keyTituloEstado">
								archigest.archivo.cf.estadoElementoCF.<c:out value="${elemento.estado}"/>
							</c:set>
							<fmt:message key="${keyTituloEstado}" />
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL] != null}">
						<display:column titleKey="archigest.archivo.fechaInicial" sortable="true" property="valorFInicial" headerClass="sortable">
							<logic:equal name="elemento" property="formatoFInicial" value="S"><fmt:message key="archigest.archivo.abreviatura.siglo"/></logic:equal>
							<bean:write name="elemento" property="valorFInicial"/>
							<logic:present name="elemento" property="calificadorFInicial">
								(<bean:write name="elemento" property="calificadorFInicial"/>)
							</logic:present>
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL] != null}">
						<display:column titleKey="archigest.archivo.fechaFinal" sortable="true" property="valorFFinal" headerClass="sortable">
							<logic:equal name="elemento" property="formatoFFinal" value="S"><fmt:message key="archigest.archivo.abreviatura.siglo"/></logic:equal>
							<bean:write name="elemento" property="valorFFinal"/>
							<logic:present name="elemento" property="calificadorFFinal">
								(<bean:write name="elemento" property="calificadorFFinal"/>)
							</logic:present>
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR] != null}">

					<display:column titleKey="archigest.archivo.cf.productor" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.cf.productor"  media="html" headerClass="sortable" >
						<c:choose>

						<c:when test="${elemento.productorDockable}">
							<c:set var="position" value="${elemento_rowNum - 1}" />
							<c:set var="productorNoDesplegado" value="productorNoDesplegado${position}" />
							<c:set var="productorDesplegado" value="productorDesplegado${position}" />

							<div id="<c:out value="${productorNoDesplegado}"/>">
								<a href="javascript:desplegarProductor(<c:out value="${position}"/>)">
									<html:img page="/pages/images/plus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${elemento.nombreCortoProductor}" />
							</div>

							<div id="<c:out value="${productorDesplegado}"/>" style="display:none">
								<a href="javascript:desplegarProductor(<c:out value="${position}"/>)">
									<html:img page="/pages/images/minus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${elemento.nombreProductor}" />
							</div>
						</c:when>
						<c:otherwise>
							<c:out value="${elemento.nombreProductor}" />
						</c:otherwise>
						</c:choose>
					</display:column>
					</c:if>

					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_INTERESADOS] != null}">
										<display:column titleKey="archigest.archivo.transferencias.interesados" media="csv excel xml pdf">
						<c:forEach  items="${unidadDocumental.interesados}" var="interesado" varStatus="pos">
							-<c:if test="${interesado.principal}">(*)</c:if><c:out value="${interesado.nombre}"/>
							<c:if test="${not pos.last}">/</c:if>
						</c:forEach>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.interesados" media="html">
						<c:forEach  items="${elemento.interesados}" var="interesado" varStatus="pos">
							-<c:if test="${interesado.principal}">
								<html:img page="/pages/images/man.gif"
								altKey="archigest.archivo.transferencias.interesadoPrincipal" titleKey="archigest.archivo.transferencias.interesadoPrincipal"
									styleClass="imgTextBottom" />
							</c:if><c:out value="${interesado.nombre}"/>
								<c:if test="${not pos.last}">
									<br/>
								</c:if>
						</c:forEach>
					</display:column>
					</c:if>

					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CONSERVADA] != null}">
					<display:column media="html" titleKey="archigest.archivo.cf.conservada.abreviado" style="text-align:center">
						<c:if test="${! empty elemento.idEliminacion}">
							<html:img page="/pages/images/udoc/conservada.gif"
							        altKey="archigest.archivo.cf.conservada"
							        titleKey="archigest.archivo.cf.conservada"
							        styleClass="imgTextMiddle"/>
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.cf.conservada.abreviado" media="csv excel xml pdf">
						<c:if test="${!empty elemento.idEliminacion}">
							<bean:message key="archigest.archivo.si" />
						</c:if>
					</display:column>
					</c:if>


					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_BLOQUEO] != null}">
						<display:column style="text-align: center;"  titleKey="archigest.archivo.cf.bloqueada.abreviado" media="html">
							<c:choose>
								<c:when test="${elemento.marcasBloqueoUnidad>0}" >
									<html:img
										page="/pages/images/udocBloqueada.gif"
										titleKey="archigest.archivo.bloqueada"
										altKey="archigest.archivo.bloqueada"
										styleClass="imgTextBottom" />
								</c:when>
								<c:otherwise>
									<html:img
										page="/pages/images/udocDesbloqueada.gif"
										titleKey="archigest.archivo.desbloqueada"
										altKey="archigest.archivo.desbloqueada"
										styleClass="imgTextBottom" />
								</c:otherwise>
							</c:choose>
						</display:column>
						<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
							<c:choose>
								<c:when test="${elemento.marcasBloqueoUnidad>0}" >
									<bean:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>

					<c:if test="${mostrarLinkDocumentos}">
						<display:column titleKey="archigest.archivo.columna.documentos" sortable="true" headerClass="sortable" media="html">
							<tiles:insert page="/pages/tiles/documentos/columna_acceso_documentos.jsp" >
								<tiles:put name="idElemento" direct="true"><c:out value="${elemento.id}"/></tiles:put>
								<tiles:put name="numDocumentos"><c:out value="${elemento.numDocumentosElectronicos}"/></tiles:put>
							</tiles:insert>
						</display:column>
					</c:if>
				</display:table>
				</html:form>
			</div> <%--bloque --%>

				<div class="separador5">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
