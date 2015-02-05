package ieci.tdw.ispac.ispacweb.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.util.MessageResources;

/**
 * Proporciona toda la información del esquema del expediente a partir del
 * estado de tramitación. Dicha información incluye el resumen de los
 * registros de las entidades, las apliaciones a utilizar para mostrarlas,
 * los trámites realizados y los hitos obtenidos hasta el momento en el expediente.
 */
public interface IScheme
{

    /**
     * Devuelve el esquema resumido de entidades correspondientes al estado
     * de tramitaci&oacute;n. Muestra los registros de la entidad de documentos
     * según el comportamiento estandar de ISPAC.
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @return  Mapa de ids de entidad (String) a los objetos IItemCollection
     * @throws ISPACException
     */
    public Map getSchemeEntities(IState iState) throws ISPACException;
    
    /**
     * Establece el esquema resumido de entidades correspondientes al estado
     * de tramitaci&oacute;n.
     * 
     * @return  Mapa con el esquema resumido de entidades
     */
    public void setSchemeEntities(Map mscheme);

    /**
     * Devuelve el cat&aacute;logo de entidades correspondiente al estado
     * de tramitaci&oacute;n.
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @return  Mapa de ids de entidad (String) a los objetos IItem
     * @throws ISPACException
     */
    public IItemCollection getCatalogEntityScheme(IState iState) throws ISPACException;

    /**
     * Devuelve el resumen de los registros correspondientes al estado de tramitaci&oacute;n.
     * Muestra los registros de la entidad según el comportamiento estandar de ISPAC
     *
     * @param  iState  el estado actual de tramitación
     * @param  entityId  identificador de entidad
     * @return  Colecci&oacute;n
     * @throws ISPACException
     */
    public IItemCollection getEntitySchemeFilter(IState iState, int entityId) throws ISPACException;
    
    /**
     * Devuelve el resumen de los registros correspondientes al estado de tramitaci&oacute;n.
     * Muestra los registros de la entidad según el comportamiento estandar de ISPAC
     *
     * @param  iState  el estado actual de tramitación
     * @param  entityDef definición de la entidad
     * @return  Colecci&oacute;n
     * @throws ISPACException
     */
    public IItemCollection getEntitySchemeFilter(IState iState, IEntityDef entitydef) throws ISPACException;

    /**
     * Devuelve el resumen de los registros correspondientes al estado de tramitaci&oacute;n.
     * Adem&aacute;s a&#241;ade al resumen de la entidad las propiedades especificadas en extracolumns[] y los filtra
     * mediante la expresi&oacute;n SQL query.
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @param  entityId  identificador de entidad
     * @return  Colecci&oacute;n
     * @throws ISPACException
     */
    public IItemCollection getEntityScheme(IState iState, int entityId, String query, Property[] extracolumns)
            throws ISPACException;

    /**
     * Se recuperan los registros de la entidad trámites
     * asociados con el expediente y actualizando el campo
     * estado de estos registros dependiendo de si es un tramite
     * abierto propio o abierto ajeno
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @return  Colecci&oacute;n de entidades de tr&aacute;mite
     * @throws ISPACException
     */
    public IItemCollection getTasks(IState iState) throws ISPACException;

   
    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. Además la crea para el primer registro de la primera entidad definida
     * en el esquema.
     *
     * @param state estado actual
     * @param path ruta de la aplicación
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp
     * @throws ISPACException
     */
    public EntityApp getDefaultEntityApp(IState state, String path, boolean noDefault) throws ISPACException;
    
    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. Además la crea para el primer registro de la primera entidad definida
     * en el esquema.
     *
     * @param state estado actual
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp
     * @throws ISPACException
     */
    public EntityApp getDefaultEntityApp(IState state, int appId, String path, boolean noDefault) throws ISPACException;

    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. La entidad y registro son también los indicados en el estado actual.
     *
     * @param state estado actual
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp el objeto aplicación solicitado
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state, String path, int urlKey, boolean noDefault) throws ISPACException;

    /**
     * Obtiene la aplicación de tramitación para la entidad y registro indicados buscando en el catálogo dependiendo
     * del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp el objeto aplicación solicitado
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state, int entityId, int entityRegId, String path, int urlKey, boolean noDefault) throws ISPACException;

    /**
     * Obtiene la aplicación de tramitación para la entidad y registro indicados. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp el objeto aplicación solicitado
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state, int entityId, int entityRegId, int appId, String path, int urlKey, boolean noDefault) throws ISPACException;
    
    /**
     * Clona las entidades (principal y secundarias) de la aplicación de tramitación
     * para la entidad y registro indicados.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param newExpedient nuevo expediente a asignar a las entidades clonadas
     * @param entityFields campos de las entidades que se clonan
     * @return Map identificadores de catálogo de las entidades secundarias que se han clonado
     * @throws ISPACException
     */
    public Map cloneEntityApp(IState state, int entityId, int entityRegId, IItem newExpedient, Map entityFields) throws ISPACException;

    /**
     * Clona las entidades (principal y secundarias) de la aplicación de tramitación
     * para la entidad y registro indicados.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param newExpedient nuevo expediente a asignar a las entidades clonadas
     * @param entityFields campos de las entidades que se clonan
     * @param noCloneSecondaryCtEntityIds identificadores de las entidades secundarias que no se van a clonar
     * @return Map identificadores de catálogo de las entidades secundarias que se han clonado
     * @throws ISPACException
     */
    public Map cloneEntityApp(IState state, int entityId, int entityRegId, IItem newExpedient, Map entityFields, Map noCloneSecondaryCtEntityIds) throws ISPACException;

    /**
     * Crea un registro para la entidad indicada y obtiene la aplicación de tramitación correspondiente. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param path ruta de la aplicación
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    //public EntityApp newEntityApp(IState state, String path) throws ISPACException;

    /**
     * Crea un registro para la entidad indicada y obtiene la aplicación de tramitación correspondiente. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param path ruta de la aplicación
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    //public EntityApp newEntityApp(IState state, int entityId, String path) throws ISPACException;

    /**
     * Crea un registro para la entidad indicada y obtiene la aplicación de tramitación correspondiente. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    //public EntityApp newEntityApp(IState state, int entityId, int appId, String path) throws ISPACException;

    /**
     * Obtiene la lista de beans con los hitos alcanzados en el expediente.
     * Añade a las propiedades propias de los hitos (ver SPAC_HITOS) las siguientes :
     * <b>AUTOR_NOMBRE</b>,<b>FASE_NOMBRE</b>,<b>TRAMITE_NOMBRE</b>,<b>HITO_NOMBRE</b>
     *
     * @param state estado actual
     * @return  lista de ItemBeans
     * @throws ISPACException
     */
    public List getMilestones(IState state) throws ISPACException;

    /**
     * Obtiene una colección con la definición de la cabecera de contexto.
     * @param state estado actual
     * @param resources Fichero de recursos
     * @return Miga de pan
     * @throws ISPACException
     */
    public IItemCollection getContextHeader(IState state, Locale locale, MessageResources resources) throws ISPACException;
    
    /**
     * Obtiene una colección con la definición de la cabecera de contexto, teniendo en cuenta la fase que se le indica en lugar
     * de la que recibe en el contexto
     * @param state estado actual
     * @param resources Fichero de recursos
     * @param stagePcdId Fase que prevalece sobre la del estado actual
     * @return Miga de pan
     * @throws ISPACException
     */
    public IItemCollection getContextHeader(IState state, Locale locale, MessageResources resources ,String stagePcdId) throws ISPACException;
    
}