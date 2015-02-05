<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<table border="0" cellpadding="0" cellspacing="0" width="100%">
 <jsp:include page="./headerInt.jsp" />
  <tr>
    <td colspan="2" width="100%">
	<div id="usuario">
		<div id="barra_usuario">
			<p class="miga">
		  		<c:choose>
		<%--
		  		<c:when test="${!empty requestScope[appConstants.actions.EXPEDIENT_CLOSED]}"> 
			        <td id="navigation" width="80%" class="menu_red" valign="middle" align="center">
			  			<b><bean:message key="info.message.expedient.closed"/></b>
			  		</td> 
		  		</c:when>
		--%>
		 		<c:when test="${!empty requestScope[appConstants.actions.READONLYSTATE]}">
			  		<logic:present name="ContextHeaderList" scope="request">
				  		<logic:iterate name="ContextHeaderList" id="contextHeader">
				  			<logic:equal name="contextHeader" property="property(CURRENTSTATE)" value="1">
								 > <b><bean:write name="contextHeader" property="property(NAME)"/></b>
				  			</logic:equal>
				  			<logic:equal name="contextHeader" property="property(CURRENTSTATE)" value="2">
				  			 > <bean:write name="contextHeader" property="property(NAME)"/>
				  			</logic:equal>
				  			<logic:equal name="contextHeader" property="property(CURRENTSTATE)" value="0">
				  				<bean:define id="url" name="contextHeader" property="property(LINK)" type="java.lang.String"/>
				  				<bean:define id="queryString" name="contextHeader" property="property(QUERYSTRING)" type="java.lang.String"/>
								 > <a class="navigation" href='<html:rewrite forward='<%= url %>'/><%= queryString%>'>
								   <bean:write name="contextHeader" property="property(NAME)"/>
								 </a>
				  			</logic:equal>
				  		</logic:iterate>
				  	</logic:present>
					<p class="bloqueo">
			          	    	<c:set var="reasonKey" value="message.readonlyReason.${requestScope[appConstants.actions.READONLYSTATE]}"/>
			          	    	<bean:define id="reasonKey" name="reasonKey" type="java.lang.String"/>
						<bean:message key='<%=reasonKey%>' />
			  		</p> 
		 		</c:when>
		  		<c:otherwise>
					  		<logic:present name="ContextHeaderList" scope="request">
					  			<logic:iterate name="ContextHeaderList" id="contextHeader">
					  				<logic:equal name="contextHeader" property="property(CURRENTSTATE)" value="1">
										 > <b><bean:write name="contextHeader" property="property(NAME)"/></b>
					  				</logic:equal>
					  				<logic:equal name="contextHeader" property="property(CURRENTSTATE)" value="2">
					  				 > <bean:write name="contextHeader" property="property(NAME)"/>
					  				</logic:equal>
					  				<logic:equal name="contextHeader" property="property(CURRENTSTATE)" value="0">
					  					<bean:define id="url" name="contextHeader" property="property(LINK)" type="java.lang.String"/>
					  					<bean:define id="queryString" name="contextHeader" property="property(QUERYSTRING)" type="java.lang.String"/>
										 > <a class="navigation" href='<html:rewrite forward='<%= url %>'/><%= queryString%>'>
										   <bean:write name="contextHeader" property="property(NAME)"/>
										 </a>
					  				</logic:equal>
					  			</logic:iterate>
					  		</logic:present>
		  		</c:otherwise>
		  		</c:choose>
			</p>
			<p class="usuario">
		            <bean:write name="User"/>
			</p>
		</div>
	</div>
    </td>
  </tr>
</table>
