<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<div class="ficha">

<div class="cuerpo_drcha" style="border-bottom:1px solid #798AB2;">
<div class="cuerpo_izda">

	

	<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
	</div>

		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TBODY>
			  <TR>
			    <TD class="etiquetaAzul12Bold" width="50%">
					Motivo seleccionado:&nbsp;
				</TD>
			  </TR></TBODY></TABLE>
		</div>

		<div class="bloque">
			<div width="98%" id="seleccion" style="text-align:left; margin-left:2%; white-space:normal;">
				<div class="separador5">&nbsp;</div>
				<div id="procedimiento" class="etiquetaAzul11Bold" width="98%"> Motivo: 
					<span id="codigoProcedimiento" class="etiquetaNegra11Normal"><c:out value="${__PROCEDIMIENTO_SELECCIONADO.codigo}" /></span>
					&nbsp;&nbsp;<span id="nombreProcedimiento" class="etiquetaNegra11Normal"><c:out value="${__PROCEDIMIENTO_SELECCIONADO.nombre}" /></span>
				</div>
				
				<div class="separador5">&nbsp;</div>
			</div>
		</div>
	<script language="JavaScript1.2" src="<c:url value="/js/x_core.js" />" type="text/JavaScript"></script>
	<script>

		function selectItem(item) {
			
			xGetElementById('nombreProcedimiento').innerHTML = item.getAttribute('procedimiento');
			xGetElementById('codigoProcedimiento').innerHTML = item.id;
		}

		var RowSel="";
		function SetRowSel(fila)
		{
			if (fila != RowSel)
			{
			if ((RowSel != "")) {
				if (document.getElementById(RowSel).parentNode.parentNode.className == 'even'	)
					document.getElementById(RowSel).style.background = "#D8D8D8";
				else
					document.getElementById(RowSel).style.background = "#FFFFFF";
			}
			RowSel = fila;
			document.getElementById(RowSel).style.background = "#B0B0C6";
			}
		}
	
	</script>

	<style>
	table.its tr.even {
	    background-color: #D8D8D8;
	}
	</style>

	<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
	</div>

	<div class="bloque">

	<display:table name='__LISTA_PROCEDIMIENTOS'  
		style="width:99%;margin-left:auto;margin-right:auto" 
		id="procedimiento" 
		pagesize="10" 
		export="false">
		<display:column titleKey="archigest.archivo.procedimientosAdministrativos"> 
			<div id='<c:out value="${procedimiento.codigo}" />' 
					procedimiento='<c:out value="${procedimiento.nombre}" />' 
					serie='<c:out value="${procedimiento.serie}" />' 
					funcion='<c:out value="${procedimiento.funcion}" />'
					codigoProductor='<c:out value="${procedimiento.sistemaProductor}" />' 
					productor='<c:out value="${procedimiento.nombreSistemaProductor}" />'
					onclick='selectItem(this);SetRowSel(this.id);' style='padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
				<c:out value="${procedimiento.codigo}" />&nbsp;&nbsp;
				<c:out value="${procedimiento.nombre}" /></div>
		
		</display:column>
	</display:table>

	</div>

	<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
	</div>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

</div> <%--ficha --%>
