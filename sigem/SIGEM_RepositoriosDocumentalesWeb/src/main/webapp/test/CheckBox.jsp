<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
	<title></title>
<script language="javascript">
	function setDocOrMult(obj)
	{
		name1 = "fields.fldsList[0].doc";
		name2="miCheck";
		var elem = document.getElementsByName(name2);
		alert(elem[0].checked);
	}
</script>
</head>

<body>
	<input type="checkbox" name="fields.fldsList[0].doc" value="on" checked="checked" onclick="setDocOrMult(this);"> Documental <br>
	<input type="checkbox" name="miCheck" onclick="setDocOrMult(this);"> Otro
</body>
</html:html>