<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<tiles:definition id="infoSerie" extends="fondos.series.cabeceraSerie">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.cf.serieDeLasUdocsAMover"/>
	</tiles:put>
</tiles:definition>

<bean:struts id="mapping" mapping="/moverUdocsAction" />

<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>

<div id="contenedor_ficha">
	<script language="JavaScript1.2" type="text/JavaScript">
		function clean()
		{
			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.codigo.value = "";
			form.titulo.value = "";
		}
		function goOn(){
			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.method.value="formBusquedaSerieDestino";

			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}

			form.submit();
		}
		function buscarElementos() {
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.buscando.msgUDocs"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			document.forms[0].submit();
		}

	</script>

	<html:form action="/moverUdocsAction">
	<input type="hidden" name="method" value="buscarUdocsAMover"/>

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
    						<fmt:message key="archigest.archivo.cf.busquedaUdocsAMover"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
									<td>
										<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
										<html:img
											page="/pages/images/Next.gif"
											altKey="archigest.archivo.siguiente"
											titleKey="archigest.archivo.siguiente"
											styleClass="imgTextBottom" />
										&nbsp;<bean:message key="archigest.archivo.siguiente"/>
										</a>
									</td>
									<td width="10">&nbsp;</td>
									<TD>
										<c:url var="cancelURL" value="/action/gestionUdocsCF">
											<c:param name="method" value="goReturnPoint" />
										</c:url>
										<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
										<html:img page="/pages/images/close.gif"
											altKey="archigest.archivo.cerrar"
											titleKey="archigest.archivo.cerrar"
											styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
								   	</TD>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors/></div>


			<tiles:insert beanName="infoSerie"/>
				<div class="separador5">&nbsp;</div>
				<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><fmt:message key="archigest.archivo.parametrosDeBusqueda"/></tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<a class="etiquetaAzul12Bold" href="javascript:buscarElementos();">
						<html:img page="/pages/images/buscar.gif"
					       altKey="archigest.archivo.buscar"
					       titleKey="archigest.archivo.buscar"
					        styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.buscar"/></a>&nbsp;&nbsp;
						<a class="etiquetaAzul12Bold" href="javascript:clean()">
							<html:img page="/pages/images/clear0.gif"
						        altKey="archigest.archivo.limpiar"
						        titleKey="archigest.archivo.limpiar"
						        styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>&nbsp;&nbsp;
			</tiles:put>


				<tiles:put name="blockContent" direct="true">

				<table class="formulario">

					<%--Filtro por codigo --%>
					<tr>
						<td class="tdTituloFicha" width="150px">
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
							<html:text property="titulo" styleClass="input60"/>
						</td>
					</tr>
				</table>
				</tiles:put>
			</tiles:insert>


			<div class="separador5">&nbsp;</div>
			<c:set var="listaUdocsBuscadas" value="${requestScope[appConstants.fondos.LISTA_UDOCS_AMOVER]}"/>
			<c:if test="${!empty listaUdocsBuscadas}">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">


				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
				<tiles:put name="blockContent" direct="true">
					<c:if test="${pageScope.listaUdocsBuscadas!=null}">
						<table class="formulario">
							<tr>
								<td align="right">
									<a class="etiquetaAzul12Bold"  id="selectAll" href="#" >
						 			<html:img page="/pages/images/checked.gif"
										    border="0"
										    altKey="archigest.archivo.selTodos"
										    titleKey="archigest.archivo.selTodos"
										    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
									&nbsp;
									<a class="etiquetaAzul12Bold"  id="desSelectAll" href="#">

						 			<html:img page="/pages/images/check.gif"
										    border="0"
										    altKey="archigest.archivo.quitarSel"
										    titleKey="archigest.archivo.quitarSel"
										    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
									&nbsp;&nbsp;
							   </td>
							</tr>
						</table>
					</c:if>

					<c:if test="${!empty listaUdocsBuscadas}">
						<table class="tablaAncho99">
							<tr class="filaAlineadaDerecha">
								<td class="etiquetaAzul11Bold" id="numElementos">
								</td>
							</tr>
						</table>
					</c:if>

					<div style="padding-top:6px;padding-bottom:6px">
						<display:table name="pageScope.listaUdocsBuscadas"
							id="elemento"
							requestURI="/action/moverUdocsAction"
							style="width:99%;margin-left:auto;margin-right:auto">

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.documentos.digitalizacion.noExistenResultadosParaLaBusqueda"/>
							</display:setProperty>
							<display:column style="width:15px">
								<input type="checkbox" name="udocsSeleccionadas" value="<c:out value="${elemento.id}"/>" checked="checked"/>
							</display:column>
							<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo"  sortable="true" headerClass="sortable">
								<c:out value="${elemento.codigo}" />
							</display:column>
							<display:column titleKey="archigest.archivo.titulo" sortProperty="titulo"  sortable="true" headerClass="sortable">
								<c:out value="${elemento.titulo}" />
								<c:set var="numElementos" value="${elemento_rowNum}"/>
							</display:column>
						</display:table>
						<c:if test="${!empty listaUdocsBuscadas}">
							<script language="JavaScript1.2" type="text/JavaScript">
								var td=document.getElementById("numElementos");
								td.innerHTML="<c:out value='${pageScope.numElementos}'/>&nbsp;<bean:message key='archigest.archivo.fondos.busqueda.elementos'/>";
							</script>
						</c:if>
					</div>
					</tiles:put>
			</tiles:insert>
			</c:if>
			<div class="separador5">&nbsp;</div>
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
	</html:form>
</div> <%--contenedor_ficha --%>

<script language="javascript">

function checkAll(id,value){
	$('#' + id).click(function(event){
		event.preventDefault();

		$('form input[type=checkbox]').each( function() {
			this.checked = value;
		});
	}
);
}


$(document).ready(function() {

	$('#selectAll').click(checkAll('selectAll',true));

	$('#desSelectAll').click(checkAll('desSelectAll',false));

});



</script>


