/*
 * Created on Jan 5, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.api;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;


public interface ISearchAPI
{
	/**
	 * Devuelve un IItem con la informaci&oacute;n del catálogo de búsquedas
	 * @param idFrm identificador del formulario en bbdd
	 * @return IItem
	 */
	public IItem getSearchForm (int idFrm) throws ISPACException;

	
	/**
	 * Devuelve un IItem con la informaci&oacute;n del catálogo de búsquedas
	 * @param nameFrm nombre del formulario en bbdd
	 * @return IItem
	 */
	public IItem getSearchForm (String nameFrm) throws ISPACException;	
	
	/**
	 * Devuelve un IItemCollection con la informaci&oacute;n de los registros
	 * de la tabla spac_ct_frmbusqueda
	 * @return IItemCollection
	 */
	public IItemCollection getSearchForms() throws ISPACException;

	/**
	 * @deprecated
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformaci&oacute;n es llevada a cabo con el xsl pasado como parametro
	 * y con el xml que representa el formulario
	 * @param idFrm identificador del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle, Map params) throws ISPACException;

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformaci&oacute;n es llevada a cabo con el xsl pasado como parametro
	 * y con el xml que representa el formulario
	 * @param idFrm identificador del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param locale Locale a utilizar para obtener los recursos internacionalizados
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params) throws ISPACException;

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformaci&oacute;n es llevada a cabo con el xsl pasado como parametro
	 * y con el xml que representa el formulario
	 * @param idFrm identificador del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param locale Locale a utilizar para obtener los recursos internacionalizados
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @param values Mapa con los valores introducidos por el usuario
	 * @param operators Mapa con los operadores seleccioandos por el usuario
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(int idFrm, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params, Map values, Map operators) throws ISPACException;
	

	/**
	 * @deprecated
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformaci&oacute;n es llevada a cabo con el xsl pasado como parametro
	 * y con el xml tambien pasado como parametro
	 * @param xml definicion del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle, Map params) throws ISPACException;
	
	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformaci&oacute;n es llevada a cabo con el xsl pasado como parametro
	 * y con el xml tambien pasado como parametro
	 * @param xml definicion del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param locale Locale a utilizar para obtener los recursos internacionalizados
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params) throws ISPACException;
	
	
	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformaci&oacute;n es llevada a cabo con el xsl pasado como parametro
	 * y con el xml tambien pasado como parametro
	 * @param xml definicion del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param locale Locale a utilizar para obtener los recursos internacionalizados
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @param values Mapa con los valores introducidos por el usuario
	 * @param operators Mapa con los operadores seleccioandos por el usuario
	 * @return un string con el formulario html
	 */
	public String buildHTMLSearchForm(String xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params, Map values , Map operators) throws ISPACException;
	/**
	 * @deprecated
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformación es llevada a cabo con el xsl pasado como parámetro
	 * y con el xml tambien pasado como parámetro
	 * @param xml fichero de definicion del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */	
	public String buildHTMLSearchForm(File xml, String xslpath, ResourceBundle resourceBundle, Map params) throws ISPACException;

	/**
	 * Genera un formulario de busqueda HTML. Para ello aplica una transformacion
	 * xsl. Esta transformación es llevada a cabo con el xsl pasado como parámetro
	 * y con el xml tambien pasado como parámetro
	 * @param xml fichero de definicion del formulario
	 * @param xslpath path del xsl para la transformacion
	 * @param resourceBundle recursos del idioma en el que se visualiza el formulario
	 * @param locale Locale a utilizar para obtener los recursos internacionalizados
	 * @param params Map de parametros a tener en cuenta en la transformacion
	 * @return un string con el formulario html
	 */	
	public String buildHTMLSearchForm(File xml, String xslpath, ResourceBundle resourceBundle, Locale locale, Map params) throws ISPACException;

	/**
	 * Devuelve un objeto de tipo SearchInfo que contine la informaci&oacute;n
	 * necesaria para realizar una determinada busqueda. Esta informaci&oacute;n
	 * es obtenida a partir del formulario de busqueda con identificador
	 * idFrm. El segundo par&aacute;metro es el dominio de la busqueda: solo mi
	 * responsabilidad o todos
	 * @param idFrm identificador del formulario
	 * @param domain dominio de la busqueda
	 * @param expState indica los expedientes sobre los que realizar la búsqueda (abiertos y/o finalizados) 
	 * @return SearchInfo
	 */
	public SearchInfo getSearchInfo(int idFrm, int domain, int expState) throws ISPACException;

	/**
	 * Devuelve un objeto de tipo SearchInfo que contine la informaci&oacute;n
	 * necesaria para realizar una determinada busqueda. Esta informaci&oacute;n
	 * es obtenida a partir del formulario de busqueda pasado como parametro
	 * El segundo par&aacute;metro es el dominio de la busqueda: s&oacute;lo mi
	 * responsabilidad o todos
	 * @param xml formulario de busqueda
	 * @param domain dominio de la busqueda
	 * @param expState TODO
	 * @return SearchInfo
	 */
	public SearchInfo getSearchInfo(String xml, int domain, int expState) throws ISPACException;

	/**
	 * Devuelve un objeto de tipo SearchInfo que contine la informaci&oacute;n
	 * necesaria para realizar una determinada busqueda. Esta informaci&oacute;n
	 * es obtenida a partir del formulario de busqueda pasado como par&aacute;metro
	 * El segundo par&aacute;metro es el dominio de la busqueda: s&oacute;lo mi
	 * responsabilidad o todos
	 * @param path formulario de busqueda
	 * @param domain dominio de la busqueda
	 * @param expState TODO
	 * @return SearchInfo
	 */
	public SearchInfo getSearchInfoPath( String path, int domain, int expState) throws ISPACException;

	/**
	 * Lleva a cabo la busqueda a partir del objeto searchinfo
	 * @param searchinfo informaci&oacute;n para llevar a cabo la busqueda
	 * @return Devuelve una colecci&oacute;n de IItems, cada uno con un resultado
	 * de la busqueda
	 */
	public IItemCollection getSearchResults(SearchInfo searchinfo) throws ISPACException;
	
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
	public SearchResultVO getLimitedSearchResults(SearchInfo searchinfo) throws ISPACException;
	
}