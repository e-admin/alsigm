<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="modificado" value="${requestScope[appConstants.valoracion.CRITERIOS_MODIFICADOS_KEY]}" />
<c:set var="archivo" value="${sessionScope[appConstants.transferencias.ARCHIVO_KEY]}" />

<bean:struts id="mappingGestionEliminacion" mapping="/gestionEliminacion" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.eliminacion.editarSeleccion"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td nowrap>
				<script>
					function goOn() {
						var form = document.forms['<c:out value="${mappingGestionEliminacion.name}" />'];
						form.method.value = "guardarEdicion";
						if (window.top.showWorkingDiv) {
							var title = '<bean:message key="archigest.archivo.operacion.guardandoInformacion"/>';
							var message = '<bean:message key="archigest.archivo.operacion.msgGuardandoInformacion"/>';
							var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
							window.top.showWorkingDiv(title, message, message2);
						}
						form.submit();
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
					<html:img page="/pages/images/save.gif" altKey="archigest.archivo.guardar" titleKey="archigest.archivo.guardar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.guardar"/>&nbsp;
				</a>
				</td>
				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionEliminacion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr>
		</table>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">

	<html:form action="/gestionEliminacion">
	<input type="hidden" id="method" name="method" value="guardarEliminacion">
	
	
	<%--Datos de eliminación--%>
	<c:if test="${param.type == '1'}">
	<div id="datosEliminacion"><%--class="bloque_tab"--%>

		<div class="bloque">
			<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.eliminacion.tituloEliminacion"/>:&nbsp;
				</TD>
				<TD class="tdDatosBold">
					<c:out value="${eliminacionForm.titulo}" />
					<html:hidden property="titulo"/>
				</TD>
			</TR>	
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.eliminacion.fechaEjecucion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:text size="14" maxlength="10" property="fechaEjecucion" />
						<archigest:calendar 
							image="../images/calendar.gif"
							formId="eliminacionForm" 
							componentId="fechaEjecucion" 
							format="dd/mm/yyyy" 
							enablePast="false" />
				</TD>	
			</TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.eliminacion.notas"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:textarea property="notas" rows="3" onchange="maxlength(this,1024,true)" onkeypress="maxlength(this,1024,false)" />
				</TD>
			</TR>		
			</TABLE>	
		</div>
	</div>
	</c:if>

	<div class="separador8">&nbsp;</div>
	
	<%--Datos de las fechas de restriccion asociadas a la eliminacion--%>

	<c:if test="${param.type == '2'}">
	<div id="datosCriterios"><%--class="bloque_tab" style="display:none"--%>	

	<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.conservacion.criterios"/></tiles:put>

		<tiles:put name="buttonBar" direct="true">
			<table cellpadding=0 cellspacing=0>
				<tr>
					<td width="10">&nbsp;</td>
					<td nowrap>
						<script>
							function solicitarUDocsElim() {
						   		var form = document.forms[0];
								<c:choose>
								<c:when test="${modificado}">
									alert("<bean:message key='archigest.archivo.conservacion.msg.guardarCriterios'/>");
								</c:when>
								<c:otherwise>
									<c:url var="verUDOCSURL" value="/action/gestionEliminacion">
										<c:param name="method" value="verUdocs" />
										<c:param name="id" value="${eliminacionForm.id}" />
										<c:if test="${!empty archivo}">
													<c:param name="idArchivo" value="${archivo.id}" />
										</c:if>
									</c:url>
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.operacion.ejecutandoEliminacion"/>';
										var message = '<bean:message key="archigest.archivo.operacion.msgEjecutandoEliminacion"/>';
										var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
										window.top.showWorkingDiv(title, message, message2);
									}
									window.location.href = "<c:out value="${verUDOCSURL}" escapeXml='false'/>";
								</c:otherwise>
								</c:choose>
							}
						</script>

						<a class="etiquetaAzul12Bold" 
							href="javascript:solicitarUDocsElim()">
						<html:img page="/pages/images/searchDoc.gif" 
							altKey="archigest.archivo.eliminacion.verUdocs" 
							titleKey="archigest.archivo.eliminacion.verUdocs" 
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminacion.verUdocs"/></a>
					</td>
				</tr>
			</table>
		</tiles:put>

		<tiles:put name="blockContent" direct="true">
		<div class="separador8">&nbsp;</div>

		<table class="w100" cellpadding="0" cellspacing="0"> 
		<tr>
			<td valign="top" width="50%">
				<div class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
					<table class="w100" cellpadding=0 cellspacing=0>
						<tr>
							<td align="right">
							<script>
								function anadir() {
									document.getElementById("method").value = "anadirFecha";
									document.forms[0].submit();
								}
							</script>
								<a class="etiquetaAzul12Bold" href="javascript:anadir()" >
									<html:img page="/pages/images/add_next.gif" altKey="archigest.archivo.eliminacion.anadirCriterio" titleKey="archigest.archivo.eliminacion.anadirCriterio" styleClass="imgTextMiddle" />
										<bean:message key="archigest.archivo.eliminacion.anadirCriterio"/>
								</a>
							</td>
						</tr>
					</table>
				</div> 
				<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
					<div class="separador8">&nbsp;</div>
					<table class="w100" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
						<tr>
							<td class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.conservacion.rangoFechas"/>
							</td>
						</tr>
					</table>
					<table class="form_centrado" cellpadding="0" cellspacing="0">
						<tr>
							<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.eliminacion.fecha"/>&nbsp;&gt;=&nbsp;</td>
							<td>
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td class="etiquetaAzul11Bold">
											<html:text property="fechaDIni" size="2" maxlength="2" styleClass="input"/>-
										</td>
										<td class="etiquetaAzul11Bold">
										   	<html:text property="fechaMIni" size="2" maxlength="2" styleClass="input"/>
										</td>
									</tr>
								</table> 
				 			</td>
							<td class="etiquetaAzul16Bold">&nbsp;&nbsp;&amp;&nbsp;&nbsp;</td>
							<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.eliminacion.fecha"/>&nbsp;&lt;=&nbsp;</td>
							<td>
								<table cellpadding="0" cellspacing="0">
									<tr>
									    <td class="etiquetaAzul11Bold">
									    	<html:text property="fechaDFin" size="2" maxlength="2" styleClass="input"/>-
									    </td>
									    <td class="etiquetaAzul11Bold">
									    	<html:text property="fechaMFin" size="2" maxlength="2" styleClass="input"/>
									    </td>
								    </tr>
								</table> 
							</td>
						</tr>
					</table>
					<div class="separador8">&nbsp;</div>
				</div>
			</td>
			<td valign="top">
				<div class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
					<table class="w100" cellpadding=0 cellspacing=0>
						<tr>
							<td align="right">
							<script>
								function eliminar() {
									document.getElementById("method").value = "eliminarFecha";
									document.forms[0].submit();
								}
							</script>
								<a class="etiquetaAzul12Bold" href="javascript:eliminar()" >
									<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminacion.eliminarCriterio" titleKey="archigest.archivo.eliminacion.eliminarCriterio" styleClass="imgTextMiddle" />
										<bean:message key="archigest.archivo.eliminacion.eliminarCriterio"/>&nbsp;
								</a>
							</td>
						</tr>
					</table>
				</div> 
				<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
					<c:set var="listaCriterios" value="${sessionScope[appConstants.valoracion.LISTA_CRITERIOS_KEY]}" />
					<c:if test="${listaCriterios != null}">
						<div id="resultados">
							<c:set var="LISTA_CRITERIOS_KEY" value="pageScope.listaCriterios"/>
							<jsp:useBean id="LISTA_CRITERIOS_KEY" type="java.lang.String" />
							<div class="separador8">&nbsp;</div>
							<display:table name="<%=LISTA_CRITERIOS_KEY%>"
								id="criterio" 
								decorator="valoracion.decorators.CriterioDecorator"
								style="width:98%;margin-left:auto;margin-right:auto">

								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.eliminacion.noCriterios"/>
								</display:setProperty>

								<display:column style="width:15px">
									<input type="checkbox" name="criterioEliminar" value="<c:out value="${criterio.id}" />" >
								</display:column>
								<display:column titleKey="archigest.archivo.eliminacion.fecha" property="fechaInicial"/>
								<display:column title="Y" style="width:10px">&nbsp;&amp;&nbsp;</display:column>
								<display:column titleKey="archigest.archivo.eliminacion.fecha" property="fechaFinal"/>
							</display:table>
							<div class="separador8">&nbsp;</div>
						</div>
					</c:if>
				</div>
			</td>
		</tr>
		</table>

		<div class="separador8">&nbsp;</div>
		</tiles:put>
	</tiles:insert>
	
	</div>
	</c:if>
	
	<html:hidden property="id"/>
	<html:hidden property="idSerie"/>
	<html:hidden property="estado"/>
	<html:hidden property="edicion" value="true"/>
	<input type="hidden" name="type" value="<c:out value="${param.type}"/>"/>
	
	</html:form>
	</tiles:put>
</tiles:insert>