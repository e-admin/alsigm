<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<script language="javascript">
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
				function abrirDocumento(idPagina, nombre)
			{

			window.open(top.g_URL + "/FileDownload?SessionPId=" + top.g_SessionPId
			+ "&BookId=" + top.g_ArchiveId.toString()
			+ "&RegId=" + top.g_FolderId.toString()
			+ "&PageId=" + idPagina
			+ "&FileName=" + nombre
            		+ "&topURL=" + top.g_URL,
			"FolderPageFile","location=no",true);
			}
		</script>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css"/>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css"/>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css" />


		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/colors.js"></script>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/dtrlist.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/frmdata.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/frmint.js"></SCRIPT>
</head>

<body>

	<div id="documentoElectronicoPropiedades">
		<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
		<h2><fmt:setBundle  basename="resources.ISicresDocumentoElectronico" scope="application"/></h2>


		<c:choose>
		<c:when test="${ documentoElectronico!= null }">

			<div >
			    <h3>
				<fmt:message key="documentoelectronico.label.propiedades"/>
				</h3>
				<p>
		    	<fmt:message key="documentoelectronico.label.nombre" />: ${documentoElectronico.name}
		    	</p>
		    	<p>
		    	<fmt:message key="documentoelectronico.label.tipoDocumento" />: <fmt:message key="documentoelectronico.tipoDocumentoAnexo.${documentoElectronico.tipoDocumentoAnexo.value}" />
		    	</p>
		    	<p>
		    	<fmt:message key="documentoelectronico.label.tipoValidez" />: <fmt:message key="documentoelectronico.tipoValidez.${documentoElectronico.tipoValidez.value}" />
		    	</p>
		    	<p>
		    	<fmt:message key="documentoelectronico.label.comentarios" />: ${documentoElectronico.comentario}
		    	</p>

			<a href="#" onclick="javascript:abrirDocumento('${documentoElectronico.id.idPagina}','${documentoElectronico.name}');">
				<fmt:message key="documentoelectronico.label.descargar" />
			</a>
			(<fmt:message key="documentoelectronico.label.hash" />: ${documentoElectronico.datosFirma.hash})
			<!--
			window.open(top.g_URL + "/getpage.jsp?SessionPId=" + top.g_SessionPId+ "&BookId=" + ${documentoElectronico.id.idLibro} + "&RegId=" + ${documentoElectronico.id.idRegistro}+ "&PageId=" + ${documentoElectronico.id.idPagina}+ "&FileName=" + ${documentoElectronico.name},"FolderPageFile","location=no",true);

			${documentoElectronico.id.id} -${documentoElectronico.id.idLibro} - ${documentoElectronico.id.idRegistro} - ${documentoElectronico.id.idPagina}
			-->

			</div>

			<c:if test="${not empty documentoElectronico.firmas}">
			<br />
			<div id="firmasExternas">

				<h3><fmt:message key="documentoelectronico.label.firmasExternas" /></h3>

					<c:forEach items="${documentoElectronico.firmas}" var="firma">

					<a href="#" onclick="javascript:abrirDocumento('${firma.id.idPagina}','${firma.name}');">
					${firma.name}
					</a>

					</c:forEach>

			</div>
			</c:if>
			<c:if test="${ documentoFirmado != null }">
			<br />
			<div id="esFirmaDe">
				<h3><fmt:message key="documentoelectronico.label.esFirmaDe" /></h3>
				 <a href="#" onclick="javascript:abrirDocumento('${documentoFirmado.id.idPagina}','${documentoFirmado.name}');">
					${documentoFirmado.name}
				</a>
			</div>

			</c:if>

		</c:when>
		<c:otherwise>
		 <script language="javascript">
            alert(top.GetIdsLan("IDS_PAGINA_NO_DOCELECTRONICO"));
            top.Main.Folder.FolderData.FolderFormTree.OpenPageDataFromDtr();
         </script>
         </c:otherwise>
         </c:choose>
	</div>
</body>



</html>