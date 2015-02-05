<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionCampoTabla" />
<c:set var="formBean" value="${sessionScope[actionMapping.name]}" />

<script language="JavaScript1.2" type="text/JavaScript">
	function save()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].action+= "?method=updateInformacion";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function cancel(){
		<c:url var="cancelURL" value="/action/gestionCampoTabla">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${cancelURL}" escapeXml="false"/>';
	}
	function changeTipoNorma()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].action+= "?method=changeTipoNorma";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/gestionCampoTabla">
	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.camposTabla.form.informacion"/>
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

			<div class="bloque"><%--bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message
							key="archigest.archivo.identificador" />:&nbsp;</td>
						<td class="tdDatos"><html:hidden property="id" /> <c:choose>
							<c:when test="${empty formBean.id}">
								<html:text property="guid" maxlength="32" styleClass="input60" />
							</c:when>
							<c:otherwise>
								<c:out value="${formBean.id}" />
							</c:otherwise>
						</c:choose></td>
					</tr>


					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" size="64" maxlength="64"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposTabla.form.norma"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="tipoNorma" styleClass="input" onchange="javascript:changeTipoNorma()">
								<html:optionsCollection name="DescripcionConstants_LISTA_TIPO_NORMAS_KEY" value="value" label="label"/>
							</html:select>
						</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposTabla.form.area"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="idArea" styleClass="input">
								<html:optionsCollection name="DescripcionConstants_LISTA_AREAS_KEY" value="id" label="nombre" />
							</html:select>
						</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposTabla.form.etiquetaXml"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="etiquetaXml" size="128" maxlength="128"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposTabla.form.etiqXmlFila"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="etiqXmlFila" size="128" maxlength="128"/>&nbsp;</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="descripcion" rows="4" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" />&nbsp;</td>
					</tr>
				</table>
			</div><%--bloque datos tabla --%>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


