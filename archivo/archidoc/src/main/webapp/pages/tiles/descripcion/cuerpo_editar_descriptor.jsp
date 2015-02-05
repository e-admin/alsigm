<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fichasDescripcion" value="${requestScope[appConstants.descripcion.FICHAS_DESCRIPCION_KEY]}"/>
<c:set var="listasControlAcceso" value="${requestScope[appConstants.descripcion.LISTAS_CONTROL_ACCESO_KEY]}"/>

<bean:struts id="actionMapping" mapping="/descriptor" />

<script language="JavaScript1.2" type="text/JavaScript">
	function save()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function cancel()
	{
		<c:url var="cancelURL" value="/action/descriptor">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${cancelURL}" escapeXml="false"/>';
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/descriptor">
  <html:hidden property="id"/>
  <html:hidden property="idLista"/>
  <html:hidden property="nombreLista"/>
  <html:hidden property="idSistExt"/>
  <html:hidden property="idDescrSistExt"/>
  <html:hidden property="tieneDescr"/>
  <html:hidden property="tipo"/>
  <html:hidden property="editClfDocs"/>
  <input type="hidden" name="estado" value="1"/>
  <input type="hidden" name="method" value="saveDescriptor"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.descriptor.form.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:save()"
										><input type="image"
												src="../images/Ok_Si.gif"
										        border="0"
										        alt="<bean:message key="archigest.archivo.aceptar"/>"
										        title="<bean:message key="archigest.archivo.aceptar"/>"
										        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:cancel()"
										><html:img page="/pages/images/Ok_No.gif"
										        border="0"
										        altKey="archigest.archivo.cancelar"
										        titleKey="archigest.archivo.cancelar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
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
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" styleClass="input99" maxlength="512"/>&nbsp;</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nombreLista"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${descriptorForm.nombreLista}"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${descriptorForm.tipo == 0}">
									<bean:message key="archivo.descriptores.tipo.sin_tipo_especifico"/>
								</c:when>
								<c:when test="${descriptorForm.tipo == 1}">
									<bean:message key="archivo.descriptores.tipo.entidad"/>
								</c:when>
								<c:when test="${descriptorForm.tipo == 2}">
									<bean:message key="archivo.descriptores.tipo.geografico"/>
								</c:when>
								<c:when test="${descriptorForm.tipo == 3}">
									<bean:message key="archivo.descriptores.tipo.materia"/>
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
							&nbsp;
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.fichasDescr"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${descriptorForm.tieneDescr == 'S'}">
									<c:out value="${descriptorForm.nombreFichaDescr}"/>&nbsp;
									<html:hidden name="descriptorForm" property="idFichaDescr"/>
								</c:when>
								<c:otherwise>
									<html:select property="idFichaDescr">
										<html:option value=""/>
										<html:optionsCollection name="fichasDescripcion" label="nombre" value="id"/>
									</html:select>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${descriptorForm.editClfDocs == 'S'}">
									<c:out value="${descriptorForm.nombreRepEcm}"/>&nbsp;
									<html:hidden name="descriptorForm" property="idRepEcm"/>
								</c:when>
								<c:otherwise>
									<bean:define id="nombreCampo" value="idRepEcm" toScope="request"/>
									<tiles:insert name="lista.repositorios.ecm" flush="true"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nivelAcceso"/>:&nbsp;</td>
						<td class="tdDatos">
							<script>
								function checkListaControlAcceso()
								{
									var form = document.forms["<c:out value="${actionMapping.name}" />"];
									if (form.nivelAcceso.value == 1)
									{
										form.idLCA.value = "";
										document.getElementById('trListaControlAcceso').style.visibility="hidden";
										xDisplay('idLCA', 'none');
										//xDisplay('trListaControlAcceso', 'none');
									}
									else{
										document.getElementById('trListaControlAcceso').style.visibility="visible";
										xDisplay('idLCA','inline');
										//xDisplay('trListaControlAcceso', 'block');
									}
								}
							</script>
							<html:select property="nivelAcceso" onchange="javascript:checkListaControlAcceso()">
								<html:option key="archivo.nivel_acceso.publico" value="1"/>
								<html:option key="archivo.nivel_acceso.archivo" value="2"/>
								<html:option key="archivo.nivel_acceso.restringido" value="3"/>
							</html:select>
						</td>
					</tr>
					<tr id="trListaControlAcceso">
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.listaControlAcceso"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="idLCA" styleId="idLCA">
								<html:option key="" value=""/>
								<html:optionsCollection name="listasControlAcceso" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
				</table>
				<script>checkListaControlAcceso()</script>
			</div><%--bloque datos tabla --%>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


