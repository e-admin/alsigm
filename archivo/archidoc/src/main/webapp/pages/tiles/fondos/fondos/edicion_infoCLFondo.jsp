<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="listasControlAcceso" value="${requestScope[appConstants.fondos.LISTAS_CONTROL_ACCESO_KEY]}"/>

<bean:struts id="mappingGestionCLFondos" mapping="/gestionClasificadorFondosAction" />
<c:set var="clfondoFormBean" value="${requestScope[mappingGestionCLFondos.name]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.editar" />
		<bean:message key="archigest.archivo.cf.clasificadorDe" />
		<bean:message key="archigest.archivo.cf.fondo" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionCLFondos.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURI" value="/action/gestionClasificadorFondosAction">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<html:form action="/gestionClasificadorFondosAction">
			<input type="hidden" name="method" value="guardarInfoCLFondo">

			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.cf.clasificadorDe" />
					<bean:message key="archigest.archivo.cf.fondo"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<div class="bloque">
						<table class="formulario">
							<tiles:insert definition="fondos.cf.infoClasificadorFondo" />
						</table>
					</div>
				</tiles:put>
			</tiles:insert>

			<div class="separador1">&nbsp;</div>
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.cf.datosGestionVisualizacion" />
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
					<TABLE class="formulario" cellpadding="0" cellspacing="0">
						<TR>
							<TD colspan="3">
								<TABLE class="formulario" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td class="tdTitulo" width="250px">
											<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
										</td>
										<td class="tdDatos">
											<script>
												function checkListaControlAcceso()
												{
													var form = document.forms["<c:out value="${mappingGestionCLFondos.name}" />"];
													if (form.nivelAcceso.value == 1)
														form.idLCA.disabled = true;
													else
														form.idLCA.disabled = false;
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
										<td class="tdTitulo">
											<bean:message key="archigest.archivo.listaControlAcceso"/>:&nbsp;
										</td>
										<td class="tdDatos">
											<select style="width:100%;" name="idLCA" <c:if test="${ClasificadorFondoForm.nivelAcceso == 1}">disabled="true"</c:if>>
												<option value="">&nbsp;</option>
												<c:forEach var="lca" items="${listasControlAcceso}">
													<option value="<c:out value="${lca.id}"/>"
														<c:if test="${lca.id==ClasificadorFondoForm.idLCA}">selected="true"</c:if>
														>
														<c:out value="${lca.nombre}"/>
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<div class="separador5">&nbsp;</div>
				</tiles:put>
			</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>