<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />
<c:set var="formBean" value="${sessionScope[mappingGestionUsuarios.name]}"/>
<c:set var="vUsuario" value="${sessionScope[appConstants.controlAcceso.USUARIO_VER_USUARIO]}"/>

<c:choose>
<c:when test="${!vUsuario.usuarioSinSistemaOrganizacion}">
	<c:choose>
		<c:when test="${param.method == 'verUsuario' || param.method == 'pantallaVerUsuario'}">
			<c:set var="classTabDatosUsuario" value="tabActual" />
			<c:set var="classTabGruposUsuario" value="tabSel" />
			<c:set var="classTabRolesUsuario" value="tabSel" />
		</c:when>
		<c:when test="${param.method == 'verGruposUsuario'}">
			<c:set var="classTabDatosUsuario" value="tabSel" />
			<c:set var="classTabGruposUsuario" value="tabActual" />
			<c:set var="classTabRolesUsuario" value="tabSel" />
		</c:when>
		<c:when test="${param.method == 'verRolesUsuario'}">
			<c:set var="classTabDatosUsuario" value="tabSel" />
			<c:set var="classTabGruposUsuario" value="tabSel" />
			<c:set var="classTabRolesUsuario" value="tabActual" />
		</c:when>
	</c:choose>
</c:when>
<c:otherwise>
	<c:choose>
		<c:when test="${param.method == 'verGruposUsuario' || param.method == 'verUsuario' || param.method == 'pantallaVerUsuario'}">
			<c:set var="classTabGruposUsuario" value="tabActual" />
			<c:set var="classTabRolesUsuario" value="tabSel" />
		</c:when>
		<c:when test="${param.method == 'verRolesUsuario'}">
			<c:set var="classTabGruposUsuario" value="tabSel" />
			<c:set var="classTabRolesUsuario" value="tabActual" />
		</c:when>
	</c:choose>
</c:otherwise>
</c:choose>




<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.gcontrol.verusuario"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			<c:url var="eliminacionUsuarioURL" value="/action/gestionUsuarios">
				<c:param name="method" value="eliminarUsuario" />
				<c:param name="usuariosABorrar" value="${vUsuario.id}" />
			</c:url>
			function eliminarUsuario() {
				if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmUsuarioEliminar'/>"))
					window.location = '<c:out value="${eliminacionUsuarioURL}" escapeXml="false"/>';

			}
			function eliminarRole() {
				var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
				form.method.value = 'quitarRoleAUsuario';
				form.submit();
			}

			function eliminarGrupo() {
				var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
				form.method.value = 'quitarGrupoAUsuario';
				form.submit();
			}
		</script>

		<table>
		<tr>
			<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_USUARIO}">
			<td width="10px"></td>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarUsuario()" >
				<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.eliminar"/></a>
			</td>
			</security:permissions>
			<td width="10px"></td>
			<TD nowrap>
				<tiles:insert definition="button.closeButton" />
			</TD>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

	<div id="barra_errores">
			<archivo:errors />
	</div>

	<div class="separador1"></div>
	<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
			  <TR>
				<TD class="etiquetaAzul12Bold" width="40%" >
					<bean:message key="archigest.archivo.informacion"/>
				</TD>
				<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}">
			    <TD class="etiquetaAzul12Bold" align="right" >
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
						<TD nowrap>
							<c:url var="anadirURL" value="/action/gestionUsuarios">
								<c:param name="method" value="edicionUsuario" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${anadirURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.editar"/></a>
						<TD>
					</TR>
					</TABLE>
				</TD>
				</security:permissions>
			  </TR>
			</TABLE>
	</div>
	<div class="bloque"> <%--primer bloque de datos --%>
		<TABLE class="formulario" >
			<tr>
				<td class="tdTitulo" width="190px">
		  			<bean:message key="archigest.archivo.tipoUsuario"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${vUsuario.tipo}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="tdTitulo" width="190px">
					<bean:message key="archigest.archivo.nombre"/>:&nbsp;
				</td>
				<td class="tdDatos"><c:out value="${vUsuario.nombre}"/></td>
			</tr>
			<c:if test="${not vUsuario.administrador}">
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.apellidos"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${vUsuario.apellidos}"/></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.email"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${vUsuario.email}"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.direccion"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${vUsuario.direccion}"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" style="vertical-align:top;" width="190px">
						<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
					</td>
					<td class="tdDatos"><c:out value="${vUsuario.descripcion}"/></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.habilitado"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:choose><c:when test="${vUsuario.estaHabilitado}">
							<bean:message key="archigest.archivo.si"/>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.no"/>
						</c:otherwise></c:choose>
					</td>
				</tr>
			</c:if>
			<c:if test="${not vUsuario.administrador}">
				<TR>
					<td class="tdTitulo" width="190px">
						<bean:message key="archigest.archivo.cacceso.fechaMaxVigencia"/>:&nbsp;
					</td>
					<TD class="tdDatos">
					<c:if test="${empty vUsuario.FMaxVigencia}">-</c:if>
					<fmt:formatDate value="${vUsuario.FMaxVigencia}" pattern="${FORMATO_FECHA}"/>
					</TD>
				</TR>
			</c:if>
		</TABLE>
	</div>

	<div class="separador8">&nbsp;</div>

	<div class="cabecero_tabs">
		<table cellspacing="0" cellpadding=0>
			<tr>
				<c:if test="${!vUsuario.usuarioSinSistemaOrganizacion}">
			    	<td class="<c:out value="${classTabDatosUsuario}" />">
						<c:url var="verUsuarioURL" value="/action/gestionUsuarios">
							<c:param name="method" value="verUsuario" />
						</c:url>
						<a href="<c:out value="${verUsuarioURL}" escapeXml="false"/>"  class="textoPestana">
							<bean:message key="archigest.archivo.organo"/>
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
				</c:if>
				<c:if test="${not vUsuario.administrador}">
			    	<td class="<c:out value="${classTabGruposUsuario}" />">
						<c:url var="verGruposUsuarioURL" value="/action/gestionUsuarios">
							<c:param name="method" value="verGruposUsuario" />
						</c:url>
						<a href="<c:out value="${verGruposUsuarioURL}" escapeXml="false"/>" class="textoPestana">
							<bean:message key="archigest.archivo.grupos"/>
						</a>
				    </td>
				    <td width="5px">&nbsp;</td>
			    	<td class="<c:out value="${classTabRolesUsuario}" />">
						<c:url var="verRolesUsuarioURL" value="/action/gestionUsuarios">
							<c:param name="method" value="verRolesUsuario" />
						</c:url>
						<a href="<c:out value="${verRolesUsuarioURL}" escapeXml="false"/>" class="textoPestana">
							<bean:message key="archigest.archivo.cacceso.rolesPermisos"/>
						</a>
				    </td>
				</c:if>
			</tr>
		</table>
	</div>

	<div class="bloque_tab"> <%--BLOQUE DE LOS TABS --%>
		<DIV class="cabecero_bloque_tab">
			<c:choose>
				<c:when test="${param.method == 'verRolesUsuario'}">
					<c:url var="anadirURL" value="/action/gestionUsuarios">
						<c:param name="method" value="listadoRolesParaAnadir" />
					</c:url>
					<c:set var="imageAnadir" value="/pages/images/addDoc.gif"/>
					<c:set var="eliminarURL" value="javascript:eliminarRole()"/>
					<c:set var="imageDelete" value="/pages/images/delDoc.gif"/>
				</c:when>
				<c:when test="${param.method == 'verGruposUsuario'}">
					<c:url var="anadirURL" value="/action/gestionUsuarios">
						<c:param name="method" value="listadoGruposParaAnadir" />
					</c:url>
					<c:set var="imageAnadir" value="/pages/images/addDoc.gif"/>
					<c:set var="eliminarURL" value="javascript:eliminarGrupo()"/>
					<c:set var="imageDelete" value="/pages/images/delDoc.gif"/>
				</c:when>
			</c:choose>

			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TR>
			    <TD class="etiquetaAzul12Bold" align="right" >
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
					  	<c:choose>
							<c:when test="${param.method == 'verRolesUsuario' || param.method == 'verGruposUsuario'}">
									<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}">
									<TD nowrap>
										<jsp:useBean id="imageAnadir" type="java.lang.String" />
										<a class="etiquetaAzul12Bold" href="<c:out value="${anadirURL}" escapeXml="false"/>" >
										<html:img page="<%=imageAnadir%>" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
										<bean:message key="archigest.archivo.anadir"/></a>
									</TD>
									<td width="10px"></td>
									<td nowrap>
										<jsp:useBean id="imageDelete" type="java.lang.String" />
										<a class="etiquetaAzul12Bold" href="<c:out value="${eliminarURL}" escapeXml="false"/>" >
										<html:img page="<%=imageDelete%>" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
										<bean:message key="archigest.archivo.eliminar"/></a>
									</td>
									</security:permissions>
							</c:when>
						</c:choose>
					</TR>
					</TABLE>
				</TD>
		<html:form action="/gestionUsuarios" >
		<input type="hidden" name="method" value="editarUsuario">
			   </TR>
			</TABLE>

		</DIV> <%--CABECERO DEL BLOQUE TAB --%>
		<c:choose>

			<%--TAB de ORGANO --%>
			<c:when test="${param.method == 'verUsuario' || param.method == 'pantallaVerUsuario'}">
				<c:choose>
					<c:when test="${!vUsuario.usuarioSinSistemaOrganizacion}">

							<TABLE class="formulario" width="99%">

							<c:set var="infoOrganoEnSistema" value="${vUsuario.organoEnArchivo}"/>
							<c:choose>
								<c:when test="${!empty infoOrganoEnSistema}">
									<c:choose>
									<c:when test="${!empty infoOrganoEnSistema.nombreLargo}">
										<tr>
											<td class="tdTitulo" style="vertical-align:top;" width="190px">
												<bean:message key="archigest.archivo.cacceso.organizacion"/>:&nbsp;
											</td>
											<td class="tdDatos">
												<table cellpadding=0 cellspacing=0>
													<c:forTokens var="organo" items="${infoOrganoEnSistema.nombreLargo}" delims="/" varStatus="nivel">
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
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<TR>
											<TD class="etiquetaAzul12Normal" colspan="2" style="text-align:center;">
												<bean:message key="archigest.archivo.cacceso.msgNoInfoOrgano"/>
											</TD>
										</TR>
									</c:otherwise>
									</c:choose>
									<TR>
										<td class="tdTitulo" width="190px">
											<bean:message key="archigest.archivo.codigo"/>:&nbsp;
										</td>
										<td class="tdDatos"><c:out value="${infoOrganoEnSistema.codigo}"/></td>
									</TR>
									<TR>
										<td class="tdTitulo">
											<bean:message key="archigest.archivo.nombre"/>:&nbsp;
										</td>
										<td class="tdDatos" ><c:out value="${infoOrganoEnSistema.nombre}"/></td>
									</TR>
									<tr><td colspan="2">&nbsp;</td></tr>
									<TR>
										<td class="tdTitulo">
											<bean:message key="archigest.archivo.archivoReceptor"/>:&nbsp;
										</td>
										<td class="tdDatos" ><c:out value="${infoOrganoEnSistema.archivoReceptor.nombre}"/></td>
									</TR>
								</c:when>
								<c:otherwise>
									<td class="tdDatos">
										<bean:message key="archigest.gestion_usuarios.usuario_sin_organo"/>
									</td>
								</c:otherwise>
							</c:choose>
							<c:if test="${not vUsuario.administrador}">
								<c:set var="infoOrganoUsuario" value="${vUsuario.infoUsuarioEnOrgano}"/>
								<c:if test="${!empty infoOrganoUsuario}">
										<tr><td colspan="2">&nbsp;</td></tr>
										<TR>
											<td class="tdTitulo" width="190px">
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
															<TD class="tdDatos">
															<c:if test="${empty infoOrganoUsuario.fechaIni}">-</c:if>
															<fmt:formatDate value="${infoOrganoUsuario.fechaIni}" pattern="${FORMATO_FECHA}"/>
															</TD>
															<td width="20px">&nbsp;</td>
															<td class="tdTitulo" width="120px">
																<bean:message key="archigest.archivo.fechaFinal"/>:&nbsp;
															</td>
															<TD class="tdDatos">
															<c:if test="${empty infoOrganoUsuario.fechaFin}">-</c:if>
															<fmt:formatDate value="${infoOrganoUsuario.fechaFin}" pattern="${FORMATO_FECHA}"/>
															</TD>
														</TR>
												</TABLE>
											</TD>
										</TR>
								</c:if>
							</c:if>
							</TABLE>
					</c:when>
				</c:choose>
			</c:when>


				<%--TAB de  ROLES --%>
			<c:when test="${param.method == 'verRolesUsuario'}">
				<c:if test="${not vUsuario.administrador}">
					<div class="separador5">&nbsp;</div>
						<div class="separador5">&nbsp;</div>
						<c:set var="listaRoles" value="${requestScope[appConstants.controlAcceso.LISTA_ROLES_USUARIO]}" />
						<display:table name="pageScope.listaRoles"
								id="rol"
								style="width:99%;margin-left:auto;margin-right:auto"
								sort="list">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.cacceso.msgNoRolesUser"/>
							</display:setProperty>

							<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}">
							<display:column style="width:10px" title="">
									<input type="checkbox" name="rolesSeleccionados" value="<c:out value="${rol.id}" />" >
							</display:column>
							</security:permissions>

							<display:column titleKey="archigest.archivo.cacceso.perfil">
								<c:url var="verURL" value="/action/gestionRoles">
									<c:param name="method" value="verRol" />
									<c:param name="idRol" value="${rol.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
								<c:out value="${rol.nombre}" />
								</a>
							</display:column>
							<display:column titleKey="archigest.archivo.modulo">
								<fmt:message key="nombreModulo.modulo${rol.tipoModulo}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="150"/>
						</display:table>
						<div class="separador5">&nbsp;</div>

					<c:set var="listaPermisos" value="${requestScope[appConstants.controlAcceso.LISTA_TOTAL_PERMISOS]}" />

					<c:if test="${!empty listaPermisos}">
						<div class="separador5">&nbsp;</div>
						<display:table name="pageScope.listaPermisos"
								id="permiso"
								style="width:99%;margin-left:auto;margin-right:auto"
								sort="list"
								defaultsort="1">
							<display:column titleKey="archigest.archivo.cacceso.permisosRoles">
							<c:set var="permisoKey">archigest.archivo.permiso.<c:out value="${permiso.perm}"/></c:set>
								<fmt:message key="${permisoKey}" />
							</display:column>
						</display:table>
						<div class="separador5">&nbsp;</div>
					</c:if>
				</c:if>
			</c:when>

			<%-- TAB de GRUPOS --%>
			<c:when test="${param.method == 'verGruposUsuario'}">
				<c:if test="${not vUsuario.administrador}">
					<c:set var="listaGrupos" value="${requestScope[appConstants.controlAcceso.LISTA_GRUPOS_USUARIO]}" />
					<display:table name="pageScope.listaGrupos"
							id="grupo"
							style="width:99%;margin-left:auto;margin-right:auto"
							sort="list"
							pagesize="10"
							requestURI="/action/gestionUsuarios">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.cacceso.msgNoGrupoUser"/>
						</display:setProperty>

						<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}">
						<display:column style="width:10px" title="">
							<input type="checkbox" name="gruposSeleccionados" value="<c:out value="${grupo.id}" />" >
						</display:column>
						</security:permissions>


						<display:column titleKey="archigest.archivo.grupo">
							<c:url var="verURL" value="/action/gestionGrupos">
								<c:param name="method" value="verGrupo" />
								<c:param name="idGrupo" value="${grupo.id}" />
							</c:url>
							<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
							<c:out value="${grupo.nombre}" />
							</a>
						</display:column>
						<display:column titleKey="archigest.archivo.descripcion">
							<c:out value="${grupo.descripcion}" />
						</display:column>
					</display:table>
				</c:if>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.cacceso.msgUserNoPuedeTenerOrgano"/>
			</c:otherwise>
		</c:choose>
	</div> <%--BLOQUE DE LOS TABS --%>
<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>
