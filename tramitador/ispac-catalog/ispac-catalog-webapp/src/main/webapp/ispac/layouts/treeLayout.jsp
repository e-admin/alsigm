<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<html>
  <head>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
		<tiles:insert page="components/head.jsp" ignore="true" flush="false"/>
		<%--
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/ajax/ajaxtags-1.1.js"/>'></script>
		--%>
		<script>
		//<!--
			window.name="ParentWindow";
		//-->
		</script>
  </head>

  <body id="docbody" onload="javascript:bodyOnLoad()">
  
    <!-- Javascripts para generar tooltips, obligatorio despues del body -->
	<tiles:insert page="components/tooltip.jsp" ignore="true" flush="false"/>
	
  	<ispac:keepalive />

	<div id="windowtile">
	
	   	<div id="headertile">
			<tiles:insert attribute="header" ignore="true"/>
		</div>
		
	   	<div id="menutile">
			<tiles:insert attribute="menu" ignore="true"/>
		</div>
		
		<div id="externalbodytile">
			<tiles:insert page="../common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
			<div id="bodytile" style="visibility:visible;">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr valign="top">
					<td colspan="2">
						<tiles:insert attribute="subtitle" ignore="true"/>
					</td>
				</tr>
	      			<tr valign="top">
	        			<td width="25%" height="440px" valign="top">
						<tiles:insert attribute="directory" ignore="true"/>
					</td>
	        			<td width="75%">
						<tiles:insert attribute="info" ignore="true" flush="false"/>
					</td>
				</tr>
			</table>
			</div>
		</div>

		<ispac:layer/>
	
		<div id="dialogworkframe" class="roundTop" style="display:none;z-index:1024">
		  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
		    <%-- <tr>
		      <td width="100%" align="right">
		        <img style='margin:4px 4px 4px'
		        		 src='<ispac:rewrite href="img/close.png"/>'
		        		 onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
		      </td>
		    </tr>
		    --%>
		    <tr>
		      <td width="100%" height="100%">
		        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
		          <tr>
		            <td>
						<ispac:rewrite id="waitPage" page="wait.jsp"/>
						
		      	      	<iframe id="workframe"
		      	      				name="workframe"
									frameborder="0"
									allowtransparency="true"
									margin="5px 5px"
									src='<%=waitPage%>'
									style="height:100%;width:100%">
						</iframe>
		      	    </td>
		      	  </tr>
		      	</table>
		      </td>
		    </tr>
		  </table>
		</div>
			<div id="dialogworkframesearch" class="roundTop" style="display:none;z-index:1024">
		  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
		    <%-- <tr>
		      <td width="100%" align="right">
		        <img style='margin:4px 4px 4px'
		        		 src='<ispac:rewrite href="img/close.png"/>'
		        		 onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
		      </td>
		    </tr>
		    --%>
		    <tr>
		      <td width="100%" height="100%">
		        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
		          <tr>
		            <td>
						<ispac:rewrite id="waitPage" page="wait.jsp"/>
						
		      	      	<iframe id="workframesearch"
		      	      				name="workframesearch"
									frameborder="0"
									allowtransparency="true"
									margin="5px 5px"
									src='<%=waitPage%>'
									style="height:100%;width:100%">
						</iframe>
		      	    </td>
		      	  </tr>
		      	</table>
		      </td>
		    </tr>
		  </table>
		</div>
	</div>

  </body>
</html>