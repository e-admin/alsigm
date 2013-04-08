<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>




<script>
	function crearSolicitudSubsanacion(idTramite, _return)
	{
		var data = checkboxElement(document.forms['customBatchForm']);
		if (data == "") {
			
			jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			return;
		}
		
		document.customBatchForm.target = "ParentWindow";
		document.customBatchForm.action = "crearSolicitudSubsanacion.do?taskId="+idTramite;
		
		if (_return != null)
		   document.customBatchForm.action =  document.customBatchForm.action+"&return="+_return;
		document.customBatchForm.submit();
	}
</script>

<html:form action="crearSolicitudSubsanacion.do">

    <!-- XML Con la lista de documentos. Se incluye para ser tratado en la accion de Crear la Solicitud de Subsanacion -->
    <logic:present name="XMLDocumentos">	
	    <html:hidden property="xml" value='<%=""+request.getAttribute("XMLDocumentos")%>'/>
	</logic:present>



<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
		<td align="right"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
		</td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" border="0" width="100%" class="box">
				<logic:present name="listDocumentos">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<!-- COMIENZO DE LAS ACCIONES -->
									<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
										<tr>
											<!-- ACCION DOCUMENTACION CORRECTA -->
											<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											<td class="formaction" height="28px" width="100%">
												<logic:present name="subsanacionCreado">
													<a href="javascript:crearSolicitudSubsanacion('<c:out value="${subsanacionCreado}"/>', 'yes');" class="formaction">
														<bean:message key="comprobarDocumentacion.boton.guardar"/>
													</a>
												</logic:present>
												<logic:notPresent name="subsanacionCreado">
													<a href="javascript:crearSolicitudSubsanacion(0, 'yes');" class="formaction">
														<bean:message key="comprobarDocumentacion.boton.guardar"/>
													</a>
												</logic:notPresent>

												<html:link action="documentacionCompleta.do" styleClass="formaction">
													<bean:message key="comprobarDocumentacion.boton.documentacionCorrecta"/>
												</html:link>

												<logic:present name="subsanacionCreado">
													<a href="javascript:crearSolicitudSubsanacion('<c:out value="${subsanacionCreado}"/>', null);" class="formaction">
														<bean:message key="comprobarDocumentacion.boton.solicitudSubsanacion"/>
													</a>
												</logic:present>
												<logic:notPresent name="subsanacionCreado">
													<a href="javascript:crearSolicitudSubsanacion(0, null);" class="formaction">
														<bean:message key="comprobarDocumentacion.boton.solicitudSubsanacion"/>
													</a>
												</logic:notPresent>
											</td>
										</tr>
									</table>
									<!-- FINAL DE LAS ACCIONES -->
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				</logic:present>

				<tr>
					<td class="blank">
						<table width="100%">
							<tr>
								<td class="formsTitleB">
									<logic:present name="listDocumentos">
										<img src='<ispac:rewrite href="img/pixel.gif"/>' width="20px"/>
										<nobr><bean:message key="comprobarDocumentacion.informacion"/></nobr>
									</logic:present>
									<logic:notPresent name="listDocumentos">
										<img src='<ispac:rewrite href="img/pixel.gif"/>' width="20px"/>
										<nobr><bean:message key="comprobarDocumentacion.sinDocumentos"/></nobr>
									</logic:notPresent>
								</td>
							<tr>
						</table>

						<html:errors/>
						
						<!-- FORMULARIO -->
						<logic:present name="listDocumentos">
							<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
								<tr>
									<td align="center"> 	
										<display:table name="listDocumentos"
													   id="object" export="true"
													   class="tableDisplay"
											  		   sort="list"
											  		   requestURI='<%= request.getContextPath()+"/comprobarDocumentacion.do" %>'>
											  		   
											<display:column titleKey="comprobarDocumentacion.etiqueta.pendiente" 
															headerClass="headerDisplayLeft"
															class="width10percent">
												<html:multibox property="multibox">
													<bean:write name="object" property="string(ID)" />
												</html:multibox>
											</display:column>
											
											<display:column titleKey="comprobarDocumentacion.etiqueta.documento" 
															headerClass="headerDisplayLeft"
															class="width90percent">
												<bean:write name="object" property="string(DOCUMENTO)" />
											</display:column>
											
										</display:table>
									</td>
								</tr>
							</table>
						
							
						</logic:present>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</html:form>
