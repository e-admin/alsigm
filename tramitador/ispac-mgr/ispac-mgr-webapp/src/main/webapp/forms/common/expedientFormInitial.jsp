<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<c:set var="sicresConnectorClass" value="${ISPACConfiguration.SICRES_CONNECTOR_CLASS}" />
<c:set var="thirdPartyAPIClass" value="${ISPACConfiguration.THIRDPARTY_API_CLASS}" />

<script language='JavaScript' type='text/javascript'><!--

	var idContenido="";
	
	function save()	{
	
		var sicresConnectorClass = '<c:out value="${sicresConnectorClass}"/>';
		
		if (sicresConnectorClass != '') {
			freg = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:FREG)' ];
	    	if (freg.value == '') {
	    	
	    		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ].value = '';
	    	}
	    }
	
		/*
		idtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ];
    	if (idtitular.value == '') {
    	
    		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ].value = '';
    	}
    	*/
    
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do";
		document.defaultForm.name = "Expedientes";

		if (validateExpedientes(document.defaultForm)) {
		
			document.defaultForm.submit();
			//Mostramos el mensaje de operación en progreso si se ha establecido un numero de registro, ya que si se incorporan documentos vinculados puede ser una operacion costosa
			if ( (document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ].value != '') && (_nreg != document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ].value) ){
				showLayer('waitOperationInProgress');
			}		

		}

		
		ispac_needToConfirm = true;
	}

	function newThirParty()	{
	
		var url = "selectThirdParty.do?option=new";
		showFrame( "workframe", url, 640, 480);
	}
	
	function selectThirdParty(target, action) {
		
		/*
		nifciftitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
		
		if (!nifciftitular.readOnly) {
			
			executeFrame(target, action);
		}
		*/
		
		identidadtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ];
		
		if (identidadtitular.readOnly) {
			
			executeFrame(target, action);
		}
	}
	
	

	function delete_EXPEDIENT_SEARCH_THIRD_PARTY() {
		
		/*
		idtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ];
		if (idtitular.value != '') {
		*/
	
		jConfirm('<bean:message key="msg.delete.data.thirdParty"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
			checkRadioById('validatedThirdParty');
	 		nifciftitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
	 		setNotReadOnly(nifciftitular);
	 		setReadOnlyIdentidad(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ]);
			clearThirdParty();
	 		nifciftitular.focus();
		}
							
	});	
	 	
	 	
	 	ispac_needToConfirm = true;
	}
	
	function clearThirdParty() {
			
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ].value = '';
		clearSearchedDataThirdParty();
	}
	
	function clearSearchedDataThirdParty() {
		
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CIUDAD)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:REGIONPAIS)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CPOSTAL)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)' ].value = '';
		document.defaultForm.elements[ 'property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOFIJO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOMOVIL)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TIPOPERSONA)' ].value = '';
	}
	
	function clearSearchedDataPostalAddressThirdParty() {
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CIUDAD)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:REGIONPAIS)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CPOSTAL)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)' ].value = '';
		document.defaultForm.elements[ 'property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOFIJO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOMOVIL)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)' ].value = '';
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TIPOPERSONA)' ].value = '';
	}
	
	
	function acceptRegistryOutput(){
	
		setReadOnly(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ]);
		
		if (document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ].value != '') {
			
			checkRadioById('validatedThirdParty');
			setReadOnly(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ]);
			setReadOnlyIdentidad(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ]);
		}
		else {
			checkRadioById('notValidatedThirdParty');
			setNotReadOnly(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ]);
			setNotReadOnlyIdentidad(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ]);
		}
		hideInfo();
		

}

	function cancelRegistryOutput(){
	
		document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ].value = '';
		removeInfoRegistry();
		hideInfo();
	}
	
	function removeInfoRegistry(){ 
	
	  jConfirm('<bean:message key="msg.delete.data.register"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
			nreg = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ];
	 		setNotReadOnly(nreg);
	 		nreg.value = '';
	 		nreg.focus();

			document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:FREG)' ].value = '';
		}
							
	});	
	
		
	 	ispac_needToConfirm = true;
	}


	
	
	
	
	function return_EXPEDIENT_SEARCH_THIRD_PARTY() {
	
		nifCifTitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
		nifCifTitular.value = nifCifTitular.value.toUpperCase();
		setReadOnly(nifCifTitular);
		setReadOnlyIdentidad(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ]);
	}
	
	function clickValidatedThirdParty() {
		
		/*
		if (document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ].value == '') {
	
			clearThirdParty();
		}
		
		setReadOnlyIdentidad(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ]);
		nifciftitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
		setNotReadOnly(nifciftitular);
		nifciftitular.focus();
		*/
		
		identidadtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ];
		
		if (!identidadtitular.readOnly) {
		
			clearSearchedDataThirdParty();
			setReadOnlyIdentidad(identidadtitular);
			nifciftitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
			setNotReadOnly(nifciftitular);
			nifciftitular.focus();
		}
		enableSearchPostal();		
	}
	
	function clickNotValidatedThirdParty() {
	
		if (document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ].value != '') {
	
			clearThirdParty();
		}
		
		//setReadOnly(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ]);
		identidadtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ];
		setNotReadOnlyIdentidad(identidadtitular);
		//identidadtitular.focus();
		nifciftitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
		setNotReadOnly(nifciftitular);
		nifciftitular.focus();
		disableSearchPostal(true);		
	}
	
	function setReadOnlyPostalAddressFields(){
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)' ];
		setReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ];
		setReadOnlyDir(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CPOSTAL)' ];
		setReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CIUDAD)' ];
		setReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:REGIONPAIS)' ];
		setReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOFIJO)' ];
		setReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOMOVIL)' ];
		setReadOnly(field);
	}

	function setNotReadOnlyPostalAddressFields(){
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)' ];
		setNotReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ];
		setNotReadOnlyDir(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CPOSTAL)' ];
		setNotReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CIUDAD)' ];
		setNotReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:REGIONPAIS)' ];
		setNotReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOFIJO)' ];
		setNotReadOnly(field);
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOMOVIL)' ];
		setNotReadOnly(field);


	}

	
	function clickValidatedPostalAddressThirdParty(){
		field = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ];
		if (field.readOnly != true){
			clearSearchedDataPostalAddressThirdParty();
			setReadOnlyPostalAddressFields();
		}
	}

	function clickNotValidatedPostalAddressThirdParty(){
		clickNotValidatedPostalAddressThirdPartyClean(true);
	}

	
	function clickNotValidatedPostalAddressThirdPartyClean(clean){
		if (clean == true){
			clearSearchedDataPostalAddressThirdParty();
		}
		setNotReadOnlyPostalAddressFields();
	}


	function disableSearchPostal(clean){
		document.getElementById('notValidatedPostalAddressThirdParty').disabled = true;
		document.getElementById('notValidatedPostalAddressThirdParty').checked= true;
		document.getElementById('validatedPostalAddressThirdParty').disabled = true;
		clickNotValidatedPostalAddressThirdPartyClean(clean);
	}
	
	function enableSearchPostal(){
		if  (document.getElementById('notValidatedPostalAddressThirdParty').disabled == true){
			document.getElementById('notValidatedPostalAddressThirdParty').disabled = false;
			document.getElementById('validatedPostalAddressThirdParty').disabled = false;
			document.getElementById('validatedPostalAddressThirdParty').checked= true;
			clickValidatedPostalAddressThirdParty();
		}
		
	}


//--></script>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>
<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<html:form action="storeEntity.do">

	<!-- Nombre del formulario, necesario para realizar la validación -->
	<html:javascript formName="Expedientes"/>
	
	<!-- Solapa que se muestra -->
	<html:hidden property="block" value="1"/>
	
	<!-- Nombre de Aplicación -->
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Indicador de sólo lectura -->
	<html:hidden property="readonly"/>
	
	<html:hidden property="property(SPAC_EXPEDIENTES:TIPOPERSONA)"/>
	<html:hidden property="property(SPAC_EXPEDIENTES:IDTITULAR)"/>
	<html:hidden property="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)"/>

	<table cellpadding="0" cellspacing="0" width="100%">
	
		<tr>
			<td>
			
				<table class="boxTab" width="100%" border="0" cellspacing="0" cellpadding="0">
				
					<tr>
						<td class="title" height="28px">
						
							<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
							
								<tr>
									<td>
									
										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
										
											<tr>
												<td>
												
													<!-- COMIENZO DE LAS ACCIONES -->
													<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
													
														<tr>
															<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
															
															<!-- ACCION SALVAR -->
															<logic:equal value="false" property="readonly" name="defaultForm">
															
																<td class="formaction" height="28px">
																	<a onclick="javascript: ispac_needToConfirm = false;" href="javascript:save();" class="formaction">
																		<bean:message key="forms.button.save"/>
																	</a>
																</td>
																
															</logic:equal>
															<!--FIN ACCION SALVAR -->
															
															<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														
													</table>
													<!-- FINAL DE LAS ACCIONES -->
													
												</td>
											</tr>
											
										</table>
										
									</td>
								</tr>
								
							</table>
							
						</td>
					</tr>
					
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
						
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
							
								<tr>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
