<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="udoc" value="${sessionScope[appConstants.fondos.UDOC_KEY]}" />
<c:set var="listasControlAcceso" value="${requestScope[appConstants.fondos.LISTAS_CONTROL_ACCESO_KEY]}"/>

<bean:struts id="actionMapping" mapping="/gestionUdocsCF" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.editar"/><c:out value="${udoc.nombreNivel}"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td>
					<a class="etiquetaAzul12Bold"
						href="javascript:document.forms['<c:out value="${actionMapping.name}" />'].submit()" >
						<html:img page="/pages/images/Ok_Si.gif"
							altKey="archigest.archivo.aceptar"
							titleKey="archigest.archivo.aceptar"
							styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
			   <td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="separador1">&nbsp;</div>
		<div class="bloque_tab">
			<TABLE class="formulario">
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${udoc.codReferenciaPersonalizado}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.unidadDoc"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${udoc.numExp}" /> <c:out value="${udoc.titulo}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.cf.serie"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${udoc.serie.titulo}" />
					</TD>
				</TR>
				<TR>
					<TD><html:img page="/pages/images/pixel.gif" width="1" height="5"/></TD>
				</TR>
			</TABLE>
		</div>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cf.datosGestionVisualizacionAlmacenamiento"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<html:form action="/gestionUdocsCF">
				<html:hidden property="id"/>
				<input type="hidden" name="method" value="saveInfoUdoc"/>

				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<script>
								function checkListaControlAcceso()
								{
									var form = document.forms["<c:out value="${actionMapping.name}" />"];
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
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.listaControlAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<select style="width:100%;" name="idLCA" <c:if test="${UnidadDocumentalForm.nivelAcceso == 1}">disabled="true"</c:if>>
								<option value="">&nbsp;</option>
								<c:forEach var="lca" items="${listasControlAcceso}">
									<option value="<c:out value="${lca.id}"/>"
										<c:if test="${lca.id==UnidadDocumentalForm.idLCA}">selected="true"</c:if>
										>
										<c:out value="${lca.nombre}"/>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.cf.fichaDescAsociada"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${udoc.fichaDescriptiva.nombre}"> -- </c:out>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${udoc.repositorioEcm.nombre}" ></c:out>
						</td>
					</tr>
				</table>
				</html:form>
			</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>