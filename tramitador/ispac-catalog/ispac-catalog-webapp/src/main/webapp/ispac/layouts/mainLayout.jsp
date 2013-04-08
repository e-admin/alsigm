<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<%@page contentType="text/html;charset=ISO-8859-1"%>
<html>

	<head>
	<script type="text/javascript" src='<ispac:rewrite href="../../dwr/engine.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../../dwr/util.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
		<tiles:insert page="components/head.jsp" ignore="true" flush="false"/>
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
					<tiles:insert attribute="body" ignore="true" flush="false"/>
				</div>
			</div>
			
			<ispac:layer/>
			<ispac:rewrite id="waitPage" page="wait.jsp"/>
			
			<div id="dialogworkframe" class="roundTop" style="display:none;z-index:1024">
			 
			  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
			    <%--<tr>
			      <td width="100%" align="right">
			        <img style='margin:4px 4px 4px'
			        		 src='<ispac:rewrite href="img/close.png"/>'
			        		 onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
			      </td>
			    </tr>
				--%>
			    <tr>
			      <td >

			        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
			          <tr>
			            <td>
		
			      	      	<iframe id="workframe"
			      	      				name="workframe"
										frameborder="0"
										margin="5px 5px"
										src='<%=waitPage%>'
										allowTransparency="true"
										style="height:100%;width:100%">
										
							</iframe>
			      	    </td>
			      	  </tr>
			      	</table>
			      </td>
			    </tr>
			  </table>
			</div>
			
			<div id="dialogworkframerefresh" class="roundTop" style="display:none;z-index:1024">
			  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
			  <%--  <tr>
			      <td width="100%" align="right">
			        <img style='margin:4px 4px 4px'
			        		 src='<ispac:rewrite href="img/close.png"/>'
			        		 onclick='javascript:<ispac:hideframe id="workframerefresh" refresh="true"/>'/>
			      </td>
			    </tr>
				--%>
			    <tr>
			      <td width="100%" height="100%">
			        <table class="content" width="100%" height="100%" cellpadding="0" cellspacing="0">
			          <tr>
			            <td>
		
			      	      	<iframe id="workframerefresh"
			      	      				name="workframerefresh"
										frameborder="0"
										allowTransparency="true"
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
			
			<div id="dialogworkframemsg" class="roundTop" style="display:none;z-index:1024">
			  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
			    <tr>
			      <td width="100%" align="right">
			        <img style='margin:4px 4px 4px'
			        		 src='<ispac:rewrite href="img/pixel.gif"/>' height="13px" border="0"/>
			      </td>
			    </tr>
			    <tr>
			      <td width="100%" height="100%">
			        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
			          <tr>
			            <td>
		
			      	      	<iframe id="workframemsg"
			      	      				name="workframemsg"
										frameborder="0"
										margin="5px 5px"
										allowTransparency="true"
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
		<div id="dialogworkframesearch" class="roundTop" style="display:none;z-index:1024">
		  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td width="100%" align="right">
		        <img style='margin:4px 4px 4px'
		        		 src='<ispac:rewrite href="img/pixel.gif"/>' height="13px" border="0"/>
		      </td>
		    </tr>
		    <tr>
		      <td width="100%" height="100%">
		        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
		          <tr>
		            <td>
	
		      	      	<iframe id="workframesearch"
		      	      				name="workframesearch"
									frameborder="0"
									margin="5px 5px"
									allowTransparency="true"
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
		<%--
	  	<div id='dialogworkframe' class='roundTop'
	  		style="background-color: #616EAA;display: none;z-index:1024;" >
	
		    <table class="dialog" border="0" width="100%" cellpadding="0" cellspacing="0">
			    <tr>
			      <td width="100%" align="right">
			        <img style='margin:4px 4px 4px 4px'
			        		 src='<ispac:rewrite href="img/close.png"/>'
			        		 onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
			      </td>
			    </tr>
		    </table>
		<div>
			<iframe id='workframe' name='workframe'
				src='<%=waitPage%>'
				style='border:2px solid #616EAA;border-top-width:0;height:100%;width:99.5%;' >
			</iframe>
		</div>
		--%>

		<%--
	 	<div id='dialogworkframe' class='roundTop'
	  		style="background-color: #616EAA;display: none;z-index:1024;" >
		  	<img style="display:inline;float:right;margin: 4px 6px 4px 2px"
		  		src='<ispac:rewrite href="img/close.png"/>'
		  		onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
		  	<div>
				<iframe id='workframe' name='workframe'
				 allowtransparency="true"
					src='<%=waitPage%>'
					style='border:2px solid #616EAA;border-top-width:0;height:100%;width:99.5%;' >
				</iframe>
			</div>
		</div>
		--%>

		<%--
		<ispac:frame/>
		<div id="layer" style="display:none;z-index:1000;background:white;filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50;"/></div>
		--%>

	</body>
  
</html>

