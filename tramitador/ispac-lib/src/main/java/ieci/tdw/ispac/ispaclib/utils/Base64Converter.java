/*
 * Base64Converter.java
 *
 * Created on May 25, 2004, 5:50 PM
 */

package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Converter
{

  /**
   * Serializa un objeto en un string aplicando codificación base64
   * @param object objeto a serializar
   * @return resultado de la serialización
   * @throws ISPACException si se produjo algún error
   */
  public static String b64Enc (Serializable object) throws ISPACException
  {
    String s = null;
    try
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream ();
      ObjectOutputStream oos = new ObjectOutputStream (baos);
      oos.writeObject(object);
      byte[] b = baos.toByteArray();
      s = new BASE64Encoder().encode(b);
    }
    catch (IOException e)
    {
      throw new ISPACException (e);
    }
    return s;    
  }

  /**
   * Serializa un objeto en un string aplicando dos codificaciones: base64 y url(utf-8)
   * @param object objeto a serializar
   * @return resultado de la serialización
   * @throws ISPACException si se produjo algún error
   */
  public static String b64AndUrlEnc (Serializable object) throws ISPACException
  {
    String s = null;
    try
    {
      s = b64Enc (object);
      s = URLEncoder.encode(s, "utf-8");
    }
    catch (UnsupportedEncodingException e)
    {
      throw new ISPACException (e);
    }
    return s;
  }
  
  /**
   * Deserializa un objeto a partir de un string en codigo base64
   * @param string en codigo base64
   * @return objeto producido tras la deserializacion
   * @throws ISPACException si se produjo algún error
   */  
  public static Serializable b64Dec (String str) throws ISPACException
  {   
    Serializable s = null;
    try 
    {
      BASE64Decoder dec = new BASE64Decoder();                
      ByteArrayInputStream buf = new ByteArrayInputStream(dec.decodeBuffer(str));
      ObjectInputStream in = new ObjectInputStream(buf);
      s = (Serializable)in.readObject();
    } 
    catch (IOException e) 
    {
      throw new ISPACException("Not serializable: " + e.getMessage(), e);
    } 
    catch (ClassNotFoundException e) 
    {
      throw new ISPACException("Not serializable: " + e.getMessage(), e);
    }  
    return s;
  }
  

  /**
   * Deserializa un objeto a partir de un string en codigo base64
   * @param string en codigo base64
   * @return objeto producido tras la deserializacion
   * @throws ISPACException si se produjo algún error
   */  
  public static Serializable b64AndUrlDec (String str) throws ISPACException  
  {  
    Serializable s = null;
    try
    {
      str = URLDecoder.decode(str, "utf-8");      
      s = b64Dec (str);
    }
    catch (UnsupportedEncodingException e)
    {
      throw new ISPACException (e);
    }
    return s;      
  }
    
}
