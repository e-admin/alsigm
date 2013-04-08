<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="infoOrgano" value="${requestScope[appConstants.controlAcceso.INFO_ORGANO]}" />
<bean:struts id="mappingGestionOrganos" mapping="/gestionOrganos" />

<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ORGANO}">

<c:if test="${infoOrgano.organoVigente}">
	<c:url var="organoNoVigenteURL" value="/action/gestionOrganos">
		<c:param name="method" value="pasarANoVigente" />
		<c:param name="idOrgano" value="${infoOrgano.idOrg}" />
	</c:url>

	<c:set var="organoVinculable" value="${infoOrgano.textoOrganizacionCodigo}"/>

	<script language="javascript">
		function noVigente(){
			if(window.confirm("<bean:message key="archigest.archivo.organo.pasar.a.no.vigente.msg"/>")){
				window.location="<c:out value="${organoNoVigenteURL}" escapeXml="false"/>";
			}
		}
	</script>
</c:if>



<c:if test="${infoOrgano.vinculable}">
	<c:url var="vincularOrganoURL" value="/action/gestionOrganos">
		<c:param name="method" value="vincularAOrganizacion" />
		<c:param name="idOrgano" value="${infoOrgano.idOrg}" />
	</c:url>

	<c:set var="organoVinculable" value="${infoOrgano.textoOrganizacionCodigo}"/>

	<script language="javascript">
		function vincularOrgano(){
			if(window.confirm("<bean:message key="archigest.archivo.vincular.organizacion.msg.ini"/><c:out value="${infoOrgano.textoOrganizacionCodigo}"/><bean:message key="archigest.archivo.vincular.organizacion.msg.fin"/>")){
				window.location="<c:out value="${vincularOrganoURL}" escapeXml="false"/>";
			}
		}
	</script>
</c:if>
</security:permissions>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.datosOrgano"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_ORGANO}">

		<c:if test="${infoOrgano.organoVigente}">
		<td nowrap="nowrap">
			<a class="etiquetaAzul12Bold" href="javascript:noVigente()" >
			<html:img page="/pages/images/no_vigente.gif" altKey="archigest.archivo.organo.pasar.a.no.vigente" titleKey="archigest.archivo.organo.pasar.a.no.vigente" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.organo.pasar.a.no.vigente"/></a>
		</td>
		<td width="10px"></td>
		</c:if>



		<c:if test="${infoOrgano.vinculable}">
		<td nowrap="nowrap">
			<a class="etiquetaAzul12Bold" href="javascript:vincularOrgano()" >
			<html:img page="/pages/images/table_relationship.gif" altKey="archigest.archivo.vincular.organizacion" titleKey="archigest.archivo.vincular.organizacion" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.vincular.organizacion"/></a>
		</td>
		<td width="10px"></td>
		</c:if>
		<td nowrap="nowrap">
			<c:url var="edicionURL" value="/action/gestionOrganos">
				<c:param name="method" value="edicionOrgano" />
				<c:param name="idOrgano" value="${infoOrgano.idOrg}" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${edicionURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.editar"/></a>
		</td>
		<td width="10px"></td>
		</security:permissions>
		<td nowrap>
			<tiles:insert definition="button.closeButton" />
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
		<table class="formulario" width="99%">
			<c:if test="${!empty infoOrgano.nombreLargo}">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="150px">
					<bean:message key="archigest.archivo.cacceso.organizacion"/>:&nbsp;
				</td>
				<td class="tdDatos">
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
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="tdTitulo">
					<bean:message key="organizacion.org.estado.vigente"/>:
				</td>
				<td class="tdDatos">
				  <c:choose>
				    <c:when test="${infoOrgano.organoVigente}">
				    <html:img page="/pages/images/checkbox-yes.gif"
				        altKey="archigest.archivo.si"
  				        titleKey="archigest.archivo.si"
				        styleClass="imgTextMiddle"/>
				    </c:when>
				    <c:otherwise>
				      <html:img page="/pages/images/checkbox-no.gif"
				        altKey="archigest.archivo.no"
  				        titleKey="archigest.archivo.no"
				        styleClass="imgTextMiddle"/>
				    </c:otherwise>
				    </c:choose>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.vinculado.organizacion"/>:
				</td>
				<td class="tdDatos"><c:out value="${infoOrgano.textoOrganizacion}"></c:out></td>
			</tr>

			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.codigo"/>:
				</td>
				<td class="tdDatos"><c:out value="${infoOrgano.codigo}" /></td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.nombre"/>:
				</td>
				<td class="tdDatos"><c:out value="${infoOrgano.nombre}" /></td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.descripcion"/>:
				</td>
				<td class="tdDatos"><c:out value="${infoOrgano.descripcion}"></c:out></td>
			</tr>
		</table>
		<div class="separador8"></div>
		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.archivoReceptor"/>:
				</td>
				<td class="tdDatos"><c:out value="${infoOrgano.archivoReceptor.nombre}" /></td>
			</tr>
		</table>
		</div>
		<div class="separador8"></div>


		<c:set var="listaUsuarios" value="${infoOrgano.usuariosAsociados}" />

		<c:if test="${not empty listaUsuarios}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="organizacion.org.usuariosasociados.vigentes"/>
			</tiles:put>

				<tiles:put name="buttonBar" direct="true">
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
						<display:table name="pageScope.listaUsuarios"
								id="usuario"
								style="width:99%; margin-top:10px; margin-bottom:10px;margin-left:auto;margin-right:auto"
								sort="list">
							<display:setProperty name="basic.msg.empty_list">
								<div class="separador8">&nbsp;</div>
								<bean:message key="organizacion.org.msgNoUsuariosOrgano"/>
								<div class="separador8">&nbsp;</div>
							</display:setProperty>
							<display:column titleKey="organizacion.user.nombre">

								<c:url var="verURL" value="/action/gestionUsuarios">
									<c:param name="method" value="verUsuario" />
									<c:param name="idUsuarioSeleccionado" value="${usuario.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
									<c:out value="${usuario.nombreYApellidos}" />
								</a>
							</display:column>
						</display:table>
				</tiles:put>
			</tiles:insert>
			</c:if>
	</tiles:put>
</tiles:insert>