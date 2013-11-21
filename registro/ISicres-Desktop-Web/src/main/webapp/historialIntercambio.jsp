<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<script language="javascript">
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
		</script>

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css">
		<![endif]-->

		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css">
		<![endif]-->

		<!--[if lte IE 6]>
			<script type="text/javascript" src="./scripts/iepngfix_tilebg.js"></script>
		<![endif]-->
		<link rel="stylesheet" type="text/css" href="./css/table.css"/>
		<link rel="stylesheet" type="text/css" href="./css/global.css"/>
		<link rel="stylesheet" type="text/css" href="./css/font.css" />

		<link rel="stylesheet" href="./css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css" media="screen" />

		<link rel="stylesheet" href="./css/terceros/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen" />

		<script type="text/javascript" src="scripts/jquery-1.6.2.min.js"></script>

		<script type="text/javascript" src="scripts/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="scripts/terceros/jquery.fancybox-1.3.4.pack.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery.blockUI.js"></script>

		<script type="text/javascript" language="javascript" src="./scripts/global.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/frmdata.js"></script>
		<script type="text/javascript" src="./scripts/terceros/jquery.fancybox-1.3.4.pack.js"></script>


		<script type="text/javascript">
			document.title = top.GetIdsLan("IDS_TIT_HIST_INTERCAMBIO_REG");

			//expande los datos del elemento pasado como parametro
			function Expand(numRow)
			{
				var detail = document.getElementById("detalle"+numRow);
				var img = document.getElementById("img"+numRow);

				if(detail){
					if(detail.style.display != 'none'){
						detail.style.display = 'none';
						img.src = "./images/datplus.gif";
					}else{
						detail.style.display = '';
						img.src = "./images/datminus.gif";
					}
				}
			}

			//en caso de error redirecciona a la informacion del registro ademas muestra un alert con el error
			function showError(msg){
				alert(msg);
				top.Main.Folder.FolderData.FolderFormTree.ReLoad();
			}

		</script>
		<style type="text/css">
			a:active, a:visited {border-style:none;
				outline:none;
				ie-dummy: expression(this.hideFocus=true);
				}
			a img{border-style:none;
			outline:none;}
		</style>
	</head>
	<body onload="ActivateFrmtMenu();">
		<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
		<c:if test="${ message == null }">
		    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
		</c:if>

		<c:if test="${requestScope.msg!=null}">
			<div style="color:blue;">
				<c:out value="${requestScope.msg}" ></c:out>
			</div>
		</c:if>

		<c:if test="${requestScope.error!=null}">
			<script type="text/javascript">showError('<c:out value="${requestScope.error}"/>');</script>
		</c:if>

		<!--  historial de entrada -->

		<c:if test="${not empty historialIntercambioEntrada}">
			<div id="divHistorialIntercambioRegEntrada">
				<c:forEach items="${historialIntercambioEntrada}" var="infoEstado" varStatus="status">
					<table id="historial" width="95%" class="Style2" cellspacing="0" cellpadding="2" align="center">
					<thead>
						<tr>
							<td colspan="5" class="historialIntercambio"><fmt:message key="intercambioRegistral.historial.entrada"/></td>
						</tr>
						<tr class="Style1"  valign="middle" tabindex="-1">
							<td align="left"><fmt:message key="intercambioRegistral.tabla.fechaIntercambio" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.estado" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.fechaEstado" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.usuario" /></td>
							<td align="center"><fmt:message key="intercambioRegistral.tabla.masInfo"/></td>
							<td align="center"><fmt:message key="intercambioRegistral.tabla.trazas"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="Style2" height="1">
							<td colspan="5"></td>
						</tr>

						<tr class="Style3" align="left" valign="middle" tabindex="-1">
							<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${infoEstado.fechaIntercambio}" /></td>
							<td><c:out value="${infoEstado.estado.name}"/></td>
							<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${infoEstado.fechaEstado}" /></td>
							<td><c:out value="${infoEstado.username}"/></td>
							<td align="center">
									<a class="linkInfoSolicitudIntercambio" href="MostrarIntercambioRegistral.do?idIntercambio=<c:out value='${infoEstado.idIntercambioInterno}'/>">
										<img src="./images/information.png" alt="<fmt:message key="intercambioRegistral.tabla.masInfo"/>"/>
									</a>
							</td>
							<td align="center">
									<a class="linkInfoSolicitudIntercambio" href="MostrarTrazasHistorialIntercambioRegistral.do?idIntercambio=<c:out value='${infoEstado.idIntercambioInterno}'/>">
										<img src="./images/legend.gif" alt="<fmt:message key="intercambioRegistral.tabla.trazas"/>"/>
									</a>
							</td>
						</tr>

						<tr class="Style2" height="1">
							<td colspan="5"></td>
						</tr>

					</tbody>
				</table>


				</c:forEach>

				<table class="Style3" width="95%" align="center">
				     <tr class="Style3" height="1" valign="middle" tabIndex="-1">
				        <td></td>
				     </tr>
				</table>
			</div>

			<hr width="95%"/>
		</c:if>




		<!--  historial de salida -->
		<c:if test="${not empty historialIntercambioSalida}">
			<div id="divHistorialIntercambioRegSalida">
				<table id="historial" width="95%" class="Style2" cellspacing="0" cellpadding="2" align="center">
					<thead>
						<tr>
							<td colspan="6" class="historialIntercambio"><fmt:message key="intercambioRegistral.historial.salida"/></td>
						</tr>
						<tr class="Style1" valign="middle" tabindex="-1">
							<td align="left"><fmt:message key="intercambioRegistral.tabla.fechaIntercambio" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.oficina" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.tipo.origen" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.destino.entidadRegistral" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.destino.unidadTramitacion" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.estado" /></td>
							<td align="left"><fmt:message key="intercambioRegistral.tabla.fechaEstado" /></td>
							<td align="center"><fmt:message key="intercambioRegistral.tabla.masInfo"/></td>
							<td align="center"><fmt:message key="intercambioRegistral.tabla.trazas"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="Style2" height="1">
							<td colspan="8"></td>
						</tr>
						<c:forEach items="${historialIntercambioSalida}" var="infoEstado" varStatus="status">
							<tr class="Style3" align="left" valign="middle" tabindex="-1">
								<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${infoEstado.fechaIntercambio}" /></td>
								<td><c:out value="${infoEstado.nombreOfic}"/></td>
								<td><fmt:message key="intercambioRegistral.type.origen.${infoEstado.tipoOrigen}"/></td>
								<td><c:out value="${infoEstado.codeEntity}"/> - <c:out value="${infoEstado.nameEntity}"/></td>
								<td><c:out value="${infoEstado.codeTramunit}"/> - <c:out value="${infoEstado.nameTramunit}"/></td>
								<!--
								<td><c:out value="${infoEstado.codeEntity}"/> <c:out value="${infoEstado.nameEntity}"/></td>
								<td><c:out value="${infoEstado.codeTramunit}"/> - <c:out value="${infoEstado.nameTramunit}"/></td>
								<td><c:out value="${infoEstado.codeTramunit}"/> <c:out value="${infoEstado.nameTramunit}"/></td>
								 -->
								<td><c:out value="${infoEstado.estado.name}"/></td>
								<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${infoEstado.fechaEstado}" /></td>
								<td align="center">
									<a class="linkInfoSolicitudIntercambio" href="MostrarIntercambioRegistral.do?idIntercambio=<c:out value='${infoEstado.idIntercambioInterno}'/>">
										<img src="./images/information.png" alt="<fmt:message key="intercambioRegistral.tabla.masInfo"/>"/>
									</a>
								</td>
								<td align="center">
									<a class="linkInfoSolicitudIntercambio" href="MostrarTrazasHistorialIntercambioRegistral.do?idIntercambio=<c:out value='${infoEstado.idIntercambioInterno}'/>">
										<img src="./images/legend.gif" alt="<fmt:message key="intercambioRegistral.tabla.trazas"/>"/>
									</a>
								</td>
							</tr>



						<tr class="Style2" height="1">
							<td colspan="5"></td>
						</tr>

						</c:forEach>
					</tbody>
				</table>
				<table class="Style3" width="95%" align="center">
				     <tr class="Style3" height="1" valign="middle" tabIndex="-1">
				        <td></td>
				     </tr>
				</table>
			</div>
		</c:if>
	</body>
<script type="text/javascript">
	desbloqueoDePantallaRegistro();
	jQuery("a.linkInfoSolicitudIntercambio").fancybox({
	'hideOnContentClick': true
	});
</script>
</html>