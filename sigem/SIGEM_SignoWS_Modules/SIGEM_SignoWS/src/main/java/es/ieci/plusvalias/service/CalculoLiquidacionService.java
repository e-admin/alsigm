package es.ieci.plusvalias.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.ADQUIRENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.DOCUMENTOSType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.DOCUMENTOSType.DOCUMENTOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.DOCUMENTOSType.DOCUMENTOType.IMPORTEType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.INMUEBLEType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.RESULTADOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.TRANSMITENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REQUESTType;
import org.notariado.ancert.xml.plusvalias.liquidacion.DireccionInmuebleType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ObjectFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.config.Configuration;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;
import es.ieci.plusvalias.util.CalculoPlusvaliaHelper;
import es.ieci.plusvalias.util.CalculoPlusvaliaValidatorHelper;
import es.ieci.plusvalias.util.DateUtils;
import es.ieci.plusvalias.util.NumberConversionUtil;

/**
 * @author angel_castro@ieci.es - 21/07/2010
 */
public class CalculoLiquidacionService implements ICalculoLiquidacion
{
	private ObjectFactory factory;
	private CalculoPlusvaliaHelper helper;
	private CalculoPlusvaliaValidatorHelper validatorHelper;
	private ReportService reporter;
	public static final Logger logger = Logger.getLogger(CalculoLiquidacionService.class);

	public CalculoLiquidacionService()
	{
		super();

		this.factory = new ObjectFactory();
	}

	public REPLYType calculoLiquidacion(REQUESTType request, String xmlMessage) throws Exception
	{
		IPlusvaliaEntidadService entidadService=Configuration.getPlusvaliaEntidadService();
		REPLYType response = factory.createCALCULOLIQUIDACIONTypeREPLYType();
		RESULTADOType resultado = factory.createCALCULOLIQUIDACIONTypeREPLYTypeRESULTADOType();

		// Comprobamos el código de acto jurídico
		String code = request.getACTOJURIDICO().getCODIGO();

//		if (!validatorHelper.isActoJuridicoValido(code))
//		{
//			throw new PlusvaliaException(ErrorCode.NOT_PLUSVALIA);
//		}

		// Creamos array de adquirientes
		Adquiriente[] adquirientes = helper.getAdquirientes(request.getADQUIRENTES().getADQUIRENTE());

		// Valida la clase de derecho
		validatorHelper.validarDerecho(adquirientes);

		// Creamos array de transmitentes
		Transmitente[] transmitentes = helper.getTransmitentes(request.getTRANSMITENTES().getTRANSMITENTE());

		// Recogemos fecha de transmision actual, fecha de autorización del protocolo
		DatosLiquidacion datosLiquidacion = new DatosLiquidacion();
		datosLiquidacion.setFechaTransActual(NumberConversionUtil.parseDate(request.getPROTOCOLO().getFECHAAUTORIZACION()));

		String refCatastral = request.getINMUEBLE().getREFERENCIACATASTRAL();
//		String tipoAncert = helper.getTipoAncert(code);

		// Comprobamos si existe la Plusvalía en la BBDD y si ya está pagada
		entidadService.comprobar(refCatastral, transmitentes, adquirientes, code, false);

		// Realizamos el cálculo
		logger.debug("Calculando las liquidaciones para borrador....");

		String notario = helper.getNotarioNombreCompleto(request.getNOTARIOTITULAR());

		Plusvalia[] plusvalias = calcular(code, refCatastral, adquirientes, transmitentes, datosLiquidacion,
				Integer.parseInt(request.getNOTARIOTITULAR().getCODIGO()), notario, (int)request.getPROTOCOLO().getNUMERO());

		// Preparamos el resto de la respuesta
		logger.debug("Generando Reply....");

		TRANSMITENTESType transmitientesReply = factory.createCALCULOLIQUIDACIONTypeREPLYTypeTRANSMITENTESType();
		transmitientesReply.getTRANSMITENTE().addAll(request.getTRANSMITENTES().getTRANSMITENTE());
		response.setTRANSMITENTES(transmitientesReply);

		ADQUIRENTESType adquirientesReply = factory.createCALCULOLIQUIDACIONTypeREPLYTypeADQUIRENTESType();
		adquirientesReply.getADQUIRENTE().addAll(request.getADQUIRENTES().getADQUIRENTE());
		response.setADQUIRENTES(adquirientesReply);

		if (request.getREPRESENTANTES() != null)
		{
			org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType.REPRESENTANTESType representantesReply = factory
					.createCALCULOLIQUIDACIONTypeREPLYTypeREPRESENTANTESType();
			representantesReply.getREPRESENTANTE().addAll(request.getREPRESENTANTES().getREPRESENTANTE());
			response.setREPRESENTANTES(representantesReply);
		}

		String emisorRequest = request.getCABECERA().getEMISOR();
		String receptorRequest = request.getCABECERA().getRECEPTOR();

		response.setCABECERA(request.getCABECERA());

		// Código INE de Badajoz 06-0153
		response.getCABECERA().setEMISOR(receptorRequest);
		response.getCABECERA().setRECEPTOR(emisorRequest);

		response.setNOTARIOTITULAR(request.getNOTARIOTITULAR());
		response.setNOTARIOAUTORIZANTE(request.getNOTARIOAUTORIZANTE());
		response.setPROTOCOLO(request.getPROTOCOLO());
		response.setACTOJURIDICO(request.getACTOJURIDICO());

		INMUEBLEType inmueble = factory.createCALCULOLIQUIDACIONTypeREPLYTypeINMUEBLEType();
		DireccionInmuebleType direccionInmueble = factory.createDireccionInmuebleType();
		direccionInmueble.setVIA(plusvalias[0].getInmueble().getNombrevia());
		direccionInmueble.setNUMEROVIA(plusvalias[0].getInmueble().getNumero());

		// Código INE de Provincias y Municipios
		direccionInmueble.setINEPROVINCIA(request.getINMUEBLE().getDIRECCION().getINEPROVINCIA());
		direccionInmueble.setINEMUNICIPIO(request.getINMUEBLE().getDIRECCION().getINEMUNICIPIO());

		inmueble.setDIRECCION(direccionInmueble);
		inmueble.setREFERENCIACATASTRAL(request.getINMUEBLE().getREFERENCIACATASTRAL());
		inmueble.setSUPERFICIEINMUEBLE(NumberConversionUtil.parseDouble2(plusvalias[0].getInmueble().getSupsolar()));
		response.setINMUEBLE(inmueble);

		// Generamos los PDFs por adquiriente
		DOCUMENTOSType documentosReply = factory.createCALCULOLIQUIDACIONTypeREPLYTypeDOCUMENTOSType();
		logger.debug("Generando PDFs....");

		double grandtotal = 0;

		for (int i = 0; i < plusvalias.length; i++)
		{
			Plusvalia plusvalia = plusvalias[i];

			grandtotal = grandtotal + plusvalia.getResultado().getTotalLiquidacion();

			byte[] pdf = reporter.generarReport("calculo", plusvalia);

			DOCUMENTOType documentoReply = factory.createCALCULOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOType();

			IMPORTEType importeParcial = factory.createCALCULOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOTypeIMPORTEType();
			importeParcial.setIMPORTETOTAL(NumberConversionUtil.formatNumber(plusvalia.getResultado().getTotalLiquidacion()));

			if (plusvalia.getResultado().getRecargo() != 0)
			{
				importeParcial.setINFORMARECARGO(true);
				importeParcial.setIMPORTERECARGO(NumberConversionUtil.formatNumber(plusvalia.getResultado().getRecargo()));
				importeParcial.setPORCENTAJERECARGO(NumberConversionUtil.formatNumber(plusvalia.getResultado().getPorcentajeRecargo()));
			}
			else
			{
				importeParcial.setINFORMARECARGO(false);
			}

			documentoReply.setPDF(pdf);
			documentoReply.setIDSUJETO(Long.parseLong(plusvalia.getSujetoPasivo().getIdentificador()));
			documentoReply.setIMPORTE(importeParcial);

			documentosReply.getDOCUMENTO().add(documentoReply);
		}

		response.setDOCUMENTOS(documentosReply);

		response.setIMPORTETOTAL(NumberConversionUtil.formatNumber(grandtotal));

		resultado.setRETORNO(false);

		response.setRESULTADO(resultado);

		// Registramos las plusvalías en la BBDD
		//helper.registrar(plusvalias);

		return response;
	}


	/**
	 * Calculamos los datos del inmueble y dividimos los calculos por transmitientes.
	 *
	 * Nos queda una plusvalia por cada transmitiente-adquiriente
	 *
	 * @param refCatastral
	 * @param adquirientes
	 * @param transmitientes
	 * @param datosLiquidacion
	 * @return
	 * @throws Exception
	 */
	public Plusvalia[] calcular(String actoJuridico, String refCatastral, Adquiriente[] adquirientes, Transmitente[] transmitentes,
			DatosLiquidacion datosLiquidacion, int numNotario, String nombreNotario, int numProtocolo) throws Exception	{
		IPlusvaliaEntidadService entidadService=Configuration.getPlusvaliaEntidadService();
		ArrayList<Plusvalia> plusvalias = new ArrayList<Plusvalia>();

		//tipoAncert = getTipoAncert(actoJuridico);
		//Map propiedades = entidadService.getPlusvaliaProperties(anyoLiquidacionActual);
		Inmueble inmueble = entidadService.getInmueble(refCatastral, datosLiquidacion);

		if (inmueble == null) {
			throw new PlusvaliaException(ErrorCode.UNKNOWN_CADASTRAL_REFERENCE);
		}

		Plusvalia plusvalia = null;

		for (int cont = 0; cont < transmitentes.length; cont++)	{
			Transmitente transmitente = transmitentes[cont];

			String nifTransmitiente = transmitente.getNif();

			// Si es por herencia, la fecha de transmisión actual es la fecha de defunción
			if (entidadService.isPlusvaliaHerencia(actoJuridico) && transmitente.getFechaDefuncion() != null) {
				datosLiquidacion.setFechaTransActual(transmitente.getFechaDefuncion());
				nifTransmitiente = "HERENCIA";
			}

			for (int j = 0; j < adquirientes.length; j++){
				Adquiriente adquiriente = adquirientes[j];

				if (entidadService.isDerechosCompatible(transmitente, adquiriente))	{
					ResultadoUnitario resultado = entidadService.calcular(actoJuridico,inmueble, adquiriente, transmitente, datosLiquidacion);

					plusvalia = new Plusvalia();

					plusvalia.setTransmitente(transmitente);
					plusvalia.setAdquiriente(adquiriente);

					plusvalia.setResultado(resultado);

					plusvalia.setNumNotario(numNotario);
					plusvalia.setNotario(nombreNotario);
					plusvalia.setNumProtocolo(numProtocolo);

					plusvalia.setNifAdq(adquiriente.getNif());
					plusvalia.setNombreAdqui(adquiriente.getNombreCompleto());
					plusvalia.setClaseDerecho(transmitente.getNumDerecho());
					plusvalia.setEdadTransmitiente(transmitente.getEdad());
					plusvalia.setPorcAdquirido(adquiriente.getCuotaParticipacion());
					plusvalia.setDiasPlazo(resultado.getNumDias());

					if (resultado.getTotalLiquidacion() == 0){
						plusvalia.setPagada(true);
					}else{
						plusvalia.setPagada(false);
					}

					//plusvalia.setNifTrans(nifTransmitiente);
					plusvalia.setNifTrans(transmitente.getNif());
					plusvalia.setNifTransAyto(nifTransmitiente);
					plusvalia.setNombreTrans(transmitente.getNombreCompleto());
					plusvalia.setPorcTransmitido(transmitente.getPorcentajeTransmitido());
					plusvalia.setPorcBonificacion(transmitente.getBonificacion());

					plusvalia.setFechatactual(datosLiquidacion.getFechaTransActual());
					plusvalia.setFechatanterior(transmitente.getFechaTransAnterior());

					plusvalia.setInmueble(inmueble);


					// TODO NO RESIDENTES
					if (entidadService.isPlusvaliaHerencia(actoJuridico) ||
							entidadService.isPlusvaliaDonacion(actoJuridico)){
						plusvalia.setSujetoPasivo(adquiriente);
					}else{
						plusvalia.setSujetoPasivo(transmitente);
					}

					entidadService.completarPlusvalia(plusvalia, actoJuridico,inmueble, adquiriente, transmitente, datosLiquidacion);
					plusvalias.add(plusvalia);


				}
			}
		}
		return plusvalias.toArray(new Plusvalia[plusvalias.size()]);
	}




	public void setHelper(CalculoPlusvaliaHelper helper)
	{
		this.helper = helper;
	}

	public void setValidatorHelper(CalculoPlusvaliaValidatorHelper validatorHelper)
	{
		this.validatorHelper = validatorHelper;
	}

	public void setReporter(ReportService reporter)
	{
		this.reporter = reporter;
	}
}