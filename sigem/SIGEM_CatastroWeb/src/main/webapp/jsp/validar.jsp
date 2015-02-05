<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
	<head></head>
	<body>
		<form id="BusquedaForm" action='<html:rewrite page="/validarReferenciaCatastral.do"/>' method="post">
			<table>
				<tr>
					<td>Referencia Catastral</td>
					<td><input type="text" name="referenciaCatastral" id="referenciaCatastral" value="0470607UN9107S" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" class="ok" value="validar" id="validar" name="validar"/>
					</td>
				</tr>
				<tr>
					<%=("true".equals((String)request.getAttribute("valido")))?"VALIDO":((request.getAttribute("valido")!=null)?"NO VALIDO":"")%>
				</tr>
			</table>
		</form>
		<%if (request.getAttribute("plano") != null) {%>
		<iframe width="550px" height="550px" src='<%=request.getAttribute("plano")%>'>
		</iframe>
		<%}%>
	</body>
</html>