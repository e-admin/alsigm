<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.catalogo_tramites.utils.Defs" %>

<html:html>

	<head>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>

		<% String mensaje = (String)request.getAttribute(Defs.MENSAJE_ERROR);%>

		<script language="Javascript">
			function fun() {
			    var hh, h=null;

			    if (isIE) {
			    	var mensaje = '<bean:message key="<%=mensaje%>"/>';
			    	var detalle = "<%=(String)request.getAttribute(Defs.MENSAJE_ERROR_DETALLE)%>";
				    var array = new Array(mensaje, detalle);
					h = window.showModalDialog("<%=request.getContextPath()%>/jsp/error_ventana.jsp", array, "dialogWidth:526px;dialogHeight:240px;resizable:no;status:no;scroll:no;");
			    } else {
			    	var left = (window.innerWidth * 2 / 3) - 261;
                	var top = (window.innerHeight * 2 / 3) - 111;
                	var propiedades = "left="+left+",top="+top+",width=522,height=222,dependent,modal";
					h = window.open("<%=request.getContextPath()%>/jsp/error_ventana.jsp?<%=Defs.MENSAJE_ERROR%>=<%=request.getAttribute(Defs.MENSAJE_ERROR)%>&<%=Defs.MENSAJE_ERROR_DETALLE%>=<%=request.getAttribute(Defs.MENSAJE_ERROR_DETALLE)%>", hh, propiedades);
					window.onfocus=function(){
						if(h && !h.closed) {
			    			h.focus();
			   			}
			       	}
			    	return false;
				}
			}
		</script>

		<script language="Javascript">

			if (parent.proc != 'undefined') {
				parent.proc.error();
			}

			var redireccion = "<%= (String)request.getAttribute(Defs.REDIRECCION) %>";
			if (redireccion != undefined && redireccion != null && redireccion != "" && redireccion != "null"){
				document.location.href = redireccion;
			}
			fun();
		</script>
	</head>

	<body>

    </body>
</html:html>