<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<bean:define id="document" name="document" type="ieci.tecdoc.sgm.core.services.catalogo.Documento"/>

<html>

    <script language="javascript">
    
        parent.doneLoad("<%=document.getId()%>", "<%=document.getDescription()%>", "<%=document.getExtension()%>", "<%=document.getSignatureHook()%>", "<%=document.getValidationHook()%>");
    
    </script>
    
</html>