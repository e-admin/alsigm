package ieci.tdw.ispac.ispacweb.dwr;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.dwr.vo.SimpleBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class SearchFormAPI extends BaseDWR {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SearchFormAPI.class);

	/**
	 * 
	 * @param code
	 * @param nameTable
	 * @param clause
	 * @param nombreCodigo
	 * @param nombreDescripcion
	 * @return
	 * @throws ISPACException
	 */
	public SimpleBean getValueByCode(String code, String nameTable,
			String clause, String nombreCodigo, String nombreDescripcion)
	throws ISPACException {

		ISessionAPI sessionAPI = null;
		try {
			sessionAPI = createSessionAPI();
			String query = "";
			if (StringUtils.isNotBlank(clause)) {
				if (!StringUtils.containsIgnoreCase(clause, "where")) {
					query = "WHERE ";
				}
				query +=" VIGENTE=1 "+ clause + " AND "+DBUtil.getToUpperSQL(nombreCodigo)+" = '" + DBUtil.replaceQuotes(code.toUpperCase()) + "'";
			} else {
				query = "WHERE VIGENTE=1 AND "+DBUtil.getToUpperSQL(nombreCodigo)+" = '" + DBUtil.replaceQuotes(code.toUpperCase()) + "'";
			}
			// Ejecutamos la consulta

			String sql = "SELECT * FROM " + nameTable + " " + query;
			DbCnt cnt = sessionAPI.getClientContext().getConnection();
			DbResultSet dbRs = null;
			// Ejecutamos la consulta
			dbRs = cnt.executeQuery(sql);
			ResultSet rs = dbRs.getResultSet();
			SimpleBean simpleBean = new SimpleBean();
			if (rs.next()) {
				simpleBean.setKey(rs.getString(nombreCodigo));
				simpleBean.setValue(rs.getString(nombreDescripcion));
				simpleBean.setId(rs.getString("ID"));

			} else {
				logger.error("No existe valor asociado al código:" + code
						+ " en la tabla " + nameTable);
				return null;
			
			}
			return simpleBean;

		} catch (ISPACException e) {
			logger.error("Error en  getValueByCode Codigo:" + code
					+ "nombre de la tabla de validación: " + nameTable, e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error en  getValueByCode Codigo:" + code
					+ "nombre de la tabla de validación: " + nameTable, t);
			throw new ISPACInfo("exception.search.getValueByCode", new Object[]{code});
		
		} finally {
			releaseSessionAPI(sessionAPI);
		}
	}
	/**
	 * Obtiene el valor asociado al código recibido en el parámetro code si en la jerarquica está relacionado 
	 * con el valor del padre , parentValue , si el campo parentValue o hierarchicalId no está informado no se tiene
	 * en cuenta la relación jerárquica
	 * @param code
	 * @param nameTable
	 * @param clause
	 * @param nombreCodigo
	 * @param nombreDescripcion
	 * @param parentValue
	 * @param hierarchicalId
	 * @return
	 * @throws ISPACException
	 */
	public SimpleBean getHierarchicalValueByCode(String code, String nameTable,
			String clause, String nombreCodigo, String nombreDescripcion , String parentValue, String hierarchicalId) throws ISPACException{
		
		//Comprobamos que el campo pertenecezca a un jerarquia y que el valor introducido esté relacionado con el padre
		ISessionAPI sessionAPI = null;

		try {
			SimpleBean simpleBean=getValueByCode(code, nameTable, clause, nombreCodigo, nombreDescripcion);
	
			if(StringUtils.isNotBlank(hierarchicalId) && StringUtils.isNotBlank(parentValue) && simpleBean!=null) {
				sessionAPI = createSessionAPI();
				DbCnt cnt = sessionAPI.getClientContext().getConnection();
				DbResultSet dbRs = null;
				//Obtenemos el id del código del padre
				String sql="SELECT * FROM SPAC_CT_ENTIDADES WHERE ID IN (SELECT ID_ENTIDAD_PADRE FROM SPAC_CT_JERARQUIAS WHERE ID="+hierarchicalId+")";
				dbRs = cnt.executeQuery(sql);
				ResultSet rs = dbRs.getResultSet();
				if(rs.next()){
					String entidadPadre=rs.getString("NOMBRE");
					sql = "SELECT COUNT(*)  FROM SPAC_CT_JERARQUIA_" + hierarchicalId + " WHERE ID_PADRE IN(SELECT ID FROM "+entidadPadre +" WHERE VALOR='"+parentValue+"') AND ID_HIJA="+simpleBean.getId();
					// Ejecutamos la consulta
					dbRs = cnt.executeQuery(sql);
					rs = dbRs.getResultSet();
					if (rs.next() && rs.getInt(1)>0){
				
						return simpleBean;
		
					} else {
						if(logger.isDebugEnabled()){
							logger.debug("El código:"+code +" tiene como valor asociado: "
										+simpleBean.getValue() 
										+"pero no pertenece a la relación jerarquica cuando el valor del padre es el:"
										+parentValue);
						}
						return null;
					}
					
				
				}
				else{
					throw new ISPACInfo("exception.search.getHierarchicalValueByCode", new Object[]{code});
				}
				}
				return simpleBean;
				} catch (SQLException e) {
				logger.error("Error en  getValueByCode Codigo:" + code
						+ "nombre de la tabla de validación: " + nameTable+" identificador de la jerarquía: "+hierarchicalId, e);
				throw new ISPACInfo("exception.search.getHierarchicalValueByCode", new Object[]{code});
				} finally {
				releaseSessionAPI(sessionAPI);
			}
	
	}
		
	/**
	 * Obtiene los valores relacionado con el padre , parenteValue, a través de la jerarquía, hierarchicalId
	 * @param parentValue
	 * @param hierarchicalId
	 * @param label
	 * @return
	 * @throws ISPACException
	 */
	public SimpleBean[] getChildValues (String parentValue, String hierarchicalId ,String valor, String label) throws ISPACException{
		SimpleBean[] beans = null;
		if(StringUtils.isNotBlank(parentValue)){
			ISessionAPI sessionAPI = null;
				try{
					sessionAPI = createSessionAPI();
					ICatalogAPI catalogAPI= sessionAPI.getAPI().getCatalogAPI();
					IItemCollection itemcol=catalogAPI.getHierarchicalDescendantValues(Integer.parseInt(hierarchicalId),parentValue);
				    List lista=itemcol.toList();
				    if (lista != null) {
						beans = new SimpleBean[lista.size()+1];
						beans[0]=new SimpleBean("","");
						int i;
						for (i = 0; i < lista.size(); i++) {
							IItem item = (IItem) lista.get(i);
							beans[i+1] = new SimpleBean(item.getString("ID"),item.getString(valor), item.getString(label));
						}
				    }
					} catch (ISPACException e) {
					logger.error("Error en  getChildValues parentValue:" + parentValue
							+ " hierarchicalId: " + hierarchicalId, e);
					throw new ISPACInfo("exception.search.getChildValues");
				} finally {
					releaseSessionAPI(sessionAPI);
			}
		}
		return beans;
	}
	
	/**
	 * En los campos de tipo validatedFieldByCode , cuando se establece el valor mediante la lupa , se ha de indicar los
	 * valores a actualizar . (Ver ParameterTag y updateFieldTag)
	 * @param nameMap
	 * @param nameAttribute
	 * @param valueAttribute
	 * @throws ISPACException
	 */
	public void addJavascriptToSetValue(String nameField , String nameAttribute , String updateAttribute) throws ISPACException{
		
		 HttpServletRequest request=getHttpServletRequest();
		 HttpSession session=request.getSession();
		 HashMap parameters = new HashMap();
		 if(session.getAttribute(nameField)!=null){
			 parameters=(HashMap) session.getAttribute(nameField);
		 }
		 parameters.put( nameAttribute, updateAttribute);
		 session.setAttribute(nameField, parameters);
		
	}
}

