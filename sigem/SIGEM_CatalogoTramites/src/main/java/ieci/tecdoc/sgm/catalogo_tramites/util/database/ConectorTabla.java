package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

/**
 * Mapea la tabla SGMRTCATALOGO_CONECTORES.
 *
 * @see ConectorDatos
 */
public class ConectorTabla
{
  private static final String TN = "SGMRTCATALOGO_CONECTORES";

  protected static final String CN_ID = "CONECTORID";
  protected static final String CN_DESCRIPTION = "DESCRIPCION";
  protected static final String CN_TYPE = "TIPO";


  /**
   * Constructor de la clase HookTable
   */
  public ConectorTabla() {
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
  public String getIdColumnName() {
     return CN_ID;
  }

  /**
   * Devuelve el nombre completo de la columna descripción del conector.
   * @return El nombre de dicha columna.
   */
  public String getDescriptionColumnName() {
     return CN_DESCRIPTION;
  }

  /**
   * Devuelve el nombre completo de la columna tipo del conector.
   * @return El nombre de dicha columna.
   */
  public String getTypeColumnName() {
     return CN_TYPE;
  }

  /**
   * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
   * separados por comas.
   * @return El nombre de las columnas.
   */
  public String getAllColumnNames() {
     String val = CN_ID + ", " + CN_DESCRIPTION + ", " + CN_TYPE;
     return val;
  }


  /**
   * Devuelve la clausula de orden por la descripción.
   * @return Los nombres de las columnas.
   */
  public String getOrderByDesc() {
     return " ORDER BY "+ CN_DESCRIPTION;
  }


  /**
   * Devuelve los nombres completos de todas las columnas mapeadas
   * actualizables, separados por comas.
   * @return El nombre de las columnas separados por comas.
   */
  public String getUpdateColumnNames() {
     String val = CN_DESCRIPTION + ", " + CN_TYPE;
     return val;
  }


  /**
   * Devuelve la cláusula de consulta con el valor del identificador que se
   * pasa como parámetro.
   * @param id Identificador del conector.
   * @return La cláusula.
   */
  public String getByIdQual(String id) {
     return " WHERE " + CN_ID + " = '" + DbUtil.replaceQuotes(id) + "'";
  }

  /**
   * Devuelve la cláusula de consulta con el valor del tipo de conector
   * @param id Identificador del tipo de conector.
   * @return La cláusula.
   */
  public String getByHookTypeQual(int hookType) {
     return " WHERE " + CN_TYPE + " = '" + hookType + "'";
  }

}
