<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionGrupos" mapping="/gestionGrupos" />
<c:set var="infoGrupo" value="${sessionScope[appConstants.controlAcceso.INFO_GRUPO]}" />
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.verGrupo"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<script>
			function crearGrupo() {
				var form = document.forms['<c:out value="${mappingGestionGrupos.name}" />'];
				form.submit();
			}
		
			function togglePerteneceAArchivo() {
				if(esChecked("perteneceAArchivo")){
					setVisible("divArchivo",true);
				}
				else {
					setVisible("divArchivo",false);
				}			
			}

			function togglePersonalizarSecciones() {
				if(esChecked("personalizarSecciones")){
					setVisible("divPersonalizarSecciones",true);
				}
				else {
					setVisible("divPersonalizarSecciones",false);
				}			
			}
	
			function componerCodigo(){
				var ejPaisProvincia = document.getElementById("ejemploPaisProvincia").value;
				var ejArchivoCodigo = document.getElementById("ejemploArchivoCodigo").value;;
				var ejCodigo = document.getElementById("ejemploCodigo").value; 
				var delimitador = document.getElementById("delimitador").value;;
	
				var codigo = ejCodigo;

				if(!document.getElementById("ocultarArchivoCodigoClasificadores").checked){
					codigo = ejArchivoCodigo + delimitador + codigo; 
				}

				if(!document.getElementById("ocultarPaisProvincia").checked){
					codigo = ejPaisProvincia + delimitador + codigo; 
				}
	
				codigo = "Ej. "  + codigo;
				document.getElementById("ejemplo").innerHTML = codigo;
			}	
					
		</script>
		
		<table><tr>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:crearGrupo()" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			</td>		
			<td width="10px"></td>		
			<td nowrap>
				<c:url var="cancelURL" value="/action/gestionGrupos">
					<c:param name="method" value="goBack" />
				</c:url>
	
				<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
			</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
		<html:form action="/gestionGrupos">				
			<input type="hidden" name="method" value="guardarGrupo">
			<html:hidden property="ejemploPaisProvincia" styleId="ejemploPaisProvincia"/>
			<html:hidden property="ejemploArchivoCodigo" styleId="ejemploArchivoCodigo"/>
			<html:hidden property="ejemploCodigo" styleId="ejemploCodigo"/>
			<html:hidden property="delimitador" styleId="delimitador"/>
			<c:set var="formBean" value="${requestScope[mappingGestionGrupos.name]}" />
			
			<c:if test="${!empty formBean.idGrupo}">
				<html:hidden property="idGrupo" />
			</c:if>
			<table class="formulario" width="99%">
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.nombre"/>:
					</td>
					<td class="tdDatos"><html:text property="nombre" styleClass="input99"  maxlength="254" /></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.descripcion"/>:
					</td>
					<td class="tdDatos"><html:textarea rows="4" property="descripcion" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" /></td>
				</tr>
				<tr>
					<td class="tdTitulo" style="padding-top:10px" colspan="2">
						<html:checkbox property="perteneceAArchivo" styleClass="checkbox" onclick="javascript:togglePerteneceAArchivo();" styleId="perteneceAArchivo" />
						<bean:message key="archigest.archivo.grupos.perteneceAArchivo"/>
					
						<div id="divArchivo" style="display:none;">
							<table class="formulario" width="99%">
								<tr>
									<td width="100px">
										<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
									</td>
									<td class="tdTitulo" width="100px">
										<bean:message key="archigest.archivo.archivo"/>:&nbsp;
									</td>
									<td class="tdDatos">
										<c:set var="listaArchivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />
										<c:choose>
											<c:when test="${!empty listaArchivos}">
												<html:select property="archivoCustodia" styleId="archivoCustodia">
													<html:options collection="listaArchivos" property="id" labelProperty="nombre" />
												</html:select>
											</c:when>
											<c:otherwise>
												<c:out value="${infoGrupo.archivoCustodia.nombre}" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" style="padding-top:10px" colspan="2">
						<html:checkbox property="personalizarSecciones" styleClass="checkbox" onclick="javascript:togglePersonalizarSecciones();" styleId="personalizarSecciones" />
						<bean:message key="archigest.archivo.grupos.personalizar.codigo"/>
					
						<c:set var="styleDiv" value="display:none;"/>

						<div id="divPersonalizarSecciones" style="display:none;" class="etiquetaAzul12Bold">
							<table class="formulario" width="99%">
								<tr>
									<td width="100px">
										<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
									</td>
									<td class="etiquetaAzul11Bold" valign="top">
										<html:checkbox property="ocultarPaisProvincia" styleClass="checkbox" styleId="ocultarPaisProvincia" onclick="componerCodigo();"></html:checkbox><bean:message key="archigest.archivo.grupos.pais.y.provincia"/>
										&nbsp;&nbsp;
										<html:checkbox property="ocultarArchivoCodigoClasificadores" styleClass="checkbox" styleId="ocultarArchivoCodigoClasificadores" onclick="componerCodigo();"></html:checkbox><bean:message key="archigest.archivo.grupos.archivo.y.codigo.clasificadores"/>
										&nbsp;&nbsp;
										<input type="checkbox" class="checkbox" disabled="disabled"><bean:message key="archigest.archivo.codigo"/>
										<div class="separador5">&nbsp;</div>
										<div id="ejemplo" class="etiquetaAzul11Bold">&nbsp;</div>
									</td>
									<td>
										
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>

			</table>

		</html:form>
		</div>
		<script>
			togglePerteneceAArchivo();
			togglePersonalizarSecciones();
			componerCodigo();
		</script>
	</tiles:put>
</tiles:insert>