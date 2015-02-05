package ieci.tecdoc.sgm.antivirus;

import ieci.tecdoc.sgm.antivirus.util.Informacion;
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.antivirus.AntivirusException;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

public class Antivirus {

	private final Logger logger = Logger.getLogger(Antivirus.class);
			
	protected String rutaAntivirus = null;
	protected String rutaTemporal = null;
	protected String parametros = null;
	protected String[] cmd = null;
	protected static final int SALIDA_ERRONEA = -999;
	
	public boolean comprobarFichero(byte[] fichero, boolean borrar) throws AntivirusException {
		String rutaFichero = null;
		try {
			if (getRutaAnivirus() != null && !getRutaAnivirus().equals("")) {
				rutaFichero = almacenarFichero(fichero);
				return comprobarFichero(rutaFichero);
			} else {
				throw new AntivirusException(AntivirusException.EXC_NO_ANTIVIRUS);
			}
		} catch(AntivirusException e) {
			throw e;
		} catch(Exception e) {
			throw new AntivirusException(AntivirusException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		} finally {
			if (borrar)
				borrarFichero(rutaFichero);
		}
	}
	
	public boolean comprobarFichero(String rutaFichero, boolean borrar) throws AntivirusException {
		try {
			return comprobarFichero(rutaFichero);
		} catch(AntivirusException e) {
			throw e;
		} catch(Exception e) {
			throw new AntivirusException(AntivirusException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		} finally {
			if (borrar)
				borrarFichero(rutaFichero);
		}
	}
	
	protected boolean comprobarFichero(String rutaFichero) throws AntivirusException {
		return true;
	}
	
	protected int ejecucionAntivirus() throws AntivirusException {
		int exitVal = SALIDA_ERRONEA;
		
		try
	    {
			logger.debug("Inicio proceso de antivirus");
			
	        StringBuffer comando = new StringBuffer();
	        for(int i=0; i<cmd.length; i++)
	        	comando.append(cmd[i]).append(" ");
	        
	        Runtime rt = Runtime.getRuntime();
	        Process proc = rt.exec(comando.toString());
	        
	        Informacion error = new Informacion(proc.getErrorStream(), "ERROR");            
	        Informacion output = new Informacion(proc.getInputStream(), "SALIDA");
	            
	        error.start();
	        output.start();
	                                
	        exitVal = proc.waitFor();
	        
	        logger.debug("Valor de salida del antivirus: " + exitVal); 
	       
	    } catch (Throwable t) {
	    	logger.error("Error en la ejecución del antivirus [ejecucionAntivirus][Throwable]", t.fillInStackTrace());
	    	throw new AntivirusException(AntivirusException.EXC_COMPROBAR_VIRUS, t.getMessage(), t);
		}
	    
	    return exitVal;
	}
	
	protected String almacenarFichero(byte[] fichero) throws AntivirusException {
		File file;
		FileOutputStream fos = null;
		String rutaFichero = rutaTemporal + "/" + new Guid().toString();
		
		try {
			file = new File(rutaFichero);
			fos = new FileOutputStream(file);

			fos.write(fichero);
			fos.flush();
		} catch (Exception e) {
			logger.error("No se ha podido almacenar el fichero: " + rutaFichero, e.getCause());
			throw new AntivirusException(AntivirusException.EXC_ALMACENAR_FICHERO, e.getMessage(), e);
		} finally {
			try {
				if (fos != null) { 
					fos.close();
					fos = null;
				}
			} catch (Exception e1) { }
		}
		
		return rutaFichero;
	}
	
	protected void borrarFichero(String rutaFichero) {
		try {
			if (rutaFichero != null) {
				File f = new File (rutaFichero);
				if (f.exists())
					f.delete();
			}
		} catch(Exception e) {
			logger.error("No se ha podido borrar el fichero: " + rutaFichero, e.getCause());
		}
	}
	
	public void setRutaAntivirus(String _rutaAntivirus) {
		rutaAntivirus=_rutaAntivirus;
	}
	
	public String getRutaAnivirus() {
		return rutaAntivirus;
	}
	
	public void setParametros(String _parametros) {
		parametros=_parametros;
	}
	
	public String getParametros() {
		return parametros;
	}
	
	public void setRutaTemporal(String _rutaTemporal) {
		rutaTemporal=_rutaTemporal;
	}
	
	public String getRutaTemporal() {
		return rutaTemporal;
	}
}
