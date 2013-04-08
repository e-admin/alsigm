<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fichas" value="${requestScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
<c:set var="niveles" value="${requestScope[appConstants.fondos.LISTA_NIVELES_KEY]}"/>

<c:if test="${fichas == null}">
	<c:set var="fichas" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
</c:if>
<c:if test="${niveles == null}">
	<c:set var="niveles" value="${sessionScope[appConstants.fondos.LISTA_NIVELES_KEY]}"/>
</c:if>

	<script language="javascript">
			var niveles = new Array();

			function Nivel(_idNivel, _idFicha){
				this.idFicha = _idFicha;
				this.idNivel = _idNivel;
			}

			function Opcion(_idFicha, _nombreFicha){
				this.idFicha = _idFicha;
				this.nombreFicha= _nombreFicha;
				this.idNivel = "";

				for(var i=0;i<niveles.length;i++){
					if(_idFicha == niveles[i].idFicha) {
						this.idNivel = niveles[i].idNivel;
					}
				}
			}


			var niveles = new Array();
			<c:if test="${not empty niveles}">
				<c:forEach var="nivel" items="${niveles}">
					niveles.push(new Nivel('<c:out value="${nivel.id}"/>','<c:out value="${nivel.idFichaDescrPref}"/>'));
				</c:forEach>
			</c:if>

			var opciones = new Array();



			function cambioNivel(){
				//Cuando se modifican los niveles, se borra la lista de fichas.
				var lista = document.forms['<c:out value="${actionMappingName}" />'].idFicha;

				if(lista){
					recargarListaFichas();
				}
			}

	</script>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.nivelesDescripcion"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<logic:present name="niveles">
			<table class="formulario">
				<tr>
					<td align="right">
						<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMappingName}" />'].niveles);cambioNivel();"
			 			><html:img page="/pages/images/checked.gif"
							    border="0"
							    altKey="archigest.archivo.selTodos"
							    titleKey="archigest.archivo.selTodos"
							    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
						&nbsp;
						<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMappingName}" />'].niveles);cambioNivel();"
			 			><html:img page="/pages/images/check.gif"
							    border="0"
							    altKey="archigest.archivo.quitarSel"
							    titleKey="archigest.archivo.quitarSel"
							    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
						&nbsp;&nbsp;
				   </td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0">
			<logic:iterate id="nivel" scope="page" name="niveles" indexId="rowNum">
			<% if ((rowNum.intValue() % 3) == 0) out.println("<tr>"); %>
				<td><html:multibox style="border:0" property="niveles" styleId="niveles" onclick="cambioNivel()"><bean:write name="nivel" property="id"/></html:multibox></td>
				<td class="etiquetaNegra12Normal"><bean:write name="nivel" property="nombre"/></td>
				<td width="10">&nbsp;</td>
			<% if ((rowNum.intValue() % 3) == 2) out.println("<tr>"); %>
			</logic:iterate>
			</table>
		</logic:present>
	</td>
</tr>
