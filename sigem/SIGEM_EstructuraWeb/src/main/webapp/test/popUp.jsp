<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<html:html locale="true">
<head>
	<title>InvesDoc</title>
	<ieci:baseInvesDoc/>
	<link rel="stylesheet" rev="stylesheet" href="include/css/tabs2.css" />
	<script src="include/js/docobj.js" type="text/javascript"></script>
	<script src="include/js/tabs.js" type="text/javascript"></script>
	<script type="text/javascript">
		window.name="popUP";
	</script>
</head>
<%-- 
<body>
	Mi popup
	<br>
	
</body>
--%>
<body onload="choosebox(1,9);">

	<div class="titulo" >Propiedades</div>

	<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,9)">
		<table summary="" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="tableft" height="17" width="7">
					<img src="include/images/dot.gif" alt="" border="0" height="17" width="7">
				</td>
				<td id="tabmiddle1">General</td>
				<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
			</tr>
		</table>
	</div>

	<div id="box1">
		<a href="javascript: alert('hola?');" target="popUP">Link </a>
		<BR>
		<input type="button" value="cerrar" onclick="window.close();"/>
	</div>

	<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,9)">
		<table summary="" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="tableft" height="17" width="7">
					<img src="include/images/dot.gif" alt="" border="0" height="17" width="7">
				</td>
				<td id="tabmiddle2">Campos</td>
				<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
			</tr>
		</table>
	</div>

	<div id="box2">
		Pestaña 2
		<BR>
		<input type="button" value="cerrar" onclick="window.close();"/>		
	</div>
	

</html:html>