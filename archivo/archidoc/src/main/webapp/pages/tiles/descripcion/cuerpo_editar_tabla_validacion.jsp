<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/tablasVld" />

<script language="JavaScript1.2" type="text/JavaScript">
	function save()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].method.value = "save";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function cancel(){
		<c:url var="cancelURL" value="/action/tablasVld">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${cancelURL}" escapeXml="false"/>';
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/tablasVld">
  <html:hidden property="id"/>
  <html:hidden property="usointerno"/>
  <input type="hidden" name="method" value="save"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.tablasValidacion.form.tabla.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold" 
										   href="javascript:save()"
										><input type="image"
												src="../images/Ok_Si.gif" 
										        alt="<bean:message key="archigest.archivo.aceptar"/>" 
										        title="<bean:message key="archigest.archivo.aceptar"/>" 
										        class="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold" 
										   href="javascript:cancel()"
										><html:img page="/pages/images/Ok_No.gif" 
										        altKey="archigest.archivo.cancelar" 
										        titleKey="archigest.archivo.cancelar" 
										        styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
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
		
			<div class="bloque"><%-- bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.tablasValidacion.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" size="64" maxlength="64"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.tablasValidacion.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="descripcion" rows="4" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" />&nbsp;</td>
					</tr>
				</table>
			</div><%-- bloque datos tabla --%>
			
		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%-- ficha --%>
  </html:form>

</div> <%-- contenedor_ficha --%>


