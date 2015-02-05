<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />

<script language="JavaScript1.2" src="<c:url value="/js/x_core.js"/>" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<c:url value="/js/x_event.js" />" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<c:url value="/js/manejo_cajas.js" />"" type="text/JavaScript"></script>


<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.transferencias.organizarCajas"/>
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:guardarCambios(document.forms[0]);" >
						<html:img page="/pages/images/save.gif" border="0" altKey="archigest.archivo.guardar" titleKey="archigest.archivo.guardar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.guardar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   <TD>
					<c:url var="goBackURL" value="/action/gestionRelaciones">
						<c:param name="method" value="verrelacion" />
						<c:param name="idrelacionseleccionada" value="${bRelacion.id}" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${goBackURL}" escapeXml="false"/>'">
						<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
			   		</a>
			   </TD>
			 </TR>
			</TABLE>
		 </TR>
		</TABLE>
	</TD>
  </TR>
</TABLE>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores"><archivo:errors /></DIV>

<DIV class="cabecero_bloque_sin_height"> <%--cabecero primer bloque de datos --%>
			<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp;</div>

<table border="0" width="99%"><tr><td valign="top" width="40%">

<script language="JavaScript1.2" type="text/JavaScript">
	var usedImages = new Array();
	var imagesSrcs = new Array('<html:rewrite page="/pages/images/down.gif"/>', '<html:rewrite page="/pages/images/up.gif"/>', '<html:rewrite page="/pages/images/caja_bajar.gif" />', '<html:rewrite page="/pages/images/caja_subir.gif" />', '<html:rewrite page="/pages/images/caja_quitar.gif" />');

	function preloadImages(pageImages) {
		for (var i=0;i<pageImages.length;i++) {
			usedImages[usedImages.length] = new Image();
			usedImages[usedImages.length - 1].src = pageImages[i];
		}
	}

	// crear objeto PageImgsManager ... metodos addImage, loadImages, get(idImage) ... objeto SwapGroup
	preloadImages(imagesSrcs);
</script>


<div class="cabecero_cajas">
	<TABLE border="0" cellpadding=0 cellspacing=0 width="98%">
	  <TBODY>
	  <TR>
	    <TD class="etiquetaAzul12Bold" width="40%">
			<bean:message key="archigest.archivo.transferencias.cajasRel"/>
		</TD>
	    <TD width="60%" align="right">
		<c:set var="estadoAbierta"><c:out value="${appConstants.transferencias.estadoREntrega.ABIERTA.identificador}"/></c:set>
		<c:set var="estadoConErrores"><c:out value="${appConstants.transferencias.estadoREntrega.CON_ERRORES_COTEJO.identificador}"/></c:set>
		<TABLE cellpadding=0 cellspacing=0>
		  <TR>
			<TD>
				<c:if test="${bRelacion.estado == estadoAbierta || bRelacion.estado == estadoConErrores}">
					<c:choose>
					<c:when test="${bRelacion.conSignatura}">
					<a class="etiquetaAzul12Normal" href="javascript:crearCaja('<html:rewrite page="/pages/images/up.gif" />','<bean:message key="archigest.archivo.transferencias.caja"/>', true)">
						<html:img page="/pages/images/caja_new.gif" altKey="archigest.archivo.transferencias.crearCaja" titleKey="archigest.archivo.transferencias.crearCaja" styleClass="imgTextBottom"/>
						&nbsp;<bean:message key="archigest.archivo.crear"/>
					</a>
					</c:when>
					<c:otherwise>
					<a class="etiquetaAzul12Normal" href="javascript:crearCaja('<html:rewrite page="/pages/images/up.gif" />','<bean:message key="archigest.archivo.transferencias.caja"/>')">
						<html:img page="/pages/images/caja_new.gif" altKey="archigest.archivo.transferencias.crearCaja" titleKey="archigest.archivo.transferencias.crearCaja" styleClass="imgTextBottom"/>
						&nbsp;<bean:message key="archigest.archivo.crear"/>
					</a>
					</c:otherwise>
					</c:choose>
				</c:if>
			</TD>
			<td width="10">&nbsp;</td>
			<td>
				<a href="javascript:eliminarCaja('<c:out value="caja${__CAJA_SELECCIONADA.id}" />')" class="etiquetaAzul11Normal" >
					<html:img page="/pages/images/caja_delete.gif" styleClass="imgTextBottom"
						altKey="archigest.archivo.transferencias.eliminarCaja" titleKey="archigest.archivo.transferencias.eliminarCaja" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				</a>
			</td>
	     </TR>
		</TABLE>
		</TD>
	  </TR></TBODY>
	</TABLE>
</div>

<c:set var="infoAsignacion" value="${sessionScope[appConstants.transferencias.ASIGNACION_UDOC2UI]}" />
<div id="cajasRelacion" style="height:400px;overflow:auto">
<%--LISTA DE CAJAS --%>
	<c:set var="CAJAS_ABIERTAS" value="${sessionScope[appConstants.transferencias.CAJAS_ABIERTAS]}" />
	<c:forEach var="unidadInstalacion" items="${infoAsignacion.unidadesInstalacion}" varStatus="numeroCaja">
		<c:set var="contenidoCajaEditable" value="false" />
		<c:if test="${bRelacion.estado == estadoAbierta || (bRelacion.estado == estadoConErrores && unidadInstalacion.devolver)}">
			<c:set var="contenidoCajaEditable" value="true" />
		</c:if>

		<div id="<c:out value="caja${numeroCaja.count-1}" />" unselectable="on">

			<TABLE cellpadding=0 cellspacing=0 class="w100" style="border-bottom:1px dotted #999999;">
			  <TBODY>
			  <TR>
			    <TD width="40%" height="18px" class="etiquetaAzul12Bold">
					<a href="javascript:clickOn(<c:out value="${numeroCaja.count-1}" />)" unselectable="on">
						<img src="<html:rewrite page="/pages/images/down.gif" />" class="imgTextMiddle" id="<c:out value="iconocaja${numeroCaja.count-1}" />"><a>
					<a id="<c:out value="linkAbrirCaja${numeroCaja.count-1}" />" href="javascript:abrirCaja(<c:out value="${numeroCaja.count-1}" />)" class="numeroCaja"  unselectable="on">
						<bean:message key="archigest.archivo.transferencias.caja"/> <c:out value="${numeroCaja.count}" />
					</a>
					<c:if test="${bRelacion.conSignatura}">
						&nbsp;&nbsp;&nbsp;<bean:message key="archigest.archivo.signatura"/>:
						&nbsp;<input class="input" type="text" size="10" maxlength="9" numeroCaja="<c:out value="${numeroCaja.count-1}" />" value="<c:out value="${unidadInstalacion.signaturaUI}" />" onchange="signaturaModificada(event)">
					</c:if>
				</TD>
			  </TR></TBODY>
			</TABLE>
		</div>
		<c:set var="isOpened" value="${CAJAS_ABIERTAS[numeroCaja.count-1]}" />
		<c:if test="${isOpened}"><script>cajaAbierta(<c:out value="${numeroCaja.count-1}" />)</script></c:if>
		<div class="contenidoCaja" id="<c:out value="udocscaja${numeroCaja.count-1}" />" <c:if test="${!isOpened}">style="display:none"</c:if> unselectable="on">

			<div class="cuerpoCaja" id="<c:out value="emptycaja${numeroCaja.count-1}" />"
				style="padding-left:30px;<c:if test="${!empty unidadInstalacion.udocs}">display:none</c:if>" unselectable="on">
				&nbsp;&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja" />
			</div>

			<c:forEach var="parteUdoc" items="${unidadInstalacion.udocs}" varStatus="numUdocEnCaja">
			<c:set var="idParteUdoc" value="udoc${parteUdoc.idUnidadDoc}_${parteUdoc.numParteUdoc}" />
			<c:set var="nPartesUdoc" value="${parteUdoc.unidadDocumental.numeroPartes}" />
			<div idUdoc="<c:out value="${idParteUdoc}" />" id="<c:out value="${idParteUdoc}" />" class="Udocs" unselectable="on" udocPos="<c:out value="${parteUdoc.posUdocEnUI}" />" numPartes="<c:out value="${nPartesUdoc}" />" <c:if test="${(numUdocEnCaja.count - 1)%2 != 0}">style="background-color:#DCDCDC"</c:if>>
				<c:if test="${bRelacion.estado == appConstants.transferencias.estadoREntrega.CON_ERRORES_COTEJO.identificador}">
				<c:choose>
					<c:when test="${parteUdoc.estadoCotejo == appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}">
						<html:img page="/pages/images/cajaPendiente.gif" />
					</c:when>
					<c:when test="${parteUdoc.estadoCotejo == appConstants.transferencias.estadoCotejo.REVISADA.identificador}">
						<html:img page="/pages/images/cajaRevisada.gif" />
					</c:when>
					<c:when test="${parteUdoc.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador}">
						<html:img page="/pages/images/error.gif" />
						<c:set var="contenidoCajaEditable" value="true" />
					</c:when>
				</c:choose>
				</c:if>
				<span class="tituloUdoc" id="<c:out value="parte${idParteUdoc}" />" style="font-weight:bold">
					<b><c:out value="${numUdocEnCaja.count}" />:</b>
					<c:if test="${nPartesUdoc > 1}">
						(<c:out value="${parteUdoc.numParteUdoc}" />/<c:out value="${nPartesUdoc}" />)
					</c:if>
				</span>
				<span id="<c:out value="titulo${idParteUdoc}" />" class="tituloUdoc" unselectable="on">
				<b><c:out value="${parteUdoc.unidadDocumental.numeroExpediente}" /></b>
				<c:out value="${parteUdoc.unidadDocumental.asunto}" />
				</span>
				<c:if test="${!empty parteUdoc.descContenido}">
				<br>
				<span id="<c:out value="descripcion${idParteUdoc}" />" class="descripcionContenido"><c:out value="${parteUdoc.descContenido}" /></span>
				</c:if>
			</div>
			<script>
				changeManager.addUdoc('<c:out value="udoc${parteUdoc.idUnidadDoc}" />', <c:out value="${parteUdoc.numParteUdoc}" />, <c:out value="${numeroCaja.count - 1}" /> , <c:out value="${numUdocEnCaja.count - 1}" />);
				</script>
			</c:forEach>
		</div>
		<script>
		addCaja(<c:if test="${bRelacion.estado == estadoConErrores}">true</c:if>);
		<c:if test="${!contenidoCajaEditable}">
		var linkAbrirCaja = xGetElementById('<c:out value="linkAbrirCaja${numeroCaja.count-1}" />');
		linkAbrirCaja.setAttribute('href','#');
		linkAbrirCaja.onclick = xPreventDefault
		</c:if>
		</script>
	</c:forEach>

</div>


<div class="cuerpoCaja" id="cajaVacia" style="padding-left:30px;display:none" unselectable="on">
	&nbsp;&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja" />
</div>

<%--FIN LISTA DE CAJAS --%>

<%/*
<c:url var="vistaCajasURL" value="/action/organizacionUdocsRelacion">
	<c:param name="method" value="verCajas" />
</c:url>
<iframe name="frmCajas" id="frmCajas" width="100%" height="360" style="BORDER-BOTTOM: #7B7B7B 1px solid;BORDER-LEFT: #999999 1px solid;BORDER-RIGHT: #999999 1px solid" marginwidth="0" frameborder="0" src="<c:out value="${vistaCajasURL}" escapeXml="false"/>"></iframe>
*/%>
</td>
<td valign="top" width="60%">
<table cellpadding="0" cellspacing="0" border="0" width="100%" id="rightPanel"><tr><td>
<div class="cabecero_cajas">
	<TABLE cellpadding=0 cellspacing=0 border="0" width="98%">
	  <TBODY>
	  <TR>
	    <TD class="etiquetaAzul12Bold" width="40%">
			<bean:message key="archigest.archivo.transferencias.udocsSinCaja"/>
		</TD>
	    <TD width="60%" align="right">
		<TABLE cellpadding=0 cellspacing=0>
		  <TR>
			<TD>
				<a class="etiquetaAzul12Normal" href="#" onclick="dividirUdoc(event)">
					<html:img titleKey="archigest.archivo.transferencias.dividirUDoc" altKey="archigest.archivo.transferencias.dividirUDoc"
						page="/pages/images/dividir.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.transferencias.dividir"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<a class="etiquetaAzul12Normal" href="#" onclick="eliminarParteUdoc(event)">
					<html:img page="/pages/images/deleteParte.gif" styleClass="imgTextMiddle"
						titleKey="archigest.archivo.transferencias.eliminarParteUDoc" altKey="archigest.archivo.transferencias.eliminarParteUDoc" />
					&nbsp;<bean:message key="archigest.archivo.transferencias.eliminarParte"/>
				</a>
			</TD>
	     </TR>
		</TABLE>
		</TD>
	  </TR></TBODY>
	</TABLE>
</div>

<div class="bloque_caja" style="height:240px;overflow:auto;padding-top:6px">

	<div id="udocsNoAsignadas" style="height:100%">
		<c:set var="partesNoAsignadas" value="${infoAsignacion.partesSinAsignar}" />
		<c:forEach var="udocSinCaja" items="${partesNoAsignadas}" varStatus="udocPos">
			<c:set var="idParteUdoc" value="udoc${udocSinCaja.idUnidadDoc}_${udocSinCaja.numParteUdoc}" />
			<c:set var="nPartesUdoc" value="${udocSinCaja.unidadDocumental.numeroPartes}" />
			<div class="Udocs" id="<c:out value="${idParteUdoc}" />" idUdoc="<c:out value="${idParteUdoc}" />"
				unselectable="on" numPartes="<c:out value="${nPartesUdoc}" />" <c:if test="${(udocPos.count - 1)%2 != 0}">style="background-color:#DCDCDC"</c:if>>
				<span class="tituloUdoc" id="<c:out value="parte${idParteUdoc}" />" style="font-weight:bold">
					<c:if test="${nPartesUdoc > 1}">
						(<c:out value="${udocSinCaja.numParteUdoc}" />/<c:out value="${nPartesUdoc}" />)
					</c:if>
				</span>
				<span id="<c:out value="titulo${idParteUdoc}" />" class="tituloUdoc" unselectable="on">
				<b><c:out value="${udocSinCaja.unidadDocumental.numeroExpediente}" /></b>
				<c:out value="${udocSinCaja.unidadDocumental.asunto}" />
				</span>
				<c:if test="${!empty parteUdoc.descContenido}">
				<br>
				<span id="<c:out value="descripcion${idParteUdoc}" />" class="descripcionContenido"><c:out value="${parteUdoc.descContenido}" /></span>
				</c:if>
			</div>

			<script>
				addUdocNoAsignada('<c:out value="${idParteUdoc}" />');
				changeManager.addUdoc('<c:out value="udoc${udocSinCaja.idUnidadDoc}" />',<c:out value="${udocSinCaja.numParteUdoc}" />,-1,-1)</script>
		</c:forEach>
	</div>
	<div id="emptyUdocsSinCaja" style="position:absolute;width:80%;top:0px;left:10%;padding-top:60px;text-align:center;display:none" >
		<bean:message key="archigest.archivo.transferencias.todasUDCajas" />
	</div>
	<c:if test="${empty partesNoAsignadas}">
		<script>xDisplay('emptyUdocsSinCaja', 'block')</script>
	</c:if>

</div>
</td></tr>
<tr><td bgcolor="#DADAE4">

<div class="separador1">&nbsp;</div>

<div id="cajaSeleccionada" class="cuerpoCaja" style="height:120px" unselectable="on">
	<div class="cabecero_cajas">
		<TABLE border="0" cellpadding=0 cellspacing=0 width="98%">
		  <TBODY>
		  <TR>
			<TD class="etiquetaAzul12Bold" width="30%">
				<span>Caja en edición: </span> <span id="msgNoCajaSeleccionada">--</span><span id="labelCajaSeleccionada"></span>
			</TD>
			<TD width="70%" align="right">
			<table cellpadding="0" cellspacing="0"><tr>
				<td>
					<a class="etiquetaAzul11Normal" href="#"  onclick="closeCajaSeleccionada(event)">
						<html:img page="/pages/images/caja_ok.gif" styleClass="imgTextMiddle"
							altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar"/>
						<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<a class="etiquetaAzul11Normal" href="#"  onclick="introDescripcionContenido(event)">
						<html:img page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"
							altKey="archigest.archivo.descripcion" titleKey="archigest.archivo.descripcion"/>
						<bean:message key="archigest.archivo.descripcion"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<a class="etiquetaAzul11Normal" href="#"  onclick="subirUdoc(event)">
						<html:img page="/pages/images/caja_subir.gif" styleClass="imgTextMiddle"
							altKey="archigest.archivo.subirUDoc" titleKey="archigest.archivo.subirUDoc"/>
						&nbsp;<bean:message key="archigest.archivo.subir"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<a class="etiquetaAzul11Normal" href="#"  onclick="bajarUdoc(event)">
						<html:img page="/pages/images/caja_bajar.gif" styleClass="imgTextMiddle"
							altKey="archigest.archivo.bajarUDoc" titleKey="archigest.archivo.bajarUDoc"/>
						&nbsp;<bean:message key="archigest.archivo.bajar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<a class="etiquetaAzul11Normal" href="#"  onclick="quitarUdoc(event)">
						<html:img page="/pages/images/caja_quitar.gif" styleClass="imgTextMiddle"
							altKey="archigest.archivo.extraer" titleKey="archigest.archivo.extraer"/>
						&nbsp;<bean:message key="archigest.archivo.extraer"/>
					</a>
				</td>
			</tr></table>
			</TD>
		  </TR></TBODY>
		</TABLE>
	</div>
	<div id="descripcionContenido"  style="display:none;position:absolute;margin:0px;padding-left:10px;padding-right:50px">
	<div class="bloque" style="margin:0px; width:100%;background:#DADAE4">
	<div class="cabecero_bloque" style="width:100%;margin:0px;padding-right:10px;border-width:0px;BORDER-BOTTOM: #7B7B7B 1px solid">
		<table cellpadding="0" cellspacing="0" align="right"><tr>
			<td>
				<a class=etiquetaAzul12Bold href="#" onclick="guardarDescripcionContenido(event)">
					<html:img page="/pages/images/right.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			   	 	&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<TD align="right">
				<a class=etiquetaAzul12Bold href="#" onclick="hideDescripcionContenido(event)">
					<html:img page="/pages/images/wrong.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
			   	 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   	</a>
			</TD>
		</tr></table>
	</div>
	<textarea id="inputDescripcionContenido" rows="3" style="width:96%;margin-top:6px;margin-bottom:4px" onclick="stopPropagation(event); this.focus()"></textarea>
	</div>
	</div>

	<script>
		var selectionGroup = new SelectableItemsGroup('udocsCajaSeleccionada');
	</script>
	<div id="udocsCajaSeleccionada" class="bloque_caja" style="height:130px;overflow:auto;padding-top:3px">
		<div id="msgSeleccionCaja" style="padding-top:40px;text-align:center">
			Seleccione en la lista de cajas aquella que desea editar
		</div>
	</div>
</div>

</td></tr>
</table>

</td></tr></table>
</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>


<html:form styleId="formulario" action="/organizacionUdocsRelacion">
	<input type="hidden" name="method" value="guardarCambios">
	<input type="hidden" name="idRelacion" value="<c:out value="${bRelacion.id}" />">
	<input type="hidden" name="cajasAbiertas">
	<input type="hidden" name="udocsNoAsignadas">
	<input type="hidden" name="createdParts">
	<input type="hidden" name="asignedParts">
	<input type="hidden" name="removedParts">
	<input type="hidden" name="changedUIs">
</html:form>

<div id="dragSymbol" style="position:absolute;top:0px;left:0px;visibility:hidden;z-index:1;height:16px;width:16px">
	<html:img page="/pages/images/docs.gif" />
</div>

<script>
xAddEventListener('rightPanel', 'click', removeSelections, false);
</script>