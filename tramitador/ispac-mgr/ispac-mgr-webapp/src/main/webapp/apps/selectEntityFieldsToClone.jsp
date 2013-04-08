<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
	<title>Página de búsqueda</title>
	
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
	
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
   </head>
<body>

<div id="contenido" class="move">
	<div class="ficha">
	<html:form action="cloneExpedient.do?method=setEntityFields" styleClass="formWithOutMarginPadding">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="select.entityFieldsToClone.title"/></h4>
				<div class="acciones_ficha">
					<html:submit onclick="javascript: enabledCheckboxes();" styleClass="btnOk">
						<bean:message key="common.message.ok"/>
					</html:submit>
					
					
					<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='<ispac:hideframe refresh="true"/>'/>										
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
	 	</div><%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
		
		<html:hidden property="entity"/>
		<div class="seccion_ficha">
		
			<logic:messagesPresent>
				<div class="infoError"><bean:message key="forms.errors.messagesPresent" /></div>
			</logic:messagesPresent>
			
			<logic:present name="ItemList">
				<jsp:useBean id='defaultForm' type="ieci.tdw.ispac.ispacmgr.action.form.EntityForm" scope="request"/>
				<!-- DisplayTag con formateador -->
				<display:table name="ItemList"
						id="item"
						export="false"
						class="tableDisplay"
						sort="list"
						defaultsort="2"
						requestURI='<%=request.getContextPath() + "/selectEntityFieldsToClone.do"%>'>
									
						<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
												
							<logic:equal name="format" property="fieldType" value="CHECKBOX">
													
									<display:column title='<%="<input type=\'checkbox\' id=\'allCheckbox\' onclick=\'javascript:_checkAll(document.forms[0].multibox, this);\'/>"%>'
											media='<%=format.getMedia()%>'
											headerClass='<%=format.getHeaderClass()%>' 
											sortable='<%=format.getSortable()%>'
											decorator='<%=format.getDecorator()%>'
											class='<%=format.getColumnClass()%>'>
															
											<c:set var="_formatProperty">
												<%=format.formatProperty(item)%>
											</c:set>
											<jsp:useBean id='_formatProperty' type="java.lang.String"/>
												<c:choose>
													<c:when test="${MandatoryFieldIdsMap[_formatProperty] != null}">
														<input type="checkbox" name="multibox" value='<%=format.formatProperty(item)%>' checked="checked" disabled="disabled"/>
													</c:when>
													<c:otherwise>
														<input type="checkbox" name="multibox" value='<%=format.formatProperty(item)%>' <%=defaultForm.isMultiboxChecked(_formatProperty)%>/>
													</c:otherwise>
												</c:choose>
									</display:column>
														
							</logic:equal>
									
							<logic:equal name="format" property="fieldType" value="DATA">
									<display:column titleKey='<%=format.getTitleKey()%>'
											media='<%=format.getMedia()%>'
											headerClass='<%=format.getHeaderClass()%>'
											sortable='<%=format.getSortable()%>'
											decorator='<%=format.getDecorator()%>'
											class='<%=format.getColumnClass()%>'>
											
												<%=format.formatProperty(item)%>
									</display:column>
														
							</logic:equal>
									
					</logic:iterate>
												
				</display:table>
									
			</logic:present>
			
			
																		
		</div> <%--seccion ficha --%>
		
	</div><%--fin cuerpo ficha --%>
		
		<ispac:layer />
</div><%--fin  ficha --%>
</html:form>
<div><%--fin contenido --%>

</body>
</html>

	<script language='JavaScript' type='text/javascript'><!--
			
			<logic:notEmpty name="SelectedAll" scope="request">	

				var idAllCheckbox = document.getElementById("allCheckbox");
				idAllCheckbox.checked = true;
				_checkAll(document.forms[0].multibox, idAllCheckbox);
			
			</logic:notEmpty>
			
			function enabledCheckboxes() {
				
				showLayer();
								
				var field = document.forms[0].multibox;
				if (field != undefined) {
					if (field.length == undefined) {
		   				field.disabled = false;
		   			}
		   			else {
						for (i = 0; i < field.length; i++) {
							field[i].disabled = false;
						}
					}
				}
			}
			positionMiddleScreen('contenido');
				$(document).ready(function(){
					$("#contenido").draggable();
			});
			
		//--></script>
