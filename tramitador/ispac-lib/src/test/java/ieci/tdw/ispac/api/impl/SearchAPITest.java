package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchDomain;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchExpState;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class SearchAPITest extends TestCase {
	
	private static final Logger logger = Logger.getLogger("TEST");

	protected ClientContext getClientContext() {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		return ctx;
	}
	
	protected SearchAPI getSearchAPI() {
		return new SearchAPI(getClientContext());
	}

	
	public void testGetSearchResultsOrderByNumExpASC() throws ISPACException {
		
		logger.info("testGetSearchResultsOrderByNumExpASC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(2, null), SearchDomain.ALLEXP, SearchExpState.ALL);
		IItemCollection results = getSearchAPI().getSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetSearchResultsOrderByNumExpDESC() throws ISPACException {
		
		logger.info("testGetSearchResultsOrderByNumExpDESC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(2, "descending"), SearchDomain.ALLEXP, SearchExpState.ALL);
		IItemCollection results = getSearchAPI().getSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetSearchResultsOrderByFechaAperturaASC() throws ISPACException {
		
		logger.info("testGetSearchResultsOrderByFechaAperturaASC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(6, null), SearchDomain.ALLEXP, SearchExpState.ALL);
		IItemCollection results = getSearchAPI().getSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetSearchResultsOrderByFechaAperturaDESC() throws ISPACException {
		
		logger.info("testGetSearchResultsOrderByFechaAperturaDESC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(6, "descending"), SearchDomain.ALLEXP, SearchExpState.ALL);
		IItemCollection results = getSearchAPI().getSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetLimitedSearchResultsOrderByNumExpASC() throws ISPACException {
		
		logger.info("testGetLimitedSearchResultsOrderByNumExpASC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(2, null), SearchDomain.ALLEXP, SearchExpState.ALL);
		SearchResultVO results = getSearchAPI().getLimitedSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetLimitedSearchResultsOrderByNumExpDESC() throws ISPACException {
		
		logger.info("testGetLimitedSearchResultsOrderByNumExpDESC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(2, "descending"), SearchDomain.ALLEXP, SearchExpState.ALL);
		SearchResultVO results = getSearchAPI().getLimitedSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetLimitedSearchResultsOrderByFechaAperturaASC() throws ISPACException {
		
		logger.info("testGetLimitedSearchResultsOrderByFechaAperturaASC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(6, null), SearchDomain.ALLEXP, SearchExpState.ALL);
		SearchResultVO results = getSearchAPI().getLimitedSearchResults(searchInfo);

		logSearchResults(results);
	}

	public void testGetLimitedSearchResultsOrderByFechaAperturaDESC() throws ISPACException {
		
		logger.info("testGetLimitedSearchResultsOrderByFechaAperturaDESC");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXML(6, "descending"), SearchDomain.ALLEXP, SearchExpState.ALL);
		SearchResultVO results = getSearchAPI().getLimitedSearchResults(searchInfo);

		logSearchResults(results);
	}


	public void testGetSearchResultsOpenTasks() throws ISPACException {
		
		logger.info("testGetSearchResultsOpenTasks");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXMLOpenTasks(), SearchDomain.ALLEXP, SearchExpState.ALL);
		IItemCollection results = getSearchAPI().getSearchResults(searchInfo);

		logTasksSearchResults(results);
		
	}
	

	public void testGetSearchResultsAllTasks() throws ISPACException {
		
		logger.info("testGetSearchResultsAllOpenTasks");
		
		SearchInfo searchInfo = new SearchInfo(getSearchFormXMLAllTasks(), SearchDomain.ALLEXP, SearchExpState.ALL);
		IItemCollection results = getSearchAPI().getSearchResults(searchInfo);

		logTasksSearchResults(results);
		
	}	
	
	
	protected String getSearchFormXMLAllTasks() {
		StringBuffer xml = new StringBuffer();

		xml.append("<?xml version='1.0' encoding='ISO-8859-1'?>");
		xml.append("<?xml-stylesheet type='text/xsl' href='SearchForm.xsl'?>");
		xml.append("<queryform displaywidth='95%' defaultSort='1'>");
		xml.append("<!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA-->");
		xml.append("<entity type='main' identifier='1'>");
		xml.append("<!--INICIO CUERPO BUSQUEDA-->");
		xml.append("	<tablename>spac_expedientes</tablename>");
		xml.append("	<description>DATOS DEL EXPEDIENTE</description>");
		xml.append("	<field type='query' order='01'>");
		xml.append("		<columnname>ID_PCD</columnname>");
		xml.append("		<description>search.form.procedimiento</description>");
		xml.append("		<datatype>integer</datatype>");
		xml.append("		<controltype size='30'  maxlength='30' tipo='validate' table='SPAC_P_PROCEDIMIENTOS' field='spac_expedientes:ID_PCD' label='NOMBRE'  value='ID' clause='WHERE ESTADO IN (2,3) AND TIPO = 1'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='04'>");
		xml.append("		<columnname>NUMEXP</columnname>");
		xml.append("		<description>search.form.numExpediente</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("		 	<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='30' maxlength='30'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='05'>");
		xml.append("		<columnname>ASUNTO</columnname>");
		xml.append("		<description>search.form.asunto</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='30' maxlength='30'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='06'>");
		xml.append("		<columnname>NREG</columnname>");
		xml.append("		<description>search.form.numRegistro</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='16' maxlength='16'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='07'>");
		xml.append("		<columnname>IDENTIDADTITULAR</columnname>");
		xml.append("		<description>search.form.interesadoPrincipal</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='50' maxlength='50'>text</controltype> ");
		xml.append("	</field>");
		xml.append("	<field type='query' order='08'>");
		xml.append("		<columnname>NIFCIFTITULAR</columnname>");
		xml.append("		<description>search.form.ifInteresadoPrincipal</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='16' maxlength='16'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='09'>");
		xml.append("		<columnname>FAPERTURA</columnname>");
		xml.append("		<description>search.form.fechaApertura</description>");
		xml.append("		<datatype>date</datatype> ");
		xml.append("		<controltype size='22' maxlength='22'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='10'>");
		xml.append("		<columnname>ESTADOADM</columnname>");
		xml.append("		<description>search.form.estadoAdm</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='20' maxlength='20' tipo='validate' table='SPAC_TBL_004' field='spac_expedientes:ESTADOADM' label='SUSTITUTO' value='VALOR'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='11'>     ");
		xml.append("		<columnname>HAYRECURSO</columnname>");
		xml.append("		<description>search.form.recurso</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='2' maxlength='2'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='12'>");
		xml.append("		<columnname>CIUDAD</columnname>");
		xml.append("		<description>search.form.ciudad</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='50' maxlength='50'>text</controltype>");
		xml.append("	</field>");
		xml.append("		<field type='query' order='13'>");
		xml.append("		<columnname>DOMICILIO</columnname>");
		xml.append("		<description>search.form.domicilio</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("      <operator><name>Contiene(Index)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype cols='100' rows='5'>textarea</controltype>");
		xml.append("	</field>");
		xml.append("<!--FIN CUERPO BUSQUEDA-->");
		xml.append("</entity>");
		xml.append("<!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA-->");
		xml.append("<!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA");
		xml.append("	obligatoria en la busqueda para que se relacione con un JOIN -->");
		xml.append("<entity type='required' identifier='51'>");
		xml.append("	<tablename>spac_dt_tramites</tablename>");
		xml.append("	<bindfield>NUMEXP</bindfield>");
		xml.append("	<field type='query' order='03'>");
		xml.append("		<columnname>ID_TRAM_PCD</columnname>");
		xml.append("		<description>search.form.tramites</description>");
		xml.append("		<datatype>stringList</datatype>");
		xml.append("		<binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>");
		xml.append("		<controltype height='75px' tipo='list' table='SPAC_CT_TRAMITES' field='spac_dt_tramites:ID_TRAM_PCD' label='NOMBRE'  value='id'>text</controltype>");
		xml.append("	</field>");
		xml.append("</entity>");
		xml.append("<!--FIN SEGUNDA ENTIDAD DE BUSQUEDA-->");
		xml.append("<!--INICIO CUERPO RESULTADO-->");
		xml.append("<!--propertyfmt type='BeanPropertySimpleFmt' property='SPAC_DT_TRAMITES.ID_TRAM_EXP' readOnly='false' dataType='string' fieldType='CHECKBOX' fieldLink='SPAC_DT_TRAMITES.ID_TRAM_EXP' /-->");
		xml.append("<!-- Necesario para obtener en la consulta la propiedad de SPAC_DT_TRAMITES.ID_TRAM_EXP que se necesita para generar el enlace del LINKPARAM -->");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_DT_TRAMITES.ID_TRAM_EXP' readOnly='false' dataType='string' fieldType='NONE' fieldLink='SPAC_DT_TRAMITES.ID_TRAM_EXP' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_DT_TRAMITES.NOMBRE' readOnly='false' dataType='string' titleKey='search.task' fieldType='LINKPARAM' fieldLink='SPAC_DT_TRAMITES.NOMBRE' styleClass='tdlink' url='showTask.do' >");
		xml.append("	<linkParam paramId='taskId' property='SPAC_DT_TRAMITES.ID_TRAM_EXP'/> ");
		xml.append("</propertyfmt>");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_DT_TRAMITES.ESTADO' readOnly='false' dataType='string' titleKey='search.state' fieldType='TASK_STATE' fieldLink='SPAC_DT_TRAMITES.ESTADO' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NUMEXP' readOnly='false' dataType='string' titleKey='search.numExp' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NUMEXP' comparator='ieci.tdw.ispac.ispacweb.comparators.NumexpComparator' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NREG' readOnly='false' dataType='string' titleKey='search.numRegistro' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NREG' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.IDENTIDADTITULAR' readOnly='false' dataType='string' titleKey='search.interesado' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.IDENTIDADTITULAR' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NIFCIFTITULAR' readOnly='false' dataType='string' titleKey='search.nifInteresado' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NIFCIFTITULAR' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.FAPERTURA' readOnly='false' dataType='date' titleKey='search.fechaApertura' fieldType='DATE' fieldLink='SPAC_EXPEDIENTES.FAPERTURA' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.ESTADOADM' readOnly='false' dataType='string' titleKey='search.estadoAdministrativo' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.ESTADOADM' validatetable='SPAC_TBL_004' substitute='SUSTITUTO' showproperty='SPAC_TBL_004.SUSTITUTO'  value='VALOR'/>");
		xml.append("<!--propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.HAYRECURSO' readOnly='false' dataType='string' titleKey='search.recurrido' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.HAYRECURSO' /-->");
		xml.append("<!--FIN CUERPO RESULTADO-->");
		xml.append("<!--INICIO ACCIONES-->");
		xml.append("<!--FIN ACCIONES-->");
		xml.append("</queryform>");		
		return xml.toString();
	}
	
	
	protected String getSearchFormXMLOpenTasks() {
		StringBuffer xml = new StringBuffer();
		
		xml.append("<?xml version='1.0' encoding='ISO-8859-1'?>");
		xml.append("<?xml-stylesheet type='text/xsl' href='SearchForm.xsl'?>");
		xml.append("<queryform displaywidth='95%' defaultSort='1' responsability='task'>");
		xml.append("<!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA-->");
		xml.append("<entity type='main' identifier='1'>");
		xml.append("<!--INICIO CUERPO BUSQUEDA-->");
		xml.append("	<tablename>spac_expedientes</tablename>");
		xml.append("	<description>DATOS DEL EXPEDIENTE</description>");
		xml.append("	<field type='query' order='01'>");
		xml.append("		<columnname>ID_PCD</columnname>");
		xml.append("		<description>search.form.procedimiento</description>");
		xml.append("		<datatype>integer</datatype>");
		xml.append("		<controltype size='30'  maxlength='30' tipo='validate' table='SPAC_P_PROCEDIMIENTOS' field='spac_expedientes:ID_PCD' label='NOMBRE'  value='ID' clause='WHERE ESTADO IN (2,3) AND TIPO = 1'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='04'>");
		xml.append("		<columnname>NUMEXP</columnname>");
		xml.append("		<description>search.form.numExpediente</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("		 	<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='30' maxlength='30'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='05'>");
		xml.append("		<columnname>ASUNTO</columnname>");
		xml.append("		<description>search.form.asunto</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='30' maxlength='30'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='06'>");
		xml.append("		<columnname>NREG</columnname>");
		xml.append("		<description>search.form.numRegistro</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='16' maxlength='16'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='07'>");
		xml.append("		<columnname>IDENTIDADTITULAR</columnname>");
		xml.append("		<description>search.form.interesadoPrincipal</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='50' maxlength='50'>text</controltype> ");
		xml.append("	</field>");
		xml.append("	<field type='query' order='08'>");
		xml.append("		<columnname>NIFCIFTITULAR</columnname>");
		xml.append("		<description>search.form.ifInteresadoPrincipal</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='16' maxlength='16'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='09'>");
		xml.append("		<columnname>FAPERTURA</columnname>");
		xml.append("		<description>search.form.fechaApertura</description>");
		xml.append("		<datatype>date</datatype> ");
		xml.append("		<controltype size='22' maxlength='22'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='10'>");
		xml.append("		<columnname>ESTADOADM</columnname>");
		xml.append("		<description>search.form.estadoAdm</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='20' maxlength='20' tipo='validate' table='SPAC_TBL_004' field='spac_expedientes:ESTADOADM' label='SUSTITUTO' value='VALOR'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='11'>     ");
		xml.append("		<columnname>HAYRECURSO</columnname>");
		xml.append("		<description>search.form.recurso</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='2' maxlength='2'>text</controltype>");
		xml.append("	</field>");
		xml.append("	<field type='query' order='12'>");
		xml.append("		<columnname>CIUDAD</columnname>");
		xml.append("		<description>search.form.ciudad</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype size='50' maxlength='50'>text</controltype>");
		xml.append("	</field>");
		xml.append("		<field type='query' order='13'>");
		xml.append("		<columnname>DOMICILIO</columnname>");
		xml.append("		<description>search.form.domicilio</description>");
		xml.append("		<datatype>string</datatype>");
		xml.append("		<operators>");
		xml.append("			<operator><name>=</name></operator>");
		xml.append("			<operator><name>&gt;</name></operator>");
		xml.append("			<operator><name>&gt;=</name></operator>");
		xml.append("			<operator><name>&lt;</name></operator>");
		xml.append("			<operator><name>&lt;=</name></operator>");
		xml.append(" 			<operator><name>Contiene(Like)</name></operator>");
		xml.append("      <operator><name>Contiene(Index)</name></operator>");
		xml.append("		</operators>");
		xml.append("		<controltype cols='100' rows='5'>textarea</controltype>");
		xml.append("	</field>");
		xml.append("<!--FIN CUERPO BUSQUEDA-->");
		xml.append("</entity>");
		xml.append("<!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA-->");
		xml.append("<!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA");
		xml.append("	obligatoria en la busqueda para que se relacione con un JOIN -->");
		xml.append("<entity type='required' identifier='51'>");
		xml.append("	<tablename>spac_tramites</tablename>");
		xml.append("	<bindfield>NUMEXP</bindfield>");
		xml.append("	<field type='query' order='03'>");
		xml.append("		<columnname>ID_TRAMITE</columnname>");
		xml.append("		<description>search.form.tramites</description>");
		xml.append("		<datatype>stringList</datatype>");
		xml.append("		<binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>");
		xml.append("		<controltype height='75px' tipo='list' table='SPAC_CT_TRAMITES' field='spac_tramites:ID_TRAMITE' label='NOMBRE'  value='id'>text</controltype>");
		xml.append("	</field>");
		xml.append("</entity>");
		xml.append("<!--FIN SEGUNDA ENTIDAD DE BUSQUEDA-->");
		xml.append("<!--INICIO CUERPO RESULTADO-->");
		xml.append("<!--propertyfmt type='BeanPropertySimpleFmt' property='SPAC_TRAMITES.ID' readOnly='false' dataType='string' fieldType='CHECKBOX' fieldLink='SPAC_TRAMITES.ID' /-->");
		xml.append("<!-- Necesario para obtener en la consulta la propiedad de SPAC_TRAMITES.ID que se necesita para generar el enlace del LINKPARAM -->");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_TRAMITES.ID' readOnly='false' dataType='string' fieldType='NONE' fieldLink='SPAC_DT_TRAMITES.ID' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_TRAMITES.NOMBRE' readOnly='false' dataType='string' titleKey='search.task' fieldType='LINKPARAM' fieldLink='SPAC_TRAMITES.NOMBRE' styleClass='tdlink' url='showTask.do' >");
		xml.append("	<linkParam paramId='taskId' property='SPAC_TRAMITES.ID'/> ");
		xml.append("</propertyfmt>");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NUMEXP' readOnly='false' dataType='string' titleKey='search.numExp' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NUMEXP' comparator='ieci.tdw.ispac.ispacweb.comparators.NumexpComparator' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NREG' readOnly='false' dataType='string' titleKey='search.numRegistro' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NREG' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.IDENTIDADTITULAR' readOnly='false' dataType='string' titleKey='search.interesado' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.IDENTIDADTITULAR' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NIFCIFTITULAR' readOnly='false' dataType='string' titleKey='search.nifInteresado' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NIFCIFTITULAR' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.FAPERTURA' readOnly='false' dataType='date' titleKey='search.fechaApertura' fieldType='DATE' fieldLink='SPAC_EXPEDIENTES.FAPERTURA' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.ESTADOADM' readOnly='false' dataType='string' titleKey='search.estadoAdministrativo' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.ESTADOADM' validatetable='SPAC_TBL_004' substitute='SUSTITUTO' showproperty='SPAC_TBL_004.SUSTITUTO'  value='VALOR'/>");
		xml.append("<!--propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.HAYRECURSO' readOnly='false' dataType='string' titleKey='search.recurrido' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.HAYRECURSO' /-->");
		xml.append("<!--FIN CUERPO RESULTADO-->");
		xml.append("<!--INICIO ACCIONES-->");
		xml.append("<!--FIN ACCIONES-->");
		xml.append("</queryform>");
		return xml.toString();
	}
	
	
	
	protected String getSearchFormXML(int defaultSort, String defaultOrder) {
		StringBuffer xml = new StringBuffer();
		
		xml.append("<?xml version='1.0' encoding='ISO-8859-1'?>");
		xml.append("<?xml-stylesheet type='text/xsl' href='SearchForm.xsl'?>");
		xml.append("<queryform displaywidth='95%'");
		
		if (defaultSort > 0) {
			xml.append(" defaultSort='").append(defaultSort).append("'");
			if (StringUtils.isNotBlank(defaultOrder)) {
				xml.append(" defaultOrder='").append(defaultOrder).append("'");
			}
		}
		
		xml.append(">");
		
		xml.append("<!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA-->");
		xml.append("<entity type='main' identifier='1'>");
		xml.append("<!--INICIO CUERPO BUSQUEDA-->");
		xml.append("  <tablename>spac_expedientes</tablename>");
		xml.append("  <description>DATOS DEL EXPEDIENTE</description>");
		xml.append("  <field type='query' order='01'>");
		xml.append("    <columnname>ID_PCD</columnname>");
		xml.append("    <description>search.form.procedimiento</description>");
		xml.append("    <datatype>integer</datatype>");
		xml.append("    <controltype size='30'  maxlength='30' tipo='validate' table='SPAC_P_PROCEDIMIENTOS' field='spac_expedientes:ID_PCD' label='NOMBRE'  value='ID' clause='WHERE ESTADO IN (2,3) AND TIPO = 1'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='04'>");
		xml.append("    <columnname>NUMEXP</columnname>");
		xml.append("    <description>search.form.numExpediente</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='30' maxlength='30'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='05'>");
		xml.append("    <columnname>ASUNTO</columnname>");
		xml.append("    <description>search.form.asunto</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='30' maxlength='30'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='06'>");
		xml.append("    <columnname>NREG</columnname>");
		xml.append("    <description>search.form.numRegistro</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='16' maxlength='16'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='07'>");
		xml.append("    <columnname>IDENTIDADTITULAR</columnname>");
		xml.append("    <description>search.form.interesadoPrincipal</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='50' maxlength='50'>text</controltype> ");
		xml.append("  </field>");
		xml.append("  <field type='query' order='08'>");
		xml.append("    <columnname>NIFCIFTITULAR</columnname>");
		xml.append("    <description>search.form.ifInteresadoPrincipal</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='16' maxlength='16'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='09'>");
		xml.append("    <columnname>FAPERTURA</columnname>");
		xml.append("    <description>search.form.fechaApertura</description>");
		xml.append("    <datatype>date</datatype> ");
		xml.append("    <controltype size='22' maxlength='22'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='10'>");
		xml.append("    <columnname>ESTADOADM</columnname>");
		xml.append("    <description>search.form.estadoAdm</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='20' maxlength='20' tipo='validate' table='SPAC_TBL_004' field='spac_expedientes:ESTADOADM' label='SUSTITUTO' value='VALOR'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='11'>     ");
		xml.append("    <columnname>HAYRECURSO</columnname>");
		xml.append("    <description>search.form.recurso</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='2' maxlength='2'>text</controltype>");
		xml.append("  </field>");
		xml.append("  <field type='query' order='12'>");
		xml.append("    <columnname>CIUDAD</columnname>");
		xml.append("    <description>search.form.ciudad</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype size='50' maxlength='50'>text</controltype>");
		xml.append("  </field>");
		xml.append("    <field type='query' order='13'>");
		xml.append("    <columnname>DOMICILIO</columnname>");
		xml.append("    <description>search.form.domicilio</description>");
		xml.append("    <datatype>string</datatype>");
		xml.append("    <operators>");
		xml.append("      <operator><name>=</name></operator>");
		xml.append("      <operator><name>&gt;</name></operator>");
		xml.append("      <operator><name>&gt;=</name></operator>");
		xml.append("      <operator><name>&lt;</name></operator>");
		xml.append("      <operator><name>&lt;=</name></operator>");
		xml.append("      <operator><name>Contiene(Like)</name></operator>");
		xml.append("    </operators>");
		xml.append("    <controltype cols='100' rows='5'>textarea</controltype>");
		xml.append("  </field>");
		xml.append("<!--FIN CUERPO BUSQUEDA-->");
		xml.append("</entity>");
		xml.append("<!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA-->");
		xml.append("<!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA-->");
		xml.append("<entity type='secondary' identifier='52'>");
		xml.append("  <tablename>spac_fases</tablename>");
		xml.append("  <bindfield>NUMEXP</bindfield>");
		xml.append("  <field type='query' order='02'>");
		xml.append("    <columnname>ID_FASE</columnname>");
		xml.append("    <description>search.form.fases</description>");
		xml.append("    <datatype>stringList</datatype>");
		xml.append("        <binding>in (select ID FROM SPAC_P_FASES WHERE ID_CTFASE IN(@VALUES))</binding>");
		xml.append("    <controltype height='75px' tipo='list' table='SPAC_CT_FASES' field='spac_fases:ID_FASE' label='NOMBRE'  value='id'>text</controltype>	");
		xml.append("  </field>");
		xml.append("</entity>");
		xml.append("<entity type='secondary' identifier='51'>");
		xml.append("  ");
		xml.append("  <tablename>spac_tramites</tablename>");
		xml.append("    <field type='query' order='03'>");
		xml.append("      <columnname>ID_TRAMITE</columnname>");
		xml.append("      <description>search.form.tramites</description>");
		xml.append("      <datatype>stringList</datatype>");
		xml.append("        <binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>");
		xml.append("      <controltype height='75px' tipo='list' table='SPAC_CT_TRAMITES' field='spac_tramites:ID_TRAMITE' label='NOMBRE'  value='id'>text</controltype>");
		xml.append("    </field>");
		xml.append("  <bindfield>NUMEXP</bindfield>");
		xml.append("</entity>");
		xml.append("<!--FIN SEGUNDA ENTIDAD DE BUSQUEDA-->");
		xml.append("<!--INICIO CUERPO RESULTADO-->");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_FASES.ID' readOnly='false' dataType='string' fieldType='CHECKBOX' fieldLink='SPAC_FASES.ID' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NUMEXP' readOnly='false' dataType='string' titleKey='search.numExp' fieldType='LINK' fieldLink='SPAC_EXPEDIENTES.NUMEXP' comparator='ieci.tdw.ispac.ispacweb.comparators.NumexpComparator' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NREG' readOnly='false' dataType='string' titleKey='search.numRegistro' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NREG' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.IDENTIDADTITULAR' readOnly='false' dataType='string' titleKey='search.interesado' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.IDENTIDADTITULAR' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.NIFCIFTITULAR' readOnly='false' dataType='string' titleKey='search.nifInteresado' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.NIFCIFTITULAR' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.FAPERTURA' readOnly='false' dataType='date' titleKey='search.fechaApertura' fieldType='DATE' fieldLink='SPAC_EXPEDIENTES.FAPERTURA' />");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.ESTADOADM' readOnly='false' dataType='string' titleKey='search.estadoAdministrativo' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.ESTADOADM' validatetable='SPAC_TBL_004' substitute='SUSTITUTO' showproperty='SPAC_TBL_004.SUSTITUTO'  value='VALOR'/>");
		xml.append("<propertyfmt type='BeanPropertySimpleFmt' property='SPAC_EXPEDIENTES.HAYRECURSO' readOnly='false' dataType='string' titleKey='search.recurrido' fieldType='LIST' fieldLink='SPAC_EXPEDIENTES.HAYRECURSO' />");
		xml.append("<!--FIN CUERPO RESULTADO-->");
		xml.append("<!--INICIO ACCIONES-->");
		xml.append("<action title='Cerrar expedientes' path='/closeProcess.do' titleKey='ispac.action.expedients.close' />");
		xml.append("<action title='Avanzar fases' path='/closeStage.do' titleKey='ispac.action.stages.next' />");
		xml.append("<action title='Retroceder fases' path='/redeployPreviousStage.do' titleKey='ispac.action.stages.return' />");
		xml.append("<!--FIN ACCIONES-->");
		xml.append("</queryform>");

		return xml.toString();
	}


	protected static void logTasksSearchResults(IItemCollection results) throws ISPACException {
		
		Assert.assertNotNull(results);
		
		while (results.next()) {
			IItem row = results.value();
			String taskName;
			if (row.getProperty("SPAC_DT_TRAMITES.NOMBRE") != null){
				taskName= "TRAMITE=["+row.getString("SPAC_DT_TRAMITES.NOMBRE") +"],";
			}else{
				taskName= "TRAMITE=["+row.getString("SPAC_TRAMITES.NOMBRE") +"],";				
			}
			 
			logger.info(taskName + "NUM_EXP=[" + row.getString("SPAC_EXPEDIENTES.NUMEXP") + "], FAPERTURA=[" + row.getString("SPAC_EXPEDIENTES.FAPERTURA") + "]");
		}
	}

	protected static void logSearchResults(IItemCollection results) throws ISPACException {
		
		Assert.assertNotNull(results);
		
		while (results.next()) {
			IItem row = results.value();
			logger.info("NUM_EXP=[" + row.getString("SPAC_EXPEDIENTES.NUMEXP") + "], FAPERTURA=[" + row.getString("SPAC_EXPEDIENTES.FAPERTURA") + "]");
		}
	}

	protected static void logSearchResults(SearchResultVO searchResult) throws ISPACException {

		Assert.assertNotNull(searchResult);
		
		logger.info("NumMaxRegistros: " + searchResult.getNumMaxRegistros());
		logger.info("NumTotalRegistros: " + searchResult.getNumTotalRegistros());
		
		logSearchResults(searchResult.getResultados());
	}

}
