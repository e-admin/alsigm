<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>


<c:set var="descriptores" value="${sessionScope[appConstants.descripcion.DESCRIPTORES_LISTA_KEY]}"/>
<bean:struts id="actionMapping" mapping="/listasDescriptoras" />

<script language="JavaScript1.2" src="../../js/utils.js" type="text/JavaScript"></script>
<c:url var="cancelURL" value="/action/listasDescriptoras">
		<c:param name="method" value="goBack" />
</c:url>

<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">

<script language="JavaScript1.2" type="text/JavaScript">
	function edit()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].method.value = "form";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function cancel(){

		window.location.href = '<c:out value="${cancelURL}" escapeXml="false"/>';
	}
	function removeDescriptors()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.ids && elementSelected(form.ids))
		{
			if (confirm("<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.descriptores.delete.confirm.msg"/>"))
			{
				document.forms["<c:out value="${actionMapping.name}" />"].method.value = "removeDescriptors";
				document.forms["<c:out value="${actionMapping.name}" />"].submit();
			}
		}
		else
			alert("<bean:message key='archigest.archivo.descripcion.listasDescriptoras.form.descriptores.delete.warning.msg'/>");
	}
	function remove()
	{
		if (confirm("<bean:message key="archigest.archivo.descripcion.listaDescriptoras.delete.confirm.msg"/>"))
		{
			<c:url var="removeURL" value="/action/listasDescriptoras">
				<c:param name="method" value="removeLista" />
				<c:param name="id" value="${listaDescriptoraForm.id}"/>
				<c:param name="name" value="${listaDescriptoraForm.nombre}"/>
			</c:url>
			window.location = "<c:out value="${removeURL}" escapeXml="false"/>";
		}
	}

	function validarDescriptores(){
		if (confirm("<bean:message key="archigest.archivo.descripcion.descriptores.validate.confirm.msg"/>")){
			document.getElementById("method").value="validarDescriptores";

			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
			document.getElementById("formulario").submit();
		}
	}
</script>
</security:permissions>
<div id="contenedor_ficha">
  <html:form action="/listasDescriptoras" styleId="formulario">
  <html:hidden property="id"/>
  <input type="hidden" name="method" id="method" value="removeDescriptors"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.descriptores.listaDescriptora"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td><a class="etiquetaAzul12Bold"
										   href="javascript:cancel()"
										><html:img page="/pages/images/close.gif"
										        border="0"
										        altKey="archigest.archivo.cerrar"
										        titleKey="archigest.archivo.cerrar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors /></div>

			<div class="separador1">&nbsp;</div>

			<div class="cabecero_bloque"><%--cabecero datos de la lista descriptora --%>
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.informacion"/>
							</td>
							<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">
					    	<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
									   	<td><a class="etiquetaAzul12Normal"
											   href="javascript:edit()"
											><html:img page="/pages/images/edit.gif"
											        altKey="archigest.archivo.editar"
											        titleKey="archigest.archivo.editar"
											        styleClass="imgTextMiddle"/>
									        &nbsp;<bean:message key="archigest.archivo.editar"/></a></td>
										<td width="10">&nbsp;</td>
										<c:if test="${!empty listaDescriptoraForm.id}">
									   	<td>
											<a class="etiquetaAzul12Normal"
											   href="javascript:remove()"
											><html:img page="/pages/images/delete.gif"
											        altKey="archigest.archivo.eliminar"
											        titleKey="archigest.archivo.eliminar"
											        styleClass="imgTextMiddle"/>
									        &nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
										</td>
										<td width="10">&nbsp;</td>
										</c:if>
									</tr>
								</table>
							</td>
							</security:permissions>
						</tr>
					</tbody>
				</table>
			</div> <%--cabecero datos de la lista descriptora --%>

			<div class="bloque"><%--bloque datos lista descriptora --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><bean:write name="listaDescriptoraForm" property="nombre" /></td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<logic:equal name="listaDescriptoraForm" property="tipo" value="1">
								<bean:message key="archivo.listaDescriptora.tipo.abierta"/>
							</logic:equal>
							<logic:equal name="listaDescriptoraForm" property="tipo" value="2">
								<bean:message key="archivo.listaDescriptora.tipo.cerrada"/>
							</logic:equal>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.tipoDescriptor"/>:&nbsp;</td>
						<td class="tdDatos">
							<logic:equal name="listaDescriptoraForm" property="tipoDescriptor" value="0">
								<bean:message key="archivo.descriptores.tipo.sin_tipo_especifico"/>
							</logic:equal>
							<logic:equal name="listaDescriptoraForm" property="tipoDescriptor" value="1">
								<bean:message key="archivo.descriptores.tipo.entidad"/>
							</logic:equal>
							<logic:equal name="listaDescriptoraForm" property="tipoDescriptor" value="2">
								<bean:message key="archivo.descriptores.tipo.geografico"/>
							</logic:equal>
							<logic:equal name="listaDescriptoraForm" property="tipoDescriptor" value="3">
								<bean:message key="archivo.descriptores.tipo.materia"/>
							</logic:equal>
							&nbsp;
						</td>
					</tr>
					<c:if test="${!empty listaDescriptoraForm.nombreFichaDescrPref or !empty listaDescriptoraForm.nombreFichaClfDocPref or !empty listaDescriptoraForm.nombreRepEcmPref}">
					<tr><td>&nbsp;</td></tr>
					</c:if>
					<c:if test="${!empty listaDescriptoraForm.nombreFichaDescrPref}">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.nombreFichaDescrPref"/>:&nbsp;</td>
						<td class="tdDatos"><bean:write name="listaDescriptoraForm" property="nombreFichaDescrPref" /></td>
					</tr>
					</c:if>
					<c:if test="${!empty listaDescriptoraForm.nombreFichaClfDocPref}">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.nombreFichaClfDocPref"/>:&nbsp;</td>
						<td class="tdDatos"><bean:write name="listaDescriptoraForm" property="nombreFichaClfDocPref" /></td>
					</tr>
					</c:if>
					<c:if test="${!empty listaDescriptoraForm.nombreRepEcmPref}">
					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;</td>
						<td class="tdDatos"><bean:write name="listaDescriptoraForm" property="nombreRepEcmPref" /></td>
					</tr>
					</c:if>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="150" style="vertical-align:top;"><bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><c:out value="${listaDescriptoraForm.descripcion}">-</c:out></td>
					</tr>
				</table>
			</div><%--bloque datos lista descriptora --%>

			<logic:present name="listaDescriptoraForm" property="id">
			<div class="separador8">&nbsp;</div>

			<div class="cabecero_bloque"><%--cabecero descriptores de la lista descriptora --%>
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.descriptores.caption"/>
							</td>
							<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">
				            <td align="right">
				              <table cellpadding="0" cellspacing="0">
				                <tr>
								<c:if test="${!empty descriptores}">
				        		  <td nowrap="nowrap">
									  <a class="etiquetaAzul12Normal"
				                         href="javascript:validarDescriptores()"
				                      ><html:img page="/pages/images/descriptores/validar.gif"
				                              altKey="archigest.archivo.validar"
				                              titleKey="archigest.archivo.validar"
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.validar"/></a>

						  		  </td>
								  <td width="10">&nbsp;</td>
								  </c:if>


				                  <td><c:url var="urlAnadir" value="/action/descriptor">
				                  		<c:param name="method" value="formDescriptor"/>
				                  		<c:param name="idLista" value="${listaDescriptoraForm.id}"/>
				                  		<c:param name="tipo" value="${listaDescriptoraForm.tipoDescriptor}"/>
				                  		<c:param name="idFichaDescr" value="${listaDescriptoraForm.idFichaDescrPref}"/>
				                  		<c:param name="idRepEcm" value="${listaDescriptoraForm.idRepEcmPref}"/>
					                  </c:url>
				                  	  <a class="etiquetaAzul12Normal"
				                         href="<c:out value="${urlAnadir}" escapeXml="false"/>"
				                      ><html:img page="/pages/images/addDoc.gif"
				                              altKey="archigest.archivo.anadir"
				                              titleKey="archigest.archivo.anadir"
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.anadir"/></a></td>
				                 <c:if test="${!empty descriptores}">
				                  <td width="10">&nbsp;</td>
				                  <td><a class="etiquetaAzul12Normal"
				                         href="javascript:removeDescriptors()"
				                      ><html:img page="/pages/images/delDoc.gif"
				                              altKey="archigest.archivo.eliminar"
				                              titleKey="archigest.archivo.eliminar"
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
				                  <td width="10">&nbsp;</td>
				                  </c:if>
				                </tr>
				              </table>
				            </td>
				            </security:permissions>
						</tr>
					</tbody>
				</table>
			</div> <%--cabecero descriptores de la lista descriptora --%>

			<div class="bloque"><%--bloque descriptores lista descriptora --%>
				<div class="separador8">&nbsp;</div>
				<div class="w98">
				<center>
					<div class="cabecero_bloque"><%--cabecero datos de la lista descriptora --%>

					<TABLE class="w98" cellpadding=0 cellspacing=0>
					  <TR>
						<TD class="etiquetaAzul12Bold" height="25px">
							<bean:message key="archigest.archivo.filtro.busqueda"/>
						</TD>
						<TD align="right">
							<table cellpadding="0" cellspacing="0">
							<tr>
								<td nowrap="nowrap">
									<a href="javascript:filtrar(this);" class="etiquetaAzul12Normal">
										<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar"
					        			titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle"/>&nbsp;
					        			<bean:message key="archigest.archivo.buscar"/>
							        </a>
								</td>
								<td width="10">&nbsp;</td>
								<td nowrap="nowrap">
									<a href="javascript:limpiar()" class="etiquetaAzul12Normal">
										<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar"
							        		titleKey="archigest.archivo.limpiar" styleClass="imgTextMiddle"/>&nbsp;
							        	<bean:message key="archigest.archivo.limpiar"/>
							        </a>
								</td>
							</tr>
						</table>
						</TD>
					  </TR>
					</TABLE>
					</div>

						<div class="bloque">
							 <script type="text/javascript">
								function filtrar(enlace){
									var backupMethod=document.forms[0].method;
									document.forms[0].method.value='filtrar';

									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}

									document.forms[0].submit();
								}

								function clean(){
									document.getElementById("calificadorFiltro").value="";
									document.getElementById("filtro").value="";
									document.getElementById("filtroEstado").value=0;
								}
							</script>
							<div class="w98">
							<table class="formulario" cellpadding="0" cellspacing="0">
								<tr>
									<td class="etiquetaAzul11Bold" width="100px">
										<bean:message key="archigest.archivo.nombre"/>:
									</td>
									<td nowrap="nowrap">
										<html:select property="calificadorFiltro" styleId="calificadorFiltro" style="height:15px">
												<html:option value="igual"><bean:message key="archigest.archivo.simbolo.exacta"/></html:option>
												<html:option value="empieza"><bean:message key="archigest.archivo.simbolo.quecomiencecon"/></html:option>
												<html:option value="termina"><bean:message key="archigest.archivo.simbolo.queterminecon"/></html:option>
												<html:option value="contiene"><bean:message key="archigest.archivo.simbolo.quecontenga"/></html:option>
										</html:select>
										<html:text property="filtro" styleId="filtro" styleClass="input60" maxlength="512"/>&nbsp;

									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="separador1">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td class="etiquetaAzul11Bold">
										<bean:message key="archigest.archivo.descripcion.descriptor.form.estado"/>
									</td>
									<td nowrap="nowrap">
										<html:select property="filtroEstado" styleId="filtroEstado">
											<html:option value="0"><bean:message key="archigest.archivo.todos"/></html:option>
											<html:option value="1"><bean:message key="archivo.estado.validacion.validado"/></html:option>
											<html:option value="2"><bean:message key="archivo.estado.validacion.no_validado"/></html:option>
										</html:select>
									</td>
								</tr>
							</table>
							</div>
						</div>
				</center>
				</div>

				<div class="w98">
				<center>
						<c:if test="${!empty descriptores}">

						<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">


						<TABLE class="w98" cellpadding=0 cellspacing=0>
						  <TR>
							<TD class="etiquetaAzul12Bold" height="25px">
								&nbsp;
							</TD>
							<TD align="right">


						<table cellpadding="0" cellspacing="0">
							<tr>
								<td nowrap="nowrap">
										<a class="etiquetaAzul12Normal"
										       href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].ids);" >
											<html:img page="/pages/images/checked.gif"
												border="0"
												altKey="archigest.archivo.selTodos"
												titleKey="archigest.archivo.selTodos"
												styleClass="imgTextBottom" /><bean:message key="archigest.archivo.selTodos"/>&nbsp;</a>
								</td>
								<td width="10">&nbsp;</td>
								<td nowrap="nowrap">
									<a class="etiquetaAzul12Normal"
								    	       href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].ids);" >
											<html:img page="/pages/images/check.gif"
												border="0"
												altKey="archigest.archivo.quitarSel"
												titleKey="archigest.archivo.quitarSel"
												styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
								</td>
							</tr>
						</table>
						</TD>
						</TR>
						</TABLE>
						</security:permissions>
						</c:if>

						<div class="bloque">
							<display:table name="pageScope.descriptores"
								style="width:99%;margin-left:auto;margin-right:auto"
								id="descriptor"
								pagesize="15"
								requestURI="/action/listasDescriptoras?method=retrieve"
								export="true">
								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.listaVacia"/>
								</display:setProperty>
								<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">
								<display:column title="" headerClass="minusDocIcon" style="width:23px;" media="html">
									<html-el:multibox property="ids" value="${descriptor.id}"></html-el:multibox>
								</display:column>
								</security:permissions>
								<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
									<c:url var="URL" value="/action/descriptor">
										<c:param name="method" value="retrieveDescriptor" />
										<c:param name="id" value="${descriptor.id}" />
										<c:param name="idLista" value="${descriptor.idLista}" />
										<c:param name="tipo" value="${listaDescriptoraForm.tipoDescriptor}" />
									</c:url>
									<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${descriptor.nombre}"/></a>
								</display:column>
								<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
								<display:column titleKey="archivo.estado.validacion.validado" sortProperty="estado" sortable="true" headerClass="sortable" style="width:80px;" media="html">
									<c:choose>
										<c:when test="${descriptor.estado == 1}">
												<html:img page="/pages/images/checkbox-yes.gif"
												   border="0"
												   altKey="archigest.archivo.si"
												   titleKey="archigest.archivo.si"
												   styleClass="imTextMiddle"/>
										</c:when>
										<c:when test="${descriptor.estado == 2}">
											<html:img page="/pages/images/checkbox-no.gif"
												   border="0"
												   altKey="archigest.archivo.no"
												   titleKey="archigest.archivo.no"
												   styleClass="imgTextMiddle"/>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</display:column>

								<display:column titleKey="archivo.estado.validacion.validado" style="width:50px;" media="csv excel xml pdf">
									<c:choose>
										<c:when test="${descriptor.estado == 1}">
											<bean:message key="archigest.archivo.si"/>
										</c:when>
										<c:when test="${descriptor.estado == 2}">
											<bean:message key="archigest.archivo.no"/>
										</c:when>
									</c:choose>
								</display:column>

								<display:column titleKey="archigest.archivo.descripcion.fechaModificacion" sortProperty="fecha" sortable="true" headerClass="sortable" style="width:80px;">
									<bean:write name="descriptor" property="timestamp" format="yyyy/MM/dd HH:mm:ss"/>
								</display:column>
								<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescr" sortable="true" headerClass="sortable" style="width:100px;"/>
								<display:column titleKey="archigest.archivo.descripcion.descrito" sortProperty="tieneDescr" sortable="true" headerClass="sortable" style="width:70px;" media="html">
									<c:choose>
										<c:when test="${descriptor.tieneDescr == 'S'}">
											<html:img page="/pages/images/checkbox-yes.gif"
												   border="0"
												   altKey="archigest.archivo.si"
												   titleKey="archigest.archivo.si"
												   styleClass="imTextMiddle"/>
										</c:when>
										<c:otherwise>
											<html:img page="/pages/images/checkbox-no.gif"
												   border="0"
												   altKey="archigest.archivo.no"
												   titleKey="archigest.archivo.no"
												   styleClass="imgTextMiddle"/>
										</c:otherwise>
									</c:choose>
								</display:column>
								<display:column titleKey="archigest.archivo.descripcion.descrito" style="width:50px;" media="csv excel xml pdf">
									<c:choose>
										<c:when test="${descriptor.tieneDescr == 'S'}">
											<bean:message key="archigest.archivo.si"/>
										</c:when>
										<c:otherwise>
											<bean:message key="archigest.archivo.no"/>
										</c:otherwise>
									</c:choose>
								</display:column>
								<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcm" sortProperty="volumen" sortable="true" headerClass="sortable" style="width:100px;"/>
							</display:table>
						</div>
				</center>
				</div>

				<div class="separador5">&nbsp;</div>


			</div><%--bloque descriptores lista descriptora --%>
			</logic:present>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


