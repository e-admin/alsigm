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

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.salas.temas.investigacion"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<script>
						function imprimirInforme(){
							var frm = document.forms['<c:out value="${actionMapping.name}" />'];
							frm.submit();
						}

						function cleanUsuario(){
							var frm = document.forms['<c:out value="${actionMapping.name}" />'];
							frm.nombre.value = "";
							frm.id.value = "";
						}

						function popupRegistros() {
							popup('<c:url value="/action/gestionUsuarioSalasConsultaAction"/>?method=buscadorUsuarios', "_blank");
						}
					</script>
					<a class="etiquetaAzul12Bold"
						href="javascript:imprimirInforme()">
						<html:img page="/pages/images/documentos/doc_pdf.gif"
						titleKey="archigest.archivo.imprimir"
						altKey="archigest.archivo.imprimir"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.imprimir"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/gestionUsuarioSalasConsultaAction">
				<html:hidden property="id" styleId="id"/>
				<input type="hidden" name="method" value="imprimirTemasInvestigacion">

				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.usuario"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property="nombre" styleId="nombre" styleClass="inputRO" size="80" readonly="true" />
							<a class="etiquetaAzul12Bold" href="javascript:popupRegistros();" >
								<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
							</a>
							<a href="javascript:cleanUsuario();">
								<html:img styleId="imgClear" page="/pages/images/clear0.gif" styleClass="imgTextMiddle"/>
							</a>
						</td>
					</tr>
				</table>
			</html:form>
		</div>
	</tiles:put>
</tiles:insert>