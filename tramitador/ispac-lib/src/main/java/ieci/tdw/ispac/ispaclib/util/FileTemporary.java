package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.naming.NamingException;

/*
 * Clase utilizada en el manejo de ficheros intermedios.
 */

public class FileTemporary {
  
  /**
   * The input buffer size.
   */
  private final static int CHUNK_SIZE = 10240;

  public FileTemporary( String sFileTemporaryPath) 
  throws ISPACException {
    
    try 
	{
      File file = new File(sFileTemporaryPath);
      if (!file.exists())
      	file.mkdir();
      
      mFileDirContext = new FileDirContext();
      mFileDirContext.setDocBase( sFileTemporaryPath);
      clean();
    } 
    catch(IllegalArgumentException e) 
	{ throw new ISPACException( e);}
  }

  /**
   * Filesystem Directory Context implementation.
   */
  protected FileDirContext mFileDirContext = null;
  
  /* 
   * Obtiene el directorio raiz.
   *
   */
  public String getFileTemporaryPath()
  { return mFileDirContext.getDocBase();}
  
  /**
   * Salva el contenido de un input stream en un fichero temporal.
   * @param in  input stream.
   * @return nombre del fichero.
   * @exception ISPACException.
   */
  public String put( InputStream in)
  throws ISPACException {
    
    String sName = null;
    
    try {
      Resource resource = new Resource( in);
      sName = mFileDirContext.bind( resource);
    } 
    catch(NamingException e) 
	{ throw new ISPACException( e);}

    return sName;
  }

  /**
   * Salva el contenido de un fichero en un fichero temporal.
   * @param fileName path del fichero origen.
   * @return nombre del fichero.
   * @exception ISPACException.
   */
  public String put( String fileName)
  throws ISPACException {
    try
	{
    	File file = new File( fileName);
    	return put(new FileInputStream(file));
	}
    catch(FileNotFoundException e)
	{
    	throw new ISPACException( e);
	}
  }
  
  /**
   * Salva el contenido de un fichero en un fichero temporal.
   * @param fileName path del fichero origen.
   * @param suffix extensión del fichero destino.
   * @return nombre del fichero.
   * @exception ISPACException.
   */
  public String put( String fileName, String suffix)
  throws ISPACException {
  	
  	FileInputStream in = null;
  	FileOutputStream out = null;
  	
    try
	{
    	String sName = mFileDirContext.newFileName( suffix);
    	
    	in = new FileInputStream( fileName);
    	out = new FileOutputStream( mFileDirContext.getDocBase() + "/" + sName);
    	
    	copy( in, out);
    	
    	return sName;
 	}
    catch(Exception e)
	{
    	throw new ISPACException( e);
	}
	finally 
	{
		try 
		{
			if (in != null)	in.close();
			if (out != null) out.close();
		} 
		catch (IOException e) 
		{}
	}
  }
  
  /**
   * Salva el contenido de un input stream en un fichero temporal.
   * @param in  input stream.
   * @param length longitud del stream que se desea copiar.
   * @return nombre del fichero.
   * @exception ISPACException.
   */
  public String put( InputStream in, int length)
  throws ISPACException {
    
    String sName = null;
    
    try {
      Resource resource = new Resource( in);
      sName = mFileDirContext.bind( resource, length);
    } 
    catch(NamingException e) 
		{ throw new ISPACException( e);}

    return sName;
  }

  /**
   * Copia el contenido de un fichero en output stream.
   * @param out  output stream.
   * @param sPath lnombre del fichero.
   * @exception ISPACException.
   */
  public void get( OutputStream out, String sPath)
  throws ISPACException {
    
    Object object;
    Resource resource = null;
    
    try {
      
      object = mFileDirContext.lookup( sPath);
      if (object instanceof Resource) {
        resource = (Resource) object;
        copy( resource.streamContent(), out);
      }
    } 
    catch(Exception e) 
		{ throw new ISPACException( e);}
  }

  /**
   * Elimina un fichero temporal.
   * @param sPath nombre del fichero.
   * @exception ISPACException.
   */
  public void delete( String sName)
  throws ISPACException {
    
    try 
    { mFileDirContext.unbind( sName);} 
    catch(NamingException e) 
	{ throw new ISPACException( e);}
  }

  public void delete(File file) throws ISPACException {
		try {
			mFileDirContext.unbind(file);
		} catch (NamingException e) {
			throw new ISPACException(e);
		}
	}

  public void rename( String oldName, String newName)
  throws ISPACException {
    try 
    { mFileDirContext.rename( oldName, newName);} 
    catch(NamingException e) 
	{ throw new ISPACException( e);}
 	
  }

  /**
   * Copia el contenido de un input stream en un output stream
   * @param in input stream
   * @param oout output
   *
   * @exception IOException if an input/output error occurs
   */
  protected void copy( InputStream in, OutputStream out) 
  throws ISPACException {
    
    BufferedInputStream inbuffer = null;
    
    try {
      
      inbuffer = new BufferedInputStream( in, CHUNK_SIZE);
      byte buffer[] = new byte[CHUNK_SIZE];
      int length = buffer.length;
      
      while (true) {
        length = inbuffer.read( buffer);
        if (length == -1) 
          break;
        out.write( buffer, 0, length);
      }
    }
    catch (IOException e) {
      try 
      { inbuffer.close();} 
      catch (Throwable t) 
      {}
      throw new ISPACException( e);
    }
  }
  
  /* 
   * Elimina todos los ficheros temporales.
   *
   */
  protected void clean() 
  throws ISPACException {
    
    try {
      File base = mFileDirContext.getBase();
      
      if (base == null)
        return; 
      
      String[] names = base.list();
      
      for (int i = 0; i < names.length; i++) {
        // Sólo elimina los ficheros creados por el manejador
        if (names[i].startsWith( "ispac"))
          mFileDirContext.unbind( names[i]);
      }
    }
    catch(NamingException e) 
		{ throw new ISPACException( e);}
  }

  /**
   * Obtiene el nombre de un fichero temporal.
   * @return nombre del fichero.
   * @exception ISPACException.
   */
  public String newFileName()
  throws ISPACException {
      return newFileName(null);
  }

  /**
   * Obtiene el nombre de un fichero temporal.
   * @return Fichero.
   * @exception ISPACException.
   */
  public File newFile() throws ISPACException {
      return newFile(null);
  }

  /**
   * Obtiene el nombre de un fichero temporal.
   * @return nombre del fichero.
   * @exception ISPACException.
   */
  public String newFileName(String suffix)
  throws ISPACException {
    
    try 
	{	return mFileDirContext.newFileName( suffix);} 
    catch(NamingException e) 
	{ throw new ISPACException( e);}
  }  

  /**
   * Obtiene el nombre de un fichero temporal.
   * @return Fichero.
   * @exception ISPACException.
   */
  public File newFile(String suffix) throws ISPACException {
    
    try 
	{	return mFileDirContext.newFile( suffix);} 
    catch(NamingException e) 
	{ throw new ISPACException( e);}
  }  

  /**
   * Obtiene el nombre de un directorio temporal.
   * @return Directorio.
   */
  public File newDirectory() {
	return mFileDirContext.newDirectory();
  }
  
}
