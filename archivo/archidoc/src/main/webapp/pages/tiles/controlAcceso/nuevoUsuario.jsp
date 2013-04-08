<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />
<c:set var="formBean" value="${sessionScope[mappingGestionUsuarios.name]}"/>
<c:set var="vUsuario" value="${sessionScope[appConstants.controlAcceso.USUARIO_VER_USUARIO]}"/>

<c:choose>
	<c:when test="${!empty vUsuario}">
		<c:set var="editando" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="editando" value="false"/>
	</c:otherwise>
</c:choose>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${editando}">
				<bean:message key="archigest.archivo.cacceso.editarUsuario"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.gcontrol.crearusuario"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">

	<script>
		function guardarUsuario() {
			var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
			<c:choose>
			<c:when test="${editando}">
				form.method.value = 'guardarEdicionUsuario';
			</c:when>
			<c:otherwise>
				form.method.value = 'guardarUsuario';
			</c:otherwise>
			</c:choose>
			form.submit();
		}

		function buscarUsuarios() {
			var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
			form.method.value = 'buscarUsuarios';
			form.submit();
		}

		function buscarOrganoInterno() {
			var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
			form.method.value = 'busquedaOrganoInterno';
			form.submit();
		}

		function eliminarOrganoInterno() {
			var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
			form.method.value = 'eliminarOrganoInterno';
			form.submit();
		}

		function cambiarTipoUsuario(){
			var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
			form.method.value = 'cambiarTipoUsuario';
			form.submit();
		}

	</script>

		<table><tr>
		<c:if test="${!empty usuarioForm.idUsrsExtGestor}">
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:guardarUsuario()" >
			<html:img page="/pages/images/Ok_Si.gif"
				altKey="archigest.archivo.aceptar"
				titleKey="archigest.archivo.aceptar"
				styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
		</td>
		<td width="10"></td>
		</c:if>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true">
				<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
				<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
			</tiles:insert>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<html:form action="/gestionUsuarios" >
		<input type="hidden" name="method" value="buscarUsuarios">

		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
			<TR>
		  		<TD class="etiquetaAzul12Bold">
		  			<bean:message key="archigest.archivo.cacceso.infoUser"/>&nbsp;
		  		</TD>
				<c:if test="${empty usuarioForm.idUsrsExtGestor}">
				<TD width="70%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
						<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionUsuarios.name}" />'].submit()">
							<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.buscarEn"/>
							<bean:message key="archigest.archivo.sistExterno"/>
						</a>
					</TD>
					<td width="10"></td>
				</TR>
				</TABLE>
				</TD>
				</c:if>
			</TR>
			</TABLE>
		</div>

		<div class="bloque">
			<div class="separador5">&nbsp;</div>

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.tipoUsuario"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:choose>
							<c:when test="${empty usuarioForm.idUsrsExtGestor}">
								<TABLE cellpadding=0 cellspacing=0>
								  <TR>
									<td >
										<c:set var="tiposUsuarios" value="${sessionScope[appConstants.controlAcceso.LISTA_TIPOS_USUARIOS]}" />
										<select name="tipoUsuario" onchange="javascript:cambiarTipoUsuario();">
											<c:forEach var="tipoUsuario" items="${tiposUsuarios}">
												<option value="<c:out value="${tipoUsuario.tipo}"/>"
													<c:if test="${tipoUsuario.tipo==param.tipoUsuario}">selected="true"</c:if>>
													<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${tipoUsuario.tipo}"/>
												</option>
											</c:forEach>
										</select>
									</td>
								 </TR>
								</TABLE>
							</c:when>
							<c:when test="${!empty vUsuario.tipo}">
								<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${vUsuario.tipo}"/>
								<html:hidden property="tipoUsuario"/>
							</c:when>
							<c:otherwise>
								<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${formBean.tipoUsuario}"/>
								<html:hidden property="tipoUsuario"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${!empty usuarioForm.idUsrsExtGestor}">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="190px">
							<bean:message key="archigest.archivo.nombre"/>:&nbsp;
						</td>
						<c:choose>
							<c:when test="${vUsuario.administrador}">
								<td class="tdDatos"><c:out value="${vUsuario.nombre}"/></td>
							</c:when>
							<c:otherwise>
								<td class="tdDatos"><html:text size="30" maxlength="254" property="nombre" /></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<c:if test="${not vUsuario.administrador}">
						<tr>
							<td class="tdTitulo" width="190px">
								<bean:message key="archigest.archivo.apellidos"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:text styleClass="input80"  maxlength="254" property="apellidos" /></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td class="tdTitulo" width="190px">
								<bean:message key="archigest.archivo.email"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:text styleClass="input80" maxlength="254" property="email" /></td>
						</tr>
						<tr>
							<td class="tdTitulo" width="190px">
								<bean:message key="archigest.archivo.direccion"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:text styleClass="input90"  maxlength="254" property="direccion" /></td>
						</tr>
						<tr>
							<td class="tdTitulo" style="vertical-align:top;" width="190px">
								<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:textarea rows="2" property="descripcion" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td class="tdTitulo" width="190px">
								<bean:message key="archigest.archivo.habilitado"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<script>
								function setDesactivado(){
									var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
									form.deshabilitado.value = !form.habilitado.checked;
								}
								</script>
								<input name="habilitado" class="checkbox" type="checkbox"<c:if test="${formBean.deshabilitado=='false'}" > checked </c:if>
								onclick="javascript:setDesactivado()"
								>
								<input id="idDesahibilitado" name="deshabilitado" type="hidden" value="<c:out value="${formBean.deshabilitado}" />">
							</td>
						</tr>
						<TR>
							<td class="tdTitulo" width="190px">
								<bean:message key="archigest.archivo.cacceso.fechaMaxVigencia"/>:&nbsp;
							</td>
							<TD class="tdDatos">
								<html:text property="fechaMaxVigencia" styleClass="input" size="12" maxlength="10"/>
								&nbsp;<archigest:calendar
									image="../images/calendar.gif"
				                    formId="usuarioForm"
				                    componentId="fechaMaxVigencia"
				                    format="dd/mm/yyyy"
				                    enablePast="true" />
							</TD>
						</TR>
					</c:if>
				</c:if>
			</TABLE>

		</div>


		<c:choose>
			<c:when test="${editando}">
				<c:set var="infoOrgano" value="${vUsuario.organoExterno}"/>
			</c:when>
			<c:otherwise>
				<c:set var="infoOrgano" value="${sessionScope[appConstants.controlAcceso.INFO_ORGANO_EXTERNO]}"/>
			</c:otherwise>
		</c:choose>

		<div class="separador8">&nbsp;</div>


		<c:choose>
		<c:when test="${!empty infoOrgano}">


		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				<TR>
			  		<TD class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.cacceso.OrgUser"/>
					</TD>
				</TR>
			</TABLE>
		</div>

		<div class="bloque">

			<div class="separador1">&nbsp;</div>

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<TR>
					<td class="tdTitulo" width="190px" style="vertical-align:top;">
						<bean:message key="archigest.archivo.cacceso.organizacion"/>:&nbsp;
					</td>
					<TD class="tdDatos">
						<table cellpadding=0 cellspacing=0>
							<c:forTokens var="organo" items="${infoOrgano.nombreLargo}" delims="/" varStatus="nivel">
							<tr>
								 <td style="vertical-align:top;">
									<span style="padding-left:<c:out value="${(nivel.count-1)*10}px"/>;" class="user_data">
			  						    <c:choose>
			  						    	<c:when test="${nivel.first && nivel.last}"/>
											<c:when test="${nivel.first}">
												<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="6px"/>
												<html:img page="/pages/images/padre.gif" styleClass="imgTextMiddle"/>
										    </c:when>
											<c:when test="${nivel.last}">
												<html:img page="/pages/images/descendiente_last.gif" styleClass="imgTextMiddle"/>
										    </c:when>
										  	<c:otherwise>
												<html:img page="/pages/images/descendiente.gif" styleClass="imgTextMiddle"/>
											</c:otherwise>
										</c:choose>
										<c:out value="${organo}" />
									</span>
								</td>
							 </tr>
							</c:forTokens>
						</table>
					</TD>
				</TR>
				<TR>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.codigo"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${infoOrgano.codigo}"/></td>
				</TR>
				<TR>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${infoOrgano.nombre}"/></td>
				</TR>
			</TABLE>
		</div>

		</c:when>

		<c:otherwise>
			<c:choose>
				<c:when test="${editando}">
					<c:set var="puedeTenerOrganoInterno" value="${vUsuario.usuarioConOrganoInterno}"/>
				</c:when>
				<c:otherwise>
					<c:set var="puedeTenerOrganoInterno" value="${sessionScope[appConstants.controlAcceso.SELECCIONAR_ORGANO_INTERNO_ENABLED] && usuarioForm.idUsrsExtGestor!=null && usuarioForm.idUsrsExtGestor!='' }"/>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${puedeTenerOrganoInterno}">

				<div class="cabecero_bloque">
					<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
						<TR>
					  		<TD class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.cacceso.OrgUser"/>
					  		</TD>
							<TD width="50%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
									  <TR>
									  <c:set var="infoOrganoInterno" value="${sessionScope[appConstants.controlAcceso.INFO_ORGANO_INTERNO]}"/>
										<c:if test="${vUsuario.administrador && (not empty infoOrganoInterno)}">
											<TD>
												<a class="etiquetaAzul12Bold" href="javascript:eliminarOrganoInterno()">
													<html:img altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" page="/pages/images/clear0.gif" styleClass="imgTextMiddle" />
													<bean:message key="archigest.archivo.eliminar"/>
												</a>
											</TD>
											<td width="10"></td>
										 </c:if>
										<TD>
										<a class="etiquetaAzul12Bold" href="javascript:buscarOrganoInterno()">
											<html:img altKey="archigest.archivo.seleccionar" titleKey="archigest.archivo.seleccionar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
											<bean:message key="archigest.archivo.seleccionar"/>
										</a>
										</TD>
										<td width="10"></td>
									 </TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
				</div>


				<div class="bloque">

					<table class="formulario" cellpadding="0" cellspacing="0">
					<c:set var="infoOrganoInterno" value="${sessionScope[appConstants.controlAcceso.INFO_ORGANO_INTERNO]}"/>
					<c:choose>
							<c:when test="${empty infoOrganoInterno}">
								<TR>
									<td width="15px">&nbsp;</td>
									<td class="tdDatos" >
										<bean:message key="archigest.archivo.cacceso.msgSelOrgUserInterno"/>
									</td>
								</TR>
							</c:when>
							<c:otherwise>
								<TR>
									<td class="tdTitulo" width="190px">
										<bean:message key="archigest.archivo.nombre"/>
									</td>
									<td class="tdDatos" >
										<c:out value="${infoOrganoInterno.nombre}"/>
										<input type="hidden" name="idOrganoInterno"
											value="<c:out value="${infoOrganoInterno.idOrg}"/>"/>
									</td>
								</TR>
								<TR>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.archivoReceptor"/>
									</td>
									<td class="tdDatos" ><c:out value="${infoOrganoInterno.archivoReceptor.nombre}"/></td>
								</TR>
								<tr>
								<td colspan="2">&nbsp;</td>
								</tr>
								<c:if test="${not vUsuario.administrador}">
									<TR>
										<td class="tdTitulo">
											<bean:message key="archigest.archivo.cacceso.validezUser"/>:&nbsp;
										</td>
										<td>&nbsp;</td>
									</TR>
									<TR>
										<td>&nbsp;</td>
										<TD>
											<TABLE cellpadding=0 cellspacing=0 >
													<TR>
														<td width="20px">&nbsp;</td>
														<td class="tdTitulo" width="120px">
															<bean:message key="archigest.archivo.fechaInicial"/>:&nbsp;
														</td>
														<TD><html:text property="fechaIniPeriodoValidez" styleClass="input" size="12" maxlength="10"/>
														&nbsp;<archigest:calendar
															image="../images/calendar.gif"
										                    formId="usuarioForm"
										                    componentId="fechaIniPeriodoValidez"
										                    format="dd/mm/yyyy"
										                    enablePast="true" /></TD>
														<td width="20px">&nbsp;</td>
														<td class="tdTitulo" width="120px">
															<bean:message key="archigest.archivo.fechaFinal"/>:&nbsp;
														</td>
														<TD><html:text property="fechaFinPeriodoValidez" styleClass="input" size="12" maxlength="10"/>
														&nbsp;<archigest:calendar
															image="../images/calendar.gif"
										                    formId="usuarioForm"
										                    componentId="fechaFinPeriodoValidez"
										                    format="dd/mm/yyyy"
										                    enablePast="true" /></TD>
													</TR>
											</TABLE>
										</TD>
									</TR>
								</c:if>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				</c:when>

			</c:choose>
		</c:otherwise>
		</c:choose>
		<div style="display:none;">
		</html:form>
		</div>
	</tiles:put>
</tiles:insert>

