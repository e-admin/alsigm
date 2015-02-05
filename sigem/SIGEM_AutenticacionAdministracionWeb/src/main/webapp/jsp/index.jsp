<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>

<%@ page import="ieci.tecdoc.sgm.administracion.utils.Defs" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@page import="ieci.tecdoc.sgm.administracion.utils.Utilidades"%>
<html>
<head>
	<script type="text/javascript" language="javascript">

	   var errorCargaActive = false;

	   function GetUserDn()
	   {
	      var e;
	      var bPluginInstall = false;
	      var bCancelInstall = false;
	      var oActiveXLDAP = null;
	      var sUserDn = "";

	      while ( (!oActiveXLDAP) && (!bPluginInstall) )
	      {
	         try
	         {
	            oActiveXLDAP = new ActiveXObject("IDocLDAPClient.IDocLDAP");
	            bPluginInstall = true;
	         }
	         catch(e)
	         {
	            if (bCancelInstall)
	            {
	               alert("Debe instalar correctamente el ActiveX.");
	               bPluginInstall = true;
	               errorCargaActive = true;
	            }
	            else
	            {
	               bCancelInstall = true;
	               var URLPlugin;
				   URLPlugin = "./plugins/IDocLDAP.cab";

	               var strRet = window.showModalDialog("sso/install.htm?URLPlugin=" + URLPlugin + "&CLSID=13180912-3296-40DF-9335-88D3E00A2E53&VerPlugin=1,0,0,1&Title=Plugin LDAP" + "&BgText=Installing IdocLDAP ActiveX", "", "dialogHeight: 150px; dialogWidth: 150px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
	            }
	         }
	      }

	      if (oActiveXLDAP)
	      {
	         try
	         {
	            //sUserDn = oActiveXLDAP.GetUserDnEncode();
	            sUserDn = oActiveXLDAP.GetUserDn();
	         }
	         catch(e)
	         {
	            alert(e.message);
	            errorCargaActive = true;
	         }
	      }
	      return sUserDn;
	   }
	</script>

	<%
		String idEntidad = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
		String idAplicacion = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
		if (Utilidades.isNuloOVacio(idEntidad)) {
			idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = "";
			}
		}
		if (idAplicacion == null) idAplicacion = new String("");

		//session.setAttribute(ConstantesBackOffice.PARAMETRO_ID_APLICACION, idAplicacion);
		//session.setAttribute(ConstantesBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);

		boolean sso = false;
		String singleSignOn = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);
		if ((singleSignOn != null) && (singleSignOn.equalsIgnoreCase("true"))) {
			sso = true;
		}
	%>

	<script language="Javascript">

		function redirige() {

			var url;

			<% if (sso) { %>
				var sUserDn = GetUserDn();
				if(!errorCargaActive){
					url = '<%=request.getContextPath()%>/obtenerUsuarioSSO.do?<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD%>=<%=idEntidad%>&<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION%>=<%=idAplicacion%>&<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_SSO_USER_DN%>=' + sUserDn;
				}
			<%} else { %>
				url = '<%=request.getContextPath()%>/inicio.do?<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD%>=<%=idEntidad%>&<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION%>=<%=idAplicacion%>';
			<% } %>

			document.location.href = url;
		}

	</script>
</head>

<body onLoad="javascript:redirige()">
<p><bean:message key="cargando"/></p>
</body>
</html>
