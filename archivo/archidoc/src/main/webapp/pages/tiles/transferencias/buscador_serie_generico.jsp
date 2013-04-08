<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%-- 

	Buscador genérico de series.
	
	Debe recibir dos parámetros: idBuscadorSerie y formName
	
	La Jsp que incluya este código debe tener un formulario con 2 campos definidos para cada buscador de series:
	serie+idBuscadorSerie
	nombreSerie+idBuscadorSerie
	
	La llamada a ésta jsp se realizará de la siguiente forma:
	
		<a href="javascript:mostrarBuscadorSeries('<c:out value="${mappingAction.name}" />','Destino')">
			<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.series" altKey="archigest.archivo.series" styleClass="imgTextMiddleHand" />
		</a>
		<bean:define id="idBuscadorSerie" value="Destino" toScope="request"/>		
		<bean:define id="formName" name="mappingAction" property="name" toScope="request"/>	
		<jsp:include page="buscador_serie_entre_archivos.jsp" flush="true" />

	el código javascript que muestra el buscador es el siguiente:

		function mostrarBuscadorSeries(formName, id) {
			var buscadorSeries = xGetElementById('buscadorSerie'+id);
			if (!buscadorSeries || buscadorSeries.style.display == 'none') {
				var form = document.forms[formName];
				form.elements['method'].value="verBuscadorSeries"+id;
				form.submit();
			} else
				xDisplay(buscadorSeries, 'none');
		}

--%>

<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}" />

<script>
	function buscarSeries(formName) {
		var form = document.forms[formName];
		form.elements['method'].value="buscarSerie<c:out value="${idBuscadorSerie}"/>";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		form.submit();
	}
</script>

<div id="buscadorSerie<c:out value="${idBuscadorSerie}"/>" class="bloque_busquedas">
	<div class="cabecero_bloque_tab">
		<TABLE width="100%" cellpadding=0 cellspacing=0>
		  	<TR>
				<TD width="100%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
					  	<TR>
							<TD>
							<a class="etiquetaAzul12Normal" href="javascript:buscarSeries('<c:out value="${formName}" />')">
								<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.buscar"/>
							</a>		
							</TD>
							<td width="10px">&nbsp;</td>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</div>

	<div class="separador5"></div>

	<table class="formulario" width="99%">
		<tr>
			<td class="tdTitulo" width="170px">
				<bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;
			</td>
			<c:set var="paramCodigoName" value="codigo${idBuscadorSerie}" /> 
			<td class="tdDatos"><input type="text" size="30" name="<c:out value="${paramCodigoName}"/>"></td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;
			</td>
			<c:set var="paramTituloName" value="titulo${idBuscadorSerie}"/> 	
			<td class="tdDatos"><input type="text" class="input80" name="<c:out value="${paramTituloName}"/>"></td>
		</tr>
	</table>

	<c:set var="listaSeries" value="${requestScope[appConstants.fondos.SERIE_KEY]}" />
	<c:if test="${!empty listaSeries}">
		<script>
			function selectSerie(item) {
				var formName = '<c:out value="${formName}" />';
				var form = document.forms[formName];
				var serie = document.getElementById('serie<c:out value="${idBuscadorSerie}"/>');
				if ((serie!=null)&&(serie!="")&&(serie!="undefined"))
					serie.value = item.id;
				var nombreserie = document.getElementById('nombreSerie<c:out value="${idBuscadorSerie}"/>');
				if ((nombreserie!=null)&&(nombreserie!="")&&(nombreserie!="undefined"))
					nombreserie.value = item.getAttribute('nombreSerie');	
				hideBusquedaSeries();
			}
		</script>
		
		<div id="listaSeries" style="width:100%;height:100px;overflow:auto;background-color:#efefef" >
			<c:forEach var="serie" items="${listaSeries}">
				<div class="etiquetaGris12Normal" id='<c:out value="${serie.id}" />' 
						nombreSerie='<c:out value="${serie.codReferencia} ${serie.titulo}" />'
						onmouseOver="this.style.backgroundColor='#dedede'"
						onmouseOut="this.style.backgroundColor='#efefef'"
						onclick="selectSerie(this)" style='padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
					<c:out value="${serie.codReferencia}" />&nbsp;&nbsp;
					<c:out value="${serie.denominacion}" /></div>
			</c:forEach>
		</div>
		
		<script>
			function hideBusquedaSeries() {
				xGetElementById('buscadorSerie<c:out value="${idBuscadorSerie}"/>').style.display='none';
			}
		</script>
		
		<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #000000">
					<a class=etiquetaAzul12Normal href="javascript:hideBusquedaSeries()">
						<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/>
					</a>&nbsp;
				</td>
			</tr>
		</table>

	</c:if>
</div>