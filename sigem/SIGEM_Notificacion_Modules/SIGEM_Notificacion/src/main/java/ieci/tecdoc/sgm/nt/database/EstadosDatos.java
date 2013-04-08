/**
 * @author Javier Septién Arceredillo
 * 
 * Fecha de Creación: 11-may-2007
 */
package ieci.tecdoc.sgm.nt.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.nt.database.datatypes.EstadoImpl;
import ieci.tecdoc.sgm.nt.database.datatypes.Estados;
import ieci.tecdoc.sgm.nt.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.nt.database.exception.DbException;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaExcepcion;
import ieci.tecdoc.sgm.nt.exception.DatosIncorrectosExcepcion;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioExcepcion;

import java.io.Serializable;




import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de expedientes del CT.
 *  
 */
public class EstadosDatos extends EstadoImpl implements Serializable{
    private static Logger logger = Logger.getLogger(NotificacionesDatos.class);
    protected boolean isDebugeable = true;
     /**
    * Recupera todos los valores de los parámetros de la sentencia
    * de consulta que se pasa como parámetro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws DbException Si se produce algún error.
    */
    public Integer loadAllValues(DbOutputStatement statement, Integer idx)
    throws DbException {
     
        if (isDebugeable)
            logger.debug("loadAllValues >> statement: " + statement.toString() + " idx entrada: " + idx);
    
        int index = idx.intValue();
     
        try{
         
            // vamos a suponer que los campos se crearon en el orden que indica la especificacion
            // de campos en la documentacion
         
            setId (statement.getShortText(index ++));
            setIdSisnot(statement.getShortText(index ++));
            setDescripcion(statement.getShortText(index ++));
            
            
        }catch(Exception e){
            throw new DbException(DbErrorCodes.NT_GET_ALL_VALUES, e);
        }
     
        if (isDebugeable)
            logger.debug("loadAllValues << ID:" + getId () + 
                    "loadAllValues << IDSisnot:" + getIdSisnot () +
                    "loadAllValues << descripcion:" + getDescripcion() );
                       
        return new Integer(index);
    }
  
  
    /**
     * Genera la sentencia de inserción de una notificacion.
     *
     * @param statement Sentencia sql precompilada.
     * @param idx Indice de posición del primer parámetro que se recoge
     * de la consulta.
     * @return Indice de posición del último parámtro recogido
     * @throws Exception Si se produce algún error.
     */
    public Integer insert(DbInputStatement statement, Integer idx)
    throws DbException {
        if (isDebugeable)
            logger.debug("insert >> statement: " + statement.toString() + " idx entrada: " + idx);
    
        int index = idx.intValue();
    
        try{
        
            statement.setShortText(index ++,getId ());
            statement.setShortText(index ++,getIdSisnot ()); 
            statement.setShortText(index ++,getDescripcion()); 
            
            
         		
        }catch(Exception e){
            throw new DbException(DbErrorCodes.NT_INSERT_ALL_VALUES,e);
        }

        if (isDebugeable)
            logger.debug("insert << statement: " + statement.toString());
    
        return new Integer(index);
    }
  
    
    /**
     * Realiza la consulta por guid.
     * 
     * 
     * 
     * @param guid GUID del documento.
     * @throws ClaveIncorrectaExcepcion si no es correcata la clave primaria
     *        DocumentosRepositorioExcepcion si el documento no existe
     */
    public void load(String entidad) throws ClaveIncorrectaExcepcion,DocumentosRepositorioExcepcion {
     
        if (getId() != null && !getId().equals("") ){
    
            DynamicTable tableInfo = new DynamicTable();
            DynamicRows rowsInfo = new DynamicRows();
            DynamicRow rowInfo = new DynamicRow();
            EstadosTabla tablaNoti = new EstadosTabla();
            DbConnection dbConn = new DbConnection();
     
            if (isDebugeable)
                logger.debug("load >> numero: " + getId());
    
            boolean incorrectGuid = false;
    
            try {
                dbConn.open(DBSessionManager.getSession(entidad));
                tableInfo.setTableObject(tablaNoti);
                tableInfo.setClassName(EstadosTabla.class.getName());
                tableInfo.setTablesMethod("getTableName");
                tableInfo.setColumnsMethod("getAllColumnNames");
                rowInfo.setClassName(EstadosDatos.class.getName());
                rowInfo.setValuesMethod("loadAllValues");
                rowInfo.addRow(this);
                rowsInfo.add(rowInfo);

                if (!DynamicFns.select( dbConn, 
                                        tablaNoti.getClausulaPorId( getId()), 
                                        tableInfo, 
                                        rowsInfo)) {
                        incorrectGuid = true;
                 }
            } catch (Exception e) {
                throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_RETRIEVE_DOCUMENT,e);
            } finally {
                try{
                     if (dbConn.existConnection())
                        dbConn.close();
                }catch(Exception ee){}
                if (incorrectGuid)
                    throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID);
            }       
        }else
              throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID);
    }
  
  
    /**
    * Añade una notificacion
    *
    * @throws Exception Si se produce algún error.
    */
    public void add(String entidad) throws Exception {
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfo = new DynamicRow();
        EstadosTabla table = new EstadosTabla();
        DbConnection dbConn = new DbConnection();
     
        logger.debug("add >> Numero de expediente: " + this.getId());
     
        try {
            dbConn.open(DBSessionManager.getSession(entidad));

            tableInfo.setTableObject(table);
            tableInfo.setClassName(EstadosTabla.class.getName());
            tableInfo.setTablesMethod("getTableName");
            tableInfo.setColumnsMethod("getAllColumnNames");

            rowInfo.addRow(this);
            rowInfo.setClassName(EstadosDatos.class.getName());
            rowInfo.setValuesMethod("insert");
            rowsInfo.add(rowInfo);

            DynamicFns.insert(dbConn, tableInfo, rowsInfo);
        } catch (Exception e) {
            throw new DbException(DbErrorCodes.NT_ADD_VALUE, e);
        } finally {
            try{
                if (dbConn.existConnection())
                    dbConn.close();
            }catch(Exception ee){}
        }
    }
  
  
    /**
    * Borra los expedientes asociadas a un guid.
    * @throws Exception Si se produce algún error.
    */
    public void delete(String entidad) 
    throws ClaveIncorrectaExcepcion,DocumentosRepositorioExcepcion{
         EstadosTabla table = new EstadosTabla();

         logger.debug("delete >> numero de expediente: " + getId());
     
         if (   getId() != null && !getId().equals("") ){
      
     
             DbConnection dbConn = new DbConnection();

             try {
                 dbConn.open(DBSessionManager.getSession(entidad));
                
                DbDeleteFns.delete(dbConn, table.getTableName(), table.getClausulaPorId(getId()));
               
             } catch (Exception e) {
                throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID,e);
             } finally {
                try{
                    if (dbConn.existConnection())
                        dbConn.close();
                }catch(Exception ee){
                    throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_DELETE_DOCUMENT,ee);
                }
             }
        }else
            throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID);
    }
  
    
        /**
    * Recupera la lista de docuemntos temporales.
    *
    * @return Lista de docuemnto temporales.
    * @throws Exception Si se produce algún error.
    */
    public Estados getEstados(String entidad) throws Exception {
        
        Estados notis = new Estados();
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfoProcedure = new DynamicRow();
        EstadosTabla table = new EstadosTabla();
        DbConnection dbConn = new DbConnection();
        EstadosDatos documentTmp;
        int counter, size;
         
        try {
            dbConn.open(DBSessionManager.getSession(entidad));

            tableInfo.setTableObject(table);
            tableInfo.setClassName(EstadosTabla.class.getName());
            tableInfo.setTablesMethod("getTableName");
            tableInfo.setColumnsMethod("getAllColumnNames");

            rowInfoProcedure.setClassName(EstadosDatos.class.getName());
            rowInfoProcedure.setValuesMethod("loadAllValues");
            rowsInfo.add(rowInfoProcedure);
            
          DynamicFns.selectMultiple(dbConn, null, false, tableInfo, rowsInfo);
              
            size = rowInfoProcedure.getRowCount();

            for (counter = 0; counter < size; counter++) {
               documentTmp = (EstadosDatos)rowInfoProcedure.getRow(counter);
               notis.add(documentTmp);
            }
        
        } catch (Exception e) {
            throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_RETRIEVE_DOCUMENT,e);
        } finally {
        	if (dbConn.existConnection())
                dbConn.close();
        }
        
        return notis;
    }
}