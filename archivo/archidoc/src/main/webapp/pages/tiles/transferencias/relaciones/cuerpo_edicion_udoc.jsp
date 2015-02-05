<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="modo" value="${sessionScope[appConstants.transferencias.banderaEdicionCreacion]}"/>
<c:set var="unidadDocumental" value="${sessionScope[appConstants.transferencias.UNIDAD_DOCUMENTAL]}"/>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<div id="contenedor_ficha">

<bean:struts id="mappingGestionUdocs" mapping="/gestionUdocsRelacion" />
<c:set var="__FORM_NAME" value="${mappingGestionUdocs.name}" />
<jsp:useBean id="__FORM_NAME" type="java.lang.String" />

<html:form action="/gestionUdocsRelacion">
<c:choose>
	<c:when test="${modo == 'C'}">
		<c:set var="actionMethodToInvoke" scope="request" value="crearUnidadDocumental" />
	</c:when>
	<c:when test="${modo == 'M'}">
		<c:set var="actionMethodToInvoke" scope="request" value="guardarUnidadDocumental" />
	</c:when>
</c:choose>
<input type="hidden" name="method" value="<c:out value="${actionMethodToInvoke}" />">


<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
		<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.transferencias.edicion"/>
			<bean:message key="archigest.archivo.transferencias.la"/>
			<c:choose>
				<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
					<bean:message key="archigest.archivo.transferencias.fraccionSerie"/>
				</c:when>
				<c:otherwise>
					<bean:message key="archigest.archivo.transferencias.unidadDoc"/>
				</c:otherwise>
			</c:choose>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			    <TD>
					<script>
						function guardar() {
							var form = document.forms['<c:out value="${mappingGestionUdocs.name}" />'];
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
						<c:choose>
							<c:when test="${empty vRelacion.idFicha}">
								<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.aceptar"/>
							</c:when>
							<c:otherwise>
								<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextTop" />
					   		 	&nbsp;<bean:message key="archigest.archivo.siguiente"/>
							</c:otherwise>
						</c:choose>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   <TD>
					<c:url var="cancelURI" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="goBack"  />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
		</TD>
		</TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
	<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<DIV class="cabecero_bloque"> <%--segundo bloque de datos (Contador de unidades documentales siempre visible) --%>
	<bean:define id="showPrevNext" value="false" toScope="request"/>
	<jsp:include page="contador_unidaddocre.jsp" flush="true" />
</DIV> <%--segundo bloque de datos (Contador de unidades documentales siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
</div>

<div class="bloque"> <%--primer bloque de datos --%>

		<TABLE class="formulario" cellpadding=0 cellspacing=0>
		<c:if test="${vRelacion.expedienteEnFicha}">
			<c:choose>
					<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
						<c:set var="rangosUDoc" value="${unidadDocumental.rangos}" />
						<c:if test="${empty rangosUDoc}">
							<c:set var="rangosUDoc" value="${sessionScope[appConstants.transferencias.LISTA_RANGOS_UDOC]}" />
						</c:if>
						<c:if test="${!empty rangosUDoc}">
							<bean:define id="rangosUDoc" name="rangosUDoc" toScope="request"/>
						</c:if>
						<tiles:insert page="../relaciones/info_rangos_udoc.jsp" flush="true"/>
					</c:when>
					<c:otherwise>
						<TR>
							<TD class="tdTitulo" width="220px">
								<bean:message key="archigest.archivo.transferencias.numExp"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<html:text property="numeroExpediente" styleClass="input99" maxlength="256" />
							</TD>
						</TR>
					</c:otherwise>
			</c:choose>
		</c:if>
			<TR>
				<TD class="tdTitulo" width="220px">
					<bean:message key="archigest.archivo.transferencias.asunto"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:textarea property="asunto" rows="3" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" width="220px">
					<bean:message key="archigest.archivo.transferencias.fIni"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="150px" class="td2Datos">
							<html:text property="fechaInicio" size="10" maxlength="10"/>
							<archigest:calendar
											image="../images/calendar.gif"
											formId="<%=__FORM_NAME%>"
											componentId="fechaInicio"
											format="dd/mm/yyyy"
											enablePast="true" />
						</TD>
						<TD width="20px">
						</TD>
						<TD width="100px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFin"/>:&nbsp;
						</TD>
						<TD width="150px" class="td2Datos">
							<html:text property="fechaFin" size="10" maxlength="10"/>
							<archigest:calendar
											image="../images/calendar.gif"
											formId="<%=__FORM_NAME%>"
											componentId="fechaFin"
											format="dd/mm/yyyy"
											enablePast="true" />
						</TD>
					</TR>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD colspan="2" class="separador12">&nbsp;
				</TD>
			</TR>
			<c:if test="${vRelacion.productorEnFicha}">
				<TR>
					<TD class="tdTitulo" valign="top" width="220px">
						<bean:message key="archigest.archivo.transferencias.orgProd"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:hidden property="idProductor" />
						<html:text property="nombreProductor" styleClass="inputRO90" readonly="true" />
						<a href="javascript:buscarOrganoProductor('<%=__FORM_NAME%>')" class="tdlink" >
							<html:img page="/pages/images/expand.gif" border="0" styleClass="imgTextMiddle"/>
						</a>
						<a href="javascript:clean()" class="tdlink">
							<html:img page="/pages/images/clear0.gif"
						        altKey="archigest.archivo.limpiar"
						        titleKey="archigest.archivo.limpiar"
						        styleClass="imgTextMiddle"/>
						</a>
						<script>
							function seleccionarProductor(idProductor, nombreProductor) {
								var form = document.forms['<%=__FORM_NAME%>'];
								form.idProductor.value = idProductor;
								form.nombreProductor.value = nombreProductor;
							}
							function clean()
							{
								var form = document.forms['<c:out value="${mappingGestionUdocs.name}" />'];
								form.idProductor.value = '';
								form.nombreProductor.value = '';
								<c:if test="${!empty posiblesProductores}">
								hideSeleccionProductor();
								</c:if>
							}
						</script>
						<c:set var="posiblesProductores" value="${requestScope[appConstants.transferencias.LISTA_PRODUCTORES]}" />
						<c:if test="${!empty posiblesProductores}">
							<div id="seleccionProductor" class="bloque" style="width:95%;margin:0px">
								<div style="padding-top:8px;padding-bottom:8px">
								<display:table name="pageScope.posiblesProductores" id="productor"
									style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.transferencias.noProductoresSerie"/>
									</display:setProperty>
									<display:column titleKey="archigest.archivo.transferencias.organismosProductores">
										<div onclick="seleccionarProductor('<c:out value="${productor.id}" />','<c:out value="${productor.nombre}" />')" style="cursor:default"><c:out value="${productor.nombre}" /></div>
									</display:column>
									<display:column titleKey="archigest.archivo.fechaInicial">
										<fmt:formatDate value="${productor.fechaInicial}" pattern="${FORMATO_FECHA}"/>
									</display:column>
									<display:column titleKey="archigest.archivo.fechaFinal">
										<fmt:formatDate value="${productor.fechaFinal}" pattern="${FORMATO_FECHA}"/>
									</display:column>
								</display:table>
								</div>
								<script>
									function hideSeleccionProductor() {
										xDisplay('seleccionProductor','none');
									}
								</script>
								<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #7B7B7B;">
								<a class="etiquetaAzul12Bold" href="javascript:hideSeleccionProductor()">
									<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.cerrar"/>
								</a>&nbsp;
								</td></tr></table>
							</div> <%--bloque --%>
						</c:if>
					</TD>
				</TR>
			</c:if>
			<c:if test="${vRelacion.conSignatura
							&& (!vRelacion.formato.multidoc || vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador)}">
				<TR>
					<TD colspan="2" class="separador12">&nbsp;
					</TD>
				</TR>
					<TR>
						<TD class="tdTitulo" width="220px">
							<bean:message key="archigest.archivo.transferencias.signaturaUDoc"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:text property="signaturaUDoc" size="40" maxlength="16" onchange="if (this.form.signaturaUI) {this.form.signaturaUI.value = this.value;}"/>
						</TD>
					</TR>
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.transferencias.signaturaUI"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:text property="signaturaUI" size="40" styleClass="inputRO" readonly="true" maxlength="16" />
					</TD>
				</TR>
			</c:if>
		</TABLE>
</div> <%--bloque --%>

<script>
	function buscarOrganoProductor(formName) {
		var form = document.forms[formName];
		if (form != null) {
			form.method.value = 'verPosiblesProductores';

			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
			form.submit();
		}

	}
</script>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>

