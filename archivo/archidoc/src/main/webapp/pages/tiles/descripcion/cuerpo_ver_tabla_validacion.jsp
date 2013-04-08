<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="valores" value="${sessionScope[appConstants.descripcion.VALORES_VALIDACION_KEY]}"/>
<bean:struts id="actionMapping" mapping="/tablasVld" />

<script language="JavaScript1.2" src="../../js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function edit()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].method.value = "form";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function removeValues()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.eliminar && elementSelected(form.eliminar)) 
		{
			if (confirm("<bean:message key="archigest.archivo.descripcion.tablasValidacion.form.valores.delete.confirm.msg"/>"))
			{
				document.forms["<c:out value="${actionMapping.name}" />"].method.value = "removeValues";
				document.forms["<c:out value="${actionMapping.name}" />"].submit();
			}
		} 
		else
			alert("<bean:message key='archigest.archivo.descripcion.tablasValidacion.form.valores.delete.warning.msg'/>");
	}
	function remove()
	{
		if (confirm("<bean:message key="archigest.archivo.descripcion.tablaValidacion.delete.confirm.msg"/>"))
		{
			<c:url var="removeURL" value="/action/tablasVld">
				<c:param name="method" value="removeTbl" />
				<c:param name="id" value="${tablaValidacionForm.id}"/>
			</c:url>
			window.location = "<c:out value="${removeURL}" escapeXml="false"/>";
		}
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/tablasVld">
  <html:hidden property="id"/>
  <input type="hidden" name="method" value="save"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.textoTablasValidacion.form.nombreTabla"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<tiles:insert definition="button.closeButton" flush="true"/>
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
		
			<div class="cabecero_bloque"><%--cabecero datos de la tabla --%>
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.informacion"/>
							</td>
							<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
					    	<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
									   	<td><a class="etiquetaAzul12Normal" 
											   href="javascript:edit()"
											><html:img page="/pages/images/edit.gif" 
											        altKey="archigest.archivo.editar"
											        titleKey="archigest.archivo.editar"
											        styleClass="imgTextMiddle"/>
									        &nbsp;<bean:message key="archigest.archivo.editar"/></a></td>
										<td width="10">&nbsp;</td>
										<c:if test="${!empty tablaValidacionForm.id && !tablaValidacionForm.usointerno}">
									   	<td>
											<a class="etiquetaAzul12Normal" 
											   href="javascript:remove()"
											><html:img page="/pages/images/delete.gif" 
											        altKey="archigest.archivo.eliminar"
											        titleKey="archigest.archivo.eliminar"
											        styleClass="imgTextBottom"/>
									        &nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
										</td>
										<td width="10">&nbsp;</td>
										</c:if>
									</tr>
								</table>
							</td>
							</security:permissions>
						</tr>
					</tbody>
				</table>
			</div> <%--cabecero datos de la tabla --%>

			<div class="bloque"><%--bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.tablasValidacion.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><bean:write name="tablaValidacionForm" property="nombre" /></td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.tablasValidacion.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><bean:write name="tablaValidacionForm" property="descripcion" /></td>
					</tr>
				</table>
			</div><%--bloque datos tabla --%>
			
			<logic:present name="tablaValidacionForm" property="id">
			<div class="separador8">&nbsp;</div>
		
			<div class="cabecero_bloque"><%--cabecero valores de la tabla --%>
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.descripcion.tablasValidacion.form.valores.caption"/>
							</td>
							<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
				            <td align="right">
				              <table cellpadding="0" cellspacing="0">
				                <tr>
				                  <td>
				                  	<c:url var="URL" value="/action/textosTblVld">
										<c:param name="method" value="retrieveValue" />
										<c:param name="idTblVld" value="${tablaValidacionForm.id}" />
									</c:url>
				                  	<a class="etiquetaAzul12Normal" href="<c:out value="${URL}" escapeXml="false" />"><html:img page="/pages/images/addDoc.gif" 
				                              altKey="archigest.archivo.anadir" 
				                              titleKey="archigest.archivo.anadir" 
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.anadir"/></a></td>
				                  <td width="10"></td>
				                  <td><a class="etiquetaAzul12Normal" 
				                         href="javascript:removeValues()"
				                      ><html:img page="/pages/images/delDoc.gif" 
				                              altKey="archigest.archivo.eliminar" 
				                              titleKey="archigest.archivo.eliminar" 
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
				                  <td width="10"></td>
				                </tr>
				              </table>
				            </td>
				            </security:permissions>
						</tr>
					</tbody>
				</table>
			</div> <%--cabecero valores de la tabla --%>

			<div class="bloque"><%--bloque valores tabla --%>
				<div class="separador1">&nbsp;</div>
				
				<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
				<c:if test="${!empty valores}">
				<div class="w100">
					<table class="w98" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="100%" align="right">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td><a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].eliminar);" >
											<html:img page="/pages/images/checked.gif" 
												border="0" 
												altKey="archigest.archivo.selTodos"
												titleKey="archigest.archivo.selTodos"
												styleClass="imgTextBottom" /><bean:message key="archigest.archivo.selTodos"/>&nbsp;</a></td>
								    	<td width="20">&nbsp;</td>
								    	<td><a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].eliminar);" >
											<html:img page="/pages/images/check.gif" 
												border="0" 
												altKey="archigest.archivo.quitarSel"
												titleKey="archigest.archivo.quitarSel"
												styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div class="separador5">&nbsp;</div>
				</c:if>
				</security:permissions>
				
				<display:table name="pageScope.valores"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="valor" 
					pagesize="15"
					requestURI="/action/tablasVld"
					export="true">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.descripcion.tablasValidacion.form.listaVacia"/>
					</display:setProperty>
					<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
					<display:column title="" style="width:23px" headerClass="minusDocIcon" media="html">
						<input type="checkbox" name="eliminar" value="<c:out value="${valor.id}"/>"/>
					</display:column>
					</security:permissions>
					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombreTblVld" sortable="true" headerClass="sortable" media="html">
						<c:url var="URL" value="/action/textosTblVld">
							<c:param name="method" value="retrieveValue" />
							<c:param name="id" value="${valor.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${valor.valor}"/></a>
					</display:column>
					<display:column titleKey="archigest.archivo.nombre" property="valor" media="csv excel xml pdf"/>
				</display:table>  
			</div><%--bloque valores tabla --%>
			</logic:present>
			
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


