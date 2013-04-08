<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html >
<head>
	<meta http-equiv="Content-Type" content="text/html">
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script>
		function showHelp(){
			window.open('<ispac:onlinehelpHref tipoObj="2" />','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');
		
		}
				function hideDesigner() {

       window.parent.hideFrame('workframe', '<ispac:rewrite page="wait.jsp"/>');	
 }
    </script>
</head>
<body  id="bodyDesigner">
 <c:set var="srcClose"><ispac:rewrite href="img/close1.png"></ispac:rewrite>  </c:set>
	<table id="tabla1" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
	    <tr>
	      <td id="cabeceraDesigner" width="100%" align="right">
	        <img style='margin:4px 4px 4px'
	        		 src='<c:out value="${srcClose}"/>' 
	        		onclick="javascript:hideDesigner()"/>
	        		
	      </td>
	    </tr>
	    <tr>
	      <td width="100%" height="100%">
	        <table class="content" border="1" width="100%" height="100%" cellpadding="0" cellspacing="0">
	          <tr>
	            <td>
				
					<ispac:rewrite id="designerPage" page='components/designer/designer.jsp'/>
					
					<iframe frameborder="0" src='<%=designerPage%>?stageId=<c:out value="${param.stageId}"/>&locale=<c:out value="${param.locale}"/>&processId=<c:out value="${param.processId}"/>' 
					style="width: 100%; height: 100%; z-index: 4000"  ></iframe>
				   
					
	      	    </td>
	      	  </tr>
	      	</table>
	      </td>
	    </tr>
	  </table>
 </body>
</html>

