<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="vSerie" value="${sessionScope[appConstants.fondos.SERIE_KEY]}"/>
<c:set var="listaPadres" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}" />
<c:set var="fondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>

<bean:struts id="mapping" mapping="/gestionSeries"/>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
		function clean()
		{
			document.getElementById("fondoSerie").value="";
			document.getElementById("codigo").value="";
			document.getElementById("tituloSerie").value="";
			
			
		}
		
		function goOn()
		{
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.cf.realizandoMovimiento"/>';
				var message = '<bean:message key="archigest.archivo.cf.realizandoMovimiento"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.method.value="informeMoverUdocs";
			form.submit();
		}
		
		function buscarElementos() {
			
			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.method.value="seleccionarNuevoPadre";
			document.forms[0].submit();
		}

		function aceptar()
		{
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.cf.realizandoMovimiento"/>';
				var message = '<bean:message key="archigest.archivo.cf.realizandoMovimiento"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			document.forms[0].submit();
		}						
		
	</script>

<tiles:definition id="infoSerie" extends="fondos.series.cabeceraSerie">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.cf.serieAMover"/>
	</tiles:put>
</tiles:definition>

<html:form action="/gestionSeries">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.cf.moverSerie" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<c:if test="${!empty listaPadres || listaPadres!=null}">
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			</td>		
			<td width="10px">&nbsp;</td>
		</c:if>
		<td nowrap>
			<c:url var="cancelURL" value="/action/gestionSeries">
				<c:param name="method" value="goBack" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			</a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert beanName="infoSerie"/>

		<div class="separador1">&nbsp;</div>
			<div class="separador1">&nbsp;</div>	
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
							<html:text property="codigo" styleId="codigo"/>
						</td>
					</tr>

					<%--Filtro por titulo --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.cf.titulo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="tituloSerie" styleId="tituloSerie" styleClass="input60"/>
						</td>
					</tr>
				</table>
			</div>
			
				<div class="separador1">&nbsp;</div>	
				
				<c:url var="moverSerieURL" value="/action/gestionSeries" />
				


			    <input type="hidden" name="method" id="method" value="moverSerie"> 
				<input type="hidden" name="idSerie" id="idSerie" value="<c:out value="${vSerie.id}" />">

				<c:if test="${!empty listaPadres}">
					<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				
						<tiles:put name="blockTitle" direct="true">
							<fmt:message key="archigest.archivo.cf.msgselNuevaUbicacionSerie" />
						</tiles:put>
						
						<tiles:put name="blockContent" direct="true">
							<div class="separador1">&nbsp;</div>
			
							<display:table name="pageScope.listaPadres" id="clasificadorSeries" 
								style="width:99%;margin-left:auto;margin-right:auto">
								<display:column style="width:15px">
									<input type="radio" name="clasificadorSeleccionado" value="<c:out value="${clasificadorSeries.id}" />" >
								</display:column>
								<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" />
								<display:column titleKey="archigest.archivo.cf.denominacion" property="titulo" />
							</display:table>
						</tiles:put>
					</tiles:insert>
				</c:if>
	</tiles:put>
	</tiles:insert>
	</html:form>