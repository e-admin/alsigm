<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<%@page contentType="text/html;charset=ISO-8859-1"%>

<html>
  <head>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
  	<tiles:insert page="components/head.jsp" ignore="true" flush="false"/>
    <script>
    	window.name="ParentWindow";
    </script>
  </head>

  <body>
  	<ispac:keepalive />

	<div id="bodytile" style="visibility:visible;">
			<tiles:insert attribute="body" ignore="true" flush="false"/>
	</div>

    <ispac:layer/>
    <ispac:frame/>
  </body>
  
</html>