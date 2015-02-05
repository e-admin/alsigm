/*
 * Created on Jan 3, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ISearchAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventBusquedaVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.search.SearchMgr;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchDomain;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class SearchAPI implements ISearchAPI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SearchAPI.class);

	/**
	 * Contexto del cliente
	 */
	private final ClientContext context;

	private IspacAuditoriaManager auditoriaManager;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            Contexto del cliente
	 */
	public SearchAPI(ClientContext context) {
		this.context = context;
		auditoriaManager = new IspacAuditoriaManagerImpl();
	}

	/**
	 * Devuelve un IItem con la información de un registro de la tabla
	 * spac_ct_frmbusqueda
	 * 
	 * @param idFrm
	 *            identificador del formulario en bbdd
	 * @return IItem
	 */
	public IItem getSearchForm(int idFrm) throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.getSearchForm(idFrm);
	}

	/**
	 * Devuelve un IItem con la información de un registro de la tabla
	 * spac_ct_frmbusqueda
	 * 
	 * @param nameFrm
	 *            nombre del formulario en bbdd
	 * @return IItem
	 */
	public IItem getSearchForm(String nameFrm) throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.getSearchForm(nameFrm);
	}

	/**
	 * Devuelve un IItemCollection con la información de los registros de la
	 * tabla spac_ct_frmbusqueda
	 * 
	 * @return IItemCollection
	 */
	public IItemCollection getSearchForms() throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.getSearchForms();
	}

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una
	 * transformacion xsl. Esta transformación es llevada a cabo con el xsl
	 * pasado como parámetro y con el xml que representa el formulario
	 * 
	 * @param idFrm
	 *            identificador del formulario
	 * @param xslpath
	 *            path del xsl para la transformacion
	 * @param resourceBundle
	 *            recursos del idioma en el que se visualiza el formulario
	 * @param params
	 *            Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle,
			Locale locale, Map params) throws ISPACException {

		return buildHTMLSearchForm(idFrm, xslpath, resourceBundle, locale, params, null, null);
	}

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una
	 * transformacion xsl. Esta transformación es llevada a cabo con el xsl
	 * pasado como parámetro y con el xml que representa el formulario
	 * 
	 * @param idFrm
	 *            identificador del formulario
	 * @param xslpath
	 *            path del xsl para la transformacion
	 * @param resourceBundle
	 *            recursos del idioma en el que se visualiza el formulario
	 * @param params
	 *            Map de parametros a tener en cuenta en la transformacion
	 * @param values
	 *            Mapa con los valores introducidos por el usuario
	 * @param operators
	 *            Mapa con los operadores seleccioandos por el usuario
	 * @return un string con el formulario html
	 */

	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle,
			Locale locale, Map params, Map values, Map operators) throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.buildHTMLSearchForm(idFrm, xslpath, resourceBundle, locale, params, values,
				operators);
	}

	/**
	 * @deprecated
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle,
			Map params) throws ISPACException {

		return buildHTMLSearchForm(idFrm, xslpath, resourceBundle, null, params);
	}

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una
	 * transformacion xsl. Esta transformación es llevada a cabo con el xsl
	 * pasado como parámetro y con el xml tambien pasado como parámetro
	 * 
	 * @param xml
	 *            definicion del formulario
	 * @param xslpath
	 *            path del xsl para la transformacion
	 * @param resourceBundle
	 *            recursos del idioma en el que se visualiza el formulario
	 * @param params
	 *            Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle,
			Locale locale, Map params) throws ISPACException {
		return buildHTMLSearchForm(xml, xslpath, resourceBundle, locale, params, null, null);
	}

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una
	 * transformacion xsl. Esta transformación es llevada a cabo con el xsl
	 * pasado como parámetro y con el xml tambien pasado como parámetro
	 * 
	 * @param xml
	 *            definicion del formulario
	 * @param xslpath
	 *            path del xsl para la transformacion
	 * @param resourceBundle
	 *            recursos del idioma en el que se visualiza el formulario
	 * @param params
	 *            Map de parametros a tener en cuenta en la transformacion
	 * @param values
	 *            Mapa con los valores introducidos por el usuario
	 * @param operators
	 *            Mapa con los operadores seleccioandos por el usuario
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle,
			Locale locale, Map params, Map values, Map operators) throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.buildHTMLSearchForm(xml, xslpath, resourceBundle, locale, params, values,
				operators);
	}

	/**
	 * @deprecated
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle,
			Map params) throws ISPACException {
		return buildHTMLSearchForm(xml, xslpath, resourceBundle, null, params);
	}

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una
	 * transformacion xsl. Esta transformación es llevada a cabo con el xsl
	 * pasado como parámetro y con el xml tambien pasado como parámetro
	 * 
	 * @param xml
	 *            fichero de definicion del formulario
	 * @param xslpath
	 *            path del xsl para la transformacion
	 * @param resourceBundle
	 *            recursos del idioma en el que se visualiza el formulario
	 * @param params
	 *            Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(File xml, String xslpath, ResourceBundle resourceBundle,
			Locale locale, Map params) throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.buildHTMLSearchForm(xml, xslpath, resourceBundle, locale, params);
	}

	/**
	 * @deprecated
	 */
	public String buildHTMLSearchForm(File xml, String xslpath, ResourceBundle resourceBundle,
			Map params) throws ISPACException {

		return buildHTMLSearchForm(xml, xslpath, resourceBundle, null, params);
	}

	/**
	 * Devuelve un objeto de tipo SearchInfo que contine la información
	 * necesaria para realizar una determinada busqueda. Esta información es
	 * obtenida a partir del formulario de busqueda con identificador idFrm. El
	 * segundo parámetro es el dominio de la busqueda: solo mi responsabilidad o
	 * todos
	 * 
	 * @param idFrm
	 *            identificador del formulario
	 * @param domain
	 *            dominio de la busqueda
	 * @param expState
	 *            indica los expedientes sobre los que realizar la búsqueda
	 *            (abiertos y/o finalizados)
	 * @return SearchInfo
	 */
	public SearchInfo getSearchInfo(int idFrm, int domain, int expState) throws ISPACException {
		if (!SearchDomain.isDomain(domain))
			throw new ISPACException("Error en SearchAPI::getSearchInfo: dominio no válido");
		SearchMgr mgr = new SearchMgr(context);
		IItem item = mgr.getSearchForm(idFrm);
		return new SearchInfo(item.getString("FRM_BSQ"), domain, expState);
	}

	/**
	 * Devuelve un objeto de tipo SearchInfo que contine la información
	 * necesaria para realizar una determinada busqueda. Esta información es
	 * obtenida a partir del formulario de busqueda pasado como parámetro El
	 * segundo parámetro es el dominio de la busqueda: sólo mi responsabilidad o
	 * todos
	 * 
	 * @param xml
	 *            formulario de busqueda
	 * @param domain
	 *            dominio de la busqueda
	 * @return SearchInfo
	 */
	public SearchInfo getSearchInfo(String xml, int domain, int expState) throws ISPACException {
		if (!SearchDomain.isDomain(domain))
			throw new ISPACException("Error en SearchAPI::getSearchInfo: dominio no válido");
		return new SearchInfo(xml, domain, expState);
	}

	/**
	 * Devuelve un objeto de tipo SearchInfo que contine la información
	 * necesaria para realizar una determinada busqueda. Esta información es
	 * obtenida a partir del formulario de busqueda pasado como parámetro El
	 * segundo parámetro es el dominio de la busqueda: sólo mi responsabilidad o
	 * todos
	 * 
	 * @param path
	 *            formulario de busqueda
	 * @param domain
	 *            dominio de la busqueda
	 * @return SearchInfo
	 */
	public SearchInfo getSearchInfoPath(String path, int domain, int expState)
			throws ISPACException {
		if (!SearchDomain.isDomain(domain))
			throw new ISPACException("Error en SearchAPI::getSearchInfo: dominio no válido");
		try {
			FileInputStream in = new FileInputStream(path);
			return new SearchInfo(in, domain, expState);
		} catch (FileNotFoundException e) {
			throw new ISPACException(e);
		}
	}

	/**
	 * Lleva a cabo la busqueda a partir del objeto searchinfo .Devuelve todos
	 * los resultados
	 * 
	 * @param searchinfo
	 *            información para llevar a cabo la busqueda
	 * @return Devuelve una colección de IItems, cada uno con un resultado de la
	 *         busqueda
	 */
	public IItemCollection getSearchResults(SearchInfo searchinfo) throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		return mgr.getSearchResults(searchinfo);
	}

	/**
	 * Lleva a cabo la búsqueda a partir de objeto searchinfo. El número de
	 * registros resultantes puede estar limitada por parámetros de
	 * configuración MAX_SEARCH_FRM_RESULTS , en caso de estar activado dicho
	 * parámetro. De otro modo la búsqueda no está limitada
	 * 
	 * @param searchinfo
	 * @return Objeto con la lista de Items , el número máximo de registros
	 *         permitidos, y el número total de registros que satisfacen la
	 *         búsqueda
	 * @throws ISPACException
	 */
	public SearchResultVO getLimitedSearchResults(SearchInfo searchinfo)
			throws ISPACException {
		SearchMgr mgr = new SearchMgr(context);
		SearchResultVO result = mgr.getLimitedSearchResults(searchinfo);

		auditSearch(searchinfo);
		return result;

	}

	/**
	 * Audita la búsqueda realizada por el usuario
	 * 
	 * @param searchinfo
	 */
	private void auditSearch(SearchInfo searchinfo) {
		if (logger.isDebugEnabled()) {
			logger.debug("auditSearch(SearchInfo, AuditContext) - start");
		}

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventBusquedaVO evento = new IspacAuditEventBusquedaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setConsulta(searchinfo.toXml());
		evento.setFecha(new Date());
		evento.setIdUser("");
		evento.setUser("");

		if (auditContext != null) {					
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}

		auditoriaManager.audit(evento);

		if (logger.isDebugEnabled()) {
			logger.debug("auditSearch(SearchInfo, AuditContext) - end");
		}
	}

}