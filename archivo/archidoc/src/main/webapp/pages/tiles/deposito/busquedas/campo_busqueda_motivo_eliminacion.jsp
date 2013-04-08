<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.deposito.motivo.eliminacion.ui"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<table class="formulario">
			<tr>
				<td align="right">
					<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].motivos);" 
		 			><html:img page="/pages/images/checked.gif" 
						    border="0" 
						    altKey="archigest.archivo.selTodos"
						    titleKey="archigest.archivo.selTodos"
						    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
					&nbsp;
					<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].motivos);" 
		 			><html:img page="/pages/images/check.gif" 
						    border="0" 
						    altKey="archigest.archivo.quitarSel"
						    titleKey="archigest.archivo.quitarSel"
						    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
					&nbsp;&nbsp;
			   </td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<html:multibox style="border:0" property="motivos" styleId="motivos">1</html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
					<fmt:message key="archigest.archivo.deposito.motivo.eliminacion.ui.1"/>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<html:multibox style="border:0" property="motivos" styleId="motivos">2</html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
					<fmt:message key="archigest.archivo.deposito.motivo.eliminacion.ui.2"/>
				</td>
			<tr>
			<tr>
				<td>
					<html:multibox style="border:0" property="motivos" styleId="motivos">3</html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
					<fmt:message key="archigest.archivo.deposito.motivo.eliminacion.ui.3"/>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<html:multibox style="border:0" property="motivos" styleId="motivos">4</html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
					<fmt:message key="archigest.archivo.deposito.motivo.eliminacion.ui.4"/>
				</td>
			<tr>
			<tr>
				<td>
					<html:multibox style="border:0" property="motivos" styleId="motivos">5</html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
				 	<fmt:message key="archigest.archivo.deposito.motivo.eliminacion.ui.5"/>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					&nbsp;
				</td>
				<td class="etiquetaNegra12Normal">
					&nbsp;
				</td>
			<tr>
		</table>
	</td>
</tr>
