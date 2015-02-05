<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="serieDestino" value="${sessionScope[appConstants.fondos.SERIE_DESTINO]}"/>
<c:set var="listaUdocsAMover" value="${sessionScope[appConstants.fondos.UDOCS_A_MOVER]}"/>
<c:set var="serieOrigen" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>
<c:set var="movimientoFinalizado" value="${sessionScope[appConstants.fondos.MOVIMIENTO_FINALIZADO_KEY]}"/>
<c:set var="listasControlAcceso" value="${sessionScope[appConstants.fondos.LISTAS_CONTROL_ACCESO_PRODUCTORES_KEY]}"/>
<c:set var="nombreListaAcceso" value="${sessionScope[appConstants.fondos.NOMBRE_LISTA_ACCESO_KEY]}"/>

<bean:struts id="mapping" mapping="/moverUdocsAction" />

<div id="contenedor_ficha">

	<c:if test="${!movimientoFinalizado}">
	<script language="JavaScript1.2" type="text/JavaScript">
	function goOn()
	{

		if(window.confirm('<bean:message key="archigest.archivo.serie.aceptarMoverUdocs.msg"/>')){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.serie.moverUdocs"/>';
				var message = '<bean:message key="archigest.archivo.serie.msgMoverUdocs"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}

			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.method.value="aceptarMoverUdocs";
			form.submit();
		}
	}

	function checkAsignacionNuevaLCA(elemento,divLista){
		if(elemento.checked){
			xDisplay(divLista,"inline");
		}
		else{
			xDisplay(divLista,"none");
		}
	}
	</script>
	</c:if>
	<html:form action="/moverUdocsAction">
	<input type="hidden" name="method" value="aceptarMoverUdocs"/>

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
						<c:choose>
							<c:when test="${movimientoFinalizado}">
								<fmt:message key="archigest.archivo.informe.movimiento.udocs.entre.series"/>
							</c:when>
							<c:otherwise>
					    		<fmt:message key="archigest.archivo.cf.moverUdocs"/>
							</c:otherwise>
						</c:choose>
				    	</td>
				    	<td align="right" height="25px">
							<table cellpadding=0 cellspacing=0>
								<c:choose>
								<c:when test="${movimientoFinalizado}">
	 							 <tr>
								   	<!-- td>
										<c:url var="informeURL" value="/action/informeMoverUdocs"/>
										<a class="etiquetaAzul12Bold" href="<c:out value="${informeURL}" escapeXml="false"/>">
											<html:img page="/pages/images/documentos/doc_pdf.gif"
										        altKey="archigest.archivo.informe"
										        titleKey="archigest.archivo.informe"
										        styleClass="imgTextMiddle"/>
											<bean:message key="archigest.archivo.informe"/>
										</a>
									</td>
									<td width="10">&nbsp;</td-->
									<TD>
										<tiles:insert definition="button.closeButton" flush="true">
											<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
										</tiles:insert>
								   	</TD>
								</tr>
								</c:when>
								<c:otherwise>
	 							 <tr>
								   	<td>
										<c:url var="informeURL" value="/action/informeMoverUdocs"/>
										<a class="etiquetaAzul12Bold" href="<c:out value="${informeURL}" escapeXml="false"/>">
											<html:img page="/pages/images/documentos/doc_pdf.gif"
										        altKey="archigest.archivo.informe"
										        titleKey="archigest.archivo.informe"
										        styleClass="imgTextMiddle"/>
											<bean:message key="archigest.archivo.informe"/>
										</a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<tiles:insert definition="button.closeButton" flush="true">
											<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
											<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
										</tiles:insert>
								   	</td>
									<td width="10">&nbsp;</td>
								   	<td>
				   						<a class="etiquetaAzul12Bold"
											href="javascript:goOn()">
											<html:img page="/pages/images/Ok_Si.gif"
											titleKey="archigest.archivo.aceptar"
											altKey="archigest.archivo.aceptar"
											styleClass="imgTextMiddle"/>
											&nbsp;<bean:message key="archigest.archivo.aceptar"/>
										</a>
									</td>
									<td width="10">&nbsp;</td>
									<TD>
										<c:url var="cancelURL" value="/action/moverUdocsAction">
											<c:param name="method" value="goReturnPoint" />
										</c:url>
										<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
										<html:img page="/pages/images/Ok_No.gif"
											altKey="archigest.archivo.cancelar"
											titleKey="archigest.archivo.cancelar"
											styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
								   	</TD>
								</tr>
								</c:otherwise>
							 </c:choose>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors/></div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
						<fmt:message key="archigest.archivo.cf.seriesMoverUDocs"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" nowrap="nowrap" height="20px" width="250px">
							<fmt:message key="archigest.archivo.cf.serieOrigen"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serieOrigen.codReferencia}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" nowrap="nowrap" height="20px">
							<fmt:message key="archigest.archivo.cf.serieDestino"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serieDestino.codReferencia}" />
						</td>
					</tr>


					<c:choose>
						<c:when test="${movimientoFinalizado}" >
							<c:if test="${MoverUdocsForm.asignarNuevaLCA}">
								<tr id="trListaControlAcceso">
									<td class="tdTitulo" nowrap="nowrap" height="20px">
										<bean:message key="archigest.archivo.mover.udocs.nueva.lista.acceso"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<c:out value="${nombreListaAcceso}"/>
									</td>
								</tr>
							</c:if>
						</c:when>
						<c:otherwise>
							<tr id="trListaControlAcceso">
								<td class="tdTitulo" nowrap="nowrap" height="25px" >
									<bean:message key="archigest.archivo.mover.udocs.nueva.lista.acceso"/>:&nbsp;
								</td>
								<td class="tdDatosBold">
									<html:checkbox property="asignarNuevaLCA" styleId="asignarNuevaLCA" styleClass="checkbox" onclick="checkAsignacionNuevaLCA(this,'divIdLCA')"></html:checkbox>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span id="divIdLCA" <c:if test="${!MoverUdocsForm.asignarNuevaLCA}">style="display:none;"</c:if>>
										<display:table name="pageScope.listasControlAcceso"
											style="width:98%;margin:6px 2px 0px 6px;"
											id="lca"
											requestURI="../../action/moverUdocsAction"
											size="1">
											<display:column>
												<input type="radio" class="radio" name="idLCA" value="<c:out value="${lca.id}"/>"
												<c:if test="${MoverUdocsForm.idLCA==lca.id}">checked</c:if>
												/>
											</display:column>
											<display:column titleKey="archigest.archivo.nombre">
												<c:out value="${lca.nombre}"/>
											</display:column>
										</display:table>
									</span>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				</tiles:put>
			</tiles:insert>

			<div class="separador5">&nbsp;</div>

			<c:if test="${movimientoFinalizado && !empty listaUdocsAMover}">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<c:choose>
						<c:when test="${movimientoFinalizado}">
							<fmt:message key="archigest.archivo.cf.listaDeUdocsSeleccionadasMovidas"/>
						</c:when>
						<c:otherwise>
				    		<fmt:message key="archigest.archivo.cf.listaDeUdocsSeleccionadasParaMover"/>
						</c:otherwise>
					</c:choose>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
				<div style="padding-top:6px;padding-bottom:6px">
					<display:table name="pageScope.listaUdocsAMover"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="elemento"
					requestURI="../../action/moverUdocsAction">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" >
						<c:out value="${elemento.codigo}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.titulo" property="titulo" />
				</display:table>
				</div>
				</tiles:put>
			</tiles:insert>
			</c:if>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
	</html:form>
</div> <%--contenedor_ficha --%>



