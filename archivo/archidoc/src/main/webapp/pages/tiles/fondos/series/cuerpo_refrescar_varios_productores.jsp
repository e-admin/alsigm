<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>


<c:set var="nuevosProductores" value="${sessionScope[appConstants.fondos.NUEVOS_PRODUCTORES_DESPUES_KEY]}"/>
<c:set var="antiguosProductores" value="${sessionScope[appConstants.fondos.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY]}"/>
<c:set var="antiguosProductoresPrimerNodo" value="${sessionScope[appConstants.fondos.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY]}"/>

<bean:struts id="mappingGestionSeries" mapping="/gestionIdentificacionAction" />


<html:form action="/gestionIdentificacionAction?method=sustituirNuevosProductores">

<html:hidden property="productoresSeleccionados" styleId="productoresSeleccionados"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.combinarProductores"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>

				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionSeries.name}" />'].submit()">
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<tiles:insert definition="button.returnButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
				</tiles:insert>

			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">


			<html:hidden property="productoresAntiguos" styleId="productoresAntiguos"/>
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockContent" direct="true">

					<div class="separador5">&nbsp;</div>

					<display:table name="pageScope.nuevosProductores" id="productor" style="width:99%">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.cf.noProductores"/>
						</display:setProperty>
						<display:column titleKey="archigest.archivo.cf.productoresNuevos">
							<c:set var="organoNoDesplegadoName" value="organoNoDesplegado" />
							<c:set var="organoDesplegadoName" value="organoDesplegado" />
							<c:set var="position" value="${productor_rowNum - 1}" />
							<c:set var="organoNoDesplegado" value="${organoNoDesplegadoName}${position}" />
							<c:set var="organoDesplegado" value="${organoDesplegadoName}${position}" />

							<div id="<c:out value="${organoNoDesplegado}"/>">
								<a href="javascript:desplegarOrgano(<c:out value="${position}"/>)">
									<html:img page="/pages/images/plus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${productor.nombre}" />
							</div>

							<div id="<c:out value="${organoDesplegado}"/>" style="display:none">
								<a href="javascript:desplegarOrgano(<c:out value="${position}"/>)">
									<html:img page="/pages/images/minus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${productor.nombreLargo}" />
							</div>

							<script>
								function desplegarOrgano(posicion) {
									switchVisibility("organoNoDesplegado"+posicion);
									switchVisibility("organoDesplegado"+posicion);
								}
							</script>

						</display:column>
						<display:column titleKey="archigest.archivo.cf.productoresActuales"  >


							<c:set var="idProductorAntiguo" value="productorAntiguo${productor_rowNum -1}"></c:set>
							<c:set var="nombreLargo" value="nombreLargo${productor_rowNum -1}"></c:set>
							<html-el:select property="productoresAntiguosPrimerNodo" styleId="${idProductorAntiguo}" onchange="javascript:mostrarNombreCompleto(${productor_rowNum -1})">
								<html:option value="">&nbsp</html:option>
								<html:optionsCollection name="antiguosProductoresPrimerNodo" value="idProductor" label="nombre"/>
							</html-el:select><br/>
							<div id="divNombreLargo<c:out value="${productor_rowNum -1}"/>">

							</div>





						</display:column>
					</display:table>


					<div class="separador5">&nbsp;</div>

				</tiles:put>
			</tiles:insert>


	</tiles:put>
</tiles:insert>

<script language="JavaScript1.2" type="text/JavaScript">


	function mostrarNombreCompleto(pos)
	{

		var idDiv = "divNombreLargo" + pos
		var SEPARADOR_PRODUCTORES = "$";
		var SEPARADOR_ORGANOS = "/";


		document.getElementById(idDiv).style.display="none";
		document.getElementById(idDiv).innerHTML = "";

		var posicionSeleccionada = document.getElementById("productorAntiguo"+pos).selectedIndex;


		if(posicionSeleccionada != 0){
			var nombresLargos = SEPARADOR_PRODUCTORES + document.getElementById("productoresAntiguos").value;

			var partes = nombresLargos.split(SEPARADOR_PRODUCTORES);
			var valor = partes[posicionSeleccionada];

			document.getElementById(idDiv).innerHTML = getNombrePorPartes(valor);
			document.getElementById(idDiv).style.display="block";
		}
	}


	function getNombrePorPartes(cadena){
		var partes = cadena.split("/");
		var separador = "<br>&nbsp;&nbsp;&nbsp;&nbsp;";

		var retorno = "";

		for(var i=0;i<partes.length;i++){
			retorno += partes[i];
			retorno += separador;
			separador+= "&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		return retorno;
	}


	var cadena = document.getElementById("productoresSeleccionados").value;
	if(cadena!=null)
	{
		var SEPARADOR_PRODUCTORES = "$";
		var productores = cadena.split(SEPARADOR_PRODUCTORES);
	    for(var i = 0; i < productores.length; i++)
	    {
	        var pos=i+1;
	        var nombre = "productorAntiguo"+pos;
	        document.getElementById(nombre).value = productores[i];
	    }
    }

</script>
	</html:form>
