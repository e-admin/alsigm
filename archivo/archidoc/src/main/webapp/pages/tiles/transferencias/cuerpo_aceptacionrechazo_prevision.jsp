<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<bean:struts id="mappingRecepcionPrevisiones" mapping="/recepcionPrevisiones" />
<c:set var="formBean" value="${requestScope[mappingRecepcionPrevisiones.name]}" />

<script>
	function verCalendarioPrevisiones() {
		document.forms["<c:out value="${mappingRecepcionPrevisiones.name}" />"].action+='?method=mostrarCalendarioPrevisiones&idArchivo=<c:out value="${bPrevision.idarchivoreceptor}"/>';
		document.forms["<c:out value="${mappingRecepcionPrevisiones.name}" />"].submit();
	}
</script>

<div id="contenedor_ficha">

<html:form action="/recepcionPrevisiones">
<html:hidden property="idPrevision"/>
<input type="hidden" name="method" value="<c:out value="${formBean.methodToPerform}" />" />
<html:hidden property="methodToPerform" />

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<c:choose>
		<c:when test="${formBean.methodToPerform == 'guardaraceptarprevision'}">
			<bean:message key="archigest.archivo.transferencias.aceptarPrev"/>
		</c:when>
		<c:when test="${formBean.methodToPerform == 'guardarrechazoprevision'}">
			<bean:message key="archigest.archivo.transferencias.rechazarPrev"/>
		</c:when>
		</c:choose>
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
	       	<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingRecepcionPrevisiones.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
	       	<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="volverURL" value="/action/recepcionPrevisiones">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
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
			<jsp:include page="cabeceracte_prevision.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador5">&nbsp;</div>

<DIV class="bloque"> <%--cabecero primer bloque de datos --%>

		<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="250px">
					<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:set var="keyTitulo" value="archigest.archivo.transferencias.tipooperacion${bPrevision.tipooperacion}" />
					<fmt:message key="${keyTitulo}" />
				</TD>
			</TR>
			<c:if test="${!bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.organoRemitente.nombreLargo}" />
					</TD>
				</TR>
			</c:if>
			<c:if test="${bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.nombrearchivoremitente}"/>
					</TD>
				</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.nombrearchivoreceptor}" />
				</TD>
			</TR>
			<c:if test="${!bPrevision.ordinaria}">
			<TR>
				<TD class="tdTitulo">
					<c:choose>
						<c:when test="${!bPrevision.entreArchivos}">
							<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
						</c:otherwise>
					</c:choose>
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.fondo.codReferencia}" />
					<c:out value="${bPrevision.fondo.titulo}" />
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.numuinstalacion}" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" style="vertical-align:top">
					<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.observaciones}"> -- </c:out>
				</TD>
			</TR>

		<c:choose>
		<c:when test="${formBean.methodToPerform == 'guardaraceptarprevision'}">
			<TR>
				<TD class="tdTitulo" style="text-align:top">
					<bean:message key="archigest.archivo.transferencias.notas"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.motivorechazo}"> -- </c:out>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="150px" class="td2Datos">
							<c:set var="formName" value="${mappingRecepcionPrevisiones.name}" />
							<jsp:useBean id="formName" type="java.lang.String" />
							<html:text property="fechainitrans" styleClass="input" size="10" maxlength="10"/>
							&nbsp;<archigest:calendar
								image="../images/calendar.gif"
	                            formId="<%=formName%>"
	                            componentId="fechainitrans"
	                            format="dd/mm/yyyy"
	                            enablePast="false" />
						</TD>
						<TD width="10px">
						</TD>
						<TD width="220px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
						</TD>
						<TD width="150px" class="td2Datos">
							<html:text property="fechafintrans" size="10" maxlength="10"/>
							&nbsp;<archigest:calendar
								image="../images/calendar.gif"
	                            formId="<%=formName%>"
	                            componentId="fechafintrans"
	                            format="dd/mm/yyyy"
	                            enablePast="false" />
						</TD>
						<c:if test="${appConstants.configConstants.mostrarCalendarioPrevisiones}">
							<TD width="220px" class="td2Titulo">
								<bean:message key="archigest.archivo.calendario.previsiones"/>:
							</TD>
							<TD width="150px" class="td2Datos">
								<a class=etiquetaAzul12Bold href="javascript:verCalendarioPrevisiones()">
									<html:img page="/pages/images/calendar_prev.gif" altKey="archigest.archivo.calendario.previsiones" titleKey="archigest.archivo.calendario.previsiones" styleClass="imgTextBottom" />
						   		</a>
							</TD>
						</c:if>
					</TR>
					</TABLE>
				</TD>
			</TR>
		</c:when>
		<c:when test="${formBean.methodToPerform == 'guardarrechazoprevision'}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.notas"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:textarea property="motivorechazo" rows="2" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>
				</TD>
			</TR>
		</c:when>
		</c:choose>
		</TABLE>



</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>

</html:form>

</div> <%--contenedor_ficha --%>