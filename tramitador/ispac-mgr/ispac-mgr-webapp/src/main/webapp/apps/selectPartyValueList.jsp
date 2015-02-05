<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Página de selección</title>
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
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
	
<%--
<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
--%>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
				$("#contenido").draggable();
			});
	// Array con los nombres de los campos del frame origen
	fields = new Array();
	
	<logic:iterate name="parameters" id="parameter" indexId="index">
		fields[<c:out value="${index}"/>] = '<c:out value="${parameter}"/>';
	</logic:iterate>
     
    // Recorre los valores pasados como una cadena separados por '$' 
    // para cargarlos en los campos del frame origen (id + "$" + nombre + "$" + tipo),
    // PARAMS:
    // _values: Cadena de valores separados por '$'
    function _setValues(_values){
    	
     	var aValues = _values.split("$");
     	
     	for (i=0; i < fields.length ; i++) {
        	var aFields = fields[i].split("=");
        	var paramName = aFields[0];
			if (paramName != 'JAVASCRIPT'){
				
          		var element = top.document.defaultForm.elements[paramName];
          		if (element != null) {
          			
          			var value = "";
          			
          			if (aFields[1] == 'ID') {
          				value = aValues[0];
          			} else if (aFields[1] == 'NOMBRE') {
          				value = aValues[1];
          			} else if (aFields[1] == 'TIPO') {
          				value = aValues[2];
          			}
          			
          			element.value = value;
        		}
			} else {
				eval('top.'+aFields[1]+'()');
			}
     	}
    }
</script>
</head>

<body>


	<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="select.value.title"/></h4>
					<div class="acciones_ficha"><input type="button"
						value='<bean:message key="common.message.cancel"/>' class="btnCancel"
						onclick='<ispac:hideframe/>' />
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
			<logic:present name="ValueList">
									
										<!-- displayTag con formateador -->
										<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

										<display:table name="ValueList"
													   id="value"
													   export='<%=formatter.getExport()%>'
													   class='<%=formatter.getStyleClass()%>'
													   sort='<%=formatter.getSort()%>'
													   pagesize='<%=formatter.getPageSize()%>'
													   defaultorder='<%=formatter.getDefaultOrder()%>'
													   defaultsort='<%=formatter.getDefaultSort()%>'
													   requestURI='<%=formatter.getRequestURI()%>'>
											
											<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
					
												<logic:equal name="format" property="fieldType" value="LINK">
												
												  	<display:column titleKey='<%=format.getTitleKey()%>'
												  					media='<%=format.getMedia()%>' 
												  					headerClass='<%=format.getHeaderClass()%>'
												  					sortable='<%=format.getSortable()%>'
												  					sortProperty='<%=format.getPropertyName()%>'
												  					decorator='<%=format.getDecorator()%>' 
												  					class='<%=format.getColumnClass()%>'
												  					comparator='<%=format.getComparator()%>'>
												  		
												  		<c:set var="link">
										   					javascript:_setValues("<bean:write name="value" property="string(VALUES)"/>");
														</c:set>
							                			<a href='<c:out value="${link}"/>' class="tdlink">
							                				<%=format.formatProperty(value)%>
												  		</a>
												  		
												  	</display:column>
												  	
												</logic:equal>
												
												<logic:equal name="format" property="fieldType" value="LIST">
												
													<display:column titleKey='<%=format.getTitleKey()%>' 
																	media='<%=format.getMedia()%>' 
																	headerClass='<%=format.getHeaderClass()%>'
																	sortable='<%=format.getSortable()%>'
																	sortProperty='<%=format.getPropertyName()%>'
																	decorator='<%=format.getDecorator()%>'
																	class='<%=format.getColumnClass()%>'
																	comparator='<%=format.getComparator()%>'>
														
														<%=format.formatProperty(value)%>
														
													</display:column>
													
												</logic:equal>
					
											</logic:iterate>
					
										</display:table>
										<br/>
									</logic:present>
			</div><%--seccion ficha --%>
		</div><%--fin cuerpo ficha --%>
		</div><%--fin  ficha --%>
<div><%--fin contenido --%>



</body>

</html>