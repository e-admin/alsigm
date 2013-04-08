<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.numero"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<html:select property="numeroComp" styleId="numeroComp">
						<html:option key="archigest.archivo.simbolo.igual.texto" value="="/>
						<html:option key="archigest.archivo.simbolo.mayor.texto" value="&gt;"/>
						<html:option key="archigest.archivo.simbolo.menor.texto" value="&lt;"/>
						<html:option key="archigest.archivo.simbolo.mayoroigual.texto" value="&gt;="/>
						<html:option key="archigest.archivo.simbolo.menoroigual.texto" value="&lt;="/>
						<html:option key="archigest.archivo.simbolo.diferente.texto" value="!="/>
				    </html:select>
				</td>
			    <td width="10">&nbsp;</td>
				<td><html:text property="numero" styleId="numero" size="20"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<td class="tdDatos"><bean:message key="archigest.archivo.busqueda.form.tipoMedida"/>:</td>
				<td class="tdDatos" width="10">&nbsp;</td>
				<td class="tdDatos">
					<html:select property="tipoMedida" styleId="tipoMedida">
						<html:option key="" value=""/>
						<html:option key="archigest.archivo.medida.longitud" value="1"/>
						<html:option key="archigest.archivo.medida.peso" value="2"/>
						<html:option key="archigest.archivo.medida.volumen" value="3"/>
				    </html:select>
				</td>
			</tr>
			<tr>
				<td class="tdDatos"><bean:message key="archigest.archivo.busqueda.form.unidadMedida"/>:</td>
				<td class="tdDatos" width="10">&nbsp;</td>
				<td class="tdDatos"><html:text property="unidadMedida" styleId="unidadMedida" size="10" maxlength="16"/></td>
			</tr>
		</table>
	</td>
</tr>
