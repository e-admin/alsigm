<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../../dwr/interface/CatalogAPI.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>

	
<div id="navmenutitle">
	<bean:message key="form.help.mainTitle"/>
</div>

<div id="navSubMenuTitle">
</div>

<div id="navmenu" >
	<ul class="actionsMenuList">
		<logic:equal name="uploadForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_HELPS_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntityUp.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
		
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTHelpList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
			</li>
		</logic:equal>
		<logic:notEqual name="uploadForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_HELPS_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntityUp.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTHelpList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
			</li>
			<ispac:hasFunction functions="FUNC_COMP_HELPS_EDIT">
			<li>
				<a href="javascript:query('<%= request.getContextPath() + "/deleteCTEntity.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>		
			</li>
			</ispac:hasFunction>
			
	  </logic:notEqual>
	  </ul>
</div>
<html:form action='/showCTEntityUp.do' enctype="multipart/form-data">


	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación
 	--%>
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(ID)"/>
	
	
	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
											<tr>
												<td>
												<logic:messagesPresent>
													<div id="formErrorsMessage">
														<bean:message key="forms.errors.formErrorsMessage"/>
													</div>
												</logic:messagesPresent>
												</td>
											</tr>
											<tr>
												<td>
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0">
																	<logic:notEqual name="uploadForm" property="key" value="-1">
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.help.propertyLabel.id" tooltipKey="form.help.propertyLabel.id.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																				<div id="formErrors">
																					<html:errors property="property(ID)"/>
																				</div>
																			</td>
																		</tr>
																	</logic:notEqual>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.help.propertyLabel.name" tooltipKey="form.help.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputSelectS" readonly="false" maxlength="250"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.help.propertyLabel.tipo" tooltipKey="form.help.propertyLabel.tipo.tooltip"/>
																			</td>
																			<td height="20">&nbsp;&nbsp;<html:select  styleId="tipoObjSelect" property="property(TIPO_OBJ)"  styleClass="inputSelectS"  >
																						
																						<html:option value="20" key="help.tipo_obj.20"/>
																						<html:option value="0" key="help.tipo_obj.0"/>
																						<html:option value="1" key="help.tipo_obj.1"/>
																						<html:option value="2" key="help.tipo_obj.2"/>
																						<html:option value="3" key="help.tipo_obj.3"/>
																						<html:option value="4" key="help.tipo_obj.4"/>
																						<html:option value="5" key="help.tipo_obj.5"/>
																						<html:option value="6" key="help.tipo_obj.6"/>
																						
																						<html:option value="8" key="help.tipo_obj.8"/>
																						<html:option value="9" key="help.tipo_obj.9"/>
																						<html:option value="10" key="help.tipo_obj.10"/>
																						<html:option value="11" key="help.tipo_obj.11"/>
																						<html:option value="12" key="help.tipo_obj.12"/>
																						<html:option value="13" key="help.tipo_obj.13"/>
																						<html:option value="10" key="help.tipo_obj.10"/>
																						<html:option value="7" key="help.tipo_obj.7"/>
																						
														
																						<html:option value="30" key="help.tipo_obj.30"/>
																						<html:option value="31" key="help.tipo_obj.31"/>
																						<html:option value="32" key="help.tipo_obj.32"/>
																						<html:option value="33" key="help.tipo_obj.33"/>
																						<html:option value="34" key="help.tipo_obj.34"/>
																						<html:option value="35" key="help.tipo_obj.35"/>
																						<html:option value="36" key="help.tipo_obj.36"/>
																						<html:option value="37" key="help.tipo_obj.37"/>
																						<html:option value="38" key="help.tipo_obj.38"/>
																						<html:option value="39" key="help.tipo_obj.39"/>
																						<html:option value="40" key="help.tipo_obj.40"/>
																						<html:option value="41" key="help.tipo_obj.41"/>
																						<html:option value="42" key="help.tipo_obj.42"/>
																						
																						
										
																					</html:select>
																					<html:errors property="property(TIPO_OBJ)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.help.propertyLabel.objeto" tooltipKey="form.help.propertyLabel.objeto.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:select  styleId="objetoAyudaSelect" property="property(ID_OBJ)"  styleClass="inputSelectS"  />
																				<div id="formErrors">
																					<html:errors property="property(ID_OBJ)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																			<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.help.propertyLabel.idioma" tooltipKey="form.search.propertyLabel.idioma.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:select property="property(IDIOMA)" styleClass="inputSelectS"  >	
																						<html:option value=""><bean:message key="language.porDefecto"/></html:option> 
																						<html:options collection="languages" property="property(CLAVE)" labelProperty="property(IDIOMA)" />
																					</html:select>	
																						
																					
																				</div>
																			</td>
																			
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.help.propertyLabel.contenido" tooltipKey="form.help.propertyLabel.contenido.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(CONTENIDO)" styleClass="input" readonly="false" cols="72" rows="7" styleId="texta"/>&nbsp; <bean:message key="catalog.data.obligatory"/><br>
																				&nbsp;&nbsp;<html:file styleClass="input" property="uploadFile" styleId="FileUp" onchange="javascript:ispac_loadFile();"/><br>
																				<div id="formErrors">
																					<html:errors property="property(CONTENIDO)"/>
																				</div>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
											<tr>
												<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
										</table>
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

	<script>
	var controlPaso = true;
	var controlFile = "";
	
		function ispac_loadFile()
		{
			if (controlPaso) {
				if (document.getElementById("FileUp").value != "" && document.getElementById("FileUp").value != controlFile) {
					controlFile = document.getElementById("FileUp").value;
					controlPaso = false;
					document.forms[0].action ='<%= request.getContextPath() + "/loadFile.do"%>';
					document.forms[0].submit();
				}
			}
			else
			{
				controlFile = document.getElementById("FileUp").value;
				controlPaso = true;
			}
		}
		function loadObjetoAyuda(tipoObj) {
		CatalogAPI.getObjectToHelp(tipoObj, function(data) {
	
				DWRUtil.removeAllOptions("objetoAyudaSelect");
				if (data) {
					DWRUtil.addOptions("objetoAyudaSelect", data, "key", "value");

				}
				
				
			});
		}
		
		
		$(document).ready(function() {
			$("#tipoObjSelect").change(function() {
				$("#tipoObjSelect option:selected").each(function () {
					loadObjetoAyuda($(this).val());
				});
			});
	
            // Establecer el gestor de errores
            dwr.engine.setErrorHandler(errorHandler);
            
            //Precargar el objeto ayuda cuando el tipo objeto tiene algo
            	$("#tipoObjSelect option:selected").each(function () {
					CatalogAPI.getObjectToHelp($(this).val(), function(data) {
						DWRUtil.removeAllOptions("objetoAyudaSelect");
						if (data) {
							DWRUtil.addOptions("objetoAyudaSelect", data, "key", "value");
							$("#objetoAyudaSelect").val('<bean:write  name="uploadForm" property="property(ID_OBJ)"/>');
						}
					});	
				});
		});
		
		   function errorHandler(message, exception) {
            jAlert(message, '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
        	}
        
	
	</script>