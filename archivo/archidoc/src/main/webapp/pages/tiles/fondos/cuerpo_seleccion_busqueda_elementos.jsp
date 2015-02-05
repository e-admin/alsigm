<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>

<script>
	function seleccionarSelectedItems(selectionFormName) {
		var selectionForm = document.forms[selectionFormName];
		if (selectionForm && selectionForm.selectedElem) {
			var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedElem");
			if(nSelected >= 1) {
				selectionForm.submit();
			} else
				alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
		} else {
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
		}
	}

	function incorporarResultados(selectionFormName) {
		var selectionForm = document.forms[selectionFormName];
		if (selectionForm) {
			selectionForm.method.value='incorporarResultadosBusqueda';
			selectionForm.submit();
		}
	}
</script>

<div class="separador8">&nbsp;</div>

<div id="contenedor_ficha">
	<div class="ficha">
		<h1><span>
		<div class="w100">
			<table class="w98" cellpadding="0" cellspacing="0">
				<tr>
					<td class="etiquetaAzul12Bold" height="25px">
						<bean:message key="archigest.archivo.cf.busqueda.seleccion.caption" />
					</td>
					<td align="right">
						<table cellpadding=0 cellspacing=0>
							<tr>
								<td>
									<bean:struts id="mappingBuscarElementos" mapping="/buscarElementos" />
									<a class="etiquetaAzul12Bold" href="javascript: seleccionarSelectedItems('<c:out value="${mappingBuscarElementos.name}" />')">
										<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar"	titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
											&nbsp;<bean:message key="archigest.archivo.aceptar" />
									</a>
								</td>
								<TD width="10">&nbsp;</TD>
								<c:url var="cancelURI" value="/action/buscarElementos">
									<c:param name="method" value="goReturnPoint" />
								</c:url>

								<td>
									<a class="etiquetaAzul12Bold" href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'" >
										<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
								   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
								   	</a>
								</td>
							</tr>
						</table>
					</TD>
				</tr>
			</table>
		</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
			<div id="barra_errores"><archivo:errors /></div>

			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.informacion"/>
				</tiles:put>
				<tiles:put name="blockContent" value="valoracion.datosEliminacion" type="definition" />
			</tiles:insert>

			<div class="separador8">&nbsp;</div>

			<div class="cabecero_bloque"> <%--cabecero segundo bloque de datos --%>
				<TABLE class="w98m1" height="100%" cellpadding=0 cellspacing=0><TBODY><TR>
				    <TD class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.resultadosBusqueda"/>
					</TD>
				    <TD align="right">
					<%--
						En la pagina llamante habra que hacer una definition con una plantilla de button bar
						(en un principio unicamente la normal (que muestra imagenes y texto en tootip) en la
						que se hace un putList de los botones a mostrar (puede haber en el tiles-defs una lista
						de todos los botones disponibles). La plantilla esa recorre la lista de botones y la pinta y
						finalmente esta pagina situa la button bar aqui en su sitio.
					--%>
						<c:if test="${(elementos != null) && (not empty elementos)}">
							<a class="etiquetaAzul12Bold" href="javascript:incorporarResultados('<c:out value="${mappingBuscarElementos.name}" />');">
								<html:img page="/pages/images/addAllResults.gif"
							        altKey="archigest.archivo.series.seleccion.addAllResults"
							        titleKey="archigest.archivo.series.seleccion.addAllResults"
							        styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.series.seleccion.addAllResults"/></a>
						</c:if>
					</TD>
				</TR></TBODY></TABLE>
			</div> <%--cabecero bloque --%>

			<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>
			<html:form action="/buscarElementos" style="padding:0; margin:0;">
				<input type="hidden" name="method" value="seleccionar">

				<div class="bloque">
					<c:if test="${(elementos != null) && (not empty elementos)}">
						<div class="separador8">&nbsp;</div>
						<table cellpadding=0 cellspacing=0 class="w100">
						  <tr>
						   <td align="right">
								<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].selectedElem);" >
									<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
									<bean:message key="archigest.archivo.selTodos"/>&nbsp;
								</a>&nbsp;
								<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].selectedElem);" >
									<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
									&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
								</a>&nbsp;&nbsp;
						   </td>
						 </tr>
						</table>
					</c:if>

					<display:table name="pageScope.elementos"
						style="width:99%;margin-left:auto;margin-right:auto"
						id="elemento"
						pagesize="0"
						sort="external"
						defaultsort="0"
						requestURI="../../action/buscarElementos?method=buscar"
						export="true"
						decorator="common.view.VisitedRowDecorator">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
						</display:setProperty>

						<display:column style="width:20px" headerClass="addIcon" media="html">
							<html-el:multibox property="selectedElem" value="${elemento.id}" ></html-el:multibox>
						</display:column>
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
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA] != null}">
							<c:set var="campoCodReferencia" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA]}"/>
							<c:choose>
								<c:when test="${campoCodReferencia.mostrarLink == 'S'}">
									<display:column titleKey="archigest.archivo.cf.codReferencia" sortProperty="codReferencia" sortable="true" headerClass="sortable" media="html">
										<c:url var="URL" value="/action/buscarElementos">
											<c:param name="method" value="verEnCuadro" />
											<c:param name="id" value="${elemento.id}" />
										</c:url>
										<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="codReferencia"/></a>
									</display:column>
								</c:when>
								<c:otherwise>
									<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" sortable="true" media="html"/>
								</c:otherwise>
							</c:choose>
							<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" media="csv excel xml pdf"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE] != null}">
							<c:set var="campoNumExp" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE]}"/>
							<c:choose>
								<c:when test="${campoNumExp.mostrarLink == 'S'}">
									<display:column titleKey="archigest.archivo.cf.numExpediente" sortProperty="numexp" sortable="true" headerClass="sortable" media="html">
										<c:url var="URL" value="/action/buscarElementos">
											<c:param name="method" value="verEnCuadro" />
											<c:param name="id" value="${elemento.id}" />
										</c:url>
										<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><bean:write name="elemento" property="numexp"/></a>
									</display:column>
								</c:when>
								<c:otherwise>
									<display:column titleKey="archigest.archivo.cf.numExpediente" property="numexp" sortable="true" media="html"/>
								</c:otherwise>
							</c:choose>
							<display:column titleKey="archigest.archivo.cf.numExpediente" property="numexp" media="csv excel xml pdf"/>
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
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO] != null}">
							<display:column titleKey="archigest.archivo.cf.fondo" property="nombreFondo" headerClass="sortable" style="width:10%;"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS] != null}">
							<display:column titleKey="archigest.archivo.cf.rango" property="nombreRangos" headerClass="sortable" style="width:10%;"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE] != null}">
							<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="html">
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
							<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;"  media="csv excel pdf xml">
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
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL] != null}">
							<display:column titleKey="archigest.archivo.cf.nivel" property="nombre" sortable="true" headerClass="sortable" />
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR] != null}">
							<display:column titleKey="archigest.archivo.cf.productor" property="nombreProductor" headerClass="sortable" />
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
							<display:column titleKey="archigest.archivo.fechaInicial">
								<logic:equal name="elemento" property="formatoFInicial" value="S">S.</logic:equal>
								<bean:write name="elemento" property="valorFInicial"/>
								<logic:present name="elemento" property="calificadorFInicial">
									(<bean:write name="elemento" property="calificadorFInicial"/>)
								</logic:present>
							</display:column>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL] != null}">
							<display:column titleKey="archigest.archivo.fechaFinal">
								<logic:equal name="elemento" property="formatoFFinal" value="S">S.</logic:equal>
								<bean:write name="elemento" property="valorFFinal"/>
								<logic:present name="elemento" property="calificadorFFinal">
									(<bean:write name="elemento" property="calificadorFFinal"/>)
								</logic:present>
							</display:column>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_BLOQUEO] != null}">
							<display:column style="text-align: center;"  media="html">
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
					</display:table>
				</div> <%--bloque --%>
			</html:form>
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>

	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>