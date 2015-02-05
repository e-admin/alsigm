<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mapping" mapping="/navegadorRegistroConsultaSalaAction" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="listaDescendientes" value="${sessionScope[appConstants.salas.LISTA_DESCENDIENTES_KEY]}"/>
<c:set var="pathPadre" value="${sessionScope[appConstants.salas.PATH_PADRE_KEY]}"/>
<c:set var="nombrePadre" value="${sessionScope[appConstants.salas.NOMBRE_PADRE_KEY]}"/>
<c:set var="esUltimoNivel" value="${sessionScope[appConstants.salas.IS_LAST_LEVEL_KEY]}"/>

<head>
	<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/x_core.js" type="text/JavaScript"></script>
	<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/x_event.js" type="text/JavaScript"></script>
	<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
	<script language="JavaScript1.2" type="text/JavaScript">
		function submitUp(){
			document.forms[0].method.value = 'getParent';
			workingDiv();
			document.forms[0].submit();
		}

		function submitDown(seleccionado){
			document.forms[0].method.value = 'getChilds';
			document.getElementById("idSeleccionado").value = seleccionado;
			workingDiv();
			document.forms[0].submit();
		}

		function getValorSeleccionado(){
			var seleccion = document.getElementById("idSeleccionado").value;
			return seleccion;
		}

		function getTipoSeleccionado(){
			return document.getElementById("tipoSeleccionado").value;
		}

		function setValorSeleccionado(valor){
			document.getElementById("idSeleccionado").value = valor;
		}

		function workingDiv(){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
		}
	</script>
	<link type="text/css"  rel="stylesheet" href="../pages/css/displaytag.css" />
	<link type="text/css"  rel="stylesheet" href="../pages/css/archivoGeneral.css" />
</head>
<body topmargin="0" bottommargin="0">
	<html:form action="/navegadorRegistroConsultaSalaAction" styleId="formulario" >
		<input type="hidden" name="method" id="method"/>
		<html:hidden property="idSeleccionado" styleId="idSeleccionado"/>
		<html:hidden property="tipoSeleccionado" styleId="tipoSeleccionado"/>

		<html:hidden property="idArchivo" styleId="idArchivo"/>
		<html:hidden property="equipoInformatico" styleId="equipoInformatico"/>

		<table cellpadding="2" width="100%" style="text-align:center;" >
			<tr>
				<td style="text-align:left;" class="etiquetaAzul11Bold" colspan="2">
					<bean:message key="archigest.archivo.deposito.situacionActual" />:
					<span class="etiquetaAzul11Normal">
						<c:choose>
							<c:when test="${not empty pathPadre}">
								<bean:message key="archigest.archivo.salas.arbol.nombre"/><c:out value="${pathPadre}"/>
								<c:if test="${not empty nombrePadre}">
									&nbsp;
									<a href="javascript:submitUp()">
										<html:img page="/pages/images/folder-up.gif" border="0" altKey="archigest.archivo.subir" titleKey="archigest.archivo.subir" styleClass="imgTextBottom" />
									</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.salas.arbol.nombre"/>
							</c:otherwise>
						</c:choose>
					</span>
				</td>
			</tr>
			<tr>
				<td width="95%">
					<display:table name="pageScope.listaDescendientes"
						id="descendiente"
						style="width:99%;margin-left:auto;margin-right:auto"
						>

						<display:column style="width:20px">
							<c:choose>
								<c:when test="${descendiente.numHijos > 0 or descendiente.estado=='L'}">
									<input type="radio" id="idSeleccionado" name="idSeleccionado" value="<bean:write name="descendiente" property="id"/>" onclick="setValorSeleccionado(this.value)">
								</c:when>
								<c:otherwise>
									<html:img page="/pages/images/pixel.gif" width="19" height="19" border="0" />
								</c:otherwise>
							</c:choose>
						</display:column>

						<display:column titleKey="archigest.archivo.nombre">
							<c:choose>
								<c:when test="${!esUltimoNivel and descendiente.numHijos > 0}">
									<a class="tdlink" href='javascript:submitDown("<c:out value="${descendiente.id}"/>")' >
										<c:out value="${descendiente.nombre}"/>
									</a>
								</c:when>
								<c:otherwise>
									<c:if test="${esUltimoNivel}"><bean:message key="archigest.archivo.salas.mesa"/> </c:if>
									<c:out value="${descendiente.nombre}"/>
								</c:otherwise>
							</c:choose>
						</display:column>

						<c:if test="${empty esUltimoNivel}">
							<display:column style="width:30px" titleKey="archigest.archivo.deposito.libres">
								<c:out value="${descendiente.numHijos}"/>
							</display:column>
						</c:if>
					</display:table>
				</td>
			</tr>
		</table>
		<script language="javascript">
			if (window.top.hideWorkingDiv) {
				window.top.hideWorkingDiv();
			}
		</script>
	</html:form>
</body>