<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!-- Establecemos el nombre del formulario a buscar en el fichero de validacion
	 en funcion de si hay cargado un documento -->
<logic:empty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ID)">
	<c:set var="formName" value="Tramite"/>
</logic:empty>

<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ID)">
	<c:set var="formName" value="DocTramite"/>
</logic:notEmpty>

<script language="JavaScript" type="text/javascript">

	function save() {

		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do";
		document.defaultForm.name = '<c:out value="${formName}"/>';

		<logic:notEmpty scope="request" name="displayTagOrderParams">
			document.defaultForm.action = document.defaultForm.action + "?" + '<bean:write scope="request" name="displayTagOrderParams" filter="false"/>';
		</logic:notEmpty>


		//if (validate<c:out value="${formName}"/>(document.defaultForm))
			document.defaultForm.submit();

		ispac_needToConfirm = true;
	}

</script>

<html:form action="showTask.do">

	<!-- Nombre de Aplicación.
		 Necesario para realizar la validación -->
	<html:javascript formName='<%=(String)pageContext.getAttribute("formName")%>'/>

	<!-- Solapa a activar-->
	<html:hidden property="block" value="1"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>

	<html:hidden property="property(SPAC_DT_TRAMITES:ID)"/>

	<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID)"/>
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID_FASE)"/>
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)"/>
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID_TPDOC)"/>

	<%--
	//Quitamos este campo el cual producía errores cuando, tras sellar un documento, se guardaban los datos del formulario.
	//Ya que cuando se sella un documento se crea una referencia nueva en el gestor documental (por transacionalidad), pero en este campo estaba
	//la anterior, por lo que si se daba a guardar se machaba la nueva con la vieja, la cual ya no existia en el gestor documental.
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:INFOPAG)"/>
	--%>

	<html:hidden property="property(SPAC_DT_DOCUMENTOS:ORIGEN_ID)" />
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:ORIGEN_TIPO)" />
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)" />
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)" />
	<html:hidden property="property(SPAC_DT_DOCUMENTOS:BLOQUEO)" />

	<table cellpadding="0" cellspacing="0" border="0" width="100%">

		<tr>
			<td>

				<table class="boxTab" width="100%" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td height="28px" class="title">

							<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">

								<tr>
									<td>

										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%" height="28">

											<tr>
												<td>

													<!-- ACCIONES PARA EL TRÁMITE -->
													<logic:present name="form">
														<div class="acciones_ficha">
															<input type="button" value='<bean:message key="common.message.close"/>' class="btnCancel" onclick='<ispac:hideframe refresh="false"/>'/>
						                                </div>
													</logic:present>
													<tiles:insert template="/forms/common/actionsTask.jsp" />

												</td>
											</tr>

											<%--
											<logic:equal value="true" property="readonly" name="defaultForm">

												<logic:notEqual name="defaultForm" property="property(SPAC_DT_TRAMITES:ESTADO)" value="3">

													<tr>
														<td style="text-align:left;" class="errorRojo">
															<img height="1" src='<ispac:rewrite href="img/error.gif"/>'/>

															<!-- Estado de Bloqueo del documento -->
															<c:set var="_lockState">
																<bean:write name="defaultForm" property="entityApp.property(BLOQUEO)"/>
															</c:set>
															<c:choose>
																<c:when test="${(empty _lockState) || (_lockState == appConstants.documentLockStates.UNLOCK)}">
																	<bean:message key="forms.task.blocked"/>
																</c:when>
																<c:otherwise>
																	<bean:message key="forms.listdoc.blocked"/>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>

												</logic:notEqual>

											</logic:equal>
											--%>

										</table>

									</td>
								</tr>

							</table>

						</td>
					</tr>
					<tr>
						<td class="blank">

							<table width="100%" border="0" cellspacing="2" cellpadding="2">

								<tr>
									<%--
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
									--%>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">

										<!-- PESTAÑAS -->
										<table border="0" cellspacing="0" cellpadding="0">

											<tr>
												<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
													<nobr><bean:message key="forms.task.task_doc"/></nobr>
												</td>
											</tr>

										</table>

										<!-- CONTENIDO DE LA PRIMERA PESTAÑA  -->
										<div style="display:block" id="block1">

											<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">

												<tr>
													<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
												</tr>
												<tr>
													<td><img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
												</tr>
												<tr>
													<td>

														<!-- CAMPOS DE TRÁMITE -->
														<tiles:insert template="/forms/common/dataTaskEnvioSesion.jsp" />

													</td>
												</tr>
												<tr>
													<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
												</tr>

											</table>

										</div>

									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>

							</table>

						</td>
					</tr>

				</table>

			</td>
		</tr>

	</table>

</html:form>

<%-- Manejador de block para resituarse en la pestaña en la que nos encontrabamos --%>
<tiles:insert template="/forms/common/manageBlock.jsp"/>

<%-- Para informar si se intenta salir del formulario sin guardar --%>
<tiles:insert template="/forms/common/observer.jsp"/>
