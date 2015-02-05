<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<div id="move">

<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4>
			<bean:message key="catalog.createnewpcd"/>
			&nbsp;>&nbsp;
			<logic:notEqual name="BlankPcd" value="true">
				<bean:message key="procedure.wizard.create.stages"/>
				&nbsp;>&nbsp;
				<bean:message key="procedure.wizard.create.tasks"/>
				&nbsp;>&nbsp;
			</logic:notEqual>
			<bean:message key="procedure.wizard.create.resume"/>
		</h4>
		<div class="acciones_ficha">
			<html:link styleClass="btnAnt" action='/overviewCreateProcedure.do?forward=prev'>
				<bean:message key="wizard.button.prev"/>
			</html:link>
			<html:link styleClass="btnOk" action='/overviewCreateProcedure.do?forward=next'>
				<bean:message key="procedure.wizard.create.procedure"/>
			</html:link>
		</div>
	</div>
</div>



<div id="helpLink">
	<ispac:onlinehelp tipoObj="36" fileName="overviewProcedure" image="img/help.gif" titleKey="title.help"/>
</div>
<%--
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<html:link action='/overviewCreateProcedure.do?forward=prev'>
				<bean:message key="wizard.button.prev"/>
			</html:link>
		</li>
		<li>
			<html:link action='/overviewCreateProcedure.do?forward=next'>
				<bean:message key="procedure.wizard.create.procedure"/>
			</html:link>
		</li>
	</ul>
</div>
--%>
<logic:equal name="BlankPcd" value="true">
<div id="navSubMenuTitle">
	<bean:message key="procedure.wizard.create.confirm.blank"/>
</div>
</logic:equal>

<logic:notEqual name="BlankPcd" value="true">
<div id="navSubMenuTitle">
	<bean:message key="procedure.wizard.create.confirm"/>:
</div>


<!-- ISPAC  displayTag con formateador -->
<display:table  name="StageList" id="stage" export="false" class="tableDisplay" >

	 <logic:iterate name="StageListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

		<logic:equal name="format" property="fieldType" value="ROWNUM">
			<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
					 		headerClass='<%=format.getHeaderClass()%>'
							sortable='<%=format.getSortable()%>'
							decorator='<%=format.getDecorator()%>'>
				<nobr>
				 <%=stage_rowNum%>
				</nobr>
			</display:column>
		</logic:equal>

		<logic:equal name="format" property="fieldType" value="LINK">
		  	<display:column titleKey='<%=format.getTitleKey()%>'
		  					media='<%=format.getMedia()%>'
		  					headerClass='<%=format.getHeaderClass()%>'
		  					sortable='<%=format.getSortable()%>'
		  					sortProperty='<%=format.getPropertyName()%>'
		  					decorator='<%=format.getDecorator()%>'>
		  					
		  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="stage"
		  			paramProperty='<%=format.getPropertyLink() %>'>
					<%=format.formatProperty(stage)%>
		  		</html:link>
		  	</display:column>
		 </logic:equal>

		<logic:equal name="format" property="fieldType" value="DATA">
			<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
					 		headerClass='<%=format.getHeaderClass()%>'
							sortable='<%=format.getSortable()%>'
							decorator='<%=format.getDecorator()%>'>
				<nobr>
					<%=format.formatProperty(stage)%>
				</nobr>
			</display:column>
		</logic:equal>

		<logic:equal name="format" property="fieldType" value="TASKS">
			<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
					 		headerClass='<%=format.getHeaderClass()%>'
							sortable='false'
							decorator='<%=format.getDecorator()%>'>

				<bean:define id='TaskList' name="format"
					property='<%= format.prepareValue((ieci.tdw.ispac.ispaclib.bean.ItemBean)stage) %>'
					toScope='request'/>


				<!--TRAMITES displayTag anidado -->
				<display:table name='TaskList' id='item' export="false" class="tableDisplay" >

					 <logic:iterate name="TaskListFormatter" id="fmt" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
						<logic:equal name="fmt" property="fieldType" value="DATA">
							<display:column titleKey='<%=fmt.getTitleKey()%>'
									 headerClass='<%=fmt.getHeaderClass()%>'
									sortable='false'
									decorator='<%=fmt.getDecorator()%>'>
								<nobr>
									<%=fmt.formatProperty(item)%>
								</nobr>
							</display:column>
						</logic:equal>
					  </logic:iterate>

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="procedure.wizard.create.noTasksStage.msg"/>
					</display:setProperty>

				</display:table>

			</display:column>
		</logic:equal>

	</logic:iterate>
</display:table>
<!-- displayTag -->
</logic:notEqual>
</div>
<script>
$(document).ready(function(){
		$("#move").draggable();
	});
</script>