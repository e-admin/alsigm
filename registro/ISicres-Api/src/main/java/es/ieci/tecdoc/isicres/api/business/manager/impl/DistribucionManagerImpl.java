package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.HibernateException;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.isicres.DistributionResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.authentication.LDAPAuthenticationPolicy;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.usecase.distribution.DistributionUseCase;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.api.business.exception.DistributionException;
import es.ieci.tecdoc.isicres.api.business.exception.LoginException;
import es.ieci.tecdoc.isicres.api.business.exception.UnknownDistributionException;
import es.ieci.tecdoc.isicres.api.business.manager.DepartamentoManager;
import es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager;
import es.ieci.tecdoc.isicres.api.business.manager.GrupoManager;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.OficinaManager;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.manager.UsuarioManager;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.ScrDistregToDistribucionVOMapper;
import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ImplicadoDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.NullLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;

/**
 * Implementación del manager de distribución de registros.
 *
 * @author IECISA
 *
 */
public class DistribucionManagerImpl extends DistribucionManager {

	protected static final Logger logger = Logger
			.getLogger(DistribucionManagerImpl.class);

	protected DistributionUseCase distributionUseCase;

	protected UnidadAdministrativaManager unidadAdministrativaManager;

	protected TipoAsuntoManager tipoAsuntoManager;

	protected LibroManager libroManager;

	protected RegistroManager registroManager;

	protected static final String REGISTRO_NUMERO_REGISTRO_PROPERTY = "registro.numeroRegistro";

	protected LoginLegacyManagerImpl loginManager;

	protected GrupoManager grupoManager;

	protected OficinaManager oficinaManager;

	protected UsuarioManager usuarioManager;

	protected DepartamentoManager departamentoManager;

	/**
	 * {@inheritDoc}
	 */
	public final void acceptDistribution(UsuarioVO usuario,
			DistribucionVO distribucion, BaseLibroVO libro) {

		Assert.notNull(distribucion.getRegistro().getNumeroRegistro(),
				"No se puede aceptar una distribucion de un registro nulo");

		checkAcceptDistributionPermissions(usuario);

		try {
			// Recuperamos la distribucion del usuario con el numero de registro
			// especificado
			DistribucionVO dist = getDistributionFromInputBox(
					usuario,
					distribucion.getRegistro(),
					new EstadoDistribucionEnum[] { EstadoDistribucionEnum.PENDIENTE });

			Integer launchDistOutRegister = new Integer(BookSession
					.invesicresConf(
							usuario.getConfiguracionUsuario().getIdEntidad())
					.getDistSRegister());

			DistributionResults distributionResults = getDistributionResults(
					usuario, new CriterioBusquedaDistribucionVO(
							BaseCriterioBusquedaVO.MAX_RESULTS, new Long(
									BaseCriterioBusquedaVO.DEFAULT_OFFSET),
							dist.getEstado().getIdEstado()),
					Keys.DISTRIBUCION_IN_DIST);

			// Recopilamos los permisos de registro sobre los libros del usuario
			List createPermBooks = getCreatePermBooks(usuario);

			// En caso de no indicar ningún libro debe cumplirse que el usuario
			// autenticado solo tenga permiso de creación en un libro de entrada
			if (libro instanceof NullLibroVO) {
				StringBuffer sb = new StringBuffer(
						"No es posible aceptar una distribucion de un registro de salida sin indicar")
						.append(" el libro de entrada en el que crear la copia del registro distribuido ")
						.append("cuando el usuario tiene permisos de creacion en mas de un libro de entrada. ")
						.append("Indique el libro de entrada sobre el que crear el registro para solucionarlo");
				Assert.isTrue(
						(!CollectionUtils.isEmpty(createPermBooks) && createPermBooks
								.size() == 1), sb.toString());
			}

			Object resultOfAcceptDistribution = null;
			try {
				preAcceptDistribucion(usuario, distribucion);

				resultOfAcceptDistribution = DistributionSession
						.acceptDistribution(usuario.getConfiguracionUsuario()
								.getSessionID(), Arrays
								.asList(new Integer[] { Integer.valueOf(dist
										.getId()) }),
								EstadoDistribucionEnum.ACEPTADO.getValue(),
								BaseCriterioBusquedaVO.DEFAULT_OFFSET,
								BaseCriterioBusquedaVO.MAX_RESULTS.intValue(),
								Keys.DISTRIBUCION_IN_DIST, Integer
										.valueOf(libro.getId()),
								createPermBooks, distributionResults,
								getDistWhere(dist.getEstado().getIdEstado()),
								StringUtils.EMPTY, launchDistOutRegister,
								usuario.getConfiguracionUsuario().getLocale(),
								usuario.getConfiguracionUsuario()
										.getIdEntidad());
			} catch (Exception e) {
				acceptDistribucionCatchException(usuario, distribucion);
			} finally {
				// Se eliminan los bloqueos que se pudieran haber generado
				if (null != resultOfAcceptDistribution) {
					unlockDistributionRegisters(usuario,
							resultOfAcceptDistribution);
				}

				postAcceptDistribution(usuario, distribucion);
			}

		} catch (Exception e) {
			acceptDistribucionCatchException(usuario, distribucion, e);
		}
	}

	/**
	 * @deprecated
	 * @param usuario
	 * @param distribucion
	 */
	public void acceptDistribucionCatchException(UsuarioVO usuario,
			DistribucionVO distribucion) {
		acceptDistribucionCatchException(usuario, distribucion, null);
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 * @param e
	 */
	public void acceptDistribucionCatchException(UsuarioVO usuario,
			DistribucionVO distribucion, Exception e) {

		StringBuffer message = new StringBuffer(
				"No se ha podido aceptar la distribucion con numero de registro asociado [")
				.append(distribucion.getRegistro().getNumeroRegistro()).append(
						"]");
		logger.error(message.toString(), e);

		throw new DistributionException(message.toString(), e);
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public void postAcceptDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public void preAcceptDistribucion(UsuarioVO usuario,
			DistribucionVO distribucion) {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	public final void acceptDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {

		// Invocamos a la aceptación de una distribución sin especificar un
		// libro
		acceptDistribution(usuario, distribucion, new NullLibroVO());
	}

	/**
	 * {@inheritDoc}
	 */
	public final void archiveDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		Assert.notNull(distribucion.getRegistro().getNumeroRegistro(),
				"No se puede archivar una distribucion de un registro nulo");

		checkArchiveDistributionPermissions(usuario);

		try {
			/*
			 * La distribucion debe encontrarse en la bandeja de entrada del
			 * usuario en estado ACEPTADO.
			 */
			DistribucionVO dist = getDistributionFromInputBox(
					usuario,
					distribucion.getRegistro(),
					new EstadoDistribucionEnum[] { EstadoDistribucionEnum.ACEPTADO });

			try {
				preArchiveDistribucion(usuario, dist);

				DistributionSession.saveDistribution(usuario
						.getConfiguracionUsuario().getSessionID(),
						Arrays.asList(new Integer[] { Integer.valueOf(dist
								.getId()) }), EstadoDistribucionEnum.ARCHIVADO
								.getValue(),
						BaseCriterioBusquedaVO.DEFAULT_OFFSET,
						BaseCriterioBusquedaVO.MAX_RESULTS.intValue(),
						Keys.DISTRIBUCION_IN_DIST, getDistWhere(dist
								.getEstado().getIdEstado()), StringUtils.EMPTY,
						usuario.getConfiguracionUsuario().getLocale(), usuario
								.getConfiguracionUsuario().getIdEntidad());
			} catch (Exception e) {
				archiveDistribucionCatchException(usuario, dist);
			} finally {
				postArchiveDistribucion(usuario, dist);
			}

		} catch (Exception e) {
			archiveDistribucionCatchException(usuario, distribucion, e);
		}
	}

	/**
	 *
	 * @param usuario
	 * @param dist
	 */
	public void postArchiveDistribucion(UsuarioVO usuario, DistribucionVO dist) {
		// nothing to do
	}

	/**
	 * @deprecated
	 * @param usuario
	 * @param dist
	 */
	public void archiveDistribucionCatchException(UsuarioVO usuario,
			DistribucionVO dist) {
		archiveDistribucionCatchException(usuario, dist, null);
	}

	/**
	 *
	 * @param usuario
	 * @param dist
	 * @param e
	 */
	public void archiveDistribucionCatchException(UsuarioVO usuario,
			DistribucionVO dist, Exception e) {

		StringBuffer message = new StringBuffer(
				"No se ha podido archivar la distribucion con numero de registro asociado [")
				.append(dist.getRegistro().getNumeroRegistro()).append("]");
		logger.error(message.toString(), e);

		throw new DistributionException(message.toString(), e);
	}

	/**
	 *
	 * @param usuario
	 * @param dist
	 */
	public void preArchiveDistribucion(UsuarioVO usuario, DistribucionVO dist) {
		// nothing to do
	}

	public final void redistributeInputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {

		checkRedistributeDistributionPrerequisites(usuario, distribucion);

		DistribucionVO dist = getDistributionFromInputBox(usuario,
				distribucion.getRegistro(), new EstadoDistribucionEnum[] {
						EstadoDistribucionEnum.PENDIENTE,
						EstadoDistribucionEnum.ACEPTADO });

		try {
			preRedistributeDistribution(usuario, dist);

			redistributeDistribution(usuario, dist, Keys.DISTRIBUCION_IN_DIST,
					distribucion.getDestinoDistribucion().getId());
		} catch (Exception e) {
			redistributeInputDistributionCatchException(usuario, dist, e);
		} finally {
			postRedistributeInputDistribution(usuario, dist);
		}
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public void postRedistributeInputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 * @param e
	 */
	public void redistributeInputDistributionCatchException(UsuarioVO usuario,
			DistribucionVO distribucion, Exception e) {
		// nothing to do
		StringBuffer message = new StringBuffer(
				"No se ha podido redistribuir el registro asociado [").append(
				distribucion.getRegistro().getNumeroRegistro()).append("]");
		logger.error(message.toString(), e);
		throw new DistributionException(message.toString(), e);
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public void preRedistributeDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		// nothing to do
	}

	/**
	 *
	 */
	public final void redistributeOutputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {

		checkRedistributeDistributionPrerequisites(usuario, distribucion);

		DistribucionVO dist = getDistributionFromOutputBox(
				usuario,
				distribucion.getRegistro(),
				new EstadoDistribucionEnum[] { EstadoDistribucionEnum.RECHAZADO });

		try {
			preRedistributeOutputDistribution(usuario, dist);

			redistributeDistribution(usuario, dist, Keys.DISTRIBUCION_OUT_DIST,
					distribucion.getDestinoDistribucion().getId());

		} catch (Exception e) {
			redistributeOutputDistributionCatchException(usuario, dist, e);
		} finally {
			postRedistributeOutputDistribution(usuario, dist);

		}
	}

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public void postRedistributeOutputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		// nothing to do
	}

	public void redistributeOutputDistributionCatchException(UsuarioVO usuario,
			DistribucionVO distribucion, Exception e) {

		StringBuffer message = new StringBuffer(
				"No se ha podido redistribuir el registro asociado [").append(
				distribucion.getRegistro().getNumeroRegistro()).append("]");

		logger.error(message.toString(), e);
		throw new DistributionException(message.toString(), e);

	}

	public void preRedistributeOutputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		// nothing to do
	}

	/**
	 * Devuelve la distribución asociada al número de registro que recibe como
	 * parámetro como atributo <code>numeroRegistro</code> de
	 * <code>registro</code>. La distribucion debe encontrarse en alguno de los
	 * estados especificados en el array <code>estados</code> en la bandeja de
	 * entrada del usuario.
	 *
	 * @param usuario
	 * @param registro
	 * @param estados
	 *            array de estados para los que se quieren buscar distribuciones
	 * @throws UnknownDistributionException
	 *             en caso de que no exista un distribucion con el número de
	 *             registro especificado
	 * @return
	 */
	protected DistribucionVO getDistributionFromInputBox(UsuarioVO usuario,
			BaseRegistroVO registro, EstadoDistribucionEnum[] estados) {

		checkLoadDistributionsPermissions(usuario);

		List inputBox = new ArrayList();

		for (int i = 0; i < estados.length; i++) {
			CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
					new Long(Integer.MAX_VALUE), new Long(
							BaseCriterioBusquedaVO.DEFAULT_OFFSET), estados[i]);
			inputBox.addAll(getInputDistributions(usuario, criterio)
					.getDistributions());
		}

		DistribucionVO find = (DistribucionVO) CollectionUtils.find(
				inputBox,
				new BeanPropertyValueEqualsPredicate(
						REGISTRO_NUMERO_REGISTRO_PROPERTY, registro
								.getNumeroRegistro()));

		if (null == find) {
			StringBuffer message = new StringBuffer(
					"No existe una distribucion en la bandeja de entrada del usuario [")
					.append(usuario.getLoginName())
					.append("] para el numero de registro [")
					.append(registro.getNumeroRegistro())
					.append("] en ninguno de los estados [");
			for (int i = 0; i < estados.length; i++) {
				message.append(estados[i].getName()).append(" ");
			}
			message.append("]");
			logger.warn(message.toString());

			throw new UnknownDistributionException(message.toString());
		}

		return find;
	}

	/**
	 * Devuelve las distribuciones asociadas al número de registro que recibe
	 * como parámetro como atributo <code>numeroRegistro</code> de
	 * <code>registro</code>. La distribucion debe encontrarse en alguno de los
	 * estados especificados en el array <code>estados</code> en la bandeja de
	 * entrada del usuario.
	 *
	 * @param usuario
	 * @param registro
	 * @param estados
	 *            array de estados para los que se quieren buscar distribuciones
	 * @throws UnknownDistributionException
	 *             en caso de que no exista un distribucion con el número de
	 *             registro especificado
	 * @return
	 */
	protected List<DistribucionVO> getDistributionsFromInputBox(
			UsuarioVO usuario, String numRegistro,
			EstadoDistribucionEnum[] estados) {

		checkLoadDistributionsPermissions(usuario);

		List<DistribucionVO> inputBox = new ArrayList<DistribucionVO>();

		for (int i = 0; i < estados.length; i++) {
			CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
					new Long(Integer.MAX_VALUE), new Long(
							BaseCriterioBusquedaVO.DEFAULT_OFFSET), estados[i]);
			inputBox.addAll((List<DistribucionVO>) getInputDistributions(
					usuario, criterio).getDistributions());
		}

		CollectionUtils.filter(inputBox, new BeanPropertyValueEqualsPredicate(
				REGISTRO_NUMERO_REGISTRO_PROPERTY, numRegistro));

		if (CollectionUtils.isEmpty(inputBox)) {
			StringBuffer message = new StringBuffer(
					"No existe una distribucion en la bandeja de entrada del usuario [")
					.append(usuario.getLoginName())
					.append("] para el numero de registro [")
					.append(numRegistro)
					.append("] en ninguno de los estados [");
			for (int i = 0; i < estados.length; i++) {
				message.append(estados[i].getName()).append(" ");
			}
			message.append("]");
			logger.warn(message.toString());

			throw new UnknownDistributionException(message.toString());
		}

		return inputBox;
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 * @param estados
	 * @return
	 */
	protected DistribucionVO getDistributionFromOutputBox(UsuarioVO usuario,
			BaseRegistroVO registro, EstadoDistribucionEnum[] estados) {

		checkLoadDistributionsPermissions(usuario);

		List outputBox = new ArrayList();

		for (int i = 0; i < estados.length; i++) {
			CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
					new Long(Integer.MAX_VALUE), new Long(
							BaseCriterioBusquedaVO.DEFAULT_OFFSET), estados[i]);
			outputBox.addAll(getOutputDistributions(usuario, criterio)
					.getDistributions());
		}

		DistribucionVO find = (DistribucionVO) CollectionUtils.find(
				outputBox,
				new BeanPropertyValueEqualsPredicate(
						REGISTRO_NUMERO_REGISTRO_PROPERTY, registro
								.getNumeroRegistro()));

		if (null == find) {
			StringBuffer message = new StringBuffer(
					"No existe una distribucion en la bandeja de salida del usuario [")
					.append(usuario.getLoginName())
					.append("] para el numero de registro [")
					.append(registro.getNumeroRegistro())
					.append("] en ninguno de los estados [");
			for (int i = 0; i < estados.length; i++) {
				message.append(estados[i].getName()).append(" ");
			}
			message.append("]");
			logger.warn(message.toString());

			throw new UnknownDistributionException(message.toString());
		}

		return find;
	}

	/**
	 * Devuelve las distribuciones asociadas al número de registro que recibe
	 * como parámetro como atributo <code>numeroRegistro</code> de
	 * <code>registro</code>. La distribucion debe encontrarse en alguno de los
	 * estados especificados en el array <code>estados</code> en la bandeja de
	 * entrada del usuario.
	 *
	 * @param usuario
	 * @param registro
	 * @param estados
	 *            array de estados para los que se quieren buscar distribuciones
	 * @throws UnknownDistributionException
	 *             en caso de que no exista un distribucion con el número de
	 *             registro especificado
	 * @return
	 */
	protected List<DistribucionVO> getDistributionsFromOutputBox(
			UsuarioVO usuario, String numRegistro,
			EstadoDistribucionEnum[] estados) {

		checkLoadDistributionsPermissions(usuario);

		List<DistribucionVO> outputBox = new ArrayList<DistribucionVO>();

		for (int i = 0; i < estados.length; i++) {
			CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
					new Long(Integer.MAX_VALUE), new Long(
							BaseCriterioBusquedaVO.DEFAULT_OFFSET), estados[i]);
			outputBox.addAll(getOutputDistributions(usuario, criterio)
					.getDistributions());
		}

		CollectionUtils.filter(outputBox, new BeanPropertyValueEqualsPredicate(
				REGISTRO_NUMERO_REGISTRO_PROPERTY, numRegistro));

		if (CollectionUtils.isEmpty(outputBox)) {
			StringBuffer message = new StringBuffer(
					"No existe una distribucion en la bandeja de salida del usuario [")
					.append(usuario.getLoginName())
					.append("] para el numero de registro [")
					.append(numRegistro)
					.append("] en ninguno de los estados [");
			for (int i = 0; i < estados.length; i++) {
				message.append(estados[i].getName()).append(" ");
			}
			message.append("]");
			logger.warn(message.toString());

			throw new UnknownDistributionException(message.toString());
		}

		return outputBox;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void rejectDistribution(UsuarioVO usuario,
			DistribucionVO distribucion) {
		Assert.notNull(distribucion);
		Assert.notNull(
				distribucion.getRegistro().getNumeroRegistro(),
				"No se puede rechazar una distribucion sin especificar el número de registro asociado");
		Assert.notNull(distribucion.getMensajeDistribucion(),
				"No se puede rechazar una distribucion sin especificar un motivo");

		checkRejectDistributionPermissions(usuario);

		DistribucionVO dist = null;
		try {
			/*
			 * Para rechazar una distribucion tiene que estar en la bandeja de
			 * entrada en estado PENDIENTE.
			 */
			dist = getDistributionFromInputBox(
					usuario,
					distribucion.getRegistro(),
					new EstadoDistribucionEnum[] { EstadoDistribucionEnum.PENDIENTE });

			boolean isLdap = LDAPAuthenticationPolicy.isLdap(usuario
					.getConfiguracionUsuario().getIdEntidad());

			preRejectDistribucion(usuario, dist);

			getDistributionUseCase()
					.rejectDistribution(
							usuario.getConfiguracionUsuario().getSessionID(),
							usuario.getConfiguracionUsuario().getIdEntidad(),
							isLdap,
							usuario.getConfiguracionUsuario().getLocale(),
							Arrays.asList(new Integer[] { Integer.valueOf(dist
									.getId()) }),
							EstadoDistribucionEnum.RECHAZADO.getValue(),
							BaseCriterioBusquedaVO.DEFAULT_OFFSET,
							Keys.DISTRIBUCION_IN_DIST,
							distribucion.getMensajeDistribucion(),
							getDistWhere(dist.getEstado().getIdEstado()),
							StringUtils.EMPTY);
		} catch (Exception e) {
			rejectDistribucionCatchException(usuario, dist, e);
		} finally {
			postRejectDistribucion(usuario, dist);
		}
	}

	/**
	 *
	 * @param usuario
	 * @param dist
	 * @param e
	 */
	public void rejectDistribucionCatchException(UsuarioVO usuario,
			DistribucionVO dist, Exception e) {

		StringBuffer message = new StringBuffer(
				"No se ha podido redistribuir la distribucion con numero de registro asociado [")
				.append(dist.getRegistro().getNumeroRegistro()).append("]");
		logger.error(message.toString(), e);

		throw new DistributionException(message.toString(), e);
	}

	/**
	 *
	 * @param usuario
	 * @param dist
	 */
	public void postRejectDistribucion(UsuarioVO usuario, DistribucionVO dist) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param dist
	 */
	public void preRejectDistribucion(UsuarioVO usuario, DistribucionVO dist) {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	public ResultadoBusquedaDistribucionVO getInputDistributions(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio) {

		return getDistributions(usuario, criterio, Keys.DISTRIBUCION_IN_DIST);
	}

	/**
	 * {@inheritDoc}
	 */
	public ResultadoBusquedaDistribucionVO getOutputDistributions(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio) {

		return getDistributions(usuario, criterio, Keys.DISTRIBUCION_OUT_DIST);
	}

	public DistributionUseCase getDistributionUseCase() {
		return distributionUseCase;
	}

	public void setDistributionUseCase(DistributionUseCase distributionUseCase) {
		this.distributionUseCase = distributionUseCase;
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

	public LoginLegacyManagerImpl getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginLegacyManagerImpl loginManager) {
		this.loginManager = loginManager;
	}

	/**
	 * Recupera las distribuciones asociadas al usuario <code>usuario</code> que
	 * cumplan las condiciones del criterio de búsqueda <code>criterio</code> y
	 * sean del tipo <code>tipo</code>. Los valores que puede tomar el parámetro
	 * tipo son <code>Keys.DISTRIBUCION_IN_DIST</code> y
	 * <code>Keys.DISTRIBUCION_OUT_DIST</code>.
	 *
	 * @see Keys
	 *
	 * @param usuario
	 * @param criterio
	 * @param tipo
	 * @return
	 */
	protected ResultadoBusquedaDistribucionVO getDistributions(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio, int tipo) {
		Assert.notNull(usuario);
		Assert.notNull(criterio);
		Assert.notNull(criterio.getEstado(),
				"Estado de distribucion no permitido");
		Assert.isTrue(tipo == Keys.DISTRIBUCION_IN_DIST
				|| tipo == Keys.DISTRIBUCION_OUT_DIST,
				"Tipo de distribucion no permitido");

		// Es necesario registrar los libros del usuario para que no fallen las
		// invocaciones al código legado.
		forceBookRegistrationForUser(usuario);

		DistributionResults distributionResults = getDistributionResults(
				usuario, criterio, tipo);

		return getResultadoBusquedaDistribucionVO(usuario, criterio, tipo,
				distributionResults);
	}

	/**
	 * Recupera las distribuciones que cumplan las condiciones del criterio de
	 * búsqueda <code>criterio</code> y sean del tipo <code>tipo</code>. Los
	 * valores que puede tomar el parámetro tipo son
	 * <code>Keys.DISTRIBUCION_IN_DIST</code> y
	 * <code>Keys.DISTRIBUCION_OUT_DIST</code>.
	 *
	 *
	 * @see Keys
	 *
	 * @param usuario
	 * @param criterio
	 * @param tipo
	 *            Tipo de distribución (Entrada o Salida). Los valores que puede
	 *            tomar el parámetro tipo son
	 *            <code>Keys.DISTRIBUCION_IN_DIST</code> y
	 *            <code>Keys.DISTRIBUCION_OUT_DIST</code>.
	 *
	 * @param returnOnlyUserDistributions
	 *            Si es verdadero devuelve únicamente las distribuciones del
	 *            usuario. Si es false devuelve todas las distribuciones que
	 *            cumplan con la condición y no tiene en cuenta que pertenezcan
	 *            al usuario
	 *
	 * @return
	 */
	protected ResultadoBusquedaDistribucionVO getDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			int tipo, String query, boolean returnOnlyUserDistributions) {
		Assert.notNull(usuario);
		Assert.notNull(criterio);

		Assert.isTrue(tipo == Keys.DISTRIBUCION_IN_DIST
				|| tipo == Keys.DISTRIBUCION_OUT_DIST,
				"Tipo de distribucion no permitido");

		// Es necesario registrar los libros del usuario para que no fallen las
		// invocaciones al código legado.
		forceBookRegistrationForUser(usuario);

		DistributionResults distributionResults = getDistributionResultsByCondition(
				usuario, criterio, tipo, query, returnOnlyUserDistributions);

		List distributions = distributionResultsToDistribucionVOList(usuario,
				distributionResults);

		return new ResultadoBusquedaDistribucionVO(distributions,
				distributionResults.getTotalSize());

	}

	/**
	 * @param usuario
	 * @param criterio
	 * @param tipo
	 * @param distributionResults
	 * @return
	 */
	private ResultadoBusquedaDistribucionVO getResultadoBusquedaDistribucionVO(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			int tipo, DistributionResults distributionResults) {

		List distributions = distributionResultsToDistribucionVOList(usuario,
				distributionResults);

		return new ResultadoBusquedaDistribucionVO(distributions,
				distributionResults.getTotalSize());
	}

	/**
	 * @param usuario
	 * @param distributionResults
	 * @return
	 */
	private List distributionResultsToDistribucionVOList(UsuarioVO usuario,
			DistributionResults distributionResults) {
		List distributions = new ArrayList();

		if (!distributionResults.getResults().values().isEmpty()) {
			Iterator iterator = distributionResults.getResults().values()
					.iterator();
			while (iterator.hasNext()) {
				Map oficReg = (Map) iterator.next();

				Iterator distIterator = oficReg.values().iterator();
				while (distIterator.hasNext()) {
					ScrDistreg reg = (ScrDistreg) distIterator.next();

					// Transformamos los datos básicos de la distribución
					DistribucionVO distribucion = new ScrDistregToDistribucionVOMapper(
							getUnidadAdministrativaManager(),
							getTipoAsuntoManager(), usuario).map(reg);

					distributions.add(distribucion);
				}
			}

		}
		return distributions;
	}

	/**
	 * {@inheritDoc} com.ieci.tecdoc.common.isicres.Keys.DISTRIBUCION_OUT_DIST
	 * com.ieci.tecdoc.common.isicres.Keys.DISTRIBUCION_IN_DIST
	 */
	protected void redistributeDistribution(UsuarioVO usuario,
			DistribucionVO distribucion, int type, String codDestino) {

		try {

			checkDistributeRejectedDistributionPermissions(usuario,
					distribucion);

			// Cambiar destino de distribucion
			Integer launchDistOutRegister = new Integer(BookSession
					.invesicresConf(
							usuario.getConfiguracionUsuario().getIdEntidad())
					.getDistSRegister());
			Integer canDestWithoutList = new Integer(BookSession
					.invesicresConf(
							usuario.getConfiguracionUsuario().getIdEntidad())
					.getCanChangeDestWithoutList());

			DistributionSession.changeDistribution(usuario
					.getConfiguracionUsuario().getSessionID(), Arrays
					.asList(new Integer[] { Integer.valueOf(distribucion
							.getId()) }), codDestino, type,
					launchDistOutRegister, canDestWithoutList, usuario
							.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad(), usuario.isLdapUser());

		} catch (BookException be) {
			StringBuffer message = new StringBuffer(
					"No se puede cambiar el destino de la distribución debido a que el destino seleccionado no tiene lista de distribución asociada.");
			logger.error(message.toString(), be);

			throw new DistributionException(message.toString(), be);
		} catch (EventException eE) {
			logger.error(eE);
			throw eE;
		} catch (Exception e) {
			StringBuffer message = new StringBuffer(
					"No se ha podido cambiar el destino de la distribucion con registro asociado [")
					.append(distribucion.getRegistro().getNumeroRegistro())
					.append("]");
			logger.error(message.toString(), e);

			throw new DistributionException(message.toString(), e);
		}
	}

	/**
	 * Verifica que se cumplen los requisitos necesarios para poder cambiar el
	 * destino de la distribucion <code>distribucion</code> por parte del
	 * usuario <code>usuario</code>.
	 *
	 * @param usuario
	 * @param distribucion
	 */
	private void checkRedistributeDistributionPrerequisites(UsuarioVO usuario,
			DistribucionVO distribucion) {
		Assert.notNull(
				distribucion.getRegistro().getNumeroRegistro(),
				"No se puede redistribuir una distribucion sin indicar el número de registro asociado.");
		Assert.notNull(
				distribucion.getDestinoDistribucion().getId(),
				"No se puede redistribuir una distribución sin indicar el código de la nueva oficina de destino");

		checkDistributeDistributionPermissions(usuario);
	}

	/**
	 * Comprueba que el usuario <code>usuario</code> tenga permisos de
	 * distribución (cambio de destino) para la distribucion
	 * <code>distribucion</code>. Solo los usuarios con perfil
	 * <i>superusuario</i> o aquellas con permiso para cambiar el destino de una
	 * distribucion rechazada podrán hacerlo.
	 *
	 * @param usuario
	 * @param distribucion
	 */
	protected void checkDistributeRejectedDistributionPermissions(
			UsuarioVO usuario, DistribucionVO distribucion) {
		PermisosAplicacionVO permisosAplicacion = usuario.getPermisos()
				.getPermisosAplicacion();
		if (!permisosAplicacion.isSuperUsuario()
				&& !permisosAplicacion.isCambiarDestinoDistribucion()
				|| (EstadoDistribucionEnum.RECHAZADO.equals(distribucion
						.getEstado().getIdEstado()) && !permisosAplicacion
						.isCambiarDestinoDistribucionRechazada())) {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos suficientes para cambiar el destino de una distribucion rechazada");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
			throw new SecurityException(sb.toString());
		}
	}

	/**
	 * Comprueba que el usuario <code>usuario</code> tenga permisos de
	 * distribución (cambio de destino) para la distribucion
	 * <code>distribucion</code>. Solo los usuarios con perfil
	 * <i>superusuario</i> o aquellas con permiso para cambiar el destino de una
	 * distribucion podrán hacerlo.
	 *
	 * @param usuario
	 *
	 */
	protected void checkDistributeDistributionPermissions(UsuarioVO usuario) {
		PermisosAplicacionVO permisosAplicacion = usuario.getPermisos()
				.getPermisosAplicacion();
		if (!permisosAplicacion.isSuperUsuario()
				&& !permisosAplicacion.isCambiarDestinoDistribucion()) {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos suficientes para cambiar el destino de una distribucion");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
			throw new SecurityException(sb.toString());
		}
	}

	/**
	 * Comprueba que el usuario <code>usuario</code> tenga permisos para aceptar
	 * la distribucion <code>distribucion</code>. Solo los usuarios con perfil
	 * <i>superusuario</i> o aquellas con permiso para aceptar distribuciones
	 * podrán hacerlo.
	 *
	 * @param usuario
	 */
	protected void checkAcceptDistributionPermissions(UsuarioVO usuario) {
		PermisosAplicacionVO permisosAplicacion = usuario.getPermisos()
				.getPermisosAplicacion();
		if (!permisosAplicacion.isSuperUsuario()
				&& !permisosAplicacion.isAceptarDistribucion()) {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos suficientes para aceptar una distribucion");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
			throw new SecurityException(sb.toString());
		}
	}

	/**
	 * Comprueba que el usuario <code>usuario</code> tenga permisos para
	 * archivar la distribucion <code>distribucion</code>. Solo los usuarios con
	 * perfil <i>superusuario</i> o aquellas con permiso para archivar
	 * distribuciones podrán hacerlo.
	 *
	 * @param usuario
	 */
	protected void checkArchiveDistributionPermissions(UsuarioVO usuario) {
		PermisosAplicacionVO permisosAplicacion = usuario.getPermisos()
				.getPermisosAplicacion();
		if (!permisosAplicacion.isSuperUsuario()
				&& !permisosAplicacion.isArchivarDistribucion()) {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos suficientes para archivar una distribucion");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
			throw new SecurityException(sb.toString());
		}
	}

	/**
	 * Comprueba que el usuario <code>usuario</code> tiene permisos suficientes
	 * para cargar distribuciones de registros.
	 *
	 * @param usuario
	 *            usuario autenticado
	 */
	protected void checkLoadDistributionsPermissions(UsuarioVO usuario) {
		// nothing to do
	}

	/**
	 * Comprueba que el usuario <code>usuario</code> tenga permisos para
	 * rechazar la distribución <code>distribucion</code>. Solo los usuarios con
	 * perfil <i>superusuario</i> o aquellas con permiso para rechazar
	 * distribuciones podrán hacerlo.
	 *
	 * @param usuario
	 */
	protected void checkRejectDistributionPermissions(UsuarioVO usuario) {
		PermisosAplicacionVO permisosAplicacion = usuario.getPermisos()
				.getPermisosAplicacion();
		if (!permisosAplicacion.isSuperUsuario()
				&& !permisosAplicacion.isRechazarDistribucion()) {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos suficientes para rechazar una distribucion");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
			throw new SecurityException(sb.toString());
		}
	}

	/**
	 * Método que se encarga de forzar el registro de los libros sobre los que
	 * puede trabajar el usuario <code>usuario</code> en la clase
	 * <code>Repository</code>. Esto es necesario para que no fallen
	 * invocaciones posteriores al código legado de ISicres.
	 *
	 * @see Repository
	 *
	 * @param usuario
	 */
	private void forceBookRegistrationForUser(UsuarioVO usuario) {
		try {
			BookSession.getInBooks(usuario.getConfiguracionUsuario()
					.getSessionID(), usuario.getConfiguracionUsuario()
					.getLocale(), usuario.getConfiguracionUsuario()
					.getIdEntidad());
			BookSession.getOutBooks(usuario.getConfiguracionUsuario()
					.getSessionID(), usuario.getConfiguracionUsuario()
					.getLocale(), usuario.getConfiguracionUsuario()
					.getIdEntidad());
		} catch (Exception e) {
			StringBuffer message = new StringBuffer(
					"Imposible registrar los libros del usuario [").append(
					usuario.getLoginName()).append("]");
			logger.error(message.toString(), e);

			throw new DistributionException(message.toString(), e);
		}
	}

	/**
	 * Elimina los bloqueos que se hayan podido generar como consecuencia de la
	 * aceptación de una distribución.
	 *
	 * @param usuario
	 * @param resultOfAcceptDistribution
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private void unlockDistributionRegisters(UsuarioVO usuario,
			Object resultOfAcceptDistribution) throws BookException,
			SessionException, ValidationException {
		List archidFdr;
		if (resultOfAcceptDistribution instanceof List) {
			archidFdr = (List) resultOfAcceptDistribution;
		} else {
			archidFdr = (List) ((Map) resultOfAcceptDistribution)
					.get(new Integer(-1));
		}

		if (archidFdr != null && !archidFdr.isEmpty()) {
			for (Iterator iterator = archidFdr.iterator(); iterator.hasNext();) {
				ScrDistreg distReg = (ScrDistreg) iterator.next();
				FolderSession.closeFolder(usuario.getConfiguracionUsuario()
						.getSessionID(), new Integer(distReg.getIdArch()),
						distReg.getIdFdr(), usuario.getConfiguracionUsuario()
								.getIdEntidad());
			}
		}
	}

	/**
	 * Devuelve las distribuciones del usuario <code>usuario</code> que cumplen
	 * con las condiciones de <code>criterio</code>. Los objetos devueltos son
	 * del API legado.
	 *
	 * @param usuario
	 * @param criterio
	 * @param tipo
	 * @return
	 */
	private DistributionResults getDistributionResults(UsuarioVO usuario,
			CriterioBusquedaDistribucionVO criterio, int tipo) {
		DistributionResults distributionResults = null;
		AuthenticationSwitcher authenticationSwitcher = null;

		try {
			boolean isOfficeAsoc = Boolean
					.valueOf(
							Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC))
					.booleanValue();

			// En caso de que se nos especifique un id de usuario hay que
			// suplantar la identidad del usuario autenticado por la del que se
			// nos da su identificador interno

			if (null != criterio.getUser()) {
				AuthenticationUser manInTheMiddle = getLoginManager()
						.getUserInfo(
								criterio.getUser(),
								usuario.getConfiguracionUsuario()
										.getIdEntidad());
				authenticationSwitcher = new AuthenticationSwitcher(usuario
						.getConfiguracionUsuario().getSessionID());
				authenticationSwitcher
						.impersonateAuthenticatedUser(manInTheMiddle);
			}

			distributionResults = DistributionSession.getDistribution(usuario
					.getConfiguracionUsuario().getSessionID(), criterio
					.getEstado().getValue(), criterio.getOffset().intValue(),
					criterio.getLimit().intValue(), tipo, getDistWhere(criterio
							.getEstado()), StringUtils.EMPTY, isOfficeAsoc,
					usuario.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad());

		} catch (LoginException le) {
			StringBuffer sb = new StringBuffer(
					"Error al recuperar las distribuciones del usuario con id [")
					.append(criterio.getUser()).append("]");
			logger.error(sb.toString());

			throw new DistributionException(sb.toString(), le);
		} catch (Exception e) {
			StringBuffer message = new StringBuffer(
					"Error al recuperar las distribuciones del usuario [")
					.append(usuario.getLoginName()).append("] en estado [")
					.append(criterio.getEstado()).append("]");
			logger.error(message.toString(), e);

			throw new DistributionException(message.toString(), e);
		} finally {
			// Restauramos la identidad del usuario autenticado
			if (null != criterio.getUser()) {
				authenticationSwitcher.restoreAuthenticatedUser();
			}
		}

		return distributionResults;
	}

	/**
	 * Devuelve las distribuciones del usuario <code>usuario</code> que cumplen
	 * con las condiciones de <code>criterio</code>. Los objetos devueltos son
	 * del API legado.
	 *
	 * @param usuario
	 * @param criterio
	 * @param tipo
	 *            Tipo de distribución (Entrada o Salida)
	 * @param returnOnlyUserDistributions
	 *            Si es verdadero devuelve únicamente las distribuciones del
	 *            usuario. Si es false devuelve todas las distribuciones que
	 *            cumplan con la condición y no tiene en cuenta que pertenezcan
	 *            al usuario
	 * @return
	 */
	private DistributionResults getDistributionResultsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			int tipo, String query, boolean returnOnlyUserDistributions) {
		DistributionResults distributionResults = null;
		AuthenticationSwitcher authenticationSwitcher = null;

		try {
			boolean isOfficeAsoc = Boolean
					.valueOf(
							Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC))
					.booleanValue();

			// En caso de que se nos especifique un id de usuario hay que
			// suplantar la identidad del usuario autenticado por la del que se
			// nos da su identificador interno

			if (!returnOnlyUserDistributions) {
				String entidad = usuario.getConfiguracionUsuario()
						.getIdEntidad();

				distributionResults = DistributionSession.getDistribution(
						usuario.getConfiguracionUsuario().getSessionID(),
						criterio.getOffset().intValue(), criterio.getLimit()
								.intValue(), tipo, query, usuario
								.getConfiguracionUsuario().getLocale(), usuario
								.getConfiguracionUsuario().getIdEntidad(),
						LDAPAuthenticationPolicy.isLdap(entidad));
			} else {

				if (null != criterio.getUser()) {
					AuthenticationUser manInTheMiddle = getLoginManager()
							.getUserInfo(
									criterio.getUser(),
									usuario.getConfiguracionUsuario()
											.getIdEntidad());
					authenticationSwitcher = new AuthenticationSwitcher(usuario
							.getConfiguracionUsuario().getSessionID());
					authenticationSwitcher
							.impersonateAuthenticatedUser(manInTheMiddle);
				}

				distributionResults = DistributionSession.getDistribution(
						usuario.getConfiguracionUsuario().getSessionID(), 0,
						criterio.getOffset().intValue(), criterio.getLimit()
								.intValue(), tipo, query, StringUtils.EMPTY,
						isOfficeAsoc, usuario.getConfiguracionUsuario()
								.getLocale(), usuario.getConfiguracionUsuario()
								.getIdEntidad());
			}

		} catch (LoginException le) {
			StringBuffer sb = new StringBuffer(
					"Error al recuperar las distribuciones del usuario con id [")
					.append(criterio.getUser()).append("]");
			logger.error(sb.toString());

			throw new DistributionException(sb.toString(), le);
		} catch (Exception e) {
			StringBuffer message = new StringBuffer(
					"Error al recuperar las distribuciones del usuario [")
					.append(usuario.getLoginName()).append("] en estado [")
					.append(criterio.getEstado()).append("]");
			logger.error(message.toString(), e);

			throw new DistributionException(message.toString(), e);
		} finally {
			// Restauramos la identidad del usuario autenticado
			if (null != criterio.getUser()) {
				authenticationSwitcher.restoreAuthenticatedUser();
			}
		}

		return distributionResults;
	}

	/**
	 * Devuelve una <code>java.util.List</code> con los identificadores de los
	 * libros sobre los que <code>usuario</code> tiene permiso de creación.
	 *
	 * @param usuario
	 * @return
	 */
	private List getCreatePermBooks(UsuarioVO usuario) {
		List perms = new ArrayList();

		List inList;
		try {
			inList = BookSession.getInBooks(usuario.getConfiguracionUsuario()
					.getSessionID(), usuario.getConfiguracionUsuario()
					.getLocale(), usuario.getConfiguracionUsuario()
					.getIdEntidad());

			boolean canCreate = false;
			for (Iterator it = inList.iterator(); it.hasNext();) {
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				Integer id = scrRegStateByLanguage.getIdocarchhdrId();

				BookSession.openBook(usuario.getConfiguracionUsuario()
						.getSessionID(), id, usuario.getConfiguracionUsuario()
						.getIdEntidad());
				canCreate = SecuritySession.canCreate(usuario
						.getConfiguracionUsuario().getSessionID(), id);
				if (canCreate) {
					perms.add(id);
				}
			}
		} catch (Exception e) {
			StringBuffer message = new StringBuffer(
					"No se ha podido recuperar los permisos de creacion para los libros del usuario [")
					.append(usuario.getLoginName()).append("]");
			logger.error(message.toString(), e);
		}

		return perms;
	}

	/**
	 * Método de utilidad para componer la where del SQL necesario para
	 * recuperar una distribución. Se usa en los diferentes métodos que invocan
	 * a métodos de <code>DistributionSession</code> por mantener compatibilidad
	 * con el código legado.
	 *
	 * @param distribution
	 * @return
	 */
	private String getDistWhere(EstadoDistribucionEnum estado) {
		return new StringBuffer("STATE=").append(estado.getValue()).toString();
	}

	/**
	 * @return el libroManager
	 */
	public LibroManager getLibroManager() {
		return libroManager;
	}

	/**
	 * @param libroManager
	 *            el libroManager a fijar
	 */
	public void setLibroManager(LibroManager libroManager) {
		this.libroManager = libroManager;
	}

	/**
	 * @return el registroManager
	 */
	public RegistroManager getRegistroManager() {
		return registroManager;
	}

	/**
	 * @param registroManager
	 *            el registroManager a fijar
	 */
	public void setRegistroManager(RegistroManager registroManager) {
		this.registroManager = registroManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getDistributionsByCondition(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO,
	 *      java.lang.String)
	 */
	@Override
	public ResultadoBusquedaDistribucionVO getUserDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query) {

		CriterioBusquedaDistribucionVO criterioTodos = new CriterioBusquedaDistribucionVO();
		criterioTodos.setLimit(Long.valueOf(Integer.MAX_VALUE));
		criterioTodos.setOffset(0L);

		// 1.- Buscar dist entrada
		ResultadoBusquedaDistribucionVO distribucionesEntrada = getDistributionsByCondition(
				usuario, criterioTodos, Keys.DISTRIBUCION_IN_DIST, query, true);

		// 2.- Buscar dist salida
		ResultadoBusquedaDistribucionVO distribucionesSalida = getDistributionsByCondition(
				usuario, criterioTodos, Keys.DISTRIBUCION_OUT_DIST, query, true);

		// 3.- Sumarlas
		List listaDistribucionesTotales = distribucionesEntrada
				.getDistributions();

		listaDistribucionesTotales.addAll(distribucionesSalida
				.getDistributions());

		int toIndex = criterio.getOffset().intValue()
				+ criterio.getLimit().intValue();
		if (toIndex > listaDistribucionesTotales.size()) {
			toIndex = listaDistribucionesTotales.size();
		}
		List listaDistribuciones = listaDistribucionesTotales.subList(criterio
				.getOffset().intValue(), toIndex);

		// int total =
		// distribucionesEntrada.getTotal()+distribucionesSalida.getTotal();
		int total = listaDistribucionesTotales.size();
		ResultadoBusquedaDistribucionVO distribucionesTotales = new ResultadoBusquedaDistribucionVO(
				listaDistribuciones, total);

		return distribucionesTotales;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getDistributionsByCondition(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO,
	 *      java.lang.String)
	 */
	@Override
	public ResultadoBusquedaDistribucionVO getDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query) {

		// 1.- Buscar dist entrada

		// Se indica distribución entrada, porque durante la búsqueda de la
		// condición dada nos es indiferente que sea una distribución de entrada
		// o de salida sino la condición recibida
		ResultadoBusquedaDistribucionVO distribucionesEntrada = getDistributionsByCondition(
				usuario, criterio, Keys.DISTRIBUCION_IN_DIST, query, false);

		// 2.- Obtenemos el tamaño de la consulta
		int total = distribucionesEntrada.getTotal();

		// 3.- Convertimos los datos
		ResultadoBusquedaDistribucionVO distribucionesTotales = new ResultadoBusquedaDistribucionVO(
				distribucionesEntrada.getDistributions(), total);

		return distribucionesTotales;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#createDistribution(java.lang.String,
	 *      java.lang.Integer, java.util.List, java.lang.Integer,
	 *      java.lang.Integer, java.lang.String, java.util.Locale,
	 *      java.lang.String)
	 */
	@Override
	public DistribucionVO createDistribution(UsuarioVO usuario, Integer bookId,
			Integer idRegister, Integer senderType, Integer senderId,
			Integer destinationType, Integer destinationId,
			String messageForUser) {

		DistribucionVO distribucion = null;
		logger.info("Creando una distribucion desde el origen [" + senderId
				+ "] al destino [" + destinationId
				+ "] para el id de registro: [" + idRegister + "] del libro: ["
				+ bookId + "]");
		try {
			String sessionId = usuario.getConfiguracionUsuario().getSessionID();
			Locale locale = usuario.getConfiguracionUsuario().getLocale();
			String entidad = usuario.getConfiguracionUsuario().getIdEntidad();
			List<Integer> listIdsRegister = new ArrayList<Integer>();
			listIdsRegister.add(idRegister);
			String idDistribucion = DistributionSession.createDistribution(
					sessionId, bookId, listIdsRegister, senderType, senderId,
					destinationType, destinationId, messageForUser, locale,
					entidad);

			IdentificadorRegistroVO identificador = new IdentificadorRegistroVO();
			identificador.setIdLibro(String.valueOf(bookId));
			identificador.setIdRegistro(String.valueOf(idRegister));

			BaseLibroVO libro = libroManager.getLibro(usuario, bookId);
			BaseRegistroVO registro = null;
			if (libro instanceof LibroEntradaVO) {
				registro = registroManager.findRegistroEntradaById(usuario,
						identificador);
			} else {
				registro = registroManager.findRegistroSalidaById(usuario,
						identificador);
			}

			UsuarioVO usuarioSender = new UsuarioVO();
			usuarioSender.setId(String.valueOf(senderId));
			// distribucion = this.getDistributionFromOutputBox(usuario,
			// registro, new EstadoDistribucionEnum[] {
			// EstadoDistribucionEnum.PENDIENTE});
			distribucion = this
					.getDistributionByBookRegUsers(usuario, bookId, idRegister,
							senderId, senderType, destinationId,
							destinationType,
							EstadoDistribucionEnum.PENDIENTE.getValue(),
							messageForUser);
			if (distribucion != null) {
				logger.info("Distribucion creada satisfactoriamente con id: ["
						+ distribucion.getId() + "]");
			} else {
				String messageError = "Ha ocurrido un error al crear una distribucion desde el origen ["
						+ senderId
						+ "] al destino ["
						+ destinationId
						+ "] para el id de registro: ["
						+ idRegister
						+ "] del libro: [" + bookId + "]";
				logger.error(messageError);
				throw new DistributionException(messageError);
			}
		} catch (Exception e) {
			String messageError = "Ha ocurrido un error al crear una distribucion desde el origen ["
					+ senderId
					+ "] al destino ["
					+ destinationId
					+ "] para el id de registro: ["
					+ idRegister
					+ "] del libro: [" + bookId + "]";
			logger.error(messageError, e);
			throw new DistributionException(messageError, e);
		}
		return distribucion;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getDistributionById(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      java.lang.Integer)
	 */
	@Override
	public DistribucionVO getDistributionById(UsuarioVO usuario,
			Integer distributionId) {

		DistribucionVO distribucion = null;
		String query = "ID=" + distributionId;
		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				1L, 0L);
		ResultadoBusquedaDistribucionVO resultado = this
				.getDistributionsByCondition(usuario, criterio, query);

		if (resultado.getDistributions() != null
				&& resultado.getDistributions().size() > 0) {
			distribucion = (DistribucionVO) resultado.getDistributions().get(0);
		}

		return distribucion;
	}

	private DistribucionVO getDistributionByBookRegUsers(UsuarioVO usuario,
			Integer bookId, Integer folderId, Integer idOrigen,
			Integer typeOrigen, Integer idDestino, Integer typeDestino,
			Integer state, String message) {

		DistribucionVO distribucion = null;
		DistributionResults distributionResults = null;

		StringBuffer query = new StringBuffer();
		query.append(" ID_ARCH=" + bookId);
		query.append(" AND ID_FDR=" + folderId);
		query.append(" AND TYPE_ORIG=" + typeOrigen);
		query.append(" AND ID_ORIG=" + idOrigen);
		query.append(" AND TYPE_DEST=" + typeDestino);
		query.append(" AND ID_DEST=" + idDestino);
		query.append(" AND STATE=" + state);
		query.append(" AND MESSAGE='" + message + "'");

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				1L, 0L);

		// En teoria si especificamos un usuario se obtienen las distribuciones
		// de este usuario
		criterio.setUser(String.valueOf(idOrigen));

		String entidad = usuario.getConfiguracionUsuario().getIdEntidad();

		try {
			distributionResults = DistributionSession.getDistribution(usuario
					.getConfiguracionUsuario().getSessionID(), 0, 1,
					Keys.DISTRIBUCION_OUT_DIST, query.toString(), usuario
							.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad(),
					LDAPAuthenticationPolicy.isLdap(entidad));
		} catch (Exception e) {
			logger.error("Error al buscar los usuarios", e);

		}

		List distributions = distributionResultsToDistribucionVOList(usuario,
				distributionResults);

		ResultadoBusquedaDistribucionVO resultado = new ResultadoBusquedaDistribucionVO(
				distributions, distributionResults.getTotalSize());

		if (resultado.getDistributions() != null
				&& resultado.getDistributions().size() > 0) {
			distribucion = (DistribucionVO) resultado.getDistributions().get(0);
		}

		return distribucion;
	}

	private void redistributeDistributionsManual(UsuarioVO usuario,
			ImplicadoDistribucionVO destinoDistribucion,
			List<Integer> idDistribuciones, int typeDist, String matter)
			throws ValidationException,
			com.ieci.tecdoc.common.exception.DistributionException,
			SessionException, BookException {
		String sessionID = usuario.getConfiguracionUsuario().getSessionID();
		Locale locale = usuario.getConfiguracionUsuario().getLocale();
		String entidad = usuario.getConfiguracionUsuario().getIdEntidad();
		Integer canDestWithoutList = new Integer(BookSession.invesicresConf(
				entidad).getCanChangeDestWithoutList());

		Integer userId = Integer.valueOf(destinoDistribucion.getId());
		Integer userType = Integer.valueOf(destinoDistribucion.getTipo());

		DistributionSession.redistributionDistribution(sessionID, locale,
				entidad, idDistribuciones, userId, typeDist,
				canDestWithoutList, matter, userType, usuario.isLdapUser());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#redistributeDistributionManual(java.lang.String,
	 *      java.util.Locale, java.lang.String, java.util.List,
	 *      java.lang.Integer, int, java.lang.Integer, java.lang.String,
	 *      java.lang.Integer)
	 */
	@Override
	public void redistributeOutputDistributionsManual(UsuarioVO usuario,
			String numRegistro, ImplicadoDistribucionVO destinoDistribucion,
			String matter) {

		List<DistribucionVO> distribuciones = getDistributionsFromOutputBox(
				usuario,
				numRegistro,
				new EstadoDistribucionEnum[] { EstadoDistribucionEnum.RECHAZADO });
		List<Integer> dis = new ArrayList<Integer>();

		// 1.- Obtener las distribuciones del registro

		if (CollectionUtils.isNotEmpty(distribuciones)) {

			// 2.- Añadir los ids de las distribuciones a la lista para
			// generar la redistribucion
			Iterator iterDistribuciones = distribuciones.iterator();
			try {

				while (iterDistribuciones.hasNext()) {
					DistribucionVO distribucion = (DistribucionVO) iterDistribuciones
							.next();
					dis.add(Integer.valueOf(distribucion.getId()));
				}
				redistributeDistributionsManual(usuario, destinoDistribucion,
						dis, Keys.DISTRIBUCION_OUT_DIST, matter);
			} catch (Exception e) {
				String messageEx = "Error al redistribuir al distribucion manual";
				logger.error(messageEx, e);
				throw new DistributionException(messageEx, e);
			}

		} else {
			StringBuffer message = new StringBuffer(
					"No existe una distribucion en la bandeja de salida del usuario [")
					.append(usuario.getLoginName())
					.append("] para el numero de registro [")
					.append(numRegistro)
					.append("] en ninguno de los estados [RECHAZADO]");

			logger.warn(message.toString());

			throw new UnknownDistributionException(message.toString());
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#redistributeInputDistributionManual(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      java.lang.Integer,
	 *      es.ieci.tecdoc.isicres.api.business.vo.ImplicadoDistribucionVO)
	 */
	@Override
	public void redistributeInputDistributionsManual(UsuarioVO usuario,
			String numRegistro, ImplicadoDistribucionVO destinoDistribucion,
			String matter) {

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO(
				Long.valueOf(Integer.MAX_VALUE), 0L);

		// Las distribuciones de entrada tienen que tener el estado ACEPTADO O
		// PENDIENTE

		List<Integer> dis = new ArrayList<Integer>();

		// 1.- Obtener las distribuciones del registro

		List<DistribucionVO> distribuciones = getDistributionsFromInputBox(
				usuario, numRegistro, new EstadoDistribucionEnum[] {
						EstadoDistribucionEnum.PENDIENTE,
						EstadoDistribucionEnum.ACEPTADO });

		if (CollectionUtils.isNotEmpty(distribuciones)) {

			// 2.- Añadir los ids de las distribuciones a la lista para
			// generar la redistribucion
			Iterator iterDistribuciones = distribuciones.iterator();
			try {
				while (iterDistribuciones.hasNext()) {
					DistribucionVO distribucion = (DistribucionVO) iterDistribuciones
							.next();
					dis.add(Integer.valueOf(distribucion.getId()));
				}
				redistributeDistributionsManual(usuario, destinoDistribucion,
						dis, Keys.DISTRIBUCION_IN_DIST, matter);
			} catch (Exception e) {
				String messageEx = "Error al redistribuir al distribucion manual";
				logger.error(messageEx, e);
				throw new DistributionException(messageEx, e);
			}
		}

		else {
			StringBuffer message = new StringBuffer(
					"No existe una distribucion en la bandeja de entrada del usuario [")
					.append(usuario.getLoginName())
					.append("] para el numero de registro [")
					.append(numRegistro)
					.append("] en ninguno de los estados [ACEPTADO, PENDIENTE]");
			logger.warn(message.toString());

			throw new UnknownDistributionException(message.toString());
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getDistributionsByRegistry(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      java.lang.Integer)
	 */
	@Override
	public ResultadoBusquedaDistribucionVO getDistributionsByRegistry(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			Integer registryId, int tipo) {

		String query = "ID_FDR=" + registryId;

		// 1.- Buscar dist entrada
		ResultadoBusquedaDistribucionVO distribuciones = getDistributionsByCondition(
				usuario, criterio, tipo, query, false);

		return distribuciones;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#deleteDistribution(net.sf.hibernate.Session,
	 *      java.lang.Integer)
	 */
	@Override
	public void deleteDistribution(String entidad, Integer distributionId) {
		try {
			DistributionSession.deleteDistribution(entidad, distributionId);
		} catch (HibernateException e) {
			String message = "Error al eliminar la distribución ["
					+ distributionId + "]";
			logger.error(message, e);
			throw new DistributionException(message, e);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getUserInputDistributionsByCondition(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO,
	 *      java.lang.String)
	 */
	@Override
	public ResultadoBusquedaDistribucionVO getUserInputDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query) {

		// 1.- Buscar dist entrada
		ResultadoBusquedaDistribucionVO distribucionesEntrada = getDistributionsByCondition(
				usuario, criterio, Keys.DISTRIBUCION_IN_DIST, query, true);

		// 2.- Obtenemos el tamaño de la consulta
		int total = distribucionesEntrada.getTotal();

		// 3.- Convertimos los datos
		ResultadoBusquedaDistribucionVO distribucionesTotales = new ResultadoBusquedaDistribucionVO(
				distribucionesEntrada.getDistributions(), total);

		return distribucionesTotales;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getUserOutputDistributionsByCondition(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO,
	 *      java.lang.String)
	 */
	@Override
	public ResultadoBusquedaDistribucionVO getUserOutputDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query) {

		// 1.- Buscar dist entrada
		ResultadoBusquedaDistribucionVO distribucionesEntrada = getDistributionsByCondition(
				usuario, criterio, Keys.DISTRIBUCION_OUT_DIST, query, true);

		// 2.- Obtenemos el tamaño de la consulta
		int total = distribucionesEntrada.getTotal();

		// 3.- Convertimos los datos
		ResultadoBusquedaDistribucionVO distribucionesTotales = new ResultadoBusquedaDistribucionVO(
				distribucionesEntrada.getDistributions(), total);

		return distribucionesTotales;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getUsuarios(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	@Override
	public List<BaseUsuarioVO> getUsuariosLdapExceptoActual(BaseUsuarioVO usuario) {

		List<BaseUsuarioVO> results = new LinkedList<BaseUsuarioVO>();

		List<BaseUsuarioVO> usuarios = usuarioManager.getUsuarios();

		for (BaseUsuarioVO usuarioVO : usuarios) {

			if (!usuarioVO.getId().equalsIgnoreCase(usuario.getId())){
				results.add(usuarioVO);
			}
		}

		return results;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getGruposPertenecientesUsuario(es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO)
	 */
	public List<GrupoUsuarioVO> getGruposPertenecientesUsuario(
			Integer idUsuario){
		return grupoManager.getGruposPertenecientesUsuario(idUsuario);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getGruposNoPertenecientesUsuario(es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO)
	 */
	public List<GrupoUsuarioVO> getGruposNoPertenecientesUsuario(
			Integer idUsuario){
		return grupoManager.getGruposNoPertenecientesUsuario(idUsuario);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getOficinas(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public List<BaseDepartamentoVO> getDepartamentosExceptoActual(
			Integer idDepartamento) {
		List<BaseDepartamentoVO> results = new LinkedList<BaseDepartamentoVO>();
		List<BaseDepartamentoVO> departamentos = departamentoManager
				.getDepartamentos();

		for (BaseDepartamentoVO baseDepartamentoVO : departamentos) {

			if (!baseDepartamentoVO.getId().equalsIgnoreCase(String.valueOf(idDepartamento))){
				results.add(baseDepartamentoVO);
			}
		}

		return results;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getDepartamentos()
	 */
	public List<BaseDepartamentoVO> getDepartamentos(){
		return departamentoManager.getDepartamentos();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager#getDepartamentosGrupoLdap(java.lang.Integer)
	 */
	@Override
	public List<BaseDepartamentoVO> getDepartamentosGrupoLdap(
			Integer idGrupoLdap) {

		return departamentoManager.getDepartamentosGrupoLdap(idGrupoLdap);
	}

	public GrupoManager getGrupoManager() {
		return grupoManager;
	}

	public void setGrupoManager(GrupoManager grupoManager) {
		this.grupoManager = grupoManager;
	}

	public OficinaManager getOficinaManager() {
		return oficinaManager;
	}

	public void setOficinaManager(OficinaManager oficinaManager) {
		this.oficinaManager = oficinaManager;
	}

	public UsuarioManager getUsuarioManager() {
		return usuarioManager;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}

	public DepartamentoManager getDepartamentoManager() {
		return departamentoManager;
	}

	public void setDepartamentoManager(DepartamentoManager departamentoManager) {
		this.departamentoManager = departamentoManager;
	}



}

/**
 * Clase que encapsula la lógica necesaria para suplantar la identidad del
 * usuario autenticado por otro usuario para recuperar las distribuciones de
 * este último sin estar autenticado.
 *
 */
final class AuthenticationSwitcher {

	public AuthenticationSwitcher(String sessionId) {
		try {
			this.cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionId);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void impersonateAuthenticatedUser(AuthenticationUser manInTheMiddle) {

		this.authenticationUser = (AuthenticationUser) cacheBag
				.get(HibernateKeys.HIBERNATE_Iuseruserhdr);

		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer(
					"Se va a suplantar la identidad del usuario [")
					.append(this.authenticationUser.getName())
					.append("] por la del usuario [")
					.append(manInTheMiddle.getName()).append("]");
			logger.debug(sb.toString());
		}

		this.cacheBag.put(HibernateKeys.HIBERNATE_Iuseruserhdr, manInTheMiddle);
	}

	public void restoreAuthenticatedUser() {
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer(
					"Se restaura la identidad del usuario [").append(
					this.authenticationUser.getName()).append("]");
			logger.debug(sb.toString());
		}
		this.cacheBag.put(HibernateKeys.HIBERNATE_Iuseruserhdr,
				this.authenticationUser);
	}

	protected AuthenticationUser authenticationUser;

	protected CacheBag cacheBag;

	protected static final Logger logger = Logger
			.getLogger(AuthenticationSwitcher.class);
}
