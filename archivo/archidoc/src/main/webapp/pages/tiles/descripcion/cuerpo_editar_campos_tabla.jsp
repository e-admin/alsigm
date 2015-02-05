<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld"
	prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>

<c:set var="valores" value="${sessionScope.LISTA_CAMPOS_DATO}" />
<bean:struts id="actionMapping" mapping="/gestionCampoTabla" />

<c:set var="formBean" value="${sessionScope[actionMapping.name]}" />

<script language="JavaScript1.2" src="../../js/utils.js"
	type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function edit() {

		document.forms["<c:out value="${actionMapping.name}" />"].action += "?method=edit";

		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}

	function removeValues() {
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.camposABorrar && elementSelected(form.camposABorrar)) {
			if (confirm("<bean:message key="archigest.archivo.descripcion.camposTabla.form.valores.delete.confirm.msg"/>")) {
				document.forms["<c:out value="${actionMapping.name}" />"].action += "?method=deleteCamposDato";
				document.forms["<c:out value="${actionMapping.name}" />"]
						.submit();
			}
		} else
			alert("<bean:message key='archigest.archivo.descripcion.camposTabla.form.valores.delete.warning.msg'/>");
	}

	function removeCampo() {

		if (confirm("<bean:message key="archigest.archivo.descripcion.camposTabla.form.delete.confirm.msg"/>")) {
			document.forms["<c:out value="${actionMapping.name}" />"].action += "?method=delete";
			document.forms["<c:out value="${actionMapping.name}" />"].submit();
		}
	}

	function subirCampo() {

		var form = document.forms['<c:out value="${actionMapping.name}" />'];

		if (form.camposABorrar && elementSelected(form.camposABorrar)) {
			form.action += "?method=subirCampo";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.descripcion.camposTabla.form.mover.msgNoCampoSeleccionado'/>");

	}

	function bajarCampo() {

		var form = document.forms['<c:out value="${actionMapping.name}" />'];

		if (form.camposABorrar && elementSelected(form.camposABorrar)) {
			form.action += "?method=bajarCampo";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.descripcion.camposTabla.form.mover.msgNoCampoSeleccionado'/>");
	}
</script>

<div id="contenedor_ficha">
	<html:form action="/gestionCampoTabla">
		<html:hidden property="id" />


		<div class="ficha">

			<h1>
				<span>
					<div class="w100">
						<table class="w98" cellpadding=0 cellspacing=0>
							<tr>
								<td class="etiquetaAzul12Bold" height="25px"><bean:message
										key="archigest.archivo.cacceso.gestionCampoTabla.edicion" /></td>
								<td align="right">
									<table cellpadding=0 cellspacing=0>
										<tr>
											<td><tiles:insert definition="button.closeButton"
													flush="true" /></td>
										</tr>
									</table></td>
							</tr>
						</table>
					</div> </span>
			</h1>

			<div class="cuerpo_drcha">
				<div class="cuerpo_izda">

					<div id="barra_errores">
						<archivo:errors />
					</div>

					<div class="separador1">&nbsp;</div>

					<div class="cabecero_bloque">
						<%--cabecero datos de la tabla --%>
						<table class="w98m1" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td class="etiquetaAzul12Bold"><bean:message
											key="archigest.archivo.informacion" /></td>

									<td align="right">
										<table cellpadding=0 cellspacing=0>
											<tr>
												<td><a class="etiquetaAzul12Normal"
													href="javascript:edit()"><html:img
															page="/pages/images/edit.gif"
															altKey="archigest.archivo.editar"
															titleKey="archigest.archivo.editar"
															styleClass="imgTextMiddle" /> &nbsp;<bean:message
															key="archigest.archivo.editar" />
												</a>
												</td>
												<td width="10">&nbsp;</td>
											</tr>
										</table></td>

								</tr>
							</tbody>
						</table>
					</div>
					<%--cabecero datos de la tabla --%>

					<div class="bloque">
						<%--bloque datos tabla --%>
						<table class="formulario">
							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.identificador" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="id" /></td>
							</tr>



							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.descripcion.camposTabla.form.nombre" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="nombre" />
								</td>
							</tr>

							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.descripcion.camposTabla.form.norma" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="norma" />
								</td>
							</tr>
							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.descripcion.camposTabla.form.area" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="area" />
								</td>
							</tr>
							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.descripcion.camposTabla.form.etiquetaXml" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="etiquetaXml" />
								</td>
							</tr>

							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.descripcion.camposTabla.form.etiqXmlFila" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="etiqXmlFila" />
								</td>
							</tr>




							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.descripcion.camposTabla.form.descripcion" />:&nbsp;</td>
								<td class="tdDatos"><bean:write name="camposTablaForm"
										property="descripcion" />
								</td>
							</tr>
						</table>
					</div>
					<%--bloque datos tabla --%>


					<div class="separador8">&nbsp;</div>

					<div class="cabecero_bloque">
						<%--cabecero valores de la tabla --%>
						<table class="w98m1" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td class="etiquetaAzul12Bold"><bean:message
											key="archigest.archivo.cacceso.gestionCampoTabla.columnas" />
									</td>

									<td align="right">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<c:if test="${!empty valores}">
													<td><a class="etiquetaAzul11Normal"
														href="javascript:subirCampo()"> <html:img
																page="/pages/images/caja_subir.gif"
																styleClass="imgTextMiddle"
																altKey="archigest.archivo.subir"
																titleKey="archigest.archivo.subir" /> &nbsp;<bean:message
																key="archigest.archivo.subir" /> </a></td>
													<td width="10">&nbsp;</td>
													<td><a class="etiquetaAzul11Normal"
														href="javascript:bajarCampo()"> <html:img
																page="/pages/images/caja_bajar.gif"
																styleClass="imgTextMiddle"
																altKey="archigest.archivo.bajar"
																titleKey="archigest.archivo.bajar" /> &nbsp;<bean:message
																key="archigest.archivo.bajar" /> </a></td>
												</c:if>
												<td width="30"></td>
												<td><c:url var="URL" value="/action/gestionCampoTabla">
														<c:param name="method" value="newCampoDato" />
													</c:url> <a class="etiquetaAzul12Normal"
													href="<c:out value="${URL}" escapeXml="false" />"><html:img
															page="/pages/images/addDoc.gif"
															altKey="archigest.archivo.anadir"
															titleKey="archigest.archivo.anadir"
															styleClass="imgTextMiddle" />&nbsp;<bean:message
															key="archigest.archivo.anadir" />
												</a>
												</td>
												<td width="10"></td>
												<td><a class="etiquetaAzul12Normal"
													href="javascript:removeValues()"><html:img
															page="/pages/images/delDoc.gif"
															altKey="archigest.archivo.eliminar"
															titleKey="archigest.archivo.eliminar"
															styleClass="imgTextMiddle" />&nbsp;<bean:message
															key="archigest.archivo.eliminar" />
												</a>
												</td>
												<td width="10"></td>

											</tr>
										</table></td>

								</tr>
							</tbody>
						</table>
					</div>
					<%--cabecero valores de la tabla --%>

					<div class="bloque">
						<%--bloque valores tabla --%>
						<div class="separador1">&nbsp;</div>


						<c:if test="${!empty valores}">
							<div class="w100">
								<table class="w98" cellpadding="0" cellspacing="0">
									<tr>
										<TD width="100%" align="right">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td><a class="etiquetaAzul12Normal"
														href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].camposABorrar);">
															<html:img page="/pages/images/checked.gif" border="0"
																altKey="archigest.archivo.selTodos"
																titleKey="archigest.archivo.selTodos"
																styleClass="imgTextBottom" />
															<bean:message key="archigest.archivo.selTodos" />&nbsp;</a>
													</td>
													<td width="20">&nbsp;</td>
													<td><a class="etiquetaAzul12Normal"
														href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].camposABorrar);">
															<html:img page="/pages/images/check.gif" border="0"
																altKey="archigest.archivo.quitarSel"
																titleKey="archigest.archivo.quitarSel"
																styleClass="imgTextBottom" />&nbsp;<bean:message
																key="archigest.archivo.quitarSel" />
													</a>
													</td>
												</tr>
											</table></td>
									</tr>
								</table>
							</div>
							<div class="separador5">&nbsp;</div>
						</c:if>
						<c:set var="listaCamposDato"
							value="${sessionScope[appConstants.controlAcceso.LISTA_CAMPOS_DATO]}" />

						<c:url var="paginationURL"
							value="/action/gestionCampoTabla">
								<c:param name="method" value="retrieveFromList" />
								<c:param name="idCampo" value="${formBean.id}" />
						</c:url>
						<jsp:useBean id="paginationURL" type="java.lang.String" />


						<display:table name="pageScope.listaCamposDato"
							style="width:99%;margin-left:auto;margin-right:auto" id="campo"
							pagesize="15" requestURI='<%=paginationURL%>' export="true"
							excludedParams="*">

							<display:column style="width:10px" title="">
								<html-el:multibox property="camposABorrar" value="${campo.id}"></html-el:multibox>
							</display:column>

							<display:column
								titleKey="archigest.archivo.descripcion.camposDato.form.nombre"
								sortProperty="nombre" sortable="false" headerClass="sortable" media="html">
								<c:url var="verURL" value="/action/gestionCampoDato">
									<c:param name="method" value="retrieveFromList" />
									<c:param name="idCampo" value="${campo.id}" />
									<c:param name="fromCampoLista" value="true" />
								</c:url>
								<a class="tdlink"
									href="<c:out value="${verURL}" escapeXml="false"/>"> <c:out
										value="${campo.nombre}" /> </a>
							</display:column>


							<display:column
								titleKey="archigest.archivo.descripcion.camposDato.form.nombre" media="csv excel pdf xml">
								<c:out value="${campo.nombre}" />
							</display:column>
							<display:column
								titleKey="archigest.archivo.descripcion.camposDato.form.tipo"
								property="tipoText" />
							<display:column titleKey="archigest.archivo.identificador"
								property="id" sortable="true" headerClass="sortable" />
							<display:column titleKey="archigest.archivo.etiqueta"
								property="etiquetaXmlText" sortable="true"
								headerClass="sortable" />

						</display:table>

					</div>
					<%--bloque valores tabla --%>


				</div>
				<%--cuerpo_izda --%>
			</div>
			<%--cuerpo_drcha --%>

			<h2>
				<span>&nbsp;</span>
			</h2>
		</div>
		<%--ficha --%>
	</html:form>

</div>
<%--contenedor_ficha --%>


