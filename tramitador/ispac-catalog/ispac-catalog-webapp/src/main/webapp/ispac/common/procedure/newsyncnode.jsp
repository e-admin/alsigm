<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>



<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="catalog.create.psyncnode"/></h4>
		<div class="acciones_ficha">
			<a href="javascript:document.defaultForm.submit();" id="btnOk" class="btnOk">
				<bean:message key="forms.button.accept"/>
			</a>
			<a  id="btnCancel" class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
			
		</div>
	</div>
</div>




<div id="navmenu">
	<div id="navmenu">
		<tiles:insert page="../tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
	</div>

</div>

<div class="stdform">
<html:form action='/addPcdSyncNode.do'>

	<html:hidden property="entityAppName"/> 
	<html:hidden property="entity"/>
	<html:hidden property="key"/>
	
	<html:hidden property="property(ID_PCD)"/>
	
	<table width="100%" cellspacing="1" cellpadding="0">
		<tr>
			<td class="blank">
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td width="100%">
							<div class="formtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<logic:notEqual name="defaultForm" property="key" value="-1">
													<tr>
														<td height="20" class="formsTitle" width="1%">
															<nobr><bean:message key="form.syncnode.propertyLabel.id"/>:</nobr>
														</td>
														<td height="20">
															&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" />
														</td>
													</tr>
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
												</logic:notEqual>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<nobr><bean:message key="form.syncnode.propertyLabel.pcd"/>:</nobr>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(NOMBRE_PCD)" styleClass="inputReadOnly" size="50" readonly="true" />
														<div id="formErrors">
																<html:errors property="property(NOMBRE_PCD)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<nobr><bean:message key="form.syncnode.propertyLabel.type"/>:</nobr>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:select property="property(TIPO)" styleClass="inputSelectScustomWidth">
															<html:option value="1" key="syncnode.type.1"/>
															<html:option value="2" key="syncnode.type.2"/>
														</html:select>
														&nbsp; <bean:message key="catalog.data.obligatory"/>
														<div id="formErrors">
																<html:errors property="property(TIPO)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
</div>
</div>
<script>
	$(document).ready(function() {

			$("#move").draggable();
			
		
		});


//--></script>