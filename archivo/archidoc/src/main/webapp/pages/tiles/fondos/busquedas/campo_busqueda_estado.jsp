<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<security:permissions permission="${appConstants.permissions.CREACION_CUADRO_CLASIFICACION}">
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<table class="formulario">
			<tr>
				<td align="right">
					<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].estados);"
		 			><html:img page="/pages/images/checked.gif"
						    border="0"
						    altKey="archigest.archivo.selTodos"
						    titleKey="archigest.archivo.selTodos"
						    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
					&nbsp;
					<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].estados);"
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
					<html:multibox style="border:0" property="estados"><c:out value="${appConstants.fondos.ESTADO_NO_VIGENTE}"/></html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
					<bean:message key="archigest.archivo.cf.noVigente"/>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<html:multibox style="border:0" property="estados" styleId="estados"><c:out value="${appConstants.fondos.ESTADO_VIGENTE}"/></html:multibox>
				</td>
				<td class="etiquetaNegra12Normal">
					<bean:message key="archigest.archivo.cf.vigente"/>
				</td>
		</table>
	</td>
</tr>
</security:permissions>
