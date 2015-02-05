<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<%@page contentType="text/html;charset=ISO-8859-1"%>
<c:if test="${requestScope['REFRESH_VIEW_KEY'] || param['REFRESH_VIEW_KEY']=='true'}">
	<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.fondos.CUADRO_CLF_VIEW_NAME}" /></c:out></c:set>
	
	<script>
		var pageFrames = window.top.frames;
		for (var i=0; i<pageFrames.length; i++) {
			if (pageFrames[i] != window && pageFrames[i].refreshWindow) {
				pageFrames[i].refreshWindow('<c:out value="${sessionScope[viewName].selectedNode.nodePath}" />');
			}
		}
	</script>
	
</c:if>

<c:set var="showTree" value="${true}"/>
<c:choose>
	<c:when test="${showTree}">
		<c:set var="treeViewDisplay" value="block"/>
		<c:set var="botoneraViewImg" value="../images/pestanya_on.gif"/>
	</c:when>
	<c:otherwise>
		<c:set var="treeViewDisplay" value="none"/>
		<c:set var="botoneraViewImg" value="../images/pestanya_off.gif"/>
	</c:otherwise>
</c:choose>

<html>
  <head>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<tiles:insert page="components/head.jsp" ignore="true" flush="false"/>
	    
	    <script>
		//<!--
			function execFromFrame(url){
				ispac_needToConfirm = true; 
				showFrame('workframe',url,640,460);
			}
			function execFromTop(url){
				ispac_needToConfirm = true; 
				top.window.location.href = url;
			}
			function execDesigner(url){
				ispac_needToConfirm = true; 
	        
				showDesigner('workframerefresh',url,10,10);
			}
			function execInRightSide(url){
			}
			
			window.name="ParentWindow";
		//-->
		</script>
  </head>

  <body id="docbody">
    	<ispac:layer id="waiting" msgKey="msg.layer.operationInProgress" styleClassMsg="messageShowLayer" />
  	<ispac:keepalive />

	<div id="dialogworkframe" class="roundTop" style="display:none;z-index:1024">
	  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
	   <%--  <tr>
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

<div id="dialogworkframerefresh" class="roundTop" style="display:none;z-index:1024">
			  <table class="dialogNoTransparent" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
			    <tr>
			      <td width="100%" align="right" class="barraClose">
			      
			      <img style='margin:4px 4px 4px'
			        		 src='<ispac:rewrite href="img/close.gif"/>'
			        		 onclick='javascript:<ispac:hideframe id="workframerefresh" refresh="true"/>'/>
			      
			      </img> 
			     
			      </td>
			    </tr>
			    <tr>
			      <td width="100%" height="100%">
			        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
			          <tr>
			            <td>
		
			      	      	<iframe id="workframerefresh"
			      	      				name="workframerefresh"
										frameborder="0"
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

	<div id="headertile">
		<tiles:insert attribute="header" ignore="true"/>
	</div>
	<div id="menutile">
		<tiles:insert attribute="menu" ignore="true"/>
	</div>

	<div id="externalbodytile">
	
	<!-- Cabecera -->
	<tiles:insert attribute="cabecera" ignore="true" flush="true" />
	    
	<div id="bodytileSinBorde">
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" style="height:100%" >
		<tr valign="top">
			<td width="250px" style="border:1px solid #FFFFFF">
<!--<%--
		        <div id="treeView" class="treeView" width="100%">

		          <table border="0" cellpadding="0" cellspacing="0" width="100%" style="height:500px">
		            <tr>
		              <td style="height:500px">
		                <div id="treeContainer" class="frameArbol">
--%>  -->          
		                <c:set var="showTreeViewAction">
		                  <tiles:insert attribute="showTreeViewAction" ignore="true" flush="false" />	
		                </c:set>
		                <c:url var="showTreeViewURL" value="${showTreeViewAction}">
		                  <c:param name="method">
		                    <c:out value="${viewAction}">crearVista</c:out>
		                  </c:param>
		                  <c:if test="${!empty viewName}"><c:param name="viewName" value="${viewName}" /></c:if>
		                </c:url>

		                  <iframe class="bloque_frame"
		                  	name="arbol" id="arbol" marginWidth="0"
		                  	style="WIDTH:250px; HEIGHT: 100%"
		                  	src='<c:out value="${showTreeViewURL}"/>' 
		                  	width="250px" height="100%" 
		                  	scrolling="automatic" frameborder="0"></iframe>
<!--<%--
		                </div>
		              </td>
				    </tr>
		          </table>
		        </div>
--%>  -->          
		    </td>
		        
		    <TD style="border:1px solid #FFFFFF">
		          <c:url var="showContentURL" value="${showContentURL}" />
		          <c:if test="${empty showContentURL}">
		            <c:set var="TREE_VIEW" value="${sessionScope[viewName]}" />
		            <c:choose>
		              <c:when test="${empty TREE_VIEW}">
		                <c:set var="defaultViewURL">
		                  <tiles:insert attribute="defaultViewURL" ignore="true" flush="false" />	
		                </c:set>
		                <c:url var="showContentURL" value="${defaultViewURL}" />
		              </c:when>
		              <c:otherwise>
		                <c:url var="showContentURL" value="${showTreeViewAction}">
		                  <c:param name="method" value="showSelectedNode" />
		                  <c:param name="viewName" value="${viewName}" />
		                </c:url>
		              </c:otherwise>
		            </c:choose>
		          </c:if>
		          <iframe class="bloque_frame"
		          	  name="ibasefrm" id="ibasefrm" marginwidth="0" 
			          scrolling="automatic" frameborder="0" 
			          height="100%" width="100%"
			          src='<c:out value="${showContentURL}"/>' 
			          style="height:100%;width:100%"></iframe>
		    </TD>
		</TR>
	</table>

	</div>
    </div>

    <ispac:layer/>
    

  </body>
</html>
