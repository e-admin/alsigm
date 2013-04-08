<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<ul>
<logic:iterate name="labelValueBeanList" id="myBean" type="org.apache.struts.util.LabelValueBean">
	<logic:equal name="myBean" property="value" value="null">
		<li><div><i><bean:write name="myBean" property="label"/></i></div></li> 
	</logic:equal>
	<logic:notEqual name="myBean" property="value" value="null">
  		<li><div class="selectme"><bean:write name="myBean" property="label"/></div></li>    
  	</logic:notEqual>
</logic:iterate>
</ul>