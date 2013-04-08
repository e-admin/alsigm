<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<bean:struts id="actionMapping" mapping="/gestionReemplazarValores" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>
<c:set var="fichasSelect" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
<bean:struts id="mappingReemplazo" mapping="/gestionReemplazarValores" />
<script language="javascript">

function reemplazarValores(){
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	document.forms['<c:out value="${mappingReemplazo.name}" />'].submit();
}

</script>




<c:if test="${fichasSelect == null}">
	<c:set var="fichasSelect" value="${requestScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
</c:if>

<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
<bean:define id="sizeCampo" value="80" toScope="request"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<c:set var="camposSelect" value="${sessionScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.descripcion.reemplazo.confirmacion"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:reemplazarValores()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
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
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">

			<tiles:insert page="/pages/tiles/fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>

			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.descripcion.reemplazo.datos.cambio"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table cellpadding="0" cellspacing="0">
					<TR>
						<c:if test="${requestScope.seriesAfectadas=='1'}">
							<TD>
								<script>
									function verSeriesAfectadas() {
										var form = document.forms['<c:out value="${mappingReemplazo.name}" />'];
										form.method.value = "verSeriesAfectadas";
										form.submit();
									}
								</script>
								<a class="etiquetaAzul12Normal" href="javascript:verSeriesAfectadas()">
									<html:img titleKey="archigest.descripcion.reemplazo.verSeriesAfectadas" altKey="archigest.descripcion.reemplazo.verSeriesAfectadas" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
									<bean:message key="archigest.descripcion.reemplazo.verSeriesAfectadas"/>
								</a>
							</TD>
							<TD width="10">&nbsp;</TD>
						</c:if>
						<c:if test="${busquedaReemplazosForm.reemplazoSimple}">
							<TD>
								<script>
									function verElementosAfectados() {
										var form = document.forms['<c:out value="${mappingReemplazo.name}" />'];
										form.method.value = "verElementosAfectados";

									    var form = document.forms['<c:out value="${actionMappingName}" />'];
									    if (window.top.showWorkingDiv) {
									      var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
									      var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
									      var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									      window.top.showWorkingDiv(title, message, message2);
									    }

										form.submit();
									}
								</script>
								<a class="etiquetaAzul12Normal" href="javascript:verElementosAfectados()">
									<html:img titleKey="archigest.descripcion.reemplazo.verElementosAReemplazar" altKey="archigest.descripcion.reemplazo.verElementosAReemplazar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
									<bean:message key="archigest.descripcion.reemplazo.verElementosAReemplazar"/>
								</a>
							</TD>
						</c:if>
					</TR>
				</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div>&nbsp;</div>
				<ul class="warning_ul">
					<c:if test="${requestScope.seriesAfectadas=='1'}">
						<li class="warning_li"><bean:message key="archigest.descripcion.reemplazo.aviso.series.afectadas"/> <bean:message key="archigest.descripcion.reemplazo.confirmacion.seriesAfectadas.mensaje"/></li>
					</c:if>
					<li class="warning_li"><bean:message key="archigest.descripcion.reemplazo.confirmacion.mensaje"/></li>
				</ul>
				<div>&nbsp;</div>
				<table class="formulario" style="width:98%;margin-left:auto;margin-right:auto">
					<c:if test="${not empty busquedaReemplazosForm.nombreObjetoAmbito && busquedaReemplazosForm.nombreObjetoAmbito[0]!=''}">
						<tr>
							<td class="<c:out value="${classTdTituloCampo}"/>" width="200px">
								<bean:message key="archigest.descripcion.reemplazo.ambitos"/>:
							</td>
							<td class="<c:out value="${classTdCampo}"/>">
								<display:table name="busquedaReemplazosForm.nombreObjetoAmbito"
									style="width:98%;"
									id="ambito2"
									pagesize="0"
									sort="external"
									defaultsort="0">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
									</display:setProperty>

									<display:column titleKey="archigest.archivo.descripcion.fichas.form.nombre" >
										<c:out value="${ambito2}"/>
									</display:column>
								</display:table>
							</td>
						</tr>
					</c:if>
					<tr>

						<td class="<c:out value="${classTdTituloCampo}"/>" width="200px">
							<bean:message key="archigest.archivo.cf.ficha"/>:
						</td>
						<td class="<c:out value="${classTdCampo}"/>">
							<c:if test="${busquedaReemplazosForm.idFicha==''}">
								<bean:message key="archigest.descripcion.reemplazo.fichas.todosLosCampos"/>
							</c:if>
							<c:if test="${busquedaReemplazosForm.idFicha!=''}">
								<c:forEach var="ficha" items="${fichasSelect}">
									<c:if test="${ficha.id==busquedaReemplazosForm.idFicha}">
										<c:out value="${ficha.nombre}"/>
									</c:if>
								</c:forEach>
							</c:if>
						</td>
					</tr>
					<c:if test="${not empty busquedaReemplazosForm.tipoCampo && busquedaReemplazosForm.campo[0] != '' }">
						<tr>
							<td class="<c:out value="${classTdTituloCampo}"/>">
								<bean:message key="archigest.archivo.cf.condiciones"/>:
							</td>
							<td class="<c:out value="${classTdCampo}"/>">
								<display:table name="busquedaReemplazosForm.tipoCampo"
									style="width:98%;"
									id="tipo"
									pagesize="0"
									sort="external"
									defaultsort="0">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
									</display:setProperty>

									<display:column>
										<c:out value="${busquedaReemplazosForm.booleano[tipo_rowNum-1]}"/>
									</display:column>
									<display:column>
										<c:out value="${busquedaReemplazosForm.abrirpar[tipo_rowNum-1]}"/>
									</display:column>

									<display:column titleKey="archigest.archivo.campo" >
										<c:forEach var="campo" items="${camposSelect}" varStatus="status">
											<c:if test="${campo.id==busquedaReemplazosForm.campo[tipo_rowNum-1]}">
												<c:out value="${campo.nombre}"/>
											</c:if>
										</c:forEach>
									</display:column>
									<display:column titleKey="archigest.archivo.descripcion.operador" >
										<c:set var="oper" value="${busquedaReemplazosForm.operador[tipo_rowNum-1]}"/>
										<c:choose>
											<c:when test="${oper == '='}">
												<bean:message key="archigest.archivo.simbolo.igual.texto" />
											</c:when>
											<c:when test="${oper == '<'}">
												<bean:message key="archigest.archivo.simbolo.menor.texto" />
											</c:when>
											<c:when test="${oper == '<='}">
												<bean:message key="archigest.archivo.simbolo.menoroigual.texto" />
											</c:when>
											<c:when test="${oper == '>'}">
												<bean:message key="archigest.archivo.simbolo.mayor.texto" />
											</c:when>
											<c:when test="${oper == '>='}">
												<bean:message key="archigest.archivo.simbolo.mayoroigual.texto" />
											</c:when>
											<c:when test="${oper == '[..]'}">
												<bean:message key="archigest.archivo.simbolo.rango" />
											</c:when>
											<c:when test="${oper == 'QC'}">
												<bean:message key="archigest.archivo.simbolo.quecontenga" />
											</c:when>
											<c:when test="${oper == 'QCC'}">
												<bean:message key="archigest.archivo.simbolo.quecomiencecon" />
											</c:when>
											<c:when test="${oper == 'QCN'}">
												<bean:message key="archigest.archivo.simbolo.quecontengan" />
											</c:when>
											<c:when test="${oper == 'EX'}">
												<bean:message key="archigest.archivo.simbolo.exacta" />
											</c:when>
											<c:otherwise>=</c:otherwise>
										</c:choose>
									</display:column>
									<c:if test="${tipo=='3' && busquedaReemplazosForm.formatoFecha1[tipo_rowNum-1] != null}">
										<display:column titleKey="auditoria.detalles.DESCRIPCION_FORMATO" >
											<c:out value="${busquedaReemplazosForm.formatoFecha1[tipo_rowNum-1]}"/>
										</display:column>
									</c:if>
									<display:column titleKey="archigest.archivo.descripcion.textoTablasValidacion.valor" >
										<c:choose>
											<c:when test="${tipo=='1'}"> <%-- texto --%>
												<c:out value="${busquedaReemplazosForm.valor1[tipo_rowNum-1]}"/>
											</c:when>
											<c:when test="${tipo=='2'}"> <%-- texto largo --%>
												<c:out value="${busquedaReemplazosForm.valor1[tipo_rowNum-1]}"/>
											</c:when>
											<c:when test="${tipo=='3'}"> <%-- fecha --%>
												<c:choose>
													<c:when test="${busquedaReemplazosForm.formatoFecha1[tipo_rowNum-1]== 'DDMMAAAA'}">
														<c:out value="${busquedaReemplazosForm.valor1D[tipo_rowNum-1]}"/> -
														<c:out value="${busquedaReemplazosForm.valor1M[tipo_rowNum-1]}"/> -
														<c:out value="${busquedaReemplazosForm.valor1A[tipo_rowNum-1]}"/>
													</c:when>
													<c:when test="${busquedaReemplazosForm.formatoFecha1[tipo_rowNum-1]== 'MMAAAA'}">
														<c:out value="${busquedaReemplazosForm.valor1M[tipo_rowNum-1]}"/> -
														<c:out value="${busquedaReemplazosForm.valor1A[tipo_rowNum-1]}"/>
													</c:when>
													<c:when test="${busquedaReemplazosForm.formatoFecha1[tipo_rowNum-1]== 'AAAA'}">
														<c:out value="${busquedaReemplazosForm.valor1A[tipo_rowNum-1]}"/>
													</c:when>
													<c:when test="${busquedaReemplazosForm.formatoFecha1[tipo_rowNum-1]== 'S'}">
														<c:out value="${busquedaReemplazosForm.valor1S[tipo_rowNum-1]}"/>
													</c:when>
												</c:choose>
											</c:when>
											<c:when test="${tipo=='4'}"> <%-- numerico --%>
												<c:out value="${busquedaReemplazosForm.valor1[tipo_rowNum-1]}"/>
											</c:when>
											<c:when test="${tipo=='5'}"> <%-- referencia --%>
												<c:out value="${busquedaReemplazosForm.nombreDesc1[tipo_rowNum-1]}"/>
											</c:when>
										</c:choose>
									</display:column>
									<display:column>
										<c:out value="${busquedaReemplazosForm.cerrarpar[tipo_rowNum-1]}"/>
									</display:column>
								</display:table>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="<c:out value="${classTdTituloCampo}"/>">
							<bean:message key="archigest.descripcion.reemplazo.campo.cambio"/>:
						</td>
						<td class="<c:out value="${classTdCampo}"/>">
							<c:forEach var="campo" items="${camposSelect}" varStatus="status">
								<c:if test="${!busquedaReemplazosForm.reemplazoSimple}">
									<c:if test="${campo.id==busquedaReemplazosForm.campoCambio}">
										<c:out value="${campo.nombre}"/>
									</c:if>
								</c:if>
								<c:if test="${busquedaReemplazosForm.reemplazoSimple}">
									<c:if test="${campo.id==busquedaReemplazosForm.campo[0]}">
										<c:out value="${campo.nombre}"/>
									</c:if>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<c:if test="${!busquedaReemplazosForm.reemplazoSimple && busquedaReemplazosForm.reemplazoValoresParciales}">
					<tr>
						<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" nowrap="nowrap">
							<bean:message key="archigest.archivo.reemplazar.forma.reemplazo"/>:&nbsp;
						</td>
						<td class="<c:out value="${classTdCampo}"/>" id="valorFormaReemplazo" nowrap="nowrap" style="border-bottom:0">
							<fmt:message key="archigest.archivo.forma.reemplazo.${busquedaReemplazosForm.formaReemplazo}"/>
						</td>
					</tr>
					</c:if>
					<c:if test="${busquedaReemplazosForm.reemplazoSimple}">
						<tr id="trValorActual">
							<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" id="etiquetaValorBuscar" nowrap="nowrap">
								<bean:message key="archigest.descripcion.reemplazo.valor.actual"/>:&nbsp;
								<html:hidden name="busquedaReemplazosForm" property="valorReemplazoParcial"/>
							</td>
							<!--<c:choose>
								<c:when test="${busquedaReemplazosForm.reemplazoValoresParciales}">
									<td class="<c:out value="${classTdCampo}"/>" id="valorBuscar" nowrap="nowrap" style="border-bottom:0">
											<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
											<c:out value="${busquedaReemplazosForm.valorReemplazoParcial}"/>
											<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
									</td>
								</c:when>
								<c:otherwise>
									<td class="<c:out value="${classTdCampo}"/>" id="valorBuscar" nowrap="nowrap" style="border-bottom:0">
										<c:out value="${busquedaReemplazosForm.nombreDesc1[0]}"/>
									</td>
								</c:otherwise>
							</c:choose>-->
							<c:set var="tipoCambio" value="${busquedaReemplazosForm.tipoCampoCambio}"/>
							<td class="<c:out value="${classTdCampo}"/>" id="valorBuscar" nowrap="nowrap" style="border-bottom:0">
								<c:choose>
									<c:when test="${tipoCambio=='1'}"> <%-- texto --%>
										<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
											<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
										</c:if>
										<c:out value="${busquedaReemplazosForm.valor1[0]}"/>
										<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
											<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
										</c:if>
									</c:when>
									<c:when test="${tipoCambio=='2'}"> <%-- texto largo --%>
										<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
											<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
										</c:if>
										<c:out value="${busquedaReemplazosForm.valor1[0]}"/>
										<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
											<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
										</c:if>
									</c:when>
									<c:when test="${tipoCambio=='3'}"> <%-- fecha --%>
										<c:choose>
											<c:when test="${busquedaReemplazosForm.formatoFecha1[0] == 'DDMMAAAA'}">
												<c:out value="${busquedaReemplazosForm.valor1D[0]}"/> -
												<c:out value="${busquedaReemplazosForm.valor1M[0]}"/> -
												<c:out value="${busquedaReemplazosForm.valor1A[0]}"/>
											</c:when>
											<c:when test="${busquedaReemplazosForm.formatoFecha1[0] == 'MMAAAA'}">
												<c:out value="${busquedaReemplazosForm.valor1M[0]}"/> -
												<c:out value="${busquedaReemplazosForm.valor1A[0]}"/>
											</c:when>
											<c:when test="${busquedaReemplazosForm.formatoFecha1[0] == 'AAAA'}">
												<c:out value="${busquedaReemplazosForm.valor1A[0]}"/>
											</c:when>
											<c:when test="${busquedaReemplazosForm.formatoFecha1[0] == 'S'}">
												<c:out value="${busquedaReemplazosForm.valor1S[0]}"/>
											</c:when>
										</c:choose>
									</c:when>
									<c:when test="${tipoCambio=='4'}"> <%-- numerico --%>
										<c:out value="${busquedaReemplazosForm.valor1[0]}"/>
									</c:when>
									<c:when test="${tipoCambio=='5'}"> <%-- referencia --%>
										<c:out value="${busquedaReemplazosForm.nombreDesc1[0]}"/>
									</c:when>
								</c:choose>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="<c:out value="${classTdTituloCampo}"/>">
							<bean:message key="archigest.descripcion.reemplazo.valor.nuevo"/>:
						</td>
						<c:set var="tipoCambio" value="${busquedaReemplazosForm.tipoCampoCambio}"/>
						<td class="<c:out value="${classTdCampo}"/>">
							<c:choose>
								<c:when test="${tipoCambio=='1'}"> <%-- texto --%>
									<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
										<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
									</c:if>
									<c:out value="${busquedaReemplazosForm.valor3}"/>
									<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
										<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
									</c:if>
								</c:when>
								<c:when test="${tipoCambio=='2'}"> <%-- texto largo --%>
									<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
										<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
									</c:if>
									<c:out value="${busquedaReemplazosForm.valor3}"/>
									<c:if test="${busquedaReemplazosForm.reemplazoValoresParciales}">
										<html:img page="/pages/images/puntos_suspensivos.gif" styleClass="imgTextBottom" />
									</c:if>
								</c:when>
								<c:when test="${tipoCambio=='3'}"> <%-- fecha --%>
									<c:choose>
										<c:when test="${busquedaReemplazosForm.formatoFecha3== 'DDMMAAAA'}">
											<c:out value="${busquedaReemplazosForm.valor3D}"/> -
											<c:out value="${busquedaReemplazosForm.valor3M}"/> -
											<c:out value="${busquedaReemplazosForm.valor3A}"/>
										</c:when>
										<c:when test="${busquedaReemplazosForm.formatoFecha3== 'MMAAAA'}">
											<c:out value="${busquedaReemplazosForm.valor3M}"/> -
											<c:out value="${busquedaReemplazosForm.valor3A}"/>
										</c:when>
										<c:when test="${busquedaReemplazosForm.formatoFecha3 == 'AAAA'}">
											<c:out value="${busquedaReemplazosForm.valor3A}"/>
										</c:when>
										<c:when test="${busquedaReemplazosForm.formatoFecha3 == 'S'}">
											<c:out value="${busquedaReemplazosForm.valor3S}"/>
										</c:when>
									</c:choose>
								</c:when>
								<c:when test="${tipoCambio=='4'}"> <%-- numerico --%>
									<c:out value="${busquedaReemplazosForm.valor3}"/>
								</c:when>
								<c:when test="${tipoCambio=='5'}"> <%-- referencia --%>
									<c:out value="${busquedaReemplazosForm.nombreDesc3}"/>
								</c:when>
							</c:choose>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<c:if test="${!busquedaReemplazosForm.reemplazoSimple}">
			<div style="height:10px">&nbsp;</div>
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.descripcion.reemplazo.simple.listadoElementosAfectados"/></tiles:put>
				<tiles:put name="blockContent" direct="true">
					<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>
					<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

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

			<div id="capaConScroll" class="bloqueConScroll">
				<div style="height:10px">&nbsp;</div>
				<display:table name="pageScope.elementos"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="elemento"
					sort="external"
					defaultsort="0"
					requestURI="/action/gestionReemplazarValores?method=checkReemplazo"
					export="true"
					excludedParams="method"
					decorator="common.view.VisitedRowDecorator">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
					</display:setProperty>
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

			<c:set var="masDeDiecinueveFilas" value="0"/>
				<c:if test="${numElementos>19}">
					<c:set var="masDeDiecinueveFilas" value="1"/>
				</c:if>

			<c:if test="${masDeDiecinueveFilas<1}">
				<SCRIPT>
					var capaConScroll=document.getElementById("capaConScroll");
					capaConScroll.className="";
				</SCRIPT>
			</c:if>

				</tiles:put>
			</tiles:insert>
		</c:if>
	</tiles:put>
</tiles:insert>
<div style="height:10px"></div>

		<html:form action="/gestionReemplazarValores">
			<input type="hidden" name="method" value="reemplazar"/>
			<html:hidden property="formaReemplazo"/>
			<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_FONDOS_AVANZADA}"/>"/>
			<c:forEach var="nodo" items="${busquedaReemplazosForm.idObjetoAmbito}" varStatus="status">
				<input type="hidden" name="idObjetoAmbito" value="<c:out value="${nodo}"/>"/>
				<input type="hidden" name="nombreObjetoAmbito" value="<c:out value="${busquedaReemplazosForm.nombreObjetoAmbito[status.index]}"/>"/>
			</c:forEach>
			<html:hidden name="busquedaReemplazosForm" property="idFicha" />
			<input type='hidden' name='formatoFechaSel1' value='0'/>
			<input type='hidden' name='formatoFechaSel2' value='0'/>
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

			<html:hidden name="busquedaReemplazosForm" property="nombreDesc2" />
			<html:hidden name="busquedaReemplazosForm" property="nombreDesc3" />
			<html:hidden property="valorReemplazoParcial" styleId="valorReemplazoParcial"/>

			<html:hidden name="busquedaReemplazosForm" property="campoCambio"/>
			<html:hidden name="busquedaReemplazosForm" property="tipoCampoCambio"/>
			<html:hidden name="busquedaReemplazosForm" property="reemplazoSimple"/>

			<html:hidden name="busquedaReemplazosForm" property="formatoFecha3"/>
			<html:hidden name="busquedaReemplazosForm" property="valor3"/>
			<html:hidden name="busquedaReemplazosForm" property="valor3D"/>
			<html:hidden name="busquedaReemplazosForm" property="valor3M"/>
			<html:hidden name="busquedaReemplazosForm" property="valor3A"/>

			<html:hidden name="busquedaReemplazosForm" property="tipoReferencia"/>

			<html:hidden name="busquedaReemplazosForm" property="formatoFecha4"/>
			<html:hidden name="busquedaReemplazosForm" property="valor4"/>
			<html:hidden name="busquedaReemplazosForm" property="valor4D"/>
			<html:hidden name="busquedaReemplazosForm" property="valor4M"/>
			<html:hidden name="busquedaReemplazosForm" property="valor4A"/>
			<html:hidden name="busquedaReemplazosForm" property="valor4S"/>
			<html:hidden name="busquedaReemplazosForm" property="nombreDesc4"/>

			<c:if test="${busquedaReemplazosForm.niveles!=null}">
				<c:forEach var="nivel" items="${busquedaReemplazosForm.niveles}">
					<input type="hidden"  name="niveles" value="<c:out value="${nivel}"/>"/>
				</c:forEach>
			</c:if>

			<c:if test="${!busquedaReemplazosForm.reemplazoSimple}">
				<c:forEach var="idElemento" items="${busquedaReemplazosForm.selectedIds}" varStatus="status">
					<input type="hidden"  name="selectedIds" value="<c:out value="${busquedaReemplazosForm.selectedIds[status.index]}"/>"/>
				</c:forEach>
			</c:if>
		</html:form>