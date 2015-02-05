package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.isicres.session.book.BookSession;

import es.ieci.tecdoc.isicres.api.business.exception.DistribucionException;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistribution;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>DistribucionVO</code> en uno de tipo <code>WSDistribution</code>.
 * 
 * @see DistribucionVO
 * @see WSDistribution
 * 
 * @author IECISA
 * 
 */
public class DistribucionVOToWSDistributionMapper implements Mapper {

	/**
	 * Constructor con parámetros de la clase. Es necesario pasar el usuario
	 * autenticado para poder utilizar su <i>sessionID</id> para recuperar la
	 * información del libro asociado a una distribución.
	 * 
	 * @param anUsuario
	 *            usuario autenticado
	 */
	public DistribucionVOToWSDistributionMapper(UsuarioVO anUsuario) {
		this.usuario = anUsuario;
	}

	public Object map(Object obj) {
		Assert.isInstanceOf(DistribucionVO.class, obj);

		DistribucionVO distribution = (DistribucionVO) obj;

		WSDistribution result = new WSDistribution();

		result.setId(Integer.valueOf(distribution.getId()).intValue());
		result.setArchiveId(Integer.valueOf(
				distribution.getRegistro().getIdLibro()).intValue());

		String sessionId = getUsuario().getConfiguracionUsuario()
				.getSessionID();
		ScrRegstate book = null;
		try {
			book = BookSession.getBook(sessionId, Integer.valueOf(distribution
					.getRegistro().getIdLibro()));
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"No se ha podido recuperar la informacion del libro con identificador [")
					.append(distribution.getRegistro().getIdLibro())
					.append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}

			throw new DistribucionException(sb.toString(), e);
		}

		result.setBookType(book.getIdocarchhdr().getType());
		result.setBookName(book.getIdocarchhdr().getName());

		result.setFolderId(Integer.valueOf(
				distribution.getRegistro().getIdRegistro()).intValue());

		if (null != distribution.getRegistro().getFechaRegistro()) {
			result.setRegisterDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(distribution.getRegistro()
							.getFechaRegistro()));
		}
		if (null != distribution.getFechaDistribucion()) {
			result
					.setDistributionDate(XMLGregorianCalendarHelper
							.toXMLGregorianCalendar(distribution
									.getFechaDistribucion()));
		}

		result.setSenderId(Integer.valueOf(
				distribution.getOrigenDistribucion().getId()).intValue());
		result.setSenderName(distribution.getOrigenDistribucion().getName());
		result.setSenderType(Integer.valueOf(
				distribution.getOrigenDistribucion().getTipo()).intValue());

		result.setDestinationId(Integer.valueOf(
				distribution.getDestinoDistribucion().getId()).intValue());
		result.setDestinationName(distribution.getDestinoDistribucion()
				.getName());
		result.setDestinationType(Integer.valueOf(distribution
				.getDestinoDistribucion().getTipo()));

		result.setState(Integer.valueOf(
				distribution.getEstado().getIdEstado().getValue()).intValue());
		result.setStateDescription(distribution.getEstado().getDescripcion());
		result.setMessage(distribution.getMensajeDistribucion());

		if (null != distribution.getEstado().getFechaEstado()) {
			result.setStateDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(distribution.getEstado()
							.getFechaEstado()));
		}

		result.setUser(distribution.getEstado().getUsuarioEstado());
		result
				.setRegisterNumber(distribution.getRegistro()
						.getNumeroRegistro());
		if (null != distribution.getRegistro().getFechaRegistro()) {
			result.setRegisterDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(distribution.getRegistro()
							.getFechaRegistro()));
		}
		result.setRegisterDestinationName(distribution.getRegistro()
				.getUnidadAdministrativaDestino().getName());
		result.setRegisterMatterTypeName(distribution.getRegistro()
				.getTipoAsunto().getDescripcion());
		result.setRegisterMatter(distribution.getRegistro().getResumen());

		return result;
	}

	protected UsuarioVO getUsuario() {
		return usuario;
	}

	// Members
	protected static final Logger logger = Logger
			.getLogger(DistribucionVOToWSDistributionMapper.class);

	protected UsuarioVO usuario;
}
