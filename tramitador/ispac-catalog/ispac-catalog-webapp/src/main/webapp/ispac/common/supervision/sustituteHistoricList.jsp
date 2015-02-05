<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<div id="move">

<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<logic:present parameter="captionkey">
			 <bean:parameter id="caption" name="captionkey"/>
			<h4><bean:message name="caption"/></h4>
		</logic:present>
			<div class="acciones_ficha">
			
			<a  class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		
		</div>
	</div>
</div>



<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>

<c:url value="showInfoEntry.do" var="_return">
	<c:param name="view">
		<bean:write name="view"/>
	</c:param>
	<c:param name="uid">
		<bean:write name="uid"/>
	</c:param>
	<logic:present name="captionkey">
		<c:param name="captionkey">
			<bean:write name="captionkey"/>
		</c:param>
	</logic:present>
</c:url>
<jsp:useBean id="_return" type="java.lang.String"/>
<c:choose>
<c:when test="${!empty action}">
	<c:set var="formName" value="sustitutionsForm"/>
</c:when>
<c:otherwise>
	<c:set var="formName" value="addSupervisedsForm"/>
</c:otherwise>
</c:choose>
<jsp:useBean id="formName" type="java.lang.String"/>


<c:choose>
<c:when test="${!empty action}">
	<c:set var="actionShow" value="${action}?method=showSustitutes"/>
</c:when>
<c:otherwise>
	<c:set var="actionShow" value="showResponsiblesForSupervision.do"/>
</c:otherwise>
</c:choose>

<c:url value="${actionShow}" var="_showResponsiblesForSupervision">
	<c:param name="uid">
		<bean:write name="uid"/>
	</c:param>
	<logic:present name="captionkey">
		<c:param name="captionkey">
			<bean:write name="captionkey"/>
		</c:param>
	</logic:present>
</c:url>

<table border="0" width="95%" align="center">
						
							
	
							<c:url value="gestionSustituciones.do" var="_deleteSustituto">
									<c:param name="method" value="deleteSubstituteHistoric"/>
								<c:param name="view">
									<bean:write name="view"/>
								</c:param>
								<c:param name="uid">
									<bean:write name="uid"/>
								</c:param>
								<logic:present name="uidGroup">
									<c:param name="uidGroup">
										<bean:write name="uidGroup" />
									</c:param>
								</logic:present>
								<logic:present name="captionkey">
									<c:param name="captionkey">
									<bean:write name="captionkey"/>
								</c:param>
								</logic:present>
								<c:param name="block" value="3"/>
							</c:url>
							<jsp:useBean id="_deleteSustituto" type="java.lang.String"/>
							
							<%-- Enlace a la pantalla de añadir sustitutos --%>
							<c:url value="gestionSustituciones.do" var="_showSustitutos">
								<c:param name="method" value="showSustitutes"/>
								<c:param name="view">
									<bean:write name="view"/>
								</c:param>
								<c:param name="uid">
									<bean:write name="uid"/>
								</c:param>
								<logic:present name="uidGroup">
									<c:param name="uidGroup">
										<bean:write name="uidGroup" />
									</c:param>
								</logic:present>
								<c:param name="block" value="3"/>
							</c:url>
							
							<c:url value="gestionSustituciones.do" var="_showSustitutosHistoric">
								<c:param name="method" value="showSustitutesHistoric"/>
								<c:param name="view">
									<bean:write name="view"/>
								</c:param>
								<c:param name="uid">
									<bean:write name="uid"/>
								</c:param>
								<logic:present name="uidGroup">
									<c:param name="uidGroup">
										<bean:write name="uidGroup" />
									</c:param>
								</logic:present>
								<c:param name="block" value="3"/>
								<logic:present name="captionkey">
									<c:param name="captionkey">
										<bean:write name="captionkey"/>
									</c:param>
								</logic:present>
							</c:url>
							<jsp:useBean id="_showSustitutos" type="java.lang.String"/>	
							<jsp:useBean id="_showSustitutosHistoric" type="java.lang.String"/>											
							<tr>
									<td>
										<div class="buttonList">
										<ispac:hasFunction functions="FUNC_PERM_EDIT">
										<ul>
											
											<c:choose>
											<c:when test="${!empty SUSTITUTOS_LIST}">
											<li>
												<a href="javascript:submitFormByIdMultibox('formDeleteSustituto');" > 
													<nobr><bean:message key="forms.button.delete"/></nobr>
												</a>
											</li>
											</c:when>
											<c:otherwise>
												<li style="background-color: #ddd; color: #aaa; cursor: default;">
													<nobr><bean:message key="forms.button.delete"/></nobr>
												</li>
											</c:otherwise>
											</c:choose>
											
										</ul>
										</ispac:hasFunction>
										</div>
									</td>
							</tr>
							
							
							<html:form styleId="formDeleteSustituto" action='<%=_deleteSustituto%>'>
								<tr>
									<td width="85%">
									<table width="100%" class="tableborder">
											<tr>
												<td>
												
													
										<display:table name="SUSTITUTOS_LIST" 
																   id='itemobj' 
																   export="false" 
																   class="tableDisplay"
																   sort="list"
																   requestURI=''>
																   
														<logic:present name="itemobj">
													
															<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
															
															<logic:iterate name="SustitutoFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
															
																<logic:equal name="format" property="fieldType" value="CHECKBOX">
																
																	<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.sustitutionsForm.multibox, this);\'/>"%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>' 
																					sortable='<%=format.getSortable()%>'
																					decorator='<%=format.getDecorator()%>'
																					class='<%=format.getColumnClass()%>'
																					style="text-align:center">
																					
																		<html:multibox property="multibox">
																			<%=format.formatProperty(item)%>
																		</html:multibox>
																		
																	</display:column>
																	
															  	</logic:equal>
														
																<logic:equal name="format" property="fieldType" value="RESPONSIBLE">
																
																	<display:column titleKey='<%=format.getTitleKey()%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																					
																		<c:set var="isUser"><bean:write name='item' property='property(USER)' /></c:set>
																		<c:set var="isGroup"><bean:write name='item' property='property(GROUP)' /></c:set>
																		<c:set var="isOrguni"><bean:write name='item' property='property(ORGUNIT)' /></c:set>
																		<span width="20px">
																			<c:choose>
																				<c:when test="${isUser}">
																					<img src='<ispac:rewrite href="img/user.gif"/>'/>
																				</c:when>
																				<c:when test="${isGroup}">
																					<img src='<ispac:rewrite href="img/group.gif"/>'/>
																				</c:when>
																				<c:when test="${isOrguni}">
																					<img src='<ispac:rewrite href="img/org.gif"/>'/>
																				</c:when>
																			</c:choose>
																		</span>
																		
																		<%=format.formatProperty(item)%>
																					
																	</display:column>
																	
																</logic:equal>
																
																<logic:equal name="format" property="fieldType" value="DATA">
																
																	<display:column titleKey='<%=format.getTitleKey()%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					sortProperty='<%=format.getPropertyName()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																					
																		<%=format.formatProperty(item)%>
																			
																	</display:column>
																	
																</logic:equal>
																
																<logic:equal name="format" property="fieldType" value="GROUP">
																
																	<display:column titleKey='<%=format.getTitleKey()%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					sortProperty='<%=format.getPropertyName()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																					
																					
																		<%=format.formatProperty(item)%>
																			
																	</display:column>
																	
																</logic:equal>
																
																 <logic:equal name="format" property="fieldType" value="GROUP_LINK_JS">
																 
																  	<display:column titleKey='<%=format.getTitleKey()%>'
																  					
																  					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					sortProperty='<%=format.getPropertyName()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																  		
																		<%=format.formatProperty(item)%>
																  		
																  	</display:column>
																  	
																</logic:equal>
																
															</logic:iterate>
															
														</logic:present>
														
													</display:table>
											</td>
											</tr>
										</table>
									</td> 
								</tr>
							</html:form>
					
				<!-- FIN BLOQUE 3 DE DATOS -->

</table>	

</div>	

<script>

	$(document).ready(function(){
		$("#move").draggable();
	});
function submitFormByIdMultibox(idForm){

	jConfirm('<bean:message key="form.sustitucion.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
		var	myform = document.getElementById(idForm);
			var checked = false;
			if (typeof myform.multibox == 'undefined')
			{
			jAlert('<bean:message key="catalog.supervision.alert.noResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				return;
			}

			mymultibox = myform.multibox;

			// cuando tenemos un checkbox
			if (typeof mymultibox.length == 'undefined')
			{
	      		if (mymultibox.checked)
	      			checked = true;
	      	}
	      	else
		    {
		    	for (var i=0; i <= mymultibox.length; i++)
			        if (myform[i].checked)
			        	checked = true;
		    }
		    if (checked)
		    	myform.submit();
		    else
				jAlert('<bean:message key="catalog.supervision.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
		});	
	
	
			
		}
		
</script>							