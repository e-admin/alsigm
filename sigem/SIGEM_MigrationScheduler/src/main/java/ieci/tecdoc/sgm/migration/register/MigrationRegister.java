package ieci.tecdoc.sgm.migration.register;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentsPagesDto;
import ieci.tecdoc.sgm.migration.mgr.dto.ResultadoMigracionDto;
import ieci.tecdoc.sgm.migration.utils.Constantes;
import ieci.tecdoc.sgm.migration.utils.Utils;
import ieci.tecdoc.sgm.migration.utils.UtilsService;
import ieci.tecdoc.sgm.registropresencial.ws.server.Document;
import ieci.tecdoc.sgm.registropresencial.ws.server.Documents;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Fields;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService;
import ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class MigrationRegister {

	private static final Logger logger = Logger.getLogger(MigrationRegister.class);
	
	/**
	 * Método que realiza el proceso de migración de registros 
	 * @param oServicioOrigen - SW de Registro de SIGEM Housing
	 * @param oServicioDestino - SW de Registro de SIGEM UAM
	 * @param userOrigen - Usuario de registro de SIGEM Housing
	 * @param userDestino - Usuario de registro de SIGEM UAM
	 * @param entidadOrigen - Entidad de registro de SIGEM Housing
	 * @param entidadDestino - Entidad de registro de SIGEM UAM
	 * @param registerInfo - Información acerca del registro
	 * @throws Exception - Se lanza una excepción genérica en caso de error
	 */
	public void registerProcess(ServicioRegistroWebService oServicioOrigen, ServicioRegistroWebService oServicioDestino, 
			UserInfo userOrigen, UserInfo userDestino, Entidad entidadOrigen, Entidad entidadDestino, 
				ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo) throws Exception {
		
		ieci.tecdoc.sgm.registropresencial.ws.server.Folder folderRegister = null;
		ieci.tecdoc.sgm.registropresencial.ws.server.Folder folderRegisterOrigen = null;
		ieci.tecdoc.sgm.registropresencial.ws.server.Folder folderRegisterDestino = null;
		ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo personInfo = null; 
		ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter = null;
		
		if(registerInfo.getNumber() != null) {
			Utils.trace("Procesamos el registro: " + registerInfo.getNumber());
			folderRegister = getFolderRegister(registerInfo);
			
			// Comprobar en el Registro de Destino que el registro a Consolidar
			// no esté Consolidado previamente
			folderRegisterDestino = getFolderDestino(registerInfo, oServicioDestino, userDestino, folderRegister, entidadDestino); 
			
			if(!UtilsService.isResponeOK(folderRegisterDestino.getErrorCode(), folderRegisterDestino.getReturnCode())) {
				Utils.trace("El registro NO ha sido Consolidado, procedemos a ello.. ");
				
				// Obtener los Datos del Registro
				folderRegisterOrigen = getFolderRegisterOrigen(registerInfo, oServicioOrigen, userOrigen, folderRegister, entidadOrigen);
				
				if(UtilsService.isResponeOK(folderRegisterOrigen.getErrorCode(), folderRegisterOrigen.getReturnCode())) {
				
					// Obtener sus documentos anexos
					DocumentsPagesDto documentos = UtilsService.getDocuments(oServicioOrigen, userOrigen, 
							entidadOrigen, registerInfo.getBookId(), folderRegisterOrigen);
					
					// Obtenemos los remitentes del registro
					personInfo = getPersonInfo(registerInfo, oServicioOrigen, userOrigen, folderRegister, entidadOrigen);
					inter = UtilsService.getPersonInfo(folderRegisterOrigen, personInfo);
					folderRegisterOrigen.setBookId(UtilsService.getBookId(registerInfo.getBookId()));
					
					// Establecemos la carpeta de destino
					Folder folderDestino = getFolderDestino(registerInfo, folderRegisterOrigen, documentos);
					
					try {
						// Consolidar los Registros en el Registro Destino
						RegisterInfo resultado = oServicioDestino.importFolder(userDestino, inter, folderDestino, entidadDestino);
						if(UtilsService.isResponeOK(resultado.getErrorCode(), resultado.getReturnCode())) {
							try {
								Utils.trace("Registro importado...");
								// Establecer el Flg de Consolidado = 1
								folderDestino.setFolderId(folderRegisterOrigen.getFolderId());
								folderDestino.setBookId(UtilsService.getBookId(registerInfo.getBookId()));
								resultado = oServicioOrigen.updateFolder(userOrigen, inter, Utils.modifyFieldConsolidated(folderDestino), entidadOrigen);
								if(UtilsService.isResponeOK(resultado.getErrorCode(), resultado.getReturnCode())) {
									Utils.trace("OK: Registro '" + folderRegister.getFolderNumber() + "' consolidado con éxito");
								} else {
									logger.error("ERROR: No se ha podido establecer el campo 'Fld" + Config.getInstance().getFieldIdConsolidated()+ "' ");
								}
							} catch (Exception e) {
								logger.error("ERROR: No se ha podido establecer el campo 'Fld" + Config.getInstance().getFieldIdConsolidated()+ "' " +
										"a consolidado para el registro '" + folderRegister.getFolderNumber() + "'");
								throw new Exception(e);
							}
						} else {
							logger.error("ERROR: No se ha podido consolidar el registro '" + folderRegister.getFolderNumber() + "'. Revise el log del SW de Registro Presencial ");
							throw new Exception("ERROR: No se ha podido consolidar el registro '" + folderRegister.getFolderNumber() + "'. Revise el log del SW de Registro Presencial ");
						}
					} catch (Exception e) {
						folderDestino.setFolderId(folderRegisterOrigen.getFolderId());
						folderDestino.setBookId(UtilsService.getBookId(registerInfo.getBookId()));
						registroNoConsolidado(e, oServicioOrigen, userOrigen, inter, folderDestino, entidadOrigen, folderRegister.getFolderNumber());
						throw new Exception(e);
					}	
				} else {
					logger.error("ERROR: No se puede obtener la carpeta para el registro: " + folderRegister.getFolderNumber());
					throw new Exception("ERROR: No se puede obtener la carpeta para el registro: " + folderRegister.getFolderNumber());
				}
			} else {
				/*Utils.trace("El registro '" + registerInfo.getNumber() + "' ya ha sido consolidado. Modificamos el Fld2000 a 'Consolidado' (1) y Fld2001 a 'Borrado de Documentos' (1) ");
				folderRegisterOrigen = getFolderRegisterOrigen(registerInfo, oServicioOrigen, userOrigen, folderRegister, entidadOrigen);
				if(UtilsService.isResponeOK(folderRegisterOrigen.getErrorCode(), folderRegisterOrigen.getReturnCode())) {
					personInfo = getPersonInfo(registerInfo, oServicioOrigen, userOrigen, folderRegister, entidadOrigen);
					inter = UtilsService.getPersonInfo(folderRegisterOrigen, personInfo);
					folderRegisterOrigen.setBookId(UtilsService.getBookId(registerInfo.getBookId()));
					Document[] document = new Document[0];
					Documents documentos = new Documents();
					documentos.setDocuments(document);
					folderRegisterOrigen.setDocumentos(documentos);
					folderRegisterOrigen.setFolderId(registerInfo.getFolderId());
					oServicioOrigen.updateFolder(userOrigen, inter, Utils.modifyFieldConsolidatedDeleteDocument(folderRegisterOrigen), entidadOrigen);
					Utils.trace("El registro '" + registerInfo.getNumber() + "' modificado con éxito");
				}*/
				logger.error("El registro '" + registerInfo.getNumber() + "' ya ha sido consolidado ");
				throw new Exception("El registro '" + registerInfo.getNumber() + "' ya ha sido consolidado");
			}
			
		} else {
			logger.error("ERROR: El número de registro NO existe: ");
			throw new Exception("ERROR: El número de registro NO existe: ");
		}
	}

	
	/**
	 * Crea el mensaje de texto que se envía en el mail
	 * @param resultado - Resultado de la migración
	 * @return (String) Mensaje a enviar
	 */
	public String getMessage(ResultadoMigracionDto resultado) {
		StringBuilder build = new StringBuilder();
		try { build.append("Resultado de la sincronización de registros electrónicos a fecha " + Utils.getDateResult(resultado.getFechaInicio()) + ":\n");
		} catch (ParseException e) { e.printStackTrace();}
		build.append("\n");
		build.append("\t" + "- [OK]:" + resultado.getTotalConsolidadosEntrada() + " Registros de entrada.");
		if (resultado.getTotalConsolidadosEntrada() > 0) {
			build.append("(" + getNumbersRegisters(resultado.getNumbersRegistersConsolidadosEntrada()) + ")");
		}
		build.append("\n\t" + "- [OK]:" + resultado.getTotalConsolidadosSalida() + " Registros de salida.");
		if (resultado.getTotalConsolidadosSalida() > 0) {
			build.append("(" + getNumbersRegisters(resultado.getNumbersRegistersConsolidadosSalida()) + ")");
		}
		build.append("\n");
		build.append("\t" + "- [KO]:" + resultado.getTotalNoConsolidadosEntrada() + " Registros de entrada.");
		if (resultado.getTotalNoConsolidadosEntrada() > 0) {
			build.append("(" + getNumbersRegisters(resultado.getNumbersRegistersNoConsolidadosEntrada()) + ")");
		}
		build.append("\n\t" + "- [KO]:" + resultado.getTotalNoConsolidadosSalida() + " Registros de salida.");
		if (resultado.getTotalNoConsolidadosSalida() > 0) {
			build.append("(" + getNumbersRegisters(resultado.getNumbersRegistersNoConsolidadosSalida()) + ")");
		}
		return build.toString();
	}
	
	/**
	 * Crea el asunto del mensaje que se envía en el mail
	 * @param resultado - Resultado de la migración
	 * @return (String) Asunto del mensaje a enviar
	 */
	
	public String getAsunto(ResultadoMigracionDto resultado) {
		StringBuilder build = new StringBuilder();
		int totalOK = 0;
		if(resultado.getTotalNoConsolidadosEntrada() > 0 || resultado.getTotalNoConsolidadosSalida() > 0) {
			build.append("[KO] Sincronización ");
			build.append("[" + resultado.getFechaInicio() + " Inicio] ");
			build.append("[" + resultado.getFechaFin() + " Fin]");
		} else {
			build.append("[OK] Sincronización ");
			totalOK = resultado.getTotalConsolidadosEntrada() + resultado.getTotalConsolidadosSalida();
			build.append("[" + totalOK + " nuevos registros]");
			build.append("[" + resultado.getFechaInicio() + " Inicio] ");
			build.append("[" + resultado.getFechaFin() + " Fin]");
		}
		
		return build.toString();
	}
	
	
	/**
	 * Método que establece el flg de consolidado a 0
	 * @param e - Exception 
	 * @param oServicioDestino - SW del Sigem de origen (Housing)
	 * @param userDestino - Usuario de registro en el sigem de destino (UAM)
	 * @param inter - PersonInfo (Remitentes)
	 * @param folderDestino - Carpeta de destino (UAM)
	 * @param entidadDestino - Código de entidad de destino (UAM)
	 * @param numberRegister - Número de registro
	 * @throws MigrationException 
	 * @throws RemoteException 
	 */
	private void registroNoConsolidado(Exception e, ServicioRegistroWebService oServicioDestino, UserInfo userDestino, 
			PersonInfo[] inter, Folder folderDestino, Entidad entidadDestino, String numberRegister) throws RemoteException, MigrationException {
		logger.error("ERROR: No se ha podido crear la carpeta para el registro '" + numberRegister + "'");
		oServicioDestino.updateFolder(userDestino, inter, Utils.modifyFieldNotConsolidated(folderDestino), entidadDestino);
		throw new MigrationException(e);
	}
	
	/**
	 * Comprueba si el registro es de entrada o salida
	 * @param registerInfo - Información del registro
	 * @return (boolean) - True: Entrada; False: Salida
	 * @throws MigrationException - Excepción lanzada
	 */
	private boolean isEntrada(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo) throws MigrationException {
		if(Config.getInstance().getOrigenIdBookEntrada().equalsIgnoreCase(registerInfo.getBookId())) return true;
		else return false;
	}
	
	/**
	 * 
	 * @param registerInfo - Información del registro
	 * @param oServicioDestino - SW de Registro de SIGEM UAM
	 * @param userDestino - Usuario de registro de SIGEM UAM
	 * @param folderRegister - Carpeta con los datos del registro
	 * @param entidadDestino - Entidad de SIGEM UAM
	 * @return (Folder) - Carpeta con los datos del registro
	 * @throws MigrationException - Excepción lanzada
	 * @throws RemoteException - Excepción lanzada en caso de error de los SW
	 */
	private Folder getFolderDestino(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo, 
			ServicioRegistroWebService oServicioDestino, UserInfo userDestino, Folder folderRegister, 
			Entidad entidadDestino) throws MigrationException, RemoteException {
		if(isEntrada(registerInfo))return oServicioDestino.getInputFolderForNumber(userDestino, folderRegister, entidadDestino);
		else return oServicioDestino.getOutputFolderForNumber(userDestino, folderRegister, entidadDestino);
	}
	
	/**
	 * 
	 * @param registerInfo - Información del registro
	 * @return (Folder) - Carpeta con información del registro
	 * @throws MigrationException - Excepción lanzada
	 */
	private Folder getFolderRegister(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo) throws MigrationException {
		if(isEntrada(registerInfo)) return UtilsService.getNumerRegisterEntrada(registerInfo.getNumber());
		else return UtilsService.getNumerRegisterSalida(registerInfo.getNumber());
	}

	/**
	 * 
	 * @param registerInfo - Información del registro
	 * @param oServicioOrigen - SW de Registro presencial de SIGEM Housing
	 * @param userOrigen - Usuario de registro de SIGEM Housing
	 * @param folderRegister - Carpeta de registro
	 * @param entidadOrigen - Entidad de SIGEM Housing
	 * @return (Folder) - Carpeta con los datos del registro
	 * @throws RemoteException - Excepción lanzada en caso de error de los SW
	 * @throws MigrationException - Excepción lanzada
	 */
	private Folder getFolderRegisterOrigen(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo, 
			ServicioRegistroWebService oServicioOrigen, UserInfo userOrigen, Folder folderRegister, Entidad entidadOrigen) throws RemoteException, MigrationException {
		if(isEntrada(registerInfo)) return oServicioOrigen.getInputFolderForNumber(userOrigen, folderRegister, entidadOrigen);
		else return oServicioOrigen.getOutputFolderForNumber(userOrigen, folderRegister, entidadOrigen);
	}
	
	
	/**
	 * 
	 * @param registerInfo - Información del registro
	 * @param oServicioOrigen - SW de Registro presencial de SIGEM Housing
	 * @param userOrigen - Usuario de registro de SIGEM Housing
	 * @param folderRegister - Carpeta de registro
	 * @param entidadOrigen - Entidad de SIGEM Housing
	 * @return (FolderWithPersonInfo) - Carpeta con los documentos anexos del registro
	 * @throws RemoteException - Excepción lanzada en caso de error de los SW
	 * @throws MigrationException - Excepción lanzada
	 */
	private FolderWithPersonInfo getPersonInfo(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo, 
			ServicioRegistroWebService oServicioOrigen, UserInfo userOrigen, Folder folderRegister, 
			Entidad entidadOrigen) throws RemoteException, MigrationException {
		if(isEntrada(registerInfo)) return oServicioOrigen.getInputRegister(userOrigen, folderRegister, entidadOrigen);
		else return oServicioOrigen.getOutputRegister(userOrigen, folderRegister, entidadOrigen);
	}
	
	
	/**
	 * 
	 * @param registerInfo - Información del registro
	 * @param folderRegisterOrigen -  Carpeta de registrode SIGEM Housing
	 * @param documentos - Documentos anexos al registro
	 * @return (Folder) - Carpeta de registro
	 * @throws RemoteException - Excepción lanzada en caso de error de los SW
	 * @throws MigrationException - Excepción lanzada
	 */
	private Folder getFolderDestino(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo, Folder folderRegisterOrigen, 
			DocumentsPagesDto documentos) throws RemoteException, MigrationException {
		if(isEntrada(registerInfo)) return UtilsService.getFolderDestinoEntrada(folderRegisterOrigen, documentos);
		else return UtilsService.getFolderDestinoSalida(folderRegisterOrigen, documentos);
	}
	
	
	/**
	 * 
	 * @param registerInfo - Información del registro
	 * @param folderRegisterOrigen -  Carpeta de registrode SIGEM Housing
	 * @param documentos - Documentos anexos al registro
	 * @return (Folder) - Carpeta de registro
	 * @throws RemoteException - Excepción lanzada en caso de error de los SW
	 * @throws MigrationException - Excepción lanzada
	 */
	private Folder getFolderOrigen(ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo registerInfo, Folder folderRegisterOrigen, 
			DocumentsPagesDto documentos) throws RemoteException, MigrationException {
		if(isEntrada(registerInfo)) return UtilsService.getFolderOrigenEntrada(folderRegisterOrigen, documentos);
		else return UtilsService.getFolderOrigenSalida(folderRegisterOrigen, documentos);
	}
	
	
	/**
	 * Formatea el listado de números de registros que se pasan como parámetro
	 * El formato es xxxxxx,xxxxxx,xxxxxx,xxxxx,
	 * @param numerosRegistros - Listado de número de registro
	 * @return
	 */
	private String getNumbersRegisters(List<String> numerosRegistros) {
		String numeroRegistro = null;
		String result = "";
		for(int i = 0; i < numerosRegistros.size(); i++) {
			numeroRegistro = (String)numerosRegistros.get(i);
			if(numeroRegistro != null && !numeroRegistro.equals("")) {
				result += numeroRegistro + ",";
			}
		}
		if(!result.equals("")) result = result.substring(0, result.length() -1);
		return result;
	}
}
