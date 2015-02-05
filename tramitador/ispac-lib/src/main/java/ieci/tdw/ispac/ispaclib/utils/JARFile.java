/*
 * JarUtil.java
 *
 * Created on April 23, 2004, 11:42 AM
 */

package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.CRC32;

public class JARFile
{


  private JarOutputStream m_jarout;           // Fichero JAR
  private String m_jarPath;                         // Path fichero JAR
  private String m_jarName;                       // Nombre del fichero JAR

  private static final String separator = System.getProperty("file.separator");

  /**
   * Constructor
   * @param jarPath path del fichero jar
   * @param jarName nombre del fichero jar
   */
  public JARFile(String jarPath, String jarName) throws ISPACException
  {
    try
    {
      Manifest manifest = new Manifest();
      manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
      String jarFullName = jarPath + separator + jarName;
      m_jarout = new JarOutputStream(new FileOutputStream(jarFullName), manifest);
      m_jarPath = jarPath;
      m_jarName = jarName;
    }
    catch (Exception e)
    {
      ISPACException ie = new ISPACException();
      ie.initCause(e);
      throw ie;
    }
  }

  /**
   * Añade una entrada al fichero jar.
   * @param fileName path del fichero que se añade al fichero JAR
   * @exception ISPACException
   */
  public void addEntry (String fileName) throws ISPACException
  {
    FileInputStream is = null;
    try
    {
      File file = new File (fileName);
      is = new FileInputStream (file);

      // Es una entrada relativa si el path del fichero JAR contiene el path del fichero 'entrada' ;
      // en caso  contrario en una entrada absoluta
      String entryName = null;
      String filePath = fileName.substring(0, fileName.lastIndexOf(separator));
      if ((filePath.equals(m_jarPath)) || (filePath.startsWith(m_jarPath) && filePath.charAt(m_jarPath.length()) == separator.toCharArray()[0]))
        entryName = fileName.substring(m_jarPath.length()+1);
      else
        entryName = fileName;

      addEntry (entryName, is, (int) file.length(), file.lastModified());
    }
    catch (FileNotFoundException e)
    {
      ISPACException ie = new ISPACException();
      ie.initCause(e);
      throw ie;
    }
    finally
    {
      try
      {
        if (is != null)
          is.close ();
      }
      catch (IOException e)
      {
        // Ignore error
      }
    }
  }

  /**
   * Añade una entrada al fichero jar.
   * @param entryName nombre de la entrada que se añade al fichero JAR
   * @param is inputstream donde se obtienen los datos de la entrada
   * @param length logitud de los datos que componen la entrada
   * @param date fecha de ultima modificacion
   * @exception ISPACException
   */
  public void addEntry (String entryName, InputStream is, int length, long date) throws ISPACException
  {
    try
    {
      byte[] data = new byte[length];
      is.read(data);
      CRC32 crc32 = new CRC32();
      crc32.update(data, 0, data.length);

      // Se crea entrada JAR
      JarEntry jarEntry = new JarEntry(entryName);
      jarEntry.setSize(data.length);
      jarEntry.setTime(date);
      jarEntry.setCrc(crc32.getValue());

      // Se añade entrada JAR al fichero JAR
      m_jarout.putNextEntry(jarEntry);
      m_jarout.write(data, 0, data.length);
      m_jarout.flush();
      m_jarout.closeEntry();
    }
    catch (IOException e)
    {
      ISPACException ie = new ISPACException();
      ie.initCause(e);
      throw ie;
    }
  }

  /**
   * Cierra fichero JAR
   */
  public void close ()
  {
    try
    {
      if (m_jarout != null)
        m_jarout.close ();
    }
    catch (IOException e)
    {
      // Ignore error
    }
    finally
    {
      m_jarout = null;
    }
  }

  /**
   * Devuelve un InputStream asociado a un fichero incluido en otro fichero jar
   * @param jarPath ruta del fichero jar
   * @param jarFileName nombre del fichero que se quiere extraer del fichero jar
   * @return InputStream del fichero a leer
   * @exception IOException, FileNotFoundException
   */
  public static InputStream getInputStreamFromEntry (String jarPath, String jarEntryName)
        throws ISPACException
  {
    JarFile jarFile = null;
    InputStream fileIS = null;
    try
    {
      jarFile = new JarFile(jarPath);
      JarEntry jarEntry = jarFile.getJarEntry(jarEntryName);
      if (jarEntry == null)
        throw new ISPACException ("JarUtil::getInputFileFromJar (" + jarPath +", " + jarEntryName + "), error: " + jarEntryName + " no se ha encontrado");

      fileIS = jarFile.getInputStream(jarEntry);
    }
    catch (IOException e)
    {
      ISPACException ie = new ISPACException ();
      ie.initCause (e);
      throw ie;
    }
    return fileIS;
  }


  /**
   * Extrae un fichero incluido dentro de un fichero JAR
   * @param jarPath ruta del fichero jar
   * @param jarFileName nombre del fichero que se quiere extraer del fichero jar
   * @param targetFileName nombre del fichero donde se extraerán los contenidos del fichero
   *        incluido en el jar
   * @exception IOException, FileNotFoundException
   */
  public static void extractFileFromJar(String jarPath, String jarFileName,  String targetFileName)
        throws ISPACException
  {

    try
    {
      InputStream fileIS = getInputStreamFromEntry (jarPath, jarFileName);
      File fileOut = new File(targetFileName);
      FileOutputStream fileOS = new FileOutputStream(fileOut);

      int length = 0;
      byte[] buffer = new byte[1024];
      while (length != -1)
      {
        length = fileIS.read(buffer);
        if (length > 0)
        {
          fileOS.write(buffer, 0, length);
        }
      }
      fileIS.close();
      fileOS.close();
    }
    catch (IOException e)
    {
      ISPACException ie = new ISPACException ();
      ie.initCause (e);
      throw ie;
    }
  }

/**
 * @return Devuelve el valor de la propiedad m_jarName.
 */
public String getM_jarName()
{
    return m_jarName;
}

/**
 * @param name Cambia el valor de la propiedad m_jarName.
 */
public void setM_jarName(String name)
{
    m_jarName = name;
}
}




  /**
   * Crea un fichero Jar con los ficheros contenidos en una serie de directorios
   * @param jarPath path del fichero jar
   * @param jarName nombre del fichero jar
   * @param sourceDirs nombre de los directorios que contienen ficheros que se quieren
   *        añadir al fichero jar
   * @param filters filtros que indican las extensiones de los ficheros que
   */
   /*


  /**
   * Clase privada que implementa filtros para ficheros

  private static class Filter implements FilenameFilter
  {
    private String[] mFilters;

    public Filter (String[] filters)
    {
      mFilters = filters;
    }

  	public boolean accept (File file, String name)
    {
      boolean rc = false;
      for (int i=0; i<mFilters.length; i++)
        if (name.toUpperCase().endsWith("." + mFilters[i].toUpperCase()))
        {
          rc = true;
          break;
        }
      return rc;
    }
  }


  public static void createJarFile(String jarPath, String jarName, String[] sourceDirs, String[] filters)
  throws ISPACException
  {
    try
    {
      Manifest manifest = new Manifest();
      manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
      String jarFullName = jarPath + separator + jarName;
      JarOutputStream jarout = new JarOutputStream(new FileOutputStream(jarFullName), manifest);

      for (int i=0; i<sourceDirs.length; i++)
      {
        File[] files = getFilesFromDir (sourceDirs[i], filters);
        for (int j=0; j<files.length; j++)
          addJarEntry (files[j], jarout, jarPath);
      }

      jarout.flush();
      jarout.finish();

    }
    catch (IOException e)
    {
      deleteFile (jarPath + separator + jarName);
      ISPACException ie = new ISPACException();
      ie.initCause(e);
      throw ie;
    }
    catch (ISPACException e)
    {
      deleteFile (jarPath + separator + jarName);
      throw e;
    }
  }

  /**
   * Este metodo devuelve el conjunto de ficheros de un directorio que cumplen
   * con unos determinados filtros
   * @param sourceDir directorio
   * @param filters filtros
   * @return array de ficheros
   */
  /*
  private static File[] getFilesFromDir (String sourceDir, String[] filters)
  {
    File fSourceDir = new File(sourceDir);
    File[] files = fSourceDir.listFiles(new Filter(filters));
    return files;
  }


  /**
   * Crea una entrada JAR para el fichero pasado como argumento
   * @param file fichero que constituira una entrada dentro del JAR
   * @param jarout fichero JAR
   * @param jarPath path donde se encuentra el fichero JAR. Este parametro es necesario
   *        para calcular si el fichero se va a incluir dentro del JAR como una entrada
   *        relativa o absoluta
   * @exception ISPACException
   */
  /*
  private static void addJarEntry (File file, JarOutputStream jarout, String jarPath) throws ISPACException
  {

    FileInputStream fis = null;

    try
    {
      // Se leen datos del fichero 'entrada' y se calcula su CRC
      fis = new FileInputStream(file);
      String fileName = file.getAbsolutePath();
      String filePath = fileName.substring(0, fileName.lastIndexOf(separator));
      byte[] data = new byte[(int)file.length()];
      fis.read(data);
      CRC32 crc32 = new CRC32();
      crc32.update(data, 0, data.length);

      // Es una entrada relativa si el path del fichero JAR contiene el path del fichero 'entrada' ;  en caso
      // contrario en una entrada absoluta
      String entryName = null;
      if ((filePath.equals(jarPath)) || (filePath.startsWith(jarPath) && filePath.charAt(jarPath.length()) == separator.toCharArray()[0]))
        entryName = fileName.substring(jarPath.length()+1);
      else
        entryName = fileName;

      // Se crea entrada JAR
      JarEntry jarEntry = new JarEntry(entryName);
      jarEntry.setSize(data.length);
      jarEntry.setTime(file.lastModified());
      jarEntry.setCrc(crc32.getValue());

      // Se añade entrada JAR al fichero JAR
      jarout.putNextEntry(jarEntry);
      jarout.write(data, 0, data.length);
      jarout.flush();
      jarout.closeEntry();

    }
    catch (Exception e)
    {
      try
      {
        if (fis != null) fis.close();
        if (jarout != null) jarout.closeEntry();
      }
      catch (IOException e2)
      {
        // Ignore error
      }

      // Lanzamos excepcion
      ISPACException ie = new ISPACException();
      ie.initCause(e);
      throw ie;
    }
  }

  /**
   * Borra un fichero
   * @param name nombre del fichero
   */
   /*
  private static void deleteFile (String name)
  {
    File file = new File(name);
    if (file != null) file.delete();
  }


      // Pruebas para crear un fichero JAR
      String[] sourceDirs = new String[]  {"/home/eduardo/borrar/eventos1", "/home/eduardo/borrar/eventos2", "/home/eduardo/borrar", "/home/eduardo/borrar2"};
      String[] filters = new String[] {"class"};
      JARFile.createJarFile("/home/eduardo/borrar", "eventos.jar", sourceDirs, filters);

*/