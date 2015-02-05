<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:set var="ThirdPartyList" value="${requestScope.ThirdPartyList}" />

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
	<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	
    <ispac:javascriptLanguage/>

<script>
			function executeSearch(){
				executeFrame("workframe", document.defaultForm.action);
			}
			

	function noCheckedInputRadio(action) {	
		
		var params='<c:out value="${requestScope.URLParams}" escapeXml="false"/>';
		var url=action+"?"+params;
		
			var noSelect = true;
			var frm = document.forms['usersForm'];
			if(frm!='undefined'){
				var elementos = document.getElementsByName("uid");
				for(var i=0; i<elementos.length; i++) {
					if(elementos[i].checked){
						noSelect = false;
						break;
					}
				}
			}
			if(noSelect){
				jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				
			}
			else{
				frm.action = url;
						var frameToReturn='<c:out value="${param['workframeToReturn']}"/>';
				if(frameToReturn!=null && frameToReturn!='undefined'){
		  
					eval(element=window.top.frames['workframe'].document.getElementById('contenido'));
					element.style.top=document.getElementById("contenido").style.top;
		 			element.style.left=document.getElementById("contenido").style.left;
		 			element.style.visibility="visible";
		 			element.style.display="block";
				}
				element=window.top.frames['workframe'].document.getElementById('workframeDirectory');
				layer=window.top.frames['workframe'].document.getElementById('layer');
			
  				if (element != null) {
    				element.style.visibility = "hidden";
    				element.style.display = "none";
    				hideLayer();
    				
  				}
  				if (layer != null) {
  					layer.style.visibility = "hidden";
				}
		
  				
				frm.submit(url);	
			}	
		}
			function hide(){
				var frame='<c:out value="${param['workframe']}" escapeXml="false"/>';
				if(frame!=null && frame!='undefined'){
					element=window.parent.document.getElementById("contenido");
				}
				else{
		 		eval(element=window.top.frames['workframe'].document.getElementById("contenido"));
		 		}
		   		eval(elementSearch=element.document.getElementById("workframesearch"));
		    	<ispac:hideframe id="workframesearch" refresh="false"/>
		 		element.style.visibility="visible";
		 		element.style.top=document.getElementById("contenido").style.top;
		 		element.style.left=document.getElementById("contenido").style.left;
		 		elementSearch.style.display="none";
	 	}
		$(document).ready(function() {
		
			$("#contenido").draggable();
			
		});
		
		 

		</script>

</head>
<body>

<logic:messagesPresent>
	<div class="infoError">
			<bean:message key="forms.errors.messagesPresent" />
	</div>
</logic:messagesPresent> 
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
					<c:url  var="urlviewUsersManager" value="${param['actionSetResponsibles']}"/>
					
					<a  class="btnOk" href="javascript:noCheckedInputRadio('<c:out value="${urlviewUsersManager}" escapeXml="false"/>');">
						<nobr><bean:message key="forms.button.aceptar"/></nobr>
					</a>
					<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='javascript:hide();' />
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>
	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
			<logic:present name="criterioVacio">
				<div class="infoError">
					<bean:message key="delegate.select.object.warning.filter.required" />
				</div>
			</logic:present>
			<c:set var="_executeSearch"><c:out value="/entrySearch.do?view=executeSearch&actionSetResponsibles=${param['actionSetResponsibles']}" /><c:out value="&${urlparams}" escapeXml="false" /></c:set>
			<jsp:useBean id="_executeSearch" type="java.lang.String" /> 		
			<html:form action='<%= _executeSearch %>' styleClass="buscador">
				<div id="buscador">
				<nobr>
						<label class="popUp">
							<bean:message key="delegate.select.object.nombre.label" />:</label> 
							<ispac:htmlText property="criterio" readonly="false" size="38%" styleClass="input"></ispac:htmlText>
							&nbsp;&nbsp; 
							<input type="submit" onclick="javascript:executeSearch();"value="<bean:message key="common.message.buscador"/>" class="input" name="buscar" />
				</nobr>
				</div>
				
			</html:form>
			<logic:present name="items">
				<c:set var="actionParam">
					<c:out value="${param['actionSetResponsibles']}" />
				</c:set>
				<br/>
				<jsp:useBean id="actionParam" type="java.lang.String" />
				<html:form action='<%= actionParam %>' styleClass="buscador">
					<div class="viewUsers">
						<div class="cabecera_seccion">
							<bean:message key="search.results"/>
						</div>
					<display:table name="items" id="item" export="false"
						class="tableDisplayUserManager" sort="list"
						requestURI="/entrySearch.do">
						<logic:iterate id="format" name="FrmSearchOrgListFormatter"
							type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
							
								<logic:equal name="format" property="property" value="RESPNAME">
									<display:column titleKey='<%=format.getTitleKey()%>'
										media='<%=format.getMedia()%>'
										headerClass='<%=format.getHeaderClass()%>'
										sortable='<%=format.getSortable()%>'
										decorator='<%=format.getDecorator()%>'
										class='<%=format.getColumnClass()%>'
										comparator='<%=format.getComparator()%>'>
										<bean:define name="item" id="uid" property="property(UID)" />
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
											<img src='<ispac:rewrite href="img/user.gif"/>' align="center"
												valign="top" width="17px" height="15px" border="0" />
											<%=format.formatProperty(item)%>
										</logic:equal>
										<logic:equal name="item" property="property(GROUP)" value="true">
											<img src='<ispac:rewrite href="img/group.gif"/>' align="center"
												valign="top" width="17px" height="15px" border="0" />
			
											<%=format.formatProperty(item)%>
			
										</logic:equal>
										<logic:equal name="item" property="property(ORGUNIT)"
											value="true">
											<img src='<ispac:rewrite href="img/org.gif"/>' align="center"
												valign="top" width="17px" height="15px" border="0" />
			
											<%=format.formatProperty(item)%>
			
										</logic:equal>
									</display:column>
									
								</logic:equal>
							</logic:iterate>
							<display:setProperty name="css.tr.even" value="none" />
							<display:setProperty name="css.tr.odd" value="none" />
							<display:setProperty name="basic.show.header" value="false" />
					</display:table>
					</div>
				</html:form>
			</logic:present>
		</div>
	</div>
</div>
</body>
</html>