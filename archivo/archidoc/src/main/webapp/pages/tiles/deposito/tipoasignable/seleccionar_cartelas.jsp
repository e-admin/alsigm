<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}"/>
<c:set var="numHuecosOcupados" value="${requestScope[appConstants.deposito.NUM_HUECOS_OCUPADOS_KEY]}"/>
<c:set var="selHuecos" value="${param['selHuecos']}"/>

<bean:struts id="actionMapping" mapping="/gestionTipoAsignableAction" />

<script>
	function generarCartelas()
	{
		var huecosOcupados = <c:out value="${numHuecosOcupados}" />;
		if(huecosOcupados > 0){
			var form = document.forms["<c:out value="${actionMapping.name}" />"];
			if (form.selHuecos[0].checked)
			{
				form.huecos.value = "*";
				form.submit();
			}
			else
			{
				if (form.textHuecos.value == "")
					alert("<bean:message key="archigest.archivo.deposito.seleccionarHuecos.selAlgunosHuecos.vacio"/>");
				else
				{
					form.huecos.value = form.textHuecos.value;
					form.submit();
				}
			}
		}else{
			alert('<bean:message key="archigest.archivo.deposito.noHuecosOcupados"/>');
		}
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.generacionCartelas"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   	<td>
				<a class="etiquetaAzul12Bold"
				   href="javascript:generarCartelas()"
				><html:img page="/pages/images/cartela.gif"
				        border="0"
				        altKey="archigest.archivo.deposito.generarCartelas"
				        titleKey="archigest.archivo.deposito.generarCartelas"
				        styleClass="imgTextBottom"/>&nbsp;
		        <bean:message key="archigest.archivo.deposito.generarCartelas"/></a>
			</td>
			<td width="10px"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" />
			</td>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div id="barra_errores"><archivo:errors /></div>
		<tiles:insert page="/pages/tiles/deposito/tipoasignable/datos_tipoasignable.jsp" />
		<div class="separador8">&nbsp;</div>

		<div class="bloque">
			<TABLE class="formulario" cellpadding="0" cellspacing="6">
				<TR>
					<TD class="tdTitulo" width="280px">
						<bean:message key="archigest.archivo.deposito.seleccionarHuecos.numHuecosOcupados"/>:&nbsp;
					</TD>
					<TD class="tdDatosBold">
						<c:out value="${numHuecosOcupados}"/>
					</TD>
				</TR>
			</TABLE>
			<div class="separador5">&nbsp;</div>
			<html:form action="/gestionTipoAsignableAction">
				<input type="hidden" name="method" value="verCartelas"/>
				<input type="hidden" name="huecos"/>
				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td class="tdTitulo" colspan="3">
							<bean:message key="archigest.archivo.deposito.seleccionarHuecos.caption" />:
						</td>
					</tr>
				</table>
				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td width="50px"></td>
						<td width="20px">
							<input type="radio" name="selHuecos" value="1" style="border:0;"
								<c:if test="${(empty selHuecos) or (selHuecos=='1')}">
									checked="true"
								</c:if>
							/></td>
						<td class="etiquetaAzul11Bold">
							&nbsp;<bean:message key="archigest.archivo.deposito.seleccionarHuecos.selTodosHuecos"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="radio" name="selHuecos" value="2" style="border:0;"
							<c:if test="${(!empty selHuecos) && (selHuecos=='2')}">
								checked="true"
							</c:if>
						/></td>
						<td class="etiquetaAzul11Bold">
							&nbsp;<bean:message key="archigest.archivo.deposito.seleccionarHuecos.selAlgunosHuecos"/>:
							&nbsp;&nbsp;&nbsp;
							<input type="text" name="textHuecos" class="input" onKeyPress="javascript:document.forms['<c:out value="${actionMapping.name}" />'].selHuecos[1].checked=true;"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td class="etiquetaAzul11Normal">
							<bean:message key="archigest.archivo.deposito.seleccionarHuecos.msgSelAlgunosHuecos"/>
						</td>
					</tr>

				</table>
			</html:form>
		 </div>

		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>
