/*
 * Created on 20-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;


/**
 *
 *
 *
 */
public interface IProcedure extends IItem
{

    static public final int PCD_STATE_DRAFT=1;
    static public final int PCD_STATE_CURRENT=2;
    static public final int PCD_STATE_OLD=3;

	public final int PROCEDURE_TYPE = 1;
	public final int SUBPROCESS_TYPE = 2;    
    
	public int getId()
		throws ISPACException;

	public int getVersion()
		throws ISPACException;

	public String getNombre()
		throws ISPACException;

	public int getEstado()
		throws ISPACException;

	public IItem getStage(int nIdStagePCD);

	public IItem getTask(int nIdTaskPCD);

	public IItemCollection getStages();

	public IItemCollection getTasks();

	public IItemCollection getTasks(int nIdStagePCD)
		throws ISPACException;
	
	public IItemCollection getTasks(int nIdStagePCD, int type)
	throws ISPACException;
}
