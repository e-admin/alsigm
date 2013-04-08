<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="seleccionMultiple" value="${sessionScope[appConstants.descripcion.SELECCION_MULTIPLE_KEY]}"/>
<c:set var="listas" value="${sessionScope[appConstants.descripcion.LISTAS_DESCRIPTORAS_KEY]}"/>
<c:set var="descriptores" value="${requestScope[appConstants.descripcion.DESCRIPTORES_LISTA_KEY]}"/>
<bean:struts id="actionMapping" mapping="/descriptores" />

<script language="JavaScript1.2" type="text/JavaScript"><!--
	function select(id, tipo, nombre)
	{
		var ref = document.forms["<c:out value="${actionMapping.name}" />"].elements["ref"].value;
		if (opener.document.getElementById(ref))
		{
			if(opener.document.getElementById(ref + "_idref"))
				opener.document.getElementById(ref + "_idref").value = id;
			if(opener.document.getElementById(ref + "_tiporef"))
				opener.document.getElementById(ref + "_tiporef").value = tipo;
			if(opener.document.getElementById(ref))
				opener.document.getElementById(ref).value = nombre;
		}

		window.close();
	}
	function selectList()
	{
		var field = document.forms["<c:out value="${actionMapping.name}" />"].field;
		if ((field!=null)&&(field.value == 'productores'))
			selectDescriptores = opener.document.getElementById("selectProductores");
		else
			selectDescriptores = opener.document.getElementById("selectDescriptores");
		if (selectDescriptores)
		{
			selectElem = document.forms["<c:out value="${actionMapping.name}" />"].descrCombo;
			for (var i = 0; i < selectElem.options.length; i++)
			{

				if ((field!=null)&&(field.value == 'productores')){
					if (opener.addOptionProductores)
						opener.addOptionProductores(selectElem.options[i].text, selectElem.options[i].value);
				} else {
					if (opener.addOption)
						opener.addOption(selectElem.options[i].text, selectElem.options[i].value);
				}
			}
		}

		window.close();
	}
	function search()
	{
		selectElem = document.forms["<c:out value="${actionMapping.name}" />"].descrCombo;
		listDescrSel = document.forms["<c:out value="${actionMapping.name}" />"].listDescrSel;
		if (listDescrSel)
		{
			listDescrSel.value = "";
			for (var i = 0; i < selectElem.options.length; i++)
			{
				if (i > 0)
					listDescrSel.value += "$$";
				listDescrSel.value += selectElem.options[i].value + "##" + selectElem.options[i].text;
			}
		}

		document.forms["<c:out value="${actionMapping.name}" />"].method.value = "search";
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
	function addOption(name, label, value)
	{
	    var selectElem = document.forms["<c:out value="${actionMapping.name}" />"].elements[name];
	    if (selectElem)
	    	selectElem.options[selectElem.options.length] = new Option(label, value);
	}
	function removeOptions(name)
	{
	    var selectElem = document.forms["<c:out value="${actionMapping.name}" />"].elements[name];
		if (selectElem)
		{
	    	for (var i = selectElem.options.length - 1; i >= 0; i--)
	    		if (selectElem.options[i].selected)
	    			selectElem.remove(i);
		}
	}
	function removeAllOptions(name)
	{
	    var selectElem = document.forms["<c:out value="${actionMapping.name}" />"].elements[name];
		if (selectElem)
		{
	    	while (selectElem.options.length > 0)
	    		selectElem.remove(0);
		}
	}

	function addDescriptor(){
		if(document.getElementById("idLista").value == ""){
			alert('<bean:message key="archigest.archivo.descripcion.descriptores.create.lista.required"/>')
			return;
		}
		else if(document.getElementById("nombre").value== ""){
			alert('<bean:message key="archigest.archivo.descripcion.descriptores.create.nombre.required"/>')
			return;
		}

		if(window.confirm('<bean:message key="archigest.archivo.descripcion.descriptores.create.confirm.msg"/>')){
			document.getElementById("method").value="crearDescriptor";
			document.getElementById("formulario").submit();
		}
	}
</script>

<div id="contenedor_ficha_centrada">
  <html:form action="/descriptores" styleId="formulario">
  <input type="hidden" name="method" id="method" value="search"/>
  <html:hidden property="ref"/>
  <html:hidden property="refIdsLists"/>
  <html:hidden property="refType"/>
  <html:hidden property="field"/>

  <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
		  	  <bean:message key="archigest.archivo.descripcion.descriptores.caption"/>
            </td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
					<td>
						<a class="etiquetaAzul12Bold"
                         	href="javascript:addDescriptor()"><html:img page="/pages/images/descriptores/add.gif"
                              altKey="archigest.archivo.crear"
                              titleKey="archigest.archivo.crear"
                              styleClass="imgTextMiddle" />
                              <bean:message key="archigest.archivo.crear"/>
                          </a>
					</td>
					<td width="50px">&nbsp;</td>

                  <c:if test="${not empty listas}">
	                  <c:if test="${not seleccionMultiple}">
	                  <td><a class="etiquetaAzul12Bold"
	                         href="javascript:selectList()"
	                      ><html:img page="/pages/images/Ok_Si.gif"
	                              altKey="archigest.archivo.aceptar"
	                              titleKey="archigest.archivo.aceptar"
	                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.aceptar"/></a></td>
	                  <td width="10">&nbsp;</td>
	                  </c:if>
                  </c:if>

                  <td><a class="etiquetaAzul12Bold"
                         href="javascript:window.close()"
                      ><html:img page="/pages/images/close.gif"
                              altKey="archigest.archivo.cerrar"
                              titleKey="archigest.archivo.cerrar"
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </span></h1>
    <div class="cuerpo_drcha">
      <div class="cuerpo_izda">
    	 <div id="barra_errores"><archivo:errors /></div>
         <div class="separador1">&nbsp;</div>

		<c:if test="${not empty listas}">
		 <table class="formulario">
		   <tr valign="top">
		     <td width="50%">
				 <div class="bloque">
				     <table class="w98">
				       <tr>
				         <td class="etiquetaAzul11Bold" width="120px">
					       <bean:message key="archigest.archivo.descripcion.descriptor.form.nombreLista"/>:&nbsp;
				         </td>
				         <td>
		 		           <html:select property="idLista" styleId="idLista">
				             <html:optionsCollection name="listas" label="nombre" value="id"/>
				           </html:select>
				         </td>
				       </tr>
				       <tr>
				         <td class="etiquetaAzul11Bold" width="120px">
					       <bean:message key="archigest.archivo.descripcion.descriptor.form.nombre"/>:&nbsp;
				         </td>
				         <td>
		 		           <html:text property="nombre" styleId="nombre" styleClass="input80" maxlength="512"/>&nbsp;
			                  <c:if test="${not empty listas}">
				                  <td><a class="etiquetaAzul12Bold"
				                         href="javascript:search()"
				                      ><html:img page="/pages/images/buscar.gif"
				                              altKey="archigest.archivo.buscar"
				                              titleKey="archigest.archivo.buscar"
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.buscar"/></a></td>
				                  <td width="10">&nbsp;</td>
			                  </c:if>

				         </td>
				       </tr>
				     </table>

				   <c:if test="${descriptores != null}">
					<display:table name="pageScope.descriptores"
						style="width:99%;margin-left:auto;margin-right:auto"
						id="descriptor"
						pagesize="10"
						sort="list"
						requestURI="/action/descriptores"
						export="false">
						<display:setProperty name="basic.msg.empty_list">
						  <bean:message key="archigest.archivo.descripcion.descriptores.vacio"/>
						</display:setProperty>
						<display:column titleKey="archigest.archivo.nombre" property="nombre" sortable="true" headerClass="sortable" />
						<display:column titleKey="archigest.archivo.descriptor.validado" style="width:15px;">
						  <c:choose>
						    <c:when test="${descriptor.estado == 1}">
						    <html:img page="/pages/images/checkbox-yes.gif"
						        altKey="archigest.archivo.transferencias.validado"
		  				        titleKey="archigest.archivo.transferencias.validado"
						        styleClass="imgTextMiddle"/>
						    </c:when>
						    <c:otherwise>
						      <html:img page="/pages/images/checkbox-no.gif"
						        altKey="archigest.archivo.transferencias.noValidado"
		  				        titleKey="archigest.archivo.transferencias.noValidado"
						        styleClass="imgTextMiddle"/>
						    </c:otherwise>
						  </c:choose>
						</display:column>
						<display:column title="" style="width:15px;">
						  <c:choose>
						  	<c:when test="${seleccionMultiple}">
							  <a href="javascript:addOption('descrCombo','<bean:write name="descriptor" property="nombre"/>','<bean:write name="descriptor" property="id"/>')"
							    ><html:img page="/pages/images/add_next.gif"
							    altKey="archigest.archivo.seleccionar"
							    titleKey="archigest.archivo.seleccionar"
							    styleClass="imgTextMiddle"/></a>
						  	</c:when>
						  	<c:otherwise>
							  <a href="javascript:select('<bean:write name="descriptor" property="id"/>', '<bean:write name="descriptor" property="tipo"/>', '<bean:write name="descriptor" property="nombre"/>')"
							    ><html:img page="/pages/images/add_next.gif"
							    altKey="archigest.archivo.seleccionar"
							    titleKey="archigest.archivo.seleccionar"
							    styleClass="imgTextMiddle"/></a>
						  	</c:otherwise>
						  </c:choose>
						</display:column>
					</display:table>
				   </c:if>
				 </div>
			   </td>


			   	 <c:if test="${seleccionMultiple}">
			     <td>
			     	<div class="bloque">
					     <table class="formulario">
					       <tr>
					         <td class="tdTitulo" nowrap="nowrap">
					         	<bean:message key="archigest.archivo.busqueda.form.descriptores"/>
							 </td>
					         <td class="tdTitulo" style="text-align:right; height:24px;">
								  <a href="javascript:removeOptions('descrCombo')"
								    ><html:img page="/pages/images/clear0.gif"
								    altKey="archigest.archivo.limpiarSel"
								    titleKey="archigest.archivo.limpiarSel"
								    styleClass="imgTextMiddle"/></a>
								  &nbsp;
								  <a href="javascript:removeAllOptions('descrCombo')"
								    ><html:img page="/pages/images/clear_all.gif"
								    altKey="archigest.archivo.limpiarTodos"
								    titleKey="archigest.archivo.limpiarTodos"
								    styleClass="imgTextMiddle"/></a>
								  &nbsp;
					         </td>
					       </tr>
					       <tr>
					       	 <td colspan="2">
					       	 	<select id="descrCombo" name="descrCombo" multiple style="width:100%;height:200px;">
					       	 		<c:set var="listDescrSel" value="${param['listDescrSel']}"/>
					       	 		<c:forTokens var="descrComboValue" items="${listDescrSel}" delims="$$">
					       	 			<jsp:useBean id="descrComboValue" type="java.lang.String"/>
										<option value="<%=descrComboValue.substring(0, descrComboValue.indexOf("##"))%>">
					       	 				<%=descrComboValue.substring(descrComboValue.indexOf("##")+2)%>
				       	 				</option>
					       	 		</c:forTokens>
					       	 	</select>
					       	 	<html:hidden property="listDescrSel" />
					       	 </td>
					       </tr>
					     </table>
			     	</div>
			     </td>
			     </c:if>

			 </tr>
		   </table>
		   </c:if>
      </div><%-- cuerpo_izda --%>
    </div><%-- cuerpo_drcha --%>
    <h2><span>&nbsp;</span></h2>
  </div><%-- ficha --%>
  </html:form>
</div><%-- contenedor_ficha --%>
