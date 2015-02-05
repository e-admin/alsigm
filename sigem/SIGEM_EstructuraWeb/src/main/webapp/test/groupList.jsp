<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>



<html:html>






<html:form action="/groupList">

<logic:iterate name="gruposBean" id="grupo" property="grupos" scope="request">

  <html:multibox property="selectedItem">
   <bean:write name="grupo" property="id"/> 
  </html:multibox> 
      <bean:write name="grupo" property="nombre"/> 
     
</logic:iterate>

<html:hidden property="submitted" value="true"/>
<html:submit>Enviar</html:submit>
</html:form>


</html:html>
