package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.entity.AxDochEntity;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.helper.DocumentosHelper;
import es.ieci.tecdoc.isicres.api.business.helper.DocumentosValidacionHelper;
import es.ieci.tecdoc.isicres.api.business.helper.LibroHelper;
import es.ieci.tecdoc.isicres.api.business.helper.RegistroHelper;
import es.ieci.tecdoc.isicres.api.business.helper.RegistroValidacionHelper;
import es.ieci.tecdoc.isicres.api.business.helper.TercerosHelper;
import es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.PermisosManager;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.manager.impl.builder.BaseRegistroVOBuilder;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.ListOfAxDochToListOfDocumentoRegistroVOMapper;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.ListOfCampoGenericoRegistroVOToListOfFlushFdrFieldMapper;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroOriginalVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoRegistroEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import gnu.trove.THashMap;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class RegistroManagerImpl extends RegistroManager {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#createRegistroEntrada(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO)
	 */
	public final RegistroEntradaVO createRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaVO registro) {

		// 2.- Abrir Libro
		LibroEntradaVO libroEntrada = (LibroEntradaVO) LibroHelper
				.obtenerDatosBasicosLibro(registro, new LibroEntradaVO());

		libroEntrada = (LibroEntradaVO) libroManager.abrirLibro(usuario,
				libroEntrada, TipoLibroEnum.ENTRADA);

		// 3.- Comprobar permisos y validez de los datos
		Boolean canCreateRegistro = canCreateRegistro(usuario, registro);
		if (canCreateRegistro.booleanValue()) {
			registro.getOficinaRegistro().setCodigoOficina(
					usuario.getConfiguracionUsuario().getOficina()
							.getCodigoOficina());
			registro.getOficinaRegistro().setId(
					usuario.getConfiguracionUsuario().getOficina().getId());

			List listaErrores = RegistroValidacionHelper
					.validateRegistroEntradaCreate(usuario, registro);

			if ((listaErrores) != null && (!listaErrores.isEmpty())) {
				String mensaje = RegistroValidacionHelper
						.getMensajeErrorListaCampos(listaErrores);
				logger.warn("Error de Validacion. Los siguientes campos no son correctos:\n"
						+ mensaje);
				throw new RegistroException(
						"Error de Validacion. Los siguientes campos no son correctos:\n"
								+ mensaje);
			}

			// 4.- Validamos los Documentos
			DocumentosValidacionHelper.validateDocuments(registro
					.getDocumentos());

			// 5.- Sustituir datos validados
			Map translatedIds = RegistroHelper
					.obtenerCamposValidadosRegistroEntrada(usuario, registro);

			// 6.- Completar Datos
			AxSf axSfNew = RegistroHelper.completarRegistroAxSf(new AxSfIn(),
					usuario, registro, translatedIds);

			try {
				preCreateRegistroEntrada(usuario, registro);

				// 7.- Crear Registro
				registro = (RegistroEntradaVO) RegistroHelper.createRegistro(
						usuario, registro, axSfNew);
			} catch (Exception e) {
				createRegistroEntradaCatchException(usuario, registro, e);
			} finally {
				postCreateRegistroEntrada(usuario, registro);
			}
		} else {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos de creación para el libro [")
					.append(registro.getIdLibro()).append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}

			throw new RegistroException(sb.toString());
		}

		return registro;
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void postCreateRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaVO registro) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 * @param e
	 */
	public void createRegistroEntradaCatchException(UsuarioVO usuario,
			RegistroEntradaVO registro, Exception e) {

		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido crear el registro en el libro [")
				.append(registro.getIdLibro()).append("]");
		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void preCreateRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaVO registro) {
		// nothing to do
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#createRegistroSalida(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO)
	 */
	public final RegistroSalidaVO createRegistroSalida(UsuarioVO usuario,
			RegistroSalidaVO registro) {

		// 2.- Abrir Libro
		LibroSalidaVO libroSalida = (LibroSalidaVO) LibroHelper
				.obtenerDatosBasicosLibro(registro, new LibroSalidaVO());

		libroSalida = (LibroSalidaVO) libroManager.abrirLibro(usuario,
				libroSalida, TipoLibroEnum.SALIDA);

		// 3.- Comprobar permisos y valided de los datos
		Boolean canCreateRegistro = canCreateRegistro(usuario, registro);
		if (canCreateRegistro.booleanValue()) {
			registro.getOficinaRegistro().setCodigoOficina(
					usuario.getConfiguracionUsuario().getOficina()
							.getCodigoOficina());
			registro.getOficinaRegistro().setId(
					usuario.getConfiguracionUsuario().getOficina().getId());

			List listaErrores = RegistroValidacionHelper
					.validateRegistroSalidaCreate(usuario, registro);

			if ((listaErrores) != null && (!listaErrores.isEmpty())) {
				String mensaje = RegistroValidacionHelper
						.getMensajeErrorListaCampos(listaErrores);
				logger.warn("Error de Validacion. Los siguientes campos no son correctos:\n"
						+ mensaje);
				throw new RegistroException(
						"Error de Validacion. Los siguientes campos no son correctos:\n"
								+ mensaje);
			}

			// 4.- Validamos los Documentos
			DocumentosValidacionHelper.validateDocuments(registro
					.getDocumentos());

			// 5.- Sustituir datos validados
			Map translatedIds = RegistroHelper
					.obtenerCamposValidadosRegistroSalida(usuario, registro);

			// 6.- Completar Datos
			AxSf axSfNew = RegistroHelper.completarRegistroAxSf(new AxSfOut(),
					usuario, registro, translatedIds);

			try {
				preCreateRegistroSalida(usuario, registro);

				// 7.- Crear Registro
				registro = (RegistroSalidaVO) RegistroHelper.createRegistro(
						usuario, registro, axSfNew);
			} catch (Exception e) {
				createRegistroSalidaCatchException(usuario, registro, e);
			} finally {
				postCreateRegistroSalida(usuario, registro);
			}
		} else {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos de creación para el libro [")
					.append(registro.getIdLibro()).append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}

			throw new RegistroException(sb.toString());
		}

		return registro;
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void postCreateRegistroSalida(UsuarioVO usuario,
			RegistroSalidaVO registro) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 * @param e
	 */
	public void createRegistroSalidaCatchException(UsuarioVO usuario,
			RegistroSalidaVO registro, Exception e) {

		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido crear el registro en el libro [")
				.append(registro.getIdLibro()).append("]");
		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void preCreateRegistroSalida(UsuarioVO usuario,
			RegistroSalidaVO registro) {
		// nothing to do
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#importRegistroEntrada(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO)
	 */
	public final RegistroEntradaVO importRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaExternoVO registro) {

		// 2.- Abrir Libro
		LibroEntradaVO libroEntrada = (LibroEntradaVO) LibroHelper
				.obtenerDatosBasicosLibro(registro, new LibroEntradaVO());

		libroEntrada = (LibroEntradaVO) libroManager.abrirLibro(usuario,
				libroEntrada, TipoLibroEnum.ENTRADA);

		// 3.- Comprobar permisos y validez de los datos
		Boolean canCreateRegistro = canCreateRegistro(usuario, registro);
		if (canCreateRegistro.booleanValue()) {
			List listaErrores = RegistroValidacionHelper
					.validateRegistroEntradaImport(usuario, registro);

			if (!CollectionUtils.isEmpty(listaErrores)) {
				String mensaje = RegistroValidacionHelper
						.getMensajeErrorListaCampos(listaErrores);
				logger.warn("Error de Validacion. Los siguientes campos no son correctos:\n"
						+ mensaje);
				throw new RegistroException(
						"Error de Validacion. Los siguientes campos no son correctos:\n"
								+ mensaje);
			}

			// 4.- Validamos los Documentos
			DocumentosValidacionHelper.validateDocuments(registro
					.getDocumentos());

			// 5.- Sustituir datos validados
			Map translatedIds = RegistroHelper
					.obtenerCamposValidadosRegistroEntrada(usuario, registro);

			// 6.- Completar Datos
			AxSf axSfNew = RegistroHelper.completarRegistroAxSf(new AxSfIn(),
					usuario, registro, translatedIds);

			try {
				preImportRegistroEntrada(usuario, registro);

				// 7.- Crear Registro
				registro = (RegistroEntradaExternoVO) RegistroHelper
						.createRegistro(usuario, registro, axSfNew);
			} catch (Exception e) {
				importRegistroEntradaCatchException(usuario, registro, e);
			} finally {
				postImportRegistroEntrada(usuario, registro);
			}

		} else {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos de creación para el libro [")
					.append(registro.getIdLibro()).append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}

			throw new RegistroException(sb.toString());
		}

		return registro;
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void postImportRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaExternoVO registro) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 * @param e
	 */
	public void importRegistroEntradaCatchException(UsuarioVO usuario,
			RegistroEntradaExternoVO registro, Exception e) {

		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido importar el registro en el libro [")
				.append(registro.getIdLibro()).append("]");

		logger.error(sb.toString());

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void preImportRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaExternoVO registro) {
		// nothing to do
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#importRegistroSalida(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaExternoVO)
	 */
	public final RegistroSalidaVO importRegistroSalida(UsuarioVO usuario,
			RegistroSalidaExternoVO registro) {

		// 2.- Abrir Libro
		LibroEntradaVO libroEntrada = (LibroEntradaVO) LibroHelper
				.obtenerDatosBasicosLibro(registro, new LibroEntradaVO());

		libroEntrada = (LibroEntradaVO) libroManager.abrirLibro(usuario,
				libroEntrada, TipoLibroEnum.SALIDA);

		// 3.- Comprobar permisos y validez de los datos
		Boolean canCreateRegistro = canCreateRegistro(usuario, registro);
		if (canCreateRegistro.booleanValue()) {
			List listaErrores = RegistroValidacionHelper
					.validateRegistroSalidaImport(usuario, registro);

			if (!CollectionUtils.isEmpty(listaErrores)) {
				String mensaje = RegistroValidacionHelper
						.getMensajeErrorListaCampos(listaErrores);
				logger.error("Error de Validacion. Los siguientes campos no son correctos:\n"
						+ mensaje);
				throw new RegistroException(
						"Error de Validacion. Los siguientes campos no son correctos:\n"
								+ mensaje);
			}

			// 4.- Validamos los Documentos
			DocumentosValidacionHelper.validateDocuments(registro
					.getDocumentos());

			// 5.- Sustituir datos validados
			Map translatedIds = RegistroHelper
					.obtenerCamposValidadosRegistroSalida(usuario, registro);

			// 6.- Completar Datos
			AxSf axSfNew = RegistroHelper.completarRegistroAxSf(new AxSfOut(),
					usuario, registro, translatedIds);

			try {
				preImportRegistroSalida(usuario, registro);

				// 7.- Crear Registro
				registro = (RegistroSalidaExternoVO) RegistroHelper
						.createRegistro(usuario, registro, axSfNew);
			} catch (Exception e) {
				importRegistroSalidaCatchException(usuario, registro, e);
			} finally {
				postImportRegistroSalida(usuario, registro);
			}

		} else {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append("] no tiene permisos de creación para el libro [")
					.append(registro.getIdLibro()).append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}

			throw new RegistroException(sb.toString());
		}

		return registro;
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void postImportRegistroSalida(UsuarioVO usuario,
			RegistroSalidaExternoVO registro) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 * @param e
	 */
	public void importRegistroSalidaCatchException(UsuarioVO usuario,
			RegistroSalidaExternoVO registro, Exception e) {

		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido importar el registro en el libro [")
				.append(registro.getIdLibro()).append("]");
		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param registro
	 */
	public void preImportRegistroSalida(UsuarioVO usuario,
			RegistroSalidaExternoVO registro) {
		// nothing to do
	}

	/**
	 *
	 */
	public final void cancelRegistroEntradaById(UsuarioVO usuario,
			IdentificadorRegistroVO id) {

		// Asertar que el registro es de entrada
		Assert.notNull(findRegistroEntradaById(usuario, id),
				"El registro con identificador [" + id.getIdRegistro()
						+ "] no es de entrada.");

		try {
			preCancelRegistroEntrada(usuario, id);

			cancelRegistroById(usuario, id);
		} catch (Exception e) {
			cancelRegistroEntradaCatchException(usuario, id, e);
		} finally {
			postCancelRegistroEntrada(usuario, id);
		}
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 */
	public void postCancelRegistroEntrada(UsuarioVO usuario,
			IdentificadorRegistroVO id) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 * @param e
	 */
	public void cancelRegistroEntradaCatchException(UsuarioVO usuario,
			IdentificadorRegistroVO id, Exception e) {

		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido cancelar el registro de entrada con id [")
				.append(id.getIdRegistro()).append("] en el libro [")
				.append(id.getIdLibro()).append("]");

		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 */
	public void preCancelRegistroEntrada(UsuarioVO usuario,
			IdentificadorRegistroVO id) {
		// nothing to do
	}

	/**
	 *
	 */
	public final void cancelRegistroSalidaById(UsuarioVO usuario,
			IdentificadorRegistroVO id) {

		// Asertar que el registro es de salida
		Assert.notNull(findRegistroSalidaById(usuario, id),
				"El registro con identificador [" + id.getIdRegistro()
						+ "] no es de salida.");

		try {
			preCancelRegistroSalida(usuario, id);

			cancelRegistroById(usuario, id);
		} catch (Exception e) {
			cancelRegistroSalidaCatchException(usuario, id, e);
		} finally {
			postCancelRegistroSalida(usuario, id);
		}
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 */
	public void postCancelRegistroSalida(UsuarioVO usuario,
			IdentificadorRegistroVO id) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 * @param e
	 */
	public void cancelRegistroSalidaCatchException(UsuarioVO usuario,
			IdentificadorRegistroVO id, Exception e) {
		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido cancelar el registro de salida con id [")
				.append(id.getIdRegistro()).append("] en el libro [")
				.append(id.getIdLibro()).append("]");

		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 */
	public void preCancelRegistroSalida(UsuarioVO usuario,
			IdentificadorRegistroVO id) {
		// nothing to do
	}

	/**
	 * Devuelve los registros de entrada del libro <code>libro</code> que
	 * cumplen con el criterio de búsqueda <code>criterioBusqueda</code> para el
	 * usuario <code>usuario</code>. Junto con los registros encontrados
	 * devuelve el número total existente sin tener en cuenta las restricciones
	 * de paginación.
	 *
	 * @see ResultadoBusquedaRegistroVO
	 */
	public ResultadoBusquedaRegistroVO findAllRegistroEntradaByCriterioWhereSql(
			UsuarioVO usuario, LibroEntradaVO libro,
			CriterioBusquedaRegistroSqlVO criterioBusqueda) {

		List axSfQueryResults = queryForAxSfQueryResults(usuario,
				criterioBusqueda.getOffset().intValue(), criterioBusqueda
						.getLimit().intValue(), Integer.valueOf(libro.getId()),
				TipoLibroEnum.ENTRADA, criterioBusqueda.getSql());

		int total = queryForAxSfQueryResultsTotalCount(axSfQueryResults);

		List registers = new ArrayList();
		for (Iterator iterator = axSfQueryResults.iterator(); iterator
				.hasNext();) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();

			Iterator axSfIterator = results.getResults().iterator();
			while (axSfIterator.hasNext()) {

				AxSf axSf = (AxSf) axSfIterator.next();
				RegistroEntradaVO result = mapToRegistroEntradaVO(usuario,
						results.getBookId(), axSf);
				registers.add(result);
			}
		}

		return new ResultadoBusquedaRegistroVO(registers, total);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#findAllRegistroEntradaForUserByCriterioWhereSql(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO)
	 */
	@Override
	public ResultadoBusquedaRegistroVO findAllRegistroEntradaForUserByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda) {
		List axSfQueryResults = queryForAxSfQueryResults(usuario,
				criterioBusqueda.getOffset().intValue(), criterioBusqueda
						.getLimit().intValue(), null, TipoLibroEnum.ENTRADA,
				criterioBusqueda.getSql(), true);

		int total = queryForAxSfQueryResultsTotalCount(axSfQueryResults);

		List registers = new ArrayList();
		for (Iterator iterator = axSfQueryResults.iterator(); iterator
				.hasNext();) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();

			Iterator axSfIterator = results.getResults().iterator();
			while (axSfIterator.hasNext()) {
				AxSf axSf = (AxSf) axSfIterator.next();

				RegistroEntradaVO result = mapToRegistroEntradaVO(usuario,
						results.getBookId(), axSf);
				registers.add(result);
			}
		}

		return new ResultadoBusquedaRegistroVO(registers, total);
	}

	/**
	 *
	 * Mapea un objeto axSf a un registro de entrada completo.
	 *
	 * @param usuario
	 *            Usuario
	 * @param bookId
	 *            Identificador del libro
	 * @param axSf
	 *            Objeto axSf que se mapea
	 * @return El registro de entrada equivalente
	 */
	private RegistroEntradaVO mapToRegistroEntradaVO(UsuarioVO usuario,
			int bookId, AxSf axSf) {
		RegistroEntradaVO result = new RegistroEntradaVO();

		// obtenemos la informacion de BaseRegistroVO
		BaseRegistroVO baseRegistroVO = getBaseRegistroVOBuilder().build(
				usuario, axSf, String.valueOf(bookId), new RegistroEntradaVO());

		// copiamos todas las propiedades en comun del BaseRegistroVO al
		// resultado
		BeanUtils.copyProperties(baseRegistroVO, result);

		// Referencia Expediente
		result.setReferenciaExpediente(axSf
				.getAttributeValueAsString(AxSf.FLD19_FIELD));

		// Registro Original
		RegistroOriginalVO registroOriginal = new RegistroOriginalVO();

		// *EntidadRegistral
		UnidadAdministrativaVO entidadRegistral = null;
		String idEntidadRegistral = axSf
				.getAttributeValueAsString(AxSf.FLD13_FIELD);
		if (StringUtils.isNotBlank(idEntidadRegistral)) {
			entidadRegistral = getUnidadAdministrativaManager().findUnidadById(
					usuario.getConfiguracionUsuario().getLocale(),
					new Integer(idEntidadRegistral));
		}
		registroOriginal.setEntidadRegistral(entidadRegistral);

		// *Fecha del registro original
		registroOriginal.setFechaRegistroOriginal((Date) axSf
				.getAttributeValue(AxSf.FLD12_FIELD));
		// *Numero del registro original
		registroOriginal.setNumeroRegistroOriginal(axSf
				.getAttributeValueAsString(AxSf.FLD10_FIELD));
		// *Tipo de registro original
		registroOriginal.setTipoRegistroOriginal(axSf
				.getAttributeValueAsString(AxSf.FLD11_FIELD));

		result.setRegistroOriginal(registroOriginal);
		return result;
	}

	/**
	 *
	 */
	public RegistroEntradaVO findRegistroEntradaById(UsuarioVO usuario,
			IdentificadorRegistroVO id) {

		RegistroEntradaVO result = new RegistroEntradaVO();
		result.setIdLibro(id.getIdLibro());

		try {
			// - Abrir Libro
			LibroEntradaVO libroEntrada = (LibroEntradaVO) LibroHelper
					.obtenerDatosBasicosLibro(result, new LibroEntradaVO());

			libroEntrada = (LibroEntradaVO) getLibroManager().abrirLibro(
					usuario, libroEntrada, TipoLibroEnum.ENTRADA);

			// - Obtenemos la informacion del registro
			AxSf axSf = FolderSession.getBookFolder(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue(), usuario
							.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad());

			if (axSf != null) {
				result = mapToRegistroEntradaVO(usuario,
						Integer.valueOf(id.getIdLibro()), axSf);
			}
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Error al obtener el registro con id [").append(
					id.getIdRegistro()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(
					"Error al Obtener el registro de Entrada por Id: ["
							+ id.getIdRegistro() + "] ", e);
		}

		return result;
	}

	/**
	 *
	 */
	public ResultadoBusquedaRegistroVO findAllRegistroSalidaByCriterioWhereSql(
			UsuarioVO usuario, LibroSalidaVO libro,
			CriterioBusquedaRegistroSqlVO criterioBusqueda) {

		List axSfQueryResults = queryForAxSfQueryResults(usuario,
				criterioBusqueda.getOffset().intValue(), criterioBusqueda
						.getLimit().intValue(), Integer.valueOf(libro.getId()),
				TipoLibroEnum.SALIDA, criterioBusqueda.getSql());

		int total = queryForAxSfQueryResultsTotalCount(axSfQueryResults);

		List registers = new ArrayList();
		for (Iterator iterator = axSfQueryResults.iterator(); iterator
				.hasNext();) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();

			Iterator axSfIterator = results.getResults().iterator();
			while (axSfIterator.hasNext()) {

				AxSf axSf = (AxSf) axSfIterator.next();
				registers.add(getBaseRegistroVOBuilder().build(usuario, axSf,
						String.valueOf(results.getBookId()),
						new RegistroSalidaVO()));
			}
		}

		return new ResultadoBusquedaRegistroVO(registers, total);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#findAllRegistroSalidaForUserByCriterioWhereSql(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO)
	 */
	@Override
	public ResultadoBusquedaRegistroVO findAllRegistroSalidaForUserByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda) {
		List axSfQueryResults = queryForAxSfQueryResults(usuario,
				criterioBusqueda.getOffset().intValue(), criterioBusqueda
						.getLimit().intValue(), null, TipoLibroEnum.SALIDA,
				criterioBusqueda.getSql(), true);

		int total = queryForAxSfQueryResultsTotalCount(axSfQueryResults);

		List registers = new ArrayList();
		for (Iterator iterator = axSfQueryResults.iterator(); iterator
				.hasNext();) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();

			Iterator axSfIterator = results.getResults().iterator();
			while (axSfIterator.hasNext()) {

				AxSf axSf = (AxSf) axSfIterator.next();
				registers.add(getBaseRegistroVOBuilder().build(usuario, axSf,
						String.valueOf(results.getBookId()),
						new RegistroSalidaVO()));
			}
		}

		return new ResultadoBusquedaRegistroVO(registers, total);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.api.business.manager.RegistroManager#
	 * findRegistroSalidaById(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO)
	 */
	public RegistroSalidaVO findRegistroSalidaById(UsuarioVO usuario,
			IdentificadorRegistroVO id) {
		RegistroSalidaVO result = new RegistroSalidaVO();
		result.setIdLibro(id.getIdLibro());

		try {
			// - Abrir Libro
			LibroSalidaVO libroSalida = (LibroSalidaVO) LibroHelper
					.obtenerDatosBasicosLibro(result, new LibroSalidaVO());

			libroSalida = (LibroSalidaVO) getLibroManager().abrirLibro(usuario,
					libroSalida, TipoLibroEnum.SALIDA);

			// - Obtenemos la informacion del registro
			AxSf axSf = FolderSession.getBookFolder(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue(), usuario
							.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad());

			Assert.isTrue(axSf instanceof AxSfOut,
					"El registro con identificador [" + id.getIdRegistro()
							+ "] no es de salida");

			if (axSf != null) {

				// obtenemos la informacion de BaseRegistroVO
				BaseRegistroVO baseRegistroVO = getBaseRegistroVOBuilder()
						.build(usuario, axSf, id.getIdLibro(),
								new RegistroSalidaVO());

				// copiamos todas las propiedades en comun del BaseRegistroVO al
				// resultado
				BeanUtils.copyProperties(baseRegistroVO, result);

			}
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Error al obtener el registro con id [").append(
					id.getIdRegistro()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(
					"Error al Obtener el registro de Salida por Id: ["
							+ id.getIdRegistro() + "] ", e);
		}

		return result;
	}

	/**
	 *
	 */
	public void lockRegistro(UsuarioVO usuario, IdentificadorRegistroVO id) {

		CacheBag cacheBag;
		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateUtil.currentSession(usuario
					.getConfiguracionUsuario().getIdEntidad());
			tran = session.beginTransaction();

			cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					usuario.getConfiguracionUsuario().getSessionID());

			ScrOfic scrOfic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);

			if (FolderSession.lock(session, Integer.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue(), user,
					scrOfic, usuario.getConfiguracionUsuario().getIdEntidad())) {
			} else {
				// No se ha podido bloquear el registro
				throw new RegistroException(
						"No se ha podido bloquear el registro ["
								+ id.getIdRegistro() + "]");
			}

			HibernateUtil.commitTransaction(tran);

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);

			StringBuffer sb = new StringBuffer(
					"No se ha podido bloquear el registro con id [")
					.append(id.getIdRegistro()).append("] para el libro [")
					.append(id.getIdLibro()).append("] para el usuario [")
					.append(usuario.getLoginName()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		} finally {
			HibernateUtil.closeSession(usuario.getConfiguracionUsuario()
					.getIdEntidad());
		}
	}

	/**
	 *
	 */
	public void unlockRegistro(UsuarioVO usuario, IdentificadorRegistroVO id) {

		AuthenticationUser au = new AuthenticationUser();
		au.setId(Integer.valueOf(usuario.getId()));

		Session session;
		Transaction tran = null;

		try {
			session = HibernateUtil.currentSession(usuario
					.getConfiguracionUsuario().getIdEntidad());
			tran = session.beginTransaction();

			FolderSession.unlock(session, Integer.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue(), au);

			HibernateUtil.commitTransaction(tran);
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);

			StringBuffer sb = new StringBuffer(
					"No se ha podido desbloquear el registro con id [")
					.append(id.getIdRegistro()).append("] para el libro [")
					.append(id.getIdLibro()).append("] para el usuario [")
					.append(usuario.getLoginName()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		} finally {
			HibernateUtil.closeSession(usuario.getConfiguracionUsuario()
					.getIdEntidad());
		}
	}

	/**
	 *
	 */
	public final void updateRegistroEntrada(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos) {

		Assert.notNull(findRegistroEntradaById(usuario, id),
				"El registro con identificador [" + id.getIdRegistro()
						+ "] no es de entrada.");

		try {
			preUpdateRegistroEntrada(usuario, id, camposGenericos);

			updateRegistro(usuario, id, camposGenericos, false);
		} catch (Exception e) {
			updateRegistroEntradaCatchException(usuario, id, camposGenericos, e);
		} finally {
			postUpdateRegistroEntrada(usuario, id, camposGenericos);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#updateRegistroEntradaIR(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO, es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO, java.util.List)
	 */
	@Override
	public void updateRegistroEntradaIR(UsuarioVO usuario,
			IdentificadorRegistroVO id,
			List<CampoGenericoRegistroVO> camposGenericos) {
		Assert.notNull(findRegistroEntradaById(usuario, id),
				"El registro con identificador [" + id.getIdRegistro()
						+ "] no es de entrada.");

		try {
			preUpdateRegistroEntrada(usuario, id, camposGenericos);

			updateRegistro(usuario, id, camposGenericos, true);
		} catch (Exception e) {
			updateRegistroEntradaCatchException(usuario, id, camposGenericos, e);
		} finally {
			postUpdateRegistroEntrada(usuario, id, camposGenericos);
		}

	}



	/**
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 */
	public void postUpdateRegistroEntrada(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 * @param e
	 */
	public void updateRegistroEntradaCatchException(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos, Exception e) {
		// nothing to do
		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido actualizar el registro de entrada con id [")
				.append(id.getIdRegistro()).append("] en el libro [")
				.append(id.getIdLibro()).append("]");

		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 */
	public void preUpdateRegistroEntrada(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos) {
		// nothing to do
	}

	/**
	 * Actualiza el registro de salida con identificador <code>id</code>
	 */
	public final void updateRegistroSalida(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos) {

		Assert.notNull(findRegistroSalidaById(usuario, id),
				"El registro con identificador [" + id.getIdRegistro()
						+ "] no es de salida.");

		try {
			preUpdateRegistroSalida(usuario, id, camposGenericos);

			updateRegistro(usuario, id, camposGenericos, false);
		} catch (Exception e) {
			updateRegistroSalidaCatchException(usuario, id, camposGenericos, e);
		} finally {
			postUpdateRegistroSalida(usuario, id, camposGenericos);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#updateRegistroSalidaIR(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO, es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO, java.util.List)
	 */
	@Override
	public void updateRegistroSalidaIR(UsuarioVO usuario,
			IdentificadorRegistroVO id,
			List<CampoGenericoRegistroVO> camposGenericos) {
		Assert.notNull(findRegistroSalidaById(usuario, id),
				"El registro con identificador [" + id.getIdRegistro()
						+ "] no es de salida.");

		try {
			preUpdateRegistroSalida(usuario, id, camposGenericos);

			updateRegistro(usuario, id, camposGenericos, true);
		} catch (Exception e) {
			updateRegistroSalidaCatchException(usuario, id, camposGenericos, e);
		} finally {
			postUpdateRegistroSalida(usuario, id, camposGenericos);
		}

	}


	/**
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 */
	public void postUpdateRegistroSalida(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos) {
		// nothing to do
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 * @param e
	 */
	public void updateRegistroSalidaCatchException(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos, Exception e) {
		StringBuffer sb = new StringBuffer("El usuario [")
				.append(usuario.getLoginName())
				.append("] no ha podido actualizar el registro de salida con id [")
				.append(id.getIdRegistro()).append("] en el libro [")
				.append(id.getIdLibro()).append("]");

		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}

		throw new RegistroException(sb.toString());
	}

	/**
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 */
	public void preUpdateRegistroSalida(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos) {
		// nothing to do
	}

	/**
	 * Adjunta el documento <code>documento</code> al registro identificado por
	 * <code>id</code> para el usuario <code>usuario</code>.
	 *
	 * @param id
	 *            identificador del registro donde adjuntar el documento
	 * @param documento
	 *            documento a adjuntar
	 * @param usuario
	 *            usuario auntenticado
	 *
	 */
	public DocumentoRegistroVO attachDocument(IdentificadorRegistroVO id,
			DocumentoRegistroVO documento, UsuarioVO usuario) {

		DocumentoRegistroVO result = null;
		List listaDocumentos = attachDocumentsUnordered(id,
				Arrays.asList(new DocumentoRegistroVO[] { documento }), usuario);
		// devolveremos el documento q contenga el nombre del documento que le
		// hemos pasado
		boolean encontrado = false;
		for (Iterator iterator = listaDocumentos.iterator(); iterator.hasNext()
				&& !encontrado;) {
			DocumentoRegistroVO documentoIt = (DocumentoRegistroVO) iterator
					.next();
			encontrado = documentoIt.getName().equals(documento.getName());
			if (encontrado) {
				result = documentoIt;
			}

		}

		return result;
	}

	/**
	 * Adjunta un listado de documentos a un registro.
	 *
	 * No garantiza el orden de inserción de los documentos.
	 *
	 * @param id
	 * @param documentos
	 * @param usuario
	 * @return
	 */
	private List attachDocumentsUnordered(IdentificadorRegistroVO id,
			List documentos, UsuarioVO usuario) {

		// Validamos los documentos
		DocumentosValidacionHelper.validateDocuments(documentos);

		// Se necesita tener abierto el libro
		BaseLibroVO libro = new BaseLibroVO(id.getIdLibro());
		libro = getLibroManager().abrirLibro(usuario, libro);

		List bookDocsWithPages = null;

		// Se guardan los documentos
		Map map = null;
		try {
			// Crea los contenedores de los documentos
			map = FolderFileSession
					.createDocuments(Integer.valueOf(id.getIdLibro()), Integer
							.valueOf(id.getIdRegistro()).intValue(),
							DocumentosHelper.getDocumentosISicres(documentos),
							Integer.valueOf(usuario.getId()), usuario
									.getConfiguracionUsuario().getIdEntidad());

			BookConf bookConf = UtilsSession.bookConf(Integer.valueOf(id
					.getIdLibro()), usuario.getConfiguracionUsuario()
					.getIdEntidad());

			BookTypeConf bookTypeConf = getBookType(id, usuario);

			// Almacena los documentos
			map = FolderFileSession.saveDocuments(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue(), map,
					bookTypeConf, bookConf, Integer.valueOf(usuario.getId()),
					usuario.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad());

			bookDocsWithPages = FolderFileSession.getBookFolderDocsWithPages(
					usuario.getConfiguracionUsuario().getSessionID(), Integer
							.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue(), usuario
							.getConfiguracionUsuario().getIdEntidad());
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"No se han podido adjuntar los documentos para el libro [")
					.append(id.getIdLibro()).append("] y registro [")
					.append(id.getIdRegistro()).append("].");
			logger.error(sb.toString(), e);

			throw new RegistroException("Error de parseo", e);
		}

		return new ListOfAxDochToListOfDocumentoRegistroVOMapper(usuario
				.getConfiguracionUsuario().getIdEntidad())
				.map(bookDocsWithPages);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#attachDocuments
	 * (es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO,
	 * java.util.List, es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public List attachDocuments(IdentificadorRegistroVO id, List documentos,
			UsuarioVO usuario) {

		List documentosAdjuntados = new LinkedList();
		if (documentos != null) {
			for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
				DocumentoRegistroVO documento = (DocumentoRegistroVO) iterator
						.next();
				DocumentoRegistroVO documentoAdjuntado = attachDocument(id,
						documento, usuario);
				documentosAdjuntados.add(documentoAdjuntado);
			}
		}
		return documentosAdjuntados;
	}

	@Override
	public boolean existDocumentByName(IdentificadorRegistroVO id,
			String documentName, UsuarioVO usuario) {
		boolean result = false;
		AxDochEntity axDochEntity = new AxDochEntity();
		String entidad;
		int docID;

		try {
			docID = axDochEntity.lookForName(id.getIdLibro(), Integer
					.parseInt(id.getIdRegistro()), documentName, usuario
					.getConfiguracionUsuario().getIdEntidad());
			if (docID != -1) {
				result = true;
			}
		} catch (Exception e) {
			String message = "Error en la busqueda de documento por nombre (existDocumentByName)idRegistro:"
					+ id.getIdRegistro() + " -idLibro:" + id.getIdLibro();
			logger.error(message);
			throw new RegistroException(message, e);
		}

		return result;
	}

	/**
	 * Devuelve los registros de entrada de todos los libros que cumplen con el
	 * criterio de búsqueda <code>criterioBusqueda</code> para el usuario
	 * <code>usuario</code>.
	 *
	 * Junto con los registros encontrados devuelve el número total existente
	 * sin tener en cuenta las restricciones de paginación.
	 *
	 * @see ResultadoBusquedaRegistroVO
	 */
	public ResultadoBusquedaRegistroVO findAllRegistroEntradaByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda) {

		List axSfQueryResults = queryForAxSfQueryResults(usuario,
				criterioBusqueda.getOffset().intValue(), criterioBusqueda
						.getLimit().intValue(), null, TipoLibroEnum.ENTRADA,
				criterioBusqueda.getSql());

		int total = queryForAxSfQueryResultsTotalCount(axSfQueryResults);

		List registers = new ArrayList();
		for (Iterator iterator = axSfQueryResults.iterator(); iterator
				.hasNext();) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();

			Iterator axSfIterator = results.getResults().iterator();
			while (axSfIterator.hasNext()) {
				AxSf axSf = (AxSf) axSfIterator.next();
				RegistroEntradaVO result = mapToRegistroEntradaVO(usuario,
						results.getBookId(), axSf);
				registers.add(result);
			}
		}
		return new ResultadoBusquedaRegistroVO(registers, total);
	}

	/**
	 *
	 */
	public ResultadoBusquedaRegistroVO findAllRegistroSalidaByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda) {

		List axSfQueryResults = queryForAxSfQueryResults(usuario,
				criterioBusqueda.getOffset().intValue(), criterioBusqueda
						.getLimit().intValue(), null, TipoLibroEnum.SALIDA,
				criterioBusqueda.getSql());

		int total = queryForAxSfQueryResultsTotalCount(axSfQueryResults);

		List registers = new ArrayList();
		for (Iterator iterator = axSfQueryResults.iterator(); iterator
				.hasNext();) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();

			Iterator axSfIterator = results.getResults().iterator();
			while (axSfIterator.hasNext()) {

				AxSf axSf = (AxSf) axSfIterator.next();
				registers.add(getBaseRegistroVOBuilder().build(usuario, axSf,
						String.valueOf(results.getBookId()),
						new RegistroSalidaVO()));
			}
		}

		return new ResultadoBusquedaRegistroVO(registers, total);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] getPaginaById(IdentificadorRegistroVO identificadorRegistro,
			String documentId, String pageId, UsuarioVO usuario) {

		byte[] page = null;
		try {
			page = FolderFileSession.getFile(usuario.getConfiguracionUsuario()
					.getSessionID(), Integer.valueOf(identificadorRegistro
					.getIdLibro()), Integer.valueOf(identificadorRegistro
					.getIdRegistro()), Integer.valueOf(pageId), usuario
					.getConfiguracionUsuario().getIdEntidad());
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Error al recuperar la página con identificador [")
					.append(pageId).append("] del documento con id [")
					.append(documentId).append("] del registro [")
					.append(identificadorRegistro.getIdRegistro())
					.append("] para el libro [")
					.append(identificadorRegistro.getIdLibro()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		}
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] getPaginaByIndex(
			IdentificadorRegistroVO identificadorRegistro, int documentIndex,
			int pageIndex, UsuarioVO usuario) {

		try {
			getLibroManager().abrirLibro(usuario,
					new BaseLibroVO(identificadorRegistro.getIdLibro()));

			List bookFolderDocsWithPages = FolderFileSession
					.getBookFolderDocsWithPages(
							usuario.getConfiguracionUsuario().getSessionID(),
							Integer.valueOf(identificadorRegistro.getIdLibro()),
							Integer.valueOf(
									identificadorRegistro.getIdRegistro())
									.intValue(), usuario
									.getConfiguracionUsuario().getIdEntidad());
			// Se ordena por identificador de AxDoch, lo que sería equivalente a
			// ordenarlos por fecha de creación

			/*
			 * Por defecto los documentos vienen ordenados por el nombre.
			 * (Atributo name)
			 *
			 * Las páginas vienen ordenadas por el orden. (Atributo sortorden)
			 */

			/*
			BeanComparator beanComparator = new BeanComparator("id");
			Collections.sort(bookFolderDocsWithPages, beanComparator);
			*/

			AxDoch selectedDocument = (AxDoch) bookFolderDocsWithPages
					.get(documentIndex - 1);
			AxPageh selectedPage = (AxPageh) CollectionUtils.find(
					selectedDocument.getPages(),
					new BeanPropertyValueEqualsPredicate("sortOrder",
							new Integer(pageIndex)));

			return FolderFileSession.getFile(usuario.getConfiguracionUsuario()
					.getSessionID(), new Integer(selectedDocument.getArchId()),
					new Integer(selectedDocument.getFdrId()), selectedPage
							.getId(), usuario.getConfiguracionUsuario()
							.getIdEntidad());

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Error al recuperar la página con índice [")
					.append(pageIndex).append("] del documento con índice [")
					.append(documentIndex).append("] del registro [")
					.append(identificadorRegistro.getIdRegistro())
					.append("] para el libro [")
					.append(identificadorRegistro.getIdLibro()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean canCreateRegistro(UsuarioVO usuario, BaseRegistroVO registro) {

		try {
			FolderSession.canCreateOrUpdateFolder(usuario
					.getConfiguracionUsuario().getSessionID(), new Integer(
					registro.getIdLibro()), registro.getDocumentos());
		} catch (Exception e) {
			logger.debug(
					"El usuario[" + usuario.getLoginName()
							+ "] no tiene permisos sobre el libro ["
							+ registro.getIdLibro() + "]", e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private ResultadoBusquedaDistribucionVO getDistribucionesPendientes(
			UsuarioVO usuario, Integer idRegistro) {

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.PENDIENTE);
		criterio.setLimit(Long.valueOf(Integer.MAX_VALUE));
		criterio.setOffset(0L);
		return distribucionManager.getDistributionsByRegistry(usuario,
				criterio, idRegistro, Keys.DISTRIBUCION_IN_DIST);
	}

	/**
	 * Cancela un registro. Elimina las distribuciones pendientes asociadas a
	 * dicho registro.
	 *
	 * Solo puede ejecutarlo el super-usuario.
	 *
	 * @param usuario
	 *            Usuario
	 * @param id
	 *            Identificador del registro
	 */
	protected void cancelRegistroById(UsuarioVO usuario,
			IdentificadorRegistroVO id) {

		// El usuario que lanza la operación debe ser Superusuario o
		// Administrador del libro de registro
		BaseLibroVO libro = new BaseLibroVO(id.getIdLibro());
		PermisosLibroVO permisoLibro = getPermisosManager().getPermisosLibro(
				libro, usuario);

		Assert.state(
				permisoLibro.isAdministrador()
						|| usuario.getPermisos().getPermisosAplicacion()
								.isSuperUsuario(),
				"Para poder cancelar un registro se debe ser administrador del libro o superusuario.");

		try {

			String idEntidad = usuario.getConfiguracionUsuario().getIdEntidad();
			/*
			 * TODO: ¿Están implementadas estas 4 restricciones?
			 */
			// El registro no puede estar siendo editado
			// El registro no puede haber sido aceptado, redistribuidos,.
			// archivado
			// o rechazado si ha sido distribuido

			/*
			 * Si el registro tiene distribuciones pendientes entonces hay que
			 * cancelar las distribuciones pendientes.
			 */

			/*
			 * TODO: No es una operación transaccional
			 */
			ResultadoBusquedaDistribucionVO distribucionesPendientes = this
					.getDistribucionesPendientes(usuario,
							Integer.valueOf(id.getIdRegistro()));

			Iterator it = distribucionesPendientes.getDistributions()
					.iterator();
			while (it.hasNext()) {
				DistribucionVO distribucion = (DistribucionVO) it.next();
				logger.debug("Se eliminará la siguiente distribución ["
						+ distribucion.getId()
						+ "] al cancelar el registro asociado");
				distribucionManager.deleteDistribution(idEntidad,
						Integer.valueOf(distribucion.getId()));
			}

			updateRegistro(
					usuario,
					id,
					Arrays.asList(new CampoGenericoRegistroVO[] { new CampoGenericoRegistroVO(
							String.valueOf(AxSf.FLD6_FIELD_ID), String
									.valueOf(EstadoRegistroEnum.ANULADO
											.getValue())) }), false);

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Se ha producido un error al cancelar el registro con identificador [")
					.append(id.getIdRegistro()).append("] del libro [")
					.append(id.getIdLibro()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		}

	}

	/**
	 * Actualiza un registro. En cuanto a los interesados, el funcionamiento es
	 * el siguiente:
	 *
	 * Si no llega ningún campo con fld9 se dejan los interesados tal y como
	 * estaban.
	 *
	 * Si llega un campo con el valor de fld9 vacío entonces se borran todos los interesados
	 *
	 * Si llegan campos con fld9 no vacíos entonces se sustityen los interesados
	 * que había por estos nuevos
	 *
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 */
	protected void updateRegistro(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos, boolean validateIR) {
		BookUseCase buc = new BookUseCase();

		UseCaseConf useCaseConf = new UseCaseConf(usuario
				.getConfiguracionUsuario().getLocale());
		useCaseConf.setSessionID(usuario.getConfiguracionUsuario()
				.getSessionID());
		useCaseConf.setEntidadId(usuario.getConfiguracionUsuario()
				.getIdEntidad());

		AxSf register = null;
		try {
			BaseLibroVO libro = getLibroManager().abrirLibro(usuario,
					new BaseLibroVO(id.getIdLibro()));

			PermisosLibroVO permisoRegistro = getPermisosManager()
					.getPermisosLibro(libro, usuario);

			/*Si es una modificacion de IR solo hay que comprobar que el usuario
			 * tenga permisos para realizar operaciones de IR.
			 */
			if (validateIR) {
				if (!(permisoRegistro.isModificacion() && usuario.getPermisos()
						.getPermisosAplicacion().isOperacionesIntercambioRegistral())) {
					throw new RuntimeException(
							"No se tienen permisos para actualizar el registro "
									+ id);
				}
			} else {

				// Para poder modificar el estado se deben tener permisos para
				// modificación de campos protegidos
				if (!(permisoRegistro.isModificacion() && usuario.getPermisos()
						.getPermisosAplicacion().isModificarCamposProtegidos())) {
					throw new RuntimeException(
							"No se tienen permisos para actualizar el registro "
									+ id);
				}
			}
			register = buc.getBookFolder(useCaseConf,
					Integer.valueOf(id.getIdLibro()),
					Integer.valueOf(id.getIdRegistro()).intValue());

			@SuppressWarnings("unchecked")
			List<FlushFdrField> atts = (List<FlushFdrField>) new ListOfCampoGenericoRegistroVOToListOfFlushFdrFieldMapper()
					.map(camposGenericos);

			List<FlushFdrInter> listaInteresados =  TercerosHelper.getListaInteresadosISicres(atts);


			// Comprobamos que los campos que se quieren actualizar existen en
			// la definición del registro
			checkExistAllFlushFdrFields(register, atts);

			lockRegistro(usuario, id);

			// Validacion de permisos

			buc.saveOrUpdateFolder(usuario.getConfiguracionUsuario()
					.getIdEntidad(), usuario.getConfiguracionUsuario()
					.getSessionID(), usuario.getConfiguracionUsuario()
					.getLocale(), Integer.valueOf(id.getIdLibro()), Integer
					.valueOf(id.getIdRegistro()).intValue(), new ArrayList(),
					atts, listaInteresados, new HashMap());

			unlockRegistro(usuario, id);

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Se ha producido un error al actualizar el registro con identificador [")
					.append(id.getIdRegistro()).append("] del libro [")
					.append(id.getIdLibro()).append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		}
	}

	public UnidadAdministrativaManager getUnidadAdministrativaManager() {
		return unidadAdministrativaManager;
	}

	public void setUnidadAdministrativaManager(
			UnidadAdministrativaManager unidadAdministrativaManager) {
		this.unidadAdministrativaManager = unidadAdministrativaManager;
	}

	public LibroManager getLibroManager() {
		return libroManager;
	}

	public void setLibroManager(LibroManager libroManager) {
		this.libroManager = libroManager;
	}

	public PermisosManager getPermisosManager() {
		return permisosManager;
	}

	public void setPermisosManager(PermisosManager permisosManager) {
		this.permisosManager = permisosManager;
	}

	public BaseRegistroVOBuilder getBaseRegistroVOBuilder() {
		return baseRegistroVOBuilder;
	}

	public void setBaseRegistroVOBuilder(
			BaseRegistroVOBuilder baseRegistroVOBuilder) {
		this.baseRegistroVOBuilder = baseRegistroVOBuilder;
	}

	/**
	 * @return el distribucionManager
	 */
	public DistribucionManager getDistribucionManager() {
		return distribucionManager;
	}

	/**
	 * @param distribucionManager
	 *            el distribucionManager a fijar
	 */
	public void setDistribucionManager(DistribucionManager distribucionManager) {
		this.distribucionManager = distribucionManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryForAxSfQueryResults(UsuarioVO usuario, int offset,
			int limit, Integer bookID, TipoLibroEnum bookType, String filter) {
		return queryForAxSfQueryResults(usuario, offset, limit, bookID,
				bookType, filter, true/*indicamos que la consulta realizada se concatene a los filtros del libro*/);
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryForAxSfQueryResults(UsuarioVO usuario, int offset,
			int limit, Integer bookID, TipoLibroEnum bookType, String filter,
			boolean appendFilter) {

		// La opcion reportOption se tratará como si solamente se busca en un
		// libro (new Integer(0)), ya que por cada libro se ejecuta la consulta
		// de forma independiente, y se añade a la
		// coleccion que se retorna, de esta forma se evita que se sobreescriba
		// la consulta y sus filtros
		Integer reportOption = new Integer(0);

		List bookIds = new ArrayList();
		if (null == bookID) {
			List booksByUser = getLibroManager().findLibrosByUser(usuario,
					bookType);
			for (Iterator iterator = booksByUser.iterator(); iterator.hasNext();) {
				BaseLibroVO libro = (BaseLibroVO) iterator.next();
				bookIds.add(Integer.valueOf(libro.getId()));
			}
		} else {
			bookIds.add(bookID);
		}

		List results = new ArrayList();
		Integer aBookId = null;
		try {
			for (Iterator iterator = bookIds.iterator(); iterator.hasNext();) {
				aBookId = (Integer) iterator.next();

				getLibroManager().abrirLibro(usuario,
						new BaseLibroVO(String.valueOf(aBookId)));

				// El usuario debe tener permisos de consulta sobre el libro
				if (SecuritySession.canQuery(usuario.getConfiguracionUsuario()
						.getSessionID(), aBookId)) {

					CacheBag cacheBag = CacheFactory.getCacheInterface()
							.getCacheEntry(
									usuario.getConfiguracionUsuario()
											.getSessionID());

					THashMap bookInformation = (THashMap) cacheBag.get(aBookId);

					//variable que por defecto contiene la consulta a realizar
					String filterAux = filter;
					// Añadimos el filtro
					if (!StringUtils.isEmpty(filter)) {
						if (appendFilter) {
							// Sustituir el filtro o añadirlo al existente que
							// nos filtra por la oficina
							String queryFilter = (String) bookInformation
									.get(ServerKeys.QUERY_FILTER);
							if (!StringUtils.isEmpty(queryFilter)) {
								//a la consulta se le añade los filtros del libro
								filterAux = queryFilter + " AND " + filter;
							}
						}
						bookInformation.put(BookSession.QUERY_FILTER, filterAux);
					}

					AxSfQuery axsfQuery = new AxSfQuery(aBookId);
					axsfQuery.setPageSize(limit);

					// Recuperamos el número de registros del libro
					int bookSize = FolderSession.openRegistersQuery(usuario
							.getConfiguracionUsuario().getSessionID(),
							axsfQuery,
							/* bookIds */Arrays
									.asList(new Integer[] { aBookId }),
							reportOption, usuario.getConfiguracionUsuario()
									.getIdEntidad());

					if (bookSize > 0) {
						if (offset <= bookSize) {
							AxSfQueryResults axSfQueryResults = FolderSession
									.navigateToRowRegistersQuery(usuario
											.getConfiguracionUsuario()
											.getSessionID(), aBookId, offset,
											usuario.getConfiguracionUsuario()
													.getLocale(), usuario
													.getConfiguracionUsuario()
													.getIdEntidad(),
											StringUtils.EMPTY);
							// Actualizamos el número total de registros a
							// devolver
							// descontando los recuperados
							limit -= axSfQueryResults.getCurrentResultsSize();

							results.add(axSfQueryResults);
						}
						offset = calculateOffset(offset, bookSize);

						FolderSession.closeRegistersQuery(usuario
								.getConfiguracionUsuario().getSessionID(),
								aBookId);
					}
				} else {
					StringBuffer sb = new StringBuffer("El usuario [")
							.append(usuario.getLoginName())
							.append("] no tiene permisos de consulta sobre el libro [")
							.append(aBookId).append("]");
					logger.warn(sb.toString());

					throw new RegistroException(
							"No se pueden recuperar los registros de "
									+ bookType.getName() + " para el usuario ["
									+ usuario.getLoginName() + "]",
							new SecurityException(sb.toString()));
				}
			}
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Error al recuperar los registros de libro [")
					.append(aBookId).append("] de tipo [")
					.append(bookType.getName()).append("] para el usuario [")
					.append(usuario.getLoginName()).append("]");

			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		}

		return results;
	}


	/**
	 * Devuelve el número total de registros encontrados para los parámetros que
	 * se reciben.
	 *
	 * @param appendQuery
	 *            Indica si se se añade el filtro SQL al filtro implícito de la
	 *            oficina del usuario
	 * @return
	 */
	private int queryForAxSfQueryResultsTotalCount(List axSfQueryResults) {

		int total = 0;
		Iterator iterator = axSfQueryResults.iterator();
		while (iterator.hasNext()) {
			AxSfQueryResults results = (AxSfQueryResults) iterator.next();
			total += results.getTotalQuerySize();
		}

		return total;
	}

	/**
	 * Devuelve el tipo de libro asociado al identificador <code>id</code> y
	 * usuario <code>usuario</code>.
	 *
	 * @param id
	 * @param usuario
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private BookTypeConf getBookType(IdentificadorRegistroVO id,
			UsuarioVO usuario) throws BookException, SessionException,
			ValidationException {
		BookTypeConf btc = new BookTypeConf();

		if (Repository
				.getInstance(usuario.getConfiguracionUsuario().getIdEntidad())
				.isInBook(id.getIdLibro()).booleanValue()) {
			btc.setBookType(1); // Libro de entrada
		} else {
			btc.setBookType(2); // Libro de salida
		}

		btc = UtilsSession.bookTypeConf(btc.getBookType(), usuario
				.getConfiguracionUsuario().getIdEntidad());
		return btc;
	}

	/**
	 * Calcula la posición inicial desde la que recuperar los registros de un
	 * libro teniendo en cuenta el tamaño del libro anterior que se haya
	 * consultado. Se trata de recalcular el offset absoluto a uno relativo al
	 * libro a consultar.
	 *
	 * @param offset
	 *            posición inicial absoluta
	 * @param bookSize
	 *            número de registros del libro consultado
	 * @return el offset relativo a aplicar al siguiente libro a consultar
	 */
	private int calculateOffset(int offset, int bookSize) {
		int newOffset = 0;

		if (offset >= bookSize) {
			newOffset = offset - bookSize;
		}

		return newOffset;
	}

	/**
	 * Comprueba que en la definición del registro <code>register</code> existen
	 * todos los campos contenidos en <code>atts</code>.
	 *
	 * @param register
	 * @param atts
	 *
	 * @throws RegistroException
	 *             en caso de que haya algún campo no definido
	 */
	private void checkExistAllFlushFdrFields(AxSf register, List atts) {
		if (!CollectionUtils.isEmpty(atts)) {
			Iterator iterator = atts.iterator();
			while (iterator.hasNext()) {
				FlushFdrField field = (FlushFdrField) iterator.next();
				if (!register.getAttributesNames().contains(
						StringUtils.join(new String[] { AxSf.FLD,
								String.valueOf(field.getFldid()) }))
						&& !register.getProposedExtendedFields().contains(
								field.getFldid())) {
					StringBuffer sb = new StringBuffer("El campo [")
							.append(field.getFldid())
							.append("] no existe para el registro [")
							.append(register
									.getAttributeValue(AxSf.FDRID_FIELD))
							.append("]");

					if (logger.isDebugEnabled()) {
						logger.debug(sb.toString());
					}

					throw new RegistroException(sb.toString());
				}
			}
		}

	}

	// Members
	private static final Logger logger = Logger
			.getLogger(RegistroManagerImpl.class);

	protected LibroManager libroManager;

	protected PermisosManager permisosManager;

	protected UnidadAdministrativaManager unidadAdministrativaManager;

	protected DistribucionManager distribucionManager;

	protected BaseRegistroVOBuilder baseRegistroVOBuilder;

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.RegistroManager#findRegistroByNumRegistro(java.lang.String)
	 */
	@Override
	public BaseRegistroVO findRegistroByNumRegistro(UsuarioVO usuario,
			String numRegistro) {
		CriterioBusquedaRegistroSqlVO criterioBusqueda = new CriterioBusquedaRegistroSqlVO();
		criterioBusqueda.setLimit(1L);
		criterioBusqueda.setOffset(0L);
		criterioBusqueda.setSql("fld1='" + numRegistro + "'");
		ResultadoBusquedaRegistroVO resultado = findAllRegistroEntradaByCriterioWhereSql(
				usuario, criterioBusqueda);

		if (resultado == null || resultado.getRegisters().size() == 0) {
			logger.warn("No se ha podido encontrar el registro con numero de registro: "
					+ numRegistro);
			throw new RegistroException(
					"No se ha podido encontrar el registro con numero de registro: "
							+ numRegistro);
		}
		BaseRegistroVO registro = (BaseRegistroVO) resultado.getRegisters()
				.get(0);

		return registro;
	}


}
