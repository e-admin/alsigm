package es.ieci.plusvalias.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.DireccionInmuebleType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType.DATOSBANCARIOSType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType.IMPORTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ObjectFactory;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.ADQUIRENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.DOCUMENTOSType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.DOCUMENTOSType.DOCUMENTOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.DOCUMENTOSType.DOCUMENTOType.IMPORTEType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.INMUEBLEType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.REPRESENTANTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.RESULTADOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.TRANSMITENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Sujeto;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.config.Configuration;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;
import es.ieci.plusvalias.util.CalculoPlusvaliaHelper;
import es.ieci.plusvalias.util.CalculoPlusvaliaValidatorHelper;
import es.ieci.plusvalias.util.DateUtils;
import es.ieci.plusvalias.util.NumberConversionUtil;
import es.ieci.plusvalias.util.PagoPlusvaliaHelper;

/**
 * @author angel_castro@ieci.es - 23/07/2010
 */
public class PagoLiquidacionService implements MessageSourceAware
{
	private MessageSource messageSource;
	private ObjectFactory factory;
	private ReportService reporter;
	private CalculoPlusvaliaHelper calculoHelper;
	private CalculoPlusvaliaValidatorHelper calculoValidatorHelper;
	private PagoPlusvaliaHelper pagoHelper;

	private GestorExpedienteService expedienteService;
	private CalculoLiquidacionService calculoService;

	public static final Logger logger = Logger.getLogger(PagoLiquidacionService.class);

	public PagoLiquidacionService()
	{
		super();
		this.factory = new ObjectFactory();
	}

	public REPLYType pagoLiquidacion(REQUESTType request, String xmlMessage) throws Exception
	{
		IPlusvaliaEntidadService entidadService=Configuration.getPlusvaliaEntidadService();

		REPLYType response = factory.createPAGOLIQUIDACIONTypeREPLYType();
		RESULTADOType resultado = factory.createPAGOLIQUIDACIONTypeREPLYTypeRESULTADOType();

		// Comprobamos el código de acto jurídico
		String code = request.getACTOJURIDICO().getCODIGO();

		// Creamos array de adquirientes
		Adquiriente[] adquirientes = calculoHelper.getAdquirientes(request.getADQUIRENTES().getADQUIRENTE());

		// Valida la clase de derecho
		calculoValidatorHelper.validarDerecho(adquirientes);

		// Creamos array de transmitentes
		Transmitente[] transmitentes = calculoHelper.getTransmitentes(request.getTRANSMITENTES().getTRANSMITENTE());

		// Recogemos fecha de transmision actual, fecha de autorización del protocolo
		DatosLiquidacion datosLiquidacion = new DatosLiquidacion();
		datosLiquidacion.setFechaTransActual(NumberConversionUtil.parseDate(request.getPROTOCOLO().getFECHAAUTORIZACION()));

		String refCatastral = request.getINMUEBLE().getREFERENCIACATASTRAL();

		// Comprobamos si existe la Plusvalía en la BBDD y si ya está pagada
		entidadService.comprobar(refCatastral, transmitentes, adquirientes, code, true);

		// Realizamos el cálculo
		logger.debug("Calculando las liquidaciones para pago....");

		String notario = calculoHelper.getNotarioNombreCompleto(request.getNOTARIOTITULAR());

		Plusvalia[] plusvalias = calculoService.calcular(code, refCatastral, adquirientes, transmitentes, datosLiquidacion,
				Integer.parseInt(request.getNOTARIOTITULAR().getCODIGO()), notario, (int)request.getPROTOCOLO().getNUMERO());

		double grandtotal = 0;
		double importeIndicado = 0;

		// Calculamos el total a pagar
		for (int i = 0; i < plusvalias.length; i++)
		{
			Plusvalia plusvalia = plusvalias[i];

			grandtotal = grandtotal + plusvalia.getResultado().getTotalLiquidacion();
		}

		List importesSujetoPasivo = request.getPAGO().getORDENPAGO().getPARAMETROSPAGO().getIMPORTES();

		// Sumamos los importes por sujeto pasivo
		for (int j = 0; j < importesSujetoPasivo.size(); j++)
		{
			double importe = NumberConversionUtil.parseNumber(((IMPORTESType)importesSujetoPasivo.get(j)).getIMPORTE());

			importeIndicado = importeIndicado + importe;
		}

		//Redondeamos a dos decimales
		grandtotal = Math.rint(grandtotal*100)/100;
		importeIndicado = Math.rint(importeIndicado*100)/100;

		// En caso de variar Importe solicitado e importe calculado
		if (grandtotal != importeIndicado)
		{
			throw new PlusvaliaException(ErrorCode.INVALID_PAYMENT_AMOUNT);
		}

		DOCUMENTOSType documentosReply = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSType();
		List adquirientesRequest2 = request.getADQUIRENTES().getADQUIRENTE();

		// Preparamos el resto de la respuesta
		logger.debug("Generando Reply....");

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

		TRANSMITENTESType transmitientesReply = factory.createPAGOLIQUIDACIONTypeREPLYTypeTRANSMITENTESType();
		transmitientesReply.getTRANSMITENTE().addAll(request.getTRANSMITENTES().getTRANSMITENTE());
		response.setTRANSMITENTES(transmitientesReply);

		ADQUIRENTESType adquirientesReply = factory.createPAGOLIQUIDACIONTypeREPLYTypeADQUIRENTESType();
		adquirientesReply.getADQUIRENTE().addAll(request.getADQUIRENTES().getADQUIRENTE());
		response.setADQUIRENTES(adquirientesReply);

		response.setREPRESENTANTES((REPRESENTANTESType)request.getREPRESENTANTES());
		Sujeto[] representantes = pagoHelper.getSujetos(request.getREPRESENTANTES());

		INMUEBLEType inmueble = factory.createPAGOLIQUIDACIONTypeREPLYTypeINMUEBLEType();
		DireccionInmuebleType direccionInmueble = factory.createDireccionInmuebleType();
		direccionInmueble.setVIA(plusvalias[0].getInmueble().getNombrevia());
		direccionInmueble.setNUMEROVIA(plusvalias[0].getInmueble().getNumero());
		// Código INE de Provincias y Municipios
		direccionInmueble.setINEPROVINCIA(request.getINMUEBLE().getDIRECCION().getINEPROVINCIA());
		direccionInmueble.setINEMUNICIPIO(request.getINMUEBLE().getDIRECCION().getINEMUNICIPIO());

		inmueble.setDIRECCION(direccionInmueble);
		inmueble.setREFERENCIACATASTRAL(refCatastral);
		inmueble.setSUPERFICIEINMUEBLE(NumberConversionUtil.parseDouble2(plusvalias[0].getInmueble().getSupsolar()));
		response.setINMUEBLE(inmueble);

		// Generamos los PDFs por adquiriente
		if (logger.isDebugEnabled()){
			logger.debug("Generando PDFs....");
		}

		for (int i = 0; i < plusvalias.length; i++){
			// Realizamos el pago
			DATOSBANCARIOSType datosBancarios = request.getPAGO().getORDENPAGO().getPARAMETROSPAGO().getDATOSBANCARIOS();

			Plusvalia plusvalia = plusvalias[i];

			if (logger.isInfoEnabled())
			{
				logger.info("Gestionando operacion de pago de plusvalia: " + plusvalia.toString());
			}

			ArrayList<byte[]> resguardos = expedienteService.registrarIniciarExpedientePagar(plusvalia, representantes, datosLiquidacion, xmlMessage,
					pagoHelper.getDatosBancarios(datosBancarios), messageSource,reporter);

			// Documento de desglose si hay
			if (resguardos.size() > 1)
			{
				IMPORTEType importeCero = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOTypeIMPORTEType();
				importeCero.setIMPORTETOTAL("0");

				// Por defecto no se recalcula recargo
				importeCero.setINFORMARECARGO(false);

				DOCUMENTOType documentoDesgloseReply = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOType();
				documentoDesgloseReply.setPDF(resguardos.get(0));
				documentoDesgloseReply.setIDSUJETO(Long.parseLong(plusvalia.getSujetoPasivo().getIdentificador()));
				documentoDesgloseReply.setIMPORTE(importeCero);

				documentosReply.getDOCUMENTO().add(documentoDesgloseReply);

				IMPORTEType importeParcial = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOTypeIMPORTEType();
				importeParcial.setIMPORTETOTAL(NumberConversionUtil.formatNumber(plusvalia.getResultado().getTotalLiquidacion()));

				// Por defecto no se recalcula recargo
				importeParcial.setINFORMARECARGO(false);

				DOCUMENTOType documentoReply = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOType();
				documentoReply.setPDF(resguardos.get(1));
				documentoReply.setIDSUJETO(Long.parseLong(plusvalia.getSujetoPasivo().getIdentificador()));
				documentoReply.setIMPORTE(importeParcial);

				documentosReply.getDOCUMENTO().add(documentoReply);
			}
			else
			{
				IMPORTEType importeParcial = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOTypeIMPORTEType();
				importeParcial.setIMPORTETOTAL(NumberConversionUtil.formatNumber(plusvalia.getResultado().getTotalLiquidacion()));

				// Por defecto no se recalcula recargo
				importeParcial.setINFORMARECARGO(false);

				DOCUMENTOType documentoReply = factory.createPAGOLIQUIDACIONTypeREPLYTypeDOCUMENTOSTypeDOCUMENTOType();
				documentoReply.setPDF(resguardos.get(0));
				documentoReply.setIDSUJETO(Long.parseLong(plusvalia.getSujetoPasivo().getIdentificador()));
				documentoReply.setIMPORTE(importeParcial);

				documentosReply.getDOCUMENTO().add(documentoReply);
			}
		}

		response.setDOCUMENTOS(documentosReply);
		response.setIMPORTETOTAL(NumberConversionUtil.formatNumber(grandtotal));

		resultado.setRETORNO(false);

		response.setRESULTADO(resultado);

		return response;
	}


	public void setCalculoHelper(CalculoPlusvaliaHelper helper)
	{
		this.calculoHelper = helper;
	}

	public void setPagoHelper(PagoPlusvaliaHelper helper)
	{
		this.pagoHelper = helper;
	}

	public void setReporter(ReportService reporter)
	{
		this.reporter = reporter;
	}

	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;

	}

	public void setCalculoValidatorHelper(
			CalculoPlusvaliaValidatorHelper calculoValidatorHelper) {
		this.calculoValidatorHelper = calculoValidatorHelper;
	}

	public void setExpedienteService(GestorExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void setCalculoService(CalculoLiquidacionService calculoService) {
		this.calculoService = calculoService;
	}
}