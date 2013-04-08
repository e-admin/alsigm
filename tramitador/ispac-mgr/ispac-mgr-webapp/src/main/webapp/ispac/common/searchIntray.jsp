<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>' />
<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
	<![endif]-->

<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
	<![endif]-->

<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
	<![endif]-->

<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
<ispac:javascriptLanguage />
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script>

		positionMiddleScreen('move');
		$(document).ready(function(){
			$("#move").draggable();
		});
    	function showSearchForm() {
    		window.location.href = 'processIntray.do?register=<c:out value="${param.register}"/>';
    	}
    	
    	function attach(numexp) {	
			//document.getElementById('move').style.visibility = "hidden"; 
			
			
			document.defaultForm.numexp.value = numexp;
			document.defaultForm.submit();
			
		}
    </script>
</head>
<body>
<div id="move">
	<div class="ficha"> 
	<div class="encabezado_ficha">
		<div class="titulo_ficha">
			<h4><bean:message key="intray.attach.search.results.titulo" /></h4>
			<div class="acciones_ficha">
				<a href="#" id="btnCancel"
				onclick="javascript:showSearchForm()" class="btnSearch"><bean:message
				key="common.message.buscador" /></a>
				<a href="#" id="btnCancel"
				onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe/>'
				class="btnCancel"><bean:message key="common.message.cancel" /></a>
			</div><%--fin acciones ficha --%>
		</div><%--fin titulo ficha --%>
	</div><%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
			<html:form action="annexIntrayProcess.do">
				<input type="hidden" name="register" value='<c:out value="${param.register}"/>' />
				<input type="hidden" name="numexp" />
				<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter" />
						<logic:present name="maxResultados"> 
 							 <c:set var="maxres" value="${requestScope['maxResultados']}"/> 
 							 <c:set var="totalres" value="${requestScope['numTotalRegistros']}"/> 
 							 <bean:define id="maxres" name="maxres" type="java.lang.String"></bean:define>
 							 <bean:define id="totalres" name="totalres" type="java.lang.String"></bean:define>
 								 <div class="aviso"> 
									<img src='<ispac:rewrite href="img/error.gif"/>' /> 
 									<bean:message  key="search.generic.hayMasResultado"  arg0='<%=maxres%>' arg1='<%=totalres%>' />
 		                          </div> 
 		                 </logic:present> 
				<display:table name="ResultsList" id="result"
					requestURI='searchIntray.do' export='<%=formatter.getExport()%>'
					class='<%=formatter.getStyleClass()%>' sort='<%=formatter.getSort()%>'
					pagesize='<%=formatter.getPageSize()%>'
					defaultorder='<%=formatter.getDefaultOrder()%>'
					defaultsort='<%=formatter.getDefaultSort()%>'>
					<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
						<logic:equal name="format" property="fieldType" value="LINK">
							<display:column titleKey='<%=format.getTitleKey()%>' media='html'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'
								class='<%=format.getColumnClass()%>'>
								<a
									href='<%="javascript:attach(\"" + format.formatProperty((ieci.tdw.ispac.ispaclib.bean.ItemBean)result) + "\")" %>'
									class='<%= format.getStyleClass() %>'> <%=format.formatProperty(result)%>
								</a>
							</display:column>
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='csv excel xml pdf'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								class='<%=format.getColumnClass()%>'>
								<%=format.formatProperty(result)%>
							</display:column>
						</logic:equal>
						<logic:equal name="format" property="fieldType" value="LIST">
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'
								class='<%=format.getColumnClass()%>'>
								<%=format.formatProperty(result)%>
							</display:column>
						</logic:equal>
						<logic:equal name="format" property="fieldType" value="DATE">
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'
								class='<%=format.getColumnClass()%>'>
								<%=format.formatProperty(result)%>
							</display:column>
						</logic:equal>
					</logic:iterate>
				</display:table>
				</html:form>
			</div>
			</div>
		</div>
	</div>
	
	<ispac:layer id="waitInProgress" msgKey="msg.layer.operationInProgress" showCloseLink="false" styleClassMsg="messageShowLayer" />
	
</body>
</html>
