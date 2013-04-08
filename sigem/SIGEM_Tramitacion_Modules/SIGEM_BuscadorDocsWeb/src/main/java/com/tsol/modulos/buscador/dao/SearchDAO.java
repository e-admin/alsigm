package com.tsol.modulos.buscador.dao;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tsol.modulos.buscador.beans.SearchBean;

public class SearchDAO {
	
	private static final Logger LOGGER = Logger.getLogger(SearchDAO.class);
	
	public static SearchBean searchDocument(ClientContext ctx, String codCotejo)
			throws ISPACException {
		
		SearchBean searchBean = null;
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Buscando el documento con el código de cotejo: [" + codCotejo + "]");
		}
		
		if (StringUtils.isNotBlank(codCotejo)) {
			
			IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
			
			String sql = new StringBuffer(" WHERE ")
	    		 .append(" COD_COTEJO='")
	    		 .append(DBUtil.replaceQuotes(codCotejo.trim()))
	    		 .append("' ORDER BY ID DESC")
	    		 .toString();

	    	IItemCollection documentos = entitiesAPI.queryEntities(ISPACEntities.DT_ID_DOCUMENTOS, sql);
			if ((documentos != null) && documentos.next()) {
				
				IItem documento = documentos.value();
				if (documento != null) {
					
					// nombre, numexp, fdoc, tp_reg, id, infopag_rde 
					searchBean = new SearchBean();
					searchBean.setId(documento.getString("ID"));
					searchBean.setNombre(documento.getString("NOMBRE"));
					searchBean.setNumExp(documento.getString("NUMEXP"));
					searchBean.setCodCotejo(documento.getString("COD_COTEJO"));
					searchBean.setFechaDoc(documento.getString("FDOC"));
					searchBean.setTpReg(documento.getString("TP_REG"));
					searchBean.setInfopag(documento.getString("INFOPAG_RDE"));
				}
			}					
		}
		
		return searchBean;
	}
	
}
