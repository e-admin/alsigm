<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
								
							</table>
							
						</td>
					</tr>
					
				</table>
				
			</td>
		</tr>
		
	</table>
	
</html:form>

<%-- Manejador de block para resituarse en la pestaña en la que nos encontrabamos --%>
<tiles:insert template="/forms/common/manageBlock.jsp"/>

<%-- Control de campos al mostrar el formulario --%>
<script language='JavaScript' type='text/javascript'><!--

	//Se utiliza esta variable para incorpar el valor inicial del numero de registro y compararlo con el que este establecido a la hora
	//de guardar el formulario para, si ha cambiado, mostrar la pantalla de operacion en progreso 
	var _nreg = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ].value;

	var sicresConnectorClass = '<c:out value="${sicresConnectorClass}"/>';
	var thirdPartyAPIClass = '<c:out value="${thirdPartyAPIClass}"/>';
	var readonly = document.defaultForm.elements[ 'readonly' ].value;
	
	if (sicresConnectorClass != '') {
	   	freg = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:FREG)' ];
	    if (freg.value != '') {
			setReadOnly(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NREG)' ]);
	    }
  	}
	
	if (thirdPartyAPIClass != '') {

	    //Activacion radio tercero
	    idtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ];
	    nifciftitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ];
	    identidadtitular = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)' ];
	    if (idtitular.value != '') {
	    	checkRadioById('validatedThirdParty');
	    	setReadOnly(nifciftitular);
	    } else {
	    	
	    	if ((nifciftitular.value != '') ||
	    		(identidadtitular.value != '')) {
	    	 	
				checkRadioById('notValidatedThirdParty');
	    	 	//setReadOnly(document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:NIFCIFTITULAR)' ]);
	    	 	
	    	 	if (readonly != 'true') {
		    	 	setNotReadOnlyIdentidad(identidadtitular);
	    	 	}
	    	 }
	    }

	    //Activacion radio direccion postal
	    iddireccionpostal = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)' ];

	    if (iddireccionpostal.value != '') {
	    	checkRadioById('validatedPostalAddressThirdParty');
			setReadOnlyPostalAddressFields();

	    } else {
	    	direccion = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ];
	    	codigoPostal = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CPOSTAL)' ];
	    	ciudad = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:CIUDAD)' ];
	    	regionPais = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:REGIONPAIS)' ];
	    	tfnoFijo = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:TFNOFIJO)' ];

			//Si el tercero es no validado o se ha marcado la direccion como libre se desactiva la validacion de la direccion 
	    	if ( 
	    	   (direccion.value != '') || (codigoPostal.value != '') || (ciudad.value != '')  || (regionPais.value != '') || (tfnoFijo.value != '')
	    	 || (idtitular.value == '' &&  (nifciftitular.value != '' || identidadtitular.value != '' ) ) 
	    	) {
				checkRadioById('notValidatedPostalAddressThirdParty');
	    	 	if (readonly != 'true') {
					setNotReadOnlyPostalAddressFields();
					if ((idtitular.value == '' &&  (nifciftitular.value != '' || identidadtitular.value != '' ) )){
						disableSearchPostal(false);
					}
	    	 	}
	    	}
	    }
	}else{
		clickNotValidatedPostalAddressThirdParty();
	}	
		
	if (readonly != 'true') {
		
		// Para informar si se intenta salir del formulario sin guardar
		// ispac_formobserver_install(this, '<bean:message key="forms.message.confirm.exit"/>');
		// ispac_load_formvalues();
	}
	else {
		disabledRadioById('validatedThirdParty');
		disabledRadioById('notValidatedThirdParty');
		disabledRadioById('notValidatedPostalAddressThirdParty');
		disabledRadioById('validatedPostalAddressThirdParty');
	}
    
//--></script>

<%-- Para informar si se intenta salir del formulario sin guardar --%>
<tiles:insert template="/forms/common/observer.jsp"/>
