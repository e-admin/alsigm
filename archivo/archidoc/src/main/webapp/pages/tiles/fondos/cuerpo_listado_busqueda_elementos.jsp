<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<c:set var="mostrarLinkDocumentos" value="false"/>
<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_DOCUMENTOS_ELECTRONICOS] != null}">
	<security:permissions action="${appConstants.documentosActions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
		<c:set var="mostrarLinkDocumentos" value="true"/>
	</security:permissions>
</c:if>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>



<c:set var="accionesPermitidas" value="${sessionScope[appConstants.common.LISTA_ACCIONES_BUSQUEDA_KEY]}"/>

<script language="javascript" type="text/javascript">

function ejecutar(){
		if(document.getElementById("accion").value == ""){
			alert('<fmt:message key="error.busquedas.accion.obligatoria"/>');
		}
		else {
			var frm = document.getElementById("formulario");
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

</script>
<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_DOCUMENTOS_ELECTRONICOS] != null}">
<tiles:insert page="/pages/tiles/documentos/script_funciones_acceso_documentos.jsp"></tiles:insert>
</c:if>


<html:form action="/resultadoBusquedaFondosAction" styleId="formulario">
<input type="hidden" name="method" id="method" value="ejecutarAccion"/>
<div id="contenedor_ficha">
	<div class="ficha">
		<h1><span>
		<div class="w100">
		<table class="w98" cellpadding="0" cellspacing="0">
		<tr>
			<td class="etiquetaAzul12Bold" height="25px">
				<bean:message key="archigest.archivo.cf.busqueda.listado.caption"/>
			</td>
			<td align="right">
				<tiles:insert definition="button.closeButton" flush="true" />
			</td>
		</tr>
		</table>
		</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
			<div id="barra_errores"><archivo:errors /></div>
			<div class="separador1">&nbsp;</div>

			<div class="bloque">
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
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA] != null}">
						<c:set var="campoCodReferencia" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA]}"/>
						<c:set var="codigoReferencia" value="${elemento.codReferencia}"/>
						<c:choose>
							<c:when test="${campoCodReferencia.mostrarLink == 'S'}">
								<display:column titleKey="archigest.archivo.cf.codReferencia" sortProperty="codReferencia" sortable="true" headerClass="sortable" media="html">
								<c:if test="${campoCodReferencia.abreviado == 'S'}">
										<c:if test="${elemento.codReferencia != elemento.codReferenciaAbreviado}">
											<span title='<c:out value="${elemento.codReferencia}"/>'><html:img page="/pages/images/abreviado.gif" styleClass="imgTextMiddle"/></span>
										</c:if>
										<c:set var="codigoReferencia" value="${elemento.codReferenciaAbreviado}"/>
								</c:if>
									<c:url var="URL" value="/action/buscarElementos">
										<c:param name="method" value="verEnCuadro" />
										<c:param name="id" value="${elemento.id}" />
									</c:url>
									<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />">
										<c:out value="${codigoReferencia}"/>
									</a>
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column titleKey="archigest.archivo.cf.codReferencia" sortable="true" sortProperty="codReferencia" headerClass="sortable"  media="html" >
									<c:if test="${campoCodReferencia.abreviado == 'S'}">
										<c:if test="${elemento.codReferencia != elemento.codReferenciaAbreviado}">
											<span title='<c:out value="${elemento.codReferencia}"/>'><html:img page="/pages/images/abreviado.gif" styleClass="imgTextMiddle"/></span>
										</c:if>
										<c:set var="codigoReferencia" value="${elemento.codReferenciaAbreviado}"/>
									</c:if>
									<c:out value="${codigoReferencia}"/>
								</display:column>
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
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA] != null}">
						<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc"  sortable="true" headerClass="sortable" property="signaturaudoc" sortProperty="signaturaudoc"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO] != null}">
						<display:column titleKey="archigest.archivo.cf.fondo" property="nombreFondo" headerClass="sortable" style="width:10%;"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS] != null}">
						<display:column titleKey="archigest.archivo.cf.rango" property="nombreRangos" headerClass="sortable" style="width:10%;"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE] != null}">
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

					<c:if test="${mostrarLinkDocumentos}">
						<display:column titleKey="archigest.archivo.columna.documentos" sortable="true" headerClass="sortable" media="html">
							<tiles:insert page="/pages/tiles/documentos/columna_acceso_documentos.jsp" >
								<tiles:put name="idElemento" direct="true"><c:out value="${elemento.id}"/></tiles:put>
								<tiles:put name="numDocumentos"><c:out value="${elemento.numDocumentosElectronicos}"/></tiles:put>
							</tiles:insert>
						</display:column>
					</c:if>
				</display:table>
			</div> <%--bloque --%>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>

	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
</html:form>