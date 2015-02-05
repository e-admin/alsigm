package ieci.tecdoc.sgm.calendario.util.database;

/**
 * Mapea la tabla SGMRTCATALOGO_CONECTORES.
 * 
 * @see ConectorDatos
 */ 
public class CalendarioTabla
{
  private static final String TN = "SGMRTCATALOGO_CALENDARIO";

  protected static final String CN_LABORABLES = "LABORABLES";
  protected static final String CN_HORA_INICIO = "HORA_INICIO";
  protected static final String CN_MINUTO_INICIO = "MINUTO_INICIO";
  protected static final String CN_HORA_FIN = "HORA_FIN";
  protected static final String CN_MINUTO_FIN = "MINUTO_FIN";

  
  /**
   * Constructor de la clase CalendarioTable
   */
  public CalendarioTabla() {
  }

  
  /**
   * Devuelve el nombre completo de la tabla mapeada sin cualificar.
   * @return El nombre de la tabla.
   */
  public String getTableName() {
     return TN;
  }

  /**
   * Devuelve el nombre completo de la columna identificador del conector
   * @return El nombre de dicha columna.
   */
  public String getLaborablesColumnName() {
     return CN_LABORABLES;
  }

  /**
   * Devuelve el nombre completo de la columna hora de inicio.
   * @return El nombre de dicha columna.
   */
  public String getHoraInicioColumnName() {
     return CN_HORA_INICIO;
  }
  
  /**
   * Devuelve el nombre completo de la columna hora de fin.
   * @return El nombre de dicha columna.
   */
  public String getHoraFinColumnName() {
     return CN_HORA_FIN;
  }
  
  /**
   * Devuelve el nombre completo de la columna minuto de inicio.
   * @return El nombre de dicha columna.
   */
  public String getMinutoInicioColumnName() {
     return CN_MINUTO_INICIO;
  }

  /**
   * Devuelve el nombre completo de la columna minuto de fin.
   * @return El nombre de dicha columna.
   */
  public String getMinutoFinColumnName() {
     return CN_MINUTO_FIN;
  }
  
  /**
   * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
   * separados por comas.
   * @return El nombre de las columnas.
   */
  public String getAllColumnNames() {
     String val = CN_LABORABLES + ", " + CN_HORA_INICIO + ", " + CN_MINUTO_INICIO +
     			", " + CN_HORA_FIN  + ", " + CN_MINUTO_FIN;
     return val;
  }
  
  /**
   * Devuelve los nombres completos de todas las columnas mapeadas
   * actualizables, separados por comas.
   * @return El nombre de las columnas separados por comas.
   */
  public String getUpdateColumnNames() {
     return getAllColumnNames();
  }
}
