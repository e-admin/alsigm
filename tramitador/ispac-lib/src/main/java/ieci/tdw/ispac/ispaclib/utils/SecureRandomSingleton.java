package ieci.tdw.ispac.ispaclib.utils;

import java.security.SecureRandom;

/**
 * Se ha creado esta clase ante la necesidad de generar números aleatorios
 * para la generación de UIDs. Todos los Session Beans de tipo IspacSesion
 * que corran dentro de una misma máquina virtual utilizarán el mismo
 * generaror de números aleatorios si utilizan esta clase para obtener el
 * SecureRandom
 */

public class SecureRandomSingleton 
{
  // Generador de números aleatorios. Existirá duranta la vida de la JVM
  private static SecureRandom seeder;

  /**
   * Constructor privado que crea el generador de numeros aleatorios. Es privado
   * para que no sea llamado desde fuera y pueda así generar un nuevo generador
   * de números aleatorios.
   */
  private SecureRandomSingleton()
  {    
    seeder = new SecureRandom ();
  }

  /**
   * Devuelve el generador de números aleatorios. Siempre devuelve el mismo. Si
   * no existe, lo crea, cosa que ocurrirá sólo la primera vez que se llame a este
   * método. 
   * @return generador de números aletarios
   */
  public static synchronized SecureRandom getSecureRandomInstance ()
  {
    if (seeder == null)
      new SecureRandomSingleton ();      
    return seeder;
  }
  
}