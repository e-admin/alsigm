<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<bean:struts id="actionMapping" mapping="/gestionSeries" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.pasoAEnEstudio"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<script>
					function pasarAEstudio() {
						var form = document.forms['<c:out value="${actionMapping.name}" />'];
						form.submit();
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:pasarAEstudio()">
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
	        <td>
				<c:url var="closeURL" value="/action/navigateAction">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="<c:out value="${closeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

<c:set var="serie" value="${sessionScope[appConstants.fondos.SERIE_KEY]}" />
<html:form action="/gestionSeries">
<input type="hidden" name="method" value="serieEnEstudio">
<input type="hidden" name="idSerie" value="<c:out value="${serie.id}" />">

	<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.serie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		
		<table class="formulario">
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.cf.contexto"/>:
				</td>
				<td class="tdDatosBold" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> <c:out value="${serie.denominacion}" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.gestor"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.gestor.nombreCompleto}" />
				</td>
			</tr>
		</table>
		</tiles:put>
	</tiles:insert>

	<div class="separador5">&nbsp;</div>
	<script>
		function changeVisibilitySeleccionNuevoGestor() {
			var seleccionNuevoGestor = xGetElementById('listaGestores');
			var visible = (seleccionNuevoGestor.style.display == 'none');
			if (visible) 
				xDisplay(seleccionNuevoGestor, 'block');
			else
				xDisplay(seleccionNuevoGestor, 'none');
		}
	</script>

	<div class="cabecero_bloque"> 
	<TABLE class="w98m1" height="100%" cellpadding=0 cellspacing=0>
	  <TBODY>
	  <TR>
	    <TD class="etiquetaAzul12Bold">
			<c:if test="${!empty param.seleccionNuevoGestor}">
				<c:set var="verListaGestores" value="${true}" />
			</c:if>
			<html:checkbox property="seleccionNuevoGestor" onclick="changeVisibilitySeleccionNuevoGestor()" value="true" />
			<bean:message key="archigest.archivo.cf.selNuevoGestorSeries"/>
		</TD>
	  </TR></TBODY></TABLE>
	</div>

	<div id="listaGestores" class="bloque" <c:if test="${!verListaGestores}">style="display:none"</c:if> style="margin-top:-1px;">
		<table class="formulario">
			<TR>
			<TD width="150px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.GestorSerie"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
			<c:set var="gestoresDeSeries" value="${sessionScope[appConstants.fondos.LISTA_GESTORES_SERIE_KEY]}" />
			<html:select property="idGestor">
				<html:options collection="gestoresDeSeries" property="id" labelProperty="nombreCompleto" />
			</html:select>
			<TD>
			</TR>
		</table>
	</div>

</html:form>
	</tiles:put>
</tiles:insert>