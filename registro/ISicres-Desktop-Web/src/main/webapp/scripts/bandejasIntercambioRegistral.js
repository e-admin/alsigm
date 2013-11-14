function changeSelectEstados()
{

	var selected = document.forms[0].tipoBandeja.options.selectedIndex;
	if(selected==0)
	{
		document.getElementById('estadosSalida').style.display='inline';
		document.getElementById('estadosEntrada').style.display='none';
		document.getElementById('libroSeleccionado').style.display='inline';
		if (document.getElementById('labelLibroSeleccionado')){
			document.getElementById('labelLibroSeleccionado').style.display='inline';
		}
	}
	else
	{
		document.getElementById('estadosSalida').style.display='none';
		document.getElementById('estadosEntrada').style.display='inline';
		document.getElementById('libroSeleccionado').style.display='none';
		if (document.getElementById('labelLibroSeleccionado')){
			document.getElementById('labelLibroSeleccionado').style.display='none';
		}
	}
}

function submitRefrescarBandejaEntradaOSalida()
{
	var tipoBandeja = document.getElementById('tipoBandeja').value;
	if(tipoBandeja==0)
	{
		document.forms[0].action='BandejaSalidaIntercambioRegistral.do';
	}
	else
	{
		document.forms[0].action='BandejaEntradaIntercambioRegistral.do';
	}
	document.forms[0].submit();
}

function submitRefrescarBusquedaAvanzadaIR(){
	var tipoBandeja = document.getElementById('tipoBandeja').value;
	if(tipoBandeja==0){
		document.forms[0].action='BusquedaBandejaSalidaIntercambioRegistral.do';
	}
	else{
		document.forms[0].action='BusquedaBandejaEntradaIntercambioRegistral.do';
	}
	document.forms[0].submit();
}

function resetBusquedaAvanzadaIR(){
	document.forms[0].reset();
}

function submitEnviarIntercambiosRegistrales(){
	uncheckOtherTabs();
	if(comprobarSiHaySeleccionados())
	{
		 var tabSelected = jQuery('#tabs').tabs( "option", "selected" );
		if(tabSelected=='1')
		{
			mostrarCapaNuevoDestino();
		}
		else
		{
			document.forms[0].action="EnviarIntercambiosRegistrales.do";
			document.forms[0].submit();
		}
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}
}

function submitEnviarIntercambiosIncompletos(){
	var nuevoDestino = document.getElementById('nuevoDestino').value;
	if(nuevoDestino=='')
	{
		//Si no se indicó el destino, aviso!
		alert(top.GetIdsLan("IDS_IR_NO_HA_SELECCIONADO_DESTINO"));
	}
	else
	{
		//Si se indicó, se envían.
		document.forms[0].action="EnviarIntercambiosRegistralesIncompletos.do";
		document.forms[0].submit();
	}
}


function submitAnularIntercambiosRegistrales(){
	uncheckOtherTabs();
	if(comprobarSiHaySeleccionados())
	{
		document.forms[0].action="AnularIntercambiosRegistrales.do";
		document.forms[0].submit();
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function uncheckOtherTabs(){

	 var tabSelected = jQuery('#tabs').tabs( "option", "selected" );
	 var checksADesmarcar = jQuery("input:checkbox[name=checkRegistro]:not(.checkTab"+tabSelected+")");
	//deseleccionar todo
		for(i=0;i<checksADesmarcar.length;i++)
		{
			checksADesmarcar[i].checked=false;
		}
}

function submitDesanularIntercambiosRegistrales(){

	if(comprobarSiHaySeleccionados())
	{
		document.forms[0].action="DesanularIntercambiosRegistrales.do";
		document.forms[0].submit();
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function submitAceptarIntercambiosRegistrales(button){

	if(comprobarSiHaySeleccionados())
	{
		document.body.style.cursor = "wait";//cambiamos cursor a estilo: cargando
		// deshabilitamos el boton para que solamente se pueda aceptar una vez
		button.disabled=true;

		document.forms[0].action="AceptarIntercambiosRegistrales.do";
		document.forms[0].submit();
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function submitReenviarIntercambiosRegistrales(){

	if(comprobarSiHaySeleccionados())
	{
		document.forms[0].action="ReenviarIntercambiosRegistrales.do";
		document.forms[0].submit();
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function submitRechazarIntercambiosRegistrales(button){

	if(comprobarSiHaySeleccionados())
	{
		document.body.style.cursor = "wait";//cambiamos cursor a estilo: cargando
		// deshabilitamos el boton para que solamente se pueda aceptar una vez
		button.disabled=true;

		document.forms[0].action="RechazarIntercambiosRegistrales.do";
		document.forms[0].submit();
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}


function comprobarSiHaySeleccionados(){

	checksRegistro = document.getElementsByName('checkRegistro');
	var haySeleccionados = false;
	for(i=0;i<checksRegistro.length;i++)
	{
		if(checksRegistro[i].checked==true)
		{
			haySeleccionados = true;
		}
	}
	return haySeleccionados;
}

function seleccionarODeseleccionarTodos()
{
 var checkSeleccionarTodos = document.getElementsByName('seleccionarTodos');
 var checkDeseleccionarTodos = document.getElementsByName('deseleccionarTodos');
 var checksRegistro = document.getElementsByName('checkRegistro');

  if(checkDeseleccionarTodos[0].checked)
 {
	checkDeseleccionarTodos[0].checked= false;
	checkSeleccionarTodos[0].checked= false;
 }
 if(checkSeleccionarTodos[0].checked)
 {
	//seleccionar todo
	for(i=0;i<checksRegistro.length;i++)
	{
		checksRegistro[i].checked=true;
	}
 }
 else
 {
	//deseleccionar todo
	for(i=0;i<checksRegistro.length;i++)
	{
		checksRegistro[i].checked=false;
	}
 }

}


function seleccionarODeseleccionarTodosInTabs()
{
 var checkSeleccionarTodos = document.getElementsByName('seleccionarTodosInTabs');
 var checkDeseleccionarTodos = document.getElementsByName('deseleccionarTodosInTabs');

 var tabSelected = jQuery('#tabs').tabs( "option", "selected" );
 var checks = jQuery('.checkTab'+tabSelected);

  if(checkDeseleccionarTodos[0].checked)
 {
	checkDeseleccionarTodos[0].checked= false;
	checkSeleccionarTodos[0].checked= false;
 }
 if(checkSeleccionarTodos[0].checked)
 {
	//seleccionar todo
	for(i=0;i<checks.length;i++)
	{
		checks[i].checked=true;
	}
 }
 else
 {
	//deseleccionar todo
	for(i=0;i<checks.length;i++)
	{
		checks[i].checked=false;
	}
 }

}

function setRowsEvents()
{
	if(document.getElementById('bandejaEntradaAceptados')!=null)
	{
		var rows = document.getElementById('bandejaEntradaAceptados').rows;

		for(i=1;i<rows.length;i++)
		{
			document.getElementById('bandejaEntradaAceptados').rows[i].style.cursor='pointer';
			document.getElementById('bandejaEntradaAceptados').rows[i].ondblclick=abrirRegistroOInfoIntercambioRegistral;
		}
	}
}

function selectState(){

	var select = document.getElementById('estadosEntrada');
	for ( var i = 0; i < select.options.length; i++ ) {
        if ( select.options[i].value == estado ) {
            select.options[i].selected = true;
           }
    }
	var selectTipoBandeja = document.getElementById('tipoBandeja');
	for	 ( var i = 0; i < selectTipoBandeja.options.length; i++ ) {
        if ( selectTipoBandeja.options[i].value == tipoBandeja ) {
            selectTipoBandeja.options[i].selected = true;
        }
    }
}

function selectBookIR(){
	var select = document.getElementById('libroSeleccionado');
	for ( var i = 0; i < select.options.length; i++ ) {
        if ( select.options[i].value == libroSeleccionado ) {
            select.options[i].selected = true;
            return;
        }
    }
}

//metodo para setear los eventos de las filas de las tablas y asociar el evento de abrir registro
function setRowsEventsSalida(){
	if(document.getElementById('bandejaSalida')!=null)
	{
		var rows = document.getElementById('bandejaSalida').rows;

		for(i=1;i<rows.length;i++)
		{
			rows[i].style.cursor='pointer';
			rows[i].ondblclick=abrirRegistro;
		}
	}
}

function selectStateSalida(){
	var select = document.getElementById('estadosSalida');
	for ( var i = 0; i < select.options.length; i++ ) {
        if ( select.options[i].value == estado ) {
            select.options[i].selected = true;
            return;
        }
    }
	var selectTipoBandeja = document.getElementById('tipoBandeja');
	for ( var i = 0; i < selectTipoBandeja.options.length; i++ ) {
        if ( selectTipoBandeja.options[i].value == tipoBandeja ) {
            selectTipoBandeja.options[i].selected = true;
            return;
        }
    }
}

//funcion para abrir el registro situado en la la fila que estamos situados de la tabla
function abrirRegistro(event)
{
	if ( !event )
    {
        event = window.event;
    }
    var target = event.currentTarget ? event.currentTarget : event.srcElement;
	var idRegistro = target.id;

	if(idRegistro=='')
	{
		idRegistro = target.parentNode.id;
	}

	var idLibro = document.getElementById('idLibroParaRegistro_'+idRegistro).value;

	var URL = top.g_URL + "/AbrirLibroVerRegistroIntercambioRegistral.do?AppId=" + top.g_AppId.toString()
			+ "&SessionPId=" + top.g_SessionPId + "&FolderView=1&ArchiveId=" + idLibro
            + "&ArchiveName=" + top.g_ArchiveName.toString()
            + "&ArchivePId=" + top.g_ArchivePId.toString()
            + "&FolderPId=" + top.g_FolderPId.toString()
            + "&FolderId=" + idRegistro
            + "&VldSave=1" + "&Idioma=" + top.Idioma.toString()
            + "&numIdioma=" + top.numIdioma.toString()
            + "&FdrQryPId=" + top.g_FdrQryPId.toString()
	        + "&OpenType=0";

	top.OpenNewWindow(URL, "", "10000", "10000", "auto","yes");
}


function aceptarIntercambiosRegistrales()
{

	if(comprobarSiHaySeleccionados())
	{
		aceptar = true;
		var documentacionFisica = document.getElementsByName('documentacionFisica');
		var checksRegistro = document.getElementsByName('checkRegistro');
		var numCheckeados = 0;
		var hayDocRequerida = false;
		var hayDocComplementaria =false;
		for(i=0;i<checksRegistro.length;i++)
		{
			if(checksRegistro[i].checked==true)
			{
				numCheckeados=numCheckeados+1;
				//Comprobar si alguno de los checkeados tiene documentación requerida.
				if(documentacionFisica[i].value=='1')
				{
					hayDocRequerida = true;
				}
				else if(documentacionFisica[i].value=='2')
				{
					hayDocComplementaria = true;
				}
			}
		}

		if(hayDocRequerida && numCheckeados>1)
		{
			//Si se seleccionaron varios, se informa de que al tener documentación física se tiene que hacer de forma individual
			alert(top.GetIdsLan("IDS_IR_NO_MULTIPLE_DOC_FISICA"));

			return;
		}
		else if(hayDocRequerida && numCheckeados==1)
		{
			//Mostramos pantalla para pedir confirmación de si llegó la documentación física
			mostrarConfirmacionDocumentacionFisica();

			return;
		}
		else if(hayDocComplementaria && numCheckeados==1)
		{
			mostrarBotonContinuarSinRecibirDocumentacion();
			mostrarConfirmacionDocumentacionFisica();

			return;
		}
		else
		{
			//Si todo fué ok, mostramos los libros para que seleccione dónde aceptarlo
			mostrarLibros();
		}

	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function rechazarIntercambiosRegistrales()
{
	if(comprobarSiHaySeleccionados())
	{
		mostrarMotivoRechazo();


	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function reenviarIntercambiosRegistrales()
{

	if(comprobarSiHaySeleccionados())
	{
		var documentacionFisica = document.getElementsByName('documentacionFisica');
		var checksRegistro = document.getElementsByName('checkRegistro');
		var numCheckeados = 0;
		var hayDocRequeridaOComplementaria = false;
		aceptar = false;
		for(i=0;i<checksRegistro.length;i++)
		{

			if(checksRegistro[i].checked==true)
			{
				numCheckeados = numCheckeados +1;
				//Comprobar si alguno de los checkeados tiene documentación requerida o complementaria
				if(documentacionFisica[i].value=='1' || documentacionFisica[i].value=='2')
				{
					hayDocRequeridaOComplementaria = true;

				}
			}
		}


		if(hayDocRequeridaOComplementaria && numCheckeados>1)
		{
			//Si se seleccionaron varios, se informa de que al tener documentación física se tiene que hacer de forma individual
			alert(top.GetIdsLan("IDS_IR_NO_MULTIPLE_DOC_FISICA"));
			return;
		}
		else if(hayDocRequeridaOComplementaria && numCheckeados==1)
		{
			//Mostramos pantalla para pedir confirmación de si llegó la documentación física
			ocultarBotonContinuarSinRecibirDocumentacion();
			mostrarConfirmacionDocumentacionFisica();

			return;
		}
		else
		{
			//Si todo fue ok, mostramos pantalla de reenvío
			mostrarCapaNuevoDestino();

		}
	}
	else
	{
		alert(top.GetIdsLan("IDS_IR_NO_HAY_SELECCIONADOS"));
	}

}

function buscarNuevoDestino()
{
	top.g_WndVld = top.Main.Workspace;
//		top.g_FormVld = document.getElementById('formBandejaEntrada');
//		top.g_Field = document.getElementById("nuevoDestino");
//		top.g_VldPath = "top.Main.Workspace" ;
//
//	 				var URL = top.g_URL + "/mainvld.htm?SessionPId=" + top.g_SessionPId
//				+ "&ArchivePId=" + top.g_ArchivePId
//				+ "&FldId=7"
//				+ "&tblvalidated=11"
//				+ "&TblFldId=7"
//				+ "&Idioma=" + top.Idioma.toString()
//				+ "&caseSensitive=CS"
//				+ "&Enabled=0&IsDtrList=0";
//
//		OnHelpWindow = true;
//		OpenVldHlpWnd(URL, 'name', '97%', '97%', 'no');

	strParams = "ArchiveId=" + g_ArchiveId.toString();
	strParams += "&SessionPId=" + g_SessionPId.toString();
	strParams += "&FolderId=" + g_FolderId.toString();
	strParams += "&FolderPId=" + g_FolderPId.toString();
	strParams += "&ArchivePId=" + g_ArchivePId.toString();
	strParams += "&FrmData=false";
	strParams += "&EnviarIR=1";

	window.open(top.g_URL + "/mainvld.htm?" + strParams, "Vld","location=no",true);



	top.g_WndVld.document.getElementById("Vld").style.left = "5px";
	top.g_WndVld.document.getElementById("Vld").style.top  = "5px";
	top.g_WndVld.document.getElementById("Vld").style.height = '97%';
	top.g_WndVld.document.getElementById("Vld").style.width =  '97%';
	top.g_WndVld.document.getElementById("Vld").style.display = "block";
}

function submitReenviarIntercambios(button)
{
	document.body.style.cursor = "wait";//cambiamos cursor a estilo: cargando
	// deshabilitamos el boton para que solamente se pueda aceptar una vez
	button.disabled=true;

	document.forms[0].action="ReenviarIntercambiosRegistrales.do";
	document.forms[0].submit();
}

function cancelarReenvio()
{
	var buscarNuevoDestino = document.getElementById('buscarNuevoDestino');
	buscarNuevoDestino.style.display='none';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='none';
}

function setDocRecibida(isDocRecibida)
{
	var docRecibida = document.getElementById('docRecibida');
	docRecibida.value=isDocRecibida;
}

function continuarDocRecibida()
{
	ocultarConfirmacionDocumentacionFisica();
	setDocRecibida(true);
	if(aceptar)
	{
		mostrarLibros();
	}
	else
	{
		mostrarCapaNuevoDestino();
	}

}
function continuarDocNoRecibida()
{
	ocultarConfirmacionDocumentacionFisica();

	setDocRecibida(false);
	if(aceptar)
	{
		mostrarLibros();
	}
	else
	{
		mostrarCapaNuevoDestino();
	}

}

function mostrarCapaNuevoDestino(){
	var buscarNuevoDestino = document.getElementById('buscarNuevoDestino');
	buscarNuevoDestino.style.display='block';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='block';
}

function mostrarLibros(event)
{
	var libros = document.getElementById('librosEntrada');
	libros.style.display='block';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='block';

}
function mostrarMotivoRechazo(event)
{
	var motivoRechazo = document.getElementById('divMotivoRechazo');
	motivoRechazo.style.display='block';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='block';

}

function ocultarLibros(event)
{
	var libros = document.getElementById('librosEntrada');
	libros.style.display='none';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='none';
}

function ocultarMotivoRechazo(event)
{
	var motivoRechazo = document.getElementById('divMotivoRechazo');
	motivoRechazo.style.display='none';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='none';
}

function mostrarConfirmacionDocumentacionFisica(event)
{
	var docFisica = document.getElementById('llegoDocumentacionFisica');
	docFisica.style.display='inline';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='block';

}

function ocultarConfirmacionDocumentacionFisica(event)
{
	var docFisica = document.getElementById('llegoDocumentacionFisica');
	docFisica.style.display='none';
	var capaBloqueo = document.getElementById('capaBloqueo');
	capaBloqueo.style.display='none';
}

function mostrarBotonContinuarSinRecibirDocumentacion(event)
{
	var buttonContinuarDocNoRecibida = document.getElementById('buttonContinuarDocNoRecibida');
	buttonContinuarDocNoRecibida.style.display='inline';
}
function ocultarBotonContinuarSinRecibirDocumentacion(event){
	var buttonContinuarDocNoRecibida = document.getElementById('buttonContinuarDocNoRecibida');
	buttonContinuarDocNoRecibida.style.display='none';

}

function mostrarInfoSolicitudIntercambio(idRegistro){

	/*jQuery.blockUI({ message: '<div id="resultInfoSolicitud"></div>',
		css: { top: '10%',
		  left: '10%',
		     width: '800px',
		    height: '310px',
		    cursor: 'cursor'}
	  });
	*/
	jQuery("#capaInfoSolicitud").dialog();

	jQuery("#capaInfoSolicitud").load('MostrarIntercambioRegistral.do',{idIntercambio:idRegistro});
}

function abrirRegistroOInfoIntercambioRegistral(event)
{
	if ( !event )
    {
        event = window.event;
    }
    var target = event.currentTarget ? event.currentTarget : event.srcElement;
	var idRegistro = target.id;

	if(idRegistro=='')
	{
		idRegistro = target.parentNode.id;
	}

	var idLibro = document.getElementById('idLibroParaRegistro_'+idRegistro).value;

	var URL = top.g_URL + "/AbrirLibroVerRegistroIntercambioRegistral.do?AppId=" + top.g_AppId.toString()
			+ "&SessionPId=" + top.g_SessionPId + "&FolderView=1&ArchiveId=" + idLibro
            + "&ArchiveName=" + top.g_ArchiveName.toString()
            + "&ArchivePId=" + top.g_ArchivePId.toString()
            + "&FolderPId=" + top.g_FolderPId.toString()
            + "&FolderId=" + idRegistro
            + "&VldSave=1" + "&Idioma=" + top.Idioma.toString()
            + "&numIdioma=" + top.numIdioma.toString()
            + "&FdrQryPId=" + top.g_FdrQryPId.toString()
	        + "&OpenType=0";



	top.OpenNewWindow(URL, "", "10000", "10000", "auto","yes");
}

$(document).ready(function($){

  // La busqueda de representantes se hace por Ajax y se muestra con en plugin fancybox
  $('.buscarEntidadesRegistralesLupa').click(function(e){
    e.preventDefault();

    $.fancybox.showActivity();

    $.ajax({
      type: 'GET',
      cache: false,
      url: 'BuscarEntidadesRegistralesDCO.do',
      dataType: 'html',
      success:function(data){
        $.fancybox({
          modal:true,
          content:data
          });
	putEventToFindER();

        }
    });
  });

  $('.buscarUnidadesTramitacionLupa').click(function(e){
      e.preventDefault();

      $.fancybox.showActivity();

      $.ajax({
        type: 'GET',
        cache: false,
        url: 'BuscarUnidadesTramitacionDCO.do',
        dataType: 'html',
        success:function(data){
          $.fancybox({
            modal:true,
            content:data
            });
          putEventToFindTramUnit();
          }
      });
    });
});

function putPaginateEvents()
{
	 // La navegacion de resultados se realiza por Ajax para mantener el foco en la ventana abierta
  $('.pagelinks > a, .sortable > a').click(function(e){
    e.preventDefault();

    $.ajax({
             type: 'POST',
             cache: false,
             url: $(this).attr('href'),
             dataType: 'html',
             success:function(data){
                 $('#fancybox-content').html(data);
                 putEventToFindER();
			   putPaginateEvents();
             }
        });
    });
}

function putEventToFindER(){
	 $('.buscarEntidadesRegistralesButton').click(function(e){
	      e.preventDefault();

	      $.fancybox.showActivity();

	      $.ajax({
	        type: 'GET',
	        cache: false,
	        url: 'BuscarEntidadesRegistralesDCO.do?codeEntityToFind='+$('#codeEntityToFind').val()+'&nameEntityToFind='+$('#nameEntityToFind').val(),
	        dataType: 'html',
	        success:function(data){
	          $.fancybox({
	            modal:true,
	            content:data
	            });
			putEventToFindER();
			putPaginateEvents();
	          }
	      });
	    });
}

function putEventToFindTramUnit(){
	 $('.buscarUnidadesTramitacionButton').click(function(e){
	      e.preventDefault();

	      $.fancybox.showActivity();

	      $.ajax({
	        type: 'GET',
	        cache: false,
	        url: 'BuscarUnidadesTramitacionDCO.do?tramunitCodeToFind='+$('#tramunitCodeToFind').val()+'&tramunitNameToFind='+$('#tramunitNameToFind').val(),
	        dataType: 'html',
	        success:function(data){
	          $.fancybox({
	            modal:true,
	            content:data
	            });
			putEventToFindTramUnit();
			putPaginateEvents();
	          }
	      });
	    });
}

function closeBusquedaDestinoIR(){
	// Cerramos los frames
	top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");

	top.g_WndVld.document.getElementById("Vld").style.display = "none";

	// Volvemos a mostrar el formulario
	top.g_FormVld.style.visibility = 'visible';
}

function closeFancybox()
{
	$.fancybox.close();
}

function validarEntidadRequerida(){

	if($('#entityCode').val()=='')
	{
		alert('La Entidad Registral es obligatoria.');
		return false;
	}
	return true;
}


jQuery("a.linkInfoSolicitudIntercambio").fancybox({
'hideOnContentClick': true
});
