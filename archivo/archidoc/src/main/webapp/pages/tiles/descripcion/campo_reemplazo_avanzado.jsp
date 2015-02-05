<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fichas" value="${requestScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
<c:set var="campos" value="${requestScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>

<c:if test="${fichas == null}">
  <c:set var="fichas" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
</c:if>
<c:if test="${campos == null}">
  <c:set var="campos" value="${sessionScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>
</c:if>
<script language="JavaScript1.2" src='<c:url value="/js/reemplazo.js" />' type="text/JavaScript"></script>
<script language="JavaScript1.2" src='<c:url value="/js/advsearch.js" />' type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
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
    var select = document.getElementsByName("campoCambio");
    var idCampo = select.item(select.selectedIndex).value;
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
    var CAMPOS = [
    <c:forEach var="campo" items="${campos}">
      ['<c:out value="${campo.id}"/>', '<c:out value="${campo.nombre}"/>', <c:out value="${campo.tipo}"/>, <c:out value="${campo.tipoReferencia}"/>, '<c:out value="${campo.idsListasDescriptoras}"/>', <c:out value="${campo.idTblPadre}"/>],
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

	  //var contentvalor4="<input type='text' name='ini1'/>";
      //var contentValor3="<input type='text' name='ini3'/>";
	  var contentvalor4 = "<input type='text' name='valor4' class='input90'/><input type='hidden' name='formatoFecha4'><input type='hidden' name='valor4D'/><input type='hidden' name='valor4M'/><input type='hidden' name='valor4A'/><input type='hidden' name='valor4S'/><input type='hidden' name='nombreDesc1'/>";
	  var contentValor3 = "<input type='text' name='valor3' class='input90'/><input type='hidden' name='formatoFecha3'><input type='hidden' name='valor3D'/><input type='hidden' name='valor3M'/><input type='hidden' name='valor3A'/><input type='hidden' name='valor3S'/><input type='hidden' name='nombreDesc2'/>";
      if(idCampo!=""){
        inputTipoCampo.value = infoCampo[2];
        document.getElementById("tipoCampoCambio").value=inputTipoCampo.value;
        document.getElementById("campoCambio").value=idCampo;
        document.getElementById("tipoReferencia").value=infoCampo[3];
        if (infoCampo[2] == 5){
          tdValor.style.width = "120px";
          tdValorNuevo.style.width = "120px";
          // contentvalor4 = "<input type='text' name='nombreDesc' class='inputRO90' readonly='true'/>"
          //    + "<input type='hidden' name='valor4'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor4D'/><input type='hidden' name='valor4M'/><input type='hidden' name='valor4A'/><input type='hidden' name='valor4S'/>";
          contentvalor4 = getCampos2(4, false, false, false, false, false, false, true);
		  contentvalor4 += "<a href='javascript:showSearchObjectForm(4"
		       + ")'><img src='../../pages/images/pixel.gif' width='2px' class='imgTextMiddle'/>"
		       + "<img src='../../pages/images/expand.gif' class='imgTextMiddle'/></a>";

          contentValor3 = getCampos2(3, false, false, false, false, false, false, true);
          contentValor3 += "<a href='javascript:showSearchObjectForm(3"
                  + ")'><img src='../../pages/images/pixel.gif' width='2px' class='imgTextMiddle'/>"
                  + "<img src='../../pages/images/expand.gif' class='imgTextMiddle'/></a>";
        }else if (infoCampo[2] == 4 || infoCampo[2] == 1 || infoCampo[2] == 2){
          contentvalor4 = "<input type='text' name='valor4' class='input90'/><input type='hidden' name='formatoFecha4'><input type='hidden' name='valor4D'/><input type='hidden' name='valor4M'/><input type='hidden' name='valor4A'/><input type='hidden' name='valor4S'/><input type='hidden' name='nombreDesc1'/>";
          contentValor3 = "<input type='text' name='valor3' class='input90'/><input type='hidden' name='formatoFecha3'><input type='hidden' name='valor3D'/><input type='hidden' name='valor3M'/><input type='hidden' name='valor3A'/><input type='hidden' name='valor3S'/><input type='hidden' name='nombreDesc2'/>";

			if(infoCampo[2] == 1 || infoCampo[2]== 2){
				if(infoCampo[5] != null)
					contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_TEXTO_CON_TABLA , document.getElementById("formaReemplazo").value);
				else
					contentformasReemplazo = getSelect("selformaReemplazo","javascript:comprobarFormaReemplazo()", FORMAS_REEMPLAZO_TEXTO , document.getElementById("formaReemplazo").value);
			}
          }else{
	      contentvalor4 = getCamposByTipoFecha2(1);
		  contentvalor4 += "<input type='hidden' name='formatoFecha2' value=' '><input type='hidden' name='valor2D' value=' '/><input type='hidden' name='valor2M' value=' '/><input type='hidden' name='valor2A' value=' '/><input type='hidden' name='valor2S' value=' '/>";
          contentValor3 = getCamposByTipoFecha2(3);
          //contentvalor4 = "<input type='hidden' name='valor4'/><input type='text' name='valor4D' size='2' maxlength='2'/>-<input type='text' name='valor4M' size='2' maxlength='2'/>-<input type='text' name='valor4A' size='4' maxlength='4'/><input type='hidden' name='nombreDesc'/>";
        }
      }
		tdForma.innerHTML = contentformasReemplazo;
		tdValor.innerHTML = contentvalor4;
		tdValorNuevo.innerHTML = contentValor3;

		document.getElementById("formaReemplazo").value = document.getElementById("selformaReemplazo").value;

		comprobarFormaReemplazo();


    }

    function getCamposByTipoFecha2(orden){
      var posicion = parseInt(document.getElementById(CAMPO_VALOR_TIPO_FECHA + orden).value,10);
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
      var CAMPO_VALOR_M = "valor"  + String(orden) + "M";
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
          alert('true');
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
      return "<input type='hidden' name='"+ nombre +"' id='"+ nombre + "' class='input90'>";
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
      if(orden==3) tdValor=document.getElementById("valorNuevo");

      document.getElementById(CAMPO_VALOR_TIPO_FECHA  + orden).value = select.selectedIndex;

      //selectOperador = document.getElementById(CAMPO_TIPO_OPERADOR);
      //if(orden == 1 && selectOperador.value != "[..]" && selectOperador.value != "EX" && selectOperador.value != "QCN"){
      //	document.getElementById(CAMPO_VALOR_TIPO_FECHA  + "2" + SEPARADOR + fila).value = select.selectedIndex;
      //}

      var contenido = getCamposByTipoFecha2(orden);
      contenido += "<input type='hidden' name='idRelacionesDesc"+orden+"' value=''/>";
      tdValor.innerHTML = contenido;
    }

	function comprobarFormaReemplazo(){
		 var selectFormas = document.getElementById("selformaReemplazo");
		 var filaValorActual = document.getElementById("trValorActual");
		 var filaValorActualP = document.getElementById("trValorActualP");
		 var filaValorNuevo = document.getElementById("trValorNuevo");

		var estiloVisible = "table-row";
		if(document.all){
			estiloVisible = "block";
		}
		var estiloOculto= "none";

		document.getElementById("formaReemplazo").value = selectFormas.value;
		 if(selectFormas){
			var valor = selectFormas.value;

			switch (valor){
				case REEMPLAZO_VALORES_EXACTOS:
					xDisplay(filaValorActual,estiloVisible);
					xDisplay(filaValorActualP,estiloOculto);
					xDisplay(filaValorNuevo,estiloVisible);
					break;

				case REEMPLAZO_VALORES_PARCIALES:
					xDisplay(filaValorActual,estiloOculto);
					xDisplay(filaValorActualP,estiloVisible);
					xDisplay(filaValorNuevo,estiloVisible);
					break;

				case REEMPLAZO_VALORES_NULOS:
					xDisplay(filaValorActual,estiloOculto);
					xDisplay(filaValorActualP,estiloOculto);
					xDisplay(filaValorNuevo,estiloVisible);
					break;
			}
	 	}
	}

  function inicio(){
    var select=document.forms['<c:out value="${actionMappingName}" />'].campoCambio;

    var optionValue='';

    for (var i=0; i<select.options.length;i++){
      if(select.options.item(i).value=='<c:out value="${busquedaReemplazosForm.campoCambio}"/>'){
        select.options.item(i).selected = true;
        optionValue=select.options.item(i).value;
        break;
      }
    }

    if(optionValue!='')
      cambioEnCampoSinOperador(select);

    //si es tipo fecha hay que hacer lo mismo con el formato de fecha
	var selectFormatoFecha4=document.forms['<c:out value="${actionMappingName}" />'].formatoFecha4;
	if(selectFormatoFecha4){
		selectFormatoFecha4.value='<c:out value="${busquedaReemplazosForm.formatoFecha4}"/>';
		if(selectFormatoFecha4.value!='' && selectFormatoFecha4.value!='')
			cambioTipoFecha(selectFormatoFecha4,4);
	}

	var selectFormatoFecha3=document.forms['<c:out value="${actionMappingName}" />'].formatoFecha3;
	if(selectFormatoFecha3){
		selectFormatoFecha3.value='<c:out value="${busquedaReemplazosForm.formatoFecha3}"/>';
		if(selectFormatoFecha3.value!='' && selectFormatoFecha3.value!=' ' )
			cambioTipoFecha(selectFormatoFecha3,3);
	}

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

      document.forms['<c:out value="${actionMappingName}" />'].valor4.
      value='<c:out value="${busquedaReemplazosForm.valor4}"/>';
    document.forms['<c:out value="${actionMappingName}" />'].valor4A.
      value='<c:out value="${busquedaReemplazosForm.valor4A}"/>';
    document.forms['<c:out value="${actionMappingName}" />'].valor4M.
      value='<c:out value="${busquedaReemplazosForm.valor4M}"/>';
    document.forms['<c:out value="${actionMappingName}" />'].valor4D.
      value='<c:out value="${busquedaReemplazosForm.valor4D}"/>';
    document.forms['<c:out value="${actionMappingName}" />'].valor4S.
      value='<c:out value="${busquedaReemplazosForm.valor4S}"/>';

    if(document.forms['<c:out value="${actionMappingName}" />'].nombreDesc3)
      document.forms['<c:out value="${actionMappingName}" />'].nombreDesc3.
        value='<c:out value="${busquedaReemplazosForm.nombreDesc3}"/>';

    if(document.forms['<c:out value="${actionMappingName}" />'].nombreDesc4)
        document.forms['<c:out value="${actionMappingName}" />'].nombreDesc4.
          value='<c:out value="${busquedaReemplazosForm.nombreDesc4}"/>';

	//cargar la lista con valores por defecto
	if(document.getElementById("campoCambio").value != ""){
		var contentformasReemplazo ="";
		var idCampo = select.value;
		var infoCampo = document.getElementById("tipoCampoCambio").value;
		if(infoCampo == 1 || infoCampo == 2 ){
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
  <html:hidden property="cerrarpar" styleId="cerrarpar" value=""/>
  <html:hidden property="abrirpar" styleId="abrirpar" value=""/>
  <html:hidden property="booleano" styleId="booleano" value=""/>
  <html:hidden property="tipoReferencia" styleId="tipoReferencia"/>

  <tr>
    <td class="<c:out value="${classTdTituloCampoSinBorde}"/>" width="170px" nowrap="nowrap">
      <bean:message key="archigest.archivo.cf.ficha"/>:&nbsp;
    </td>
    <td class="<c:out value="${classTdCampo}"/>" style="border-bottom:0">
      <c:set var="fichasSelect" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
      <c:if test="${busquedaReemplazosForm.idFicha==''}">
        <bean:message key="archigest.descripcion.reemplazo.fichas.todosLosCampos"/>
      </c:if>
      <c:if test="${busquedaReemplazosForm.idFicha!=''}">
        <c:forEach var="ficha" items="${fichasSelect}">
          <c:if test="${ficha.id==busquedaReemplazosForm.idFicha}">
            <c:out value="${ficha.nombre}"/>
          </c:if>
        </c:forEach>
      </c:if>
    </td>
  </tr>
  <tr>
    <td class="<c:out value="${classTdTituloCampoSinBorde}"/>">
      <bean:message key="archigest.descripcion.reemplazo.campo"/>:&nbsp;
      <input type='hidden' size='1' name='formatoFechaSel1' id='formatoFechaSel1' value='0'/>
    </td>
    <td class="<c:out value="${classTdCampo}"/>" style="border-bottom:0">
      <select name="campoCambio" id="campoCambio" onchange="javascript:cambioEnCampoSinOperador(this)">
        <option value=""></option>
        <c:forEach var="campo" items="${campos}">
          <option value="<c:out value="${campo.id}"/>"><c:out value="${campo.nombre}"/></option>
        </c:forEach>
      </select>
      <input type="hidden" name="tipoCampoCambio" id="tipoCampoCambio"/>
    </td>
  </tr>
	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" nowrap="nowrap">
			<bean:message key="archigest.archivo.reemplazar.forma.reemplazo"/>:&nbsp;
			<html:hidden property="formaReemplazo" styleId="formaReemplazo"/>
		</td>
		<td class="<c:out value="${classTdCampo}"/>" id="valorFormaReemplazo" nowrap="nowrap" style="border-bottom:0">
			<select name="selformaReemplazo" id="selformaReemplazo">
			</select>
		</td>
	</tr>
	<tr id="trValorActual" style="display:none;">
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" id="etiquetaValorBuscar" nowrap="nowrap">
			<bean:message key="archigest.descripcion.reemplazo.valor.actual"/>:&nbsp;
		</td>
		<td class="<c:out value="${classTdCampo}"/>" id="valorBuscar" nowrap="nowrap" style="border-bottom:0">
			<input type="text" name="ini" id="ini" class="input90">
			<input type="hidden" name="formatoFecha4" id="formatoFecha4">
			<input type="hidden" name="valor4" id="valor4"/>
			<input type="hidden" name="valor4D" id="valor4D"/>
			<input type="hidden" name="valor4M" id="valor4M"/>
			<input type="hidden" name="valor4A" id="valor4A"/>
			<input type="hidden" name="valor4S" id="valor4S"/>

			<input type="hidden" name="nombreDesc4" id="nombreDesc4"/>
			<input type="hidden" name="idRelacionesDesc4" id="idRelacionesDesc4"/>
		</td>
	</tr>
	<tr id="trValorActualP">
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" id="etiquetaValorBuscar" nowrap="nowrap">
			<bean:message key="archigest.descripcion.reemplazo.valor.actual"/>:&nbsp;
		</td>
		<td class="<c:out value="${classTdCampo}"/>" id="valorBuscar" nowrap="nowrap" style="border-bottom:0">
			<html:text property="valorReemplazoParcial" styleId="valorReemplazoParcial" styleClass="input90"/>
		</td>
	</tr>
	<tr id="trValorNuevo">
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" style="border-bottom:0;" nowrap="nowrap" >
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
