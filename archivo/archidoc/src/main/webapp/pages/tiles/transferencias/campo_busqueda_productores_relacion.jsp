<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" type="text/JavaScript"><!--
	function popupProductores(field)
	{
		popup("<c:url value="/action/descriptores"/>?method=showBusquedaProductoresRelacion&field=" + field, "_blank");
	}
	function cleanProductores()
	{
		var form = document.forms[0];
		selectElem = form.elements["selectProductores"];
		for (var i = selectElem.options.length - 1; i >= 0; i--)
			if (selectElem.options[i].selected)
				selectElem.remove(i);
		concatIdsProductores();
	}
	function cleanAllProductores()
	{
		var form = document.forms[0];
		selectElem = form.elements["selectProductores"];
		if ((selectElem != null) && (selectElem != '') && (selectElem != 'undefined')) {
			while (selectElem.options.length > 0)
				selectElem.remove(0);
		}
		concatIdsProductores();
	}
	function concatIdsProductores()
	{
		var form = document.forms[0];
		var elem = form.productoresSeleccionados;
		if (elem)
		{
			var value = "";
			selectElem = form.elements["selectProductores"];
			for (var i = 0; i < selectElem.options.length; i++)
			{
				if (i > 0)
					value += "#";
				value += selectElem.options[i].value + ":" 
					+ selectElem.options[i].text;
			}
			elem.value = value;
		}
	}

	var i=0;
	
--></script>

<tr>
	<td class="tdTituloFicha"><bean:message key="archigest.archivo.busqueda.form.productor"/>:&nbsp;</td>
	<td class="tdDatosFicha">
		<table cellpadding="0" cellspacing="0">
			<tr valign="top">
				<td width="500">
					<html:hidden property="productoresSeleccionados"/>
					<select id="selectProductores" multiple="multiple" style="width:100%;height: 60px">
						<c:forTokens var="prodComboValue" items="${itemsProductores}" delims="#">
		       	 			<jsp:useBean id="prodComboValue" type="java.lang.String"/>
		       	 			<option value="<%=prodComboValue.substring(0, prodComboValue.indexOf(":"))%>">
		       	 				<%=prodComboValue.substring(prodComboValue.indexOf(":")+1)%>
	       	 				</option>
		       	 		</c:forTokens>
					</select>
					<script>
						function addOptionProductores(label,value)
						{
							selectElem = document.getElementById("selectProductores");
							selectElem.options[selectElem.options.length] = new Option(label, value);
							concatIdsProductores();
						}
					</script>
				</td>
				<td width="10"></td>
				<td><a href="javascript:popupProductores('productoresSeleccionados');"
				    ><html:img page="/pages/images/buscar.gif" 
					        altKey="archigest.archivo.buscar"
					        titleKey="archigest.archivo.buscar"
					        styleClass="imgTextMiddle"/></a>
					<a href="javascript:cleanProductores();"
					><html:img page="/pages/images/clear0.gif" 
					        altKey="archigest.archivo.limpiar"
					        titleKey="archigest.archivo.limpiar"
					        styleClass="imgTextMiddle"/></a>
					<a href="javascript:cleanAllProductores();"
					><html:img page="/pages/images/clear_all.gif" 
					        altKey="archigest.archivo.limpiarTodos"
					        titleKey="archigest.archivo.limpiarTodos"
					        styleClass="imgTextMiddle"/></a>
				</td>
			</tr>
		</table>
	</td>
</tr>
