<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>

<bean:struts id="actionMapping" mapping="/gestionMotivosRechazo" />
<c:set var="editable" value="${sessionScope[appConstants.solicitudes.MOTIVO_EDITABLE_KEY]}"/>

<script language="javascript">
function aceptar(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.submit();
}
</script>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
  <tr>  
    <td class="etiquetaAzul12Bold" height="25px">
    	<c:choose>
			<c:when test="${!empty motivoRechazoForm.id}">
				<bean:message key="NavigationTitle_MOTIVO_RECHAZO_EDICION"/>
			</c:when>
			<c:otherwise>
				<bean:message key="NavigationTitle_MOTIVO_RECHAZO_ALTA"/>
			</c:otherwise>
		</c:choose>		
    </td>
    <td align="right">
		<table cellpadding=0 cellspacing=0>
		 <tr>
	      	  <td>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>	      	  
	      	  </td>
			  <td width="10">&nbsp;</td>	      	  
	      	  <td>
				<c:url var="volver2URL" value="/action/gestionMotivosRechazo">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
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

<DIV id="barra_errores">
	<archivo:errors/>
</DIV>

<div class="separador1">&nbsp;</div>

<DIV class="bloque"> <%--segundo bloque de datos --%>	
	<html:form action="/gestionMotivosRechazo" styleId="formulario">
		<input type="hidden" name="method" id="method" value="addMotivo"/>
		<html:hidden property="id"/>

		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" nowrap="nowrap" width="150px">
					<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
				</td>
				<td class="tdDatos"><html:text property="motivo" styleClass="input80" maxlength="254"/></td>
			</tr>	
			<c:if test="${editable}">	
				<tr>
					<td class="tdTitulo" nowrap="nowrap">
						<bean:message key="archigest.archivo.solicitudes.tipoSolicitud"/>:&nbsp;
					</td>
					<td class="tdDatos" nowrap="nowrap">
						<html:select property="tipoSolicitud">					
							<html:option value=""/>
							<html:option key="archigest.archivo.solicitudes.consulta" value="1"/>
							<html:option key="archigest.archivo.solicitudes.prestamo" value="2"/>
							<html:option key="archigest.archivo.solicitudes.prorroga" value="3"/>
						</html:select>
					</td>
				</tr>
			</c:if>
			<c:if test="${!editable}">
				<html:hidden property="tipoSolicitud"/>
				<tr>
					<td class="tdTitulo" nowrap="nowrap">
						<bean:message key="archigest.archivo.solicitudes.tipoSolicitud"/>:&nbsp;
					</td>
					<td class="tdDatos" nowrap="nowrap">
						<c:choose>
							<c:when test="${motivoRechazoForm.tipoSolicitud == 1}">
								<bean:message key="archigest.archivo.solicitudes.consulta"/>
							</c:when>
							<c:when test="${motivoRechazoForm.tipoSolicitud == 2}">
								<bean:message key="archigest.archivo.solicitudes.prestamo"/>
							</c:when>
							<c:when test="${motivoRechazoForm.tipoSolicitud == 2}">
								<bean:message key="archigest.archivo.solicitudes.prorroga"/>
							</c:when>
						</c:choose>
					</td>
				</tr>
			</c:if>
		</table>
	</html:form>
</div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
