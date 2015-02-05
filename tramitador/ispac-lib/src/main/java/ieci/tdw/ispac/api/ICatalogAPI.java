package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityIndex;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;

import java.util.List;
import java.util.Map;

public interface ICatalogAPI
{
    public int ENTITY_CT_ENTITY=1;
    public int ENTITY_CT_APP=2;
    public int ENTITY_CT_RULE=3;
    public int ENTITY_CT_SEARCHFORM=4;
    public int ENTITY_CT_STAGE=5;
    public int ENTITY_CT_TASK=6;
    public int ENTITY_CT_TYPEDOC=7;
    public int ENTITY_CT_PROCEDURE=8;
    public int ENTITY_CT_TEMPLATE=9;

	public int ENTITY_CT_STAGETASK=10;
	public int ENTITY_CT_TASKTYPEDOC=11;
	public int ENTITY_CT_PCDORG=12;
    public int ENTITY_CT_SEARCHFORM_ORG=13;

	public int ENTITY_P_ENTITY=20;
	public int ENTITY_P_STAGE=21;
	public int ENTITY_P_PROCEDURE=22;
	public int ENTITY_P_TASK=23;
	public int ENTITY_P_FRMSTAGE=24;
	public int ENTITY_P_FRMTASK=25;
	public int ENTITY_P_FSTD=26;

	public int ENTITY_DT_DOCUMENTOS=27;
    //public int ENTITY_P_PLANTDOC=28;
    public int ENTITY_P_PLANTPROC=29;
    public int ENTITY_P_FLUJOS=30;
    public int ENTITY_P_SINCNODE=31;
    public int ENTITY_P_NODOS=32;

    public int ENTITY_P_SUBPROCEDURE=33;
    public int ENTITY_P_ACTIVITIES=34;

    public int ENTITY_PUB_ACTIONS=40;
    public int ENTITY_PUB_APPLICATIONS=41;
    public int ENTITY_PUB_CONDITIONS=42;
    public int ENTITY_PUB_ERRORS=43;
    public int ENTITY_PUB_MILESTONES=44;
    public int ENTITY_PUB_RULES=45;

    public int ENTITY_SPAC_VLDTBL_SIST_PRODUCTORES=46;
    public int ENTITY_SPAC_VLDTBL_ACTS_FUNCS=47;
    public int ENTITY_SPAC_VLDTBL_MATS_COMP=48;
    public int ENTITY_SPAC_VLDTBL_SIT_TLM=49;

	public int ENTITY_SIGNPROCESS_HEADER=50;
	public int ENTITY_SIGNPROCESS_DETAIL=51;

    public int ENTITY_SPAC_TBL_002=52;
    public int ENTITY_SPAC_VLDTBL_FORMA_INIC=53;
    public int ENTITY_SPAC_TBL_001=54;
    public int ENTITY_SPAC_VLDTBL_EFEC_SLNCIO=55;
    public int ENTITY_SPAC_TBL_009=56;
    public int ENTITY_SPAC_VLDTBL_RECURSOS=57;
    public int ENTITY_SPAC_CALENDARIOS=58;
    public int ENTITY_CT_INFORMES=61;
    public int ENTITY_CT_SYSTEM_VARS=62;
    public int ENTITY_CT_HELPS=63;
    public int ENTITY_CT_INFORMES_ORG = 14;


    public int ENTITY_SIGNPROCESS=59;
    public int ENTITY_P_EVENTOS=60;

    public static String ID_FIELD_NAME 			= "id";
    public static String NUMEXP_FIELD_NAME 		= "numexp";
    public static String CODIGO_FIELD_NAME 		= ID_FIELD_NAME;
    public static String VALOR_FIELD_NAME 		= "valor";
    public static String ESTADO_FIELD_NAME 		= "vigente";
    public static String ORDEN_FIELD_NAME		= "orden";
    public static String SUSTITUTO_FIELD_NAME 	= "sustituto";

    public static String ID_FIELD_ENTIDAD_PADRE = "id_padre";
    public static String ID_FIELD_ENTIDAD_HIJA = "id_hija";
    public static String HIERARCHICAL_TABLE_NAME = "SPAC_CT_JERARQUIA_";

    /**
	 * Obtiene una entidad sin ningún valor de registro.
	 *
	 * @param entityId identificador de la entidad
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getCTEntity(int ctentityId) throws ISPACException;

	/**
	 * Crea una nuevo registro para la entidad.
	 *
	 * @param entityId identificador de la entidad
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem createCTEntity(int ctentityId) throws ISPACException;
	public IItem createCTEntity(DbCnt cnt, int ctentityId) throws ISPACException;



	/**
	 * Crea una nuevo registro para un campo de la entidad en SPAC_CT_ENTIDADES_RESOURCES.
	 *
	 * @return IItem
	 * @throws ISPACException
    */
    public IItem createCTEntityResource() throws ISPACException;

	/**
	 * Obtiene el registro de una entidad
	 *
	 * @param entityId identificador de la entidad
	 * @param entityRegId identificador del registro
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getCTEntity(int ctentityId, int ctentityRegId)
			throws ISPACException;

	/**
	 * Obtiene los registros de una entidad
	 *
	 * @param entityId identificador de la entidad
	 * @param ctentityRegIds identificadores de los registros
	 * @return IItemCollection colección de objetos
	 * @throws ISPACException
	 */
	public IItemCollection getCTEntityByIds(int ctentityId, Map ctentityRegIds)
			throws ISPACException;

	/**
	 * Obtiene una colección de entidades resultado de una consulta.
	 *
	 * @param entityId identificador de la entidad
	 * @param query búsqueda a realizar sobre la entidad
	 * @return IItemCollection colección de objetos
	 * @throws ISPACException
	 */
	public IItemCollection queryCTEntities(int ctentityId, String query)
		throws ISPACException;

	public IItemCollection queryCTEntitiesForUpdate(int ctentityId, String query)
		throws ISPACException;

	public void setCTEntitiesForUpdate(int ctentityId, String query)
		throws ISPACException;

	/**
	 * Obtiene el número de entidades que cumplen la consulta
	 *
	 * @param entityId identificador de la entidad
	 * @param query búsqueda a realizar sobre la entidad
	 * @return int el número de objetos EntityDAO existente
	 * @throws ISPACException
	 */
	public int countCTEntities(int entityId, String query)
		throws ISPACException;


    /**
	 * <p>Permite especificar una consulta sobre una o m&aacute;s entidades del catálogo o tablas.
	 * Para ello es preciso definir un mapa con el prefijo que se quiere asignar a
	 * las tablas que intervien en la consulta. El resultado es una colecci&oacute;n de IItems
	 * compuestos por el cada una de las filas resultado del join con las propiedades
	 * calificadas seg&uacute;n el prefijo asociado con anterioridad</p>
	 * <br>
	 * <p>
	 * Ejemplo: Para realizar el siguiente join con el api de entidades de catálogo.<br><br>
	 * <tt>
	 * SELECT * FROM SPAC_PROCESOS PROCESO, SPAC_FASES FASE WHERE PROCESO.ID = FASE.ID_EXP
	 * </tt><br><br>
	 * Se utilizaría el siguiente código.<br>
	 * <pre>
	 * tableentitymap.put("PROCESO","SPAC_PROCESOS");
	 * tableentitymap.put("FASE","SPAC_FASES");
	 *
	 * IItemCollection consulta;
	 * consulta=entityAPI.queryCTEntities(tableentitymap," WHERE PROCESO.ID = FASE.ID_EXP ");
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
	 * Es posible especificar para el join tanto tablas como entidades del catálogo. Basta con asignar al
	 * mapa de prefijos el nombre de la tabla o un objeto Integer o Long con el identificador
	 * de la entidad.
	 * </p> <br><br>
	 * <pre>
	 * tableentitymap.put("PROCESO","SPAC_PROCESOS");
	 * tableentitymap.put("APP",new Integer(ICatalogAPI.ENTITY_CT_APP));
	 * tableentitymap.put("DATOS",new Integer(23));
	 * </pre>
	 *
	 * @param tableentitymap Mapa con la información necesaria de las tablas o entidades
	 * que intervienen en el join.
	 * @param query búsqueda a realizar sobre las entidades especificadas mediante SQL
	 * @return  Colección de IItems con el resultado de la consulta.
	 * @throws ISPACException
	 */
	public IItemCollection queryCTEntities(Map tableentitymap, String query)
			throws ISPACException;

	/**
     * Obtiene la aplicación indicada cargada con los datos del registro
     * de entidad de catálogo especificado.
     *
     * @param int identificador de la aplicación
     * @param int identificador de la entidad
     * @param int identificador del registro de la entidad
     * @param path ruta de la aplicación
     * @return EntityApp cargada con los datos del registro especificado
     * @throws ISPACException
     */
	public EntityApp getCTEntityApp(int appId, int ctentityId, int ctentityRegId, String path)
		throws ISPACException;

	/**
     * Obtiene la aplicación indicada preparada para recibir los datos para un
     * registro de entidad de catálogo.
     *
     * @param int identificador de la aplicación
     * @param int identificador de la entidad
     * @param path ruta de la aplicación
     * @return EntityApp inicializada para acceder a los datos de la entidad especificada
     * @throws ISPACException
     */
    public EntityApp getCTEntityApp(int appId, int ctentityId, String path)
 		throws ISPACException;

    /**
     * Obtiene la aplicación indicada. Además crea un registro
     * para la entidad especificada y carga la aplicación con sus datos.
     *
     * @param int identificador de la aplicación
     * @param int identificador de la entidad
     * @param path ruta de la aplicación
     * @return EntityApp cargada con los datos del registro recien creado
     * @throws ISPACException
     */
    public EntityApp newCTEntityApp(int appId, int ctentityId, String path)
		throws ISPACException;

	/**
     * Obtiene la aplicación asociada a la entidad de catálogo en su definición.
     * La aplicación ya se devuelve cargada con los datos del registro especificado.
     *
     * @param int identificador de la entidad
     * @param int identificador del registro de la entidad
     * @param path ruta de la aplicación
     * @return EntityApp cargada con los datos del registro especificado
     * @throws ISPACException
     */
     public EntityApp getCTDefaultEntityApp(int ctentityId, int ctentityRegId, String path)
        throws ISPACException;

 	/**
      * Obtiene la aplicación asociada a la entidad de catálogo en su definición.
      * La aplicación no se devuelve cargada pero si inicializada.
      *
      * @param int identificador de la entidad
      * @param path ruta de la aplicación
      * @return EntityApp inicializada para acceder a los datos de la entidad especificada
      * @throws ISPACException
      */
    public EntityApp getCTDefaultEntityApp(int ctentityId, String path)
		throws ISPACException;

    /**
     * Obtiene la aplicación asociada a la entidad de catálogo en su definición.
     * Además crea un registro para la entidad especificada y carga la aplicación
     * con sus datos.
     *
     * @param int identificador de la entidad
     * @param path ruta de la aplicación
     * @return EntityApp cargada con los datos del registro recien creado
     * @throws ISPACException
     */
	public EntityApp newCTDefaultEntityApp(int ctentityId, String path)
		throws ISPACException;

	/**
     * Obtiene la coleccion de los eventos y las reglas relacionados con un objeto
     * concreto, definido por un tipo numerico del objeto y un identificador, tambien
     * numerico.
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @return IItemCollection cargada con los datos de los eventos y las reglas relacionados
     * 	con ese objeto concreto
     * @throws ISPACException
     */
	public IItemCollection getPEvents(int pobjectType, int pobjectId)
		throws ISPACException;

	/**
     * Obtiene la coleccion de los datos de las reglas relacionadas con un objeto concreto,
     * definido por un tipo numerico del objeto y un identificador, tambien
     * numerico, y relacionadas con un codigo de evento.
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @return IItemCollection cargada con los datos de las reglas relacionados
     * 	con ese objeto y evento concretos
     * @throws ISPACException
     */
	public IItemCollection getPRulesEvent(int pobjectType, int pobjectId, int peventCod)
		throws ISPACException;

	/**
     * Obtiene los datos de la reglas relacionada con un objeto concreto,
     * definido por un tipo numerico del objeto y un identificador, tambien
     * numerico, y relacionada con un codigo de evento.
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @param int identificador de la regla
     * @param int orden de ejecución
     * @return IItemCollection cargada con los datos de las reglas relacionados
     * 	con ese objeto y evento concretos
     * @throws ISPACException
     */
	public IItem getPRuleEvent(int pobjectType, int pobjectId, int peventCod, int ruleId, int order)
		throws ISPACException;

	/**
     * Elimina un evento para un tipo e identificador de objeto concreto, y en cascada todas las reglas
     * asociadas al evento.
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @throws ISPACException
     */
	public void delPEvent(int pobjectType, int pobjectId, int peventCod)
		throws ISPACException;

	/**
     * Elimina una regla para un codigo de vento, de un tipo e identificador de objeto concreto
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @param int identificador de la regla
     * @throws ISPACException
     */
	public void delPRuleEvent(int pobjectType, int pobjectId, int peventCod, int pruleId,int order)
		throws ISPACException;

	/**
     * Inserta una nueva regla a un evento de un tipo e identificador de objeto concretos
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @param int identificador de la regla
     * @throws ISPACException
     */
	public void addPRuleEvent(int pobjectType, int pobjectId, int peventCod, int pruleId)
	throws ISPACException;

	/**
     * Inserta una nueva condicion a un evento de un tipo e identificador de objeto concretos
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @param String condición
     * @throws ISPACException
     */
	public void addPConditionEvent(int pobjectType, int pobjectId, int peventCod, String condition)
		throws ISPACException;

	/**
     * Incrementa el orden de una regla a el siguinete orden existente y decrementa al sustituido
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @param int identificador de la regla
     * @param int orden de la regla
     * @throws ISPACException
     */
	public void incOrderPEvents(int pobjectType, int pobjectId, int peventCod, int pruleId, int orderRule)
	throws ISPACException;

	/**
     * Decrementa el orden de una regla a el siguinete orden existente e incrementa al sustituido
     *
     * @param int tipo de objeto
     * @param int identificador del objecto
     * @param int codigo del evento
     * @param int identificador de la regla
     * @param int orden de la regla
     * @throws ISPACException
     */
	public void decOrderPEvents(int pobjectType, int pobjectId, int peventCod, int pruleId, int orderRule)
	throws ISPACException;

	/**
	 * Establece la definicion del plazo de un objeto
	 * @param Id
	 * @param objType: ICatalogAPI.ENTITY_P_PROCEDURE, ICatalogAPI.ENTITY_P_SUBPROCEDURE
	 * ICatalogAPI.ENTITY_P_STAGE, ICatalogAPI.ENTITY_P_ACTIVITIES, ICatalogAPI.ENTITY_P_TASK
	 * @param idObj
	 * @param sXML
	 * @return
	 * @throws ISPACException
	 */
	public IItem setDeadLine(int Id, int objType, int idObj, String sXML)
	throws ISPACException;

	/**
	 * Obtiene un registro de la tabla de deficion de plazos
	 * @param objType
	 * @param objType: ICatalogAPI.ENTITY_P_PROCEDURE, ICatalogAPI.ENTITY_P_SUBPROCEDURE
	 * ICatalogAPI.ENTITY_P_STAGE, ICatalogAPI.ENTITY_P_ACTIVITIES, ICatalogAPI.ENTITY_P_TASK
	 * @return
	 * @throws ISPACException
	 */
	public IItem getDeadLine(int objType, int idObj)
	throws ISPACException;

	/**
	 * Elimina la definicion del plazo de un objeto
	 * @param objType: ICatalogAPI.ENTITY_P_PROCEDURE, ICatalogAPI.ENTITY_P_SUBPROCEDURE
	 * ICatalogAPI.ENTITY_P_STAGE, ICatalogAPI.ENTITY_P_ACTIVITIES, ICatalogAPI.ENTITY_P_TASK
	 * @param idObj
	 * @throws ISPACException
	 */
	public void dropDeadLine(int objType, int idObj)
	throws ISPACException;

	/**
	 * Obtiene los informes del catálogo.
    *
	 * @return Lista de informes del catálogo.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTReports() throws ISPACException;

	/**
	 * Obtiene los informes del catálogo a partir del nombre.
    *
	 * @param pattern
	 *            Patrón del nombre del informe.
	 * @return Lista de informes del catálogo.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTReports(String pattern)
			throws ISPACException;

	/**
	 * Obtiene la información del informe en el catálogo.
    *
	 * @param id
	 *            Identificador del informe.
	 * @return Información del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem getCTReport(int id) throws ISPACException;

	/**
	 * Obtiene la colección de circuitos de firma asociados a un determinado procedimiento
	 * @param  pcdId
	 * @return IItemCollection
	 * @throws ISPACException
	 */
	public IItemCollection getCtosFirmasProcedure(int pcdId)
	throws ISPACException;

	/**
	 * Añade un circuito de firmas al procedimiento seleccionado
	 * @param pcdId
	 * @param ctofirmaId
	 * @throws ISPACException
	 */
	public void addCtoFirmas(int pcdId, int ctofirmaId)
	throws ISPACException;

	/**
	 * Borra el circuito de firmas asociado al procedimiento que se le pasa como parametro
	 * @param pcdId
	 * @param ctofirmaId
	 * @throws ISPACException
	 */
	public void dropCtoFirmas(int pcdId, int ctofirmaId)
	throws ISPACException;

	/**
	 * Resta una unidad al orden en el que se encuentra la entidad del procedimiento. Ejemplo pasa de posicion 2 a 1
	 * @param procedureId
	 * @param pentityId
	 * @throws ISPACException
	 */
	public void incOrderPEntity(int procedureId, int pentityId)
	throws ISPACException;

	/**
    * Suma una unidad al orden en el que se encuentra la entidad del procedimiento. Ejemplo pasa la entidad de orden 1
	 * a la posicion 2 y la 2 a la 1
	 * @param procedureId
	 * @param pentityId
	 * @throws ISPACException
	 */
	public void decOrderPEntity(int procedureId, int pentityId)
	throws ISPACException;

	/**
    *
    * Creacion de una entidad: Creacion de la tabla que almacena las filas de la entidad
	 * y registro de la tabla como entidad del tramitador
	 * @param entityType
	 * @param logicalName
	 * @param physicalName
	 * @param description
	 * @param entityDefinition
	 * @param valueColSize
	 * @param subsColSize
	 * @return
	 * @throws ISPACException
	 */
	public int createEntity(EntityType entityType, String logicalName, String physicalName, String description,
							EntityDef entityDefinition,
							int valueColSize, int subsColSize, boolean tblExist) throws ISPACException;
	/**
	 * Modificacion una entidad del catalago
	 * @param entityId
	 * @param physicalName
	 * @param newLogicalName
	 * @param newDescription
	 * @param newEntityDefinition
	 * @param language
	 * @throws ISPACException
	 */
	public void modifyEntity(int entityId, String physicalName, String newLogicalName, String newDescription,
				 EntityDef newEntityDefinition, String language) throws ISPACException;

	/**
	 * Añadir un campo a la entidad.
    *
	 * @param entityId Identificador de la entidad.
	 * @param newField Campo nuevo a añadir.
	 * @param createResources Indicador para crear los recursos asociados al campo.
	 * @throws ISPACException
	 * @return Identificador del nuevo campo añadido.
	 */
	public int addField(int entityId, EntityField newField, boolean createResources)
	throws ISPACException;

	/**
	 * Modificar un campo de la entidad.
    *
	 * @param entityId Identificador de la entidad.
	 * @param field Campo a modificar.
	 * @throws ISPACException
	 */
	public void saveField(int entityId, EntityField field)
	throws ISPACException;

	/**
	 * Elimina un campo a la entidad que se está creando
	 * @param fieldToRemove
	 * @param entityDefinition
	 * @return
	 * @throws ISPACException
	 */
	public EntityDef dropFieldTemporal(EntityField fieldToRemove, EntityDef entityDefinition)throws ISPACException;

	/**
	 * Eliminar un campo a la entidad
	 * @param entityId
	 * @param fieldToDrop
	 * @return
	 * @throws ISPACException
	 */
	public EntityDef dropField(int entityId, EntityField fieldToDrop) throws ISPACException;

	/**
	 * Eliminacion de la entidad
	 * @param entityId
	 * @throws ISPACException
	 */
	public void deleteEntity(int entityId, boolean deleteTableInBD) throws ISPACException;

	/**
	 * Eliminacion de todos los campos de la entidad con id "entityId", independientemente del lenguage, cuya clave sea "campo".
	 * @param entityId
	 * @throws ISPACException
	 */
	public void deleteResources(int entityId, String campo)throws ISPACException;

	/**
	 * Añadir un indice
	 * @param entityId
	 * @param newIndex
	 * @throws ISPACException
	 */
	public void addIndex(int entityId, EntityIndex newIndex) throws ISPACException;

	/**
    * Eliminar un indice
	 * @param entityId
	 * @param indexToDrop
	 * @return
	 * @throws ISPACException
	 */
	public EntityDef dropIndex(int entityId, EntityIndex indexToDrop) throws ISPACException;

	/**
    * Añadir una validacion
	 * @param entityId
	 * @param newValidation
	 * @throws ISPACException
	 */
	public void addValidation(int entityId, EntityValidation newValidation) throws ISPACException;

	/**
	 * Eliminar una validacion
	 * @param entityId
	 * @param indexToDrop
	 * @return La definicion de la entidad sin la validacion
	 * @throws ISPACException
	 */
	public EntityDef dropValidation(int entityId, EntityValidation indexToDrop) throws ISPACException;

    /**
     * Actualiza la definicion de un indice
     * @param entityId
     * @param indexToUpdate
     * @throws ISPACException
     */
	public void updateIndex(int entityId, EntityIndex indexToUpdate) throws ISPACException;

	/**
	 * Regenera el formulario JSP de una entidad.
    *
	 * @param entityId Identificador de la entidad
	 * @param definition Definición de la entidad
	 * @return El formulario JSP
	 * @throws ISPACException
	 */
    public String regenerateEntityForm(int entityId, EntityDef definition)
    throws ISPACException;

	/**
	 * Regenera el JSP de formulario
    *
	 * @param keyId Identificador del formulario
	 * @param entityDef Definición de la entidad a la que pertenece el formulario
	 * @param entityFrmJsp Formulario JSP de la entidad a la que pertenece el formulario
	 * @throws ISPACException
	 */
	public void remakeForm(int keyId, EntityDef entityDef, String entityFrmJsp) throws ISPACException;

	/**
     * Incrementa el orden de un productor al siguiente orden existente y
     * decrementa el sustituido
     *
     * @param id Identificador del productor dentro del procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
	public void incOrderPcdProducer(int id) throws ISPACException;

	/**
     * Decrementa el orden de un productor al anterior orden existente e
     * incrementa el sustituido
     *
     * @param id Identificador del productor dentro del procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
	public void decOrderPcdProducer(int id) throws ISPACException;

	/**
	 * Obtiene las entidades asociadas a un procedimiento
    *
	 * @param pcdId
    * @return Devuelve las entidades de un procedimiento
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureEntities(int pcdId) throws ISPACException;

	/**
	 * Obtiene las entidades asociadas a un procedimiento junto con sus formularios asociados
    *
	 * @param pcdId
	 * @return Lista de itemBeans con las entidades y formularios asociados a nivel de procedimiento
	 * @throws ISPACException
	 */
	public List getProcedureEntitiesForm(int pcdId) throws ISPACException;

	/**
	 * Obtiene las entidades asociadas a un subproceso junto con sus formularios asociados
    *
	 * @param subPcdId
	 * @return Lista de itemBeans con las entidades y formularios asociados a nivel de subproceso
	 * @throws ISPACException
	 */
	public List getSubprocedureEntitiesForm(int subPcdId) throws ISPACException;

	/**
	 * Obtiene las entidades asociadas a una fase en el procedimiento junto con sus formularios asociados
    *
	 * @param pcdId
	 * @param stageId
	 * @param keyDefine Clave para el mensaje de formulario definido en otro elemento
    * @return Lista de itemBeans con las entidades y formularios asociados a nivel de fase en el procedimiento
	 * @throws ISPACException
	 */
	public List getStageEntities(int pcdId, int stageId, String keyDefine) throws ISPACException;

	/**
	 * Obtiene las entidades asociadas a un trámite en el procedimiento junto con sus formularios asociados
    *
	 * @param pcdId
	 * @param taskId
    * @return Lista de itemBeans con las entidades y formularios asociados a nivel de trámite en el procedimiento
	 * @throws ISPACException
	 */
	public List getTaskEntities(int pcdId, int taskId) throws ISPACException;

	/**
    *
	 * @param entityId
	 * @return Obtiene una entidad de procedimiento
	 * @throws ISPACException
	 */
	public IItem getProcedureEntity(int entityId)
	throws ISPACException;

	/**
	 * @param query filtro para la busqueda de tablas jerárquicas
	 * @return Listado de tablas jerárquicas
	 * @throws ISPACException
	 */
	public IItemCollection getHierarchicalTables(String query)
    throws ISPACException;

	/**
	 * @param hierarchicalId Identificador de la tabla jerárquica
	 * @param query Consulta a aplicar
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getHierarchicalTableValues(int hierarchicalId,String query)
	throws ISPACException;


	/**
	 * @param tableName Nombre de la tabla jerárquica
	 * @param values Identificadores de los conjuntos de valores a eliminar
	 * @return
	 * @throws ISPACException
	 */
	public boolean deleteHierarchicalTableValues(String tableName, String[] values)
	throws ISPACException;

	/**
	 * @param tableName Nombre de la tabla jerárquica
	 * @param parentIds Valores de la tabla de validación que ejerce el rol de ascendiente
	 * @param descendantIds Valores de la tabla de validación que ejerce el rol de descendiente
	 * @throws ISPACException
	 */
	public boolean deleteHierarchicalTableValues(String tableName, List parentIds,List descendantIds)
	throws ISPACException;

	/**
	 * @param tableName Nombre de la tabla jerárquica
	 * @param parentValue Valor de la tabla de validación que ejerce el rol de ascendiente
	 * @param descendantValue Valor de la tabla de validación que ejerce el rol de descendiente
	 * @return
	 * @throws ISPACException
	 */
	public boolean addHierarchicalTableValue(String tableName, int parentValue, int descendantValue)
	throws ISPACException;

	/**
	 * @param tableName Nombre de la tabla jerárquica
	 * @param parentIds Valores de la tabla de validación que ejerce el rol de ascendiente
	 * @param descendantIds Valores de la tabla de validación que ejerce el rol de descendiente
	 * @throws ISPACException
	 */
	public boolean addHierarchicalTableValues(String tableName, List parentIds, List descendantIds)
	throws ISPACException;



    /**
     * @param name Nombre de la tabla jerárquica
     * @param description Descripción
     * @param entidadPadreId identificador de la tabla de validación que tendrá el roj padre en la estructura jerárquica
     * @param entidadHijaId identificador de la tabla de validicón que tendrá el rol hija en la estructura jerárquica
     * @param createTable indica se se crea una nueva tabla para almacenar la relacion o no (usar una vista existente)
     * @throws ISPACException
     */
    public void createHierarchicalTable(String name, String description, int entidadPadreId, int entidadHijaId, boolean createTable)
    throws ISPACException;

    /**
     * Devuelve la lista de valores de la entidad hija relacionado con el código seleccionado en la entidad padre
     * @param hierarchicalId Identificador de la jerarquía
     * @param codeParent Código seleccionado de la entidad con rol 'padre'
     * @return
     */
    public IItemCollection getHierarchicalDescendantValues(int hierarchicalId, String codeParent) throws ISPACException;

    /**
     * Elimina la estructura de una tabla jerárquica
     * @param id Identificador de la estructura de tabla jerárquica a borrar
     * @throws ISPACException
     */
    public void deleteHierarchicalTable(int id, boolean deleteTableBD)
    throws ISPACException;

    /**
     * Obtiene la información de un procedimiento en el catálogo.
     * @param ctPcdId Identificador del procedimiento en el catálogo
     * @return Información del procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTProcedure(int ctPcdId) throws ISPACException;

    /**
     * Obtiene la lista de fases del catálogo.
     * @return Lista de fases del catñalogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTStages() throws ISPACException;

    /**
     * Obtiene la lista de fases del catálogo a partir del nombre.
     * @param name Nombre de la fase.
     * @return Lista de fases del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTStages(String name) throws ISPACException;

    /**
     * Obtiene la lista de trámites de una fase del catálogo.
     * @param ctStageId Identificador de la fase en el catálogo.
     * @return Lista de trámites del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTStageTasks(int ctStageId) throws ISPACException;

    /**
     * Obtiene la información de una fase en el catálogo.
     * @param ctStageId Identificador de la fase en el catálogo
     * @return Información de la fase.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTStage(int ctStageId) throws ISPACException;

    /**
     * Obtiene la información de un trámite en el catálogo.
     * @param ctTaskId Identificador del trámite en el catálogo
     * @return Información del trámite.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTTask(int ctTaskId) throws ISPACException;

    /**
     * Obtiene la información de un trámite en el catálogo
     * a partir del trámite en el procedimiento.
     * @param taskPcdId Identificador del trámite en el procedimiento.
     * @return Información del trámite.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTTaskPCD(int taskPcdId) throws ISPACException;

    /**
     * Obtiene la lista de reglas del catálogo.
     * @return Lista de reglas del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTRules() throws ISPACException;

    /**
     * Obtiene la lista de reglas del catálogo que contengan una cadena en el nombre.
     * @param pattern Cadena que debe estar contenida en el nombre.
     * @return Lista de reglas del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTRules(String pattern) throws ISPACException;

    /**
     * Obtiene las tablas de validacion dadas de alta
     * @return Lista de tablas de validacion
     * @throws ISPACException
     */
    public IItemCollection getValidationTables()throws ISPACException;

    /**
     * Obtiene las tablas de validacion dadas de alta y que cumple con el nombre por el que se filtra
     * @param nameFilter: Nombre de la tabla de validacion en bbdd
     * @return Lista de tablas de validacion que cumplen la condición
     * @throws ISPACException
     */
    public IItemCollection getValidationTablesByName(String nameFilter)throws ISPACException;

    /**
     * Obtiene la lista de responsables para los que se ha asignado el formulario de búsqueda.
     * @param searchFormId Identificador del formulario de búsqueda.
     * @return Lista de responsables asignados al formulario de búsqueda.
     * @throws ISPACException si ocurre algún error.
     */
    public List getSearchFormOrganization(int searchFormId) throws ISPACException;

    /**
     * Obtiene todos los formularios de búsqueda definidos
     * @return
     * @throws ISPACException
     */
    public IItemCollection getSearchForms()throws ISPACException;
    /**
	 * Obtiene las variables de sistema a partir del nombre.
    *
	 * @param pattern
	 *            Patrón del nombre de la variable de sistema
	 * @return Lista de variables del sistema
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTSystemVars(String pattern) throws ISPACException ;

	/**
	 * Obtiene las variables de sistema
    *
	 * @return Lista de variables de sistema
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTSystemVars() throws ISPACException ;


	/**
	 * Obtiene la información de una variable de sistema.
    *
	 * @param id
	 *            Identificador de la variable del sistema
	 * @return Información de la variable de sistema
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem getCTSystemVar(int id) throws ISPACException ;

	/**
    *
	 * @param nameVarSystemCod :Nombre de la variable de sistema que contiene el
	 * código del tipo documental
	 * @return id del tipo documental
	 * @throws ISPACException
	 */

	public String getIdTpDocByCode(String nameVarSystemCod)throws ISPACException;


	/**
	 * Obtiene el número de tipos de documentos que tiene asociados el tramite
	 * @param idTask Tramite
	 * @return
	 * @throws ISPACException
	 */

	public int countTaskTpDoc (String idTask) throws ISPACException ;

	/**
	 * Activa la busqueda documental para el campo nameField de la entidad nameTable
	 * @param cnt
	 * @param nameField
	 * @param nameTable
	 * @throws ISPACException
	 */
	public void activateDocumentarySearch (DbCnt cnt,  String  nameField, String nameTable) throws ISPACException;

	/**
	 * Asocia los informes al formulario de búsqueda
	 * @param idFrm Identificador del formulario
	 * @param idReports Array con los identificadores de los informes
	 * @return
	 */
	public void asociateReportToFrmBusqueda(int idFrm, int [] idReports)throws ISPACException;

	/**
	 * Obtiene los informes de un tipo determinado
	 * @param type Tipo del informe 1-Genérico 2-Específico 3-Global 4-Busqueda
	 * @return
	 */
	public IItemCollection getReportByType(int type) throws ISPACException;

	/**
	 * Actualiza las asociaciones entre el formulario de busqueda y los informes
	 * @param idFrm Identificador del formulario
	 * @param idReports Array con los identificadores de los informes que van a estar asociados con el idFrm
	 * @throws ISPACException
	 */
	public void updateAsociateReportToFrmBusqueda(int idFrm, String [] idReports)throws ISPACException;

	/**
	 * Obtiene los identificadores de los informes que estan asociados al formulario de búsqueda
	 * @param idFrm Identificador del formulario de búsqueda
	 * @return Colección con los informes asociados al formulario de búsqueda
	 * @throws ISPACException
	 */
	public IItemCollection getAsociateReports(int idFrm)throws ISPACException;

	 /**
	 * Obtiene las ayudas a partir del nombre.
    *
	 * @param pattern
	 *            Patrón del nombre de la ayuda
	 * @return Lista de ayudas
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTHelps(String pattern) throws ISPACException ;

	/**
	 * Obtiene las ayudas
    *
	 * @return Lista de ayudas
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTHelps() throws ISPACException ;


	/**
	 * Obtiene la información de una ayuda.
    *
	 * @param id
	 *            Identificador de la ayuda
	 * @return Información de la ayuda
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem getCTHelp(int id) throws ISPACException ;

	/**
	 * Obtiene la inforamción de una ayuda
    *
	 * @param tipoObj Tipo de objeto de la ayuda
	 * @param idObj Identificador de la ayuda
	 * @param idioma Idioma
	 * @return
	 * @throws ISPACException
	 */
	public IItem getCTHelp (String tipoObj, String idObj, String idioma)throws ISPACException;





}