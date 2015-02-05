<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page contentType="text/html;charset=ISO-8859-1"%>

<html>
  <head>
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
		<script type="text/javascript" src="scripts/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.alerts.js"></script>
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
				
				<c:set var="wizardTitle" value="${requestScope['WIZARD_TITLE']}"/>
				<c:set var="wizardTitleKey" value="${requestScope['WIZARD_TITLE_KEY']}"/>
				<c:choose>
					<c:when test="${!empty wizardTitle}">
						<c:set var="title" value="${wizardTitle}"/>
					</c:when>
					<c:when test="${!empty wizardTitleKey}">
						<bean:define id="wizardTitleKey" name="wizardTitleKey"/>
						<c:set var="title">
							<bean:message key='<%= String.valueOf(wizardTitleKey)%>'/>
						</c:set>
					</c:when>
					<c:otherwise>
						<c:set var="title" value=""/>
					</c:otherwise>
				</c:choose>
				<div id="navmenutitle">
					<c:out value="${title}"/>
				</div>
				
				<c:set var="wizardSubtitle" value="${requestScope['WIZARD_SUBTITLE']}"/>
				<c:set var="wizardSubtitleKey" value="${requestScope['WIZARD_SUBTITLE_KEY']}"/>
				<c:choose>
					<c:when test="${!empty wizardSubtitle}">
						<c:set var="subtitle" value="${wizardSubtitle}"/>
					</c:when>
					<c:when test="${!empty wizardSubtitleKey}">
						<bean:define id="wizardSubtitleKey" name="wizardSubtitleKey"/>
						<c:set var="subtitle">
							<bean:message key='<%= String.valueOf(wizardSubtitleKey)%>'/>
						</c:set>
					</c:when>
					<c:otherwise>
						<c:set var="subtitle" value="&nbsp;"/>
					</c:otherwise>
				</c:choose>
				<div id="navSubMenuTitle">
					<c:out value="${subtitle}"/>
				</div>
				<div id="navsubmenu">
					<c:set var="buttonBar" value="${requestScope['WIZARD_BUTTONBAR']}"/>
					<c:if test="${!empty buttonBar}">
					<ul class="actionsMenuList">
						<c:forEach var="button" items="${buttonBar}">
						<c:set var="titleKey" value="${button.titleKey}"/>
						<bean:define id="titleKey" name="titleKey" type="java.lang.String"/>
						<c:choose>
							<c:when test="${button.active}">
								<li>
									<a href='<c:out value="${button.url}" escapeXml="false"/>'><nobr><bean:message key='<%=titleKey%>'/></nobr></a>
								</li>
							</c:when>
							<c:otherwise>
								<li style="background-color: #ddd; color: #aaa; cursor: default;">
									<nobr><bean:message key='<%=titleKey%>'/></nobr>
								</li>
							</c:otherwise>
						</c:choose>
						</c:forEach>
					</ul>
					</c:if>
				</div>
				<div id="bodycontainer">
					<tiles:insert attribute="body" ignore="true" flush="false"/>
				</div>
			</div>
		</div>
		<ispac:layer/>
		<div id="dialogworkframe" class="roundTop" style="display:none;z-index:1024">
		  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
		<%--     <tr>
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