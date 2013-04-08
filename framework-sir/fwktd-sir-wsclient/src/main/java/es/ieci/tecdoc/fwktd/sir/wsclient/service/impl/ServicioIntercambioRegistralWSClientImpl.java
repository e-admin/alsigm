package es.ieci.tecdoc.fwktd.sir.wsclient.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.EstadoAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.HashMapString;
import es.ieci.tecdoc.fwktd.sir.ws.service.IntercambioRegistralWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.HashMapString.Item;
import es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * Implementación del servicio de intercambio registral en formato SICRES 3.0
 * que utiliza el servicio web.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioIntercambioRegistralWSClientImpl implements
		ServicioIntercambioRegistral {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioIntercambioRegistralWSClientImpl.class);

	/**
	 * Clase que mapea objetos DTO <-> VO.
	 */
	private Mapper mapper;
	
	protected IntercambioRegistralWSClientFactory intercambioRegistralWSClientFactory;
	
	
	

	public IntercambioRegistralWSClientFactory getIntercambioRegistralWSClientFactory() {
		return intercambioRegistralWSClientFactory;
	}

	public void setIntercambioRegistralWSClientFactory(
			IntercambioRegistralWSClientFactory intercambioRegistralWSClientFactory) {
		this.intercambioRegistralWSClientFactory = intercambioRegistralWSClientFactory;
	}
	

	/**
	 * Constructor.
	 */
	public ServicioIntercambioRegistralWSClientImpl() {
		super();
	}

	/**
	 * Metodo para obtener cliente de servicio web de intercambio registral
	 * @return
	 */
	public IntercambioRegistralWS getIntercambioRegistralWS() {
		return getIntercambioRegistralWSClientFactory().getIntercambioRegistralWS(); 
	}
	
	
	

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#countAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	public int countAsientosRegistrales(CriteriosVO criterios) {

		logger.info("Llamada a countAsientosRegistrales: criterios=[{}]",
				criterios);

		// Obtener el número de asientos registrales
		return getIntercambioRegistralWS().countAsientosRegistrales(
				getMapper().getCriteriosDTO(criterios));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#findAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	public List<AsientoRegistralVO> findAsientosRegistrales(
			CriteriosVO criterios) {

		logger.info("Llamada a findAsientosRegistrales: criterios=[{}]",
				criterios);

		// Realizar la búsqueda de asientos registrales
		return getMapper().getAsientoRegistralVOList(getIntercambioRegistralWS()
				.findAsientosRegistrales(getMapper().getCriteriosDTO(criterios)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getEstadoAsientoRegistral(java.lang.String)
	 */
	public EstadoAsientoRegistraVO getEstadoAsientoRegistral(String id) {
		
		EstadoAsientoRegistraVO result = null;
		logger.info("Llamada a getEstadoAsientoRegistral: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");
		EstadoAsientoRegistralDTO estadoDTO = getIntercambioRegistralWS().getEstadoAsientoRegistral(id);
		
		result = getMapper().getEstadoAsientoRegistraVO(estadoDTO);
		
		
		return result ;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getAsientoRegistral(java.lang.String)
	 */
	public AsientoRegistralVO getAsientoRegistral(String id) {

		logger.info("Llamada a getAsientoRegistral: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener la información del asiento registral
		return getMapper().getAsientoRegistralVO(getIntercambioRegistralWS()
				.getAsientoRegistral(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#saveAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
	 */
	public AsientoRegistralVO saveAsientoRegistral(
			AsientoRegistralFormVO asientoForm) {

		logger.info("Llamada a saveAsientoRegistral: [{}]", asientoForm);

		Assert.notNull(asientoForm, "'asientoForm' must not be null");

		// Salvar el asiento registral
		return getMapper().getAsientoRegistralVO(getIntercambioRegistralWS()
				.saveAsientoRegistral(
						getMapper().getAsientoRegistralFormDTO(asientoForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#updateAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO)
	 */
	public void updateAsientoRegistral(InfoBAsientoRegistralVO infoBAsiento) {

		logger.info("Llamada a updateAsientoRegistral: [{}]", infoBAsiento);

		Assert.notNull(infoBAsiento, "'infoBAsiento' must not be null");
		Assert.hasText(infoBAsiento.getId(),
				"'infoBAsiento.id' must not be empty");

		// Salvar el asiento registral
		getIntercambioRegistralWS().updateAsientoRegistral(
				getMapper().getInfoBAsientoRegistralDTO(infoBAsiento));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#deleteAsientoRegistral(java.lang.String)
	 */
	public void deleteAsientoRegistral(String id) {

		logger.info("Llamada a deleteAsientoRegistral: [{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Eliminar el asiento registral
		getIntercambioRegistralWS().deleteAsientoRegistral(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#addAnexo(java.lang.String,
	 *      es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO)
	 */
	public AnexoVO addAnexo(String idAsientoRegistral, AnexoFormVO anexoForm) {

		logger.info("Llamada a addAnexo: [{}]", anexoForm);

		Assert.notNull(anexoForm, "'anexo' must not be null");
		Assert.hasText(idAsientoRegistral,
				"'idAsientoRegistral' must not be empty");

		// Salvar el anexo
		return getMapper().getAnexoVO(getIntercambioRegistralWS().addAnexo(
				idAsientoRegistral, getMapper().getAnexoFormDTO(anexoForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#updateAnexo(es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO)
	 */
	public AnexoVO updateAnexo(AnexoVO anexo) {

		logger.info("Llamada a updateAnexo: [{}]", anexo);

		Assert.notNull(anexo, "'anexo' must not be null");
		Assert.hasText(anexo.getId(), "'anexo.id' must not be empty");
		Assert.hasText(anexo.getIdAsientoRegistral(),
				"'anexo.idAsientoRegistral' must not be empty");

		// Modificar el anexo
		return getMapper().getAnexoVO(getIntercambioRegistralWS().updateAnexo(
				getMapper().getAnexoDTO(anexo)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#removeAnexo(java.lang.String)
	 */
	public void removeAnexo(String idAnexo) {

		logger.info("Llamada a removeAnexo: [{}]", idAnexo);

		Assert.hasText(idAnexo, "'idAnexo' must not be empty");

		// Eliminar el anexo
		getIntercambioRegistralWS().removeAnexo(idAnexo);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#addInteresado(java.lang.String,
	 *      es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO)
	 */
	public InteresadoVO addInteresado(String idAsientoRegistral,
			InteresadoFormVO interesadoForm) {

		logger.info("Llamada a addInteresado: [{}]", interesadoForm);

		Assert.hasText(idAsientoRegistral,
				"'idAsientoRegistral' must not be empty");
		Assert.notNull(interesadoForm, "'interesadoForm' must not be null");

		// Salvar el interesado
		return getMapper().getInteresadoVO(getIntercambioRegistralWS()
				.addInteresado(idAsientoRegistral,
						getMapper().getInteresadoFormDTO(interesadoForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#updateInteresado(es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO)
	 */
	public InteresadoVO updateInteresado(InteresadoVO interesado) {

		logger.info("Llamada a updateInteresado: [{}]", interesado);

		Assert.notNull(interesado, "'interesado' must not be null");
		Assert.hasText(interesado.getId(), "'interesado.id' must not be empty");
		Assert.hasText(interesado.getIdAsientoRegistral(),
				"'interesado.idAsientoRegistral' must not be empty");

		// Modificar el interesado
		return getMapper().getInteresadoVO(getIntercambioRegistralWS()
				.updateInteresado(getMapper().getInteresadoDTO(interesado)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#removeInteresado(java.lang.String)
	 */
	public void removeInteresado(String idInteresado) {

		logger.info("Llamada a removeInteresado: [{}]", idInteresado);

		Assert.hasText(idInteresado, "'idInteresado' must not be empty");

		// Eliminar el interesado
		getIntercambioRegistralWS().removeInteresado(idInteresado);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getContenidoAnexo(java.lang.String)
	 */
	public byte[] getContenidoAnexo(String id) {

		logger.info("Llamada a getContenidoAnexo: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener el contenido del anexo
		return getIntercambioRegistralWS().getContenidoAnexo(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#setContenidoAnexo(java.lang.String,
	 *      byte[])
	 */
	public void setContenidoAnexo(String id, byte[] contenido) {

		logger.info("Llamada a updateContenidoAnexo: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Actualziar el contenido del anexo
		getIntercambioRegistralWS().setContenidoAnexo(id, contenido);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getHistoricoAsientoRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getHistoricoAsientoRegistral(String id) {

		logger.info("Llamada a getHistoricoAsientoRegistral: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener las trazas de intercambio
		return getMapper().getTrazabilidadVOList(getIntercambioRegistralWS()
				.getHistoricoAsientoRegistral(id));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getHistoricoMensajeIntercambioRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getHistoricoMensajeIntercambioRegistral(
			String id) {

		logger.info(
				"Llamada a getHistoricoMensajeIntercambioRegistral: id=[{}]",
				id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener las trazas de intercambio
		return getMapper().getTrazabilidadVOList(getIntercambioRegistralWS()
				.getHistoricoMensajeIntercambioRegistral(id));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getHistoricoCompletoAsientoRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getHistoricoCompletoAsientoRegistral(String id) {

		logger.info("Llamada a getHistoricoCompletoAsientoRegistral: id=[{}]",
				id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener las trazas de intercambio
		return getMapper().getTrazabilidadVOList(getIntercambioRegistralWS()
				.getHistoricoCompletoAsientoRegistral(id));
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#enviarAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
	 */
	public AsientoRegistralVO enviarAsientoRegistral(
			AsientoRegistralFormVO asientoForm) {

		logger.info("Llamada a enviarAsientoRegistral: [{}]", asientoForm);

		Assert.notNull(asientoForm, "'asientoForm' must not be null");

		// Enviar el asiento registral
		return getMapper().getAsientoRegistralVO(getIntercambioRegistralWS()
				.enviarAsientoRegistral(
						getMapper().getAsientoRegistralFormDTO(asientoForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#enviarAsientoRegistral(java.lang.String)
	 */
	public void enviarAsientoRegistral(String id) {

		logger.info("Llamada a enviarAsientoRegistral: [{}]", id);

		Assert.hasText(id, "'id' must not be null");

		// Enviar el asiento registral
		getIntercambioRegistralWS().enviarAsientoRegistralById(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#validarAsientoRegistral(java.lang.String,
	 *      java.lang.String, java.util.Date)
	 */
	public void validarAsientoRegistral(String id, String numeroRegistro,
			Date fechaRegistro) {

		logger.info(
				"Llamada a validarAsientoRegistral: id=[{}], numeroRegistro=[{}], fechaRegistro=[{}]",
				new Object[] { id, numeroRegistro, fechaRegistro });

		Assert.hasText(id, "'id' must not be empty");
		Assert.hasText(numeroRegistro, "'numeroRegistro' must not be empty");
		Assert.notNull(fechaRegistro, "'fechaRegistro' must not be null");

		// Validar el asiento registral
		getIntercambioRegistralWS().validarAsientoRegistral(id, numeroRegistro,
				DateUtils.toXMLGregorianCalendar(fechaRegistro));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#reenviarAsientoRegistral(java.lang.String)
	 */
	public void reenviarAsientoRegistral(String id) {

		logger.info("Llamada a reenviarAsientoRegistral: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Reenviar el asiento registral
		getIntercambioRegistralWS().reenviarAsientoRegistralById(id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#reenviarAsientoRegistral(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO)
	 */
	public void reenviarAsientoRegistral(String id, InfoReenvioVO infoReenvio) {

		logger.info(
				"Llamada a reenviarAsientoRegistral: id=[{}], infoReenvio=[{}]",
				id, infoReenvio);

		Assert.hasText(id, "'id' must not be empty");
		Assert.notNull(infoReenvio, "'infoReenvio' must not be null");

		// Reenviar el asiento registral
		getIntercambioRegistralWS().reenviarAsientoRegistral(id,
				getMapper().getInfoReenvioDTO(infoReenvio));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#rechazarAsientoRegistral(java.lang.String,
	 *      es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO)
	 */
	public void rechazarAsientoRegistral(String id, InfoRechazoVO infoRechazo) {

		logger.info(
				"Llamada a rechazarAsientoRegistral: id=[{}], infoRechazo=[{}]",
				id, infoRechazo);

		Assert.hasText(id, "'id' must not be empty");
		Assert.notNull(infoRechazo, "'infoRechazo' must not be null");

		// Rechazar el asiento registral
		getIntercambioRegistralWS().rechazarAsientoRegistral(id,
				getMapper().getInfoRechazoDTO(infoRechazo));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#anularAsientoRegistral(java.lang.String)
	 */
	public void anularAsientoRegistral(String id) {

		logger.info("Llamada a anularAsientoRegistral: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Anular el asiento registral
		getIntercambioRegistralWS().anularAsientoRegistral(id);
	}

//	public List<ValidacionAnexoVO> validarAnexos(String id) {
//
//		logger.info("Llamada a validarAnexos: id=[{}]", id);
//
//		Assert.hasText(id, "'id' must not be empty");
//
//		// Validar los anexos del asiento registral
//		return getMapper().getListaValidacionAnexoVO(getIntercambioRegistralWS().validarAnexos(id));
//	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#recibirFicheroIntercambio(java.lang.String,
	 *      java.util.List)
	 */
	public AsientoRegistralVO recibirFicheroIntercambio(
			String xmlFicheroIntercambio, List<byte[]> documentos) {

		logger.info("Llamada a recibirFicheroIntercambio");

		Assert.hasText(xmlFicheroIntercambio,
				"'xmlFicheroIntercambio' must not be empty");

		// Cargar la información del asiento registral
		return getMapper().getAsientoRegistralVO(getIntercambioRegistralWS()
				.recibirFicheroIntercambio(xmlFicheroIntercambio, documentos));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#recibirMensaje(java.lang.String)
	 */
	public void recibirMensaje(String xmlMensaje) {

		logger.info("Llamada a recibirMensaje: [{}]", xmlMensaje);

		Assert.hasText(xmlMensaje, "'xmlMensaje' must not be empty");

		// Recibir el mensaje
		getIntercambioRegistralWS().recibirMensaje(xmlMensaje);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#procesarFicherosRecibidos()
	 */
	public void procesarFicherosRecibidos() {

		logger.info("Llamada a procesarFicherosRecibidos");

		// Procesar los ficheros recibidos
		getIntercambioRegistralWS().procesarFicherosRecibidos();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#comprobarTimeOutEnvios()
	 */
	public void comprobarTimeOutEnvios() {

		logger.info("Llamada a comprobarTimeOutEnvios");

		// Comprobar el time-out de los ficheros de intercambio enviados
		getIntercambioRegistralWS().comprobarTimeOutEnvios();
	}

}
