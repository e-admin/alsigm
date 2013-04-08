<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Búsqueda</title>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="scripts/sorttable.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
<ispac:javascriptLanguage/>
 
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
	
	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
	<![endif]-->

</head>

<body>
<div id="move">


	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="documentStage.create.title"/></h4>
				<div class="acciones_ficha"><a href="#" id="btnCancel"
					onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe />'
					class="btnCancel"><bean:message key="common.message.close" /></a>
				</div><%--fin acciones ficha --%>
		</div><%--fin titulo ficha --%>
	</div><%--fin encabezado ficha --%>

		<table cellpadding="0" cellspacing="1" border="0" class="boxGris" width="99%" style="margin-top:6px; margin-left:4px">
		
			<tr>
				<td width="100%" class="blank">
				
						<c:url value="selectTemplateDocumentType.do" var="_linkFromTemplate">
							<c:param name="field" value="documentTypeId"/>
							<c:param name="documentTypeId" value="${param.documentId}"/>
							<c:param name="new" value="true"/>
						</c:url>
		
						<c:url value="attachFile.do" var="_linkAttach">
							<c:param name="documentTypeId" value="${param.documentId}"/>
						</c:url>
						<c:url value="scanFiles.do" var="_linkScan">
							<c:param name="documentTypeId" value="${param.documentId}"/>
						</c:url>
						<c:url value="uploadRepoFiles.do" var="_linkFromRepository">
							<c:param name="documentTypeId" value="${param.documentId}"/>
						</c:url>
		
						<table border="0" cellspacing="2" cellpadding="2" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="14px"/></td>
							</tr>
							<tr>
								<td width="100%" style="text-align:center">
									<a class="tdlink" href='<c:out value="${_linkFromTemplate}"/>'><bean:message key="documentStage.generate.from.template"/></a> | 
									<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
									<a class="tdlink" href='<c:out value="${_linkAttach}"/>'><bean:message key="documentStage.generate.attach"/></a> | 
									<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
									<a class="tdlink" href='<c:out value="${_linkScan}"/>'><bean:message key="documentStage.generate.scan"/></a>  | 
									<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
									<a class="tdlink" href='<c:out value="${_linkFromRepository}"/>'><bean:message key="documentStage.generate.from.repository"/></a>
								</td>
							</tr>
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="14px"/></td>
							</tr>
						</table>
		
				</td>
			</tr>
		</table>
	</div>
</div>
<%--
<table border="0" width="100%">
	<tr>
		<td align="center">
				<c:url value="selectTemplateDocumentType.do" var="_linkFromTemplate">
					<c:param name="field" value="documentTypeId"/>
					<c:param name="documentTypeId" value="${param.documentId}"/>
				</c:url>

				<c:url value="attachFile.do" var="_linkAttach">
					<c:param name="documentTypeId" value="${param.documentId}"/>
				</c:url>
				<c:url value="scanFiles.do" var="_linkScan">
					<c:param name="documentTypeId" value="${param.documentId}"/>
				</c:url>
				<a class="tdlink" href='<c:out value="${_linkFromTemplate}"/>'><bean:message key="generate.documentStage.from.template"/></a> | 
				<a class="tdlink" href='<c:out value="${_linkAttach}"/>'><bean:message key="generate.documentStage.attach"/></a> | 
				<a class="tdlink" href='<c:out value="${_linkScan}"/>'><bean:message key="generate.documentStage.scan"/></a>
		</td>
	</tr>
</table>
--%>

</body>

</html>

<SCRIPT>
	positionMiddleScreen('move');
	$(document).ready(function(){
		$("#move").draggable();
	});
</SCRIPT>