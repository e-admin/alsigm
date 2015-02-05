<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script>
	var select = 1;
	function SelectTab(i)
	{
		document.getElementById('div'+ select).style.display='none';
		document.getElementById('td'+ select).className='tabUnselect';
		document.getElementById('div'+ i).style.display='block';
		document.getElementById('td'+ i).className = 'tabSelect';
		select = i;
	}
</script>

<bean:define name="entityid" id="entityid" type="java.lang.String"/>
<bean:define name="entityregid" id="entityregid" type="java.lang.String"/>
<bean:define id="urlExp" name="urlExp" type ="java.lang.String"/>
<bean:define name="pcdid" id="pcdid" type="java.lang.String"/>

<%--
<c:url value="${urlExp}" var="link">
	<c:param name="block" value="1"/>
</c:url>
<c:set var="_link" value="${link}"/>
<jsp:useBean id="_link" type="java.lang.String"/>
--%>

<table align="center" cellpadding="0" cellspacing="0" border="0" width="99%">
	<tr>
		<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td>
	</tr>
	<tr>
		<td>
		
			<div id="header">
			
			  	<ul id="menuList">
			  
				  	<!-- listar schemeentities-->
				  	<logic:iterate id="entity" name="SchemeList">
				  	
				  		<bean:define id="entityId1" name="entity" property="property(ID)"/>
				  		
				  		<!-- Condicion para que no muestre el tab de tramites-->
						<c:if test="${entityId1 != '7'}">
							
							<%--
					  		<c:if test="${entity.regs > '0'}">
					  		--%>
						  		<c:choose>
						  			<c:when test="${entityId1 == entityid}">
									    <li class="selected"><nobr><img src='<ispac:rewrite href="img/tab_act_left.png"/>' border="0px" style="margin-bottom:-2px;" />
									</c:when>
									<c:otherwise>
										<li class="unselected"><nobr><img src='<ispac:rewrite href="img/tab_des_left.gif"/>' border="0px" style="margin-bottom:-2px;" />
									</c:otherwise>
								</c:choose>
								<%--
								<html:link href='<%=_link%>' name="Params" paramName="entityId1" paramId="entity">
								--%>
								<html:link action='<%=urlExp%>' name="Params" paramName="entityId1" paramId="entity">
						    		<bean:write name="entity" property="property(TAB_LABEL)"/>
						    	</html:link>
						  		<c:choose>
						  			<c:when test="${entityId1 == entityid}">
						  				<img src='<ispac:rewrite href="img/tab_act_right.png"/>' border="0px" style="margin-bottom:-2px;" />
									</c:when>
									<c:otherwise>
										<img src='<ispac:rewrite href="img/tab_des_right.gif"/>' border="0px" style="margin-bottom:-2px;" />
									</c:otherwise>
								</c:choose>
								</nobr>
							    </li>
							
							<%--
							</c:if>
							--%>
							
						</c:if>
						
						<%--
						<c:if test="${entityId1 == '2'}">
							<c:choose>
					  			<c:when test="${entityId1 == entityid}">
								    <li class="selected">
								</c:when>
								<c:otherwise>
									<li class="unselected">
								</c:otherwise>
							</c:choose>					
								<c:url value="${urlExp}" var="_link">
									<c:param name="appId" value="6"/>
									<c:param name="entity" value='${entityId1}'/>
									<c:param name="stageId" value='${Params.stageId}'/>
								</c:url>
								<a href='<c:out value="${_link}"/>'><bean:write name="entity" property="property(DESCRIPCION)"/></a>
							</li>
						</c:if>
						--%>
						
				    </logic:iterate>
	
					<%--
				    <!-- Extra tabs -->
				    <logic:present name="extraTabs">
					    <logic:iterate name="extraTabs" id="tab">
						  	<li class="selected">
							   	<html:link href="#">
						  			<bean:write name="tab"/>
							  	</html:link>
						  	</li>
					    </logic:iterate>
					</logic:present>
					--%>
				
			  	</ul>
			  	
			</div>
			
		</td>
		<td align="right">
				<%--
				<ispac:onlinehelp fileName='<%="pcds/"+pcdid%>' image="img/help.gif" titleKey="header.help"/>
				--%>
				<%--
				<ispac:onlinehelp fileName='expForm' extension="jsp" queryString='<%="pcdid="+pcdid%>' image="img/help.gif" titleKey="header.help"/>
				--%>
				<ispac:onlinehelp  tipoObj="3" idObj='<%= pcdid %>'  extension="jsp" queryString='<%="pcdid="+pcdid%>' image="img/help.gif" titleKey="header.help"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table cellpadding="0" cellspacing="1" border="0" width="100%" class="tab">
				<tr>
					<td class="blank" align="center" width="100%">
						<tiles:insert attribute="tabform" flush="true"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>