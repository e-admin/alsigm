<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define name="TpObj" id="TpObj"/>
<bean:define name="IdObj" id="IdObj"/>
<bean:define name="entityId" id="entityId"/>
<bean:define name="codEvent" id="codEvent"/>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script language="javascript">
//<!--
	function doSubmit() {
		if (isCheckSelected(document.defaultForm.multibox)) {
			document.defaultForm.submit();
		} else {
			jAlert('<bean:message key="rules.select.msg.empty"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}
//-->
</script>
<script>

	$(document).ready(function(){
		$("#move").draggable();
	});
	
</script>
<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="select.rules.caption"/></h4>
		<div class="acciones_ficha">
			<a class="btnOk" href="javascript:doSubmit()">
				<bean:message key="forms.button.accept"/>
			</a>
			<a class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</div>
	</div>
</div>



<div id="bodycontainer" class="tableborder">

	<html:form action='/events.do'>
	
		<input type="hidden" name="method" value="addRules"/>
		<input type="hidden" name="TpObj" value='<c:out value="${requestScope.TpObj}"/>'/>
		<input type="hidden" name="IdObj" value='<c:out value="${requestScope.IdObj}"/>'/>
		<input type="hidden" name="entityId" value='<c:out value="${requestScope.entityId}"/>'/>
		<input type="hidden" name="codEvent" value='<c:out value="${requestScope.codEvent}"/>'/>
		
		<display:table name="RulesList" id="item" export="false" class="tableDisplay"
	  		sort="list" requestURI='' style="width:95%;">

			<logic:iterate name="RulesListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="CHECKBOX">
				
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>' 
									sortable='<%=format.getSortable()%>' 
									class='<%=format.getColumnClass()%>'
									decorator='<%=format.getDecorator()%>'>
									
						<html:multibox property="multibox">
							<%=format.formatProperty(item)%>
						</html:multibox>
						
					</display:column>
					
			  	</logic:equal>

				<logic:equal name="format" property="fieldType" value="LINK">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									sortProperty='<%=format.getPropertyName()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
						
						<center>	
							<html:link action='<%=format.getUrl() + "?TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&codEvent=" + codEvent%>' styleClass='<%=format.getStyleClass()%>'
								paramId='<%=format.getId()%>' paramName="item" paramProperty='<%=format.getPropertyLink()%>'>
								
								<%=format.formatProperty(item)%>
								
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
						
						<nobr><%=format.formatProperty(item)%></nobr>
						
					</display:column>
					
				</logic:equal>

			</logic:iterate>
			
		</display:table>
		
	</html:form>
	
</div>
</div>