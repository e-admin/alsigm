<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci" %>
<%@ page isErrorPage="true" autoFlush="true" %> 
<html:html>
<head>
<ieci:baseInvesDoc/>
	<title><bean:message key="message.common.title"/></title>
  <link rel="stylesheet" type="text/css" href="include/css/error.css"/>
</head>
<body>
<%-- 
<ieci:errors bean='<%=request.getAttribute("errorbean") %>'></ieci:errors>
--%>


<html-el:messages id="msg" message="true" bundle="${requestScope.bundle}">
	<script>
	window.alert("<bean:write name='msg'/>" );
	
	function getParent(obj) {	
		return obj.top;
	}
	/*
	if(parent.frames.length)
		top.location=document.location;
	*/
	
	if (window.opener){
		var obj = window.opener;
		var principal = getParent(obj);
		// principal.location.replace(document.location);
		window.close();
	}
	else
		history.back();
	</script>
</html-el:messages>



<%-- 
<html:messages id="msg" message="true" bundle="errors">
<script>
window.alert("<bean:write name='msg'/>");
this.history.back(-1);
</script>
</html:messages>
--%>

<%-- 
<logic:messagesPresent message="true">
	<ieci:messages id="message" message="true" bundle="errors">
		<script>
			alert("<c:out value="${message}" escapeXml="false"/>");
		   this.history.back(-1);
		</script>
	</ieci:messages>
</logic:messagesPresent>
--%>
</body>
<script>
/*
if (this.name == "edicion" || this.name == 'deptos' || this.name == 'properties' || this.name == ' grupos' ){
	window.top.document.getElementById('error').innerHTML=this.document.getElementsByTagName('body')[0].innerHTML;
	this.document.getElementById('errorDiv').style.visibility='hidden';
}
else if (this.name=="tree" || this.name=="busqueda" || this.name=="propiedades" || this.name=="edicion" ){
	window.top.document.getElementById('error').innerHTML=this.document.getElementsByTagName('body')[0].innerHTML;
	this.document.getElementById('errorDiv').style.visibility='hidden';
}



if ( this.name=="pop" || this.name.indexOf("Modal")!=-1 ){
   window.opener.top.document.getElementById('error').innerHTML=this.document.getElementsByTagName('body')[0].innerHTML;
   window.close();
}else if ( this.name=="menu" || this.name=="header" ){
   window.top.document.getElementById('error').innerHTML=window.top.document.getElementsByTagName('body')[0].innerHTML+this.document.getElementsByTagName('body')[0].innerHTML;
   this.history.back(-1);
}else if ( this.name=="data" ){
	window.top.document.getElementById('error').innerHTML=this.document.getElementsByTagName('body')[0].innerHTML;
	this.document.getElementById('errorDiv').style.visibility='hidden';
}
*/
</script>



</html:html>