<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/ajaxtags.tld" prefix="ajax" %>


<div id="porletArea">
	<ajax:portlet
	  source="portlet_1"
	  baseUrl='<%=request.getContextPath() + "/modalCTTasksList.do"%>'
  	  title="Tr&aacute;mites asociados"
	  classNamePrefix="portlet"
	  />
</div>