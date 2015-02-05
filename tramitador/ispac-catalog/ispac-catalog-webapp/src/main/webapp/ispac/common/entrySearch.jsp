<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="ThirdPartyList" value="${requestScope.ThirdPartyList}"/>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
		<script type="text/javascript"
		src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
		<script type="text/javascript"
		src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
		<ispac:javascriptLanguage/>
		<script>

		function selectSigner(view, uid, url){
			var myform;
			var doSubmit=1;
			var hideFrame=1;
			if(document.forms['usersForm']!=null){
				myform=document.forms['usersForm'];
				document.usersForm.view.value=view;
				document.usersForm.uid.value=uid;
				if(document.usersForm.procId.value){
					hideFrame=0;
				}
				
			} else if(document.forms['selectForm']!=null){
			
				myform=document.forms['selectForm'];
				document.selectForm.view.value=view;
				document.selectForm.uid.value=uid;
				
			} else{
			
				myform=document.getElementById("formseleccion")
				if(myform.action!=null) {
					myform.view.value=view;
					myform.uid.value=uid;
					myform.target = "_parent";
				} else { 
					doSubmit=0;
					parent.window.location.href = url;
				}
			}
	
			if(doSubmit==1)
			{
				if(hideFrame==1){
					<ispac:hideframe />
					<ispac:hideframe id="workframesearch" />
				}
				
				myform.submit();
			}
		}
		
		function hide(){
	
		 	if(window.parent.frames['workframe'].document.getElementById("move").style.visibility!='hidden') {
				<ispac:hideframe  refresh="false"/>
		 	}
		 	else{
		 		 eval(element=window.parent.frames['workframe'].document.getElementById("move"));
			    <ispac:hideframe id="workframesearch" refresh="false"/>
			   	parent.showLayer();
			 	element.style.visibility="visible";
			 	element.style.top=document.getElementById("move").style.top;
			 	element.style.left=document.getElementById("move").style.left;
		 	}
		 
		 
		 

		 	
	 	}
		$(document).ready(function() {
			$("#move").draggable();
			
		});
		
		 

		</script>
	
	</head>
	<body >

	<ispac:layer id="layerSearch"/>
	<div id="move" class="move" >

			<div class="ficha">
				<div class="encabezado_ficha">
					<div class="titulo_ficha">
						<h4><bean:message key="catalog.search.estructuraOrganizativa" /></h4>
						<div class="acciones_ficha">
							<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='javascript:hide();'/>				
						</div><%--fin acciones ficha --%>
					</div><%--fin titulo ficha --%>
				</div><%--fin encabezado ficha --%>
				</div>
			<div id="navmenu">
				<tiles:insert page="/ispac/common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
			</div>
			<div  class="contenedorBuscador">
						<div id="buscador">
						
						<c:set var="_executeSearch"><c:out value="/entrySearch.do?view=executeSearch&mode=Simple&actionSetResponsibles=${param['actionSetResponsibles']}" /></c:set>
						
						<jsp:useBean id="_executeSearch" type="java.lang.String" />
						<html:form styleId="formbuscador" action='<%= _executeSearch %>'>
						<input type="hidden" name="circuitoId"
							value='<c:out value="${param['key']}"/>' />
						<input type="hidden" name="id" value='<c:out value="${param['id']}"/>' />
						<input type="hidden" name="procId"
							value='<c:out value="${param['procId']}"/>' />
						<input type="hidden" name="parameters"
							value='<c:out value="${param['parameters']}"/>' />
						<input type="hidden" name="action"
							value='<c:out value="${param['action']}"/>' />
						<input type="hidden" name="captionkey"
							value='<c:out value="${param['captionkey']}"/>' />
						<input type="hidden" name="onlyselectusers"
							value='<c:out value="${param['onlyselectusers']}"/>' />
						<input type="hidden" name="replSigner"
							value='<c:out value="${param['replSigner']}"/>' />
						<input type="hidden" name="entity"
							value='<c:out value="${param['entity']}"/>' />
						<input type="hidden" name="selVarios"
							value='<c:out value="${param['selVarios']}"/>' />
						<input type="hidden" name="key"
							value='<c:out value="${param['key']}"/>' />
						<input type="hidden" name="entityAppName"
							value='<c:out value="${param['entityAppName']}"/>' />
						<input type="hidden" name="readonly"
							value='<c:out value="${param['readonly']}"/>' />
							<label class="popUp">
								<label class="popUp"><bean:message key="select.object.nombre.label" />:</label>
								<ispac:htmlText property="filter" readonly="false" size="38%"
								styleClass="input"></ispac:htmlText> &nbsp;&nbsp; <input
							type="submit" value="<bean:message key="search.button"/>"
							class="form_button_white" name="buscar" />
							</label>
					</html:form>
					
						</div>
					
					<div class="stdform">
					<br/>
					<logic:present name="items">
						<c:set var="actionParam"><c:out value="${param['actionSetResponsibles']}" /></c:set> 
						<jsp:useBean id="actionParam" type="java.lang.String" /> 
						<html:form styleId="formseleccion" action='<%= actionParam %>'>
						
						<input type="hidden" name="circuitoId"
							value='<c:out value="${param['key']}"/>' />
						<input type="hidden" name="nameFrame" value="workframeSearch" />
						<input type="hidden" name="nameFrameParent" value="workframe" />
						<input type="hidden" name="id" value='<c:out value="${param['id']}"/>' />
						<input type="hidden" name="parameters"
							value='<c:out value="${param['parameters']}"/>' />
						<input type="hidden" name="procId"
							value='<c:out value="${param['procId']}"/>' />
						<input type="hidden" name="action"
							value='<c:out value="${param['action']}"/>' />
						<input type="hidden" name="captionkey"
							value='<c:out value="${param['captionkey']}"/>' />
						<input type="hidden" name="onlyselectusers"
							value='<c:out value="${param['onlyselectusers']}"/>' />
						<input type="hidden" name="replSigner"
							value='<c:out value="${param['replSigner']}"/>' />
						<input type="hidden" name="entity"
							value='<c:out value="${param['entity']}"/>' />
						<input type="hidden" name="selVarios"
							value='<c:out value="${param['selVarios']}"/>' />
						<input type="hidden" name="key"
							value='<c:out value="${param['key']}"/>' />
						<input type="hidden" name="entityAppName"
							value='<c:out value="${param['entityAppName']}"/>' />
						<input type="hidden" name="readonly"
							value='<c:out value="${param['readonly']}"/>' />
							
						<input type="hidden" name="uid"/>
						<input type="hidden" name="view"/>
					
						<display:table name="items" id="item" export="false" 
							class="tableDisplay" sort="list" pagesize="20" style="width:95%" requestURI="/entrySearch.do">		
							<c:set var="uid"></c:set>	
							<c:set var="view"></c:set>				
						<logic:iterate id="format" name="CTFrmSearchOrgListFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
										<c:set var="link"></c:set>	
									<logic:equal name="format" property="property" value="UID">
										
										<display:column 
											media='<%=format.getMedia()%>'
											headerClass='<%=format.getHeaderClass()%>' 
											sortable='<%=format.getSortable()%>'>
												
										
											<c:set var="isUser"><bean:write name='item' property='property(USER)' /></c:set>
											<c:set var="isGroup"><bean:write name='item' property='property(GROUP)' /></c:set>
											<c:set var="isOrguni"><bean:write name='item' property='property(ORGUNIT)' /></c:set>
											<bean:define id="uidint" name="item" property="property(UID)"/>
										
												<c:choose>
													<c:when test="${isUser=='true'}">
														<c:set var="view" value="organization"></c:set>	
														<c:set var="uid" ><bean:write name="item" property="property(UID)" /></c:set>	
														<c:url 	value="${param['actionSetResponsibles']}" var="_link">
													
															<c:param name="view" value="organization"/>
															<c:param name="uid" >
															<bean:write name="item" property="property(UID)"/>
															</c:param>
														</c:url>
														<input type="button"  class="btnUser" onclick="javascript:selectSigner('<c:out value="${view}"/>','<c:out value="${uid}"/>' ,'<c:out value="${_link}" escapeXml="false" />');"/>				
													</c:when>
													<c:when test="${isGroup=='true'}">
														<c:set var="view" value="groups"></c:set>	
														<c:set var="uid" ><bean:write name="item" property="property(UID)" /></c:set>	
														<c:url value="${param['actionSetResponsibles']}" var="_link">
															<c:param name="view" value="groups"/>
															<c:param name="uid" >
															<bean:write name="item" property="property(UID)"/>
															</c:param>
														</c:url>
														<input type="button"  class="btnGroup" onclick="javascript:selectSigner('<c:out value="${view}"/>','<c:out value="${uid}"/>','<c:out value="${_link}" escapeXml="false" />');"/>				
													
													</c:when>
													<c:when test="${isOrguni=='true'}">
															<c:set var="view" value="organization"></c:set>	
														<c:set var="uid" ><bean:write name="item" property="property(UID)" /></c:set>	
														<c:url value="${param['actionSetResponsibles']}" var="_link">
															<c:param name="view" value="organization"/>
															<c:param name="uid" >
															<bean:write name="item" property="property(UID)"/>
															</c:param>
														</c:url>
														<input type="button"  class="btnOrg" onclick="javascript:selectSigner('<c:out value="${view}"/>','<c:out value="${uid}"/>','<c:out value="${_link}" escapeXml="false" />');"/>				
													
													</c:when>
												</c:choose>
												
												</display:column>
									</logic:equal>
									
									<logic:equal name="format" property="property" value="RESPNAME">
									
										<display:column titleKey='<%=format.getTitleKey()%>' 
											media='<%=format.getMedia()%>'
											headerClass='<%=format.getHeaderClass()%>' 
											sortable='<%=format.getSortable()%>'>
											<a href="javascript:selectSigner('<c:out value="${view}"/>','<c:out value="${uid}"/>','<c:out value="${_link}" escapeXml="false" />');"
															 class="displayLinkSearch">
																<%=format.formatProperty(item)%>
														</a>
										
											</input>
										
										</display:column>
									
										
								</logic:equal>	
							</logic:iterate>
							</display:table>
							
							</html:form>
				
					</logic:present>
					
				</div> 
				
			</div>       			
</div>
</body>

</html>