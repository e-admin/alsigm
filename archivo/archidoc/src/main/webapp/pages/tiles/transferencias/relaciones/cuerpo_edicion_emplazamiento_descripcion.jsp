<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingComposicionEmplazamiento" mapping="/composicionEmplazamiento" />
<c:set var="formName" value="${mappingComposicionEmplazamiento.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<script>

	function isEmplazamientoValidado(){
		return xGetElementById("emplazamientoValidado").checked;
	}
	
	function aceptar(formName){
	
		var pais = document.getElementById('pais');
		var refPais = document.getElementById('refPais');			
		if ((pais!=null) && (pais.value!="") && (pais.value!="undefined")&&
			(refPais!=null) && (refPais.value!="") && (refPais.value!="undefined")){						

			if (opener.document.getElementById(refPais.value))
			{
				window.opener.document.getElementById(refPais.value).value = pais.value;
			}
		}
		
		var provincia = document.getElementById('provincia');
		var refProvincia = document.getElementById('refProvincia');			
		if ((provincia!=null) && (provincia.value!="") && (provincia.value!="undefined")&&
			(refProvincia!=null) && (refProvincia.value!="") && (refProvincia.value!="undefined")){						

			if (opener.document.getElementById(refProvincia.value))
			{
				window.opener.document.getElementById(refProvincia.value).value = provincia.value;
			}
		}
		
		var municipio = document.getElementById('municipio');
		var refMunicipio = document.getElementById('refConcejo');			
		if ((municipio!=null) && (municipio.value!="") && (municipio.value!="undefined")&&
			(refMunicipio!=null) && (refMunicipio.value!="") && (refMunicipio.value!="undefined")){						

			if (opener.document.getElementById(refMunicipio.value))
			{
				window.opener.document.getElementById(refMunicipio.value).value = municipio.value;
			}
		}
		
		var poblacion = document.getElementById('poblacion');
		var refPoblacion = document.getElementById('refPoblacion');			
		if ((poblacion!=null) && (poblacion.value!="") && (poblacion.value!="undefined")&&
			(refPoblacion!=null) && (refPoblacion.value!="") && (refPoblacion.value!="undefined")){						

			if (opener.document.getElementById(refPoblacion.value))
			{
				window.opener.document.getElementById(refPoblacion.value).value = poblacion.value;
			}
		}
		
		var localizacion = document.getElementById('direccion');
		var refLocalizacion = document.getElementById('refLocalizacion');			
		if ((localizacion!=null) && (localizacion.value!="") && (localizacion.value!="undefined")&&
			(refLocalizacion!=null) && (refLocalizacion.value!="") && (refLocalizacion.value!="undefined")){						

			if (opener.document.getElementById(refLocalizacion.value))
			{
				window.opener.document.getElementById(refLocalizacion.value).value = localizacion.value;
			}
		}							

		var refValidado = document.getElementById('refValidado');			
		if ((refValidado!=null) && (refValidado.value!="") && (refValidado.value!="undefined")){						

			if (opener.document.getElementById(refValidado.value))
			{
				if (isEmplazamientoValidado()) {
					validadoTextSi = document.getElementById('validadoTextSi');
					if ((validadoTextSi!=null) && (validadoTextSi.value!="") && (validadoTextSi.value!="undefined"))
						window.opener.document.getElementById(refValidado.value).selectedIndex = validadoTextSi.value;
				} else {
					validadoTextNo = document.getElementById('validadoTextNo');
					if ((validadoTextNo!=null) && (validadoTextNo.value!="") && (validadoTextNo.value!="undefined"))
						window.opener.document.getElementById(refValidado.value).selectedIndex = validadoTextNo.value;
				}
			}
		}
		
		document.getElementById("linkPressed").value="aceptar";
		window.close();
	}
	
	window.onunload=function cerrarVentana(){
		var linkPressed=document.getElementById("linkPressed").value;
		if(linkPressed!="aceptar" &&
		   linkPressed!="buscar"){
			idRow=document.getElementById("idRow");
			window.opener.removeTableRow(idRow.value);
		}
	}
	function cerrarVentana2(){
		document.getElementById("linkPressed").value="cerrarVentana";
		window.close();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.edicion"/>
		<bean:message key="archigest.archivo.transferencias.los"/>
		<bean:message key="archigest.archivo.transferencias.emplazamientos"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">	
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:aceptar('<c:out value="${mappingComposicionEmplazamiento.name}" />');" >
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
			   <TD>
			   		<a class=etiquetaAzul12Bold href="javascript:cerrarVentana2();">
						<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">						

		<bean:define id="isPopup" value="true" toScope="request"/>
		<bean:define id="soportaBusquedaExtendida" name="SOPORTA_BUSQUEDA_EXTENDIDA" toScope="session"/>
		<tiles:insert page="./geograficos/busqueda_edicion_geograficos.jsp" flush="true"/>
	
	</tiles:put>
</tiles:insert>