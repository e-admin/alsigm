<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="org.apache.struts.util.MessageResources"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="ieci.tdw.ispac.ispaclib.notices.Notices"%>
<script>
function recibir(){
	var frm = document.forms['batchForm'];
	var data = checkboxElement(frm.multibox);
	if (data != "") {
		frm.action= '<%= request.getContextPath()  + "/checkNotice.do?state=1"%>';
		frm.submit();
	} else {
		jAlert('<bean:message key="avisos.form.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
	}

}
function archivar(){
	var frm = document.forms['batchForm'];
	var data = checkboxElement(frm.multibox);
	if (data != "") {
		frm.action='<%= request.getContextPath()  + "/checkNotice.do?state=2"%>';
		frm.submit();
	} else {
		jAlert('<bean:message key="avisos.form.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
	}
}

</script>
<html:form action="/showNoticeList" >

	<table border="0" width="100%">
		<tr>
			<td align="right">
				<ispac:onlinehelp tipoObj="9" image="img/help.gif" titleKey="header.help"/>
			</td>
		</tr>
	</table>

	<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
					<tr>
						<td class="title" height="18px">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
									<td width="100%" class="menuhead">
										<bean:message key="avisos.titulo" />
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="blank">

							<logic:notEmpty scope="request" name="NoticesList">

								<table cellpadding="0" cellspacing="0" border="0" >
								<tr>
									<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td class="formaction" height="28px">
										<input type="button" value='<bean:message key="forms.button.recibir"/>' onclick="javascript:recibir();" class="form_button_white"/>
									</td>
									<td class="formaction" height="28px">
										<input type="button" value='<bean:message key="forms.button.archivar"/>' onclick="javascript:archivar();" class="form_button_white"/>
									</td>
								</tr>
								</table>

							</logic:notEmpty>

							<!-- displayTag con formateador -->
							<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

							<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
							<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

							<display:table name="NoticesList"
										   id="notice"
										   form="batchForm"
										   excludedParams="multibox"
										   decorator="checkboxDecorator"
							  			   requestURI="/showNoticeList.do"
										   export='<%=formatter.getExport()%>'
								   		   class='<%=formatter.getStyleClass()%>'
										   sort='<%=formatter.getSort()%>'
										   pagesize='<%=formatter.getPageSize()%>'
										   defaultorder='<%=formatter.getDefaultOrder()%>'
										   defaultsort='<%=formatter.getDefaultSort()%>'>

								<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

									<logic:equal name="format" property="fieldType" value="CHECKBOX">

										<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

										<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.batchForm.multibox, this);\'/>"%>'
														media='<%=format.getMedia()%>'
														sortable='<%=format.getSortable()%>'
														decorator='<%=format.getDecorator()%>'
														comparator='<%=format.getComparator()%>'
														headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'
														property="checkbox">
										</display:column>

									</logic:equal>


									<logic:equal name="format" property="fieldType" value="CALCULATE_LINK">
									  	<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'
										><c:set var="id_tramite"><bean:write name="notice" property="property(SPAC_AVISOS_ELECTRONICOS:ID_TRAMITE)"/></c:set>
										<jsp:useBean id="id_tramite" type="java.lang.String"/>
										<c:set var="id_fase"><bean:write name="notice" property="property(SPAC_AVISOS_ELECTRONICOS:ID_FASE)"/></c:set>
										<jsp:useBean id="id_fase" type="java.lang.String"/>
										<c:set var="tipo_aviso"><bean:write name="notice" property="property(SPAC_AVISOS_ELECTRONICOS:TIPO_AVISO)"/></c:set>
										<jsp:useBean id="tipo_aviso" type="java.lang.String"/>
										<c:set var="tipoAvisoActividadDelegada"><%=Notices.TIPO_AVISO_ACTIVIDAD_DELEGADA%></c:set>
										<c:choose>
											<c:when test="${!empty id_tramite && tipo_aviso != tipoAvisoActividadDelegada}">
													<html:link 	action='showTask.do'
									  					styleClass='<%=format.getStyleClass()%>'
									  					paramId='taskId'
									  					paramName="notice"
									  					paramProperty='property(SPAC_AVISOS_ELECTRONICOS:ID_TRAMITE)'><%=format.formatProperty(notice)%></html:link>
											</c:when>
											<c:when test="${!empty id_fase && tipo_aviso == tipoAvisoActividadDelegada}">

													<html:link 	action='<%="showSubProcess.do?activityId="+id_fase%>'
									  					styleClass='<%=format.getStyleClass()%>'
									  					paramId='taskId'
									  					paramName="notice"
									  					paramProperty='property(SPAC_AVISOS_ELECTRONICOS:ID_TRAMITE)'><%=format.formatProperty(notice)%></html:link>
											</c:when>
											<c:otherwise>
													<html:link 	action='selectAnActivity.do'
									  					styleClass='<%=format.getStyleClass()%>'
									  					paramId='numexp'
									  					paramName="notice"
									  					paramProperty='property(SPAC_AVISOS_ELECTRONICOS:ID_EXPEDIENTE)'><%=format.formatProperty(notice)%></html:link>
											</c:otherwise>
										</c:choose>
									</display:column>
									</logic:equal>




									<logic:equal name="format" property="fieldType" value="LINK">

									  	<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'>

									  		<html:link 	action='<%=format.getUrl()%>'
									  					styleClass='<%=format.getStyleClass()%>'
									  					paramId='<%=format.getId()%>'
									  					paramName="notice"
									  					paramProperty='<%=format.getPropertyLink() %>'>

												<c:set var="estado"><bean:write name="notice" property="property(ESTADO_AVISO)"/></c:set>
												<c:if test="${estado==0}"><b></c:if>

					    							<c:choose>
					    								<c:when test="${!empty format.propertyValueKey}">
						    								<bean:message key='<%=(String)format.formatProperty(notice)%>'/>
						    							</c:when>
					    								<c:otherwise>
							    							<%=format.formatProperty(notice)%>
					    								</c:otherwise>
					    							</c:choose>

				    							<c:if test="${estado==0}"></b></c:if>

									  		</html:link>

									  	</display:column>

									</logic:equal>

									<logic:equal name="format" property="fieldType" value="LIST">

										<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'>

											<c:set var="estado"><bean:write name="notice" property="property(ESTADO_AVISO)"/></c:set>
											<c:if test="${estado==0}"><b></c:if>

			    								<%=format.formatProperty(notice)%>

			    							<c:if test="${estado==0}"></b></c:if>

										</display:column>

									</logic:equal>

									<logic:equal name="format" property="fieldType" value="LIST_FROMRESOURCES">

										<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'>

											<c:set var="estado"><bean:write name="notice" property="property(ESTADO_AVISO)"/></c:set>
											<c:if test="${estado==0}"><b></c:if>

			    								<bean:message key='<%=format.formatProperty(notice).toString()%>'/>

			    							<c:if test="${estado==0}"></b></c:if>

										</display:column>

									</logic:equal>

									<logic:equal name="format" property="fieldType" value="LIST_EXPORTFROMRESOURCES">

										<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'>
											<c:set var="propertyValue"><%=format.formatProperty(notice)%></c:set>
											<jsp:useBean id="propertyValue" type="java.lang.String"/>
											<%--bean:message key='<%=propertyValue%>' /--%>
											<%
											MessageResources messageResources = (MessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
											messageResources.setReturnNull(true);
											String message =  null;

											int pos = propertyValue.indexOf(',');
											if (pos == -1){
												message = messageResources.getMessage(request.getLocale(), propertyValue);
											}else{
												String[] args = StringUtils.split(propertyValue.substring(pos), ',');
												message = messageResources.getMessage(request.getLocale(), propertyValue.substring(0,pos), args);
											}

											if (message == null) {
												message = propertyValue;
											}
											out.println(message);
											%>
										</display:column>

									</logic:equal>

									<logic:equal name="format" property="fieldType" value="LIST_EXPORT">

										<display:column titleKey='<%=format.getTitleKey()%>'
									  					media='<%=format.getMedia()%>'
									  					sortable='<%=format.getSortable()%>'
									  					sortProperty='<%=format.getPropertyName()%>'
									  					decorator='<%=format.getDecorator()%>'
									  					comparator='<%=format.getComparator()%>'
									  					headerClass='<%=format.getHeaderClass()%>'
														class='<%=format.getColumnClass()%>'>

											<%=format.formatProperty(notice)%>

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
