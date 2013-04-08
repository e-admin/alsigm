package ieci.tdw.ispac.ispacpublicador.business.reader.ispac;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacpublicador.business.reader.IReader;
import ieci.tdw.ispac.ispacpublicador.business.reader.ispac.dao.MilestoneDAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ISPACReader implements IReader {

    /** Logger de la clase. */
    private static final Logger logger = Logger.getLogger(ISPACReader.class);
    
    /** Nombre asignado al sistema externo sobre el que se aplica este lector. */
    private final String ID_SISTEMA_EXTERNO = "TRAMITADOR";
    
    /** Contexto de cliente. */
    private IClientContext context = null;
    
    
    /**
     * Constructor.
     *
     */
    public ISPACReader() {
    	this.context = new ClientContext();
    }
    
	/**
	 * Obtiene la lista de nuevos hitos, tomando como filtro (límite inferior) 
	 * el valor <code>'infLimitId'</code>
	 * @param infLimitId Límite inferior de identifcador de Hito.
	 * @return Lista de hitos. 
	 */
    public List getMilestoneList(int infLimitId) {
    	List milestones = new ArrayList();
    	DbCnt cnt = null;
    	
        try {
        	cnt = context.getConnection();
			List list = MilestoneDAO.getNewMilestoneList(cnt, infLimitId);
			if (!CollectionUtils.isEmpty(list)) {
				milestones.addAll(list);
			}
		} catch (ISPACException e) {
			logger.error("Error en el lector: " + this.getClass().getName(), e);
		} finally {
			context.releaseConnection(cnt);
		}
		
		return milestones;
    }

	/**
	 * Obtiene el identificador del sistema externo sobre el que se realiza la
	 * lectura de nuevos hitos.
	 * 
	 * @return Identificador del sistema externo.
	 */
    public String getIdSistemExterno() {
        return ID_SISTEMA_EXTERNO;
    }
}
