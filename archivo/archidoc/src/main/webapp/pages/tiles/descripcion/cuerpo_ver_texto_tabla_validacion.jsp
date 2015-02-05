<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
	<c:set var="modo" value="EDICION"/>
</security:permissions>

<bean:struts id="actionMapping" mapping="/textosTblVld" />

<script language="JavaScript1.2" type="text/JavaScript">
	function save()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function remove()
	{
		if (confirm("<bean:message key="archigest.archivo.descripcion.textoTablasValidacion.delete.confirm.msg"/>"))
		{
			<c:url var="removeURL" value="/action/textosTblVld">
				<c:param name="method" value="removeValue" />
				<c:param name="id" value="${param.id}"/>
			</c:url>
			window.location = "<c:out value="${removeURL}" escapeXml="false"/>";
		}
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/textosTblVld">
  <html:hidden property="id"/>
  <html:hidden property="idTblVld"/>
  <html:hidden property="nombreTblVld"/>
  <input type="hidden" name="method" value="saveValue"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.textoTablasValidacion.caption"/>
				    	</td>
				    	<td align="right">
				    		<c:choose>
				    		<c:when test="${modo=='EDICION'}">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold" 
										   href="javascript:save()"
										><html:img page="/pages/images/Ok_Si.gif" 
										        altKey="archigest.archivo.aceptar"
										        titleKey="archigest.archivo.aceptar"
										        styleClass="imgTextBottom"/>
								        &nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<c:if test="${!empty textoTablaValidacionForm.id}">
								   	<td>
										<a class="etiquetaAzul12Bold" 
										   href="javascript:remove()"
										><html:img page="/pages/images/delete.gif" 
										        altKey="archigest.archivo.eliminar"
										        titleKey="archigest.archivo.eliminar"
										        styleClass="imgTextBottom"/>
								        &nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
									</td>
									<td width="10">&nbsp;</td>
									</c:if>
								   	<td>
										<tiles:insert definition="button.closeButton" flush="true">
											<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
											<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
										</tiles:insert>
									</td>
								</tr>
							</table>
							</c:when>
							<c:otherwise>
							<tiles:insert definition="button.closeButton" flush="true"/>
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>
		
		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
				
			<div id="barra_errores"><archivo:errors /></div>
			
			<div class="separador1">&nbsp;</div>
		
			<div class="bloque"><%--bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.textoTablasValidacion.form.nombreTabla"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${textoTablaValidacionForm.nombreTblVld}"/>&nbsp;</td>
					</tr>
				</table>
			</div>
			
			<div class="separador5">&nbsp;</div>
		
			<div class="bloque"><%--bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.textoTablasValidacion.valor"/>:&nbsp;</td>
						<td class="tdDatos">
				    		<c:choose>
				    		<c:when test="${modo=='EDICION'}">
								<html:textarea property="valor" rows="4"/>
							</c:when>
							<c:otherwise>
								<c:out value="${textoTablaValidacionForm.valor}"/>
							</c:otherwise>
							</c:choose>
							&nbsp;
						</td>
					</tr>
				</table>
			</div><%--bloque datos tabla --%>
			
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


