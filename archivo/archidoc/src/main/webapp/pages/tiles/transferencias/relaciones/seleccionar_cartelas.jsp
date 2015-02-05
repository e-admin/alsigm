<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="numUnidadesInstalacion" value="${requestScope[appConstants.transferencias.NUM_UNIDADES_INSTALACION]}"/>
<c:set var="selCajas" value="${param['selCajas']}"/>

<bean:struts id="actionMapping" mapping="/gestionRelaciones" />

<script>
	function generarCartelas()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.selCajas[0].checked)
		{
			form.cajas.value = "*";
			form.submit();
		}
		else
		{
			if (form.textCajas.value == "")
				alert("<bean:message key="archigest.archivo.transferencias.seleccionarAlgunasCajas.vacio"/>");
			else
			{
				form.cajas.value = form.textCajas.value;
				form.submit();
			}
		}
	}
</script>

<div id="contenedor_ficha">

	<html:form action="/gestionRelaciones">
	<input type="hidden" name="method" value="verCartelas"/>
	<input type="hidden" name="cajas"/>
	
	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.transferencias.generacionCartelas"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold" 
										   href="javascript:generarCartelas()"
										><html:img page="/pages/images/cartela.gif" 
										        border="0" 
										        altKey="archigest.archivo.transferencias.generarCartelas"
										        titleKey="archigest.archivo.transferencias.generarCartelas"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/></a>
									</td>
									<td width="10px"></td>
								   	<td nowrap="nowrap">
										<tiles:insert definition="button.closeButton" flush="true" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>
		
		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
		
			<div id="barra_errores"><archivo:errors /></div>
			<div class="separador1">&nbsp;</div>

			<div class="cabecero_bloque_sin_height">
				<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
			</div>
			

			<div class="bloque">
				<TABLE class="formulario" cellpadding=0 cellspacing=6>
					<TR>
						<TD class="tdTitulo" width="180px">
							<bean:message key="archigest.archivo.transferencias.seleccionarCajas.total"/>:&nbsp;
						</TD>
						<TD class="tdDatosBold">
							<c:out value="${numUnidadesInstalacion}"/>
						</TD>
					</TR>
				</TABLE>
				<div class="separador5">&nbsp;</div>
				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td class="tdTitulo" colspan="3">
							<bean:message key="archigest.archivo.transferencias.seleccionarCajas.caption" />:
						</td>
					</tr>
				</table>
				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td width="50px"></td>
						<td width="20px">
							<input type="radio" name="selCajas" value="1" style="border:0;"
							<c:if test="${(empty selCajas) or (selCajas=='1')}">
								checked="true"
							</c:if>
						/></td>
						<td class="etiquetaAzul11Bold">&nbsp;<bean:message key="archigest.archivo.transferencias.seleccionarTodasCajas"/></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="radio" name="selCajas" value="2" style="border:0;"
							<c:if test="${(!empty selCajas) && (selCajas=='2')}">
								checked="true"
							</c:if>
						/></td>
						<td class="etiquetaAzul11Bold">
							&nbsp;<bean:message key="archigest.archivo.transferencias.seleccionarAlgunasCajas"/>:
							&nbsp;&nbsp;&nbsp;
							<input type="text" name="textCajas" class="input" onKeyPress="javascript:document.forms['<c:out value="${actionMapping.name}" />'].selCajas[1].checked=true;"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td class="etiquetaAzul11Normal">
							<bean:message key="archigest.archivo.transferencias.msgSeleccionarAlgunasCajas"/>
						</td>
					</tr>
				</table>
			 </div>
		
			<div class="separador5">&nbsp;</div>
	
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
	</html:form>
</div> <%--contenedor_ficha --%>
