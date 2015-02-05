<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="usuario" value="${sessionScope[appConstants.salas.USUARIO_ARCHIVO_KEY]}" />
<c:set var="lista" value="${usuario.listaArchivos}" />
<c:set var="listaArchivosSeleccionar" value="${sessionScope[appConstants.salas.LISTA_ARCHIVOS_SELECCIONAR_KEY]}"/>
<bean:struts id="actionMapping" mapping="/gestionUsuarioSalasConsultaAction" />

<security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">
<script language="javascript">
	function editar()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value="editar";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
</script>
</security:permissions>

<security:permissions action="${appConstants.salasActions.ELIMINAR_USUARIO_ACTION}">
<script language="javascript">
function eliminar(){
	if(window.confirm('<fmt:message key="archigest.archivo.salas.eliminar.usuario.msg"/>')){
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value="eliminar";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
}
</script>
</security:permissions>
<html:form action="/gestionUsuarioSalasConsultaAction">
<input type="hidden" name="method" id="method" value=""/>
<input type="hidden" name="id" value="<c:out value="${usuario.id}"/>"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.usuario.ver"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<c:if test="${empty listaArchivosSeleccionar }">
			<tr>

				<security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">



				<td nowrap="nowrap">
					<a class="etiquetaAzul12Bold" href="javascript:editar()" >
						<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.editar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>

				<security:permissions action="${appConstants.salasActions.ELIMINAR_USUARIO_ACTION}">
				<td nowrap="nowrap">
						<a class="etiquetaAzul12Bold"
						href="javascript:eliminar()">
						<html:img page="/pages/images/delete.gif"
							altKey="archigest.archivo.eliminar"
							titleKey="archigest.archivo.eliminar"
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
			</c:if>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
				<tiles:insert name="salas.datos.usuario" flush="true" ignore="true"/>
		</div>
		<div class="separador5">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.archivos.asociados"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">
				<c:if test="${empty listaArchivosSeleccionar }">
				<script language="javascript">
					function desasociarArchivos(){
						var form = document.forms['<c:out value="${actionMapping.name}" />'];
						if (form.idsArchivo && !elementSelected(form.idsArchivo)){
							alert("<bean:message key='archigest.archivo.sala.esNecesarioSeleccionarAlMenosUnArchivo'/>");
						}
						else if(window.confirm('<fmt:message key="archigest.archivo.salas.desasociar.archivo.usuario.msg"/>')){
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.method.value="desasociarArchivos";
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							form.submit();
						}
					}

					function asociarArchivos(){
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.method.value="asociarArchivos";
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							form.submit();
					}
				</script>

				<table cellpadding="0" cellspacing="0">
					<tr>
						<td nowrap="nowrap">
							<a class="etiquetaAzul12Bold" href="javascript:asociarArchivos()">
								<html:img page="/pages/images/table_add.gif"
									altKey="archigest.archivo.salas.usuario.asociar.archivo"
									titleKey="archigest.archivo.salas.usuario.asociar.archivo"
									styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.salas.usuario.asociar.archivo"/>
							</a>
						</td>
						<td width="10">&nbsp;</td>
						<td nowrap="nowrap">
							<a class="etiquetaAzul12Bold" href="javascript:desasociarArchivos()">
								<html:img page="/pages/images/table_delete.gif"
									altKey="archigest.archivo.salas.usuario.desasociar.archivo"
									titleKey="archigest.archivo.salas.usuario.desasociar.archivo"
									styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.salas.usuario.desasociar.archivo"/>
							</a>
						</td>
						<td width="10">&nbsp;</td>
				</tr>
				</table>
				</c:if>
				</security:permissions>


			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
					<tiles:put name="blockContent" direct="true">
						<div class="bloque">
							<div class="separador1">&nbsp;</div>

							<display:table name="pageScope.lista"
								id="archivo"
								style="width:99%;margin-left:auto;margin-right:auto">

								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.salas.usuario.sin.archivos.asociados"/>
								</display:setProperty>


								<security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">
								<c:if test="${empty listaArchivosSeleccionar }">
								<display:column media="html" style="width:10px">
									<input type="checkbox" name="idsArchivo" value="<c:out value="${archivo.idArchivo}"/>"/>
								</display:column>
								</c:if>
								</security:permissions>
								<display:column titleKey="archigest.archivo.archivo">
									<c:out value="${archivo.nombreArchivo}"/>
								</display:column>
							</display:table>

							<div class="separador1">&nbsp;</div>
						</div>
					</tiles:put>
				</tiles:insert>
			</tiles:put>
		</tiles:insert>

		<c:if test="${not empty listaArchivosSeleccionar }">
			<div class="separador5">&nbsp;</div>
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.asociar.archivos"/></tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">

					<script language="javascript">
						function cancelarAsociarArchivos(){
								var form = document.forms["<c:out value="${actionMapping.name}" />"];
								form.method.value="cancelarAsociarArchivos";
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
								}

								form.submit();
						}

						function aceptarAsociarArchivos(){
								var form = document.forms["<c:out value="${actionMapping.name}" />"];
								if(form.idsArchivo && numElementsSelected(form.idsArchivo) > 0){
									form.method.value="aceptarAsociarArchivos";
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}

									form.submit();
								}else{
									alert("<bean:message key='archigest.archivo.sala.esNecesarioSeleccionarAlMenosUnArchivo'/>");
								}
						}
					</script>

					<table cellpadding="0" cellspacing="0">
						<tr>
							<td nowrap="nowrap">
								<a class="etiquetaAzul12Bold" href="javascript:aceptarAsociarArchivos()">
									<html:img page="/pages/images/Ok_Si.gif"
										altKey="archigest.archivo.aceptar"
										titleKey="archigest.archivo.aceptar"
										styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.aceptar"/>
								</a>
							</td>
							<td width="10">&nbsp;</td>
							<td nowrap="nowrap">
								<a class="etiquetaAzul12Bold" href="javascript:cancelarAsociarArchivos()">
									<html:img page="/pages/images/Ok_No.gif"
										altKey="archigest.archivo.cancelar"
										titleKey="archigest.archivo.cancelar"
										styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.cancelar"/>
								</a>
							</td>
							<td width="10">&nbsp;</td>
					</tr>
					</table>
					</security:permissions>
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
					<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
						<tiles:put name="blockContent" direct="true">
							<div class="separador1">&nbsp;</div>
							<display:table name="pageScope.listaArchivosSeleccionar"
								id="archivo"
								style="width:99%;margin-left:auto;margin-right:auto">
								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.salas.usuario.sin.archivos.asociados"/>
								</display:setProperty>
								<security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">
								<display:column media="html" style="width:10px">
									<input type="checkbox" name="idsArchivo" value="<c:out value="${archivo.idArchivo}"/>"/>
								</display:column>
								</security:permissions>
								<display:column titleKey="archigest.archivo.archivo">
									<c:out value="${archivo.nombreArchivo}"/>
								</display:column>
							</display:table>
							<div class="separador1">&nbsp;</div>
						</tiles:put>
					</tiles:insert>
				</tiles:put>

			</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>