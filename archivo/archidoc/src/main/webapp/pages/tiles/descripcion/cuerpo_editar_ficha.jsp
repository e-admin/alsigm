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

<bean:struts id="actionMapping" mapping="/gestionFicha" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="formBean" value="${sessionScope[actionMapping.name]}" />

<c:set var="frm" value="${sessionScope[formName]}" />
<c:set var="tiposNivel"
	value="${sessionScope[appConstants.descripcion.LISTA_TIPOS_NIVEL_KEY]}" />
<c:set var="mostrarSubtipos"
	value="${sessionScope[appConstants.fondos.MOSTRAR_SUBTIPOS]}" />
<c:set var="listaSubtipos"
	value="${sessionScope[appConstants.fondos.LISTA_SUBTIPOS_NIVEL]}" />



<script language="javascript">
	function exportar() {
		document.getElementById("method").value = "export";
		document.getElementById("formulario").submit();
	}

	function duplicar() {
		document.getElementById("method").value = "duplicar";
		document.getElementById("formulario").submit();
	}

	function edicionAvanzada() {
		document.getElementById("method").value = "edicionAvanzada";
		document.getElementById("formulario").submit();
	}
</script>


<div id="contenedor_ficha">
	<html:form action="/gestionFicha" styleId="formulario">
		<input type="hidden" id="method" name="method" />
		<div class="ficha">

			<h1>
				<span>
					<div class="w100">
						<table class="w98" cellpadding=0 cellspacing=0>
							<tr>
								<td class="etiquetaAzul12Bold" height="25px"><bean:message
										key="archigest.archivo.descripcion.fichas.form.fichas.caption" />
								</td>
								<td align="right">
									<table cellpadding=0 cellspacing=0>
										<tr>
											<c:if test="${not empty formBean.id}">
												<td><a class="etiquetaAzul12Bold"
													href="javascript:duplicar();"> <input type="image"
														src="../images/duplicarDoc.gif"
														alt="<bean:message key="archigest.fichas.crear.a.partir.de"/>"
														title="<bean:message key="archigest.fichas.crear.a.partir.de"/>"
														class="imgTextMiddle" />&nbsp;<bean:message
															key="archigest.fichas.crear.a.partir.de" />
												</a></td>
												<td width="10">&nbsp;</td>

												<c:if test="${formBean.conEdicionAvanzada}">
													<td><a class="etiquetaAzul12Bold"
														href="javascript:edicionAvanzada();"> <input
															type="image" src="../images/editarDoc.gif"
															alt="<bean:message key="archigest.fichas.edicion.avanzada"/>"
															title="<bean:message key="archigest.fichas.edicion.avanzada"/>"
															class="imgTextMiddle" />&nbsp;<bean:message
																key="archigest.fichas.edicion.avanzada" />
													</a></td>
													<td width="10">&nbsp;</td>
												</c:if>

												<td><c:url var="urlVerFormatos"
														value="/action/gestionFormatoFicha">
														<c:param name="method" value="verFormatosByFicha" />
														<c:param name="idFicha" value="${frm.id}" />
													</c:url> <a class="etiquetaAzul12Bold"
													href="<c:out value="${urlVerFormatos}" escapeXml="false"/>">
														<input type="image" src="../images/go.gif"
														alt="<bean:message key="archigest.archivo.menu.admin.VerFormatosFicha"/>"
														title="<bean:message key="archigest.archivo.menu.admin.VerFormatosFicha"/>"
														class="imgTextMiddle" />&nbsp;<bean:message
															key="archigest.archivo.menu.admin.VerFormatosFicha" />
												</a></td>
												<td width="10">&nbsp;</td>

											</c:if>
											<td><a class="etiquetaAzul12Bold"
												href="javascript:create(document.forms['fichasForm'],'<bean:message key='archigest.archivo.descripcion.fichas.form.nombre.empty.msg'/>','<bean:message key='archigest.archivo.descripcion.fichas.form.definicion.empty.msg'/>')"><input
													type="image" src="../images/Ok_Si.gif"
													alt="<bean:message key="archigest.archivo.aceptar"/>"
													title="<bean:message key="archigest.archivo.aceptar"/>"
													class="imgTextMiddle" />&nbsp;<bean:message
														key="archigest.archivo.aceptar" />
											</a></td>
											<td width="10">&nbsp;</td>
											<td><a class="etiquetaAzul12Bold"
												href="javascript:call(document.forms['fichasForm'],'goBack')"><html:img
														page="/pages/images/Ok_No.gif" border="0"
														altKey="archigest.archivo.cancelar"
														titleKey="archigest.archivo.cancelar"
														styleClass="imgTextMiddle" />&nbsp;<bean:message
														key="archigest.archivo.cancelar" />
											</a></td>
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



					<div class="bloque">
						<%--bloque datos tabla --%>
						<table class="formulario">
							<tr>
								<td class="tdTitulo" width="150"><bean:message
										key="archigest.archivo.identificador" />:&nbsp;</td>
								<td class="tdDatos"><html:hidden property="id" /> <c:choose>
										<c:when test="${empty formBean.id}">
											<html:text property="guid" maxlength="32" size="40" />
										</c:when>
										<c:otherwise>
											<c:out value="${formBean.id}" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>


							<tr>
								<td class="tdTitulo" width="180"><bean:message
										key="archigest.archivo.descripcion.fichas.form.nombre" />:&nbsp;</td>
								<td class="tdDatos"><html:text property="nombre" size="64"
										maxlength="64" />&nbsp;</td>
							</tr>

							<tr>
								<td class="tdTitulo"><bean:message
										key="archigest.archivo.descripcion.fichas.form.tipoNorma" />:&nbsp;</td>
								<td class="tdDatos"><html:select property="tipoNorma"
										styleClass="input"
										onchange="javascript:changeTipoNorma(document.forms['fichasForm']);">
										<html:optionsCollection
											name="DescripcionConstants_LISTA_TIPO_NORMAS_KEY"
											value="value" label="label" />
									</html:select></td>
							</tr>
							<tr>
								<td class="tdTitulo" nowrap="nowrap"><bean:message
										key="archigest.archivo.tipo" />:&nbsp;</td>
								<td class="tdDatos" nowrap="nowrap"><html-el:select
										property="tipoNivel"
										onchange="javascript:changeTipoNorma(document.forms['fichasForm']);">
										<html:option value="" />
										<html:optionsCollection
											name="DescripcionConstants_LISTA_TIPO_NIVELES_KEY"
											label="label" value="value" />
									</html-el:select>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<c:if test="${mostrarSubtipos}">
								<tr>
									<td class="tdTitulo" nowrap="nowrap"><bean:message
											key="archigest.archivo.subtipo" />
									</td>
									<td class="tdDatos"><html-el:select
											property="subTipoNivel">
											<html:option value="" />
											<html-el:optionsCollection
												name="FondosConstants_LISTA_SUBTIPOS_NIVEL" label="label"
												value="value" />
										</html-el:select></td>
								</tr>
							</c:if>
							<tr>
								<td class="tdTitulo" style="vertical-align: top;"><bean:message
										key="archigest.archivo.descripcion.fichas.form.descripcion" />:&nbsp;</td>
								<td class="tdDatos"><html:textarea property="descripcion"
										rows="2" onchange="maxlength(this,254,true)"
										onkeypress="maxlength(this,254,false)" />&nbsp;</td>
							</tr>
						</table>
					</div>
					<%--bloque datos tabla --%>

					<div class="separador5">&nbsp;</div>



					<div class="cabecero_bloque">
						<%--cabecero valores de la tabla --%>
						<table class="w98m1" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td>&nbsp;</td>
									<td align="right">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td><a class="etiquetaAzul12Bold"
													href="javascript:call(document.forms['fichasForm'],'import')"><input
														type="image" src="../images/importar.gif"
														alt="<bean:message key="archigest.archivo.importar"/>"
														title="<bean:message key="archigest.archivo.importar"/>"
														class="imgTextMiddle" />&nbsp;<bean:message
															key="archigest.archivo.importar" />
												</a></td>
												<td width="10">&nbsp;</td>
												<td><a class="etiquetaAzul12Bold"
													href="javascript:exportar()"> <input
														type="image" src="../images/exportar.gif"
														alt="<bean:message key="archigest.archivo.exportar"/>"
														title="<bean:message key="archigest.archivo.exportar"/>"
														class="imgTextMiddle" />&nbsp;<bean:message
															key="archigest.archivo.exportar" />
												</a></td>
												<td width="10">&nbsp;</td>
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



						<div class="w100">
							<table class="formulario" cellpadding="0" cellspacing="0">
								<tr>

									<td class="tdTitulo" width="150" style="vertical-align: top;"><bean:message
											key="archigest.archivo.descripcion.fichas.form.definicion" />:&nbsp;</td>
									<td class="tdDatos"><html:textarea property="definicion"
											rows="20" />&nbsp;</td>

								</tr>
							</table>
						</div>

					</div>
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


