<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">


<!-- ESTABLECER AL ACEPTAR LOS VALORES DEL REGISTRO ENCONTRADO -->
<script>
	function accept() {
		setValues();
		acceptRegistry();
	}
</script>


<!-- ESTABLECER AL ACEPTAR LOS VALORES DEL REGISTRO ENCONTRADO -->
<logic:present name="Value">
	<ispac:updatefields name="Value" parameters='SEARCH_SPAC_DT_DOCUMENTOS_NREG_SALIDA' function="true"/>
</logic:present>

		<div class="ficha"> 
		
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<div class="acciones_ficha">
						<a href="#" id="btnOkShowRegister"  onclick="javascript:accept();" class="btnOk"><bean:message key="common.message.ok"/></a>				
						<a href="#" id="btnCancelShowRegister"  onclick="javascript:cancelRegistry();" class="btnCancel"><bean:message key="common.message.cancel"/></a>				
					</div>
					
				</div>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
				
					<div class="cabecera_seccion">
			            <logic:equal name="TIPO_REGISTRO" value="ENTRADA" scope="request">
            				<h4><bean:message key="registro.entrada.datos"/></h4>
            			</logic:equal>
            			<logic:equal name="TIPO_REGISTRO" value="SALIDA" scope="request">
							<h4><bean:message key="registro.salida.datos"/></h4>
            			</logic:equal>
					</div>

						<p>
						<!-- REGISTRO DE SALIDA NO ENCONTRADO -->
						<logic:equal name="REG_FOUND" value="false" scope="request">
							<div>
                				<logic:equal name="TIPO_REGISTRO" value="ENTRADA" scope="request">
                  					<bean:message key="registro.entrada.notfound"/>
                				</logic:equal>
                				<logic:equal name="TIPO_REGISTRO" value="SALIDA" scope="request">
									<bean:message key="registro.salida.notfound"/>
                				</logic:equal>
								<bean:write name="NUMERO_REGISTRO"/>
							</div>			
						</logic:equal>
						
						<!-- REGISTRO DE SALIDA ENCONTRADO -->
						<logic:equal name="REG_FOUND" value="true" scope="request">
						
							<!-- NUMERO DE REGISTRO -->
							<div>	
								<label class="popUpInfo" ><nobr><bean:message key="registro.salida.numero"/></nobr></label>
								<label><bean:write name="NUMERO_REGISTRO"/></label>
							
							</div>
							<!-- FECHA DE REGISTRO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.salida.fecha"/></nobr></label>	
								<label><bean:write name="FECHA_REGISTRO"/></label>
							</div>
							<!-- USUARIO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.salida.usuario"/></nobr></label>
								<logic:present name="USUARIO">
									<label><bean:write name="USUARIO"/></label>
								</logic:present>
							</div>		
						
              				<logic:equal name="TIPO_REGISTRO" value="ENTRADA" scope="request">
                				<!-- ORIGEN -->
                				<div>
                  					<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.remitente"/></nobr></label>
                    				<logic:present name="REMITENTES">
                      					<label><bean:write name="REMITENTES"/></label>
                    				</logic:present>
                				</div>
              				</logic:equal>
              
              				<logic:equal name="TIPO_REGISTRO" value="SALIDA" scope="request">
								<!-- DESTINO -->
								<div>
									<label class="popUpInfo" ><nobr><bean:message key="registro.salida.destinatarios"/></nobr></label>
									<logic:present name="DESTINATARIOS">
										<label><bean:write name="DESTINATARIOS"/></label>
									</logic:present>
								</div>
              				</logic:equal>
            
							<!-- OFICINA REGISTRO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.salida.oficina.registro"/></nobr></label>
									<logic:present name="OFICINA_REGISTRO">
										<label><bean:write name="OFICINA_REGISTRO"/></label>
									</logic:present>
							</div>	
							<!-- FECHA DE CREACIÓN -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.salida.fecha"/></nobr></label>
									<logic:present name="FECHA_CREACION">
										<label><bean:write name="FECHA_CREACION"/></label>
									</logic:present>
							</div>		
								
							<!-- ORGANISMO ORIGEN -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.salida.origen"/></nobr></label>
									<logic:present name="ORGANISMO_ORIGEN">
										<label><bean:write name="ORGANISMO_ORIGEN"/></label>
									</logic:present>
							</div>		
							<!-- ORGANISMO DESTINO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.destino"/></nobr></label>
									<logic:present name="ORGANISMO_DESTINO">
									<label>	<bean:write name="ORGANISMO_DESTINO"/></label>
									</logic:present>
							</div>	
							<!-- ASUNTO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.tipo.asunto"/></nobr></label>
									<logic:present name="ASUNTO">
										<label><bean:write name="ASUNTO"/></label>
									</logic:present>
							</div>	
							<!-- RESUMEN -->
							<div>
							<label class="popUpInfo" ><nobr><bean:message key="registro.salida.resumen"/></nobr></label>
								<logic:present name="RESUMEN">
									<label><bean:write name="RESUMEN"/></label>
								</logic:present>
							</div>	
							
						</logic:equal>
	
						</p>

				</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

