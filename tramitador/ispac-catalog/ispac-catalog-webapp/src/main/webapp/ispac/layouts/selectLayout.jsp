<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<html>
  <head> 
    <title><bean:message key="head.title"/></title>
    <link rel="stylesheet" href="css/styles.css"/>
    <link rel="stylesheet" href="css/newstyles.css"/>
    <link rel="stylesheet" href="css/nicetabs.css"/>    
   	<script type="text/javascript" src="scripts/utilities.js"></script>
   	<script type="text/javascript" src="scripts/newutilities.js"></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

  </head>
  <body>
  	<div id="body" class="bodytiles">
  		<tiles:insert attribute="info" ignore="true"/>
  	</div>
  	<div id="button" class="buttontiles">
		<input type="button" value="Salir" class="form_button_white" onclick='<ispac:hideframe/>'/>
	</div>
	
	<ispac:layer/>
	
	<div id='dialogworkframe' class='roundTop'
  		style="background-color: #616EAA;display: none;z-index:1024;" >
	  <%--	<img style="display:inline;float:right;margin: 0px 6px 4px 2px"
	  		src='<ispac:rewrite href="img/close.png"/>'
	  		onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/> --%>
		<div>
		  	<ispac:rewrite id="waitPage" page="wait.jsp"/>
		  	
			<iframe id='workframe' name='workframe'
				src='<%=waitPage%>'
				allowTransparency="true"
				style='border:2px solid #616EAA;border-top-width:0;height:100%;width:99.5%;' >
			</iframe>
		</div>
	</div>
  </body>
</html>