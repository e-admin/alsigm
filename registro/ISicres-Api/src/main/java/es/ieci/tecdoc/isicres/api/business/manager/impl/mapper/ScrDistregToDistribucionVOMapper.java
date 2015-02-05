package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeComparator;

import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrDistregstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.utils.HibernateUtil;

import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.EstadoDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.ImplicadoDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;

/**
 * Clase que se encarga de mapear las propiedades de objetos de tipo
 * <code>ScrDistreg</code> en objetos de tipo <code>DistribucionVO</code>.
 * 
 * @author IECISA
 * 
 */
public class ScrDistregToDistribucionVOMapper {

	public ScrDistregToDistribucionVOMapper(
			UnidadAdministrativaManager anUnidadAdministrativaManager,
			TipoAsuntoManager aTipoAsuntoManager, UsuarioVO usuario) {
		setTipoAsuntoManager(aTipoAsuntoManager);
		setUnidadAdministrativaManager(anUnidadAdministrativaManager);
		setUsuario(usuario);
	}

	public DistribucionVO map(ScrDistreg reg) {
		DistribucionVO distribucion = new DistribucionVO();

		distribucion.setId(String.valueOf(reg.getId()));
		distribucion.setMensajeDistribucion(reg.getMessage());
		distribucion.setFechaDistribucion(reg.getDistDate());

		ImplicadoDistribucionVO origenDistribucion = new ImplicadoDistribucionVO();
		try {
			origenDistribucion.setName(DistributionSession
					.getOrigDestDescription(getUsuario()
							.getConfiguracionUsuario().getSessionID(), reg,
							true, getUsuario().getConfiguracionUsuario()
									.getIdEntidad()));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer(
						"No se ha podido recuperar el nombre del origen de la distribucion con identificador [")
						.append(reg.getIdFdr()).append("]");
				logger.debug(sb.toString(), e);
			}
		}
		origenDistribucion.setId(String.valueOf(reg.getIdOrig()));
		origenDistribucion.setTipo(String.valueOf(reg.getTypeOrig()));
		distribucion.setOrigenDistribucion(origenDistribucion);

		ImplicadoDistribucionVO destinoDistribucion = new ImplicadoDistribucionVO();
		destinoDistribucion.setId(String.valueOf(reg.getIdDest()));
		destinoDistribucion.setTipo(String.valueOf(reg.getTypeDest()));
		try {
			destinoDistribucion.setName(DistributionSession
					.getOrigDestDescription(getUsuario()
							.getConfiguracionUsuario().getSessionID(), reg,
							false, getUsuario().getConfiguracionUsuario()
									.getIdEntidad()));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer(
						"No se ha podido recuperar el nombre del destino de la distribucion con identificador [")
						.append(reg.getIdFdr()).append("]");
				logger.debug(sb.toString(), e);
			}
		}
		distribucion.setDestinoDistribucion(destinoDistribucion);

		ScrDistregstate lastState = null;
		try {
			List distregstate = ISicresQueries.getScrDistregstate(HibernateUtil
					.currentSession(usuario.getConfiguracionUsuario()
							.getIdEntidad()), Integer.valueOf(distribucion
					.getId()));
			lastState = (ScrDistregstate) Collections.max(distregstate,
					new Comparator() {

						public int compare(Object o1, Object o2) {
							ScrDistregstate scrDistregstate1 = (ScrDistregstate) o1;
							ScrDistregstate scrDistregstate2 = (ScrDistregstate) o2;

							return DateTimeComparator.getInstance().compare(
									scrDistregstate1.getStateDate(),
									scrDistregstate2.getStateDate());
						}
					});
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"No se han podido recuperar los estados para la distribucion con identificador [")
					.append(distribucion.getId()).append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
		}
		if (null == lastState) {
			StringBuffer sb = new StringBuffer(
					"No se ha podido recuperar el estado de la distribucion [")
					.append(reg.getId()).append("]");
			throw new IllegalStateException(sb.toString());
		}

		EstadoDistribucionVO estado = new EstadoDistribucionVO();
		estado
				.setIdEstado(EstadoDistribucionEnum.getEnum(lastState
						.getState()));
		estado.setFechaEstado(lastState.getStateDate());
		estado.setUsuarioEstado(lastState.getUsername());
		distribucion.setEstado(estado);

		BaseRegistroVO registro = populateRegistro(reg);
		distribucion.setRegistro(registro);

		return distribucion;
	}

	/**
	 * 
	 * @param reg
	 * @return
	 */
	private BaseRegistroVO populateRegistro(ScrDistreg reg) {

		AxSf bookFolder = null;
		try {
			BookSession.openBook(usuario.getConfiguracionUsuario()
					.getSessionID(), new Integer(reg.getIdArch()), usuario
					.getConfiguracionUsuario().getIdEntidad());

			bookFolder = FolderSession.getBookFolder(getUsuario()
					.getConfiguracionUsuario().getSessionID(), new Integer(reg
					.getIdArch()), reg.getIdFdr(), getUsuario()
					.getConfiguracionUsuario().getLocale(), getUsuario()
					.getConfiguracionUsuario().getIdEntidad());
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Error al obtener información del registro [").append(
					reg.getIdFdr()).append("] para el libro [").append(
					reg.getIdArch()).append("].");

			throw new RegistroException(sb.toString(), e);
		}

		BaseRegistroVO registro = new AxSfToBaseRegistroVOMapper(getUsuario(),
				getTipoAsuntoManager(), getUnidadAdministrativaManager(),
				String.valueOf(reg.getIdArch()), String.valueOf(reg.getIdFdr()))
				.map(bookFolder);

		return registro;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public UnidadAdministrativaManager getUnidadAdministrativaManager() {
		return unidadAdministrativaManager;
	}

	public void setUnidadAdministrativaManager(
			UnidadAdministrativaManager unidadAdministrativaManager) {
		this.unidadAdministrativaManager = unidadAdministrativaManager;
	}

	public TipoAsuntoManager getTipoAsuntoManager() {
		return tipoAsuntoManager;
	}

	public void setTipoAsuntoManager(TipoAsuntoManager tipoAsuntoManager) {
		this.tipoAsuntoManager = tipoAsuntoManager;
	}

	// Members
	protected static final Logger logger = Logger
			.getLogger(ScrDistregToDistribucionVOMapper.class);

	protected UsuarioVO usuario;

	protected UnidadAdministrativaManager unidadAdministrativaManager;

	protected TipoAsuntoManager tipoAsuntoManager;
}
