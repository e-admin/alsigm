<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define name="TpObj" id="TpObj"/>
<bean:define name="IdObj" id="IdObj"/>
<bean:define name="entityId" id="entityId"/>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script>

	$(document).ready(function(){
		$("#move").draggable();
	});
	
</script>
<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="select.event.caption"/></h4>
		<div class="acciones_ficha">
			<a class="btnCancel"href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</div>
	</div>
</div>




<div id="bodycontainer" class="tableborder">
	
	<display:table name="EventsList" id="item" export="false" 
			class="tableDisplay" sort="list" requestURI='' style="width:95%;">

		<logic:iterate name="EventsListFormatter" id="format" 
				type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="LINK">
			
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'>
					
					<center>
						<html:link action='<%=format.getUrl() + "&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId %>' styleClass='<%=format.getStyleClass()%>'
							paramId='<%=format.getId()%>' paramName="item" paramProperty='<%=format.getPropertyLink() %>'>
								
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
								
					<nobr><bean:message key='<%=(String)format.formatProperty(item)%>'/></nobr>
					
				</display:column>
				
			</logic:equal>

		</logic:iterate>
		
	</display:table>

</div>
</div>