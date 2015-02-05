<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="divisionFraccionSerie" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="modo" value="${sessionScope[appConstants.fondos.BANDERA_CREACION_EDICION]}"/>
<c:set var="udocEnFS" value="${sessionScope[appConstants.fondos.UNIDAD_DOCUMENTAL_EN_FS]}"/>
<c:set var="prevUnidadDocumentalEnFs" value="${udocEnFS.prevUdocEnDivisionFs}"/>
<c:set var="nextUnidadDocumentalEnFs" value="${udocEnFS.nextUdocEnDivisionFs}"/>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>

<div id="contenedor_ficha">

<bean:struts id="mappingGestionUdocsEnFS" mapping="/gestionUDocsEnFS" />
<c:set var="__FORM_NAME" value="${mappingGestionUdocsEnFS.name}" />
<jsp:useBean id="__FORM_NAME" type="java.lang.String" />

<html:form action="/gestionUDocsEnFS">
<c:choose>
	<c:when test="${modo == 'C'}">
		<c:set var="actionMethodToInvoke" scope="request" value="crearUnidadDocumentalEnFS" />
	</c:when>
	<c:when test="${modo == 'M'}">
		<c:set var="actionMethodToInvoke" scope="request" value="guardarUnidadDocumentalEnFS" />
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
				<c:when test="${divisionFraccionSerie.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
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
							var form = document.forms['<c:out value="${mappingGestionUdocsEnFS.name}" />'];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
						<c:choose>
							<c:when test="${empty divisionFraccionSerie.idFicha}">							
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
					<c:url var="cancelURI" value="/action/gestionUDocsEnFS">
						<c:param name="method" value="goBack" />
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

<div id="barra_errores">
		<archivo:errors />
</div>


<DIV class="cabecero_bloque"> <%--primer bloque de datos (resumen siempre visible) --%>
	<bean:define id="showPrevNext" value="true" toScope="request"/>
	<jsp:include page="/pages/tiles/fondos/fraccionSerie/cabeceracte_divisionFS.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%></div>

<div class="bloque"> <%--primer bloque de datos --%>
		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<c:choose>
					<c:when test="${divisionFraccionSerie.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
						<c:if test="${!empty udocEnFS}">
							<c:set var="rangosUDoc" value="${udocEnFS.rangos}" />
						</c:if>
						<c:if test="${empty rangosUDoc}">
							<c:set var="rangosUDoc" value="${sessionScope[appConstants.fondos.LISTA_RANGOS_UDOC]}" />
						</c:if>
						<c:if test="${!empty rangosUDoc}">
							<bean:define id="rangosUDoc" name="rangosUDoc" toScope="request"/>
						</c:if>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/info_rangos_udoc.jsp" flush="true"/>
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
		</TABLE>	
</div> <%--bloque --%>
</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;</span></h2>

</div> <%--ficha --%>
</html:form>

</div> <%--contenedor_ficha --%>

