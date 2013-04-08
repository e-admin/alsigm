package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

/**
 * Mapea la tabla SGMRTCATALOGO_ORGANOS.
 *
 * @see OrganoDestinatarioDatos
 */
public class OrganoDestinatarioTabla
{
  private static final String TN = "SGMRTCATALOGO_ORGANOS";

  protected static final String CN_ID = "ORGANO";
  protected static final String CN_DESCRIPTION = "DESCRIPCION";


  /**
   * Constructor de la clase ProcedureTable
   */
  public OrganoDestinatarioTabla() {
  }


  /**
   * Devuelve el nombre completo de la tabla mapeada sin cualificar.
   * @return El nombre de la tabla.
   */
  public String getTableName() {
     return TN;
  }

  /**
   * Devuelve el nombre completo de la columna identificador del órgano destinatario
   * @return El nombre de dicha columna.
   */
  public String getIdColumnName() {
     return CN_ID;
  }

  /**
   * Devuelve el nombre completo de la columna descripción del órgano destinatario.
   * @return El nombre de dicha columna.
   */
  public String getDescriptionColumnName() {
     return CN_DESCRIPTION;
  }


  /**
   * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
   * separados por comas.
   * @return El nombre de las columnas.
   */
  public String getAllColumnNames() {
     String val = CN_ID + ", " + CN_DESCRIPTION;
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
     String val = CN_DESCRIPTION;
     return val;
  }


  /**
   * Devuelve la cláusula de consulta con el valor del identificador que se
   * pasa como parámetro.
   * @param id Identificador del órgano destinatario.
   * @return La cláusula.
   */
  public String getByIdQual(String id) {
     return " WHERE " + CN_ID + " = '" + DbUtil.replaceQuotes(id) + "'";
  }

}
