/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/procedure/IPcdElement.java,v $
 * $Revision: 1.6 $
 * $Date: 2008/04/04 08:33:40 $
 * $Author: ildefong $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.List;
import java.util.Map;

/**
 * IPcdElement
 *
 *
 * @version $Revision: 1.6 $ $Date: 2008/04/04 08:33:40 $
 */
public interface IPcdElement {
	
	//=========================================================================
	// Constantes para los tipos de procesos
	//=========================================================================
	public static final int TYPE_PROCEDURE = 1;
	public static final int TYPE_SUBPROCEDURE = 2;
	//=========================================================================
	
    public int getId() throws ISPACException;
    public Integer getIntegerId() throws ISPACException;

    public int getCatalogId() throws ISPACException;
    public Integer getCatalogIntegerId() throws ISPACException;

    public void load(DbCnt cnt) throws ISPACException;
    public void store(DbCnt cnt) throws ISPACException;
    public void delete(DbCnt cnt) throws ISPACException;

    public IPcdElement duplicate(DbCnt cnt) throws ISPACException;

    public void setProcedureId(int procedureId) throws ISPACException;
    public void setStagePcdId(int stagePcdId) throws ISPACException;
    public void setTaskPcdId(int taskPcdId) throws ISPACException;



    public Integer getOrigId() throws ISPACException;
    public Integer getDestId() throws ISPACException;
    public void setOrigId(Integer elementId) throws ISPACException;
    public void setDestId(Integer elementId) throws ISPACException;


    public List getOrigElement() throws ISPACException;
    public List getDestElement() throws ISPACException;
    public void addOrigElement(IPcdElement element) throws ISPACException;
    public void addDestElement(IPcdElement element) throws ISPACException;

    public String toXml() throws ISPACException;
    public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds, Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds) throws ISPACException;
    public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds, Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds, Map ctHelpsIds) throws ISPACException;
    
}
