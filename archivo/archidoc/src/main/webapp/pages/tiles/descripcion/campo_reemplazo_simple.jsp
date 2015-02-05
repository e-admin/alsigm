<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fichas" value="${requestScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
<c:set var="campos" value="${requestScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>
<c:set var="formasReemplazo" value="${requestScope[appConstants.fondos.LISTA_FORMAS_REEMPLAZO_SIMPLE_KEY]}"/>

<c:if test="${formasReemplazo == null}">
  <c:set var="formasReemplazo" value="${sessionScope[appConstants.fondos.LISTA_FORMAS_REEMPLAZO_SIMPLE_KEY]}"/>
</c:if>

<c:if test="${fichas == null}">
	<c:set var="fichas" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
</c:if>
<c:if test="${campos == null}">
	<c:set var="campos" value="${sessionScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>
</c:if>
		
<script language="JavaScript1.2" src='<c:url value="/js/advsearch.js" />' type="text/JavaScript"></script>
<script language="JavaScript1.2" src='<c:url value="/js/reemplazo.js" />' type="text/JavaScript"></script>
			
<script language="JavaScript1.2" type="text/JavaScript"><!--
var CAMPOS = [
      		<c:forEach var="campo" items="${campos}">
      			['<c:out value="${campo.id}"/>', '<c:out value="${campo.nombre}"/>', <c:out value="${campo.tipo}"/>, <c:out value="${campo.tipoReferencia}"/>, '<c:out value="${campo.idsListasDescriptoras}"/>',<c:out value="${campo.idTblPadre}"/>],
      		</c:forEach>
      			['-100', '<bean:message key="archigest.archivo.campo.descriptor"/>', 5, 1, '', '']
      		];

      		var TIPOSFECHA =[
      			['DDMMAAAA', '<bean:message key="archigest.archivo.formato.DDMMAAAA" />'],
      			['MMAAAA', '<bean:message key="archigest.archivo.formato.MMAAAA" />'],
      			['AAAA', '<bean:message key="archigest.archivo.formato.AAAA" />'],
      			['S', '<bean:message key="archigest.archivo.formato.S" />']						
      		];


      	      var FORMAS_REEMPLAZO_GENERICAS =  [  
      	                [REEMPLAZO_VALORES_EXACTOS, '<bean:message key="archigest.archivo.forma.reemplazo.0" />'],
      	      			[REEMPLAZO_VALORES_NULOS, '<bean:message key="archigest.archivo.forma.reemplazo.2" />']
      	      ];
      	              	  
      	      var FORMAS_REEMPLAZO_TEXTO = [
        	                [REEMPLAZO_VALORES_EXACTOS, '<bean:message key="archigest.archivo.forma.reemplazo.0" />'],
         	      			[REEMPLAZO_VALORES_PARCIALES, '<bean:message key="archigest.archivo.forma.reemplazo.1" />'],
        	                [REEMPLAZO_VALORES_NULOS, '<bean:message key="archigest.archivo.forma.reemplazo.2" />']
      	                          	      
      	      ];

      	      var FORMAS_REEMPLAZO_GENERICAS_CON_TABLA =  [  
       	                [REEMPLAZO_VALORES_EXACTOS, '<bean:message key="archigest.archivo.forma.reemplazo.0" />']
       	      ];
       	              	  
       	      var FORMAS_REEMPLAZO_TEXTO_CON_TABLA = [
         	                [REEMPLAZO_VALORES_EXACTOS, '<bean:message key="archigest.archivo.forma.reemplazo.0" />'],
          	      			[REEMPLAZO_VALORES_PARCIALES, '<bean:message key="archigest.archivo.forma.reemplazo.1" />']       	                          	      
       	      ];	


function buscarElementos(){
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		form.submit();
	}
	
	function cambioFicha(){
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		form.method.value = '<c:out value="${methodLoadFicha}" />';
		form.submit();
	}
	
	function hideAllDivs(){
		showSelects();
		xHide("busqDescriptores");
	}
	
	function showBusqDescDiv(){
		var busqDescriptores = xGetElementById('busqDescriptores');
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();
		xHide(busqDescriptores);
		xDisplay(busqDescriptores, 'block');
		var top = (clientHeight - xHeight(busqDescriptores))/2;
		var left = (clientWidth - xWidth(busqDescriptores))/2;
		xMoveTo(busqDescriptores, left, top);
		hideSelects();
		xShow(busqDescriptores);
	}
	
	var ordenBusquedaDescriptor=1;
	
	function selectDescriptor(id, nombre){
		hideAllDivs();
		var idTd="valorBuscar";
		if(ordenBusquedaDescriptor==3){
			idTd="valorNuevo";
		}
		
		var tdValor = document.getElementById(idTd);
		var inputs = tdValor.getElementsByTagName("input");
		for (var i = 0; i < inputs.length; i++){
			if (inputs[i].name == "valor"+ordenBusquedaDescriptor)
				inputs[i].value = id;
			else if (inputs[i].name == "nombreDesc"+ordenBusquedaDescriptor)
				inputs[i].value = nombre;
		}
	}

	function selectElement(id, nombre){
		selectDescriptor(id, nombre);
	}

	function showSearchObjectForm(orden){
		ordenBusquedaDescriptor=orden;
		var select = document.getElementsByName("campo")[0];
		var idCampo = select[select.selectedIndex].value;
		var campo = null;
		for (var i = 0; campo == null && i < CAMPOS.length; i++) {
			if (idCampo == CAMPOS[i][0]) {
				campo = CAMPOS[i];
			}
		}
			
		if (campo != null) {
			var form = document.forms['<c:out value="${actionMappingName}" />'];
			var frame = document.getElementById("frmSeleccionDescriptor");
			if (frame) {
			    if (campo[3] == 1 || campo[3] == 3) {
			        frame.src = "<c:url value="/action"/>/descriptores?method=formBusqDescriptor&idsListas=" + campo[4];
			    } else if (campo[3] == 2) {
			    	frame.src = "<c:url value="/action"/>/elementos?method=formBusquedaElem";
		        }
			}
			showBusqDescDiv();
		}
	}

		function cambioEnCampoSinOperador(select){
			var tdForma = document.getElementById("valorFormaReemplazo");
			var tdValor = document.getElementById("valorBuscar");
			var tdValorNuevo = document.getElementById("valorNuevo");
			var idCampo = select.options[select.selectedIndex].value;
			var infoCampo = getInfoCampo(idCampo);
			var inputTipoCampo = select.parentNode.getElementsByTagName("input").item(0);

			var contentformasReemplazo = ""; 

			if(infoCampo[5] != null)
				contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_GENERICAS_CON_TABLA , document.getElementById("formaReemplazo").value);
			else
				contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_GENERICAS , document.getElementById("formaReemplazo").value);
			
			//var contentValor1="<input type='text' name='ini' class='input90'/>";
			//var contentValor3="<input type='text' name='ini3' class='input90'/>";
			var contentValor1 = "<input type='text' name='valor1' class='input90'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc1'/>";
			var contentValor3 = "<input type='text' name='valor3' class='input90'/><input type='hidden' name='formatoFecha3'><input type='hidden' name='valor3D'/><input type='hidden' name='valor3M'/><input type='hidden' name='valor3A'/><input type='hidden' name='valor3S'/><input type='hidden' name='nombreDesc2'/>";
			inputTipoCampo.value ="";
			document.getElementById("tipoCampoCambio").value="";
			document.getElementById("campoCambio").value=idCampo;
			document.getElementById("tipoReferencia").value=infoCampo[3];
			if(idCampo!=""){
				inputTipoCampo.value = infoCampo[2];
				document.getElementById("tipoCampoCambio").value=inputTipoCampo.value;
				//document.getElementById("campoCambio").value=idCampo;
				if (infoCampo[2] == 5){
					tdValor.style.width = "50px"; 
					tdValorNuevo.style.width = "50px";
					// contentValor1 = "<input type='text' name='nombreDesc' class='inputRO90' readonly='true'/>"
					//    + "<input type='hidden' name='valor1'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/>";
					contentValor1 = getCampos2(1, false, false, false, false, false, false, true);
					       
					contentValor1 += "<a href='javascript:showSearchObjectForm(1" 
					        + ")'><img src='../../pages/images/pixel.gif' width='2px' class='imgTextMiddle'/>"
					        + "<img src='../../pages/images/expand.gif' class='imgTextMiddle'/></a>";
					        
					contentValor3 = getCampos2(3, false, false, false, false, false, false, true);   
					contentValor3 += "<a href='javascript:showSearchObjectForm(3" 
					        + ")'><img src='../../pages/images/pixel.gif' width='2px' class='imgTextMiddle'/>"
					        + "<img src='../../pages/images/expand.gif' class='imgTextMiddle'/></a>";
				}else if (infoCampo[2] == 4 || infoCampo[2] == 1 || infoCampo[2] == 2){
					contentValor1 = "<input type='text' name='valor1' class='input90'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc1'/>";
					contentValor3 = "<input type='text' name='valor3' class='input90'/><input type='hidden' name='formatoFecha3'><input type='hidden' name='valor3D'/><input type='hidden' name='valor3M'/><input type='hidden' name='valor3A'/><input type='hidden' name='valor3S'/><input type='hidden' name='nombreDesc2'/>";

					if(infoCampo[2] == 1 || infoCampo[2]== 2){
						if(infoCampo[5] != null)
							contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_TEXTO_CON_TABLA , document.getElementById("formaReemplazo").value);
						else
							contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_TEXTO , document.getElementById("formaReemplazo").value);
					}
				}else{
					contentValor1 = getCamposByTipoFecha2(1);
					contentValor1 += "<input type='hidden' name='formatoFecha2' value=' '><input type='hidden' name='valor2D' value=' '/><input type='hidden' name='valor2M' value=' '/><input type='hidden' name='valor2A' value=' '/><input type='hidden' name='valor2S' value=' '/>";
					contentValor3 = getCamposByTipoFecha2(3);
					//contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2'/>-<input type='text' name='valor1M' size='2' maxlength='2'/>-<input type='text' name='valor1A' size='4' maxlength='4'/><input type='hidden' name='nombreDesc'/>";
				}
			}

			tdForma.innerHTML = contentformasReemplazo;
			tdValor.innerHTML = contentValor1;
			tdValorNuevo.innerHTML = contentValor3;

			document.getElementById("formaReemplazo").value = document.getElementById("selformaReemplazo").value;

			comprobarFormaReemplazo();
		}
		
		function getCamposByTipoFecha2(orden){
			var posicion = parseInt(document.getElementById(CAMPO_VALOR_TIPO_FECHA + orden).value,10);
			if(!posicion) posicion=0;
			var valor = TIPOSFECHA[posicion][0];
			var str;
		
			switch(valor){
				case SIGLO:
					str = getCampos2(orden,true, false, false, false, false, true, false);
					//str = "<input type='text' size='5' maxlength='5' name='valor"+ orden +"S'/><input type='hidden' name='valor"+ orden +"D' size='2' maxlength='2'/><input type='hidden' name='valor"+ orden +"M' size='2' maxlength='2'/><input type='hidden' name='valor"+ orden +"A' size='4' maxlength='4'/>";
					break;
				case ANIO:
						str = getCampos2(orden,true, false, false, false, true, false, false);
						//str = "<input type='hidden' name='valor"+ orden +"D' size='2' maxlength='2'/><input type='hidden' name='valor"+ orden +"M' size='2' maxlength='2'/><input type='text' name='valor"+ orden +"A' size='4' maxlength='4'/><input type='hidden' name='valor"+ orden +"S'/>";
						break;
				case MES_ANIO:
						str = getCampos2(orden,true, false, false, true, true, false, false);
						//str = "<input type='hidden' name='valor"+ orden +"D' size='2' maxlength='2'/><input type='text' name='valor"+ orden +"M' size='2' maxlength='2'/>-<input type='text' name='valor"+ orden +"A' size='4' maxlength='4'/><input type='hidden' name='valor"+ orden +"S'/>";
						break;
				case DIA_MES_ANIO:
						str = getCampos2(orden,true, false, true, true, true, false, false);
						//str = "<input type='text' name='valor"+ orden +"D' size='2' maxlength='2'/>-<input type='text' name='valor"+ orden +"M' size='2' maxlength='2'/>-<input type='text' name='valor"+ orden +"A' size='4' maxlength='4'/><input type='hidden' name='valor"+ orden +"S'/>";
						break;
			}
			//str += "<input type='hidden' name='valor"+ orden +"'/><input type='hidden' name='nombreDesc'/>";
			return str;
		}
		
		function getCampos2(orden,mostrarFormato, mostrarValor, mostrarValorD, mostrarValorM, mostrarValorA, mostrarValorS, mostrarDesc){
			var CAMPO_FORMATOFECHA = CAMPO_TIPO_FECHA + String(orden) ;
			var CAMPO_VALOR 	= "valor"  + String(orden);
			var CAMPO_VALOR_D	= "valor"  + String(orden) + "D";
			var CAMPO_VALOR_M 	= "valor"  + String(orden) + "M";
			var CAMPO_VALOR_A	= "valor"  + String(orden) + "A";
			var CAMPO_VALOR_S	= "valor"  + String(orden) + "S";
			var CAMPO_NOMBREDESC= "nombreDesc" + String(orden);
		
			if(mostrarFormato){
				CAMPO_FORMATOFECHA = getSelectTipoFecha2(orden) + "&nbsp;";
			}else{
				CAMPO_FORMATOFECHA = getCampoHidden2(CAMPO_FORMATOFECHA);
			}
			
			if(mostrarValor){
				CAMPO_VALOR = getCampoTextoSimple2(CAMPO_VALOR);
			}else{
				CAMPO_VALOR = getCampoHidden2(CAMPO_VALOR);
			}	
			
			if(mostrarValorD){
				CAMPO_VALOR_D = getCampoTexto2(CAMPO_VALOR_D,2,2);
			}else{
				CAMPO_VALOR_D = getCampoHidden2(CAMPO_VALOR_D);
			}
		
			if(mostrarValorM){
				CAMPO_VALOR_M = getCampoTexto2(CAMPO_VALOR_M,2,2);
			}else{
				CAMPO_VALOR_M = getCampoHidden2(CAMPO_VALOR_M);
			}
			
			if(mostrarValorA){
				CAMPO_VALOR_A = getCampoTexto2(CAMPO_VALOR_A,4,4);
			}
			else{
				CAMPO_VALOR_A = getCampoHidden2(CAMPO_VALOR_A);
			}	
			
			if(mostrarValorS){
				CAMPO_VALOR_S = getCampoTexto2(CAMPO_VALOR_S,5,5);
			}else{
				CAMPO_VALOR_S = getCampoHidden2(CAMPO_VALOR_S);
			}
			
			if(mostrarDesc){
				CAMPO_NOMBREDESC = getCampoSoloLectura2(CAMPO_NOMBREDESC);
			}else{
				CAMPO_NOMBREDESC = getCampoHidden2(CAMPO_NOMBREDESC);
			}
			
			str = 	CAMPO_FORMATOFECHA + CAMPO_VALOR + CAMPO_VALOR_D;
		
			if(mostrarValorD && mostrarValorM){
				 str += SEPARADOR_FECHA;
			}
		
			str +=	CAMPO_VALOR_M;
		
			if(mostrarValorM && mostrarValorA) {
				str+= SEPARADOR_FECHA;
			}
		
			str += 	CAMPO_VALOR_A + CAMPO_VALOR_S + CAMPO_NOMBREDESC;
			
			//alert(str);		
			return str;
		}
		
		function getCampoHidden2(nombre){
			return "<input type='hidden' name='"+ nombre +"' id='"+ nombre + "'>";
		}

		function getCampoTexto2(nombre, tamanio, longitudMax){
			return "<input type='text' name='"+ nombre +"' id='"+ nombre +"' size='"+ tamanio + "' maxlength='"+ longitudMax +"'>";
		}
		
		function getCampoTextoSimple2(nombre){
			return "<input type='text' name='"+ nombre +"' id='"+ nombre +"' class='input90'>";
		}
		
		function getCampoSoloLectura2(nombre){
			return "<input type='text' name='"+ nombre +"' id='"+ nombre +"' class='inputRO90' readonly='true'>";
		}
		
		function getSelectTipoFecha2(orden){
			var nombre = CAMPO_TIPO_FECHA + String(orden);
		
			var nombreCampo = CAMPO_VALOR_TIPO_FECHA + String(orden);
			var posicionSeleccionada = parseInt(document.getElementById(nombreCampo).value,10);
			
			var str = "<select name='" + nombre +"' id='"+ nombre +"' onchange='cambioTipoFecha(this,"+ orden + ")'>";
				for(var i=0;i<TIPOSFECHA.length;i++){
					var valor = TIPOSFECHA[i][0];
					var texto = TIPOSFECHA[i][1];
					str += "<option value='"+ valor +"'";
					
					if(i == posicionSeleccionada){
						str += " SELECTED";
					}
					str += ">"+ texto +"</option>";
				}
			str += "</select>";
		
			return str;
		}
		
	function cambioTipoFecha(select,orden){
		var tdValor=document.getElementById("valorBuscar");
		if(orden==3) tdValor=document.getElementById("valorNuevo");
	
		document.getElementById(CAMPO_VALOR_TIPO_FECHA  + orden).value = select.selectedIndex;
	
		//selectOperador = document.getElementById(CAMPO_TIPO_OPERADOR);
		//if(orden == 1 && selectOperador.value != "[..]" && selectOperador.value != "EX" && selectOperador.value != "QCN"){
		//	document.getElementById(CAMPO_VALOR_TIPO_FECHA  + "2" + SEPARADOR + fila).value = select.selectedIndex;
		//}
		
		var contenido = getCamposByTipoFecha2(orden);
		contenido += "<input type='hidden' name='idRelacionesDesc"+orden+"' value=''/>";
		if(orden==1) contenido += "<input type='hidden' name='formatoFecha2' value=' '><input type='hidden' name='valor2D' value=' '/><input type='hidden' name='valor2M' value=' '/><input type='hidden' name='valor2A' value=' '/><input type='hidden' name='valor2S' value=' '/>";
		tdValor.innerHTML = contenido;
	}

	function comprobarFormaReemplazo(){
		 var selectFormas = document.getElementById("selformaReemplazo");


		 var filaAOcultar = document.getElementById("trValorActual");

		//Ocultar Todas
		xDisplay(filaAOcultar,'none');

		document.getElementById("formaReemplazo").value = selectFormas.value;
		 if(selectFormas){
			var valor = selectFormas.value;

			switch (valor){
				case REEMPLAZO_VALORES_EXACTOS:
					if(document.all) xDisplay(filaAOcultar,'block');
					else xDisplay(filaAOcultar,'table-row');
					document.getElementById("operador").value="=";
					break;

				case REEMPLAZO_VALORES_PARCIALES:
					if(document.all) xDisplay(filaAOcultar,'block');
					else xDisplay(filaAOcultar,'table-row');
					document.getElementById("operador").value="QC";
					break;

				case REEMPLAZO_VALORES_NULOS:
					document.getElementById("operador").value="=";
					break;
			}
	 	}
	}

	function inicio(){
		var select=document.forms['<c:out value="${actionMappingName}" />'].campo;
		var optionValue='';
	
		for (var i=0; i<select.options.length;i++){
			if(select.options.item(i).value=='<c:out value="${busquedaReemplazosForm.campo[0]}"/>'){
				select.options.item(i).selected = true;
				optionValue=select.options.item(i).value;
				break;
			}	
		}	
		if(optionValue!='')
			cambioEnCampoSinOperador(select);
			
		var selectFormatoFecha1=document.forms['<c:out value="${actionMappingName}" />'].formatoFecha1;
		if(selectFormatoFecha1){
			selectFormatoFecha1.value='<c:out value="${busquedaReemplazosForm.formatoFecha1[0]}"/>';
			if(selectFormatoFecha1.value!='' && selectFormatoFecha1.value!='') 
				cambioTipoFecha(selectFormatoFecha1,1);
		}
		
		var selectFormatoFecha3=document.forms['<c:out value="${actionMappingName}" />'].formatoFecha3;
		if(selectFormatoFecha3){
			selectFormatoFecha3.value='<c:out value="${busquedaReemplazosForm.formatoFecha3}"/>';
			if(selectFormatoFecha3.value!='' && selectFormatoFecha3.value!=' ' ) 
				cambioTipoFecha(selectFormatoFecha3,3);
		}
			
		document.forms['<c:out value="${actionMappingName}" />'].valor1.
			value='<c:out value="${busquedaReemplazosForm.valor1[0]}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor1A.
			value='<c:out value="${busquedaReemplazosForm.valor1A[0]}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor1M.
			value='<c:out value="${busquedaReemplazosForm.valor1M[0]}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor1D.
			value='<c:out value="${busquedaReemplazosForm.valor1D[0]}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor1S.
			value='<c:out value="${busquedaReemplazosForm.valor1S[0]}"/>';
			
			
		document.forms['<c:out value="${actionMappingName}" />'].valor3.
			value='<c:out value="${busquedaReemplazosForm.valor3}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor3A.
			value='<c:out value="${busquedaReemplazosForm.valor3A}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor3M.
			value='<c:out value="${busquedaReemplazosForm.valor3M}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor3D.
			value='<c:out value="${busquedaReemplazosForm.valor3D}"/>';
		document.forms['<c:out value="${actionMappingName}" />'].valor3S.
			value='<c:out value="${busquedaReemplazosForm.valor3S}"/>';
			
		document.forms['<c:out value="${actionMappingName}" />'].nombreDesc1.
			value='<c:out value="${busquedaReemplazosForm.nombreDesc1[0]}"/>';
		
		if(document.forms['<c:out value="${actionMappingName}" />'].nombreDesc3)
			document.forms['<c:out value="${actionMappingName}" />'].nombreDesc3.
				value='<c:out value="${busquedaReemplazosForm.nombreDesc3}"/>';

		//cargar la lista con valores por defecto
		if(document.getElementById("campo_0").value != ""){
			var contentformasReemplazo ="";
			var idCampo = select.options[select.selectedIndex].value;
			var infoCampo = getInfoCampo(idCampo);
			if(infoCampo[2] == 1 || infoCampo[2]== 2){
				if(infoCampo[5] != null)
					contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_TEXTO_CON_TABLA , document.getElementById("formaReemplazo").value);
				else
					contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_TEXTO , document.getElementById("formaReemplazo").value);
			}else{
				if(infoCampo[5] != null)
					contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_GENERICAS_CON_TABLA ,document.getElementById("formaReemplazo").value );
				else
					contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_GENERICAS ,document.getElementById("formaReemplazo").value );
			}

			var tdForma = document.getElementById("valorFormaReemplazo");
			tdForma.innerHTML = contentformasReemplazo;
		}

		
		comprobarFormaReemplazo();				
	}



	</script>

	<html:hidden property="contadorDetallesAvanzados" styleId="contadorDetallesAvanzados" />
	<html:hidden property="operador" styleId="operador"/>
	<html:hidden property="cerrarpar" styleId="cerrarpar" value=""/>
	<html:hidden property="abrirpar" styleId="abrirpar" value=""/>
	<html:hidden property="booleano" styleId="booleano" value=""/>
	<html:hidden property="campoCambio" styleId="campoCambio" value=""/>
	<html:hidden property="tipoCampoCambio" styleId="tipoCampoCambio" value=""/>
	<html:hidden property="tipoReferencia" styleId="tipoReferencia" value=""/>

	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" width="170px" nowrap="nowrap">
			<bean:message key="archigest.archivo.cf.ficha"/>:&nbsp;
		</td>
		<td class="<c:out value="${classTdCampo}"/>" style="border-bottom: 0;">
			<html:select styleId="idFicha" property="idFicha" onchange="javascript:cambioFicha()">
				<option value=""><bean:message key="archigest.descripcion.reemplazo.fichas.todosLosCampos"/></option>
				<html:optionsCollection name="fichas" label="nombre" value="id"/>
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>"  nowrap="nowrap">
			<bean:message key="archigest.descripcion.reemplazo.campo"/>:&nbsp;
			<input type='hidden' size='1' name='formatoFechaSel1' id='formatoFechaSel1' value='0'/>
		</td>
		<td class="<c:out value="${classTdCampo}"/>" style="border-bottom:0" nowrap="nowrap">
			<select name="campo" id="campo_0" onchange="javascript:cambioEnCampoSinOperador(this)">
				<option value=""></option>
				<c:forEach var="campo" items="${campos}">
					<option value="<c:out value="${campo.id}"/>"><c:out value="${campo.nombre}"/></option>
				</c:forEach>
			</select>
			<input type="hidden" name="tipoCampo" id="tipoCampo"/>
		</td>
	</tr>
	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;">
			<bean:message key="archigest.archivo.reemplazar.forma.reemplazo"/>:&nbsp;
			<html:hidden property="formaReemplazo" styleId="formaReemplazo"/>
		</td>
		<td class="<c:out value="${classTdCampo}"/>" id="valorFormaReemplazo" nowrap="nowrap" style="border-bottom:0">
			<select name="selformaReemplazo" id="selformaReemplazo">
			</select>
		</td>
	</tr>
	<tr id="trValorActual">
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" id="etiquetaValorBuscar">
			<bean:message key="archigest.descripcion.reemplazo.valor.actual"/>:&nbsp;
		</td>								
		<td class="<c:out value="${classTdCampo}"/>" id="valorBuscar" nowrap="nowrap" style="border-bottom:0">
			<input type="text" name="ini" id="ini" class="input90">
			<input type="hidden" name="formatoFecha1" id="formatoFecha1">
			<input type="hidden" name="valor1" id="valor1"/>
			<input type="hidden" name="valor1D" id="valor1D"/>
			<input type="hidden" name="valor1M" id="valor1M"/>
			<input type="hidden" name="valor1A" id="valor1A"/>
			<input type="hidden" name="valor1S" id="valor1S"/>

			<input type="hidden" name="nombreDesc1" id="nombreDesc1"/>
			<input type="hidden" name="idRelacionesDesc1" id="idRelacionesDesc1"/>
		</td>
	</tr>
	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;">
			<bean:message key="archigest.descripcion.reemplazo.valor.nuevo"/>:&nbsp;
			<input type='hidden' size='1' name='formatoFechaSel3' id='formatoFechaSel3' value='0'/>							
		</td>	
		<td class="<c:out value="${classTdCampo}"/>" id="valorNuevo" nowrap="nowrap" style="border-bottom:0">
			<input type="text" name="ini3" id="ini3" class="input90"/>
			<input type="hidden" name="formatoFecha3" id="formatoFecha3"/>
			<input type="hidden" name="valor3" id="valor3"/>
			<input type="hidden" name="valor3D" id="valor3D"/>
			<input type="hidden" name="valor3M" id="valor3M"/>
			<input type="hidden" name="valor3A" id="valor3A"/>
			<input type="hidden" name="valor3S" id="valor3S"/>


			<input type="hidden" name="nombreDesc3" id="nombreDesc3"/>
			<input type="hidden" name="idRelacionesDesc3" id="idRelacionesDesc3"/>
		</td>
	</tr>
	<script language="javascript">
		inicio();
	</script>
