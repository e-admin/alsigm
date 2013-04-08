package ieci.tecdoc.sgm.rde.database.util;

import ieci.tecdoc.sgm.base.miscelanea.Goodies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Calse que implementa métodos de apoyo para operaciones con ficheros
 *
 */
public class Utilities {
	
  /**
   * Devuelve los bytes contenidos en un fichero
   * @param loc Fichero a leer
   * @return byte[] Bytes que contiene el fichero
   * @throws Exception
   */
  public static byte[] readBytesFromFile(File loc)
    throws Exception {
    int size;
    byte[] data;
    FileInputStream fis = null;

    try { 
      size = getFileSize(loc);
      data = new byte[size];
      fis = new FileInputStream(loc);
      fis.read(data);
      fis.close();
      fis = null;
      return data;
    } catch (Exception e) {
      try {
        if (fis != null) fis.close();
        throw e;
      } catch (Exception e1) {
        throw e;
      }
    }
  }
  
  
  /**
   * Devuelve el tamaño de un fichero
   * @param loc Fichero a leer
   * @return int Tamaño del fichero
   */
  public static int getFileSize(File loc) {
    int size = (int) loc.length();
    return size;
  }
  
  
  /**
   * Devuelve el hash o resumen de un conjunto de bytes
   * @param clear Conjunto de bytes sobre los que calcular el hash
   * @return String Hash del conjunto de bytes
   * @throws Exception
   */
  public static String getHash(byte[] clear) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] b = md.digest(clear);

    int size = b.length;
    StringBuffer h = new StringBuffer(size);
    for (int i = 0; i < size; i++) {
        int u = b[i]&255; // unsigned conversion
        if (u<16) {
            h.append("0"+Integer.toHexString(u));
        } else {
            h.append(Integer.toHexString(u));
        }
    }
    return h.toString();
  } 
  
  
  /**
   * Convierte el contenido de un documento a InputStream
   * @param path Ruta del docuemnto a convertir
   * @return InputStream Contenido del documento
   * @throws FileNotFoundException
   */
  public static InputStream getStreamFromFile(String path)
    throws FileNotFoundException {
    InputStream document = null;
    File f = new File(path);
    document = new FileInputStream(f);
    return document;
  }
  
  
  /**
   * Obtiene la extensión de un documento
   * @param name Nombre o ruta del documento
   * @return String Extensión del documento
   */
  public static String getExtension(String name){
    int index = name.lastIndexOf(".");
    if (index != -1)
      return name.substring(index+1, name.length()).toUpperCase();
    return "";
  }
  
  
  /**
   * Crea un documento con contenido
   * @param path Ruta donde crear el documento
   * @param name Nombre del documento (sin extension)
   * @param extension Extensión del documento
   * @param contenido Contenido del documento
   * @throws FileNotFoundException
   * @throws Exception
   */
  public static void createFile(String path, String name, String extension, byte[] contenido)
    throws FileNotFoundException, Exception {
    File recuperado = new File(path + name + "." + extension);
    FileOutputStream writer = new FileOutputStream(recuperado);
    writer.write(contenido);
    writer.close();
  }
  
  /**
   * Devuelve el tamaño de un fichero en formato binario
   * @param stream Contenido del fichero
   * @return Tamaño del contenido del fichero
   * @throws Exception Si se produce algún error
   */
  public static int getBinaryStreamSize(InputStream stream)
    throws Exception {
    byte[] bytes = Goodies.getBytes(stream);
    return bytes.length;
  }
}
