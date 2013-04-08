package ieci.tdw.applets.idocscan.helpers;

import ieci.tdw.applets.idocscan.Debug;
import ieci.tdw.applets.idocscan.Messages;
import ieci.tdw.applets.idocscan.exceptions.InstallerException;
import ieci.tdw.applets.idocscan.exceptions.UserCancelException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

/**
 * Utilidad para descomprimir ficheros.
 *
 */
public class ZipHelper {

	//========================================================================
	// Constantes para los métodos de reemplazo de ficheros
	//========================================================================
    private static final int ASK_REPLACE 	= 0;
    private static final int ALL_REPLACE 	= 1;
    private static final int NONE_REPLACE 	= 2;
	//========================================================================

    /** Método de reemplazo de ficheros. */
    private int replaceMethod = ASK_REPLACE;

    
    /**
     * Obtiene una instancia de la clase.
     * @return Instancia de la clase.
     */
    public static synchronized ZipHelper getInstance() {
    	return new ZipHelper();
    }
   
	/**
	 * Descomprime el fichero en el directorio especificado.
	 * @param zipFile Nombre del fichero comprimido.
	 * @param dir Directorio donde dejar los ficheros.
	 * @param replaceMethod Método de reemplazo de ficheros.
	 * @throws InstallerException si ocurre algún error.
	 */
	public void unzip(URL zipFile, File dir) 
			throws InstallerException {
		
		InputStream is = null;
        
        try {

			Debug.log("Extrayendo " + zipFile + " al directorio: "
					+ dir.getAbsolutePath());

        	is = zipFile.openStream();
        	ZipInputStream zis = new ZipInputStream(is);
        	ZipEntry zipEntry = zis.getNextEntry();
	        
	        while (zipEntry != null) {
	        	unzip(zipEntry, zis, dir);
		        zipEntry = zis.getNextEntry();
	        }
	        
		} catch (InstallerException e) {
			Debug.log("Error extrayendo el zip: " + e.toString());
			e.printStackTrace();
	        throw e;
		} catch (IOException e) {
			Debug.log("Error extrayendo el zip: " + e.toString());
			e.printStackTrace();
	        throw new InstallerException("installer.exception.unzip", 
	        		new Object[] { dir }, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
    }	
	
	private void unzip(ZipEntry zipEntry, ZipInputStream zis, File directory) 
			throws IOException, InstallerException {
		
        File entryFile = new File(directory, zipEntry.getName());
        
        if (entryFile.exists() && !replace(entryFile.getAbsolutePath())) { 
        	Debug.log("Dejando versión existente de " + zipEntry.getName());
        } else {
	        FileOutputStream fos = null;
	        byte buffer[] = new byte[1024];
	        
	        try {
	        	Debug.log("Extrayendo " + zipEntry.getName() + "...");
		        fos = new FileOutputStream(entryFile);
		        int i = zis.read(buffer, 0, buffer.length);
		        while(i > 0) { 
		            fos.write(buffer, 0, i);
		            i = zis.read(buffer, 0, buffer.length);
		        }
	        } finally {
	        	if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {}
	        	}
	        }
        }
	}
	
	private boolean replace(String fileName) throws InstallerException {
		boolean result = false;

        if (replaceMethod == ALL_REPLACE) {
            result = true;
        } else if(replaceMethod == NONE_REPLACE) { 
            result = false;
        } else if (replaceMethod == ASK_REPLACE) {

	        String options[] = { 
	        		Messages.getString("applet.button.yes"),
	        		Messages.getString("applet.button.no"),
	        		Messages.getString("applet.button.all"),
	        		Messages.getString("applet.button.noone"),
	        		Messages.getString("applet.button.cancel") };
			
	        int selectedOption = JOptionPane.showOptionDialog(null, 
	        		Messages.getString("applet.replace.fileExists.message", 
	        				new Object[] { fileName } ),
	        		Messages.getString("applet.replace.title"), 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null, options, options[1]);
			
			switch (selectedOption) {
		
				case 2:
					replaceMethod = ALL_REPLACE;
				case 0:
					result = true;
					break;
		
				case 3:
					replaceMethod = NONE_REPLACE;
				case 1:
					result = false;
					break;
		
				default:
					throw new UserCancelException();
			}
        }

		return result;
	}

}
