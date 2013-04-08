<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page contentType="text/html;charset=ISO-8859-1"%>

<html>
<head>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<tiles:insert page="components/head.jsp" ignore="true" flush="false"/>
	<style>
	body {
		scrollbar-arrow-color: #454a4d;
		scrollbar-3dlight-color: #cfd3d5;
		scrollbar-highlight-color: #ffffff;
		scrollbar-face-color: #F5FBFE;
		scrollbar-shadow-color: #F5FBFE;
		scrollbar-track-color: #f0f0f1;
		scrollbar-darkshadow-color: #cfd3d5;
	}
	</style>	
</head>

<body id="docbody">

	<!-- Javascripts para generar tooltips, obligatorio despues del body -->
	<tiles:insert page="components/tooltip.jsp" ignore="true" flush="false"/>
	
    <div id="windowtile">
		<div id="externalbodytileFrame">
			<tiles:insert page="../common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>

			<div id="panel">
				<div id="tabs">
					<tiles:importAttribute name="currentTabName" ignore="true" />
					<tiles:importAttribute name="tabNames" ignore="true" />
					<tiles:importAttribute name="tabEntityId" ignore="true" />

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<c:set var="digitalSignManagementActive" value="${ISPACConfiguration.DIGITAL_SIGN_CONNECTOR_CLASS}"/>
							<c:forEach var="tabName" items="${tabNames}" varStatus="status">
								<c:if test="${!(tabName == 'ctosfirma' && empty digitalSignManagementActive)}">
								<c:choose>
									<c:when test="${tabName == currentTabName}">
										<td class="select" align="center">
											<c:set var="tabDescrKey" value="procedure.tabs.${tabName}"/>
											<bean:define id="tabDescrKey" name="tabDescrKey" type="java.lang.String"/>
											<bean:message key='<%=tabDescrKey%>'/>
										</td>
									</c:when>
									<c:otherwise>
										<td class="unselect" align="center">
											<c:url var="url" value="showProcedureEntity.do">
												<c:param name="method" value="${tabName}"/>
												<c:param name="entityId" value="${tabEntityId}"/>
												<c:param name="regId" value="${param.regId}"/>
											</c:url>
											<c:set var="tabDescrKey" value="procedure.tabs.${tabName}"/>
											<bean:define id="tabDescrKey" name="tabDescrKey" type="java.lang.String"/>
											<a href='<c:out value="${url}"/>'>
												<bean:message key='<%=tabDescrKey%>'/>
											</a>
								       	</td>
									</c:otherwise>
								</c:choose>
								</c:if>
								<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
							</c:forEach>
							<td width="100%" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
						</tr>
					</table>
				</div>
				<tiles:insert attribute="body" ignore="true" flush="false"/>
			</div>
		</div>
		<ispac:layer/>

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
		      <td width="100%" height="100%">
		        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
		          <tr>
		            <td>
			            <ispac:rewrite id="waitPage" page="wait.jsp"/>
			            
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
	</div>
</body>
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

</html>