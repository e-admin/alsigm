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

<%--
<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
--%>

<script>
	// Array con los nombres de los campos del frame origen
	fields = new Array();
	
	<logic:iterate name="parameters" id="parameter" indexId="index">
		fields[<c:out value="${index}"/>] = '<c:out value="${parameter}"/>';
	</logic:iterate>
     
	// Rellena un campo del frame origen cuyo nombre es '_name' con el valor '_value'
	// PARAMS
	// _name: nombre del campo en el formulario origen
	// _value: Valor a cargar
	function _populateValue(_name,_value){
  		var element = null;
  		element = top.document.defaultForm.elements[ _name ];
  		if (element != null)
  			element.value = _value;
	}
    
    // Recorre los valores pasados como una cadena separados por '$' 
    // para cargarlos en los campos del frame origen.
    // PARAMS:
    // _values: Cadena de valores separados por '$'
    function _setValues(_values){
    	var aValues = new Array();
     	var aFields = new Array();
     	aValues = _values.split("$");
     	for(i=0;i < aValues.length ; i++){
        	aFields = fields[i].split("=");
         	_populateValue(aFields[0],aValues[i] );
     	}
    }
</script>
</head>

<body>


<table cellpadding="0" cellspacing="1" border="0" class="boxGris" width="99%" style="margin-top:6px; margin-left:4px">
	<tr>
		<td width="100%">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td class="title" height="18px" width="80%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr valign="bottom">
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
								<td width="100%" class="menuhead">
									<bean:message key="select.value.title"/>
								</td>
							</tr>
						</table>
					</td>
					<td width="20%" style="text-align:right">
						<%-- BOTON SALIR --%>
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
							</tr>
							<tr>
								<td style="text-align:right">
									<input type="button" value='<bean:message key="common.message.cancel"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
									<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
								</td>
							</tr>
						</table>						
					</td>
				</tr>
				<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100%" class="blank">
			<table border="0" cellspacing="2" cellpadding="2" width="100%">
				<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
				<tr>
					<td valign="middle" align="center" class="formsTitle" width="100%">
			    		<table cellpadding="0" cellspacing="1" border="0" width="98%" >
							<tr>
								<td class="index"><html:errors/></td>
							</tr>
							<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
					      	<tr>
					        	<td>
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
							                			<a href='<c:out value="${link}"/><ispac:hideframe/>' class="tdlink">
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
										
									</logic:present>
			        			</td>
			      			</tr>
			    		</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>

</html>