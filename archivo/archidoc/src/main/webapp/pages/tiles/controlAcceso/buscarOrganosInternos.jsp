<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />
<c:set var="formBean" value="${sessionScope[mappingGestionUsuarios.name]}"/>
<c:set var="tipoUsuarioCfg" value="${sessionScope[appConstants.controlAcceso.TIPO_USUARIO_KEY]}"/>
<c:set var="listaOrganos" value="${sessionScope[appConstants.controlAcceso.LISTA_ORGANOS]}" />

<script>
	function seleccionarOrgano() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		form.method.value = 'seleccionarOrganoInterno';
		form.submit();
	}

	function buscar() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		form.method.value = 'buscarOrganoInterno';				
		form.submit();
	}
</script>

<html:form action="/gestionUsuarios" >
<input type="hidden" name="method" >

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.selOrgUser"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
		<tr>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:seleccionarOrgano()" >
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
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div id="barra_errores"><archivo:errors/></div>	
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.gcontrol.verusuario"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			
				<TABLE class="w98m1" cellpadding=0 cellspacing=2>
					<TR>
						<TD width="70%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.usuario"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${formBean.nombre}"/>
								<c:out value="${formBean.apellidos}"/>
							</span>
						</TD>
						<TD width="30%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.tipoUsuario"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${tipoUsuarioCfg.nombre}"/>
							</span>
						</TD>
					</TR>
				</TABLE>				
			</tiles:put>
		</tiles:insert>

		<div class="separador8"></div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.buscarEn"/>
				<bean:message key="archigest.archivo.cacceso.organosInternos"/>:
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
					<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/>
				</a>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="120px">
							<bean:message key="archigest.archivo.codigo"/>:
						</td>
						<td class="tdDatos">
							<html:text property="codigoOrgano" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.nombre"/>:
						</td>
						<td class="tdDatos">
							<html:text property="nombreOrgano" styleClass="input60"/>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<c:if test="${!empty listaOrganos}">
			<div class="separador8"></div>

			<div class="cabecero_bloque">
				<table class="w98" cellpadding=0 cellspacing=0 height="100%"><tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.resultadosBusqueda"/>
				</td>		
				</tr></table>
			</div>

			<div class="bloque" style="padding-top:8px;padding-bottom:8px">

				<table class="w98m1">
			    <TR><TD class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.cacceso.msgSelOrgUserInterno"/>
				</TD></TR>
				</table>
						<display:table name="pageScope.listaOrganos" 
								id="organo" 
								style="width:98%;margin-left:auto;margin-right:auto"
								sort="list"
								requestURI="/action/gestionUsuarios"
								pagesize="10">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.cacceso.msgNoOrgInternosBusqueda"/>
							</display:setProperty>
						
							
							<display:column style="width:10px">
									<input type="radio" name="idOrganoInterno" value="<c:out value="${organo.idOrg}" />" 
									<c:if test="${organo.idOrg == formBean.idOrganoInterno}"> checked </c:if> >
							</display:column>
							<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" style="width:10">
								<c:out value="${organo.codigo}"> -- </c:out>
							</display:column>	
							<display:column titleKey="archigest.archivo.nombre" property="nombre" sortable="true"/>							
							<display:column titleKey="archigest.archivo.archivoReceptor" property="archivoReceptor.nombre" sortable="true"/>
							<display:column titleKey="archigest.archivo.descripcion" property="descripcion" sortable="true"/>
							<display:column titleKey="archigest.archivo.cf.vigente" style="text-align:center">
								<c:choose>
								<c:when test="${organo.vigente == 'S'}">
									<html:img page="/pages/images/checkbox-yes.gif"  align="center" />
								</c:when>
								<c:otherwise>
									<html:img page="/pages/images/checkbox-no.gif"  align="center"/>
								</c:otherwise>
								</c:choose>
							</display:column>
						</display:table>

			</div>
		</c:if>
	</tiles:put>
</tiles:insert>


	</html:form>