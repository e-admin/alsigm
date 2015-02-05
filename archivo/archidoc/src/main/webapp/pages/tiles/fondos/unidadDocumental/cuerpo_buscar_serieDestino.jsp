<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:struts id="mapping" mapping="/moverUdocsAction" />

<c:set var="beanForm" value="${sessionScope[mapping.name]}"/>

<c:set var="fondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>

<div id="contenedor_ficha">

	<script language="JavaScript1.2" type="text/JavaScript">
		function clean()
		{
			var form = document.forms['<c:out value="${mapping.name}" />'];
			document.getElementById("fondoSerie").value="";
			document.getElementById("codigoSerie").value="";
			document.getElementById("tituloSerie").value="";
		}
		function goOn()
		{
			var form = document.forms['<c:out value="${mapping.name}" />'];

			if(form.idSerieDestino && form.idSerieDestino.value!=""){

				form.method.value="informeMoverUdocs";

				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
					var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
					window.top.showWorkingDiv(title, message);
				}

				form.submit();
			}
			else{
				alert('<bean:message key="errors.item_no_selected" />');
			}
		}
	</script>

	<html:form action="/moverUdocsAction">
	<input type="hidden" name="method" value="buscarSerieDestino"/>

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.cf.moverUdocs"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<tiles:insert definition="button.closeButton" flush="true">
											<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
											<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
										</tiles:insert>
								   	</td>
									<td width="10">&nbsp;</td>
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
										<c:url var="cancelURL" value="/action/moverUdocsAction">
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
										<script>
											function buscarElementos() {
												if (window.top.showWorkingDiv) {
													var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
													var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
													var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
													window.top.showWorkingDiv(title, message, message2);
												}
												document.forms[0].submit();
											}
										</script>
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
							<html:text styleId="codigoSerie" property="codigoSerie"/>
						</td>
					</tr>

					<%--Filtro por titulo --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.cf.titulo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text styleId="tituloSerie" property="tituloSerie" styleClass="input60"/>
						</td>
					</tr>

				</table>
			</div>
			<div class="separador5">&nbsp;</div>
			<c:set var="listaSeriesBuscadas" value="${sessionScope[appConstants.fondos.LISTA_SERIES_DESTINO]}"/>
			<c:if test="${!empty listaSeriesBuscadas}">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
				<tiles:put name="blockContent" direct="true">
				<div style="padding-top:6px;padding-bottom:6px">
					<display:table name="pageScope.listaSeriesBuscadas"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="elemento"
					requestURI="../../action/moverUdocsAction">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
					</display:setProperty>
					<display:column>
						<input type="radio" name="idSerieDestino" value="<c:out value="${elemento.id}"/>"
						<c:if test="${beanForm.idSerieDestino==element.id}"> checked	</c:if>
						/>
					</display:column>
					<display:column titleKey="archigest.archivo.codigoReferencia" sortProperty="codReferencia" sortable="true" headerClass="sortable">
						<c:out value="${elemento.codReferencia}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.titulo" property="titulo" sortable="true" headerClass="sortable"/>
				</display:table>
				</div>
				</tiles:put>

			</tiles:insert>
			</c:if>
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
	</html:form>
</div> <%--contenedor_ficha --%>


