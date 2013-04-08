<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="repositoriosEcm"
	value="${sessionScope[appConstants.fondos.REPOSITORIOS_ECM_KEY]}" />

<TR>
	<td class="tdTitulo" colspan="3">
		<bean:message key="archigest.archivo.cf.uDocsSerie"/>
	</td>
</TR>
<tr>
	<td id="TD_2" class="tdDatosFicha" colspan="3"><script>
	    elemento = new ElementInfo();
	    elemento.id = "2";
      	elemento.nestedElements[elemento.nestedElements.length] = "9";
		elemento.nestedElements[elemento.nestedElements.length] = "10";
		elemento.nestedElements[elemento.nestedElements.length] = "11";
		elemento.nestedElements[elemento.nestedElements.length] = "12";
	    elementsInfo.addTableElementInfo(elemento);
	  </script>
		<table cellpadding="0" cellspacing="0">
			<tr>
			<td><a class="etiquetaAzul12Bold"
				href="javascript:addTableRow('2','TABLA');"> <html:img
				page="/pages/images/plus.gif" altKey="organizacion.anadir"
				titleKey="organizacion.anadir" styleClass="imgTextBottom" /> &nbsp;<bean:message
				key="organizacion.anadir" /> </a></td>
			<td width="10">&nbsp;</td>
			<td>
			<a class="etiquetaAzul12Bold" href="javascript:removeTableRows('2');">
			<html:img
				page="/pages/images/minus.gif" altKey="archigest.archivo.eliminar"
				titleKey="archigest.archivo.eliminar" styleClass="imgTextBottom" />
			&nbsp;<bean:message key="archigest.archivo.eliminar" />
			</a>
			</td>
			</tr>
		</table>
		<div id="divTbl">
		<table id="tabla2" class="tablaFicha">
		<thead>
		<tr>
		<th><img alt="Eliminar" src="../images/delete.gif"></th><script>
		    elemento = new ElementInfo();
		    elemento.id = "9";
		    elemento.type = "1";
		    elemento.style = "";
		    elemento.mandatory = "N";
		    elemento.initialValue = "";
		    elemento.defaultOptionValue = "";
		    var counter = null;
		    <c:forEach var="nivel" items="${listaNivelesDocumentales}">
	  	        elemento.options[elemento.options.length] = "<c:out value="${nivel.id}"/>";
	  	        elemento.options[elemento.options.length] = "<c:out value="${nivel.nombre}"/>";
		    </c:forEach>
		    elemento.defaultFormatValue = "";
		    elemento.refType = "";
		    elemento.defaultTipoMedida = "";
		    elemento.defaultUnidadMedida = "";
		    elemento.mostrarTipoMedida = "";
		    elemento.mostrarUnidadMedida = "";
		    elemento.validationTable= "";
		    elemento.editable="S";
		    elemento.optionsTextId=true;
		    elemento.usarMismoIdYNombre=true;
		    elementsInfo.addFieldElementInfo(elemento);

		  </script><th style="">Nivel Documental<input type="hidden" name="campo_9_idtabla" value="2"></th><script>
		    elemento = new ElementInfo();
		    elemento.id = "10";
		    elemento.type = "1";
		    elemento.style = "";
		    elemento.mandatory = "N";
		    elemento.initialValue = "";
		    elemento.defaultOptionValue = "";
		    <c:forEach var="fichaUdoc" items="${fichasUdocs}">
		    	elemento.options[elemento.options.length] = "<c:out value="${fichaUdoc.id}"/>";
		    	elemento.options[elemento.options.length] = "<c:out value="${fichaUdoc.nombre}"/>";
		    </c:forEach>
		    elemento.defaultFormatValue = "";
		    elemento.refType = "";
		    elemento.defaultTipoMedida = "";
		    elemento.defaultUnidadMedida = "";
		    elemento.mostrarTipoMedida = "";
		    elemento.mostrarUnidadMedida = "";
		    elemento.validationTable= "";
		    elemento.editable="S";
		    elemento.optionsTextId=true;
   		    elemento.usarMismoIdYNombre=true;
		    elementsInfo.addFieldElementInfo(elemento);

		  </script><th style=""><bean:message key="archigest.archivo.cf.fichaDescAsociada"/><input type="hidden" name="campo_10_idtabla" value="2"></th><script>
		    elemento = new ElementInfo();
		    elemento.id = "11";
		    elemento.type = "1";
		    elemento.style = "";
		    elemento.mandatory = "N";
		    elemento.initialValue = "";
		    elemento.defaultOptionValue = "";
  		    <c:forEach var="fichaCLDoc" items="${listaFichasCLDocumentales}">
		    	elemento.options[elemento.options.length] = "<c:out value="${fichaCLDoc.id}"/>";
		    	elemento.options[elemento.options.length] = "<c:out value="${fichaCLDoc.nombre}"/>";
		    </c:forEach>
		    elemento.defaultFormatValue = "";
		    elemento.refType = "";
		    elemento.defaultTipoMedida = "";
		    elemento.defaultUnidadMedida = "";
		    elemento.mostrarTipoMedida = "";
		    elemento.mostrarUnidadMedida = "";
		    elemento.validationTable= "";
		    elemento.editable="S";
		    elemento.optionsTextId=true;
		    elemento.usarMismoIdYNombre=true;
		    elementsInfo.addFieldElementInfo(elemento);

		  </script><th style=""><bean:message key="archigest.archivo.cf.fichaClasifDocAsociada"/><input type="hidden" name="campo_11_idtabla" value="2"></th><script>
		    elemento = new ElementInfo();
		    elemento.id = "12";
		    elemento.type = "1";
		    elemento.style = "";
		    elemento.mandatory = "N";
		    elemento.initialValue = "";
		    elemento.defaultOptionValue = "";

		  	<c:if test="${not empty repositoriosEcm}">
			<c:forEach var="repositorioEcm" items="${repositoriosEcm}">
		    	elemento.options[elemento.options.length] = "<c:out value="${repositorioEcm.id}"/>";
		    	elemento.options[elemento.options.length] = "<c:out value="${repositorioEcm.nombre}"/>";
		    </c:forEach>


		    elemento.defaultFormatValue = "";
		    elemento.refType = "";
		    elemento.defaultTipoMedida = "";
		    elemento.defaultUnidadMedida = "";
		    elemento.mostrarTipoMedida = "";
		    elemento.mostrarUnidadMedida = "";
		    elemento.validationTable= "";
		    elemento.editable="S";
		    elemento.optionsTextId=true;
		    elemento.usarMismoIdYNombre=true;
   		    elemento.tieneCheckRelacionado=true; // Se usa para indicar que este tipo de elemento se va a mostrar con un check al lado
   		    elemento.textoCheck="Aplicar retroactivamente";
		    elementsInfo.addFieldElementInfo(elemento);

		    </c:if>

		  </script><th style=""><bean:message key="archigest.archivo.repositorio.ecm"/><input type="hidden" name="campo_12_idtabla" value="2"></th>
		</tr>
		</thead>
		<tbody></tbody>
		</table>
		</div>
	</td>
	</tr>
<script>
<c:if test="${!empty infoUDocsSerie}">
	<c:forEach var="infoUDoc" items="${infoUDocsSerie}">
		javascript:addTableRow('2','TABLA');
	</c:forEach>
		<%--niveles --%>
		var elementosNivel = document.getElementsByName("campo_9");
		if (elementosNivel)
	  	{
 			var i = 0;
	  			<c:forEach var="infoUDoc" items="${infoUDocsSerie}">
		  			var valorActual9 = "<c:out value="${infoUDoc.nivelUDoc.id}"/>";
		        	var opcionesElementoNivel = elementosNivel[i].options;
	    	    	for (var j = 0; j < opcionesElementoNivel.length; j++)
	  				{
	  					if (opcionesElementoNivel[j].value == valorActual9)
	            			opcionesElementoNivel[j].selected = true;
	        		}
	        		i = i + 1;
	        	</c:forEach>
	  	}
	  	<%--fichas descriptivas --%>
	  	var elementosFicha = document.getElementsByName("campo_10");
		if (elementosFicha)
	  	{
			var k = 0;
				<c:forEach var="infoUDoc" items="${infoUDocsSerie}">
					var valorActual10 = "<c:out value="${infoUDoc.ficha.id}"/>";
		        	var opcionesElementoFicha = elementosFicha[k].options;
		        	for (var l = 0; l < opcionesElementoFicha.length; l++)
		  			{
		        		if (opcionesElementoFicha[l].value == valorActual10)
		            		opcionesElementoFicha[l].selected = true;
		        	}
		        	k = k +1;
	        	</c:forEach>
	  	}

	  	<%--fichas documentos --%>
	  	var elementosFichaClf = document.getElementsByName("campo_11");
		if (elementosFichaClf)
	  	{
			var m = 0;
				<c:forEach var="infoUDoc" items="${infoUDocsSerie}">
					var valorActual11 = "<c:out value="${infoUDoc.fichaClf.id}"/>";
		        	var opcionesElementoFichaClf = elementosFichaClf[m].options;
		        	for (var n = 0; n < opcionesElementoFichaClf.length; n++)
		  			{
		        		if (opcionesElementoFichaClf[n].value == valorActual11)
		            		opcionesElementoFichaClf[n].selected = true;
		        	}
		        	m = m + 1;
		        </c:forEach>
	  	}

	  	<%--Repositorios ECM --%>

	  	<c:if test="${not empty repositoriosEcm}">

	  	var elementosRepEcm = document.getElementsByName("campo_12");
		if (elementosRepEcm)
	  	{
			var p = 0;
			<c:forEach var="infoUDoc" items="${infoUDocsSerie}">
				var valorActual12 = "<c:out value="${infoUDoc.repositorioEcm.id}"/>";
	        	var opcionesElementoRepEcm = elementosRepEcm[p].options;
	        	for (var q = 0; q < opcionesElementoRepEcm.length; q++)
	  			{
	        		if (opcionesElementoRepEcm[q].value == valorActual12)
	            		opcionesElementoRepEcm[q].selected = true;
	        	}

	        	var checkElementoRepEcm = document.getElementById("campo_12_check_"+(p+1));
	        	var updateUDocs = "<c:out value="${infoUDoc.updateUDocs}"/>";
		        if (updateUDocs == "true") {
	        		checkElementoRepEcm.checked = true;
	        		checkElementoRepEcm.disabled = true;
	        	}
				else {
	        		checkElementoRepEcm.checked = false;
	        	}

	        	p = p + 1;
	        </c:forEach>
	  	}

	  	</c:if>
</c:if>
</script>