package ieci.tdw.ispac.ispaclib.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

public class UUIDGen
{

	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(UUIDGen.class);
	
  /**
   * Constructor vacio
   */
  public UUIDGen()
  {
  }

  /**
   * Devuelve un UUID representado por 32 digitos, los cuales son calculados
   * en grupos de 8 bajo procedimientos distinos.
   * Grupo 1 (caracteres 1-8):
   * @param obj objeto para el cual se calcula el UUID
   * @param seeder generador de numeros aleatorios
   * @result String que representa el UUID
   */
  //public static String getUUID (Object obj, SecureRandom seeder)
  public static String getUUID (Object obj)
  {
    String uuid = null;
    try
    {
      uuid = new String();
      uuid = uniqueDownToTheMillisecond ();
      uuid += uniqueAcrossACluster ();
      uuid += uniqueDownToTheObjectJVM (obj);
      uuid += uniqueWithinObjectWithinMillisecond (/*seeder*/);
      logger.info("uuid generado con éxito");
     
    }
    catch (UnknownHostException e)
    {
      logger.error("Error al obtener el uuid método getUUID de la clase UUIDGen"+e);
      uuid = null;
    }
    return uuid;
  }

  /**
   * Devuelve el grupo de digitos (1-8). Estos corresponden con la representacion
   * hexadecimal de los 32 bits bajos devueltos en la llamada al metodo
   * System.currentTimeMillis()
   * @result String grupo de digitos (1-8)
   */
  private  static String uniqueDownToTheMillisecond ()
  {
    long timeNow = System.currentTimeMillis();
    int timeLow = (int) timeNow & 0xFFFFFFFF;
    return hexFormat(timeLow, 8);
  }

  /**
   * Devuelve el grupo de digitos (9-16). Estos corresponden con la representacion
   * hexadecimal de la dirección IP de la máquina
   * @result String grupo de digitos (9-16)
   */
  private  static String uniqueAcrossACluster () throws UnknownHostException
  {
    String hex = "";
    InetAddress inet = InetAddress.getLocalHost();
    byte[] bytes = inet.getAddress();
    for (int i=0; i<4; i++)
    {
      int n = bytes[i];
      // si n es negativo, lo hacemos positivo haciendo complemento a 1 y sumando 1
      if (n < 0)
        n = ~n + 1;
      hex += Integer.toHexString(n);
    }

    logger.info("La representación hexadecimal de la IP de la máquina , ha sido generada con éxito "+hex);
    return hex;
  }

  /**
   * Devuelve el grupo de digitos (17-24). Estos corresponden con la representacion
   * hexadecimal de los 32 bits bajos devueltos en la llamada al metodo
   * System.identityHashCode ()
   * @param obj objeto del cual se obtiene el hashcode
   * @result String grupo de digitos (9-16)
   */
  private  static String uniqueDownToTheObjectJVM (Object obj)
  {
    int thisHashCode = System.identityHashCode(obj);
    return hexFormat(thisHashCode, 8);
  }


  /**
   * Devuelve el grupo de digitos (25-32). Estos corresponden con la representacion
   * hexadecimal de los 32 bits bajos de un numero aleatorio generado a través de la
   * clase java.security.SecureRandom
   * @param seeder generador de numero aleatorios. Para objetos dentro de una misma
   * maquina virtual, el seeder debe ser el mismo
   * @result String grupo de digitos (25-32)
   */
  private  static String uniqueWithinObjectWithinMillisecond (/*SecureRandom seeder*/)
  {
    SecureRandom seeder = SecureRandomSingleton.getSecureRandomInstance ();
    int aleatNumber = seeder.nextInt ();
    return hexFormat(aleatNumber, 8);
  }

  /**
   * Pasa a formato hexadecimal un número entero quedandose con j digitos hexadecimales
   */
  private static String hexFormat (int i, int j)
  {
    String s = Integer.toHexString (i);
    return padHex (s, j) + s;
  }

  /**
   * Rellena de ceros un string por la izquierda hasta el string tenga i digitos
   */
  private static String padHex (String s, int i)
  {
    StringBuffer tmpBuffer = new StringBuffer ();
    if (s.length() < i)
    {
      for (int j=0; j<i - s.length(); j++)
        tmpBuffer.append('0');
    }
    return tmpBuffer.toString();
  }

}