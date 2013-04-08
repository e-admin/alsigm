<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="actionMapping" mapping="/gestionRegistroConsultaAction" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="mostrarNavegacion" value="${sessionScope[appConstants.salas.MOSTRAR_NAVEGACION_REGISTRO_KEY]}" />
<c:set var="archivos" value="${sessionScope[appConstants.salas.LISTA_ARCHIVOS_KEY]}" />

<html:form action="/gestionRegistroConsultaAction">
	<input type="hidden" name="method" value="crearRegistro" />
	<html:hidden property="elementoSeleccionado" styleId="elementoSeleccionado"/>
	<html:hidden property="tipoSeleccionado" styleId="tipoSeleccionado"/>

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.salas.registro.crear"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<script type="text/javascript" language="javascript">
				function save() {
					var form = document.forms['<c:out value="${actionMapping.name}" />'];
					var navegador = window.frames["navegador"];
					if(navegador != null){
						var valor = navegador.getValorSeleccionado();
						var tipo = navegador.getTipoSeleccionado();
						document.getElementById("elementoSeleccionado").value = valor;
						document.getElementById("tipoSeleccionado").value = tipo;
						if(valor == null || valor == "" ){
							alert("<bean:message key='archigest.archivo.salas.seleccioneMesa'/>");
							return;
						}
						form.submit();
					}else{
						alert("<bean:message key='archigest.archivo.salas.seleccioneMesa'/>");
						return;
					}
				}

				function reload() {
					var form = document.forms['<c:out value="${actionMapping.name}" />'];
					form.method.value = 'reload';
					form.submit();
				}
			</script>
			<table>
				<tr>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true">
							<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
							<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
						</tiles:insert>
					</td>
					<td width="10"></td>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:save()" >
							<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.siguiente"/>
						</a>
					</td>
					<td width="10"></td>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true">
							<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
						</tiles:insert>
					</td>
				</tr>
			</table>
		</tiles:put>
		<tiles:put name="boxContent" direct="true">
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.salas.datos.usuario.consulta"/>
				</tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<table>
						<tr>
							<td>
								<a href="javascript:showHideDiv('User');">
									<html:img styleId="imgUser" page="/pages/images/down.gif" titleKey="archigest.archivo.desplegar" altKey="archigest.archivo.desplegar" styleClass="imgTextBottom" />
				   				</a>
				   			</td>
				   		</tr>
				   	</table>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<tiles:insert name="salas.datos.usuario.consulta" flush="true"/>
				</tiles:put>
			</tiles:insert>

			<div class="separador5">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.salas.datos.registro"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<table class="formulario">
						<tr>
							<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.archivo"/>:</td>
							<td class="tdDatos">
								<html:select property="idArchivo" onchange="javascript:reload();">
									<html:option value="">&nbsp;</html:option>
									<html:options collection="archivos" property="idArchivo" labelProperty="nombreArchivo"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tdTitulo"><bean:message key="archigest.archivo.salas.equipo.informatico"/>:</td>
							<td class="tdDatos">
								<html:select property="equipoInformatico" onchange="javascript:reload();">
									<html:option value="">&nbsp;</html:option>
									<html:option value="S"><bean:message key="archigest.archivo.si"/></html:option>
									<html:option value="N"><bean:message key="archigest.archivo.no"/></html:option>
								</html:select>
							</td>
						</tr>
					</table>

					<c:if test="${mostrarNavegacion}">
					<div id="naveg" >
						<table class="w100" cellpadding=0 cellspacing=0>
							<tr>
								<td>
									<c:url var="urlNavegador" value="/action/navegadorRegistroConsultaSalaAction">
										<c:param name="method" value="initial"/>
										<c:param name="idArchivo" value="${form.idArchivo}"/>
										<c:param name="equipoInformatico" value="${form.equipoInformatico}"/>
									</c:url>
									<script type="text/javascript" language="javascript">
										function resizeNavegador() {
											var frameNavegador = document.getElementById("navegador");
											if (frameNavegador.contentDocument && frameNavegador.contentDocument.body.offsetHeight)
												frameNavegador.height = frameNavegador.contentDocument.body.offsetHeight;
											else if (frameNavegador.Document && frameNavegador.Document.body.scrollHeight)
												frameNavegador.height = frameNavegador.Document.body.scrollHeight;
										}

										if (window.top.showWorkingDiv) {
											var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
											var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
											window.top.showWorkingDiv(title, message);
										}
									</script>

									<iframe id="navegador" name="navegador" src='<c:out value="${urlNavegador}" escapeXml="false"/>' frameborder="0" height="100%" width="100%">
									</iframe>

									<script type="text/javascript" language="javascript">
										var frameNavegador = document.getElementById("navegador");
										if (frameNavegador.addEventListener){
											frameNavegador.addEventListener("load", resizeNavegador, false);
										}else if (frameNavegador.attachEvent) {
											frameNavegador.detachEvent("onload", resizeNavegador);
											frameNavegador.attachEvent("onload", resizeNavegador);
										}
									</script>
								</td>
							</tr>
						</table>
					</div>
					</c:if>
				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>