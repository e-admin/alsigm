<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import='ieci.tdw.ispac.api.entities.SpacEntities'%>

<c:set var="ENTITY_TASK" scope="page"><%=SpacEntities.SPAC_DT_TRAMITES%></c:set>

<bean:define name="pcdId" id="pcdId"/>
<bean:define name="entityId" id="entityId"/>

<ispac:hasFunction var="edicion" functions="FUNC_INV_PROCEDURES_EDIT"/>

<div class="tabButtons">
	<c:if test="${edicion}">
	<ul class="tabButtonList">
		<li>
			<ispac:linkframe id="ENTITYMANAGER"
				styleClass=""
     			target="workframe"
				action='<%="selectObject.do?codetable=SPAC_CT_ENTIDADES&codefield=ID&valuefield=NOMBRE&caption=select.entity.caption&decorator=/formatters/entities/chooseentityformatter2.xml&pcdId=" + pcdId + "&sqlquery=" + java.net.URLEncoder.encode("WHERE TIPO=1")%>'
				titleKey="catalog.add"
				width="640"
				height="480"
				showFrame="true">
			</ispac:linkframe>
		</li>
	</ul>
	</c:if>
</div>

<div class="tabContent">

	<html:form action='/selectEntities.do'>

		<display:table name="ItemsList"
					   id="item"
					   export="true"
					   class="tableDisplay"
		  			   sort="list"
		  			   pagesize="45"
		  			   requestURI="/showProcedureEntity.do">

			 <bean:define id="CTENTITY_ID" name="item" property="property(CTENTITY:ID)" />

			 <logic:iterate name="ItemsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

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

						<logic:notEmpty name="item" property='property(NOMBRE_APP_KEY)'>

							<bean:define type="java.lang.String" id="valueKey" name="item" property='property(NOMBRE_APP_KEY)'/>
							<bean:message key='<%=valueKey%>' />

							<c:set var="propertyValue"><%=format.formatProperty(item)%></c:set>
							<c:if test="${not empty propertyValue}">
								<br/>
								(<%=format.formatProperty(item)%>)
							</c:if>

						</logic:notEmpty>

						<logic:empty name="item" property='property(NOMBRE_APP_KEY)'>

							<%=format.formatProperty(item)%>

							<logic:notEmpty name="item" property='property(KEY)'>
								<bean:define type="java.lang.String" id="valueKey" name="item" property='property(KEY)' />
								<bean:message key='<%=valueKey%>' />
							</logic:notEmpty>

						</logic:empty>

						<logic:notEmpty name="item" property='property(NOMBRE_VISIBLE_RULE)'>
							<br/><br/>
							<bean:message key="procedure.entity.form.rule.visible" /><br/>
							<bean:write name="item" property='property(NOMBRE_VISIBLE_RULE)' />
						</logic:notEmpty>

					</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="MODORDER">

					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>

							<c:if test="${CTENTITY_ID != ENTITY_TASK}">

								<center>

									<html:link
										action='<%=format.getUrl() + "&pcdId=" + pcdId + "&entityId=" + entityId + "&modOrder=INC"%>'
										styleClass="aOrderButton" paramId='<%=format.getId()%>'
										paramName="item" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
									<html:link
										action='<%=format.getUrl() + "&pcdId=" + pcdId + "&entityId=" + entityId + "&modOrder=DEC"%>'
										styleClass="aOrderButton" paramId='<%=format.getId()%>'
										paramName="item" paramProperty='<%=format.getPropertyLink() %>' >-</html:link>

								</center>

							</c:if>

					</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="LINKFRM">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

				  		<%--
				  		<c:if test="${CTENTITY_ID != ENTITY_TASK}">
				  		--%>

							<bean:define id="fieldlink" name="item" property="property(PENTITIES:ID)" />

							<table>
								<tr class="tableDisplay">
									<td>
										<bean:message key="procedure.card.entities.propertyLabel.assign.form" />
									</td>
									<td>

										<ispac:linkframe id="ENTITYAPPMANAGER"
											styleClass='<%=format.getStyleClass()%>'
											target="workframe"
											action='<%="selectObject.do?noSearch=true&codetable=SPAC_CT_APLICACIONES&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.caption&decorator=/formatters/entities/entitiesappformatter2.xml&" + format.getId() + "=" + fieldlink.toString()+"&sqlquery=WHERE ID IN (SELECT ID FROM SPAC_CT_APLICACIONES WHERE ENT_PRINCIPAL_ID="+CTENTITY_ID+")"%>'
											titleKey="procedure.card.entities.linkLabel.assign.form"
											width="640"
											height="480"
											showFrame="true">
										</ispac:linkframe>

										<ispac:linkframe id="ENTITYAPPRULEMANAGER"
											styleClass='<%=format.getStyleClass()%>'
											target="workframe"
											action='<%="selectObject.do?noSearch=true&codetable=SPAC_CT_REGLAS&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.rule.caption&decorator=/formatters/entities/entitiesappruleformatter.xml&" + format.getId() + "=" + fieldlink.toString()%>'
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

										<html:link action='<%=format.getUrl() + "&entityId=" + entityId%>'
											styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
											paramProperty='<%=format.getPropertyLink()%>'>

											<bean:message key="procedure.button.entity.form.default"/>

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

										<html:link action='<%="entities.do?method=visible&regId=" + pcdId + "&entityId=" + entityId + "&keyId=" + fieldlink.toString()%>'
												   styleClass='<%=format.getStyleClass()%>'>
											<bean:message key="procedure.button.entity.form.visible"/>
										</html:link>

										<html:link action='<%="entities.do?method=novisible&regId=" + pcdId + "&entityId=" + entityId + "&keyId=" + fieldlink.toString()%>'
												   styleClass='<%=format.getStyleClass()%>'>
											<bean:message key="procedure.button.entity.form.noVisible"/>
										</html:link>

										<ispac:linkframe id="ENTITYVISIBLERULEMANAGER"
														 styleClass='<%=format.getStyleClass()%>'
														 target="workframe"
														 action='<%="selectObject.do?noSearch=true&codetable=SPAC_CT_REGLAS&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.rule.visible.caption&decorator=/formatters/entities/entitiesvisibleruleformatter.xml&" + format.getId() + "=" + fieldlink.toString()%>'
														 titleKey="procedure.card.entities.linkLabel.assign.visible.rule"
														 width="640"
														 height="480"
														 showFrame="true">
										</ispac:linkframe>

									</td>
								</tr>
							</table>
				  		<%--
				  		</c:if>
				  		--%>

				  	</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="FRM_READONLY">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

				  		<%--
				  		<c:if test="${CTENTITY_ID != ENTITY_TASK}">
				  		--%>

					  		<logic:empty name="item" property="property(NO_VISIBLE)">

								<center>

									<logic:empty name="item" property="property(PENTITIES:FRM_READONLY)">
										<bean:message key="procedure.entity.form.readonly.no"/>
									</logic:empty>
									<logic:notEmpty name="item" property="property(PENTITIES:FRM_READONLY)">
										<bean:message key="procedure.entity.form.readonly.yes"/>
									</logic:notEmpty>

									<c:if test="${edicion}">
										<html:link action='<%=format.getUrl() + "&regId=" + pcdId + "&entityId=" + entityId%>'
											styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
											paramProperty='<%=format.getPropertyLink()%>'>

											<logic:empty name="item" property="property(PENTITIES:FRM_READONLY)">
												<bean:message key="procedure.button.entity.form.readonly.yes"/>
											</logic:empty>
											<logic:notEmpty name="item" property="property(PENTITIES:FRM_READONLY)">
												<bean:message key="procedure.button.entity.form.readonly.no"/>
											</logic:notEmpty>

										</html:link>
									</c:if>
						  		</center>

						  	</logic:empty>

						<%--
						</c:if>
						--%>

				  	</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="EVENTS">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

						<center>

							<bean:define id="fieldlink" name="item" property="property(PENTITIES:ID)" />
							<bean:define id="labelentity" name="item" property="property(ETIQUETA)" />

							<ispac:linkframe id="EVENTMANAGER"
								styleClass='<%=format.getStyleClass()%>'
				     			target="workframe"
								action='<%=format.getUrl() + "&" + format.getId() + "=" + fieldlink.toString() + "&label=" + labelentity.toString()%>'
								titleKey='<%=format.getPropertyValueKey()%>'
								width="640"
								height="480"
								showFrame="true">
							</ispac:linkframe>

						</center>

				  	</display:column>

				 </logic:equal>

				<logic:equal name="format" property="fieldType" value="DELENTITY">

				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

				  		<logic:empty name="item" property="property(NO_DELETE)">

							<center>

						  		<bean:define name="item" id="delId" property="property(PENTITIES:ID)"/>
						  		<c:url var="eliminateEntity" value="/entities.do">
						  			<c:param name="method" value="delete"/>
									<c:param name="regId" value="${pcdId}"/>
									<c:param name="entityId" value="${entityId}"/>
									<c:param name="delId" value="${delId}"/>
								</c:url>

								<a href = "javascript:messageConfirm('<c:out value="${eliminateEntity}" escapeXml="false"/>','<bean:message key="entity.eliminateMessage.confirm"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>');" class='<%=format.getStyleClass()%>'/>
							  		<nobr><bean:message key='<%=format.getPropertyValueKey()%>'/></nobr>
							  	</a>

						  	</center>

					  	</logic:empty>

				  	</display:column>

				 </logic:equal>

			</logic:iterate>

		</display:table>

	</html:form>

</div>