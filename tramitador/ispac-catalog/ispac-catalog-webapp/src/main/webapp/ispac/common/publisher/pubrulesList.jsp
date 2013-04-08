<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="ieci.tdw.ispac.api.item.IProcedure"%>

<bean:define name="pcdId" id="pcdId"/>
<bean:define name="stageId" id="stageId"/>
<bean:define name="taskId" id="taskId"/>
<bean:define name="typeDoc" id="typeDoc"/>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>
<div id="navmenutitle">
	<bean:message key="title.pubRules"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ul class="actionsMenuList">
		<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
		<li>
			<% String URL="/showPubRule.do?entityId=45&regId=-1&pcdId=" + pcdId + "&stageId=" + stageId + "&taskId=" + taskId + "&typeDoc=" + typeDoc;%>
			<a href="javascript:redirect('<%=URL%>')">
				<bean:message key="new.rule"/>
			</a>
		</li>
		</ispac:hasFunction>
		<li>
			<a href='<%=request.getContextPath() + "/showPubRulesGroupList.do"%>'>
				<bean:message key="forms.button.close"/>
			</a>
		</li>
	</ul>
</div>

<html:form action='/showPubRulesList.do'>

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
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
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.procedure" tooltipKey="form.pubRule.propertyLabel.procedure.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(PCD_NAME)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.version" tooltipKey="form.pubRule.propertyLabel.version.tooltip"/>
																			</td>
																			<td height="20">
																				<logic:equal name="defaultForm" property="property(PCD_ESTADO)" value="<%=String.valueOf(IProcedure.PCD_STATE_DRAFT)%>">
																					&nbsp;&nbsp;<input type="text" size="10" class="inputReadOnly" readonly="true" value='<bean:message key="procedure.versions.draft" />' />
																				</logic:equal>
																				<logic:notEqual name="defaultForm" property="property(PCD_ESTADO)" value="<%=String.valueOf(IProcedure.PCD_STATE_DRAFT)%>">
																					&nbsp;&nbsp;<html:text property="property(PCD_NVERSION)" size="10" styleClass="inputReadOnly" readonly="true" />
																				</logic:notEqual>
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.stage" tooltipKey="form.pubRule.propertyLabel.stage.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(STAGE_NAME)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.task" tooltipKey="form.pubRule.propertyLabel.task.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(TASK_NAME)" size="80" styleClass="inputReadOnly" readonly="true" />
																			</td>
																		</tr>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pubRule.propertyLabel.typeDoc" tooltipKey="form.pubRule.propertyLabel.typeDoc.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(TYPEDOC_NAME)" size="80" styleClass="inputReadOnly" readonly="true" />
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


	<display:table name="PubRulesList"
				   id='itemobj'
				   export="false"
				   class="tableDisplay"
				   sort="list"
	   			   pagesize="45"
				   requestURI="/showPubRulesList.do"
				   defaultsort="1">>

		<logic:present name="itemobj">

			<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>

			 <logic:iterate name="PubRulesListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="EVENT_NAME">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									group="1">

						<%=format.formatProperty(item)%>

					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="INFO_NAME">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									group="2">

						<%=format.formatProperty(item)%>

					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="ID_ACCION">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'>

						<ispac:lookUp name="ActionsMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" />

					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="ID_CONDICION">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'>

						<ispac:lookUp name="ConditionsMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" />

					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="ID_APLICACION">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'>

						<ispac:lookUp name="ApplicationsMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" />

					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="MODORDER">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'>

							<bean:define name="item" id="eventId" property="property(ID_EVENTO)"/>
							<bean:define name="item" id="infoId" property="property(ID_INFO)"/>
							<bean:define name="item" id="order" property="property(ORDEN)"/>

							<center>
								<html:link
									action='<%=format.getUrl() + "&ModOrden=INC" + "&pcdId=" + pcdId + "&stageId=" + stageId + "&taskId=" + taskId + "&typeDoc=" + typeDoc + "&eventId=" + eventId + "&infoId=" + infoId + "&order=" + order%>'
									styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
								<html:link
									action='<%=format.getUrl() + "&ModOrden=DEC" + "&pcdId=" + pcdId + "&stageId=" + stageId + "&taskId=" + taskId + "&typeDoc=" + typeDoc + "&eventId=" + eventId + "&infoId=" + infoId + "&order=" + order%>'
									styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>'>-</html:link>
							</center>

					</display:column>
				</logic:equal>


				<logic:equal name="format" property="fieldType" value="LINK">
				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'
				  					style="text-align: center;">

				  		<html:link action='<%=format.getUrl() + "&pcdId=" + pcdId + "&stageId=" + stageId + "&taskId=" + taskId + "&typeDoc=" + typeDoc%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item" paramProperty='<%=format.getPropertyLink() %>'>
							<bean:message key='<%=format.getPropertyValueKey()%>' />
				  		</html:link>

				  	</display:column>
				 </logic:equal>

			</logic:iterate>

		</logic:present>

	</display:table>
	<!-- displayTag -->

</html:form>