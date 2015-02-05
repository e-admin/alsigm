package es.ieci.plusvalias.service;

import java.util.ArrayList;
import java.util.Date;

import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.pe.ws.client.Liquidacion;
import ieci.tecdoc.sgm.pe.ws.client.Pago;
import ieci.tecdoc.sgm.registro.util.database.RegistroDatos;
import ieci.tecdoc.sgm.tram.ws.client.dto.Cadena;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType.DATOSBANCARIOSType;
import org.springframework.context.MessageSource;

import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.api.DatosBancarios;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.Sujeto;
import es.ieci.plusvalias.config.Configuration;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;
import es.ieci.plusvalias.sigem.receipt.KeyStoreData;
import es.ieci.plusvalias.util.CalculoPlusvaliaHelper;
import es.ieci.plusvalias.util.DateUtils;
import es.ieci.plusvalias.util.PagoPlusvaliaHelper;
import es.ieci.plusvalias.util.SigmHelper;
import es.ieci.sigem.PagoTelematico;
import es.ieci.sigem.RegistroTelematico;
import es.ieci.sigem.Tramitacion;

public class GestorExpedienteService {
	public static Logger logger = Logger.getLogger(GestorExpedienteService.class);
	
	private SigmHelper sigemHelper;

	public RegistroDatos insertaRegistroTelematico(Plusvalia plusvalia, MessageSource messageSource) throws PlusvaliaException	{
		String entidad = Configuration.getIdEntidad();
		String destinoRegistro = Configuration.getRegistroOficina();
		String organoRegistro = Configuration.getRegistroOrgano();
		String asunto = Configuration.getRegistroAsunto();

		RegistroDatos registro = null;

		try{
			RegistroTelematico rt = new RegistroTelematico(entidad);

			registro = rt.registra(plusvalia.getSujetoPasivo().getNif(), plusvalia.getSujetoPasivo().getNombreCompleto(), null, asunto,
					destinoRegistro, organoRegistro, "");
		}catch (Exception e){
			logger.error("Error al insertar el registro telematico. " + e.getMessage(), e);
			throw new PlusvaliaException(ErrorCode.UNKNOWN);
		}

		return registro;
	}
	
	public boolean addDocumentoRegistroTelematico(String numRegistro, String extension, String codigo, byte[] docData, MessageSource messageSource)	{
		boolean ok = true;

		try{
			String entidad = Configuration.getIdEntidad();
			RegistroTelematico rt = new RegistroTelematico(entidad);
			rt.storeAndRegisterDocument(numRegistro, codigo, extension, docData);
		}catch (Exception e){
			logger.debug(e.getMessage(), e);
			ok = false;
		}

		return ok;
	}
	
	public ArrayList<byte[]> registrarIniciarExpedientePagar(Plusvalia plusvalia, Sujeto[] representantes, DatosLiquidacion datosLiquidacion,
			String xmlSolicitud, DatosBancarios datosBancarios, MessageSource messageSource, ReportService reporter) throws Exception {
		IPlusvaliaEntidadService entidadService=Configuration.getPlusvaliaEntidadService();
		
		Plusvalia plusvaliaRegistrada = entidadService.getPlusvalia(plusvalia.getInmueble().getRefCatastral(), plusvalia.getNifTrans(),
				plusvalia.getNifAdq(), plusvalia.getClaseDerecho());
		
		byte[] pdf = null;
		
		ArrayList<byte[]> docs = new ArrayList<byte[]>();
		
		/* ??? */String pagoEndpoint = Configuration.getPagoEndPoint();
		String idEntidad = Configuration.getIdEntidad();
		
		PagoTelematico pt = new PagoTelematico(pagoEndpoint);

		if(plusvaliaRegistrada == null){		
			RegistroDatos registro = insertaRegistroTelematico(plusvalia, messageSource);
			
			// Hacemos el registro e iniciamos el trámite
	
			RegistroTelematico rt = new RegistroTelematico(idEntidad);
			
			plusvalia.setFolderIdRegistro(registro.getRegistryNumber());
			byte[] desglosePago = reporter.generarReport("calculo", plusvalia);
			docs.add(desglosePago);
	
			// Insertamos los documentos en el registro
			boolean registroOk = addDocumentoRegistroTelematico(registro.getRegistryNumber(), RegistroTelematico.EXTENSION_PDF,
					RegistroTelematico.CODIGO_JUSTIFICANTE, desglosePago, messageSource);
	
			if (registroOk)	{
				String xml = Configuration.getRegistroXmlSolicitud();
	
				xml = xml.replaceFirst("#DATOS_ESPECIFICOS#", xmlSolicitud); 
				logger.debug("XML de solicitud del registro [" + registro.getRegistryNumber() +"]: " + xml);
	
				registroOk = addDocumentoRegistroTelematico(registro.getRegistryNumber(), RegistroTelematico.EXTENSION_XML,
						RegistroTelematico.CODIGO_SOLICITUD, Goodies.fromStrToUTF8(xml), messageSource);
				
				if (registroOk){
					String entidadEmisora = Configuration.getPagoEntidadEmisora();
					String idTasa = Configuration.getPagoIdTasa();
					/* ??? */String diasPlazo = String.valueOf(DateUtils.getDiasPlazoLiquidacion());
	
					/* ??? */String detalle = sigemHelper.getDetalleLiquidacion(plusvalia, Configuration.getPagoDetalle());
					
					boolean liquidacionOK = false;
					String referenciaLiquidacion = "";
					String errorCodeLiquidacion = "";
					
					Liquidacion liquidacion = null;
					
					if (plusvalia.getResultado().getTotalLiquidacion() == 0){
						liquidacionOK = true;
					}else{
						// Damos de alta la liquidación en el pago telemático
						liquidacion = pt.altaLiquidacion(idEntidad, new Date(), entidadEmisora, idTasa, plusvalia.getSujetoPasivo().getNif(),
								plusvalia.getSujetoPasivo().getNombreCompleto(), diasPlazo, plusvalia.getResultado().getTotalLiquidacion(), detalle);
						
						liquidacionOK = liquidacion.getReturnCode().equals("OK");
						
						if (liquidacionOK)
						{
							referenciaLiquidacion = liquidacion.getReferencia();
						}
						else
						{
							errorCodeLiquidacion = liquidacion.getErrorCode();
						}
					}				
	
					if (liquidacionOK){
						plusvalia.setReferenciaPago(referenciaLiquidacion);
	
						/* ??? */String tramitacionEndpoint = Configuration.getTramitacionEndPoint();
						String codProcedimiento = Configuration.getTramitacionCodProcedimiento();
						String idFaseCatalogo = Configuration.getTramitacionIdFaseTerminacion();
						String datosEspecificos = Configuration.getTramitacionDatosEspecificos();
	
						Tramitacion t = new Tramitacion(tramitacionEndpoint);
	
						// Creamos el expediente en el tramitador
						Cadena expediente = t.crearExpediente(idEntidad, codProcedimiento, sigemHelper.getDatosEspecificos(plusvalia, plusvalia.getTransmitente(), datosEspecificos),
								registro.getRegistryNumber(), registro.getRegistryDate(), plusvalia.getSujetoPasivo(), representantes,
								sigemHelper.getDocumentosTramitacion(rt, registro.getRegistryNumber()));
	
						if (expediente.getReturnCode().equals("OK")) {
							// Movemos el expediente a fase de terminación
							t.moverExpedienteAFase(idEntidad, expediente.getValor(), idFaseCatalogo);
	
							rt.actualizaRegistro(registro);
	
							plusvalia.setNumExpediente(expediente.getValor());
							
							// Guardamos la plusvalia
							entidadService.almacenarPlusvalia(plusvalia);
							
							pdf = realizarPago(plusvalia, datosBancarios, pt, liquidacion, entidadService);
							
							//TODO
							plusvalia.setPagada(true);
							
							// Guardamos el pago.....
							entidadService.almacenarPlusvalia(plusvalia);
						}
						else
						{
							// TODO Anular el registro
							logger.error("Error al iniciar el trámite. ErrorCode:" + expediente.getErrorCode());
							
							if (!referenciaLiquidacion.equals(""))
							{
								pt.bajaLiquidacion(idEntidad, referenciaLiquidacion);
							}
	
							rt.eliminaRegistro(registro.getRegistryNumber());
	
							throw new PlusvaliaException(ErrorCode.UNKNOWN);
						}
					}else{
						// TODO Anular Registro
						logger.error("Error al dar de alta la liquidación. ErrorCode:" + errorCodeLiquidacion);
	
						rt.eliminaRegistro(registro.getRegistryNumber());
	
						throw new PlusvaliaException(ErrorCode.UNKNOWN);
					}
				}else{
					logger.error("Error al registrar la solicitud. Num registro:" + registro.getRegistryNumber());
	
					rt.eliminaRegistro(registro.getRegistryNumber());
	
					throw new PlusvaliaException(ErrorCode.UNKNOWN);
				}
			}else{
				logger.error("Error al registrar la solicitud. Num registro:" + registro.getRegistryNumber());
	
				rt.eliminaRegistro(registro.getRegistryNumber());
	
				throw new PlusvaliaException(ErrorCode.UNKNOWN);
			}
		}else{	
			if (plusvaliaRegistrada.getResultado().getTotalLiquidacion() == 
					(Math.rint(plusvalia.getResultado().getTotalLiquidacion() * 100) / 100)) {
				byte[] desglosePago = reporter.generarReport("calculo", plusvalia);
				docs.add(desglosePago);
				
				Liquidacion liquidacion = pt.buscarLiquidacion(idEntidad, plusvaliaRegistrada.getReferenciaPago());
				
				plusvalia.setReferenciaPago(plusvaliaRegistrada.getReferenciaPago());
				
				pdf = realizarPago(plusvalia, datosBancarios, pt, liquidacion, entidadService);
				
				// Guardamos el pago.....
				entidadService.almacenarPlusvalia(plusvalia);
			}else{
				throw new PlusvaliaException(ErrorCode.INVALID_PAYMENT_AMOUNT);
			}			
		}
		
		if (pdf != null){
			docs.add(pdf);
		}
		
		return docs;
	}
	
	private byte[] realizarPago(Plusvalia plusvalia, DatosBancarios datosBancarios, 
			PagoTelematico pt, Liquidacion liquidacion,	IPlusvaliaEntidadService entidadService) throws Exception {
		byte[] resguardoPagoFirmado = null;
		
		if (liquidacion == null) {
			return resguardoPagoFirmado;
		}

		String idEntidad = Configuration.getIdEntidad();
		
		Pago pago = null;

		entidadService.prepagoProcess(plusvalia,datosBancarios, 
				sigemHelper.getLiquidacion(liquidacion));
		
		if (liquidacion.getEstado().equals(PagoTelematico.ESTADO_PAGADO)) {
			//Si ya se ha pagado sólo se devuelve el documento de pago
			pago = pt.getDetallePago(idEntidad, plusvalia.getReferenciaPago());
			
			pago.setLiquidacion(liquidacion);
			pago.setIdTasa(liquidacion.getIdTasa());
			pago.setIdEntidadEmisora(liquidacion.getIdEntidadEmisora());
			
			resguardoPagoFirmado = getResguardoPago(pt, pago);
		} else {
			// Si no están pagada se realiza el pago y se devuelve el resguardo del mismo			
			pago = pt.realizarPago(idEntidad, plusvalia.getNumExpediente(), plusvalia.getReferenciaPago(), datosBancarios.getEntidad(),
					datosBancarios.getOficina(), datosBancarios.getDc(), datosBancarios.getNumCuenta(), datosBancarios.getNifTitular(),
					datosBancarios.getNombreTitular());
			
			if (pago.getReturnCode().equals("OK")) {			
				pago.setLiquidacion(liquidacion);			

				resguardoPagoFirmado = getResguardoPago(pt, pago);

				/* ??? */String tramitacionEndpoint = Configuration.getTramitacionEndPoint();
				//String tipoDocumento = messageSource.getMessage("sigem.tramitacion.tipoDocumentos", null, null);
				String idFaseArchivo = Configuration.getTramitacionIdFaseArchivo();
				String nomResguardo = Configuration.getPagoNombreResguardo();
				//String nomSolicitud = messageSource.getMessage("sigem.pago.nombreSolicitud", null, null);
				//String nomEscritura = messageSource.getMessage("sigem.pago.nombreEscritura", null, null);

				Tramitacion t = new Tramitacion(tramitacionEndpoint);

				//Anexamos la escritura
				//anexarDocumentoExpediente(t, idEntidad, plusvalia.getNumExpediente(), escritura, nomEscritura, RegistroTelematico.EXTENSION_PDF, tipoDocumento);

				// Anexamos el resguardo del pago al expediente
				if (anexarDocumentoExpediente(t, idEntidad, plusvalia.getNumExpediente(), resguardoPagoFirmado, nomResguardo,
						RegistroTelematico.EXTENSION_PDF, RegistroTelematico.TRAMITADOR_JUSTIFICANTE))	{
					// Avanzamos el expediente de fase
					t.moverExpedienteAFase(idEntidad, plusvalia.getNumExpediente(), idFaseArchivo);
				} else {
					logger.error("No se ha podido anexar el resguardo");
				}
			} else {
				throw new PlusvaliaException(ErrorCode.PAYMENT_UNKNOW_ERROR);
			}
		}		
		
		entidadService.postpagoProcess(plusvalia,datosBancarios, 
				sigemHelper.getLiquidacion(liquidacion));
		
		return resguardoPagoFirmado;
	}
	
	private byte[] getResguardoPago(PagoTelematico pt, Pago pago) throws Exception	{
		byte[] resguardoPagoFirmado = null;
		
		String idEntidad = Configuration.getIdEntidad();
		String keystorePath = Configuration.getFirmaPdfKeyStorePath();
		String keystoreType = Configuration.getFirmaPdfKeyStoreType();
		String keystorePassword = Configuration.getFirmaPdfKeyStorePassword();
		String keystoreAlias = Configuration.getFirmaPdfKeyStoreAlias();
		String keystoreAliasPassword = Configuration.getFirmaPdfKeyStoreAliasPassword();
		String receiptPath = Configuration.getFirmaPdfReceiptPath();
		String reason = Configuration.getFirmaPdfReason();
		String field = Configuration.getFirmaPdfField();
		String location = Configuration.getFirmaPdfLocation();
		String text = Configuration.getFirmaPdfAux();

		KeyStoreData keystoreData = new KeyStoreData(keystorePath, keystoreType, keystorePassword, keystoreAlias, keystoreAliasPassword);

		resguardoPagoFirmado = pt.getResguardoPago(keystoreData, idEntidad, pago, receiptPath, reason, field, location, text);
		
		return resguardoPagoFirmado;
	}
	
	public boolean anexarDocumentoExpediente(Tramitacion t, String idEntidad, String numExp, byte[] contenido, String nomDocumento, String extension, String code)
		throws Exception {
		return t.anexarDocsExpediente(idEntidad, numExp, contenido, nomDocumento, extension, code);
	}

	public void setSigemHelper(SigmHelper sigemHelper) {
		this.sigemHelper = sigemHelper;
	}
}
