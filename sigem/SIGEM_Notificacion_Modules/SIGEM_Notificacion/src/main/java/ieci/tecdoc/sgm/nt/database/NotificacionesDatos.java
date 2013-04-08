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
import ieci.tecdoc.sgm.nt.database.datatypes.NotificacionImpl;
import ieci.tecdoc.sgm.nt.database.datatypes.Notificaciones;
import ieci.tecdoc.sgm.nt.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.nt.database.exception.DbException;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaExcepcion;
import ieci.tecdoc.sgm.nt.exception.DatosIncorrectosExcepcion;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioExcepcion;

import java.io.Serializable;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de expedientes del CT.
 *  
 */
public class NotificacionesDatos extends NotificacionImpl implements Serializable{
    private static Logger logger = Logger.getLogger(NotificacionesDatos.class);
    protected boolean isDebugeable = true;
    
    //private static Hashtable descripcionesEstadosCache = new Hashtable();
    public static final char ALL = 'a';
    public static final char SEARCH = 's';
    public static final char SEARCH_STATE = 'e';
    public static final char SEARCH_TO_UPDATE = 'u';

    
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

     
        statement.setLongInteger(index++, getEstado().intValue());
        statement.setDateTime(index++, getFechaActualizacionEstado());
        if(!StringUtils.isEmpty(getId())) statement.setLongText(index++,getId());
        statement.setShortText(index++,getError());
     
        return new Integer(index);
    }
    
    /**
     * Actualiza el conector cuyo identificador se pasa como parámetro.
    * @throws Exception Si se produce algún error.
    */
    public void update(String entidad) throws Exception {
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfo = new DynamicRow();
        NotificacionesTabla table = new NotificacionesTabla();
        DbConnection dbConn = new DbConnection();
     
        logger.debug("Update Noti <-- NotiId: " + getId());
     
        try {
            dbConn.open(DBSessionManager.getSession(entidad));
        
            tableInfo.setTableObject(table);
            tableInfo.setClassName(NotificacionesTabla.class.getName());
            tableInfo.setTablesMethod("getTableName");
            tableInfo.setColumnsMethod("getUpdateColumnNames");
            if(StringUtils.isEmpty(getId())) tableInfo.setColumnsMethod("getUpdateEstadoColumnNames");
        
            rowInfo.addRow(this);
            rowInfo.setClassName(NotificacionesDatos.class.getName());
            rowInfo.setValuesMethod("updateAll");
            rowsInfo.add(rowInfo);
        
            String where=table.getClausulaPorNotiId(
            		getNotiId(),getNifDestinatario(),getNumeroExpediente());
            logger.debug("NotiId="+getNotiId()+" Id="+getId()+" NIF="+getNifDestinatario()+" numExp="+getNumeroExpediente()+" estado="+getEstado().intValue()+" sentencia="+where);
            DynamicFns.update(dbConn,where, tableInfo, rowsInfo);
            
        } catch (Throwable e) {
        	logger.error(e);
            throw new DatosIncorrectosExcepcion(DocumentosRepositorioErrorCodigos.EC_UPDATE_STATE,e);
        } finally {
        if (dbConn.existConnection())
            dbConn.close();
     }
  }
  
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
            setNifDestinatario (statement.getShortText(index ++));
            setRegistroSalida(statement.getShortText(index ++));
            setFechaRegistroAnterior (statement.getDateTime(index ++));
            setNumeroExpediente (statement.getShortText(index ++));
            setProcedimientoExpedienteAnterior (statement.getShortText(index ++));
            setEstado(new Integer(statement.getLongInteger(index ++)));
            setFechaActualizacionEstado (statement.getDateTime(index ++));
            setFechaEfectuadaEntrega (statement.getDateTime(index ++));            
            setUsuario (statement.getShortText(index ++));
            setTipoCorrespondencia (statement.getShortText(index ++));
            setOrganismo (statement.getShortText(index ++));
            setAsunto (statement.getShortText(index ++));
            setTipo (statement.getShortText(index ++));        
            setTexto (statement.getShortText(index ++));        
            setNombreDestinatario (statement.getShortText(index ++));       
            setApellidosDestinatario (statement.getShortText(index ++));    
            setCorreoDestinatario (statement.getShortText(index ++));    
            setIdioma(statement.getShortText(index ++));    
            setTipoVia(statement.getShortText(index ++)); 
            setNombreVia(statement.getShortText(index ++)); 
            setNumeroVia(statement.getShortText(index ++)); 
            setEscaleraVia(statement.getShortText(index ++));         
            setPisoVia(statement.getShortText(index ++));        
            setPuertaVia(statement.getShortText(index ++));           
            setTelefono(statement.getShortText(index ++));    
            setMunicipio(statement.getShortText(index ++));    
            setProvincia(statement.getShortText(index ++));    
            setCodigoPostal(statement.getShortText(index ++));  
            setError(statement.getShortText(index ++));
            setNotiId (statement.getShortText(index ++));
            setSistemaId (statement.getShortText(index ++));
            setDEU (statement.getShortText(index ++));
            setMovil (statement.getShortText(index ++));
            
            //setDescripcionEstado(obtenerDescripcionEstado(getSistemaId()+"."+getEstado()));

        }catch(Exception e){
            throw new DbException(DbErrorCodes.NT_GET_ALL_VALUES, e);
        }
     
        if (isDebugeable)
            logger.debug("loadAllValues << ID:" + getId () + 
                        " ,Nif destinatario:" + getNifDestinatario () + 
                        " ,Registro de salida:" + getRegistroSalida() + 
                        " ,Fecha del regitro anterior:" + getFechaRegistroAnterior () + 
                        " ,Numero de expediente:" + getNumeroExpediente () + 
                        " ,Procedimiento de expediente anterior:" + getProcedimientoExpedienteAnterior () + 
                        " ,Fecha en la que se efectua la entrega:" + getFechaEfectuadaEntrega () );
     
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
            statement.setShortText(index ++,getNifDestinatario ()); 
            statement.setShortText(index ++,getRegistroSalida()); 
            statement.setDateTime(index ++, getFechaRegistroAnterior ()); 
            statement.setShortText(index ++,getNumeroExpediente ());
            statement.setShortText(index ++,getProcedimientoExpedienteAnterior ());
            statement.setLongInteger(index ++,getEstado().intValue());
            statement.setDateTime(index ++, getFechaActualizacionEstado());       
            statement.setDateTime(index ++, getFechaEfectuadaEntrega ()); 
            statement.setShortText(index ++, getUsuario ());
            statement.setShortText(index ++, getTipoCorrespondencia ());
            statement.setShortText(index ++,getOrganismo ());
            statement.setShortText(index ++, getAsunto ());
            statement.setShortText(index ++, getTipo ());        
            statement.setShortText(index ++, getTexto ());        
            statement.setShortText(index ++, getNombreDestinatario ());       
            statement.setShortText(index ++, getApellidosDestinatario ());    
            statement.setShortText(index ++, getCorreoDestinatario ());    
            statement.setShortText(index ++, getIdioma());    
            statement.setShortText(index ++, getTipoVia()); 
            statement.setShortText(index ++, getNombreVia()); 
            statement.setShortText(index ++, getNumeroVia()); 
            statement.setShortText(index ++, getEscaleraVia());         
            statement.setShortText(index ++, getPisoVia());        
            statement.setShortText(index ++, getPuertaVia());           
            statement.setShortText(index ++, getTelefono());    
            statement.setShortText(index ++, getMunicipio());    
            statement.setShortText(index ++, getProvincia());    
            statement.setShortText(index ++, getCodigoPostal());  
            statement.setShortText(index ++, getError()); 
            statement.setShortText(index ++,getNotiId ());
            statement.setShortText(index ++,getSistemaId ());
            statement.setShortText(index ++,getDEU());
            statement.setShortText(index ++,getMovil());
            
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
     
        if ((getNotiId() != null && !getNotiId().equals("")) ||
                (getNifDestinatario() != null && !getNifDestinatario().equals("") &&
                  getNumeroExpediente() != null && !getNumeroExpediente().equals("") )){
    
            DynamicTable tableInfo = new DynamicTable();
            DynamicRows rowsInfo = new DynamicRows();
            DynamicRow rowInfo = new DynamicRow();
            NotificacionesTabla tablaNoti = new NotificacionesTabla();
            DbConnection dbConn = new DbConnection();
     
            if (isDebugeable)
                logger.debug("load >> numero: " + getId());
    
            boolean incorrectGuid = false;
    
            try {
                dbConn.open(DBSessionManager.getSession(entidad));
                
//                if(descripcionesEstadosCache.size()==0) {
//                	rellenarEstados(entidad);
//                }
                
                tableInfo.setTableObject(tablaNoti);
                tableInfo.setClassName(NotificacionesTabla.class.getName());
                tableInfo.setTablesMethod("getTableName");
                tableInfo.setColumnsMethod("getAllColumnNames");
                rowInfo.setClassName(NotificacionesDatos.class.getName());
                rowInfo.setValuesMethod("loadAllValues");
                rowInfo.addRow(this);
                rowsInfo.add(rowInfo);

                if (!DynamicFns.select( dbConn, 
                         tablaNoti.getClausulaPorNotiId( getNotiId(),
                        		 getNifDestinatario(),getNumeroExpediente()), 
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
        NotificacionesTabla table = new NotificacionesTabla();
        DbConnection dbConn = new DbConnection();
     
        logger.debug("add >> Numero de expediente: " + this.getId());
     
        try {
            dbConn.open(DBSessionManager.getSession(entidad));

            tableInfo.setTableObject(table);
            tableInfo.setClassName(NotificacionesTabla.class.getName());
            tableInfo.setTablesMethod("getTableName");
            tableInfo.setColumnsMethod("getAllColumnNames");

            rowInfo.addRow(this);
            rowInfo.setClassName(NotificacionesDatos.class.getName());
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
         NotificacionesTabla table = new NotificacionesTabla();

         logger.debug("delete >> numero de expediente: " + getId());
     
         if ((getNotiId() != null && !getNotiId().equals("")) ||
                 (getNifDestinatario() != null && !getNifDestinatario().equals("") &&
                   getNumeroExpediente() != null && !getNumeroExpediente().equals("") )){
      
     
             DbConnection dbConn = new DbConnection();

             try {
                 dbConn.open(DBSessionManager.getSession(entidad));
                
                DbDeleteFns.delete(dbConn, table.getTableName(), table.getClausulaPorNotiId(
                		this.getNotiId(),this.getNifDestinatario(),this.getNumeroExpediente()));
               
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
  
    
     public Notificaciones getNotificaciones(char funcion_,
                                            ieci.tecdoc.sgm.nt.bean.IdentificadorBean params_, String entidad) throws Exception {
         ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaNotiBean criterios = new ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaNotiBean();
         criterios.setNotificacion(params_.getCodigoDeNotificacion());
         
         return  getNotificaciones(funcion_, criterios, entidad);
     }
    
    /**
    * Recupera la lista de docuemntos temporales.
    *
    * @return Lista de docuemnto temporales.
    * @throws Exception Si se produce algún error.
    */
    public Notificaciones getNotificaciones(char funcion_,
                                            ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaNotiBean params_, String entidad) throws Exception {
        Notificaciones notis = new Notificaciones();
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfoProcedure = new DynamicRow();
        NotificacionesTabla table = new NotificacionesTabla();
        DbConnection dbConn = new DbConnection();
        NotificacionesDatos documentTmp;
        int counter, size;
         
        try {
            dbConn.open(DBSessionManager.getSession(entidad));

//            if(descripcionesEstadosCache.size()==0) {
//            	rellenarEstados(entidad);
//            }
            
            tableInfo.setTableObject(table);
            tableInfo.setClassName(NotificacionesTabla.class.getName());
            tableInfo.setTablesMethod("getTableName");
            tableInfo.setColumnsMethod("getAllColumnNames");

            rowInfoProcedure.setClassName(NotificacionesDatos.class.getName());
            rowInfoProcedure.setValuesMethod("loadAllValues");
            rowsInfo.add(rowInfoProcedure);
            
            switch(funcion_){
            	case SEARCH_TO_UPDATE:
            		DynamicFns.selectMultiple(dbConn, table.getClausulaPorBusquedaEstadoParaActualizar( params_.getEstado() ), false, tableInfo, rowsInfo);
            		break;
                case SEARCH_STATE:
                    DynamicFns.selectMultiple(dbConn, table.getClausulaPorBusquedaEstado( params_.getEstado() ), false, tableInfo, rowsInfo);
                    break;
                case SEARCH:DynamicFns.selectMultiple(dbConn, table.getClausulaPorBusqueda( params_ ), false, tableInfo, rowsInfo);
                    break;
                default:DynamicFns.selectMultiple(dbConn, null, false, tableInfo, rowsInfo);
                    break;
            }
                    
            size = rowInfoProcedure.getRowCount();

            for (counter = 0; counter < size; counter++) {
               documentTmp = (NotificacionesDatos)rowInfoProcedure.getRow(counter);
               notis.add(documentTmp);
            }
        
        } catch (Exception e) {
            throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_RETRIEVE_DOCUMENT,e);
        } finally {
        		if(dbConn.existConnection()){
        			 dbConn.close();
        		}
        }
        
        return notis;
    }
    
//    private synchronized void rellenarEstados(String entidad) throws Exception {
//        if(descripcionesEstadosCache.size()==0) {
//	    	Estados estados = new EstadosDatos().getEstados(entidad);
//	    	for(int i=0; i<estados.count(); i++) {
//	    		Estado estado = estados.get(i);
//	    		descripcionesEstadosCache.put(estado.getId(), estado.getDescripcion());
//	    	}
//	    		
//        }
//    }
    
//    private String obtenerDescripcionEstado(String id) {
//    	if(id==null) {
//    		return null;
//    	} else {
//    		Object descripcion = descripcionesEstadosCache.get(id);
//    		if(descripcion==null) {
//    			return id.toString();
//    		} else {
//    			return descripcion.toString();
//    		}
//    	}
//    }
}
