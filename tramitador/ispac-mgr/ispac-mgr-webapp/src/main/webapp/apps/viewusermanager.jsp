<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html">
	<title>Búsqueda</title>
	<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
	<c:choose>
		<c:when test="${!empty param['workframe']}">
			<c:set var="_workframe" value="${param['workframe']}" />
		</c:when>
		<c:otherwise>
			<c:set var="_workframe" value="workframe" />
		</c:otherwise>
	</c:choose>
	<jsp:useBean id="_workframe" type="java.lang.String" />
	<script language='JavaScript' type='text/javascript'><!--

		//Variable que indica si están deshabilitados los enlaces.
		disabledLinks = false;
	
		function noCheckedInputRadio(action) {	
	
		 var params='<c:out value="${requestScope.URLParams}" escapeXml="false"/>';
		 var url=action+"?"+params;
	
			var noSelect = true;
			
			var elementos = document.getElementsByName("uid");
			for(var i=0; i<elementos.length; i++) {
				if(elementos[i].checked){
					noSelect = false;
					break;
				}
			}
			
			if (noSelect) {
				jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				
			}
			else {
				var frm = document.forms[0];		
				frm.action = url;
				var frameToReturn='<c:out value="${param['workframeToReturn']}"/>';
		
				if(frameToReturn!=null && frameToReturn!='undefined'){
		  
					eval(element=window.top.frames['workframe'].document.getElementById('contenido'));
					element.style.top=document.getElementById("contenido").style.top;
		 			element.style.left=document.getElementById("contenido").style.left;
		 			element.style.visibility="visible";
		 			element.style.display="block";
				}
				frm.submit(url);
				showMsgInProgress();
			}
		}
		
	function showMsgInProgress(){
		document.getElementById('inProgress').style.display='block';

		document.getElementById('btnOk').disabled=true;
		document.getElementById('btnOk').href='#';
		document.getElementById('btnCancel').disabled=true;
		document.getElementById('btnCancel').href='#';
		document.getElementById('btnOrg').disabled=true;
		document.getElementById('btnOrg').href='#';
		document.getElementById('btnGroup').disabled=true;
		document.getElementById('btnGroup').href='#';
		document.getElementById('btnSearch').disabled=true;
		document.getElementById('btnSearch').href='#';
		disabledLinks = true;
	}
	
	function checkDisabled(){
		return !disabledLinks;
	}
	
	
	
		function hide(){
		var frameToReturn='<c:out value="${param['workframeToReturn']}"/>';
		
		if(frameToReturn!=null && frameToReturn!='undefined'){
		  
			eval(element=window.top.frames['workframe'].document.getElementById('contenido'));
			element.style.top=document.getElementById("contenido").style.top;
		 	element.style.left=document.getElementById("contenido").style.left;
		 	element.style.visibility="visible";
		 	element.style.display="block";
		}

		<ispac:hideframe id="<%=_workframe%>"/>
		}
		
		function  hideShowFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) {
		    action+='<c:out value="&actionSetResponsibles=/viewUsersManager.do&" escapeXml="false"/>'+'<c:out value="${requestScope.URLParams}" escapeXml="false"/>';
			document.getElementById("contenido").style.visibility="hidden";
			document.getElementById("contenido").style.top="0px";
			document.getElementById("contenido").style.left="0px";
			document.getElementById("workframesearch").style.display="block";
			
			showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) ;
			
			
		}
		
	//--></script>
	</head>

	<body>

	<div id="contenido" class="move">
	
		<div class="ficha">
		<bean:define id="urlparams" name="URLParams"/>
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<logic:present parameter="captionkey">
						<bean:parameter id="caption" name="captionkey"/>
						<h4><bean:message name="caption"/></h4>
					</logic:present>
					<div class="acciones_ficha">
						<c:url  var="urlviewUsersManager" value="/viewUsersManager.do"/>
						<a  class="btnOk" id="btnOk" href="javascript:noCheckedInputRadio('<c:out value="${urlviewUsersManager}" escapeXml="false"/>')">
							<nobr><bean:message key="forms.button.aceptar"/></nobr>
						</a>
						
				
						<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" id="btnCancel" onclick="javascript:hide();"/>
					</div>
				</div>
			</div>
		
			<div class="cuerpo_ficha">
			
				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent> 
					<c:if test="${!(requestScope['noviewgroups'] == 'true')}">
					<div class="cabecera_seccion">
						<h4><bean:message key="viewusermanager.titleNavigator"/>
							<logic:equal scope="request" name="type" value="groups">
								<bean:message key="viewusermanager.groups"/>
							</logic:equal>
							<logic:equal scope="request" name="type" value="organization">
								<bean:message key="viewusermanager.organization"/>
							</logic:equal>
						</h4>
					</div>
						
						<br/>
						<div id="navmenu">
								<ul class="actionsMenuList">			
										<li class="withoutBorder">
											<c:url var="urlOrganizationView" value="/viewUsersManager.do" >
													<c:param name="type" value="organization"/>
											</c:url>	
											<a  id="btnOrg"  class="btnOrg" href='<c:out value="${urlOrganizationView}" escapeXml="false"/><c:out value="&" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>'>
													<nobr><bean:message key="viewusermanager.organization"/></nobr>
											</a>
										</li>			
										<li class="withOutBorder">
											<c:url  var="urlGroupsView" value="/viewUsersManager.do">
												<c:param name="type" value="groups"/>
											</c:url>
											<a id="btnGroup" class="btnGroup" href='<c:out value="${urlGroupsView}" escapeXml="false"/><c:out value="&" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>'>
												<nobr><bean:message key="viewusermanager.groups"/></nobr>
											</a>
										</li>
											<li class="withOutBorder">
											<c:url value="/entrySearch.do" var="urlSearchView">
												<c:param name="view" value="showSearch"></c:param>
											</c:url>
											<a id="btnSearch" class="btnSearch" 
												href="javascript:hideShowFrame('workframesearch', '<c:out value="${urlSearchView}" escapeXml="false" />')">
										
												<nobr><bean:message key="common.message.buscador" /></nobr>
											</a>
										</li>
										
										
								</ul>
							</div>					
						</c:if>
						<br>
						<html:form action='/viewUsersManager.do'>
							<div class="viewUsers" >
							<logic:present name="ancestors">
							
							<c:set var="esPrimero" value="1"/>
							<logic:iterate id="ancestor" name="ancestors" type="ieci.tdw.ispac.ispaclib.bean.ItemBean" indexId="cnt">
								<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
									<logic:equal name="format" property="fieldType" value="ANCESTOR">
										<logic:equal name="cnt" value="0">
										<div class="cabecera_seccion">
										<%=format.formatProperty(ancestor)%>
										</div>
										</logic:equal>
											<logic:notEqual name="cnt" value="0">
											
											<c:choose>
												<c:when test="${esPrimero==1}">
													<c:set var="esPrimero" value="0"/>
													&nbsp;
												</c:when>
												<c:otherwise>
													<c:out value=","/>
												</c:otherwise>
											</c:choose>
												<html:link action='<%=format.getUrl() + "&" + urlparams%>' 
														onclick='return checkDisabled();'
														styleClass="resplink" 
														paramId='<%=format.getId()%>' 
														paramName="ancestor"
														paramProperty='<%=format.getPropertyLink() %>'>
														<%=format.formatProperty(ancestor)%>
												</html:link>
											</logic:notEqual>
											
										</logic:equal>
									</logic:iterate>
									
								</logic:iterate>
							</logic:present>
							<c:if test="${!empty requestScope.items}">
								<display:table name="items" 
									 id="item" 
									 export="false" 
									 class="tableDisplayUserManager"
									 sort="list" 
									requestURI='<%= request.getContextPath()+"/viewUserManager.do" %>' >
																		
								<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
									<logic:equal name="format" property="fieldType" value="ITEMLINK">			
										<display:column titleKey='<%=format.getTitleKey()%>' 
											media='<%=format.getMedia()%>' 
											headerClass='<%=format.getHeaderClass()%>'
											sortable='<%=format.getSortable()%>'
											decorator='<%=format.getDecorator()%>'
											class='<%=format.getColumnClass()%>'
											comparator='<%=format.getComparator()%>'>
												<bean:define name="item" id="uid" property="property(UID)"/>	
												<c:choose>
													<c:when test="${requestScope['onlyselectusers'] == 'true'}">
															<logic:equal name="item" property="property(USER)" value="true">															
																	<input type="radio" name="uid" value=<%=uid%>>
															</logic:equal>
													</c:when>
													<c:otherwise>
														<input type="radio" name="uid" value=<%=uid%>>
													</c:otherwise>
												</c:choose>
												<logic:equal name="item" property="property(USER)" value="true">
													<img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>									
															<%=format.formatProperty(item)%>
												</logic:equal>
												<logic:equal name="item" property="property(GROUP)" value="true">
													<img src='<ispac:rewrite href="img/group.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>									
													<html:link action='<%=format.getUrl() + "&" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
																onclick='return checkDisabled();'
																paramProperty='<%=format.getPropertyLink() %>'>
																<%=format.formatProperty(item)%>																		
													</html:link>
												</logic:equal>
												<logic:equal name="item" property="property(ORGUNIT)" value="true">
													<img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>									
													<html:link action='<%=format.getUrl() + "&" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
															onclick='return checkDisabled();'
															paramProperty='<%=format.getPropertyLink() %>'>
															<%=format.formatProperty(item)%>																		
													</html:link>
												</logic:equal>
										</display:column>
								</logic:equal>
						</logic:iterate>
						<display:setProperty name="css.tr.even" value="none"/>
						<display:setProperty name="css.tr.odd" value="none"/>
						<display:setProperty name="basic.show.header" value="false"/>
					</display:table>
				
				</c:if>
				</div>
					</html:form>

					<!--  Capa para presentar el mensaje de Operacion en progreso al realizar la firma -->
					<div id="inProgress" style="display:none; text-align: center">
						<b><bean:message key="msg.layer.operationInProgress" /></b>
					</div>


			</div>
	   	</div>
	</div>
</div>
</div>

<ispac:layer />
 
 <ispac:frame id="workframesearch" />
  	
	
	
</body>

</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>