<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<TR>
	<TD class="tdTitulo" width="220px">
		<bean:message key="archigest.archivo.transferencias.rangoExpedientes"/>:&nbsp;
	</TD>
	<td id="TD_102" class="tdDatosFicha">
		<script>
			    elemento = new ElementInfo();
						    elemento.id = "102";
							elemento.nestedElements[elemento.nestedElements.length] = "201";
						    elemento.nestedElements[elemento.nestedElements.length] = "202";
						    elementsInfo.addTableElementInfo(elemento);
						</script>
						<table cellpadding="0" cellspacing="0">
						<tr>
						<td><a class="etiquetaAzul12Bold" href="javascript:addTableRow('102','TABLA');">
						<img class="imgTextBottom" title="A&ntilde;adir" alt="A&ntilde;adir" border="0" src="../images/plus.gif"></img>&nbsp;A&ntilde;adir</a></td><td width="10">&nbsp;</td><td><a class="etiquetaAzul12Bold" href="javascript:removeTableRows('102');">
						<img class="imgTextBottom" title="Eliminar" alt="Eliminar" border="0" src="../images/minus.gif"></img>&nbsp;Eliminar</a></td>
						</tr>
						</table>
					    <table id="tabla102" class="tablaFicha">
					    <thead><tr><th><img alt="Eliminar" src="../images/delete.gif"/></th><script>							
							    elemento = new ElementInfo();
							    elemento.id = "201";
							    //elemento.id = "rangoInicial";
							    elemento.type = "1";
							    elemento.style = "";
							    elemento.mandatory = "N";
							    elemento.initialValue = "";
							    elemento.defaultOptionValue = "";
							    elemento.defaultFormatValue = "";
							    elemento.refType = "";
							    elemento.defaultTipoMedida = "";
							    elemento.defaultUnidadMedida = "";
							    elemento.mostrarTipoMedida = "";
							    elemento.mostrarUnidadMedida = "";
							    elemento.optionsTextId=true;
							    elemento.usarMismoIdYNombre=true;
							    elementsInfo.addFieldElementInfo(elemento);
						    </script><th style="">
							<bean:message key="archigest.archivo.desde"/>
							<input type="hidden" name="campo_201_idtabla" value="102"/></th><script>
							    elemento = new ElementInfo();
							    elemento.id = "202";
							    //elemento.id = "rangoFinal";
							    elemento.type = "1";
							    elemento.style = "";
							    elemento.mandatory = "N";
							    elemento.initialValue = "";
							    elemento.defaultOptionValue = "";
							    elemento.defaultFormatValue = "";
							    elemento.refType = "";
							    elemento.defaultTipoMedida = "";
							    elemento.defaultUnidadMedida = "";
							    elemento.mostrarTipoMedida = "";
							    elemento.mostrarUnidadMedida = "";
							    elemento.optionsTextId=true;
							    elemento.usarMismoIdYNombre=true;
							    elementsInfo.addFieldElementInfo(elemento);
				    </script> <th style="">
						<bean:message key="archigest.archivo.hasta"/>					
						<input type="hidden" name="campo_202_idtabla" value="102"/></th></tr>
				</thead>
			<tbody></tbody>		
		</table>					      
	 </td>
</TR>

<script>
<c:if test="${!empty rangosUDoc}">
	<c:forEach var="rangoUDoc" items="${rangosUDoc}">
		javascript:addTableRow('102','TABLA');
	</c:forEach>
	<%-- Rangos Iniciales y Finales--%>
	var elementosRangoInicial = document.getElementsByName("campo_201");
	var elementosRangoFinal = document.getElementsByName("campo_202");
	if (elementosRangoInicial)
	{
 		var i = 0;
	 	<c:forEach var="rangoUDoc" items="${rangosUDoc}">
	  		elementosRangoInicial[i].value = "<c:out value="${rangoUDoc.desde}" escapeXml="false"/>";    	
	       	i = i + 1;
	    </c:forEach>
	}
	
	if (elementosRangoFinal)
	{
 		var j = 0;
	 	<c:forEach var="rangoUDoc" items="${rangosUDoc}">
	  		elementosRangoFinal[j].value = "<c:out value="${rangoUDoc.hasta}" escapeXml="false"/>";    	
	       	j = j + 1;
	    </c:forEach>
	}

</c:if> 
</script>
