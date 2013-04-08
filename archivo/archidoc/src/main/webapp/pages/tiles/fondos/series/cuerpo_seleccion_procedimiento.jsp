<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="vListaProcedimientos" 	value="${sessionScope[appConstants.fondos.LISTA_PROCEDIMIENTOS_KEY]}"/>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.asociarProc"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   <TD>
		   <bean:struts id="mappingIdentificacionSerie" mapping="/gestionIdentificacionAction" />
		   <c:if test="${!empty vListaProcedimientos}">
				<script>
					function seleccionarProcedimiento() {
						var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
						form.method.value = "vincularProcedimiento";
						form.submit();
					}
				</script>
				<a class=etiquetaAzul12Bold href="javascript:seleccionarProcedimiento()">
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</c:if>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.returnButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.serie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="180px">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> <c:out value="${serie.denominacion}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<script>
			function setMethod(fieldMethod) {
				if (!fieldMethod.value)
					fieldMethod.value = 'buscarProcedimiento';
			}
		</script>
		<html:form action="/gestionIdentificacionAction" onsubmit="setMethod(this.method)">
		<input type="hidden" name="method">



		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.msgSelProcIdentificacion"/></tiles:put>
		<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		  <TR>
				<TD>
				<script>
					function buscarProcedimientos() {
						var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
						form.method.value = "buscarProcedimiento";
						form.submit();
					}
				</script>
				<a class="etiquetaAzul12Normal" href="javascript:buscarProcedimientos()">
					<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/>
				</a>
				</TD>
		 </TR>
		</TABLE>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
		<c:set var="tipoProcedimiento"><c:out value="${param.tipoProcedimiento}">2</c:out></c:set>
		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.cf.tipoProcedimiento"/>:&nbsp;</td>
				<td class="tdDatos">
					<table cellpadding="0" cellspacing="3"><tr>
					<td class="etiquetaNegra11Normal"><bean:message key="archigest.archivo.cf.procedimientoAutomatizado"/>:&nbsp;</td>
					<td><input type="radio" class="radio" name="tipoProcedimiento" value="2" <c:if test="${empty tipoProcedimiento || tipoProcedimiento == 2}">checked</c:if>></td>
					<td class="etiquetaNegra11Normal"><bean:message key="archigest.archivo.cf.procedimientoNoAutomatizado"/>:&nbsp;</td>
					<td><input class="radio" type="radio" name="tipoProcedimiento" value="3" <c:if test="${tipoProcedimiento == 3}">checked</c:if>></td>
					</tr></table>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.nombre"/>:&nbsp;</td></td>
				<td class="tdDatos"><input type="text" size="40" name="tituloProcedimiento" value="<c:out value="${param.tituloProcedimiento}" />"></td>
			</tr>
		</table>

		<c:if test="${vListaProcedimientos != null}">
		<div style="background-color:#efefef">
			<display:table name='pageScope.vListaProcedimientos'
						style="width:99%;margin-left:auto;margin-right:auto"
						id="procedimiento"
						sort="list"
						export="false">

			<display:column style="width:1%">
				<html-el:radio property="procedimiento" value="${procedimiento.id}"></html-el:radio>
			</display:column>

			<display:column titleKey="archigest.archivo.cf.codigo" >
				<c:out value="${procedimiento.codigo}"/>
			</display:column>

			<display:column titleKey="archigest.archivo.cf.codigo" >
				<c:out value="${procedimiento.nombre}"/>
			</display:column>
			</display:table>
		</div>
		</c:if>
		<div class="separador5">&nbsp;</div>
		</tiles:put>
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>