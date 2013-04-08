<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="infoOrgano" value="${sessionScope[appConstants.controlAcceso.INFO_ORGANO]}" />
<bean:struts id="mappingGestionOrganos" mapping="/gestionOrganos" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${empty infoOrgano}">
				<bean:message key="archigest.archivo.cacceso.altaOrgano"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.cacceso.editarOrgano"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
	<script>
		function guardarOrgano() {
			var form = document.forms['<c:out value="${mappingGestionOrganos.name}" />'];
			form.submit();
		}
	</script>
		<table><tr>

		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:guardarOrgano()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
		</td>
		<td width="10">&nbsp;</td>
		<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
		<table class="formulario" width="99%">

		<html:form action="/gestionOrganos" >
			<input type="hidden" name="method" value="guardarOrgano">
			<input type="hidden" name="idOrgano" value="<c:out value="${infoOrgano.idOrg}" />">
			<html:hidden property="sistemaExterno" />
			<html:hidden property="idEnSistemaExterno" />
			<html:hidden property="nombreLargo" />

			<c:if test="${!empty organoForm.nombreLargo}">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="140px">
					<bean:message key="archigest.archivo.cacceso.organizacion"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<table cellpadding=0 cellspacing=0>
						<c:forTokens var="organo" items="${organoForm.nombreLargo}" delims="/" varStatus="nivel">
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

			<c:if test="${not empty infoOrgano}">
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
					    <html:hidden property="vigente"/>
				</td>
			</tr>
			</c:if>

			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.codigo"/>:
				</td>
				<td class="tdDatos">
					<c:out value="${organoForm.codigo}" />
					<html:hidden property="codigo" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.nombre"/>:
				</td>
				<td class="tdDatos"><html:text styleClass="input95" property="nombre" maxlength="254"/></td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.descripcion"/>:
				</td>
				<td class="tdDatos"><html:textarea styleClass="input95" style="height:40px" rows="4" property="descripcion" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" /></td>
			</tr>
		</table>

		<div class="separador8"></div>

		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" width="140px">
					<bean:message key="archigest.archivo.archivoReceptor"/>:
				</td>
				<td class="tdDatos">
				<c:set var="archivos" value="${requestScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />
				<c:choose>
					<c:when test="${!empty archivos[1]}">
					<html:select property="archivoReceptor">
						<html:options collection="archivos" property="id" labelProperty="nombre" />
					</html:select>
					</c:when>
					<c:otherwise>
						<c:set var="archivoReceptor" value="${archivos[0]}" />
						<c:out value="${archivoReceptor.nombre}" />
						<input type="hidden" name="archivoReceptor" value="<c:out value="${archivoReceptor.id}" />">
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</html:form>
		</table>
		</div>
	</tiles:put>
</tiles:insert>