<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script>
	function selectByName(form)
	{
		form.thirdPartySearchType[0].checked = true;
		form.companySearchToken.value = "";
		form.ifSearchToken.value = "";
	}
	function selectByCompany(form)
	{
		form.thirdPartySearchType[1].checked = true;
		form.nameSearchToken.value = "";
		form.surname1SearchToken.value = "";
		form.surname2SearchToken.value = "";
		form.ifSearchToken.value = "";
	}
	function selectByIF(form)
	{
		form.thirdPartySearchType[2].checked = true;
		form.nameSearchToken.value = "";
		form.surname1SearchToken.value = "";
		form.surname2SearchToken.value = "";
		form.companySearchToken.value = "";
	}
</script>

<div class="titulo_left_bloque">
	<bean:message key="archigest.archivo.buscarPor" />:&nbsp;&nbsp;
</div>

<table class="formulario" cellpadding="0" cellspacing="0">
	<tr>
		<td class="tdTitulo" colspan="2">
			<html:radio property="thirdPartySearchType" value="NOMBRE" styleClass="radio"/>
			<bean:message key="archigest.archivo.transferencias.nombreApellidos"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="padding-left:30px">
			<table class="formulario" cellpadding=0 cellspacing=0>
				<tr>
					<td class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.nombre" />:
					</td>
					<td class="tdDatos">
						<html:text property="nameSearchToken" size="20"
							onclick="javascript:selectByName(this.form)"  />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.PrimerApellido" />:
					</td>
					<td class="tdDatos">
						<html:text property="surname1SearchToken" size="40"
							onclick="javascript:selectByName(this.form)" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.SegundoApellido" />:
					</td>
					<td class="tdDatos">
						<html:text property="surname2SearchToken" size="40"
							onclick="javascript:selectByName(this.form)" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table class="formulario" cellpadding=0 cellspacing=0>
	<tr>
		<td class="tdTitulo" width="250px">
			<html:radio property="thirdPartySearchType" value="RSOCIAL" styleClass="radio"/>
			<bean:message key="archigest.archivo.razonSocial"/>:
		</td>
		<td class="tdDatos" align="left">
			<html:text property="companySearchToken" size="60"
				onclick="javascript:selectByCompany(this.form)"  />
		</td>
	</tr>
</table>
<table class="formulario" cellpadding=0 cellspacing=0>
	<tr>
		<td class="tdTitulo" width="250px">
			<html:radio property="thirdPartySearchType" value="IF" styleClass="radio"/>
			<bean:message key="archigest.archivo.transferencias.numIdentidad"/>:
		</td>
		<td class="tdDatos" align="left">
			<html:select property="tipoNumeroIdentificacion">
				<html:option value="6"><bean:message key="archigest.archivo.transferencias.terceros.6"/></html:option>
				<html:option value="7"><bean:message key="archigest.archivo.transferencias.terceros.7"/></html:option>
				<html:option value="8"><bean:message key="archigest.archivo.transferencias.terceros.8"/></html:option>
				<html:option value="9"><bean:message key="archigest.archivo.transferencias.terceros.9"/></html:option>
			</html:select>

			<html:text property="ifSearchToken" size="15"
				onclick="javascript:selectByIF(this.form)"  />
		</td>
	</tr>
</table>
