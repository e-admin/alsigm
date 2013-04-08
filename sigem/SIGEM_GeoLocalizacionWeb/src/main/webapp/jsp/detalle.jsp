<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@page import="ieci.tecdoc.sgm.geolocalizacion.utils.Defs"%>
<%@page import="ieci.tecdoc.sgm.core.services.geolocalizacion.Via"%>
<%@page import="ieci.tecdoc.sgm.core.services.geolocalizacion.Portales"%>
<%@page import="ieci.tecdoc.sgm.core.services.geolocalizacion.Portal"%>
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
		</script>
	</head>
	<body>
		<div id="cerrar" name="cerrar" style="align:right; width: 100%; height: 20px; top: 0px; left: 0px; background-color: #4B7AA5; filter: alpha(opacity=100); position: absolute; z-index:10; visibility: visible;">
			<table width="100%" height="100%" cellpadding="0px" cellspacing="0px" border="0px">
				<tr>
					<td align="right">
						<img src="img/ico_close.jpg" style="cursor: pointer" alt='<bean:message key="ieci.tecdoc.sgm.geolocalizacion.boton.cerrar"/>' onclick="javascript:cerrar();"/>
					</td>
				</tr>
			</table>
		</div>
		<table width="99%" height="100%" cellpadding="0px" cellspacing="0px" border="0px">
			<logic:present name="<%=Defs.PARAMETRO_VIA%>">
				<tr><td colspan=2><br/></td></tr>
				<tr>
					<td colspan=2>
						<table style="border: 1px solid #4B7AA5; padding:3px" width="100%" cellpadding=0 cellspacing=0>
							<tr class="gr" bgcolor="#4B7AA5" height="20px" valign="middle">
								<td class="gr" style="color: white" width="100%" colspan=2>
									<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.detalleVia"/>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.idVia"/>:</b>
								</td>
								<td class="gr">
									<bean:write name="<%=Defs.PARAMETRO_VIA%>" property="idVia"/>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.tipoVia"/>:</b>
								</td>
								<td class="gr">
									<bean:write name="<%=Defs.PARAMETRO_VIA%>" property="tipoVia"/>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.nombreVia"/>:</b>
								</td>
								<td class="gr">
									<bean:write name="<%=Defs.PARAMETRO_VIA%>" property="nombreVia"/>	
								</td>
							</tr>
						</table>
					</td>
				</tr>				
				<%
				int idPortal = new Integer((String)request.getAttribute(Defs.PARAMETRO_ID_PORTAL)).intValue();
				Via via = (Via)request.getAttribute(Defs.PARAMETRO_VIA);
				Portales portales = via.getPortales();
				for(int i=0; i<portales.count(); i++){
					Portal portal = (Portal)portales.get(i);
					if (idPortal == portal.getIdPortal()) {
				%>
				<tr>
					<td colspan=2>
						<table style="border: 1px solid #4B7AA5; padding:3px" width="100%" cellpadding=0 cellspacing=0>
							<tr class="gr" bgcolor="#4B7AA5" height="20px" valign="middle">
								<td class="gr" style="color: white" width="100%" colspan=2>
									<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.detallePortal"/>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.idPortal"/>:</b>
								</td>
								<td class="gr">
									<%=portal.getIdPortal()%>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.tipoPortal"/>:</b>
								</td>
								<td class="gr">
									<%=portal.getTipoPortal()%>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.numeroPortal"/>:</b>
								</td>
								<td class="gr">
									<%=portal.getNumPortal()%>
								</td>
							</tr>
							<tr>
								<td class="gr" width="40%">
									<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.coordenadas"/>:</b>
								</td>
								<td class="gr">
									<%=portal.getCoords().getCoordX() + ", " + portal.getCoords().getCoordY()%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%	
						break;
					}
				}
				%>
				<tr><td colspan=2><br/></td></tr>
				<tr>
					<td class="gr" width="40%">
						<b><bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.fuente"/>:</b>
					</td>
					<td class="gr">
						<bean:write name="<%=Defs.PARAMETRO_VIA%>" property="fuente"/>	
					</td>
				</tr>
			</logic:present>
			<logic:notPresent name="<%=Defs.PARAMETRO_VIA%>">
				<tr height="100%" valign="middle">
					<td class="gr" align="center">
						<bean:message key="ieci.tecdoc.sgm.geolocalizacion.texto.detalleNotFound"/>
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