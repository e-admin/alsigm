<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>

<c:set var="vTiposClSeries" value="${sessionScope[appConstants.fondos.TIPOS_CLASIFICADOR_SERIES]}"/>
<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
<c:set var="isEditando" value="${ClasificadorSerieForm.id!=null}" />

<bean:struts id="gestionClasificadorSeriesMapping" mapping="/gestionClasificadorSeriesAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
		<c:when test="${!isEditando}">
			<bean:message key="archigest.archivo.crear" />
			<bean:message key="archigest.archivo.cf.clasificador" />
		</c:when>
		<c:otherwise>
			<bean:message key="archigest.archivo.editar" />
			<bean:message key="archigest.archivo.cf.clasificador" />
		</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<script>
					function guardarClasificador() {
						var form = document.forms['<c:out value="${gestionClasificadorSeriesMapping.name}" />'];
						if(<c:out value="${!isEditando}"/>){
							if (form.tipoClasificador.length && !elementSelected(form.tipoClasificador)){
								alert('<bean:message key="archigest.archivo.cf.msgTipoClasificadorCrear"/>');
								return;
							}
						}else{
							if(document.getElementById('codigoOld').value != document.getElementById('codigo').value){
								if (window.confirm("<bean:message key='archigest.archivo.fondos.update.codigo.confirm.msg'/>") == false)
									return;
							}
						}
						if (window.top.showWorkingDiv) {
							var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
							var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
							window.top.showWorkingDiv(title, message);
						}
						form.submit();												
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:guardarClasificador()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="cancelURI" value="/action/gestionClasificadorSeriesAction">
					<c:param name="method" value="cancelar" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<html:form action="/gestionClasificadorSeriesAction">

		<input type="hidden" name="method" value="guardarClasificadorSeries" />
		<html:hidden property="idPadre" />
		<html:hidden property="nivelPadre" />
		<html:hidden property="id" />
		<html:hidden property="codigoOld" styleId="codigoOld"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.fondo"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<div style="padding-left:160px; text-align:left">
				<tiles:insert definition="fondos.cf.jerarquiaElemento" />
			</div>
		</tiles:put>
		</tiles:insert>

		<div class="separador5"></div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.datosClasificador"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario" cellpadding="0" cellspacing="2">
			<c:choose>
			<c:when test="${!empty vTiposClSeries[1]}">
					<TR>
						<TD class="tdTitulo" width="180px">
							<bean:message key="archigest.archivo.cf.tipo" />:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="clasificadorSerieForm" value="${requestScope[gestionClasificadorSeriesMapping.name]}" />

							<c:forEach var="tipoClasificador" items="${vTiposClSeries}">
								<input type="radio" class="radio" name="tipoClasificador" value="<c:out value="${tipoClasificador.id}" />"
								 <c:if test="${clasificadorSerieForm.tipoClasificador == tipoClasificador.id}">checked</c:if>>
								<c:out value="${tipoClasificador.nombre}" />
								<html:img page="/pages/images/pixel.gif" styleClass="imgTextBottom" width="20px" height="1" />
							</c:forEach>
						</TD>
					</TR>
			</c:when>
			<c:otherwise>
				<c:set var="tipoClasificador" value="${vTiposClSeries[0]}" />
					<TR>
						<TD class="tdTitulo" width="180px">
							<bean:message key="archigest.archivo.cf.tipo" />:&nbsp;
						</TD>

						<TD class="tdDatos">
							<input type="hidden" name="tipoClasificador" value="<c:out value="${tipoClasificador.id}" />">
							<c:out value="${tipoClasificador.nombre}" />
						</TD>
					</TR>
			</c:otherwise>
			</c:choose>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.codigo" />:&nbsp;
					</td>
					<td class="tdDatosBold">
						<html:text property="codigo" size="10" maxlength="128" styleId="codigo"/>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<html:text property="denominacion" styleClass="input99" maxlength="1024"/>
					</td>
				</tr>
				<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<html:text property="codOrdenacion" maxlength="32"/>
						</td>
					</tr>
				</c:if>
			</table>

		</tiles:put>
		</tiles:insert>

		</html:form>
	</tiles:put>
</tiles:insert>