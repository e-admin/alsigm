<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionTareasDigitalizacion" />

<c:set var="tarea" value="${requestScope[appConstants.documentos.TAREA_KEY]}"/>

<div id="contenedor_ficha" style="-moz-box-sizing: border-box;">

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
					<td class="etiquetaAzul12Bold" height="25px">
						<bean:message key="archigest.archivo.documentos.digitalizacion.altaTarea"/>
					</td>
					<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
									<c:url var="cancelURI" value="/action/gestionTareasDigitalizacion">
										<c:param name="method" value="goBack"  />
									</c:url>
									<td><a class="etiquetaAzul12Bold" href="<c:out value="${cancelURI}" escapeXml="false"/>">
										<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cerrar"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>
		
		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
		
			<div id="barra_errores"><archivo:errors /></div>
			<div class="separador1">&nbsp;</div>
	
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.documentos.digitalizacion.datosTarea"/></tiles:put>
				<tiles:put name="blockContent" direct="true">

				<TABLE class="formulario"> 
					<TR>
						<TD class="tdTitulo" width="150px">
							Objeto:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:message key="${tarea.nombreTipoObjeto}"/>
						</TD>
					</TR>
					<c:choose>
						<c:when test="${tarea.codRefObj!=null}">
						<TR>
							<TD class="tdTitulo">
								Código:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${tarea.codRefObj}"/>
							</TD>
						</TR>
						</c:when>
					</c:choose>
					<TR>
						<TD class="tdTitulo">
							Título:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.tituloObj}"/>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.documentos.digitalizacion.usuarioCaptura"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.usuarioCaptura.nombreCompleto}"/>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>
					<TR>
						<TD class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.documentos.digitalizacion.observaciones"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.observaciones}"/>
						</TD>
					</TR>
				</TABLE>

				</tiles:put>
			</tiles:insert>

		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%-- ficha --%>

</div>
</html:form>

