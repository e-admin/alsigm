<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionReemplazarValores" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<tiles:insert page="/pages/tiles/fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>
<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.descripcion.reemplazo.avanzado"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<TR>
				<c:if test="${pageScope.elementos!=null}">
				    <TD>
						<bean:struts id="mappingReemplazo" mapping="/gestionReemplazarValores" />
						<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingReemplazo.name}" />'].submit();" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>

				<TD>
					<c:url var="cancelURI" value="/action/gestionReemplazarValores">
						<c:param name="method" value="goBack"  />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'" >
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/gestionReemplazarValores">
				<input type="hidden" name="method" value="checkReemplazo"/>
				<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_FONDOS_AVANZADA}"/>"/>

				<html:hidden name="busquedaReemplazosForm" property="idFicha" />
				<input type='hidden' size='1' name='formatoFechaSel1' value='0'/>
				<input type='hidden' size='1' name='formatoFechaSel2' value='0'/>
				<c:if test="${busquedaReemplazosForm.tipoCampo[0]==null}">
					<input type="hidden" name="booleano" value="<c:out value="${busquedaReemplazosForm.booleano[status.index]}"/>"/>
						<input type="hidden" name="abrirpar" value="<c:out value="${busquedaReemplazosForm.abrirpar[status.index]}"/>"/>
						<input type="hidden"  name="campo" value="<c:out value="${busquedaReemplazosForm.campo[status.index]}"/>"/>
						<input type="hidden"  name="cerrarpar" value="<c:out value="${busquedaReemplazosForm.cerrarpar[status.index]}"/>"/>
						<input type="hidden"  name="tipoCampo" value="<c:out value="${nodo}"/>"/>
						<input type="hidden"  name="operador" value="<c:out value="${busquedaReemplazosForm.operador[status.index]}"/>"/>
						<input type="hidden"  name="formatoFecha1" value="<c:out value="${busquedaReemplazosForm.formatoFecha1[status.index]}"/>"/>
						<input type="hidden"  name="valor1" value="<c:out value="${busquedaReemplazosForm.valor1[status.index]}"/>"/>
						<input type="hidden"  name="valor1D" value="<c:out value="${busquedaReemplazosForm.valor1D[status.index]}"/>"/>
						<input type="hidden"  name="valor1M" value="<c:out value="${busquedaReemplazosForm.valor1M[status.index]}"/>"/>
						<input type="hidden"  name="valor1A" value="<c:out value="${busquedaReemplazosForm.valor1A[status.index]}"/>"/>
						<input type="hidden"  name="valor1S" value="<c:out value="${busquedaReemplazosForm.valor1S[status.index]}"/>"/>
						<input type="hidden"  name="nombreDesc1" value="<c:out value="${busquedaReemplazosForm.nombreDesc1[status.index]}"/>"/>
						<input type="hidden"  name="formatoFecha2" value="<c:out value="${busquedaReemplazosForm.formatoFecha2[status.index]}"/>"/>
						<input type="hidden"  name="valor2" value="<c:out value="${busquedaReemplazosForm.valor2[status.index]}"/>"/>
						<input type="hidden"  name="valor2D" value="<c:out value="${busquedaReemplazosForm.valor2D[status.index]}"/>"/>
						<input type="hidden"  name="valor2M" value="<c:out value="${busquedaReemplazosForm.valor2M[status.index]}"/>"/>
						<input type="hidden"  name="valor2A" value="<c:out value="${busquedaReemplazosForm.valor2A[status.index]}"/>"/>
						<input type="hidden"  name="valor2S" value="<c:out value="${busquedaReemplazosForm.valor2S[status.index]}"/>"/>
						<input type="hidden"  name="tiposReferencia" value="<c:out value="${busquedaReemplazosForm.tiposReferencia[status.index]}"/>"/>
				</c:if>
				<c:if test="${busquedaReemplazosForm.tipoCampo[0]!=null}">
					<c:forEach var="nodo" items="${busquedaReemplazosForm.tipoCampo}" varStatus="status">
						<input type="hidden" name="booleano" value="<c:out value="${busquedaReemplazosForm.booleano[status.index]}"/>"/>
						<input type="hidden" name="abrirpar" value="<c:out value="${busquedaReemplazosForm.abrirpar[status.index]}"/>"/>
						<input type="hidden"  name="campo" value="<c:out value="${busquedaReemplazosForm.campo[status.index]}"/>"/>
						<input type="hidden"  name="cerrarpar" value="<c:out value="${busquedaReemplazosForm.cerrarpar[status.index]}"/>"/>
						<input type="hidden"  name="tipoCampo" value="<c:out value="${nodo}"/>"/>
						<input type="hidden"  name="operador" value="<c:out value="${busquedaReemplazosForm.operador[status.index]}"/>"/>
						<input type="hidden"  name="formatoFecha1" value="<c:out value="${busquedaReemplazosForm.formatoFecha1[status.index]}"/>"/>
						<input type="hidden"  name="valor1" value="<c:out value="${busquedaReemplazosForm.valor1[status.index]}"/>"/>
						<input type="hidden"  name="valor1D" value="<c:out value="${busquedaReemplazosForm.valor1D[status.index]}"/>"/>
						<input type="hidden"  name="valor1M" value="<c:out value="${busquedaReemplazosForm.valor1M[status.index]}"/>"/>
						<input type="hidden"  name="valor1A" value="<c:out value="${busquedaReemplazosForm.valor1A[status.index]}"/>"/>
						<input type="hidden"  name="valor1S" value="<c:out value="${busquedaReemplazosForm.valor1S[status.index]}"/>"/>
						<input type="hidden"  name="nombreDesc1" value="<c:out value="${busquedaReemplazosForm.nombreDesc1[status.index]}"/>"/>
						<input type="hidden"  name="formatoFecha2" value="<c:out value="${busquedaReemplazosForm.formatoFecha2[status.index]}"/>"/>
						<input type="hidden"  name="valor2" value="<c:out value="${busquedaReemplazosForm.valor2[status.index]}"/>"/>
						<input type="hidden"  name="valor2D" value="<c:out value="${busquedaReemplazosForm.valor2D[status.index]}"/>"/>
						<input type="hidden"  name="valor2M" value="<c:out value="${busquedaReemplazosForm.valor2M[status.index]}"/>"/>
						<input type="hidden"  name="valor2A" value="<c:out value="${busquedaReemplazosForm.valor2A[status.index]}"/>"/>
						<input type="hidden"  name="valor2S" value="<c:out value="${busquedaReemplazosForm.valor2S[status.index]}"/>"/>
						<input type="hidden"  name="tiposReferencia" value="<c:out value="${busquedaReemplazosForm.tiposReferencia[status.index]}"/>"/>
					</c:forEach>
				</c:if>

				<html:hidden name="busquedaReemplazosForm" property="nombreDesc2" />

				<c:if test="${pageScope.elementos!=null}">
					<div style="height:20px">&nbsp;</div>
					<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
						<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.descripcion.reemplazo.datos.cambio"/></tiles:put>
						<tiles:put name="blockContent" direct="true">
							<table class="formulario">
								<bean:define id="methodLoadFicha" value="cargarCamposReemplazoAvanzado" toScope="request"/>

								<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
								<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
								<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
								<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
								<bean:define id="sizeCampo" value="80" toScope="request"/>

								<tiles:insert page="../descripcion/campo_reemplazo_avanzado.jsp" flush="true"/>
							</table>
						</tiles:put>
					</tiles:insert>
				</c:if>



				<c:if test="${pageScope.elementos!=null}">
					<table class="formulario">
						<tr>
							<td align="right">
								<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].selectedIds);"
					 			><html:img page="/pages/images/checked.gif"
									    border="0"
									    altKey="archigest.archivo.selTodos"
									    titleKey="archigest.archivo.selTodos"
									    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
								&nbsp;
								<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].selectedIds);"
					 			><html:img page="/pages/images/check.gif"
									    border="0"
									    altKey="archigest.archivo.quitarSel"
									    titleKey="archigest.archivo.quitarSel"
									    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
								&nbsp;&nbsp;
						   </td>
						</tr>
					</table>
				</c:if>

				<c:set var="numElementos" value="${sessionScope[appConstants.fondos.NUM_RESULTADOS_LISTA_ELEMENTOS_CF]}"/>
				<c:if test="${!empty numElementos}">
					<table class="tablaAncho99">
						<tr class="filaAlineadaDerecha">
							<td class="etiquetaAzul11Bold">
								<c:out value="${numElementos}"/>&nbsp;
								<bean:message key="archigest.archivo.fondos.busqueda.elementos"/>
							</td>
						</tr>
					</table>
				</c:if>

				<div id="capaConScroll" class="bloqueConScroll" style="height:300px">
					<div style="height:10px">&nbsp;</div>
					<display:table name="pageScope.elementos" htmlId="tablaElementosAfectados"
						style="width:99%;margin-left:auto;margin-right:auto"
						id="elemento"
						sort="external"
						defaultsort="0"
						requestURI="../../action/gestionReemplazarValores?method=verElementosAfectados"
						excludedParams="method"
						export="true"
						decorator="common.view.VisitedRowDecorator">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
						</display:setProperty>
						<display:column style="width:20px" media="html">
							<c:set var="checkBoxSinPoner" value="1"/>
							<c:forEach var="identificador" items="${busquedaReemplazosForm.selectedIds}" varStatus="status">
								<c:if test="${identificador == elemento.id}">
									<input type="checkbox" name="selectedIds" value="<c:out value="${elemento.id}"/>" checked/>
									<c:set var="checkBoxSinPoner" value="0"/>
								</c:if>
							</c:forEach>
							<c:if test="${checkBoxSinPoner==1}">
								<input type="checkbox" name="selectedIds" value="<c:out value="${elemento.id}"/>"/>
							</c:if>
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
						<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="csv excel pdf xml">
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
				</div>
				<div style="height:10px">&nbsp;</div>

				<c:if test="${pageScope.numElementos<10}">
					<SCRIPT>
						var capaConScroll=document.getElementById("capaConScroll");
						capaConScroll.className="";
					</SCRIPT>
				</c:if>

				<c:forEach var="nodo" items="${busquedaReemplazosForm.idObjetoAmbito}" varStatus="status">
					<input type="hidden" name="idObjetoAmbito" value="<c:out value="${nodo}"/>"/>
					<input type="hidden" name="nombreObjetoAmbito" value="<c:out value="${busquedaReemplazosForm.nombreObjetoAmbito[status.index]}"/>"/>
				</c:forEach>

			</html:form>
		</div> <%-- bloque --%>
	</tiles:put>
</tiles:insert>