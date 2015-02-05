package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;

/**
 * Utilidad para leer ficheros.
 */
public class FileHelper
{

	public static final String SEPARADOR_DIRECTORIOS = File.separator;
	
	/**
	 * Obtiene el contenido de un fichero de texto.
	 * @param filename Nombre del fichero.
	 * @return Contenido del fichero de texto.
	 * @throws IOException si ocurre algún error.
	 */
	public static String getTextFileContent(String filename) throws IOException
	{
		StringBuffer xml = new StringBuffer();
		FileInputStream fis = new FileInputStream(filename);
		byte [] buffer = new byte[1024];
		
		int i = fis.read(buffer, 0, buffer.length);
		while (i > -1)
		{
			xml.append(new String(buffer, 0, i));
			i = fis.read(buffer, 0, buffer.length);
		}
		
		fis.close();
		
		return xml.toString();
	}
	
	public static String getTextFileContent(InputStream inputStream) throws IOException
	{
		StringBuffer xml = new StringBuffer();
		byte [] buffer = new byte[1024];
		
		int i = inputStream.read(buffer, 0, buffer.length);
		while (i > -1)
		{
			xml.append(new String(buffer, 0, i));
			i = inputStream.read(buffer, 0, buffer.length);
		}
		
		inputStream.close();
		
		return xml.toString();
	}
	
	public static void write(String path, String content)throws IOException
	{
		
		
		FileOutputStream file=new FileOutputStream(path.trim());
		file.write(content.getBytes());
		file.close();
		
	}

	public static String getNormalizedToSystemPath(String path){
		String normalizedPath = FilenameUtils.normalize(path);
		return FilenameUtils.separatorsToSystem(normalizedPath);
	}
	
	public static String getNormalizedToSystemFilePath(String directorio, String fichero){
		
		String nombreDirectorio = directorio;
		if (nombreDirectorio==null)
			nombreDirectorio="";
		
		String nombreFichero = fichero;
		if (nombreFichero==null)
			nombreFichero="";
			
		return getNormalizedToSystemPath(nombreDirectorio + SEPARADOR_DIRECTORIOS + nombreFichero);
	}
	
	
}
