<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<div id="BreadCrumbs">
	<%
		boolean sw = false;
	%>
	<logic:iterate name="BreadCrumbs" id="breadCrumb" type="ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumb">
		<%
			if(sw){
		%>
			&nbsp;>&nbsp;	
		<%
			}
			sw = true;
		%>
		<bean:write name="breadCrumb" property="title"/>
		       	<a href='<bean:write name="breadCrumb" property="URL"/>'>
				<bean:write name="breadCrumb" property="name"/>
	  		</a>
	</logic:iterate>
</div>