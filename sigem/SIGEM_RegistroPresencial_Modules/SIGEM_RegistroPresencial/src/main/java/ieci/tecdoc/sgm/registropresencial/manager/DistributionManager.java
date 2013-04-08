package ieci.tecdoc.sgm.registropresencial.manager;

import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.distribution.DistributionServices;
import ieci.tecdoc.sgm.registropresencial.info.InfoDistribution;

import java.util.List;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;

public class DistributionManager {
	private static Logger _logger = Logger.getLogger(DistributionManager.class);

	public static final int STATE_ARCHIVADO = 3;

	public List getInputDistribution(User user, int state, int firstRow,
			int maxResults, int typeBookRegisterDist, boolean oficAsoc,
			String entidad) throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException {
		List result = null;
		try {
			result = DistributionServices.getDistribution(user, state,
					firstRow, maxResults, 1, typeBookRegisterDist, oficAsoc,
					entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		}

		return result;

	}

	public List getOutputDistribution(User user, int state, int firstRow,
			int maxResults, int typeBookRegisterDist, boolean oficAsoc,
			String entidad) throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException {
		List result = null;
		try {
			result = DistributionServices.getDistribution(user, state,
					firstRow, maxResults, 2, typeBookRegisterDist, oficAsoc,
					entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		}

		return result;

	}

	public Integer countInputDistribution(User user, int state,
			int typeBookRegisterDist, boolean oficAsoc, String entidad)
			throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException {
		Integer result = null;
		try {
			result = DistributionServices.countDistribution(user, state, 1,
					typeBookRegisterDist, oficAsoc, entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		}

		return result;

	}

	public Integer countOutputDistribution(User user, int state,
			int typeBookRegisterDist, boolean oficAsoc, String entidad)
			throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException {
		Integer result = null;
		try {
			result = DistributionServices.countDistribution(user, state, 2,
					typeBookRegisterDist, oficAsoc, entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible obtain distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		}

		return result;

	}

	public InfoDistribution getDistributionForNumber(User user,
			Integer distributionId, String entidad)
			throws DistributionException {
		InfoDistribution result = null;
		try {
			result = DistributionServices.getDistributionById(user,
					distributionId, entidad);
		} catch (TecDocException e) {
			_logger.error("Impossible obtain distribution: " + distributionId,
					e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} catch (Exception e) {
			_logger.error("Impossible obtain distribution: " + distributionId,
					e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		}

		return result;
	}

	public void acceptDistribution(User user, String registerNumber,
			Integer bookId, String entidad) throws ValidationException,
			BookException, DistributionException, SecurityException,
			SessionException {
		try {
			DistributionServices.acceptDistribution(user, registerNumber,
					bookId, entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible accept distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible accept distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}
	}

	public void acceptDistribution(User user, Integer distributionId,
			String entidad) throws ValidationException, BookException,
			DistributionException, SecurityException, SessionException {
		try {
			DistributionServices.acceptDistribution(user, distributionId,
					entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible accept distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible accept distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}
	}

	public void archiveDistribution(User user, Integer distributionId,
			String entidad) throws ValidationException, BookException,
			DistributionException, SecurityException, SessionException {
		try {
			DistributionServices
					.saveDistribution(user, distributionId, entidad);
		} catch (TecDocException e) {
			_logger.error("Impossible archive distribution for user: " + user,
					e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		} catch (Exception e) {
			_logger.error("Impossible archive distribution for user: " + user,
					e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		}
	}

	public void archiveDistribution(User user, String registerNumber,
			Integer bookId, String entidad) throws ValidationException,
			BookException, DistributionException, SecurityException,
			SessionException {
		try {
			DistributionServices.saveDistribution(user, registerNumber,
					STATE_ARCHIVADO, entidad);
		} catch (TecDocException e) {
			_logger.error("Impossible archive distribution for user: " + user,
					e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		} catch (Exception e) {
			_logger.error("Impossible archive distribution for user: " + user,
					e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		}
	}

	public void rejectDistribution(User user, String registerNumber,
			String remarks, String entidad) throws ValidationException,
			BookException, DistributionException, SecurityException,
			SessionException {
		try {
			DistributionServices.rejectDistribution(user, registerNumber,
					remarks, entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible reject distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible reject distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
		}
	}

	public void rejectDistribution(User user, Integer distributionId,
			String remarks, String entidad) throws ValidationException,
			BookException, DistributionException, SecurityException,
			SessionException {
		try {
			DistributionServices.rejectDistribution(user, distributionId,
					remarks, entidad);
		} catch (TecDocException e) {
			_logger
					.error("Impossible reject distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
		} catch (Exception e) {
			_logger
					.error("Impossible reject distribution for user: " + user,
							e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
		}
	}

	public void changeInputDistribution(User user, String registerNumber,
			String code, String entidad) throws ValidationException,
			DistributionException, SessionException, SecurityException,
			BookException {
		try {
			DistributionServices.changeDistribution(user, registerNumber, code,
					1, entidad);
		} catch (TecDocException e) {
			_logger.error("Impossible redistribute distribution for user: "
					+ user, e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		} catch (Exception e) {
			_logger.error("Impossible redistribute distribution for user: "
					+ user, e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		}
	}

	public void changeOutputDistribution(User user, String registerNumber,
			String code, String entidad) throws ValidationException,
			DistributionException, SessionException, SecurityException,
			BookException {
		try {
			DistributionServices.changeDistribution(user, registerNumber, code,
					2, entidad);
		} catch (TecDocException e) {
			_logger.error("Impossible redistribute distribution for user: "
					+ user, e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		} catch (Exception e) {
			_logger.error("Impossible redistribute distribution for user: "
					+ user, e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		}
	}

}
