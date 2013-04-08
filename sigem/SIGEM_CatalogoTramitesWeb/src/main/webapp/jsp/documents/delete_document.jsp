<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<bean:define id="id" name="deletedID" type="java.lang.String" />

<html>

    <script language="javascript">
    
        parent.doneDelete("<%=id%>");
    
    </script>
    
</html>