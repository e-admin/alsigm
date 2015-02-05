package ieci.tecdoc.sgm.calendario.util.database;

/**
 * Mapea la tabla SGMRTCATALOGO_CONECTORES.
 * 
 * @see ConectorDatos
 */ 
public class CalendarioDiaTabla
{
  private static final String TN = "SGMRTCATALOGO_DIASCALENDARIO";

  protected static final String CN_ID = "ID";
  protected static final String CN_FECHA = "FECHA";
  protected static final String CN_DESCRIPCION = "DESCRIPCION";
  protected static final String CN_FIJO = "FIJO";

  
  /**
   * Constructor de la clase CalendarioDiaTable
   */
  public CalendarioDiaTabla() {
  }

  
  /**
   * Devuelve el nombre completo de la tabla mapeada sin cualificar.
   * @return El nombre de la tabla.
   */
  public String getTableName() {
     return TN;
  }

  /**
   * Devuelve el nombre completo de la columna identificador
   * @return El nombre de dicha columna.
   */
  public String getIdColumnName() {
     return CN_ID;
  }
  
  /**
   * Devuelve el nombre completo de la columna identificador para el máximo
   * @return El nombre de dicha columna.
   */
  public String getMaxIdColumnName() {
     return "MAX (" + CN_ID + ")";
  }
  
  /**
   * Devuelve el nombre completo de la columna identificador fecha del festivo
   * @return El nombre de dicha columna.
   */
  public String getFechaColumnName() {
     return CN_FECHA;
  }

  /**
   * Devuelve el nombre completo de la columna descripción del festivo.
   * @return El nombre de dicha columna.
   */
  public String getDescripcionColumnName() {
     return CN_DESCRIPCION;
  }
  
  /**
   * Devuelve el nombre completo de la columna fijo.
   * @return El nombre de dicha columna.
   */
  public String getFijoColumnName() {
     return CN_FIJO;
  }
  
  /**
   * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
   * separados por comas.
   * @return El nombre de las columnas.
   */
  public String getAllColumnNames() {
     String val = CN_ID + ", " + CN_FECHA + ", " + CN_DESCRIPCION + ", " + CN_FIJO;
     return val;
  }
  
  /**
   * Devuelve los nombres completos de todas las columnas mapeadas
   * actualizables, separados por comas.
   * @return El nombre de las columnas separados por comas.
   */
  public String getUpdateColumnNames() {
	  String val = CN_FECHA + ", " + CN_DESCRIPCION + ", " + CN_FIJO;
	  return val;
  }
  
  /**
   * Devuelve la cláusula de consulta con el id
   * @param date Fecha del dia festivo.
   * @return La cláusula.
   */
  public String getByIdQual(int id) {
     return " WHERE " + CN_ID + " = '" + id + "'";
  }
  
  /**
   * Devuelve la clausula de orden por la descripción.
   * @return Los nombres de las columnas.
   */
  public String getOrderByDesc() {
     return " ORDER BY "+ CN_DESCRIPCION;
  }
  
  /**
   * Devuelve la clausula de orden por parametro.
   * @return Los nombres de las columnas.
   */
  public String getOrder(int order) {
	  if (order == 0)
		  return " ORDER BY "+ CN_DESCRIPCION;
	  else return " ORDER BY "+ CN_FECHA;
  }
  
  /**
   * Devuelve la clausula por tipo y con orden por la descripción.
   * @param type Tipo de dia (0 para fijo, 1 para variable)
   * @return Los nombres de las columnas.
   */
  public String getByTypeAndOrderQual(int type, int order) {
	  if (order == 0)
		  return " WHERE " + CN_FIJO + " = '" + type + "' ORDER BY "+ CN_DESCRIPCION;
	  else return " WHERE " + CN_FIJO + " = '" + type + "' ORDER BY "+ CN_FECHA;
  }
}
