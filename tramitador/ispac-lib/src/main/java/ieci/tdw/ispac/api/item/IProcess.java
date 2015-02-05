/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 *
 */
public interface IProcess extends IItem
{
	public int OPEN = 1;
	public int CLOSED = 2;
	public int CANCELED = 3;
	public int ARCHIVED	= 4;
	public int DELETED	= 5;

	public int PROCESS_TYPE = 1;
	public int SUBPROCESS_TYPE = 2;
	
	public IItemCollection getStages()
	throws ISPACException;

	
	/**
	 * @param stagePcdId identificador de fase en el procedimiento
	 * @return una fase activa para el proceso cuya fase en el procedimiento sea la indicada en <code>stagePcdId</code>
	 * @throws ISPACException
	 */
	public IStage getStage(DbCnt cnt,int stagePcdId)
	throws ISPACException;
	
	public IItemCollection getOpenedStages()
	throws ISPACException;

	public IItemCollection getMilestones()
	throws ISPACException;

	public IItemCollection getSyncnodes()
	throws ISPACException;

	public IItemCollection getTasks()
	throws ISPACException;

	public IItemCollection getTasks(int nIdStage)
	throws ISPACException;
	
	public IItemCollection getActivities()
	throws ISPACException;

	public IItemCollection getOpenActivities()
	throws ISPACException;
	
	public boolean isProcess()
	throws ISPACException;
	
	public boolean isSubProcess()
	throws ISPACException;
	
	public String getDeadlineXML(DbCnt cnt) throws ISPACException;
	

}
