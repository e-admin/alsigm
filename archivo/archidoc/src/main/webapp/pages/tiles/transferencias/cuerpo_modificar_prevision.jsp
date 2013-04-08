<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<div id="contenedor_ficha">

<html:form action="/gestionPrevisiones">
<input type="hidden" name="method" value="guardarmodificacion">
<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.transferencias.modificacion" />
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
	       	<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
	       	<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="cancelURI" value="/action/gestionPrevisiones">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
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
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${bPrevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />
				</TD>
			</TR>
			<c:if test="${!bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrevision.organoRemitente.nombreLargo}"/>
					</TD>
				</TR>
			</c:if>
			<c:if test="${bPrevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
					</TD>
					<TD class="tdDatos" id="nombreArchivoRemitente">
						<c:out value="${bPrevision.nombrearchivoremitente}"/>
					</TD>
				</TR>
			</c:if>
			<c:if test="${!bPrevision.ordinaria}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.nombrearchivoreceptor}"/>
				</TD>
			</TR>
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
					<c:out value="${bPrevision.fondo.codReferencia}"/>
					<c:out value="${bPrevision.fondo.titulo}"/>
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.numuinstalacion}"/>
				</TD>
			</TR>

			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="120px" class="td2Datos">
							<c:choose>
								<c:when test="${ bPrevision.conAlgunaRelacionNoAbierta}">
									<fmt:formatDate value="${bPrevision.fechainitrans}" pattern="${FORMATO_FECHA}"/>
								</c:when>
								<c:otherwise>
									<html:text property="fechainitrans" styleClass="input" readonly="true" size="10" maxlength="10"/>
									&nbsp;<archigest:calendar
										image="../images/calendar.gif"
			                            formId="PrevisionForm"
			                            componentId="fechainitrans"
			                            format="dd/mm/yyyy"
			                            enablePast="true" />
			                    </c:otherwise>
	                        </c:choose>
						</TD>
						<TD width="20px">
						</TD>
						<TD width="200px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
						</TD>
						<TD width="120px" class="td2Datos">
						<html:text property="fechafintrans" styleClass="input" readonly="true" size="10" maxlength="10"/>
						&nbsp;<archigest:calendar
							image="../images/calendar.gif"
                            formId="PrevisionForm"
                            componentId="fechafintrans"
                            format="dd/mm/yyyy"
                            enablePast="true" />
						</TD>
						<c:if test="${appConstants.configConstants.mostrarCalendarioPrevisiones}">
							<TD width="200px" class="td2Titulo">
								<bean:message key="archigest.archivo.calendario.previsiones"/>:
							</TD>
							<TD width="120px" class="td2Datos">
								<c:url scope="request" var="mostrarCalendarioPrevisionesURL" value="/action/recepcionPrevisiones">
									<c:param name="method" value="mostrarCalendarioPrevisiones" />
									<c:param name="idArchivo"><c:out value="${bPrevision.idarchivoreceptor}"/></c:param>
								</c:url>
								<a class=etiquetaAzul12Bold href="<c:out value="${mostrarCalendarioPrevisionesURL}" escapeXml="false"/>">
									<html:img page="/pages/images/calendar_prev.gif" altKey="archigest.archivo.calendario.previsiones" titleKey="archigest.archivo.calendario.previsiones" styleClass="imgTextBottom" />
						   		</a>
							</TD>
						</c:if>
					</TR>
					</TABLE>
				</TD>
			</TR>
		<c:if test="${!empty bPrevision.motivorechazo}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.notas"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.motivorechazo}"/>
				</TD>
			</TR>
		</c:if>
			<TR>
				<TD class="tdTitulo" style="vertical-align:top">
					<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPrevision.observaciones}"/>
				</TD>
			</TR>
		</TABLE>

</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form>

</div> <%--contenedor_ficha --%>


