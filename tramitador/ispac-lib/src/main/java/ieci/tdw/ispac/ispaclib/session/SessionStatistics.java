/*
 * SessionStatistics.java
 *
 * Created on June 4, 2004, 4:20 PM
 */

package ieci.tdw.ispac.ispaclib.session;


public class SessionStatistics
{
  
  private long opTime;      // Tiempo de operacion en el servidor
  private long hits;        // Número de operaciones llevadas a cabo en el servidor
  private long ssTime;      // Tiempo total de la sesion
  
  /**
   * Constructor vacio
   */
  public SessionStatistics ()
  {    
  }
  
  /** 
   * Construye un objeto con las estadisticas de la sesion
   * @param opTime tiempo de operacion
   * @param hits operaciones realizadas en la sesion
   * @param ssTime tiempo de sesion
   */
  public SessionStatistics(long opTime, long hits, long ssTime)
  {
    this.opTime = opTime;    
    this.hits = hits;
    this.ssTime = ssTime;
  }
  
  /**
   * Devuelve el tiempo empleado en realizar las operaciones
   * @return tiempo empleado en realizar las operaciones
   */
  public long getOpTime ()
  {
    return opTime;
  }
        
  /**
   * Devuelve el numero de operaciones realizadas en la sesion
   * @return numero de operaciones realizadas en la sesion
   */  
  public long getHits ()
  {
    return hits;
  }
  
  
  /** Devuelve el tiempo de sesion 
   * @return tiempo de sesion
   */
  public long getSsTime ()
  {
    return System.currentTimeMillis() - ssTime;
  }
     
  /**
   * Convierte el objeto en un string
   */
  public String toString ()
  {
    String result = "Hits: " + hits;
    result += "\nOperation time: " + opTime;
    result += "\nSession time: " +  ssTime;
    return result;
  }
}
