<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/asignacionCajasReencajado" />
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="unidadInstalacion" value="${sessionScope[appConstants.transferencias.CAJA_KEY]}"/>

<SCRIPT>
	function aceptar(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		form.submit();
	}
</SCRIPT>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.descContenidoUI"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<html:form action="/asignacionCajasReencajado">
			<input type="hidden" name="method" value="guardarEditarDescripcionUI" />
			<input type="hidden" name="idRelacion" value="<c:out value="${vRelacion.id}"/>"/>
			<input type="hidden" name="idUnidadInstalacion" value="<c:out value="${unidadInstalacion.id}"/>"/>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockContent" direct="true">
					<TABLE class="formulario" cellpadding=0 cellspacing=0>
						<TR>
							<TD class="tdTitulo" width="220px">
								<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:choose>
									<c:when test="${unidadInstalacion.descripcion!=null}">
										<c:set var="descripcionContenidoValue" value="${unidadInstalacion.descripcion}" />
										<jsp:useBean id="descripcionContenidoValue" type="java.lang.String"/>
										<html:textarea property="descripcionContenido" value="<%=descripcionContenidoValue%>"
											onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"

										/>
									</c:when>
									<c:otherwise>
										<html:textarea property="descripcionContenido" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>
									</c:otherwise>
								</c:choose>
							</TD>
						</TR>
					</TABLE>
				</tiles:put>
			</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>