<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionTareasDigitalizacion" />

<c:set var="beanForm" value="${sessionScope[actionMapping.name]}"/>
<c:set var="descriptorParaTarea" value="${sessionScope[appConstants.documentos.DESCRIPTOR_PARA_CREAR_TAREA_KEY]}"/>
<c:set var="elementoParaTarea" value="${sessionScope[appConstants.documentos.ELEMENTO_PARA_CREAR_TAREA_KEY]}"/>
<script>
function aceptar(){
	var form = document.forms['<c:out value="${actionMapping.name}"/>'];
	form.method.value="guardarTarea";
	form.submit();
}
function cancelar(){
	var form = document.forms['<c:out value="${actionMapping.name}"/>'];
	form.method.value="goReturnPoint";
	form.submit();
}
</script>
<html:form action="/gestionTareasDigitalizacion">
<input type="hidden" name="method"/>

<div id="contenedor_ficha" style="-moz-box-sizing: border-box;">
<div class="content_container">

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
										<html:img page="/pages/images/Previous.gif" 
										altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras"
										styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.atras"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<td><a class="etiquetaAzul12Bold" href="javascript:aceptar()">
										<html:img page="/pages/images/Ok_Si.gif" 
										altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar"
										styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a></td>
									<td width="10">&nbsp;</td>
									<td><a class="etiquetaAzul12Bold" href="javascript:cancelar()">
										<html:img page="/pages/images/Ok_No.gif" 
										altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar"
										styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a></td>
									
									<c:if test="${appConstants.configConstants.mostrarAyuda}">
										<td width="10">&nbsp;</td>
										<td>
											<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
											<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
											<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/digitalizacion/CrearTareaDigitalizacion.htm');">
												<html:img page="/pages/images/help.gif" 
													altKey="archigest.archivo.ayuda" 
													titleKey="archigest.archivo.ayuda" 
													styleClass="imgTextMiddle"/>
													&nbsp;<bean:message key="archigest.archivo.ayuda"/>
											</a>
										</td>
									</c:if>
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
							<bean:message key="archigest.archivo.objeto"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:choose>
								<c:when test="${descriptorParaTarea!=null}">
									<bean:message key="archivo.descriptores.tipo.entidad"/>
								</c:when>
								<c:otherwise>
									<fmt:message key="archigest.archivo.cf.tipoElemento.${elementoParaTarea.tipo}"/>
								</c:otherwise>
							</c:choose>
						</TD>
					</TR>
					<c:choose>
						<c:when test="${elementoParaTarea!=null}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.codigo"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${elementoParaTarea.codigo}"/>
							</TD>
						</TR>
						</c:when>
					</c:choose>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.titulo"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:choose>
								<c:when test="${descriptorParaTarea!=null}">
									<c:out value="${descriptorParaTarea.nombre}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${elementoParaTarea.titulo}"/>
								</c:otherwise>
							</c:choose>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.documentos.digitalizacion.usuarioCaptura"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="bListaCapturadoresKey" value="${appConstants.documentos.USUARIOS_CAPTURADORES_KEY}"/>
							<jsp:useBean id="bListaCapturadoresKey" type="java.lang.String"/>
							<html:select property='idGestor' size="1" styleClass="input">
								<option value="" >--</option>
								<html:optionsCollection name="<%=bListaCapturadoresKey%>" label="nombreCompleto" value="id"/>
							</html:select>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>
					<TR>
						<TD class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.documentos.digitalizacion.observaciones"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
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
</div>
</html:form>

