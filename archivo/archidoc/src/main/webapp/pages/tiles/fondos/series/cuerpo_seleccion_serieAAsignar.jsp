<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:if test="${param.method == 'verBuscadorSeries'}"><script>removeCookie('tabSeleccionSerie');</script></c:if>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.cesionDeControlDeSeries"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<script>
					function goOn() {
						var seleccionSerieForm = document.forms['seleccionSerie'];
						if (seleccionSerieForm.serieSeleccionada)
							if (!elementSelected(seleccionSerieForm.serieSeleccionada))
								alert("<bean:message key='archigest.archivo.cf.unaSerieMinimo'/>");
							else{
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
								}
								seleccionSerieForm.submit();
							}
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td>
				<c:url var="closeURL" value="/action/gestionAsignacionSerie">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${closeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

	<script>
		var tabPanel = new TabPanel("tabSeleccionSerie");
		tabPanel.addTab(new Tab("buscadorSerie", "buscadorSerie"));
		tabPanel.addTab(new Tab("seriesGestor", "seriesGestor"));

		function selectTipoBusqueda(tipoSeleccionSerie) {
			tabPanel.showTab(tipoSeleccionSerie);
			xDisplay('resultados','none');
		}


		function buscar(){

		}
	</script>

	<div class="cabecero_tabs">

			<table cellspacing="0" cellpadding=0>
				<tr>
			    	<td class="tabActual" id="pbuscadorSerie">
						<a href="javascript:selectTipoBusqueda('buscadorSerie');document.forms['busquedaSerie'].reset()" id="tbuscadorSerie" class="textoPestana">
							<bean:message key="archigest.archivo.cf.buscarSeries"/>
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
			    	<td class="tabSel" id="pseriesGestor">
						<a href="javascript:selectTipoBusqueda('seriesGestor');document.forms['seleccionSeriesGestor'].reset()" id="tseriesGestor" class="textoPestana">
							<bean:message key="archigest.archivo.cf.seriesPorGestor"/>
						</a>
				    </td>
				</tr>
			</table>
	</div>

	<div id="buscadorSerie" class="bloque_tab">
		<div class="cabecero_bloque_tab">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TR>
				<TD width="100%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
						<TD>
							<script>
								function busquedaSerie(){
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}
									document.forms['busquedaSerie'].submit();
								}
							</script>
							<a class="etiquetaAzul12Normal" href="javascript:busquedaSerie();">
								<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.buscar"/>
							</a>
						</TD>
				 </TR>
				</TABLE>
				</TD>
			</TR></TABLE>
		</div>
	<c:url var="gestionAsignacionSerie" value="/action/gestionAsignacionSerie" />
	<table class="w100">
		<tr>
			<td width="10px">&nbsp;</td>
			<td class="etiquetaAzul12Bold"><bean:message key="archigest.archivo.buscarPor"/>:&nbsp;</td>
		</tr>
	</table>
	<table class="formulario" width="99%">
	<form name="busquedaSerie" action="<c:out value="${gestionAsignacionSerie}" escapeXml="false"/>" onreset="this.fondo.selectedIndex=0;this.codigo.value='';this.titulo.value='';return false;">
		<input type="hidden" name="method" value="buscarSerie">
		<tr>
			<td width="20px">&nbsp;</td>
			<td class="tdTitulo" width="150px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
			<td class="tdDatos">
			<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}" />
			<select name="fondo">
						<option value=""> -- <bean:message key="archigest.archivo.cf.fondosDocumentales"/>-- </option>
						<c:forEach var="fondo" items="${listaFondos}">
						<option value="<c:out value="${fondo.id}" />" <c:if test="${fondo.id == param.fondo}">selected</c:if>> <c:out value="${fondo.label}" /> </option>
						</c:forEach>
					</select>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;
			</td>
			<td class="tdDatos"><input type="text" size="30" name="codigo" value="<c:out value="${param.codigo}" />"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;
			</td>
			<td class="tdDatos"><input type="text" class="input80" name="titulo" value="<c:out value="${param.titulo}" />"></td>
		</tr>
	</form>
		</table>
	</div>

	<div id="seriesGestor" class="bloque_tab" style="display:none">
	<div class="cabecero_bloque_tab">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
			<TD width="100%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
					<TD>&nbsp;</TD>
			 </TR>
			</TABLE>
			</TD>
		</TR></TABLE>
	</div>
	<table class="formulario" width="99%">
	<form name="seleccionSeriesGestor" action="<c:out value="${gestionAsignacionSerie}" escapeXml="false"/>" onreset="this.gestor.selectedIndex=0;return false">
		<input type="hidden" name="method" value="seriesGestor" >
		<c:set var="listaGestores" value="${sessionScope[appConstants.fondos.LISTA_GESTORES_SERIE_KEY]}" />
		<tr>
		<td class="tdTitulo" width="120px"><bean:message key="archigest.archivo.cf.GestorSeries"/>:&nbsp;</td>
		<td class="tdDatos">
			<script>
				function selGestorSeries(){
					if (window.top.showWorkingDiv) {
						var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
						var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
						window.top.showWorkingDiv(title, message);
					}
					document.forms['seleccionSeriesGestor'].submit();
				}
			</script>
			<select name="gestor" onChange="selGestorSeries();">
				<option> -- <bean:message key="archigest.archivo.cf.selGestorSeries"/>--
				<c:forEach var="gestor" items="${listaGestores}">
				<option value="<c:out value="${gestor.id}" />" <c:if test="${gestor.id == param.gestor}">selected</c:if>> <c:out value="${gestor.nombreCompleto}" />
				</c:forEach>
			</select>
		</td>
		</tr>
	</form>
	</table>
	</div>

	<form method="post" name="seleccionSerie" action="<c:out value="${gestionAsignacionSerie}" escapeXml="false"/>">
	<input type="hidden" name="method" value="seleccionarSeries">

	<c:set var="listaSeries" value="${requestScope[appConstants.fondos.SERIE_KEY]}" />
	<c:if test="${listaSeries != null}">
	<div id="resultados">

	<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.msgSelSerieCeder"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms['seleccionSerie'].serieSeleccionada)">
					<html:img page="/pages/images/checked.gif" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
				&nbsp;
			</td>
			<td width="10">&nbsp;</td>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms['seleccionSerie'].serieSeleccionada)">
					<html:img page="/pages/images/check.gif" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
				&nbsp;&nbsp;
			</td>
		</tr></table>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
	  <div id="capaConScroll" class="bloqueConScroll">
		<div class="separador5"></div>
		<c:set var="LISTA_SERIES_KEY" value="pageScope.listaSeries"/>
		<jsp:useBean id="LISTA_SERIES_KEY" type="java.lang.String" />
		<display:table name="<%=LISTA_SERIES_KEY%>"
			id="serie"
			export="false"
			style="width:98%;margin-left:auto;margin-right:auto">
			<display:column>
				<input type="checkbox" name="serieSeleccionada" value="<c:out value="${serie.id}" />" >
				<c:set var="numElementos" value="${serie_rowNum}"/>
			</display:column>

			<display:column titleKey="archigest.archivo.cf.codReferencia">
				<c:out value="${serie.codReferencia}" />
			</display:column>

			<display:column titleKey="archigest.archivo.denominacion" property="titulo" />
			<display:column titleKey="archigest.archivo.estado">
				<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
			</display:column>

			<display:column titleKey="archigest.archivo.fondo">
				<c:out value="${serie.fondo.codigo}" />
				<c:out value="${serie.fondo.titulo}" />
			</display:column>
			<c:if test="${param.method != 'seriesGestor'}">
			<display:column titleKey="archigest.archivo.gestor">
				<c:out value="${serie.gestor.nombreCompleto}" />
			</display:column>
			</c:if>
		</display:table>
	   </div>
	   <div class="separador5"></div>

	   <c:set var="masDeDiecinueveFilas" value="0"/>
		<c:if test="${pageScope.numElementos>19}">
			<c:set var="masDeDiecinueveFilas" value="1"/>
		</c:if>

		<c:if test="${masDeDiecinueveFilas<1}">
			<SCRIPT>
				var capaConScroll=document.getElementById("capaConScroll");
				capaConScroll.className="";
			</SCRIPT>
		</c:if>
	</tiles:put>
	</tiles:insert>


	</div>
	</c:if>

	</tiles:put>
</tiles:insert>

<script>
	tabPanel.showTab(getCookie("tabSeleccionSerie"));
</script>