package ieci.tecdoc.sgm.admsistema.proceso.managers;

import ieci.tecdoc.sgm.admsistema.proceso.utils.FileUtils;
import ieci.tecdoc.sgm.admsistema.proceso.utils.ProcessUtils;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public class RepoExportManagerImpl implements IRepoExportManager {

	/**
	 * Comando de importación del repositorio de documentos.
	 */
	private String importCommand = null;
	
	/**
	 * Comando de exportación del repositorio de documentos.
	 */
	private String exportCommand = null;


	/**
	 * Constructor.
	 */
	public RepoExportManagerImpl() {
		super();
	}

	public String getImportCommand() {
		return importCommand;
	}

	public void setImportCommand(String importCommand) {
		this.importCommand = importCommand;
	}

	public String getExportCommand() {
		return exportCommand;
	}

	public void setExportCommand(String exportCommand) {
		this.exportCommand = exportCommand;
	}
	
	/**
	 * Importa el repositorio de documentos.
	 * @param logger Logger
	 * @param repoDir Directorio con los datos del repositorio.
	 * @param options Opciones de importación del repositorio de documentos.
	 * @return true si el repositorio de documentos se ha importado correctamente.
	 */
	public boolean importRepository(Logger logger, File repoDir, Map options) {
		
		boolean ret = true;
		
		if (logger.isInfoEnabled()) {
			logger.info("Importación del repositorio: " + repoDir);
		}
		
		if (repoDir.isDirectory()) {
			
			String importEntityId = (String) options.get(Defs.ID_ENTIDAD_IMP);
			File newRepoDir = new File(repoDir.getParent(), importEntityId + "_" + repoDir.getName().substring(4));
			
			try {
				
				// Renombrar con el prefijo de la entidad de importación
				repoDir.renameTo(newRepoDir);
					
				if (logger.isDebugEnabled()) {
					logger.debug("Directorio renombrado temporalmente a [" + newRepoDir.getName() + "]");
				}
	
				// Componer el comando de exportación nativo a ejecutar
				String cmd = replaceOptions(importCommand, options);
				cmd = StringUtils.replace(cmd, "${path}", newRepoDir.getName());
				cmd = StringUtils.replace(cmd, "${basename}", newRepoDir.getParentFile().getAbsolutePath());
				cmd = StringUtils.replace(cmd, "${directory}", newRepoDir.getName());
	
				if (logger.isDebugEnabled()) {
					logger.debug("Comando de importación del repositorio transformado: " + cmd);
				}
				
				// Ejecutar el comando de importación del repositorio de documentos
				int exitValue = ProcessUtils.executeCommand(cmd, null, newRepoDir.getParentFile(), logger);
				if (exitValue != 0) {
					ret = false;
				}
				
			} finally {
				
				// Renombrar para dejar el nombre original
				newRepoDir.renameTo(repoDir);

				if (logger.isDebugEnabled()) {
					logger.debug("Directorio renombrado a [" + repoDir.getName() + "]");
				}
			}
		}
		
		return ret;
	}

	/**
	 * Exporta el repositorio de documentos.
	 * @param logger Logger
	 * @param repository Nombre del repositorio.
	 * @param exportDir Directorio donde se exportará el repositorio.
	 * @param options Opciones de exportación del repositorio de documentos.
	 * @return true si el repositorio de documentos se ha exportado correctamente.
	 */
	public boolean exportRepository(Logger logger, String repository, File exportDir, Map options) {
		
		boolean ret = true;

		if (logger.isInfoEnabled()) {
			logger.info("Exportando repositorio [" + repository + "] al directorio: " + exportDir.getAbsolutePath());
		}
		
		// Asegurarse de que existe el directorio del fichero
		FileUtils.ensureExistDirectory(exportDir);

		// Componer el comando de exportación nativo a ejecutar
		String cmd = replaceOptions(exportCommand, options);
		cmd = StringUtils.replace(cmd, "${path}", repository);
		cmd = StringUtils.replace(cmd, "${directory}", exportDir.getAbsolutePath());

		if (logger.isDebugEnabled()) {
			logger.debug("Comando de exportación del repositorio transformado: " + cmd);
		}
		
		// Ejecutar el comando de exportación del repositorio de documentos
		int exitValue = ProcessUtils.executeCommand(cmd, logger);
		if (exitValue != 0) {
			ret = false;
		}
		
		return ret;
	}
	
	/**
	 * Elimina los repositorios documentales.
	 * @param logger Logger.
	 * @param repositories Directorios de los repositorios documentales.
	 * @param options Opciones de la eliminación de repositorios.
	 * @return true si los repositorios se han eliminado correctamente.
	 */
	public boolean deleteRepositories(Logger logger, String[] repositories, Map options) {
		
		boolean ret = true;
		
		if (logger.isInfoEnabled()) {
			logger.info("Eliminando los repositorios [" + StringUtils.join(repositories, ",") + "]");
		}

		String ftpHost = (String) options.get(Defs.FTP_HOST_EXP);
        String ftpPort = (String) options.get(Defs.FTP_PUERTO_EXP);
        String ftpUser = (String) options.get(Defs.FTP_USUARIO_EXP);
        String ftpPass = (String) options.get(Defs.FTP_PASS_EXP);
        
        FTPClient ftpClient = new FTPClient();
        
        try {

        	// Conectar al servidor FTP
	        if (StringUtils.isBlank(ftpPort)) {
		        
	        	if (logger.isDebugEnabled()) {
		        	logger.debug("[FTP] Conectando al servidor: " + ftpHost);
		        }

		        ftpClient.connect(ftpHost);
	        } else {
	        	int iport = 21;
	        	try {
	        		iport = Integer.parseInt(ftpPort);
	        	} catch(NumberFormatException e) {}

	        	if (logger.isDebugEnabled()) {
		        	logger.debug("[FTP] Conectando al servidor: " + ftpHost + ":" + iport);
		        }

	        	ftpClient.connect(ftpHost, iport);
	        }
	        if (logger.isDebugEnabled()) {
	        	logger.debug("[FTP] connect: " + ftpClient.getReplyString());
	        }
	        
	        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {

	        	// Login FTP
		        if (logger.isDebugEnabled()) {
		        	logger.debug("[FTP] Autenticando el usuario: " + ftpUser);
		        }
		        ftpClient.login(ftpUser, ftpPass);
		        if (logger.isDebugEnabled()) {
		        	logger.debug("[FTP] login: " + ftpClient.getReplyString());
		        }
		        
		        // Eliminar los repositorios
		        if (repositories != null) {
		        	for (int i = 0; i < repositories.length; i++) {
		        		boolean res = deleteRepository(logger, ftpClient, repositories[i]);
		        		if (!res) {
		        			ret = false;
		        		}
		        	}
		        }
		        
		        // Logout
		        ftpClient.logout();
		        if (logger.isDebugEnabled()) {
		        	logger.debug("[FTP] logout: " + ftpClient.getReplyString());
		        }
		        
	        } else {
	        	logger.error("Error en la conexión al servidor de repositorios de documentos: " + ftpClient.getReplyString());
	        	ret = false;
	        }
	        
        } catch (Throwable t) {
        	logger.error("Error al eliminar los repositorios de documentos", t);
        	ret = false;
        } finally {
        	try {
        		if (ftpClient.isConnected()) {
        			ftpClient.disconnect();
        		}
        	} catch (IOException e) {
        		logger.warn("Error al desconectarse del repositorio FTP", e);
        	}
        }

        return ret;
	}

	protected boolean deleteRepository(Logger logger, FTPClient ftpClient, String repository) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Eliminando el repositorio [" + repository + "]...");
		}
		
		boolean res = false;
		
		try {
			
			if (logger.isDebugEnabled()) {
				ftpClient.pwd();
				logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
			}

			res = ftpClient.changeWorkingDirectory(repository);
			if (logger.isDebugEnabled()) {
				logger.debug("[FTP] cwd: " + ftpClient.getReplyString());
			}
			
			if (res) {

				if (logger.isDebugEnabled()) {
					ftpClient.pwd();
					logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
				}

				res = deleteRepositoryContent(logger, ftpClient, repository);

				if (logger.isDebugEnabled()) {
					ftpClient.pwd();
					logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
				}

				ftpClient.changeToParentDirectory();

				if (logger.isDebugEnabled()) {
					ftpClient.pwd();
					logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
				}

				if (ftpClient.removeDirectory(repository)) {
					if (logger.isInfoEnabled()) {
						logger.info("El repositorio [" + repository + "] se ha borrado con éxito: " + ftpClient.getReplyString());
					}
					res = true;
				} else {
					logger.warn("No se ha podido borrar el repositorio [" + repository + "]: " + ftpClient.getReplyString());
					res = false;
				}

	
			} else {
				logger.warn("El repositorio [" + repository + "] no existe");
				res = true;
			}
			
		} catch (Throwable t) {
			logger.error("Error al eliminar el repositorio [" + repository + "]", t);
			res = false;
		}
		
		return res;
	}
	
	protected boolean deleteRepositoryContent(Logger logger, FTPClient ftpClient, String repository) {
		
		boolean ret = true;
		
		try {
			
			FTPFile[] files = ftpClient.listFiles(".");
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						
						if (!".".equals(files[i].getName()) && !"..".equals(files[i].getName())) {
						
							if (logger.isDebugEnabled()) {
								ftpClient.pwd();
								logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
							}
	
							ftpClient.changeWorkingDirectory(files[i].getName());
							
							if (logger.isDebugEnabled()) {
								logger.debug("[FTP] cwd: " + ftpClient.getReplyString());
								ftpClient.pwd();
								logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
							}
	
							deleteRepositoryContent(logger, ftpClient, files[i].getName());
							
							if (logger.isDebugEnabled()) {
								ftpClient.pwd();
								logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
							}
	
							ftpClient.changeToParentDirectory();
							
							if (logger.isDebugEnabled()) {
								logger.debug("[FTP] cdup: " + ftpClient.getReplyString());
								ftpClient.pwd();
								logger.debug("[FTP] pwd: " + ftpClient.getReplyString());
							}
	
							
							if (logger.isDebugEnabled()) {
								logger.debug("Eliminando el directorio: " + files[i].getName());
							}
							
							if (ftpClient.removeDirectory(files[i].getName())) {
								if (logger.isDebugEnabled()) {
									logger.debug("Directorio [" + files[i].getName() + "] eliminado: " + ftpClient.getReplyString());
								}
							} else {
								logger.error("No se ha podido borrar el directorio [" + files[i].getName() + "]: " + ftpClient.getReplyString());
								ret = false;
							}
						}
						
					} else if (files[i].isFile()) {
						
						if (logger.isDebugEnabled()) {
							logger.debug("Eliminando el fichero: " + files[i].getName());
						}
						
						if (ftpClient.deleteFile(files[i].getName())) {
							if (logger.isDebugEnabled()) {
								logger.debug("Fichero [" + files[i].getName() + "] eliminado: " + ftpClient.getReplyString());
							}
						} else {
							logger.error("No se ha podido borrar el fichero [" + files[i].getName() + "]:" + ftpClient.getReplyString());
							ret = false;
						}
						
					}
				}
			}

		} catch (Throwable e) {
			logger.error("Error al eliminar el contenido del repositorio [" + repository + "]", e);
			ret = false;
		}

		return ret;
	}
	
	/**
	 * Copia y limpia el repositorio de documentos de una exportación.
	 * 
	 * @param logger
	 *            Logger.
	 * @param srcDir
	 *            Directorio de origen
	 * @param destDir
	 *            Directorio de destino
	 */
	public void cleanRepository(Logger logger, File srcDir, File destDir) {
		
		if ((srcDir != null) && srcDir.isDirectory() && (destDir != null)) {
			
			File[] srcHosts = srcDir.listFiles();
			if (srcHosts != null) {
				for (int contHosts = 0; contHosts < srcHosts.length; contHosts++) {
					File srcHostDir = srcHosts[contHosts];
					if (srcHostDir.isDirectory()) {
						
						// Crear el directorio del host
						File destHostDir = new File(destDir, srcHostDir.getName());
						if (destHostDir.mkdir()) {
							
							File[] srcRepoDirs = srcHostDir.listFiles();
							if (srcRepoDirs != null) {
								for (int contRepos = 0; contRepos < srcRepoDirs.length; contRepos++) {
									
									File srcRepoDir = srcRepoDirs[contRepos];
									if (srcRepoDir.isDirectory()) {

										// Crear el directorio del repositorio
										File destRepoDir = new File(destHostDir, srcRepoDir.getName());
										if (destRepoDir.mkdir()) {
											
											// Copiar el fichero REPLABEL
											File repLabelFile = new File(srcRepoDir, "REPLABEL");
											try {
												FileUtils.copyFileToDirectory(repLabelFile, destRepoDir);
											} catch (IOException e) {
												logger.warn("No se ha podido copiar el fichero [" + repLabelFile.getAbsolutePath() 
														+ "] al directorio [" + destRepoDir.getAbsolutePath() + "]");
											}
					
											// Crear los directorios de los volúmenes
											File[] srcVolDirs = srcRepoDir.listFiles();
											if (srcVolDirs != null) {
												for (int contVols = 0; contVols < srcVolDirs.length; contVols++) {
													
													File srcVolDir = srcVolDirs[contVols];
													if (srcVolDir.isDirectory()) {
														
														// Crear el directorio del volumen
														File destVolDir = new File(destRepoDir, srcVolDir.getName());
														if (destVolDir.mkdir()) {

															// Copiar el fichero VOLLABEL
															File volLabelFile = new File(srcVolDir, "VOLLABEL");
															try {
																FileUtils.copyFileToDirectory(volLabelFile, destVolDir);
															} catch (IOException e) {
																logger.warn("No se ha podido copiar el fichero [" + volLabelFile.getAbsolutePath() 
																		+ "] al directorio [" + destVolDir.getAbsolutePath() + "]");
															}

															// Crear el directorio 1
															File destVol1Dir = new File(destVolDir, "1");
															if (!destVol1Dir.mkdir()) {
																logger.warn("No se ha podido crear el directorio: " + destVol1Dir.getAbsolutePath());
															}
															
														} else {
															logger.warn("No se ha podido crear el directorio: " + destVolDir.getAbsolutePath());
														}
													}
												}
											}
											
										} else {
											logger.warn("No se ha podido crear el directorio: " + destRepoDir.getAbsolutePath());
										}
									}
								}
							}
							
						} else {
							logger.warn("No se ha podido crear el directorio: " + destHostDir.getAbsolutePath());
						}
					}
				}
			}
		}
	}
	
	protected String replaceOptions(String str, Map options) {
		
		if (StringUtils.isNotBlank(str) && (options != null)) {
			
			str = StringUtils.replace(str, "${home}", System.getProperty("user.home"));
			str = StringUtils.replace(str, "${date}", (String) options.get("CURRENT_DATE"));

			str = StringUtils.replace(str, "${exportEntityId}", (String) options.get(Defs.ID_ENTIDAD_EXP));

			str = StringUtils.replace(str, "${exportDbHost}", (String) options.get(Defs.BD_HOST_EXP));
			str = StringUtils.replace(str, "${exportDbPort}", (String) options.get(Defs.BD_PUERTO_EXP));
			str = StringUtils.replace(str, "${exportDbUsername}", (String) options.get(Defs.BD_USUARIO_EXP));
			str = StringUtils.replace(str, "${exportDbPassword}", (String) options.get(Defs.BD_PASS_EXP));

			str = StringUtils.replace(str, "${exportFtpHost}", (String) options.get(Defs.FTP_HOST_EXP));
			str = StringUtils.replace(str, "${exportFtpPort}", (String) options.get(Defs.FTP_PUERTO_EXP));
			str = StringUtils.replace(str, "${exportFtpUsername}", (String) options.get(Defs.FTP_USUARIO_EXP));
			str = StringUtils.replace(str, "${exportFtpPassword}", (String) options.get(Defs.FTP_PASS_EXP));

			str = StringUtils.replace(str, "${importEntityId}", (String) options.get(Defs.ID_ENTIDAD_IMP));
			
			str = StringUtils.replace(str, "${importDbHost}", (String) options.get(Defs.BD_HOST_IMP));
			str = StringUtils.replace(str, "${importDbPort}", (String) options.get(Defs.BD_PUERTO_IMP));
			str = StringUtils.replace(str, "${importDbUsername}", (String) options.get(Defs.BD_USUARIO_IMP));
			str = StringUtils.replace(str, "${importDbPassword}", (String) options.get(Defs.BD_PASS_IMP));

			str = StringUtils.replace(str, "${importFtpHost}", (String) options.get(Defs.FTP_HOST_IMP));
			str = StringUtils.replace(str, "${importFtpPort}", (String) options.get(Defs.FTP_PUERTO_IMP));
			str = StringUtils.replace(str, "${importFtpUsername}", (String) options.get(Defs.FTP_USUARIO_IMP));
			str = StringUtils.replace(str, "${importFtpPassword}", (String) options.get(Defs.FTP_PASS_IMP));

		}
		
		return str;
	}

}
