<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html:html>
<html:form action="/testAction">

Texto: <html:text property="nombre"></html:text>
<html:submit>Enviar</html:submit>
</html:form>


</html:html>
