<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>Página de búsqueda</title>
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
		 <link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
  
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    
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
		
		<%
			String entity = request.getParameter("entity");
		
			// Nombre la variable de sesión donde se han salvado
			// los parámetros que utiliza el tag actionframe
			String parameters = request.getParameter("parameters");
			
			
			String multivalueId = request.getParameter("multivalueId");
		
			// Action a ejecutar al seleccionar
			String setAction = request.getParameter("setAction");
			if (setAction == null) {
		
				setAction = "setValue.do";
			}
			
			// Action a ejecutar al buscar
			String searchAction = request.getParameter("searchAction");
			if (searchAction == null) {
				
				searchAction = "searchValue.do";
			}
			
			// Clave para el título de la pantalla
			String captionKey = request.getParameter("captionKey");
			if (captionKey == null) {
				
				captionKey = "select.value.title";
			}
			
			// Mostrar el buscador
			String showSearch = request.getParameter("showSearch");
			if (showSearch == null) {
				
				showSearch = "true";
			}
						
			String displayTagURI = searchAction + "?entity=" + entity 
								 + "&parameters=" + parameters
			 					 + "&setAction=" + setAction
			 					 + "&searchAction=" + searchAction
			 					 + "&captionKey=" + captionKey
			 					 + "&showSearch=" + showSearch;
			
			// Clave para la etiqueta del buscador
			String searchKey = request.getParameter("searchKey");
			if (showSearch.equals("true")) {

				if (searchKey == null) {
					
					searchKey = "search.generic.containsText";
				}
				
				displayTagURI += "&searchKey=" + searchKey;
			}
			else {
				searchKey = "";
			}

			// Parámetros de búsqueda
			String operador = (String) request.getAttribute("operador");
			if (operador != null) {
				
				displayTagURI += "&operador=" + operador;
			}
			else {
				operador = "";
			}
			String criterio = (String) request.getAttribute("criterio");	
			if (criterio != null) {
				
				displayTagURI += "&criterio=" + criterio;
			}
			else {
				criterio = "";
			}
			
			// Formateador para la lista
			String xmlFormatter = request.getParameter("xmlFormatter");
			if ((xmlFormatter != null) &&
				(xmlFormatter != "")) {

				displayTagURI += "&xmlFormatter=" + xmlFormatter;
			}
			else {
				xmlFormatter = "";
			}

			// Valor del campo implicado en la búsqueda
			String field = request.getParameter("field");
			if ((field != null) &&
				(field != "")) {
		
				displayTagURI += "&field=" + field;
			}
			else {
				field = "";
			}
		%>
		
	<ispac:rewrite id="searchValue" action='<%=searchAction%>' />
	<ispac:rewrite id="setValue" action='<%=setAction%>' />
		
		<script language='JavaScript' type='text/javascript'><!--
			
			function SelectValue(value) {
		
				document.defaultForm.action = '<%=setValue%>' + "?value=" + value;
				document.defaultForm.submit();
			}
			
		//--></script>
		
	</head>
	
	<body>
	<div id="contenido" class="move" >
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key='<%=captionKey%>' /></h4>
				<div class="acciones_ficha">
					<input type="button" value='<bean:message key="common.message.close"/>' class="btnCancel" onclick='<ispac:hideframe/>'/>				
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
				<div class="cabecera_seccion">
							<h4><bean:message key='<%=searchKey%>' /> </h4>
				</div>
											
				<html:form action='<%=searchAction%>'
				method="post">

					<input type="hidden" name="entity" value='<%=entity%>' />
					<input type="hidden" name="parameters" value='<%=parameters%>' />
					<input type="hidden" name="multivalueId" value='<%=multivalueId%>' />
					<input type="hidden" name="setAction" value='<%=setAction%>' />
					<input type="hidden" name="searchAction" value='<%=searchAction%>' />
					<input type="hidden" name="captionKey" value='<%=captionKey%>' />
					<input type="hidden" name="xmlFormatter" value='<%=xmlFormatter%>' />
					<input type="hidden" name="showSearch" value='<%=showSearch%>' />
					<input type="hidden" name="searchKey" value='<%=searchKey%>' />
					<input type="hidden" name="operador" value='<%=operador%>' />
					<input type="hidden" name="criterio" value='<%=criterio%>' />
					<input type="hidden" name="field" value='<%=field%>' />
			
					<c:set var="_showSearch"><%=showSearch%></c:set>
					<c:if test="${_showSearch == 'true'}">
						<logic:present name="maxResultados">
						<c:set var="maxres" value="${requestScope['maxResultados']}" />
						<c:set var="totalres" value="${requestScope['numTotalRegistros']}" />
						<bean:define id="maxres" name="maxres" type="java.lang.String"></bean:define>
						<bean:define id="totalres" name="totalres" type="java.lang.String"></bean:define>
						<div class="aviso"><img
							src='<ispac:rewrite href="img/error.gif"/>' /> <bean:message
							key="search.generic.hayMasResultado" arg0='<%=maxres%>'
							arg1='<%=totalres%>' /></div>
						<span style="padding-left: 13px"><bean:message
							key="search.generic.hayMasResultado.consejo" /></span>
					</logic:present>
							
						<p>
						<div id="buscador">
						<nobr>
						<label class="popUp"><bean:message key="common.operator" />:</label><html:select
							styleClass="inputSelect" property="property(operador)">
							<html:option value="1">
								<bean:message key="common.operator.contains" />
							</html:option>
							<html:option value="2">
								<bean:message key="common.operator.begin" />
							</html:option>
							<html:option value="3">
								<bean:message key="common.operator.end" />
							</html:option>
						</html:select> &nbsp;&nbsp; <label class="popUp"><bean:message key="common.texto" />:</label><ispac:htmlText
							property="property(criterio)" readonly="false" size="38%"
							styleClass="input"></ispac:htmlText> &nbsp;&nbsp; <input
							type="submit" value="<bean:message key="search.button.search"/>"
							class="form_button_white" name="buscar" />
							</nobr>
							</div>
					</c:if>
											
							        		<!-- displayTag con formateador -->
											<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>
							        		
	        			<div class="tableDisplay">
	        			
     										<display:table  name="ValueList"
															id="value"
															export='<%=formatter.getExport()%>'
									class='<%=formatter.getStyleClass()%>' sort='<%=formatter.getSort()%>'
															pagesize='<%=formatter.getPageSize()%>'
															defaultorder='<%=formatter.getDefaultOrder()%>'
															defaultsort='<%=formatter.getDefaultSort()%>'
									requestURI='<%=displayTagURI%>' excludedParams="*">
												
												<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																   		
													<!-- ENLACE -->
													<logic:equal name="format" property="fieldType" value="LINK">
													
													  	<display:column titleKey='<%=format.getTitleKey()%>'
													  					media='<%=format.getMedia()%>'
													  					headerClass='<%=format.getHeaderClass()%>'
													  					sortable='<%=format.getSortable()%>'
													  					sortProperty='<%=format.getPropertyName()%>'
													  					class='<%=format.getColumnClass()%>'
													  					decorator='<%=format.getDecorator()%>'
													  					comparator='<%=format.getComparator()%>'>
													  					
													  				<bean:define name="value" property="property(VALOR)" id="valor"/>

													              	<a class="element" href="javascript:SelectValue('<%= valor.toString() %>');">
											              					<%=format.formatProperty(value)%>
														            </a>
											                
													  	</display:column>
													  	
										   			</logic:equal>
													   		
										   			<!-- DATO DE LA LISTA -->
										   			<logic:equal name="format" property="fieldType" value="LIST">
										   			
														<display:column titleKey='<%=format.getTitleKey()%>'
																	    media='<%=format.getMedia()%>'
													  					headerClass='<%=format.getHeaderClass()%>'
													  					sortable='<%=format.getSortable()%>'
													  					sortProperty='<%=format.getPropertyName()%>'
													  					class='<%=format.getColumnClass()%>'
													  					decorator='<%=format.getDecorator()%>'
													  					comparator='<%=format.getComparator()%>'>
													  					
															<%=format.formatProperty(value)%>
															
														</display:column>
														
										   			</logic:equal>
										   			
												</logic:iterate>
												
											</display:table>

					<div>
			
				</html:form>
			<ispac:layer />
		</div><%--seccion ficha --%> 
		</div><%--fin cuerpo ficha --%>

		</div><%--fin  ficha --%>
	<div><%--fin contenido --%>
	
	</body>
	
</html>

<script>

positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>