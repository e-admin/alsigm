package es.ieci.tecdoc.fwktd.dir3.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {

	private static Base64Utils _instance = new Base64Utils();

	/**
	 * Constructor protegido para evitar creación de instancias desde otras clases
	 */
	protected Base64Utils() {}

	/**
	 * Obtiene la instancía única de la clase
	 * @return La instancia única de la clase
	 */
	public static Base64Utils getInstance()
	{
		return _instance;
	}

	/**
	 * Decodifica una cadena en Base64 y guarda los datos a un fichero
	 * @param inputBase64String Cadena codificada en Base 64
	 * @param outFileName Nombre del fichero
	 * @throws IOException
	 */
	public void decodeToFile(String inputBase64String, String outFileName) throws IOException
	{
		//Decodificar la cadena en Base 64
		ByteBuffer fileData = ByteBuffer.wrap(Base64.decodeBase64(inputBase64String));
		//Crear un canal de escritura al fichero de salida
		File file = new File(outFileName);
		FileChannel wChannel = new FileOutputStream(file).getChannel();
		//Escribir los datos y cerrar el fichero.
		wChannel.write(fileData);
		wChannel.close();
	}
}
