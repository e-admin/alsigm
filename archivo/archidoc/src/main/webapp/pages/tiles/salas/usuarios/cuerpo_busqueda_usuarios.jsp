<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/gestionUsuarioSalasConsultaAction" />
<c:set var="formBean" value="${requestScope[actionMapping.name]}"/>
<c:set var="listaArchivosSeleccionar" value="${requestScope[appConstants.salas.LISTA_ARCHIVOS_SELECCIONAR_KEY]}"/>
<c:set var="informeUsuarios" value="${sessionScope[appConstants.salas.INFORME_USUARIOS_KEY]}"/>
<c:set var="buscadorUsuarios" value="${sessionScope[appConstants.salas.BUSCADOR_USUARIOS_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.salas.usuarios.busqueda"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.salasActions.VER_USUARIOS_ACTION}">
				<td nowrap>
					<script>
						function imprimirInforme(){
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.method.value= 'imprimirBusqueda';
							form.submit();
						}

						function buscarUsuarios(){
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.submit();
						}
					</script>
					<c:choose>
						<c:when test="${informeUsuarios}">
							<a class="etiquetaAzul12Bold"
								href="javascript:imprimirInforme()">
								<html:img page="/pages/images/documentos/doc_pdf.gif"
								titleKey="archigest.archivo.imprimir"
								altKey="archigest.archivo.imprimir"
								styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.imprimir"/>
							</a>
						</c:when>
						<c:otherwise>
							<a class="etiquetaAzul12Bold"
								href="javascript:buscarUsuarios()">
								<html:img page="/pages/images/Ok_Si.gif"
								titleKey="archigest.archivo.aceptar"
								altKey="archigest.archivo.aceptar"
								styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.aceptar"/>
							</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<c:choose>
						<c:when test="${buscadorUsuarios}">
							<a class="etiquetaAzul12Bold" href="javascript:window.close();">
								<html:img page="/pages/images/close.gif"
			                              altKey="archigest.archivo.cerrar"
			                              titleKey="archigest.archivo.cerrar"
			                              styleClass="imgTextMiddle" />&nbsp;
			                    <bean:message key="archigest.archivo.cerrar"/>
			                </a>
						</c:when>
						<c:otherwise>
							<tiles:insert definition="button.closeButton" flush="true"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/gestionUsuarioSalasConsultaAction">
				<html:hidden property="id" />
				<input type="hidden" name="method" value="buscarUsuarios">

				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.archivo"/>:</td>
						<td class="tdDatos">
							<c:set var="archivos" value="${sessionScope[appConstants.salas.LISTA_ARCHIVOS_KEY]}" />
							<html:select property="idArchivo">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="archivos" property="id" labelProperty="nombre"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td width="200px" class="tdTitulo">
							<fmt:message key="archigest.archivo.doc.identificativo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property='numDocIdentificacion' styleClass="input" maxlength="32" />
							<c:set var="tiposDoc" value="${sessionScope[appConstants.salas.LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY]}" />
							<html:select property="tipoDocIdentificacion">
								<html:option value=""></html:option>
								<html:options collection="tiposDoc" property="id" labelProperty="nombre" />
							</html:select>
						</td>
					</tr>

					<tr>
						<td  class="tdTitulo">
							<fmt:message key="archigest.archivo.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property='nombre' styleClass="input80" maxlength="64" />
						</td>
					</tr>

					<tr>
						<td  class="tdTitulo">
							<fmt:message key="archigest.archivo.apellidos"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property='apellidos' styleClass="input99" maxlength="254" />
						</td>
					</tr>


					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.usuario.vigente"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:set var="listaSiNo" value="${sessionScope[appConstants.salas.LISTA_VACIO_SI_NO]}" />
							<html:select property="vigenteSearch">
								<html:options collection="listaSiNo" property="key" labelProperty="value" />
							</html:select>
						</td>
					</tr>

					<bean:define id="title" value="archigest.archivo.salas.usuario.fechaAlta" toScope="request"/>
					<bean:define id="fechaComp" value="fechaCompAlta" toScope="request"/>
					<bean:define id="fechaFormato" value="fechaFormatoAlta" toScope="request"/>
					<bean:define id="fechaD" value="fechaDAlta" toScope="request"/>
					<bean:define id="fechaM" value="fechaMAlta" toScope="request"/>
					<bean:define id="fechaA" value="fechaAAlta" toScope="request"/>
					<bean:define id="fechaS" value="fechaSAlta" toScope="request"/>

					<bean:define id="fechaIniFormato" value="fechaIniFormatoAlta" toScope="request"/>
					<bean:define id="fechaIniD" value="fechaIniDAlta" toScope="request"/>
					<bean:define id="fechaIniM" value="fechaIniMAlta" toScope="request"/>
					<bean:define id="fechaIniA" value="fechaIniAAlta" toScope="request"/>
					<bean:define id="fechaIniS" value="fechaIniSAlta" toScope="request"/>

					<bean:define id="fechaFinFormato" value="fechaFinFormatoAlta" toScope="request"/>
					<bean:define id="fechaFinD" value="fechaFinDAlta" toScope="request"/>
					<bean:define id="fechaFinM" value="fechaFinMAlta" toScope="request"/>
					<bean:define id="fechaFinA" value="fechaFinAAlta" toScope="request"/>
					<bean:define id="fechaFinS" value="fechaFinSAlta" toScope="request"/>

					<bean:define id="suffix" value="Alta" toScope="request"/>
					<bean:define id="idTr" value="fechaAlta" toScope="request"/>

					<tiles:insert name="salas.campo.fecha.registro" flush="true"/>
				</table>
			</html:form>
		</div>
	</tiles:put>
</tiles:insert>
