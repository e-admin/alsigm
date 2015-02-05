<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<c:set var="percent" value="${sessionScope[appConstants.common.PROGRESSBAR_PERCENT_KEY]}"/> 
<c:set var="widthiFrame" value="188"/>
<c:if test="${percent==null}">
	<c:set var="percent" value="0"/>
</c:if>
<c:if test="${percent!=null}">
	<c:set var="widthProgressBar" value="${(percent/100)*(widthiFrame-2)}"/>
</c:if>

<c:url var="progressBarURL" value="/action/progressBarRefresher" scope="request">
	<c:param name="method" value="updateProgressBar"/>
</c:url>
<script>
	function resetProgressBar(){
		var progressBar=document.getElementById("progressBar");
		if (progressBar){
			progressBar.style.width=0;
		}
	}
	function actualizarPercent(){
		var percent = '<c:out value="${percent}"/>';
		if(percent!=null && percent >= 100){
			window.parent.endGeneracion();
			resetProgressBar();			
		}else{
			window.location='<c:out value="${progressBarURL}" escapeXml="false"/>';
		}
	}
</script>

<body style="margin:0" padding="0" >
	<table id="tabla" bgcolor="#EFEFEF" width="100%" height="100%" 
		style="border-color:#C7C9D3;border-top-style:solid;border-left-style:solid;border-right-style:solid;border-bottom-style:solid;border-width:1px" 
		margin="0" cellpadding="0" cellspacing="0">
	<tr><td>
		<div style="width:<c:out value="${widthProgressBar}"/>px;height:8px;margin:0;padding:0;background-color:#E5E5ED" id="progressBar">&nbsp;</div>
	</td></tr>
	</table>
</body>
<script>
	setTimeout("actualizarPercent()",2000);
</script>
