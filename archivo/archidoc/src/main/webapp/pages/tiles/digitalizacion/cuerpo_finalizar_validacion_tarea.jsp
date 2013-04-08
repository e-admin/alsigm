<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionTareasDigitalizacion" />
<c:set var="tarea" value="${sessionScope[appConstants.documentos.TAREA_KEY]}"/>
<c:set var="beanForm" value="${sessionScope[actionMapping.name]}"/>

<script>
function aceptar(){
	var form = document.forms['<c:out value="${actionMapping.name}"/>'];
	form.method.value="guardarfinalizarValidacionTarea";
	form.submit();
}
function cancelar(){
	var form = document.forms['<c:out value="${actionMapping.name}"/>'];
	form.method.value="goBack";
	form.submit();
}
</script>
<div id="contenedor_ficha" style="-moz-box-sizing: border-box;">
<div class="content_container">
<html:form action="/gestionTareasDigitalizacion">
<input type="hidden" name="method"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
					<td class="etiquetaAzul12Bold" height="25px">
						<bean:message key="archigest.archivo.documentos.digitalizacion.finValidacionTarea"/>
					</td>
					<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
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
									<td width="10">&nbsp;</td>
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
							<fmt:message key="${tarea.nombreTipoObjeto}"/>
						</TD>
					</TR>
					<c:choose>
						<c:when test="${tarea.codRefObj!=null}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.codigo"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${tarea.codRefObj}"/>
							</TD>
						</TR>
						</c:when>
					</c:choose>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.titulo"/>:&nbsp;
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
						<TD class="tdTitulo">
							<bean:message key="archigest.arcivo.estado"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:message key="${tarea.nombreEstado}"/>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>					
					<TR>
						<TD class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.documentos.digitalizacion.motivoErrores"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"/>
						</TD>
					</TR>
				</TABLE>
				</tiles:put>
			</tiles:insert>



		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%-- ficha --%>
</html:form>
</div>
</div>


