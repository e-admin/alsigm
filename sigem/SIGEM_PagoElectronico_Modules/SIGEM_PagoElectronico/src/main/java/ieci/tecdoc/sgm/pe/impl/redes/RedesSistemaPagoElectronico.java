package ieci.tecdoc.sgm.pe.impl.redes;
/*
 * $Id: RedesSistemaPagoElectronico.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.Cuaderno57;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad1_2;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad3;
import ieci.tecdoc.sgm.pe.SistemaPagoElectronicoBase;
import ieci.tecdoc.sgm.pe.Util;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

/**
 * Implementación para Red.es del sistema de pago electrónico.
 * @author Carlos Navas
 */
public class RedesSistemaPagoElectronico extends SistemaPagoElectronicoBase {

	private static final String REDES_OK_CODE = "00";
	
	/**
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(RedesSistemaPagoElectronico.class);


	/**
	 * Método que realiza un pago de Cuaderno60 Modalidades 1 y 2.
	 * @param Cuaderno60Modalidad1_2 Cuaderno con los datos del pago.
	 * @return Cuaderno60Modalidad1_2 Cuaderno con los datos completos del pago.
	 * @throws PagoElectronicoException En caso de producirse algún error.
	 */
	public Cuaderno60Modalidad1_2 pagoCuaderno60Modalidad1_2(Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion {
		if(logger.isDebugEnabled()){
			logger.debug("Comienzo de pago de Cuaderno60 Modalidad 1,2...");
		}
		
		Cuaderno60_1_2 oCuaderno = obtenerCuaderno60_1_2(poCuaderno);
		CuadernoRespuesta60_1_2 oCuadernoRespuesta = null;
		ServicioOrganismoWSServiceLocator locator = new ServicioOrganismoWSServiceLocator();
		try {
			oCuadernoRespuesta = locator.getServicioOrganismoWS().pago60_1_2(oCuaderno);
		} catch (RemoteException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch (ServiceException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch(Throwable t){
			logger.error("Error en invocación al servicio de pago de Red.es", t);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, t);
		}

		if(logger.isDebugEnabled()){
			StringBuffer sbMensaje = new StringBuffer("pagoCuaderno60Modalidad1_2 - Respuesta obtenida. Código de respuesta: ");
			if(oCuadernoRespuesta != null){
				sbMensaje.append(oCuadernoRespuesta.getCodigoRetorno());				
			}
			logger.debug(sbMensaje.toString());
			logger.debug("pagoCuaderno60Modalidad1_2 - Validando la respuesta...");
		}
		// Validamos la respuesta
		validarRespuesta(oCuadernoRespuesta);
		
		// Devolvemos el documento de ingreso con los datos rellenos
		poCuaderno = obtenerCuaderno60Modalidad1_2(oCuadernoRespuesta);
		
		if(logger.isDebugEnabled()){
			logger.debug("pagoCuaderno60Modalidad1_2 - Pago realizado con éxito.");
		}
		return poCuaderno;
	}

	
	/**
	 * Método que realiza una búsqueda sobre los pagos del Cuaderno60 Modalidades 1,2 existentes.
	 * @param CriterioBusquedaPago Criterios para realizar la búsqueda.
	 * @return Cuaderno60Modalidad1_1[] Array con los cuadernos que cumplen el criterio de búsqueda.
	 * @throws PagoElectronicoException En caso de producirse algún error.
	 */
	public Cuaderno60Modalidad1_2[] consultaCuaderno60Modalidad1_2(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion {
		BusquedaSop oBusqueda = obtenerCriterioBusquedaCuaderno60_1_2(oCriterio);
		CuadernoRespuesta60_1_2[] oRespuesta = null;
		ServicioOrganismoWSServiceLocator locator = new ServicioOrganismoWSServiceLocator();
		try {
			Object[] respuesta = locator.getServicioOrganismoWS().consulta(oBusqueda);
			oRespuesta = new CuadernoRespuesta60_1_2[respuesta.length];
			System.arraycopy(respuesta, 0, oRespuesta, 0, respuesta.length);
		} catch (RemoteException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch (ServiceException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch(Throwable t){
			logger.error("Error en invocación al servicio de pago de Red.es", t);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, t);
		}
		
		// validamos el resultado
		validarRespuesta(oRespuesta);
		
		return obtenerCuaderno60Modalidad1_2(oRespuesta);
	}

	/**
	 * Método que realiza un pago de Cuaderno60 Modalidad 3.
	 * @param Cuaderno60Modalidad3 Cuaderno con los datos del pago.
	 * @return Cuaderno60Modalidad3 Cuaderno con los datos completos del pago.
	 * @throws PagoElectronicoException En caso de producirse algún error.
	 */	
	public Cuaderno60Modalidad3 pagoCuaderno60Modalidad3(Cuaderno60Modalidad3 poCuaderno) throws PagoElectronicoExcepcion {
		if(logger.isDebugEnabled()){
			logger.debug("Comienzo de pago de Cuaderno60 Modalidad 3...");
		}
		
		Cuaderno60_3 oCuaderno = obtenerCuaderno60_3(poCuaderno);
		CuadernoRespuesta60_3 oCuadernoRespuesta = null;
		ServicioOrganismoWSServiceLocator locator = new ServicioOrganismoWSServiceLocator();
		try {
			oCuadernoRespuesta = locator.getServicioOrganismoWS().pago60_3(oCuaderno);
		} catch (RemoteException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch (ServiceException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch(Throwable t){
			logger.error("Error en invocación al servicio de pago de Red.es", t);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, t);
		}

		if(logger.isDebugEnabled()){
			StringBuffer sbMensaje = new StringBuffer("Respuesta obtenida. Código de respuesta: ");
			if(oCuadernoRespuesta != null){
				sbMensaje.append(oCuadernoRespuesta.getCodigoRetorno());				
			}
			logger.debug(sbMensaje.toString());
			logger.debug("Validando la respuesta...");
		}
		// Validamos la respuesta
		validarRespuesta(oCuadernoRespuesta);
		
		// Devolvemos el documento de ingreso con los datos rellenos
		poCuaderno = obtenerCuaderno60Modalidad3(oCuadernoRespuesta);
		
		if(logger.isDebugEnabled()){
			logger.debug("Pago realizado con éxito.");
		}
		return poCuaderno;
	}
	
	
	/**
	 * Método que realiza una búsqueda sobre los pagos del Cuaderno60 Modalidad 3 existente..
	 * @param CriterioBusquedaPago Criterios para realizar la búsqueda.
	 * @return Cuaderno60Modalidad3[] Array con los cuadernos que cumplen el criterio de búsqueda.
	 * @throws PagoElectronicoException En caso de producirse algún error.
	 */
	public Cuaderno60Modalidad3[] consultaCuaderno60Modalidad3(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion {
		BusquedaSop oBusqueda = obtenerCriterioBusquedaCuaderno60_3(oCriterio);
		CuadernoRespuesta60_3[] oRespuesta = null;
		ServicioOrganismoWSServiceLocator locator = new ServicioOrganismoWSServiceLocator();
		try {
			Object[] respuesta = locator.getServicioOrganismoWS().consulta(oBusqueda);
			oRespuesta = new CuadernoRespuesta60_3[respuesta.length];
			System.arraycopy(respuesta, 0, oRespuesta, 0, respuesta.length);
		} catch (RemoteException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch (ServiceException e) {
			logger.error("Error en invocación al servicio de pago de Red.es", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, e);
		} catch(Throwable t){
			logger.error("Error en invocación al servicio de pago de Red.es", t);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO, t);
		}
		
		// validamos el resultado
		validarRespuesta(oRespuesta);
		
		return obtenerCuaderno60Modalidad3(oRespuesta);
	}
	
	/**
	 * Método auxiliar que devuelve la implementación de un cuaderno 60_1_2 utilizada por el conector soap
	 * a partir de un bean de negocio.
	 * @param poDoc Bean de negocio que representa un cuaderno 60_1_2.
	 * @return Cuaderno60_1_2 Instancia del cuarderno usada por el conector soap.
	 */
	private Cuaderno60_1_2 obtenerCuaderno60_1_2(Cuaderno60Modalidad1_2 poDoc){
		Cuaderno60_1_2 oCuaderno = new Cuaderno60_1_2();
		
		// Parámetros de configuración
		oCuaderno.setTipo(Configuracion.obtenerPropiedad(Configuracion.KEY_TIPO_PETICION_ALTA));
		oCuaderno.setPasarela(Configuracion.obtenerPropiedad(Configuracion.KEY_PASARELA));
		oCuaderno.setReservado(Configuracion.obtenerPropiedad(Configuracion.KEY_RESERVADO));
		oCuaderno.setCpr(ConfiguracionComun.obtenerCPRModalidad(poDoc.getTipo()));
		
		// Parámetros comunes a los cuadernos 60
		oCuaderno.setOrganismoEmisor(poDoc.getOrganismoEmisor());	
		oCuaderno.setNifCertificado(poDoc.getNifCertificado());		
		oCuaderno.setNifRepresentante1(poDoc.getNifRepresentante1());
		oCuaderno.setNifRepresentante2(poDoc.getNifRepresentante2());
		oCuaderno.setNomRepresentante1(poDoc.getNomRepresentante1());
		oCuaderno.setNomRepresentante2(poDoc.getNomRepresentante2());
		oCuaderno.setCodEntidad(poDoc.getCodEntidad());
		oCuaderno.setIdentificadorMedioPago(poDoc.getIdentificadorMedioPago());
		oCuaderno.setImporte(poDoc.getImporte());
		oCuaderno.setCcc(poDoc.getCcc());
		//String panRedes=getConcatPanFechaCaducidadTarjeta(poDoc.getPan(),poDoc.getFecCaducidadTarjetaCredito());
		oCuaderno.setPan(poDoc.getPan());
		oCuaderno.setFecCaducidad(poDoc.getFecCaducidadTarjetaCredito());
		
		/*if (poDoc.getPan() != null && poDoc.getPan().length() > 0)
		{
			oCuaderno.setPan(poDoc.getPan());
		}
		else
		{
			oCuaderno.setPan("6011000000000004");
		}
		
		if (poDoc.getFecCaducidadTarjetaCredito() != null && poDoc.getFecCaducidadTarjetaCredito().length() > 0)
		{
			oCuaderno.setFecCaducidad(poDoc.getFecCaducidadTarjetaCredito());
		}
		else
		{
			oCuaderno.setFecCaducidad("1220");
		}*/
		
		oCuaderno.setFecha(poDoc.getFecha());
		oCuaderno.setHora(poDoc.getHora());

		// Parámetros específicos
		oCuaderno.setCccDomiciliacion(poDoc.getCccDomiciliacion());
		oCuaderno.setCodDomiciliacion(poDoc.getCodDomiciliacion());
		oCuaderno.setIdent1(poDoc.getIdent1());
		oCuaderno.setIdent2(poDoc.getIdent2());
		oCuaderno.setReferencia(poDoc.getReferencia());
		oCuaderno.setIdioma(poDoc.getIdioma());		
		
		oCuaderno.setUrlRetorno(Configuracion.obtenerPropiedad(Configuracion.KEY_URL_RETORNO));
		return oCuaderno;
	}

	/**
	 * Método auxiliar que devuelve la implementación de un cuaderno 60_3 utilizada por el conector soap
	 * a partir de un bean de negocio.
	 * @param poDoc Bean de negocio que representa un cuaderno 60_3.
	 * @return Cuaderno60_3 Instancia del cuarderno usada por el conector soap.
	 */
	private Cuaderno60_3 obtenerCuaderno60_3(Cuaderno60Modalidad3 poDoc){
		Cuaderno60_3 oCuaderno = new Cuaderno60_3();
		
		// Parámetros de configuración
		oCuaderno.setTipo(Configuracion.obtenerPropiedad(Configuracion.KEY_TIPO_PETICION_ALTA));
		oCuaderno.setPasarela(Configuracion.obtenerPropiedad(Configuracion.KEY_PASARELA));
		oCuaderno.setReservado(Configuracion.obtenerPropiedad(Configuracion.KEY_RESERVADO));
		oCuaderno.setCpr(ConfiguracionComun.obtenerCPRModalidad(Cuaderno60Modalidad3.MODALIDAD_3));
		
		// Parámetros comunes a los cuadernos 60
		oCuaderno.setOrganismoEmisor(poDoc.getOrganismoEmisor());
		oCuaderno.setNifCertificado(poDoc.getNifCertificado());
		oCuaderno.setNifRepresentante1(poDoc.getNifRepresentante1());
		oCuaderno.setNifRepresentante2(poDoc.getNifRepresentante2());
		oCuaderno.setNomRepresentante1(poDoc.getNomRepresentante1());
		oCuaderno.setNomRepresentante2(poDoc.getNomRepresentante2());		
		oCuaderno.setCodEntidad(poDoc.getCodEntidad());
		oCuaderno.setIdentificadorMedioPago(poDoc.getIdentificadorMedioPago());
		oCuaderno.setImporte(poDoc.getImporte());
		oCuaderno.setCcc(poDoc.getCcc());
		oCuaderno.setIdioma(poDoc.getIdioma());
		//String panRedes=getConcatPanFechaCaducidadTarjeta(poDoc.getPan(),poDoc.getFecCaducidadTarjetaCredito());
		oCuaderno.setPan(poDoc.getPan());
		oCuaderno.setFecCaducidad(poDoc.getFecCaducidadTarjetaCredito());
		oCuaderno.setFecha(poDoc.getFecha());
		oCuaderno.setHora(poDoc.getHora());
		
		// Parámetros específicos
		oCuaderno.setAcreditacionPagos(poDoc.getAcreditacionPagos());
		oCuaderno.setCodTributo(poDoc.getCodTributo());
		oCuaderno.setExpediente(poDoc.getExpediente());
		oCuaderno.setFechaDevengo(poDoc.getFechaDevengo());
		oCuaderno.setInformacionEspecifica(poDoc.getInformacionEspecifica());
		oCuaderno.setJustificante(poDoc.getJustificante());
		oCuaderno.setNifContribuyente(poDoc.getNifContribuyente());

		oCuaderno.setUrlRetorno(Configuracion.obtenerPropiedad(Configuracion.KEY_URL_RETORNO));
		return oCuaderno;
	}

	/**
	 * Método auxiliar que devuelve un bean de negocio a partir de la implementación de un cuaderno 60_1_2 utilizada por el conector soap
	 * @param Cuaderno60Modalidad1_2 Instancia del cuarderno usada por el conector soap.
	 * @return poCuaderno Bean de negocio que representa un cuaderno 60_1_2.
	 */
	private Cuaderno60Modalidad1_2 obtenerCuaderno60Modalidad1_2(CuadernoRespuesta60_1_2 poCuaderno){
		Cuaderno60Modalidad1_2 oRespuesta = new Cuaderno60Modalidad1_2();
		
		//	Parámetros comunes a los cuadernos 60
		oRespuesta.setNifCertificado(poCuaderno.getNifCertificado());
		oRespuesta.setNifRepresentante1(poCuaderno.getNifRepresentante1());
		oRespuesta.setNifRepresentante2(poCuaderno.getNifRepresentante2());
		oRespuesta.setNomRepresentante1(poCuaderno.getNomRepresentante1());
		oRespuesta.setNomRepresentante2(poCuaderno.getNomRepresentante2());
		oRespuesta.setOrganismoEmisor(poCuaderno.getOrganismoEmisor());
		//String [] panFechaCaducidadTarjeta=getSplitPanFechaCaducidadTarjeta(poCuaderno.getPan());
		oRespuesta.setPan(poCuaderno.getPan());
		oRespuesta.setFecCaducidadTarjetaCredito(poCuaderno.getFecCaducidad());
		
		//oRespuesta.setFecCaducidad(poCuaderno.getFecCaducidad());
		oRespuesta.setCodEntidad(poCuaderno.getCodEntidad());
		oRespuesta.setIdentificadorMedioPago(poCuaderno.getIdentificadorMedioPago());
		oRespuesta.setIdioma(poCuaderno.getIdentificadorMedioPago());
		oRespuesta.setImporte(poCuaderno.getImporte());
		oRespuesta.setFecha(poCuaderno.getFecha());
		oRespuesta.setHora(poCuaderno.getHora());
		oRespuesta.setCcc(poCuaderno.getCcc());
		oRespuesta.setNrc(poCuaderno.getNrc());

		// Parámetros específicos
		oRespuesta.setCccDomiciliacion(poCuaderno.getCccDomiciliacion());
		oRespuesta.setCodDomiciliacion(poCuaderno.getCodDomiciliacion());
//		oRespuesta.setCpr(poCuaderno.getCpr());
		oRespuesta.setIdent1(poCuaderno.getIdent1());
		oRespuesta.setIdent2(poCuaderno.getIdent2());
		oRespuesta.setReferencia(poCuaderno.getReferencia());
		oRespuesta.setTipo(ConfiguracionComun.obtenerCPRModalidad(poCuaderno.getTipo()));
		return oRespuesta;
	}
	
	
	/**
	 * Método auxiliar que devuelve un array de beans de negocio a partir de un array con implementaciones 
	 * de cuaderno 60_1_2 utilizada por el conector soap
	 * @param  poCuadernos Array de instancias del cuarderno usada por el conector soap.
	 * @return Cuaderno60Modalidad1_2[] Bean de negocio que representa un cuaderno 60_1_2.
	 */
	private Cuaderno60Modalidad1_2[] obtenerCuaderno60Modalidad1_2(CuadernoRespuesta60_1_2[] poCuadernos){
		Cuaderno60Modalidad1_2[] oResultado = null;
		if(poCuadernos != null){
			oResultado = new Cuaderno60Modalidad1_2[poCuadernos.length];
			for(int eContador = 0; eContador < poCuadernos.length; eContador++){
				oResultado[eContador] = obtenerCuaderno60Modalidad1_2(poCuadernos[eContador]);
			}
		}
		return oResultado;
	}

	/**
	 * Método auxiliar que devuelve un bean de negocio a partir de la implementación de un cuaderno 60_3 utilizada por el conector soap
	 * @param poCuaderno Instancia del cuarderno usada por el conector soap.
	 * @return Cuaderno60Modalidad3 Bean de negocio que representa un cuaderno 60_3.
	 */
	private Cuaderno60Modalidad3 obtenerCuaderno60Modalidad3(CuadernoRespuesta60_3 poCuaderno){
		Cuaderno60Modalidad3 oRespuesta = new Cuaderno60Modalidad3();
		
		// Parámetros comunos a los cuadernos 60
		oRespuesta.setCcc(poCuaderno.getCcc());
		oRespuesta.setCodEntidad(poCuaderno.getCodEntidad());
		
		//String [] panFechaCaducidadTarjeta=getSplitPanFechaCaducidadTarjeta(poCuaderno.getPan());
		oRespuesta.setPan(poCuaderno.getPan());
		oRespuesta.setFecCaducidadTarjetaCredito(poCuaderno.getFecCaducidad());
		
		//oRespuesta.setFecCaducidad(poCuaderno.getFecCaducidad());
		oRespuesta.setFecha(poCuaderno.getFecha());
		oRespuesta.setHora(poCuaderno.getHora());
		oRespuesta.setIdentificadorMedioPago(poCuaderno.getIdentificadorMedioPago());
		oRespuesta.setIdioma(poCuaderno.getIdentificadorMedioPago());
		oRespuesta.setImporte(poCuaderno.getImporte());
		oRespuesta.setNifCertificado(poCuaderno.getNifCertificado());
		oRespuesta.setNifRepresentante1(poCuaderno.getNifRepresentante1());
		oRespuesta.setNifRepresentante2(poCuaderno.getNifRepresentante2());
		oRespuesta.setNomRepresentante1(poCuaderno.getNomRepresentante1());
		oRespuesta.setNomRepresentante2(poCuaderno.getNomRepresentante2());
		oRespuesta.setOrganismoEmisor(poCuaderno.getOrganismoEmisor());
		oRespuesta.setPan(poCuaderno.getPan());
		oRespuesta.setTipo(ConfiguracionComun.obtenerCPRModalidad(poCuaderno.getTipo()));
		oRespuesta.setNrc(poCuaderno.getNrc());
		
		// Parámetros específicos.
		oRespuesta.setAcreditacionPagos(poCuaderno.getAcreditacionPagos());
		oRespuesta.setCodTributo(poCuaderno.getCodTributo());
		oRespuesta.setExpediente(poCuaderno.getExpediente());
		oRespuesta.setFechaDevengo(poCuaderno.getFechaDevengo());
		oRespuesta.setInformacionEspecifica(poCuaderno.getInformacionEspecifica());
		oRespuesta.setJustificante(poCuaderno.getJustificante());
		oRespuesta.setNifContribuyente(poCuaderno.getNifContribuyente());
		return oRespuesta;
	}
	
	
	/**
	 * Método auxiliar que devuelve un array de beans de negocio a partir de un array con implementaciones 
	 * de cuaderno 60_3 utilizada por el conector soap
	 * @param  poCuadernos Array de instancias del cuarderno usada por el conector soap.
	 * @return Cuaderno60Modalidad3[] Bean de negocio que representa un cuaderno 60_3.
	 */
	private Cuaderno60Modalidad3[] obtenerCuaderno60Modalidad3(CuadernoRespuesta60_3[] poCuadernos){
		Cuaderno60Modalidad3[] oResultado = null;
		if(poCuadernos != null){
			oResultado = new Cuaderno60Modalidad3[poCuadernos.length];
			for(int eContador = 0; eContador < poCuadernos.length; eContador++){
				oResultado[eContador] = obtenerCuaderno60Modalidad3(poCuadernos[eContador]);
			}
		}
		return oResultado;
	}

	
	/**
	 * Método auxiliar que valida la respuesta obtenida desde el servicio web de Red.es
	 * @param poRespuesta Cuaderno de respuesta.
	 * @throws PagoElectronicoExcepcion En caso de haberse producido algún error.
	 */
	private void validarRespuesta(CuadernoRespuesta60_1_2[] poRespuesta) throws PagoElectronicoExcepcion{
		if(poRespuesta != null){
			if(poRespuesta.length > 0){
				validarRespuesta(poRespuesta[0]);
			}
		}else{
			StringBuffer sbError = new StringBuffer("Error en la petición de pago al servicio externo: ");
			sbError.append("error durante la firma y envío de la petición.");
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO);
		}
	}

	
	/**
	 * Método auxiliar que valida la respuesta obtenida desde el servicio web de Red.es
	 * @param poRespuesta Cuaderno de respuesta.
	 * @throws PagoElectronicoExcepcion En caso de haberse producido algún error.
	 */
	private void validarRespuesta(CuadernoRespuesta60_1_2 poRespuesta) throws PagoElectronicoExcepcion{
		// Si la respuesta es null ha habido un problema durante el
		// proceso de firma y envío.
		if(poRespuesta == null){
			StringBuffer sbError = new StringBuffer("Error en la petición de pago al servicio externo: ");
			sbError.append("error durante la firma y envío de la petición.");
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO);
		}
		
		// Si el código de error es 00 todo ha ido bien
		if(	(REDES_OK_CODE.equals(poRespuesta.getCodigoRetorno()) || ("".equals(poRespuesta.getCodigoRetorno())))
		){
			if(logger.isDebugEnabled()){
				logger.debug("Éxito en la pago mediante el sistema externo.");
			}
			return;
		}
		
		// Se ha producido algún error
		StringBuffer sbError = new StringBuffer("Error realizando el pago. Mensaje de error: ");
		sbError.append(Configuracion.obtenerMensajeErrorRedes(poRespuesta.getCodigoRetorno()));
		logger.error(sbError.toString());
		throw new PagoElectronicoExcepcion(Configuracion.obtenerCodigoExcepcionSistemaPago(poRespuesta.getCodigoRetorno()));
		
	}
	
	
	/**
	 * Método auxiliar que valida la respuesta obtenida desde el servicio web de Red.es
	 * @param poRespuesta Cuaderno de respuesta.
	 * @throws PagoElectronicoExcepcion En caso de haberse producido algún error.
	 */
	private void validarRespuesta(CuadernoRespuesta60_3[] poRespuesta) throws PagoElectronicoExcepcion{
		if(poRespuesta != null){
			if(poRespuesta.length > 0){
				validarRespuesta(poRespuesta[0]);
			}
		}else{
			StringBuffer sbError = new StringBuffer("Error en la petición de pago al servicio externo: ");
			sbError.append("error durante la firma y envío de la petición.");
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO);
		}
	}
	

	/**
	 * Método auxiliar que valida la respuesta obtenida desde el servicio web de Red.es
	 * @param poRespuesta Cuaderno de respuesta.
	 * @throws PagoElectronicoExcepcion En caso de haberse producido algún error.
	 */
	private void validarRespuesta(CuadernoRespuesta60_3 oRespuesta) throws PagoElectronicoExcepcion{
		// Si la respuesta es null ha habido un problema durante el
		// proceso de firma y envío.
		if(oRespuesta == null){
			StringBuffer sbError = new StringBuffer("Error en la petición de pago al servicio externo: ");
			sbError.append("error durante la firma y envío de la petición.");
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO);
		}
		
		// Si el código de error es 00 todo ha ido bien
		if(	(REDES_OK_CODE.equals(oRespuesta.getCodigoRetorno()) || ("".equals(oRespuesta.getCodigoRetorno())))
		){
			if(logger.isDebugEnabled()){
				logger.debug("Éxito en la pago mediante el sistema externo.");
			}
			return;
		}
		
		// Se ha producido algún error
		StringBuffer sbError = new StringBuffer("Error realizando el pago. Mensaje de error: ");
		sbError.append(Configuracion.obtenerMensajeErrorRedes(oRespuesta.getCodigoRetorno()));
		logger.error(sbError.toString());
		throw new PagoElectronicoExcepcion(Configuracion.obtenerCodigoExcepcionSistemaPago(oRespuesta.getCodigoRetorno()));
		
	}
	
	/**
	 * Método auxiliar que devuelve la implementación de un objeto de búsqueda del conector soap a partir de
	 * la implementación del objeto de búsqueda de negocio.
	 * @param oCriterio Objeto con los criterios de búsqueda
	 * @return BusquedaSop Objeto de busqueda para el conector soap.
	 */
	private BusquedaSop obtenerCriterioBusquedaCuaderno60_1_2(CriterioBusquedaPago oCriterio){
		BusquedaSop oBusqueda = new BusquedaSop();
		oBusqueda.setNifCertificado(oCriterio.getNif());
		oBusqueda.setCpr(ConfiguracionComun.obtenerCPRModalidad(oCriterio.getTipo()));
		oBusqueda.setEstado(oCriterio.getEstado());
		oBusqueda.setFechaDesde(Util.getFechaCuaderno60(oCriterio.getFechaDesde()));
		oBusqueda.setFechaHasta(Util.getFechaCuaderno60(oCriterio.getFechaHasta()));
		oBusqueda.setNRC(oCriterio.getNRC());
		oBusqueda.setReferencia(oCriterio.getReferencia());
		oBusqueda.setCodOrganismo(Configuracion.obtenerPropiedad(Configuracion.KEY_CONSULTA_COD_ORGANISMO));
		oBusqueda.setOrganismoEmisor(oCriterio.getEntidad());
		return oBusqueda;
	}
	
	/**
	 * Método auxiliar que devuelve la implementación de un objeto de búsqueda del conector soap a partir de
	 * la implementación del objeto de búsqueda de negocio.
	 * @param oCriterio Objeto con los criterios de búsqueda
	 * @return BusquedaSop Objeto de busqueda para el conector soap.
	 */
	private BusquedaSop obtenerCriterioBusquedaCuaderno60_3(CriterioBusquedaPago oCriterio){
		BusquedaSop oBusqueda = new BusquedaSop();
		oBusqueda.setNifContribuyente(oCriterio.getNif());
		oBusqueda.setCpr(ConfiguracionComun.obtenerCPRModalidad(oCriterio.getTipo()));
		oBusqueda.setEstado(oCriterio.getEstado());
		oBusqueda.setFechaDesde(Util.getFechaCuaderno60(oCriterio.getFechaDesde()));
		oBusqueda.setFechaHasta(Util.getFechaCuaderno60(oCriterio.getFechaHasta()));
		oBusqueda.setJustificante(oCriterio.getReferencia());	
		oBusqueda.setCodOrganismo(Configuracion.obtenerPropiedad(Configuracion.KEY_CONSULTA_COD_ORGANISMO));
		oBusqueda.setNRC(oCriterio.getNRC());
		return oBusqueda;
	}


	public Cuaderno57 pagoCuaderno57(Cuaderno57 poCuaderno) throws PagoElectronicoExcepcion {
		throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_C57_NOT_SUPPORTED);
	}
	
//	private String getJuntarPanFechaCaducidadTarjeta(String pan,String fechaCaducidadTarjeta){
//		String panRedes=pan;
//		String cadTarjeta=fechaCaducidadTarjeta;
//		if(!StringUtils.isEmpty(pan)) 
//			panRedes=Util.rellenarConCerosIzquierda(pan, 16);	
//		if(!StringUtils.isEmpty(fechaCaducidadTarjeta)) 
//			cadTarjeta=Util.rellenarConCerosIzquierda(fechaCaducidadTarjeta, 4);
//		
//		if(!StringUtils.isEmpty(pan) && !StringUtils.isEmpty(fechaCaducidadTarjeta))
//			panRedes+=cadTarjeta;
//		return panRedes;
//	}
//	
//	private String[] getDividirPanFechaCaducidadTarjeta(String concatPanFechaCaducidadTarjeta){
//		String[] panFechaCaducidadTarjeta =new String[2];
//		if(concatPanFechaCaducidadTarjeta==null || concatPanFechaCaducidadTarjeta.length()<20){
//			panFechaCaducidadTarjeta[0]=concatPanFechaCaducidadTarjeta;
//		}else{
//			String pan=concatPanFechaCaducidadTarjeta.substring(0,16);
//			String fechaCaducidadTarjeta=concatPanFechaCaducidadTarjeta.substring(16);
//			panFechaCaducidadTarjeta[0]=pan;
//			panFechaCaducidadTarjeta[1]=fechaCaducidadTarjeta;
//		}
//		return panFechaCaducidadTarjeta;
//	}
}
