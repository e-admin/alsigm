package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import java.io.Serializable;

//import ieci.tecdoc.sgm.catalogo_tramites.Configuracion;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatario;
import ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.OrganosDestinatarios;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de órganos destinatarios.
 * @see OrganoDestinatarioTable
 * @see OrganoDestinatario
 * @see OrganoDestinatarioImpl
 * @see OrganosDestinatarios
 */
public class OrganoDestinatarioDatos extends OrganoDestinatarioImpl implements Serializable {
  private static final Logger logger = Logger.getLogger(OrganoDestinatarioDatos.class);
  boolean isDebugeable = true;
  
  /**
   * Constructor de la clase AddresseeData
   */  
  public OrganoDestinatarioDatos() {
  }
  
  
  /**
   * Constructor de la clase AddresseeData
   * @param addressee Datos relacionados con un órgano destinatario
   */
  public OrganoDestinatarioDatos(OrganoDestinatario addressee) {
     this.setId(addressee.getId());
     this.setDescription(addressee.getDescription());
  }
  
  
  /**
   * Recupera todos los valores de los parámetros de la sentencia de consulta
   * que se pasa como parámetro.
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge de la
   *  consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer loadAllValues(DbOutputStatement statement, Integer idx)
  throws Exception {
     
     int index = idx.intValue();
     
     id = statement.getShortText(index++);
     description = statement.getShortText(index++);
     
     return new Integer(index);
  }

  
  /**
   * Genera la sentencia de inserción de un registro.
   * @param statement Sentencia SQL precompilada.
   * @param idx Indice de posición del primer parámetro en la sentencia.
   * @return Indice de posición del último parámetro en la sentencia.
   * @throws Exception Si se produce algún error.
   */
  public Integer insert(DbInputStatement statement, Integer idx)
  throws Exception {
     int index = idx.intValue();
     
     statement.setShortText(index++, id);
     statement.setShortText(index++, description);
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de actualización de un registro.
   * @param statement Sentencia SQL precompilada.
   * @param idx Indice de posición del primer parámetro en la sentencia.
   * @return Indice de posición del último parámetro en la sentencia.
   * @throws Exception Si se produce algún error.
   */
  public Integer updateAll(DbInputStatement statement, Integer idx)
  throws Exception {
     int index = idx.intValue();

     statement.setShortText(index++, description);
     
     return new Integer(index);
  }
  
  /**
   * Recoge el organo destinatariocuyo identificador se pasa como
   * parámetro.
   * @param documentId Identificador de l órgano destinatario.
   * @throws Exception Si se produce algún error.
   */
  public void load(String addresseeId, String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     OrganoDestinatarioTabla table = new OrganoDestinatarioTabla();
     DbConnection dbConn = new DbConnection();
     boolean load = true;
  
     logger.debug("Load Addressee <-- AddresseeId: " + addresseeId);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(OrganoDestinatarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(OrganoDestinatarioDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        if (!DynamicFns.select(dbConn,table.getByIdQual(addresseeId), tableInfo, rowsInfo)) {
          load = false;
        }
        
        if (load)
        	logger.debug("Load Addressee --> Addressee: " + this.toString());
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENT);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
        if (!load)
          throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_BAD_ADDRESSEEID);
     }
  }
  

  /**
   * Inserta un organo destinatario.
   * @throws Exception Si se produce algún error.
   */
  public void insert(String entidad) throws Exception {     
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     OrganoDestinatarioTabla table = new OrganoDestinatarioTabla();
     DbConnection dbConn = new DbConnection();
     
     if (isDebugeable)
       logger.debug("Insert Addressee <-- AddresseeId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(OrganoDestinatarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(OrganoDestinatarioDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_ADDRESSEE);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Actualiza el registro de órgano destinatario cuyo identificador se pasa como
   * parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void update(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     OrganoDestinatarioTabla table = new OrganoDestinatarioTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Addressee <-- Adressee: " + this.toString());
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(OrganoDestinatarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getUpdateColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(OrganoDestinatarioDatos.class.getName());
        rowInfo.setValuesMethod("updateAll");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn,table.getByIdQual(id), tableInfo, rowsInfo);
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_ADDRESSEE);
     } catch (Exception e) {
       throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
        if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  
  /**
   * Elimina el registro de órgano destinatario cuyo identificador se pasa como
   * parámetro.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) throws Exception {
     OrganoDestinatarioTabla table = new OrganoDestinatarioTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("delete <-- AddresseeId: " + id);
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        DbDeleteFns.delete(dbConn, table.getTableName(), table.getByIdQual(id));
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_DOCUMENT);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
  }
  
  /**
   * Recupera la lista de documentos.
   * @return Lista de documentos.
   * @throws Exception Si se produce algún error.
   */
  public static OrganosDestinatarios getAddressees(String entidad) throws Exception {
     OrganosDestinatarios addressees = new OrganosDestinatarios();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoDocument = new DynamicRow();
     OrganoDestinatarioTabla table = new OrganoDestinatarioTabla();
     DbConnection dbConn = new DbConnection();
     OrganoDestinatarioDatos addressee;
     int counter, size;
     
     logger.debug("Get Addressees");
     
     try {
      	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(OrganoDestinatarioTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoDocument.setClassName(OrganoDestinatarioDatos.class.getName());
        rowInfoDocument.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoDocument);
        
        DynamicFns.selectMultiple(dbConn, table.getOrderByDesc(), false, tableInfo, rowsInfo);
        size = rowInfoDocument.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           addressee = (OrganoDestinatarioDatos)rowInfoDocument.getRow(counter);
           addressees.add(addressee);
        }
        
     } catch (CatalogoTramitesExcepcion e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_ADDRESSEES);
     } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
	           dbConn.close();
     }
     
     return addressees;
  }
}
