<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import='ieci.tdw.ispac.api.ISPACEntities'%>

<c:set var="ENTITY_FORM_READONLY" scope="page"><%=ISPACEntities.ENTITY_FORM_READONLY%></c:set>

<bean:define name="pcdId" id="pcdId"/>
<bean:define name="entityId" id="entityId"/>
<bean:define name="regId" id="regId"/>

<div class="tabContent">

	<html:form action='/selectEntities.do'>

		<display:table name="ItemsList"
					   id="item"
					   export="true"
					   class="tableDisplay"
	  				   sort="list"
	  				   pagesize="45"
	  				   requestURI="/showProcedureEntity.do">

		<bean:define id='itemobj' name='item' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>

			 <logic:iterate name="ItemsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="LINK">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

				  		<center>

					  		<html:link action='<%=format.getUrl() + "&pcdId=" + pcdId + "&entityId=" + entityId%>'
					  				   styleClass='<%=format.getStyleClass()%>'
					  				   paramId='<%=format.getId()%>'
					  				   paramName="item"
					  				   paramProperty='<%=format.getPropertyLink()%>'>

								<bean:message key='<%=format.getPropertyValueKey()%>' />

					  		</html:link>

					  	</center>

				  	</display:column>

				 </logic:equal>

				<logic:equal name="format" property="fieldType" value="DATA">

					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
							 		headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>

						<%=format.formatProperty(item)%>

					</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="FORM">

					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
							 		headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>

						<%=format.formatProperty(item)%>

						<logic:notEmpty name="item" property='property(NOMBRE_APP_KEY)'>
							<bean:define type="java.lang.String" id="valueKey" name="item" property='property(NOMBRE_APP_KEY)' />
							<bean:message key='<%=valueKey%>' />
						</logic:notEmpty>

						<logic:notEmpty name="item" property='property(NOMBRE_VISIBLE_RULE)'>
							<br/><br/>
							<bean:message key="procedure.entity.form.rule.visible" /><br/>
							<bean:write name="item" property='property(NOMBRE_VISIBLE_RULE)' />
						</logic:notEmpty>

					</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="LINKFRM">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

						<bean:define id="ENTITY_ID" name="item" property="property(CTENTITY:ID)" />
						<bean:define id="fieldlink" name="item" property='<%=format.getPropertyLink()%>' />

						<table>
							<tr class="tableDisplay">
								<td>
									<bean:message key="procedure.card.entities.propertyLabel.assign.form" />
								</td>
								<td>

									<ispac:linkframe id="ENTITYAPPMANAGER"
													 styleClass='<%=format.getStyleClass()%>'
													 target="workframe"
													 action='<%="selectObjectToTreeElement.do?noSearch=true&codetable=SPAC_CT_APLICACIONES&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.caption&decorator=/formatters/entities/entitiesappformatter2.xml&" + format.getId() + "=" + fieldlink.toString()+"&sqlquery=WHERE ID IN (SELECT ID FROM SPAC_CT_APLICACIONES WHERE ENT_PRINCIPAL_ID="+ENTITY_ID+")"%>'
													 titleKey="procedure.card.entities.linkLabel.assign.form"
													 width="640"
													 height="480"
													 showFrame="true">
									</ispac:linkframe>

									<ispac:linkframe id="ENTITYAPPRULEMANAGER"
													 styleClass='<%=format.getStyleClass()%>'
													 target="workframe"
													 action='<%="selectObjectToTreeElement.do?noSearch=true&codetable=SPAC_CT_REGLAS&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.rule.caption&decorator=/formatters/entities/entitiesappruleformatter.xml&" + format.getId() + "=" + fieldlink.toString()%>'
													 titleKey="procedure.card.entities.linkLabel.assign.rule"
													 width="640"
													 height="480"
													 showFrame="true">
									</ispac:linkframe>

								</td>
							</tr>
							<tr>
								<td></td>
								<td>

									<html:link action='<%="entities.do?method=delete&regId=" + regId +"&entityId=" + entityId+ "&delId=" + itemobj.getProperty("ID_P_FRM")%>'
											   styleClass='<%=format.getStyleClass()%>'>
										<bean:message key="procedure.button.entity.form.stage"/>
									</html:link>

								</td>
							</tr>
							<tr>
								<td colspan="2"></td>
							</tr>
							<tr class="tableDisplay">
								<td>
									<bean:message key="procedure.card.entities.propertyLabel.assign.visible" />
								</td>
								<td>

									<html:link action='<%="entities.do?method=visible&" + format.getId() + "=" + fieldlink.toString() + "&regId=" + regId +"&entityId=" + entityId+ "&keyId=" + itemobj.getProperty("ID_P_FRM")%>'
											   styleClass='<%=format.getStyleClass()%>'>
										<bean:message key="procedure.button.entity.form.visible"/>
									</html:link>

									<html:link action='<%="entities.do?method=novisible&" + format.getId() + "=" + fieldlink.toString() + "&regId=" + regId +"&entityId=" + entityId+ "&keyId=" + itemobj.getProperty("ID_P_FRM")%>'
											   styleClass='<%=format.getStyleClass()%>'>
										<bean:message key="procedure.button.entity.form.noVisible"/>
									</html:link>

									<ispac:linkframe id="ENTITYVISIBLERULEMANAGER"
													 styleClass='<%=format.getStyleClass()%>'
													 target="workframe"
													 action='<%="selectObjectToTreeElement.do?noSearch=true&codetable=SPAC_CT_REGLAS&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.rule.visible.caption&decorator=/formatters/entities/entitiesvisibleruleformatter.xml&" + format.getId() + "=" + fieldlink.toString()%>'
													 titleKey="procedure.card.entities.linkLabel.assign.visible.rule"
													 width="640"
													 height="480"
													 showFrame="true">
									</ispac:linkframe>

								</td>
							</tr>
						</table>

				  	</display:column>

				 </logic:equal>

				<logic:equal name="format" property="fieldType" value="FRM_READONLY">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

						<logic:empty name="item" property="property(NO_VISIBLE)">

							<center>

						  		<logic:empty name="item" property="property(PENTITIES:FRM_READONLY)">

							  		<html:link action='<%=format.getUrl() + "&regId=" + regId +"&entityId=" + entityId+ "&keyId=" + itemobj.getProperty("ID_P_FRM") + "&readonly=" + ISPACEntities.ENTITY_FORM_READONLY%>'
							  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
							  			paramProperty='<%=format.getPropertyLink()%>'>
										<bean:message key="procedure.button.entity.form.readonly.yes"/>
							  		</html:link>

							  		<html:link action='<%=format.getUrl() + "&regId=" + regId +"&entityId=" + entityId+ "&keyId=" + itemobj.getProperty("ID_P_FRM") + "&readonly=" + ISPACEntities.ENTITY_FORM_EDITABLE%>'
							  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
							  			paramProperty='<%=format.getPropertyLink()%>'>
										<bean:message key="procedure.button.entity.form.readonly.no"/>
							  		</html:link>

						  		</logic:empty>

								<logic:notEmpty name="item" property="property(PENTITIES:FRM_READONLY)">

									<bean:define id="FRM_READONLY" name="item" property="property(PENTITIES:FRM_READONLY)" />

									<c:choose>

										<c:when test="${FRM_READONLY == ENTITY_FORM_READONLY}">

											<bean:message key="procedure.entity.form.readonly.yes"/>

									  		<html:link action='<%=format.getUrl() + "&regId=" + regId +"&entityId=" + entityId+ "&keyId=" + itemobj.getProperty("ID_P_FRM") + "&readonly=" + ISPACEntities.ENTITY_FORM_EDITABLE%>'
									  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
									  			paramProperty='<%=format.getPropertyLink()%>'>
												<bean:message key="procedure.button.entity.form.readonly.no"/>
									  		</html:link>

										</c:when>

										<c:otherwise>

											<bean:message key="procedure.entity.form.readonly.no"/>

									  		<html:link action='<%=format.getUrl() + "&regId=" + regId +"&entityId=" + entityId+ "&keyId=" + itemobj.getProperty("ID_P_FRM") + "&readonly=" + ISPACEntities.ENTITY_FORM_READONLY%>'
									  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
									  			paramProperty='<%=format.getPropertyLink()%>'>
												<bean:message key="procedure.button.entity.form.readonly.yes"/>
									  		</html:link>

										</c:otherwise>

									</c:choose>

								</logic:notEmpty>

							</center>

						</logic:empty>

				  	</display:column>

				</logic:equal>

			</logic:iterate>

		</display:table>

	</html:form>

</div>