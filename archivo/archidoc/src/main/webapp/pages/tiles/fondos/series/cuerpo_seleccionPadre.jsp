<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="listaPadres" value="${requestScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}" />
<c:set var="fondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>
<c:set var="clasificadorSeries" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />

<bean:struts id="mapping" mapping="/gestionClasificadorSeriesAction"/>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">

		function clean()
		{
			document.getElementById("fondoSerie").value="";
			document.getElementById("codigo").value="";
			document.getElementById("tituloSerie").value="";
		}

		function buscarElementos() {
			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.method.value="seleccionarNuevoPadre";
			form.submit();
		}

		function aceptar(){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.cf.realizandoMovimiento"/> ';
				var message = '<bean:message key="archigest.archivo.cf.realizandoMovimiento"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			document.forms[0].submit();
		}

</script>

<html:form action="/gestionClasificadorSeriesAction">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.moverElementoDelCuadro"/></tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<c:if test="${!empty listaPadres}">
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			</td>
			<td width="10px">&nbsp;</td>
		</c:if>
		<td nowrap>
			<c:url var="cancelURL" value="/action/gestionFondoAction">
				<c:param name="method" value="goBack" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextTop" />
			&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert definition="fondos.fondo.infoClasificador">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cf.elementoMover"/>
			</tiles:put>
		</tiles:insert>

		<DIV class="cabecero_bloque"> <%--cabecero primer bloque de datos --%>

			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TBODY>
			  <TR>
			    <TD class="etiquetaAzul12Bold" width="50%">
					<bean:message key="archigest.archivo.cf.busqueda.caption"/>
				</TD>
			    <TD width="50%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
				   	<td>

						<a class="etiquetaAzul12Bold" href="javascript:buscarElementos();">
						<html:img page="/pages/images/buscar.gif"
						        altKey="archigest.archivo.buscar"
						        titleKey="archigest.archivo.buscar"
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
					</td>
					<td width="10">&nbsp;</td>
				   	<td>
						<a class="etiquetaAzul12Bold" href="javascript:clean()">
						<html:img page="/pages/images/clear0.gif"
						        altKey="archigest.archivo.limpiar"
						        titleKey="archigest.archivo.limpiar"
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
					</td>
					<td width="10">&nbsp;</td>
			     </TR>
				</TABLE>
				</TD>
			  </TR></TBODY></TABLE>
			</div> <%--cabecero bloque --%>

				<div class="bloque">
				<table class="formulario">


					<%--Filtro por fondo --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">

							<html:select styleId="fondoSerie" property="fondoSerie">
								<html:option value="">&nbsp;&nbsp;&nbsp;</html:option>
								<html:options collection="fondos" property="id" labelProperty="titulo"/>
							</html:select>


						</td>
					</tr>

					<%--Filtro por codigo --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="codigo"/>
						</td>
					</tr>

					<%--Filtro por titulo --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.cf.titulo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="tituloSerie" styleClass="input60"/>
						</td>
					</tr>
				</table>
			</div>


			<input type="hidden" name="method" value="moverClasificador">
			<input type="hidden" name="idClasificador" value="<c:out value="${clasificadorSeries.id}" />">
			<html:hidden property="id"/>
			<c:if test="${listaPadres!=null}">
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cf.selNuevaUbicacion"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:set var="nivelClasificador" value="${requestScope[appConstants.fondos.NIVEL_ELEMENTO_CF]}" />
				<c:set var="nivelesDestino" value="${requestScope[appConstants.fondos.LISTA_NIVELES_KEY]}" />
				<c:set var="listaNivelesDestino">
					<c:forEach items="${nivelesDestino}" var="nivel" varStatus="countNum"><c:out value="${nivel.nombre}" /><c:if test="${! countNum.last}">, </c:if></c:forEach>
				</c:set>

				<display:table name="pageScope.listaPadres" id="clasificadorSerie"
					style="width:99%;margin-left:auto;margin-right:auto" defaultsort="2">

					<display:column style="width:15px">
						<input type="radio" name="nuevoPadre" value="<c:out value="${clasificadorSerie.id}" />" >
					</display:column>

					<display:column titleKey="archigest.archivo.codigo" style="width:50px" property="codReferencia" />
					<display:column titleKey="archigest.archivo.denominacion" property="titulo" />
				</display:table>

			</tiles:put>
			</tiles:insert>
			</c:if>

	</tiles:put>
</tiles:insert>
</html:form>