package es.ieci.tecdoc.fwktd.dir3.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


public class ZipUtils {

	private static ZipUtils _instance = new ZipUtils();

	/**
	 * Constructor protegido para evitar la creación de instancias desde otras clases
	 */
	protected ZipUtils() {}

	/**
	 * Obtiene la instancia única de la clase
	 * @return La instancia única de la clase
	 */
	public static ZipUtils getInstance()
	{
		return _instance;
	}

	/**
	 * Descomprime un
	 * @param zipFileName
	 * @param outputDirectory
	 * @throws ZipException
	 * @throws IOException
	 */
	public List<String> unzipFile(String zipFileName, String outputDirectory) throws ZipException, IOException
	{
		//Datos para el buffer de escritura del contenido del Zip
		int BUFFER = 2048;
		byte[] data = new byte[BUFFER];
		int currentByte;

		//Lista de ficheros que se devolvera
		List<String> destFileList = new ArrayList<String>();

		//Cargar el fichero Zip
		File file = new File(zipFileName);
		ZipFile zipFile = new ZipFile(file);

		//Obtener las entradas (ficheros y directorios) del fichero zip
		Enumeration zipFileEntries = zipFile.entries();

		while (zipFileEntries.hasMoreElements())
		{
			ZipEntry zipEntry = (ZipEntry)zipFileEntries.nextElement();
			//Obtener el nombre del Fichero o Directorio de la entrada actual a partir
			//del directorio de salida
			File destFile = new File(outputDirectory, zipEntry.getName());
			//Crear la estructura de directorios si es necesaria
			destFile.getParentFile().mkdirs();

			if (!zipEntry.isDirectory())
			{
				//Añadir fichero a la lista de resultados
				destFileList.add(destFile.getAbsolutePath());

				//Flujo de entrada de los datos del fichero comprimido
				BufferedInputStream bufferedInputStream =
					new BufferedInputStream(zipFile.getInputStream(zipEntry));

				//Fichero destino de los datos del fichero comprimido
				FileOutputStream fileOutputStream = new FileOutputStream(destFile);

				//Flujo de salida al fichero destino de los datos del fichero comprimido
				BufferedOutputStream bufferedOutputStream =
					new BufferedOutputStream(fileOutputStream, BUFFER);

				//Escribir el fichero destino
				while((currentByte = bufferedInputStream.read(data, 0, BUFFER)) != -1)
				{
					bufferedOutputStream.write(data, 0, currentByte);
				}

				//Forzar escritura de datos del flujo de salida
				bufferedOutputStream.flush();
				//Cerrar flujos
				bufferedOutputStream.close();
				bufferedInputStream.close();
			}
		}

		//Devolver lista de ficheros extraidos
		return destFileList;
	}
}
