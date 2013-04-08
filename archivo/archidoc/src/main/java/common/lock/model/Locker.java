package common.lock.model;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import se.usuarios.ServiceClient;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBEntityFactory;
import common.db.DbDataSourceMultiEntidadImpl;
import common.db.IDBEntityFactory;
import common.db.ITransactionManager;
import common.db.TransactionManagerFactory;
import common.lock.db.ILockDBEntity;
import common.lock.exceptions.LockerException;
import common.lock.vos.LockVO;
import common.util.TypeConverter;

/**
 * Clase Singleton que se encarga de los bloqueo en BD.
 */
public class Locker {

	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(Locker.class);

	/** DBEntity de acceso a la tabla de bloqueos. */
	private ILockDBEntity lockDBEntity = null;

	/** Gestor de transacciones. */
	private ITransactionManager txManager = null;

	/**
	 * Constructor.
	 */
	public Locker(ServiceClient serviceClient) {
		txManager = TransactionManagerFactory
				.getTransactionManager(new DbDataSourceMultiEntidadImpl(
						serviceClient.getEngine()));
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		lockDBEntity = dbEntityFactory.getLockDBEntityImplBase(txManager);
	}

	/**
	 * Bloquea un objeto para el usuario actual.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idObj
	 *            Identificador del objeto a bloquear.
	 * @param tipoObj
	 *            Tipo de objeto a bloquear.
	 * @param modulo
	 *            Módulo desde el que se va a bloquear el objeto.
	 * @throws LockerException
	 *             si el objeto está ya bloqueado.
	 */
	public void bloquea(ServiceClient serviceClient, String idObj, int tipoObj,
			int modulo) throws LockerException {
		if (logger.isInfoEnabled())
			logger.info("Bloqueando objeto:" + Constants.NEWLINE
					+ "- ID USUARIO: " + serviceClient.getId()
					+ Constants.NEWLINE + "- ID OBJETO: " + idObj
					+ Constants.NEWLINE + "- TIPO OBJETO: " + tipoObj
					+ Constants.NEWLINE + "- MODULO: " + modulo);

		try {
			// Iniciar transacción
			txManager.iniciarTransaccion();

			// Obtener la información del bloqueo
			LockVO bloqueo = lockDBEntity.getBloqueoForUpdate(idObj, tipoObj,
					modulo);
			if (logger.isDebugEnabled())
				logger.debug("Bloqueo encontrado:" + Constants.NEWLINE
						+ bloqueo);

			if (bloqueo != null) {
				// Comprobar si el usuario ya tenía bloqueado el objeto
				if (bloqueo.getIdUsuario().equals(serviceClient.getId())) {
					if (logger.isDebugEnabled())
						logger.debug("El usuario tiene bloqueado el objeto");

					// Actualizar el bloqueo
					bloqueo.setTs(new Date());
					lockDBEntity.updateBloqueo(bloqueo);
				}
				// Comprobar si se ha excedido el tiempo de bloqueo
				else if (tiempoBloqueoExcedido(bloqueo.getTs())) {
					if (logger.isDebugEnabled())
						logger.debug("El tiempo de bloqueo se ha excedido");

					// Actualizar el bloqueo para el usuario actual
					bloqueo.setTs(new Date());
					bloqueo.setIdUsuario(serviceClient.getId());
					lockDBEntity.updateBloqueo(bloqueo);
				}
				// Objeto bloqueado por otro usuario
				else {
					if (logger.isDebugEnabled())
						logger.debug("El objeto está bloqueado por otro usuario: "
								+ bloqueo.getIdUsuario()
								+ " - "
								+ bloqueo.getUsuario());

					throw new LockerException(bloqueo);
				}
			} else {
				if (logger.isDebugEnabled())
					logger.debug("El objeto no está bloqueado y se va a bloquear");

				// Bloquear
				lockDBEntity.insertBloqueo(new LockVO(idObj, tipoObj, modulo,
						serviceClient.getId()));
			}

			// Commit
			txManager.commit();
		} catch (LockerException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} catch (RuntimeException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} finally {
			// Liberar la conexión
			txManager.liberarConexion();
		}
	}

	/**
	 * Bloquea un objeto para el cualquier usuario y sin tener en cuenta el
	 * tiempo.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idObj
	 *            Identificador del objeto a bloquear.
	 * @param tipoObj
	 *            Tipo de objeto a bloquear.
	 * @param modulo
	 *            Módulo desde el que se va a bloquear el objeto.
	 * @throws LockerException
	 *             si el objeto está ya bloqueado.
	 */
	public void bloqueaCualquiera(ServiceClient serviceClient, String idObj,
			int tipoObj, int modulo) throws LockerException {
		if (logger.isInfoEnabled())
			logger.info("Bloqueando objeto:" + Constants.NEWLINE
					+ "- ID USUARIO: " + serviceClient.getId()
					+ Constants.NEWLINE + "- ID OBJETO: " + idObj
					+ Constants.NEWLINE + "- TIPO OBJETO: " + tipoObj
					+ Constants.NEWLINE + "- MODULO: " + modulo);

		try {
			// Iniciar transacción
			txManager.iniciarTransaccion();

			// Obtener la información del bloqueo
			LockVO bloqueo = lockDBEntity.getBloqueoForUpdate(idObj, tipoObj,
					modulo);
			if (logger.isDebugEnabled())
				logger.debug("Bloqueo encontrado:" + Constants.NEWLINE
						+ bloqueo);

			if (bloqueo != null) {

				if (logger.isDebugEnabled())
					logger.debug("El objeto está bloqueado por: "
							+ bloqueo.getIdUsuario() + " - "
							+ bloqueo.getUsuario());

				throw new LockerException(bloqueo);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("El objeto no está bloqueado y se va a bloquear");

				// Bloquear
				lockDBEntity.insertBloqueo(new LockVO(idObj, tipoObj, modulo,
						serviceClient.getId()));
			}

			// Commit
			txManager.commit();
		} catch (LockerException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} catch (RuntimeException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} finally {
			// Liberar la conexión
			txManager.liberarConexion();
		}
	}

	/**
	 * Comprueba que el usuario tenga bloqueado el objeto.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idObj
	 *            Identificador del objeto a bloquear.
	 * @param tipoObj
	 *            Tipo de objeto a bloquear.
	 * @param modulo
	 *            Módulo desde el que se va a bloquear el objeto.
	 * @return true si el usuario tiene bloqueado el objeto.
	 */
	public boolean bloqueadoPorUsuario(ServiceClient serviceClient,
			String idObj, int tipoObj, int modulo) {
		if (logger.isInfoEnabled())
			logger.info("Comprobando bloqueo para el objeto:"
					+ Constants.NEWLINE + "- ID USUARIO: "
					+ serviceClient.getId() + Constants.NEWLINE
					+ "- ID OBJETO: " + idObj + Constants.NEWLINE
					+ "- TIPO OBJETO: " + tipoObj + Constants.NEWLINE
					+ "- MODULO: " + modulo);

		boolean bloqueadoPorUsuario = false;

		try {
			// Iniciar transacción
			txManager.iniciarTransaccion();

			// Obtener la información del bloqueo
			LockVO bloqueo = lockDBEntity.getBloqueoForUpdate(idObj, tipoObj,
					modulo);
			if (logger.isDebugEnabled())
				logger.debug("Bloqueo encontrado:" + Constants.NEWLINE
						+ bloqueo);

			// Comprobar que el usuario tenga bloqueado el objeto
			if ((bloqueo != null)
					&& bloqueo.getIdUsuario().equals(serviceClient.getId())) {
				if (logger.isDebugEnabled())
					logger.debug("El objeto está bloqueado por el usuario");

				// Actualizar el bloqueo
				bloqueo.setTs(new Date());
				lockDBEntity.updateBloqueo(bloqueo);

				bloqueadoPorUsuario = true;
			} else {
				if (logger.isDebugEnabled())
					logger.debug("El objeto no está bloqueado por el usuario");
			}

			// Commit
			txManager.commit();
		} catch (RuntimeException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} finally {
			// Liberar la conexión
			txManager.liberarConexion();
		}

		return bloqueadoPorUsuario;
	}

	/**
	 * Desbloquea un objeto para el usuario actual.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idObj
	 *            Identificador del objeto a desbloquear.
	 * @param tipoObj
	 *            Tipo de objeto a desbloquear.
	 * @param modulo
	 *            Módulo desde el que se va a desbloquear el objeto.
	 */
	public void desbloquea(ServiceClient serviceClient, String idObj,
			int tipoObj, int modulo) {
		if (logger.isInfoEnabled())
			logger.info("Desbloqueando objeto:" + Constants.NEWLINE
					+ "- ID USUARIO: " + serviceClient.getId()
					+ Constants.NEWLINE + "- ID OBJETO: " + idObj
					+ Constants.NEWLINE + "- TIPO OBJETO: " + tipoObj
					+ Constants.NEWLINE + "- MODULO: " + modulo);

		try {
			// Iniciar transacción
			txManager.iniciarTransaccion();

			// Desbloquear
			lockDBEntity.deleteBloqueo(idObj, tipoObj, modulo,
					serviceClient.getId());

			// Commit
			txManager.commit();
		} catch (RuntimeException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} finally {
			// Liberar la conexión
			txManager.liberarConexion();
		}
	}

	/**
	 * Desbloquea un objeto para el usuario actual.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idObj
	 *            Identificador del objeto a desbloquear.
	 * @param tipoObj
	 *            Tipo de objeto a desbloquear.
	 * @param modulo
	 *            Módulo desde el que se va a desbloquear el objeto.
	 */
	public void desbloquea(String idUsuario, String idObj, int tipoObj,
			int modulo) {
		if (logger.isInfoEnabled())
			logger.info("Desbloqueando objeto:" + Constants.NEWLINE
					+ "- ID USUARIO: " + idUsuario + Constants.NEWLINE
					+ "- ID OBJETO: " + idObj + Constants.NEWLINE
					+ "- TIPO OBJETO: " + tipoObj + Constants.NEWLINE
					+ "- MODULO: " + modulo);

		try {
			// Iniciar transacción
			txManager.iniciarTransaccion();

			// Desbloquear
			lockDBEntity.deleteBloqueo(idObj, tipoObj, modulo, idUsuario);

			// Commit
			txManager.commit();
		} catch (RuntimeException e) {
			// Rollback
			txManager.rollback();

			throw e;
		} finally {
			// Liberar la conexión
			txManager.liberarConexion();
		}
	}

	/**
	 * Indica si se ha excedido el tiempo de bloqueo.
	 * 
	 * @param timestamp
	 *            Timestamp del bloqueo.
	 * @return true si el tiempo de bloqueo se ha excedido.
	 */
	protected boolean tiempoBloqueoExcedido(Date timestamp) {
		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		int maxPeriodo = TypeConverter.toInt(csa.getConfiguracionGeneral()
				.getPeriodoMaximoBloqueo(), -1);
		if (maxPeriodo > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, maxPeriodo * (-1));

			if (cal.getTimeInMillis() > timestamp.getTime())
				return true;
		}

		return false;
	}

	public LockVO getBloqueo(String idObj, int tipoObj, int modulo) {
		return lockDBEntity.getBloqueo(idObj, tipoObj, modulo);
	}

}
