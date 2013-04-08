package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * Utilidades sobre ficheros.
 *
 */
public class FileUtils {
	
	/**
	 * Tamaño por defecto del buffer de datos
	 */
	public static final int BUFFER_SIZE = 2048;

	
	public static String getFileExtension(File file) {
		String ext = null;
		
		if (file != null) {
			ext = getFileExtension(file.getName());
		}
		
		return ext;
	}

	public static String getFileExtension(String fileName) {
		String ext = null;

		if (StringUtils.isNotBlank(fileName)) {
			int ix = fileName.lastIndexOf(".");
			if (ix >= 0) {
				ext = fileName.substring(ix + 1);
			}
		}
		
		return ext;
	}

	/**
	 * Obtiene el contenido de un fichero.
	 * @param file Fichero.
	 * @return Contenido del fichero.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static byte [] retrieveFile(File file) throws ISPACException {
		
		byte [] data = null;
		FileInputStream fis = null;
		
        try {

        	data = new byte[(int)file.length()];
        	
            fis = new FileInputStream(file);
            fis.read(data);
            fis.close();
            
            fis = null;
        } catch (IOException e) {
        	throw new ISPACException(e);
        } finally {
            try {
                if(fis != null) {
                    fis.close();
                }
            } catch(Exception e1) {}
        }
        
        return data;
	}

	public static byte [] retrieveFile(InputStream is) throws ISPACException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			copy(is, baos);
		} catch (IOException e) {
			throw new ISPACException(e);
		}
		
        return baos.toByteArray();
	}

	public static void writeFile(File file, byte[] content) throws ISPACException {
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(file);
			fos.write(content);
			fos.flush();
			fos.close();
			fos = null;
			
		} catch (IOException e) {
			throw new ISPACException(e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e1) {}
		}
	}

	/**
	 * Elimina todos los ficheros y subdirectorios a partir de un directorio recursivamente.
	 *
	 * @param dir Directorio del que se quiere borrar todos sus ficheros y subdirectorios.
	 */
	public static void deleteDirContents(File dir) {
		if (dir == null) {
			return;
		}

		// Llamar recursivamente a la función para eliminar los subdirectorios
		String[] allDirs = dir.list();
		if (allDirs != null) {
			
			for (int i = 0; i < allDirs.length; i++) {
				
				File subElement = new File(dir, allDirs[i]);
				if (subElement.isDirectory()) {
					deleteDirContents(subElement);
				}
			}
		}

		// Eliminar los ficheros y subdirectorios
		String[] allFilesAndDirsInThisDir = dir.list();
		if (allFilesAndDirsInThisDir != null) {
			
			for (int i = 0; i < allFilesAndDirsInThisDir.length; i++) {
				
				deleteFile(new File(dir, allFilesAndDirsInThisDir[i]));
			}
		}
	}
	
	/**
	 * Borra el archivo o directorio indicado.
	 *
	 * @param file Ruta del fichero o directorio a borrar.
	 */
	public static void deleteFile(File file) {
		file.delete();
	}
	
	public static String retrieveFileAsString(File file) throws ISPACException {
		
		BufferedReader reader = null;
		StringBuffer sBuffer = new StringBuffer();
		
		try {

			reader = new BufferedReader(new FileReader(file));	
			char[] chars = new char[BUFFER_SIZE];
			int numRead = 0;
			while((numRead = reader.read(chars)) > -1) {
				sBuffer.append(String.valueOf(chars, 0, numRead));	
			}
        }
		catch (IOException ioe) {
        	throw new ISPACException(ioe);
        }
        finally {
            try {
                if(reader != null) {
                	reader.close();
                }
            }
            catch(Exception e) {}
        }

		return sBuffer.toString();
	}
	
	public static String retrieveInputStreamAsString(InputStream inputStream) throws ISPACException {
		
		InputStreamReader reader = null;
		StringBuffer sBuffer = new StringBuffer();
		
		try {

			reader = new InputStreamReader(inputStream);	
			char[] chars = new char[BUFFER_SIZE];
			int numRead = 0;
			while((numRead = reader.read(chars)) > -1) {
				sBuffer.append(String.valueOf(chars, 0, numRead));	
			}
        }
		catch (IOException ioe) {
        	throw new ISPACException(ioe);
        }
        finally {
            try {
                if(reader != null) {
                	reader.close();
                }
            }
            catch(Exception e) {}
        }

		return sBuffer.toString();
	}

	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		
		if (in != null) {
			try {
				byte[] buffer = new byte[1024];
				int len = in.read(buffer, 0, buffer.length);
				while (len > 0) {
					out.write(buffer, 0, len);
					len = in.read(buffer, 0, buffer.length);
				}
			} finally {
				in.close();
			}
		}
	}

	public static void copy(File file, OutputStream out) throws IOException {
		if (file != null) {
			copy(new FileInputStream(file), out);
		}
	}
	
//	public static String getMimeType(byte[] file) throws ISPACException {
//			
//		try {
//			MagicMatch match = Magic.getMagicMatch(file);
//			return match.getMimeType();
//		} 
//		catch (Exception e) {
//		}
//		
//		return "";
//	}
	
}