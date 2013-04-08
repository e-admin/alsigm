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
			<bean:message key="archigest.archivo.salas.resumen.servicios"/>
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
				<input type="hidden" name="method" value="imprimirResumenServicios">

				<table class="formulario">
					<bean:define id="title" value="archigest.archivo.fecha" toScope="request"/>
					<bean:define id="fechaComp" value="fechaCompExp" toScope="request"/>
					<bean:define id="fechaFormato" value="fechaFormatoExp" toScope="request"/>
					<bean:define id="fechaD" value="fechaDExp" toScope="request"/>
					<bean:define id="fechaM" value="fechaMExp" toScope="request"/>
					<bean:define id="fechaA" value="fechaAExp" toScope="request"/>
					<bean:define id="fechaS" value="fechaSExp" toScope="request"/>

					<bean:define id="fechaIniFormato" value="fechaIniFormatoExp" toScope="request"/>
					<bean:define id="fechaIniD" value="fechaIniDExp" toScope="request"/>
					<bean:define id="fechaIniM" value="fechaIniMExp" toScope="request"/>
					<bean:define id="fechaIniA" value="fechaIniAExp" toScope="request"/>
					<bean:define id="fechaIniS" value="fechaIniSExp" toScope="request"/>

					<bean:define id="fechaFinFormato" value="fechaFinFormatoExp" toScope="request"/>
					<bean:define id="fechaFinD" value="fechaFinDExp" toScope="request"/>
					<bean:define id="fechaFinM" value="fechaFinMExp" toScope="request"/>
					<bean:define id="fechaFinA" value="fechaFinAExp" toScope="request"/>
					<bean:define id="fechaFinS" value="fechaFinSExp" toScope="request"/>

					<bean:define id="suffix" value="Exp" toScope="request"/>
					<bean:define id="idTr" value="fechaExp" toScope="request"/>

					<tiles:insert name="salas.campo.fecha.registro" flush="true"/>
				</table>
			</html:form>
		</div>
	</tiles:put>
</tiles:insert>
