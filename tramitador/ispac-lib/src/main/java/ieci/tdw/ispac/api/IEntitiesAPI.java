package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.entity.EntityResources;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;

import java.util.List;
import java.util.Map;

public interface IEntitiesAPI {

    /**
     * Obtiene una entidad sin ningún valor de registro a partir del identificador de la entidad.
     *
     * @param entityId identificador de la entidad
     * @return IItem registro vacío de la entidad
     * @throws ISPACException
     */
    public IItem getEntity(int entityId) 
    	throws ISPACException;
    
    /**
     * Obtiene una entidad sin ningún valor de registro a partir de la definición de la entidad.
     *
     * @param entityDef definición de la entidad
     * @return IItem registro vacío de la entidad
     * @throws ISPACException
     */
    public IItem getEntity(IEntityDef entitydef) 
    	throws ISPACException;

    /**
     * Obtiene una entidad sin ningún valor de registro a partir del nombre de la entidad.
     *
     * @param entityName nombre de la entidad
     * @return IItem registro vacío de la entidad
     * @throws ISPACException
     */
    public IItem getEntity(String entityName) 
    	throws ISPACException;

    /**
     * Obtiene la descripción de un campo de una entidad catalogada. 
     * @param entityName Nombre de la entidad
     * @param fieldName Nombre del campo de la entidad
     * @return Property información del campo.
     * @throws ISPACException
     */
    public Property getEntityFieldProperty(String entityName, String fieldName) throws ISPACException;

    /**
     * Crea un nuevo registro para la entidad a partir del identificador de la entidad y
     * establece el número de secuencia en el campo clave de la entidad.
     *
     * @param entityId identificador de la entidad
     * @return IItem registro de la entidad con clave
     * @throws ISPACException
     */
    public IItem createEntity(int entityId) 
    	throws ISPACException;
    
    /**
     * Crea un nuevo registro para la entidad a partir del nombre de la entidad
     * establece el número de secuencia en el campo clave de la entidad y el
     * número de expediente en el campo número de expediente de la entidad
     * (SPAC_CT_ENTIDADES - CAMPO_NUMEXP) si tiene valor.
	 * 
     * @param entityname nombre de la entidad
     * @param numexp número de expediente
     * @return IItem registro de la entidad con clave y número de expediente si tiene
     * @throws ISPACException
	 */
    public IItem createEntity(String entityname, String numexp) 
    	throws ISPACException;
    
    /**
     * Obtiene el registro de una entidad.
     *
     * @param entityId identificador de la entidad
     * @param entityRegId identificador del registro de la entidad
     * @return IItem registro con datos de la entidad
     * @throws ISPACException
     */
    public IItem getEntity(int entityId, int entityRegId) 
    	throws ISPACException;

    
    /**
     * Obtiene el registro de una entidad.
     *
     * @param entityId nombre de la entidad
     * @param entityRegId identificador del registro de la entidad
     * @return IItem registro con datos de la entidad
     * @throws ISPACException
     */
    public IItem getEntity(String entityName, int entityRegId) 
	throws ISPACException;    
    
    /**
     * Obtiene el registro de una entidad.
     *
     * @param entityDef definición de la entidad
     * @param entityRegId identificador del registro de la entidad
     * @return IItem registro con datos de la entidad
     * @throws ISPACException
     */
    public IItem getEntity(IEntityDef entitydef, int entityRegId) 
    	throws ISPACException;

    /**
     * Obtiene una colección de entidades resultado de una consulta.
     *
     * @param entityId identificador de la entidad
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return IItemCollection colección de objetos EntityDAO resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(int entityId, String query)
    	throws ISPACException;
    
    /**
     * Obtiene una colección de entidades resultado de una consulta
     * bloqueando los registros de la tabla de la entidad.
     *
     * @param entityId identificador de la entidad
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return IItemCollection colección de objetos EntityDAO resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntitiesForUpdate(int entityId, String query)
    	throws ISPACException;
    
    /**
     * Obtiene una colección de entidades resultado de una consulta.
     *
     * @param entityName Nombre de la entidad (tabla asociada)
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return IItemCollection colección de objetos EntityDAO resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(String entityName, String query)
    	throws ISPACException;

    /**
     * @param entityName Nombre de la entidad (tabla asociada) 
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return número de registro eliminados
     * @throws ISPACException
     */
    public int deleteEntities(String entityName, String query)
    	throws ISPACException;
    
    /**
     * 
     * @param entityName Nombre de la entidad
     * @param sqlBase consulta sql sólo con la clausula select y where
     * @param order por lo que quiera ordenar el usuario, en caso de que se vaya a usuar la clausula order by
     * @param limit número máximo de registros que va a devolver la consulta
     * @return Colección de objetos EntityDAO resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(String entityName, String sqlBase, String order, int limit)
    	throws ISPACException;
    
    /**
     * Obtiene una colección de entidades resultado de una consulta.
     *
     * @param entityDef definición de la entidad
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return IItemCollection colección de objetos EntityDAO resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(IEntityDef entityDef, String query)
    	throws ISPACException;
    
    /**
     * 
     * @param entityName Nombre de la entidad sobre la que se va a realizar la consulta
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return int númer de objetos EntityDAO que cumplen la consulta.
     * @throws ISPACException 
     */
    public int countResultQuery(String entityName, String query) throws ISPACException;

    /**
     * Obtiene el número de entidades que cumplen la consulta.
     *
     * @param entityId identificador de la entidad
     * @param query búsqueda a realizar sobre los registros de la entidad
     * @return int número de objetos EntityDAO que cumplen la consulta
     * @throws ISPACException
     */
    public int countEntities(int entityId, String query)
        throws ISPACException;

    /**
     * Obtiene el número de entidades que intervienen en el proceso a partir de su número de expediente.
     *
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @return int número de objetos EntityDAO existentes
     * @throws ISPACException
     */
    public int countProcessEntities(int entityId, String numexp)
        throws ISPACException;
    
    /**
     * Obtiene el número de documentos asociados a la fase que están pendientes de firma.
     * @param stageId Identificador de la fase activa.
     * @return Número de documentos
     * @throws ISPACException si ocurre algún error.
     */
    public int countStageDocumentsInSignCircuit(int stageId) 
    	throws ISPACException;
    
    /**
     * Obtiene el número de documentos asociados al trámite que están pendientes de firma.
     * @param taskId Identificador del trámite activo.
     * @return Número de documentos
     * @throws ISPACException si ocurre algún error.
     */
    public int countTaskDocumentsInSignCircuit(int taskId) 
    	throws ISPACException;

    /**
     * <p>Permite especificar una consulta sobre una o m&aacute;s entidades o tablas.
     * Para ello es preciso definir un mapa con el prefijo que se quiere asignar a
     * las tablas que intervienen la consulta. El resultado es una colecci&oacute;n de IItems
     * compuestos por el cada una de las filas resultado del join con las propiedades
     * calificadas seg&uacute;n el prefijo asociado con anterioridad
     * Los IItems resultantes de la consulta no son actualizables mediante el método store().
     *  </p>
     * <br>
     * <p>
     * Ejemplo: Para realizar el siguiente join con el api de entidades.<br><br>
     * <tt>
     * SELECT * FROM SPAC_PROCESOS PROCESO, SPAC_FASES FASE WHERE PROCESO.ID = FASE.ID_EXP
     * </tt><br><br>
     * Se utilizaría el siguiente código.<br>
     * <pre>
     * tableentitymap.put("PROCESO","SPAC_PROCESOS");
     * tableentitymap.put("FASE","SPAC_FASES");
     *
     * IItemCollection consulta;
     * consulta=entityAPI.queryJoinEntities(tableentitymap," WHERE PROCESO.ID = FASE.ID_EXP ");
     *</pre>
     *<br><br>
     * Para acceder a las propiedades de los IItems resultado de la consulta
     * hay que calificarlas adecuadamente utilizando su prefijo
     * <br><br>
     * <pre>
     * consulta.getString("PROCESO:NUMEXP");
     * consulta.getString("FASE:NOMBRE");
     * </pre>
     *</p>
     * <p>
     * Es posible especificar para el join tanto tablas como entidades. Basta con asignar al
     * mapa de prefijos el nombre de la tabla o un objeto Integer o Long con el identificador
     * de la entidad.
     * </p> <br><br>
     * <pre>
     * tableentitymap.put("PROCESO","SPAC_PROCESOS");
     * tableentitymap.put("EXPED",new Integer(ISPACEntities.DT_ID_EXPEDIENTES);
     * tableentitymap.put("DATOS",new Integer(23);
     *</pre>
     *
     * @param tableentitymap mapa con la información necesaria de las tablas o entidades
     * 		  que intervienen en el join
     * @param query búsqueda a realizar sobre las entidades especificada mediante SQL
     * @return IItemCollection colección de objetos resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(Map tableentitymap, String query)
    	throws ISPACException;

    /**
     * <p>Permite especificar una consulta sobre una o m&aacute;s entidades o tablas.
     * Para ello es preciso definir un mapa con el prefijo que se quiere asignar a
     * las tablas que intervienen la consulta. El resultado es una colecci&oacute;n de IItems
     * compuestos por el cada una de las filas resultado del join con las propiedades
     * calificadas seg&uacute;n el prefijo asociado con anterioridad</p>
     * <br>
     * <p>
     * Ejemplo: Para realizar el siguiente join con el api de entidades.<br><br>
     * <tt>
     * SELECT * FROM SPAC_PROCESOS PROCESO, SPAC_FASES FASE WHERE PROCESO.ID = FASE.ID_EXP
     * </tt><br><br>
     * Se utilizaría el siguiente código.<br>
     * <pre>
     * tableentitymap.put("PROCESO","SPAC_PROCESOS");
     * tableentitymap.put("FASE","SPAC_FASES");
     *
     * IItemCollection consulta;
     * consulta=entityAPI.queryJoinEntities(tableentitymap," WHERE PROCESO.ID = FASE.ID_EXP ");
     *</pre>
     *<br><br>
     * Para acceder a las propiedades de los IItems resultado de la consulta
     * hay que calificarlas adecuadamente utilizando su prefijo
     * <br><br>
     * <pre>
     * consulta.getString("PROCESO:NUMEXP");
     * consulta.getString("FASE:NOMBRE");
     * </pre>
     *</p>
     * <p>
     * Es posible especificar para el join tanto tablas como entidades. Basta con asignar al
     * mapa de prefijos el nombre de la tabla o un objeto Integer o Long con el identificador
     * de la entidad.
     * </p> <br><br>
     * <pre>
     * tableentitymap.put("PROCESO","SPAC_PROCESOS");
     * tableentitymap.put("EXPED",new Integer(ISPACEntities.DT_ID_EXPEDIENTES);
     * tableentitymap.put("DATOS",new Integer(23));
     * </pre>
     *
     * @param tableentitymap mapa con la información necesaria de las tablas o entidades
     * 		  que intervienen en el join
     * @param query búsqueda a realizar sobre las entidades especificada mediante SQL
     * @param distinct devuelve filas únicas
     * @return IItemCollection colección de objetos resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(Map tableentitymap, String query,boolean distinct)
    	throws ISPACException;

    /**
     * Obtiene una colección de entidades resultado de una consulta.
     *
     * @param tablename tabla de la entidad
     * @param keyproperty campo clave de la entidad
     * @param sequence secuencia de la entidad
     * @param query búsqueda a realizar sobre la entidad
     * @return IItemCollection colección de objetos EntityDAO resultado de la consulta
     * @throws ISPACException
     */
    public IItemCollection queryEntities(String tablename,String keyproperty,String sequence,String query)
        throws ISPACException;

    /**
     * Obtiene una entidad asociada a un procedimiento.
     *
     * @param procedureId identificador del procedimiento
     * @param entityId identificador de la entidad
     * @return IItem objeto EntityProcedureDAO con la entidad del procedimiento
     * @throws ISPACException
     */
    public IItem getProcedureEntity(int procedureId, int entityId)
    	throws ISPACException;

    /**
     * Obtiene una colección con las entidades asociadas a un procedimiento.
     *
     * @param procedureId identificador del procedimiento
     * @return IItemCollection colección de objetos EntityProcedureDAO de entidades del procedimiento
     * @throws ISPACException
     */
    public IItemCollection getProcedureEntities(int procedureId)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con las entidades asociadas a un procedimiento
     * y que están visibles para la fase o trámite del procedimiento.
     *
     * @param procedureId identificador del procedimiento
     * @return IItemCollection colección de objetos EntityProcedureDAO de entidades del procedimiento
     * @throws ISPACException
     */
    public IItemCollection getProcedureEntities(int procedureId, int stagePcdId, int taskPcdId)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con las entidades asociadas a una fase de un procedimiento.
     *
     * @param procedureId identificador del procedimiento
     * @param stageId identificador de la fase en el procedimiento
     * @return IItemCollection colección de objetos EntityProcedureDAO de entidades de la fase en el procedimiento
     * @throws ISPACException
     */
    public IItemCollection getStageEntities(int procedureId, int stagePcdId)
    	throws ISPACException;

    /**
     * Obtiene una colección con las entidades asociadas a un trámite de un procedimiento.
     *
     * @param procedureId identificador del procedimiento
     * @param taskId identificador del trámite en el procedimiento
     * @return IItemCollection colección de objetos EntityProcedureDAO de entidades del trámite en el procedimiento
     * @throws ISPACException
     */
    public IItemCollection getTaskEntities(int procedureId, int taskId)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los registros de una entidad de un expediente concreto,
     * a partir del nombre de la entidad y el número de expediente.
     *
     * @param entityname nombre de la entidad
     * @param numexp número de expediente
     * @return IItemCollection collección de objetos CTEntityDAO de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getEntities(String entityname, String numexp)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente.
     *
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @return IItemCollection collección de objetos CTEntityDAO de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getEntities(int entityId, String numexp)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los registros de una entidad de un expediente concreto,
     * a partir del nombre de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     *
     * @param entityname nombre de la entidad
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los registros de entidades
     * @return IItemCollection collección de objetos CTEntityDAO de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getEntities(String entityname, String numexp, String sqlQuery)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     *
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los registros de entidades
     * @return IItemCollection collección de objetos CTEntityDAO de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getEntities(int entityId, String numexp, String sqlQuery)
    	throws ISPACException;
    
    /**
     * Obtiene una colección ordenada con los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     *
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los registros de entidades
     * @param order criterio de ordenación para la consulta
     * @return CollectionDAO collección de objetos CTEntityDAO de entidades del expediente
     */
    public IItemCollection getEntitiesWithOrder(int entityId, String numexp, String sqlQuery, String order)
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK) y el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) de los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente.
     *
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @return IItemCollection colección de objetos de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getSchemeEntities(int entityId, String numexp)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK) y el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) de los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente.
     *
     * @param entityDef definición de la entidad
     * @param numexp número de expediente
     * @return IItemCollection colección de objetos de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getSchemeEntities(IEntityDef entitydef, String numexp)
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK) y el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) de los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     * Además, se pueden añadir propiedades extra que formen parte de la entidad en su representación
     * para el esquema.
     *
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @param query consulta a realizar para seleccionar los registros de entidades
     * @param extraprop propiedades a añadir al esquema
     * @return CollectionDAO colección de objetos de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getSchemeEntities(int entityId, String numexp, String query, Property[] extraprop)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK) y el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) de los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     * Además, se pueden añadir propiedades extra que formen parte de la entidad en su representación
     * para el esquema.
     *
     * @param entityDef definición de la entidad
     * @param numexp número de expediente
     * @param query consulta a realizar para seleccionar los registros de entidades
     * @param extraprop propiedades a añadir al esquema
     * @return CollectionDAO colección de objetos de entidades del expediente
     * @throws ISPACException
     */
    public IItemCollection getSchemeEntities(IEntityDef entitydef, String numexp, String query, Property[] extraprop)
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK) y el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) de los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     * Además, se pueden añadir propiedades extra que formen parte de la entidad en su representación
     * para el esquema.
     *
     * @param entityDef definición de la entidad
     * @param numexp número de expediente
     * @param query consulta a realizar para seleccionar los registros de entidades
     * @param extraprop propiedades a añadir al esquema
     * @param orderBy Indica el campo por el que se quiere ordenar
     * @param desc Indica si se quiere ordernar descencientemente
     * @return CollectionDAO colección de objetos de entidades del expediente ordenados por el campo orderBy
     * @throws ISPACException
     */
    public IItemCollection getSchemeEntities(IEntityDef entitydef, String numexp, String query, Property[] extraprop ,String orderBy , boolean desc)
    	throws ISPACException;
    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK), el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) y el estado para los documentos
     * con estado FINALIZADO de un expediente concreto, a partir número de expediente.
     *
     * @param numexp número de expediente
     * @return IItemCollection colección de documentos asociados al expediente
     * @throws ISPACException
     */
    public IItemCollection getSchemeProcessDocuments(String numexp)
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK), el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) y el estado para los documentos
     * asociados a una fase de un expediente concreto cuyo autor es el usuario conectado,
     * a partir número de expediente y el idenfificador de la fase. 
     *
     * @param numexp número de expediente
     * @param stageId identificador de la fase en el expediente
     * @return IItemCollection colección de documentos asociados a la fase
     * @throws ISPACException
     */
    public IItemCollection getSchemeStageDocuments(String numexp, int stageId) 
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK), el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) y el estado para los documentos de un tipo
     * asociados a una fase de un expediente concreto cuyo autor es el usuario conectado,
     * a partir número de expediente y el idenfificador de la fase. 
     *
     * @param numexp número de expediente
     * @param stageId identificador de la fase en el expediente
     * @param docTypeId identificador del tipo de documento
     * @return IItemCollection colección de documentos de un tipo asociados a la fase
     * @throws ISPACException
     */
    public IItemCollection getSchemeStageDocuments(String numexp, int stageId, int docTypeId)
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK), el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) y el estado para los documentos
     * asociados a un trámite de un expediente concreto cuyo autor es el usuario conectado,
     * a partir número de expediente y el idenfificador del trámite.
     *
     * @param numexp número de expediente
     * @param taskId identificador del trámite en el expediente
     * @return IItemCollection colección de documentos asociados al trámite
     * @throws ISPACException
     */
    public IItemCollection getSchemeTaskDocuments(String numexp, int taskId)
    	throws ISPACException;

    /**
     * Obtiene una colección con el identificador (SPAC_CT_ENTIDADES - CAMPO_PK), el esquema
     * o campo diferenciador (SPAC_CT_ENTIDADES - SCHEMA_EXPR) y el estado para los documentos de un tipo
     * asociados a un trámite de un expediente concreto cuyo autor es el usuario conectado,
     * a partir número de expediente y el idenfificador del trámite.
     *
     * @param numexp número de expediente
     * @param taskId identificador del trámite en el expediente
     * @param docTypeId identificador del tipo de documento
     * @return IItemCollection colección de documentos de un tipo asociados al trámite
     * @throws ISPACException
     */
    public IItemCollection getSchemeTaskDocuments(String numexp, int taskId,int documentId)
    	throws ISPACException;

    /**
     * Obtiene la descripción de una entidad catálogada. 
     * Consultar <tt>SPAC_CT_ENTIDADES<tt> en el módelo de datos.
     *
     * @param entityId identificador de la entidad
     * @return IItem definición de la entidad
     * @throws ISPACException
     */
    public IItem getCatalogEntity(int entityId)
    	throws ISPACException;
    
    /**
     * Obtiene la descripción de una entidad catálogada. 
     * Consultar <tt>SPAC_CT_ENTIDADES<tt> en el módelo de datos.
     *
     * @param entityName Nombre de la entidad (tabla asociada)
     * @return IItem definición de la entidad
     * @throws ISPACException
     */
    public IItem getCatalogEntity(String entityName)
    	throws ISPACException;

    /**
     * Obtiene una colección con todas las descripciones de las entidades existentes en el catálogo.
     * Consultar <tt>SPAC_CT_ENTIDADES<tt> en el módelo de datos.
     *
     * @return IItemCollection colección con todas las entidades catalogadas
     * @throws ISPACException
     */
    public IItemCollection getCatalogEntities()
    	throws ISPACException;

    /**
     * Obtiene una colección con las descripciones de las entidades existentes en el catálogo
     * a partir de una consulta.
     * Consultar <tt>SPAC_CT_ENTIDADES<tt> en el módelo de datos.
     *
     * @param query búsqueda a realizar sobre las entidades del catálogo
     * @return IItemCollection colección con las entidades catalogadas que cumplen la consulta
     * @throws ISPACException
     */
    public IItemCollection getCatalogEntities(String query)
    	throws ISPACException;

    
    // EXPEDIENTES //

    /**
     * Devuelve el registro de la entidad de expedientes.
     *
     * @param regentityId identificador del registro en la entidad de expedientes
     * @return IItem registro del expediente solicitado
     * @throws ISPACException
     */
    public IItem getExpedient(int regentityId) 
    	throws ISPACException;

    /**
     * Devuelve el registro de la entidad de expedientes 
     * a partir del número de expediente.
     *
     * @param numexp número de expediente
     * @return IItem registro del expediente solicitado
     * @throws ISPACException
     */
    public IItem getExpedient(String numexp) 
    	throws ISPACException;

    /**
     * Obtiene los registros de la entidad de expedietes
     * a partir del número de registro.
     * 
     * @param regNum Número de registro.
     * @return Lista de expedientes.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getExpedientsByRegNum(String regNum)
		throws ISPACException;
    
    // INTERVINIENTES //
    
    /**
     * Devuelve el registro de la entidad de intervinientes.
     *
     * @param entityRegId identificador del registro en la entidad de intervinientes
     * @return IItem registro del interviniente solicitado
     * @throws ISPACException
     */
    public IItem getParticipant(int entityRegId) 
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los intervinientes asociados a un expediente concreto,
     * a partir del número de expediente y una consulta para seleccionar los intervinientes.
     *
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los intervinientes
     * @param order criterio de ordenación
     * @return IItemCollection colección de intervientes del expediente
     * @throws ISPACException
     */
    public IItemCollection getParticipants(String numexp, String sqlQuery, String order) 
    	throws ISPACException;


    //   DOCUMENTOS //
    
    /**
     * Devuelve el registro de la entidad de documentos.
     *
     * @param entityRegId identificador del registro en la entidad de documentos
     * @return IItem registro del documento solicitado
     * @throws ISPACException
     */
    public IItem getDocument(int entityRegId) 
    	throws ISPACException;

    /**
     * Obtiene una colección con los documentos asociados a un expediente concreto,
     * a partir del número de expediente y una consulta para seleccionar los documentos.
     *
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los documentos
     * @param order criterio de ordenación
     * @return IItemCollection colección de documentos del expediente
     * @throws ISPACException
     */
    public IItemCollection getDocuments(String numexp, String sqlQuery, String order)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los documentos asociados a un registro de una entidad.
     *
     * @param entityId identificador de la entidad en el catálogo
     * @param entityRegId identificador del registro de la entidad
     * @return IItemCollection colección de documentos del registro de la entidad
     * @throws ISPACException
     */
    public IItemCollection getEntityDocuments(int entityId, int entityRegId)
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los documentos generados con una determinada plantilla
     * y asociados a un expediente concreto que estén asociados a trámites abiertos,  
     * a partir del número de expediente y el identificador de la plantilla.
     * 
     * @param numexp número de expediente
     * @param idTemplate identificador de la plantilla con la que se generó el documento
     * @return IItemCollection colección de documentos del expediente
     * @throws ISPACException
     */
	public IItemCollection getDocumentsOpenedTask(String numexp, Integer idTemplate)
		throws ISPACException;
    
    
    /**
     * Obtiene una colección con los documentos generados con una determinada plantilla
     * y asociados a un expediente concreto,  
     * a partir del número de expediente y el identificador de la plantilla.
     * 
     * @param numexp número de expediente
     * @param idTemplate identificador de la plantilla con la que se generó el documento
     * @return IItemCollection colección de documentos del expediente
     * @throws ISPACException
     */
    public IItemCollection getDocuments(String numexp, int idTemplate) 
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los documentos creados en un trámite de un expediente,
     * a partir del número de expediente y del identificador del trámite.
     * 
     * @param numexp número del expediente
     * @param taskId identificador del trámite en el expediente
     * @return IItemCollection colección de documentos creados en el trámite
     * @throws ISPACException
     */
    public IItemCollection getTaskDocuments(String numexp, int taskId) 
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los documentos creados en una fase de un expediente,
     * a partir del número de expediente y del identificador de la fase.
     * 
     * @param numexp número del expediente
     * @param stageId identificador de la fase en el expediente
     * @return IItemCollection colección de documentos creados en la fase
     * @throws ISPACException
     */
    public IItemCollection getStageDocuments(String numexp, int stageId) 
    	throws ISPACException;
    
    /**
     * Obtiene una colección con los documentos creados en una fase de un expediente,
     * a partir del identificador de la fase.
     * 
     * @param stageId identificador de la fase en el expediente
     * @return IItemCollection colección de documentos creados en la fase
     * @throws ISPACException
     */
    public IItemCollection getStageDocuments(int stageId) throws ISPACException;
    
    // APLICACIONES QUE PRESENTAN LAS ENTIDADES //

    /**
     * Obtiene la aplicación solicitada para la entidad y la devuelve debidamente cargada
     * con los datos del registro a partir del identificador suministrado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     *
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param appId identificador de la aplicación si ya viene especificada
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getEntityApp(String numExp, int procedureId, int appId, int entityId, int entityRegId, String path, int urlKey)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación solicitada para la entidad y la devuelve debidamente cargada
     * con los datos del registro a partir del identificador suministrado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     *
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param appId identificador de la aplicación si ya viene especificada
     * @param entitydef definición de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getEntityApp(String numExp, int procedureId, int appId, IEntityDef entitydef, int entityRegId, String path, int urlKey)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación asociada a la entidad para el procedimiento suministrado y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     *
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getProcedureEntityApp(String numExp, int procedureId, int entityId, int entityRegId, String path, int urlKey, Map params)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación asociada a la entidad para el procedimiento suministrado y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     *
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param entityDef definición de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getProcedureEntityApp(String numExp, int procedureId, IEntityDef entitydef, int entityRegId, String path, int urlKey, Map params)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación asociada a la entidad para la fase suministrada y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param stagePcdId identificador de la fase en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getStageEntityApp(String numExp, int procedureId, int stagePcdId, int entityId, int entityRegId, String path, int urlKey, Map params)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación asociada a la entidad para la fase suministrada y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param stagePcdId identificador de la fase en el procedimiento para la que se busca la aplicación
     * @param entityDef definición de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getStageEntityApp(String numExp, int procedureId, int stagePcdId, IEntityDef entitydef, int entityRegId, String path, int urlKey, Map params)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación asociada a la entidad para el trámite suministrado y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param taskPcdId identificador del trámite en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (ID_TRAMITE = 0)
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getTaskEntityApp(String numExp, int procedureId, int taskPcdId, int entityId, int entityRegId, String path, int urlKey, boolean noDefault, Map params)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación asociada a la entidad para el trámite suministrado y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param taskPcdId identificador del trámite en el procedimiento para la que se busca la aplicación
     * @param entityDef definición de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (ID_TRAMITE = 0)
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getTaskEntityApp(String numExp, int procedureId, int taskPcdId, IEntityDef entitydef, int entityRegId, String path, int urlKey, boolean noDefault, Map params)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación solicitada para la entidad.
	 * La aplicación no contiene ningún dato de registro
	 * ya que no se ha especificado el identificador. 
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param appId identificador de la aplicación si ya viene especificada
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getEntityApp(String numexp, int procedureId, int appId, int entityId, String path, int urlKey)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación solicitada para la fase suministrada.
	 * La aplicación no contiene ningún dato de registro
	 * ya que no se ha especificado el identificador. 
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param stagePcdId identificador de la fase en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    /*
    public EntityApp getStageEntityApp(String numExp, int procedureId, int stagePcdId, int entityId, String path, int urlKey)
    	throws ISPACException;
    */
    
    /**
     * Obtiene la aplicación solicitada para el trámite suministrado.
	 * La aplicación no contiene ningún dato de registro
	 * ya que no se ha especificado el identificador. 
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param taskPdcId identificador del trámite en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_tramite=0)
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    /*
    public EntityApp getTaskEntityApp(String numExp, int procedureId, int taskPcdId, int entityId, String path, int urlKey, boolean noDefault)
    	throws ISPACException;
    */
    
    /**
     * Obtiene la aplicación asociada a la entidad de expediente
     * para el proceso con el número de expediente suministrado y
     * la entrega rellenada con los datos del expediente.
     *
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param path ruta de la aplicación
     * @param urlKey clave de la entidad en la url
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getExpedientEntityApp(String numexp, int procedureId, String path, int urlKey)
    	throws ISPACException;
    
    /**
     * Obtiene la aplicación solicitada para la entidad.
     * Además, crea un nuevo registro para la entidad que es precisamente
     * al que hará referencia la aplicación.
     *
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param appId identificador de la aplicación si ya viene especificada
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    /*
    public EntityApp newEntityApp(int procedureId, int appId, int entityId, String path)
    	throws ISPACException;
    */

    /**
     * Busca la aplicación por defecto para visualizar la entidad.
     * Además, crea un nuevo registro para la entidad que es precisamente
     * al que hará referencia la aplicación.
     *
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp newEntityApp(int entityId, String path)
    	throws ISPACException;

    /**
     * Busca la aplicación definida en el procedimiento para visualizar la entidad.
     * Además, crea una nuevo registro para la entidad que es precisamente
     * al que hará referencia la aplicación.
     * 
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    /*
    public EntityApp newProcedureEntityApp(int procedureId, int entityId, String path)
    	throws ISPACException;
    */

    /**
     * Busca la aplicación definida en la fase indicada para visualizar la entidad.
     * Además, crea una nuevo registro para la entidad que es precisamente
     * al que hará referencia la aplicación.
     * 
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param stagePcdId identificador de la fase en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    /*
    public EntityApp newStageEntityApp(int procedureId, int stagePcdId, int entityId, String path)
    	throws ISPACException;
    */

    /**
     * Busca la aplicación definida en el trámite indicado para visualizar la entidad.
     * Además, crea una nuevo registro para la entidad que es precisamente
     * al que hará referencia la aplicación.
     * 
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param taskPcdId dentificador del trámite en el procedimiento para el que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada
     * @see EntityApp
     * @throws ISPACException
     */
    /*
    public EntityApp newTaskEntityApp(int procedureId, int taskPcdId, int entityId, String path)
    	throws ISPACException;
    */

    /**
     * Obtiene la aplicación asociada a la entidad para el procedimiento suministrado y
     * la devuelve debidamente cargada con los datos del registro indicado
     * y asociado al número de expediente.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     *
     * @param numexp número de expediente
     * @param procedureId identificador del procedimiento para el que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param entityRegId identificador del registro a cargar por la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getCatalogEntityApp(String numexp, int procedureId, int entityId, int entityRegId, String path)
    	throws ISPACException;

    /**
     * Obtiene la aplicación asociada a la entidad y
     * la devuelve debidamente cargada con los datos del item suministrado.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     *
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param item registro con los datos a cargar en la aplicación
     * @param path ruta de la aplicación
     * @return aplicación solicitada con los datos de la entidad
     * @see EntityApp
     * @throws ISPACException
     */
    public EntityApp getCatalogEntityApp(int entityId, IItem item, String path)
    	throws ISPACException;
    
    
    // TRÁMITES //
    
    /**
     * Devuelve el registro de la entidad de trámites
     * a partir del identificador del trámite en el proceso.
     * 
     * @param taskProcessId identificador del trámite en el proceso
     * @return IItem registro del trámite solicitado
     * @throws ISPACException
     */
    public IItem getTask(int taskProcessId) 
    	throws ISPACException;

    /**
     * Devuelve el registro de la entidad de trámites.
     * 
     * @param taskId identificador del registro en la entidad de trámites
     * @return IItem registro del trámite solicitado
     * @throws ISPACException
     */
    public IItem getEntityTask(int taskId)
    	throws ISPACException;

    /**
     * Obtiene una colección con los trámites del número de expediente suministrado.
     *
     * @param numexp número de expediente
     * @return CollectionDAO colección de objetos CTEntityDAO de trámites del expediente
     * @throws ISPACException
     */
    public IItemCollection getExpedientTasks(String numexp)
    	throws ISPACException;

    /**
     * Obtiene una colección con los trámites del número de expediente suministrado
     * y para un determinada fase.
     *
     * @param numexp número de expediente
     * @param stagepcd identificador de la fase en el procedimiento
     * @return CollectionDAO colección de objetos CTEntityDAO de trámites del expediente
     * @throws ISPACException
     */
    public IItemCollection getStageTasks (String numexp, int stagepcd)
    	throws ISPACException;

    
    // ENTIDADES //

    /**
     * Obtiene el sustituto de un valor para una entidad de sustitución,
     * a partir del identificador de la entidad y el valor suministrado.
     * 
     * @param entityId identificador de la entidad de sustitución
     * @param value valor del sustituto
     * @return String sustituto para el valor suministrado
     * @throws ISPACException
     */
    public String getSubstitute(int entityId, String value) 
    	throws ISPACException;
    
    /**
     * Obtiene el sustituto de un valor para una entidad de sustitución,
     * a partir del nombre de la entidad y el valor suministrado.
     * 
     * @param entityname nombre de la entidad de sustitución
     * @param value valor del sustituto
     * @return String sustituto para el valor suministrado
     * @throws ISPACException
     */
    public String getSubstitute(String entityname, String value) 
    	throws ISPACException;
    
    /**
     * Obtiene el IItem del sustituto de un valor para una entidad de sustitución,
     * a partir del nombre de la entidad y el valor suministrado.
     * 
     * @param entityname nombre de la entidad de sustitución
     * @param value valor del sustituto
     * @return IItem registro de sustituto para el valor suministrado
     * @throws ISPACException
     */
    public IItem getSubstituteIItem(String entityname, String value) 
    	throws ISPACException;
    
    /**
     * Obtiene los recursos para las etiquetas de una determinada entidad.
     * 
     * @param entityId identificador de la entidad
     * @return Etiquetas de la entidad
     * @throws ISPACException si ocurre algún error
     */
    public EntityResources getEntityResources(int entityId) throws ISPACException;
    
    /**
     * Obtiene los recursos para las etiquetas de una determinada entidad en el idioma suministrado.
     * 
     * @param entityId identificador de la entidad
     * @param language idioma
     * @return IItemCollection colección con las etiquetas de la entidad
     * @throws ISPACException
     */
    public IItemCollection getEntityResources(int entityId, String language) 
    	throws ISPACException;
    
    /**
     * Obtiene los recursos para las etiquetas de una determinada entidad en el idioma suministrado.
     * 
     * @param entityId identificador de la entidad
     * @param keys claves de los recursos
     * @param language idioma
     * @return IItemCollection colección con las etiquetas de la entidad
     * @throws ISPACException
     */
    public IItemCollection getEntityResources(int entityId, String keys, String language) 
    	throws ISPACException;
    
    /**
     * Obtiene el recurso de una determinada entidad en el idioma suministrado.
     * 
     * @param entityId identificador de la entidad
     * @param key clave del recurso
     * @param language idioma
     * @return IItem EntityResourceDAO encontrado
     * @throws ISPACException
     */
	public IItem getEntityResource(int entityId, String key, String language) throws ISPACException;
    
    /**
     * Obtiene el recurso para la etiqueta de un campo de una determinada entidad en el idioma suministrado.
     * 
     * @param entityId identificador de la entidad
     * @param language idioma
     * @param key clave del campo de la entidad
     * @return String etiqueta para el campo de la entidad
     * @throws ISPACException
     */
    public String getEntityResourceValue(int entityId, String language, String key) 
    	throws ISPACException;
    
    /**
     * Obtiene los recursos no asociados a los campos de una determinada entidad en el idioma suministrado.
     * 
     * @param entityId identificador de la entidad
     * @param fieldKeys claves de los campos de la entidad
     * @param language idioma
     * @return IItemCollection colección con los recursos de la entidad
     * @throws ISPACException
     */ 
    public IItemCollection getEntityOtherResources(int entityId, String fieldKeys, String language)
    	throws ISPACException;
    
    /**
     * Obtiene las claves de los recursos no asociados a los campos de una determinada entidad.
     * 
     * @param entityId identificador de la entidad
     * @param fieldKeys claves de los campos de la entidad
     * @return IItemCollection colección con las claves
     * @throws ISPACException
     */ 
    public IItemCollection getEntityOtherResourceKeys(int entityId, String fieldKeys)
    	throws ISPACException;
    
    /**
     * Comprueba si para una entidad existe un recurso con la clave proporcionada
     * 
     * @param entityId identificador de la entidad
     * @param key clave
     * @return Cierto si el recurso existe, en caso contrario, falso.
     * @throws ISPACException
     */
    public boolean isEntityResourceKey(int entityId, String key)
    	throws ISPACException;
    
    /**
     * Obtiene los recursos para las etiquetas de varias entidades.
     * 
     * @param entitiesNames entidades de las que se van a obtener los recursos
     * @return Map etiquetas para los nombres de entidad
     * @throws ISPACException
     */
	public Map getEntitiesResourcesMap(String entitiesNames[]) 
		throws ISPACException;
    
	/**
	 * Obtiene la definición extendida para varias entidades
	 * con el nombre de la entidad obtenido a partir de los recursos.
	 *  
	 * @param entitiesCollection colección de entidades
	 * @return lista de entidades con campos extra: 
	 * 		   ETIQUETA: Campo internacionalizado con el nombre de la etiqueta, 
	 * 		   la propiedad utilizada para obtener la etiqueta es 'NOMBRE'
	 */
	public List getEntitiesExtendedItemBean(IItemCollection entitiesCollection)
		throws ISPACException;
	
	/**
	 * Obtiene la definición extendida para varias entidades
	 * con un campo de la entidad obtenido a partir de los recursos.
	 * 
	 * @param strNameProperty campo de la entidad a obtener de los recursos
	 * @param entitiesCollection colección de entidades
	 * @return entidades con campos extra: 
	 * 		   ETIQUETA: Campo internacionalizado con el nombre de la etiqueta, 
	 * 		   la propiedad utilizada para obtener la etiqueta se pasa por parametro
	 * @throws ISPACException
	 */
	public List getEntitiesExtendedItemBean(String keyNameProperty, IItemCollection entitiesCollection)
		throws ISPACException;
	
    /**
     * Clona el registro de una entidad asignando un nuevo número de expediente y
     * copiando los datos de los campos suministrados en el nuevo registro de entidad.
     * 
     * @param entityId identificador de la entidad a clonar
     * @param keyId identificador del registro a clonar
     * @param newNumExp número de expediente a establecer en la entidad clonada
     * @param idFieldsToClone identificadores de los campos de la definición de la entidad a clonar
     * @return cierto si la entidad se ha clonado correctamente
     * @throws ISPACException
     */
    public boolean cloneRegEntity(int entityId, int keyId, String newNumExp, String[] idFieldsToClone) 
    	throws ISPACException;
    
    /**
     * Copia el registro de una entidad en otro registro suministrado en función de una
     * lista de campos a excluir de copiar y los identificadores de los campos a copiar.
     * 
     * @param entityId identificador de la entidad a copiar
     * @param keyId identificador del registro a clonar
     * @param item registro en el que se copian los datos
     * @param excludedList lista con los nombres de los campos que no se copian
     * @param idFieldsToCopy identificadores de los campos de la definición de la entidad a copiar
     * @throws ISPACException
     */
	public void copyRegEntityData(int entityId, int keyId, IItem item, List excludedList, String[] idFieldsToCopy) 
		throws ISPACException;
	
	/**
	 * Copia los datos de un registro en otro registro en función de una
	 * lista de campos a excluir de copiar y una lista de campos a copiar.
	 * 
	 * @param itemSource registro fuente de los datos
	 * @param item registro en el que se copian los datos
	 * @param excludedList lista con los nombres de los campos que no se copian
	 * @param copyList lista con los nombres de los campos a copiar
	 * @throws ISPACException
	 */
	public void copyRegEntityData(IItem itemSource, IItem item, List excludedList, List copyList) 
		throws ISPACException;

	/**
	 * Clona los expedientes relacionados de un expediente
	 * en otro nuevo número de expediente suministrado.
	 * 
	 * @param numexp número de expediente del que se toman sus expedientes relacionados (padres e hijos en la relación)
	 * @param newNumExp nuevo número de expediente para el que se establecen los expedientes relacionados
	 * @throws ISPACException
	 */
	public void cloneRelatedExpedient(String numexp, String newNumExp) 
		throws ISPACException;
	
	
	/**
	 * Obtiene el formulario por defecto para la entidad
	 * @param entityId
	 * @return
	 * @throws ISPACException
	 */
	public Object getFormDefault(int entityId) throws ISPACException;
	
	/**
	 * 
	 * @param numexp: Expediente
	 * @param idPhasePcd: Fase dentro del procedimiento en la que esta el trámite
	 * @param idTaskPcd: Trámite dentro del procedimiento del que queremos obtener la informacion
	 * @return
	 * @throws ISPACException
	 */
    public IItemCollection getTasksExpInPhase(String numexp, int idPhasePcd, int idTaskPcd) throws ISPACException;

    /**
     * Comprueba si un trámite no se puede borrar
     * 
     * @param taskId Identificador del trámite
     * @return
     * @throws ISPACException
     */
	public boolean undeleteTask(int taskId) throws ISPACException;
	
	/**
	 * Elimina un documento a partir del identificador del documento.
	 * Esto conlleva eliminar el registro con los datos del documento,
	 * los documento físicos asociados (fichero original y fichero firmado)
	 * y cualquier referencia al documento en un proceso de firma.
	 * 
	 * @param documentId Identificador del documento
	 * @throws ISPACException
	 */
	public void deleteDocument(int documentId) throws ISPACException;
	
	/**
	 * Elimina el documento.
	 * Esto conlleva eliminar el registro con los datos del documento,
	 * los documento físicos asociados (fichero original y fichero firmado)
	 * y cualquier referencia al documento en un proceso de firma.
	 * 
	 * @param document Documento a borrar
	 * @throws ISPACException
	 */
	public void deleteDocument(IItem document) throws ISPACException;
	
	/**
	 * Elimina todos los documentos (incluso los físicos) para un expediente
	 * @param numExp : Expediente del que se quieren eliminar todos los documentos
	 * @throws ISPACException
	 */
	
	public void deleteAllDocumentsOfNumExp(String numExp)throws ISPACException;

	/**
	 * Desasocia el documento al registro de la entidad.
	 * 
	 * @param document Documento a desasociar
	 * @param deleteDataDocument Indicador para borrar el registro de SPAC_DT_DOCUMENTOS
	 * @throws ISPACException
	 */
	public void deleteDocumentFromRegEntity(IItem document, boolean deleteDataDocument) throws ISPACException;
	
	/**
	 * Elimina un trámite a partir del identificador del trámite.
	 * Esto conlleva eliminar toda la información del trámite en el procedimiento,
	 * incluyento los documentos generados.
	 * 
	 * @param documentId Identificador del trámite
	 * @throws ISPACException
	 */
	public void deleteTask(int taskId) throws ISPACException;
	
	 /**
     * Modifica la ordenacion de los valores de la tabla de validacion atendiendo al tipo de ordenacion que se realiza
     * @param entityId: Identificador de la tabla de validación sobre la que se realizará la ordenación
     * @param tipoOrdenacion: Por valor o por sustituto
     * @return 
     * @throws ISPACException
     */
    
    public void orderValuesTblValidation(int entityId, String tipoOrdenacion) throws ISPACException;
    
    /**
     * 
     * @param entityName Nombre de la entidad
     * @param query Filtro para la consulta
     * @param order por lo que quiera ordenar el usuario, en caso de que se vaya a usuar la clausula order by
     * @return Objeto con la lista resultante de la búsqueda , el num máx de registros que se pueden obtener 
     * en la búsqueda y el núm total de registros que satisfacen la búsqueda
     * @throws ISPACException
     */
    
    public SearchResultVO getLimitedQueryEntities(String entityName, String query, String order) throws ISPACException;
	
}