package ieci.tdw.ispac.ispacweb.dwr;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispacweb.dwr.vo.SimpleBean;

import java.util.List;

import org.apache.log4j.Logger;

public class CatalogAPI extends BaseDWR {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(CatalogAPI.class);
	
	/*help.tipo_obj.0											=Bandeja Entrada
	help.tipo_obj.1												=Formulario de B\u00fasqueda
	help.tipo_obj.2												=Diseñador
	help.tipo_obj.3												=Procedimiento
	help.tipo_obj.4												=Avisos electr\u00f3nicos
	help.tipo_obj.5												=Documentos distribuidos
	help.tipo_obj.6												=Plazos vencidos
	help.tipo_obj.7												=Circuito de Firma
	helptipo_obj.8												=Resultado de Búsqueda
	*/
	
	public static final int TIPO_OBJETO_BANDEJA_ENTRADA						=0;
	public static final int TIPO_OBJETO_FRM_BUSQUEDA						=1;
	public static final int TIPO_OBJETO_DISENADOR							=2;
	public static final int TIPO_OBJETO_PROCEDIMIENTO						=3;
	public static final int TIPO_OBJETO_AVISOS_ELECTRONICOS					=4;
	public static final int TIPO_OBJETO_DOCUMENTOS_DISTRIBUIDOS				=5;
	public static final int TIPO_OBJETO_PLAZOS_VENCIDOS						=6;
	public static final int TIPO_OBJETO_CIRCUITO_FIRMA						=7;
	public static final int TIPO_OBJETO_RESULTADO_BUSQUEDA					=8;
	

	
	/**
	 * Devuelve el número de expedientes que existen para un procedimiento
	 * determinado
	 * @param idPcd : Identificador del procedimiento
	 * @return Número de expedientes existentes para el idPcd recibido como parámetro
	 * 
	 * @throws ISPACException
	 */
	public SimpleBean countExpsByPcd(int idPcd)throws ISPACException {
		
		ISessionAPI sessionAPI = null;
		sessionAPI = createSessionAPI();
		IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
		IProcedureAPI procedureAPI = invesflowAPI.getProcedureAPI();
		SimpleBean simpleBean = new SimpleBean("NUMEXPS","0");
		
		try{
			int numExps= procedureAPI.countExpedientesByPcd(idPcd);
			if(logger.isDebugEnabled()){
				logger.debug("CatalogAPI:countExpsByPcd --> numExps"+numExps);
			}
			simpleBean.setValue(numExps+"");
		}catch(ISPACException e){
			logger.error("Error al obtener el número de expedientes de un procedimiento",e);
			throw e;
		}finally{
			releaseSessionAPI (sessionAPI);
		}
		return simpleBean;
	}
	
	public SimpleBean[] getObjectToHelp(int tipoObj) throws ISPACException{
		
		ISessionAPI sessionAPI = null;
		sessionAPI = createSessionAPI();
		IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
		ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
		SimpleBean[] simpleBeans=null;
		
		try {
			//Solo se tratan aquellos objetos que en el tramitador tengan ventana propia con la ayuda
			switch(tipoObj){
				case TIPO_OBJETO_FRM_BUSQUEDA: 				simpleBeans= getFrmSearchBeans(catalogAPI.getSearchForms().toList());
															break;
				case TIPO_OBJETO_PROCEDIMIENTO: 			simpleBeans= getProcedureBeans(catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_PROCEDURE, "WHERE TIPO=" + IProcess.PROCESS_TYPE).toList());
															break;
				case TIPO_OBJETO_RESULTADO_BUSQUEDA:		simpleBeans= getFrmSearchBeans(catalogAPI.getSearchForms().toList());
															break;
																				
			}
			
			return simpleBeans;
		} catch (ISPACException e) {
			logger.error("Error al obtener los circuitos de firma", e);
			throw e;
		}finally {
			releaseSessionAPI(sessionAPI);
		}		
	
	}
	
	
	/**
	 * Conviernte la lista de items a array de simpleBean 
	 * Requisito: El item debe tener campo id y nombre
	 * @param itemcol
	 * @return
	 * @throws ISPACException 
	 */
	protected SimpleBean[] getFrmSearchBeans(List lista) throws ISPACException {
		
		SimpleBean[] beans = null;
		if (lista != null) {
			
			beans = new SimpleBean[lista.size()+1];
			beans[0]=new SimpleBean("-1","");
			int i;
			for (i = 0; i < lista.size(); i++) {
				IItem item = (IItem) lista.get(i);
				beans[i+1] = new SimpleBean(item.getKey().toString(), item.getString("DESCRIPCION"));
			}
		}
		return beans;
	}
	
	protected SimpleBean[] getProcedureBeans(List lista) throws ISPACException {
		SimpleBean[] beans = null;
		if (lista != null) {
			
			beans = new SimpleBean[lista.size()+1];
			beans[0]=new SimpleBean("-1","");
			int i;
			for (i = 0; i < lista.size(); i++) {
				IItem item = (IItem) lista.get(i);
				beans[i+1] = new SimpleBean(item.getKey().toString(), item.getString("NOMBRE")+"_"+item.getString("NVERSION"));
			}
		}
		return beans;
	}
	
}
