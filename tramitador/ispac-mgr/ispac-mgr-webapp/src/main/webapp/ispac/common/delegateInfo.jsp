<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define name="idsStage" id="stages"/>
<bean:define name="idsTask" id="tasks"/>

<img src='<ispac:rewrite href="img/logo_menu.gif"/>' style="position:absolute; top:95; left:10; z-index:-1"/>

<table cellpadding="5" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
				<tr>
					<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="15px"/></td>
				</tr>
				<tr>
	  				<td>
						<logic:present name="menus">
							<ispac:menuBar id="menu" name="menus" position="cols" styleLevel0="menu0" styleLevel1="menu1" padding="2px"></ispac:menuBar>
						</logic:present>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
			  	<tr>
			  	  	<td class="blank">
		    			<table cellpadding="1" cellspacing="0" border="0" width="100%">
							<tr>
								<td width="15px" align="center">
	            				    <img id='imgsch33' src='<ispac:rewrite href="img/arrow_down_sch.gif"/>' 
	            					width="10px" height="11px" border="0" style="cursor:pointer" 
	            					onclick='javascript:hide_expand(33, "img/arrow_down_sch.gif", "img/arrow_right_sch.gif");'/>
	        					</td>
								<td class="schema">
					               <a href='javascript:hide_expand(33, "img/arrow_down_sch.gif", "img/arrow_right_sch.gif");' class="titulo_menu">
										<bean:message key="delegateInfo.title" /> 
								   </a>
								</td>
							</tr>
							<tr>
       							<td colspan="2">
        							<div style="display:block" id="blocksch33">
        								<table border="0" width="100%" cellpadding="1" cellspacing="1">

							<logic:present name="selectedProc">
					  	  	  	<logic:iterate name="selectedProc" id="selectStage">
					  	  	  		<tr>
					  	  	  			<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="5px"/></td>
					  	  	  			<td width="16px"><img src='<ispac:rewrite href="img/folder.gif"/>' height="16px" width="16px" border="0"/></td>
					  	  	  			<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="5px"/></td>
					  	  	  			<td class="menu" height="18px">
											<c:set var="_stageId"><bean:write name="selectStage" property="property(ID_FASE)"/></c:set>
											<nobr><bean:write name="stagesMap" property='<%=pageContext.getAttribute("_stageId")+".property(NOMBRE)"%>'/></nobr>
					  	  	  			</td>
					  	  	  			<td class="menu" height="18px">(<bean:write name="selectStage" property="property(NUMEXP)"/>)</td>
					  	  	  		</tr>
				  	  	  		</logic:iterate>
				  	  	  	</logic:present>
				  	  	  	<logic:present name="selectedTask">
					  	  	  	<logic:iterate name="selectedTask" id="selectTask">
					  	  	  		<tr>
					  	  	  			<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="5px"/></td>
					  	  	  			<td width="16px"><img src='<ispac:rewrite href="img/tramiteestado1.gif"/>' height="16px" width="16px" border="0"/></td>
					  	  	  			<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="5px"/></td>
					  	  	  			<td class="menu" height="18px"><nobr><bean:write name="selectTask" property="property(NOMBRE)"/></nobr></td>
					  	  	  			<td class="menu" height="18px">(<bean:write name="selectTask" property="property(NUMEXP)"/>)</td>
					  	  	  		</tr>
					  	  	  	</logic:iterate>
					  	  	  </logic:present>

										</table>
									</div>
								</td>
							</tr>
						</table>
			  	  	</td>
			  	</tr>
			</table>
		</td>
	</tr>
</table>

<html:form action="/delegateGroup.do">
	<input type="hidden" name="idsStage" value='<%= stages%>'/>
	<input type="hidden" name="idsTask" value='<%= tasks%>'/>
</html:form>

