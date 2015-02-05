<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table border="0" width="100%">
	<tr>
		<td align="right">
			<ispac:onlinehelp fileName="subprocessList" image="img/help.gif" titleKey="header.help"/>
		</td>
	</tr>
</table>

<html:form action="showSubProcessList.do">
	<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="box">
					<tr>
						<td class="title" height="18px">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
									<td width="100%" class="menuhead">

										<c:set var="_paramSubProcessList" value="${appConstants.actions.SUBPROCESS_ACTIVITY_LIST}"/>
										<jsp:useBean id="_paramSubProcessList" type="java.lang.String"/>

										<logic:iterate name='<%=_paramSubProcessList%>' id="subprocess" length="1">
											<bean:message key="workList.activity"/> '<bean:write name="subprocess" property="property(WORKLIST:NAME_STAGE)"/>'
										</logic:iterate>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="blank">

							<!-- displayTag con formateador -->
							<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

							<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
							<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

							<c:set var="listaSubprocesos" value="${requestScope[appConstants.actions.SUBPROCESS_ACTIVITY_LIST]}"/>
							<display:table name="pageScope.listaSubprocesos"
										   id="subprocess"
										   form="batchForm"
										   excludedParams="multibox"
										   decorator="checkboxDecorator"
										   requestURI="/showSubProcessList.do"
										   export='<%=formatter.getExport()%>'
										   class='<%=formatter.getStyleClass()%>'
										   sort='<%=formatter.getSort()%>'
										   pagesize='<%=formatter.getPageSize()%>'
										   defaultorder='<%=formatter.getDefaultOrder()%>'
										   defaultsort='<%=formatter.getDefaultSort()%>'>

								<%--
								<display:column title='' media='html'>
								    <div style="display:none">
								      <bean:write name="subprocess" property="property(WORKLIST:ID)"/>
									</div>
								</display:column>
								--%>

								<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

									<logic:equal name="format" property="fieldType" value="CHECKBOX">

										<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

										<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.batchForm.multibox, this);\'/>"%>'
														media='<%=format.getMedia()%>'
														headerClass='<%=format.getHeaderClass()%>'
														sortable='<%=format.getSortable()%>'
														decorator='<%=format.getDecorator()%>'
														comparator='<%=format.getComparator()%>'
														class='<%=format.getColumnClass()%>'
														property="checkbox">
										</display:column>

								    </logic:equal>

									<logic:equal name="format" property="fieldType" value="LINK">

									  	<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					class='<%=format.getColumnClass()%>'>

									  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="subprocess"
									  			paramProperty='<%=format.getPropertyLink() %>'>

									  			<%=format.formatProperty(subprocess)%>

									  		</html:link>

									  	</display:column>

									 </logic:equal>

									<logic:equal name="format" property="fieldType" value="LIST">

										<display:column titleKey='<%=format.getTitleKey()%>'
														media='<%=format.getMedia()%>'
														headerClass='<%=format.getHeaderClass()%>'
														sortable='<%=format.getSortable()%>'
														sortProperty='<%=format.getPropertyName()%>'
														decorator='<%=format.getDecorator()%>'
														comparator='<%=format.getComparator()%>'
														class='<%=format.getColumnClass()%>'>

											<%=format.formatProperty(subprocess)%>

										</display:column>

									</logic:equal>

								</logic:iterate>

							</display:table>
							<!-- displayTag -->

						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>