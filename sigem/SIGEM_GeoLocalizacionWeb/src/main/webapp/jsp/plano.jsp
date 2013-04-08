<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
Integer ajuste = (Integer)request.getAttribute(Defs.PARAMETRO_AJUSTE_ZOOM);
int ajusteZoom = (ajuste == null) ? 0 : ajuste.intValue();
%>

<%@page import="ieci.tecdoc.sgm.geolocalizacion.utils.Defs"%>
<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
		
		<script language="Javascript">
			parent.document.getElementById('visorMapa').style.visibility="visible";
			parent.document.getElementById('esperaMapa').style.visibility="hidden";
			
			function cerrar(){
				parent.document.getElementById('idPortal').disabled = false;
				parent.document.getElementById('tdPortales').style.visibility = 'visible';
				parent.document.getElementById('fondoMapa').style.visibility="hidden";
				parent.document.getElementById('visorMapa').style.visibility="hidden";
			}
			
			function zoom(tipo){
				var cant = <%=ajusteZoom%>;
				if (tipo == "mas")
					cant = cant - 200;
				else if(tipo == "menos")
					cant = cant + 200;
				document.location.href = 'obtenerMapa.do?<%=Defs.PARAMETRO_ID_VIA%>=<bean:write name="<%=Defs.PARAMETRO_ID_VIA%>" />&<%=Defs.PARAMETRO_NUMERO_PORTAL%>=<bean:write name="<%=Defs.PARAMETRO_NUMERO_PORTAL%>" />&<%=Defs.PARAMETRO_AJUSTE_ZOOM%>=' + cant;
			}
		</script>
	</head>
	<body>
		<div id="cerrar" name="cerrar" style="align:right; width: 100%; height: 20px; top: 0px; left: 0px; background-color: #4B7AA5; filter: alpha(opacity=100); position: absolute; z-index:10; visibility: visible;">
			<table width="100%" height="100%" cellpadding="0px" cellspacing="0px" border="0px">
				<tr>
					<td align="right">
						<img src="img/ico_close.jpg" style="cursor: pointer" alt="" onclick="javascript:cerrar();"/>
					</td>
				</tr>
			</table>
		</div>
		<table width="99%" height="100%" cellpadding="0px" cellspacing="0px" border="0px">
			<logic:present name="<%=Defs.PARAMETRO_URL_PLANO%>">
				<tr height="35px">
					<td align="right" valign="bottom">
						<img src="img/ico_lupa_mas.jpg" style="cursor: pointer" alt="<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.acercar"/>" onclick="javascript:zoom('mas');"/>
						&nbsp;&nbsp;
						<img src="img/ico_lupa_menos.jpg" style="cursor: pointer" alt="<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.alejar"/>" onclick="javascript:zoom('menos');"/>
					<td>
				</tr>
				<tr>
					<td align="center" valign="top">
						<img src="<%=request.getAttribute(Defs.PARAMETRO_URL_PLANO)%>" />
					</td>
				</tr>
			</logic:present>
			<logic:notPresent name="<%=Defs.PARAMETRO_URL_PLANO%>">
				<tr height="100%" valign="middle">
					<td class="gr" align="center">
						<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.planoNotFound"/>
					</td>
				</tr>
			</logic:notPresent>
		</table>
		
		<script language="Javascript">
			if (isIE)
				document.getElementById("cerrar").style.width = "107%";
		</script>
	</body>
</html>