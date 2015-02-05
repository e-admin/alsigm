package ieci.tecdoc.sgm.admsistema.proceso.utils;

import java.io.File;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class ProcessUtils {

	/**
	 * Ejecuta un comando.
	 * @param command Comando a ejecutar.
	 * @return Valor de retorno.
	 */
	public static int executeCommand(String command) {
		return executeCommand(command, Logger.getLogger(ProcessUtils.class));
	}
	
	/**
	 * Ejecuta un comando.
	 * @param command Comando a ejecutar.
	 * @param logger Logger
	 * @return Valor de retorno.
	 */
	public static int executeCommand(String command, Logger logger) {
		return executeCommand(command, null, null, logger);
	}
	
	/**
	 * Ejecuta un comando.
	 * @param command Comando a ejecutar.
	 * @param envp Variables de entorno.
	 * @param workingDirectory Directorio de trabajo.
	 * @param logger Logger
	 * @return Valor de retorno.
	 */
	public static int executeCommand(String command, String[] envp, File workingDirectory, Logger logger) {

		int exitValue = -1;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Lanzando el comando: [" + command + "]");
		}

		try {
			
//			String osName = System.getProperty("os.name");
//            String[] cmd = new String[3];
//            
//            if (osName.equals( "Windows NT" ) ) {
//                cmd[0] = "cmd.exe" ;
//                cmd[1] = "/C" ;
//                cmd[2] = command;
//            } else if( osName.equals( "Windows 95" ) ) {
//                cmd[0] = "command.com" ;
//                cmd[1] = "/C" ;
//                cmd[2] = command;
//            }

            Process process = Runtime.getRuntime().exec(command, envp, workingDirectory);
	
	        // any output?
	        StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "PROCESS", logger);
	
			// any error message?
	        StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "PROCESS", logger);
	
	        // kick them off
	        outputGobbler.start();
	        errorGobbler.start();
	
			// Wait for process to terminate and catch any Exceptions.
	        exitValue = process.waitFor();
	        
	        
		} catch (Throwable t) {
			logger.error("Error ejecutando el comando: [" + command + "]", t);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Valor de retorno del comando [" + command + "]: " + exitValue);
		}

		return exitValue;
	}

}
