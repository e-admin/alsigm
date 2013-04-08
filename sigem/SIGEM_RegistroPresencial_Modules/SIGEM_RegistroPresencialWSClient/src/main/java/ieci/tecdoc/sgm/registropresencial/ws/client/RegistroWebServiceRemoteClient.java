package ieci.tecdoc.sgm.registropresencial.ws.client;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.registro.BookInfo;
import ieci.tecdoc.sgm.core.services.registro.DistributionInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.FieldInfoQuery;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfoPersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegistroException;
import ieci.tecdoc.sgm.core.services.registro.ServicioRegistro;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.ListaPersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType;

import java.rmi.RemoteException;

public class RegistroWebServiceRemoteClient implements ServicioRegistro {

	private ServicioRegistroWebService_PortType service;

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#acceptDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void acceptDistribution(UserInfo user, String registerNumber,
			Integer bookId, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().acceptDistribution(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getFolderWS(
							registerNumber, bookId),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#acceptDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void acceptDistribution(UserInfo user, Integer distributionId,
			Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().acceptDistributionEx(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil
							.getDistributionInfoWS(distributionId),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));

			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#changeInputDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void changeInputDistribution(UserInfo user, String registerNumber,
			String code, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().changeInputDistribution(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getFolderWS(
							registerNumber, null),
					RegistroWebServiceRemoteClientUtil
							.getDistributionOptionsWS(code, 1),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#changeOutputDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void changeOutputDistribution(UserInfo user, String registerNumber,
			String code, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().changeOutputDistribution(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getFolderWS(
							registerNumber, null),
					RegistroWebServiceRemoteClientUtil
							.getDistributionOptionsWS(code, 1),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#createFolder(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.registro.FieldInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.PersonInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.DocumentInfo[],
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterInfo createFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo oRegInfo = getService()
					.createFolder(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getPersonInfoWS(inter),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									bookId, atts, documents),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterInfo(oRegInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oRegInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#consolidateFolder(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.registro.FieldInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.PersonInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.DocumentInfo[],
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterInfo consolidateFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo oRegInfo = getService()
					.consolidateFolder(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getPersonInfoWS(inter),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									bookId, atts, documents),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterInfo(oRegInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oRegInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#importFolder(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.registro.FieldInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.PersonInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.DocumentInfo[],
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterInfo importFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo oRegInfo = getService()
					.importFolder(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getPersonInfoWS(inter),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									bookId, atts, documents),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterInfo(oRegInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oRegInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getDocumentFolder(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public DocumentQuery getDocumentFolder(UserInfo user, Integer bookID,
			Integer folderId, Integer docID, Integer pageID, Entidad entidad)
			throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document oDocument = getService()
					.getDocumentFolder(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getDocumentWS(
									bookID, folderId, docID, pageID),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oDocument)) {
				return RegistroWebServiceRemoteClientUtil
						.getDocumentQuery(oDocument);
			} else {
				throw getRegistroException((IRetornoServicio) oDocument);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getInputFolderForNumber(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterWithPagesInfo getInputFolderForNumber(UserInfo user,
			Integer bookId, String folderNumber, Entidad entidad)
			throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder oFolder = getService()
					.getInputFolderForNumber(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									folderNumber, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oFolder)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterQueryInfo(oFolder);
			} else {
				throw getRegistroException((IRetornoServicio) oFolder);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getOutputFolderForNumber(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterWithPagesInfo getOutputFolderForNumber(UserInfo user,
			Integer bookId, String folderNumber, Entidad entidad)
			throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder oFolder = getService()
					.getOutputFolderForNumber(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									folderNumber, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oFolder)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterQueryInfo(oFolder);
			} else {
				throw getRegistroException((IRetornoServicio) oFolder);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getOutputRegister(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterWithPagesInfoPersonInfo getOutputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo oFolder = getService()
					.getOutputRegister(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									folderNumber, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oFolder)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterQueryInfoPersonInfo(oFolder);
			} else {
				throw getRegistroException((IRetornoServicio) oFolder);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getInputDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer, java.lang.Boolean,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public DistributionInfo[] getInputDistribution(UserInfo user,
			Integer state, Integer firstRow, Integer maxResults,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo oDistInfo = getService()
					.getInputDistribution(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getDistributionSearchCriteriaWS(state,
											firstRow, maxResults),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oDistInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getDistributionsInfo(oDistInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oDistInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getOutputDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer, java.lang.Boolean,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public DistributionInfo[] getOutputDistribution(UserInfo user,
			Integer state, Integer firstRow, Integer maxResults,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo oDistInfo = getService()
					.getOutputDistribution(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getDistributionSearchCriteriaWS(state,
											firstRow, maxResults),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oDistInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getDistributionsInfo(oDistInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oDistInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#rejectDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void rejectDistribution(UserInfo user, String registerNumber,
			String remarks, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().rejectDistribution(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getFolderWS(
							registerNumber, null),
					RegistroWebServiceRemoteClientUtil
							.getDistributionOptionsWS(remarks, 2),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#rejectDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void rejectDistribution(UserInfo user, Integer distributionId,
			String remarks, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().rejectDistributionEx(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil
							.getDistributionInfoWS(distributionId),
					RegistroWebServiceRemoteClientUtil
							.getDistributionOptionsWS(remarks, 2),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#archiveDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void archiveDistribution(UserInfo user, String registerNumber,
			Integer bookId, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().archiveDistribution(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getFolderWS(
							registerNumber, bookId),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#archiveDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void archiveDistribution(UserInfo user, Integer distributionId,
			Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().archiveDistributionEx(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil
							.getDistributionInfoWS(distributionId),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#updateFolder(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.registro.FieldInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.PersonInfo[],
	 *      ieci.tecdoc.sgm.core.services.registro.DocumentInfo[],
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterInfo updateFolder(UserInfo user, Integer bookId,
			Integer folderId, FieldInfo[] atts, PersonInfo[] inter,
			DocumentInfo[] documents, Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo oRegInfo = getService()
					.updateFolder(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getPersonInfoWS(inter),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									bookId, atts, documents),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterInfo(oRegInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oRegInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#findFolder(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.registro.FieldInfoQuery[],
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterInfo[] findFolder(UserInfo user, Integer bookId,
			FieldInfoQuery[] atts, Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo oRegInfo = getService()
					.findFolder(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getFolderSearchCriteriaWS(bookId, atts),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));

			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegistersInfo(oRegInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oRegInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#addDocument(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.registro.DocumentInfo[],
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void addDocument(UserInfo user, Integer bookId, Integer folderId,
			DocumentInfo[] documents, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRetorno = getService().addDocument(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getFolderWS(bookId,
							documents),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRetorno)) {
				return;
			} else {
				throw getRegistroException((IRetornoServicio) oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getInputRegister(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public RegisterWithPagesInfoPersonInfo getInputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo oFolder = getService()
					.getInputRegister(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									folderNumber, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oFolder)) {
				return RegistroWebServiceRemoteClientUtil
						.getRegisterQueryInfoPersonInfo(oFolder);
			} else {
				throw getRegistroException((IRetornoServicio) oFolder);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getInterestedInputRegister(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public PersonInfo[] getInterestedInputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		try {
			ListaPersonInfo list = (ListaPersonInfo) getService()
					.getInterestedInputRegister(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									folderNumber, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) list)) {
				if (list == null || list.getPersonsInfo() == null
						|| list.getPersonsInfo().length == 0)
					return null;
				return RegistroWebServiceRemoteClientUtil.getPersonInfo(list
						.getPersonsInfo());
			} else {
				throw getRegistroException((IRetornoServicio) list);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getInterestedOutputRegister(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public PersonInfo[] getInterestedOutputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		try {
			ListaPersonInfo list = (ListaPersonInfo) getService()
					.getInterestedOutputRegister(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									folderNumber, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) list)) {
				if (list == null || list.getPersonsInfo() == null
						|| list.getPersonsInfo().length == 0)
					return null;
				return RegistroWebServiceRemoteClientUtil.getPersonInfo(list
						.getPersonsInfo());
			} else {
				throw getRegistroException((IRetornoServicio) list);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#canCreateRegister(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public Boolean canCreateRegister(UserInfo user, Integer bookType,
			Integer oficAsoc, Integer onlyOpenBooks, Entidad entidad)
			throws RegistroException {
		try {
			RetornoServicio oRegInfo = getService().canCreateRegister(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getSearchCriteriaWS(
							bookType, oficAsoc, onlyOpenBooks),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#countInputDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer, java.lang.Boolean,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public Integer countInputDistribution(UserInfo user, Integer state,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		try {
			DistributionCountInfo distributionCountInfo = getService()
					.countInputDistribution(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getDistributionSearchCriteriaWS(
											typeBookRegisterDist, oficAsoc),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (distributionCountInfo != null) {
				return distributionCountInfo.getCount();
			} else {
				return new Integer(0);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#countOutputDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer, java.lang.Boolean,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public Integer countOutputDistribution(UserInfo user, Integer state,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		try {
			DistributionCountInfo distributionCountInfo = getService()
					.countOutputDistribution(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getDistributionSearchCriteriaWS(
											typeBookRegisterDist, oficAsoc),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (distributionCountInfo != null) {
				return distributionCountInfo.getCount();
			} else {
				return new Integer(0);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getBooksForType(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public BookInfo[] getBooksForType(UserInfo user, Integer bookType,
			Integer oficAsoc, Integer onlyOpenBooks, Entidad entidad)
			throws RegistroException {
		try {
			BooksInfo oBooksInfo = getService().getBooksForType(
					RegistroWebServiceRemoteClientUtil.getUserInfoWS(user),
					RegistroWebServiceRemoteClientUtil.getSearchCriteriaWS(
							bookType, oficAsoc, onlyOpenBooks),
					RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));

			if (ServiciosUtils.isReturnOK((IRetornoServicio) oBooksInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getBookInfo(oBooksInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oBooksInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getDistribution(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public DistributionInfo getDistribution(UserInfo user,
			Integer distributionId, Entidad entidad) throws RegistroException {
		try {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo oDistInfo = getService()
					.getDistribution(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil
									.getDistributionInfoWS(distributionId),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oDistInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getDistributionInfo(oDistInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oDistInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#getOfficeCanCreateRegisterByUser(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.Integer, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public OfficeInfo[] getOfficeCanCreateRegisterByUser(UserInfo user,
			Integer bookID, Entidad entidad) throws RegistroException {
		try {
			OfficesInfo oOfficesInfo = getService()
					.getOfficeCanCreateRegisterByUser(
							RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),
							RegistroWebServiceRemoteClientUtil.getFolderWS(
									bookID, null),
							RegistroWebServiceRemoteClientUtil
									.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oOfficesInfo)) {
				return RegistroWebServiceRemoteClientUtil
						.getOfficeInfo(oOfficesInfo);
			} else {
				throw getRegistroException((IRetornoServicio) oOfficesInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.core.services.registro.ServicioRegistro#existMatterTypeInOffice(ieci.tecdoc.sgm.core.services.registro.UserInfo,
	 *      java.lang.String, java.lang.String,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public Boolean existMatterTypeInOffice(UserInfo user, String matterTypeCode,
			String officeCode, Entidad entidad) throws RegistroException {
		try {
			RetornoServicio oRegInfo = getService().existMatterTypeInOffice(
				RegistroWebServiceRemoteClientUtil
									.getUserInfoWS(user),

									 matterTypeCode, officeCode,
				RegistroWebServiceRemoteClientUtil.getEntidadWS(entidad));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oRegInfo)) {
				return Boolean.TRUE;
			} else if (ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR.equals(oRegInfo.getErrorCode())){
					return Boolean.FALSE;
			}
			else{
				throw getRegistroException((IRetornoServicio) oRegInfo);
			}
		} catch (RemoteException e) {
			throw new RegistroException(
					RegistroException.
					EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}

	}

	public ServicioRegistroWebService_PortType getService() {
		return service;
	}

	public void setService(ServicioRegistroWebService_PortType service) {
		this.service = service;
	}

	private RegistroException getRegistroException(IRetornoServicio oReturn) {
		return new RegistroException(Long.valueOf(oReturn.getErrorCode())
				.longValue());
	}

}
