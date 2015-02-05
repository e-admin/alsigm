/*
 * Created on Jan 4, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.SearchResourceListFactory;
import ieci.tdw.ispac.ispaclib.common.constants.InformationMilestonesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.TableDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLStageDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.search.objects.IEntity;
import ieci.tdw.ispac.ispaclib.search.objects.IField;
import ieci.tdw.ispac.ispaclib.search.objects.IPropertyFmt;
import ieci.tdw.ispac.ispaclib.search.objects.impl.Entity;
import ieci.tdw.ispac.ispaclib.search.objects.impl.PropertyFmt;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchDomain;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchExpState;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;


/*
 * Comentarios a la generación de la query de busqueda:
 *
 * La generación de la query se divide en tres partes:
 * 1) Obtener cuales son las columnas que representan
 *    el resultado de la busqueda (SELECT)
 * 2) Decidir cuales son las tablas que intervienes
 * 		en la query (FROM)
 * 3) Establecer las condiciones de la búsqueda, las
 * 		condiciones de mapeo entre tablas y la condición
 * 		de responsabilidad (esta última sólo si se buscan
 * 		sólo expedientes de mi responsabilidad)
 *
 * El resultado de la búsqueda es determinado a través del
 * archivo xml que representa el formulario de búsqueda.
 *
 * Decidir las tablas que forman parte de la query depende
 * de dos factores:
 *
 * 1) Si se realiza búsqueda por algún campo de esa tabla
 * 2) El resultado de la búsqueda contiene algun campo de
 * esa tabla
 *
 * Por tanto, se pueden establecer lo siguiente:
 *
 * 1) La tabla expedientes siempre aparece en el FROM
 * 2) Si no se realiza búsqueda por ningun campo de una
 * entidad y se presenta en el resultado algún campo de esa
 * misma entidad, entonces esa entidad entra dentro del FROM
 * y se hace un outer join entre esa entidad y expedientes.
 * 3) Si no se realiza busqueda por ningun campo de una entidad
 * ni se presentan en el resultado campos de esa entidad, entonces
 * como el logico, la entidad no participa en la query.
 * 4) En el resto de casos, la entidad si participa en la query
 * con un join normal
 *
 * Para obtener los expedientes de mi responsabilidad, se hace
 * a través de una subquery por la cual se obtienes los numeros
 * de expedientes que tienes alguna fase o tramites con id_resp
 * perteneciente a la lista de responsables.
 *
 *
 */

public class SearchMgr
{

	final static String MAIN_ENTITY = "SPAC_EXPEDIENTES";
	final static String RELATION_MULTIPLE_ENTITY = "SPAC_ENTIDADES_RELACION_MULTIPLE";
	final static String BINDFIELD_MAINENTITY = "NUMEXP";
	final static String MULTIPLE_RELATION_TYPE = "multiple";

	// Entidad obligatoria en la búsqueda para que se relacione con un JOIN
	final static String REQUIRED_ENTITY_TYPE = "required";

	// Propiedad opcional para establecer el ámbito de la responsabilidad (stage, task)
	final static String QUERYFORM_RESPONSABILITY_PROPERTY = "responsability";
	// Responsabilidad a nivel de fase que será efectiva siempre y cuando esté presente la tabla SPAC_FASES
	final static String QUERYFORM_RESPONSABILITY_PROPERTY_STAGE_VALUE = "stage";
	// Responsabilidad a nivel de trámite que será efectiva siempre y cuando esté presente la tabla SPAC_TRAMITES
	final static String QUERYFORM_RESPONSABILITY_PROPERTY_TASK_VALUE = "task";

	private static final Logger logger = Logger.getLogger(SearchMgr.class);

	/**
	 * ClientContext
	 */
	private ClientContext mcctx = null;

	/**
	 * Constructor
	 *
	 * @param clCtx contexto del cliente
	 */
	public SearchMgr (ClientContext cctx)
	{
		mcctx = cctx;
	}

	/**
	 * Devuelve un IItemCollection con los formulario de búsqueda
	 * (en realidad, la configuracion del formulario de busqueda)
	 * @return coleccion de formularios de busqueda
	 * @throws ISPACException
	 */
	public IItemCollection getSearchForms() throws ISPACException
	{
		DbCnt cnt = null;
		IItemCollection collection = null;
		try
		{
			cnt = mcctx.getConnection();
			String respList = mcctx.getAPI().getWorkListAPI().getRespString();
			CTFrmBusquedaDAO frmDAO = new CTFrmBusquedaDAO(cnt);
			collection =  frmDAO.getSearchForms(cnt, respList).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en FrmBusquedaBO::getSearchForms ()", ie);
		}
		finally
		{
			mcctx.releaseConnection(cnt);
		}
		return collection;
	}

	/**
	 * Devuelve un formulario de búsqueda de bbdd
	 * @param idFrm identificador del formulario de busqueda
	 * @return formulario de busqueda
	 * @throws ISPACException
	 */
	public IItem getSearchForm (int idFrm) throws ISPACException
	{
		CTFrmBusquedaDAO frmDAO;

		DbCnt cnt = null;
		try
		{
			cnt = mcctx.getConnection();
			frmDAO = new CTFrmBusquedaDAO(cnt, idFrm);
		}
		finally
		{
			mcctx.releaseConnection(cnt);
		}
		return frmDAO;
	}


	public IItem getSearchForm(String nameFrm) throws ISPACException {
		CTFrmBusquedaDAO frmDAO;

		DbCnt cnt = null;
		try
		{
			cnt = mcctx.getConnection();
			frmDAO = new CTFrmBusquedaDAO();
			frmDAO.load(mcctx.getConnection(), "WHERE DESCRIPCION = '" + nameFrm + "'");
		}
		finally
		{
			mcctx.releaseConnection(cnt);
		}
		return frmDAO;
	}



	/**
	 * Devuelve el formulario html de busqueda. Este es obtenido mediante la
	 * transformacion de un xml (que es obtenido a su vez de base de datos y
	 * cuya clave primaria es idFrm) con un xsl pasado como argumento
	 * @param idFrm clave primaria del xml
	 * @param xslpath path del xsl
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @return formulario de busqueda html
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @param values Mapa con los valores introducidos por el usuario
	 * @param operators Mapa con los operadores seleccioandos por el usuario
	 * @throws ISPACException
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params,Map values, Map operators) throws ISPACException
	{
		try
		{
			IItem form = getSearchForm(idFrm);
			String sForm = form.getString("FRM_BSQ");
			return buildHTMLSearchForm(sForm, xslpath, resourceBundle, locale, params,values , operators);
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en SearchMgr::getSearchForm (" + idFrm + ","
					+ xslpath + ")", e);
		}
	}

	/**
	 * Devuelve el formulario html de busqueda. Este es obtenido mediante la
	 * transformacion de un xml (que es obtenido a su vez de base de datos y
	 * cuya clave primaria es idFrm) con un xsl pasado como argumento
	 * @param idFrm clave primaria del xml
	 * @param xslpath path del xsl
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @return formulario de busqueda html
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @throws ISPACException
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params) throws ISPACException
	{
		return buildHTMLSearchForm(idFrm, xslpath, resourceBundle, locale, params, null,null);
	}

	/**
	 * Devuelve el formulario html de busqueda. Este es obtenido mediante la
	 * trasnformacion de un xml (que es obtenido a su vez de base de datos y
	 * cuya clave primaria es idFrm) con un xsl pasado como argumento
	 * @param xml definicion del formulario
	 * @param xslpath path del xsl
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @return formulario de busqueda html
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @throws ISPACException
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params)throws ISPACException{
		return buildHTMLSearchForm(xml, xslpath, resourceBundle, locale, params, null,null);
	}

	/**
	 * Devuelve el formulario html de busqueda. Este es obtenido mediante la
	 * trasnformacion de un xml (que es obtenido a su vez de base de datos y
	 * cuya clave primaria es idFrm) con un xsl pasado como argumento
	 * @param xml definicion del formulario
	 * @param xslpath path del xsl
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @return formulario de busqueda html
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @param values Mapa con los valores introducidos por el usuario
	 * @param operators Mapa con los operadores seleccioandos por el usuario
	 * @throws ISPACException
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params,Map values, Map operators) throws ISPACException
	{
		StringWriter sw = null;

		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xmlSource = new StreamSource(new StringReader(xml));
			Source xslSource = new StreamSource(new java.net.URL("file", "", xslpath).openStream());
			Properties properties = SearchResourceListFactory.getSearchResourceProperties(SearchResourceListFactory.SEARCH_RESOURCETYPE,xml);
			PropertiesHelper propertiesHelper = new PropertiesHelper(locale, properties, resourceBundle);

			Transformer transformer = tFactory.newTransformer(xslSource);
			sw = new StringWriter();
			transformer.setParameter("propertiesHelper", propertiesHelper);
			transformer.setParameter("resourceBundle", resourceBundle);
			transformer.setParameter("mapParams", params);
			if(values==null){
				values=new HashMap();
			}
			transformer.setParameter("values", values);
			if(operators==null){
				operators=new HashMap();
			}
			transformer.setParameter("operators", operators);

			transformer.transform(xmlSource, new StreamResult(sw));
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en SearchMgr::buildHTMLSearchForm (" + xml + ","
					+ xslpath + ")", e);
		}
		return sw.getBuffer().toString();
	}

	/**
	 * Devuelve el formulario html de busqueda. Este es obtenido mediante la
	 * trasnformacion de un xml (que es obtenido a su vez de base de datos y
	 * cuya clave primaria es idFrm) con un xsl pasado como argumento
	 * @param xml fichero de definicion del formulario
	 * @param xslpath path del xsl
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @return formulario de busqueda html
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @throws ISPACException
	 */
	public String buildHTMLSearchForm(File xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params) throws ISPACException
	{
		return buildHTMLSearchForm(xml, xslpath, resourceBundle, locale, params, null , null);
	}
	/**
	 * Devuelve el formulario html de busqueda. Este es obtenido mediante la
	 * trasnformacion de un xml (que es obtenido a su vez de base de datos y
	 * cuya clave primaria es idFrm) con un xsl pasado como argumento
	 * @param xml fichero de definicion del formulario
	 * @param xslpath path del xsl
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @return formulario de busqueda html
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @param values Mapa con los valores introducidos por el usuario
	 * @param operators Mapa con los operadores seleccioandos por el usuario
	 * @throws ISPACException
	 */
	public String buildHTMLSearchForm(File xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params, Map values , Map operators) throws ISPACException
	{
		StringWriter sw = null;

		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xmlSource = new StreamSource(xml);
			Source xslSource = new StreamSource(new java.net.URL("file", "", xslpath).openStream());

			Properties properties = SearchResourceListFactory.getSearchResourceProperties(SearchResourceListFactory.SEARCH_RESOURCETYPE, xml);
			PropertiesHelper propertiesHelper = new PropertiesHelper(locale, properties, resourceBundle);

			Transformer transformer = tFactory.newTransformer(xslSource);
			sw = new StringWriter();
			transformer.setParameter("propertiesHelper", propertiesHelper);
			transformer.setParameter("resourceBundle", resourceBundle);
			transformer.setParameter("mapParams", params);
			if(values==null){
				values=new HashMap();
			}
			transformer.setParameter("values", values);
			if(operators==null){
				operators=new HashMap();
			}
			transformer.setParameter("operators", operators);
			transformer.transform(xmlSource, new StreamResult(sw));
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en SearchMgr::buildHTMLSearchForm (" + xml + ","
					+ xslpath + ")", e);
		}
		return sw.getBuffer().toString();
	}

	/**
	 * Lleva a cabo la búsqueda a partir de objeto searchinfo.
	 * El número de registros resultantes puede estar limitada por parámetros de configuración
	 * MAX_SEARCH_FRM_RESULTS , en caso de estar activado dicho parámetro. De otro modo la búsqueda
	 * no está limitada
	 * @param searchinfo
	 * @return Objeto con la lista de Items ,
	 * 		   el número máximo de registros permitidos,
	 * 		   y el número total de registros que satisfacen la búsqueda
	 * @throws ISPACException
	 */
	public SearchResultVO getLimitedSearchResults(SearchInfo searchinfo) throws ISPACException {

		DbCnt cnt = null;
		SearchResultVO searchResultVO= new SearchResultVO();

		try {

			Map joins = calculateKindOfJoinForEntities(searchinfo);
			String[] tableNames = getTableNames(searchinfo);
			String fromClause = getFromClause(searchinfo, joins);

			Property[] columns = getSelectClause(searchinfo);
			String conditions = getWhereClause(searchinfo, joins);
			String order = getOrder(searchinfo, columns);

			CollectionDAO results = TableDAO.newCollectionDAO(TableDAO.class, tableNames, fromClause, columns);

			cnt = mcctx.getConnection();
			String sMaxResultados = ISPACConfiguration.getInstance().get(ISPACConfiguration.MAX_SEARCH_FRM_RESULTS);
			int max=0;
			if(StringUtils.isNotBlank(sMaxResultados)){
				max=TypeConverter.parseInt(sMaxResultados.trim(),0);
				if(max>0){
					searchResultVO.setNumMaxRegistros(max);
					results.query(cnt, conditions, order ,max);
				}
			}

			if(max==0){

				if (StringUtils.isNotBlank(order)) {
					conditions += " ORDER BY " + order;
				}

				results.query(cnt, conditions);
			}

			searchResultVO.setResultados(results.disconnect());
			int tamLista=searchResultVO.getResultados().toList().size();
			searchResultVO.setNumTotalRegistros(tamLista);

			// Crear hito de búsqueda
			mcctx.getAPI().getTransactionAPI().newMilestone(0, 0, 0,
					InformationMilestonesConstants.SEARCH_MILESTONE,
					new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>")
						.append("<infoaux>")
						.append(getFormValuesXML(searchinfo))
						.append("</infoaux>")
						.toString(),
					"Búsqueda avanzada");

			//Si la lista resultante de la búsqueda es igual al número maximo de resultados permitidos
			//realizamos un consulta count para saber cuantos registros satisfacen la consulta
			if(searchResultVO.getNumMaxRegistros()!=SearchResultVO.NO_LIMITED){
				if(tamLista==searchResultVO.getNumMaxRegistros()){
					searchResultVO.setNumTotalRegistros(results.count(cnt, conditions));
				}
			}

		}catch(ISPACException e){
			logger.error("Error al ejecutar la búsqueda avanzada", e);
			throw new ISPACInfo("exception.searchforms.resultSearch");
		}finally {
			mcctx.releaseConnection(cnt);
		}

		return searchResultVO;
	}

	/**
	 * Devuelve los resultados de la busqueda. Estos los devuelve en forma de
	 * lista de ObjectBeans
	 *
	 * @param form bean con informacion de busqueda
	 * @return lista de resultados
	 * @throws ISPACException
	 */
	public IItemCollection getSearchResults(SearchInfo searchinfo) throws ISPACException {

		DbCnt cnt = null;

		try {

			Map joins = calculateKindOfJoinForEntities(searchinfo);
			String[] tableNames = getTableNames(searchinfo);
			String fromClause = getFromClause(searchinfo, joins);

			String conditions = getWhereClause(searchinfo, joins);
			Property[] columns = getSelectClause(searchinfo);

			CollectionDAO results = TableDAO.newCollectionDAO(TableDAO.class, tableNames, fromClause, columns);

			// Añadir el orden para la búsqueda
			String order = getOrder(searchinfo, columns);
			if (StringUtils.isNotBlank(order)) {
				conditions += " ORDER BY " + order;
			}

			cnt = mcctx.getConnection();
			results.query(cnt, conditions);

			// Crear hito de búsqueda
			mcctx.getAPI().getTransactionAPI().newMilestone(0, 0, 0,
					InformationMilestonesConstants.SEARCH_MILESTONE,
					new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>")
						.append("<infoaux>")
						.append(getFormValuesXML(searchinfo))
						.append("</infoaux>")
						.toString(),
					"Búsqueda avanzada");

			return results.disconnect();

		} catch(ISPACException e) {
			logger.error("Error al realizar la búsqueda avanzada", e);
			throw new ISPACInfo("exception.searchforms.resultSearch");
		} finally {
			mcctx.releaseConnection(cnt);
		}
	}

//	private String getFromValidationTables(SearchInfo searchinfo) throws ISPACException {
//		//Añadimos las tablas de validacion
//		StringBuffer validationTables = new StringBuffer();
//		int numPropertyFmt = searchinfo.getNumPropertyFmts();
//		for (int j=0; j<numPropertyFmt; j++)
//		{
//			IPropertyFmt propfmt = searchinfo.getPropertyFmt(j);
//			String validatetable = (String)propfmt.get("validatetable");
//			if (StringUtils.isNotEmpty(validatetable)){
//				if (validationTables.length() > 0){
//					validationTables.append(",");
//				}
//				validationTables.append(validatetable);
//			}
//		}
//		return validationTables.toString();
//
//	}

	private Property[] getSelectClause(SearchInfo searchinfo) throws ISPACException
	{
		int nProperty=0;
		ArrayList selectclause = new ArrayList();
		/*
		int numEntities = searchinfo.getNumEntities();
		for (int i=0; i<numEntities; i++)
		{
			IEntity entity = searchinfo.getEntity(i);
			int numPropertyFmt = entity.getNumPropertyFmts();
			for (int j=0; j<numPropertyFmt; j++)
			{
				IPropertyFmt propfmt = entity.getPropertyFmt(j);
				String property = propfmt.getString("property");
				String datatype = propfmt.getString("dataType");
				int type = toIntDataType(datatype);
				selectclause.add(new Property(nProperty, property, type));
				nProperty++;
			}
		}
		*/

		int numPropertyFmt = searchinfo.getNumPropertyFmts();
		if (numPropertyFmt == 0) {
			throw new ISPACInfo("exception.searchforms.noResultProperties", new String[] {searchinfo.getDescription()});
		}

		for (int j=0; j<numPropertyFmt; j++)
		{
			IPropertyFmt propfmt = searchinfo.getPropertyFmt(j);
			String property = null;
			String validatetable = (String)propfmt.get("validatetable");

			if (StringUtils.isNotEmpty(validatetable))
				property = validatetable+"."+propfmt.getString("substitute");
			else
				property = propfmt.getString("property");

			String datatype = propfmt.getString("dataType");
			int type = toIntDataType(datatype);
			selectclause.add(new Property(nProperty, property, type));
			nProperty++;
		}

		// TODO Por que se hace?
		//selectclause.add(new Property(nProperty, "SPAC_EXPEDIENTES.ID", Types.INTEGER));
		return (Property[]) selectclause.toArray(new Property[1]);
	}

	private Map calculateKindOfJoinForEntities (SearchInfo searchinfo)
	throws ISPACException
	{
		HashMap map = new HashMap ();
		int nEntities = searchinfo.getNumEntities();
		for (int i=0; i<nEntities; i++)
		{
			IEntity entity = searchinfo.getEntity(i);
			String table = entity.getTable();
			//Si la tabla es la usada como comodin para relaciones multiples o es la tabla que puede ser asociadas con varias entidades
			//no se incluirán como parte de un LEFT OUTER JOIN
			if (table.equalsIgnoreCase(RELATION_MULTIPLE_ENTITY)) {

				map.put(table.toUpperCase(), new Boolean(false));
			}
			else if ( ((Entity)entity).getType().equalsIgnoreCase(SearchMgr.MULTIPLE_RELATION_TYPE)) {

				map.put(table.toUpperCase(), new Boolean(false));
			}
			else if (!table.equalsIgnoreCase(MAIN_ENTITY)) {

				// Entidad obligatoria en la búsqueda
				// entonces se hace el JOIN para la tabla tables[i]
				if ( ((Entity)entity).getType().equalsIgnoreCase(SearchMgr.REQUIRED_ENTITY_TYPE)) {

					map.put(table.toUpperCase(), new Boolean(false));

				} else {

					// Si se desea presentar algun dato de esta entidad en el resultado,
				    // entonces se hace OUTER JOIN entre tabla EXPEDIENTES y la tabla tables[i].
					// Necesario cuando en el criterio de búsqueda existe una tabla de validación
					// que también se presenta en el resultado.
					boolean isEntityUsedInPresentation = isEntityUsedInPresentation(table, searchinfo);

					if (isEntityUsedInPresentation) {

						map.put(table.toUpperCase(), new Boolean(true));
					}
					else {
						// Si se ha introducido un criterio de búsqueda según entidad tables[i]
						// y no se desea presentar algun dato de esta entidad en el resultado,
						// la entidad participa en la query con un JOIN normal.
						boolean isEntityUsedInSearch = isEntityUsedInSearch (table, searchinfo);

						if (isEntityUsedInSearch) {

		                	map.put(table.toUpperCase(), new Boolean(false));
		                }

						// Si no se ha introducido ningun criterio de busqueda segun entidad tables[i]
						// ni tampoco se desea presentar algun dato de esta entidad en el resultado,
						// entoces la entidad no participa en la query.
					}
				}
			}
		}

		return map;
	}

	private boolean isEntityUsedInSearch (String entity, SearchInfo searchinfo)
	throws ISPACException
	{
		boolean rc = false;
		IEntity ent = searchinfo.getEntity(entity);
		int nFields = ent.getNumFields();
		for (int i=0; i<nFields; i++)
		{
			IField field = ent.getField(i);
			if (field.hasValue())
			{
				rc = true;
				break;
			}
		}
		return rc;
	}

	/*
	private boolean isEntityUsedInPresentation (String entity, Property[] columns)
	{
		boolean rc = false;
		String ent = entity.toUpperCase();
		for (int i=0; i<columns.length; i++)
		{
			String column = columns[i].getName().toUpperCase();
			if (column.indexOf(ent) != -1)
			{
				rc = true;
				break;
			}
		}
		return rc;
	}
	*/

	private boolean isEntityUsedInPresentation (String entity, SearchInfo searchInfo)
	throws ISPACException
	{
		boolean rc = false;
		String ent = entity.toUpperCase();
		int numPropertyFmt = searchInfo.getNumPropertyFmts();
		for (int j=0; j<numPropertyFmt; j++)
		{
			IPropertyFmt propfmt = searchInfo.getPropertyFmt(j);
			String column = propfmt.getString("property").toUpperCase();
			if (column.indexOf(ent) != -1)
			{
				rc = true;
				break;
			}
		}
		return rc;
	}

	private String [] getTableNames(SearchInfo searchinfo) throws ISPACException {
		List tableNames = new ArrayList();

		int nEntities = searchinfo.getNumEntities();
		for (int i=0; i<nEntities; i++) {
			IEntity entity = searchinfo.getEntity(i);
			String table = entity.getTable();
			if (StringUtils.isNotBlank(table)) {
				tableNames.add(table.toUpperCase());
			}
		}

		return (String[])tableNames.toArray(new String[tableNames.size()]);
	}

	private String getFromClause(SearchInfo searchinfo, Map joins) throws ISPACException
	{
		StringBuffer fromclause = new StringBuffer("");

		//generacion del left outer
		String leftOuterSQL = composeLeftOuterJoinSql(searchinfo, joins);
		if (leftOuterSQL != null && leftOuterSQL.length() > 0) {
			fromclause.append(leftOuterSQL);
		}else{
			//anadir la entity principal
			fromclause.append(SearchMgr.MAIN_ENTITY);
		}

		int nEntities = searchinfo.getNumEntities();
		for (int i=0; i<nEntities; i++)
		{
			IEntity entity = searchinfo.getEntity(i);
			String table = entity.getTable();
			//si hay left outer joins con la tabla principal no se añade (lo hara el generador de joins)
			if (joins.keySet().size()>0){
				Boolean join = (Boolean)joins.get(table.toUpperCase());
				if (join != null && !join.booleanValue())
                {
					if (fromclause.length()>0)
						fromclause.append(",");
					fromclause.append(table);
                }
			}
		}
        if (searchinfo.getExpState() != SearchExpState.ALL)
        	fromclause.append(", SPAC_PROCESOS");


/*
		int domain = searchinfo.getDomain();
		if (domain == SearchDomain.ONLYMYEXP)
		{
			String resplist = mcctx.getAPI().getWorkListAPI().getRespString();
			fromclause+= ", (select spac_fases.numexp from spac_fases "
									 + " where id_resp in (" + resplist + ")"
									 + " union "
									 + " select spac_tramites.numexp from spac_tramites "
									 + " where id_resp in (" + resplist + ")) myexp ";
		}
*/
		return fromclause.toString();
	}

	private String composeLeftOuterJoinSql(SearchInfo searchinfo, Map joins)
    throws ISPACException
    {
        StringBuffer outerJoinsSQL = new StringBuffer();
        int nEntities = searchinfo.getNumEntities();
        if (nEntities <= 1)
            return null;

        for (int i = 0; i < nEntities; i++)
        {
            IEntity entity = searchinfo.getEntity(i);
            String table = entity.getTable();
            if (!table.equalsIgnoreCase(SearchMgr.MAIN_ENTITY))
            {
                Boolean join = (Boolean)joins.get(table.toUpperCase());
                if (join != null && join.booleanValue())
                {

//                	if (conditions.length() != 0) {
//						//conditions.append(" , ");
//					}
                	if(outerJoinsSQL.length() == 0) {
						outerJoinsSQL.append(SearchMgr.MAIN_ENTITY);
					}
                    outerJoinsSQL.append(" LEFT OUTER JOIN ");
                    outerJoinsSQL.append(table);
                    outerJoinsSQL.append(" ON ");
                    outerJoinsSQL.append(SearchMgr.MAIN_ENTITY);
                    outerJoinsSQL.append(".");
                    outerJoinsSQL.append(SearchMgr.BINDFIELD_MAINENTITY);
                    outerJoinsSQL.append(" = ");
					outerJoinsSQL.append(table);
					outerJoinsSQL.append(".");
					outerJoinsSQL.append(entity.getBindField());
                }
            }
        }

		int numPropertyFmt = searchinfo.getNumPropertyFmts();
		for (int j=0; j<numPropertyFmt; j++)
		{
			IPropertyFmt propfmt = searchinfo.getPropertyFmt(j);
			String validatetable = (String)propfmt.get("validatetable");
			if (StringUtils.isNotBlank(validatetable))
			{
            	if(outerJoinsSQL.length() == 0) {
					outerJoinsSQL.append(SearchMgr.MAIN_ENTITY);
				}

            	String validateTableName = validatetable;
            	String validateTableAlias = validatetable;

            	String [] nombreAlias = validatetable.split(":");
            	if (nombreAlias!=null && nombreAlias.length>1){
            		validateTableName = nombreAlias[0] + " " + nombreAlias[1];
            		validateTableAlias = nombreAlias[1];
            		propfmt.set("validatetable", validateTableAlias);
            		searchinfo.setPropertyFmt((PropertyFmt) propfmt);
            	}

                outerJoinsSQL.append(" LEFT OUTER JOIN ");
                outerJoinsSQL.append(validateTableName);
                outerJoinsSQL.append(" ON ");
                outerJoinsSQL.append(propfmt.get("property"));
                outerJoinsSQL.append(" = ");
				outerJoinsSQL.append(validateTableAlias);
				outerJoinsSQL.append(".");
				outerJoinsSQL.append(propfmt.get("value"));
			}
		}

        if (outerJoinsSQL.length()==0)
            return null;

        return outerJoinsSQL.toString();
    }

	private String getWhereClause(SearchInfo searchinfo, Map joins )
			throws ISPACException {

		StringBuffer condition = new StringBuffer();

		DbCnt cnt = mcctx.getConnection();

		try {

			String sql = SearchProduct.getJoinConditions(searchinfo, joins);
		    if (sql != null && sql.trim().length() != 0) {
				condition.append("where ").append(sql);
			}

			sql = getConditionsFromInputFormValues(cnt, searchinfo);
			if (sql != null && sql.trim().length() != 0) {
				if (condition.length() == 0) {
					condition.append( "where ");
				} else {
					condition.append( " and ");
				}
				condition.append( sql);
			}
//			sql = getConditionsFromValidatedFields(searchinfo);
//			if (sql != null && sql.trim().length() != 0) {
//				if (condition.length() == 0) {
//					condition.append( "where ");
//				} else {
//					condition.append( " and ");
//				}
//				condition.append( sql);
//			}

			sql = getResponsabilityCondition(searchinfo, joins);
			if (sql != null && sql.trim().length() != 0) {
				if (condition.length() == 0) {
					condition.append( "where ");
				} else {
					condition.append( " and ");
				}
				condition.append( sql);
			}


			sql = getExpStateCondition(searchinfo);
			if (sql != null && sql.trim().length() != 0) {
				if (condition.length() == 0) {
					condition.append( "where ");
				} else {
					condition.append( " and ");
				}
				condition.append( sql);
			}

			//Filtramos para no mostrar los subprocesos
			if (joins.get("SPAC_FASES") != null){
				if (condition.length() == 0) {
					condition.append( "where ");
				} else {
					condition.append( " and ");
				}
				condition.append("(spac_fases.tipo = "+IStage.STAGE_TYPE)
				.append(" or spac_fases.tipo is null)");
			}
			if (joins.get("SPAC_PROCESOS") != null){
				if (condition.length() == 0) {
					condition.append( "where ");
				} else {
					condition.append( " and ");
				}
				condition.append( "spac_procesos.tipo = "+IProcess.PROCESS_TYPE);
			}


		} finally {
		    mcctx.releaseConnection(cnt);
		}

		return condition.toString();
	}

	private String getOrder(SearchInfo searchinfo, Property[] columns) throws ISPACException {

		StringBuffer order = new StringBuffer();

		// Establecer el orden
		int defaultSort = TypeConverter.parseInt(String.valueOf(searchinfo.get("defaultSort")), -1);
		if ((defaultSort > 0) && (defaultSort <= columns.length)) {
			order.append(columns[defaultSort-1].getName());

			// Comprobar si hay que indicar orden descendente
			if ("descending".equalsIgnoreCase(String.valueOf(searchinfo.get("defaultOrder")))) {
				order.append(" DESC");
			}
		}

		return order.toString();
	}

//	private String getConditionsFromValidatedFields(SearchInfo searchinfo) throws ISPACException {
//		StringBuffer condition = new StringBuffer();
//		int numPropertyFmt = searchinfo.getNumPropertyFmts();
//		for (int j=0; j<numPropertyFmt; j++)
//		{
//			IPropertyFmt propfmt = searchinfo.getPropertyFmt(j);
//			String validatetable = (String)propfmt.get("validatetable");
//
//			if (StringUtils.isNotEmpty(validatetable)){
//				if (condition.length() > 0)
//					condition.append(" AND ");
//				condition.append(propfmt.get("property"))
//				.append(" = ")
//				.append(validatetable)
//				.append(".")
//				.append(propfmt.get("value"));
//			}
//		}
//		return condition.toString();
//	}

	private String getResponsabilityCondition(SearchInfo searchinfo, Map joins) throws ISPACException
	{
		String condition = null;

		if (searchinfo.getDomain() == SearchDomain.ONLYMYEXP)
		{
			String resplist = mcctx.getAPI().getWorkListAPI().getRespString();
			if (resplist.equals(Responsible.SUPERVISOR))
				return "spac_expedientes.numexp in (select spac_procesos.numexp from spac_procesos)";

			// Cambio debido al bajo rendimiento de UNION
//			condition = "spac_expedientes.numexp in "
//					  + "(select spac_fases.numexp from spac_fases "
//					  + " where " + DBUtil.addInResponsibleCondition("id_resp", resplist)
//					  + " union "
//					  + " select spac_tramites.numexp from spac_tramites "
//					  + " where " + DBUtil.addInResponsibleCondition("id_resp", resplist)
//					  + " union "
//					  + " select spac_procesos.numexp from spac_procesos "
//					  + " where " + DBUtil.addInResponsibleCondition("id_resp", resplist) + ")";

			String inResponsibleCondition = DBUtil.addInResponsibleCondition("ID_RESP", resplist);
			String inPermResponsibleCondition = DBUtil.addInResponsibleCondition("SPC_PERMS.ID_RESP", resplist);
			//String respCondition = DBUtil.addInResponsibleCondition("id_resp", resplist);

			// Ámbito de responsabilidad
			String responsability = (String) searchinfo.get(QUERYFORM_RESPONSABILITY_PROPERTY);
			if (StringUtils.isNotBlank(responsability) &&
				(responsability.equalsIgnoreCase(QUERYFORM_RESPONSABILITY_PROPERTY_STAGE_VALUE) ||
				 responsability.equalsIgnoreCase(QUERYFORM_RESPONSABILITY_PROPERTY_TASK_VALUE))) {

				/*
				if (responsability.equals("stage")) {
					condition = "(spac_expedientes.numexp in "
						  + "(select spac_fases.numexp from spac_fases "
						  + " where " + WLStageDAO.addInResponsibleOrExistPermissionCondition(inResponsibleCondition,inPermResponsibleCondition)
						  + ")";
				} else {
					condition = "(spac_expedientes.numexp in (select spac_wl_task.numexp from spac_wl_task "
					  + " where " + WLTaskDAO.addInResponsibleOrExistPermissionCondition(
							  DBUtil.addInResponsibleCondition("RESP", resplist), inPermResponsibleCondition)
					  + ")";
				}
				*/
				if (responsability.equalsIgnoreCase(QUERYFORM_RESPONSABILITY_PROPERTY_STAGE_VALUE)) {

					if (joins.get("SPAC_FASES") != null) {

						condition = "(spac_fases.id in "
							  + "(select spac_fases.id from spac_fases "
							  + " where " + WLStageDAO.addInResponsibleOrExistPermissionCondition(inResponsibleCondition,inPermResponsibleCondition)
							  + ")";
					} else {
						condition = "(spac_expedientes.numexp in "
							  + "(select spac_fases.numexp from spac_fases "
							  + " where " + WLStageDAO.addInResponsibleOrExistPermissionCondition(inResponsibleCondition,inPermResponsibleCondition)
							  + ")";
					}
				} else {
					if (joins.get("SPAC_TRAMITES") != null) {

						condition = "(spac_tramites.id in (select spac_wl_task.id from spac_wl_task "
						  + " where " + WLTaskDAO.addInResponsibleOrExistPermissionCondition(
								  DBUtil.addInResponsibleCondition("RESP", resplist), inPermResponsibleCondition)
						  + ")";
					} else {
						condition = "(spac_expedientes.numexp in (select spac_wl_task.numexp from spac_wl_task "
							  + " where " + WLTaskDAO.addInResponsibleOrExistPermissionCondition(
									  DBUtil.addInResponsibleCondition("RESP", resplist), inPermResponsibleCondition)
							  + ")";
					}
				}
			} else {
				condition = "(spac_expedientes.numexp in "
					  + "(select spac_fases.numexp from spac_fases "
					  + " where " + WLStageDAO.addInResponsibleOrExistPermissionCondition(inResponsibleCondition,inPermResponsibleCondition)
					  + ") or spac_expedientes.numexp in (select spac_wl_task.numexp from spac_wl_task "
					  + " where " + WLTaskDAO.addInResponsibleOrExistPermissionCondition(
							  DBUtil.addInResponsibleCondition("RESP", resplist), inPermResponsibleCondition)
					  + ")";
			}

			condition += " or spac_expedientes.numexp in (select spac_procesos.numexp from spac_procesos "
				  + " where estado = " + IProcess.CLOSED
				  + " and " + TXProcesoDAO.addInResponsibleOrExistPermissionCondition(inResponsibleCondition, inPermResponsibleCondition) + "))";
		}

		return condition;
	}

	private String getExpStateCondition(SearchInfo searchinfo) throws ISPACException
	{
		String condition = null;

		if ((searchinfo.getExpState() == SearchExpState.ACTIVE)
				|| (searchinfo.getExpState() == SearchExpState.END)
				|| (searchinfo.getExpState() == SearchExpState.ARCHIVE))
		{
			condition = " SPAC_PROCESOS.NUMEXP = " + SearchMgr.MAIN_ENTITY
				+ ".NUMEXP AND SPAC_PROCESOS.ESTADO = " + searchinfo.getExpState();
//			String resplist = mcctx.getAPI().getWorkListAPI().getRespString();
//
//			condition = "spac_expedientes.numexp in "
//					  + "(select spac_fases.numexp from spac_fases "
//					  + " where id_resp in (" + resplist + ")"
//					  + " union "
//					  + " select spac_tramites.numexp from spac_tramites "
//					  + " where id_resp in (" + resplist + ")"
//					  + " union "
//					  + " select spac_procesos.numexp from spac_procesos "
//					  + " where id_resp in (" + resplist + "))";
		}

		return condition;
	}



	private String getConditionsFromInputFormValues(DbCnt cnt,
			SearchInfo searchinfo) throws ISPACException {

		StringBuffer condition = new StringBuffer();
		int nEntities = searchinfo.getNumEntities();
		for (int i=0; i<nEntities; i++) {
			IEntity entity = searchinfo.getEntity(i);
			String table = entity.getTable();
			int nFields = entity.getNumFields();
			for (int j=0; j<nFields; j++) {
				IField field = entity.getField(j);

				if (StringUtils.equals("hidden", field.getControlType().getControlType())){
					if (condition.length() != 0) {
						condition.append( " and ");
					}
					String binding=field.getBinding();
					condition.append( table);
					condition.append( ".");
					condition.append( binding);
				}else if (field.hasValue()) {

					//TODO [Multivalue] Quitar esta comprobacion cuando se soporte búsqueda sobre campos multivalor
					CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entity.getTable().toUpperCase());
					if (ctEntityDAO.getType() == EntityType.PROCESS_ENTITY_TYPE.getId()){
			        	String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
			        	EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);
			        	if (entityDefinition.getField(field.getColumName()).isMultivalue()){
			        		//throw new ISPACException ("No está soportada la búsqueda sobre campos multivalor");
			        		throw new ISPACInfo("exception.multivalue.nosupport");
			        	}
					}



					String nameField = field.getColumName();
					String type = field.getDatatype();
					String operator = field.getOperator();
					Object value = field.getValue();
					if(StringUtils.isNotEmpty(value.toString())){
					if (condition.length() != 0) {
						condition.append( " and ");
					}


					if(!StringUtils.equalsIgnoreCase(type,"stringList")){

					String operador=prepareOperator(value.toString(), type, operator);
					if(!StringUtils.containsIgnoreCase(operador, "CONTAINS")){
						condition.append( table);
						condition.append( ".");
						condition.append( nameField);
						condition.append( " ");
						condition.append( operador);
						condition.append( " ");
						condition.append( prepareValue(cnt, value.toString(), type, operator));
					}
					//Busqueda documental
					else{
						condition.append(DBUtil.getContainsOperator(cnt, table, nameField, value.toString()));
					}
					}
					else{
						/*Si es una selección múltiple necesitamos conocer el tipo del valor
						el tipo se especifica mediante el atributo typeValue  , sólo admite dos valores
						numeric y string, por defecto se supone el tipo numeric*/

						String typeValue="numeric";
						if(field.getControlType().get("typeValue")!=null){
							typeValue=field.getControlType().getString("typeValue");
						}
						String binding=field.getBinding();
						String[]valuesItem=value.toString().split(",");
						valuesItem[0]=valuesItem[0].substring(1);
						String ulti=valuesItem[valuesItem.length-1];
						valuesItem[valuesItem.length-1]=ulti.substring(0, ulti.length()-1);
						condition.append(" ( ");
						if(StringUtils.isNotEmpty(binding)){
							//Se sustituye ${VALUES} por los valores que tengamos
							String values ="";
							for(int k=0; k<valuesItem.length; k++){
								if(k!=0){
									values+=" , ";
								}
								values+=prepareValue(cnt,  valuesItem[k], typeValue,"");
							}

							binding=binding.replaceAll("@VALUES", values);

							condition.append( table);
							condition.append( ".");
							condition.append( nameField);
							condition.append( " ");
							condition.append( binding);

						}
						else{
							for(int k=0; k<valuesItem.length;k++){
								if(k!=0){
									condition.append(" OR ");
								}

								condition.append( table);
								condition.append( ".");
								condition.append( nameField);
								condition.append( " ");
								condition.append( "=");
								condition.append( " ");
								condition.append( prepareValue(cnt, StringUtils.trim(valuesItem[k]), typeValue, "="));
						}
					}
						condition.append(" ) ");
				}
				}

			}
		}
		}
		return condition.toString();
	}

	private String prepareOperator (String value, String type, String operator)
	{
		if (type.equalsIgnoreCase("string") )
		{
		     if (value.indexOf('*') != -1 || value.indexOf('_') != -1 || StringUtils.containsIgnoreCase(operator, "like"))
				operator = " like ";

		     else if(StringUtils.isEmpty(operator)){
					operator=" = ";
			}
		     else if(StringUtils.containsIgnoreCase(operator, "Index")){
		    	 operator="CONTAINS";
		     }
		}
        else if (type.equalsIgnoreCase("date"))
        {
            if ( operator==null || operator.length()==0 || operator.equals("=") || operator.equals(".."))
                operator = "between";
        }
        else if (type.equalsIgnoreCase("integer")||type.equalsIgnoreCase("long"))
        {
            if (operator==null || operator.length()==0)
                operator=" = ";
        }


		return operator;
	}

	private String prepareValue(DbCnt cnt, String value, String type,
			String operator) throws ISPACException {

		if (type.equalsIgnoreCase("string")) {

			if(!StringUtils.containsIgnoreCase(operator, "like")){
			value = "'" + DBUtil.replaceQuotes(value) + "'";
			value = value.replace('*', '%');
			}
			else{
				value='%'+value+'%';
				value = "'" + DBUtil.replaceQuotes(value) + "'";
			}
		}else if(type.equalsIgnoreCase("integer")||type.equalsIgnoreCase("long") ||type.equalsIgnoreCase("numeric")){

			value=DBUtil.replaceQuotes(value);
		}
		else if (type.equalsIgnoreCase("date")) {

			if (operator.equals("=")) {
				if (!GenericValidator.isDate(value, "dd/MM/yyyy", true))
					throw new ISPACInfo("exception.date.invalid", value);

				//value = "{TS '" + value + " 00:00:00'} AND {TS '" + value + " 23:59:59'}";
	        	value = new StringBuffer()
	        		.append(DBUtil.getToTimestampByBD(cnt, value + " 00:00:00"))
	        		.append(" AND ")
	        		.append(DBUtil.getToTimestampByBD(cnt, value + " 23:59:59"))
	        		.toString();

            }else if (operator.equals("..")) {
            	String initDate = getInitIntervalDate(value, ";");
            	String endDate = getEndIntervalDate(value, ";");
            	if (!GenericValidator.isDate(initDate, "dd/MM/yyyy", true) || !GenericValidator.isDate(endDate, "dd/MM/yyyy", true))
					throw new ISPACInfo("exception.date.invalid", value);
            	try {
					if (DateUtil.compare(DateUtil.getDate(initDate), DateUtil.getDate(endDate)) == 1)
						throw new ISPACInfo("exception.date.interval.invalid", value);
				} catch (ISPACInfo e) {
					throw e;
				} catch (Exception e) {
					throw new ISPACException(e);
				}


            	value = new StringBuffer()
            		.append(DBUtil.getToTimestampByBD(cnt, initDate+ " 00:00:00"))
            		.append(" AND ")
            		.append(DBUtil.getToTimestampByBD(cnt, endDate + " 23:59:59"))
            		.toString();
            } else if (operator.equals(">") || operator.equals("<=")) {
				if (!GenericValidator.isDate(value, "dd/MM/yyyy", true))
					throw new ISPACInfo("exception.date.invalid", value);
            	//value = "{TS '" + value + " 23:59:59'}";
            	value = DBUtil.getToTimestampByBD(cnt, value + " 23:59:59");
            } else if (operator.equals(">=") || operator.equals("<")) {
				if (!GenericValidator.isDate(value, "dd/MM/yyyy", true))
					throw new ISPACInfo("exception.date.invalid");
            	//value = "{TS '" + value + " 00:00:00'}";
            	value = DBUtil.getToTimestampByBD(cnt, value + " 00:00:00");
            }
		}
		return value;
	}

	private int toIntDataType(String sType)
	{
		int type = -1;
		if (sType.equalsIgnoreCase("string"))
			type = Types.VARCHAR;
		else if (sType.equalsIgnoreCase("integer"))
			type = Types.INTEGER;
		else if (sType.equalsIgnoreCase("long"))
			type = Types.BIGINT;
		else if (sType.equalsIgnoreCase("date"))
			type = Types.DATE;
		return type;
	}

	private String getInitIntervalDate(String value, String separator){
		return StringUtils.substring(value, 0, StringUtils.indexOf(value, separator));
	}

	private String getEndIntervalDate(String value, String separator){
		return StringUtils.substring(value, StringUtils.indexOf(value, separator)+1);
	}

	private String getFormValuesXML(SearchInfo searchinfo) throws ISPACException {

		StringBuffer conditions = new StringBuffer();

		int nEntities = searchinfo.getNumEntities();
		for (int i=0; i<nEntities; i++) {
			IEntity entity = searchinfo.getEntity(i);
			String table = entity.getTable();
			int nFields = entity.getNumFields();
			for (int j=0; j<nFields; j++) {
				IField field = entity.getField(j);
				if (field.hasValue()) {
					conditions.append(XmlTag.newTag("condition",
							XmlTag.newTag("parameter", table + "." + field.getColumName(), true)
							+ XmlTag.newTag("operator", field.getOperator() != null ? field.getOperator() : "=", true)
							+ XmlTag.newTag("value", field.getValue().toString(), true)
							));
				}
			}
		}

		return XmlTag.newTag("searchinfo", conditions);
	}


}
