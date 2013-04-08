<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/calendarlibtag.tld" prefix="calendar" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="archivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
	
<c:if test="${!empty archivos}">
<c:url scope="request" var="mostrarCalendarioPrevisionesURL" value="/action/recepcionPrevisiones">
	<c:param name="method" value="mostrarCalendarioPrevisiones" />
</c:url>


<script language="javascript">

	function recargar(){
		var direccion = "<c:out value="${mostrarCalendarioPrevisionesURL}" escapeXml="false"/>";

		var idarchivo = document.getElementById("idArchivo").value; 

		if(idarchivo != null && idarchivo != ""){
			direccion += "&idArchivo=" + idarchivo; 
		}	
	
		window.location = direccion;
	}
</script>
</c:if>
	
<div id="contenedor_ficha">

	<div class="ficha">
		<html:form action="/recepcionPrevisiones" styleId="frm" styleClass="formulario">
		<html:hidden property="mes"/>
		<h1>
			<span>
				<div class="w100">
					<table class="w98" cellpadding=0 cellspacing=0>
						<tr>
					    	<td class="etiquetaAzul12Bold" height="25px">
					    		<bean:message key="archigest.archivo.calendario.previsiones"/>

						<c:if test="${!empty archivos}">
							&nbsp;<html:select property="idArchivo" styleId="idArchivo" onchange="recargar()">
									<html:options collection="archivos" property="id" labelProperty="nombre" />
								</html:select>
							</c:if>

				    		</td>

					    	<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
										<td>
											<a class="etiquetaAzul12Bold" href="javascript:switchVisibility('leyenda');" >
												<html:img page="/pages/images/legend.gif" altKey="archigest.archivo.calendar.legend" titleKey="archigest.archivo.calendar.legend" styleClass="imgTextMiddle" />
												&nbsp;<bean:message key="archigest.archivo.calendar.legend"/>
											</a>
										</td>
										<TD width="10">&nbsp;</TD>
										<td>
											<c:url var="volverURL" value="/action/recepcionPrevisiones">
												<c:param name="method" value="goBack" />
											</c:url>
											<a class="etiquetaAzul12Bold" href="<c:out value="${volverURL}" escapeXml="false"/>" >
												<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
												&nbsp;<bean:message key="archigest.archivo.cerrar"/>
											</a>
										</td>											
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</span>
		</h1>

		<div class="floatingLeyenda" id="leyenda" style="display:none;">
			<div class="cabeceraLeyenda">
				<table class="width100">
					<tr class="cabeceraLeyenda">
						<td class="tituloLeyendaLeft">
							<html:img page="/pages/images/pixel.gif" width="5px" styleClass="imgTextMiddle" />
							 <bean:message key="archigest.archivo.calendar.previsiones"/> 
						</td>
						<td class="accionesLeyendaRight">
							<html:img page="/pages/images/close.gif" 
								altKey="archigest.archivo.cerrar" 
								titleKey="archigest.archivo.cerrar" 
								styleClass="imgTextMiddle" 
								onclick="switchVisibility('leyenda');"/>
							<html:img page="/pages/images/pixel.gif" width="2px" height="17" styleClass="imgTextMiddle" />
						</td>
					</tr>
				</table>
			</div>
			<div class="cuerpoLeyenda">
				<c:set var="calendarLegend" value="${sessionScope[appConstants.transferencias.CALENDAR_LEGEND_KEY]}"/>
				<c:forEach var="legend" items="${calendarLegend}">
					<div class="legendLine">
						<div class="legendElement" style='background-color:#<c:out value="${legend.color}"/>'>
							&nbsp;&nbsp;&nbsp;	
						</div>&nbsp;<c:out value="${legend.inicio}"/>
						<c:choose>
							<c:when test="${legend.ultimo}">+
							</c:when>
							<c:otherwise>
								<c:if test="${not empty legend.fin}">- <c:out value="${legend.fin}"/></c:if>
							</c:otherwise>
						</c:choose>
						<br/>
					</div>
				
				</c:forEach>
			</div>
		</div>		
		

			
			<div class="cuerpo_drcha">
				<div class="cuerpo_izda">
						
					<div id="barra_errores"><archivo:errors /></div>
	
					<div class="bloque">
						<div id="divTbl" style="margin-left:2px">
							<table >
								<c:url scope="request" var="mostrarMesURL" value="/action/recepcionPrevisiones">
									<c:param name="method" value="mostrarMes" />
									<c:param name="mes" value="" />
								</c:url>					
								<calendar:Calendar scope="session" name="calendar-config" urlRequestParameter="mostrarMesURL"/>
							</table>
						</div>
					</div>
				
				</div> <%--cuerpo_izda --%>
				<h2><span>&nbsp;<br/></span></h2>
				
			</div> <%--cuerpo_drcha --%>
		</html:form>
		
	</div> <%--ficha --%>

</div> <%--contenedor_ficha --%>
