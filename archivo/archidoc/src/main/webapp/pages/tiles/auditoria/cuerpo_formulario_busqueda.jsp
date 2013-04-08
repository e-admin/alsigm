<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="modulos" value="${requestScope[appConstants.auditoria.LISTA_MODULOS_KEY]}"/>
<c:set var="acciones" value="${requestScope[appConstants.auditoria.LISTA_ACCIONES_KEY]}"/>
<c:set var="grupos" value="${requestScope[appConstants.auditoria.LISTA_GRUPOS_KEY]}"/>
<bean:struts id="actionMapping" mapping="/auditoriaBuscar" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<c:set var="formBean" value="${requestScope[actionMapping.name]}"/>
<script language="JavaScript1.2" type="text/JavaScript">
	function clean()
	{
		xGetElementById("modulo").value = "-1";
		xGetElementById("accion").value = "-1";
		xGetElementById("grupo").value = "-1";
		xGetElementById("ip").value = "";
		cleanFecha(xGetElementById(formulario));
	}
</script>

<div id="contenedor_ficha">

	<div class="ficha">
		<html:form action="/auditoriaBuscar?method=buscar" styleId="formulario">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.auditoria.busqueda.form.caption"/></td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:document.forms['<c:out value="${actionMapping.name}" />'].submit()"
										><input type="image"
												src="<%=request.getContextPath()%>/pages/images/buscar.gif"
										        border="0"
										        alt="<bean:message key="archigest.archivo.buscar"/>"
										        title="<bean:message key="archigest.archivo.buscar"/>"
										        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:clean()"
										><html:img page="/pages/images/clear0.gif"
										        border="0"
										        altKey="archigest.archivo.limpiar"
										        titleKey="archigest.archivo.limpiar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<TD>
										<c:url var="cancelURL" value="/action/auditoriaBuscar">
											<c:param name="method" value="goBack" />
										</c:url>
										<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false" />'">
											<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
											&nbsp;<bean:message key="archigest.archivo.cerrar"/>
										</a>
							   		</TD>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors/></div>

			<c:url var="url" value="/action/auditoriaBuscar?method=loadAcciones"/>
			<script>
				function cambiaModulo() {
					var set = document.forms['<c:out value="${actionMapping.name}" />'].elements.item("modulo");

					if (set.value>=0) {
						window.document.forms['<c:out value="${actionMapping.name}" />'].action='<c:out value="${url}" escapeXml="false"/>&modulo='+set.value;
						window.document.forms['<c:out value="${actionMapping.name}" />'].submit();
					}
				}
			</script>

			<div class="bloque">
				<table class="formulario">

					<c:url var="url" value="/action/auditoriaBuscar?method=loadAcciones"/>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.auditoria.busqueda.form.modulo"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:select property="modulo" styleId="modulo" styleClass="input" onchange="javascript:cambiaModulo()">
								<html:option key="archigest.archivo.auditoria.busqueda.form.value.todos" value="-1"/>
			              		<c:forEach items="${modulos}" var="modulo">
			              			<option value="<c:out value="${modulo.id}"/>" <c:if test="${formBean.modulo==modulo.id}"> selected</c:if> >
			              				<fmt:message key="${modulo.name}"/>
			              			</option>
			              		</c:forEach>
							</html:select>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.auditoria.busqueda.form.accion"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:select property="accion"  styleId="accion" styleClass="input">
								<html:option key="archigest.archivo.auditoria.busqueda.form.value.todos" value="-1"/>
			              		<c:forEach items="${acciones}" var="accion">
			              			<option value="<c:out value="${accion.id}"/>" <c:if test="${formBean.accion==accion.id}"> selected</c:if> >
			              				<fmt:message key="${accion.name}"/>
			              			</option>
			              		</c:forEach>
							</html:select>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.auditoria.busqueda.form.grupo"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:select property="grupo"  styleId="grupo" styleClass="input">
								<html:option key="archigest.archivo.auditoria.busqueda.form.value.todos" value="-1"/>
								<html:optionsCollection name="grupos" label="nombre" value="id"/>
							</html:select>
							&nbsp;
						</td>
					</tr>

				<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
				<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
				<bean:define id="sizeCampo" value="150" toScope="request"/>
				<tiles:insert page="/pages/tiles/deposito/busquedas/campo_busqueda_fecha.jsp" flush="true"/>

					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.auditoria.busqueda.form.ip"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:text property="ip"  styleId="ip" size="15" maxlength="15" styleClass="input"/>
						</td>
					</tr>
				</table>
			</div>

		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
		</html:form>
	</div> <%-- ficha --%>

</div> <%-- contenedor_ficha --%>