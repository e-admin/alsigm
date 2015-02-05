<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<html:html locale="true">
<head>
	<title></title>
	<ieci:baseInvesDoc/>
	<script src="include/js/modalWindow.js" type="text/javascript"></script>
	<script type="text/javascript">
	
		var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
		var path = appBase + "/test/popUp.jsp";
		var width = 400;
		var height = 300;
		function openWindows()
		{
			ShowWindow(path,width,height);
			// fnOpen();
		}
		
		function fnRandom(iModifier){
		   return parseInt(Math.random()*iModifier);
		}
		function fnSetValues(){
		   var iHeight=oForm.oHeight.options[oForm.oHeight.selectedIndex].text;
		   var iWidth= oForm.oWidth.options[oForm.oWidth.selectedIndex].text;   
		   
		   if(iHeight.indexOf("Random")>-1){
		      iHeight=fnRandom(document.body.clientHeight);
		   }
		   if(iWidth.indexOf("Random")>-1){
		      iWidth=fnRandom(document.body.clientHeight);
		   }
		   
		   var sFeatures="dialogHeight: " + iHeight + "px;" + "dialogWidth: " + iWidth + "px;"  ;
		   return sFeatures;
		}
		function fnOpen(){
		   var sFeatures=fnSetValues();
		   // window.showModalDialog("showModalDialog_target.htm", "", sFeatures);
		   window.showModalDialog(path, "", sFeatures);
		   
		}
	</script>
</head>
<body>
	<FORM NAME=oForm>
	<table>
		<tr>
			<td>Ancho</td>
			<td>Alto</td>
		</tr>
		<tr>
			<td>
				<SELECT NAME="oWidth">
				   <OPTION>-- Aleatorio --
				   <OPTION>150
				   <OPTION>200
				   <OPTION>250
				   <OPTION>300
				</SELECT>			
			</td>
			<td>
				<SELECT NAME="oHeight">
				   <OPTION>-- Aleatorio --
				   <OPTION>150
				   <OPTION>200
				   <OPTION>250
				   <OPTION>300
				</SELECT>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="pop up" onclick="openWindows();"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="javascript: openWindows();">Link </a>
			</td>
		</tr>
	</table>
	

	
	
</body>
</html:html>